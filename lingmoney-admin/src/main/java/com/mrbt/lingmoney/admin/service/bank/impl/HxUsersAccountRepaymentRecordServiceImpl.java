package com.mrbt.lingmoney.admin.service.bank.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.mrbt.lingmoney.admin.exception.DataUnCompleteException;
import com.mrbt.lingmoney.admin.service.bank.HxUsersAccountRepaymentRecordService;
import com.mrbt.lingmoney.mapper.HxUsersAccountRepaymentRecordMapper;
import com.mrbt.lingmoney.mapper.TradingMapper;
import com.mrbt.lingmoney.mapper.UsersAccountMapper;
import com.mrbt.lingmoney.model.HxUsersAccountRepaymentRecord;
import com.mrbt.lingmoney.model.Trading;
import com.mrbt.lingmoney.model.UsersAccount;
import com.mrbt.lingmoney.utils.PageInfo;
import com.mrbt.lingmoney.utils.ResultParame.ResultInfo;

/**
 *
 *@author syb
 *@date 2017年9月8日 下午2:31:36
 *@version 1.0
 **/
@Service
public class HxUsersAccountRepaymentRecordServiceImpl implements HxUsersAccountRepaymentRecordService {

	@Autowired
	private HxUsersAccountRepaymentRecordMapper hxUsersAccountRepaymentRecordMapper;
	@Autowired
	private TradingMapper tradingMapper;
	@Autowired
	private UsersAccountMapper usersAccountMapper;

	@Override
	public PageInfo queryWithSelfCondition(String tel, String name, String initTime, Integer page, Integer rows, Integer status) {

		PageInfo pi = new PageInfo(page, rows);

		Map<String, Object> params = new HashMap<String, Object>();
		params.put("tel", tel);
		params.put("name", name);
		params.put("initTime", initTime);

		if (StringUtils.isEmpty(status)) {
			params.put("status", null);
		} else {
			params.put("status", status);
		}

		params.put("start", pi.getFrom());
		params.put("number", pi.getSize());
		List<Map<String, Object>> list = hxUsersAccountRepaymentRecordMapper.queryWithSelfCondition(params);
		pi.setRows(list);

		if (list != null && list.size() > 0) {
			int total = hxUsersAccountRepaymentRecordMapper.countWithSelfCondition(params);

			pi.setCode(ResultInfo.SUCCESS.getCode());
			pi.setMsg(ResultInfo.SUCCESS.getMsg());
			pi.setTotal(total);

		} else {
			pi.setCode(ResultInfo.NO_DATA.getCode());
			pi.setMsg(ResultInfo.NO_DATA.getMsg());
			pi.setTotal(0);
		}

		return pi;
	}

	@Transactional
	@Override
	public PageInfo manualRecoverPayment(String id) {
		PageInfo pi = new PageInfo();

		if (StringUtils.isEmpty(id)) {
			pi.setCode(ResultInfo.PARAMETER_ERROR.getCode());
			pi.setMsg(ResultInfo.PARAMETER_ERROR.getMsg());
			return pi;
		}

		HxUsersAccountRepaymentRecord repaymentRecord = hxUsersAccountRepaymentRecordMapper.selectByPrimaryKey(id);

		if (repaymentRecord == null) {
			pi.setCode(ResultInfo.EMPTY_DATA.getCode());
			pi.setMsg(ResultInfo.EMPTY_DATA.getMsg());
			return pi;
		}

		if (repaymentRecord.getState() == 1) {
			pi.setCode(ResultInfo.SUCCESS.getCode());
			pi.setMsg("回款成功");
			return pi;
		}
		
		Trading trading = tradingMapper.selectByPrimaryKey(repaymentRecord.gettId());
		
		UsersAccount userAccount = usersAccountMapper.selectByUid(trading.getuId());

		if (userAccount == null) {
			throw new DataUnCompleteException("用户账户信息有误");
		}

		UsersAccount userAccountRecord = new UsersAccount();
		userAccountRecord.setId(userAccount.getId());
		userAccountRecord.setBalance(userAccount.getBalance().add(repaymentRecord.getAmount()));
		userAccountRecord.setFrozenMoney(userAccount.getFrozenMoney().subtract(repaymentRecord.getAmount()));

		int result = usersAccountMapper.updateByPrimaryKeySelective(userAccountRecord);

		if (result < 1) {
			throw new DataUnCompleteException("更新用户账户信息失败");
		}

		HxUsersAccountRepaymentRecord record = new HxUsersAccountRepaymentRecord();
		record.setId(id);
		record.setState(1);
		result = hxUsersAccountRepaymentRecordMapper.updateByPrimaryKeySelective(record);

		if (result < 1) {
			throw new DataUnCompleteException("更新回款记录信息失败");
		}

		pi.setCode(ResultInfo.SUCCESS.getCode());
		pi.setMsg("回款成功");

		return pi;
	}

}
