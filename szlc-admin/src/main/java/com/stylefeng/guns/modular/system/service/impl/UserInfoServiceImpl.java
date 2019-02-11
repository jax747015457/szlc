package com.stylefeng.guns.modular.system.service.impl;

import com.stylefeng.guns.modular.system.model.UserInfo;
import com.stylefeng.guns.modular.system.dao.UserInfoMapper;
import com.stylefeng.guns.modular.system.service.IUserInfoService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 用户信息 服务实现类
 * </p>
 *
 * @author stylefeng123
 * @since 2018-06-19
 */
@Service
public class UserInfoServiceImpl extends ServiceImpl<UserInfoMapper, UserInfo> implements IUserInfoService {

    @Resource
    UserInfoMapper userInfoMapper;

    @Override
    public List<Map<String, Object>> past_7_day() {
        return userInfoMapper.past_7_day();
    }
}
