package com.stylefeng.guns.rest.modular.system.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.stylefeng.guns.core.enums.EnumOrderRebateStatus;
import com.stylefeng.guns.core.enums.EnumOrderStatus;
import com.stylefeng.guns.rest.modular.system.dao.OrderInfoMapper;
import com.stylefeng.guns.rest.modular.system.dao.OrderRebateMapper;
import com.stylefeng.guns.rest.modular.system.model.OrderInfo;
import com.stylefeng.guns.rest.modular.system.model.OrderRebate;
import com.stylefeng.guns.rest.modular.system.service.IOrderRebateService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * <p>
 * 收益回款订单 服务实现类
 * </p>
 *
 * @author szlc123
 * @since 2018-05-24
 */
@Service
@Transactional
public class OrderRebateServiceImpl extends ServiceImpl<OrderRebateMapper, OrderRebate> implements IOrderRebateService {

    @Resource
    OrderInfoMapper orderInfoMapper;

    @Resource
    OrderRebateMapper orderRebateMapper;

    @Override
    public boolean updateConfimRebate(OrderRebate orderRebate) {
        boolean flag = false;
        // 修改回款信息
        orderRebate.setLockSpId(0);
        orderRebateMapper.updateById(orderRebate);

        // 查询操作回款信息
        orderRebate = orderRebateMapper.selectById(orderRebate.getId());
        if (orderRebate != null && orderRebate.getOrderId() != null) {
            // 非确认的回款订单
            Integer[] status = new Integer[]{EnumOrderRebateStatus.waitRebate.getValue(), EnumOrderRebateStatus.inTheRebate.getValue(), EnumOrderRebateStatus.exceptionOrder.getValue()};
            // 查询非确认的回款订单
            Integer count = orderRebateMapper.selectCount(new EntityWrapper<OrderRebate>().eq("orderId", orderRebate.getOrderId()).in("status", status));
            if (count != null || count == 0) {
                // 如果订单的回款子订单全部已确认，则修改主订单状态
                OrderInfo order = new OrderInfo();
                order.setId(orderRebate.getOrderId());
                order.setStatus(EnumOrderStatus.rebateSuccessful.getValue());
                orderInfoMapper.updateById(order);

//                orderInfoMapper.selectOne(order);
//                orderInfoMapper.selectOne(new EntityWrapper<OrderInfo>().eq("userId", 1).and().eq("status", EnumOrderStatus.waitRebate.getValue()).orderBy("id"));
            }
            // 操作成功
            flag = true;
        }
        return flag;
    }
}
