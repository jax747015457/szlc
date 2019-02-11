package com.stylefeng.guns.modular.system.controller;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.stylefeng.guns.core.base.controller.BaseController;
import com.stylefeng.guns.modular.system.model.MchContactInfo;
import com.stylefeng.guns.modular.system.service.MchContactInfoService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 商户信息管理
 * @Date 2018-06-19 14:47:39
 */
@Controller
@RequestMapping("/mchContactInfo")
public class MchContactInfoController extends BaseController {

    private String PREFIX = "/system/MchContactInfo/";

    @Autowired
    private MchContactInfoService mchContactInfoService;
    
    /**
     * 跳转到商户信息管理首页
     */
    @RequestMapping("")
    public String index() {
        return PREFIX + "mchContactInfo.html";
    }

    /**
    * 获取商户信息列表
    */
   @RequestMapping(value = "/list")
   @ResponseBody
   public Object list(String sTureName, String sPhone) {
	   System.out.println("进入了    list(String sTureName, String sPhone)");
       return mchContactInfoService.selectList(new EntityWrapper<MchContactInfo>()
               .like("sTureName", sTureName)
               .or().like("iId", sTureName)
               .like("sPhone", sPhone)
               .orderBy("iId", false));
   }
    
    /**
     * 跳转到添加用户信息
     *//*
    @RequestMapping("/usercfo")
    public String usercfo() {
        return PREFIX + "usercfo.html";
    }
    
    *//**
     * 跳转到添加用户信息
     *//*
    @RequestMapping("/userlogs")
    public String userlogs() {
        return PREFIX + "userlogs.html";
    }*/
    
   /* *//**
     * 跳转到修改用户信息
     *//*
    @RequestMapping("/userInfo_update/{userInfoId}")
    public String userInfoUpdate(@PathVariable Integer userInfoId, Model model) {
        UserInfo userInfo = userInfoService.selectById(userInfoId);
        model.addAttribute("item",userInfo);
        LogObjectHolder.me().set(userInfo);
        return PREFIX + "userInfo_edit.html";
    }

    *//**
     * 获取用户信息列表
     *//*
    @RequestMapping(value = "/list")
    @ResponseBody
    public Object list(String phone, String nickName) {
        return userInfoService.selectList(new EntityWrapper<UserInfo>()
                .like("phone", phone)
                .or().like("nickName", nickName).orderBy("id", false));
    }

    *//**
     * 新增用户信息
     *//*
    @RequestMapping(value = "/add")
    @ResponseBody
    public Object add(UserInfo userInfo) {
        if (userInfo != null) {
            userInfo.setAccount(userInfo.getPhone());
            userInfo.setCreditScore(0);
            userInfo.setStatus(EnumObjStatus.normal.getValue());
            userInfo.setCreateTime(new Date());
        }
        userInfoService.insert(userInfo);
        return SUCCESS_TIP;
    }

    *//**
     * 删除用户信息
     *//*
    @RequestMapping(value = "/delete")
    @ResponseBody
    public Object delete(@RequestParam Integer userInfoId) {
        userInfoService.deleteById(userInfoId);
        return SUCCESS_TIP;
    }

    *//**
     * 修改用户信息
     *//*
    @RequestMapping(value = "/update")
    @ResponseBody
    public Object update(UserInfo userInfo) {
        userInfoService.updateById(userInfo);
        return SUCCESS_TIP;
    }

    *//**
     * 用户信息详情
     *//*
    @RequestMapping(value = "/detail/{userInfoId}")
    @ResponseBody
    public Object detail(@PathVariable("userInfoId") Integer userInfoId) {
        return userInfoService.selectById(userInfoId);
    }
    *//**
     * 跳转到查看详情页面
     *//*
    @RequestMapping("/userInfo_view/{id}")
    public String userInfoView(@PathVariable Integer id, Model model) {
        UserInfo userInfo = userInfoService.selectById(id);
        model.addAttribute("item",userInfo);
        LogObjectHolder.me().set(userInfo);
        return PREFIX + "userInfo_view.html";
    }

    *//**
     * 启用/冻结
     *//*
    @ResponseBody
    @RequestMapping(value = "/freeze")
    public Object freeze(UserInfo obj) {
        if (obj != null) {
            // 获取当前状态
            Integer status = userInfoService.selectById(obj.getId()).getStatus();
            // 修改为启用/冻结状态
            obj.setStatus(status == EnumObjStatus.normal.getValue() ? EnumObjStatus.freeze.getValue() : EnumObjStatus.normal.getValue());
            userInfoService.updateById(obj);
        }
        return SUCCESS_TIP;
    }

    *//**
     * 跳转到二维码
     *//*
    @RequestMapping("/userInfo_qrcode/{id}")
    public String userInfoQrcode(@PathVariable Integer id, Model model) {
        UserQrcode userQrcode = userQrcodeService.selectOne(new EntityWrapper<UserQrcode>().eq("userId", id));
        model.addAttribute("item",userQrcode == null ? new UserQrcode() : userQrcode);
        return PREFIX + "userInfo_qrcode.html";
    }

    *//**
     * 重置用户密码
     *//*
    @RequestMapping(value = "/updateResetPwd")
    @ResponseBody
    public Object updateResetPwd(UserInfo userInfo) {
        if (userInfo != null && ToolUtil.isNotEmpty(userInfo.getUserPwd())) {
            userInfo.setUserPwd(MD5Util.encrypt(userInfo.getUserPwd()));
        }
        userInfoService.updateById(userInfo);
        return SUCCESS_TIP;
    }

    *//**
     * 跳转到查修改优先度
     *//*
    @RequestMapping("/userInfo_orderby/{id}")
    public String userInfoOrderby(@PathVariable Integer id, Model model) {
        UserInfo userInfo = userInfoService.selectById(id);
        model.addAttribute("item",userInfo);
        LogObjectHolder.me().set(userInfo);
        return PREFIX + "userInfo_orderby.html";
    }*/
}
