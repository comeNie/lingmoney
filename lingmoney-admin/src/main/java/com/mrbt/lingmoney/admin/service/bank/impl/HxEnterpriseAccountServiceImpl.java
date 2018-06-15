package com.mrbt.lingmoney.admin.service.bank.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.dom4j.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.mrbt.lingmoney.admin.service.bank.HxEnterpriseAccountService;
import com.mrbt.lingmoney.bank.HxBaseData;
import com.mrbt.lingmoney.bank.eaccount.HxAccountBanlance;
import com.mrbt.lingmoney.bank.enterprise.HxEnterpriseAccount;
import com.mrbt.lingmoney.bank.withdraw.HxCashWithdraw;
import com.mrbt.lingmoney.mapper.EnterpriseAccountMapper;
import com.mrbt.lingmoney.mapper.EnterpriseTransactorMapper;
import com.mrbt.lingmoney.model.EnterpriseAccount;
import com.mrbt.lingmoney.model.EnterpriseAccountExample;
import com.mrbt.lingmoney.model.EnterpriseTransactor;
import com.mrbt.lingmoney.utils.PageInfo;
import com.mrbt.lingmoney.utils.PropertiesUtil;
import com.mrbt.lingmoney.utils.ResultParame;

/**
 *
 *@author syb
 *@date 2017年9月29日 上午9:11:30
 *@version 1.0
 **/
@Service
public class HxEnterpriseAccountServiceImpl implements HxEnterpriseAccountService {
	private static final String BANK_URL = PropertiesUtil.getPropertiesByKey("BANK_POST_URL");

	@Autowired
	private HxEnterpriseAccount hxEnterpriseAccount;
	
	@Autowired
	private EnterpriseAccountMapper enterpriseAccountMapper;
	
	@Autowired
	private EnterpriseTransactorMapper enterpriseTransactorMapper;
	
	@Autowired
	private HxAccountBanlance hxAccountBanlance;
	
	@Autowired
	private HxCashWithdraw hxCashWithdraw;
	
	private static final String CASH_WITHDRAW_RETURN_URL = PropertiesUtil.getPropertiesByKey("CASH_WITHDRAW_RETURN_URL");

	@Override
	public Map<String, String> accountOpen(String appId, Integer clientType, String companyName, String occCodeNo,
			String logGroup) {
		Map<String, String> resultMap = hxEnterpriseAccount.requestEnterpriseOpenAccount(clientType, appId,
				companyName, occCodeNo, "", logGroup);
		resultMap.put("bankUrl", BANK_URL);
		
		// 如果该企业账号已存在，执行更新操作
		EnterpriseAccountExample example = new EnterpriseAccountExample();
		example.createCriteria().andIdNumberEqualTo(occCodeNo);
		List<EnterpriseAccount> list = enterpriseAccountMapper.selectByExample(example);
		if (list != null && list.size() > 0) {
			EnterpriseAccount enterpriseAccount = list.get(0);
			if (enterpriseAccount.getStatus() == 1) {
				// 如果该企业已经开户成功，不在请求
				return null;
			}

			EnterpriseAccount record = new EnterpriseAccount();
			record.setId(enterpriseAccount.getId());
			record.setEnterpriseName(companyName);
			record.setChannelFlow(resultMap.get("channelFlow").toString());
			record.setStatus(0);
			record.setRequestTime(new Date());
			record.setAppId(appId);
			enterpriseAccountMapper.updateByPrimaryKeySelective(record);

		} else {
			// 不存在初始化请求记录
			EnterpriseAccount enterpriseAccount = new EnterpriseAccount();
			String uuid = UUID.randomUUID().toString().replaceAll("-", "");
			enterpriseAccount.setId(uuid);
			enterpriseAccount.setEnterpriseName(companyName);
			enterpriseAccount.setIdNumber(occCodeNo);
			enterpriseAccount.setChannelFlow(resultMap.get("channelFlow").toString());
			enterpriseAccount.setStatus(0);
			enterpriseAccount.setRequestTime(new Date());
			enterpriseAccount.setAppId(appId);
			enterpriseAccountMapper.insertSelective(enterpriseAccount);
		}

		return resultMap;
	}

