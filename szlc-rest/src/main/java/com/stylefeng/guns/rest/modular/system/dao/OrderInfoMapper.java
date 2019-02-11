package com.stylefeng.guns.rest.modular.system.dao;

import com.stylefeng.guns.rest.modular.system.model.OrderInfo;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.stylefeng.guns.rest.modular.system.model.vo.OrderVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 订单 Mapper 接口
 * </p>
 *
 * @author szlc123
 * @since 2018-06-28
 */
public interface OrderInfoMapper extends BaseMapper<OrderInfo> {

    /** 查询用户的订单 */
    List<OrderVo> selectOpListByUserAndStatus(@Param("userId") Integer userId, @Param("productId") Integer productId, @Param("status") Integer[] status, @Param("current") Integer current, @Param("size") Integer size);

    /** 查询用户的订单(含用户信息) */
    List<OrderVo> selectOpuListByUserAndStatus(@Param("userId") Integer userId, @Param("productId") Integer productId, @Param("status") Integer[] status, @Param("current") Integer current, @Param("size") Integer size);
}
