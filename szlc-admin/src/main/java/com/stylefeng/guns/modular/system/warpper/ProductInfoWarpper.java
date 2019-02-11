package com.stylefeng.guns.modular.system.warpper;

import com.stylefeng.guns.core.base.warpper.BaseControllerWarpper;
import com.stylefeng.guns.core.common.constant.factory.ConstantFactory;
import com.stylefeng.guns.core.enums.EnumObjStatus;

import java.util.List;
import java.util.Map;

/**
 * 产品管理的包装类
 */
public class ProductInfoWarpper extends BaseControllerWarpper {

    public ProductInfoWarpper(List<Map<String, Object>> list) {
        super(list);
    }

    @Override
    public void warpTheMap(Map<String, Object> map) {
        // 累计资金/用户数量
        Map<String, Object> obj = ConstantFactory.me().getProductInfoInAddsMoneyAndUserNum((Integer) map.get("id"));
        Object addsMoney = obj == null ? 0 : obj.get("addsMoney");
        Object userNum = obj == null ? 0 : obj.get("userNum");
        map.put("addsMoney", addsMoney == null ? 0 : addsMoney);
        map.put("userNum", userNum == null ? 0 : userNum);
        map.put("status", (Integer) map.get("status") == EnumObjStatus.freeze.getValue() ? EnumObjStatus.freeze.getDes() : EnumObjStatus.normal.getDes());
    }

}
