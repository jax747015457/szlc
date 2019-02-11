package com.stylefeng.guns.modular.system.service.impl;

import com.stylefeng.guns.modular.system.model.MchContactInfo;
import com.stylefeng.guns.modular.system.dao.MchContactInfoMapper;
import com.stylefeng.guns.modular.system.service.MchContactInfoService;
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
public class MchContactInfoServiceImpl extends ServiceImpl<MchContactInfoMapper, MchContactInfo> implements MchContactInfoService {

    @Resource
    MchContactInfoMapper mchContactInfoMapper;

    @Override
    public List<Map<String, Object>> list() {
        return mchContactInfoMapper.list();
    }
}
