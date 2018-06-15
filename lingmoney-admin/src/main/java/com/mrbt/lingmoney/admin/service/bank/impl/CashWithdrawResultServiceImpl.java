package com.mrbt.lingmoney.admin.service.bank.impl;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.mrbt.lingmoney.admin.service.bank.CashWithdrawResultService;
import com.mrbt.lingmoney.bank.withdraw.HxCashWithdrawResult;
import com.mrbt.lingmoney.commons.cache.RedisDao;
import com.mrbt.lingmoney.mapper.AccountFlowMapper;
import com.mrbt.lingmoney.mapper.UsersAccountMapper;
import com.mrbt.lingmoney.model.AccountFlow;
import com.mrbt.lingmoney.model.AccountFlowExample;
import com.mrbt.lingmoney.model.UsersAccount;
import com.mrbt.lingmoney.utils.DateUtils;
import com.mrbt.lingmoney.utils.PageInfo;
import com.mrbt.lingmoney.utils.PropertiesUtil;
import com.mrbt.lingmoney.utils.ResultParame;

/**
 * 
 * CashWithdrawResultServiceImpl
 *
 */
@Service
public class CashWithdrawResultServiceImpl implements CashWithdrawResultService {

	@Autowired
	private HxCashWithdrawResult hxCashWithdrawResult;

	@Autowired
	private AccountFlowMapper accountFlowMapper;

	@Autowired
	private UsersAccountMapper usersAccountMapper;

	@Autowired
	private RedisDao redisDao;

	private static final Logger LOGGER = LogManager.getLogger(CashWithdrawResultServiceImpl.class);

	private static final String LOGHEARD = "\nHxCashWithdrawResult_";

	private static final String BANKURL = PropertiesUtil.getPropertiesByKey("BANK_POST_URL");

