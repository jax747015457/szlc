package com.stylefeng.guns.modular.system.controller;

import com.stylefeng.guns.config.properties.GunsProperties;
import com.stylefeng.guns.core.base.controller.BaseController;
import com.stylefeng.guns.core.common.exception.BizExceptionEnum;
import com.stylefeng.guns.core.exception.GunsException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.util.UUID;

/**
 * 上传文件
 */
@RestController
@RequestMapping("/upload")
public class UploadController extends BaseController {
    private final static Logger log = LoggerFactory.getLogger(UploadController.class);

    @Autowired
    private GunsProperties gunsProperties;

    /**
     * 上传图片(上传到项目的webapp/static/img)
     */
    @RequestMapping("/image")
    public String image(@RequestPart("file") MultipartFile picture) {
        String pictureName = UUID.randomUUID().toString() + ".jpg";
        try {
            // 文件目录路径
            String fileSavePath = gunsProperties.getFileUploadPath();
            picture.transferTo(new File(fileSavePath + pictureName));
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw new GunsException(BizExceptionEnum.UPLOAD_ERROR);
        }
        return pictureName;
    }

    /**
     * UEditor编辑器上传图片
     */
    @RequestMapping("/imageUp")
    public String imageUp(@RequestPart("upfile") MultipartFile picture, HttpServletRequest request) {
        String callback = request.getParameter("callback");
        String pictureName = UUID.randomUUID().toString() + ".jpg";
        try {
            // 上传文件目录
            String fileSavePath = gunsProperties.getFileUploadPath();
            picture.transferTo(new File(fileSavePath + pictureName));
            // 文件全路径
            pictureName = gunsProperties.getPictureServerAddress() + pictureName;

            String result = "{'original': '" + picture.getOriginalFilename() + "', 'state': 'SUCCESS', 'url': '" + pictureName + "'}";
            if (callback == null) {
                return result;
            } else {
                return "<script>" + callback + "(" + result + ")</script>";
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            String result = "{'original': '', 'state': '文件上传失败','url': ''}";
            if (callback == null) {
                return result;
            } else {
                return "<script>" + callback + "(" + result + ")</script>";
            }
        }
    }

}
