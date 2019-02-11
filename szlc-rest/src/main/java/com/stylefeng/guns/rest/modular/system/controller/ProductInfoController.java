package com.stylefeng.guns.rest.modular.system.controller;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.stylefeng.guns.core.enums.EnumObjStatus;
import com.stylefeng.guns.rest.common.util.ApiJson;
import com.stylefeng.guns.rest.modular.system.model.ProductInfo;
import com.stylefeng.guns.rest.modular.system.model.SetInfo;
import com.stylefeng.guns.rest.modular.system.model.vo.ProductVo;
import com.stylefeng.guns.rest.modular.system.service.IProductInfoService;
import com.stylefeng.guns.rest.modular.system.service.ISetInfoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/product")
public class ProductInfoController {
    private final static Logger log = LoggerFactory.getLogger(ProductInfoController.class);

    @Autowired
    private IProductInfoService productInfoService;

    @Autowired
    private ISetInfoService setinfoService;

    /**
     * 获取产品列表
     */
    @RequestMapping("/list")
    public Object list(String key, Integer desc, Integer min, Integer max, Integer current, Integer size) {
        try {
            Wrapper<ProductInfo> wrapper = new EntityWrapper<ProductInfo>().eq("status", EnumObjStatus.normal.getValue()).and().like("productName", key);
            // 收益率升降序，1降序，0升序，-1不限
            if (desc != null && desc == 1) {
                wrapper.orderBy("annualRate", false);
            } else if (desc != null && desc == 0) {
                wrapper.orderBy("annualRate", true);
            } else {
                wrapper.orderBy("id", false);
            }
            // 起投金额筛选
            wrapper.between("minMoney", min, max);

            // 分页查询
            List<ProductInfo> tempList = productInfoService.selectPage(new Page(current == null ? 1 : current, size == null ? 10 : size), wrapper).getRecords();
            // 封装数据
            List<ProductVo> list = new ArrayList<>();
            for (ProductInfo obj : tempList) {
                // 封装数据集
                list.add(new ProductVo(obj.getId(), obj.getProductName(), obj.getProductNo(), obj.getAnnualRate(), obj.getMinMoney()));
            }
            return ApiJson.returnOK(list);
        } catch (Exception e) {
            e.printStackTrace();
            log.error(ApiJson.msgException, e);
        }
        return ApiJson.returnNG();
    }

    /**
     * 获取产品详情
     */
    @RequestMapping("/info")
    public Object info(Integer id) {
        try {
            // 查询产品详情
            ProductInfo pro = productInfoService.selectById(id);

            // 查询收益/取出规则
            String parameName = "keyStr";
            String incomeRulesUrl = "incomeRulesUrl";
            String takeoutRulesUrl = "takeoutRulesUrl";
            List<SetInfo> setList = setinfoService.selectList(new EntityWrapper<SetInfo>().eq(parameName, incomeRulesUrl).or().eq(parameName, takeoutRulesUrl));
            for (SetInfo set : setList) {
                if (incomeRulesUrl.equals(set.getKeyStr()))
                    incomeRulesUrl = set.getValueStr();
                if (takeoutRulesUrl.equals(set.getKeyStr()))
                    takeoutRulesUrl = set.getValueStr();
            }
            return ApiJson.returnOK(new ProductVo(pro, incomeRulesUrl, takeoutRulesUrl));
        } catch (Exception e) {
            e.printStackTrace();
            log.error(ApiJson.msgException, e);
        }
        return ApiJson.returnNG();
    }

}
