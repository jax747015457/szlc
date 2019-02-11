package com.stylefeng.guns.rest.modular.system.model;

import java.io.Serializable;

import com.baomidou.mybatisplus.enums.IdType;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;
import java.io.Serializable;

/**
 * <p>
 * 系统设置
 * </p>
 *
 * @author szlc123
 * @since 2018-06-12
 */
@TableName("app_set_info")
public class SetInfo extends Model<SetInfo> {

    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    /**
     * 参数名
     */
    private String paramName;
    /**
     * 键值
     */
    private String keyStr;
    /**
     * 参数值
     */
    private String valueStr;
    /**
     * 备注说明
     */
    private String remark;
    /**
     * 扩展字段
     */
    private String key1;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getParamName() {
        return paramName;
    }

    public void setParamName(String paramName) {
        this.paramName = paramName;
    }

    public String getKeyStr() {
        return keyStr;
    }

    public void setKeyStr(String keyStr) {
        this.keyStr = keyStr;
    }

    public String getValueStr() {
        return valueStr;
    }

    public void setValueStr(String valueStr) {
        this.valueStr = valueStr;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getKey1() {
        return key1;
    }

    public void setKey1(String key1) {
        this.key1 = key1;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "SetInfo{" +
        "id=" + id +
        ", paramName=" + paramName +
        ", keyStr=" + keyStr +
        ", valueStr=" + valueStr +
        ", remark=" + remark +
        ", key1=" + key1 +
        "}";
    }
}
