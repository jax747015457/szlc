package com.stylefeng.guns.modular.system.controller;

import com.stylefeng.guns.config.properties.GunsProperties;
import com.stylefeng.guns.core.base.controller.BaseController;
import com.stylefeng.guns.core.log.LogObjectHolder;
import com.stylefeng.guns.core.util.ReadOrWriteFile;
import com.stylefeng.guns.modular.system.model.Advertising;
import com.stylefeng.guns.modular.system.service.IAdvertisingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.File;

/**
 * 广告控制器
 * @Date 2018-06-28 17:50:38
 */
@Controller
@RequestMapping("/advertising")
public class AdvertisingController extends BaseController {

    private String PREFIX = "/system/advertising/";

    @Autowired
    private GunsProperties gunsProperties;

    @Autowired
    private IAdvertisingService advertisingService;

    /**
     * 跳转到广告首页
     */
    @RequestMapping("")
    public String index() {
        return PREFIX + "advertising.html";
    }

    /**
     * 跳转到添加广告
     */
    @RequestMapping("/advertising_add")
    public String advertisingAdd() {
        return PREFIX + "advertising_add.html";
    }

    /**
     * 跳转到修改广告
     */
    @RequestMapping("/advertising_update/{advertisingId}")
    public String advertisingUpdate(@PathVariable Integer advertisingId, Model model) {
        Advertising advertising = advertisingService.selectById(advertisingId);
        // 读取文件内容
        String content = ReadOrWriteFile.read(gunsProperties.getFileUploadPath(), advertising.getContent());
        advertising.setContent(content);
        model.addAttribute("item",advertising);
        LogObjectHolder.me().set(advertising);
        return PREFIX + "advertising_edit.html";
    }

    /**
     * 获取广告列表
     */
    @RequestMapping(value = "/list")
    @ResponseBody
    public Object list(String condition) {
        return advertisingService.selectList(null);
    }

    /**
     * 新增广告
     */
    @RequestMapping(value = "/add")
    @ResponseBody
    public Object add(Advertising advertising) {
        // 写入文件
        String fileName = ReadOrWriteFile.write(gunsProperties.getFileUploadPath(), "advertising_" + advertising.getId() + ".html", advertising.getContent());
        advertising.setContent(fileName);
        advertisingService.insert(advertising);
        return SUCCESS_TIP;
    }

    /**
     * 删除广告
     */
    @RequestMapping(value = "/delete")
    @ResponseBody
    public Object delete(@RequestParam Integer advertisingId) {
        // 删除文件
        Advertising advertising = advertisingService.selectById(advertisingId);
        if (advertising != null) {
            File file = new File(gunsProperties.getFileUploadPath() + advertising.getContent());
            file.delete();
        }
        advertisingService.deleteById(advertisingId);
        return SUCCESS_TIP;
    }

    /**
     * 修改广告
     */
    @RequestMapping(value = "/update")
    @ResponseBody
    public Object update(Advertising advertising) {
        // 写入文件
        String fileName = ReadOrWriteFile.write(gunsProperties.getFileUploadPath(), "advertising_" + advertising.getId() + ".html", advertising.getContent());
        advertising.setContent(fileName);
        advertisingService.updateById(advertising);
        return SUCCESS_TIP;
    }

    /**
     * 广告详情
     */
    @RequestMapping(value = "/detail/{advertisingId}")
    @ResponseBody
    public Object detail(@PathVariable("advertisingId") Integer advertisingId) {
        return advertisingService.selectById(advertisingId);
    }
}
