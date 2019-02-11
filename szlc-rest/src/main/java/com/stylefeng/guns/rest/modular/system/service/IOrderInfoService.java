package com.stylefeng.guns.rest.modular.system.service;

import com.baomidou.mybatisplus.service.IService;
import com.stylefeng.guns.rest.modular.system.model.OrderInfo;
import com.stylefeng.guns.rest.modular.system.model.vo.OrderVo;

import java.util.List;

/**
 * <p>
 * 订单 服务类
 * </p>
 *
 * @author szlc123
 * @since 2018-05-24
 */
public interface IOrderInfoService extends IService<OrderInfo> {

    /** 查询用户的订单 */
    List<OrderVo> selectOpListByUserAndStatus(Integer userId, Integer productId, Integer[] status, Integer current, Integer size);

    /** 查询用户的订单(含用户信息) */
    List<OrderVo> selectOpuListByUserAndStatus(Integer userId, Integer productId, Integer[] status, Integer current, Integer size);

}
