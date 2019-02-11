package com.stylefeng.guns.rest.modular.system.controller;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.stylefeng.guns.core.base.controller.BaseController;
import com.stylefeng.guns.core.enums.EnumOrderRebateStatus;
import com.stylefeng.guns.core.enums.EnumOrderStatus;
import com.stylefeng.guns.core.util.DateUtil;
import com.stylefeng.guns.core.util.ToolUtil;
import com.stylefeng.guns.rest.common.util.ApiJson;
import com.stylefeng.guns.rest.modular.system.model.OrderInfo;
import com.stylefeng.guns.rest.modular.system.model.OrderRebate;
import com.stylefeng.guns.rest.modular.system.model.ProductInfo;
import com.stylefeng.guns.rest.modular.system.model.UserInfo;
import com.stylefeng.guns.rest.modular.system.model.vo.OrderVo;
import com.stylefeng.guns.rest.modular.system.service.IOrderInfoService;
import com.stylefeng.guns.rest.modular.system.service.IOrderRebateService;
import com.stylefeng.guns.rest.modular.system.service.IProductInfoService;
import com.stylefeng.guns.rest.modular.system.service.IUserInfoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.*;

@RestController
@RequestMapping("/api/order")
public class OrderInfoController extends BaseController {
    private final static Logger log = LoggerFactory.getLogger(OrderInfoController.class);

    @Autowired
    private IProductInfoService productInfoService;

    @Autowired
    private IOrderInfoService orderInfoService;

    @Autowired
    private IOrderRebateService orderRebateService;

    @Autowired
    private IUserInfoService userInfoService;

    /**
     * 个人中心
     */
    @RequestMapping("/zone")
    public Object zone(Integer userId) {
        try {
            // 获取用户信息
            UserInfo user = userInfoService.selectById(userId);

            // 需要查询理财订单状态（待收益、收益中、回款异常）
            Integer[] status = new Integer[]{EnumOrderStatus.waitRebate.getValue(), EnumOrderStatus.inTheRebate.getValue(), EnumOrderStatus.exceptionOrder.getValue()};
            // 查询用户理财资金
            Map<String, Object> map = orderInfoService.selectMap(new EntityWrapper<OrderInfo>()
                    .setSqlSelect("IFNULL(SUM(money+incomeAmount),0) AS sumMoney, IFNULL(SUM(money),0) AS buyMoney, IFNULL(SUM(incomeAmount),0) AS incomeAmount")
                    .in("status", status).eq("userId", userId));

            // 查询用户理财订单
            List<OrderVo> tempList = orderInfoService.selectOpListByUserAndStatus(userId, null, status, null, null);
            // 封装订单数据
            List<OrderVo> list = new ArrayList<>();
            for (OrderVo obj : tempList) {
                list.add(new OrderVo(obj.getId(), obj.getProductId(), obj.getProductName(), obj.getProductNo(), obj.getMoney(), obj.getIncomeAmount(), obj.getStatus()));
            }

            map.put("list", list);
            map.put("avatar", user != null ? user.getAvatar() : null);
            return ApiJson.returnOK(map);
        } catch (Exception e) {
            e.printStackTrace();
            log.error(ApiJson.msgException, e);
        }
        return ApiJson.returnNG();
    }

