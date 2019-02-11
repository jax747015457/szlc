package com.stylefeng.guns.rest.modular.system.controller;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.stylefeng.guns.core.enums.EnumUrlType;
import com.stylefeng.guns.rest.common.util.ApiJson;
import com.stylefeng.guns.rest.config.properties.AppProperties;
import com.stylefeng.guns.rest.modular.system.model.BannerInfo;
import com.stylefeng.guns.rest.modular.system.model.vo.BannerVo;
import com.stylefeng.guns.rest.modular.system.service.IBannerInfoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/banner")
public class BannerInfoController {
    private final static Logger log = LoggerFactory.getLogger(BannerInfoController.class);

    @Autowired
    private AppProperties appProperties;

    @Autowired
    private IBannerInfoService bannerInfoService;

    /**
     * 获取Banner
     */
    @RequestMapping("/list")
    public Object list(Integer current, Integer size) {
         try {
             // 分页查询
             List<BannerInfo> tempList = bannerInfoService.selectPage(new Page(current == null ? 1 : current, size == null ? 10 : size), new EntityWrapper<BannerInfo>().eq("isSue", 1).eq("isDelete", 0).orderBy("orderBy", false)).getRecords();

             // 封装返回数据
             List<BannerVo> list = new ArrayList<>();
             for (BannerInfo obj : tempList) {
                 String urlHtml = obj.getUrlType() == EnumUrlType.web.getValue() ? obj.getUrlHtml() : appProperties.getPictureServerAddress() + obj.getContent();
                 list.add(new BannerVo(appProperties.getPictureServerAddress() + obj.getImgUrl(), obj.getUrlType(), urlHtml));
             }

             return ApiJson.returnOK(list);
        } catch (Exception e) {
            e.printStackTrace();
             log.error(ApiJson.msgException, e);
        }
        return ApiJson.returnNG();
    }

}
