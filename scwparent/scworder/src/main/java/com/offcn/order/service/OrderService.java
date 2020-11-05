package com.offcn.order.service;

import com.offcn.order.po.TOrder;
import com.offcn.order.vo.req.OrderInfoSubmitVo;

/**
 * @ClassName OrderService
 * @Deacription TODO
 * @Author 葛言超
 * @Date 2020/11/4 16:39
 * version:1.0 * 注意：本内容仅限于公司内部传阅，禁止外泄以及用于其他的商业目
 */
public interface OrderService {

    TOrder saveOrder(OrderInfoSubmitVo vo);
}
