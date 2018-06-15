package com.mrbt.lingmoney.utils;

/**
 * 常量类
 * 
 * @author lzp
 *
 */
public class AppCons {

	public static final String hardUrl = ProductUtils.getContent("webHardUrl");
    public static final String hardUrlApp = ProductUtils.getContent("appHardUrl");
	/**
	 * 设置银行维护写入redis前缀
	 */
	public static final String MAINTAIN = "LINGQIAN";

	/**
	 * 京东在线支付通知URL前缀
	 */
	public static final String JD_ONLINE_NOTITY = hardUrl + "/purchase/onlineNotity/";

	/**
	 * 京东快捷支付通知url
	 */
	public static final String QUICK_NOTICE = hardUrl + "/purchase/quickNotice/";
    public static final String QUICK_NOTICE_APP = hardUrlApp + "/purchase/quickNotice/";
	/**
	 * 返回的商户页面
	 */
	public static final String JD_ONLINE_CALLBANK = hardUrl + "/purchase/onlineCallBank/";

	public static int DEFAULT_PAGE_SIZE = 10;
	public static String USER_PICTURE_DIR = "TUPAN";
	public static String COMPANY_URL = "http://www.lingmoney.cn/regist";
    public static String REFERRAL_URL = "http://app2.lingmoney.cn/infoInterface/recommend";

	// 跳转支付页面url
	public static final String PURCHASE_ACTIVE_URL = "/bank/singleBidding";

	// 跳转支付页面url
	public static final String PURCHASE_ACTIVE_URL_JD = "/purchase/active";

	// 收益类型0:固定收益类，1:浮动类
	public static int PROFIT_TYPE_0 = 0;
	public static int PROFIT_TYPE_1 = 1;
	// 0:无,1:固定不变，2:区间;
	public static int PROFIT_FIX_TYPE_0 = 0;
	public static int PROFIT_FIX_TYPE_1 = 1;
	public static int PROFIT_FIX_TYPE_2 = 2;

	public enum CostType {
		pre_cost(0, "前置费用收取"), after_cost(1, "后置费用收取");
		private int code;
		private CostType(int code, String msg) {
			this.code = code;
		}

		public int getCode() {
			return code;
		}

		public void setCode(int code) {
			this.code = code;
		}
	}

	/**
	 * 默认当前页为第一页
	 */
	public static int DEFAULT_PAGE_START = 1;
	/**
	 * 初始化用户密码 123456
	 */
	public static String DEFAULT_PSW = "123456";
	/**
	 * 默认的可删除的状态代码
	 */
	public static Integer DEFAULT_STATE = 1;
	/**
	 * 存入session的对象名称
	 */
	public static String SESSION_USER = "CURRENT_USER";
	public static String SESSION_USER_ID = "CURRENT_USER_ID";
	/**
	 * 页面级别的前缀
	 */
	public static String PAGE_PREFFIX = "rest/forward/";

	/**
	 * 交易的时间
	 */
	public static int TRAD_HOUR = 16;

	/**
	 * 无规则限制
	 */
	public static int RULE_NONE = 3;
	/**
	 * 金额限制
	 */
	public static int RULE_MONEY = 0;
	/**
	 * 时间限制
	 */
	public static int RULE_DATE = 1;
	/**
	 * 金额时间双重限制
	 */
	public static int RULE_MONEY_DATE = 2;
	/**
	 * 单位为月，value=1
	 */
	public static int UNIT_TIME_MONTH = 2;
	/**
	 * 单位为天，value=0
	 */
	public static int UNIT_TIME_DAY = 0;

	/**
	 * 单位为周，value=2
	 */
	public static int UNIT_TIME_WEEK = 1;

	/**
	 * 单位为无，value=3
	 */
	public static int UNIT_TIME_NONE = 4;
	/**
	 * 购买的状态 0
	 */
	public static int BUY_STATUS = 0;

	/**
	 * 买入的支付成功状态 1
	 */
	public static int BUY_OK = 1;
	/**
	 * 买入的支付冻结状态 4
	 */
	public static int BUY_FROKEN = 4;
	/**
	 * 买入的支付失败状态 5
	 */
	public static int BUY_FAIL = 5;

