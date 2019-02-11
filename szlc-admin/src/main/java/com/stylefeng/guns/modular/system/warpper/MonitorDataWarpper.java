package com.stylefeng.guns.modular.system.warpper;

import com.stylefeng.guns.core.base.warpper.BaseControllerWarpper;
import com.stylefeng.guns.core.enums.EnumMonitorTypeStatus;
import com.stylefeng.guns.core.enums.EnumOrderRebateStatus;

import java.util.List;
import java.util.Map;

/**
 * 监控数据包装类
 */
public class MonitorDataWarpper extends BaseControllerWarpper {

    public MonitorDataWarpper(List<Map<String, Object>> list) {
        super(list);
    }

    @Override
    public void warpTheMap(Map<String, Object> map) {
        map.put("type", EnumMonitorTypeStatus.getDesByCode((Integer) map.get("type")));
    }

}
