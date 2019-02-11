package com.stylefeng.guns.rest.modular.system.controller;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.stylefeng.guns.rest.common.util.ApiJson;
import com.stylefeng.guns.rest.config.properties.AppProperties;
import com.stylefeng.guns.rest.modular.system.model.NoticeInfo;
import com.stylefeng.guns.rest.modular.system.service.INoticeInfoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/notice")
public class NoticeController {
    private final static Logger log = LoggerFactory.getLogger(NoticeController.class);

    @Autowired
    private AppProperties appProperties;

    @Autowired
    private INoticeInfoService noticeInfoService;

    /**
     * 获取公告
     */
    @RequestMapping("/list")
    public Object list(Integer current, Integer size) {
         try {
             // 分页查询
             List<NoticeInfo> tempList = noticeInfoService.selectPage(new Page(current == null ? 1 : current, size == null ? 10 : size), new EntityWrapper<NoticeInfo>().orderBy("id", false)).getRecords();

             // 封装数据
             List<NoticeInfo> list = new ArrayList<>();
             for (NoticeInfo obj : tempList) {
                 obj.setCoverPlan(appProperties.getPictureServerAddress() + obj.getCoverPlan());
                 obj.setContent(appProperties.getPictureServerAddress() + obj.getContent());
                 list.add(obj);
             }

             return ApiJson.returnOK(list);
        } catch (Exception e) {
            e.printStackTrace();
             log.error(ApiJson.msgException, e);
        }
        return ApiJson.returnNG();
    }

}