	/**
	 * 预购买 VALUE=6
	 */
	public static int BUY_BEFOREHAND = 6;

	/**
	 * 已撤标 8
	 */
	public static int BUY_CANCEL_BID = 8;

	/**
	 * 已流标 9
	 */
	public static int BUY_LOSS_BID = 9;

	/**
	 * 支付中12
	 */
	public static int BUY_TRADING = 12;

	/**
	 * 京东支付超时  17
	 */
	public static int BUY_JD_OVERTIME = 17;
	
	/**
	 * 订单失效 18
	 */
	public static int BUY_CANCEL = 18;

	/**
	 * 卖出的状态2
	 */
	public static int SELL_STATUS = 2;

	/**
	 * 卖出的支付成功状态3
	 */
	public static int SELL_OK = 3;

    /**
     * 兑付等待中
     */
    public static int SELL_WAITING = 15;

	/**
	 * 固定收益类的标志 value=0
	 */
	public static int FIX_FLAG = 0;
	/**
	 * 固定类产品子类型为固定不变 value=1
	 */
	public static int FIX_FLAG_SUB_FIX = 1;

	/**
	 * 固定类产品子类型为区间 value=2
	 */
	public static int FIX_FLAG_SUB_AREA = 2;
	/**
	 * 浮动收益类的标志
	 */
	public static int FLOAT_FLAG = 1;

	/**
	 * 默认的产品分类类型(浮动)
	 */
	public static int DEFAULT_TYPE = 1;
	/**
	 * 收取佣金标志，value=1
	 */
	public static int FEES_COST = 1;
	/**
	 * 不收取佣金标志，value=0
	 */
	public static int FEES_NONE = 0;
	/**
	 * 审批通过的产品 value=2
	 */
	public static int PRODUCT_APPROVAL_OK = 2;
	/**
	 * 审批提交的产品 value=1
	 */
	public static int PRODUCT_APPROVAL_SUTMIT = 1;

	/**
	 * 审批初始化的产品 value=0
	 */
	public static int PRODUCT_APPROVAL_INIT = 0;
	/**
	 * 提交状态的产品
	 */
	public static int PRODUCT_APPROVAL_SUBMIT = 1;
	/**
	 * 审核通过的产品
	 */
	public static int PRODUCT_APPROVAL_PUBLISH = 2;
	/**
	 * 产品初始化 value=0
	 */
	public static int PRODUCT_STATUS_INIT = 0;
	/**
	 * 标的筹集期 value=1
	 */
	public static int PRODUCT_STATUS_READY = 1;
	/**
	 * 项目运行中/已放款 value=2
	 */
	public static int PRODUCT_STATUS_RUNNING = 2;
	/**
	 * 项目汇款中，产品到期，用户回款 3
	 */
	public static int PRODUCT_STATUS_REPAYMENT = 3;
	/**
	 * 项目已结束 value=4
	 */
	public static int PRODUCT_STATUS_FINISH = 4;
	/**
	 * 项目已作废 value=5
	 */
	public static int PRODUCT_STATUS_ABANDON = 5;
	/**
	 * 筹集金额未达标 value=6
	 */
	public static int PRODUCT_STATUS_COLLECT_FAIL = 6;
	/**
	 * 筹集金额已达标/等待申请放款 value=7
	 */
	public static int PRODUCT_STATUS_COLLECT_SUCCESS = 7;
	/**
	 * 流标申请中 value=8
	 */
	public static int PRODUCT_STATUS_APPLY_BIDDINGLOSS = 8;
	/**
	 * 已流标 value=9
	 */
	public static int PRODUCT_STATUS_BIDDINGLOSSED = 9;
	/**
	 * 放款申请中 value=10
	 */
	public static int PRODUCT_STATUS_APPLY_LENDDING = 10;
	/**
	 * 已确认自动还款 value=11
	 */
	public static int PRODUCT_STATUS_CONFIRM_AUTO_REPAYMENT = 11;

