package com.mrbt.lingmoney.web.controller.newuserreward;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mrbt.lingmoney.service.newuserreward.NewUserRewardService;
import com.mrbt.lingmoney.utils.PageInfo;
import com.mrbt.lingmoney.utils.ResultParame;
import com.mrbt.lingmoney.web.controller.common.CommonMethodUtil;

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
	 * 
	 * @return 返回信息
	 */
	@RequestMapping("/getNewuserReward")
	public @ResponseBody Object getNewuserReward() {
		LOG.info("/newUserReward/getNewuserReward");
		PageInfo pageInfo = new PageInfo();
		try {
			pageInfo.setCode(ResultParame.ResultInfo.SUCCESS.getCode());
			pageInfo.setMsg(ResultParame.ResultInfo.SUCCESS.getMsg());

			pageInfo.setObj(newUserRewardService.getNewuserReward(1));

		} catch (Exception e) {
			CommonMethodUtil.handelCatchedException(pageInfo, e, LOG, "查询新手福利错误");
		}

		return pageInfo;
	}
}
