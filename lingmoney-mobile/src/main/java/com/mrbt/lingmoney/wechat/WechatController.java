package com.mrbt.lingmoney.wechat;

import java.io.IOException;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mrbt.lingmoney.service.wechat.WechatService;
import com.mrbt.lingmoney.service.wechat.tools.WxUtil;
import com.mrbt.lingmoney.utils.Common;
import com.mrbt.lingmoney.utils.ResultParame;

import net.sf.json.JSONObject;

/**
 * 微信分享
 * 微信公众号中配置的域名
 * test.lingmoney.cn
 * app.lingmoney.cn
 * @author Administrator
 *
 */
@Controller
@RequestMapping(value = "/wechat")
public class WechatController {
	
	@Autowired
	private WechatService wechatService;
	
	/**
	 * @param request request
	 * @param response response
	 * @param shareUrl	分享的URL,给什么返回什么
	 * @return resJson
	 */
	@RequestMapping(value = "/wechatShareUrl")
	public @ResponseBody Object wechatShareUrl(HttpServletRequest request, HttpServletResponse response, String shareUrl) {
		response.setHeader("Access-Control-Allow-Origin", "*");
		JSONObject resJson = new JSONObject();
		try {
			Map<String, String> res = wechatService.wechatChareUrl(shareUrl);
			
			JSONObject dataJson = new JSONObject();
			
			resJson.put("code", ResultParame.ResultInfo.SUCCESS.getCode());
			resJson.put("msg", "url处理成功");
			
			dataJson.put("appId", WxUtil.APPID);
			dataJson.put("timestamp", res.get("timestamp"));
			dataJson.put("nonceStr", res.get("nonceStr"));
			dataJson.put("signature", res.get("signature"));
			dataJson.put("url", shareUrl);
			
			resJson.put("data", dataJson);
		} catch (Exception e) {
			e.printStackTrace();
			resJson.put("code", ResultParame.ResultInfo.SERVER_ERROR.getCode());
			resJson.put("msg", ResultParame.ResultInfo.SERVER_ERROR.getMsg());
		}
		return resJson;
	}
	
	/**
	 * @param request request
	 * @param response response
	 * @param model model
	 * @param shareUrl	决定要分享的URL，weChatConfig.properties文件中
	 * @param referralTel	分享时带的推荐码
	 * @return shareUrl
	 */
	@RequestMapping(value = "/recommend")
	public String recommend(HttpServletRequest request, HttpServletResponse response, Model model, String shareUrl, String referralTel) {
		
		String fromAdd = request.getParameter("from");
		String isappinstalled = request.getParameter("isappinstalled");
		String url = Common.getPropertyVal("weChatConfig.properties", shareUrl) + "&referralTel=" + referralTel;
		if (fromAdd != null && !fromAdd.equals("")) {
			url = url + "&from=" + fromAdd;
		}
		
		if (isappinstalled != null && !isappinstalled.equals("")) {
			url = url + "&isappinstalled=" + isappinstalled;
		}
		
		Map<String, String> res = wechatService.wechatChareUrl(url);
		
		model.addAttribute("appId", WxUtil.APPID);
	    model.addAttribute("timestamp", res.get("timestamp"));
		model.addAttribute("nonceStr", res.get("nonceStr"));
		model.addAttribute("signature", res.get("signature"));
		model.addAttribute("url", url);
		model.addAttribute("referralTel", referralTel);
		
		return shareUrl;
	}
	
	/**
	 * 发送短信，短链接页面，拉新活动H5
	 * @param request request
	 * @param response response
	 * @param model model
	 * @return string
	 */
	@RequestMapping(value = "/pullNewLogin")
	public String pullNewLogin(HttpServletRequest request, HttpServletResponse response, Model model) {
		return "pullNewLogin";
	}
	
	/**
	 * 发送短信，短链接页面，拉新活动H5
	 * @param request request
	 * @param response response
	 * @param model model
	 * @return string
	 */
	@RequestMapping(value = "/pullNewActivityHome")
	public String pullNewActivityHome(HttpServletRequest request, HttpServletResponse response, Model model) {
		
		return "pullNewActivityHome";
	}
	
	
	/**
	 * 发送短信，短链接页面，拉新活动H5
	 * @param request request
	 * @param response response
	 * @param model model 
	 * @return string
	 */
	@RequestMapping(value = "/pullNewRegist")
	public String pullNewRegist(HttpServletRequest request, HttpServletResponse response, Model model) {
		
		return "pullNewRegist";
	}
	
	/**
	 * 通过手机号和密码返回推荐码
	 * @param request request
	 * @param response response
	 * @param model model
	 */
	@RequestMapping(value = "/queryRecomCode")
	public void queryRecomCode(HttpServletRequest request, HttpServletResponse response, Model model) {
		
		response.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=utf-8");
		
		JSONObject json = new JSONObject();
		String loginAccount = request.getParameter("account");
		String telephone = request.getParameter("account");
		String password = request.getParameter("password");
		
		try {
			json = wechatService.queryUserRecomCode(loginAccount, telephone, password);
			json.write(response.getWriter());
		} catch (Exception e) {
			e.printStackTrace();
			json.put("code", ResultParame.ResultInfo.SERVER_ERROR.getCode());
			json.put("msg", ResultParame.ResultInfo.SERVER_ERROR.getMsg());
			try {
				json.write(response.getWriter());
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
	}
	
}
