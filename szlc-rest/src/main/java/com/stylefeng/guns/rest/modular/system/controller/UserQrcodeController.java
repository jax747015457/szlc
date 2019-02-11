package com.stylefeng.guns.rest.modular.system.controller;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.stylefeng.guns.rest.common.util.ApiJson;
import com.stylefeng.guns.rest.config.properties.AppProperties;
import com.stylefeng.guns.rest.modular.system.model.UserQrcode;
import com.stylefeng.guns.rest.modular.system.service.IUserQrcodeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/userQrcode")
public class UserQrcodeController {
    private final static Logger log = LoggerFactory.getLogger(UserQrcodeController.class);

    @Autowired
    private AppProperties appProperties;

    @Autowired
    private IUserQrcodeService userQrcodeService;

    /**
     * 获取我的二维码
     */
    @RequestMapping("/info")
    public Object info(Integer userId) {
         try {
             // 分页查询
             UserQrcode qrcode = userQrcodeService.selectOne(new EntityWrapper<UserQrcode>().eq("userId", userId));
             qrcode.setQrCodeAli(appProperties.getPictureServerAddress() + qrcode.getQrCodeAli());
             qrcode.setQrCodeWx(appProperties.getPictureServerAddress() + qrcode.getQrCodeWx());
             qrcode.setQrCodeWxMonitor(appProperties.getPictureServerAddress() + qrcode.getQrCodeWxMonitor());
             return ApiJson.returnOK(qrcode);
        } catch (Exception e) {
            e.printStackTrace();
             log.error(ApiJson.msgException, e);
        }
        return ApiJson.returnNG();
    }

    @RequestMapping("/update")
    public Object update(Integer userId, String qrCodeAli, String qrCodeWx, String qrCodeWxMonitor) {
        try {
            // 分页查询
            UserQrcode qrcode = userQrcodeService.selectOne(new EntityWrapper<UserQrcode>().eq("userId", userId));
            // 如果二维码信息为空，创建对象
            if (qrcode == null) {
                qrcode = new UserQrcode();
                qrcode.setUserId(userId);
            }

            // 修改支付宝二维码
            if (qrCodeAli != null) {
                qrcode.setQrCodeAli(qrCodeAli);
            }
            // 修改微信二维码
            if (qrCodeWx != null) {
                qrcode.setQrCodeWx(qrCodeWx);
            }
            // 修改微信监控到账二维码
            if (qrCodeWxMonitor != null) {
                qrcode.setQrCodeWxMonitor(qrCodeWxMonitor);
            }

            // 系统判定操作逻辑
            if (qrcode.getId() == null) {
                // 新增二维码信息
                userQrcodeService.insert(qrcode);
            } else {
                // 修改二维码信息
                userQrcodeService.update(qrcode, new EntityWrapper<UserQrcode>().eq("userId", userId));
            }
            return ApiJson.returnOK();
        } catch (Exception e) {
            e.printStackTrace();
            log.error(ApiJson.msgException, e);
        }
        return ApiJson.returnNG();
    }

}
