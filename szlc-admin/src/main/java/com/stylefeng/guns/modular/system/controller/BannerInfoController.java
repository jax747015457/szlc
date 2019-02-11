package com.stylefeng.guns.modular.system.controller;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.stylefeng.guns.config.properties.GunsProperties;
import com.stylefeng.guns.core.base.controller.BaseController;
import com.stylefeng.guns.core.log.LogObjectHolder;
import com.stylefeng.guns.core.util.ReadOrWriteFile;
import com.stylefeng.guns.modular.system.model.BannerInfo;
import com.stylefeng.guns.modular.system.service.IBannerInfoService;
import com.stylefeng.guns.modular.system.warpper.BannerWarpper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.File;

/**
 * Banner信息控制器
 * @Date 2018-06-19 17:18:54
 */
@Controller
@RequestMapping("/bannerInfo")
public class BannerInfoController extends BaseController {

    private String PREFIX = "/system/bannerInfo/";

    @Autowired
    private GunsProperties gunsProperties;

    @Autowired
    private IBannerInfoService bannerInfoService;

    /**
     * 跳转到Banner信息首页
     */
    @RequestMapping("")
    public String index() {
        return PREFIX + "bannerInfo.html";
    }

    /**
     * 跳转到添加Banner信息
     */
    @RequestMapping("/bannerInfo_add")
    public String bannerInfoAdd() {
        return PREFIX + "bannerInfo_add.html";
    }

    /**
     * 跳转到修改Banner信息
     */
    @RequestMapping("/bannerInfo_update/{bannerInfoId}")
    public String bannerInfoUpdate(@PathVariable Integer bannerInfoId, Model model) {
        BannerInfo bannerInfo = bannerInfoService.selectById(bannerInfoId);
        // 读取文件内容
        String content = ReadOrWriteFile.read(gunsProperties.getFileUploadPath(), bannerInfo.getContent());
        bannerInfo.setContent(content);
        model.addAttribute("item",bannerInfo);
        LogObjectHolder.me().set(bannerInfo);
        return PREFIX + "bannerInfo_edit.html";
    }

    /**
     * 获取Banner信息列表
     */
    @RequestMapping(value = "/list")
    @ResponseBody
    public Object list(String condition) {
        return new BannerWarpper(bannerInfoService.selectMaps(new EntityWrapper<BannerInfo>().like("title", condition))).warp();
    }

    /**
     * 新增Banner信息
     */
    @RequestMapping(value = "/add")
    @ResponseBody
    public Object add(BannerInfo bannerInfo) {
        bannerInfoService.insert(bannerInfo);
        if (bannerInfo != null) {
            // 写入文件内容
            String fileName = ReadOrWriteFile.write(gunsProperties.getFileUploadPath(), "banner_" + bannerInfo.getId() + ".html", bannerInfo.getContent());
            bannerInfo.setContent(fileName);
            bannerInfoService.updateById(bannerInfo);
        }
        return SUCCESS_TIP;
    }

    /**
     * 删除Banner信息
     */
    @RequestMapping(value = "/delete")
    @ResponseBody
    public Object delete(@RequestParam Integer bannerInfoId) {
        // 删除文件
        BannerInfo banner = bannerInfoService.selectById(bannerInfoId);
        if (banner != null) {
            File file = new File(gunsProperties.getFileUploadPath() + banner.getContent());
            file.delete();
        }
        bannerInfoService.deleteById(bannerInfoId);
        return SUCCESS_TIP;
    }

    /**
     * 修改Banner信息
     */
    @RequestMapping(value = "/update")
    @ResponseBody
    public Object update(BannerInfo bannerInfo) {
        if (bannerInfo != null && bannerInfo.getContent() != null) {
            // 写入文件内容
            String fileName = ReadOrWriteFile.write(gunsProperties.getFileUploadPath(), "banner_" + bannerInfo.getId() + ".html", bannerInfo.getContent());
            bannerInfo.setContent(fileName);
        }
        bannerInfoService.updateById(bannerInfo);
        return SUCCESS_TIP;
    }

    /**
     * Banner信息详情
     */
    @RequestMapping(value = "/detail/{bannerInfoId}")
    @ResponseBody
    public Object detail(@PathVariable("bannerInfoId") Integer bannerInfoId) {
        return bannerInfoService.selectById(bannerInfoId);
    }
}
