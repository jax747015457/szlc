package com.stylefeng.guns.modular.system.controller;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.stylefeng.guns.core.base.controller.BaseController;
import com.stylefeng.guns.core.log.LogObjectHolder;
import com.stylefeng.guns.core.util.DateUtil;
import com.stylefeng.guns.core.util.ExcelExportUtil;
import com.stylefeng.guns.modular.system.model.OrderRebate;
import com.stylefeng.guns.modular.system.model.SettlementLog;
import com.stylefeng.guns.modular.system.service.IOrderRebateService;
import com.stylefeng.guns.modular.system.service.ISettlementLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 回款结算记录控制器
 * @Date 2018-07-27 11:44:30
 */
@Controller
@RequestMapping("/settlementLog")
public class SettlementLogController extends BaseController {

    private String PREFIX = "/system/settlementLog/";

    @Autowired
    private ISettlementLogService settlementLogService;

    @Autowired
    private IOrderRebateService orderRebateService;

    /**
     * 跳转到回款结算记录首页
     */
    @RequestMapping("")
    public String index() {
        return PREFIX + "settlementLog.html";
    }

    /**
     * 跳转到添加回款结算记录
     */
    @RequestMapping("/settlementLog_add")
    public String settlementLogAdd() {
        return PREFIX + "settlementLog_add.html";
    }

    /**
     * 跳转到修改回款结算记录
     */
    @RequestMapping("/settlementLog_update/{settlementLogId}")
    public String settlementLogUpdate(@PathVariable Integer settlementLogId, Model model) {
        SettlementLog settlementLog = settlementLogService.selectById(settlementLogId);
        model.addAttribute("item",settlementLog);
        LogObjectHolder.me().set(settlementLog);
        return PREFIX + "settlementLog_edit.html";
    }

    /**
     * 获取回款结算记录列表
     */
    @RequestMapping(value = "/list")
    @ResponseBody
    public Object list(String condition, Integer spId) {
        Wrapper wrapper = new EntityWrapper<SettlementLog>();
        if(spId != null) wrapper.eq("spId", spId);
        return settlementLogService.selectList(wrapper);
    }

    /**
     * 新增回款结算记录
     */
    @RequestMapping(value = "/add")
    @ResponseBody
    public Object add(SettlementLog settlementLog) {
        settlementLogService.insert(settlementLog);
        return SUCCESS_TIP;
    }

    /**
     * 删除回款结算记录
     */
    @RequestMapping(value = "/delete")
    @ResponseBody
    public Object delete(@RequestParam Integer settlementLogId) {
        settlementLogService.deleteById(settlementLogId);
        return SUCCESS_TIP;
    }

    /**
     * 修改回款结算记录
     */
    @RequestMapping(value = "/update")
    @ResponseBody
    public Object update(SettlementLog settlementLog) {
        settlementLogService.updateById(settlementLog);
        return SUCCESS_TIP;
    }

    /**
     * 回款结算记录详情
     */
    @RequestMapping(value = "/detail/{settlementLogId}")
    @ResponseBody
    public Object detail(@PathVariable("settlementLogId") Integer settlementLogId) {
        return settlementLogService.selectById(settlementLogId);
    }

    /**
     * 导出结算记录Excel
     */
    @RequestMapping(value="/exportSettlementLog")
    public void exportSettlementLog(Integer settlementId, HttpServletRequest request, HttpServletResponse response) {
        List<OrderRebate> list = orderRebateService.selectList(new EntityWrapper<OrderRebate>().eq("settlementId", settlementId));
        // 表格数据【封装】
        List<List<String>> dataList = new ArrayList<List<String>>();

        // 头部列【封装】
        List<String> shellList = new ArrayList<String>();
        shellList.add("回款ID");
        shellList.add("订单ID");
        shellList.add("回款金额");
        shellList.add("回款时间");
        dataList.add(shellList);

        // 详细数据列【封装】
        for (OrderRebate obj : list) {
            shellList = new ArrayList<String>();
            shellList.add(obj.getId().toString() + "");
            shellList.add(obj.getOrderId().toString() + "");
            shellList.add(obj.getMoney().toString() + "");
            shellList.add(DateUtil.formatDate(obj.getRebateTime(), "YYYY-MM-dd HH:mm:ss"));

            dataList.add(shellList);
        }

        try {
            // 调用工具类进行导出
            ExcelExportUtil.easySheet("结算记录"+DateUtil.formatDate(new Date(), "YYYYMMdd"), "结算记录", dataList, request, response);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
