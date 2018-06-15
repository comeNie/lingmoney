package com.mrbt.lingmoney.admin.controller.task;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ApplicationObjectSupport;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.mrbt.lingmoney.admin.service.schedule.PayByJdService;
import com.mrbt.lingmoney.admin.service.trading.RedeemFailFlowService;
import com.mrbt.lingmoney.admin.vo.trading.PayByJdResult;
import com.mrbt.lingmoney.mapper.AccountFlowMapper;
import com.mrbt.lingmoney.mapper.CustomQueryMapper;
import com.mrbt.lingmoney.mapper.TradingMapper;
import com.mrbt.lingmoney.mapper.UsersAccountMapper;
import com.mrbt.lingmoney.mapper.UsersMapper;
import com.mrbt.lingmoney.mapper.UsersPropertiesMapper;
import com.mrbt.lingmoney.model.AccountFlow;
import com.mrbt.lingmoney.model.AccountFlowExample;
import com.mrbt.lingmoney.model.RedeemFailFlow;
import com.mrbt.lingmoney.model.Trading;
import com.mrbt.lingmoney.model.Users;
import com.mrbt.lingmoney.model.UsersAccount;
import com.mrbt.lingmoney.model.UsersProperties;
import com.mrbt.lingmoney.model.schedule.WenYingBaoRedeem;
import com.mrbt.lingmoney.utils.ResultParame;

/**
 * 稳赢宝自动还款--京东
 * 
 * @author Administrator
 *
 */
@Component
public class AutoRedeemTask extends ApplicationObjectSupport {

	private static final Logger LOGGER = LogManager.getLogger(AutoRedeemTask.class);

	@Autowired
	private CustomQueryMapper customQueryMapper;
	@Autowired
	private AccountFlowMapper accountFlowMapper;
	@Autowired
	private UsersPropertiesMapper usersPropertiesMapper;
	@Autowired
	private UsersMapper usersMapper;
	@Autowired
	private TradingMapper tradingMapper;

	@Autowired
	private RedeemFailFlowService redeemFailFlowService;
	@Autowired
	private PayByJdService payByJdService;
    @Autowired
    private UsersAccountMapper usersAccountMapper;

	/**
	 * 成功状态
	 */
	private final int statusOk = 1;
	/**
	 * 失败状态
	 */
	private final int statusFail = 2;
	
	/**
	 * 产品兑付状态
	 */
	private final int sellStatus = 2;
	/**
	 * 等待状态
	 */
	private final int statusWait = 15;
	/**
	 * 设置交易类型为稳赢宝
	 */
	private final String bizType = "1";
	
	private final int pType = 0;

