package com.stylefeng.guns.modular.system.warpper;

import com.stylefeng.guns.core.base.warpper.BaseControllerWarpper;
import com.stylefeng.guns.core.enums.EnumObjIsFlag;
import com.stylefeng.guns.core.enums.EnumUrlType;

import java.util.Map;

/**
 * Banner包装类
 */
public class BannerWarpper extends BaseControllerWarpper {

    public BannerWarpper(Object list) {
        super(list);
    }

    @Override
    public void warpTheMap(Map<String, Object> map) {
        map.put("isSue", (Integer) map.get("isSue") == EnumObjIsFlag.yes.getValue() ? "已发布" : "未发布");
        map.put("urlType", EnumUrlType.getDesByCode((Integer) map.get("urlType")));
    }

}
