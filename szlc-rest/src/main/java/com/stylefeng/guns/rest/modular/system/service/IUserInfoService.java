package com.stylefeng.guns.rest.modular.system.service;

import com.baomidou.mybatisplus.service.IService;
import com.stylefeng.guns.rest.modular.system.model.UserInfo;
import com.stylefeng.guns.rest.modular.system.model.UserSet;

/**
 * <p>
 * 用户信息 服务类
 * </p>
 *
 * @author szlc123
 * @since 2018-05-18
 */
public interface IUserInfoService extends IService<UserInfo> {

    void insert(UserInfo user, UserSet userSet);
}
