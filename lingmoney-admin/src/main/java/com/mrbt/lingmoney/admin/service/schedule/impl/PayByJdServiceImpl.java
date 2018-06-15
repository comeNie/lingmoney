package com.mrbt.lingmoney.admin.service.schedule.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mrbt.lingmoney.admin.service.schedule.PayByJdService;
import com.mrbt.lingmoney.admin.vo.trading.PayByJdResult;
import com.mrbt.lingmoney.mapper.PaymentCardLimitMapper;
import com.mrbt.lingmoney.mapper.PaymentPartitionMapper;
import com.mrbt.lingmoney.model.PaymentCardLimit;
import com.mrbt.lingmoney.model.PaymentCardLimitExample;
import com.mrbt.lingmoney.model.PaymentPartition;
import com.mrbt.lingmoney.model.PaymentPartitionExample;
import com.mrbt.lingmoney.utils.ResultParame;
import com.mrbt.pay.jd.reback.CodeConst;
import com.mrbt.pay.jd.reback.RebackResultVo;
import com.mrbt.pay.jd.reback.ReturnByJd;

/**
 * 京东支付服务类
 * 
 * @author airgilbert
 *
 */

@Service
public class PayByJdServiceImpl implements PayByJdService {
	private static final Logger LOGGER = LogManager.getLogger(PayByJdServiceImpl.class);
	
	@Autowired
	private ReturnByJd returnByJd;
	@Autowired
	private PaymentCardLimitMapper paymentCardLimitMapper;
	@Autowired
	private PaymentPartitionMapper paymentPartitionMapper;

	private final int allPay = 0, successPay = 3, waitPay = 15, failPay = 16, notSubmit = 0, submit = 1;
	
	private final BigDecimal zero = new BigDecimal(0);

