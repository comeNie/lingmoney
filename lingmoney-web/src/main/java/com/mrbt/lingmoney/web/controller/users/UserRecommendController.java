package com.mrbt.lingmoney.web.controller.users;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.mrbt.lingmoney.service.users.UsersService;
import com.mrbt.lingmoney.service.web.MyRecommendService;
import com.mrbt.lingmoney.utils.ResultParame;
import com.mrbt.lingmoney.web.controller.common.CommonMethodUtil;

/**
 * 我的推荐
 * 
 * @author yiban 2017年10月19日 上午9:48:18
 *
 */

@Controller
@RequestMapping("/usersRecommend")
public class UserRecommendController {
	private static final Logger LOG = LogManager.getLogger(UserRecommendController.class);
	@Autowired
	private MyRecommendService myRecommendService;
	@Autowired
	private UsersService usersService;

	/**
	 * 我的推荐
	 * 
	 * @param model
	 *            数据包装
	 * @param request
	 *            请求
	 * @return 返回信息
	 */
	@RequestMapping("/show")
	public String list(ModelMap model, HttpServletRequest request) {
		String uid = CommonMethodUtil.getUidBySession(request);

		if (StringUtils.isNotBlank(uid)) {
			try {
				myRecommendService.packageRecommednInfo(request, model, uid);
				CommonMethodUtil.modelUserBaseInfo(model, request, usersService);

			} catch (Exception e) {
				LOG.error("进入用户推荐页失败，系统错误" + e.getMessage());
				e.printStackTrace();
				return String.valueOf(ResultParame.ResultInfo.SERVER_ERROR.getCode());
			}

		} else {
			return "users/login";
		}

		return "myLingqian/myRecommend";
	}

	/**
	 * 推荐兑奖
	 * 
	 * @param model
	 *            数据包装
	 * @param request
	 *            请求
	 * @return 返回信息
	 */
	@RequestMapping("/myRecomAward")
	public String myRecomAward(ModelMap model, HttpServletRequest request) {
		try {
			CommonMethodUtil.modelUserBaseInfo(model, request, usersService);
			
		} catch (Exception e) {
			LOG.error("进入我的推荐获奖页面失败，系统错误" + e.getMessage());
			e.printStackTrace();
		}
		
		return "myLingqian/myRecomAward";
	}
}
