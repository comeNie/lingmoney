package com.mrbt.lingmoney.admin.service.activityReward.impl;

import java.io.StringReader;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.io.SAXReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import com.mrbt.lingmoney.admin.service.activityReward.ActivityRewardService;
import com.mrbt.lingmoney.admin.service.bank.HxQueryTradingResultService;
import com.mrbt.lingmoney.bank.reward.HxSingleRewardOrCutAMelon;
import com.mrbt.lingmoney.mapper.AccountFlowMapper;
import com.mrbt.lingmoney.mapper.HxAccountMapper;
import com.mrbt.lingmoney.mapper.ProductMapper;
import com.mrbt.lingmoney.mapper.TradingMapper;
import com.mrbt.lingmoney.mapper.UsersAccountMapper;
import com.mrbt.lingmoney.model.AccountFlow;
import com.mrbt.lingmoney.model.HxAccount;
import com.mrbt.lingmoney.model.Product;
import com.mrbt.lingmoney.model.ProductExample;
import com.mrbt.lingmoney.model.Trading;
import com.mrbt.lingmoney.model.TradingExample;
import com.mrbt.lingmoney.model.UsersAccount;
import com.mrbt.lingmoney.model.UsersAccountExample;
import com.mrbt.lingmoney.mongo.WapActivityRewardMongo;
import com.mrbt.lingmoney.utils.ResultParame;

/** 
 * @author  suyibo 
 * @date 创建时间：2018年5月10日
 */
@Service
public class ActivityRewardServiceImpl implements ActivityRewardService {
	private static final Logger LOGGER = LogManager.getLogger(ActivityRewardServiceImpl.class);

	SimpleDateFormat SF = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	// private final String END_TIME = "2018-05-22 00:00:00";
	// private final String BATCH = "20180513000365";

	private final String WAP_END_TIME = "2018-07-01 00:00:00";

	@Autowired
	private TradingMapper tradingMapper;
	@Autowired
	private MongoTemplate mongoTemplate;
	@Autowired
	private UsersAccountMapper usersAccountMapper;
	@Autowired
	private AccountFlowMapper accountFlowMapper;
	@Autowired
	private HxAccountMapper hxAccountMapper;
	@Autowired
	private HxQueryTradingResultService hxQueryTradingResultService;
	@Autowired
	private HxSingleRewardOrCutAMelon hxSingleRewardOrCutAMelon;
	@Autowired
	private ProductMapper productMapper;

