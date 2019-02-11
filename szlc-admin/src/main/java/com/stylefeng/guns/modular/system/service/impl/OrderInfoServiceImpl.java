package com.stylefeng.guns.modular.system.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.stylefeng.guns.core.enums.EnumOrderRebateStatus;
import com.stylefeng.guns.core.util.DateUtil;
import com.stylefeng.guns.modular.system.dao.MessageInfoMapper;
import com.stylefeng.guns.modular.system.dao.OrderInfoMapper;
import com.stylefeng.guns.modular.system.dao.OrderRebateMapper;
import com.stylefeng.guns.modular.system.model.MessageInfo;
import com.stylefeng.guns.modular.system.model.OrderInfo;
import com.stylefeng.guns.modular.system.model.OrderRebate;
import com.stylefeng.guns.modular.system.model.vo.OrderInfoVo;
import com.stylefeng.guns.modular.system.service.IOrderInfoService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 订单 服务实现类
 * </p>
 */
@Service
@Transactional
public class OrderInfoServiceImpl extends ServiceImpl<OrderInfoMapper, OrderInfo> implements IOrderInfoService {

    @Resource
    private OrderInfoMapper orderInfoMapper;

    @Resource
    private OrderRebateMapper orderRebateMapper;

    @Resource
    private MessageInfoMapper messageInfoMapper;

    @Override
    public List<OrderInfoVo> getVoList(String orderNo, String productName, String productNo, String nickName, String phone, Integer status, String beginTime, String endTime) {
        return orderInfoMapper.getVoList(orderNo, productName, productNo, nickName, phone, status, beginTime, endTime);
    }

    @Override
    public boolean updateOrderAddRebate(OrderInfo order) {
        boolean flag = true;
        // 设置理财订单回款信息
        OrderRebate orderRebate = new OrderRebate();
        orderRebate.setRebateNo(1);
        orderRebate.setOrderId(order.getId());
        // 回款金额为订单金额 + 收益金额
        orderRebate.setMoney(order.getMoney().add(order.getIncomeAmount()));
        orderRebate.setCreateTime(new Date());

        // 查询理财订单中回款数据（未确认状态）：待回款、回款中
        Integer[] status = new Integer[]{EnumOrderRebateStatus.waitRebate.getValue(), EnumOrderRebateStatus.inTheRebate.getValue()};
        List<OrderRebate> rebateList = orderRebateMapper.selectList(new EntityWrapper<OrderRebate>().eq(false,"id", order.getId()).in("status", status).groupBy("id"));
        if (rebateList != null && rebateList.size() >= 1) {
            // 设置为待回款（如果存在待回款的订单）
            orderRebate.setStatus(EnumOrderRebateStatus.waitRebate.getValue());
            // 获取理财订单回款最后的时间，追加24小时
            orderRebate.setRebateTime(DateUtil.getDateAddHour(rebateList.get(0).getRebateTime(), 24));
        } else {
            // 设置为待回款（未请求扫码的都为待回款）
            orderRebate.setStatus(EnumOrderRebateStatus.waitRebate.getValue());
            orderRebate.setRebateTime(new Date());
        }

        // 修改订单状态，已支付
        order.setIncomeTime(orderRebate.getRebateTime());
        orderInfoMapper.updateById(order);

        // 添加理财订单回款信息
        orderRebateMapper.insert(orderRebate);

        try {
            // 系统消息：订单支付成功消息通知
            MessageInfo message = new MessageInfo();
            message.setUserId(order.getUserId());
            message.setCreateTime(new Date());
            message.setTitle("产品购买成功");
            message.setContent("您的订单" + order.getOrderNo() + "，支付" + order.getMoney() + "元已购买成功，订单正在等待收益中~");
            messageInfoMapper.insert(message);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return flag;
    }

    @Override
    public List<Map<String, Object>> past_7_day() {
        return orderInfoMapper.past_7_day();
    }

    @Override
    public Integer now_week() {
        return orderInfoMapper.now_week();
    }

    @Override
    public Integer past_1_week() {
        return orderInfoMapper.past_1_week();
    }

    @Override
    public List<Map<String, Object>> past_2_month() {
        return orderInfoMapper.past_2_month();
    }

    @Override
    public List<Map<String, Object>> past_2_year() {
        return orderInfoMapper.past_2_year();
    }
}
