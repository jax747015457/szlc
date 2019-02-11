package com.stylefeng.guns.modular.system.controller;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.stylefeng.guns.core.base.controller.BaseController;
import com.stylefeng.guns.core.enums.EnumObjDelete;
import com.stylefeng.guns.core.log.LogObjectHolder;
import com.stylefeng.guns.core.util.DateUtil;
import com.stylefeng.guns.core.util.ToolUtil;
import com.stylefeng.guns.modular.system.model.ServiceProvider;
import com.stylefeng.guns.modular.system.service.IServiceProviderService;
import com.stylefeng.guns.modular.system.warpper.ServiceProviderWarpper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.Date;
import java.util.UUID;

/**
 * 服务商控制器
 * @Date 2018-06-28 17:34:53
 */
@Controller
@RequestMapping("/serviceProvider")
public class ServiceProviderController extends BaseController {

    private String PREFIX = "/system/serviceProvider/";

    @Autowired
    private IServiceProviderService serviceProviderService;

    /**
     * 跳转到服务商首页
     */
    @RequestMapping("")
    public String index() {
        return PREFIX + "serviceProvider.html";
    }

    /**
     * 跳转到添加服务商
     */
    @RequestMapping("/serviceProvider_add")
    public String serviceProviderAdd() {
        return PREFIX + "serviceProvider_add.html";
    }

    /**
     * 跳转到修改服务商
     */
    @RequestMapping("/serviceProvider_update/{serviceProviderId}")
    public String serviceProviderUpdate(@PathVariable Integer serviceProviderId, Model model) {
        ServiceProvider serviceProvider = serviceProviderService.selectById(serviceProviderId);
        model.addAttribute("item",serviceProvider);
        LogObjectHolder.me().set(serviceProvider);
        return PREFIX + "serviceProvider_edit.html";
    }

    /**
     * 获取服务商列表
     */
    @RequestMapping(value = "/list")
    @ResponseBody
    public Object list(String condition, String beginTime, String endTime) {
        Wrapper wrapper = new EntityWrapper<ServiceProvider>().eq("isDelete", EnumObjDelete.normal.getValue());
        if (ToolUtil.isNotEmpty(condition)) {
            wrapper.andNew().like("serverName", condition);
            wrapper.or().like("serverIp", condition);
        }
        if (ToolUtil.isNotEmpty(beginTime) && ToolUtil.isNotEmpty(endTime)) {
            wrapper.andNew().between("createTime", beginTime, endTime);
        }
        return new ServiceProviderWarpper(serviceProviderService.selectMaps(wrapper)).warp();
    }

    /**
     * 新增服务商
     */
    @RequestMapping(value = "/add")
    @ResponseBody
    public Object add(ServiceProvider serviceProvider) {
        if (serviceProvider != null) {
            serviceProvider.setIsDelete(EnumObjDelete.normal.getValue());
            serviceProvider.setCreateTime(new Date());
        }
        serviceProviderService.insert(serviceProvider);
        return SUCCESS_TIP;
    }

    /**
     * 删除服务商
     */
    @RequestMapping(value = "/delete")
    @ResponseBody
    public Object delete(@RequestParam Integer serviceProviderId) {
        ServiceProvider serviceProvider = new ServiceProvider();
        serviceProvider.setId(serviceProviderId);
        serviceProvider.setIsDelete(EnumObjDelete.deleted.getValue());
        serviceProviderService.updateById(serviceProvider);
        return SUCCESS_TIP;
    }

    /**
     * 修改服务商
     */
    @RequestMapping(value = "/update")
    @ResponseBody
    public Object update(ServiceProvider serviceProvider) {
        serviceProviderService.updateById(serviceProvider);
        return SUCCESS_TIP;
    }

    /**
     * 服务商详情
     */
    @RequestMapping(value = "/detail/{serviceProviderId}")
    @ResponseBody
    public Object detail(@PathVariable("serviceProviderId") Integer serviceProviderId) {
        return serviceProviderService.selectById(serviceProviderId);
    }


    /**
     * 更新下载商户密钥
     */
    @RequestMapping(value = "/updateDownSecretKey")
    @ResponseBody
    public Object updateDownSecretKey(Integer id) {
        // 生成新的商户密钥
        String newKey = UUID.randomUUID().toString() + UUID.randomUUID().toString();
        // 设置商户密钥
        ServiceProvider serviceProvider = new ServiceProvider();
        serviceProvider.setId(id);
        serviceProvider.setSecretKey(newKey);
        // 更新
        serviceProviderService.updateById(serviceProvider);

        // 下载文件
        exportFile(super.getHttpServletResponse(), "商户密钥"+DateUtil.formatDate(new Date(), "yyyyMMddHHmmss"), ".txt", "商户密钥：" + newKey);

        return SUCCESS_TIP;
    }
    /**
     * 下载文件
     */
    public static void exportFile(HttpServletResponse response, String fileName, String suffix, String data) {
        try {
            //设置响应头，输出文件
            response.setContentType("application/octet-stream;charset=ISO8859-1");
            fileName = new String(fileName.getBytes("gb2312"),"ISO8859-1");
            response.setHeader("Content-Disposition", "attachment;filename="+fileName+URLEncoder.encode(suffix, "ISO8859-1"));

            BufferedOutputStream buff = null;
            ServletOutputStream outStr = null;
            outStr = response.getOutputStream();
            buff = new BufferedOutputStream(outStr);
            buff.write(data.getBytes("UTF-8"));
            buff.flush();
            buff.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