	@Override
	public PageInfo queryAccountWithdraw(String appId, String oldReqseqno, String uId) throws Exception {
		// 生成日志头
		String logGroup = LOGHEARD + System.currentTimeMillis() + "_";

		LOGGER.info("提现结果查询请求：" + logGroup + "，用户{}，流水号{}", uId, oldReqseqno);

		PageInfo pageInfo = new PageInfo();

		// 如果不传用户ID，则为后台查询；否则为客户端查询
		if (!StringUtils.isEmpty(uId)) {
			UsersAccount usersAccount = usersAccountMapper.selectByUid(uId);
			if (StringUtils.isEmpty(usersAccount)) {
				LOGGER.info("提现结果查询请求失败：" + logGroup + "，用户{}，流水号{}" + "，用户不存在", uId, oldReqseqno);
				pageInfo.setMsg("该用户不存在");
				pageInfo.setCode(ResultParame.ResultInfo.ACCOUNT_NUMBER_NON_EXIST.getCode());
				return pageInfo;
			}
		}

		// 根据原提现交易流水和从数据库中取出原提现交易日期
		AccountFlowExample example = new AccountFlowExample();
		example.createCriteria().andNumberEqualTo(oldReqseqno);
		List<AccountFlow> accountFlowList = accountFlowMapper.selectByExample(example);
		if (StringUtils.isEmpty(accountFlowList) || accountFlowList.size() == 0) {
			LOGGER.info("提现结果查询请求失败：" + logGroup + "，用户{}，流水号{}" + "，交易不存在", uId, oldReqseqno);
			pageInfo.setMsg("该交易不存在");
			pageInfo.setCode(ResultParame.ResultInfo.RETURN_DATA_EMPTY_DATA.getCode());
			return pageInfo;
		}
		AccountFlow accountFlow = accountFlowList.get(0);
		// 查询出uId
		uId = usersAccountMapper.selectByPrimaryKey(accountFlow.getaId()).getuId();

		// 频繁查询过滤
		String seqNo = "queryAccountWithdraw_" + oldReqseqno;
		if (redisDao.get(seqNo) != null) {
			LOGGER.info("充值结果查询请求失败：" + logGroup + "，用户{}，流水号{}" + "，2分钟内频繁查询", uId, oldReqseqno);
			pageInfo.setMsg("2分钟内频繁查询");
			pageInfo.setCode(ResultParame.ResultInfo.RETURN_DATA_EMPTY_DATA.getCode());
			return pageInfo;
		}
		redisDao.set(seqNo, uId);
		redisDao.expire(seqNo, ResultParame.ResultNumber.TWO.getNumber(), TimeUnit.MINUTES);

		Integer returnStatus = accountFlow.getStatus(); // 提现交易状态
		if (returnStatus == 1) {
			LOGGER.info("提现结果查询请求成功：" + logGroup + "，用户{}，流水号{}" + "，提现成功", uId, oldReqseqno);
			pageInfo.setMsg("提现成功");
			pageInfo.setCode(ResultParame.ResultInfo.SUCCESS.getCode());
			pageInfo.setObj(accountFlow.getMoney());
			return pageInfo;
		}
		if (returnStatus == ResultParame.ResultNumber.TWO.getNumber()) {
			LOGGER.info("提现结果查询请求失败：" + logGroup + "，用户{}，流水号{}" + "，该操作已失败", uId, oldReqseqno);
			pageInfo.setMsg("该操作已失败");
			pageInfo.setCode(ResultParame.ResultInfo.TRADING_NOT_SUCCESS.getCode());
			return pageInfo;
		}

		String transDt = new SimpleDateFormat("YYYYMMDD").format(accountFlow.getOperateTime());

		// 5分钟后才可查询提现结果
		Date now = new Date();
		Date operateTime = accountFlow.getOperateTime();
		if (operateTime == null
				|| DateUtils.dateDiffMins(operateTime, now) <= ResultParame.ResultNumber.FIVE.getNumber()) {
			LOGGER.info("提现结果查询请求失败：" + logGroup + "，用户{}，流水号{}" + "，5分钟后再进行查询", uId, oldReqseqno);
			pageInfo.setMsg("5分钟后再进行查询");
			pageInfo.setCode(ResultParame.ResultInfo.TRADING_NOT_SUCCESS.getCode());
			return pageInfo;
		}

		// 7、向银行提交请求
		Map<String, Object> resMap = hxCashWithdrawResult.queryCashWithdrawResult(appId, transDt, oldReqseqno, logGroup,
				BANKURL);

		if (resMap != null) {
			LOGGER.info("提现结果查询请求：" + logGroup + "，用户{}，流水号{}" + "，银行返回报文为{}", uId, oldReqseqno, resMap.toString());
			String errorCode = resMap.get("errorCode").toString();
			if (errorCode.equals("0")) { // 以下信息，只有errorCode =0时才返回，即正常响应时才返回
				String status = resMap.get("status").toString();
				if (status.equals("0")) { // 受理成功，不代表操作成功
					String returnStatus2 = resMap.get("RETURN_STATUS").toString();
					if (returnStatus2.equals("S")) {
						// 处理成功
                        if (rightOption(accountFlow, uId) > 0) {
							pageInfo.setCode(ResultParame.ResultInfo.SUCCESS.getCode());
							pageInfo.setMsg("提现成功");
							pageInfo.setObj(accountFlow.getMoney());
						} else {
							pageInfo.setCode(ResultParame.ResultInfo.UPDATE_FAIL.getCode());
							pageInfo.setMsg("更新数据失败");
						}
					} else if (returnStatus2.equals("F")) {
						// 处理失败
						String errormsg = resMap.get("ERRORMSG").toString();
                        errorOption(accountFlow, uId);
						pageInfo.setCode(ResultParame.ResultInfo.NO_SUCCESS.getCode());
						pageInfo.setMsg(errormsg);
					} else {
						// 处理中
						processOption(accountFlow, uId, returnStatus2);
						pageInfo.setCode(ResultParame.ResultInfo.ING.getCode());
						pageInfo.setMsg("处理中");
					}
				} else if (status.equals("1")) { // 受理失败，交易可置为失败
                    errorOption(accountFlow, uId);
					pageInfo.setCode(ResultParame.ResultInfo.TRADING_NOT_SUCCESS.getCode());
					pageInfo.setMsg("银行受理失败");
				} else {
					pageInfo.setCode(ResultParame.ResultInfo.ING.getCode());
					pageInfo.setMsg("处理中");
				}
			} else { // 错误，返回具体错误原因
				// 如果返回code不为访问频繁
				if (!resMap.get("errorCode").equals("OGW100200009")) {
					// 错误，返回具体错误原因
					String errorMsg = resMap.get("errorMsg").toString();
					// 如果无此流水号，则把所传流水号添加到map
					if (resMap.get("errorCode").equals("OGWERR999997")) {
						resMap.put("OLDREQSEQNO", oldReqseqno);
					}
                    errorOption(accountFlow, uId);
					pageInfo.setCode(ResultParame.ResultInfo.TRADING_NOT_SUCCESS.getCode());
					pageInfo.setMsg(errorMsg);
				} else {
					pageInfo.setCode(ResultParame.ResultInfo.ING.getCode());
					pageInfo.setMsg("处理中");
				}
			}
		} else {
			pageInfo.setCode(ResultParame.ResultInfo.ING.getCode());
			pageInfo.setMsg("处理中");
		}

		return pageInfo;

	}

