package com.stylefeng.guns.modular.system.controller;

import com.stylefeng.guns.core.base.controller.BaseController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.beans.factory.annotation.Autowired;
import com.stylefeng.guns.core.log.LogObjectHolder;
import org.springframework.web.bind.annotation.RequestParam;
import com.stylefeng.guns.modular.system.model.MessageInfo;
import com.stylefeng.guns.modular.system.service.IMessageInfoService;

/**
 * 系统消息控制器
 * @Date 2018-07-23 16:06:50
 */
@Controller
@RequestMapping("/messageInfo")
public class MessageInfoController extends BaseController {

    private String PREFIX = "/system/messageInfo/";

    @Autowired
    private IMessageInfoService messageInfoService;

    /**
     * 跳转到系统消息首页
     */
    @RequestMapping("")
    public String index() {
        return PREFIX + "messageInfo.html";
    }

    /**
     * 跳转到添加系统消息
     */
    @RequestMapping("/messageInfo_add")
    public String messageInfoAdd() {
        return PREFIX + "messageInfo_add.html";
    }

    /**
     * 跳转到修改系统消息
     */
    @RequestMapping("/messageInfo_update/{messageInfoId}")
    public String messageInfoUpdate(@PathVariable Integer messageInfoId, Model model) {
        MessageInfo messageInfo = messageInfoService.selectById(messageInfoId);
        model.addAttribute("item",messageInfo);
        LogObjectHolder.me().set(messageInfo);
        return PREFIX + "messageInfo_edit.html";
    }

    /**
     * 获取系统消息列表
     */
    @RequestMapping(value = "/list")
    @ResponseBody
    public Object list(String condition) {
        return messageInfoService.selectList(null);
    }

    /**
     * 新增系统消息
     */
    @RequestMapping(value = "/add")
    @ResponseBody
    public Object add(MessageInfo messageInfo) {
        messageInfoService.insert(messageInfo);
        return SUCCESS_TIP;
    }

    /**
     * 删除系统消息
     */
    @RequestMapping(value = "/delete")
    @ResponseBody
    public Object delete(@RequestParam Integer messageInfoId) {
        messageInfoService.deleteById(messageInfoId);
        return SUCCESS_TIP;
    }

    /**
     * 修改系统消息
     */
    @RequestMapping(value = "/update")
    @ResponseBody
    public Object update(MessageInfo messageInfo) {
        messageInfoService.updateById(messageInfo);
        return SUCCESS_TIP;
    }

    /**
     * 系统消息详情
     */
    @RequestMapping(value = "/detail/{messageInfoId}")
    @ResponseBody
    public Object detail(@PathVariable("messageInfoId") Integer messageInfoId) {
        return messageInfoService.selectById(messageInfoId);
    }
}
