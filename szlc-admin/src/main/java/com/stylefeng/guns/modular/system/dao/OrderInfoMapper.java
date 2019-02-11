package com.stylefeng.guns.modular.system.dao;

import com.stylefeng.guns.modular.system.model.OrderInfo;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.stylefeng.guns.modular.system.model.vo.OrderInfoVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 订单 Mapper 接口
 * </p>
 */
public interface OrderInfoMapper extends BaseMapper<OrderInfo> {

    /**
     * 查询产品的累计金额/用户数量
     */
    Map<String, Object> getProductInfoInAddsMoneyAndUserNum(@Param("productId") Integer productId, @Param("status") Integer[] status);

    /**
     * 查询订单信息
     */
    List<OrderInfoVo> getVoList(@Param("orderNo") String orderNo, @Param("productName") String productName, @Param("productNo") String productNo, @Param("nickName") String nickName, @Param("phone") String phone, @Param("status") Integer status, @Param("beginTime") String beginTime, @Param("endTime") String endTime);

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
