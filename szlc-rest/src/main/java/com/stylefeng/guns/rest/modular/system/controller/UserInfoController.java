package com.stylefeng.guns.rest.modular.system.controller;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.juhe.JuheApi;
import com.stylefeng.guns.core.enums.EnumObjStatus;
import com.stylefeng.guns.core.util.MD5Util;
import com.stylefeng.guns.rest.common.util.ApiJson;
import com.stylefeng.guns.rest.config.properties.AppProperties;
import com.stylefeng.guns.rest.modular.system.model.UserInfo;
import com.stylefeng.guns.rest.modular.system.model.UserSet;
import com.stylefeng.guns.rest.modular.system.service.IUserInfoService;
import com.stylefeng.guns.rest.modular.system.service.IUserSetService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

@RestController
@RequestMapping("/api/user")
public class UserInfoController {
    private final static Logger log = LoggerFactory.getLogger(UserInfoController.class);

    @Autowired
    private AppProperties appProperties;

    @Autowired
    private IUserInfoService userInfoService;

    @Autowired
    private IUserSetService userSetService;

    /**
     * 注册
     */
    @RequestMapping("/regist")
    public Object regist(String phone, String userPwd, String realName, String idCard, String handPwd) {
        try {
            UserInfo user = userInfoService.selectOne(new EntityWrapper<UserInfo>().eq("phone", phone));
            // 判断用户是否注册
            if (user != null) {
                return ApiJson.returnNG(ApiJson.eMsgUserPhoneExist);
            } else {
                // 验证身份证号
                boolean flag = JuheApi.idCard(realName, idCard);
                if (!flag) {
                    return ApiJson.returnNG(ApiJson.eMsgUserIdCardError);
                }

                // 封装用户信息
                user = new UserInfo();
                user.setAccount(phone);// 默认手机号为登录账号
                user.setPhone(phone);
                user.setNickName("用户");
                user.setUserPwd(MD5Util.encrypt(userPwd));
                user.setRealName(realName);
                user.setIdCard(idCard);
                user.setHandPwd(handPwd);
                user.setStatus(EnumObjStatus.normal.getValue());
                user.setCreateTime(new Date());

                // 封装用户设置信息
                UserSet userSet = new UserSet();
                // 默认强制开启
                userSet.setIsReceiveMsg(1);
                // 存入数据库
                userInfoService.insert(user, userSet);

                user = setUserVo(user);

                return ApiJson.returnOK(user, ApiJson.sMsgUserRegistSuccess);
            }
        } catch (Exception e) {
            e.printStackTrace();
            log.error(ApiJson.msgException, e);
        }
        return ApiJson.returnNG();
    }

    /**
     * 登录
     */
    @RequestMapping("/login")
    public Object login(String phone, String userPwd, String handPwd) {
        try {
            // 查询用户信息
            UserInfo user = userInfoService.selectOne(new EntityWrapper<UserInfo>().eq("status", EnumObjStatus.normal.getValue())
                    .eq("phone", phone)
                    .andNew().eq("userPwd", MD5Util.encrypt(userPwd == null ? "" : userPwd)).or().eq("handPwd", handPwd));

            // 封装用户信息
            if (user != null) {
                user = setUserVo(user);

                return ApiJson.returnOK(user);
            } else {
                return ApiJson.returnNG(ApiJson.eMsgUserLoginError);
            }
        } catch (Exception e) {
            e.printStackTrace();
            log.error(ApiJson.msgException, e);
        }
        return ApiJson.returnNG();
    }

    /**
     * 忘记密码
     */
    @RequestMapping("/resetPwd")
    public Object resetPwd(String phone, String userPwd) {
        try {
            UserInfo user = userInfoService.selectOne(new EntityWrapper<UserInfo>().and().eq("phone", phone));
            // 判断用户信息
            if (user != null) {
                user.setUserPwd(MD5Util.encrypt(userPwd));
                // 修改密码
                userInfoService.updateById(user);

                return ApiJson.returnOK();
            } else {
                return ApiJson.returnNG(ApiJson.eMsgUserPhoneError);
            }
        } catch (Exception e) {
            e.printStackTrace();
            log.error(ApiJson.msgException, e);
        }
        return ApiJson.returnNG();
    }

