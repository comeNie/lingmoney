package com.mrbt.lingmoney.service.users;

import javax.servlet.http.HttpServletRequest;

import com.mrbt.lingmoney.utils.PageInfo;

/**
 * 用户相关基础接口
 * 
 * @author YJQ
 *
 */
public interface UsersService {

	/**
	 * 用户注册
	 * 
	 * @author YJQ 2017年4月12日
	 * @param telephone 手机号
	 * @param password 密码
	 * @param invitationCode 推荐人code
	 * @param verificationCode 验证码
	 * @param path 
	 * @param channel 渠道号
	 * @return pageInfo 
	 * @throws Exception 
	 */
	PageInfo regist(String telephone, String password, String invitationCode, String verificationCode, String path,
			Integer channel) throws Exception;

	/**
	 * web端登录验证
	 * 
	 * @author YJQ 2017年5月19日
	 * @param account 账号
	 * @return pageinfo
	 * @throws Exception 
	 */
	PageInfo verifyLogin(String account) throws Exception;

	/**
	 * 用户登录
	 * 
	 * @author YJQ 2017年4月12日
	 * @param request req
	 * @param account 账号
	 * @param password 密码
	 * @return pageinfo
	 * @throws Exception 
	 */
	PageInfo loginForWeb(HttpServletRequest request, String account, String password) throws Exception;

	/**
	 * 移动端登录
	 * 
	 * @author YJQ 2017年5月18日
	 * @param request req
	 * @param account 账号
	 * @param password 密码
	 * @return pageinfo
	 * @throws Exception 
	 */
	PageInfo loginForMobile(HttpServletRequest request, String account, String password) throws Exception;

	/**
	 * 退出登录
	 * 
	 * @author YJQ 2017年4月27日
	 * @param key redis中uid的key
	 * @return pageinfo
	 * @throws Exception 
	 */
	PageInfo logout(String key) throws Exception;

	/**
	 * 实名认证
	 * 
	 * @author YJQ 2017年4月14日
	 * @param key redis中uid的key
	 * @param name 姓名
	 * @param idCard 身份证号
	 * @return pageinfo
	 * @throws Exception 
	 */
	PageInfo userVerified(String token, String uId, String name, String idCard) throws Exception;

	/**
	 * 返回用户状态
	 * 
	 * @author YJQ 2017年4月14日
	 * @param key redis中uid的key
	 * @return pageinfo 
	 * @throws Exception 
	 */
	PageInfo queryUserStatus(String uId) throws Exception;

	/**
	 * 查询用户信息
	 * 
	 * @author YJQ 2017年5月18日
	 * @param key redis中uid的key
	 * @return pageinfo
	 * @throws Exception 
	 */
	PageInfo queryUsersInfo(String key) throws Exception;

	/**
	 * 修改用户友盟推送token
	 * 
	 * @author YJQ 2017年5月24日
	 * @param key redis中uid的key
	 * @param deviceToken 
	 * @param deviceType 
	 * @return pageinfo
	 * @throws Exception 
	 */
	PageInfo updateUserDeviceToken(String uId, String deviceToken, Integer deviceType) throws Exception;

	    /**
     * 京东绑卡--获取验证码
     * 
     * @param uid 用户id
     * @param tel 手机号
     * @param name 姓名
     * @param idCard 身份证号
     * @param number 卡号
     * @param bankShort 银行简称
     * @return 绑卡结果
     */
	PageInfo getJDBindCardSecurityCode(String uid, String tel, String name, String idCard, String number,
			String bankShort);

	/**
	 * 京东绑卡--申请绑卡
	 * 
	 * @param uid 用户id
	 * @param idCard 身份证
	 * @param name 姓名
	 * @param number 卡号
	 * @param tel 手机号
	 * @param bankShort 银行简称
	 * @param msgcode 验证码
	 * @return pageinfo
	 */
	PageInfo jdBindCardCertification(String uid, String idCard, String name, String number, String tel,
			String bankShort, String msgcode);

	/**
	 * 绑卡支持银行列表
	 * 
	 * @return pageinfo
	 */
	PageInfo listSupportBank();

	/**
	 * 根据用户ID查询已绑定的京东银行卡
	 * 
	 * @param uid 用户id
	 * @return pageinfo
	 */
	PageInfo listJDBankCard(String uid);

	/**
	 * 返回红包信息 by 实际赠送金额
	 * 
	 * @author YJQ 2017年7月4日
	 * @param pageInfo 
	 * @param actualAmount 实际金额
	 * @return pageinfo
	 * @throws Exception 
	 */
	PageInfo queryRedPackage(PageInfo pageInfo, Double actualAmount) throws Exception;

	/**
	 * 返回红包信息 by uid
	 * 
	 * @author YJQ 2017年7月4日
	 * @param pageInfo 
	 * @param key redis中uid的key
	 * @return pageinfo
	 * @throws Exception 
	 */
	PageInfo queryRedPackage(PageInfo pageInfo, String uId) throws Exception;

	/**
	 * 用户购买产品状态
	 * 
	 * @param uId 用户id
	 * @return pageinfo
	 */
	PageInfo userBuyState(String uId);

    /**
     * 新手特供活动pc端url
     * 
     * @param priority
     *            优先级。0，普通活动；1，新手特供活动（首页）；2，新手特供活动（理财列表页）；3，新手特供活动（购买页）
     * @return obj 为map类型，包含标题和链接地址
     * @throws Exception
     */
    PageInfo activityNovicePcUrl(int priority);

	/**
	 * 验证用户密码
	 * @author YJQ  2017年8月7日
	 * @param key redis中uid的key
	 * @param pwd 密码
	 * @return pageInfo
	 * @throws Exception 
	 */
	PageInfo verifyUserPwd(String uId, String pwd) throws Exception;

    /**
     * 保存或更新风险测评分数
     * 
     * @author yiban
     * @date 2018年3月23日 上午11:35:00
     * @version 1.0
     * @param uid 用户id
     * @param score 分数
     *
     */
    void saveOrUpdateAssessmentResult(String uid, Integer score);

    /**
     * 查询测评结果
     * 
     * @author yiban
     * @date 2018年3月24日 下午12:42:17
     * @version 1.0
     * @param uid
     * @return
     *
     */
    Integer hasTestRisAssessment(String uid);
}
