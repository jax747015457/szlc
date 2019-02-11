package com.kdDemo.action.xqiang;

import java.io.IOException;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSON;
import com.kdDemo.action.xqiang.comm.CharsetTypeEnum;
import com.kdDemo.action.xqiang.comm.ClientSignature;
import com.kdDemo.action.xqiang.config.xQingConfig;
import com.kdDemo.utils.StringUtils;

/**
 * Servlet implementation class xQingPayNotifyAction
 */
@WebServlet("/xQingPayNotifyAction")
public class xQingPayNotifyAction extends HttpServlet {
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		resp.setContentType("text/html;charset=utf-8");
		req.setCharacterEncoding("utf-8");
		System.out.println("=========进入小强回调方法==========");
		String result = StringUtils.parseRequst(req);
		System.out.println("回调内容：" + result);
		Map<String, String> resultMap = StringUtils.StrToMap(result);
		String stateCode = resultMap.get("stateCode");
		if (!StringUtils.isEmpty(stateCode) && "2".equals(stateCode)) {
			// 验签
			String signMsg = resultMap.remove("signMsg");
			String signStr = StringUtils.createRetStr(resultMap);
			try {
				String newsignMsg = ClientSignature.genSignByMD5(signStr, CharsetTypeEnum.UTF8, xQingConfig.key);
				if (signMsg.equals(newsignMsg)) {
					result = "success";
				} else {
					result = "sign fail";
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			result = "fail";
		}
		resp.getWriter().print(result);
	}

}
