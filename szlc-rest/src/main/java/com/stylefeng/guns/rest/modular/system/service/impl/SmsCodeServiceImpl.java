package com.stylefeng.guns.rest.modular.system.service.impl;

import com.stylefeng.guns.rest.modular.system.model.SmsCode;
import com.stylefeng.guns.rest.modular.system.dao.SmsCodeMapper;
import com.stylefeng.guns.rest.modular.system.service.ISmsCodeService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 短信验证码 服务实现类
 * </p>
 *
 * @author szlc123
 * @since 2018-05-22
 */
@Service
public class SmsCodeServiceImpl extends ServiceImpl<SmsCodeMapper, SmsCode> implements ISmsCodeService {

}
