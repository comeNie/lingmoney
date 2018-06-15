package com.mrbt.lingmoney.web.controller.bank;

import javax.servlet.http.HttpServletRequest;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mrbt.lingmoney.service.bank.BankAccountBalanceService;
import com.mrbt.lingmoney.utils.PageInfo;
import com.mrbt.lingmoney.utils.ResultParame;
import com.mrbt.lingmoney.web.controller.common.CommonMethodUtil;

/**
 * 账户余额
 * @author luox
 * @Date 2017年6月5日
 */
@Controller
@RequestMapping(value = "/bank", method = RequestMethod.POST)
public class BankAccountBalanceController {
	private static final Logger LOG = LogManager.getLogger(BankAccountBalanceController.class);
	@Autowired
	private BankAccountBalanceService bankAccountBalanceService;
	
	/**
	 * 账户余额校验
	 * 
	 * @param amount
	 *            金额
	 * @param request
	 *            请求
	 * @return 信息
	 */
	@RequestMapping("/hxAccoutBalanceVerify")
	public @ResponseBody Object hxAccoutBalanceVerify(
			String amount, HttpServletRequest request) {
		LOG.info("账户余额校验");
		PageInfo pageInfo = null;
		try {
			String uId = CommonMethodUtil.getUidBySession(request);
			pageInfo = bankAccountBalanceService.hxAccoutBalanceVerify("PC", uId, amount);
		} catch (Exception e) {
			e.printStackTrace();
			LOG.error(e);
			pageInfo = new PageInfo();
			pageInfo.setCode(ResultParame.ResultInfo.SERVER_ERROR.getCode());
			pageInfo.setMsg(e.getMessage());
		}
		return pageInfo;
	}
	
	/**
	 * 华兴E账户
	 * 
	 * @param request
	 *            请求
	 * @return 信息
	 */
	@RequestMapping("/userHxAccout")
	public @ResponseBody Object userHxAccout(HttpServletRequest request) {
		LOG.info("账户余额查询");
		PageInfo pageInfo = null;
		try {
			String uId = CommonMethodUtil.getUidBySession(request);
			pageInfo = bankAccountBalanceService.userHxAccout(uId);
		} catch (Exception e) {
			e.printStackTrace();
			LOG.error(e);
			pageInfo = new PageInfo();
			pageInfo.setCode(ResultParame.ResultInfo.SERVER_ERROR.getCode());
			pageInfo.setMsg(e.getMessage());
		}
		return pageInfo;
	}
}
