package com.mrbt.lingmoney.service.web;

import org.springframework.ui.ModelMap;

import com.mrbt.lingmoney.utils.PageInfo;

import net.sf.json.JSONObject;

/**
 *@author syb
 *@date 2017年5月10日 下午4:04:33
 *@version 1.0
 *@description 我的账户service
 **/
public interface MyAccountService {

	/**
	 * 查询用户领宝数 和 领宝使用记录（按使用日期倒序，id倒序）
	 * 
	 * @param pageNo 页码
	 * @param uid 用户id
	 * @param pageSize 每页条数
	 * @return 查询结果
	 */
	PageInfo listLingbaoRecord(Integer pageNo, String uid, Integer pageSize);

	/**
	 * 包装用户绑卡卡信息页面
	 * @param modelMap 
	 * @param uid 用户id
	 */
	void packageBindBankCardInfo(ModelMap modelMap, String uid);

	/**
	 * 
	 * @description 根据日期和用户ID查询该用户当月的理财流水记录
	 * @author syb
	 * @date 2017年8月24日 下午6:28:39
	 * @version 1.0
	 * @param dateTime
	 *            时间
	 * @param uid
	 *            用户id
	 * @return 日历展示数据
	 *
	 */
	JSONObject queryStaticMonth(String dateTime, String uid);

	/**
	 * 我的礼品页数据
	 * @param uid 
	 * @param pageNo 
	 * @return 查询结果
	 */
	PageInfo listGiftInfo(String uid, Integer pageNo);

	/**
	 * 京东绑卡--查询所有快捷支付银行
	 * @return 查询结果
	 */
	PageInfo listQuickPaymentBank();

	/**
	 * 京东绑卡--验证银行卡号是否已被绑定
	 * @param number 银行卡号
	 * @return 查询结果
	 */
	PageInfo isCardNumberBeBinded(String number);

	/**
	 * 京东绑卡--根据卡号前六位查询银行信息
	 * @param number 银行卡前六位
	 * @return 查询结果
	 */
	PageInfo queryBankInfoByTopSix(String number);

	/**
	 * 京东绑卡--验证身份证
	 * @param idCard 身份证号
	 * @param uid 用户id
	 * @return 验证结果
	 */
	PageInfo testBindCardId(String uid, String idCard);

	/**
	 * 删除京东绑定银行卡
	 * @param uid 用户id
	 * @param id 银行卡id
	 * @return 删除结果
	 */
	PageInfo deleteBankCard(String uid, Integer id);

	/**
	 * 查询华兴银行账户绑卡结果
	 * @param uId 用户id
	 * @return 查询结果
	 */
	PageInfo queryHxAccountBindCardInfo(String uId);

}
