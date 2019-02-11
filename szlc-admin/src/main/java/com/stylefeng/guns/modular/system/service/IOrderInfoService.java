package com.stylefeng.guns.modular.system.service;

import com.stylefeng.guns.modular.system.model.OrderInfo;
import com.baomidou.mybatisplus.service.IService;
import com.stylefeng.guns.modular.system.model.vo.OrderInfoVo;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 订单 服务类
 * </p>
 *
 * @author stylefeng123
 * @since 2018-06-19
 */
public interface IOrderInfoService extends IService<OrderInfo> {

    /**
     * 查询订单信息
     */
    List<OrderInfoVo> getVoList(String orderNo, String productName, String productNo, String nickName, String phone, Integer status, String beginTime, String endTime);

    /**
     * 支付回调，生成回款信息
     */
    boolean updateOrderAddRebate(OrderInfo order);

    /**
     * 过去7天订单量
     */
    List<Map<String, Object>> past_7_day();

    /**
     * 本周订单量
     */
    Integer now_week();

    /**
     * 上周订单量
     */
    Integer past_1_week();

    /**
     * 本月/上月订单量
     */
    List<Map<String, Object>> past_2_month();

    /**
     * 今年/去年订单量
     */
    List<Map<String, Object>> past_2_year();
}
