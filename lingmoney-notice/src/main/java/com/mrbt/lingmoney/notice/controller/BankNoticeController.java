package com.mrbt.lingmoney.notice.controller;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.dom4j.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.mrbt.lingmoney.bank.HxBaseData;
import com.mrbt.lingmoney.bank.eaccount.HxAccountBanlance;
import com.mrbt.lingmoney.service.bank.AccountRechargeService;
import com.mrbt.lingmoney.service.bank.AutoTenderAuthorizeService;
import com.mrbt.lingmoney.service.bank.BankAccountService;
import com.mrbt.lingmoney.service.bank.BankCardService;
import com.mrbt.lingmoney.service.bank.BankInitiativeSingleRecallService;
import com.mrbt.lingmoney.service.bank.CashWithdrawService;
import com.mrbt.lingmoney.service.bank.HxAutoRepaymentService;
import com.mrbt.lingmoney.service.bank.HxBiddingLossService;
import com.mrbt.lingmoney.service.bank.HxEnterpriseAccountService;
import com.mrbt.lingmoney.service.bank.HxEnterpriseAgentService;
import com.mrbt.lingmoney.service.bank.HxEnterpriseRechargeNoticeService;
import com.mrbt.lingmoney.service.bank.HxReexchangeService;
import com.mrbt.lingmoney.service.bank.HxUnsubscribeService;
import com.mrbt.lingmoney.service.bank.SingleBiddingService;
import com.mrbt.lingmoney.service.bank.SinglePaymentService;
import com.mrbt.lingmoney.service.bank.TransferDebtService;

/**
 * 此类用于接收银行通知，统一处理通知报文，通过不同的transcode调用对应的service处理
 */
@Controller
public class BankNoticeController {
	
	private static String logHeard = "bankNoticeInfo_";
	
	private static final Logger LOGGER = LogManager.getLogger(HxAccountBanlance.class);
	
	@Autowired
	private BankAccountService bankAccountService;
	@Autowired
	private BankCardService bankCardService;
	@Autowired
	private AccountRechargeService accountRechargeService;
	@Autowired
	private CashWithdrawService cashWithdrawService;
	@Autowired
	private TransferDebtService transferDebtService;
	@Autowired
	private SingleBiddingService singleBiddingService;
	@Autowired
	private SinglePaymentService singlePaymentService;
	@Autowired
	private AutoTenderAuthorizeService autoTenderAuthorizeService;
	@Autowired
	private HxAutoRepaymentService hxAutoRepaymentService;
	@Autowired
	private BankInitiativeSingleRecallService bankInitiativeSingleRecallService;
	@Autowired
	private HxBiddingLossService hxBiddingLossService;
	@Autowired
	private HxReexchangeService hxReexchangeService;
	@Autowired
	private HxUnsubscribeService hxUnsubscribeService;
	@Autowired
	private HxEnterpriseAccountService hxEnterpriseAccountService;
	@Autowired
	private HxEnterpriseAgentService hxEnterpriseAgentService;
	@Autowired
	private HxEnterpriseRechargeNoticeService hxEnterpriseRechargeNoticeService;
	
