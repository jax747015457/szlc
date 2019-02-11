package com.stylefeng.guns.modular.system.job;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.stylefeng.guns.core.enums.EnumMonitorTypeStatus;
import com.stylefeng.guns.core.enums.EnumObjIsFlag;
import com.stylefeng.guns.core.enums.EnumOrderRebateStatus;
import com.stylefeng.guns.core.util.DateUtil;
import com.stylefeng.guns.modular.system.model.CWechatData;
import com.stylefeng.guns.modular.system.model.MonitorData;
import com.stylefeng.guns.modular.system.model.OrderRebate;
import com.stylefeng.guns.modular.system.model.UserInfo;
import com.stylefeng.guns.modular.system.service.ICWechatDataService;
import com.stylefeng.guns.modular.system.service.IMonitorDataService;
import com.stylefeng.guns.modular.system.service.IOrderRebateService;
import com.stylefeng.guns.modular.system.service.IUserInfoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

/**
 * 上传微信监控数据
 */
@Component
@EnableScheduling
public class UploadWecharData {
    Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private ICWechatDataService cWechatDataService;
    @Autowired
    private IMonitorDataService monitorDataService;
    @Autowired
    private IUserInfoService userInfoService;
    @Autowired
    private IOrderRebateService orderRebateService;

    /**
     * 根据上传微信监控数据，处理回款订单状态（30秒钟执行一次）
     * 固定等待时间 @Scheduled(fixedDelay = 时间间隔(毫秒) )
     * @Scheduled(fixedRate = 30 * 000)
     * Corn表达式 @Scheduled(cron = Corn表达式)
     * @Scheduled(cron = "0/30 * * * * ? ")
     */
    @Scheduled(fixedRate = 30 * 1000)
    public void handleOrderStatus() {
        try{
            // 查询未上传数据的微信监控数据
            List<CWechatData> list = cWechatDataService.selectList(new EntityWrapper<CWechatData>().eq("isUpload", EnumObjIsFlag.no.getValue()));
            for (CWechatData obj : list) {
                // 查询监控人的ID
                UserInfo user = userInfoService.selectOne(new EntityWrapper<UserInfo>().like("ID", obj.getReceiver()));

                // 监控数据写入数据库
                MonitorData data = new MonitorData();
                data.setUserId(user != null ? user.getId() : null);
                data.setMoney(new Double(obj.getMoney()));
                data.setType(EnumMonitorTypeStatus.wechatPay.getValue());
                data.setCreateTime(obj.getRtime());
                monitorDataService.insert(data);

                // 通过监控数据匹配回款数据
                Integer[] status = new Integer[]{EnumOrderRebateStatus.inTheRebate.getValue(), EnumOrderRebateStatus.exceptionOrder.getValue()};
                OrderRebate orderRebate = orderRebateService.selectOne(new EntityWrapper<OrderRebate>().eq("money", obj.getMoney()).and().in("status", status));
                if (orderRebate != null) {
                    orderRebate.setStatus(EnumOrderRebateStatus.monitorConfirm.getValue());
                    orderRebate.setMonitorId(data.getId());
                    orderRebate.setConfirmTime(new Date());
                    // 监控回款确认操作
                    orderRebateService.updateConfimRebate(orderRebate);
                }
                // 更改监控状态为已上传
                obj.setIsUpload(EnumObjIsFlag.yes.getValue());
                cWechatDataService.updateById(obj);

                //定时任务代码
                System.out.println("监控数据更新：" + obj.getReceiver() + "__" + obj.getMoney() + "__" + DateUtil.formatDate(new Date(), "HH:mm:ss"));
            }
        } catch (Exception e) {
            e.printStackTrace();
            log.error("上传微信监控数据异常", e.getMessage());
        }
    }

}
