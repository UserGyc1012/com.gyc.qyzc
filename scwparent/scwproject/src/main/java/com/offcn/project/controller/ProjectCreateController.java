package com.offcn.project.controller;

import com.alibaba.fastjson.JSON;
import com.offcn.dycommon.response.AppResponse;
import com.offcn.project.contants.ProjectConstant;
import com.offcn.project.enums.ProjectStatusEnume;
import com.offcn.project.po.TReturn;
import com.offcn.project.service.ProjectCreateService;
import com.offcn.project.service.ProjectInfoService;
import com.offcn.project.vo.req.ProjectBaseVoInfo;
import com.offcn.project.vo.req.ProjectRedisStorageVo;
import com.offcn.project.vo.req.ProjectReturnVo;
import com.offcn.vo.BaseVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName ProjectCreateController
 * @Deacription TODO
 * @Author 葛言超
 * @Date 2020/11/3 19:13
 * version:1.0 * 注意：本内容仅限于公司内部传阅，禁止外泄以及用于其他的商业目
 */
@Api(tags = "项目基本功能模块（创建、保存、项目信息获取、文件上传等）")
@RequestMapping("/project")
@RestController
public class ProjectCreateController {
    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    @Autowired
    private ProjectCreateService projectCreateService;


    @ApiOperation("项目发起第1步-阅读同意协议")
    @PostMapping("/init")
    public AppResponse<String> init(BaseVo vo){
        String accessToken = vo.getAccessToken();
        String memberId = stringRedisTemplate.opsForValue().get(accessToken);
        if(StringUtils.isEmpty(memberId)){
            return AppResponse.FAIL("没权限请登录");
        }
        int i = Integer.parseInt(memberId);

        String s = projectCreateService.initCreateProjiect(i);
        return AppResponse.OK(s);
    }
    @ApiOperation("项目发起第二步-保存项目的基本信息")
    @PostMapping("/savebaseInfo")
    public AppResponse<String> savebaseInfo(ProjectBaseVoInfo vo){
        //1.取得redis中之前储存的项目信息（就是哪一个傻逼id）
        String ID = stringRedisTemplate.opsForValue().get(ProjectConstant.TEMP_PROJECT_PREFIX + vo.getProjectToken());
        //2.转换为redis储存对应的vo对象
        ProjectRedisStorageVo projectRedisStorageVo = JSON.parseObject(ID, ProjectRedisStorageVo.class);
        //3.数据替换后，将收集的数据，复制到和redis映射的vo中
        BeanUtils.copyProperties(vo,projectRedisStorageVo);
        //4.将这个vo对象转换为json字符串
        String string = JSON.toJSONString(projectRedisStorageVo);
        //5.重新更新到redis
        stringRedisTemplate.opsForValue().set(ProjectConstant.TEMP_PROJECT_PREFIX + vo.getProjectToken(), string);
        return AppResponse.OK("OK");
    }

    @ApiOperation("项目发起第3步-项目保存项目回报信息")
    @PostMapping("/savereturn")
    public AppResponse<Object> saveReturnInfo(@RequestBody List<ProjectReturnVo> pro){
        ProjectReturnVo projectReturnVo = pro.get(0);
        String projectToken = projectReturnVo.getProjectToken();
        //得到redis中之前存储的项目信息
        String projectContext = stringRedisTemplate.opsForValue().get(ProjectConstant.TEMP_PROJECT_PREFIX + projectToken);
//              2.将里面的ProjectRedisStorageVo，转换为ProjectRedisStorageVo
//          因为他的类型没有发生变化，
//        主要是ProjectReturn是ProjectRedisStorageVo的一部分

        ProjectRedisStorageVo storageVo = JSON.parseObject(projectContext, ProjectRedisStorageVo.class);
        List<TReturn> returns =new ArrayList<>();

        for (ProjectReturnVo returnVo : pro) {
            TReturn tReturn = new TReturn();
            BeanUtils.copyProperties(returnVo,tReturn);
            returns.add(tReturn);
        }
        //4.更新return集合
        storageVo.setProjectReturns(returns);
        String string = JSON.toJSONString(storageVo);
        stringRedisTemplate.opsForValue().set(ProjectConstant.TEMP_PROJECT_PREFIX+projectToken,string);
        return AppResponse.OK("ok");
    }
    @ApiOperation("项目发起第4步-项目保存项目回报信息")
    @ApiImplicitParams({

            @ApiImplicitParam(name = "accessToken",value = "用户令牌",required = true),
            @ApiImplicitParam(name = "projectToken",value="项目标识",required = true),
            @ApiImplicitParam(name="ops",value="用户操作类型 0-保存草稿 1-提交审核",required = true)
    })
    @PostMapping("/submit")
    public AppResponse<Object> submit(String accessToken,String projectToken,String ops) {
        //1.先进行校验，检查accessToken
        String memberID = stringRedisTemplate.opsForValue().get(accessToken);
        if (StringUtils.isEmpty(memberID)) {
            return AppResponse.FAIL("无权限请登录");
        }
            //2、通过项目token 获取redis存储对象
            String projectJsonStr = stringRedisTemplate.opsForValue().get(ProjectConstant.TEMP_PROJECT_PREFIX + projectToken);
            ProjectRedisStorageVo redisStorageVo = JSON.parseObject(projectJsonStr,ProjectRedisStorageVo.class);
            if (redisStorageVo != null && !StringUtils.isEmpty(ops)) {
                if ("1".equals(ops)) {
                    projectCreateService.saveProjectInfo(ProjectStatusEnume.SUBMIT_AUTH, redisStorageVo);
                    return AppResponse.OK("提交成功，项目处于审核中");

                } else {
                    projectCreateService.saveProjectInfo(ProjectStatusEnume.DRAFT, redisStorageVo);
                    return AppResponse.OK("提交成功，项目存于草稿中");
                }
            }

            return AppResponse.FAIL("提交失败");
        }

    }
