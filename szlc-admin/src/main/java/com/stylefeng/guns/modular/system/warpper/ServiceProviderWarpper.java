package com.stylefeng.guns.modular.system.warpper;

import com.stylefeng.guns.core.base.warpper.BaseControllerWarpper;
import com.stylefeng.guns.core.common.constant.factory.ConstantFactory;

import java.util.List;
import java.util.Map;

/**
 * 产品管理的包装类
 */
public class ServiceProviderWarpper extends BaseControllerWarpper {

    public ServiceProviderWarpper(List<Map<String, Object>> list) {
        super(list);
    }

    @Override
    public void warpTheMap(Map<String, Object> map) {
        map.put("requestApiNum", ConstantFactory.me().getServiceProviderInRequestApiNum((Integer) map.get("id")));
    }

}
