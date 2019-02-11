package com.stylefeng.guns.rest.modular.system.model.vo;

import com.stylefeng.guns.core.util.DateUtil;

import java.util.Date;

/**
 * <p>
 * 用户信用分
 * </p>
 */
public class CreditScoreVo {
    /**
     * 来源
     */
    private String sources;
    /**
     * 累计
     */
    private String type;
    /**
     * 分数
     */
    private Integer score;
    /**
     * 创建时间
     */
    private String createTime;

    public CreditScoreVo() {
    }

    public String getSources() {
        return sources;
    }

    public void setSources(String sources) {
        this.sources = sources;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = DateUtil.formatDate(createTime, "yyyy-MM-dd HH:mm");
    }
}
