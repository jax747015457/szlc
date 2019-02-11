package com.stylefeng.guns.modular.system.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.stylefeng.guns.core.enums.EnumObjDelete;
import com.stylefeng.guns.core.enums.EnumOrderRebateScanCode;
import com.stylefeng.guns.core.enums.EnumOrderRebateStatus;
import com.stylefeng.guns.core.enums.EnumOrderStatus;
import com.stylefeng.guns.core.shiro.ShiroKit;
import com.stylefeng.guns.core.support.HttpKit;
import com.stylefeng.guns.core.util.Convert;
import com.stylefeng.guns.core.util.DateUtil;
import com.stylefeng.guns.modular.system.dao.*;
import com.stylefeng.guns.modular.system.model.*;
import com.stylefeng.guns.modular.system.model.vo.OrderRebateScanVo;
import com.stylefeng.guns.modular.system.service.IOrderRebateService;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.*;

/**
 * <p>
 * 收益回款订单 服务实现类
 * </p>
 */
@Service
@Transactional
public class OrderRebateServiceImpl extends ServiceImpl<OrderRebateMapper, OrderRebate> implements IOrderRebateService {
    private final static org.slf4j.Logger log = LoggerFactory.getLogger(OrderRebateServiceImpl.class);

    @Resource
    private OrderInfoMapper orderInfoMapper;

    @Resource
    private OrderRebateMapper orderRebateMapper;

    @Resource
    private ServiceProviderMapper serviceProviderMapper;

    @Resource
    private UserQrcodeMapper userQrcodeMapper;

    @Resource
    private MessageInfoMapper messageInfoMapper;

    @Resource
    private SettlementLogMapper settlementLogMapper;

    /**
     * 用户二维码
     */
    Map<String, Date> userQRcodeMap = new HashMap<>();

    @Override
    public List<Map<String, Object>> getMapList(Integer orderId, String orderNo, Integer status, String beginTime, String endTime) {
        return orderRebateMapper.getMapList(orderId, orderNo, status, beginTime, endTime);
    }

    @Override
    public boolean updateConfimRebate(OrderRebate orderRebate) {
        boolean flag = false;
        // 修改回款订单信息
        orderRebate.setLockSpId(0);
        orderRebateMapper.updateById(orderRebate);
        // 查询回款订单信息
        orderRebate = orderRebateMapper.selectById(orderRebate.getId());
        if (orderRebate != null && orderRebate.getOrderId() != null) {
            // 查询理财订单中回款数据（未确认状态）：未回款、回款中、异常未确认
            Integer[] status = new Integer[]{EnumOrderRebateStatus.waitRebate.getValue(), EnumOrderRebateStatus.inTheRebate.getValue(), EnumOrderRebateStatus.exceptionOrder.getValue()};
            Integer count = orderRebateMapper.selectCount(new EntityWrapper<OrderRebate>().eq("orderId", orderRebate.getOrderId()).and().in("status", status));

            // 回款订单状态已全部确认，则操作理财订单已确认回款
            if (count != null && count == 0) {
                // 设置理财订单已确认回款
                OrderInfo order = new OrderInfo();
                order.setId(orderRebate.getOrderId());
                order.setStatus(EnumOrderStatus.rebateSuccessful.getValue());
                orderInfoMapper.updateById(order);
            }
            // 操作成功
            flag = true;
        }
        return flag;
    }

