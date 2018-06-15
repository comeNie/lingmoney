package com.mrbt.lingmoney.service.bank.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.dom4j.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mrbt.lingmoney.bank.HxBaseData;
import com.mrbt.lingmoney.bank.repayment.HxAutoRepaymentAuthorization;
import com.mrbt.lingmoney.mapper.HxAutoRepaymentApplyInfoMapper;
import com.mrbt.lingmoney.model.HxAutoRepaymentApplyInfo;
import com.mrbt.lingmoney.model.HxAutoRepaymentApplyInfoExample;
import com.mrbt.lingmoney.service.bank.HxAutoRepaymentService;

/**
 * @author syb
 * @date 2017年6月6日 下午5:14:26
 * @version 1.0
 * @description
 **/
@Service
public class HxAutoRepaymentServiceImpl implements HxAutoRepaymentService {
    private static final Logger LOGGER = LogManager.getLogger(HxAutoRepaymentServiceImpl.class);

	@Autowired
	private HxAutoRepaymentAuthorization hxAutoRepaymentAuthorization;
	@Autowired
	private HxAutoRepaymentApplyInfoMapper hxAutoRepaymentApplyInfoMapper;

	@Override
	public String autoRepaymentAuthCallBack(Document document) {
		String logGroup = "\n自动还款授权回调_" + System.currentTimeMillis();
		// 有数据则表示成功
		if (document != null) {
			LOGGER.info("{}解密后银行异步通知：\n{}", logGroup, document.asXML());

			Map<String, Object> map = HxBaseData.xmlToMap(document);

			// 银行交易流水号
			String bankFlow = (String) map.get("RESJNLNO");
			// 原交易流水号
			String transFlow = (String) map.get("OLDREQSEQNO");

			HxAutoRepaymentApplyInfoExample autoRepaymentExample = new HxAutoRepaymentApplyInfoExample();
			autoRepaymentExample.createCriteria().andChannelFlowEqualTo(transFlow);
			autoRepaymentExample.setOrderByClause("apply_time desc");
			List<HxAutoRepaymentApplyInfo> autoRepaymentList = hxAutoRepaymentApplyInfoMapper
					.selectByExample(autoRepaymentExample);

			if (autoRepaymentList != null && autoRepaymentList.size() > 0) {
				HxAutoRepaymentApplyInfo applyInfo = autoRepaymentList.get(0);

				// 更新自动还款申请信息表状态为成功
				HxAutoRepaymentApplyInfo record = new HxAutoRepaymentApplyInfo();
				record.setId(applyInfo.getId());
				record.setState(1);
				record.setBankFlow(bankFlow);
				record.setResponseTime(new Date());
				int result = hxAutoRepaymentApplyInfoMapper.updateByPrimaryKeySelective(record);

				if (result > 0) {
					LOGGER.info("{}更新自动还款记录成功。\n{}", logGroup, record.toString());
				} else {
					LOGGER.error("{}更新自动还款记录失败。\n{}", logGroup, record.toString());
				}

			}

			// 生成应答返回报文
			Map<String, Object> resMap = HxBaseData.xmlToMap(document);
			String resMsg = hxAutoRepaymentAuthorization.responseAutoRepaymentAuthorization(resMap, logGroup);
			if (resMsg != null) {
				return resMsg;
			}

		}

		return null;
	}

}