	// /**
	// * 活动奖励现金红包
	// *
	// * @author suyibo
	// * @date 2018-05-10 17:03:00
	// */
	// @Override
	// public void activityRewardTask() {
	// try {
	// Date endDate = SF.parse(END_TIME);
	// Date nowDate = new Date();
	// // 死循环，活动结束跳出循环
	// while (nowDate.getTime() < endDate.getTime()) {
	// //查询交易记录写入到mongodb
	// postTradingInfo();
	//
	// //发送红包
	// sendRedPacket();
	//
	// //查询红包发送结果
	// querySendRedPacket();
	//
	//
	// nowDate = new Date();
	// }
	// } catch (Exception e1) {
	// e1.printStackTrace();
	// }
	// }
	//
	// private void querySendRedPacket() {
	//
	// LOGGER.info("querySendRedPacket执行");
	// /**
	// * 10秒后查看红包发送结果
	// */
	// List<ActivityRewardMongo> rewardList = mongoTemplate.find(new
	// Query(Criteria.where("status").is(2)), ActivityRewardMongo.class);
	// try {
	// Thread.sleep(10 * 1000);
	// if (!rewardList.isEmpty()) {
	// LOGGER.info("查看红包发送结果开始");
	// for (ActivityRewardMongo mongo : rewardList) {
	// if (null != mongo.getNumber() && !mongo.getNumber().equals("")) {
	//
	// Map<String, Object> resultMap =
	// hxQueryTradingResultService.queryHxTradingResult(mongo.getNumber(), null,
	// mongo.getLogGroup());
	//
	// if (resultMap != null) {
	// if ("S".equals(resultMap.get("STATUS"))) {
	// // 获取用户账户
	// UsersAccountExample ex = new UsersAccountExample();
	// ex.createCriteria().andUIdEqualTo(mongo.getuId());
	// List<UsersAccount> resLi = usersAccountMapper.selectByExample(ex);
	// if (resLi == null || resLi.size() == 0) {
	// continue;
	// }
	// UsersAccount userAcc = resLi.get(0);
	//
	// // 更新用户账户表
	// UsersAccount userAccNew = new UsersAccount();
	// userAccNew.setBalance(userAcc.getBalance().add(new
	// BigDecimal(mongo.getMoney())));
	// usersAccountMapper.updateByExampleSelective(userAccNew, ex);
	//
	// /**
	// * 增加奖励流水
	// */
	// AccountFlow accountFlow = new AccountFlow();
	// accountFlow.setaId(userAcc.getId());
	// accountFlow.setOperateTime(new Date());
	// accountFlow.setMoney(new BigDecimal(mongo.getMoney()));
	// accountFlow.setStatus(1); // 0，处理中；1，已完成；2，处理失败；3，撤标成功；4.流标
	// // 5,支付完成;6取消支付； 7支付中；// 8待支付
	// accountFlow.setType(ResultParame.ResultNumber.FOUR.getNumber()); //
	// 0：充值，1：取现，2：理财，3：赎回,4:奖励
	// accountFlow.setNumber(mongo.getNumber());
	// accountFlowMapper.insert(accountFlow);
	//
	// // 更新mongo库
	// Update update = new Update();
	// update.set("status", 3); // 赠送成功
	// update.set("rewardDate", SF.format(new Date()));
	// mongoTemplate.upsert(new Query(Criteria.where("id").is(mongo.getId())),
	// update, ActivityRewardMongo.class);
	// LOGGER.info("发送成功");
	// } else {
	// // 更新mongo库
	// Update update = new Update();
	// update.set("status", 4); // 返回未知结果，重新发送
	// mongoTemplate.upsert(new Query(Criteria.where("id").is(mongo.getId())),
	// update, ActivityRewardMongo.class);
	// LOGGER.info("返回未知或失败结果，请技术检测");
	// }
	// }
	// }
	// }
	// LOGGER.info("查看红包发送结果结束");
	// }
	// } catch (InterruptedException e) {
	// e.printStackTrace();
	// }
	// }
	//
	// private void sendRedPacket() {
	// LOGGER.info("sendRedPacket执行");
	// // 查询出所有待发放的红包
	// List<ActivityRewardMongo> activityRewardList = mongoTemplate.find(new
	// Query(Criteria.where("status").is(1)), ActivityRewardMongo.class);
	// if (!activityRewardList.isEmpty()) {
	// LOGGER.info("查询mongo数据，执行状态为发放中的红包发放开始");
	// for (ActivityRewardMongo mongo : activityRewardList) {
	// // 华兴账户
	// HxAccount account = hxAccountMapper.selectByUid(mongo.getuId());
	//
	// if (account == null) {
	// LOGGER.info("账户无效");
	// continue;
	// }
	//
	// String acNo = account.getAcNo();
	// String acName = account.getAcName();
	//
	// // 银行账户更新
	// String logGroup = "\nrequestSingleRewardOrCutAMelon_" +
	// System.currentTimeMillis() + "_";
	// Document xml =
	// hxSingleRewardOrCutAMelon.requestSingleRewardOrCutAMelon("PC", acNo,
	// acName, String.valueOf(mongo.getMoney()), "返现红包奖励", logGroup);
	// // 转生String类型
	// String xmlData = xml.asXML().replaceAll("\n", "");
	// // 获取流水号
	// String channelFlow = queryTextFromXml(xmlData, "channelFlow");
	//
	// Update update = new Update();
	// update.set("number", channelFlow);
	// update.set("logGroup", logGroup);
	// update.set("status", 2);
	// mongoTemplate.upsert(new Query(Criteria.where("id").is(mongo.getId())),
	// update, ActivityRewardMongo.class);
	// }
	// LOGGER.info("查询mongo数据，执行状态为发放中的红包发放结束");
	// }
	//
	// }
	//
	// private void postTradingInfo() {
	//
	// LOGGER.info("postTradingInfo执行");
	//
	// // 查询出活动产品所有购买成功的交易
	// ProductExample productExample = new ProductExample();
	// productExample.createCriteria().andBatchEqualTo(BATCH);
	// List<Product> productList =
	// productMapper.selectByExample(productExample);
	// if (!productList.isEmpty()) {
	// for (Product product : productList) {
	// TradingExample example = new TradingExample();
	// example.createCriteria().andPIdEqualTo(product.getId()).andStatusEqualTo(4).andBuyMoneyGreaterThanOrEqualTo(new
	// BigDecimal("10000"));
	// List<Trading> tradingList = tradingMapper.selectByExample(example);
	// // 成功交易不为空则计入
	// if (!tradingList.isEmpty()) {
	// LOGGER.info("活动产品成功购买计入mongo开始");
	// for (Trading trading : tradingList) {
	// // 先从mongo中查询此交易是否存在，不存在则添加
	// ActivityRewardMongo findOne = mongoTemplate.findOne(new
	// Query(Criteria.where("tId").is(String.valueOf(trading.getId()))),
	// ActivityRewardMongo.class);
	// if (null != findOne) {
	// continue;
	// }
	// ActivityRewardMongo activityRewardMongo = new ActivityRewardMongo();
	// // 奖励金额
	// // (投资金额*0.002,10)-2.(红包金额尾数为8且红包金额略小于投资金额的0.2%)
	// BigDecimal buyMoney = trading.getBuyMoney();
	// String rewardMoney = String.valueOf(buyMoney.multiply(new
	// BigDecimal(0.002)).intValue());
	// rewardMoney = rewardMoney.replace(rewardMoney.charAt(rewardMoney.length()
	// - 1) + "", "0");
	// int money = Integer.valueOf(rewardMoney) - 2;
	//
	// activityRewardMongo.settId(String.valueOf(trading.getId()));
	// activityRewardMongo.setuId(trading.getuId());
	// activityRewardMongo.setRewardDate("");
	// activityRewardMongo.setStatus(0); // 未赠送
	// activityRewardMongo.setMoney(money);
	// activityRewardMongo.setNumber("");
	// activityRewardMongo.setLogGroup("");
	// activityRewardMongo.setCreateDate(new Date());
	// // 存入数据
	// mongoTemplate.insert(activityRewardMongo);
	// }
	// LOGGER.info("活动产品成功购买计入mongo结束");
	// }
	// }
	// }
	// }

