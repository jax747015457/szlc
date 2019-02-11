package com.stylefeng.guns.modular.system.controller;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.stylefeng.guns.core.base.controller.BaseController;
import com.stylefeng.guns.core.enums.EnumObjStatus;
import com.stylefeng.guns.core.log.LogObjectHolder;
import com.stylefeng.guns.core.util.ToolUtil;
import com.stylefeng.guns.modular.system.model.ProductInfo;
import com.stylefeng.guns.modular.system.service.IProductInfoService;
import com.stylefeng.guns.modular.system.warpper.ProductInfoWarpper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;

/**
 * 理财产品控制器
 * @Date 2018-06-19 14:47:21
 */
@Controller
@RequestMapping("/productInfo")
public class ProductInfoController extends BaseController {

    private String PREFIX = "/system/productInfo/";

    @Autowired
    private IProductInfoService productInfoService;

    /**
     * 跳转到理财产品首页
     */
    @RequestMapping("")
    public String index() {
        return PREFIX + "productInfo.html";
    }

    /**
     * 跳转到添加理财产品
     */
    @RequestMapping("/productInfo_add")
    public String productInfoAdd() {
        return PREFIX + "productInfo_add.html";
    }

    /**
     * 跳转到修改理财产品
     */
    @RequestMapping("/productInfo_update/{productInfoId}")
    public String productInfoUpdate(@PathVariable Integer productInfoId, Model model) {
        ProductInfo productInfo = productInfoService.selectById(productInfoId);
        model.addAttribute("item",productInfo);
        LogObjectHolder.me().set(productInfo);
        return PREFIX + "productInfo_edit.html";
    }

    /**
     * 获取理财产品列表
     */
    @RequestMapping(value = "/list")
    @ResponseBody
    public Object list(String productName, String productNo, Integer status, String beginTime, String endTime) {
        Wrapper wrapper = new EntityWrapper<ProductInfo>();
        if (ToolUtil.isNotEmpty(productName)) {
            wrapper.and().like("productName", productName);
        }
        if (ToolUtil.isNotEmpty(productNo)) {
            wrapper.and().like("productNo", productNo);
        }
        if (ToolUtil.isNotEmpty(status) && status != -1) {
            wrapper.and().eq("status", status);
        }
        if (ToolUtil.isNotEmpty(beginTime) && ToolUtil.isNotEmpty(endTime)) {
            wrapper.between("createTime", beginTime, endTime);
        }
        return new ProductInfoWarpper(productInfoService.selectMaps(wrapper)).warp();
    }

    /**
     * 新增理财产品
     */
    @RequestMapping(value = "/add")
    @ResponseBody
    public Object add(ProductInfo productInfo) {
        if (productInfo != null) {
            productInfo.setStatus(EnumObjStatus.normal.getValue());
            productInfo.setCreateTime(new Date());
        }
        productInfoService.insert(productInfo);
        return SUCCESS_TIP;
    }

    /**
     * 删除理财产品
     */
    @RequestMapping(value = "/delete")
    @ResponseBody
    public Object delete(@RequestParam Integer productInfoId) {
        productInfoService.deleteById(productInfoId);
        return SUCCESS_TIP;
    }

    /**
     * 修改理财产品
     */
    @RequestMapping(value = "/update")
    @ResponseBody
    public Object update(ProductInfo productInfo) {
        productInfoService.updateById(productInfo);
        return SUCCESS_TIP;
    }

    /**
     * 理财产品详情
     */
    @RequestMapping(value = "/detail/{productInfoId}")
    @ResponseBody
    public Object detail(@PathVariable("productInfoId") Integer productInfoId) {
        return productInfoService.selectById(productInfoId);
    }

    /**
     * 启用/冻结
     */
    @ResponseBody
    @RequestMapping(value = "/freeze")
    public Object freeze(ProductInfo obj) {
        if (obj != null) {
            // 获取当前状态
            Integer status = productInfoService.selectById(obj.getId()).getStatus();
            // 修改为启用/冻结状态
            obj.setStatus(status == EnumObjStatus.normal.getValue() ? EnumObjStatus.freeze.getValue() : EnumObjStatus.normal.getValue());
            productInfoService.updateById(obj);
        }
        return SUCCESS_TIP;
    }
}
