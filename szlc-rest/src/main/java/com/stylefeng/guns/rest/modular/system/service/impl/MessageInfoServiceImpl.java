package com.stylefeng.guns.rest.modular.system.service.impl;

import com.stylefeng.guns.rest.modular.system.model.MessageInfo;
import com.stylefeng.guns.rest.modular.system.dao.MessageInfoMapper;
import com.stylefeng.guns.rest.modular.system.service.IMessageInfoService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 系统消息 服务实现类
 * </p>
 *
 * @author szlc123
 * @since 2018-05-18
 */
@Service
public class MessageInfoServiceImpl extends ServiceImpl<MessageInfoMapper, MessageInfo> implements IMessageInfoService {

}