	/**
	 * 查询xml中指标签的text
	 * 
	 * @param xml
	 *            xml数据
	 * @param emlName
	 *            节点名称
	 * @return
	 */
	private static String queryTextFromXml(String xml, String emlName) {
		try {
			SAXReader reader = new SAXReader();
			StringReader in = new StringReader(xml);
			Document doc = reader.read(in);

			String emlText = doc.selectSingleNode("//" + emlName).getText();

			if (!StringUtils.isBlank(emlText)) {
				return emlText;
			}
		} catch (DocumentException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * wap推广活动奖励现金红包
	 * 
	 * @author suyibo
	 * @date 2018-05-28 10:07:00
	 */
	@Override
	public void wapActivityRewardTask() {
		try {
			Date endDate = SF.parse(WAP_END_TIME);
			Date nowDate = new Date();
			// 死循环，活动结束跳出循环
			while (nowDate.getTime() < endDate.getTime()) {
				// 查询活动时间内wap用户交易记录写入到mongodb
				postWapTradingInfo(nowDate);

				// 发送红包
				wapSendRedPacket();

				// 查询红包发送结果
				queryWapSendRedPacket();

				nowDate = new Date();
			}
		} catch (Exception e1) {
			e1.printStackTrace();
		}
	}

	private void postWapTradingInfo(Date nowDate) {

		LOGGER.info("postWapTradingInfo执行");

		// 查询出活动期间wap购买成功的交易
		TradingExample example = new TradingExample();
		example.createCriteria().andPlatformTypeEqualTo(2).andStatusEqualTo(4).andBuyMoneyGreaterThanOrEqualTo(new BigDecimal("2000")).andBuyDtGreaterThanOrEqualTo(nowDate);
		List<Trading> tradingList = tradingMapper.selectByExample(example);
		// 成功交易不为空则计入
		if (!tradingList.isEmpty()) {
			LOGGER.info("wap端成功购买计入mongo开始");
			for (Trading trading : tradingList) {
				// 先从mongo中查询此交易是否存在，不存在则添加
				WapActivityRewardMongo findOne = mongoTemplate.findOne(new Query(Criteria.where("tId").is(String.valueOf(trading.getId()))), WapActivityRewardMongo.class);
				if (null != findOne) {
					continue;
				}
				WapActivityRewardMongo activityRewardMongo = new WapActivityRewardMongo();
				// 奖励金额
				// 单笔认购2000元，认购上不封顶，以100的整数倍递增，认购成功后，即可返一个现金红包进行发放。
				// 一年期现金红包计算方式：投资金额*0.2%/365*项目期限
				// 半年期现金红包计算方式：投资金额*0.2%/365*项目期限
				// 三月期现金红包计算方式：投资金额*0.2%/365*项目期限
				// 一月期现金红包计算方式：投资金额*0.3%/365*项目期限

				BigDecimal buyMoney = trading.getBuyMoney();
				// 获取用户购买产品期限
				ProductExample productExample = new ProductExample();
				productExample.createCriteria().andIdEqualTo(trading.getpId());
				List<Product> productList = productMapper.selectByExample(productExample);
				String rewardMoney = ""; // 初始化奖励金额
				if (!productList.isEmpty()) {
					Product product = productList.get(0); // 购买的产品对象
					if (product.getfTime() > 25 && product.getfTime() < 40) { // 一个月期
						rewardMoney = String.valueOf(buyMoney.multiply(new BigDecimal(0.003)).multiply(new BigDecimal(product.getfTime())));
					} else {
						rewardMoney = String.valueOf(buyMoney.multiply(new BigDecimal(0.002)).multiply(new BigDecimal(product.getfTime())));
					}
				}
				Double money = new BigDecimal(Double.valueOf(rewardMoney) / 365.0).setScale(2, BigDecimal.ROUND_DOWN).doubleValue();
				if (money < 0.5) {
					money = 0.5;
				} else if (money > 400) {
					money = 400.00;
				}

				activityRewardMongo.settId(String.valueOf(trading.getId()));
				activityRewardMongo.setuId(trading.getuId());
				activityRewardMongo.setRewardDate("");
				activityRewardMongo.setStatus(0); // 未赠送
				activityRewardMongo.setMoney(String.valueOf(money));
				activityRewardMongo.setNumber("");
				activityRewardMongo.setLogGroup("");
				activityRewardMongo.setCreateDate(new Date());
				// 存入数据
				mongoTemplate.insert(activityRewardMongo);
			}
			LOGGER.info("wap端成功购买计入mongo结束");
		}

	}

	private void wapSendRedPacket() {
		LOGGER.info("wapSendRedPacket执行");
		// 查询出所有待发放的红包
		List<WapActivityRewardMongo> activityRewardList = mongoTemplate.find(new Query(Criteria.where("status").is(1)), WapActivityRewardMongo.class);
		if (!activityRewardList.isEmpty()) {
			LOGGER.info("查询mongo数据，执行状态为发放中的红包发放开始");
			for (WapActivityRewardMongo mongo : activityRewardList) {
				// 华兴账户
				HxAccount account = hxAccountMapper.selectByUid(mongo.getuId());

				if (account == null) {
					LOGGER.info("账户无效");
					continue;
				}

				String acNo = account.getAcNo();
				String acName = account.getAcName();

				// 银行账户更新
				String logGroup = "\nrequestSingleRewardOrCutAMelon_" + System.currentTimeMillis() + "_";
				Document xml = hxSingleRewardOrCutAMelon.requestSingleRewardOrCutAMelon("PC", acNo, acName,
						String.valueOf(mongo.getMoney()), "返现红包奖励", logGroup);
				// 转生String类型
				String xmlData = xml.asXML().replaceAll("\n", "");
				// 获取流水号
				String channelFlow = queryTextFromXml(xmlData, "channelFlow");

				Update update = new Update();
				update.set("number", channelFlow);
				update.set("logGroup", logGroup);
				update.set("status", 2);
				mongoTemplate.upsert(new Query(Criteria.where("id").is(mongo.getId())), update, WapActivityRewardMongo.class);
			}
			LOGGER.info("查询mongo数据，执行状态为发放中的红包发放结束");
		}

	}

	private void queryWapSendRedPacket() {

		LOGGER.info("queryWapSendRedPacket执行");
		/**
		 * 10秒后查看红包发送结果
		 */
		List<WapActivityRewardMongo> rewardList = mongoTemplate.find(new Query(Criteria.where("status").is(2)), WapActivityRewardMongo.class);
		try {
			Thread.sleep(10 * 1000);
			if (!rewardList.isEmpty()) {
				LOGGER.info("wap查看红包发送结果开始");
				for (WapActivityRewardMongo mongo : rewardList) {
					if (null != mongo.getNumber() && !mongo.getNumber().equals("")) {

						Map<String, Object> resultMap = hxQueryTradingResultService
								.queryHxTradingResult(mongo.getNumber(), null, mongo.getLogGroup());

						if (resultMap != null) {
							if ("S".equals(resultMap.get("STATUS"))) {
								// 获取用户账户
								UsersAccountExample ex = new UsersAccountExample();
								ex.createCriteria().andUIdEqualTo(mongo.getuId());
								List<UsersAccount> resLi = usersAccountMapper.selectByExample(ex);
								if (resLi == null || resLi.size() == 0) {
									continue;
								}
								UsersAccount userAcc = resLi.get(0);

								// 更新用户账户表
								UsersAccount userAccNew = new UsersAccount();
								userAccNew.setBalance(userAcc.getBalance().add(new BigDecimal(mongo.getMoney())));
								usersAccountMapper.updateByExampleSelective(userAccNew, ex);

								/**
								 * 增加奖励流水
								 */
								AccountFlow accountFlow = new AccountFlow();
								accountFlow.setaId(userAcc.getId());
								accountFlow.setOperateTime(new Date());
								accountFlow.setMoney(new BigDecimal(mongo.getMoney()));
								accountFlow.setStatus(1); // 0，处理中；1，已完成；2，处理失败；3，撤标成功；4.流标
								// 5,支付完成;6取消支付； 7支付中；// 8待支付
								accountFlow.setType(ResultParame.ResultNumber.FOUR.getNumber()); // 0：充值，1：取现，2：理财，3：赎回,4:奖励
								accountFlow.setNumber(mongo.getNumber());
								accountFlowMapper.insert(accountFlow);

								// 更新mongo库
								Update update = new Update();
								update.set("status", 3); // 赠送成功
								update.set("rewardDate", SF.format(new Date()));
								mongoTemplate.upsert(new Query(Criteria.where("id").is(mongo.getId())), update, WapActivityRewardMongo.class);
								LOGGER.info("发送成功");
							} else {
								// 更新mongo库
								Update update = new Update();
								update.set("status", 4); // 返回未知结果，重新发送
								mongoTemplate.upsert(new Query(Criteria.where("id").is(mongo.getId())), update, WapActivityRewardMongo.class);
								LOGGER.info("返回未知或失败结果，请技术检测");
							}
						}
					}
				}
				LOGGER.info("wap查看红包发送结果结束");
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

}
