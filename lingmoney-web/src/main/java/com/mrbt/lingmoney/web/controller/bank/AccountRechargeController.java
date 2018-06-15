package com.mrbt.lingmoney.web.controller.bank;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mrbt.lingmoney.service.bank.AccountRechargeService;
import com.mrbt.lingmoney.service.users.UsersService;
import com.mrbt.lingmoney.utils.PageInfo;
import com.mrbt.lingmoney.utils.ResultParame;
import com.mrbt.lingmoney.web.controller.BaseController;
import com.mrbt.lingmoney.web.controller.common.CommonMethodUtil;
import com.mrbt.lingmoney.web.vo.view.ResultPageInfo;

/**
 * 用户充值
 * 
 * @author Administrator
 *
 */

@Controller
@RequestMapping("bank")
public class AccountRechargeController extends BaseController {

	private static final Logger LOGGER = LogManager.getLogger(BankAccountController.class);

	@Autowired
	private AccountRechargeService accountRechargeService;
	@Autowired
	private UsersService usersService;

	/**
	 * 发起账户充值请求
	 * 
	 * @param request
	 *            请求
	 * @param response
	 *            相应
	 * @param amount
	 *            金额
	 * @param model
	 *            数据模型类
	 * @return 账户充值信息
	 */
	@RequestMapping(value = "/accountRecharge", method = RequestMethod.POST)
	public @ResponseBody Object accountRecharge(HttpServletRequest request, HttpServletResponse response, String amount,
			Model model) {
		LOGGER.info("账户充值");
		PageInfo pageInfo = new PageInfo();
		try {
			String uId = CommonMethodUtil.getUidBySession(request);
			int clientType = 0;
			String appId = "PC";
			pageInfo = accountRechargeService.accountRechargeRequest(clientType, appId, uId, amount);

		} catch (Exception e) {
			LOGGER.info("账户充值异常：" + e.toString()); // 抛出堆栈信息
			e.printStackTrace();
			pageInfo.setCode(ResultParame.ResultInfo.SERVER_ERROR.getCode());
			pageInfo.setMsg(ResultParame.ResultInfo.SERVER_ERROR.getMsg());
		}
		return pageInfo;
	}

	/**
	 * 账户充值银行异步通知报文
	 * 
	 * @param request
	 * @param response
	 * @param note
	 *            用户uId
	 * @return
	 */
	/*
	 * @RequestMapping(value = "/accountRechargeCallBack/{note}") public String
	 * accountRechargeCallBack(HttpServletRequest request, HttpServletResponse
	 * response,
	 * 
	 * @PathVariable String note) { try { request.setCharacterEncoding("utf-8");
	 * response.setContentType("text/html;charset=utf-8"); String xml =
	 * getXmlDocument(request);// 接收银行发送的报文 LOGGER.info("账户开通银行异步通知报文" + xml);
	 * 
	 * String res = accountRechargeService.accountRechargeCallBack(xml, note);
	 * response.getWriter().write(res); } catch (Exception e) {
	 * e.printStackTrace(); }
	 * 
	 * return null; }
	 */

	/**
	 * 查询银行账户充值结果
	 * 
	 * @param request
	 *            请求
	 * @param model
	 *            数据模型包装类
	 * @param number
	 *            数量
	 * @return 查询银行账户充值结果
	 */
	@RequestMapping("/queryAccountRecharge")
	public String queryAccountRecharge(HttpServletRequest request, ModelMap model, String number) {
		LOGGER.info("查询银行账户充值结果");
		PageInfo pageInfo = null;
		try {
			String uId = CommonMethodUtil.getUidBySession(request);
			String appId = "PC";
			pageInfo = accountRechargeService.queryAccountRecharge(uId, number, appId);
		} catch (Exception e) {
			e.printStackTrace();
		}

		try {
			CommonMethodUtil.modelUserBaseInfo(model, request, usersService);
		} catch (Exception e) {
			e.printStackTrace();
		}

		ResultPageInfo info = new ResultPageInfo();
		info.setType(ResultParame.ResultNumber.THREE.getNumber());
		info.setAutoReturnName("会员首页");
		info.setAutoReturnUrl("/myLingqian/show");
		info.setButtonOneName("查看记录");
		info.setButtonOneUrl("/myFinancial/accountFlow");
		info.setButtonTwoName("继续充值");
		info.setButtonTwoUrl("/myFinancial/accountRecharge");

		int status = ResultParame.ResultNumber.ZERO.getNumber();
		String resultMsg = "充值结果处理中......";
		if (pageInfo != null) {
			if (pageInfo.getCode() == ResultParame.ResultInfo.SUCCESS.getCode()) {
				status = ResultParame.ResultNumber.ONE.getNumber();
				resultMsg = "恭喜您，充值成功！";
				info.setBackup(pageInfo.getObj());
			} else if (pageInfo.getCode() == ResultParame.ResultInfo.NO_SUCCESS.getCode()) {
				status = ResultParame.ResultNumber.TWO.getNumber();
				resultMsg = "很遗憾，充值失败！";
				info.setFailReason(pageInfo.getMsg());
				info.setButtonTwoName("重新充值");
			}
		}
		info.setStatus(status);
		info.setResultMsg(resultMsg);

		model.addAttribute("resultInfo", info);

		return "results";
	}

	/**
	 * 返回商户操作
	 * 
	 * @author YJQ 2017年8月1日
	 * @param note
	 *            note
	 * @param model
	 *            数据模型包装类
	 * @param request
	 *            请求
	 * @return 操作信息
	 */
	@RequestMapping("/accountRechargeCallBack/{note}")
	public String queryAccountRecharge(@PathVariable String note, ModelMap model, HttpServletRequest request) {
		LOGGER.info("查询银行账户充值结果");
		PageInfo pageInfo = null;
		try {
			pageInfo = accountRechargeService.queryAccountRecharge(note);
		} catch (Exception e) {
			e.printStackTrace();
		}

		try {
			CommonMethodUtil.modelUserBaseInfo(model, request, usersService);
		} catch (Exception e) {
			e.printStackTrace();
		}

		ResultPageInfo info = new ResultPageInfo();
		info.setType(ResultParame.ResultNumber.THREE.getNumber());
		info.setAutoReturnName("会员首页");
		info.setAutoReturnUrl("/myLingqian/show");
		info.setButtonOneName("查看记录");
		info.setButtonOneUrl("/myFinancial/accountFlow");
		info.setButtonTwoName("继续充值");
		info.setButtonTwoUrl("/myFinancial/accountRecharge");

		int status = ResultParame.ResultNumber.ZERO.getNumber();
		String resultMsg = "充值结果处理中......";
		if (pageInfo != null) {
			if (pageInfo.getCode() == ResultParame.ResultInfo.SUCCESS.getCode()) {
				status = ResultParame.ResultNumber.ONE.getNumber();
				resultMsg = "恭喜您，充值成功！";
				info.setBackup(pageInfo.getObj());
			} else if (pageInfo.getCode() == ResultParame.ResultInfo.NO_SUCCESS.getCode()) {
				status = ResultParame.ResultNumber.TWO.getNumber();
				resultMsg = "很遗憾，充值失败！";
				info.setFailReason(pageInfo.getMsg());
				info.setButtonTwoName("重新充值");
			}
		}
		info.setStatus(status);
		info.setResultMsg(resultMsg);

		model.addAttribute("resultInfo", info);

		return "hxCallBack";
	}
}
