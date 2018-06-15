package com.mrbt.lingmoney.admin.controller.task;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ApplicationObjectSupport;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.mrbt.lingmoney.admin.service.schedule.PayByJdService;
import com.mrbt.lingmoney.admin.vo.trading.PayByJdResult;
import com.mrbt.lingmoney.mapper.AccountFlowMapper;
import com.mrbt.lingmoney.mapper.CustomQueryMapper;
import com.mrbt.lingmoney.mapper.RedeemFailFlowMapper;
import com.mrbt.lingmoney.mapper.TakeheartSellPayMapper;
import com.mrbt.lingmoney.mapper.TakeheartTransactionFlowMapper;
import com.mrbt.lingmoney.mapper.TradingMapper;
import com.mrbt.lingmoney.mapper.UsersMapper;
import com.mrbt.lingmoney.mapper.UsersPropertiesMapper;
import com.mrbt.lingmoney.model.AccountFlow;
import com.mrbt.lingmoney.model.AccountFlowExample;
import com.mrbt.lingmoney.model.RedeemFailFlow;
import com.mrbt.lingmoney.model.TakeheartSellPay;
import com.mrbt.lingmoney.model.TakeheartTransactionFlow;
import com.mrbt.lingmoney.model.Trading;
import com.mrbt.lingmoney.model.Users;
import com.mrbt.lingmoney.model.UsersProperties;
import com.mrbt.lingmoney.model.schedule.TakeHeartRedeem;
import com.mrbt.lingmoney.utils.ResultParame.ResultNumber;

/**
 * 随心取兑付申请与查询计划任务--京东
 * 
 * @author Administrator
 *
 */
@Component
public class AutoRedeemTakeHeartTask extends ApplicationObjectSupport {

	private static final Logger LOGGER = LogManager.getLogger(AutoRedeemTakeHeartTask.class);

	@Autowired
	private CustomQueryMapper customQueryMapper;
	@Autowired
	private AccountFlowMapper accountFlowMapper;
	@Autowired
	private UsersMapper usersMapper;
	@Autowired
	private UsersPropertiesMapper usersPropertiesMapper;
	@Autowired
	private TakeheartTransactionFlowMapper takeheartTransactionFlowMapper;
	@Autowired
	private TakeheartSellPayMapper takeheartSellPayMapper;
	@Autowired
	private TradingMapper tradingMapper;
	@Autowired
	private RedeemFailFlowMapper redeemFailFlowMapper;

	@Autowired
	private PayByJdService payByJdService;

	private static final String TAKEN_PART_PAY = "2", TAKEN_ALL_PAY = "3";
	
	private static final int STATUS4 = 4, STATUS15 = 15, STATUS17 = 17, STATUS18 = 18, SLEEP_TIME = 10000; 

