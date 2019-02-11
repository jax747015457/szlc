package com.stylefeng.guns.modular.system.controller;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.stylefeng.guns.core.base.controller.BaseController;
import com.stylefeng.guns.core.util.DateUtil;
import com.stylefeng.guns.modular.system.model.UserInfo;
import com.stylefeng.guns.modular.system.service.INoticeService;
import com.stylefeng.guns.modular.system.service.IOrderInfoService;
import com.stylefeng.guns.modular.system.service.IUserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 总览信息
 *
 * @author fengshuonan
 * @Date 2017年3月4日23:05:54
 */
@Controller
@RequestMapping("/blackboard")
public class BlackboardController extends BaseController {

    @Autowired
    private INoticeService noticeService;

    @Autowired
    private IUserInfoService userInfoService;

    @Autowired
    private IOrderInfoService orderInfoService;

    /**
     * 跳转到黑板
     */
    @RequestMapping("")
    public String blackboard(Model model) {
        // 平台总用户数
        Integer userTotalNum = userInfoService.selectCount(null);
        model.addAttribute("userTotalNum", userTotalNum);
        // 本月新增用户数
        Integer userAddNum = userInfoService.selectCount(new EntityWrapper<UserInfo>().eq("createTime", DateUtil.formatDate(new Date(), "yyyy-MM")));
        model.addAttribute("userAddNum", userAddNum);
        // 平台总订单数
        Integer orderTootalNum = orderInfoService.selectCount(null);
        model.addAttribute("orderTootalNum", orderTootalNum);

        // 过去7天订单量(包含今天/昨天2天数据)
        List<Map<String, Object>> p7d = orderInfoService.past_7_day();
        Integer todayNum = new Integer(p7d.get(0).get("num").toString());
        Integer yestodayNum = new Integer(p7d.get(1).get("num").toString());
        Double addRate_day = 100.00;
        if (yestodayNum != 0) addRate_day = (todayNum - yestodayNum) * 100.00 / yestodayNum;
        String addRate_str = todayNum < yestodayNum ? "下降" : "上升";
        model.addAttribute("todayNum", todayNum);
        model.addAttribute("todayAddRate", "相比昨日" + addRate_str + Math.abs(new BigDecimal(addRate_day).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue()) + "%");

        // 本周/上周总订单量
        Integer weekNum = orderInfoService.now_week();
        Integer pastWeekNum = orderInfoService.past_1_week();
        addRate_day = 100.00;
        if (pastWeekNum != 0) addRate_day = (weekNum - pastWeekNum) * 100.00 / pastWeekNum;
        addRate_str = weekNum < pastWeekNum ? "下降" : "上升";
        model.addAttribute("weekNum", weekNum);
        model.addAttribute("weekAddRate", "相比上周" + addRate_str + Math.abs(new BigDecimal(addRate_day).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue()) + "%");

        // 本月/上月总订单量
        List<Map<String, Object>> p2m = orderInfoService.past_2_month();
        Integer monthNum = new Integer(p2m.get(0).get("num").toString());
        Integer lastMonthNum = new Integer(p2m.get(1).get("num").toString());
        addRate_day = 100.00;
        if (lastMonthNum != 0) addRate_day = (monthNum - lastMonthNum) * 100.00 / lastMonthNum;
        addRate_str = monthNum < lastMonthNum ? "下降" : "上升";
        model.addAttribute("monthNum", monthNum);
        model.addAttribute("monthAddRate", "相比上月" + addRate_str + Math.abs(new BigDecimal(addRate_day).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue()) + "%");

        // 今年/去年总订单量
        List<Map<String, Object>> p2y = orderInfoService.past_2_year();
        Integer yearNum = new Integer(p2y.get(0).get("num").toString());
        Integer lastYearNum = new Integer(p2y.get(1).get("num").toString());
        addRate_day = 100.00;
        if (lastYearNum != 0) addRate_day = (yearNum - lastYearNum) * 100.00 / lastYearNum;
        addRate_str = yearNum < lastYearNum ? "下降" : "上升";
        model.addAttribute("yearNum", yearNum);
        model.addAttribute("yearAddRate", "相比去年" + addRate_str + Math.abs(new BigDecimal(addRate_day).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue()) + "%");

        // 过去7天用户注册数
        List<Map<String, Object>> p7u = userInfoService.past_7_day();
        List<Object> timeList = new ArrayList<Object>();
        List<Object> numList = new ArrayList<Object>();
        for (Map<String, Object> map : p7u) {
            timeList.add("'" + map.get("time") + "'");
            numList.add("'" + map.get("num") + "'");
        }
        model.addAttribute("timeList", timeList);
        model.addAttribute("numList", numList);

        // 过去7天订单量
        List<Object> timeList2 = new ArrayList<Object>();
        List<Object> numList2 = new ArrayList<Object>();
        for (Map<String, Object> map : p7d) {
            timeList2.add("'" + map.get("time") + "'");
            numList2.add("'" + map.get("num") + "'");
        }
        model.addAttribute("timeList2", timeList2);
        model.addAttribute("numList2", numList2);

        return "/blackboard.html";
    }
}
