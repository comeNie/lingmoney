package com.mrbt.lingmoney.utils;

import java.math.BigDecimal;

public class ResultParame {

	public static enum ResultInfo {
		
		/**
		 * 成功, 200
		 */
		SUCCESS("成功", 200),
		/**
		 * 请选择txt文件, 201
		 */
		TXT_FILE("请选择txt文件", 201),
		/**
		 * 交易处理失败, 207
		 */
		TRADING_FAIL("交易处理失败", 207),
		/**
		 * 交易处理失败, 207
		 */
		TRADING_FAIL2("交易处理失败", 208),
		/**
		 * 今日已签到, 251
		 */
		SIGN_SUCCESS("今日已签到", 251),
		/**
		 * 签到失败,   252
		 */
		SIGN_NOT_SUCCESS("签到失败", 252),
		/**
		 * 原购物车数量大于现有库存,   253
		 */
		BUYCOUNT_ERROE("原购物车数量大于现有库存", 253),
		/**
		 * 领宝不足,   254
		 */
		LINGBAO_INSUFFICIENT("领宝不足", 254),
		/**
		 * 该活动已下线,   255
		 */
		ACTIVITY_NOT_LINE("该活动已下线", 255),
		/**
		 * 抽奖信息有误,   256
		 */
		LUCK_DRAW_INFO_ERROR("抽奖信息有误", 256),
		/**
		 * 请选择收货地址,   257
		 */
		RECEIVING_ADDRESS("请选择收货地址", 257),
		/**
		 * 请选择收货地址,   258
		 */
		EXCHANGE_NOT_SUCCESS("请选择收货地址", 258),
		/**
		 * 产品已下架,   259
		 */
		PROJECT_NOT_LINE("产品已下架", 259),
		/**
		 * 请勿连续点击,   260
		 */
		NOT_CONTINUITY_CHECK("请勿连续点击", 260),
		/**
		 * 兑换超过限额,   261
		 */
		EXCHANGE_COUNT_ERROR("兑换超过限额", 261),
		/**
		 * 产品库存不足,   262
		 */
		PROJECT_INSUFFICIENT("产品库存不足", 262),
		/**
		 * 购物车已满,   263
		 */
		SHOPPING_CART_FULL("购物车已满", 263),
		/**
		 * 非兑换类产品,   264
		 */
		NOT_RECEIVING_PROJECT("非兑换类产品", 264),
		/**
		 * 中奖礼品不可删除,   265
		 */
		LUCK_PROJECT_NOT_DEL("中奖礼品不可删除", 265),
		/**
		 * 当前产品暂未开放认购,   270
		 */
		PROJECT_NOT_SHOPPING("当前产品暂未开放认购", 270),
		/**
		 * 当前产品暂已停止认购,   271
		 */
		PROJECT_STOP_SHOPPING("当前产品暂已停止认购", 271),
		/**
		 * 购买金额小于最低金额,   272
		 */
		AMOUNT_LESSTHAN_MIN_AMOUNT("购买金额小于最低金额", 272),
		/**
		 * 购买金额有误,   273
		 */
		AMOUNT_ERROR("购买金额有误", 273),
		/**
		 * 购买金额超过剩余可购金额,   274
		 */
		AMOUNT_THAN("购买金额超过剩余可购金额", 274),
		/**
		 * 该用户未实名认证,   275
		 */
		USERNAME_NOT_TRUE("该用户未实名认证", 275),
		/**
		 * 该用户未绑卡,   276
		 */
		USER_NOT_CRAD("该用户未绑卡", 276),
		/**
		 * 账户余额不足,   277
		 */
		USER_BALANCE_INSUFFICIENT("账户余额不足", 277),
		/**
		 * 购买金额超过剩余可购金额,   278
		 */
		BUYMONEY_OVERFLOW("购买金额超过剩余可购金额", 278),
		/**
		 * 购买失败（生成trading失败）,   280
		 */
		BUY_NOT_SUCCESS("购买失败（生成trading失败）", 280),
		/**
		 * 非稳赢宝产品,   281
		 */
		NOT_WENYINGBAO("非稳赢宝产品", 281),
		/**
		 * 更新数据失败 , 300
		 */
		UPDATE_FAIL("更新数据失败", 300),
		/**
		 * 产品状态有误 , 301
		 */
		PRODUCT_STATUS_FAIL("更新数据失败", 301),
		/**
		 * 两分钟内不可多次提交，请耐心等待 , 304
		 */
		SUBMIT_FREQUENTLY_IN_TWO_MIN("两分钟内不可多次提交，请耐心等待", 304),
		/**
		 * 非稳赢宝产品,   307
		 */
		BANK_CARD_ALREADY_BIND("该银行卡已被绑定", 307),
		/**
		 * 该身份证已被绑定,   309
		 */
		ID_CARD_ALREADY_BIND("该身份证已被绑定", 309),
		/**
		 * 服务器不理解请求的语法,   400
		 */
		NO_UNDERSTAND("服务器不理解请求的语法", 400), 
		/**
		 * 服务器找不到请求的网页,   404
		 */
		NOT_FOUND("服务器找不到请求的网页", 404),
		/**
		 * 服务器内部错误,   500
		 */
		SERVER_ERROR("服务器内部错误", 500),
		/**
		 * 参数错误,   501
		 */
		PARAMS_ERROR("参数错误", 501),
		/**
		 * 暂无数据, 502
		 */
		NOT_FOUND_DATA("暂无数据", 502),
		/**
		 * 请选择txt文件, 503
		 */
		CHOOSE_TXT("请选择txt文件", 503),
		/**
		 * 请勿重复交易, 504
		 */
		RECOMMIT("请勿重复交易", 504),
		/**
		 * 一分钟内不可多次获取验证码，请耐心等待, 505
		 */
		PLEASE_WAIT("一分钟内不可多次获取验证码，请耐心等待", 505),
		/**
		 * 参数缺失 1001
		 */
		PARAM_MISS("参数缺失", 1001),
		/**
		 * 请求json格式有误,   1002
		 */
		FORMAT_JSON_ERROR("请求json格式有误", 1002),
		/**
		 * 数据为空,   1003
		 */
		EMPTY_DATA("数据为空", 1003),
		/**
		 * 请求参数格式错误,   1004
		 */
		FORMAT_PARAMS_ERROR("请求参数格式错误", 1004),
		/**
		 * 跨域请求失败,   1005
		 */
		CROSS_REQUEST_ERROR("跨域请求失败", 1005),
		/**
		 * 重复请求,   1006
		 */
		REQUEST_AGAIN("重复请求", 1006),
		/**
		 * 京东绑卡不可用,   1007
		 */
		JINGDONG_CRAD_ERROR("京东绑卡不可用", 1007),
		/**
		 * 操作失败,   1008
		 */
		DO_FAIL("操作失败", 1008),
		/**
		 * 登录超时,   1009
		 */
		LOGIN_OVERTIME("登录超时", 1009),
		/**
		 * 返回数据为空,   2001
		 */
		RETURN_EMPTY_DATA("返回数据为空", 2001),
		/**
		 * 员工不存在, 2003
		 */
		EMPLOYEE_NON_EXISTENT("员工不存在", 2003),
		/**
		 * 员工没有注册零钱儿, 2004
		 */
		EMPLOYEE_NOT_REGISTER_LINGMOMEY("员工没有注册零钱儿", 2004),
		/**
		 * 员工已经注册了零钱儿, 2002
		 */
		EMPLOYEE_REGISTER_LINGMOMEY("员工已经注册了零钱儿", 2002),
		/**
		 * 身份证号不符, 2005
		 */
		IDCARD_ERROR("身份证号不符", 2005),
		/**
		 * 修改失败, 2007
		 */
		MODIFY_FAIL("修改失败", 2007),
		/**
		 * 提交参数有误, 3001
		 */
		SUBMIT_PARAMS_ERROR("提交参数有误", 3001),
		/**
		 * 用户账号已存在,   3002
		 */
		ACCOUNT_EXIST("用户账号已存在", 3002),
		/**
		 * 推荐码不存在,   3003
		 */
		RECOMMEND_NON_EXIST("推荐码不存在", 3003),
		/**
		 * 账号密码不匹配,   3004
		 */
		PASSWARD_ERROR("账号密码不匹配", 3004),
		/**
		 * 账号已作废,   3005
		 */
		ACCOUNT_NUMBER_VOID("账号已作废", 3005),
		/**
		 * 用户账号不存在,   3006
		 */
		ACCOUNT_NUMBER_NON_EXIST("用户账号不存在", 3006),
		/**
		 * "验证码错误或失效,   3007
		 */
		VERIFICATION_CODE_ERROR("验证码错误或失效", 3007),
		/**
		 * 本日验证次数超出限定,   3008
		 */
		VERIFICATION_COUNT_ERROR("本日验证次数超出限定", 3008),
		/**
		 * 登录超时,请重新登录,   3009
		 */
		LOGIN_TIMEOUT("登录超时,请重新登录", 3009),
		/**
		 * 用户未实名,   3010
		 */
		USER_NOT_REALNAME("用户未实名", 3010),
		/**
		 * 返回的数据为空,   3011
		 */
		RETURN_DATA_EMPTY_DATA("返回的数据为空", 3011),
		/**
		 * 未开通华兴E账户,   3012
		 */
		NOT_HX_ACCOUNT("未开通华兴E账户", 3012),
		/**
		 * 已开通未激活,   3013
		 */
		HX_ACCOUNT_NOT_ACTIVATION("已开通未激活", 3013),
		/**
		 * 已开通已激活,   3014
		 */
		HX_ACCOUNT_ACTIVATION("已开通已激活", 3014),
		/**
		 * 用户输错密码次数超过限制,   3015
		 */
		PASSWARD_COUNT_ERROR("用户输错密码次数超过限制", 3015),
		/**
		 * 邮箱重复,   3016
		 */
		RE_EMAIL("邮箱重复", 3016),
		/**
		 * 身份证错误,   3017
		 */
		ID_CRAD_ERROR("身份证错误", 3017),
		/**
		 * 身份证已经存在,   3018
		 */
		ID_CRAD_EXIST("身份证已经存在", 3018),
		/**
		 * 银行账号格式错误,   3019
		 */
		ACCOUNT_FORMAT_ERROR("银行账号格式错误", 3019),
		/**
		 * 银行账号已经存在,   3020
		 */
		ACCOUNT_FORMAT_EXIST("银行账号已经存在", 3020),
		/**
		 * 账号户名格式有误,   3021
		 */
		ACCOUNT_NAME_FORMAT_EXIST("账号户名格式有误", 3021),
		/**
		 * 处理中,   3022
		 */
		ING("处理中", 3022),
		/**
		 * 失败,   3023
		 */
		NO_SUCCESS("失败", 3023),
		/**
		 * 账户余额不足,   3024
		 */
		BALANCE_INSUFFICIENT("账户余额不足", 3024),
		/**
		 * 还有待支付产品", 3030
		 */
		HAVE_PROJECT_NOT_PAY("还有待支付产品", 3030),
		/**
		 * 账户与户名不匹配,   3031
		 */
		ACCOUNT_NAME_ERROR("账户与户名不匹配", 3031),
		/**
		 * 开通E账户，正在处理中,   3032
		 */
		E_ACCOUNT_ING("开通E账户，正在处理中", 3032),
		/**
		 * 合同不存在，3033
		 */
		CONTRACT_NO("合同暂未生成",3033),
		/**
		 * 银行升级维护中，具体恢复时间以华兴银行实际恢复时间为准。, 3034
		 */
		SYSTEM_UPDATE("银行升级维护中，具体恢复时间以华兴银行实际恢复时间为准。", 3034),
		/**
		 * 产品不存在, 4001
		 */
		PROJECT_NOT_EXIST("产品不存在", 4001),
		/**
		 * 随心取停止申购,   5001
		 */
		SUIXINQU_STOP_BUY("随心取停止申购", 5001),
		/**
		 * 订单不允许重复提交,   5002
		 */
		ORDER_NO_AGAIN_SUBMIT("订单不允许重复提交", 5002),
		/**
		 * 订单不存在,   5003
		 */
		ORDER_NOT_EXIST("订单不存在", 5003),
		/**
		 * 订单状态有误,   5004
		 */
		ORDER_STATUS_ERROR("订单状态有误", 5004),
		/**
		 * 交易成功,   5005
		 */
		TRADING_SUCCESS("交易成功", 5005),
		/**
		 * 交易失败,   5006
		 */
		TRADING_NOT_SUCCESS("交易失败", 5006),
		/**
		 * 交易异常，后续在对账文件中确认交易状态或线下人工处理,   5007
		 */
		TRADING_EXCPTION("交易异常，后续在对账文件中确认交易状态或线下人工处理", 5007),
		/**
		 * 订单已失效", 5008
		 */
		ORDER_INVALID("订单已失效", 5008),
		/**
		 * 支付成功,   5009
		 */
		PAYSUCCESS("支付成功", 5009),
		/**
		 * 支付失败,   5010
		 */
		PAY_NOT_SUCCESS("支付失败", 5010),
		/**
		 * 更新出错,   5011
		 */
		UPDATE_ERROR("更新出错", 5011),
		/**
		 * 交易信息不匹配,   5012
		 */
		ORDER_INFO_ERROR("交易信息不匹配", 5012),
		/**
		 * 取消订单失败,   5013
		 */
		ORDER_CANCEL_ERROR("取消订单失败", 5013),
		/**
		 * 产品不在筹集期,   5014
		 */
		PRODUCT_CANNOT_BUY("产品不在筹集期", 5014),
		/**
		 * 优惠券信息不符,   5015
		 */
        REDPACKET_UNSUITABLE("优惠券信息不符", 5015),
		/**
		 * 该优惠券无法使用,   5016
		 */
		REDPACKET_UNUSEABLE("该优惠券无法使用", 5016),
		/**
		 * 该优惠券不符合使用条件,   5017
		 */
		REDPACKET_UNVALID("该优惠券不符合使用条件", 5017),
		
