package com.offcn.order.service.impl;

import com.offcn.dycommon.enums.OrderStatusEnumes;
import com.offcn.dycommon.response.AppResponse;
import com.offcn.order.mapper.TOrderMapper;
import com.offcn.order.po.TOrder;
import com.offcn.order.po.TReturn;
import com.offcn.order.service.OrderService;
import com.offcn.order.service.ProjectServiceFegin;
import com.offcn.order.vo.req.OrderInfoSubmitVo;
import com.offcn.util.AppDateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

/**
 * @ClassName OrderServiceImpl
 * @Deacription TODO
 * @Author 葛言超
 * @Date 2020/11/4 16:41
 * version:1.0 * 注意：本内容仅限于公司内部传阅，禁止外泄以及用于其他的商业目
 */
@Service
public class OrderServiceImpl implements OrderService {
    @Autowired
    private StringRedisTemplate redisTemplate;

    @Autowired
    private TOrderMapper orderMapper;

    @Autowired
    private ProjectServiceFegin projectServiceFegin;

    @Override
    public TOrder saveOrder(OrderInfoSubmitVo vo) {
        TOrder order = new TOrder();
        //获得令牌
        String accessToken = vo.getAccessToken();
        //从缓存中得到会员id
        String memberId = redisTemplate.opsForValue().get(accessToken);
        order.setMemberid(Integer.parseInt(memberId));
        //项目ID
        order.setProjectid(vo.getProjectid());
        //回报项目ID 可以有多个，默认的那个
        order.setReturnid(vo.getReturnid());
        //生成订单编号
        String orderNum = UUID.randomUUID().toString().replace("-", "");
        order.setOrdernum(orderNum);
        //订单创建时间
        order.setCreatedate(AppDateUtils.getFormatTime());
        //从项目模块中得到回报列表
        AppResponse<List<TReturn>> returnAppResponse = projectServiceFegin.returnInfo(vo.getProjectid());
        List<TReturn> tReturnList = returnAppResponse.getData();
        TReturn tReturn = tReturnList.get(0);
        //计算回报金额，支持数量*支持金额+运费
        Integer totalMoney = vo.getRtncount() * tReturn.getSupportmoney() + tReturn.getFreight();

        order.setMoney(totalMoney);
        //回报数量
        order.setRtncount(vo.getRtncount());
        //支付状态  未支付
        order.setStatus(OrderStatusEnumes.UNPAY.getCode()+"");
        //收货地址
        order.setAddress(vo.getAddress());
        //是否开发票
        order.setInvoice(vo.getInvoice().toString());
        //发票名头
        order.setInvoictitle(vo.getInvoictitle());
        //备注
        order.setRemark(vo.getRemark());
        orderMapper.insertSelective(order);
        return order;
    }
}
