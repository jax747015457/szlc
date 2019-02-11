package com.stylefeng.guns.rest.modular.system.model;

import java.io.Serializable;

import com.baomidou.mybatisplus.enums.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;
import java.io.Serializable;

/**
 * <p>
 * 广告设置
 * </p>
 *
 * @author szlc123
 * @since 2018-06-28
 */
@TableName("app_advertising")
public class Advertising extends Model<Advertising> {

    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    /**
     * Banner图
     */
    private String imgUrl;
    /**
     * 图片尺寸
     */
    private String imgTip;
    /**
     * 跳转类型
     */
    private Integer urlType;
    /**
     * 跳转路径
     */
    private String urlHtml;
    /**
     * 页面内容
     */
    private String content;
    /**
     * 备注说明
     */
    private String remark;
    /**
     * 创建时间
     */
    private Date createTime;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getImgTip() {
        return imgTip;
    }

    public void setImgTip(String imgTip) {
        this.imgTip = imgTip;
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

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "Advertising{" +
        "id=" + id +
        ", imgUrl=" + imgUrl +
        ", imgTip=" + imgTip +
        ", urlType=" + urlType +
        ", urlHtml=" + urlHtml +
        ", content=" + content +
        ", remark=" + remark +
        ", createTime=" + createTime +
        "}";
    }
}
