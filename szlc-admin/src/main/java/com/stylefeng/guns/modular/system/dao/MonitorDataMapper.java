package com.stylefeng.guns.modular.system.dao;

import com.stylefeng.guns.modular.system.model.MonitorData;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 回款监控数据 Mapper 接口
 * </p>
 *
 * @author stylefeng123
 * @since 2018-06-28
 */
public interface MonitorDataMapper extends BaseMapper<MonitorData> {

    /**
     * 查询监控数据
     */
    List<Map<String,Object>> getMapList(@Param("nickName") String nickName, @Param("phone") String phone, @Param("type") Integer type, @Param("beginTime") String beginTime, @Param("endTime") String endTime);
}
