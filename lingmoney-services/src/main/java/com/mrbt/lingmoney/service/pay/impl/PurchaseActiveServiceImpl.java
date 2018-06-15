package com.mrbt.lingmoney.service.pay.impl;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.MessageFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONObject;

import org.apache.commons.lang.math.NumberUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;

import com.alibaba.fastjson.JSON;
import com.mrbt.lingmoney.mapper.ActivityRecordMapper;
import com.mrbt.lingmoney.mapper.GiftDetailMapper;
import com.mrbt.lingmoney.mapper.ProductMapper;
import com.mrbt.lingmoney.mapper.PurchaseActiveMapper;
import com.mrbt.lingmoney.mapper.SupportBankMapper;
import com.mrbt.lingmoney.mapper.TakeheartTransactionFlowMapper;
import com.mrbt.lingmoney.mapper.TradingMapper;
import com.mrbt.lingmoney.mapper.UsersAccountMapper;
import com.mrbt.lingmoney.mapper.UsersBankMapper;
import com.mrbt.lingmoney.mapper.UsersMapper;
import com.mrbt.lingmoney.mapper.UsersPropertiesMapper;
import com.mrbt.lingmoney.model.ActivityRecord;
import com.mrbt.lingmoney.model.GiftDetail;
import com.mrbt.lingmoney.model.GiftDetailExample;
import com.mrbt.lingmoney.model.Product;
import com.mrbt.lingmoney.model.PurchaseActivePostInfo;
import com.mrbt.lingmoney.model.SupportBank;
import com.mrbt.lingmoney.model.TakeheartTransactionFlow;
import com.mrbt.lingmoney.model.Trading;
import com.mrbt.lingmoney.model.Users;
import com.mrbt.lingmoney.model.UsersAccount;
import com.mrbt.lingmoney.model.UsersBank;
import com.mrbt.lingmoney.model.UsersProperties;
import com.mrbt.lingmoney.model.trading.SmsMessage;
import com.mrbt.lingmoney.service.common.SmsService;
import com.mrbt.lingmoney.service.pay.PurchaseActiveService;
import com.mrbt.lingmoney.service.pay.UsersBankService;
import com.mrbt.lingmoney.service.trading.TradingFixRuleBuyService;
import com.mrbt.lingmoney.service.trading.TradingService;
import com.mrbt.lingmoney.service.trading.impl.TradingFixRuleBuyServiceImpl.CallbackType;
import com.mrbt.lingmoney.utils.AppCons;
import com.mrbt.lingmoney.utils.DateUtils;
import com.mrbt.lingmoney.utils.ProductUtils;
import com.mrbt.lingmoney.utils.ResultParame;
import com.mrbt.pay.config.PayConfig;
import com.mrbt.pay.face.PayMentIF;
import com.mrbt.pay.jd.QuickJdPay;
import com.mrbt.pay.jd.vo.QuickJdPayVo;

/** 
 * @author  suyibo 
 * @date 创建时间：2018年1月2日
 */
@Service
public class PurchaseActiveServiceImpl implements PurchaseActiveService {

	private static final Logger LOGGER = LogManager.getLogger(PurchaseActiveServiceImpl.class);

	// 如果为真，正常交易，如果为假，测试模式
//	private static boolean payMode = true;
	private static boolean payMode = false;
	@Autowired
	private QuickJdPay qjdPay;
	@Autowired
	public PayMentIF jdPay;
	@Autowired
	private UsersBankService usersBankService;
	@Autowired
	private PurchaseActiveMapper purchaseActiveMapper;
	@Autowired
	private TradingMapper tradingMapper;
	@Autowired
	private TakeheartTransactionFlowMapper takeheartTransactionFlowMapper;
	@Autowired
	private UsersMapper usersMapper;
	@Autowired
	private ProductMapper productMapper;
	@Autowired
	private SmsService smsService;
	@Autowired
	private ActivityRecordMapper activityRecordMapper;
	@Autowired
	private UsersAccountMapper usersAccountMapper;
	@Autowired
	private TradingService tradingService;
	@Autowired
	private GiftDetailMapper giftDetailMapper;
	@Autowired
	public PayConfig payConfig;
	@Autowired
	private UsersBankMapper usersBankMapper;
	@Autowired
	private SupportBankMapper supportBankMapper;
	@Autowired
	private UsersPropertiesMapper usersPropertiesMapper;
	@Autowired
	private TradingFixRuleBuyService tradingFixRuleBuyService;

