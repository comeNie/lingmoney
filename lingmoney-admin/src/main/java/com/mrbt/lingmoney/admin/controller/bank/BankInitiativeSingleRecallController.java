package com.mrbt.lingmoney.admin.controller.bank;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mrbt.lingmoney.admin.controller.base.BaseController;
import com.mrbt.lingmoney.admin.service.bank.BankAccountBalanceService;
import com.mrbt.lingmoney.admin.service.bank.BankAccountService;
import com.mrbt.lingmoney.admin.service.bank.BankInitiativeSingleRecallService;

/**
 *@author syb
 *@date 2017年6月5日 上午11:01:18
 *@version 1.0
 *@description 银行主动单笔撤标
 **/
@Controller
@RequestMapping("/bank")
public class BankInitiativeSingleRecallController extends BaseController {
	private static final Logger LOGGER = LogManager.getLogger(BankInitiativeSingleRecallController.class);
	
	@Autowired
	private BankInitiativeSingleRecallService bankInitiativeSingleRecallService;
	
	@Autowired
	private BankAccountBalanceService bankAccountBalanceService;
	
	@Autowired
	private BankAccountService bankAccountService;
	
	/**
	 * 处理银行主动单笔撤标请求
	 * 1.插入一条撤标记录
	 * 2.更新trading 和 trading_fix_info 表状态为撤标
	 * 3.更新account_flow表状态为撤标（根据交易流水号查询）
	 * 4.插入一条回款记录（hx_users_account_repayment_record）
	 * @param request REQ
	 * @param response RES
	 * @return 返回页面
	 */
	@RequestMapping(value = "/initiativeSingleRecall", method = RequestMethod.POST)
	public String initiativeSingleRecall(HttpServletRequest request, HttpServletResponse response) {
		String logGroup = "\ninitiativeSingleRecall_" + System.currentTimeMillis() + "_";
		
		try {
			String xml = getXmlDocument(request);
			
			LOGGER.info("{}收到银行主动单笔撤标请求：\n {}", logGroup, xml);
			
			String xmlResponse = bankInitiativeSingleRecallService.initiativeSingleRecall(xml, logGroup);
			
			if (xmlResponse != null) {
				response.setContentLength(xmlResponse.getBytes().length);
				response.setContentType("application/xml; charset=UTF-8");
				response.getWriter().write(xmlResponse);
			}
		} catch (Exception e) {
			LOGGER.error("{}处理银行主动单笔撤标请求异常,\n {}", logGroup, e.toString());
			e.printStackTrace();
		}
		
		return null;
	}

    // ==================以下为测试页面使用========================

	/**
	 * 查询用户余额
	 * @param acNo  华兴E账户
	 * @param acName  账户名称
	 * @return 返回用户余额
	 */
	@RequestMapping(value = "/queryUserBalance", method = RequestMethod.POST)
	public @ResponseBody String queryUserBalance(String acNo, String acName) {

		try {
			return bankAccountBalanceService.findUserBanlance("PC", acName, acNo).toJSONString();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * 查询E账户
	 * @param type	类型
	 * @param acNo	华兴E账户
	 * @return	返回用户状态
	 */
	@RequestMapping(value = "/queryUserAccount")
	public @ResponseBody Object queryUserAccount(Integer type, String acNo) {
		return bankAccountService.queryRealNameAuthenticationResult(type, acNo, "test");
	}
    // =================以上为测试页面使用========================

}
