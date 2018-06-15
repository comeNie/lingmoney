package com.mrbt.lingmoney.service.bank.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.dom4j.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mrbt.lingmoney.bank.HxBaseData;
import com.mrbt.lingmoney.bank.enterprise.HxEnterpriseAgent;
import com.mrbt.lingmoney.mapper.EnterpriseAccountMapper;
import com.mrbt.lingmoney.mapper.EnterpriseTransactorMapper;
import com.mrbt.lingmoney.model.EnterpriseAccount;
import com.mrbt.lingmoney.model.EnterpriseTransactor;
import com.mrbt.lingmoney.model.EnterpriseTransactorExample;
import com.mrbt.lingmoney.service.bank.HxEnterpriseAgentService;

/**
 *
 *@author syb
 *@date 2017年9月29日 下午3:35:02
 *@version 1.0
 **/
@Service
public class HxEnterpriseAgentServiceImpl implements HxEnterpriseAgentService {
	private static final Logger LOGGER = LogManager.getLogger(HxEnterpriseAgentServiceImpl.class);

	@Autowired
	private HxEnterpriseAgent hxEnterpriseAgent;
	@Autowired
	private EnterpriseAccountMapper enterpriseAccountMapper;
	@Autowired
	private EnterpriseTransactorMapper enterpriseTransactorMapper;

	@Override
	public String updateEnterpriseAgentInfoCallBack(Document document) {
		String logGroup = "\nupdateEnterpriseAgentInfoCallBack_" + System.currentTimeMillis() + "_";

		if (document != null) {
			LOGGER.info("{}解密后银行异步通知：\n{}", logGroup, document.asXML());

			Map<String, Object> map = HxBaseData.xmlToMap(document);

			Map<String, Object> asyncMsg = new HashMap<String, Object>();
			asyncMsg.put("channelFlow", map.get("channelFlow"));

			// 原请求变更流水号
			String oldreqseqno = (String) map.get("OLDREQSEQNO");
			EnterpriseTransactorExample example = new EnterpriseTransactorExample();
			example.createCriteria().andChannelFlowEqualTo(oldreqseqno);

			List<EnterpriseTransactor> list = enterpriseTransactorMapper.selectByExample(example);
			if (list != null && list.size() > 0) {
				// 更新企业经办人信息
				EnterpriseTransactor record = new EnterpriseTransactor();
				record.setId(list.get(0).getId());
				record.setStatus(1);
				record.setName((String) map.get("PARTYNAME"));
				record.setMobile((String) map.get("PARTYPHONE"));
				enterpriseTransactorMapper.updateByPrimaryKeySelective(record);

				// 成功后更新企业账户中经办人信息
				EnterpriseAccount enterpriseAccount = enterpriseAccountMapper
						.selectByPrimaryKey(list.get(0).getEacId());
				if (enterpriseAccount != null) {
					enterpriseAccount.setPartyName(record.getName());
					enterpriseAccount.setPartyMobile(record.getMobile());
					enterpriseAccountMapper.updateByPrimaryKeySelective(enterpriseAccount);
				}

				return hxEnterpriseAgent.generatinAsynsReply(asyncMsg, logGroup, true);

			} else {
				return hxEnterpriseAgent.generatinAsynsReply(asyncMsg, logGroup, false);
			}
		}
		return null;
	}

}
