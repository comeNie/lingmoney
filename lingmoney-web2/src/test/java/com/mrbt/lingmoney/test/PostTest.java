package com.mrbt.lingmoney.test;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;import org.apache.commons.collections.set.TypedSortedSet;

/**
 * wap端接口测试工具类
 * @author Administrator
 *
 */
public class PostTest {
	
	/**
	 * 访问服务器地址
	 */
	private static final String URL_HEARD = "http://10.10.12.36:8080";
//	private static final String URL_HEARD = "http://10.20.1.88:2013";
//	private static final String URL_HEARD = "http://www2.lingmoney.cn";
	
	private static final PostTest pt = new PostTest();
	private static final Map<String, Object> params = new HashMap<String, Object>();
	private static final Map<String, Object> headers = new HashMap<String, Object>();
	private static String url = "";
	
	public static void main(String[] args) {
		headers.put("Accept", "application/json, text/javascript, */*; q=0.01");
		headers.put("Accept-Encoding", "gzip, deflate");
		headers.put("Accept-Language", "zh-CN,zh;q=0.9");
		headers.put("Connection", "keep-alive");
		headers.put("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
		headers.put("Cookie", "JSESSIONID=1627D567E627F7330DB08F59CFA27958");
		headers.put("Host", "10.10.12.36:8080");
		headers.put("Origin", "http://10.10.12.36:8080");
		headers.put("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/66.0.3359.27 Safari/537.36");
		headers.put("X-Requested-With", "XMLHttpRequest");
		//生成图形验证码：浏览器直接访问 
//		System.out.println(URL_HEARD + "/commonsetVerify/pictureCode?picKey=11111111111");
		//验证图形验证码
//		pt.verPicCode("11111111111", "9enc");//验证图片验证码
//		pt.verifyTelephone("13811888301");//验证用户手机号
//		pt.sendReg("13811888301", "11111111111", "ev8t");//获取注册短信验证码
//		pt.register("13811888301","qwe123", "18801199949", "105782", 1);//注册提交
//		pt.registerAndLogin("13811888301","qwe123", "18801199949", "105782", 1);//注册提交并登录
		
//		pt.login("13683173295", "000000");//登录
//		pt.queryUserStatus();//获取登录状态
//		pt.loginOut();//登出
		
//		pt.queryPcHomeBanner("000");//查询首页BANNER
//		pt.queryTotalData();//查询首页累计数据
//		pt.queryInfoNoticeList(1, 3);//查询公告列表
//		pt.queyrinfoNoticeDetail(197);//查询公告详情
//		pt.queryListNewerProduct(1, 1);//查询新手标产品
//		pt.queryProductList("000", 1, 10, null, 1, null, null);//产品列表
//		pt.queryProductType();//产品类型
//		pt.queryProductBaseInfo(2472);//产品基本信息
//		pt.queryproductDesc(2505);//查询产品详情
//		pt.queryProductInfo(2505);//获取产品信息
//		pt.queryProductDetailInfoData(2505);//获取项目详情数据
//		pt.tradingRecordListVersionOne(2505, 1, 10);//获取单个产品理财记录
		pt.queryFinancialAvailableRedPacket("", 1000.00);//查询产品可用优惠券
		
		//用户首页相关接口
//		pt.accountOpenStatus();//查询用户开通E账户状态
//		pt.queryUserFinProcess();//查询用户理财进程
//		pt.queryUserBalance();//查询用户余额, 累计理财金额, 累计收益
//		pt.currentFinance();//当前理财详情
//		pt.userHxAccout();//查询华兴E账户
		
//		pt.moneyFlow(1, 10, null, "");//获取资金流水（交易记录）0：充值，1：提现，2：理财，3：赎回, 4:奖励， 5:还款，6：放款
		
//		pt.myFinance(1, 10, 1, "");//我的理财      0:买入，1支付成功，2卖出，3卖出成功, 4 资金冻结，5支付失败，7退回, 12支付中
//		pt.financeDetail(23919);//理财详情
//		pt.hasMessage();//用户是否有消息
//		pt.userMessage(1, 10);//用户消息列表
//		pt.messageDetail(30625);//消息详情
//		pt.redPacket(3, 1, 1, 10);//查询用户优惠券信息  0：未使用、未过期， 1，已使用，2，已过期
//		pt.getUserInfo();//获取用户信息
		
//		pt.popularActivityList();
//		pt.onlineCallBack();
	}

	public void queryFinancialAvailableRedPacket(String pCode, Double buyMoney) {
		url = URL_HEARD + "/product/queryFinancialAvailableRedPacket";
		params.put("pCode", pCode);
		params.put("buyMoney", buyMoney);
		pt.resquestPost(url, params);
	}


	public void tradingRecordListVersionOne(Integer proId, Integer pageNo, Integer pageSize) {
		url = URL_HEARD + "/product/tradingRecordListVersionOne";
		params.put("pageNo", pageNo);
		params.put("pageSize", pageSize);
		params.put("proId", proId);
		pt.resquestPost(url, params);
	}


	public void getUserInfo() {
		url = URL_HEARD + "/users/queryUserAccountSetInfo";
		pt.resquestPost(url, params);
	}

	public void redPacket(Integer type, Integer hrpType, Integer pageNo, Integer pageSize) {
		url = URL_HEARD + "/userfinance/redPacket";
		params.put("pageNo", pageNo);
		params.put("pageSize", pageSize);
		params.put("type", type);
		params.put("hrpType", hrpType);
		pt.resquestPost(url, params);
	}

	public void messageDetail(Integer mId) {
		url = URL_HEARD + "/userfinance/messageDetail";
		params.put("mId", mId);
		pt.resquestPost(url, params);
	}

	public void userMessage(Integer pageNo, Integer pageSize) {
		url = URL_HEARD + "/userfinance/userMessage";
		params.put("pageNo", pageNo);
		params.put("pageSize", pageSize);
		pt.resquestPost(url, params);
	}

	public void hasMessage() {
		url = URL_HEARD + "/userfinance/hasMessage";
		pt.resquestPost(url, params);
	}

	public void financeDetail(Integer tId) {
		url = URL_HEARD + "/userfinance/financeDetail";
		params.put("tId", tId);
		pt.resquestPost(url, params);
	}

	public void myFinance(Integer pageNo, Integer pageSize, Integer status, String multyStatus) {
		url = URL_HEARD + "/userfinance/myFinance";
		params.put("pageNo", pageNo);
		params.put("pageSize", pageSize);
		if (status != null) {
			params.put("status", status);
		}
		if (multyStatus != null ) {
			params.put("multyStatus", multyStatus);
		}
		pt.resquestPost(url, params);
	}

	public void moneyFlow(Integer pageNo, Integer pageSize, Integer type, String types) {
		url = URL_HEARD + "/userfinance/moneyFlow";
		params.put("pageNo", pageNo);
		params.put("pageSize", pageSize);
		if (type != null) {
			params.put("type", type);
		}
		if (types != null ) {
			params.put("multyTypes", types);
		}
		pt.resquestPost(url, params);
	}

	public void userHxAccout() {
		url = URL_HEARD + "/bank/userHxAccout";
		pt.resquestPost(url, params);
	}

	public void currentFinance() {
		url = URL_HEARD + "/userfinance/currentFinance";
		pt.resquestPost(url, params);
	}

	public void queryUserBalance() {
		url = URL_HEARD + "/userfinance/getUserBalance";
		pt.resquestPost(url, params);
	}

	public void queryUserFinProcess() {
		url = URL_HEARD + "/userfinance/userFinProcess";
		pt.resquestPost(url, params);
	}

	public void accountOpenStatus() {
		url = URL_HEARD + "/bank/accountOpenStatus";
		pt.resquestPost(url, params);
	}

	public void queryProductType() {
		url = URL_HEARD + "/product/queryProductType";
		pt.resquestPost(url, params);
	}
	
	public void queryProductDetailInfoData(Integer pId) {
		url = URL_HEARD + "/product/productDetailInfoData";
		params.put("pId", pId);
		pt.resquestPost(url, params);
	}

	public void queryProductBaseInfo(int proId) {
		url = URL_HEARD + "/product/productBaseInfo";
		params.put("proId", proId);
		pt.resquestPost(url, params);
	}
	
	public void queryproductDesc(Integer proId) {
		url = URL_HEARD + "/product/productDesc";
		params.put("proId", proId);
		pt.resquestPost(url, params);
	}

	public void queryProductInfo(int proId) {
		url = URL_HEARD + "/product/productInfo";
		params.put("proId", proId);
		pt.resquestPost(url, params);
		
	}

	public void queryProductList(String cityCode, Integer pageNo, Integer pageSize, Integer pcId, Integer status, Integer startTime,Integer endTime) {
		url = URL_HEARD + "/product/productList";
		params.put("pageNo", pageNo);
		params.put("pageSize", pageSize);
		params.put("cityCode", cityCode);
		if (pcId != null) {
			params.put("pcId", pcId);
		}
		if (status != null) {
			params.put("status", status);
		}
		if (startTime != null) {
			params.put("startTime", startTime);
		}
		if (endTime != null) {
			params.put("endTime", endTime);
		}
		pt.resquestPost(url, params);
		
	}

	public void queryListNewerProduct(int pageNo, int pageSize) {
		url = URL_HEARD + "/product/listNewerProduct";
		params.put("pageNo", pageNo);
		params.put("pageSize", pageSize);
		pt.resquestPost(url, params);
	}

	public void queyrinfoNoticeDetail(int id) {
		url = URL_HEARD + "/info/queryNoticeDetail";
		params.put("id", id);
		pt.resquestPost(url, params);
	}

	public void queryInfoNoticeList(int pageNo, int pageSize) {
		url = URL_HEARD + "/info/queryNoticeList";
		params.put("pageNo", pageNo);
		params.put("pageSize", pageSize);
		pt.resquestPost(url, params);
	}

	public void queryTotalData() {
		url = URL_HEARD + "/home/queryTotal";
		pt.resquestPost(url, params);
	}

	public void queryPcHomeBanner(String cityCode) {
		url = URL_HEARD + "/bannerInfo/homeMainBanner";
		params.put("cityCode", cityCode);
		pt.resquestPost(url, params);
		
	}

	public void queryUserStatus() {
		url = URL_HEARD + "/users/queryUserStatus";
		pt.resquestPost(url, params);
	}

	public void sendReg(String phone, String picKey, String code) {
		url = URL_HEARD + "/sms/sendreg";
		params.put("phone", phone);
		params.put("picKey", picKey);
		params.put("code", code);
		pt.resquestPost(url, params);
	}

	public void verifyTelephone(String telephone) {
		url = URL_HEARD + "/usersVerify/verifyTelephone";
		params.put("telephone", telephone);
		pt.resquestPost(url, params);
	}
	
	/**
	 * 用户注册,并登录
	 * @param i 
	 * @param verificationCode 
	 * @param invitationCode 
	 * @param password 
	 * @param telephone 
	 */
	public void registerAndLogin(String telephone, String password, String invitationCode, String verificationCode, int channel) {
		url = URL_HEARD + "/usersVerify/regist";
		params.put("telephone", telephone);
		params.put("password", password);
		params.put("invitationCode", invitationCode);
		params.put("verificationCode", verificationCode);
		params.put("channel", channel);
		pt.resquestPost(url, params);
		
	}

	/**
	 * 用户注册
	 * @param i 
	 * @param verificationCode 
	 * @param invitationCode 
	 * @param password 
	 * @param telephone 
	 */
	public void register(String telephone, String password, String invitationCode, String verificationCode, int channel) {
		url = URL_HEARD + "/users/register";
		params.put("telephone", telephone);
		params.put("password", password);
		params.put("invitationCode", invitationCode);
		params.put("verificationCode", verificationCode);
		params.put("channel", channel);
		pt.resquestPost(url, params);
		
	}

	/**
	 * 验证图形验证码
	 * @param picKey
	 * @param code
	 */
	public void verPicCode(String picKey, String code) {
		url = URL_HEARD + "/commonsetVerify/verPicCode";
		params.put("picKey", picKey);
		params.put("code", code);
		pt.resquestPost(url, params);
	}

	public void onlineCallBack() {
//		url = URL_HEARD + "/purchase/onlineNotity/2974_28557_0FB0CBBA4BBBD06CB5D84F8E3683D586";
//		url = URL_HEARD + "/purchase/onlineCallBank/2974_28557_0FB0CBBA4BBBD06CB5D84F8E3683D586";
		url = URL_HEARD + "/purchase/quickNotice/8bea49a87a41a1e521515387725632_17370_17370_0FB0CBBA4BBBD06CB5D84F8E3683D586";
		params.put("v_oid", "20180108-110226189002-0961519ef4acb09861515366069109");
		params.put("v_pstatus", 20);
		params.put("v_amount", 1.00);
		params.put("v_md5str", "AE6662E26BCF0A66F53D4FAFEDAA636E");
		pt.resquestPost(url, params);
	}
	
	/**
	 * 热门活动列表
	 */
	public void popularActivityList() {
		url = URL_HEARD + "/activity/activityList";
		params.put("pageNo", 1);
		params.put("pageSize", 10);
		pt.resquestPost(url, params);
	}
	
	/**
	 * 用户退出
	 */
	public void loginOut() {
		url = URL_HEARD + "/users/logout";
		pt.resquestPost(url, params);
	}
	/**
	 * 用户登录
	 * @param password 
	 * @param account 
	 */
	public void login(String account, String password) {
		url = URL_HEARD + "/users/login";
		params.put("account", account);
		params.put("password", password);
		pt.resquestPost(url, params);
	}

	/**
	 * 发送url请求
	 * @param url
	 * @param paramsJson
	 */
	private void resquestPost(String url, Map<String, Object> params) {
		String reString;
		try {
			reString = HttpClientUtil.httpPostRequest(url, headers, params);
			System.out.println(reString);
//			System.out.println(formatJson(reString));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 格式化 json
	 * @param jsonStr	json字符串
	 * @return	字符串
	 */
	public static String formatJson(String jsonStr) {
		if (null == jsonStr || "".equals(jsonStr))
			return "";
		StringBuilder sb = new StringBuilder();
		char last = '\0';
		char current = '\0';
		int indent = 0;
		for (int i = 0; i < jsonStr.length(); i++) {
			last = current;
			current = jsonStr.charAt(i);
			switch (current) {
			case '{':
			case '[':
				sb.append(current);
				sb.append('\n');
				indent++;
				addIndentBlank(sb, indent);
				break;
			case '}':
			case ']':
				sb.append('\n');
				indent--;
				addIndentBlank(sb, indent);
				sb.append(current);
				break;
			case ',':
				sb.append(current);
				if (last != '\\') {
					sb.append('\n');
					addIndentBlank(sb, indent);
				}
				break;
			default:
				sb.append(current);
			}
		}
		return sb.toString();
	}

	/**
	 * 添加space
	 * @param sb
	 * @param indent
	 * @author lizhgb
	 * @Date 2015-10-14 上午10:38:04
	 */
	private static void addIndentBlank(StringBuilder sb, int indent) {
		for (int i = 0; i < indent; i++) {
			sb.append("  ");
		}
	}
}

