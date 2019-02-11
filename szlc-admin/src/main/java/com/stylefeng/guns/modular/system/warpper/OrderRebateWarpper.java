package com.stylefeng.guns.modular.system.warpper;

import com.stylefeng.guns.core.base.warpper.BaseControllerWarpper;
import com.stylefeng.guns.core.common.constant.factory.ConstantFactory;
import com.stylefeng.guns.core.enums.EnumObjStatus;
import com.stylefeng.guns.core.enums.EnumOrderRebateStatus;

import java.util.List;
import java.util.Map;

/**
 * 订单回款的包装类
 */
public class OrderRebateWarpper extends BaseControllerWarpper {

    public OrderRebateWarpper(List<Map<String, Object>> list) {
        super(list);
    }

    @Override
    public void warpTheMap(Map<String, Object> map) {
        map.put("status", EnumOrderRebateStatus.getDesByCode((Integer) map.get("status")));
    }

}
