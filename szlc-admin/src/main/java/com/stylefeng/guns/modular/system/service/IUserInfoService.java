package com.stylefeng.guns.modular.system.service;

import com.stylefeng.guns.modular.system.model.UserInfo;
import com.baomidou.mybatisplus.service.IService;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 用户信息 服务类
 * </p>
 *
 * @author stylefeng123
 * @since 2018-06-19
 */
public interface IUserInfoService extends IService<UserInfo> {

    /**
     * 过去7天用户注册数
     */
    List<Map<String, Object>> past_7_day();

}