	@Override
	@Transactional
	public PayByJdResult defrayPay(String cardBank, String cardNo, String cardName, String outTradeNo,
			String cardPhone, BigDecimal tradeAmount, String bizType, String uid, int bizId) {

		//默认兑付限额
		BigDecimal limitAmount = new BigDecimal("50000");
		// 获取限额
		PaymentCardLimitExample paymentCardLimitExmp = new PaymentCardLimitExample();
		paymentCardLimitExmp.createCriteria().andCardShortEqualTo(cardBank);
		
		List<PaymentCardLimit> paymentCardLimitList = paymentCardLimitMapper.selectByExample(paymentCardLimitExmp);

		if (paymentCardLimitList != null && paymentCardLimitList.size() > 0) {
			limitAmount = paymentCardLimitList.get(0).getLimitAmount();
		}
		// 京东操作返回的内容
		RebackResultVo queryResult = null;
		
		// 查询京东账户余额
		while ((queryResult = returnByJd.accountQuery()) == null || !queryResult.isOk()) {
			try {
				sendSms("京东账户查询错误，请尽快查询");
				Thread.sleep(ResultParame.ResultNumber.THIRTY.getNumber()
						* ResultParame.ResultNumber.ONE_THOUSAND.getNumber());
			} catch (InterruptedException e) {
				LOGGER.error("京东账户查询错误，请联系管理员:" + e.getMessage());
			}
		}
		
		// 当余额不足的时候返回空值
		if (queryResult.getAvail_amount().compareTo(tradeAmount) < 1) {
			sendSms("余额不足，请联系财务尽快充值");
			return null;
		}

		PaymentPartitionExample paymentPartitionExmp = new PaymentPartitionExample();
		PaymentPartitionExample.Criteria cri = paymentPartitionExmp.createCriteria();
		if (StringUtils.isNotBlank(outTradeNo)) {
			cri.andOutBizCodeEqualTo(outTradeNo);
		}
		paymentPartitionExmp.setOrderByClause("partition_index");

		List<PaymentPartition> listPay = paymentPartitionMapper.selectByExample(paymentPartitionExmp);
		// 如果没有当前的数据，则插入订单数据
		if (listPay == null || listPay.size() == 0) {
			listPay = initPaymentPartition(cardBank, cardNo, cardName, outTradeNo, cardPhone, tradeAmount, limitAmount,
					bizType, uid, bizId);
		}
		// 当前服务需要返回的内容
		PayByJdResult vo = null;
		// 是否所有的兑付信息都放入到数据库中
		while ((vo = getReachMoney(listPay)).isAllPayInDatabase(tradeAmount) < 0) {
			
			listPay = addPaymentPartition(cardBank, cardNo, cardName, outTradeNo, cardPhone, tradeAmount, limitAmount,
					bizType, uid, bizId, listPay, vo);
			
		}
		
		// 如果存储的金额大于交易金额，则报错，请管理员尽快处理
		if (vo.isAllPayInDatabase(tradeAmount) > 0) {
			LOGGER.error("金额初始化错误，请联系管理员进行操作");

			sendSms("金额初始化错误，请联系管理员进行操作");
			return null;
		}
		// 做京东支付
		PaymentPartition record = new PaymentPartition();
		for (PaymentPartition tmp : listPay) {
			if (!vo.isSubmit()) {
				LOGGER.info("outTradeNo:" + outTradeNo + "未做确认操作，请联系管理员进行确认");

				return null;
			}
			
			if (tmp.getStatus() == allPay) {
				queryResult = returnByJd.defrayPay(cardBank, cardNo, cardName, tmp.getOutBizCodePartition(),
                        cardPhone, tmp.getUserAmount());
				record.setId(tmp.getId());

				if (queryResult.isOk()) {
					record.setStatus(successPay);
					record.setReachTime(new Date());
					paymentPartitionMapper.updateByPrimaryKeySelective(record);
					// 设置这个临时对象，返回的时候通过getReachMoney即可
					tmp.setStatus(successPay);

				} else if (queryResult.isWait() || queryResult.getResultCode() == null
						|| (queryResult.getResultCode() != null && queryResult.getResultCode().equals(
								CodeConst.OUT_TRADE_NO_EXIST))) {
					record.setStatus(waitPay);
					paymentPartitionMapper.updateByPrimaryKeySelective(record);
					// 设置这个临时对象，返回的时候通过getReachMoney即可
					tmp.setStatus(waitPay);

				} else {
					record.setStatus(failPay);
					record.setResponseMsg(queryResult.getResultInfo());
					paymentPartitionMapper.updateByPrimaryKeySelective(record);
					// 设置这个临时对象，返回的时候通过getReachMoney即可
					tmp.setStatus(failPay);
				}
			}
		}
		return getReachMoney(listPay);
	}
	
	/**
	 * 京东查询余额接口
	 * 
	 * @return 数据返回
	 */
	public RebackResultVo accountQuery() {
		return returnByJd.accountQuery();
	}
	
