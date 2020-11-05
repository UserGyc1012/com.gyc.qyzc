package com.offcn.order.controller;

import com.offcn.dycommon.response.AppResponse;
import com.offcn.order.po.TOrder;
import com.offcn.order.service.OrderService;
import com.offcn.order.vo.req.OrderInfoSubmitVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @ClassName OrderController
 * @Deacription TODO
 * @Author 葛言超
 * @Date 2020/11/4 17:11
 * version:1.0 * 注意：本内容仅限于公司内部传阅，禁止外泄以及用于其他的商业目
 */
@Api(tags = "保存订单")
@RestController
@RequestMapping("/order")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @PostMapping("/createOrder")
    @ApiOperation("保存订单")
    public AppResponse<TOrder> createOrder(@RequestBody OrderInfoSubmitVo vo){
        try {
            TOrder order = orderService.saveOrder(vo);
            AppResponse<TOrder> orderAppResponse = AppResponse.OK(order);
            return  orderAppResponse;
        } catch (Exception e) {
            e.printStackTrace();
            return AppResponse.FAIL(null);
        }
    }
}
