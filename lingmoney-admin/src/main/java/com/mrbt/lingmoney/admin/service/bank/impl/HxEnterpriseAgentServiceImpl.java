package com.mrbt.lingmoney.admin.service.bank.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.mrbt.lingmoney.admin.service.bank.HxEnterpriseAgentService;
import com.mrbt.lingmoney.bank.enterprise.HxEnterpriseAgent;
import com.mrbt.lingmoney.mapper.EnterpriseAccountMapper;
import com.mrbt.lingmoney.mapper.EnterpriseTransactorMapper;
import com.mrbt.lingmoney.model.EnterpriseAccount;
import com.mrbt.lingmoney.model.EnterpriseAccountExample;
import com.mrbt.lingmoney.model.EnterpriseTransactor;
import com.mrbt.lingmoney.model.EnterpriseTransactorExample;
import com.mrbt.lingmoney.utils.PageInfo;
import com.mrbt.lingmoney.utils.PropertiesUtil;
import com.mrbt.lingmoney.utils.ResultParame.ResultInfo;
import com.mrbt.lingmoney.utils.ResultParame.ResultNumber;

/**
 *
 *@author syb
 *@date 2017年9月29日 上午11:18:54
 *@version 1.0
 **/
@Service
public class HxEnterpriseAgentServiceImpl implements HxEnterpriseAgentService {
	private static final String BANK_URL = PropertiesUtil.getPropertiesByKey("BANK_POST_URL");

	@Autowired
	private HxEnterpriseAgent hxEnterpriseAgent;
	@Autowired
	private EnterpriseTransactorMapper enterpriseTransactorMapper;
	@Autowired
	private EnterpriseAccountMapper enterpriseAccountMapper;

	@Override
	public Map<String, String> requestUpdateEnterpriseAgentInfo(String appId, Integer clientType, String bankNo,
			String logGroup) {
		if (StringUtils.isEmpty(bankNo)) {
			return null;
		}

		Map<String, String> resultMap = hxEnterpriseAgent.requestUpdateEnterpriseAgentInfo(clientType, appId, bankNo, "", logGroup);
		resultMap.put("bankUrl", BANK_URL);

		EnterpriseAccountExample accountExmp = new EnterpriseAccountExample();
		accountExmp.createCriteria().andBankNoEqualTo(bankNo);
		List<EnterpriseAccount> accountList = enterpriseAccountMapper.selectByExample(accountExmp);

		if (accountList == null || accountList.size() < 1) {
			return null;
		}

		EnterpriseAccount enterpriseAccount = accountList.get(0);

		EnterpriseTransactor enterpriseTransactor = new EnterpriseTransactor();
		String uuid = UUID.randomUUID().toString().replaceAll("-", "");
		enterpriseTransactor.setId(uuid);
		enterpriseTransactor.setChannelFlow(resultMap.get("channelFlow"));
		enterpriseTransactor.setEacId(enterpriseAccount.getId());
		enterpriseTransactor.setRequestTime(new Date());
		enterpriseTransactor.setStatus(0);
		enterpriseTransactorMapper.insertSelective(enterpriseTransactor);

		return resultMap;
	}

	@Override
	public PageInfo queryUpdateEnterpriseAgentInfoResult(String id, String logGroup) {
		PageInfo pi = new PageInfo();

		EnterpriseTransactor enterpriseTransactor = enterpriseTransactorMapper.selectByPrimaryKey(id);

		if (enterpriseTransactor == null) {
			pi.setCode(ResultInfo.EMPTY_DATA.getCode());
			pi.setMsg("未查询到有效记录");
			return pi;
		}

		int recordStatus = enterpriseTransactor.getStatus();

		if (recordStatus == 0) {
			Map<String, Object> resultMap = hxEnterpriseAgent.queryEnterpriseOpenAccount(
					enterpriseTransactor.getChannelFlow(), logGroup);

			String errorCode = (String) resultMap.get("errorCode");
			if ("0".equals(errorCode)) {
				pi.setCode(ResultInfo.SUCCESS.getCode());

				// 数据更新
				EnterpriseTransactor record = new EnterpriseTransactor();
				record.setId(id);
				record.setResponseTime(new Date());
				/**
				 * PENDING：待审核 APPROVED：审核通过 REJECT：拒绝
				 */
				String status = (String) resultMap.get("STATUS");

				if ("S".equals(status)) {
					pi.setMsg(ResultInfo.SUCCESS.getMsg());
					record.setStatus(1);
					record.setName((String) resultMap.get("PARTYNAME"));
					record.setMobile((String) resultMap.get("PARTYPHONE"));
					enterpriseTransactorMapper.updateByPrimaryKeySelective(record);

					// 成功后更新企业账户中经办人信息
					EnterpriseAccount enterpriseAccount = enterpriseAccountMapper.selectByPrimaryKey(enterpriseTransactor.getEacId());
					if (enterpriseAccount != null) {
						enterpriseAccount.setPartyName(record.getName());
						enterpriseAccount.setPartyMobile(record.getMobile());
						enterpriseAccountMapper.updateByPrimaryKeySelective(enterpriseAccount);
					}

				} else if ("F".equals(status)) {
					String errorMsg = (String) resultMap.get("ERRORMSG");
					pi.setMsg("操作失败，原因：" + errorMsg);
					record.setStatus(ResultNumber.TWO.getNumber());
					record.setErrorMsg(errorMsg);
					enterpriseTransactorMapper.updateByPrimaryKeySelective(record);

				} else {
					pi.setMsg("未知状态，请与管理员联系");
				}

			} else {
				String errorMsg = (String) resultMap.get("errorMsg");
				pi.setCode(ResultInfo.MODIFY_REJECT.getCode());
				pi.setMsg(errorMsg);
			}

		} else if (recordStatus == 1) {
			pi.setMsg("审核通过");

		} else if (recordStatus == ResultNumber.TWO.getNumber()) {
			pi.setMsg("审核失败");
		}

		return pi;
	}

	@Override
	public PageInfo listTransactor(Integer page, Integer rows, String eacId) {
		PageInfo pi = new PageInfo(page, rows);

		EnterpriseTransactorExample example = new EnterpriseTransactorExample();
		if (!StringUtils.isEmpty(eacId)) {
			example.createCriteria().andEacIdEqualTo(eacId);
		}
		example.setLimitStart(pi.getFrom());
		example.setLimitEnd(pi.getSize());
		example.setOrderByClause("request_time desc");

		List<EnterpriseTransactor> list = enterpriseTransactorMapper.selectByExample(example);
		pi.setRows(list);

		if (list != null && list.size() > 0) {
			pi.setTotal(enterpriseTransactorMapper.countByExample(example));

		} else {
			pi.setTotal(0);
		}

		return pi;
	}

}