	/**
	 * 12 还款申请已提交
	 */
	public static int PRODUCT_STATUS_REPAYMENT_APPLY = 12;
	/**
	 * 13 还款成功
	 */
	public static int PRODUCT_STATUS_REPAYMENT_SUCCESS = 13;
	/**
	 * 14 还款收益明细已提交
	 */
	public static int PRODUCT_STATUS_INCOME_DETAIL_APPLY = 14;
	/**
	 * 15 还款收益明细提交成功
	 */
	public static int PRODUCT_STATUS_INCOME_DETAIL_SUCCESS = 15;

	/**
	 * 发布状态
	 */
	public static int PUBLIST_STATUS = 1;
	/**
	 * 导出成功 value=0
	 */
	public static int SELL_SUBMIT_EXCEL_EXPORT = 0;
	/**
	 * 导入成功 value=1
	 */
	public static int SELL_SUBMIT_EXCEL_IMPORT = 1;
	/**
	 * 还款中 value=2
	 */
	public static int SELL_SUBMIT_RETURN_PROCESS = 2;
	/**
	 * 还款成功 value=3
	 */
	public static int SELL_SUBMIT_RETURN_OK = 3;
	/**
	 * 还款失败 value=4
	 */
	public static int SELL_SUBMIT_RETURN_FAIL = 4;
	/**
	 * 还款失败描述文字 value=金额确认错误
	 */
	public static String SELL_SUBMIT_RETURN_FAIL_MSG = "金额确认错误";
	public static int LINT_BAO_FRIST_COUNT = 1000;
	public static int LINT_BAO_FRIST_COUNT_T = 500;

	public static String USER_BUY_REQUESTNO = "USER_BUY_REQUESTNO";
	public static String USER_ZHIC_REQUESTNO = "USER_ZHIC_REQUESTNO";
	public static String USER_TIANX_REQUESTNO = "USER_TIANX_REQUESTNO";
	// public static String USER_SELL_REQUESTNO ="USER_TIANX_REQUESTNO";

	public static String HOME_BIG_BANNER_INFO = "HOME_BIG_BANNER_INFO";
	public static String HOMR_BANNER_INFRO = "HOMR_BANNER_INFRO";
	public static String HOMR_USER_ASK = "HOMR_USER_ASK";
	public static String HOMR_MEDIA = "HOMR_MEDIA";
	public static String HOMR_NOTICE = "HOMR_NOTICE";
	public static String HOMR_LISTX = "HOMR_LISTX";
	public static String HOMR_LISTY = "HOMR_LISTY";
	public static String USER_NAME_PREFIX = "lingqian";

	public static String HOME_CARTOONCATEGORY = "HOME_CARTOONCATEGORY";
	public static String HOME_CARTOONCONTENT = "HOME_CARTOONCONTENT";
	public static String HOME_FINANCIALMANAGEMENT = "HOME_FINANCIALMANAGEMENT";
	public static String HOMELIST_FINANCIALMANAGEMENT = "HOMELIST_FINANCIALMANAGEMENT";

	public static String pay_certificate_path = Thread.currentThread().getContextClassLoader().getResource("").getPath()
			+ "hk1001001@test.com.p12.pfx";
	public static String pay_certificate_password = "123qwe";
	public static String pay_certificate_String = "yeepay.com";

	/*
	 * 注册阶段：
	 * 
	 * 1、您正在注册领钱儿，您的短信验证码是 XXXXX，为了保障您的账号安全，验证短信请勿泄露给其他人，此验证码一分钟内有效，请尽快使用。【领钱儿】
	 * 
	 * 2、尊敬的XXX（此为用户名），欢迎您注册领钱儿。领钱儿已将1000领宝放入您的账户中，快去查收吧。关注微信（lingqianer888）
	 * 接收消息更便捷 。【领钱儿】
	 * 
	 * 
	 * 充值提醒：
	 * 
	 * 【领钱儿】亲爱的XXX，您刚刚在领钱儿进行了充值，金额为XX元。如非本人操作，请致电400-005-1655或联系微信客服(
	 * lingqianer888 )。
	 * 
	 * 
	 * 购买提醒：
	 * 
	 * 【领钱儿】亲爱的XXX，您刚刚成功认购XXX(产品名称)，认购金额XXXX元。如有疑问，请致电400-005-1655或微信客服(
	 * lingqianer888)。
	 * 
	 * 赎回提醒：
	 * 
	 * 【领钱儿】亲爱的XXX，您在XXX年X月X日于领钱儿申请的XXX（产品）已赎回，金额为XX元。如非本人操作，请致电400-005-1655。
	 */
	//public static String regist_content_y = "您正在注册领钱儿，您的短信验证码是{0}，为了保障您的账号安全，验证短信请勿泄露给其他人，此验证码{1}分钟内有效，请尽快使用。";
	public static String regist_content_y="亲爱的用户，您正在注册领钱儿，您的验证码是{0}，为了保证您的账号安全，请勿将验证码信息泄露给他人，此验证码{1}分钟内有效，请尽快使用。";
	public static String regist_content = "欢迎您注册领钱儿。领钱儿已将1000领宝放入您的账户中，快去查收吧。";

