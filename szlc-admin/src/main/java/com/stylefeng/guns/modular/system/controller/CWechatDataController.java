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
import com.stylefeng.guns.modular.system.model.CWechatData;
import com.stylefeng.guns.modular.system.service.ICWechatDataService;

/**
 * 微信收款监控控制器
 * @Date 2018-07-10 11:44:48
 */
@Controller
@RequestMapping("/cWechatData")
public class CWechatDataController extends BaseController {

    private String PREFIX = "/system/cWechatData/";

    @Autowired
    private ICWechatDataService cWechatDataService;

    /**
     * 跳转到微信收款监控首页
     */
    @RequestMapping("")
    public String index() {
        return PREFIX + "cWechatData.html";
    }

    /**
     * 跳转到添加微信收款监控
     */
    @RequestMapping("/cWechatData_add")
    public String cWechatDataAdd() {
        return PREFIX + "cWechatData_add.html";
    }

    /**
     * 跳转到修改微信收款监控
     */
    @RequestMapping("/cWechatData_update/{cWechatDataId}")
    public String cWechatDataUpdate(@PathVariable Integer cWechatDataId, Model model) {
        CWechatData cWechatData = cWechatDataService.selectById(cWechatDataId);
        model.addAttribute("item",cWechatData);
        LogObjectHolder.me().set(cWechatData);
        return PREFIX + "cWechatData_edit.html";
    }

    /**
     * 获取微信收款监控列表
     */
    @RequestMapping(value = "/list")
    @ResponseBody
    public Object list(String condition) {
        return cWechatDataService.selectList(null);
    }

    /**
     * 新增微信收款监控
     */
    @RequestMapping(value = "/add")
    @ResponseBody
    public Object add(CWechatData cWechatData) {
        cWechatDataService.insert(cWechatData);
        return SUCCESS_TIP;
    }

    /**
     * 删除微信收款监控
     */
    @RequestMapping(value = "/delete")
    @ResponseBody
    public Object delete(@RequestParam Integer cWechatDataId) {
        cWechatDataService.deleteById(cWechatDataId);
        return SUCCESS_TIP;
    }

    /**
     * 修改微信收款监控
     */
    @RequestMapping(value = "/update")
    @ResponseBody
    public Object update(CWechatData cWechatData) {
        cWechatDataService.updateById(cWechatData);
        return SUCCESS_TIP;
    }

    /**
     * 微信收款监控详情
     */
    @RequestMapping(value = "/detail/{cWechatDataId}")
    @ResponseBody
    public Object detail(@PathVariable("cWechatDataId") Integer cWechatDataId) {
        return cWechatDataService.selectById(cWechatDataId);
    }
}