	@Override
	public JSONObject validParames(HttpServletRequest request, String uId) {
		JSONObject json = new JSONObject();
		String dizNumber = request.getParameter("dizNumber");
		String userBankId = request.getParameter("userBankId");
		// String strong = "0.01";
		String strong = request.getParameter("strong");
		String pType = request.getParameter("pType");
		// 将订单编号放入redis，如果有，则return，没有，则往下
		if (!usersBankService.checkBankRedis(dizNumber, ResultParame.ResultNumber.ONE.getNumber())) {
			json.put("code", ResultParame.ResultInfo.PLEASE_WAIT.getCode());
			json.put("msg", ResultParame.ResultInfo.PLEASE_WAIT.getMsg());
			return json;
		}

		PurchaseActivePostInfo info = null;
		boolean isTrue = true;
		if (pType.equals("17")) {
			info = purchaseActiveMapper.queryPostInfo2(dizNumber);
			if (info != null && !info.getStatus().equals("2")) {
				isTrue = false;
			}
		} else {
			info = purchaseActiveMapper.queryPostInfo(dizNumber);
			if (info != null && !info.getStatus().equals("12")) {
				isTrue = false;
			}
		}

		if (!isTrue) {
			json.put("code", ResultParame.ResultInfo.RECOMMIT.getCode());
			json.put("msg", ResultParame.ResultInfo.RECOMMIT.getMsg());
			usersBankService.deleteBankRedis(dizNumber, ResultParame.ResultNumber.ONE.getNumber());
			return json;
		}

		if (userBankId != null && !userBankId.equals("") && dizNumber != null && !dizNumber.equals("") && strong != null
				&& !strong.equals("")) {

			UsersBank userBank = usersBankService.findByUserBankId(Integer.parseInt(userBankId));

			/**
			 * 参数: card_bank - 持卡人支付卡号发卡行 例如ABC（中国农业银行） card_no - 持卡人支付的卡号
			 * card_name - 持卡人的姓名 card_idno - 持卡人的身份证号 card_phone - 持卡人的手机号
			 * trade_id - 交易号，数字或字母，长度为30个字符串 trade_amount - 交易的金额
			 */
			UsersProperties usersProperties = usersPropertiesMapper.selectByuId(uId);
			String n = "\n";
			StringBuffer sb = new StringBuffer();
			sb.append("银行简称：" + userBank.getBankShort() + n);
			sb.append("银行卡号：" + userBank.getNumber() + n);
			sb.append("姓名：" + usersProperties.getName() + n);
			sb.append("身份证号：" + usersProperties.getIdCard() + n);
			sb.append("手机号：" + userBank.getTel() + n);
			sb.append("订单号：" + dizNumber + n);
			sb.append("交易金额："
					+ new BigDecimal(Double.parseDouble(strong) * ResultParame.ResultNumber.ONE_HUNDRED.getNumber())
					+ n);

			QuickJdPayVo resSign = null;
			if (payMode) {
				resSign = qjdPay.signBank(userBank.getBankShort(), userBank.getNumber(),
						usersProperties.getName(), usersProperties.getIdCard(),
						userBank.getTel(), dizNumber,
						new BigDecimal(Double.parseDouble(strong) * ResultParame.ResultNumber.ONE_HUNDRED.getNumber()));
			} else {
				resSign = new QuickJdPayVo();
				resSign.setResultCode("0000");
			}

			System.out.println("快捷支付签约：" + JSON.toJSON(resSign) + n + sb.toString());

			if (resSign.isOk()) {
				usersBankService.deleteBankRedis(dizNumber, ResultParame.ResultNumber.ONE.getNumber());
				json.put("code", ResultParame.ResultInfo.SUCCESS.getCode());
				json.put("msg", resSign.getResultInfo());
			} else {
				usersBankService.deleteBankRedis(dizNumber, ResultParame.ResultNumber.ONE.getNumber());
				json.put("code", resSign.getResultCode());
				json.put("msg", resSign.getResultInfo());
			}
		} else {
			usersBankService.deleteBankRedis(dizNumber, ResultParame.ResultNumber.ONE.getNumber());
			json.put("code", ResultParame.ResultInfo.TRADING_FAIL2.getCode());
			json.put("msg", "no success");
		}

		return json;
	}

