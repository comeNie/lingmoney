package com.mrbt.lingmoney.model;

import java.util.ResourceBundle;

public class PayVo {
	private static ResourceBundle rb = ResourceBundle.getBundle("yeep");
	private static String homePath = "";
	public static String Platform_no = "21140011137";
	public static String pay_ip = "220.181.25.203";
	public static int pay_port = 80;
	public static String localhostUrl = "http://www.lingmoney.com.cn";
	public static String adminLocalhostUrl = "http://www.lingmoney.com.cn";
	public static String cilentLocalhostUrl = "http://www.lingmoney_mobile.com.cn";
	public static String ip_file_path = "";
	public static boolean verifySign;

	static {
		homePath = rb.getString("homePath");
		pay_ip = rb.getString("payIP");
		pay_port = Integer.parseInt(rb.getString("payPort").trim());
		Platform_no = rb.getString("Platform_no");
		localhostUrl = rb.getString("localhostUrl");
		ip_file_path = rb.getString("IPfilePath");
		adminLocalhostUrl = rb.getString("admin_localhostUrl");
		cilentLocalhostUrl = rb.getString("client_localhostUrl");
		String verifySign_str = rb.getString("verifySign");
		if (verifySign_str != null && verifySign_str.equalsIgnoreCase("true")) {
			verifySign = true;
		} else {
			verifySign = false;
		}
		System.out.println(verifySign);
		System.out.println(adminLocalhostUrl);

	}
	// http://119.161.147.110:8088/member/bha/toRegister，需要修改为http://220.181.25.233:8081/member/bha/toRegister
	private static String pay_url = homePath + "/member/bha/";
	public static String pay_zhi_Url = homePath + "/member/bhaexter/bhaController";
	// 手机客户端访问wap地址
	private static String client_pay_url = homePath + "/member/bhawireless/";

	// :118.194.195.188:7081
	// 118.194.195.188:7081
	/*
	 * public static String user_regist_call_url =
	 * "http://www.lingmoney.com.cn/userRegistController/callbackUrl.html";
	 * public static String user_regist_notify_url =
	 * "http://www.lingmoney.com.cn/userRegistController/notifyUrl.html";
	 * 
	 * public static String user_bind_bank_call_url =
	 * "http://www.lingmoney.com.cn/userBinkBankController/callbackUrl.html";
	 * public static String user_bind_bank_notify_url =
	 * "http://www.lingmoney.com.cn/userBinkBankController/notifyUrl.html";
	 * 
	 * public static String user_recharge_call_url =
	 * "http://www.lingmoney.com.cn/userRechargeController/callbackUrl.html";
	 * public static String user_recharge_notify_url =
	 * "http://www.lingmoney.com.cn/userRechargeController/notifyUrl.html";
	 * 
	 * public static String user_Withdraw_call_url =
	 * "http://www.lingmoney.com.cn/userWithdrawalsController/callbackUrl.html";
	 * public static String user_Withdraw_notify_url =
	 * "http://www.lingmoney.com.cn/userWithdrawalsController/notifyUrl.html";
	 * 
	 * public static String user_transfer_call_url =
	 * "http://www.lingmoney.com.cn/userTransferController/callbackUrl.html";
	 * public static String user_transfer_notify_url =
	 * "http://www.lingmoney.com.cn/userTransferController/notifyUrl.html";
	 * 
	 * public static String user_transfer_confirm_notify_url =
	 * "http://www.lingmoney.com.cn/userTransferController/confirmNotifyUrl.html";
	 * 
	 * public static String user_authorizeAutoTransfer_call_url =
	 * "http://www.lingmoney.com.cn/userAuthorizeAutoTransferController/callbackUrl.html";
	 * public static String user_authorizeAutoTransfer_notify_url =
	 * "http://www.lingmoney.com.cn/userAuthorizeAutoTransferController/notifyUrl.html";
	 * 
	 * // 手机客户端回调Controller public static String client_user_regist_call_url =
	 * "http://192.168.1.65:8080/clientUserRegistController/callbackUrl.html";
	 * public static String client_user_bind_bank_call_url =
	 * "http://192.168.1.65:8080/clientUserBinkBankController/callbackUrl.html";
	 * public static String client_user_authorizeAutoTransfer_call_url =
	 * "http://192.168.1.65:8080/clientUserAuthorizeAutoTransferController/callbackUrl.html";
	 * public static String client_user_transfer_call_url =
	 * "http://192.168.1.65:8080/clientUserTransferController/callbackUrl.html";
	 * public static String client_user_recharge_call_url =
	 * "http://192.168.1.65:8080/userRechargeController/callbackUrl.html";
	 * public static String client_user_Withdraw_call_url =
	 * "http://192.168.1.65:8080/userWithdrawalsController/callbackUrl.html";
	 * 
	 * public static String user_autoreturn_call_url =
	 * "http://www.lingmoney.com.cn/rest/autoReturnCallbackController/callbackUrl.html";
	 * public static String user_autoreturn_notify_url =
	 * "http://www.lingmoney.com.cn/rest/autoReturnCallbackController/notifyUrl.html";
	 */
	public static String FEE_MODE = "PLATFORM";
	public static String FEE_MODE_USER = "USER";