    /**
     * 用户信息
     */
    @RequestMapping("/info")
    public Object info(Integer id) {
        try {
            // 查询用户信息
            UserInfo user = userInfoService.selectById(id);

            // 封装用户信息
            if (user != null) {
                user.setAvatar(appProperties.getPictureServerAddress() + user.getAvatar());
                user = setUserVo(user);

                return ApiJson.returnOK(user);
            } else {
                return ApiJson.returnNG(ApiJson.eMsgUserInfoNotExist);
            }
        } catch (Exception e) {
            e.printStackTrace();
            log.error(ApiJson.msgException, e);
        }
        return ApiJson.returnNG();
    }

    /**
     * 修改用户信息
     */
    @RequestMapping("/update")
    public Object update(String id, String nickName, String avatar, String realName, String idCard) {
        try {
            UserInfo user = userInfoService.selectById(id);
            // 判断用户信息
            if (user != null) {
                if (nickName != null) {
                    user.setNickName(nickName);
                }
                if (avatar != null) {
                    user.setAvatar(avatar);
                }
                if (realName != null) {
                    user.setRealName(realName);
                }
                if (idCard != null) {
                    user.setIdCard(idCard);
                }

                // 修改信息
                userInfoService.updateById(user);

                return ApiJson.returnOK();
            } else {
                return ApiJson.returnNG(ApiJson.eMsgUserInfoNotExist);
            }
        } catch (Exception e) {
            e.printStackTrace();
            log.error(ApiJson.msgException, e);
        }
        return ApiJson.returnNG();
    }

    /**
     * 安全设置
     */
    @RequestMapping("/securitySet")
    public Object securitySet(Integer id, String phone, String oldPwd, String newPwd, String handPwd) {
        try {
            UserInfo user = userInfoService.selectById(id);
            // 判断用户信息
            if (user != null) {
                // 设置修改条件
                user = new UserInfo();
                user.setId(id);

                // 验证手机号
                if (phone != null && !"".equals(phone.trim())) {
                    user.setPhone(phone);
                }
                // 验证密码
                if (oldPwd != null && newPwd != null && !"".equals(newPwd.trim())) {
                    if (!user.getUserPwd().equals(MD5Util.encrypt(oldPwd))) {
                        return ApiJson.returnNG(ApiJson.eMsgUserPwdError);
                    }
                    user.setUserPwd(MD5Util.encrypt(newPwd));
                }
                // 验证手势密码
                if (handPwd != null) {
                    user.setHandPwd(handPwd);
                }

                // 修改信息
                userInfoService.updateById(user);

                return ApiJson.returnOK();
            } else {
                return ApiJson.returnNG(ApiJson.eMsgUserInfoNotExist);
            }
        } catch (Exception e) {
            e.printStackTrace();
            log.error(ApiJson.msgException, e);
        }
        return ApiJson.returnNG();
    }

    /**
     * 绑定邮箱
     */
    @RequestMapping("/bindEmail")
    public Object bindEmail(Integer id, String email) {
        try {
            UserInfo user = userInfoService.selectById(id);
            // 判断用户信息
            if (user != null) {
//                // 生成随机验证码
//                String code = "";
//                for (int i = 0; i < 4; i++) {
//                    code += new Random().nextInt(10);
//                }
//                code = "1234";
//
//                // 短信验证码
//                EmailCode ec = new EmailCode();
//                ec.setUserId(id);
//                ec.setEmail(email);
//                ec.setCode(code);
//                ec.setCreateTime(new Date());
//
//                // 保存验证信息
//                emailCodeService.insert(ec);

                // 修改用户邮箱（直接绑定跳过验证）
                user.setEmail(email);
                userInfoService.updateAllColumnById(user);

                return ApiJson.returnOK();
            } else {
                return ApiJson.returnNG(ApiJson.eMsgUserInfoNotExist);
            }
        } catch (Exception e) {
            e.printStackTrace();
            log.error(ApiJson.msgException, e);
        }
        return ApiJson.returnNG();
    }

    /**
     * 封装用户数据（个人中心）
     */
    public UserInfo setUserVo(UserInfo user) {
        UserInfo obj = new UserInfo();
        obj.setId(user.getId());
        obj.setPhone(user.getPhone());
        obj.setAccount(user.getAccount());
        obj.setNickName(user.getNickName());
        obj.setAvatar(user.getAvatar());
        obj.setRealName(user.getRealName());
        obj.setIdCard(user.getIdCard());
        obj.setCreditScore(user.getCreditScore());
        obj.setHandPwd(user.getHandPwd());
        return obj;
    }
}


