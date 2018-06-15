package com.mrbt.lingmoney.web.controller.bank;

import javax.servlet.http.HttpServletRequest;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mrbt.lingmoney.commons.exception.PageInfoException;
import com.mrbt.lingmoney.service.bank.BankAccountBalanceService;
import com.mrbt.lingmoney.utils.PageInfo;
import com.mrbt.lingmoney.utils.ResultParame;
import com.mrbt.lingmoney.web.controller.common.CommonMethodUtil;

/**
 * 用户映射查询
 * @author luox
 * @Date 2017年7月13日
 */
@Controller
@RequestMapping(value = "/bank")
public class UserBalanceController {
	private static final Logger LOGGER = LogManager.getLogger(UserBalanceController.class);
	@Autowired
	private BankAccountBalanceService bankAccountBalanceService;
	
	/**
	 * 用户发起用户映射查询请求
	 * 
	 * @param request
	 *            请求
	 * @return 信息
	 */
	@RequestMapping("/findUserBalance")
	public @ResponseBody Object findUserBalance(HttpServletRequest request) {
		LOGGER.info("用户发起用户映射查询请求");
		PageInfo pageInfo = new PageInfo();
		String uId = CommonMethodUtil.getUidBySession(request);
		try {
			pageInfo = bankAccountBalanceService.findUserBalance("PC", uId);
		} catch (PageInfoException e) {
			pageInfo.setCode(e.getCode());
			pageInfo.setMsg(e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			pageInfo.setCode(ResultParame.ResultInfo.SERVER_ERROR.getCode());
			pageInfo.setMsg(ResultParame.ResultInfo.SERVER_ERROR.getMsg());
		}
		return pageInfo;
	}
}