		REDPACKET_UNVALID_F("该优惠券不符合使用条件，第二次购买才能使用哦！", 5017),
		
		/**
		 * 该优惠券已过期,   5018
		 */
		REDPACKET_OVERTIME("该优惠券已过期", 5018),
		/**
		 * 随心取卖出失败,   5100
		 */
		SUIXINQU_BUY_NOT_SUCCESS("随心取卖出失败", 5100),
		/**
		 * 借款编号不存在,   6001
		 */
		BORROW_NUMBER_NOT_EXIST("借款编号不存在", 6001),
		/**
		 * 标的不存在,   6002
		 */
		BIAODI_NOT_EXIST("标的不存在", 6002),
		/**
		 * 交易流水号不存在,   6003
		 */
		TRADING_NUMBER_NOT_EXIST("交易流水号不存在", 6003),
		/**
		 * 生成报文失败,   6004
		 */
		CRETE_MESSAGE_NOT_SUCCESS("生成报文失败", 6004),
		/**
		 * 银行返回报文失败,   6005
		 */
		RETURN_MESSAGE_NOT_SUCCESS("银行返回报文失败", 6005),
		/**
		 * 银行受理成功,   6006
		 */
		BANK_SUCCESS("银行受理成功", 6006),
		/**
		 * 银行受理失败,   6007
		 */
		BANK_NOT_SUCCESS("银行受理失败", 6007),
		/**
		 * 银行受理中,   6008
		 */
		BANK_ING("银行受理中", 6008),
		/**
		 * 银行受理超时,   6009
		 */
		BANK_TIMEOUT("银行受理超时", 6009),
		/**
		 * 请求银行处理失败,   6020
		 */
		BANK_REQUEST_NOT_SUCCESS("请求银行处理失败", 6020),
		/**
		 * 标的被撤销,   6021
		 */
		BIAODI_CANCAL("标的被撤销", 6021),
		/**
		 * 标的被流标,   6022
		 */
		BIAODI_RETURN("标的被流标", 6022),
		/**
		 * 标的已成立,   6023
		 */
		BIAODI_SUCCESS("标的已成立", 6023),
		/**
		 * 交易失败,   6024
		 */
		TRAD_NOT_SUCCESS("交易失败", 6024),
		/**
		 * 交易处理中,   6025
		 */
		TRAD_ING("交易处理中", 6025),
		/**
		 * 交易完成,   6026
		 */
		TRAD_COMPLATE("交易完成", 6026),
		/**
		 * 自动还款授权失败,   6027
		 */
		AUTOMATIC_BORROW_NOT_SUCCESS("自动还款授权失败", 6027),
		/**
		 * 未开通自动还款,   6028)
		 */
		NOT_AUTOMATIC_BORROW("未开通自动还款", 6028),
		/**
		 * 未到指定查询时间,   6029
		 */
		NOT_SELECT_TIME("未到指定查询时间", 6029),
		/**
		 * 交易结果未明,   6030
		 */
		NOT_TRADING_RESULT("交易结果未明", 6030),
		/**
		 * 标的未成立,   6031)
		 */
		BIAODI_NOT_FOUND("标的未成立", 6031),
		/**
		 * 应答了错误页面,   6032
		 */
		ANSWER_ERROR_HTML("应答了错误页面", 6032),
		/**
		 * 接口访问过于频繁，请稍后再访问", 6033
		 */
		WAIT_REQUEST("接口访问过于频繁，请稍后再访问", 6033),
		/**
		 * 银行正在处理上一次的请求，请稍候再试, 6034
		 */
		BANK_ING_WAIT("银行正在处理上一次的请求，请稍候再试", 6034),
		/**
		 * 自动还款已授权, 6035
		 */
		AUTOMATIC_BORROW("自动还款已授权", 6035),
		/**
		 * 数据已存在, 600
		 */
		DATA_EXISTED("数据已存在", 600),
		/**
		 * 不能修改数据, 250
		 */
		MODIFY_REJECT("不能修改数据", 250),
		/**
		 * 暂无数据, 3011
		 */
		NO_DATA("暂无数据", 3011),
		/**
		 * 参数错误, 3001
		 */
		PARAMETER_ERROR("参数错误", 3001),
		/**
		 * 用户登录已过期,3309
		 */
		LOGIN_FAILURE("用户登录已过期",3309),
		/**
		 * 用户超时,请重新登录, 600
		 */
		ADMIN_LOGO_OUTTIME("用户超时,请重新登录", 600),
		/**
		 * 交易不存在, 20012
		 */
		TWENTY_THOUSAND_AND_TWELVE("交易不存在", 20012),
		/**
		 * 不是已经买入的产品, 20032
		 */
		TWENTY_THOUSAND_AND_THIRTY_TWO("不是已经买入的产品", 20032),
		/**
		 * 赎回用户错误，非本用户, 20011
		 */
		TWENTY_THOUSAND_AND_ELEVEN("赎回用户错误，非本用户", 20011),
		/**
		 * 赎回类型错误, 20022
		 */
		TWENTY_THOUSAND_AND_TWENTY_TWO("赎回类型错误", 20022),
		/**
		 * 金额有误,请重新输入, 12000
		 */
		TWELVE_THOUSAND("金额有误,请重新输入", 12000),
		/**
		 * 卖出失败,理财金额小于卖出金额, 11000
		 */
		ELEVEN_THOUSAND("卖出失败,理财金额小于卖出金额", 11000),