	/**
	 * 接收银行异步回调
	 * @param response 响应
	 * @param request 接收银行报文
	 */
    @RequestMapping(value = "")
	public void update(HttpServletResponse response, HttpServletRequest request) {
		try {
			
			String logGroup = logHeard + System.currentTimeMillis();
			
			String noticeXml = getXmlDocument(request);
			
			LOGGER.info(logGroup + "通知报文:\n" + noticeXml);
			
			String resXml = "";
			
            if (noticeXml != null && !noticeXml.equals("")) {
				Document document = HxBaseData.analysisAsyncMsg(noticeXml, logGroup);
				
				String transCode = HxBaseData.queryTextFromXml(document, "TRANSCODE");
				
				System.out.println(document.asXML());
				
                if (transCode != null) {
                    if (transCode.equals("OGWR0001")) {
                        //开通E账户
                        LOGGER.info(logGroup + "开通E账户:\n" + noticeXml);
                        resXml = bankAccountService.opertionAccountOpen(document);
                    } else if (transCode.equals("OGWR0002")) {
                        //绑卡
                        LOGGER.info(logGroup + "绑卡:\n" + noticeXml);
                        resXml = bankCardService.receiveTiedCardResult(document);
                    } else if (transCode.equals("OGWR0003")) {
                        //单笔账户充值
                        LOGGER.info(logGroup + "单笔账户充值:\n" + noticeXml);
//                        resXml = accountRechargeService.opentionAccountRecharge(document);
                    } else if (transCode.equals("OGWR0004")) {
                        //单笔提现
                        LOGGER.info(logGroup + "单笔提现:\n" + noticeXml);
//                        resXml = cashWithdrawService.cashWithdrawCallBack(document);
                    } else if (transCode.equals("OGWR0005")) {
                        //单笔投标
                        LOGGER.info(logGroup + "单笔投标:\n" + noticeXml);
//                        resXml = singleBiddingService.singleBiddingCallBack(document);
                    } else if (transCode.equals("OGWR0006")) {
                        //单笔投标授权,自动投标授权 TODO
                        LOGGER.info(logGroup + "单笔投标授权,自动投标授权:\n" + noticeXml);
//                        resXml = autoTenderAuthorizeService.autoTenderAuthorizeCallBack(document);
                    } else if (transCode.equals("OGWR0007")) {
                        //债权转让,TODO
                        LOGGER.info(logGroup + "债权转让:\n" + noticeXml);
//                        resXml = transferDebtService.transferDebtCallBack(document);
                    } else if (transCode.equals("OGWR0008")) {
                        //借款人单标还款
                        LOGGER.info(logGroup + "借款人单标还款:\n" + noticeXml);
//                        resXml = singlePaymentService.receivePaymentResult(document);
                    } else if (transCode.equals("OGWR0011")) {
                        //自动还款授权
                        LOGGER.info(logGroup + "自动还款授权:\n" + noticeXml);
//                        resXml = hxAutoRepaymentService.autoRepaymentAuthCallBack(document);
                    } else if (transCode.equals("OGW0014T")) {
                        // 银行主动单笔撤标
                        LOGGER.info(logGroup + "银行主动单笔撤标:\n" + noticeXml);
//                        resXml = bankInitiativeSingleRecallService.initiativeSingleRecall(document);
                    } else if (transCode.equals("OGW0015T")) {
                        // 银行主动流标
                        LOGGER.info(logGroup + "银行主动流标:\n" + noticeXml);
//                        resXml = hxBiddingLossService.handleHxInitiativeBiddingLoss(document);
                    } else if (transCode.equals("OGW00227")) {
                        // 退汇异步通知
                        LOGGER.info(logGroup + "退汇异步通知:\n" + noticeXml);
//                        resXml = hxReexchangeService.handelHxReexchange(document);
                    } else if (transCode.equals("OGW00237")) {
                        // 销户通知
                        LOGGER.info(logGroup + "销户通知:\n" + noticeXml);
//                        resXml = hxUnsubscribeService.handelHxUnsubscribe(document);
                    } else if (transCode.equals("OGW00252")) {
                        // 企业开户回调
                        LOGGER.info(logGroup + "企业开户回调:\n" + noticeXml);
//                        resXml = hxEnterpriseAccountService.hxEnterpriseAccountOpenCallBack(document);
                    } else if (transCode.equals("OGW00261")) {
                        // 更新企业经办人信息
                        LOGGER.info(logGroup + "更新企业经办人信息:\n" + noticeXml);
//                        resXml = hxEnterpriseAgentService.updateEnterpriseAgentInfoCallBack(document);
                    } else if (transCode.equals("OGW00265")) {
                        // 充值来账通知
                        LOGGER.info(logGroup + "充值来账通知:\n" + noticeXml);
                        resXml = hxEnterpriseRechargeNoticeService.callBackOfEnterpriseRecharge(document);
                    }
				}
			}
			response.getWriter().write(resXml);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 接收银行xml报文
	 * @param request 请求对象
	 * @return xml报文字符串
	 * @throws Exception 抛出异常
	 */
	public String getXmlDocument(HttpServletRequest request) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(request.getInputStream(), "UTF-8"));
		String buffer = null;

		StringBuffer xml = new StringBuffer();

		while ((buffer = br.readLine()) != null) {
			xml.append(buffer);
		}
		return xml.toString();
	}

}
