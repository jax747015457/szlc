package com.stylefeng.guns.modular.system.dao;

import com.stylefeng.guns.modular.system.model.OrderRebate;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 收益回款订单 Mapper 接口
 * </p>
 *
 * @author stylefeng123
 * @since 2018-06-28
 */
public interface OrderRebateMapper extends BaseMapper<OrderRebate> {

    List<Map<String,Object>> getMapList(@Param("orderId") Integer orderId, @Param("orderNo") String orderNo, @Param("status") Integer status, @Param("beginTime") String beginTime, @Param("endTime") String endTime);

    Integer updateStatusToOutTime(@Param("exceptionOrder")Integer exceptionOrder, @Param("outTime")Date outTime, @Param("inTheRebate")Integer inTheRebate);

    List<Map<String,Object>> getOrderOutTime(@Param("outTime")Date outTime, @Param("inTheRebate")Integer inTheRebate);
}
