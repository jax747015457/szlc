package com.stylefeng.guns.modular.system.controller;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.stylefeng.guns.config.properties.GunsProperties;
import com.stylefeng.guns.core.base.controller.BaseController;
import com.stylefeng.guns.core.log.LogObjectHolder;
import com.stylefeng.guns.core.util.ReadOrWriteFile;
import com.stylefeng.guns.modular.system.model.NoticeInfo;
import com.stylefeng.guns.modular.system.service.INoticeInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.File;
import java.util.Date;

/**
 * 系统公告控制器
 *
 * @Date 2018-06-13 20:41:02
 */
@Controller
@RequestMapping("/noticeInfo")
public class NoticeInfoController extends BaseController {

    private String PREFIX = "/system/noticeInfo/";

    @Autowired
    private GunsProperties gunsProperties;

    @Autowired
    private INoticeInfoService noticeInfoService;

    /**
     * 跳转到系统公告首页
     */
    @RequestMapping("")
    public String index() {
        return PREFIX + "noticeInfo.html";
    }

    /**
     * 跳转到添加系统公告
     */
    @RequestMapping("/noticeInfo_add")
    public String noticeInfoAdd() {
        return PREFIX + "noticeInfo_add.html";
    }

    /**
     * 跳转到修改系统公告
     */
    @RequestMapping("/noticeInfo_update/{noticeInfoId}")
    public String noticeInfoUpdate(@PathVariable Integer noticeInfoId, Model model) {
        NoticeInfo noticeInfo = noticeInfoService.selectById(noticeInfoId);
        // 读取文件内容
        String content = ReadOrWriteFile.read(gunsProperties.getFileUploadPath(), noticeInfo.getContent());
        noticeInfo.setContent(content);
        model.addAttribute("item", noticeInfo);
        LogObjectHolder.me().set(noticeInfo);
        return PREFIX + "noticeInfo_edit.html";
    }

    /**
     * 获取系统公告列表
     */
    @RequestMapping(value = "/list")
    @ResponseBody
    public Object list(String condition) {
        return noticeInfoService.selectList(new EntityWrapper<NoticeInfo>()
                .like("title", condition)
                .or().like("content", condition)
                .orderBy("id", false)
        );
    }

    /**
     * 新增系统公告
     */
    @RequestMapping(value = "/add")
    @ResponseBody
    public Object add(NoticeInfo noticeInfo) {
        if (noticeInfo != null) {
            noticeInfo.setCreateTime(new Date());
            noticeInfoService.insert(noticeInfo);
        }
        if (noticeInfo != null) {
            // 写入文件内容
            String fileName = ReadOrWriteFile.write(gunsProperties.getFileUploadPath(), "notice_" + noticeInfo.getId() + ".html", noticeInfo.getContent());
            noticeInfo.setContent(fileName);
            noticeInfoService.updateById(noticeInfo);
        }
        return SUCCESS_TIP;
    }

    /**
     * 删除系统公告
     */
    @RequestMapping(value = "/delete")
    @ResponseBody
    public Object delete(@RequestParam Integer noticeInfoId) {
        // 删除文件
        NoticeInfo notice = noticeInfoService.selectById(noticeInfoId);
        if (notice != null) {
            File file = new File(gunsProperties.getFileUploadPath() + notice.getContent());
            file.delete();
        }
        noticeInfoService.deleteById(noticeInfoId);
        return SUCCESS_TIP;
    }

    /**
     * 修改系统公告
     */
    @RequestMapping(value = "/update")
    @ResponseBody
    public Object update(NoticeInfo noticeInfo) {
        if (noticeInfo != null) {
            // 写入文件内容
            String fileName = ReadOrWriteFile.write(gunsProperties.getFileUploadPath(), "notice_" + noticeInfo.getId() + ".html", noticeInfo.getContent());
            noticeInfo.setContent(fileName);
        }
        noticeInfoService.updateById(noticeInfo);
        return SUCCESS_TIP;
    }

    /**
     * 系统公告详情
     */
    @RequestMapping(value = "/detail/{noticeInfoId}")
    @ResponseBody
    public Object detail(@PathVariable("noticeInfoId") Integer noticeInfoId) {
        return noticeInfoService.selectById(noticeInfoId);
    }
}
