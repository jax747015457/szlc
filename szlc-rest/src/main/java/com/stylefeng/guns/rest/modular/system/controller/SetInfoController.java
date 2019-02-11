package com.stylefeng.guns.rest.modular.system.controller;

import com.stylefeng.guns.core.enums.EnumUrlType;
import com.stylefeng.guns.rest.common.util.ApiJson;
import com.stylefeng.guns.rest.config.properties.AppProperties;
import com.stylefeng.guns.rest.modular.system.model.Advertising;
import com.stylefeng.guns.rest.modular.system.service.IAdvertisingService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

import static com.stylefeng.guns.rest.common.util.ApiJson.msgException;

@RestController
@RequestMapping("/api/set")
public class SetInfoController {
    private final static Logger log = LoggerFactory.getLogger(SetInfoController.class);

    @Autowired
    private AppProperties appProperties;

    @Autowired
    private IAdvertisingService advertisingService;

    /**
     * 获取广告图片
     */
    @RequestMapping(value = "adImg", produces = "application/json;charset=UTF-8")
    public Object adImg() {
        try {
            // 查询广告设置
            Advertising ad = advertisingService.selectById(1);

            // 封装数据
            Map<String, Object> map = new HashMap<>();
            if (ad != null) {
                map.put("urlType", ad.getUrlType());
                map.put("urlHtml", ad.getUrlType() == EnumUrlType.web.getValue() ? ad.getUrlHtml() : appProperties.getPictureServerAddress() + ad.getContent());
                map.put("advertisingImg", appProperties.getPictureServerAddress() + ad.getImgUrl());
            }
            return ApiJson.returnOK(map);
        } catch (Exception e) {
            e.printStackTrace();
            log.error(msgException, e);
        }
        return ApiJson.returnNG();
    }

}