	@Override
	public PageInfo queryAccountOpenResult(String id, String logGroup) {
		PageInfo pi = new PageInfo();

		EnterpriseAccount enterpriseAccount = enterpriseAccountMapper.selectByPrimaryKey(id);

		if (enterpriseAccount == null) {
			pi.setCode(ResultParame.ResultInfo.SUBMIT_PARAMS_ERROR.getCode());
			pi.setMsg("未查询到指定数据");
			return pi;
		}

		int recordStatus = enterpriseAccount.getStatus();

		if (recordStatus == 0) {
			Map<String, Object> resultMap = hxEnterpriseAccount.queryEnterpriseOpenAccount(
					enterpriseAccount.getChannelFlow(), logGroup);

			String errorCode = (String) resultMap.get("errorCode");
			if ("0".equals(errorCode)) {
				pi.setCode(ResultParame.ResultInfo.SUCCESS.getCode());

				EnterpriseAccount record = new EnterpriseAccount();
				record.setId(enterpriseAccount.getId());

				String status = (String) resultMap.get("STATUS");
				if ("PENDING".equals(status)) {
					pi.setMsg("审核中，请稍后再试");

				} else if ("APPROVED".equals(status)) {
					pi.setMsg("恭喜你，审核通过");
					record.setStatus(1);
					record.setResponseTime(new Date());
					record.setBankNo((String) resultMap.get("COMACNO"));
					record.setPartyName((String) resultMap.get("PARTYNAME"));
					record.setPartyMobile((String) resultMap.get("PHONENO"));
					record.setErrorMsg(""); // 成功后更新失败信息为空

					enterpriseAccountMapper.updateByPrimaryKeySelective(record);

					// 保存一条经办人记录
					EnterpriseTransactor enterpriseTransactor = new EnterpriseTransactor();
					String uuid = UUID.randomUUID().toString().replace("-", "");
					enterpriseTransactor.setId(uuid);
					enterpriseTransactor.setEacId(enterpriseAccount.getId());
					enterpriseTransactor.setName(record.getPartyName());
					enterpriseTransactor.setMobile(record.getPartyMobile());
					enterpriseTransactor.setRequestTime(enterpriseAccount.getRequestTime());
					enterpriseTransactor.setResponseTime(new Date());
					enterpriseTransactor.setStatus(1);
					enterpriseTransactorMapper.insertSelective(enterpriseTransactor);

				} else if ("REJECT".equals(status)) {
					String errorMsg = (String) resultMap.get("ERRORMSG");
					pi.setMsg("很遗憾，审核被拒绝。原因：" + errorMsg);
					record.setStatus(ResultParame.ResultNumber.TWO.getNumber());
					record.setErrorMsg(errorMsg);
					record.setResponseTime(new Date());
					enterpriseAccountMapper.updateByPrimaryKeySelective(record);

				} else {
					pi.setMsg("未知状态，请稍后再试");
				}

			} else {
				pi.setCode(ResultParame.ResultInfo.MODIFY_REJECT.getCode());
				String errorMsg = (String) resultMap.get("errorMsg");
				pi.setMsg(errorMsg);
			}

		} else if (recordStatus == 1) {
			pi.setMsg("审核通过");

		} else if (recordStatus == ResultParame.ResultNumber.TWO.getNumber()) {
			pi.setMsg("审核失败");
		}

		return pi;
	}

	@Override
	public PageInfo listEnterpriseAccount(String companyName, String bankNo, Integer status, Integer page, Integer rows) {
		PageInfo pi = new PageInfo(page, rows);

		EnterpriseAccountExample enterpriseExmp = new EnterpriseAccountExample();
		EnterpriseAccountExample.Criteria criteria = enterpriseExmp.createCriteria();
		if (!StringUtils.isEmpty(companyName)) {
			criteria.andEnterpriseNameLike("%" + companyName + "%");
		}

		if (!StringUtils.isEmpty(bankNo)) {
			criteria.andBankNoLike("%" + bankNo + "%");
		}

		if (!StringUtils.isEmpty(status)) {
			criteria.andStatusEqualTo(status);
		}

		enterpriseExmp.setLimitStart(pi.getFrom());
		enterpriseExmp.setLimitEnd(pi.getSize());
		enterpriseExmp.setOrderByClause("request_time desc");

		List<EnterpriseAccount> list = enterpriseAccountMapper.selectByExample(enterpriseExmp);
		pi.setRows(list);

		if (list != null && list.size() > 0) {
			pi.setTotal(enterpriseAccountMapper.countByExample(enterpriseExmp));
		} else {
			pi.setTotal(0);
		}

		return pi;
	}

	@Override
	public PageInfo queryCompanyBalance(String companyNameId) {
		PageInfo pageInfo = new PageInfo();
		try {
			//查询企业账户信息
			EnterpriseAccount eaccount = enterpriseAccountMapper.selectByPrimaryKey(companyNameId);
			if (eaccount != null) {
				// 账户余额
				Document document = hxAccountBanlance.requestAccountBalance("PC", eaccount.getEnterpriseName(), eaccount.getBankNo());
				Map<String, Object> resMap = HxBaseData.xmlToMap(document);
				
				pageInfo.setCode(ResultParame.ResultInfo.SUCCESS.getCode());
				pageInfo.setMsg("查询成功");
				pageInfo.setObj(resMap);
			} else {
				pageInfo.setCode(ResultParame.ResultInfo.EMPTY_DATA.getCode());
				pageInfo.setMsg("查询企业账户不存在");
			}
		} catch (Exception e) {
			e.printStackTrace();
			pageInfo.setCode(ResultParame.ResultInfo.SERVER_ERROR.getCode());
			pageInfo.setMsg("服务器错误");
		}
		return pageInfo;
	}

	@Override
	public Map<String, String> withdrawals(String companyNameId, String amount) {
		
		EnterpriseAccount eaccount = enterpriseAccountMapper.selectByPrimaryKey(companyNameId);
		// 银行户名
		String acName = eaccount.getEnterpriseName();
		// 银行账号
		String acNo = eaccount.getBankNo();
		// 备注
		String remark = "用户" + eaccount.getEnterpriseName() + "，单笔提现，金额为" + amount;
		// 生成报文
		try {
			Map<String, String> resultMap = hxCashWithdraw.requestCashWithdraw(0, "PC", acNo, acName, amount, remark, companyNameId, CASH_WITHDRAW_RETURN_URL, "withdrawals");
			resultMap.put("bankUrl", BANK_URL);
			return resultMap;
		} catch (Exception e) {
			e.printStackTrace();
			return null; 
		}
	}

}
