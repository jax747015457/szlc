package com.stylefeng.guns.modular.system.service.impl;

import com.stylefeng.guns.modular.system.model.MonitorData;
import com.stylefeng.guns.modular.system.dao.MonitorDataMapper;
import com.stylefeng.guns.modular.system.service.IMonitorDataService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 回款监控数据 服务实现类
 * </p>
 *
 * @author stylefeng123
 * @since 2018-06-27
 */
@Service
public class MonitorDataServiceImpl extends ServiceImpl<MonitorDataMapper, MonitorData> implements IMonitorDataService {

    @Resource
    MonitorDataMapper monitorDataMapper;

    @Override
    public List<Map<String, Object>> getMapList(String nickName, String phone, Integer type, String beginTime, String endTime) {
        return monitorDataMapper.getMapList(nickName, phone, type, beginTime, endTime);
    }
}
