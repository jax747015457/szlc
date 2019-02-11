package com.stylefeng.guns.modular.system.job;

import com.stylefeng.guns.core.enums.EnumOrderRebateStatus;
import com.stylefeng.guns.core.util.DateUtil;
import com.stylefeng.guns.modular.system.service.IOrderRebateService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * 回款订单超时状态操作
 */
@Component
@EnableScheduling
public class OutTimeOrderRebate {
    Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private IOrderRebateService orderRebateService;

    /**
     *  处理超时未确认的回款订单（30分钟执行一次）
     * 固定等待时间 @Scheduled(fixedDelay = 时间间隔(毫秒) )
     * @Scheduled(fixedRate = 1000 * 60 * 30)
     * Corn表达式 @Scheduled(cron = Corn表达式)
     * @Scheduled(cron = "0 0/30 * * * *")
     */
    @Scheduled(cron = "0 0/30 * * * *")
    public void updateStatusToOutTime() {
        try {
            Date outTime = DateUtil.getDateAddHour(new Date(), -48);
            // 处理超时未确认的回款订单（状态：回款中->超时未确认）
            orderRebateService.updateStatusToOutTime(EnumOrderRebateStatus.exceptionOrder.getValue(), outTime, EnumOrderRebateStatus.inTheRebate.getValue());
            log.error("每隔30分钟执行一次___"+ DateUtil.formatDate(new Date(), "HH:mm:ss"));
        } catch (Exception e) {
            e.printStackTrace();
            log.error("回款订单超时状态操作异常", e.getMessage());
        }
    }

}