    /**
     * 订单信息
     */
    @RequestMapping("/info")
    public Object info(Integer productId, Integer userId) {
        try {
            // 封装集合
            Map<String, Object> map = new HashMap<>();

            // 产品信息
            ProductInfo pro = productInfoService.selectById(productId);

            // 需要查询理财订单状态（待收益、收益中、回款成功、回款异常）
            Integer[] status = new Integer[]{EnumOrderStatus.waitRebate.getValue(),EnumOrderStatus.inTheRebate.getValue(), EnumOrderStatus.rebateSuccessful.getValue(), EnumOrderStatus.exceptionOrder.getValue()};
            List<OrderInfo> orderList = orderInfoService.selectList(new EntityWrapper<OrderInfo>().eq("productId", productId).eq("userId", userId).in("status", status));
            // 总资产
            BigDecimal sumMoney = new BigDecimal(0);
            // 收益本金
            BigDecimal incomeMoney = new BigDecimal(0);
            // 未收益本金
            BigDecimal waitIncomeMoney = new BigDecimal(0);
            // 持有收益
            BigDecimal incomeAmount = new BigDecimal(0);
            // 起息时间、待起息时间
            Date incomeTime = null, waitIncomeTime = null;
            for (OrderInfo order : orderList) {
                // 总资产 = 购买金额 + 收益金额
                sumMoney = sumMoney.add(order.getMoney().add(order.getIncomeAmount()));
                if (order.getStatus() != EnumOrderStatus.waitRebate.getValue()) {
                    // 收益本金累加
                    incomeMoney = incomeMoney.add(order.getMoney());
                    // 收益起息时间
                    incomeTime = order.getIncomeTime();
                } else {
                    // 未收益本金累加
                    waitIncomeMoney = waitIncomeMoney.add(order.getMoney());
                    // 未收益起息时间
                    waitIncomeTime = order.getIncomeTime();
                }
                // 持有收益累加
                incomeAmount = incomeAmount.add(order.getIncomeAmount());
            }
            // 封装数据
            map.put("productName", pro.getProductName());
            map.put("productNo", pro.getProductNo());
            map.put("annualRate", pro.getAnnualRate());
            map.put("sumMoney", sumMoney);
            map.put("incomeMoney", incomeMoney);
            map.put("waitIncomeMoney", waitIncomeMoney);
            map.put("incomeAmount", incomeAmount);
            map.put("incomeTime", incomeTime);
            map.put("waitIncomeTime", waitIncomeTime);

            return ApiJson.returnOK(map);
        } catch (Exception e) {
            e.printStackTrace();
            log.error(ApiJson.msgException, e);
        }
        return ApiJson.returnNG();
    }

    /**
     * 下单
     */
    @RequestMapping("/create")
    public Object create(Integer productId, Integer userId, Double money) {
        try {
            ProductInfo pro = productInfoService.selectById(productId);
            if (pro != null) {
                // 设置订单信息
                OrderInfo order = new OrderInfo();
                order.setOrderNo(DateUtil.formatDate(new Date(), "yyMMddHHmmss") + ToolUtil.getRandomNumString(4));
                order.setStatus(EnumOrderStatus.waitPay.getValue());
                order.setProductId(productId);
                order.setUserId(userId);
                order.setMoney(new BigDecimal(money));
                order.setIncomeAmount(new BigDecimal(pro.getAnnualRate() * money / 100).setScale(2,BigDecimal.ROUND_DOWN));
                order.setAnnualRate(pro.getAnnualRate());
                order.setCreateTime(new Date());

                // 查询理财订单中回款数据（未确认状态）：待回款、回款中
                Integer[] status = new Integer[]{EnumOrderRebateStatus.waitRebate.getValue(), EnumOrderRebateStatus.inTheRebate.getValue()};
                List<OrderRebate> rebateList = orderRebateService.selectList(new EntityWrapper<OrderRebate>().eq(false,"id", order.getId()).in("status", status).groupBy("id"));
                if (rebateList != null && rebateList.size() >= 1) {
                    // 获取理财订单回款最后的时间，追加24小时
                    order.setIncomeTime(DateUtil.getDateAddHour(rebateList.get(0).getRebateTime(), 24));
                } else {
                    order.setIncomeTime(new Date());
                }

                orderInfoService.insert(order);

                return ApiJson.returnOK(order);
            } else {
                return ApiJson.returnNG(ApiJson.eMsgProductInfoNotExist);
            }
        } catch (Exception e) {
            e.printStackTrace();
            log.error(ApiJson.msgException, e);
        }
        return ApiJson.returnNG();
    }

