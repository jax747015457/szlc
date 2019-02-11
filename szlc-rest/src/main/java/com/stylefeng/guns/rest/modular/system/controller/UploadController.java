package com.stylefeng.guns.rest.modular.system.controller;

import com.stylefeng.guns.core.base.controller.BaseController;
import com.stylefeng.guns.rest.common.util.ApiJson;
import com.stylefeng.guns.rest.config.properties.AppProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.UUID;

import static com.stylefeng.guns.rest.common.util.ApiJson.msgException;

/**
 * 上传文件
 */
@RestController
@RequestMapping("/api/upload")
public class UploadController extends BaseController {
    private final static Logger log = LoggerFactory.getLogger(UploadController.class);

    @Autowired
    private AppProperties appProperties;

    /**
     * 上传图片(上传到项目的webapp/static/img)
     */
    @RequestMapping("/image")
    public Object image(@RequestPart("file") MultipartFile picture) {
        String pictureName = UUID.randomUUID().toString() + ".jpg";
        try {
            String fileSavePath = appProperties.getFileUploadPath();
            picture.transferTo(new File(fileSavePath + pictureName));
            return ApiJson.returnOK(pictureName);
        } catch (Exception e) {
            e.printStackTrace();
            log.error(msgException, e);
        }
        return ApiJson.returnNG();
    }
}
