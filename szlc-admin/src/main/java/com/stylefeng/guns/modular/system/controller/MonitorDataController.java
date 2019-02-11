package com.stylefeng.guns.modular.system.controller;

import com.stylefeng.guns.core.base.controller.BaseController;
import com.stylefeng.guns.modular.system.warpper.MonitorDataWarpper;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.beans.factory.annotation.Autowired;
import com.stylefeng.guns.core.log.LogObjectHolder;
import org.springframework.web.bind.annotation.RequestParam;
import com.stylefeng.guns.modular.system.model.MonitorData;
import com.stylefeng.guns.modular.system.service.IMonitorDataService;

/**
 * 回款监控数据控制器
 * @Date 2018-06-27 13:20:45
 */
@Controller
@RequestMapping("/monitorData")
public class MonitorDataController extends BaseController {

    private String PREFIX = "/system/monitorData/";

    @Autowired
    private IMonitorDataService monitorDataService;

    /**
     * 跳转到回款监控数据首页
     */
    @RequestMapping("")
    public String index() {
        return PREFIX + "monitorData.html";
    }

    /**
     * 跳转到添加回款监控数据
     */
    @RequestMapping("/monitorData_add")
    public String monitorDataAdd() {
        return PREFIX + "monitorData_add.html";
    }

    /**
     * 跳转到修改回款监控数据
     */
    @RequestMapping("/monitorData_update/{monitorDataId}")
    public String monitorDataUpdate(@PathVariable Integer monitorDataId, Model model) {
        MonitorData monitorData = monitorDataService.selectById(monitorDataId);
        model.addAttribute("item",monitorData);
        LogObjectHolder.me().set(monitorData);
        return PREFIX + "monitorData_edit.html";
    }

    /**
     * 获取回款监控数据列表
     */
    @RequestMapping(value = "/list")
    @ResponseBody
    public Object list(String nickName, String phone, Integer type, String beginTime, String endTime) {
        if (type != null && type == -1)
            type = null;
        return new MonitorDataWarpper(monitorDataService.getMapList(nickName, phone, type, beginTime, endTime)).warp();
    }

    /**
     * 新增回款监控数据
     */
    @RequestMapping(value = "/add")
    @ResponseBody
    public Object add(MonitorData monitorData) {
        monitorDataService.insert(monitorData);
        return SUCCESS_TIP;
    }

    /**
     * 删除回款监控数据
     */
    @RequestMapping(value = "/delete")
    @ResponseBody
    public Object delete(@RequestParam Integer monitorDataId) {
        monitorDataService.deleteById(monitorDataId);
        return SUCCESS_TIP;
    }

    /**
     * 修改回款监控数据
     */
    @RequestMapping(value = "/update")
    @ResponseBody
    public Object update(MonitorData monitorData) {
        monitorDataService.updateById(monitorData);
        return SUCCESS_TIP;
    }

    /**
     * 回款监控数据详情
     */
    @RequestMapping(value = "/detail/{monitorDataId}")
    @ResponseBody
    public Object detail(@PathVariable("monitorDataId") Integer monitorDataId) {
        return monitorDataService.selectById(monitorDataId);
    }
}
