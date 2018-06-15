package com.mrbt.lingmoney.testng;

import java.math.BigDecimal;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.fastjson.JSON;
import com.mrbt.lingmoney.utils.DateUtils;
import com.mrbt.myutils.secret.SecretFactory;
import com.mrbt.pay.face.QuickPayMentIF;
import com.mrbt.pay.jd.PaySecret;
import com.mrbt.pay.jd.QuickJdPay;
import com.mrbt.pay.jd.vo.QuickJdPayVo;

/**
 * 快捷支付
 * 
 * @author Administrator
 *
 */
public class TestqjdPay extends SpringTestNG {
	@Autowired
	public QuickJdPay qjdPay;
	
	@Autowired
	private QuickPayMentIF quickPayMentIF;

	@Autowired
	public SecretFactory secretFactory;

	@Autowired
	public PaySecret paySecret;
	
	@Test
	public void test() throws Exception {
//		qianYue();
//		http://www.lingmoney.cn/purchase/quickNotice/fb3c737e1015ade371473215669507_2974_4182_4566.html
//		zhifu();
		
//		query();
	}
	
	/**
	 * 
	 */
	private void query() {
		try {
//			while(true){
				QuickJdPayVo vo = quickPayMentIF.queryByNet("ccc902120639e48ac1471855865367");
				System.out.println(JSON.toJSON(vo));
				
//				QuickJdPayVo vo2 = quickPayMentIF.queryByNet("18515e487078576421510884131591");
//				System.out.println(JSON.toJSON(vo2));
//				vo = quickPayMentIF.queryByNet("3ba88f51e71be058b1482297320619");
//				System.out.println(JSON.toJSON(vo));
//				
//				vo = quickPayMentIF.queryByNet("d837be1bcebf5322a1482855292965");
//				System.out.println(JSON.toJSON(vo));
//			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
	}
	public void zhifu(){
		QuickJdPayVo vo = qjdPay.quickSell(
				"ICBC", 
				"6212260200109616512", 
				"喻龙", 
				"210181198412136131", 
				"13683173295",
				"ccc902120639e48ac1471855865367", 
				new BigDecimal("300"), 
				"697417", 
				null, 
				DateUtils.getDateStr(),
				DateUtils.getTimeStr());

		System.out.println("=====================" + JSON.toJSONString(vo) + "==========");
	}

	public void qianYue() {
		QuickJdPayVo vo = qjdPay.signBank(
				"ICBC", 
				"6212260200109616512", 
				"喻龙", 
				"210181198412136131", 
				"13683173295",
				"ccc902120639e48ac1471855865367", 
				new BigDecimal("300"));

		System.out.println(JSON.toJSON(vo));

		if (!vo.isOk()) {
			System.out.println("=====================" + vo.getResultInfo() + "==========");
		}
	}
}
