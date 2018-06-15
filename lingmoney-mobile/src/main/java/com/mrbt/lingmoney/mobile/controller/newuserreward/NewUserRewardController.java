package com.mrbt.lingmoney.mobile.controller.newuserreward;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mrbt.lingmoney.service.newuserreward.NewUserRewardService;
import com.mrbt.lingmoney.utils.PageInfo;
import com.mrbt.lingmoney.utils.ResultParame.ResultInfo;
import com.mrbt.lingmoney.utils.ResultParame.ResultNumber;

/**
 * 新手福利
 * @author luox
 * @Date 2017年6月26日
 */
@Controller
@RequestMapping(value = "/newUserReward", method = RequestMethod.POST)
public class NewUserRewardController {

	private static final Logger LOG = LogManager.getLogger(NewUserRewardController.class);
	@Autowired
	private NewUserRewardService newUserRewardService;
	
	/**
	 * 新手福利
	 * @return pageInfo
	 */
	@RequestMapping("/getNewuserReward")
	public @ResponseBody Object getNewuserReward() {
		LOG.info("/newUserReward/getNewuserReward");
		PageInfo pageInfo = null;
		try {
			pageInfo = new PageInfo();
			pageInfo.setCode(ResultInfo.SUCCESS.getCode());
			pageInfo.setMsg(ResultInfo.SUCCESS.getMsg());

			pageInfo.setObj(newUserRewardService.getNewuserReward(ResultNumber.TWO.getNumber()));
		} catch (Exception e) {
			e.printStackTrace();
			LOG.error(e);
			pageInfo = new PageInfo();
			pageInfo.setCode(ResultInfo.SERVER_ERROR.getCode());
			pageInfo.setMsg(ResultInfo.SERVER_ERROR.getMsg());
		}
		return pageInfo;
	}
}
