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

import com.mrbt.lingmoney.service.bank.BankAccountService;
import com.mrbt.lingmoney.service.users.UsersService;
import com.mrbt.lingmoney.utils.PageInfo;
import com.mrbt.lingmoney.utils.ResultParame;
import com.mrbt.lingmoney.web.controller.BaseController;
import com.mrbt.lingmoney.web.controller.common.CommonMethodUtil;
import com.mrbt.lingmoney.web.vo.view.ResultPageInfo;

/**
 * 银行账户开通相关接口
 * 
 * @author Administrator
 *
 */
@Controller
@RequestMapping("/bank")
public class BankAccountController extends BaseController {

	private static final Logger LOGGER = LogManager.getLogger(BankAccountController.class);

	@Autowired
	private BankAccountService bankAccountService;
	@Autowired
	private UsersService usersService;

	/**
	 * 发起账户开立请求
	 * 
	 * @param request
	 *            请求
	 * @param response
	 *            相应
	 * @param acName
	 *            账户名
	 * @param idNo
	 *            账号
	 * @param mobile
	 *            电话号码
	 * @param model
	 *            数据包装类
	 * @return 信息
	 */
	@RequestMapping(value = "/accountOpen", method = RequestMethod.POST)
	public @ResponseBody Object accountOpen(HttpServletRequest request, HttpServletResponse response, String acName,
			String idNo, String mobile, Model model) {
		LOGGER.info("银行账户开立");
		String uId = CommonMethodUtil.getUidBySession(request);
		PageInfo pageInfo = new PageInfo();
		try {
			pageInfo = bankAccountService.requestAccountOpen(uId, acName, idNo, mobile, 0, "PC");
		} catch (Exception e) {
			e.printStackTrace();
			pageInfo.setCode(ResultParame.ResultInfo.SERVER_ERROR.getCode());
			pageInfo.setMsg("服务器错误");
		}
		return pageInfo;
	}

	/**
	 * 账户开通银行返回商户按钮
	 * 
	 * @param request
	 *            请求
	 * @param response
	 *            相应
	 * @param uId
	 *            用户uId
	 * @param seqNo
	 *            账户号
	 * @param model
	 *            数据
	 * @return 信息
	 */
	@RequestMapping(value = "/accountOpenCallBack/{uId}/{seqNo}")
	public String accountOpenCallBack(HttpServletRequest request, HttpServletResponse response,
			@PathVariable String uId, @PathVariable String seqNo, ModelMap model) {

		try {
			CommonMethodUtil.modelUserBaseInfo(model, request, usersService);
		} catch (Exception e) {
			e.printStackTrace();
		}

		try {
			ResultPageInfo resultPageInfo = new ResultPageInfo();
			resultPageInfo.setType(1);
			resultPageInfo.setAutoReturnName("查看账户");
			resultPageInfo.setAutoReturnUrl("/myAccount/bindBankCard");
			resultPageInfo.setButtonOneName("会员首页");
			resultPageInfo.setButtonOneUrl("/myLingqian/show");
			resultPageInfo.setButtonTwoName("查看账户");
			resultPageInfo.setButtonTwoUrl("/myAccount/bindBankCard");

			LOGGER.info("账户开通银行返回商户");
			PageInfo pageInfo = bankAccountService.queryAccountOpen(uId, seqNo);

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
	 * 查询银行账户开立结果
	 * 
	 * @param request
	 *            请求
	 * @param model
	 *            数据包装
	 * @param seqNo
	 *            号
	 * @return 信息
	 */
	@RequestMapping("/queryAccountOpen")
	public String queryAccountOpen(HttpServletRequest request, ModelMap model, String seqNo) {
		LOGGER.info("查询银行账户开立结果");

		String uId = CommonMethodUtil.getUidBySession(request);

		PageInfo pageInfo = bankAccountService.queryAccountOpen(uId, seqNo);

		try {
			CommonMethodUtil.modelUserBaseInfo(model, request, usersService);
		} catch (Exception e) {
			e.printStackTrace();
		}

		ResultPageInfo info = new ResultPageInfo();
		info.setType(1);
		info.setAutoReturnName("银行卡页面");
		info.setAutoReturnUrl("/myAccount/bindBankCard");
		info.setButtonOneName("立即查看");
		info.setButtonOneUrl("/myAccount/bindBankCard");
		info.setButtonTwoName("绑卡激活");
		info.setButtonTwoUrl("/bank/tiedCard");

		int status = ResultParame.ResultNumber.ZERO.getNumber();
		String resultMsg = "开通华兴电子账户处理中......";
		if (pageInfo != null) {
			if (pageInfo.getCode() == ResultParame.ResultInfo.SUCCESS.getCode()) {
				status = ResultParame.ResultNumber.ONE.getNumber();
				resultMsg = "恭喜您，您已成功开通华兴电子账户";
				info.setBackup(pageInfo.getObj());
			} else if (pageInfo.getCode() == ResultParame.ResultInfo.TRADING_NOT_SUCCESS.getCode()) {
				status = ResultParame.ResultNumber.TWO.getNumber();
				resultMsg = "很遗憾，开通华兴电子账户失败！";
				info.setFailReason(pageInfo.getMsg());
				info.setButtonTwoName("重新申请");
			}
		}
		info.setStatus(status);
		info.setResultMsg(resultMsg);

		model.addAttribute("resultInfo", info);

		return "results";
	}

	/**
	 * 查询用户开通E账户状态 废弃
	 * 
	 * @param request
	 *            请求
	 * @param response
	 *            相应
	 * @return 信息
	 */
	@RequestMapping("/accountOpenStatus")
	public @ResponseBody Object accountOpenStatus(HttpServletRequest request, HttpServletResponse response) {
		PageInfo pageInfo = new PageInfo();
		String uId = CommonMethodUtil.getUidBySession(request);
		pageInfo = bankAccountService.accountOpenStatus(uId);
		return pageInfo;
	}

}
