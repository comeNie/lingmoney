package com.mrbt.lingmoney.service.bank.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.dom4j.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mrbt.lingmoney.bank.HxBaseData;
import com.mrbt.lingmoney.bank.enterprise.HxEnterpriseAccount;
import com.mrbt.lingmoney.mapper.EnterpriseAccountMapper;
import com.mrbt.lingmoney.mapper.EnterpriseTransactorMapper;
import com.mrbt.lingmoney.model.EnterpriseAccount;
import com.mrbt.lingmoney.model.EnterpriseAccountExample;
import com.mrbt.lingmoney.model.EnterpriseTransactor;
import com.mrbt.lingmoney.service.bank.HxEnterpriseAccountService;

/**
 *
 *@author syb
 *@date 2017年9月29日 下午2:56:30
 *@version 1.0
 **/
@Service
public class HxEnterpriseAccountServiceImpl implements HxEnterpriseAccountService {
    private static final Logger LOGGER = LogManager.getLogger(HxEnterpriseAccountServiceImpl.class);

	@Autowired
	private HxEnterpriseAccount hxEnterpriseAccount;
	@Autowired
	private EnterpriseAccountMapper enterpriseAccountMapper;
	@Autowired
	private EnterpriseTransactorMapper enterpriseTransactorMapper;

	@Override
	public String hxEnterpriseAccountOpenCallBack(Document document) {
		String logGroup = "\nhxEnterpriseAccountOpenCallBack_" + System.currentTimeMillis() + "_";

		if (document != null) {
            LOGGER.info("{}解密后银行异步通知：\n{}", logGroup, document.asXML());

			Map<String, Object> map = HxBaseData.xmlToMap(document);

			// 银行通知流水号
			String channelFlow = (String) map.get("channelFlow");
			Map<String, Object> asyncMsg = new HashMap<String, Object>();
			asyncMsg.put("channelFlow", channelFlow);

			// 原开户请求流水号
			String oldreqseqno = (String) map.get("OLDREQSEQNO");
			EnterpriseAccountExample example = new EnterpriseAccountExample();
			example.createCriteria().andChannelFlowEqualTo(oldreqseqno);

			List<EnterpriseAccount> list = enterpriseAccountMapper.selectByExample(example);
			if (list != null && list.size() > 0) {
				EnterpriseAccount enterpriseAccount = list.get(0);

				EnterpriseAccount updateRecord = new EnterpriseAccount();
				updateRecord.setId(enterpriseAccount.getId());
				/**
				 * PENDING：待审核 APPROVED：审核通过 REJECT：拒绝
				 */
				String status = (String) map.get("STATUS");
				if ("APPROVED".equals(status)) {
                    LOGGER.info("{}_开户成功。", logGroup);

					// 更新企业账户信息
					updateRecord.setStatus(1);
					updateRecord.setResponseTime(new Date());
					updateRecord.setBankNo((String) map.get("COMACNO"));
					updateRecord.setPartyName((String) map.get("PARTYNAME"));
					updateRecord.setPartyMobile((String) map.get("PHONENO"));
					enterpriseAccountMapper.updateByPrimaryKeySelective(updateRecord);

					// 保存一条经办人记录
					EnterpriseTransactor enterpriseTransactor = new EnterpriseTransactor();
					String uuid = UUID.randomUUID().toString().replace("-", "");
					enterpriseTransactor.setId(uuid);
					enterpriseTransactor.setEacId(enterpriseAccount.getId());
					enterpriseTransactor.setName(updateRecord.getPartyName());
					enterpriseTransactor.setMobile(updateRecord.getPartyMobile());
					enterpriseTransactor.setRequestTime(enterpriseAccount.getRequestTime());
					enterpriseTransactor.setResponseTime(new Date());
					enterpriseTransactor.setStatus(1);
					enterpriseTransactorMapper.insertSelective(enterpriseTransactor);

				} else if ("REJECT".equals(status)) {
                    LOGGER.info("{}_开户失败。", logGroup);

					updateRecord.setStatus(2);
					updateRecord.setErrorMsg((String) map.get("ERRORMSG"));
					updateRecord.setResponseTime(new Date());
					enterpriseAccountMapper.updateByPrimaryKeySelective(updateRecord);
				}

				return hxEnterpriseAccount.generatinAsynsReply(asyncMsg, logGroup, true);

			} else {
				return hxEnterpriseAccount.generatinAsynsReply(asyncMsg, logGroup, false);
			}
		}

		return null;
	}

}
