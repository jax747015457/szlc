package com.stylefeng.guns.modular.system.controller;

import com.stylefeng.guns.config.properties.GunsProperties;
import com.stylefeng.guns.core.base.controller.BaseController;
import com.stylefeng.guns.core.log.LogObjectHolder;
import com.stylefeng.guns.core.util.ReadOrWriteFile;
import com.stylefeng.guns.modular.system.model.SetInfo;
import com.stylefeng.guns.modular.system.service.ISetInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 系统设置控制器
 * @Date 2018-06-19 14:57:56
 */
@Controller
@RequestMapping("/setInfo")
public class SetInfoController extends BaseController {

    private String PREFIX = "/system/setInfo/";

    @Autowired
    private GunsProperties gunsProperties;

    @Autowired
    private ISetInfoService setInfoService;

    /**
     * 跳转到系统设置首页
     */
    @RequestMapping("")
    public String index() {
        return PREFIX + "setInfo.html";
    }

    /**
     * 跳转到添加系统设置
     */
    @RequestMapping("/setInfo_add")
    public String setInfoAdd() {
        return PREFIX + "setInfo_add.html";
    }

    /**
     * 跳转到修改系统设置
     */
    @RequestMapping("/setInfo_update/{setInfoId}")
    public String setInfoUpdate(@PathVariable Integer setInfoId, Model model) {
        SetInfo setInfo = setInfoService.selectById(setInfoId);
        model.addAttribute("item",setInfo);
        LogObjectHolder.me().set(setInfo);
        return PREFIX + "setInfo_edit.html";
    }

    /**
     * 获取系统设置列表
     */
    @RequestMapping(value = "/list")
    @ResponseBody
    public Object list(String condition) {
        return setInfoService.selectList(null);
    }

    /**
     * 新增系统设置
     */
    @RequestMapping(value = "/add")
    @ResponseBody
    public Object add(SetInfo setInfo) {
        setInfoService.insert(setInfo);
        return SUCCESS_TIP;
    }

    /**
     * 删除系统设置
     */
    @RequestMapping(value = "/delete")
    @ResponseBody
    public Object delete(@RequestParam Integer setInfoId) {
        setInfoService.deleteById(setInfoId);
        return SUCCESS_TIP;
    }

    /**
     * 修改系统设置
     */
    @RequestMapping(value = "/update")
    @ResponseBody
    public Object update(SetInfo setInfo) {
        setInfoService.updateById(setInfo);
        return SUCCESS_TIP;
    }

    /**
     * 系统设置详情
     */
    @RequestMapping(value = "/detail/{setInfoId}")
    @ResponseBody
    public Object detail(@PathVariable("setInfoId") Integer setInfoId) {
        return setInfoService.selectById(setInfoId);
    }

    /**
     * 跳转到修改相关介绍页
     */
    @RequestMapping("/html")
    public String html(Model model) {
        String content_1 = ReadOrWriteFile.read(gunsProperties.getFileUploadPath(), "aboutUs.html");
        model.addAttribute("url_1", gunsProperties.getPictureServerAddress() + "aboutUs.html");
        model.addAttribute("content_1", content_1);

        String content_2 = ReadOrWriteFile.read(gunsProperties.getFileUploadPath(), "userAgreement.html");
        model.addAttribute("url_2", gunsProperties.getPictureServerAddress() + "userAgreement.html");
        model.addAttribute("content_2", content_2);

        String content_3 = ReadOrWriteFile.read(gunsProperties.getFileUploadPath(), "incomeRules.html");
        model.addAttribute("url_3", gunsProperties.getPictureServerAddress() + "incomeRules.html");
        model.addAttribute("content_3", content_3);

        String content_4 = ReadOrWriteFile.read(gunsProperties.getFileUploadPath(), "takeoutRules.html");
        model.addAttribute("url_4", gunsProperties.getPictureServerAddress() + "takeoutRules.html");
        model.addAttribute("content_4", content_4);

        String content_5 = ReadOrWriteFile.read(gunsProperties.getFileUploadPath(), "tradingRules.html");
        model.addAttribute("url_5", gunsProperties.getPictureServerAddress() + "tradingRules.html");
        model.addAttribute("content_5", content_5);
        return PREFIX + "html.html";
    }

    /**
     * 修改相关介绍页面内容
     */
    @ResponseBody
    @RequestMapping(value = "/updateHtml")
    public Object updateHtml(Integer type, String content) {
        String fileName = "html.html";
        switch(type){
            case 1:
                fileName = "aboutUs.html";
                break;
            case 2:
                fileName = "userAgreement.html";
                break;
            case 3:
                fileName = "incomeRules.html";
                break;
            case 4:
                fileName = "takeoutRules.html";
                break;
            case 5:
                fileName = "tradingRules.html";
                break;
        }
        // 写入文件内容
        ReadOrWriteFile.write(gunsProperties.getFileUploadPath(), fileName, content);
        return SUCCESS_TIP;
    }
}
