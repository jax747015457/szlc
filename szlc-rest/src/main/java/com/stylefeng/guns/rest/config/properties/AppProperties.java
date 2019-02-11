package com.stylefeng.guns.rest.config.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.io.File;

import static com.stylefeng.guns.core.util.ToolUtil.getTempPath;
import static com.stylefeng.guns.core.util.ToolUtil.isEmpty;

/**
 * app相关配置
 */
@Configuration
@ConfigurationProperties(prefix = AppProperties.APP_PREFIX)
public class AppProperties {

    public static final String APP_PREFIX = "app";

    // 文件上传位置配置
    private String fileUploadPath;

    // 图片服务器地址
    private String pictureServerAddress;

    private Boolean haveCreatePath = false;

    public String getFileUploadPath() {
        //如果没有写文件上传路径,保存到临时目录
        if (isEmpty(fileUploadPath)) {
            return getTempPath();
        } else {
            //判断有没有结尾符,没有得加上
            if (!fileUploadPath.endsWith(File.separator)) {
                fileUploadPath = fileUploadPath + File.separator;
            }
            //判断目录存不存在,不存在得加上
            if (!haveCreatePath) {
                File file = new File(fileUploadPath);
                file.mkdirs();
                haveCreatePath = true;
            }
            return fileUploadPath;
        }
    }

    public void setFileUploadPath(String fileUploadPath) {
        this.fileUploadPath = fileUploadPath;
    }

    public String getPictureServerAddress() {
        return pictureServerAddress;
    }

    public void setPictureServerAddress(String pictureServerAddress) {
        this.pictureServerAddress = pictureServerAddress;
    }
}