	/**
	 * 失败操作
	 * 
	 * @param accountFlow
	 *            accountFlow
	 * @param uId
	 *            uId
	 */
    private void errorOption(AccountFlow accountFlow, String uId) {
        // 更新流水状态为失败
		accountFlow.setStatus(ResultParame.ResultNumber.TWO.getNumber());
        accountFlowMapper.updateByPrimaryKeySelective(accountFlow);

        // 更新users_account表中的frozen_money↓ balance↑
        UsersAccount usersAccount = usersAccountMapper.selectByUid(uId);
        BigDecimal frozen = usersAccount.getFrozenMoney().subtract(accountFlow.getMoney());
        usersAccount.setFrozenMoney(frozen.doubleValue() <= 0 ? new BigDecimal(0) : frozen);
        usersAccount.setBalance(usersAccount.getBalance().add(accountFlow.getMoney()));
        usersAccountMapper.updateByPrimaryKeySelective(usersAccount);
	}

	/**
	 * 成功操作
	 * 
	 * @param accountFlow
	 *            accountFlow
	 * @param uId
	 *            uId
	 * @return 数据返回
	 */
    private int rightOption(AccountFlow accountFlow, String uId) {
        // 更新流水状态为成功
        accountFlow.setStatus(1);
        int res = accountFlowMapper.updateByPrimaryKeySelective(accountFlow);
        if (res > 0) {
            // 更新users_account表中的frozen_money，减少
            UsersAccount usersAccount = usersAccountMapper.selectByUid(uId);
            BigDecimal frozen = usersAccount.getFrozenMoney().subtract(accountFlow.getMoney());
            usersAccount.setFrozenMoney(frozen.doubleValue() <= 0 ? new BigDecimal(0) : frozen);
            return usersAccountMapper.updateByPrimaryKeySelective(usersAccount);
		}
		return 0;
	}

	/**
	 * 处理中操作
	 * 
	 * @param accountFlow
	 *            accountFlow
	 * @param uId
	 *            uId
	 * @param status
	 *            status
	 */
    private void processOption(AccountFlow accountFlow, String uId, String status) {
        int returnStatus = 0;
        /**
         * R 页面处理中（客户仍停留在页面操作，25分钟商户后仍收到是此状态可置为失败） N
         * 未知（已提交后台，商户需再次发查询接口。但2小时后状态仍未变的则可置为异常，无需再发起查询， 后续在对账文件中确认交易状态或线下人工处理）
         * P 预授权成功（非实时到账，2小时内到账） D
         * 后台支付系统处理中（商户需再次发查询接口。但1小时后状态仍未变的则可置为异常，无需再发起查询，
         * 后续在对账文件中确认交易状态或线下人工处理） S 成功 F 失败
         * 
         */
        switch (status) {
        case "P":
			returnStatus = 0; // 订单预授权中
            break;
        case "R":
            long minuteDiff = DateUtils.dateDiffMins(accountFlow.getOperateTime(), new Date());
			if (minuteDiff >= ResultParame.ResultNumber.TWENTY_FIVE.getNumber()) {
				returnStatus = ResultParame.ResultNumber.TWO.getNumber(); // 失败
            }
            break;
        case "N":
            long minuteDiff1 = DateUtils.dateDiffMins(accountFlow.getOperateTime(), new Date());
			if (minuteDiff1 >= ResultParame.ResultNumber.ONE_HUNDRED_TWENTY.getNumber()) {
				returnStatus = ResultParame.ResultNumber.TWO.getNumber(); // 异常
				LOGGER.error("账户流水id为{}的提现处理结果异常，请后续在对账文件中确认交易状态或线下人工处理", accountFlow.getId());
			}
            break;
        case "D":
            long minuteDiff2 = DateUtils.dateDiffMins(accountFlow.getOperateTime(), new Date());
			if (minuteDiff2 >= ResultParame.ResultNumber.SIXTY.getNumber()) {
				returnStatus = ResultParame.ResultNumber.TWO.getNumber(); // 异常
				LOGGER.error("账户流水id为{}的提现处理结果异常，请后续在对账文件中确认交易状态或线下人工处理", accountFlow.getId());
			}
            break;

        default:
            break;
        }
        if (returnStatus != 0) {
            accountFlow.setStatus(returnStatus);
            accountFlowMapper.updateByPrimaryKeySelective(accountFlow);
            // 更新users_account表中的frozen_money，减少
            UsersAccount usersAccount = usersAccountMapper.selectByUid(uId);
            BigDecimal frozen = usersAccount.getFrozenMoney().subtract(accountFlow.getMoney());
            usersAccount.setFrozenMoney(frozen.doubleValue() <= 0 ? new BigDecimal(0) : frozen);
            usersAccount.setBalance(usersAccount.getBalance().add(accountFlow.getMoney()));
            usersAccountMapper.updateByPrimaryKeySelective(usersAccount);
		}
	}
}