	@Override
	public PayByJdResult tradeQuery(String outTradeNo) {
		// 查询当前数据库中的数据
		PaymentPartitionExample paymentPartitionExmp = new PaymentPartitionExample();
		PaymentPartitionExample.Criteria cri = paymentPartitionExmp.createCriteria();
		if (StringUtils.isNotBlank(outTradeNo)) {
			cri.andOutBizCodeEqualTo(outTradeNo);
		}
		paymentPartitionExmp.setOrderByClause("partition_index");

		List<PaymentPartition> listPay = paymentPartitionMapper.selectByExample(paymentPartitionExmp);

		RebackResultVo queryResult = null;
		// 做京东支付
		PaymentPartition record = new PaymentPartition();
		if (listPay != null && listPay.size() > 0 && !getReachMoney(listPay).isSubmit()) {
			LOGGER.info("查询交易中发现有未确认订单,outTradeNo:" + outTradeNo + "未做确认操作，请联系管理员进行确认");

			return null;
		}
		for (PaymentPartition tmp : listPay) {
			if (tmp.getStatus() != null && tmp.getStatus() != failPay && tmp.getStatus() != successPay) {

				queryResult = returnByJd.tradeQuery(tmp.getOutBizCodePartition());

				record.setId(tmp.getId());

				if (queryResult.isOk()) {
					record.setStatus(successPay);
					paymentPartitionMapper.updateByPrimaryKeySelective(record);
					// 设置这个临时对象，返回的时候通过getReachMoney即可
					tmp.setStatus(successPay);

				} else if (queryResult.isWait()) {
					record.setStatus(waitPay);
					if (tmp.getStatus() != null && tmp.getStatus() != waitPay) {
						paymentPartitionMapper.updateByPrimaryKeySelective(record);
						// 设置这个临时对象，返回的时候通过getReachMoney即可
						tmp.setStatus(waitPay);
					}

				} else {
					record.setStatus(failPay);
					record.setResponseMsg(queryResult.getResultInfo());
					paymentPartitionMapper.updateByPrimaryKeySelective(record);
					tmp.setStatus(failPay);
				}
			}
		}

		return getReachMoney(listPay);
	}
	
	/**
	 * 初始化所有信息
	 * 
	 * @param cardBank
	 *            cardBank
	 * @param cardNo
	 *            cardNo
	 * @param cardName
	 *            cardName
	 * @param outTradeNo
	 *            outTradeNo
	 * @param cardPhone
	 *            cardPhone
	 * @param tradeAmount
	 *            tradeAmount
	 * @param limitAmount
	 *            limitAmount
	 * @param bizType
	 *            bizType
	 * @param uid
	 *            uid
	 * @param bizId
	 *            bizId
	 * @return 数据返回
	 */
	@Transactional
	public List<PaymentPartition> initPaymentPartition(String cardBank, String cardNo, String cardName,
			String outTradeNo, String cardPhone, BigDecimal tradeAmount, BigDecimal limitAmount, String bizType,
			String uid, int bizId) {
		int index = 1, end = 1;
		
		if (tradeAmount.compareTo(limitAmount) > 0) {
			end = tradeAmount.divide(limitAmount, 0, BigDecimal.ROUND_UP).intValue();
		}
		
		List<PaymentPartition> list = new ArrayList<PaymentPartition>();
		
		for (int i = index; i <= end && (tradeAmount.compareTo(zero) > 0); i++) {
			PaymentPartition vo = new PaymentPartition();
			vo.setOutBizCode(outTradeNo);
			vo.setBizType(bizType);
			vo.setBizId(bizId);
			vo.setuId(uid);
			vo.setStatus(allPay);
			vo.setUserTelphone(cardPhone);
			vo.setUserCardNo(cardNo);
			vo.setUserCardShort(cardBank);
			vo.setUserName(cardName);
			vo.setOperTime(new Date());
			vo.setSubmit(notSubmit);
			// 设置分批的位置号
			vo.setPartitionIndex(i);
			vo.setUserAmount(tradeAmount.compareTo(limitAmount) > 0 ? limitAmount : tradeAmount);
			// 设置分批订单号
			vo.setOutBizCodePartition(vo.getOutBizCode() + i);
			
			paymentPartitionMapper.insertSelective(vo);
			
			tradeAmount = tradeAmount.subtract(limitAmount);
			list.add(vo);
		}
		return list;
	}
	
