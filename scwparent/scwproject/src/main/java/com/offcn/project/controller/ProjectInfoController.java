package com.offcn.project.controller;

import com.offcn.dycommon.response.AppResponse;
import com.offcn.project.enums.ProjectImageTypeEnume;
import com.offcn.project.po.*;
import com.offcn.project.service.ProjectInfoService;
import com.offcn.project.vo.resp.ProjectInfoVo;
import com.offcn.project.vo.resp.ProjectVo;
import com.offcn.util.OssTemplate;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @ClassName ProjectInfoController
 * @Deacription TODO
 * @Author 葛言超
 * @Date 2020/11/3 17:01
 * version:1.0 * 注意：本内容仅限于公司内部传阅，禁止外泄以及用于其他的商业目
 */
@Api(tags = "项目基本功能模块（文件上传、项目信息获取等）")
@RequestMapping("/project")
@RestController
public class ProjectInfoController {
    @Autowired
    private OssTemplate ossTemplate;
    @Autowired
    private ProjectInfoService projectInfoService;

    @ApiOperation("文件上传功能")
    @PostMapping("/upload")
    public AppResponse<Map<String, Object>> upload(@RequestParam("file") MultipartFile[] files) throws IOException {
        Map<String, Object> map = new HashMap<>();
        List<String> list = new ArrayList<>();
        if (files != null && files.length > 0) {
            for (MultipartFile item : files) {
                String upload = ossTemplate.upload(item.getInputStream(), item.getOriginalFilename());
                list.add(upload);
            }
        }
        map.put("urls", list);

        //Logger.debug("ossTemplate信息：{},文件上传成功访问路径{}",ossTemplate,list);
        return AppResponse.OK(map);
    }

    @ApiOperation("获取项目回报列表")
    @GetMapping("/details/returns/{projectId}")
    public AppResponse<List<TReturn>> detailsReturn(@PathVariable("projectId") Integer projectId) {

        List<TReturn> returns = projectInfoService.gerProjectReturns(projectId);
        return AppResponse.OK(returns);
    }

    @ApiOperation("获取系统所有的项目")
    @GetMapping("/all")
    public AppResponse<List<ProjectVo>> all() {
        //1.创建集合用来存储全部项目的VO
        List<ProjectVo> proVo = new ArrayList<>();
        //2.查询所有项目
        List<TProject> allProjects = projectInfoService.getAllProjects();
        for (TProject tProject : allProjects) {
            //获取项目编号
            Integer id = tProject.getId();
//根据项目编号获取项目配图
            List<TProjectImages> images = projectInfoService.getProjectImages(id);
            ProjectVo projectVo = new ProjectVo();
            BeanUtils.copyProperties(tProject, projectVo);
            //遍历项目配图集合
            for (TProjectImages tProjectImages : images) {
                //如果图片类型是头部图片，则设置头部图片路径到项目VO
                if (tProjectImages.getImgtype() == ProjectImageTypeEnume.HEADER.getCode()
                ) {
                    projectVo.setHeaderImage(tProjectImages.getImgurl());
                }
            }
            proVo.add(projectVo);
        }
        return AppResponse.OK(proVo);
    }
    @ApiOperation("获取项目信息详情")
    @GetMapping("/details/info/{projectId}")
    public AppResponse<ProjectInfoVo> detailsInfo(@PathVariable("projectId") Integer projectId) {
        TProject projectInfo = projectInfoService.getProjectInfo(projectId);
        ProjectInfoVo projectVo = new ProjectInfoVo();
        // 1、查出这个项目的所有图片
        List<TProjectImages> projectImages = projectInfoService.getProjectImages(projectInfo.getId());
        List<String> detailsImage = new ArrayList<>();
        for (TProjectImages tProjectImages : projectImages) {
            if (tProjectImages.getImgtype() == 0) {
                projectVo.setHeaderImage(tProjectImages.getImgurl());
            } else {
                detailsImage.add(tProjectImages.getImgurl());
            }
        }
        projectVo.setDetailsImage(detailsImage);

        // 2、项目的所有支持回报；
        List<TReturn> returns = projectInfoService.gerProjectReturns(projectInfo.getId());
        projectVo.setProjectReturns(returns);

        BeanUtils.copyProperties(projectInfo, projectVo);
        return AppResponse.OK(projectVo);
    }
    @ApiOperation("获取系统所有的项目标签")
    @GetMapping("/tags")
    public AppResponse<List<TTag>> tags() {
        List<TTag> tags = projectInfoService.getAllProjectTags();
        return AppResponse.OK(tags);
    }
    @ApiOperation("获取系统所有的项目分类")
    @GetMapping("/types")
    public AppResponse<List<TType>> types() {
        List<TType> types = projectInfoService.getProjectTypes();
        return AppResponse.OK(types);
    }
    @ApiOperation("获取回报信息")
    @GetMapping("/returns/info/{returnId}")
    public AppResponse<TReturn> getTReturn(@PathVariable("returnId") Integer returnId){
        TReturn tReturn = projectInfoService.getReturnInfo(returnId);
        return AppResponse.OK(tReturn);
    }
}