	public static String WITHDRAWTYPE_NORMAL = "NORMAL";

	public static String WITHDRAWTYPE = "URGENT";

	public static String ACCOUNT_QUERY_SERVICE = "ACCOUNT_INFO";

	public static String ACCOUNT_ACCOUNT_CONFRIM_SERVICE = "COMPLETE_TRANSACTION";
	// public static String WITHDRAW_EXPIRES =DateUtils.formatDate(new Date(),
	// pattern);

	// 投标
	public static String SERVICE_TYPE_TENDRE = "TENDER";
	// 还款TRANSFER
	public static String SERVICE_TYPE_REPAYMENT = "REPAYMENT";
	// 债权转让
	public static String SERVICE_TYPE_CREDIT_ASSIGNMENT = "CREDIT_ASSIGNMENT";
	// 转账
	public static String SERVICE_TYPE_TRANSFER = "TRANSFER";
	// 分润，仅在资金转账明细中使用
	public static String SERVICE_TYPE_COMMISSION = "COMMISSION";

	public static String SERVICE_UN_BIND_BAND_CARD = "toUnbindBankCard";

	// 平台的会员托管账户
	public static String USET_TYPE_M = "MEMBER";
	// 平台在易宝开通的商户账户
	public static String USET_TYPE_MT = "MERCHANT";

	// 平台交易帐户
	public static String Target_Platform_UserNo = "103";
	// public static String Target_Platform_UserNo ="lingqianXM860294";
	// CONFIRM表示解冻后完成资金划转
	public static String ACCOUNT_CONFIRM_CONFIRM = "CONFIRM";
	// 表示解冻后取消转账
	public static String ACCOUNT_CONFIRM_CANCEL = "CANCEL";

	/*
	 * public static String pay_certificate_path = "resource" +
	 * File.separator+"hk1001001@test.com.p12.pfx"; public static String
	 * pay_certificate_password = "123qwe"; public static String
	 * pay_certificate_String = "yeepay.com";
	 */

	public static int pay_time_limit = 8;

	public static String getPay_url() {

		return pay_url;
	}

	public static String getPlatform_no() {
		return Platform_no;
	}

	public static void setPlatform_no(String platform_no) {
		Platform_no = platform_no;
	}

	public void setPay_url(String pay_url) {
		this.pay_url = pay_url;
	}

	public static String getClient_pay_url() {
		return client_pay_url;
	}

	public void setClient_pay_url(String client_pay_url) {
		this.client_pay_url = client_pay_url;
	}

	public static String buy_frozen = "buyFrozen";

	public static String interest_key = "interestKey";

