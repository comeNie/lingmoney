package com.mrbt.lingmoney.wechat.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mrbt.lingmoney.service.wechat.WechatService;
import com.mrbt.lingmoney.service.wechat.tools.WxUtil;

import net.sf.json.JSONObject;

@Controller
@RequestMapping(value = "/wechat")
public class WechatController {
	
	@Autowired
	private WechatService wechatService;
	
	
	/**
	 * 
	 * @param model
	 * @param shareUrl	决定要分享的URL，weChatConfig.properties文件中
	 * @param referralTel	分享时带的推荐码
	 * @return
	 */
	@RequestMapping(value = "/wechatShareUrl")
	public @ResponseBody Object wechatShareUrl(HttpServletRequest request, HttpServletResponse response, String shareUrl) {
		response.setHeader("Access-Control-Allow-Origin", "*");
		JSONObject resJson = new JSONObject();
		try {
			String fromAdd = request.getParameter("from");
			String isappinstalled = request.getParameter("isappinstalled");
			if(fromAdd != null && !fromAdd.equals("")){
				shareUrl = shareUrl + "&from=" + fromAdd;
			}
			
			if(isappinstalled != null && !isappinstalled.equals("")){
				shareUrl = shareUrl + "&isappinstalled=" + isappinstalled;
			}
			
			Map<String, String> res = wechatService.wechatChareUrl(shareUrl);
			
			JSONObject dataJson = new JSONObject();
			
			resJson.put("code", 200);
			resJson.put("msg", "url处理成功");
			
			dataJson.put("appId", WxUtil.APPID);
			dataJson.put("timestamp", res.get("timestamp"));
			dataJson.put("nonceStr", res.get("nonceStr"));
			dataJson.put("signature", res.get("signature"));
			dataJson.put("url", shareUrl);
			resJson.put("data", dataJson);
		} catch (Exception e) {
			e.printStackTrace();
			resJson.put("code", 500);
			resJson.put("msg", "服务器错误");
		}
		return resJson;
	}
}