	/**
	 * 随心取兑付申请
	 * 
	 * @throws Exception
	 */
	public void youTakePayment() {

		logger.info("随心取兑付申请开始");

		try {
			// 查询兑付数据
			List<TakeHeartRedeem> takeHeartRedeemList = customQueryMapper.listTakeHeartPlayMoney(STATUS4);

			// 设置循环查询，for执行完成后间隔5秒在循环while，如果没有查询到说跳出while循环
			if (takeHeartRedeemList != null && takeHeartRedeemList.size() > 0) {

				LOGGER.info("查询到确认兑付的数据共：" + takeHeartRedeemList.size());

				// 循环查询到的数据
				for (TakeHeartRedeem redeemInfo : takeHeartRedeemList) {
					LOGGER.info(redeemInfo.toString());

					String uid = redeemInfo.getuId();

					// 获取用户信息
					Users users = usersMapper.selectByPrimaryKey(uid);
					
					UsersProperties usersProperties = usersPropertiesMapper.selectByuId(uid);

					// 生成操作时间
					Date optionDate = new Date();

					// 查询绑定的银行
					List<Map<String, Object>> userBankList = customQueryMapper.queryPaymentBankInfoByUid(uid);
					// 判断是否绑定银行卡
					if (userBankList != null && userBankList.size() > 0) {
						// 获取兑付金额
						BigDecimal sellMoney = redeemInfo.getSellMoney();

						// 获取默认的银行卡
						Map<String, Object> tmp = userBankList.get(0);
						String bankShort = (String) tmp.get("bankShort");
						String tel = (String) tmp.get("tel");
						String number = (String) tmp.get("number");

						// 兑付请求
						LOGGER.info("随心取调用兑付");

						PayByJdResult vo = payByJdService.defrayPay(bankShort, number, usersProperties.getName(),
								redeemInfo.getNumber(), tel, sellMoney,
								redeemInfo.getRedeemType() == 0 ? TAKEN_PART_PAY : TAKEN_ALL_PAY, users.getId(),
								redeemInfo.getTfId());
						
						//第一次调用接口返回空，在后台确认兑付后，再次执行才会调用京东兑付
						if (vo == null) {
							continue;
						}
						if (vo.isOk()) { // set status=
							taksPaymentOkOrWait(redeemInfo, 1, redeemInfo.getRedeemType(), usersProperties, users,
									optionDate, vo.getMsg(), number);
						} else if (vo.isWait()) { // set status = 15
							taksPaymentOkOrWait(redeemInfo, STATUS15, STATUS17, usersProperties, users, optionDate, vo.getMsg(),
									number);
						} else {
							taksPaymentError(redeemInfo, STATUS18, "17", usersProperties, users, optionDate, vo.getMsg(),
									tmp);
						}

					} else {
						// 处理没有绑定银行卡逻辑
						taksPaymentError(redeemInfo, STATUS18, "17", usersProperties, users, optionDate, "没有绑定银行卡", null);
					}

				}
				Thread.sleep(SLEEP_TIME);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 随心取兑付查询
	 * 
	 * @throws Exception
	 */
	public void youTakeQuery() {
		LOGGER.info("随心取兑付查询");
		// 查询兑付数据
		List<TakeHeartRedeem> takeHeartRedeemList = customQueryMapper.listTakeHeartPlayMoney(STATUS15);

		try {
			if (takeHeartRedeemList != null && takeHeartRedeemList.size() > 0) {
				for (TakeHeartRedeem redeemInfo : takeHeartRedeemList) {

					PayByJdResult vo = payByJdService.tradeQuery(redeemInfo.getNumber());
					if (vo == null) {
						continue;
					}
					if (vo.isOk()) { // set status = 1
						taksQueryOk(redeemInfo, 1);
					} else if (vo.isWait()) { // set status = 15
						LOGGER.info("查询结果等待，不做处理！");
					} else {
						// ===============兑付失败操作流程
						String uid = redeemInfo.getuId();
						// 选择兑付的时使用的绑定银行银行信息
						List<Map<String, Object>> userBankList = customQueryMapper.queryPaymentBankInfoByUid(uid);
						Map<String, Object> selectUserBank = null;
						for (int j = 0; j < userBankList.size(); j++) {
							Map<String, Object> usersBank = userBankList.get(j);
							LOGGER.info(usersBank.get("number") + "\t" + redeemInfo.getCardnumber());
							if (usersBank.get("number").equals(redeemInfo.getCardnumber())) {
								selectUserBank = usersBank;
							}
						}
						// 获取用户信息
						Users users = usersMapper.selectByPrimaryKey(uid);
						UsersProperties usersProperties = usersPropertiesMapper.selectByuId(uid);
						// 生成操作时间
						Date optionDate = new Date();
						taksQueryError(redeemInfo, vo.getMsg(), STATUS18, usersProperties, users, optionDate, selectUserBank);
					}
				}
				Thread.sleep(SLEEP_TIME);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	/**
	 * 兑付对应数据库操作
	 * @param redeemInfo	redeemInfo
	 * @param status	status
	 * @param pType	pType
	 * @param usersProperties	usersProperties
	 * @param users	users
	 * @param optionDate	optionDate
	 * @param okInfo	okInfo
	 * @param cardNumber	cardNumber
	 */
	@Transactional
	public void taksPaymentOkOrWait(TakeHeartRedeem redeemInfo, int status, int pType,
			UsersProperties usersProperties, Users users, Date optionDate, String okInfo, String cardNumber) {

		// 数据操作结果标识
		int result = 0;

		// 插入流水account_flow
		result = accountFlowMapper.insertSelective(getAccountFlow(redeemInfo, status, okInfo, optionDate, cardNumber));

		if (result > 0) {
			LOGGER.error("随心取兑付，保存账户流水成功。");
		} else {
			LOGGER.error("随心取兑付，保存账户流水失败。{}", redeemInfo.gettId());
		}

		// 更新takeheart_transaction_flow状态
		TakeheartTransactionFlow transFlowRecord = new TakeheartTransactionFlow();
		transFlowRecord.setId(redeemInfo.getTfId());
		transFlowRecord.setState(status);
		if (!StringUtils.isEmpty(cardNumber)) {
			transFlowRecord.setCardPan(cardNumber);
		}
		result = takeheartTransactionFlowMapper.updateByPrimaryKeySelective(transFlowRecord);

		if (result > 0) {
			LOGGER.info("随心取兑付，更新随心取交易流水成功。");
		} else {
			LOGGER.error("随心取兑付，更新{}号随心取交易流水失败。status:{}, cardNumber:{}", redeemInfo.getTfId(), status, cardNumber);
		}

		// 更新takeheart_sell_pay状态
		TakeheartSellPay sellPayRecord = new TakeheartSellPay();
		sellPayRecord.setId(redeemInfo.getId());
		sellPayRecord.setState(status);
		result = takeheartSellPayMapper.updateByPrimaryKeySelective(sellPayRecord);

		if (result > 0) {
			LOGGER.info("随心取兑付，更新随心取卖出信息状态成功。");
		} else {
			LOGGER.error("随心取兑付，更新{}号随心取卖出信息状态失败。status:{}", redeemInfo.getId(), status);
		}
		
		// 成功时
		if (status == 1 && pType == 1) { // 全部赎回
			Trading tradingRecord = new Trading();
			tradingRecord.setId(redeemInfo.gettId());
			tradingRecord.setStatus(ResultNumber.THREE.getNumber());
			result = tradingMapper.updateByPrimaryKeySelective(tradingRecord);

			if (result > 0) {
				LOGGER.info("随心取兑付，全部赎回，更新交易状态成功。");
			} else {
				LOGGER.error("随心取兑付，全部赎回，更新{}号交易状态失败。status:3", redeemInfo.gettId());
			}
		}
	}

	/**
	 * 随心取兑付失败处理
	 * @param redeemInfo		redeemInfo
	 * @param status	status
	 * @param pType	pType
	 * @param usersProperties	usersProperties	
	 * @param users	users
	 * @param optionDate	optionDate
	 * @param errorInfo	errorInfo
	 * @param usersBank	usersBank
	 */
	@Transactional
	public void taksPaymentError(TakeHeartRedeem redeemInfo, int status, String pType,
			UsersProperties usersProperties, Users users, Date optionDate, String errorInfo,
			Map<String, Object> usersBank) {

		// 数据操作成功标识
		int result = 0;

		// 插入错误日志
		result = redeemFailFlowMapper.insertSelective(getRedeemFailFlow(redeemInfo, status, pType, usersProperties,
				users,
				errorInfo, optionDate, usersBank));

		if (result > 0) {
			LOGGER.info("随心取兑付，插入错误日志记录成功。");
		} else {
			LOGGER.error("随心取兑付，插入错误日志记录失败。tid:{},errorInfo:{}", redeemInfo.gettId(), errorInfo);
		}

		// 插入流水，失败是2
		result = accountFlowMapper.insertSelective(getAccountFlow(redeemInfo, ResultNumber.TWO.getNumber(), errorInfo, optionDate,
				(String) usersBank.get("number")));

		if (result > 0) {
			LOGGER.info("随心取兑付，插入账户流水记录成功。");
		} else {
			LOGGER.error("随心取兑付，插入账户流水记录失败。number:{}, errorInfo:{}", usersBank.get("number"), errorInfo);
		}

		// 更新takeheart_transaction_flow状态为0
		TakeheartTransactionFlow transFlowRecord = new TakeheartTransactionFlow();
		transFlowRecord.setId(redeemInfo.getTfId());
		transFlowRecord.setState(0);
		if (usersBank.containsKey("number") && usersBank.get("number") != null) {
			transFlowRecord.setCardPan((String) usersBank.get("number"));
		}
		result = takeheartTransactionFlowMapper.updateByPrimaryKeySelective(transFlowRecord);

		if (result > 0) {
			LOGGER.info("随心取兑付，更新随心取交易流水状态成功。");
		} else {
			LOGGER.error("随心取兑付，更新{}号随心取交易流水状态失败。status:0", redeemInfo.getTfId());
		}

		// 更新takeheart_sell_pay状态0
		TakeheartSellPay takeHeartSellPay = new TakeheartSellPay();
		takeHeartSellPay.setId(redeemInfo.getId());
		takeHeartSellPay.setState(0);
		result = takeheartSellPayMapper.updateByPrimaryKeySelective(takeHeartSellPay);

		if (result > 0) {
			LOGGER.info("随心取兑付，更新随心取卖出信息状态成功。");
		} else {
			LOGGER.error("随心取兑付，更新{}号随心取卖出状态失败。status:0", redeemInfo.getId());
		}
	}

	/**
	 * 查询兑付成功
	 * @param redeemInfo	redeemInfo
	 * @param status	status
	 */
	@Transactional
	public void taksQueryOk(TakeHeartRedeem redeemInfo, int status) {

		// 数据操作成功标识
		int result = 0;

		// 更新流水account_flow状态
		AccountFlowExample accountFlowExmp = new AccountFlowExample();
		accountFlowExmp.createCriteria().andNumberEqualTo(redeemInfo.getNumber());

		AccountFlow accountFlowRecord = new AccountFlow();
		accountFlowRecord.setStatus(status);
		accountFlowRecord.setNote("兑付成功");
		result = accountFlowMapper.updateByExampleSelective(accountFlowRecord, accountFlowExmp);

		if (result > 0) {
			LOGGER.info("随心取兑付查询，更新账户流水状态成功。");
		} else {
			LOGGER.error("随心取兑付查询，更新{}号账户流水状态失败。status：{}", redeemInfo.getNumber(), status);
		}

		// 更新takeheart_transaction_flow状态
		TakeheartTransactionFlow transFlowRecord = new TakeheartTransactionFlow();
		transFlowRecord.setId(redeemInfo.getTfId());
		transFlowRecord.setState(status);
		result = takeheartTransactionFlowMapper.updateByPrimaryKeySelective(transFlowRecord);

		if (result > 0) {
			LOGGER.info("随心取兑付查询，更新随心取交易流水状态成功。");
		} else {
			LOGGER.error("随心取兑付查询，更新{}号随心取交易流水状态失败。status：{}", redeemInfo.getTfId(), status);
		}

		// 更新takeheart_sell_pay状态
		TakeheartSellPay takeHeartSellPay = new TakeheartSellPay();
		takeHeartSellPay.setId(redeemInfo.getId());
		takeHeartSellPay.setState(status);
		result = takeheartSellPayMapper.updateByPrimaryKeySelective(takeHeartSellPay);

		if (result > 0) {
			LOGGER.info("随心取兑付查询，更新随心取卖出状态成功。");
		} else {
			LOGGER.error("随心取兑付查询，更新{}号随心取卖出状态失败。status：{}", redeemInfo.getId(), status);
		}

		if (redeemInfo.getRedeemType() == 1) { // 全部赎回
			Trading tradingRecord = new Trading();
			tradingRecord.setId(redeemInfo.gettId());
			tradingRecord.setStatus(ResultNumber.THREE.getNumber());
			result = tradingMapper.updateByPrimaryKeySelective(tradingRecord);

			if (result > 0) {
				LOGGER.info("随心取兑付查询，全部赎回，更新交易状态成功。");
			} else {
				LOGGER.error("随心取兑付查询，全部赎回，更新{}号交易状态成功。status：3", redeemInfo.gettId());
			}

		}
	}

	/**
	 * 查询兑付失败
	 * @param redeemInfo	redeemInfo
	 * @param resultInfo	resultInfo
	 * @param status	status
	 * @param usersProperties	usersProperties
	 * @param users	users
	 * @param optionDate	optionDate
	 * @param usersBank	usersBank
	 */
	@Transactional
	public void taksQueryError(TakeHeartRedeem redeemInfo, String resultInfo, int status,
			UsersProperties usersProperties, Users users, Date optionDate, Map<String, Object> usersBank) {

		// 数据操作成功 标识
		int result = 0;

		// 插入错误日志redeem_fail_flow
		result = redeemFailFlowMapper.insertSelective(getRedeemFailFlow(redeemInfo, status, "17", usersProperties,
				users, resultInfo, optionDate, usersBank));

		if (result > 0) {
			LOGGER.info("随心取兑付结果查询，插入错误日志记录成功。");
		} else {
			LOGGER.error("随心取兑付结果查询，插入错误日志记录失败。tid:{},errorInfo:{}", redeemInfo.gettId(), resultInfo);
		}

		// 更新流水account_flow状态2
		AccountFlowExample accountFlowExmp = new AccountFlowExample();
		accountFlowExmp.createCriteria().andNumberEqualTo(redeemInfo.getNumber());

		AccountFlow accountFlowRecord = new AccountFlow();
		accountFlowRecord.setStatus(ResultNumber.TWO.getNumber());
		accountFlowRecord.setNote(resultInfo);
		result = accountFlowMapper.updateByExampleSelective(accountFlowRecord, accountFlowExmp);

		if (result > 0) {
			LOGGER.info("随心取兑付结果查询，更新账户流水状态成功。");
		} else {
			LOGGER.error("随心取兑付结果查询，更新{}号账户流水状态失败。status:2, resultInfo:{}", redeemInfo.getNumber(), resultInfo);
		}

		// 更新takeheart_transaction_flow状态为0
		TakeheartTransactionFlow transFlowRecord = new TakeheartTransactionFlow();
		transFlowRecord.setId(redeemInfo.getTfId());
		transFlowRecord.setState(0);
		if (usersBank.containsKey("number") && usersBank.get("number") != null) {
			transFlowRecord.setCardPan((String) usersBank.get("number"));
		}
		result = takeheartTransactionFlowMapper.updateByPrimaryKeySelective(transFlowRecord);

		if (result > 0) {
			LOGGER.info("随心取兑付结果查询，更新随心取交易流水成功。");
		} else {
			LOGGER.error("随心取兑付结果查询，更新{}号随心取交易流水失败。status:0", redeemInfo.getTfId());
		}

		// 更新takeheart_sell_pay状态0
		TakeheartSellPay takeHeartSellPay = new TakeheartSellPay();
		takeHeartSellPay.setId(redeemInfo.getId());
		takeHeartSellPay.setState(0);
		result = takeheartSellPayMapper.updateByPrimaryKeySelective(takeHeartSellPay);

		if (result > 0) {
			LOGGER.info("随心取兑付结果查询，更新随心取卖出信息成功。");
		} else {
			LOGGER.error("随心取兑付结果查询，更新{}号随心取取卖出信息失败。status:0", redeemInfo.getId());
		}
	}

	/**
	 * 获取流水accountFlow对象
	 * @param redeemInfo	
	 * @param status	status
	 * @param errorInfo		optionDate
	 * @param optionDate	optionDate
	 * @param cardNumber cardNumber
	 * @return optionDate
	 */
	private AccountFlow getAccountFlow(TakeHeartRedeem redeemInfo, int status, String errorInfo, Date optionDate,
			String cardNumber) {
		AccountFlow accountFlow = new AccountFlow();
		accountFlow.settId(redeemInfo.gettId());
		accountFlow.setaId(redeemInfo.getaId());
		accountFlow.setOperateTime(optionDate);
		accountFlow.setMoney(redeemInfo.getSellMoney());
		accountFlow.setNumber(redeemInfo.getNumber());
		accountFlow.setStatus(status);
		accountFlow.setNote(errorInfo);
		accountFlow.setType(ResultNumber.THREE.getNumber());
		accountFlow.setFee(new BigDecimal(0));
		accountFlow.setPlatform(0);
		accountFlow.setCardPan(cardNumber);
		return accountFlow;
	}

	/**
	 * 获取错误日志信息vo
	 * @param redeemInfo  A
	 * @param status	 A
	 * @param pType	A
	 * @param usersProperties	A
	 * @param users	A
	 * @param errorInfo	A
	 * @param optionDate	A
	 * @param usersBank	A
	 * @return	REUTRN 
	 */
	private RedeemFailFlow getRedeemFailFlow(TakeHeartRedeem redeemInfo, int status, String pType,
			UsersProperties usersProperties, Users users, String errorInfo, Date optionDate,
			Map<String, Object> usersBank) {

		RedeemFailFlow redeemFailFlow = new RedeemFailFlow();
		redeemFailFlow.setuId(redeemInfo.getuId());
		redeemFailFlow.setuName(usersProperties.getName());
		redeemFailFlow.setuTelephone(users.getTelephone());
		redeemFailFlow.settId(redeemInfo.gettId());
		redeemFailFlow.setpCode(redeemInfo.getpCode());
		redeemFailFlow.setDizNumber(redeemInfo.getNumber());
		redeemFailFlow.setBuyMoney(redeemInfo.getSellMoney());
		redeemFailFlow.setRedeemFailTime(optionDate);
		redeemFailFlow.setFailReason(errorInfo);
		redeemFailFlow.setStatus(status);
		redeemFailFlow.setOperateName("系统");
		redeemFailFlow.setOperateTime(new Date());
		redeemFailFlow.setpType(pType);
		redeemFailFlow.setBankCard((String) usersBank.get("number"));
		redeemFailFlow.setBankName((String) usersBank.get("bankName"));
		redeemFailFlow.setBankShort((String) usersBank.get("bankShort"));
		redeemFailFlow.setBankTel((String) usersBank.get("tel"));
		return redeemFailFlow;
	}

}