    @Override
    public OrderRebateScanVo getOrderRebateScan(String account, String key, Double money, Integer type) {
        // 默认回款金额
        money = money == null ? 0 : money;
        // 默认设置允许访问
        int code = EnumOrderRebateScanCode.OK.getValue();

        // 获取服务商信息
        ServiceProvider serviceProvider = new ServiceProvider();
        serviceProvider.setIsDelete(EnumObjDelete.normal.getValue());
        serviceProvider.setPublicKey(account);
        serviceProvider.setPrivateKey(key);
        serviceProvider = serviceProviderMapper.selectOne(serviceProvider);

        // 验证服务商账号
        if (serviceProvider == null) {
            code = EnumOrderRebateScanCode.AccountError.getValue();
        }
        // 验证服务商IP
        if (serviceProvider != null && !HttpKit.getIp().equals(serviceProvider.getServerIp())
                && (serviceProvider.getServerIp() != null && !"".equals(serviceProvider.getServerIp()))) {
            code = EnumOrderRebateScanCode.IpException.getValue();
            log.error("__RepIP:__" + HttpKit.getIp() + "________________ServerIp():__" + serviceProvider.getServerIp());
            return new OrderRebateScanVo(code, EnumOrderRebateScanCode.getDesByCode(code) + HttpKit.getIp());
        }

        if (EnumOrderRebateScanCode.OK.getValue() != code) {
            // 不允许访问
            return new OrderRebateScanVo(code, EnumOrderRebateScanCode.getDesByCode(code));
        } else {
            // 最后确认的回款订单信息
            OrderRebate orderRebate = null;
            // 回款金额
            BigDecimal rebateMoney = null;
            // 支付宝收款码
            String qrCodeAli = null;
            // 微信收款码
            String qrCodeWx = null;

            // 查询该金额、该服务商是否有锁定的二维码（有锁定的二维码，说明这个二维码可能出现了异常情况，先处理）
            List<OrderRebate> rebateList = orderRebateMapper.selectList(new EntityWrapper<OrderRebate>()
                    .gt("money", money).or().eq("money", money)
                    .andNew().eq("lockSpId", serviceProvider.getId()));
            // 无锁定的二维码，直接获取正常的二维码
            if(rebateList == null || rebateList.size() == 0) {
                // 运行访问，获取订单回款扫码信息，待回款（请求确认后状态变为回款中），获取回款金额 >= 当前的回款订单升序排列，前面为最优
                rebateList = orderRebateMapper.selectList(new EntityWrapper<OrderRebate>()
                        .gt("money", money).or().eq("money", money)
                        .andNew().eq("status", EnumOrderRebateStatus.waitRebate.getValue())
                        .orderBy("money"));
            }

            // 如果请求金额与转款金额，fistList为回款金额完全匹配的(通过用户优先度确定顺序)，secondList为大于回款金额的（剩余部分重新生成回款订单）
            List<OrderRebate> fistList = new ArrayList<>();
            List<OrderRebate> secondList = new ArrayList<>();

            // 如果存在转款信息
            if (rebateList != null && rebateList.size() > 0) {
                for (OrderRebate rebate : rebateList) {
                    if (rebate.getMoney().doubleValue() == money) {
                        fistList.add(rebate);
                    } else {
                        secondList.add(rebate);
                    }
                }
            }

            // 是否筛选出符合条件的回款信息
            OrderRebateScanVo vo = null;
            // 回款金额完全匹配的(通过用户优先度确定顺序)
            if(fistList != null && fistList.size() > 0) {
                for (OrderRebate rebate : fistList) {
                    if(vo != null) break;
                    vo = setOrderRebateScanVo(code, rebate, money, rebateMoney, type, qrCodeAli, qrCodeWx, serviceProvider.getId());
                }
            }
            // 为大于回款金额的（剩余部分重新生成回款订单）
            if(secondList != null && secondList.size() > 0) {
                for (OrderRebate rebate : secondList) {
                    if (vo != null) break;
                    vo = setOrderRebateScanVo(code, rebate, money, rebateMoney, type, qrCodeAli, qrCodeWx, serviceProvider.getId());
                }
            }
            if (vo == null)
                vo = new OrderRebateScanVo(EnumOrderRebateScanCode.NoData.getValue(), EnumOrderRebateScanCode.NoData.getDes());
            return vo;
        }
    }
    public OrderRebateScanVo setOrderRebateScanVo(Integer code, OrderRebate orderRebate, Double money, BigDecimal rebateMoney, Integer type, String qrCodeAli, String qrCodeWx, Integer serviceProviderId){
        // 查询主订单信息
        OrderInfo order = orderInfoMapper.selectById(orderRebate.getOrderId());

        System.out.println(orderRebate);
        if (orderRebate != null && orderRebate.getLockSpId() == null){
            // 用户二维码获取时间（5分钟内不重复获取）
            if (userQRcodeMap == null) userQRcodeMap = new HashMap<>();
            Date qrDate = userQRcodeMap.get(order.getUserId().toString());
            if (qrDate != null) {
                long nowTime = qrDate.getTime();
                long qrTime = qrDate.getTime();
                int minutes = (int) ((nowTime - qrTime) / (1000 * 60));
                if(minutes < 5){
                    return null;
                }
            }
        }

        // 获取用户收款二维码
        List<UserQrcode> userQrcodeList = userQrcodeMapper.selectList(new EntityWrapper<UserQrcode>().eq("userId", order.getUserId()));
        if (type != null && type != 0) {
            // 如果获取二维码，有指定类型判断是否指定类型有二维码【限码类型：type=1（支付宝）type=2（微信支付）】
            if (userQrcodeList != null && userQrcodeList.size() > 0) {
                // 如果获取支付宝的二维码，判断支付宝码是否为空，并返回
                if(type == 1 && userQrcodeList.get(0).getQrCodeAli() != null) {
                    qrCodeAli = userQrcodeList.get(0).getQrCodeAli();
                }

                // 如果获取微信的二维码，判断微信码是否为空，并返回
                else if(type == 2 && userQrcodeList.get(0).getQrCodeWx() != null){
                    qrCodeWx = userQrcodeList.get(0).getQrCodeWx();
                } else {
                    return null;
                }
            } else {
                // 如果用户收款二维码未上传，则跳过
                return null;
            }
        } else {
            // 如果获取二维码类型不限，获取其中一个码即可【不限码类型】
            if (userQrcodeList != null && userQrcodeList.size() > 0 && (userQrcodeList.get(0).getQrCodeAli() != null || userQrcodeList.get(0).getQrCodeWx() != null)) {
                qrCodeAli = userQrcodeList.get(0).getQrCodeAli();
                qrCodeWx = userQrcodeList.get(0).getQrCodeWx();
            } else {
                // 如果用户收款二维码未上传，则跳过
                return null;
            }
        }


        // 修改主订单状态：待收益-->收益中
        order.setId(orderRebate.getOrderId());
        order.setStatus(EnumOrderStatus.inTheRebate.getValue());
        orderInfoMapper.updateById(order);

        // 修改回款订单状态：待回款-->回款中，并判断回款金额是否固定，固定金额则拆分回款订单回款部分，不固定回款金额则不拆分回款订单直接返回金额
        if(money != null && money > 0 && orderRebate.getMoney().doubleValue() > money) {
            // 固定金额则拆分回款订单回款部分，重新生成一个回款订单信息，存入数据库
            OrderRebate ooo = new OrderRebate();
            ooo.setRebateNo(orderRebate.getRebateNo() + 1);
            ooo.setMoney(orderRebate.getMoney().subtract(new BigDecimal(money)));
            ooo.setOrderId(orderRebate.getOrderId());
            ooo.setStatus(EnumOrderRebateStatus.waitRebate.getValue());
            ooo.setRebateTime(DateUtil.getDateAddHour(new Date(), 24));
            ooo.setCreateTime(new Date());
            orderRebateMapper.insert(ooo);

            // 设置回款金额
            orderRebate.setMoney(new BigDecimal(money));
        }
        // 修改回款订单信息（状态、服务商ID，或有金额修改）
        orderRebate.setStatus(EnumOrderRebateStatus.inTheRebate.getValue());
        orderRebate.setServiceProviderId(serviceProviderId);
        orderRebate.setLockSpId(serviceProviderId);
        orderRebate.setRebateTime(new Date());
        orderRebate.setQrCodeAli(qrCodeAli);
        orderRebate.setQrCodeWx(qrCodeWx);
        orderRebateMapper.updateById(orderRebate);
        // 设置回款金额
        rebateMoney = orderRebate.getMoney();

        try {
            // 系统消息：回款订单消息通知
            MessageInfo message = new MessageInfo();
            message.setUserId(order.getUserId());
            message.setCreateTime(new Date());
            message.setTitle("产品收益提醒");
            message.setContent("您的订单" + order.getOrderNo() + "，已开始收益。请注意确认回款金额"+orderRebate.getMoney()+"元。");
            messageInfoMapper.insert(message);

            // 记录用户二维码获取时间（5分钟内不重复获取）
            if (userQRcodeMap == null) userQRcodeMap = new HashMap<>();
            userQRcodeMap.put(order.getUserId().toString(), new Date());
        } catch (Exception e) {
            e.printStackTrace();
        }

        // 已有符合条件的回款信息
        return new OrderRebateScanVo(code, EnumOrderRebateScanCode.getDesByCode(code), orderRebate.getId(), rebateMoney, qrCodeAli, qrCodeWx);
    }

