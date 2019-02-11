package com.stylefeng.guns.modular.system.service.impl;

import com.stylefeng.guns.modular.system.model.MessageInfo;
import com.stylefeng.guns.modular.system.dao.MessageInfoMapper;
import com.stylefeng.guns.modular.system.service.IMessageInfoService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 系统消息 服务实现类
 * </p>
 *
 * @author stylefeng123
 * @since 2018-07-23
 */
@Service
public class MessageInfoServiceImpl extends ServiceImpl<MessageInfoMapper, MessageInfo> implements IMessageInfoService {

}
