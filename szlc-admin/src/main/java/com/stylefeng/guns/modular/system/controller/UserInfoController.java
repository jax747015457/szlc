package com.stylefeng.guns.modular.system.controller;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.stylefeng.guns.core.base.controller.BaseController;
import com.stylefeng.guns.core.enums.EnumObjStatus;
import com.stylefeng.guns.core.util.MD5Util;
import com.stylefeng.guns.core.util.ToolUtil;
import com.stylefeng.guns.modular.system.model.UserQrcode;
import com.stylefeng.guns.modular.system.service.IUserQrcodeService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.beans.factory.annotation.Autowired;
import com.stylefeng.guns.core.log.LogObjectHolder;
import org.springframework.web.bind.annotation.RequestParam;
import com.stylefeng.guns.modular.system.model.UserInfo;
import com.stylefeng.guns.modular.system.service.IUserInfoService;

import java.util.Date;

/**
 * 用户信息控制器
 * @Date 2018-06-19 14:47:39
 */
@Controller
@RequestMapping("/userInfo")
public class UserInfoController extends BaseController {

    private String PREFIX = "/system/userInfo/";

    @Autowired
    private IUserInfoService userInfoService;
    @Autowired
    private IUserQrcodeService userQrcodeService;

    /**
     * 跳转到用户信息首页
     */
    @RequestMapping("")
    public String index() {
        return PREFIX + "userInfo.html";
    }

    /**
     * 跳转到添加用户信息
     */
    @RequestMapping("/userInfo_add")
    public String userInfoAdd() {
        return PREFIX + "userInfo_add.html";
    }

    /**
     * 跳转到修改用户信息
     */
    @RequestMapping("/userInfo_update/{userInfoId}")
    public String userInfoUpdate(@PathVariable Integer userInfoId, Model model) {
        UserInfo userInfo = userInfoService.selectById(userInfoId);
        model.addAttribute("item",userInfo);
        LogObjectHolder.me().set(userInfo);
        return PREFIX + "userInfo_edit.html";
    }

    /**
     * 获取用户信息列表
     */
    @RequestMapping(value = "/list")
    @ResponseBody
    public Object list(String phone, String nickName) {
        return userInfoService.selectList(new EntityWrapper<UserInfo>()
                .like("phone", phone)
                .or().like("nickName", nickName).orderBy("id", false));
    }

    /**
     * 新增用户信息
     */
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

    /**
     * 删除用户信息
     */
    @RequestMapping(value = "/delete")
    @ResponseBody
    public Object delete(@RequestParam Integer userInfoId) {
        userInfoService.deleteById(userInfoId);
        return SUCCESS_TIP;
    }

    /**
     * 修改用户信息
     */
    @RequestMapping(value = "/update")
    @ResponseBody
    public Object update(UserInfo userInfo) {
        userInfoService.updateById(userInfo);
        return SUCCESS_TIP;
    }

    /**
     * 用户信息详情
     */
    @RequestMapping(value = "/detail/{userInfoId}")
    @ResponseBody
    public Object detail(@PathVariable("userInfoId") Integer userInfoId) {
        return userInfoService.selectById(userInfoId);
    }
    /**
     * 跳转到查看详情页面
     */
    @RequestMapping("/userInfo_view/{id}")
    public String userInfoView(@PathVariable Integer id, Model model) {
        UserInfo userInfo = userInfoService.selectById(id);
        model.addAttribute("item",userInfo);
        LogObjectHolder.me().set(userInfo);
        return PREFIX + "userInfo_view.html";
    }

    /**
     * 启用/冻结
     */
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

    /**
     * 跳转到二维码
     */
    @RequestMapping("/userInfo_qrcode/{id}")
    public String userInfoQrcode(@PathVariable Integer id, Model model) {
        UserQrcode userQrcode = userQrcodeService.selectOne(new EntityWrapper<UserQrcode>().eq("userId", id));
        model.addAttribute("item",userQrcode == null ? new UserQrcode() : userQrcode);
        return PREFIX + "userInfo_qrcode.html";
    }

    /**
     * 重置用户密码
     */
    @RequestMapping(value = "/updateResetPwd")
    @ResponseBody
    public Object updateResetPwd(UserInfo userInfo) {
        if (userInfo != null && ToolUtil.isNotEmpty(userInfo.getUserPwd())) {
            userInfo.setUserPwd(MD5Util.encrypt(userInfo.getUserPwd()));
        }
        userInfoService.updateById(userInfo);
        return SUCCESS_TIP;
    }

    /**
     * 跳转到查修改优先度
     */
    @RequestMapping("/userInfo_orderby/{id}")
    public String userInfoOrderby(@PathVariable Integer id, Model model) {
        UserInfo userInfo = userInfoService.selectById(id);
        model.addAttribute("item",userInfo);
        LogObjectHolder.me().set(userInfo);
        return PREFIX + "userInfo_orderby.html";
    }
}