	@Override
	public JSONObject quickPaymentPay(HttpServletRequest request, String uId) {
		JSONObject json = new JSONObject();
		HttpSession session = request.getSession();
		String dizNumber = request.getParameter("dizNumber");
		String userBankId = request.getParameter("userBankId");
		// String strong = "0.01";
		String strong = request.getParameter("strong");
		String telCode = request.getParameter("telCode");
		String tId = request.getParameter("tId");
		String infoId = request.getParameter("infoId");
		String pType = request.getParameter("pType");

		System.out.println(userBankId + "\t" + dizNumber + "\t" + strong + "\t" + telCode);

		// 将订单编号放入redis，如果有，则return，没有，则往下
		if (!usersBankService.checkBankRedis(dizNumber, ResultParame.ResultNumber.TWO.getNumber())) {
			json.put("code", ResultParame.ResultInfo.PLEASE_WAIT.getCode());
			json.put("msg", ResultParame.ResultInfo.PLEASE_WAIT.getMsg());
			return json;
		}

		PurchaseActivePostInfo info = null;
		boolean isTrue = true;
		if (pType.equals("17")) {
			info = purchaseActiveMapper.queryPostInfo2(dizNumber);
			if (info != null && !info.getStatus().equals("2")) {
				isTrue = false;
			}
		} else {
			info = purchaseActiveMapper.queryPostInfo(dizNumber);
			if (info != null && !info.getStatus().equals("12")) {
				isTrue = false;
			}
		}

		if (!isTrue) {
			json.put("code", ResultParame.ResultInfo.RECOMMIT.getCode());
			json.put("msg", ResultParame.ResultInfo.RECOMMIT.getMsg());
			usersBankService.deleteBankRedis(dizNumber, ResultParame.ResultNumber.TWO.getNumber());
			return json;
		}

		if (userBankId != null && !userBankId.equals("") && dizNumber != null && !dizNumber.equals("") && strong != null
				&& !strong.equals("") && telCode != null && !telCode.equals("")) {
			UsersBank userBank = usersBankService.findByUserBankId(Integer.parseInt(userBankId));

			/**
			 * 参数: card_bank - 持卡人支付卡号发卡行 例如ABC（中国农业银行） card_no - 持卡人支付的卡号
			 * card_name - 持卡人的姓名 card_idno - 持卡人的身份证号 card_phone - 持卡人的手机号
			 * trade_id - 交易号，数字或字母，长度为30个字符串 trade_amount - 交易的金额
			 * 
			 * trade_code - 手机验证码 trade_notice - 通知地址（可空） trade_date -
			 * 交易日期格式为(yyyyMMdd) trade_time - 交易时间格式为(HHmmss)
			 */
			UsersProperties usersProperties = usersPropertiesMapper.selectByuId(uId);
			String n = "\n";
			StringBuffer sb = new StringBuffer();
			sb.append("银行简称：" + userBank.getBankShort() + n);
			sb.append("银行卡号：" + userBank.getNumber() + n);
			sb.append("姓名：" + usersProperties.getName() + n);
			sb.append("身份证号：" + usersProperties.getIdCard() + n);
			sb.append("手机号：" + userBank.getTel() + n);
			sb.append("订单号：" + dizNumber + n);
			sb.append("交易金额：" + new BigDecimal(Double.parseDouble(strong) * 100) + n);
			sb.append("验证码：" + telCode + n);
			sb.append("通知地址：" + AppCons.QUICK_NOTICE + dizNumber + "_" + tId + "_" + infoId + "_" + uId + n);
			sb.append("交易日期" + DateUtils.getDateStr() + n);
			sb.append("交易时间" + DateUtils.getTimeStr() + n);

			QuickJdPayVo resPay = null;

			if (payMode) {
				resPay = qjdPay.quickSell(userBank.getBankShort(), userBank.getNumber(),
						usersProperties.getName(), usersProperties.getIdCard(),
						userBank.getTel(), dizNumber, new BigDecimal(Double.parseDouble(strong) * 100), telCode,
						AppCons.QUICK_NOTICE + dizNumber + "_" + tId + "_" + infoId + "_" + uId,
						DateUtils.getDateStr(), DateUtils.getTimeStr());
			} else {
				resPay = new QuickJdPayVo();
				resPay.setResultCode("0000");
			}

			System.out.println("快捷支付：" + JSON.toJSON(resPay) + n + sb.toString());

			// 支付记录
			String key = AppCons.USER_BUY_REQUESTNO;

			if (resPay.isOk() || resPay.getResultCode().equals("0000")) {// 成功
				usersBankService.deleteBankRedis(dizNumber, 2);
				payOverOptertion(1, Integer.parseInt(tId), dizNumber, Integer.parseInt(infoId), uId);
				session.setAttribute(key, "0");
				buyWeiyingbaoGetLingbao(uId, Integer.parseInt(tId), Integer.parseInt(infoId));
				
				json.put("code", ResultParame.ResultInfo.SUCCESS.getCode());
				json.put("msg", ResultParame.ResultNumber.ZERO.getNumber());
			} else if (resPay.getResultCode().equals("0001") || resPay.getResultCode().equals("6")) {// 处理中
				usersBankService.deleteBankRedis(dizNumber, ResultParame.ResultNumber.TWO.getNumber());
				
				payOverOptertion(ResultParame.ResultNumber.TWELVE.getNumber(), Integer.parseInt(tId), dizNumber,
						Integer.parseInt(infoId), uId);
				session.setAttribute(key, "1");
				json.put("code", ResultParame.ResultInfo.SUCCESS.getCode());
				json.put("msg", "支付处理中");
			} else {// 失败
				usersBankService.deleteBankRedis(dizNumber, ResultParame.ResultNumber.TWO.getNumber());
				session.setAttribute(key, "2");
				json.put("code", resPay.getResultCode());// 失败代码
				json.put("msg", resPay.getResultInfo());// 失败信息
			}
		} else {
			usersBankService.deleteBankRedis(dizNumber, ResultParame.ResultNumber.TWO.getNumber());
			json.put("code", ResultParame.ResultInfo.TRADING_FAIL2.getCode());
			json.put("msg", "no success");
		}
		return json;
	}

