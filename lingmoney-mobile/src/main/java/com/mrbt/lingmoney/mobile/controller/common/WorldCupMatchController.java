package com.mrbt.lingmoney.mobile.controller.common;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.mrbt.lingmoney.mobile.controller.BaseController;
import com.mrbt.lingmoney.service.common.WorldCupMatchService;
import com.mrbt.lingmoney.service.discover.LingbaoLotteryService;
import com.mrbt.lingmoney.utils.AppCons;
import com.mrbt.lingmoney.utils.PageInfo;

/**
 * 世界杯竞猜相关
 * @author ruochen.yu
 *
 */
@Controller
@RequestMapping("/worldCup")
public class WorldCupMatchController extends BaseController {
	
	
	@Autowired
	private WorldCupMatchService worldCupMatchService;
	
	@Autowired
	private LingbaoLotteryService lingbaoLotteryService;
	
	@RequestMapping(value = "/world_cup1")
	public String worldCup1(String token, Model model) {
		model.addAttribute("token", token);
		return "/worldCup/world_cup1";
	}
	
	@RequestMapping(value = "/world_cup2")
	public String worldCup2(String token, Model model) {
		model.addAttribute("token", token);
		return "/worldCup/world_cup2";
	}
	
	@RequestMapping(value = "/world_cup3")
	public String worldCup3(String token, Model model) {
		model.addAttribute("token", token);
		return "/worldCup/world_cup3";
	}
	
	/**
	 * 比赛场次
	 * @param activityKey
	 * @param request
	 * @return
	 */
	@RequestMapping("/queryMatchInfo")
	public @ResponseBody Object queryMatchInfo(HttpServletRequest request, String token) {
		JSONObject jsonObject = new JSONObject();
		PageInfo pageInfo = new PageInfo();
		String uId = getUid(AppCons.TOKEN_VERIFY + token);
		worldCupMatchService.queryMatchInfo(pageInfo, uId);
		jsonObject.put("json", pageInfo);
		return jsonObject;
	}
	
	/**
	 * 竞猜
	 * @param request
	 * @param matchId	场次ID
	 * @param gameChoice	竞猜选项  1平，2left， 3right
	 * @return
	 */
	@RequestMapping("/guessingCompet")
	public @ResponseBody Object guessingCompet(HttpServletRequest request, Integer matchId, Integer gameChoice, String token) {
		JSONObject jsonObject = new JSONObject();
		PageInfo pageInfo = new PageInfo();
		String uId = getUid(AppCons.TOKEN_VERIFY + token);
		worldCupMatchService.guessingCompet(pageInfo, uId, matchId, gameChoice);
		jsonObject.put("json", pageInfo);
		return jsonObject;
	}
	
	/**
	 * 查询用户竞猜列表
	 * @param activityKey
	 * @param request
	 * @return
	 */
	@RequestMapping("/queryUserGuess")
	public @ResponseBody Object queryUserGuess(HttpServletRequest request, String token) {
		JSONObject jsonObject = new JSONObject();
		PageInfo pageInfo = new PageInfo();
		String uId = getUid(AppCons.TOKEN_VERIFY + token);
		worldCupMatchService.queryUserGuess(pageInfo, uId);
		jsonObject.put("json", pageInfo);
		return jsonObject;
	}
	
	/**
	 * 查询抽奖活动信息
	 *
	 * @Description
	 * @return
	 */
	@RequestMapping("/queryChoujiangItem")
	@ResponseBody
	public Object queryChoujiangItem(Integer typeID) {
		net.sf.json.JSONObject json = lingbaoLotteryService.queryChoujiangItem(typeID);
		return json;
	}

	
	/**
	 * 抽奖
	 * @Description
	 * @param request
	 * @return
	 */
	@RequestMapping("/validateChoujiang")
	@ResponseBody
	public Object validateChoujiang(HttpServletRequest request, HttpServletResponse response, Integer typeID, String token) {
		JSONObject jsonObject = new JSONObject();
		String uId = getUid(AppCons.TOKEN_VERIFY + token);
		PageInfo pageInfo = new PageInfo();
		worldCupMatchService.validateChoujiang(uId, typeID, pageInfo);
		jsonObject.put("json", pageInfo);
		return jsonObject;
	}
	
	/**
	 * 获取抽奖次数
	 *
	 * @Description
	 * @param request
	 * @return
	 */
	@RequestMapping("/validateChoujiangCount")
	@ResponseBody
	public Object validateChoujiangCount(HttpServletRequest request, HttpServletResponse response, Integer typeID, String token) {
		JSONObject jsonObject = new JSONObject();
		String uId = getUid(AppCons.TOKEN_VERIFY + token);
		PageInfo pageInfo = new PageInfo();
		worldCupMatchService.validateChoujiangCount(uId, typeID, pageInfo);
		jsonObject.put("json", pageInfo);
		return jsonObject;
	}
	
	/**
	 * 查询用户竞猜列表
	 * @param activityKey
	 * @param request
	 * @return
	 */
	@RequestMapping("/winningCount")
	public @ResponseBody Object winningCount(HttpServletRequest request, String token) {
		JSONObject jsonObject = new JSONObject();
		PageInfo pageInfo = new PageInfo();
		String uId = getUid(AppCons.TOKEN_VERIFY + token);
		worldCupMatchService.winningCount(pageInfo, uId);
		jsonObject.put("json", pageInfo);
		return jsonObject;
	}
	
}
