package com.stylefeng.guns.rest.modular.system.service;

import com.stylefeng.guns.rest.modular.system.model.OrderRebate;
import com.baomidou.mybatisplus.service.IService;

/**
 * <p>
 * 收益回款订单 服务类
 * </p>
 *
 * @author szlc123
 * @since 2018-05-24
 */
public interface IOrderRebateService extends IService<OrderRebate> {

    /** 回款确认操作 */
    boolean updateConfimRebate(OrderRebate orderRebate);

}