	/**
	 * 补录打款信息
	 * 
	 * @param cardBank
	 *            cardBank
	 * @param cardNo
	 *            cardNo
	 * @param cardName
	 *            cardName
	 * @param outTradeNo
	 *            outTradeNo
	 * @param cardPhone
	 *            cardPhone
	 * @param tradeAmount
	 *            tradeAmount
	 * @param limitAmount
	 *            limitAmount
	 * @param bizType
	 *            bizType
	 * @param uid
	 *            uid
	 * @param bizId
	 *            bizId
	 * @param listPay
	 *            listPay
	 * @param result
	 *            PayByJdResult
	 * @return 返回数据
	 */
	@Transactional
	public List<PaymentPartition> addPaymentPartition(String cardBank, String cardNo, String cardName,
			String outTradeNo, String cardPhone, BigDecimal tradeAmount, BigDecimal limitAmount, String bizType,
			String uid, int bizId, List<PaymentPartition> listPay, PayByJdResult result) {
		int index = 1, end = 1;
		if (listPay != null && listPay.size() > 0) {
			index = listPay.get(listPay.size() - 1).getPartitionIndex() + 1;
			end = index;
		}
		tradeAmount = tradeAmount.subtract(result.getNotFailMoney());
		if (tradeAmount.compareTo(limitAmount) > 0) {
			end = tradeAmount.divide(limitAmount, 0, BigDecimal.ROUND_UP).intValue()
					+ listPay.get(listPay.size() - 1).getPartitionIndex();
		}

		for (int i = index; i <= end && (tradeAmount.compareTo(zero) > 0); i++) {
			PaymentPartition vo = new PaymentPartition();
			vo.setOutBizCode(outTradeNo);
			vo.setBizType(bizType);
			vo.setBizId(bizId);
			vo.setuId(uid);
			vo.setStatus(allPay);
			vo.setUserTelphone(cardPhone);
			vo.setUserCardNo(cardNo);
			vo.setOperTime(new Date());
			vo.setUserCardShort(cardBank);
			vo.setUserName(cardName);
			vo.setSubmit(notSubmit);
			// 设置分批的位置号
			vo.setPartitionIndex(i);
			vo.setUserAmount(tradeAmount.compareTo(limitAmount) > 0
					? limitAmount
					: tradeAmount);
			// 设置分批订单号
			vo.setOutBizCodePartition(vo.getOutBizCode() + i);
			tradeAmount = tradeAmount.subtract(limitAmount);
			paymentPartitionMapper.insertSelective(vo);
			listPay.add(vo);
		}

		return listPay;
	}
	
	/**
	 * 获取已经打款的总金额
	 * 
	 * @param listPay
	 *            listPay
	 * @return 返回数据
	 */
	public PayByJdResult getReachMoney(List<PaymentPartition> listPay) {
		PayByJdResult vo = new PayByJdResult();
		vo.setDataListSize(listPay.size());
		for (PaymentPartition tmp : listPay) {
			vo.setSubmit((vo.isSubmit() && tmp.getSubmit() == submit));

			if (tmp.getStatus() != null && tmp.getStatus() == successPay) {
				vo.setReachMoney(vo.getReachMoney().add(tmp.getUserAmount()));
				vo.getSuccessList().add(tmp.getOutBizCodePartition() + "," + tmp.getUserAmount().toString());

			} else if (tmp.getStatus() != null && tmp.getStatus() == waitPay) {
				vo.setWaitMoney(vo.getWaitMoney().add(tmp.getUserAmount()));
				vo.getWaitList().add(tmp.getOutBizCodePartition() + "," + tmp.getUserAmount().toString());

			} else if (tmp.getStatus() != null && tmp.getStatus() == failPay) {
				vo.setFailMoney(vo.getFailMoney().add(tmp.getUserAmount()));
				vo.getFailList().add(tmp.getOutBizCodePartition() + "," + tmp.getUserAmount().toString());
			}

			// 不是失败的金额做统计
			if (tmp.getStatus() != null && tmp.getStatus() != failPay) {
				vo.setNotFailMoney(vo.getNotFailMoney().add(tmp.getUserAmount()));
			}

			vo.setAllMoney(vo.getAllMoney().add(tmp.getUserAmount()));
		}

		return vo;
	}

	/**
	 * 
	 * 发送信息
	 * 
	 * @param msg
	 *            消息
	 */
	public void sendSms(String msg) {
		//发送格式有问题，先不发了
//		SendSMSUtils.sendSMS(lzpMobile, msg);
	}

}
