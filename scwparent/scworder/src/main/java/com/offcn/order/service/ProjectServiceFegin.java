package com.offcn.order.service;

import com.offcn.dycommon.response.AppResponse;
import com.offcn.order.po.TReturn;
import com.offcn.order.service.impl.ProjectServiceFeignException;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

/**
 * @ClassName ProjectServiceFegin
 * @Deacription TODO
 * @Author 葛言超
 * @Date 2020/11/4 16:28
 * version:1.0 * 注意：本内容仅限于公司内部传阅，禁止外泄以及用于其他的商业目
 */

@FeignClient(value = "SCW-PROJECT",fallback = ProjectServiceFeignException.class)
public interface ProjectServiceFegin {

    @GetMapping("/project/details/returns/{projectId}")
    public AppResponse<List<TReturn>> returnInfo(@PathVariable(name="projectId") Integer projectId);
}
