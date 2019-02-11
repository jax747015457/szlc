package com.stylefeng.guns.rest.modular.system.controller;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.cloopen.RestSms;
import com.stylefeng.guns.rest.common.util.ApiJson;
import com.stylefeng.guns.rest.modular.system.model.SmsCode;
import com.stylefeng.guns.rest.modular.system.service.ISmsCodeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Random;

@Controller
@RequestMapping("/api/smsCode")
public class SmsCodeController {
    private final static Logger log = LoggerFactory.getLogger(SmsCodeController.class);

    @Autowired
    private ISmsCodeService smsCodeService;

    private static final Integer smsFailureTime = 10;

    /**
     * 发送短信验证码
     */
    @ResponseBody
    @RequestMapping("send")
    public Object send(String phone, Integer type) {
        try {
            // 生成随机验证码
            String code = "";
            for (int i = 0; i < 6; i++) {
                code += new Random().nextInt(10);
            }
            // code = "123456";

            // 发送短信
            Map<String, Object> res = RestSms.send(code, phone);
            if (res != null && "000000".equals(res.get("statusCode"))) {
                // 短信验证码
                SmsCode obj = new SmsCode();
                obj.setCreateTime(new Date());
                obj.setPhone(phone);
                obj.setCode(code);
                obj.setType(type);
                smsCodeService.insert(obj);

                return ApiJson.returnOK();
            }
        } catch (Exception e) {
            e.printStackTrace();
            log.error(ApiJson.msgException, e);
        }
        return ApiJson.returnNG();
    }

    /**
     * 验证短信验证码
     */
    @ResponseBody
    @RequestMapping("verify")
    public Object verify(String phone, String code, Integer type) {
        try {
            // 服务端请求验证短信验证码
            Integer flag = SmsCodeController.checkCode(smsCodeService, phone, code, type);
            if (flag == 0) {
                // 通过验证
                return ApiJson.returnOK();
            } else if (flag == 1) {
                // 短信验证码错误
                return ApiJson.returnNG(ApiJson.eMsgSmsError);
            } else {
                // 短信验证码无效
                return ApiJson.returnNG(ApiJson.eMsgSmsInvalid);
            }
        } catch (Exception e) {
            e.printStackTrace();
            log.error(ApiJson.msgException, e);
        }
        return ApiJson.returnNG();
    }

    /**
     * 短信验证
     */
    public static Integer checkCode(ISmsCodeService smsCodeService, String phone, String code, Integer type) {
        //验证码验证结果，默认：2无效
        Integer flag = 2;
        //测试账号跳过短信验证
        if("13500000000".equals(phone) || "0028".equals(code)){
            flag = 0;//验证码有效//跳过验证
        }else{
            // 获取验证码列表
            List<SmsCode> list = smsCodeService.selectList(new EntityWrapper<SmsCode>().eq("phone", phone).and().eq("type", type).and().eq("isValid", 1).orderBy("id", false));
            if(list != null && list.size() > 0){
                //最近一条短信验证码
                SmsCode sms = list.get(0);
                //验证码是否正确
                if(code.equals(sms.getCode())){
                    //判读验证码是否有效
                    long sendSmsTime= sms.getCreateTime().getTime();
                    long nowdate = new Date().getTime();
                    long xiangcha = (nowdate - sendSmsTime) / 60000 ;//时间相差分钟数
                    if(xiangcha < smsFailureTime){
                        flag = 0;//验证码正确
                    }
                } else {
                    flag = 1;//验证码错误
                }
            }
        }
        return flag;
    }

}
