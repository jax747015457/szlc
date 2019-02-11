package com.stylefeng.guns.rest.modular.system.controller;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.stylefeng.guns.rest.common.util.ApiJson;
import com.stylefeng.guns.rest.modular.system.model.CreditScore;
import com.stylefeng.guns.rest.modular.system.model.vo.CreditScoreVo;
import com.stylefeng.guns.rest.modular.system.service.ICreditScoreService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/creditScore")
public class CreditScoreController {
    private final static Logger log = LoggerFactory.getLogger(CreditScoreController.class);

    @Autowired
    private ICreditScoreService creditScoreService;

    /**
     * 获取信用分
     */
    @RequestMapping("/list")
    public Object list(Integer userId, Integer current, Integer size) {
         try {
             // 分页查询
             List<CreditScore> tempList = creditScoreService.selectPage(new Page(current == null ? 1 : current, size == null ? 10 : size), new EntityWrapper<CreditScore>().eq("userId", userId).orderBy("id", false)).getRecords();
             List<CreditScoreVo> list = new ArrayList<>();
             // 封装数据
             for (CreditScore cs : tempList) {
                 CreditScoreVo vo = new CreditScoreVo();
                 vo.setSources(cs.getSources());
                 vo.setScore(cs.getScore());
                 vo.setType(cs.getType());
                 vo.setCreateTime(cs.getCreateTime());

                 list.add(vo);
             }

             return ApiJson.returnOK(list);
        } catch (Exception e) {
            e.printStackTrace();
             log.error(ApiJson.msgException, e);
        }
        return ApiJson.returnNG();
    }

}
