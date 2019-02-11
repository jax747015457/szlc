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
import com.stylefeng.guns.modular.system.model.UserQrcode;
import com.stylefeng.guns.modular.system.service.IUserQrcodeService;

/**
 * 转账二维码控制器
 * @Date 2018-06-29 20:22:08
 */
@Controller
@RequestMapping("/userQrcode")
public class UserQrcodeController extends BaseController {

    private String PREFIX = "/system/userQrcode/";

    @Autowired
    private IUserQrcodeService userQrcodeService;

    /**
     * 跳转到转账二维码首页
     */
    @RequestMapping("")
    public String index() {
        return PREFIX + "userQrcode.html";
    }

    /**
     * 跳转到添加转账二维码
     */
    @RequestMapping("/userQrcode_add")
    public String userQrcodeAdd() {
        return PREFIX + "userQrcode_add.html";
    }

    /**
     * 跳转到修改转账二维码
     */
    @RequestMapping("/userQrcode_update/{userQrcodeId}")
    public String userQrcodeUpdate(@PathVariable Integer userQrcodeId, Model model) {
        UserQrcode userQrcode = userQrcodeService.selectById(userQrcodeId);
        model.addAttribute("item",userQrcode);
        LogObjectHolder.me().set(userQrcode);
        return PREFIX + "userQrcode_edit.html";
    }

    /**
     * 获取转账二维码列表
     */
    @RequestMapping(value = "/list")
    @ResponseBody
    public Object list(String condition) {
        return userQrcodeService.selectList(null);
    }

    /**
     * 新增转账二维码
     */
    @RequestMapping(value = "/add")
    @ResponseBody
    public Object add(UserQrcode userQrcode) {
        userQrcodeService.insert(userQrcode);
        return SUCCESS_TIP;
    }

    /**
     * 删除转账二维码
     */
    @RequestMapping(value = "/delete")
    @ResponseBody
    public Object delete(@RequestParam Integer userQrcodeId) {
        userQrcodeService.deleteById(userQrcodeId);
        return SUCCESS_TIP;
    }

    /**
     * 修改转账二维码
     */
    @RequestMapping(value = "/update")
    @ResponseBody
    public Object update(UserQrcode userQrcode) {
        userQrcodeService.updateById(userQrcode);
        return SUCCESS_TIP;
    }

    /**
     * 转账二维码详情
     */
    @RequestMapping(value = "/detail/{userQrcodeId}")
    @ResponseBody
    public Object detail(@PathVariable("userQrcodeId") Integer userQrcodeId) {
        return userQrcodeService.selectById(userQrcodeId);
    }
}
