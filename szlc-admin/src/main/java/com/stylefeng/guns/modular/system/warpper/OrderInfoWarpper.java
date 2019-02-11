package com.stylefeng.guns.modular.system.warpper;

import com.stylefeng.guns.core.enums.EnumOrderStatus;
import com.stylefeng.guns.modular.system.model.vo.OrderInfoVo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 产品管理的包装类
 */
public class OrderInfoWarpper {

    public Object warp(List<OrderInfoVo> voList) {
        List<Map<String, Object>> list = new ArrayList<>();
        for (OrderInfoVo vo : voList) {
            Map<String, Object> map = new HashMap<>();
            map.put("id", vo.getId());
            map.put("orderNo", vo.getOrderNo());
            map.put("productId", vo.getProductId());
            map.put("productName", vo.getProductName());
            map.put("productNo", vo.getProductNo());
            map.put("userId", vo.getUserId());
            map.put("nickName", vo.getNickName());
            map.put("realName", vo.getRealName());
            map.put("phone", vo.getPhone());
            map.put("money", vo.getMoney());
            map.put("annualRate", vo.getAnnualRate()+"%");
            map.put("incomeAmount", vo.getIncomeAmount());
            map.put("createTime", vo.getCreateTime());
            map.put("incomeTime", vo.getIncomeTime());
            map.put("status", EnumOrderStatus.getDesByCode(vo.getStatus()));

            list.add(map);
        }
        return list;
    }

}
