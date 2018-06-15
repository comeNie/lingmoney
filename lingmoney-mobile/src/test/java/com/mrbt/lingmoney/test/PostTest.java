package com.mrbt.lingmoney.test;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.net.util.Base64;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.mrbt.lingmoney.unit.CrawlerWebSousePost;
import com.mrbt.lingmoney.utils.AES256Encryption;

import net.sf.json.JSONObject;

/**
 * 手机端接口测试工具类
 * @author Administrator
 *
 */
public class PostTest {
	
	private static final Logger LOGGER = LogManager.getLogger(PostTest.class);

//	 private static String urlHeard = "http://app2.lingmoney.cn/";
	private static String urlHeard = "http://103.235.254.243:8021";
//	 private static String urlHeard = "http://10.10.20.96:8013";
	 
	 private static String token = "8a69b95c82214a50b56044009fa9f0d498076bc449a24557bb5219baeaaddb19";

	public static void main(String[] args) {

		PostTest pt = new PostTest();
		JSONObject paramsJson = new JSONObject();
		String url = "";

		//解密
//		String str = "YfAjq8macaNOSpCw9OyPar9aZQ2zTX+wwSflWc+RkvER30hfxxRtKBuRBq3DW/J6aBl7JaeGmTDBeZHEhZCbWg==";
//		String decStr = AES256Encryption.aes256Decrypt(str);
//		System.out.println(decStr);
		
		//加密
//		paramsJson.put("token", "2a148a0dfbb24633990236a2955e1842491dbc5e01b44e6d9c30e7d8c629beb8");
//		paramsJson.put("activitiesKey", "bankCustody");
//		paramsJson.put("needLogin", "N");
//		System.out.println(AES256Encryption.aes256Encrypt(paramsJson.toString()));
		
		// 登录
//		url = urlHeard + "/users/login";
//		paramsJson.put("account", "13683173295");
//		paramsJson.put("password", "000000");
//		pt.resquestPost(url, paramsJson);
		
		//查询用户优惠券的个数
		url = urlHeard + "/activityReward/showCouponReminding";
		paramsJson.put("token", token);
		pt.resquestPost(url, paramsJson);
		
		//获取爱心值
//		url = urlHeard + "/commonweal/achieveLove";
//		paramsJson.put("token", token);
//		pt.resquestPost(url, paramsJson);
		
		//获取合同数据接口
//		url = urlHeard + "/tradContract/getPdfPath";
//		paramsJson.put("token", token);
//		paramsJson.put("tId", 20999);
//		pt.resquestPost(url, paramsJson);

		//获取具体用户(未查看)优惠券内容信息
//		url = urlHeard + "/appRedPacket/redPacketInfo";
//		paramsJson.put("token", token);
//		pt.resquestPost(url, paramsJson);
		
		//生成请求开立账户加密数据，需要先执行人登录
//		paramsJson.put("token", token);
//		paramsJson.put("acName", "喻龙");
//		paramsJson.put("idNo", "210181198412136131");
//		paramsJson.put("mobile", "13683173295");
//		System.out.println(AES256Encryption.aes256Encrypt(paramsJson.toString()));
		
		//查询用户资金流水
//		url = urlHeard + "/userfinance/moneyFlow";
//		paramsJson.put("token", token);
//		paramsJson.put("pageNo", 1);
//		paramsJson.put("pageSize", 5);
//		paramsJson.put("type", 2);
//		pt.resquestPost(url, paramsJson);
		
//		url = urlHeard + "/appRedPacket/isDisplayInfo";
//		pt.resquestPost(url, paramsJson);
//		
		//投标
//		paramsJson.put("token", token);
//		paramsJson.put("tId", 17472);
//		System.out.println(AES256Encryption.aes256Encrypt(paramsJson.toString()));
		
		//判断用户开通E账户状态
//		url = urlHeard + "/bank/accountOpenStatus";
//		paramsJson.put("token", token);
//		pt.resquestPost(url, paramsJson);
		
		//查询用户E账户信息
//		url = urlHeard + "/bank/userHxAccout";
//		paramsJson.put("token", token);
//		pt.resquestPost(url, paramsJson);
		
		//预期收益
//		url = urlHeard + "/userfinance/expectEarnings";
//		paramsJson.put("token", token);
//		pt.resquestPost(url, paramsJson);
		
		//当前理财
//		url = urlHeard + "/userfinance/currentFinance";
//		paramsJson.put("token", token);
//		pt.resquestPost(url, paramsJson);
		
		//用户余额
//		url = urlHeard + "/userfinance/getUserBalance";
//		paramsJson.put("token", token);
//		pt.resquestPost(url, paramsJson);
		
		//查询用户的红包/加息券
//		url = urlHeard + "/userfinance/redPacket";
//		paramsJson.put("token", token);
//		paramsJson.put("pageNo", 1);
//		paramsJson.put("pageSize", 5);
//		paramsJson.put("type", 0);
//		paramsJson.put("hrp_type", 1);
//		pt.resquestPost(url, paramsJson);
		
		//查询可用的红包/加息券
//		url = urlHeard + "/userfinance/queryFinancialAvailableRedPacket";
//		paramsJson.put("token", token);
//		paramsJson.put("pCode", "41001603011514");
//		paramsJson.put("buyMoney", 1);
//		pt.resquestPost(url, paramsJson);
		
		
//		url = urlHeard + "/activity/activityList";
//		paramsJson.put("pageNo", 1);
//		paramsJson.put("pageSize", 5);
//		pt.resquestPost(url, paramsJson);
		
//		url = urlHeard + "/product/tradingRecordList";
//		paramsJson.put("account", "13683173295");
//		paramsJson.put("password", "123456");
//		pt.resquestPost(url, paramsJson);
		
		//绑卡数据加密
//		paramsJson.put("token", token);
//		System.out.println(AES256Encryption.aes256Encrypt(paramsJson.toString()));
		
		//账户充值
//		paramsJson.put("token", token);
//		paramsJson.put("amount", 10);
//		System.out.println(AES256Encryption.aes256Encrypt(paramsJson.toString()));
		
		//账户充值查询
//		url = urlHeard + "/bank/queryAccountRecharge";
//		paramsJson.put("token", token);
//		paramsJson.put("number", "P2P20220180309092ODVR9G803WY");
//		pt.resquestPost(url, paramsJson);
		
		//自动投标授权
//		paramsJson.put("token", token);
//		paramsJson.put("remark", "自动投标授权");
//		System.out.println(AES256Encryption.aes256Encrypt(paramsJson.toString()));
		
		//提现
//		paramsJson.put("token", token);
//		paramsJson.put("amount", 100);
//		System.out.println(AES256Encryption.aes256Encrypt(paramsJson.toString()));
		
		//债权转让
//		paramsJson.put("token", token);
//		paramsJson.put("amount", 10000);
//		paramsJson.put("remark", "发起债权转让");
//		paramsJson.put("pCode", "41001603011028");
//		System.out.println(AES256Encryption.aes256Encrypt(paramsJson.toString()));

		// 查询新手特供活动
//		 url = urlHeard + "/activity/activityNovice";
//		 paramsJson.put("needLogin", "Y");
//		 paramsJson.put("token", token);
//		 paramsJson.put("priority", "Y");
//		 paramsJson.put("type", "Y");
//		 pt.resquestPost(url, paramsJson);

		// 首页弹框提示
//		url = urlHeard + "/alertPrompt/alertPromptInfo";
//		paramsJson.put("type", "1");
//		paramsJson.put("needLogin", "Y");
//		paramsJson.put("token", token);
//		pt.resquestPost(url, paramsJson);

		// url = urlHeard + "/users/jdBindCardGetSecurityCode";
		// paramsJson.put("token", token);
		// paramsJson.put("tel", "15846350394");
		// paramsJson.put("name", "耿志海");
		// paramsJson.put("idCard", "232101199302155233");
		// paramsJson.put("number", "6217001140010060199");
		// paramsJson.put("bankShort", "CMBC");
		// pt.resquestPost(url, paramsJson);

		// 我的理财
//		 url = urlHeard + "/userfinance/myFinance";
//		 paramsJson.put("token", token);
//		 paramsJson.put("pageNo", "1");
//		 paramsJson.put("status", 3);
//		 paramsJson.put("pageSize", "10");
//		 pt.resquestPost(url, paramsJson);

		// 发送注册短信验证码
//		 url = urlHeard + "/sms/sendreg";
//		 paramsJson.put("phone", "13683173299");
//		 paramsJson.put("picKey", "111111");
//		 paramsJson.put("code", "1l1c");
//		 pt.resquestPost(url, paramsJson);

		// 发送忘记秘密短信验证码
		// url = urlHeard + "/sms/sendModiPw";
		// paramsJson.put("phone", "13683173299");
		// pt.resquestPost(url, paramsJson);

		// 修改登录手机号
		// url = urlHeard + "/sms/sendModiPhone";
		// paramsJson.put("origialPhone", "13683a173295");
		// paramsJson.put("targetPhone", "13683173295");
		// paramsJson.put("token", token);
		// pt.resquestPost(url, paramsJson);

		// 查询发送短信验证码
		// url = urlHeard + "/sms/querySmsVerify";
		// paramsJson.put("phone", "13683173288");
		// paramsJson.put("type", 1);
		// pt.resquestPost(url, paramsJson);

		// 验证短信验证码,verifyCode是上一个接口查询到的验证码
		// url = urlHeard + "/sms/verify";
		// paramsJson.put("phone", "13683173288");
		// paramsJson.put("verifyCode", "185476");
		// pt.resquestPost(url, paramsJson);

		// 请求客户端首页banner
//		 url = urlHeard + "/bannerInfo/homeMainBanner";
//		 paramsJson.put("sizeCode", "4");
//		 paramsJson.put("cityCode", "000");
//		 pt.resquestPost(url, paramsJson);

		// url = urlHeard + "/appbase/bootImage";
		// paramsJson.put("sizeCode", "0");
		// paramsJson.put("cityCode", "000");
		// pt.resquestPost(url, paramsJson);

		// 验证图片验证码
		// url = urlHeard + "/commonset/verPicCode";
		// paramsJson.put("picKey", "111111");
		// paramsJson.put("code", "1l1c");
		// pt.resquestPost(url, paramsJson);

//		 url = urlHeard + "/appbase/update";
//		 paramsJson.put("version", "3.5");
//		 paramsJson.put("type", 0);
//		 pt.resquestPost(url, paramsJson);

		// 首页产品
//		 url = urlHeard + "/product/homeProduct";
//		 paramsJson.put("cityCode", "131");
//		 paramsJson.put("type", "0");
//		 paramsJson.put("needLogin", "Y");
//		 paramsJson.put("token", token);
//		 pt.resquestPost(url, paramsJson);
		
//		url = urlHeard + "/product/queryProductType";
//		pt.resquestPost(url, paramsJson);

		// 产品详情
//		 url = urlHeard + "/product/productDesc";
//		 paramsJson.put("proId", 2538);
//		 System.out.println(AES256Encryption.aes256Encrypt(paramsJson.toString()));
//		 pt.resquestPost(url, paramsJson);

		// 产品项目信息
		// url = urlHeard + "/product/productInfo";
		// System.out.println(AES256Encryption.aes256Encrypt(paramsJson.toString()));
		// pt.resquestPost(url, paramsJson);

		// 查询产品列表
//		 url = urlHeard + "/product/productList";
//		 paramsJson.put("status", 1);
//		 pt.resquestPost(url, paramsJson);

		// 查询理财课堂列表
		// url = urlHeard + "/financialClass/getList";
		// paramsJson.put("pageNo", 1);
		// paramsJson.put("pageSize", 10);
		// pt.resquestPost(url, paramsJson);

		// 查询理财课堂详情
		// url = urlHeard + "/financialClass/getDetailById";
		// paramsJson.put("id", 158);
		// pt.resquestPost(url, paramsJson);

		// 查询推荐我的人
		// url = urlHeard + "/users/recommendMe";
		// paramsJson.put("token", token);

		// 查询我推荐的人
		// url = urlHeard + "/myDiscover/getUserAccount";
		// paramsJson.put("token", token);
		// paramsJson.put("acName", "张小小");
		// paramsJson.put("idNo", "210181198412136131");
		// pt.resquestPost(url, paramsJson);

		// url = urlHeard + "/myDiscover/getUserAccount";
		// paramsJson.put("token", token);
		// pt.resquestPost(url, paramsJson);

		// 活动列表
		// url = urlHeard + "/activity/activityList";
		// pt.resquestPost(url, paramsJson);

		// 活动内页
		// url = urlHeard + "/activity/activityInfo";
		// paramsJson.put("activityId", 192);
		// paramsJson.put("activityUrl", "invitation");
		// pt.resquestPost(url, paramsJson);

		// 通过banner跳转活动页面（h5）
		// url = urlHeard + "/activity/activityShow";
		// paramsJson.put("activityUrl", "invitation");
		// paramsJson.put("needLogin", "N");
		// paramsJson.put("token", token);
		// pt.resquestPost(url, paramsJson);

		// url = urlHeard + "/users/modifyAvatar";
		// paramsJson.put("token", token);
		// paramsJson.put("image", readImage());
		// pt.resquestPost(url, paramsJson);

		// 单笔提现
		// url = urlHeard + "/bank/cashWithdraw";
		// paramsJson.put("amount", "1000");
		// paramsJson.put("token", token);
		// pt.resquestPost(url, paramsJson);

		// url = urlHeard + "/bank/queryAccountOpen";
		// paramsJson.put("token", token);
		// pt.resquestPost(url, paramsJson);

		// paramsJson.put("token", token);
		// paramsJson.put("amount", "100.00");
		// System.out.println(AES256Encryption.aes256Encrypt(paramsJson.toString()));

		// 购买产品，生成订单
//		 url = urlHeard + "/purchase/buyProduct";
//		 paramsJson.put("token",  token);
//		 paramsJson.put("pCode", "41001603011546");
//		 paramsJson.put("buyMoney", 1);
//		 pt.resquestPost(url, paramsJson);
		
//		 url = urlHeard + "/purchase/checkQuickPayMentFirst";
//		 paramsJson.put("token",  token);
//		 paramsJson.put("tid", 17445);
//		 paramsJson.put("pCode", "41001603011573");
//		 paramsJson.put("usersBankId", 4435);
//		 pt.resquestPost(url, paramsJson);

		// 单笔投标
		// url = urlHeard + "/bank/singleBidding";
		// paramsJson.put("tId", "11179");
		// paramsJson.put("token", token);
		// pt.resquestPost(url, paramsJson);

		// 测试购买返回
		// url = urlHeard + "/bank/singleBiddingCallBack";
		// paramsJson.put("tId", "11039");
		// paramsJson.put("type", "4");
		// paramsJson.put("token", token);
		// System.out.println(AES256Encryption.aes256Encrypt(paramsJson.toString()));
		// pt.resquestPost(url, paramsJson);

		// 取消支付
		// url = urlHeard + "/purchase/cancelPay";
		// paramsJson.put("token", token);
		// paramsJson.put("tId", "11039");
		// System.out.println(AES256Encryption.aes256Encrypt(paramsJson.toString()));
		// pt.resquestPost(url, paramsJson);
		//

		// 查询未支付订单
		// url = urlHeard + "/purchase/unPayOrder";
		// paramsJson.put("token", token);
		// System.out.println(AES256Encryption.aes256Encrypt(paramsJson.toString()));
		// pt.resquestPost(url, paramsJson);
		
		// 查询预期收益
//		 url = urlHeard + "/userfinance/expectEarnings";
//		 paramsJson.put("token", token);
//		 pt.resquestPost(url, paramsJson);
		
		// 用户注册弹出奖励图片
//		 url = urlHeard + "/appRedPacket/isDisplayInfo";
//		 pt.resquestPost(url, paramsJson);

	}

