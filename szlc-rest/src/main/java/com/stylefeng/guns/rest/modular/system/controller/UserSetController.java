package com.stylefeng.guns.rest.modular.system.controller;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.stylefeng.guns.rest.common.util.ApiJson;
import com.stylefeng.guns.rest.config.properties.AppProperties;
import com.stylefeng.guns.rest.modular.system.model.SetInfo;
import com.stylefeng.guns.rest.modular.system.model.UserSet;
import com.stylefeng.guns.rest.modular.system.model.vo.UserSetVo;
import com.stylefeng.guns.rest.modular.system.service.ISetInfoService;
import com.stylefeng.guns.rest.modular.system.service.IUserSetService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/userSet")
public class UserSetController {
    private final static Logger log = LoggerFactory.getLogger(UserSetController.class);

    @Autowired
    private AppProperties appProperties;

    @Autowired
    private IUserSetService userSetService;

    @Autowired
    private ISetInfoService setinfoService;

    /**
     * 获取用户设置信息
     */
    @RequestMapping("/info")
    public Object info(Integer userId) {
        try {
            // 查询用户设置
            UserSet us = userSetService.selectOne(new EntityWrapper<UserSet>().eq("userId", userId));
            // 查询系统设置
            List<SetInfo> setList = setinfoService.selectList(new EntityWrapper<SetInfo>().eq("keyStr", "userAgreementH5").or().eq("keyStr", "aboutUsH5"));

            // 封装数据
            UserSetVo vo = new UserSetVo();
            if (us != null) {
                vo.setIsReceiveMsg(us.getIsReceiveMsg());
            }
            if (setList != null) {
                for (SetInfo s : setList) {
                    if ("aboutUsH5".equals(s.getKeyStr())){
                        vo.setAboutUsH5(s.getValueStr());
                    }
                    if ("userAgreementH5".equals(s.getKeyStr())){
                        vo.setUserAgreementH5(s.getValueStr());
                    }
                }
            }

            // 返回数据
            return ApiJson.returnOK(vo);
        } catch (Exception e) {
            e.printStackTrace();
            log.error(ApiJson.msgException, e);
        }
        return ApiJson.returnNG();
    }

    /**
     * 获取系统公共设置信息
     */
    @RequestMapping("/system")
    public Object system() {
        try {
            // 查询系统设置
            List<SetInfo> setList = setinfoService.selectList(null);

            // 封装返回数据
            Map<String, String> map = new HashMap<>();
            if (setList != null) {
                for (SetInfo s : setList) {
                    if ("aboutUsH5".equals(s.getKeyStr())){
                        map.put("aboutUsH5", s.getValueStr());
                    }
                    if ("userAgreementH5".equals(s.getKeyStr())){
                        map.put("userAgreementH5", s.getValueStr());
                    }
                    if ("incomeRulesUrl".equals(s.getKeyStr())){
                        map.put("incomeRulesUrl", s.getValueStr());
                    }
                    if ("takeoutRulesUrl".equals(s.getKeyStr())){
                        map.put("takeoutRulesUrl", s.getValueStr());
                    }
                    if ("tradingRulesUrl".equals(s.getKeyStr())){
                        map.put("tradingRulesUrl", s.getValueStr());
                    }
                }
            }
            // 图片服务器地址
            map.put("pictureServerAddress", appProperties.getPictureServerAddress());

            // 返回数据
            return ApiJson.returnOK(map);
        } catch (Exception e) {
            e.printStackTrace();
            log.error(ApiJson.msgException, e);
        }
        return ApiJson.returnNG();
    }

    /**
     * 修改用户设置
     */
    @RequestMapping("/update")
    public Object update(Integer userId, Integer isReceiveMsg) {
        try {
            // 用户设置
            UserSet us = new UserSet();
            us.setIsReceiveMsg(isReceiveMsg);
            userSetService.update(us, new EntityWrapper<UserSet>().eq("userId", userId));

            // 返回数据
            return ApiJson.returnOK();
        } catch (Exception e) {
            e.printStackTrace();
            log.error(ApiJson.msgException, e);
        }
        return ApiJson.returnNG();
    }

}