		/**
		 * 标的与支付通道不一致
		 */
		TRADING_CHANNEL_ACCORD_ERROR("标的与支付通道不符，请选择正确的交易通道", 7000);
		;
		
		ResultInfo(String msg, int code) {
			this.msg = msg;
			this.code = code;
		}
		
		private final String msg;
		private final int code;
		
		public String getMsg() {
			return msg;
		}
		
		public int getCode() {
			return code;
		}
	}

	public static enum ResultNumber {
		 
		/**
		 * 常量整数：-1
		 */
		MINUS_ONE(-1),
		/**
		 * 常量整数：-3
		 */
		MINUS3(-3), 
		/**
		 * 常量整数：-2
		 */
		MINUS2(-2),
		/**
		 * 常量整数：0
		 */
		ZERO(0),
		/**
		 * 常量整数：1
		 */
		ONE(1),
		/**
		 * 常量整数：2
		 */
		TWO(2),
		/**
		 * 常量整数：3
		 */
		THREE(3),
		/**
		 * 常量整数：4
		 */
		FOUR(4),
		/**
		 * 常量整数：5
		 */
		FIVE(5),
		/**
		 * 常量整数：7
		 */
		SIX(6),
		/**
		 * 常量整数：7
		 */
		SEVEN(7),
		/**
		 * 常量整数：8
		 */
		EIGHT(8),
		/**
		 * 常量整数：16
		 */
		SIXTEEN(16) ,
		/**
		 * 常量整数：10
		 */
		TEN(10),
		/**
		 * 常量整数：20000
		 */
		EXPORT_MAX(20000),
		/**
		 * 常量整数：9
		 */
		NINE(9),
		/**
		 * 常量整数：12
		 */
		TWELVE(12),
		/**
		 * 常量整数：13
		 */
		THIRTEEN(13),
		/**
		 * 常量整数：14
		 */
		FOURTEEN(14),
		/**
		 * 常量整数：15
		 */
		FIFTEEN(15),
		/**
		 * 常量整数：17
		 */
		SEVENTEEN(17),
		/**
		 * 常量整数：18
		 */
		EIGHTEEN(18),
		/**
		 * 常量整数：20
		 */
		TWENTY(20),
		/**
		 * 常量整数：25
		 */
		TWENTY_FIVE(25),
		/**
		 * 常量整数：30
		 */
		THIRTY(30),
		/**
		 * 常量整数：160
		 */
		ONE_HUNDRED_AND_SIXTY(160),
		/**
		 * 常量整数：40
		 */
		FORTY(40),
		/**
		 * 常量整数：50
		 */
		FIFTY(50),
		/**
		 * 常量整数：60
		 */
		SIXTY(60),
		/**
		 * 常量整数：99
		 */
		NINETY_NINE(99),
		/**
		 * 常量整数：100
		 */
		ONE_HUNDRED(100),
		/**
		 * 常量整数：120
		 */
		ONE_HUNDRED_TWENTY(120),
		/**
		 * 常量整数：200
		 */
		TWO_HUNDRED(200),
		UPLOAD_FILE_SIZE(10240), 
		/**
		 * 常量整数：10000
		 */
		SET_CRAD_COUNT(10000),
		/**
		 * 常量整数：150
		 */
		ONE_HUNDRED_AND_FIFTY(150),
		/**
		 * 常量整数：400
		 */
		FOUR_HUNDRED(400),
		/**
		 * 常量整数：500
		 */
		FIVE_HUNDRED(500),
		/**
		 * 常量整数：1001
		 */
		ONE_ZERO_ZERO_ONE(1001),
		/**
		 * 常量整数：1003
		 */
		ONE_ZERO_ZERO_THREE(1003),
		/**
		 * 常量整数：1007
		 */
		ONE_ZERO_ZERO_SEVEN(1007),
		/**
		 * 常量整数：1024
		 */
		ONE_ZERO_TWO_FOUR(1024),
		/**
		 * 常量整数：128
		 */
		ONE_HUN_TWEN_TIGHT(128),
		/**
		 * 0xFF
		 */
		LXFF(0xFF),
		/**
		 * 常量整数：302
		 */
		THREE_ZERO_TWO(302),
		/**
		 * 常量整数：365
		 */
		THREE_SIXTY_FIVE(365),
		/**
		 * 常量整数：1000
		 */
		ONE_THOUSAND(1000),
		/**
		 * 常量整数：2000
		 */
		TWO_THOUSAND(2000),
		/**
		 * 常量整数：3000
		 */
		THREE_THOUSAND(3000),
		/**
		 * 常量整数：3031
		 */
		THREE_ZERO_THREE_ONE(3031),
		/**
		 * 常量整数：3500
		 */
		THREE_THOUSAND_AND_FIVE_HUNDRED(3500),
		/**
		 * 常量整数：4000
		 */
		FOUR_THOUSAND(4000),
		/**
		 * 常量整数：4500
		 */
		FOUR_THOUSAND_AND_FIVE_HUNDRED(4500),
		/**
		 * 常量整数：5000
		 */
		FIVE_THOUSAND(5000),
		/**
		 * 常量整数：8192
		 */
		EIGHT_ONE_NINETY_TWO(8192),
		/**
		 * 常量整数：540026
		 */
		FIVE_FOUR_ZERO_ZERO_TWO_SIX(540026),

		;

		
		ResultNumber(int number){
			this.number = number;
		}
		
		private final int number;
		
		public int getNumber() {
			return number;
		}
	}

	public static enum ResultBigDecimal {
		
		/**
		 * 常量整数：0.005
		 */
		ZPZZF(new BigDecimal("0.005")),
		
		/**
		 * 常量整数：0.01
		 */
		ZERO_POINT_ZERO_ONE(new BigDecimal("0.01")),
		/**
		 * 常量整数：0.01
		 */
		ZERO_POINT_ZERO_TWO_FIVE(new BigDecimal("0.025"));
		
		private final BigDecimal bigDec;
		
		ResultBigDecimal(BigDecimal bigDec){
			this.bigDec = bigDec;
		}
		
		public BigDecimal getBigDec() {
			return bigDec;
		}
	}
}
