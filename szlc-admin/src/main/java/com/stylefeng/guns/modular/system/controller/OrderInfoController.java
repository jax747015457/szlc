package com.stylefeng.guns.modular.system.controller;

import com.stylefeng.guns.core.base.controller.BaseController;
import com.stylefeng.guns.core.log.LogObjectHolder;
import com.stylefeng.guns.modular.system.model.OrderInfo;
import com.stylefeng.guns.modular.system.service.IOrderInfoService;
import com.stylefeng.guns.modular.system.warpper.OrderInfoWarpper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 订单信息控制器
 * @Date 2018-06-19 14:50:11
 */
@Controller
@RequestMapping("/orderInfo")
public class OrderInfoController extends BaseController {

    private String PREFIX = "/system/orderInfo/";

    @Autowired
    private IOrderInfoService orderInfoService;

    /**
     * 跳转到订单信息首页
     */
    @RequestMapping("")
    public String index() {
        return PREFIX + "orderInfo.html";
    }

    /**
     * 跳转到添加订单信息
     */
    @RequestMapping("/orderInfo_add")
    public String orderInfoAdd() {
        return PREFIX + "orderInfo_add.html";
    }

    /**
     * 跳转到修改订单信息
     */
    @RequestMapping("/orderInfo_update/{orderInfoId}")
    public String orderInfoUpdate(@PathVariable Integer orderInfoId, Model model) {
        OrderInfo orderInfo = orderInfoService.selectById(orderInfoId);
        model.addAttribute("item",orderInfo);
        LogObjectHolder.me().set(orderInfo);
        return PREFIX + "orderInfo_edit.html";
    }

    /**
     * 获取订单信息列表
     */
    @RequestMapping(value = "/list")
    @ResponseBody
    public Object list(String orderNo, String productName, String productNo, String nickName, String phone, Integer status, String beginTime, String endTime) {
        if (status != null && status == -1)
            status = null;
        return new OrderInfoWarpper().warp(orderInfoService.getVoList(orderNo, productName, productNo, nickName, phone, status, beginTime, endTime));
    }

    /**
     * 新增订单信息
     */
    @RequestMapping(value = "/add")
    @ResponseBody
    public Object add(OrderInfo orderInfo) {
        orderInfoService.insert(orderInfo);
        return SUCCESS_TIP;
    }

    /**
     * 删除订单信息
     */
    @RequestMapping(value = "/delete")
    @ResponseBody
    public Object delete(@RequestParam Integer orderInfoId) {
        orderInfoService.deleteById(orderInfoId);
        return SUCCESS_TIP;
    }

    /**
     * 修改订单信息
     */
    @RequestMapping(value = "/update")
    @ResponseBody
    public Object update(OrderInfo orderInfo) {
        orderInfoService.updateById(orderInfo);
        return SUCCESS_TIP;
    }

    /**
     * 订单信息详情
     */
    @RequestMapping(value = "/detail/{orderInfoId}")
    @ResponseBody
    public Object detail(@PathVariable("orderInfoId") Integer orderInfoId) {
        return orderInfoService.selectById(orderInfoId);
    }
}