    @Override
    public List<Map<String, Object>> getOrderOutTime(Date outTime, Integer inTheRebate) {
        return orderRebateMapper.getOrderOutTime(outTime, inTheRebate);
    }

    @Override
    public Integer updateStatusToOutTime(Integer exceptionOrder, Date outTime, Integer inTheRebate) {
        return orderRebateMapper.updateStatusToOutTime(exceptionOrder, outTime, inTheRebate);
    }

    @Override
    public void settlement(String ids) {
        // 生成结算记录
        SettlementLog sLog = new SettlementLog();
        sLog.setStatus(1);
        sLog.setCreateTime(new Date());
        sLog.setAdminId(ShiroKit.getUser().getId());
        settlementLogMapper.insert(sLog);

        // 生成回款记录（设置回款记录的结算ID）
        for (Integer id : Convert.toIntArray(true, Convert.toStrArray(",", ids))) {
            OrderRebate obj = new OrderRebate();
            obj.setId(id);
            obj.setSettlementId(sLog.getId());
            orderRebateMapper.updateById(obj);
        }

        // 服务商ID
        Integer spId = null;
        // 结算金额
        BigDecimal money = new BigDecimal(0);
        List<OrderRebate> list = orderRebateMapper.selectList(new EntityWrapper<OrderRebate>().eq("settlementId", sLog.getId()));
        for (OrderRebate o : list) {
            if (spId == null)
                spId = o.getServiceProviderId();
            money =  money.add(o.getMoney());
        }

        // 完善结算记录信息
        sLog.setSpId(spId);
        sLog.setMoney(money);
        settlementLogMapper.updateById(sLog);
    }
}
