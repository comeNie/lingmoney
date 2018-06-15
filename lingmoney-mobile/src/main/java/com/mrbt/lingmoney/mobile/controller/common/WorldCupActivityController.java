package com.mrbt.lingmoney.mobile.controller.common;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.mrbt.lingmoney.mobile.controller.BaseController;
import com.mrbt.lingmoney.service.common.WorldCupActivityService;
import com.mrbt.lingmoney.utils.AppCons;
import com.mrbt.lingmoney.utils.PageInfo;
import com.mrbt.lingmoney.utils.ResultParame.ResultInfo;


/**
 * 世界杯答题相关
 * @author ruochen.yu
 *
 */
@Controller
@RequestMapping("/worldCup")
public class WorldCupActivityController extends BaseController {
	
	@Autowired
	private WorldCupActivityService worldCupActivityService;
	
	
	@RequestMapping(value = "/worldCupIndex")
	public String worldCupIndex(String token, Model model) {
		model.addAttribute("token", token);
		return "/worldCup/worldCupIndex";
	}
	
	@RequestMapping(value = "/worldAnswer")
	public String worldAnswer(String token, Model model) {
		model.addAttribute("token", token);
		return "/worldCup/worldAnswer";
	}
	
	@RequestMapping(value = "/worldResult")
	public String worldResult(String token, Model model) {
		model.addAttribute("token", token);
		return "/worldCup/worldResult";
	}
	
	/**
	 * 获取问题列表
	 * @param activityKey
	 * @param request
	 * @return
	 */
	@RequestMapping("/answersList")
	public @ResponseBody Object activityTime() {
		JSONObject jsonObject = new JSONObject();
		PageInfo pageInfo = new PageInfo();
		worldCupActivityService.getAnswersList(pageInfo);
		jsonObject.put("json", pageInfo);
		return jsonObject;
	}
	
	/**
	 * 计算得分
	 * @param activityKey
	 * @param request
	 * @return
	 */
	@RequestMapping("/calculatedFraction")
	public @ResponseBody Object calculatedFraction(String sheet) {
		JSONObject jsonObject = new JSONObject();
		PageInfo pageInfo = new PageInfo();
		worldCupActivityService.calculatedFraction(sheet, pageInfo);
		jsonObject.put("json", pageInfo);
		return jsonObject;
	}
	
	/**
	 * 通过手机号，记录得分 
	 * @param phone	手机号
	 * @param score	得分
	 * @return
	 */
	@RequestMapping("/saveScoreByPhone")
	public @ResponseBody Object saveScoreByPhone(String phone, Integer score) {
		JSONObject jsonObject = new JSONObject();
		PageInfo pageInfo = new PageInfo();
		worldCupActivityService.saveScoreByPhone(phone, score, pageInfo);
		jsonObject.put("json", pageInfo);
		return jsonObject;
	}
	
	/**
	 * 查询用户的答题分数
	 * @param phone
	 * @param score
	 * @return
	 */
	@RequestMapping("/queryUsersScore")
	public @ResponseBody Object queryUsersScore(HttpServletRequest request, String token) {
		JSONObject jsonObject = new JSONObject();
		PageInfo pageInfo = new PageInfo();
		
		String uId = getUid(AppCons.TOKEN_VERIFY + token);
		
		if (uId != null) {
			worldCupActivityService.queryUsersScore(uId, pageInfo);
		} else {
			pageInfo.setCode(ResultInfo.LOGIN_TIMEOUT.getCode());
			pageInfo.setMsg(ResultInfo.LOGIN_TIMEOUT.getMsg());
		}
		jsonObject.put("json", pageInfo);
		return jsonObject;
	}
	
	/**
	 * 领取奖励
	 * @param request
	 * @return
	 */
	@RequestMapping("/receiveRedEnve")
	public @ResponseBody Object receiveRedEnve(HttpServletRequest request, String token) {
		JSONObject jsonObject = new JSONObject();
		PageInfo pageInfo = new PageInfo();
		String uId = getUid(AppCons.TOKEN_VERIFY + token);
		if (uId != null) {
			worldCupActivityService.receiveRedEnve(uId, pageInfo);
		} else {
			pageInfo.setCode(ResultInfo.LOGIN_TIMEOUT.getCode());
			pageInfo.setMsg(ResultInfo.LOGIN_TIMEOUT.getMsg());
		}
		jsonObject.put("json", pageInfo);
		return jsonObject;
	}
	
}
