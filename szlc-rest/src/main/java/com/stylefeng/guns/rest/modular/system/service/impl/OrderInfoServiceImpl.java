package com.stylefeng.guns.rest.modular.system.service.impl;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.stylefeng.guns.rest.modular.system.dao.OrderInfoMapper;
import com.stylefeng.guns.rest.modular.system.model.OrderInfo;
import com.stylefeng.guns.rest.modular.system.model.vo.OrderVo;
import com.stylefeng.guns.rest.modular.system.service.IOrderInfoService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 订单 服务实现类
 * </p>
 *
 * @author szlc123
 * @since 2018-05-24
 */
@Service
public class OrderInfoServiceImpl extends ServiceImpl<OrderInfoMapper, OrderInfo> implements IOrderInfoService {

    @Resource
    private OrderInfoMapper orderInfoMapper;

    @Override
    public List<OrderVo> selectOpListByUserAndStatus(Integer userId, Integer productId, Integer[] status, Integer current, Integer size) {
        if (current != null && size != null) {
            current = (current - 1) * size;
        }
        return orderInfoMapper.selectOpListByUserAndStatus(userId, productId, status, current, size);
    }

    @Override
    public List<OrderVo> selectOpuListByUserAndStatus(Integer userId, Integer productId, Integer[] status, Integer current, Integer size) {
        if (current != null && size != null) {
            current = (current - 1) * size;
        }
        return orderInfoMapper.selectOpuListByUserAndStatus(userId, productId, status, current, size);
    }
}
