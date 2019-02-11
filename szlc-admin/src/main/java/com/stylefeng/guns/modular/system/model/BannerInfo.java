package com.stylefeng.guns.modular.system.model;

import java.io.Serializable;

import com.baomidou.mybatisplus.enums.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;
import java.io.Serializable;

/**
 * <p>
 * Banner信息
 * </p>
 *
 * @author szlc123
 * @since 2018-07-18
 */
@TableName("app_banner_info")
public class BannerInfo extends Model<BannerInfo> {

    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    /**
     * 标题
     */
    private String title;
    /**
     * Banner图
     */
    private String imgUrl;
    /**
     * 排序
     */
    private Integer orderby;
    /**
     * 跳转类型
     */
    private Integer urlType;
    /**
     * 跳转路径
     */
    private String urlHtml;
    /**
     * 网页内容
     */
    private String content;
    /**
     * 备注说明
     */
    private String remark;
    /**
     * 是否发布
     */
    private Integer isSue;
    /**
     * 是否删除
     */
    private Integer isDelete;
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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public Integer getOrderby() {
        return orderby;
    }

    public void setOrderby(Integer orderby) {
        this.orderby = orderby;
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

    public Integer getIsSue() {
        return isSue;
    }

    public void setIsSue(Integer isSue) {
        this.isSue = isSue;
    }

    public Integer getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(Integer isDelete) {
        this.isDelete = isDelete;
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
        return "BannerInfo{" +
        "id=" + id +
        ", title=" + title +
        ", imgUrl=" + imgUrl +
        ", orderby=" + orderby +
        ", urlType=" + urlType +
        ", urlHtml=" + urlHtml +
        ", content=" + content +
        ", remark=" + remark +
        ", isSue=" + isSue +
        ", isDelete=" + isDelete +
        ", createTime=" + createTime +
        "}";
    }
}
