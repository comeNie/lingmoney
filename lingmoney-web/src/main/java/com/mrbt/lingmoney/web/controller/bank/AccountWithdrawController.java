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

import com.mrbt.lingmoney.service.bank.CashWithdrawResultService;
import com.mrbt.lingmoney.service.bank.CashWithdrawService;
import com.mrbt.lingmoney.service.users.UsersService;
import com.mrbt.lingmoney.utils.PageInfo;
import com.mrbt.lingmoney.utils.ResultParame;
import com.mrbt.lingmoney.web.controller.BaseController;
import com.mrbt.lingmoney.web.controller.common.CommonMethodUtil;
import com.mrbt.lingmoney.web.vo.view.ResultPageInfo;

/**
 * 用户提现
 * 
 * @author lihq
 * @date 2017年6月21日 下午5:01:49
 * @description
 * @version 1.0
 * @since 2017-04-12
 */

@Controller
@RequestMapping("bank")
public class AccountWithdrawController extends BaseController {

	private static final Logger LOGGER = LogManager.getLogger(AccountWithdrawController.class);

	@Autowired
	private CashWithdrawService cashWithdrawService;
	@Autowired
	private CashWithdrawResultService cashWithdrawResultService;
	@Autowired
	private UsersService usersService;

	/**
	 * 发起账户提现请求
	 * 
	 * @param request
	 *            请求
	 * @param amount
	 *            金额
	 * @return 提现请求信息
	 */
	@RequestMapping(value = "/accountWithdraw", method = RequestMethod.POST)
	public @ResponseBody Object accountWithdraw(HttpServletRequest request, String amount) {
		LOGGER.info("账户提现");
		PageInfo pageInfo = new PageInfo();
		try {
			String uId = CommonMethodUtil.getUidBySession(request);
			int clientType = ResultParame.ResultNumber.ZERO.getNumber();
			String appId = "PC";
			pageInfo = cashWithdrawService.requestCashWithdraw(clientType, appId, amount, uId);

		} catch (Exception e) {
			LOGGER.info("账户提现异常：" + e.toString()); // 抛出堆栈信息
			e.printStackTrace();
			pageInfo.setCode(ResultParame.ResultInfo.SERVER_ERROR.getCode());
			pageInfo.setMsg(ResultParame.ResultInfo.SERVER_ERROR.getMsg());
		}

		return pageInfo;
	}

	/**
	 * 账户提现返回商户
	 * 
	 * @param request
	 *            请求
	 * @param response
	 *            相应
	 * @param note
	 *            流水号
	 * @param uId
	 *            用户id
	 * @param model
	 *            数据包装
	 * @return 账户提现返回商户信息
	 */
	@RequestMapping(value = "/cashWithdrawCallBack/{note}/{uId}")
	public String accountWithdrawCallBack(HttpServletRequest request, HttpServletResponse response,
			@PathVariable String note, @PathVariable String uId, ModelMap model) {

		try {
			CommonMethodUtil.modelUserBaseInfo(model, request, usersService);
		} catch (Exception e) {
			e.printStackTrace();
		}

		ResultPageInfo resultPageInfo = new ResultPageInfo();
		resultPageInfo.setType(ResultParame.ResultNumber.FOUR.getNumber());
		resultPageInfo.setAutoReturnName("会员首页");
		resultPageInfo.setAutoReturnUrl("/myLingqian/show");
		resultPageInfo.setButtonOneName("查看记录");
		resultPageInfo.setButtonOneUrl("/myFinancial/accountFlow");
		resultPageInfo.setButtonTwoName("继续提现");
		resultPageInfo.setButtonTwoUrl("/myFinancial/accountWithdraw");
		try {
			LOGGER.info("账户提现银行返回商户-" + note);
			PageInfo pageInfo = cashWithdrawResultService.queryAccountWithdraw("PC", note, uId);
			if (pageInfo.getCode() == ResultParame.ResultInfo.SUCCESS.getCode()) {
				resultPageInfo.setStatus(ResultParame.ResultNumber.ONE.getNumber());
				resultPageInfo.setResultMsg(pageInfo.getMsg());
			} else if (pageInfo.getCode() == ResultParame.ResultInfo.ING.getCode()) {
				resultPageInfo.setStatus(ResultParame.ResultNumber.THREE.getNumber());
				resultPageInfo.setResultMsg(pageInfo.getMsg());
			} else {
				resultPageInfo.setStatus(ResultParame.ResultNumber.TWO.getNumber());
				resultPageInfo.setResultMsg(pageInfo.getMsg());
				resultPageInfo.setFailReason(pageInfo.getMsg());
			}
			model.addAttribute("resultInfo", resultPageInfo);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return "hxCallBack";
	}

	/**
	 * 提交到华兴银行
	 * 
	 * @param requestData
	 *            请求数据
	 * @param transCode
	 *            交易流水
	 * @param bankUrl
	 *            银行链接
	 * @param model
	 *            数据包装类
	 * @return 提交到华兴银行
	 */
	@RequestMapping(value = "accountWithdrawAction", method = RequestMethod.POST)
	public String accountWithdrawAction(String requestData, String transCode, String bankUrl, Model model) {
		model.addAttribute("requestData", requestData);
		model.addAttribute("transCode", transCode);
		model.addAttribute("bankUrl", bankUrl);
		return "hxBank/hxBankAction";
	}

	/**
	 * 查询提现结果
	 * 
	 * @param request
	 *            请求
	 * @param model
	 *            数据包装类
	 * @param number
	 *            数量
	 * @return 查询提现结果
	 */
	@RequestMapping("/queryAccountWithdraw")
	public String queryAccountWithdraw(HttpServletRequest request, ModelMap model, String number) {
		LOGGER.info("查询提现结果");
		PageInfo pageInfo = null;
		try {
			String uId = CommonMethodUtil.getUidBySession(request);
			String appId = "PC";
			pageInfo = cashWithdrawResultService.queryAccountWithdraw(appId, number, uId);
		} catch (Exception e) {
			e.printStackTrace();
		}

		try {
			CommonMethodUtil.modelUserBaseInfo(model, request, usersService);
		} catch (Exception e) {
			e.printStackTrace();
		}

		ResultPageInfo info = new ResultPageInfo();
		info.setType(ResultParame.ResultNumber.FOUR.getNumber());
		info.setAutoReturnName("会员首页");
		info.setAutoReturnUrl("/myLingqian/show");
		info.setButtonOneName("查看记录");
		info.setButtonOneUrl("/myFinancial/accountFlow");
		info.setButtonTwoName("继续提现");
		info.setButtonTwoUrl("/myFinancial/accountWithdraw");

		int status = ResultParame.ResultNumber.ZERO.getNumber();
		String resultMsg = "提现结果处理中......";
		if (pageInfo != null) {
			if (pageInfo.getCode() == ResultParame.ResultInfo.SUCCESS.getCode()) {
				status = ResultParame.ResultNumber.ONE.getNumber();
				resultMsg = "恭喜您，提现成功！";
				info.setBackup(pageInfo.getObj());
			} else if (pageInfo.getCode() == ResultParame.ResultInfo.TRADING_NOT_SUCCESS.getCode()) {
				status = ResultParame.ResultNumber.TWO.getNumber();
				resultMsg = "很遗憾，提现失败！";
				info.setFailReason(pageInfo.getMsg());
				info.setButtonTwoName("重新提现");
			}
		}
		info.setStatus(status);
		info.setResultMsg(resultMsg);

		model.addAttribute("resultInfo", info);

		return "results";
	}

}
