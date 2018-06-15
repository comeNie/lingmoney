package com.mrbt.lingmoney.web.controller.users;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;

import com.mrbt.lingmoney.commons.cache.RedisGet;
import com.mrbt.lingmoney.commons.cache.RedisSet;
import com.mrbt.lingmoney.model.UserFinance;
import com.mrbt.lingmoney.service.trading.TradingService;
import com.mrbt.lingmoney.service.users.UsersService;
import com.mrbt.lingmoney.service.web.UsersIndexService;
import com.mrbt.lingmoney.utils.AppCons;
import com.mrbt.lingmoney.utils.PageInfo;
import com.mrbt.lingmoney.utils.ResultParame;
import com.mrbt.lingmoney.web.controller.common.CommonMethodUtil;

/**
 * 我的领钱儿
 * 
 * @author Administrator
 *
 */
@Controller
@RequestMapping("/myLingqian")
public class MyLingqianController {
	private static final Logger LOG = LogManager.getLogger(MyLingqianController.class);

	@Autowired
	private UsersIndexService usersIndexService;
	@Autowired
	private UsersService usersService;
	@Autowired
	private TradingService tradingService;
	@Autowired
	private RedisSet redisSet;
	@Autowired
	private RedisGet redisGet;

	/**
	 * 我的领钱儿--首页
	 * 
	 * @param model
	 *            数据包装
	 * @param request
	 *            请求
	 * @return 返回信息
	 */
	@RequestMapping("/show")
	public String list(ModelMap model, HttpServletRequest request) {
		// 帐户， 帐户流水， 消息
		String uid = CommonMethodUtil.getUidBySession(request);
		if (!StringUtils.isEmpty(uid)) {
			try {
				// 判断是否初次进去，redis在登录时赋值为1，30分钟超时
				String key = AppCons.AFTERLOGIN_FIRST_IN_MYLINGQIAN + uid;
				String times = (String) redisGet.getRedisStringResult(key);
				if (!StringUtils.isEmpty(times) && "1".equals(times)) {
					model.addAttribute("firstTimeIn", true);
					redisSet.setRedisStringResult(key, "2");
				} else {
					model.addAttribute("firstTimeIn", false);
				}
				
				usersIndexService.packageUsersInfo(model, request, uid);
				
				// 待支付订单信息
				PageInfo pi = tradingService.unPayOrder(uid);
				if (pi.getCode() == ResultParame.ResultInfo.SUCCESS.getCode()) {
					UserFinance userFinance = (UserFinance) pi.getObj();
					long lastTime = userFinance.getRemainDt();
					model.addAttribute("lastTime", lastTime);

				} else {
					model.addAttribute("lastTime", null);
				}
				
				CommonMethodUtil.modelUserBaseInfo(model, request, usersService);

			} catch (Exception e) {
				LOG.error("我的领钱儿首页信息获取失败" + e.getMessage());
				e.printStackTrace();
				return String.valueOf(ResultParame.ResultInfo.SERVER_ERROR.getCode());
			}

		} else {
			return "users/login";
		}

		return "myLingqian/myLingqianIndex";
	}
}
