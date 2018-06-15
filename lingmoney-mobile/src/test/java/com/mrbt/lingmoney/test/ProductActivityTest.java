package com.mrbt.lingmoney.test;

import java.util.HashMap;
import java.util.Map;
import org.junit.Test;

import com.mrbt.lingmoney.unit.CrawlerWebSousePost;
import com.mrbt.lingmoney.utils.AES256Encryption;

import net.sf.json.JSONObject;

/**
 * 产品和活动接口压力测试
 * 
 * @author lihq
 * @date 2017年4月27日 下午4:05:06
 * @description
 * @version 1.0
 * @since 2017-04-12
 */
public class ProductActivityTest {

	public static int count = 0;
	private static String urlHeard = "http://192.168.1.146:8080";

	@Test
	public void testMultithreading() {
		// TODO 测试

		try {
			int x = 100;// 访问的总次数
			int y = 100;// 启动的线程个数

			while (x > 0) {
				x--;
				JSONObject paramsJson = new JSONObject();
				
				// TODO 测试首页产品
				String[] tokens = { "45b0307f749843a9a6d719c1f3cd583cc8f7ccba7d94455da25aeb250df04968",
						"45b0307f749843a9a6d719c1f3cd583cc8f7ccba7d94455da25aeb250df04968",
						"45b0307f749843a9a6d719c1f3cd583cc8f7ccba7d94455da25aeb250df04968",
						"45b0307f749843a9a6d719c1f3cd583cc8f7ccba7d94455da25aeb250df04968" };
				int roundNum = (int) Math.round(Math.random() * (tokens.length - 1));
				paramsJson.put("token", tokens[roundNum]);
				paramsJson.put("needLogin", "Y");
				String url = urlHeard + "/product/homeProduct";
				
				// TODO 测试单个产品信息
//				int proId = (int) (Math.random() * 950 + 53);
//				paramsJson.put("proId", proId);
//				String url = urlHeard + "/product/productList";
				
				// TODO 测试产品列表
//				int status = new Random().nextInt(4);
//				String[] cityCodes = { "000", "131", "236", "179" };
//				String cityCode = cityCodes[new Random().nextInt(4)];
//				int pageNo = new Random().nextInt(5);
//				int pageSize = new Random().nextInt(5) + 5;
//				paramsJson.put("status", status);
//				paramsJson.put("cityCode", cityCode);
//				paramsJson.put("pageNo", pageNo);
//				paramsJson.put("pageSize", pageSize);
//				String url = urlHeard + "/product/productList";
				
				// TODO 测试单个产品详情
//				int proId = (int) (Math.random() * 950 + 53);
//				paramsJson.put("proId", proId);
//				String url = urlHeard + "/product/productDesc";
				
				// TODO 测试单个产品图片
//				int proId = (int) (Math.random() * 950 + 53);
//				paramsJson.put("proId", proId);
//				String url = urlHeard + "/product/productPic";
				
				// TODO 测试产品投资记录
//				int proId = (int) (Math.random() * 950 + 53);
//				int pageNo = new Random().nextInt(5);
//				int pageSize = new Random().nextInt(5) + 5;
//				paramsJson.put("proId", proId);
//				paramsJson.put("pageNo", pageNo);
//				paramsJson.put("pageSize", pageSize);
//				String url = urlHeard + "/product/tradingRecordList";
				
				// TODO 测试活动列表
//				int pageNo = new Random().nextInt(5);
//				int pageSize = new Random().nextInt(5) + 5;
//				paramsJson.put("pageNo", pageNo);
//				paramsJson.put("pageSize", pageSize);
//				String url = urlHeard + "/activity/activityList";
				
				// TODO 测试活动内页
//				int activityId = (int) (Math.random() * 4 + 157);
//				paramsJson.put("activityId", activityId);
//				String url = urlHeard + "/activity/activityInfo";

				// TODO 测试活动产品说明
//				int activityId = (int) (Math.random() * 4 + 157);
//				int productIndex = (int) (Math.random() * 2) + 1;
//				paramsJson.put("activityId", activityId);
//				paramsJson.put("productIndex", productIndex);
//				String url = urlHeard + "/activity/activityDesc";

				// TODO 测试拉新活动统计
//				String[] tokens = { "45b0307f749843a9a6d719c1f3cd583cc8f7ccba7d94455da25aeb250df04968",
//						"45b0307f749843a9a6d719c1f3cd583cc8f7ccba7d94455da25aeb250df04968",
//						"45b0307f749843a9a6d719c1f3cd583cc8f7ccba7d94455da25aeb250df04968",
//						"45b0307f749843a9a6d719c1f3cd583cc8f7ccba7d94455da25aeb250df04968" };
//				int roundNum = (int) Math.round(Math.random() * (tokens.length - 1));
//				int judgeStatus = (int) Math.round(Math.random());
//				paramsJson.put("token", tokens[roundNum]);
//				paramsJson.put("judgeStatus", judgeStatus);
//				String url = urlHeard + "/activity/activityRecommend";

				Thread t = new ProductRun(url, paramsJson);
				t.start();
				while (Thread.activeCount() > y) {
					Thread.sleep(1);
				}
				t.join();

			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		String content = "H%2F5etqn%2B88OhbAo%2FnUvgthK0CMrUFTlBdMVdnCx7fePmDU14qCx0o3FMvpYJkkV%2B";
		System.out.println("加密：" + content);
		System.out.println("解密：" + AES256Encryption.aes256Decrypt(content));
//		String content2 = "940E0D4C5CFB303E623B3B39721FF7B6";
//		System.out.println("解密：" + content2);
//		System.out.println("加密：" + AES256Encryption.aes256Encrypt(content2));

		Map<String, Object> params = new HashMap<String, Object>();
		JSONObject paramsJson = new JSONObject();
		paramsJson.put("token", "1234");
		paramsJson.put("needLogin", "Y");
		params.put("params", AES256Encryption.aes256Encrypt(paramsJson.toString()));
//		System.out.println(params.get("params"));
		
		
	}

}

class ProductRun extends Thread {

	private String url;// 请求url
	private JSONObject paramsJson;// 参数

	public ProductRun(String url, JSONObject paramsJson) {
		this.url = url;
		this.paramsJson = paramsJson;
	}

	public void run() {
		resquestPost(url, paramsJson);
	}

	/**
	 * 发送url请求
	 * 
	 * @param url
	 * @param paramsJson
	 */
	private void resquestPost(String url, JSONObject paramsJson) {
		Map<String, String> params = new HashMap<String, String>();
		System.out.println("请求参数加密前[" + paramsJson.toString() + "]");
		params.put("params", AES256Encryption.aes256Encrypt(paramsJson.toString()));
		System.out.println("请求参数加密后[" + params + "]");
		try {
			String xml = CrawlerWebSousePost.post(url, params);
			System.out.println("请求结果[" + xml + "]");
			System.out.println("请求结果大小[" + xml.length() + "]");
			if(xml.startsWith("{")){
				String decStr = AES256Encryption.aes256Decrypt(JSONObject.fromObject(xml).getString("result"));

				System.out.println("解密后的结果");
				System.out.println("----------------------------------------------------------------------------");
				System.out.println(formatJson(decStr));
				System.out.println("----------------------------------------------------------------------------");
			}
			

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