    /**
     * 收益明细
     */
    @RequestMapping("/income")
    public Object income(Integer userId, Integer productId, Integer current, Integer size) {
        try {
            Map<String, Object> map = new HashMap<>();
            // 持有收益
            BigDecimal incomeAmount = new BigDecimal(0);
            // 累计收益
            BigDecimal addsMoney = new BigDecimal(0);

            // 需要查询理财订单状态（回款成功（确认到账））
            Integer[] status = new Integer[]{EnumOrderStatus.rebateSuccessful.getValue()};

            // 查询用户理财订单
            List<OrderVo> tempList = orderInfoService.selectOpListByUserAndStatus(userId, productId, status, current, size);
            // 封装订单数据
            List<OrderVo> list = new ArrayList<>();
            for (OrderVo obj : tempList) {
                list.add(new OrderVo(obj.getProductName(), obj.getIncomeAmount(), obj.getCreateTime()));
                // 累计持有收益
                addsMoney.add(obj.getIncomeAmount());
            }

            // 封装数据
            map.put("incomeAmount", incomeAmount);
            map.put("addsMoney", addsMoney);
            map.put("list", list);

            return ApiJson.returnOK(map);
        } catch (Exception e) {
            e.printStackTrace();
            log.error(ApiJson.msgException, e);
        }
        return ApiJson.returnNG();
    }

    /**
     * 购买记录
     */
    @RequestMapping("/tradingRecord")
    public Object tradingRecord(Integer userId, Integer productId, Integer current, Integer size) {
        try {
            // 需要查询理财订单状态（待收益、收益中、回款成功、回款异常）
            Integer[] status = new Integer[]{EnumOrderStatus.waitRebate.getValue(),EnumOrderStatus.inTheRebate.getValue(), EnumOrderStatus.rebateSuccessful.getValue(), EnumOrderStatus.exceptionOrder.getValue()};

            // 查询用户理财订单
            List<OrderVo> tempList = orderInfoService.selectOpListByUserAndStatus(userId, productId, status, current, size);
            // 封装订单数据
            List<OrderVo> list = new ArrayList<>();
            for (OrderVo obj : tempList) {
                list.add(new OrderVo(obj.getMoney(), obj.getProductName(), obj.getCreateTime()));
            }

            return ApiJson.returnOK(list);
        } catch (Exception e) {
            e.printStackTrace();
            log.error(ApiJson.msgException, e);
        }
        return ApiJson.returnNG();
    }

    /**
     * 回款管理
     */
    @RequestMapping("/rebateManage")
    public Object rebateManage(Integer userId, Integer productId, Integer current, Integer size) {
        try {
            // 需要查询理财订单状态（待收益、收益中、回款成功、回款异常）
            Integer[] status = new Integer[]{EnumOrderStatus.waitRebate.getValue(),EnumOrderStatus.inTheRebate.getValue(), EnumOrderStatus.rebateSuccessful.getValue(), EnumOrderStatus.exceptionOrder.getValue()};

            // 查询用户理财订单
            List<OrderVo> tempList = orderInfoService.selectOpListByUserAndStatus(userId, productId, status, current, size);
            // 封装订单数据
            List<OrderVo> list = new ArrayList<>();
            for (OrderVo obj : tempList) {
                List<OrderRebate> rebateList = orderRebateService.selectList(new EntityWrapper<OrderRebate>().eq("orderId", obj.getId()));

                list.add(new OrderVo(obj.getId(), obj.getProductName(), obj.getIncomeAmount(), obj.getStatus(), rebateList));
            }

            return ApiJson.returnOK(list);
        } catch (Exception e) {
            e.printStackTrace();
            log.error(ApiJson.msgException, e);
        }
        return ApiJson.returnNG();
    }

    /**
     * 回款确认
     */
    @RequestMapping("/rebateConfirm")
    public Object rebateConfirm(Integer rebateId) {
        try {
            // 回款信息，设置确认回款
            OrderRebate orderRebate = new OrderRebate();
            orderRebate.setId(rebateId);
            orderRebate.setStatus(EnumOrderRebateStatus.userConfirm.getValue());
            orderRebate.setConfirmTime(new Date());
            // 回款确认操作
            boolean flag = orderRebateService.updateConfimRebate(orderRebate);
            if (flag) {
                return ApiJson.returnOK();
            }
        } catch (Exception e) {
            e.printStackTrace();
            log.error(ApiJson.msgException, e);
        }
        return ApiJson.returnNG();
    }

}
