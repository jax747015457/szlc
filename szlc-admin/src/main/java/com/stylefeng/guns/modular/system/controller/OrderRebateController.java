package com.stylefeng.guns.modular.system.controller;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.stylefeng.guns.core.base.controller.BaseController;
import com.stylefeng.guns.core.enums.EnumOrderRebateScanCode;
import com.stylefeng.guns.core.enums.EnumOrderRebateStatus;
import com.stylefeng.guns.core.log.LogObjectHolder;
import com.stylefeng.guns.modular.system.model.OrderRebate;
import com.stylefeng.guns.modular.system.model.vo.OrderRebateScanVo;
import com.stylefeng.guns.modular.system.service.IOrderRebateService;
import com.stylefeng.guns.modular.system.warpper.OrderRebateWarpper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

/**
 * 收益回款控制器
 * @Date 2018-06-28 16:23:45
 */
@Controller
@RequestMapping("/orderRebate")
public class OrderRebateController extends BaseController {
    Logger log = LoggerFactory.getLogger(this.getClass());

    private String PREFIX = "/system/orderRebate/";

    @Autowired
    private IOrderRebateService orderRebateService;

    /**
     * 跳转到收益回款首页
     */
    @RequestMapping("")
    public String index(Integer orderId, Model model) {
        model.addAttribute("orderId", orderId);
        return PREFIX + "orderRebate.html";
    }

    /**
     * 跳转到添加收益回款
     */
    @RequestMapping("/orderRebate_add")
    public String orderRebateAdd() {
        return PREFIX + "orderRebate_add.html";
    }

    /**
     * 跳转到修改收益回款
     */
    @RequestMapping("/orderRebate_update/{orderRebateId}")
    public String orderRebateUpdate(@PathVariable Integer orderRebateId, Model model) {
        OrderRebate orderRebate = orderRebateService.selectById(orderRebateId);
        model.addAttribute("item",orderRebate);
        LogObjectHolder.me().set(orderRebate);
        return PREFIX + "orderRebate_edit.html";
    }

    /**
     * 获取收益回款列表
     */
    @RequestMapping(value = "/list")
    @ResponseBody
    public Object list(Integer orderId, String orderNo, Integer status, String beginTime, String endTime) {
        if (status != null && status == -1)
            status = null;
        return new OrderRebateWarpper(orderRebateService.getMapList(orderId, orderNo, status, beginTime, endTime)).warp();
    }

    /**
     * 新增收益回款
     */
    @RequestMapping(value = "/add")
    @ResponseBody
    public Object add(OrderRebate orderRebate) {
        orderRebateService.insert(orderRebate);
        return SUCCESS_TIP;
    }

    /**
     * 删除收益回款
     */
    @RequestMapping(value = "/delete")
    @ResponseBody
    public Object delete(@RequestParam Integer orderRebateId) {
        orderRebateService.deleteById(orderRebateId);
        return SUCCESS_TIP;
    }

    /**
     * 修改收益回款
     */
    @RequestMapping(value = "/update")
    @ResponseBody
    public Object update(OrderRebate orderRebate) {
        // 平台确认收益回款
        if (orderRebate != null && orderRebate.getStatus() == EnumOrderRebateStatus.systemConfirm.getValue()) {
            // 设置回款信息
            orderRebate.setConfirmTime(new Date());
            orderRebate.setStatus(EnumOrderRebateStatus.systemConfirm.getValue());
            // 确认回款
            orderRebateService.updateConfimRebate(orderRebate);
        } else {
            orderRebateService.updateById(orderRebate);
        }
        return SUCCESS_TIP;
    }

    /**
     * 收益回款详情
     */
    @RequestMapping(value = "/detail/{orderRebateId}")
    @ResponseBody
    public Object detail(@PathVariable("orderRebateId") Integer orderRebateId) {
        return orderRebateService.selectById(orderRebateId);
    }

    /**
     * 跳转到取消异常
     */
    @RequestMapping("/orderRebate_cancelExcetion/{orderRebateId}")
    public String orderRebate_cancelExcetion(@PathVariable Integer orderRebateId, Model model) {
        OrderRebate orderRebate = orderRebateService.selectById(orderRebateId);
        model.addAttribute("item",orderRebate);
        LogObjectHolder.me().set(orderRebate);
        return PREFIX + "orderRebate_cancelExcetion.html";
    }

    /**
     * 跳转到服务商扫码收款界面
     */
    @RequestMapping(value = "scan")
    public String scan(String account, String key, Double money, Model model) {
        return PREFIX + "scan.html";
    }
    /**
     * 扫码回款信息
     */
    @ResponseBody
    @RequestMapping(value = "getQRcode", method = RequestMethod.POST)
    public Object getQRcode(String account, String key, Double money) {

        return orderRebateService.getOrderRebateScan(account, key, money, null);
    }
    /**
     * 确认已付款（解锁商户调用的二维码）
     */
    @ResponseBody
    @RequestMapping(value = "unlockSpId", method = RequestMethod.POST)
    public Object unlockSpId(Integer orderRebateId) {
        OrderRebate obj = new OrderRebate();
        obj.setId(orderRebateId);
        obj.setLockSpId(0);
        orderRebateService.updateById(obj);
        System.out.println(SUCCESS_TIP);
        return SUCCESS_TIP;
    }
    @RequestMapping(value = "qrcode")
    public Object qrcode(String account, String key, Double money, Integer type, Model model) {
        // 获取二维码
        OrderRebateScanVo vo = orderRebateService.getOrderRebateScan(account, key, money, type);
        if (vo != null && vo.getCode() == EnumOrderRebateScanCode.OK.getValue()) {
            // 直接解锁二维码
            //unlockSpId(vo.getOrderRebateId());
        }
        model.addAttribute("data", JSON.toJSONString(vo));
        model.addAttribute("type", type);
        return PREFIX + "qrcode.html";
    }

    /**
     * 跳转到收益回款结算
     */
    @RequestMapping("serviceProvider")
    public String serviceProvider(Integer serviceProviderId, Model model) {
        model.addAttribute("serviceProviderId", serviceProviderId);
        return PREFIX + "serviceProvider.html";
    }
    /**
     * 获取收益回款列表
     */
    @RequestMapping(value = "/serviceProviderList")
    @ResponseBody
    public Object serviceProviderList(Integer serviceProviderId) {
        // 返回未结算的回款记录
        return orderRebateService.selectList(new EntityWrapper<OrderRebate>().eq("serviceProviderId", 1).and().isNull("settlementId"));
    }
    /**
     * 收益回款-结算
     */
    @ResponseBody
    @RequestMapping(value = "/settlement")
    public Object settlement(String ids) {
        // 结算操作
        orderRebateService.settlement(ids);
        return SUCCESS_TIP;
    }
}