	public static String readImage() {
		InputStream is;
		try {
			is = new BufferedInputStream(new FileInputStream("E:/b0348535-d83d-4fda-affd-58010d5225c7.jpg"));
			return Base64.encodeBase64String(input2byte(is));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static final byte[] input2byte(InputStream inStream) throws IOException {
		ByteArrayOutputStream swapStream = new ByteArrayOutputStream();
		byte[] buff = new byte[100];
		int rc = 0;
		while ((rc = inStream.read(buff, 0, 100)) > 0) {
			swapStream.write(buff, 0, rc);
		}
		byte[] in2b = swapStream.toByteArray();
		return in2b;
	}

	/**
	 * 发送url请求
	 * 
	 * @param url
	 * @param paramsJson
	 */
	private void resquestPost(String url, JSONObject paramsJson) {
		Map<String, String> params = new HashMap<String, String>();
		params.put("params", AES256Encryption.aes256Encrypt(paramsJson.toString()));
		System.out.println("请求参数[" + params + "]");
		try {
			String xml = CrawlerWebSousePost.post(url, params);
			System.out.println("请求结果[" + xml + "]");
			System.out.println("请求结果大小[" + xml.length() + "]");

			String decStr = AES256Encryption.aes256Decrypt(JSONObject.fromObject(xml).getString("result"));

			System.out.println("解密后的结果(未格式化)");
			System.out.println("----------------------------------------------------------------------------");
			System.out.println(decStr);
			System.out.println("----------------------------------------------------------------------------");
			System.out.println("解密后的结果(格式化)");
			System.out.println("----------------------------------------------------------------------------");
			System.out.println(formatJson(decStr));
			System.out.println("----------------------------------------------------------------------------");

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 格式化
	 * 
	 * @param jsonStr
	 * @return
	 * @author lizhgb
	 * @Date 2015-10-14 下午1:17:35
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
	 * 
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
