package com.mrbt.lingmoney.service.userfinance;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONObject;

import com.mrbt.lingmoney.model.UsersMessage;
import com.mrbt.lingmoney.utils.PageInfo;

/**
 * 我的理财首页
 * 
 * @author 罗鑫
 * @Date 2017年4月14日
 */
public interface UserFinanceHomeService {

	/**
	 * 查询用户余额
	 * 
	 * @param token token
	 * @return json
	 *
	 */
	JSONObject findUserBalance(String uId);

	/**
	 * 是否有消息
	 * 
	 * @param token token
	 * @return json
	 *
	 */
	JSONObject hasMessage(String uId);

	/**
	 * 查询理财明细
	 * 
	 * @param token token
	 * @return json
	 *
	 */
	JSONObject findUserFinance(String uId);

	/**
	 * 查询预期收益
     * @param token token
     * @return json
	 */
	JSONObject findExpectEarnings(String uId);

	/**
	 * 查询用户信息
	 * @param token token
     * @return json
	 */
	JSONObject finUserInfo(String uId);

	/**
	 * 查询交易数据
	 * 
	 * @param uId 用户id
	 * @param pageInfo pageinfo
	 * @param status 交易状态
	 * @param multyStatus 交易状态，多个用英文逗号分隔
	 */
	void findTradingData(String uId, PageInfo pageInfo, Integer status, String multyStatus);

	/**
	 * 查询累计收益
	 * @param uId 用户id
     * @return json 
	 */
	JSONObject findIncome(String uId);

	/**
	 * 查询消息
	 * @param token token
	 * @param pageInfo pageinfo
	 */
	void findUserMessage(String uId, PageInfo pageInfo);

	/**
	 * 消息详情
	 * @param mId 消息id
	 * @return usersmessage
	 */
	UsersMessage findUserMessageById(Integer mId);

	/**
	 * 理财详情
	 * 
	 * @param tId 交易id
	 * @param token token
	 * @return json 
	 */
	JSONObject findUserTradingDetail(Integer tId, String uId);

	/**
	 * 资金流水
	 * 
	 * @param token token 
	 * @param pageInfo pageinfo
	 * @param type 类型
	 * @param types 多类型
	 */
	void findMoneyFlow(String uId, PageInfo pageInfo, Integer type, String types);

	/**
	 * 查询推荐码
	 * @param uId uId 
	 * @param request request
	 * @throws Exception 
	 * @return json
	 */
	JSONObject findUserReferralCode(String uId, HttpServletRequest request) throws Exception;

	/**
	 * 查询随心取交易流水
	 * 
	 * @param uid 用户id
	 * @param pId 产品id
	 * @param tId 交易id
	 * @param limit 条数
	 * @return pageinfo
	 */
	PageInfo findTakeHeartTransactionFlow(String uid, Integer pId, Integer tId, Integer limit);

	/**
	 * 查询随心取交易流水
	 * 
	 * @param uId 用户id
	 * @param pageInfo pageinfo
	 * @param tId 交易id
	 * @param yearmonth 时间 
	 * @return pageinfo
	 */
	PageInfo findTakeheartTransactionFlow(String uId, PageInfo pageInfo, Integer tId, String yearmonth);

	/**
	 * 查询用户冻结金额
	 * 
	 * @param token
	 *            token
	 */
	JSONObject getUserFreeingAmount(String uId);

    /**
     * 获取用户回款日历
     * 
     * @author yiban
     * @date 2018年3月15日 上午9:46:03
     * @version 1.0
     * @param uId 用户id
     * @param date 日期 yyyy-MM
     * @return pageinfo pageinfo
     * doneMoney 已回款金额  ， waitingMoney 待回款金额，doneDay 已回款日期， waitingDay 待回款日期 
     *
     */
    PageInfo getRepaymentCalendar(String uId, String date);

    /**
     * 获取陪伴天数
     * 
     * @author yiban
     * @date 2018年3月15日 上午11:10:15
     * @version 1.0
     * @param uId 用户id
     * @return pageinfo pageinfo
     *
     */
    PageInfo getAccompanyDays(String uId);

    /**
     * 查询冻结金额
     * 产生冻结金额  1.理财  account status 5 （支付成功）, 7（支付中） 金额为冻结中。 提现 account status 0 为冻结中。
     * @author yiban
     * @date 2018年3月18日 下午2:43:01
     * @version 1.0
     * @param uid
     * @param pageNo
     * @param pageSize
     * @return
     *
     */
    PageInfo listFrozenMoneyDetail(String uid, Integer pageNo, Integer pageSize);

    /**
     * 查询用户理财进程
     * accountOpen 开户
     * bindCard 绑卡激活
     * recharge 充值
     * buyProduct 购买产品
     * @param uId 用户uId
     * @param pageInfo
     */
	void queryUserFinProcess(String uId, PageInfo pageInfo);
}
