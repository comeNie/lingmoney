package com.mrbt.lingmoney.service.users;

import com.mrbt.lingmoney.model.HxAccount;
import com.mrbt.lingmoney.model.HxBidBorrowUnionInfo;
import com.mrbt.lingmoney.model.HxCard;
import com.mrbt.lingmoney.model.HxPaymentBidBorrowUnionInfo;
import com.mrbt.lingmoney.model.LingbaoAddress;
import com.mrbt.lingmoney.utils.PageInfo;

/**
 * 验证服务
 * 
 * @author YJQ
 *
 */
public interface VerifyService {
	/**
	 * 验证注册账号
	 * 
	 * @author YJQ 2017年5月11日
	 * @param account 账号
	 * @throws Exception 
	 */
	void verifyAccount(String account) throws Exception;

	/**
	 * 验证手机号格式
	 * 
	 * @author YJQ 2017年5月19日
	 * @param telephone 手机号
	 * @throws Exception 
	 */
	void verifyTelephoneBase(String telephone) throws Exception;

	/**
	 * 验证手机号码
	 * 
	 * @author YJQ 2017年5月19日
	 * @param telephone 手机号
	 * @param type 0-注册 1-忘记密码
	 * @throws Exception 
	 */
	void verifyTelephone(String telephone, Integer type) throws Exception;

	/**
	 * 验证登录账号
	 * 
	 * @author YJQ 2017年5月18日
	 * @param account 账号
	 * @throws Exception 
	 */
	void verifyLoginAccount(String account) throws Exception;

	/**
	 * 验证推荐码
	 * 
	 * @author YJQ 2017年5月11日
	 * @param referralTel 推荐码
	 * @throws Exception 
	 */
	void verifyReferralTel(String referralTel) throws Exception;

	/**
	 * 验证短信验证码
	 * 
	 * @author YJQ 2017年5月11日
	 * @param telephone 手机号
	 * @param verificationCode 短信验证码
	 * @throws Exception 
	 */
	void verifyMsgCode(String telephone, String verificationCode) throws Exception;

	/**
	 * 注册表单全部验证-mobile
	 * 
	 * @author YJQ 2017年5月11日
	 * @param telephone 手机号
	 * @param referralTel 推荐码
	 * @param verificationCode 验证码
	 * @return pageinfo
	 * @throws Exception 
	 */
	PageInfo verifyMobileAll(String telephone, String referralTel, String verificationCode) throws Exception;

	/**
	 * 注册表单全部验证-web
	 * 
	 * @author YJQ 2017年5月11日
	 * @param account 账号
	 * @param telephone 手机号
	 * @param referralTel 推荐码
	 * @param verificationCode 验证码
	 * @return pageinfo
	 * @throws Exception 
	 */
	PageInfo verifyWebAll(String account, String telephone, String referralTel, String verificationCode)
			throws Exception;

	/**
	 * 验证用户id
	 * 
	 * @author YJQ 2017年5月18日
	 * @param id 用户id
	 * @throws Exception 
	 */
	void verifyUserId(Object id) throws Exception;

	/**
	 * 验证中文
	 * 
	 * @author YJQ 2017年5月19日
	 * @param val 
	 * @param item 返回msg ： item + 格式错误
	 * @throws Exception 
	 */
	void verifyName(String val, String item) throws Exception;

	/**
	 * 验证用户身份证号
	 * 
	 * @author YJQ 2017年5月19日
	 * @param idCard 身份证号
	 * @throws Exception 
	 */
	void verifyIdCard(String idCard) throws Exception;

	/**
	 * 验证地址id
	 * 
	 * @author YJQ 2017年5月19日
	 * @param id 地址id
	 * @param uid 用户id
	 * @return 地址详情
	 * @throws Exception 
	 */
	LingbaoAddress verifyAddressId(Integer id, String uid) throws Exception;

	/**
	 * 验证地址格式
	 * 
	 * @author YJQ 2017年5月19日
	 * @param address 地址
	 * @throws Exception 
	 */
	void verifyAddress(String address) throws Exception;

	/**
	 * 验证是否为空
	 * 
	 * @author YJQ 2017年5月19日
	 * @param obj 
	 * @param msg 
	 * @throws Exception 
	 */
	void verifyEmpty(Object obj, String msg) throws Exception;

	/**
	 * 验证电子邮箱地址
	 * 
	 * @author YJQ 2017年5月19日
	 * @param email 邮件地址
	 * @throws Exception 
	 */
	void verifyEmail(String email) throws Exception;

	/**
	 * 验证redis存储结果
	 * 
	 * @author YJQ 2017年5月19日
	 * @param key redis中uid的key
	 * @param targetVal 验证码
	 * @throws Exception 
	 */
	void verifyRedisCode(String key, String targetVal) throws Exception;

