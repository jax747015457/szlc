package com.stylefeng.guns.modular.system.service;

import com.baomidou.mybatisplus.service.IService;
import com.stylefeng.guns.modular.system.model.OrderRebate;
import com.stylefeng.guns.modular.system.model.vo.OrderRebateScanVo;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 收益回款订单 服务类
 * </p>
 */
public interface IOrderRebateService extends IService<OrderRebate> {

    /**
     * 查询回款信息
     */
    List<Map<String, Object>> getMapList(Integer orderId, String orderNo, Integer status, String beginTime, String endTime);

    /**
     * 确认回款操作
     */
    boolean updateConfimRebate(OrderRebate orderRebate);

    /**
     * 获取回款订单扫码信息
     */
    OrderRebateScanVo getOrderRebateScan(String account, String key, Double money, Integer type);

    /**
     * 获取超时未确认的订单
     */
    List<Map<String, Object>> getOrderOutTime(Date outTime, Integer inTheRebate);
    /**
     * 处理超时未确认的回款订单（状态：回款中->超时未确认）
     */
    Integer updateStatusToOutTime(Integer exceptionOrder, Date outTime, Integer inTheRebate);

    /**
     * 服务商回款结算
     * @param ids
     */
    void settlement(String ids);
}
