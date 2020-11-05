package com.offcn.order.service.impl;

import com.offcn.dycommon.response.AppResponse;
import com.offcn.order.po.TReturn;
import com.offcn.order.service.ProjectServiceFegin;

import java.util.List;

/**
 * @ClassName ProjectServiceFeignException
 * @Deacription TODO
 * @Author 葛言超
 * @Date 2020/11/4 16:32
 * version:1.0 * 注意：本内容仅限于公司内部传阅，禁止外泄以及用于其他的商业目
 */
public class ProjectServiceFeignException implements ProjectServiceFegin {
    @Override
    public AppResponse<List<TReturn>> returnInfo(Integer projectId) {
        AppResponse<List<TReturn>> fail = AppResponse.FAIL(null);
        fail.setMessage("调用远程服务器失败【订单】");
        return fail;
    }
}
