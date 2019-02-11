package com.stylefeng.guns.rest.modular.system.service.impl;

import com.stylefeng.guns.rest.modular.system.model.EmailCode;
import com.stylefeng.guns.rest.modular.system.dao.EmailCodeMapper;
import com.stylefeng.guns.rest.modular.system.service.IEmailCodeService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 邮件验证码 服务实现类
 * </p>
 *
 * @author szlc123
 * @since 2018-05-24
 */
@Service
public class EmailCodeServiceImpl extends ServiceImpl<EmailCodeMapper, EmailCode> implements IEmailCodeService {

}