	public static String chongzhi_content = "亲爱的{0}，您刚刚在领钱儿进行了充值，金额为{1}元。如非本人操作，请致电400-005-1655或联系微信客服(lingqianer888)。";
	//public static String chongzhi_content = "尊敬的{0}女士/先生，您于【变量】成功充值{1}元，感谢您选择领钱儿，祝您理财愉快。如有疑问请咨询400-0051-655。";
	
	//public static String buy_content = "亲爱的{0}，您刚刚成功认购{1}，认购金额{2}元。如有疑问，请致电400-005-1655或微信客服(lingqianer888)。";
	public static String buy_content = "尊敬的{0}女士/先生，您已成功认购{1}产品，产品期限为{2}天，认购金额为{3}元。";
	
	public static String sell_content = "亲爱的{0}，您在{1}于领钱儿申请的{2}已赎回，金额为{3}元。如非本人操作，请致电400-005-1655。";
	public static String password_content_y = "您的短信验证码是{0}，为了保障您的账号安全，验证短信请勿泄露给其他人，此验证码{1}分钟内有效，请尽快使用。";

	public static String fist_tianx = "fist_tianx";

	public static String authentication = "authentication";

	public static String fist_chongzhi = "fist_chongzhi";
	public static String password_code = "password_code";
	public static String login_error_verification = "login_error_verification_";
	public static String login_error_verification_ip = "login_error_verification_ip_";

	//public static String regist_content_new = "尊敬的{0}，您好！恭喜您成功注册“领钱儿”网理财平台。为提升您的账户安全，请尽快完成实名认证吧！";
	public static String regist_content_new = "尊敬的{0}，您好！恭喜您成功注册“领钱儿”网理财平台！";
	
	public static String certification_content = "恭喜您已完成实名认证，领钱儿已将100领宝放入您的账户中，快去看看吧！";

	public static String untreated_request_no = "untreated_request_no";

	public static String reward_redpacket = "亲爱的{0}，您在认购{1}中，使用了{2}元反现红包，现已存入您在领钱儿的账户中，请注意查收！";
    
	/*新加短信信息—提现*/
	public static String cash_withdrawal ="尊敬的{0}女士/先生，您已成功提现{1}元，欢迎再次使用，祝您生活愉快！如需帮助详情咨询400-0051-655。";
	
	/*新加站内信*/
	public static String registered_successfully="尊敬的{0}先生/女士，您好！您已成功注册“领钱儿”网理财平台！恭喜您获得千元红包！继续完成华兴银行E账户开通、绑卡激活E账户、首笔充值理财等行为，还可获得加息礼包。领钱儿已顺利接入银行存管系统，更安心、更便捷的移动理财方式等着您来体验。感谢您对领钱儿一直以来的关注与支持！祝您生活愉快！";//注册成功
	public static String tied_card_successfully="尊敬的{0}先生/女士，恭喜您绑卡成功，成为领钱儿最尊贵的正式用户！领钱儿已将加息券放入您的卡券中，赶快使用吧！感谢您对领钱儿一直以来的关注与支持！祝您生活愉快！";//绑卡成功
	public static String recharge="尊敬的{0}女士/先生，您于{1}成功充值{2}元，感谢您选择领钱儿，祝您理财愉快。如有疑问请咨询400-0051-655。感谢您对领钱儿一直以来的关注与支持！祝您生活愉快！";//充值
	public static String purchasing="尊敬的{0}女士/先生，您已成功认购领钱儿{1}产品，产品期限为{2}，认购金额为{3}，项目到期时，理财金额和预期年化收益会自动转入您的账户中，祝您理财愉快。感谢您对领钱儿一直以来的关注与支持！祝您生活愉快！";//购买
	public static String cash_withdrawal_successfully="尊敬的{0}女士/先生，您已成功提现{1}元，此次提现过程无任何手续费，欢迎您再次选择领钱儿，祝您生活顺利、日进斗金！如需帮助请详询400-0051-655感谢您对领钱儿一直以来的关注与支持！祝您生活愉快！";//提现成功

