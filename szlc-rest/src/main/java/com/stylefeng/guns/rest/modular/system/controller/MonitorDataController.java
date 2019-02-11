package com.stylefeng.guns.rest.modular.system.controller;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.stylefeng.guns.core.enums.EnumOrderRebateStatus;
import com.stylefeng.guns.rest.common.util.ApiJson;
import com.stylefeng.guns.rest.modular.system.model.MonitorData;
import com.stylefeng.guns.rest.modular.system.model.OrderRebate;
import com.stylefeng.guns.rest.modular.system.service.IMonitorDataService;
import com.stylefeng.guns.rest.modular.system.service.IOrderRebateService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api/monitorData")
public class MonitorDataController {
    private final static Logger log = LoggerFactory.getLogger(MonitorDataController.class);

    @Autowired
    private IMonitorDataService monitorDataService;
    @Autowired
    private IOrderRebateService orderRebateService;

    /**
     * 上传监控数据
     */
    @RequestMapping("/upload")
    public Object upload(Integer userId, Integer type, Double money) {
        try {
            // 保存监控数据
            MonitorData data = new MonitorData(userId, type, money, new Date());
            monitorDataService.insert(data);

            // 通过监控数据匹配回款数据
            Integer[] status = new Integer[]{EnumOrderRebateStatus.inTheRebate.getValue(), EnumOrderRebateStatus.exceptionOrder.getValue()};
            OrderRebate orderRebate = orderRebateService.selectOne(new EntityWrapper<OrderRebate>().eq("money", money).and().in("status", status));
            if (orderRebate != null) {
                orderRebate.setStatus(EnumOrderRebateStatus.monitorConfirm.getValue());
                orderRebate.setConfirmTime(new Date());
                orderRebate.setMonitorId(data.getId());
                // 监控回款确认操作
                orderRebateService.updateConfimRebate(orderRebate);
            }

            return ApiJson.returnOK();
        } catch (Exception e) {
            e.printStackTrace();
            log.error(ApiJson.msgException, e);
        }
        return ApiJson.returnNG();
    }

}
