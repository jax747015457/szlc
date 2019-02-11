package com.stylefeng.guns.modular.system.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.stylefeng.guns.modular.system.model.MchContactInfo;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 用户信息 Mapper 接口
 * </p>
 *
 * @author stylefeng123
 * @since 2018-06-19
 */
public interface MchContactInfoMapper extends BaseMapper<MchContactInfo> {

    /**
     * 商户信息列表
     */
    List<Map<String, Object>> list();
}
