package com.mrbt.lingmoney.admin.controller.bank;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mrbt.lingmoney.admin.service.bank.HxSingleRewardOrCutAMelonService;
import com.mrbt.lingmoney.utils.PageInfo;
import com.mrbt.lingmoney.utils.ResultParame.ResultInfo;

/**
 * @author syb
 * @date 2017年6月7日 下午2:59:48
 * @version 1.0
 * @description 单笔奖励或分红
 **/
@Controller
@RequestMapping("/bank")
public class HxSingleRewardOrCutAMelonController {
	private static final Logger LOGGER = LogManager.getLogger(HxSingleRewardOrCutAMelonController.class);
	private static final String APPID = "PC";

	@Autowired
	private HxSingleRewardOrCutAMelonService hxSingleRewardOrCutAMelonService;

	/**
	 * 单笔奖励或分红 请求
	 * 
	 * @param hxAccountId 账户ID
	 * @param amount 金额
	 * @param remark 备注
	 * @return	返回
	 */
	@RequestMapping(value = "/requestSingleRewardOrCutAMelon", method = RequestMethod.POST)
	public @ResponseBody Object requestSingleRewardOrCutAMelon(String hxAccountId, String amount, String remark) {
		String logGroup = "\nrequestSingleRewardOrCutAMelon_" + System.currentTimeMillis() + "_";
		LOGGER.info(logGroup + "单笔奖励或分红 请求 \t" + hxAccountId + "\t" + amount + "\t" + remark);
		PageInfo pageInfo = new PageInfo();
		try {
			pageInfo = hxSingleRewardOrCutAMelonService.requestSingleRewardOrCutAMelon(APPID, hxAccountId, amount, remark,
					logGroup);
		} catch (Exception e) {
			e.printStackTrace();
			pageInfo.setCode(ResultInfo.SERVER_ERROR.getCode());
			pageInfo.setMsg(ResultInfo.SERVER_ERROR.getMsg());
			LOGGER.error(logGroup + "单笔奖励或分红请求失败，系统错误。 \n" + e.getMessage());
		}
		
		return pageInfo;
	}
}
