package com.mrbt.lingmoney.service.bank.impl;

import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.dom4j.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mrbt.lingmoney.bank.HxBaseData;
import com.mrbt.lingmoney.bank.recall.HxReexchange;
import com.mrbt.lingmoney.mapper.AccountFlowMapper;
import com.mrbt.lingmoney.mapper.UsersAccountMapper;
import com.mrbt.lingmoney.model.AccountFlow;
import com.mrbt.lingmoney.model.AccountFlowExample;
import com.mrbt.lingmoney.model.UsersAccount;
import com.mrbt.lingmoney.service.bank.HxReexchangeService;
import com.mrbt.lingmoney.utils.AppCons;

/**
 *@author syb
 *@date 2017年8月14日 下午3:15:13
 *@version 1.0
 *@description 
 **/
@Service
public class HxReexchangeServiceImpl implements HxReexchangeService {
	private static final Logger LOGGER = LogManager.getLogger(HxReexchangeServiceImpl.class);

	@Autowired
	private HxReexchange hxReexchange;
	@Autowired
	private UsersAccountMapper usersAccountMapper;
	@Autowired
	private AccountFlowMapper accountFlowMapper;

	@Override
	public String handelHxReexchange(Document document) {

		String logGroup = "\n银行退汇_" + System.currentTimeMillis();

        String errorCode = "0"; // 错误码 默认0：成功
        String errorMsg = "success"; // 错误信息，默认success
        String oldReqseqNo = ""; // 本交易的请求报文的报文头流水
        String channelFlow = ""; // 渠道流水号
        String status = "0"; // 业务状态 0：受理成功 1：受理失败 2：受理中 3：受理超时，不确定

		if (document != null) {
			Map<String, Object> map = HxBaseData.xmlToMap(document);

			channelFlow = (String) map.get("channelFlow");
			oldReqseqNo = channelFlow;

			String sts = (String) map.get("STATUS");
			String transMsg = (String) map.get("TRANSMSG");
			String rejectReason = (String) map.get("REJECTREASON");

			AccountFlowExample accountFlowExmp = new AccountFlowExample();
			accountFlowExmp.createCriteria().andNumberEqualTo(oldReqseqNo).andTypeEqualTo(1);

			// 退汇，更新提现状态为失败。修改账户余额和冻结金额
			List<AccountFlow> accountFlowList = accountFlowMapper.selectByExample(accountFlowExmp);
			if (accountFlowList != null && accountFlowList.size() > 0) {
				AccountFlow accountFlow = accountFlowList.get(0);

				AccountFlow accountFlowRecord = new AccountFlow();
				accountFlowRecord.setId(accountFlow.getId());
                accountFlowRecord.setStatus(AppCons.ACCOUNT_FLOW_FAIL);
				accountFlowRecord.setNote(rejectReason);

				int result = accountFlowMapper.updateByPrimaryKeySelective(accountFlowRecord);

				if (result > 0) {
					LOGGER.info("{}退汇，修改提现账户流水状态成功。", logGroup);

					UsersAccount usersAccount = usersAccountMapper.selectByPrimaryKey(accountFlow.getaId());

					if (usersAccount != null) {
						UsersAccount usersAccountRecord = new UsersAccount();

						usersAccountRecord.setId(usersAccount.getId());
						usersAccountRecord.setBalance(usersAccount.getBalance().add(accountFlow.getMoney()));
						usersAccountRecord.setFrozenMoney(usersAccount.getFrozenMoney()
								.subtract(accountFlow.getMoney()));

						result = usersAccountMapper.updateByPrimaryKeySelective(usersAccountRecord);

						if (result > 0) {
							LOGGER.info("{}退汇，更新用户账户{}余额，冻结金额成功。", logGroup, usersAccount.getId());
						} else {
							LOGGER.error("{}退汇，更新用户账户{}余额，冻结金额失败。", logGroup, usersAccount.getId());
						}

					}

				} else {
					LOGGER.error("{}退汇，修改提现账户流水状态失败。", logGroup);
				}

			} else {
				errorCode = "100001";
				errorMsg = "未查询到该提现记录";
				status = "1";
			}

			System.out.println("退汇信息：" + sts + ":" + transMsg + ":" + rejectReason);

		} else {
			errorCode = "100002";
			errorMsg = "解析数据失败";
			status = "1";
		}
		
		LOGGER.info("{}银行退汇请求响应参数：channelFlow:{}, oldReqseqNo:{}, errorCode:{}, errorMsg:{}", logGroup,
				channelFlow, oldReqseqNo, errorCode, errorMsg);

		return hxReexchange.responseReexchange(channelFlow, oldReqseqNo, errorCode, errorMsg, status, logGroup);
	}

}