	/**
	 * 购买稳赢宝送领宝
	 *
	 * @Description
	 * @param uid
	 *            用户ID
	 * @param tid
	 *            交易id
	 * @param infoid
	 *            交易信息id
	 */
	private void buyWeiyingbaoGetLingbao(String uid, Integer tid, Integer infoid) {
		// 支付成功，如果是购买的额稳赢宝，赠送领宝：理财额÷365天×理财天数÷50＝所赠送领宝
		Trading trading = tradingMapper.selectByPrimaryKey(tid);
		String wenyingbaocode = ProductUtils.getContent("wenyingbao_code");
		String chedaibaocode = ProductUtils.getContent("chedaibao_code");
		int takeheartcode = ProductUtils.getIntContent("takeHeart_pID");
		// 如果购买的是随心取
		if (trading.getpId().intValue() == takeheartcode) {
			TakeheartTransactionFlow takeheartTransactionFlow = takeheartTransactionFlowMapper
					.selectByPrimaryKey(infoid);
			// 发送短信
			try {
				String sendContent = AppCons.buy_content;
				Users users = usersMapper.selectByPrimaryKey(uid);
				sendContent = MessageFormat.format(sendContent, users.getLoginAccount(),
						productMapper.selectByPrimaryKey(trading.getpId()).getName(),
						takeheartTransactionFlow.getMoney());
				// sendSMSUtils.sendSMS(users.getTelephone(), sendContent);
				// 发送短信修改为放入redis统一发送
				SmsMessage message = new SmsMessage();
				message.setTelephone(users.getTelephone());
				message.setContent(sendContent);
				smsService.saveSmsMessage(message);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		// 获取产品代码前十位和属性文件中的产品代码对比
		String productCode = trading.getpCode().substring(ResultParame.ResultNumber.ZERO.getNumber(),
				ResultParame.ResultNumber.TEN.getNumber());
		if (null != wenyingbaocode && wenyingbaocode.equals(productCode)) {
			Product product = productMapper.selectByPrimaryKey(trading.getpId());
			// 计算领宝
			int lingbao = (trading.getFinancialMoney().multiply(new BigDecimal(product.getfTime())))
					.divide(new BigDecimal(ResultParame.ResultNumber.THREE_SIXTY_FIVE.getNumber()
							* ResultParame.ResultNumber.FIFTY.getNumber()), RoundingMode.HALF_UP)
					.intValue();
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("uId", uid);
			map.put("tId", trading.getId());
			map.put("name", "理财");
			map.put("date", DateUtils.getDtStr(new Date()));
			map.put("amount", lingbao);
			int i = activityRecordMapper.queryByTidUidName(map);
			if (i < ResultParame.ResultNumber.ONE.getNumber()) { // 避免重复赠送
				ActivityRecord ar = new ActivityRecord();
				ar.setAmount(lingbao);
				ar.setContent("购买稳赢宝送领宝");
				ar.setName("理财");
				ar.setStatus(ResultParame.ResultNumber.ONE.getNumber());
				ar.settId(trading.getId());
				ar.setType(ResultParame.ResultNumber.ZERO.getNumber());
				ar.setuId(uid);
				ar.setUseDate(new Date());
				// 向领宝记录表中插入记录
				activityRecordMapper.insert(ar);
				UsersAccount ua = usersAccountMapper.selectByUid(uid);
				ua.setCountLingbao(lingbao + ua.getCountLingbao());
				// 更新用户账户领宝数量
				usersAccountMapper.updateByPrimaryKey(ua);
				// 发送短信
				try {
					String sendContent = AppCons.buy_content;
					Users users = usersMapper.selectByPrimaryKey(uid);
					sendContent = MessageFormat.format(sendContent, users.getLoginAccount(), product.getName(),
							trading.getFinancialMoney());
					// sendSMSUtils.sendSMS(users.getTelephone(), sendContent);
					// 发送短信修改为放入redis统一发送
					SmsMessage message = new SmsMessage();
					message.setTelephone(users.getTelephone());
					message.setContent(sendContent);
					smsService.saveSmsMessage(message);
				} catch (Exception e) {
					e.printStackTrace();
				}

			}
		} else if (null != chedaibaocode && chedaibaocode.equals(productCode)) {
			Product product = productMapper.selectByPrimaryKey(trading.getpId());
			int lingbao = (trading.getFinancialMoney().multiply(new BigDecimal(product.getfTime())))
					.divide(new BigDecimal(ResultParame.ResultNumber.THREE_SIXTY_FIVE.getNumber()
							* ResultParame.ResultNumber.FIFTY.getNumber()), RoundingMode.HALF_UP)
					.intValue();
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("uId", uid);
			map.put("tId", trading.getId());
			map.put("name", "理财");
			map.put("date", DateUtils.getDtStr(new Date()));
			map.put("amount", lingbao);
			int i = activityRecordMapper.queryByTidUidName(map);
			if (i < ResultParame.ResultNumber.ONE.getNumber()) {
				ActivityRecord ar = new ActivityRecord();
				ar.setAmount(lingbao);
				ar.setContent("购买车贷宝送领宝");
				ar.setName("理财");
				ar.setStatus(ResultParame.ResultNumber.ONE.getNumber());
				ar.settId(trading.getId());
				ar.setType(ResultParame.ResultNumber.ZERO.getNumber());
				ar.setuId(uid);
				ar.setUseDate(new Date());
				// 向领宝记录表中插入记录
				activityRecordMapper.insert(ar);
				UsersAccount ua = usersAccountMapper.selectByUid(uid);
				ua.setCountLingbao(lingbao + ua.getCountLingbao());
				// 更新用户账户领宝数量
				usersAccountMapper.updateByPrimaryKey(ua);
				// 发送短信
				try {
					String sendContent = AppCons.buy_content;
					Users users = usersMapper.selectByPrimaryKey(uid);
					sendContent = MessageFormat.format(sendContent, users.getLoginAccount(), product.getName(),
							trading.getFinancialMoney());
					// sendSMSUtils.sendSMS(users.getTelephone(), sendContent);
					// 发送短信修改为放入redis统一发送
					SmsMessage message = new SmsMessage();
					message.setTelephone(users.getTelephone());
					message.setContent(sendContent);
					smsService.saveSmsMessage(message);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}

	}

	/**
	 * 支付完成处理方法
	 * 
	 * @param i
	 *            1：支付成功， 6：支付出来中
	 * @param tId
	 * @param bizCode
	 *            订单号
	 * @param infoId
	 * @param uid
	 *            用户ID
	 */
	private boolean payOverOptertion(Integer i, Integer tId, String bizCode, Integer infoId, String uid) {
		try {
			Trading trading = tradingMapper.selectTradingWithProduect(tId);

			System.out.println(tId + "\t" + infoId + "\t" + JSON.toJSON(trading));

			if (trading != null) {
				LOGGER.info("用户支付业务，trading不为空");
				
				// 先判断产品状态
				boolean flag = false;
				if (trading.getProduct().getpModel() == ResultParame.ResultNumber.ONE.getNumber()) {
					if (i == ResultParame.ResultNumber.ONE.getNumber()) {
                        flag = tradingFixRuleBuyService.handleBuyProduct(tId, bizCode, uid, CallbackType.ok);
					} else {
                        flag = tradingFixRuleBuyService.handleBuyProduct(tId, bizCode, uid, CallbackType.trading);
					}
				} else {
					System.out.println(trading.getStatus());
					if (i == ResultParame.ResultNumber.ONE.getNumber()) {
                        flag = tradingFixRuleBuyService.handleBuyProduct(tId, bizCode, uid, CallbackType.ok);
					} else {
                        flag = tradingFixRuleBuyService.handleBuyProduct(tId, bizCode, uid, CallbackType.trading);
					}

					if (flag) {

						LOGGER.info("账户" + uid + "订单购买成功" + bizCode);
						// 更新礼物订单表
						GiftDetail record = new GiftDetail();
						record.setState(ResultParame.ResultNumber.ONE.getNumber());
						GiftDetailExample example = new GiftDetailExample();
						example.createCriteria().andTIdEqualTo(tId);
						giftDetailMapper.updateByExample(record, example);
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	@Override
	public void buyGetLingbao(String uId, Integer tId, Integer infoId) {
		buyWeiyingbaoGetLingbao(uId, tId, infoId);
	}

	@Override
	public boolean payOptertion(int i, int tId, String bizNumber, int infoId, String uId) {
		return payOverOptertion(i, tId, bizNumber, infoId, uId);
	}

	@Override
	public JSONObject onlinePayment(HttpServletRequest request, String uId) {
		JSONObject json = new JSONObject();

		String onlineBankNo = request.getParameter("onlineBankNo");
		String dizNumber = request.getParameter("dizNumber");
		String strong = request.getParameter("strong");
		String tId = request.getParameter("tId");
		String infoId = request.getParameter("infoId");
		String pType = request.getParameter("pType");

		String newDizNumber = DateUtils.getDateStr() + "-" + payConfig.getGateway_no() + "-" + dizNumber;

		PurchaseActivePostInfo info = null;
		boolean isTrue = true;
		if (pType.equals("17")) {
			info = purchaseActiveMapper.queryPostInfo2(dizNumber);
			if (info != null && !info.getStatus().equals("2")) {
				isTrue = false;
			}
		} else {
			info = purchaseActiveMapper.queryPostInfo(dizNumber);
			if (info != null && !info.getStatus().equals("12")) {
				isTrue = false;
			}
		}

		if (!isTrue) {
			json.put("code", ResultParame.ResultInfo.RECOMMIT.getCode());
			json.put("msg", ResultParame.ResultInfo.RECOMMIT.getMsg());
			return json;
		}

		String n = "\n";
		StringBuffer sb = new StringBuffer();
		sb.append("网银支付参数：" + n);
		sb.append("订单号:" + newDizNumber + n);
		sb.append("支付金额:" + new BigDecimal(Double.parseDouble(strong)) + n);
		sb.append("银行编号:" + onlineBankNo + n);
		sb.append("返回URL:" + AppCons.JD_ONLINE_CALLBANK + info.gettId() + "_" + infoId + "_" + uId + n);
		sb.append("通知URL:" + "[url:=" + AppCons.JD_ONLINE_NOTITY + info.gettId() + "_" + infoId + "_" + uId + "]" + n);

		String bankUrl = jdPay.sendPayInfoToBank(newDizNumber, new BigDecimal(Double.parseDouble(strong)), onlineBankNo,
				AppCons.JD_ONLINE_CALLBANK + info.gettId() + "_" + infoId + "_" + uId,
				"[url:=" + AppCons.JD_ONLINE_NOTITY + info.gettId() + "_" + infoId + "_" + uId + "]");

		sb.append("银行URL:" + bankUrl);
		System.out.println(sb.toString());

		if (bankUrl != null && !bankUrl.equals("")) {
			json.put("code", ResultParame.ResultInfo.SUCCESS.getCode());
			json.put("msg", bankUrl);
		} else {
			json.put("code", ResultParame.ResultInfo.TXT_FILE.getCode());
			json.put("msg", "银行支付失败!");
		}
		return json;
	}

	@Override
	public String toPurchassActive(HttpServletRequest request, String uId, ModelMap modelMap) {

		String ids = request.getParameter("id");
		if (ids == null || ids.equals("")) {
			System.out.println("id is null");
			return "purchaseActive";
		}

		String[] idsArray = ids.split("_");
		String pType = idsArray[ResultParame.ResultNumber.ONE.getNumber()];

		PurchaseActivePostInfo info = null;
		String pId = request.getParameter("pId");
		if (pId == null || pId.equals("")) {
			System.out.println("pId is null");
			return "purchaseActive";
		}
		Product product = productMapper.selectByPrimaryKey(NumberUtils.toInt(pId));
		if (product == null) {
			System.out.println("product is null");
			return "purchaseActive";
		}
		// 判断稳赢宝和车贷宝开始时间、结束时间、状态
		if (product.getRule() == ResultParame.ResultNumber.TWO.getNumber()) {
			Date date = new Date();
			if (product.getStatus() != ResultParame.ResultNumber.ONE.getNumber()) {
				System.out.println("当前产品暂未开放认购，状态为：" + product.getStatus());
				return "purchaseActive";
			}
			if (date.getTime() < product.getStDt().getTime()) {
				System.out.println("当前产品暂未开放认购");
				return "purchaseActive";
			}
			if (date.getTime() > product.getEdDt().getTime()) {
				System.out.println("当前产品暂已停止认购");
				return "purchaseActive";
			}
		}
		if (NumberUtils.toInt(pId) == ProductUtils.getIntContent("takeHeart_pID")) {
			System.out.println("购买随心取");
			info = purchaseActiveMapper.queryPostInfo2(idsArray[ResultParame.ResultNumber.ZERO.getNumber()]);
			// t_id,如果是随心取，tId为takeheart_transaction_flow表的ID
			modelMap.addAttribute("tId", info.getInfoId());
		} else {
			System.out.println("购买稳盈宝或新手标");
			info = purchaseActiveMapper.queryPostInfo(idsArray[ResultParame.ResultNumber.ZERO.getNumber()]);
			// t_id，如果是稳赢宝，tId为trading表的t_id
			modelMap.addAttribute("tId", info.gettId());
		}
		// 查询绑定的银行卡
		List<UsersBank> usersBank = usersBankMapper.selectByUId(uId);
		System.out.println("查询用户绑定的银行卡" + JSON.toJSONString(usersBank));
		modelMap.addAttribute("usersBank", usersBank);

		// 查询银行支付支持的银行
		List<SupportBank> onlineBank = supportBankMapper.findOnlineBank();
		modelMap.addAttribute("onlineBank", onlineBank);

		// 订单号
		modelMap.addAttribute("dizNumber", idsArray[ResultParame.ResultNumber.ZERO.getNumber()]);
		// 购买金额
		modelMap.addAttribute("financialMoney", info.getFinancialMoney());
		// infoId
		modelMap.addAttribute("infoId", info.getInfoId());
		// 产品类型
		modelMap.addAttribute("pType", pType);
		//产品编码
		modelMap.addAttribute("pCode", product.getCode());
		
		return "purchaseActive";
	}

}
