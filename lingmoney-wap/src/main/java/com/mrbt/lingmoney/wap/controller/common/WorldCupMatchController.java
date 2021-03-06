package com.mrbt.lingmoney.wap.controller.common;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mrbt.lingmoney.service.common.WorldCupMatchService;
import com.mrbt.lingmoney.service.discover.LingbaoLotteryService;
import com.mrbt.lingmoney.utils.PageInfo;

import net.sf.json.JSONObject;

/**
 * 世界杯竞猜相关
 * @author ruochen.yu
 *
 */
@Controller
@RequestMapping("/worldCup")
public class WorldCupMatchController {
	
	
	@Autowired
	private WorldCupMatchService worldCupMatchService;
	
	@Autowired
	private LingbaoLotteryService lingbaoLotteryService;
	
	/**
	 * 比赛场次
	 * @param activityKey
	 * @param request
	 * @return
	 */
	@RequestMapping("/queryMatchInfo")
	public @ResponseBody Object queryMatchInfo(HttpServletRequest request) {
		PageInfo pageInfo = new PageInfo();
		String uId = CommonMethodUtil.getUidBySession(request);
		worldCupMatchService.queryMatchInfo(pageInfo, uId);
		return pageInfo;
	}
	
	/**
	 * 竞猜
	 * @param request
	 * @param matchId	场次ID
	 * @param gameChoice	竞猜选项  1平，2left， 3right
	 * @return
	 */
	@RequestMapping("/guessingCompet")
	public @ResponseBody Object guessingCompet(HttpServletRequest request, Integer matchId, Integer gameChoice) {
		PageInfo pageInfo = new PageInfo();
		String uId = CommonMethodUtil.getUidBySession(request);
		worldCupMatchService.guessingCompet(pageInfo, uId, matchId, gameChoice);
		return pageInfo;
	}
	
	/**
	 * 查询用户竞猜列表
	 * @param activityKey
	 * @param request
	 * @return
	 */
	@RequestMapping("/queryUserGuess")
	public @ResponseBody Object queryUserGuess(HttpServletRequest request) {
		PageInfo pageInfo = new PageInfo();
		String uId = CommonMethodUtil.getUidBySession(request);
		worldCupMatchService.queryUserGuess(pageInfo, uId);
		return pageInfo;
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
		JSONObject json = lingbaoLotteryService.queryChoujiangItem(typeID);
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
	public Object validateChoujiang(HttpServletRequest request, HttpServletResponse response, Integer typeID) {
		
		String uid = CommonMethodUtil.getUidBySession(request);
		PageInfo pageInfo = new PageInfo();
		worldCupMatchService.validateChoujiang(uid, typeID, pageInfo);
		
		return pageInfo;
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
	public Object validateChoujiangCount(HttpServletRequest request, HttpServletResponse response, Integer typeID) {
		String uid = CommonMethodUtil.getUidBySession(request);
		
		PageInfo pageInfo = new PageInfo();
		worldCupMatchService.validateChoujiangCount(uid, typeID, pageInfo);
		
		return pageInfo;
	}
	
	/**
	 * 查询用户竞猜列表
	 * @param activityKey
	 * @param request
	 * @return
	 */
	@RequestMapping("/winningCount")
	public @ResponseBody Object winningCount(HttpServletRequest request) {
		PageInfo pageInfo = new PageInfo();
		String uId = CommonMethodUtil.getUidBySession(request);
		worldCupMatchService.winningCount(pageInfo, uId);
		return pageInfo;
	}
	
}
