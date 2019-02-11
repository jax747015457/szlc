package com.stylefeng.guns.modular.system.service;

import com.stylefeng.guns.modular.system.model.MchContactInfo;
import com.baomidou.mybatisplus.service.IService;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 商户信息 服务类
 * </p>
 *
 * @author stylefeng123
 * @since 2018-06-19
 */
public interface MchContactInfoService extends IService<MchContactInfo> {

    /**
     * 过去7天用户注册数
     */
    List<Map<String, Object>> list();

}