	/**
	 * 京东兑付第一步
	 */
	public void autoRedeem() {
		logger.info("\n执行自动还款功能第一步");
		
		Map<String, Object> parMap = new HashMap<String, Object>();
		parMap.put("status", sellStatus); //产品兑付状态
		parMap.put("pType", 0); //产品类型，是京东，还是华兴，这里只有京东才执行这个计划任务
		// 还款数据
		List<WenYingBaoRedeem> redeemInfoList = customQueryMapper.listAutoPlayMoney(parMap);
		try {
			if (redeemInfoList != null && redeemInfoList.size() > 0) {
				Trading trading = null;
				BigDecimal sellCount = null;

				for (WenYingBaoRedeem redeemInfo : redeemInfoList) {
					
					sellCount = redeemInfo.getSellCount();
					
					int tid = redeemInfo.gettId();
					trading = tradingMapper.selectByPrimaryKey(tid);
					// 生成account_flow基本数据
					
					AccountFlowExample accountFlowExmp = new AccountFlowExample();
					accountFlowExmp.createCriteria().andNumberEqualTo(trading.getOutBizCode());
					List<AccountFlow> accountFlowList = accountFlowMapper.selectByExample(accountFlowExmp);
					AccountFlow accountFlow = null;
					if (accountFlowList == null || accountFlowList.isEmpty()) {
						accountFlow = generateAccountFlow(sellCount, trading.getOutBizCode(), redeemInfo);
						accountFlowMapper.insertSelective(accountFlow);
					} else {
						accountFlow = accountFlowList.get(0);
					}

					String uid = redeemInfo.getuId();

					// 查找用户属性表
					UsersProperties usersProperties = usersPropertiesMapper.selectByuId(uid);
					// 查找用户绑定的银行卡，按照default默认倒序查找
					List<Map<String, Object>> userBankList = customQueryMapper.queryPaymentBankInfoByUid(uid);
					Users users = usersMapper.selectByPrimaryKey(uid);
					if (userBankList == null || userBankList.size() < 1) {
						LOGGER.info("用户{}没有绑定银行卡，添加错误记录流水", uid);

						RedeemFailFlow redeemFailFlow = generateRedeemFailFlow(sellCount, trading.getOutBizCode(),
								redeemInfo, usersProperties.getName(), users.getTelephone(), "没有绑定银行卡");
						redeemFailFlowService.save(redeemFailFlow);

						sellOperationByStatus(uid, tid, sellCount.subtract(redeemInfo.getBuyMonney()), trading.getOutBizCode(), "没有绑定银行卡", statusFail);
					} else {
						Map<String, Object> tmp = userBankList.get(0);
						String bankShort = (String) tmp.get("bankShort");
						String tel = (String) tmp.get("tel");
						String number = (String) tmp.get("number");

						PayByJdResult vo = payByJdService.defrayPay(bankShort, number, usersProperties.getName(),
								trading.getOutBizCode(), tel, sellCount, bizType, users.getId(), trading.getId());

						if (vo == null) {
							continue;
						}

						if (vo.isOk()) { // set status = 3
							LOGGER.info("用户{}交易成功，金额为：{}", uid, sellCount.toString());

							sellOperationByStatus(uid, tid, sellCount.subtract(redeemInfo.getBuyMonney()), trading.getOutBizCode(), number, statusOk);
						} else if (vo.isWait()) { // set status
							// =15
							LOGGER.info("用户{}交易等待中，金额为：{}", uid, sellCount.toString());

							sellOperationByStatus(uid, tid, sellCount.subtract(redeemInfo.getBuyMonney()), trading.getOutBizCode(), number, statusWait);
						} else {
							LOGGER.info("用户{}交易失败，金额为：{},保存错误信息流水", uid, sellCount.toString());

							RedeemFailFlow redeemFailFlow = generateRedeemFailFlow(sellCount, trading.getOutBizCode(),
									redeemInfo, usersProperties.getName(), users.getTelephone(), "info:" + vo.getMsg());
							redeemFailFlowService.save(redeemFailFlow);

							sellOperationByStatus(uid, tid, sellCount.subtract(redeemInfo.getBuyMonney()), trading.getOutBizCode(), number, statusFail);

						}

					}
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 京东兑付第二步
	 */
	public void autoRedeem2() {
		LOGGER.info("执行自动还款功能第二步查询等待status=15");
		
		Map<String, Object> parMap = new HashMap<String, Object>();
		parMap.put("status", statusWait); //产品兑付状态
		parMap.put("pType", pType); //产品类型，是京东，还是华兴，这里只有京东才执行这个计划任务
		List<WenYingBaoRedeem> redeemInfoList = customQueryMapper.listAutoPlayMoney(parMap);
		
		try {
			if (redeemInfoList != null && redeemInfoList.size() > 0) {
				Trading trading = null;
				BigDecimal sellCount = null;
				for (WenYingBaoRedeem redeemInfo : redeemInfoList) {

					int tid = redeemInfo.gettId();
					// 根据tid查询交易表
					trading = tradingMapper.selectByPrimaryKey(tid);
					PayByJdResult vo = payByJdService.tradeQuery(trading.getOutBizCode());
					if (vo == null) {
						continue;
					}
					sellCount = redeemInfo.getSellCount();

					String uid = redeemInfo.getuId();
					// 查找用户属性表
					UsersProperties usersProperties = usersPropertiesMapper.selectByuId(uid);

					Users users = usersMapper.selectByPrimaryKey(uid);

					if (vo.isOk()) { // set status = 3
						LOGGER.info("用户{}还款成功，金额为：{}", uid, sellCount.toString());

						sellOperationByStatus(uid, tid, sellCount.subtract(redeemInfo.getBuyMonney()), trading.getOutBizCode(), trading.getCardPan(),
								statusOk);
					} else if (!vo.isWait()) {
						LOGGER.info("用户{}还款失败，金额为：{},保存错误信息流水", uid, sellCount.toString());

						RedeemFailFlow redeemFailFlow = generateRedeemFailFlow(sellCount, trading.getOutBizCode(),
								redeemInfo, usersProperties.getName(), users.getTelephone(), "info:" + vo.getMsg());
						redeemFailFlowService.save(redeemFailFlow);

						sellOperationByStatus(uid, tid, sellCount.subtract(redeemInfo.getBuyMonney()), trading.getOutBizCode(), trading.getCardPan(),
								statusFail);

					}

					Thread.sleep(ResultParame.ResultNumber.THIRTY.getNumber()
							* ResultParame.ResultNumber.ONE_THOUSAND.getNumber());
				}

			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		// 处理主表记录
		try {
			customQueryMapper.updateSellBatchAfterRepayment();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 生成流水表
	 * 
	 * @param sellCount
	 *            卖出的金额
	 * @param requestNo
	 *            流水订单号
	 * @param redeem
	 *            封装
	 * @return	return
	 */
	private AccountFlow generateAccountFlow(BigDecimal sellCount, String requestNo, WenYingBaoRedeem redeem) {
		AccountFlow accountFlowRecord = new AccountFlow();

		accountFlowRecord.setMoney(sellCount);
		accountFlowRecord.setOperateTime(new Date());
		accountFlowRecord.setNumber(requestNo);
		accountFlowRecord.setPlatform(0);
		accountFlowRecord.setType(ResultParame.ResultNumber.THREE.getNumber());
		accountFlowRecord.setStatus(0);
		accountFlowRecord.setaId(redeem.getaId());
		accountFlowRecord.settId(redeem.gettId());
        accountFlowRecord.setNote("赎回");
		return accountFlowRecord;
	}

	/**
	 * 生成还款错误记录，主要是用户没有默认银行卡
	 * 
	 * @param sellCount
	 *            兑付统计
	 * @param outBizCode
	 *            订单号
	 * @param redeem
	 *            封装
	 * @param name
	 *            姓名
	 * @param telephone
	 *            用户电话
	 * @param rason
	 *            rason
	 * @return 数据返回
	 */
	private RedeemFailFlow generateRedeemFailFlow(BigDecimal sellCount, String outBizCode, WenYingBaoRedeem redeem,
			String name, String telephone, String rason) {
		RedeemFailFlow redeemFailFlow = new RedeemFailFlow();
		redeemFailFlow.setBuyMoney(sellCount);
		redeemFailFlow.setDizNumber(outBizCode);
		redeemFailFlow.setFailReason(rason);
		redeemFailFlow.setpCode(redeem.getpCode());
		redeemFailFlow.setRedeemFailTime(new Date());
		redeemFailFlow.setStatus(ResultParame.ResultNumber.EIGHTEEN.getNumber());
		redeemFailFlow.settId(redeem.gettId());
		redeemFailFlow.setuId(redeem.getuId());
		redeemFailFlow.setuName(name);
		redeemFailFlow.setuTelephone(telephone);
		return redeemFailFlow;
	}

	/**
	 * 根据状态修改表，users_account,account_flow,trading,trading_fix_info表
	 * 
	 * @param uid
	 *            用户id
	 * @param tid
	 *            交易id
	 * @param profit
	 *            利润
	 * @param requestNO
	 *            交易流水
	 * @param cardNum
	 *            银行卡号
	 * @param status
	 *            状态值
	 * @return return
	 */
	@Transactional
    public boolean sellOperationByStatus(String uid, int tid, BigDecimal profit, String requestNO,
			String cardNum, int status) {
		// 数据操作是否成功标识
		int result = 0;

		// 卖出后数据更新所需参数MAP
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("cardPan", cardNum);
		params.put("tid", tid);

		if (status == 1) {
			params.put("sbiStatus", ResultParame.ResultNumber.THREE.getNumber());
			params.put("tradingStatus", ResultParame.ResultNumber.THREE.getNumber());

            // 稳赢宝卖出成功后增加账户总收益
            UsersAccount usersAccount = usersAccountMapper.selectByUid(uid);
            if (usersAccount != null) {
                UsersAccount record = new UsersAccount();
                record.setId(usersAccount.getId());
                record.setIncome(usersAccount.getIncome().add(profit));
                usersAccountMapper.updateByPrimaryKeySelective(record);
            }

		} else if (status == ResultParame.ResultNumber.FIFTEEN.getNumber()) {
			params.put("sbiStatus", ResultParame.ResultNumber.FIFTEEN.getNumber());
			params.put("tradingStatus", ResultParame.ResultNumber.FIFTEEN.getNumber());

		} else if (status == ResultParame.ResultNumber.TWO.getNumber()) {
			params.put("sbiStatus", ResultParame.ResultNumber.FOUR.getNumber());
			params.put("tradingStatus", ResultParame.ResultNumber.SIXTEEN.getNumber());
		}

		// 更新sell_batch_info,trading,trading_fix_info表
		if (params.containsKey("sbiStatus")) {
			result = customQueryMapper.updateDataAfterSell(params);

			if (result > 0) {
				LOGGER.info("交易{}自动还款成功，更新卖出信息成功。", tid);
			} else {
				LOGGER.error("交易{}自动还款成功，更新卖出信息失败。", tid);
				return false;
			}
		}

		AccountFlowExample accountFlowExmp = new AccountFlowExample();
		accountFlowExmp.createCriteria().andNumberEqualTo(requestNO);
		List<AccountFlow> accountFlowList = accountFlowMapper.selectByExample(accountFlowExmp);

		if (accountFlowList != null && accountFlowList.size() > 0) {
			AccountFlow accountFlow = accountFlowList.get(0);

			AccountFlow accountFlowRecord = new AccountFlow();
			accountFlowRecord.setId(accountFlow.getId());
			accountFlowRecord.setStatus(status);
			accountFlowRecord.setCardPan(cardNum);
			result = accountFlowMapper.updateByPrimaryKeySelective(accountFlowRecord);

			if (result > 0) {
				LOGGER.info("自动还款完成，更新账户流水信息成功。");
			} else {
				LOGGER.error("自动还款完成，更新账户流水信息失败。", accountFlowRecord.toString());
				return false;
			}

		} else {
			LOGGER.error("自动还款完成，更新账户流水信息失败。未查询到编号：{}的账户流水", requestNO);
			return false;
		}

		return true;
	}
}
