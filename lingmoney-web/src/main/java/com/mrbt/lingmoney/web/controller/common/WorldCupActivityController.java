package com.mrbt.lingmoney.web.controller.common;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mrbt.lingmoney.service.common.WorldCupActivityService;
import com.mrbt.lingmoney.utils.PageInfo;
import com.mrbt.lingmoney.utils.ResultParame.ResultInfo;

/**
 * 世界杯答题相关
 * @author ruochen.yu
 *
 */
@Controller
@RequestMapping("/worldCup")
public class WorldCupActivityController {
	
	@Autowired
	private WorldCupActivityService worldCupActivityService;
	
	/**
	 * 获取问题列表
	 * @param activityKey
	 * @param request
	 * @return
	 */
	@RequestMapping("/answersList")
	public @ResponseBody Object activityTime() {
		PageInfo pageInfo = new PageInfo();
		worldCupActivityService.getAnswersList(pageInfo);
		return pageInfo;
	}
	
	/**
	 * 计算得分
	 * @param activityKey
	 * @param request
	 * @return
	 */
	@RequestMapping("/calculatedFraction")
	public @ResponseBody Object calculatedFraction(String sheet) {
		PageInfo pageInfo = new PageInfo();
		worldCupActivityService.calculatedFraction(sheet, pageInfo);
		return pageInfo;
	}
	
	/**
	 * 通过手机号，记录得分 
	 * @param phone	手机号
	 * @param score	得分
	 * @return
	 */
	@RequestMapping("/saveScoreByPhone")
	public @ResponseBody Object saveScoreByPhone(String phone, Integer score) {
		PageInfo pageInfo = new PageInfo();
		worldCupActivityService.saveScoreByPhone(phone, score, pageInfo);
		return pageInfo;
	}
	
	/**
	 * 查询用户的答题分数
	 * @param phone
	 * @param score
	 * @return
	 */
	@RequestMapping("/queryUsersScore")
	public @ResponseBody Object queryUsersScore(HttpServletRequest request) {
		PageInfo pageInfo = new PageInfo();
		String uId = CommonMethodUtil.getUidBySession(request);
		if (uId != null) {
			worldCupActivityService.queryUsersScore(uId, pageInfo);
		} else {
			pageInfo.setCode(ResultInfo.LOGIN_TIMEOUT.getCode());
			pageInfo.setMsg(ResultInfo.LOGIN_TIMEOUT.getMsg());
		}
		return pageInfo;
	}
	
	/**
	 * 领取奖励
	 * @param request
	 * @return
	 */
	@RequestMapping("/receiveRedEnve")
	public @ResponseBody Object receiveRedEnve(HttpServletRequest request) {
		PageInfo pageInfo = new PageInfo();
		String uId = CommonMethodUtil.getUidBySession(request);
		if (uId != null) {
			worldCupActivityService.receiveRedEnve(uId, pageInfo);
		} else {
			pageInfo.setCode(ResultInfo.LOGIN_TIMEOUT.getCode());
			pageInfo.setMsg(ResultInfo.LOGIN_TIMEOUT.getMsg());
		}
		return pageInfo;
	}
	
}
