package com.stylefeng.guns.rest.modular.system.controller;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.stylefeng.guns.rest.common.util.ApiJson;
import com.stylefeng.guns.rest.modular.system.model.MessageInfo;
import com.stylefeng.guns.rest.modular.system.service.IMessageInfoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/message")
public class MessageController {
    private final static Logger log = LoggerFactory.getLogger(MessageController.class);

    @Autowired
    private IMessageInfoService messageInfoService;

    /**
     * 获取系统消息
     */
    @RequestMapping("/list")
    public Object list(Integer userId, Integer current, Integer size) {
         try {
             // 分页查询
             List<MessageInfo> list = messageInfoService.selectPage(new Page(current == null ? 1 : current, size == null ? 10 : size), new EntityWrapper<MessageInfo>().eq("userId", userId).orderBy("id", false)).getRecords();

             return ApiJson.returnOK(list);
        } catch (Exception e) {
            e.printStackTrace();
             log.error(ApiJson.msgException, e);
        }
        return ApiJson.returnNG();
    }

}
