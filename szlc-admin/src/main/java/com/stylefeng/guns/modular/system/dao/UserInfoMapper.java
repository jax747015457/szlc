package com.stylefeng.guns.modular.system.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.stylefeng.guns.modular.system.model.UserInfo;

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
public interface UserInfoMapper extends BaseMapper<UserInfo> {

    /**
     * 过去7天用户注册数
     */
    List<Map<String, Object>> past_7_day();
}
