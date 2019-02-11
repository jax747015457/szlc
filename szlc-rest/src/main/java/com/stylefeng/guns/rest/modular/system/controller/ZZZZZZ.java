package com.stylefeng.guns.rest.modular.system.controller;

import com.juhe.JuheApi;
import com.stylefeng.guns.rest.common.util.ApiJson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ZZZZZZ {
    private final static Logger log = LoggerFactory.getLogger(ZZZZZZ.class);

    @RequestMapping("api")
    public Object api() {
        return ApiJson.returnOK();
    }

    @RequestMapping("idCard")
    public Object idCard(String realname, String idcard) {
         try {
             boolean flag = JuheApi.idCard("汤海", "352230199309202000");

             return ApiJson.returnOK(flag);
        } catch (Exception e) {
            e.printStackTrace();
             log.error(ApiJson.msgException, e);
        }
        return ApiJson.returnNG();
    }

}
