package com.mrbt.lingmoney.admin.service.base.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.Element;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.mrbt.lingmoney.admin.mongo.BidderInfoMongo;
import com.mrbt.lingmoney.admin.service.base.CommonMethodService;
import com.mrbt.lingmoney.mapper.CustomQueryMapper;
import com.mrbt.lingmoney.mapper.ProductMapper;
import com.mrbt.lingmoney.mapper.ProductParamMapper;

/**
 *@author syb
 *@date 2017年7月5日 下午5:50:25
 *@version 1.0
 *@description 
 **/
@Service
public class CommonMethodServiceImpl implements CommonMethodService {
	private static final Logger LOGGER = LogManager.getLogger(CommonMethodServiceImpl.class);

	@Autowired
	private ProductParamMapper productParamMapper;
	@Autowired
	private MongoTemplate mongoTemplate;
	@Autowired
	private CustomQueryMapper customQueryMapper;
    @Autowired
    private ProductMapper productMapper;

	@Override
	public HashMap<String, String> findHoliday() {
		List<String> paramList = productParamMapper.findHolidayList();
		HashMap<String, String> holidMap = new HashMap<String, String>();
		for (String dt : paramList) {
			holidMap.put(dt, dt);
		}
		return holidMap;
	}

	@Override
	public void saveBanklendingInfoToMongo(Document resDoc, Integer type) {
		List<BidderInfoMongo> mongoList = new ArrayList<BidderInfoMongo>();
		@SuppressWarnings("unchecked")
		List<Element> rsList = resDoc.selectNodes("//RSLIST");
		if (rsList != null && rsList.size() > 0) {
			for (Element rsElem : rsList) {
				BidderInfoMongo info = new BidderInfoMongo();
				info.setType(type);
				info.setSave_date(new Date());
				String reqSeqNo = rsElem.selectSingleNode("//REQSEQNO").getText(); // 原投标流水号
				info.setReq_seq_no(reqSeqNo);
				String rsLoanNo = rsElem.selectSingleNode("//LOANNO").getText(); // 借款编号
				info.setLoan_no(rsLoanNo);
				String acNo = rsElem.selectSingleNode("//ACNO").getText(); // 投资人账号
				info.setAc_no(acNo);
				String acName = rsElem.selectSingleNode("//ACNAME").getText(); // 投资人账号户名
				info.setAc_name(acName);
				String amount = rsElem.selectSingleNode("//AMOUNT").getText(); // 投标金额
				info.setAmount(new BigDecimal(amount).doubleValue());
				String status = rsElem.selectSingleNode("//STATUS").getText(); // 状态
																				// L待处理R
																				// 正在处理N未明F失败S成功
				info.setStatus(status);
				if (status.equals("F")) {
					String errMsg = rsElem.selectSingleNode("//ERRMSG").getText(); // STATUS=F返回
																					// 错误原因
					info.setErr_msg(errMsg);
				}
				String extFiled3 = rsElem.selectSingleNode("//EXT_FILED3").getText(); // 备用字段3
				if (!StringUtils.isEmpty(extFiled3)) {
					info.setExt_filed3(extFiled3);
				}
				mongoList.add(info);
			}

			mongoTemplate.insertAll(mongoList);
		}
	}

	@Override
	public BigDecimal countTotalRepaymentMoney(Integer pid, String logGroup) {
		// 正常还款总金额
		BigDecimal allMoney = customQueryMapper.sumAllRepaymentMoney(pid);
		LOGGER.info("{}查询到的还款总额：{}", logGroup, allMoney.toString());

		// 查询加息总额
		BigDecimal extraMoney = customQueryMapper.sumRedPacketMoneyByPid(pid);
		LOGGER.info("{}计算的加息总额：{}", logGroup, extraMoney.toString());

		// 还款金额 = 还款总额 + 加息券金额
        BigDecimal money = allMoney.add(extraMoney);

		LOGGER.info("{}还款总金额：{}", logGroup, money.toString());

		return money;
	}

}
