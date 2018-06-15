package com.mrbt.lingmoney.service.bank.impl;

import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.dom4j.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mrbt.lingmoney.bank.HxBaseData;
import com.mrbt.lingmoney.bank.recall.HxUnsubscribe;
import com.mrbt.lingmoney.mapper.HxAccountMapper;
import com.mrbt.lingmoney.mapper.UsersPropertiesMapper;
import com.mrbt.lingmoney.model.HxAccount;
import com.mrbt.lingmoney.model.HxAccountExample;
import com.mrbt.lingmoney.model.UsersProperties;
import com.mrbt.lingmoney.model.UsersPropertiesExample;
import com.mrbt.lingmoney.service.bank.HxUnsubscribeService;

/**
 *@author syb
 *@date 2017年8月14日 下午4:25:23
 *@version 1.0
 *@description 
 **/
@Service
public class HxUnsubscribeServiceImpl implements HxUnsubscribeService {
	private static final Logger LOGGER = LogManager.getLogger(HxUnsubscribeServiceImpl.class);

	@Autowired
	private HxUnsubscribe hxUnsubscribe;
	@Autowired
	private HxAccountMapper hxAccountMapper;
	@Autowired
	private UsersPropertiesMapper usersPropertiesMapper;

	@Override
	public String handelHxUnsubscribe(Document document) {
		String logGroup = "\n销户通知_" + System.currentTimeMillis();

        String errorCode = "0"; // 错误码 默认0：成功
        String errorMsg = "success"; // 错误信息，默认success
        String oldReqseqNo = ""; // 本交易的请求报文的报文头流水
        String channelFlow = ""; // 渠道流水号
        String status = "0"; // 业务状态 0：受理成功 1：受理失败 2：受理中 3：受理超时，不确定

		if (document != null) {
			Map<String, Object> map = HxBaseData.xmlToMap(document);

			channelFlow = (String) map.get("channelFlow");
			oldReqseqNo = channelFlow;

			String acNo = (String) map.get("ACNO");
			String reason = (String) map.get("REASON");
			String transdt = (String) map.get("TRANSDT");

			// 销户后更新华兴账户状态，修改账户属性信息
			HxAccountExample hxAccountExmp = new HxAccountExample();
			hxAccountExmp.createCriteria().andAcNoEqualTo(acNo);
			List<HxAccount> hxAccountList = hxAccountMapper.selectByExample(hxAccountExmp);

			if (hxAccountList != null && hxAccountList.size() > 0) {
				HxAccount hxAccount = hxAccountList.get(0);

				HxAccount hxAccountRecord = new HxAccount();
				hxAccountRecord.setId(hxAccount.getId());
				hxAccountRecord.setStatus(4);

				int result = hxAccountMapper.updateByPrimaryKeySelective(hxAccountRecord);

				if (result > 0) {
					LOGGER.info("{}收到销户通知，更新用户账户状态成功。销户原因：{}", logGroup, reason);

					UsersPropertiesExample usersPropertiesExmp = new UsersPropertiesExample();
					usersPropertiesExmp.createCriteria().andUIdEqualTo(hxAccount.getuId());

					UsersProperties usersPropertiesRecord = new UsersProperties();
					usersPropertiesRecord.setCertification(0);
					usersPropertiesRecord.setBank(0);

					result = usersPropertiesMapper.updateByExampleSelective(usersPropertiesRecord, usersPropertiesExmp);

					if (result > 0) {
						LOGGER.info("{}收到销户通知，更新用户{}属性信息成功。", logGroup, hxAccount.getuId());
					} else {
						LOGGER.error("{}收到销户通知，更新用户{}属性信息失败。", logGroup, hxAccount.getuId());
					}

				} else {
					LOGGER.error("{}收到销户通知，更新用户账户状态失败。销户原因：{}", logGroup, reason);
				}

			}

			System.out.println("销户信息：\n 账号：" + acNo + "；销户原因:" + reason + "；销户日期:" + transdt + "；流水号:" + channelFlow);

		} else {
			errorCode = "100002";
			errorMsg = "解析数据失败";
			status = "1";
		}

		LOGGER.info("{}销户通知响应参数：channelFlow:{}, oldReqseqNo:{}, errorCode:{}, errorMsg:{}", logGroup, channelFlow,
				oldReqseqNo, errorCode, errorMsg);

		return hxUnsubscribe.responseUnsubscribe(channelFlow, oldReqseqNo, errorCode, errorMsg, status, logGroup);
	}

}
