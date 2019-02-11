package com.stylefeng.guns.rest.modular.system.service.impl;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.stylefeng.guns.rest.modular.system.dao.UserInfoMapper;
import com.stylefeng.guns.rest.modular.system.dao.UserSetMapper;
import com.stylefeng.guns.rest.modular.system.model.UserInfo;
import com.stylefeng.guns.rest.modular.system.model.UserSet;
import com.stylefeng.guns.rest.modular.system.service.IUserInfoService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * <p>
 * 用户信息 服务实现类
 * </p>
 *
 * @author szlc123
 * @since 2018-05-18
 */
@Service
@Transactional
public class UserInfoServiceImpl extends ServiceImpl<UserInfoMapper, UserInfo> implements IUserInfoService {

    @Resource
    private UserInfoMapper userInfoMapper;

    @Resource
    private UserSetMapper userSetMapper;

    @Override
    public void insert(UserInfo user, UserSet userSet){
        // 存入用户信息
        userInfoMapper.insert(user);
        System.out.println(user.getId()+"_____________");
        // 存入用户设置
        userSet.setUserId(user.getId());
        userSetMapper.insert(userSet);
    }
}