	/**
	 * 验证0/1标志项
	 * 
	 * @author YJQ 2017年5月19日
	 * @param val 标志
	 * @throws Exception 
	 */
	void verifyStatus(Integer val) throws Exception;

    /**
     * 验证邮箱是否重复
     * 
     * @param email 邮件地址
     * @param uid 用户id
     * @return 邮件地址
     * @throws Exception 
     *
     */
	String verifyEmailNoRepeat(String email, String uid) throws Exception;

    /**
     * 验证微信号
     * 
     * @param wechat 微信号
     * @return 微信号
     * @throws Exception 
     *
     */
	String verifyWechat(String wechat) throws Exception;

    /**
     * 验证昵称
     * 
     * @param name 昵称
     * @throws Exception 
     *
     */
	void verifyNickName(String name) throws Exception;

	/**
	 * 手机号和身份证号是否匹配
	 * 
	 * @author YJQ 2017年5月20日
	 * @param telephone 手机号
	 * @param uid 用户id
	 * @throws Exception 
	 */
	void verifyTelephoneAndUid(String telephone, String uid) throws Exception;

	/**
	 * 验证密码
	 * 
	 * @author YJQ 2017年5月22日
	 * @param pwd 密码
	 * @throws Exception 
	 */
	void verifyPwd(String pwd) throws Exception;

	/**
	 * 验证可为空的用户信息
	 * 
	 * @author YJQ 2017年5月25日
	 * @param val 验证目标值
	 * @param item 信息条目
	 * @throws Exception 
	 */
	void verifyUserInfo(String val, String item) throws Exception;

	/**
	 * 验证从事职业 汉字+/
	 * 
	 * @author YJQ 2017年5月25日
	 * @param val 职业
	 * @throws Exception 
	 */
	void verifyJob(String val) throws Exception;

	/**
	 * 验证登录错误次数
	 * 
	 * @author YJQ 2017年5月25日
	 * @param key redis中uid的key
	 * @param times 条目 IP/
	 * @throws Exception 
	 */
	void verifyLoginErrorCount(String key, Integer times) throws Exception;

	/**
	 * 验证银行卡开卡状态
	 * 
	 * @author YJQ 2017年6月5日
	 * @param accId 用户账户id
	 * @return integer 
	 * @throws Exception 
	 */
	Integer verifyAcc(String accId) throws Exception;

	/**
	 * 验证华兴E账户
	 * 
	 * @author YJQ 2017年6月7日
	 * @param acNo 账号
	 * @return hxaccount
	 * @throws Exception 
	 */
	HxAccount verifyAcNo(String acNo) throws Exception;

	/**
	 * 验证金额格式
	 * 
	 * @author YJQ 2017年6月8日
	 * @param money 金额
	 * @param msg 信息
	 * @throws Exception 
	 */
	void verifyMoney(String money, String msg) throws Exception;

	/**
	 * 验证借款信息
	 * 
	 * @author YJQ 2017年6月9日
	 * @param lomoNo 借款编号
	 * @param bwAcNo 借款账号
	 * @param bwAcName 借款户名
	 * @return hxbidborrowunioninfo
	 * @throws Exception 
	 */
	HxBidBorrowUnionInfo verifyBorrowInfo(String lomoNo, String bwAcNo, String bwAcName) throws Exception;

	/**
	 * 验证华兴E账户(用uid)
	 * 
	 * @author YJQ 2017年6月7日
	 * @param uid 用户id
	 * @return hxaccount
	 * @throws Exception 异常
	 */
	HxAccount verifyAcNoByUid(String uid) throws Exception;

	/**
	 * 验证是否绑卡
	 * @author YJQ  2017年6月26日
	 * @param accId 账号id
	 * @return hxcard
	 * @throws Exception 
	 */
	HxCard verifyBankCard(String accId) throws Exception;

	/**
	 * 通过开卡流水号查到账户信息
	 * @author YJQ  2017年7月31日
	 * @param channelFlow 渠道号
	 * @return hxaccount
	 * @throws Exception 
	 */
	HxAccount verifyAcNoByChannelFlow(String channelFlow) throws Exception;

	
	/**
	 * 验证借款信息
	 * 
	 * @author YJQ 2017年6月9日
	 * @param lomoNo 借款编号
	 * @return hxbidborrowunioninfo
	 * @throws Exception 
	 */
	HxBidBorrowUnionInfo verifyBorrowInfo(String lomoNo) throws Exception;

	/**
	 * 验证还款流水号
	 * @author YJQ  2017年6月19日
	 * @param channelFlow 渠道号
	 * @return hxpaymentbidborrowunioninfo
	 * @throws Exception 
	 */
	HxPaymentBidBorrowUnionInfo verifyPaymentBorrowInfo(Object channelFlow) throws Exception;
}
