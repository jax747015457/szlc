package com.stylefeng.guns.rest.modular.system.model.vo;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.enums.IdType;

import java.io.Serializable;
import java.util.*;

public class BannerVo {
    /**
     * Banner图
     */
    private String imgUrl;
    /**
     * 跳转类型
     */
    private Integer urlType;
    /**
     * 跳转路径
     */
    private String urlHtml;

    public BannerVo() {
    }

    public BannerVo(String imgUrl, Integer urlType, String urlHtml) {
        this.imgUrl = imgUrl;
        this.urlType = urlType;
        this.urlHtml = urlHtml;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public Integer getUrlType() {
        return urlType;
    }

    public void setUrlType(Integer urlType) {
        this.urlType = urlType;
    }

    public String getUrlHtml() {
        return urlHtml;
    }

    public void setUrlHtml(String urlHtml) {
        this.urlHtml = urlHtml;
    }
}
