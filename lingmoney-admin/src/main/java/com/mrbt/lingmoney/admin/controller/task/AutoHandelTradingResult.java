package com.mrbt.lingmoney.admin.controller.task;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.mrbt.lingmoney.admin.service.bank.SingleBiddingResultService;
import com.mrbt.lingmoney.admin.service.schedule.ScheduleService;
import com.mrbt.lingmoney.mapper.ProductMapper;
import com.mrbt.lingmoney.mapper.TradingMapper;
import com.mrbt.lingmoney.model.Product;
import com.mrbt.lingmoney.model.Trading;
import com.mrbt.lingmoney.model.TradingExample;
import com.mrbt.lingmoney.utils.DateUtils;
import com.mrbt.lingmoney.utils.PageInfo;
import com.mrbt.lingmoney.utils.ResultParame.ResultNumber;
import java.util.Date;

/**
 * 自动查询支付结果
 * 
 * @author Administrator
 *
 */
@Component
public class AutoHandelTradingResult {
	private static final Logger LOG = LogManager.getLogger(AutoHandelTradingResult.class);
	@Autowired
	private ScheduleService scheduleService;
	@Autowired
	private TradingMapper tradingMapper;
	@Autowired
	private SingleBiddingResultService singleBiddingResultService;
	
	@Autowired
	private ProductMapper productMapper;
	
	private static final int STATUS12 = 12;
	// @Scheduled(cron = "0 0/5 * * * ? ")
	// 间隔10分钟执行    修改trading状态为18点击购买，但是没有进行支付的数据
	/**
	 * 执行方法
	 */
	public void autoHandelTradingResult() {
		LOG.info("支付结果处理定时任务开始执行");
		scheduleService.saveScheduleLog(null, "支付结果处理定时任务开始执行", null);

		TradingExample example = new TradingExample();
		example.createCriteria().andStatusEqualTo(STATUS12);
		List<Trading> overTimeTradingList = tradingMapper.selectByExample(example);

		if (overTimeTradingList != null && overTimeTradingList.size() > 0) {
			for (Trading trading : overTimeTradingList) {
				// 处理支付结果
				try {
					//查询产品信息
					Product product = productMapper.selectProductByCode(trading.getpCode());
					if (product.getpType() == ResultNumber.TWO.getNumber()) { //处理华兴订单
						
						LOG.info("处理华兴银行的产品订单：" + trading.getId() + "\t" + trading.getBizCode());
						
						PageInfo pi = singleBiddingResultService.requestSingleBiddingResult(0, "PC", trading.getuId(), trading.getBizCode());
						LOG.info("查询支付中{}号交易结果为{}", trading.getId(), pi.getMsg());
						
					} else { //处理京东订单
						
						//判断交易时间,如果交易时间已经超过了10分钟处理订单
						LOG.info("处理京东的订单，定时删除t_id = " + trading.getId() + "u_id=" + trading.getuId() + "buyMoney = " + trading.getFinancialMoney() + "\t" + DateUtils.minuteDiff(trading.getBuyDt(), new Date()));
						if (DateUtils.minuteDiff(trading.getBuyDt(), new Date()) > ResultNumber.TEN.getNumber()) {
							singleBiddingResultService.updateProductReachMoney(trading.getId(), trading.getpId(), trading.getBuyMoney());
						}
					}
				} catch (Exception e1) {
					e1.printStackTrace();
					LOG.info("{}号支付结果处理失败，查询异常，错误原因：{}", trading.getId(), e1.toString());
				}
				
			}
		}

		scheduleService.saveScheduleLog(null, "支付结果处理定时任务执行完毕", null);
		LOG.info("支付结果处理定时任务执行完毕");
	}

	// 查询交易状态
//	public QueryDirect query(String requestNo) {
//		String url = PayVo.pay_zhi_Url;
//		String serviceName = "QUERY";
//		Query query = new Query();
//		query.setMode("CP_TRANSACTION");
//		query.setPlatformNo(PayVo.getPlatform_no());
//		query.setRequestNo(requestNo);
//		HttpParams hp = new HttpParamsBuilder().url(url).requestProperty("Accept", "").connectionTimeout(5000)
//				.readTimeout(10000).build();
//		List<NameValuePair> list = new ArrayList<NameValuePair>();
		//待确认问题
//		String sign = YeepSignUtils.doSign(query.toXml());
//		list.add(new BasicNameValuePair("sign", sign));
//		list.add(new BasicNameValuePair("service", serviceName));
//		list.add(new BasicNameValuePair("req", query.toXml()));
//		try {
//			String content = AHttpUtils.getInstance(PayVo.pay_ip, PayVo.pay_port).getDataByPost(hp, list);
//			XStream xstream = new XStream(new DomDriver());
//			xstream.autodetectAnnotations(true);
//			xstream.alias("response", QueryDirect.class);
//			QueryDirect result = (QueryDirect) xstream.fromXML(content);
//			return result;
//		} catch (Exception e) {
//			e.printStackTrace();
//			return null;
//
//		}
//		return null;
//	}
}