	/**
	 * 期间注册送礼活动
	 */
	public static String REGIST_ACTIVE_START = "2016-08-09 00:00:00";
	public static String REGIST_ACTIVE_END = "2016-10-09 24:00:00";

	/**
	 * 修改手机号
	 */
	public static String CHANGE_PHONE_CONTENT = "尊敬的{0}，您正在更换注册手机号码，您的短信验证码是{1}，为了保障您的账号安全，验证短信请勿泄露给其他人，此验证码{2}分钟内有效，请尽快使用。";
	public static String CHANGE_PHONE_ORIGINAL = "original";
	public static String CHANGE_PHONE_RECENT = "recent";

	// REDIS 前缀
	/**
	 * 防止用户连点redis前缀
	 */
	public static String REPEAT_CLICK = "repeatClick_";// 后面加 token或sessionid

	/**
	 * 抽奖概率，抽奖也展示使用，第二天0点过期
	 */
	public static String DAY_LOTTERY_RATIO = "dayLotteryRatio_";
	/**
	 * 当前用户购物车数量，六小时后过期
	 */
	public static String GIFT_CART_NUMBER = "giftCartNumber_";
	/**
	 * 购物车限额
	 */
	public static String CARTCOUNT_REDIS_KEY = "cartcount";
	/**
	 * 签到记录，第二天0点过期
	 */
	public static String SINGIN_REDIS_KEY = "checkin_";
	/**
	 * 短信发送
	 */
	public static String SMSMESSAGE_KEY_HEAD = "smsmessage:data:";
	/**
	 * 发送验证邮件验证码
	 */
	public static String SEND_EMAIL_CODE = "sendEmail_";
	/**
	 * 发送验证邮件验证码
	 */
	public static String SEND_MODIFY_TELE = "modifyTele_";
	/**
	 * token验证
	 */
	public static String TOKEN_VERIFY = "token_";
	/**
	 * token验证
	 */
	public static String UID_VERIFY = "uid_";
	/**
	 * 京东绑卡开关 存放数据为 String类型 可用为Y 不可用为N或者NULL
	 */
	public static String JD_BINDCARD_AVALIABLE = "jdBindcardAvailable";
	/**
	 * 用户登录后首次进入我的领钱儿 标识
	 */
	public static String AFTERLOGIN_FIRST_IN_MYLINGQIAN = "firstInMylingqianAfterLogin_";
	/**
	 * 注册最后送红包时间
	 */
	public static String LAST_REGIST_REDPACKET_TIME = "lastRegistRedpacketTime";
	/**
	 * 520返现红包
	 */
	public static String FIVE_TWO_ZERO_REDPACKET = "fiveTwoZeroPedpacket";

	/**
	 * 融资人的个人信息 0
	 */
	public static int USER_FINANCING_P = 0;
	/**
	 * 融资人的企业信息 1
	 */
	public static int USER_FINANCING_E = 1;
	/**
	 * 融资人已经验证
	 */
	public static int USER_FINANCING_STATUS_OK = 1;
	/**
	 * 是否自动交易 否 value=0
	 */
	public static int AUTO_PAY_FALSE = 0;
	/**
	 * 是否自动交易 是 value=1
	 */
	public static int AUTO_PAY_TRUE = 1;

