package com.stylefeng.guns.modular.system.service;

import com.stylefeng.guns.modular.system.model.MonitorData;
import com.baomidou.mybatisplus.service.IService;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 回款监控数据 服务类
 * </p>
 *
 * @author stylefeng123
 * @since 2018-06-27
 */
public interface IMonitorDataService extends IService<MonitorData> {

    /**
     * 查询回款信息
     */
    List<Map<String, Object>> getMapList(String nickName, String phone, Integer type, String beginTime, String endTime);

}
