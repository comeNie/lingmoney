package com.mrbt.lingmoney.mobile.controller.dream;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.mrbt.lingmoney.mobile.controller.BaseController;
import com.mrbt.lingmoney.model.UsersDreamInfo;
import com.mrbt.lingmoney.service.dream.DreamServer;
import com.mrbt.lingmoney.utils.AppCons;
import com.mrbt.lingmoney.utils.ResultParame.ResultInfo;

/** 
 * @author  suyibo 
 * @date 创建时间：2018年3月30日
 */
@Controller
@RequestMapping("/dream")
public class DreamController extends BaseController {
	
	
	private static final Logger LOGGER = LogManager.getLogger(DreamController.class);
	
	@Autowired
	private DreamServer dreamServer;
	
	/**
	 * 梦想列表页
	 */
	@RequestMapping("/dreamHtml")
	public String dreamHtml(String token, Model model) {
		model.addAttribute("token", token);
		//验证用户持有中梦想
		String tokenKey = AppCons.TOKEN_VERIFY + token;
		String uId = getUid(tokenKey);
		if (uId != null && !uId.equals("")) {
			UsersDreamInfo udi = dreamServer.queryUsersHaveDream(uId);
			if (udi != null) {
				model.addAttribute("dreamid", udi.getDreamInfoId());
				model.addAttribute("udi", udi);
				return "/dream/dreamHold";
			}
		}
		return "/dream/dream";
	}
	
	/**
	 * 梦想增加页
	 */
	@RequestMapping("/dreamHold")
	public String dreamHold(String token, Model model, Integer dreamid) {
		model.addAttribute("token", token);
		model.addAttribute("dreamid", dreamid);
		return "/dream/dreamHold";
	}
	
	/**
	 * 梦想增加页
	 */
	@RequestMapping("/dreamAdd")
	public String dreamAdd(String token, Model model, Integer dreamid) {
		model.addAttribute("token", token);
		model.addAttribute("dreamid", dreamid);
		return "/dream/dreamadd";
	}
	
	/**
	 * 梦想完成页
	 */
	@RequestMapping("/dreamComplete")
	public String dreamComplete(String token, Model model, Integer dreamid) {
		model.addAttribute("token", token);
		model.addAttribute("dreamid", dreamid);
		return "/dream/dreamComplete";
	}
	
	/**
	 * 获取梦想类别列表
	 * @param token 用户token
	 */
	@RequestMapping(value = "/queryDreamInfo")
	public @ResponseBody Object queryDreamInfo(String token) {
		LOGGER.info("获取梦想类别列表");
		
		JSONObject jsonObject = new JSONObject();
		
		String tokenKey = AppCons.TOKEN_VERIFY + token;
		String uId = getUid(tokenKey);
		
		try {
			jsonObject = dreamServer.queryDreamInfo(uId);
		} catch (Exception e) {
			jsonObject.put("code", ResultInfo.SERVER_ERROR.getCode());
			jsonObject.put("msg", ResultInfo.SERVER_ERROR.getMsg());
			e.printStackTrace();
			LOGGER.error("获取梦想类别列表，系统错误。" + "\n" + e.getMessage());
		}
		return jsonObject;
	}
	
	
	/**
	 * 通过基础信息ID获取详情
	 * @param token 用户token
	 */
	@RequestMapping(value = "/queryBaseDreamInfo")
	public @ResponseBody Object queryBaseDreamInfo(Integer baseId) {
		LOGGER.info("获取梦想信息详情");
		
		JSONObject jsonObject = new JSONObject();
		
		try {
			jsonObject = dreamServer.queryBaseDreamInfo(baseId);
		} catch (Exception e) {
			jsonObject.put("code", ResultInfo.SERVER_ERROR.getCode());
			jsonObject.put("msg", ResultInfo.SERVER_ERROR.getMsg());
			e.printStackTrace();
			LOGGER.error("获取梦想类别列表，系统错误。" + "\n" + e.getMessage());
		}
		return jsonObject;
	}
	
	/**
	 * 用户选择梦想
	 * @param token 用户token
	 */
	@RequestMapping(value = "/selectDream")
	public @ResponseBody Object selectDream(String token, Integer baseId) {
		LOGGER.info("获取梦想信息详情");
		
		JSONObject jsonObject = new JSONObject();
		
		String tokenKey = AppCons.TOKEN_VERIFY + token;
		String uId = getUid(tokenKey);
		
		try {
			jsonObject = dreamServer.selectDream(uId, baseId);
		} catch (Exception e) {
			jsonObject.put("code", ResultInfo.SERVER_ERROR.getCode());
			jsonObject.put("msg", ResultInfo.SERVER_ERROR.getMsg());
			e.printStackTrace();
			LOGGER.error("获取梦想类别列表，系统错误。" + "\n" + e.getMessage());
		}
		return jsonObject;
	}
	
	/**
	 * 查询用户现在持有的梦想
	 * @param token 用户token
	 */
	@RequestMapping(value = "/queryUsersDreamInfo")
	public @ResponseBody Object queryUsersDreamInfo(String token, Integer baseId) {
		LOGGER.info("获取梦想信息详情");
		
		JSONObject jsonObject = new JSONObject();
		
		String tokenKey = AppCons.TOKEN_VERIFY + token;
		String uId = getUid(tokenKey);
		
		try {
			jsonObject = dreamServer.queryUsersDreamInfo(uId, baseId);
		} catch (Exception e) {
			jsonObject.put("code", ResultInfo.SERVER_ERROR.getCode());
			jsonObject.put("msg", ResultInfo.SERVER_ERROR.getMsg());
			e.printStackTrace();
			LOGGER.error("获取梦想类别列表，系统错误。" + "\n" + e.getMessage());
		}
		return jsonObject;
	}
}