	/**
	 * 购物车数量限制
	 */
	public static final int SHOP_CART_LIMIT = 20;
	/**
	 * 随心取产品CODE
	 */
	public static final String TAKE_HEART_PCODE = "12001704020391";
	/**
	 * 新手标产品CODE
	 */
	public static final String New_Buyer_PCODE = "12001704020170";
	/**
	 * 随心取标识，用户赎回时reids判断
	 */
	public static final String TAKE_HEART = "takeheart.";
	/**
	 * 产品模式为钱包模式
	 */
	public static int PRODUCT_P_MODE_WALLET = 1;
	/**
	 * 钱包流水表中的交易类型，买入类型(0)
	 */
	public static int WALLET_FLOW_TYPE_BUY = 0;

	/**
	 * 钱包流水表中的交易类型，卖出类型(1)
	 */
	public static int WALLET_FLOW_TYPE_SELL = 1;

	/**
	 * 钱包流水表中的交易类型，付息类型(2)
	 */
	public static int WALLET_FLOW_TYPE_INTEREST = 2;

	/**
	 * 钱包流水表中的交易状态，失败状态(0)
	 */
	public static int WALLET_FLOW_STATE_FAIL = 0;

	/**
	 * 钱包流水表中的交易状态，成功状态(1)
	 */
	public static int WALLET_FLOW_STATE_SUCCESS = 1;

	/**
	 * 钱包流水表中的交易状态，待处理状态(2)
	 */
	public static int WALLET_FLOW_STATE_PROCESS = 2;

	/**
	 * 钱包流水表中的交易状态，冻结状态(3)
	 */
	public static int WALLET_FLOW_STATE_FROKEN = 3;
	/**
	 * 钱包模式的部分赎回(value=0)
	 */
	public static int WALLET_SELL_PART = 0;
	/**
	 * 钱包模式的全部赎回(value=1)
	 */
	public static int WALLET_SELL_ALL = 1;
	/**
	 * 自动扫钱的标志位
	 */
	public static int WALLET_BATCH_FLAG = 1;
	/**
	 * 大额约束条款的key
	 */
	public static String WALLET_BIGSELL_CONSTRA_KEY = "lingqian.wallet.big.";

    //======================account_flow.status=============================
    /**
     * 0，处理中
     */
    public static final int ACCOUNT_FLOW_HANDELING = 0;
    /**
     * 1 已完成
     */
    public static final int ACCOUNT_FLOW_FINISH = 1;
    /**
     * 2 处理失败
     */
    public static final int ACCOUNT_FLOW_FAIL = 2;
    /**
     * 3 撤标成功
     */
    public static final int ACCOUNT_FLOW_BIDDING_CANCEL = 3;
    /**
     * 4 流标成功
     */
    public static final int ACCOUNT_FLOW_BIDDING_LOSS = 4;
    /**
     * 5 冻结中
     */
    public static final int ACCOUNT_FLOW_FROZEN = 5;
    /**
     * 6 取消支付
     */
    public static final int ACCOUNT_FLOW_CANCEL_PAYMENT = 6;
    /**
     * 7 支付中
     */
    public static final int ACCOUNT_FLOW_DURING_PAYEMENT = 7;
    /**
     * 8 待支付
     */
    public static final int ACCOUNT_FLOW_WAITING_PAYMENT = 8;
    //======================account_flow.status=============================

    //======================account_flow.type===============================
    /**
     * 0 充值
     */
    public static final int ACCOUNT_FLOW_TYPE_RECHARGE = 0;
    /**
     * 1 取现
     */
    public static final int ACCOUNT_FLOW_TYPE_WITHDRAW = 1;
    /**
     * 2 理财
     */
    public static final int ACCOUNT_FLOW_TYPE_FINANCIAL = 2;
    /**
     * 3 赎回
     */
    public static final int ACCOUNT_FLOW_TYPE_REDEEM = 3;
    /**
     * 5 还款
     */
    public static final int ACCOUNT_FLOW_TYPE_REPAYMENT = 5;
    /**
     * 6 放款
     */
    public static final int ACCOUNT_FLOW_TYPE_BANKLENDDING = 6;
    //======================account_flow.type===============================

    /**
     * 新手产品2018
     */
    public static final String NEW_PRODUCT_TYPE_ID = ProductUtils.getContent("new_buyer_product_typeid");

}