	public static String user_regist_call_url = localhostUrl + "/userRegistController/callbackUrl.html";
	public static String user_regist_notify_url = localhostUrl + "/userRegistController/notifyUrl.html";

	public static String user_bind_bank_call_url = localhostUrl + "/userBinkBankController/callbackUrl.html";
	public static String user_bind_bank_notify_url = localhostUrl + "/userBinkBankController/notifyUrl.html";

	public static String user_recharge_call_url = localhostUrl + "/userRechargeController/callbackUrl.html";
	public static String user_recharge_notify_url = localhostUrl + "/userRechargeController/notifyUrl.html";

	public static String user_Withdraw_call_url = localhostUrl + "/userWithdrawalsController/callbackUrl.html";
	public static String user_Withdraw_notify_url = localhostUrl + "/userWithdrawalsController/notifyUrl.html";

	public static String user_transfer_call_url = localhostUrl + "/userTransferController/callbackUrl.html";
	public static String user_transfer_notify_url = localhostUrl + "/userTransferController/notifyUrl.html";

	public static String user_transfer_confirm_notify_url = localhostUrl
			+ "/userTransferController/confirmNotifyUrl.html";
	public static String user_authorizeAutoTransfer_call_url = localhostUrl
			+ "/userAuthorizeAutoTransferController/callbackUrl.html";
	public static String user_authorizeAutoTransfer_notify_url = localhostUrl
			+ "/userAuthorizeAutoTransferController/notifyUrl.html";

	public static String user_unbundling_bank_call_url = localhostUrl + "/UserUnbundlingBank/callbackUrl.html";

	public static String user_toResetMobile_call_url = localhostUrl + "/toResetMobileController/callbackUrl.html";
	public static String user_toResetMobile_notify_url = localhostUrl + "/toResetMobileController/notifyUrl.html";

	// 手机客户端回调Controller
	public static String client_user_regist_call_url = cilentLocalhostUrl
			+ "/clientUserRegistController/callbackUrl.html";
	public static String client_user_bind_bank_call_url = cilentLocalhostUrl
			+ "/clientUserBinkBankController/callbackUrl.html";
	public static String client_user_authorizeAutoTransfer_call_url = cilentLocalhostUrl
			+ "/clientUserAuthorizeAutoTransferController/callbackUrl.html";
	public static String client_user_transfer_call_url = cilentLocalhostUrl
			+ "/clientUserTransferController/callbackUrl.html";
	public static String client_user_recharge_call_url = cilentLocalhostUrl
			+ "/clientUserRechargeController/callbackUrl.html";
	public static String client_user_Withdraw_call_url = cilentLocalhostUrl
			+ "/clientUserWithdrawalsController/callbackUrl.html";

	public static String user_autoreturn_call_url = localhostUrl
			+ "/rest/autoReturnCallbackController/callbackUrl.html";
	public static String user_autoreturn_notify_url = localhostUrl
			+ "/rest/autoReturnCallbackController/notifyUrl.html";

	public static String back_trading_id_list = "back_trading_id_list";
	public static String wallet_trading_Frozen = "wallet_trading_Frozen";
	public static String lingqian_wallet_big = "lingqian.wallet.big."; // 最高限额的Key
																		// +'yyyy-mm-dd'
	public static String lingqian_wallet_Statistics = "lingqian.wallet.Statistics";

	public static String lingqian_takeHeart_Statistics = "lingqian.takeHeart.Statistics";

	// notify 返回类型
	public static String ReceivePayNotifyList = "ReceivePayNotifyList";// 充值
	public static String RECHARGE = "RECHARGE";// 充值
	public static String REGIST = "REGIST";// 注册，实名认证
	public static String WITHDRAWALS = "WITHDRAWALS";// 提现，提现
	public static String TRANSFER = "TRANSFER";// 支付
	public static String QUERYRECHARGE = "QUERYRECHARGE";// 充值

	public static String ProcessingPayNotify = "ProcessingPayNotify.";// 充值

	// 随心取处理关键字
	public static String takeheart = "takeheart.";

}
