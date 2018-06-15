package com.mrbt.lingmoney.admin.service.bank.impl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Map;
import java.util.UUID;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.mrbt.lingmoney.admin.service.bank.CompanyAdvPaymentService;
import com.mrbt.lingmoney.admin.service.bank.PaymentCommonService;
import com.mrbt.lingmoney.admin.service.base.VerifyService;
import com.mrbt.lingmoney.bank.deal.HxCompanyAdvPayment;
import com.mrbt.lingmoney.bank.deal.dto.HxCompanyAdvPaymentReqDto;
import com.mrbt.lingmoney.bank.utils.exception.ResponseInfoException;
import com.mrbt.lingmoney.mapper.HxPaymentMapper;
import com.mrbt.lingmoney.model.HxBidBorrowUnionInfo;
import com.mrbt.lingmoney.model.HxPayment;
import com.mrbt.lingmoney.model.Product;
import com.mrbt.lingmoney.utils.AppCons;
import com.mrbt.lingmoney.utils.ResultParame;

/**
 * 
 * CompanyAdvPaymentServiceImpl
 *
 */
@Service
public class CompanyAdvPaymentServiceImpl implements CompanyAdvPaymentService {
	
	@Autowired
	private VerifyService verifyService;
	
	@Autowired
	private HxCompanyAdvPayment hxCompanyAdvPayment;
	
	@Autowired
	private HxPaymentMapper hxPaymentMapper;
	
	@Autowired
	private PaymentCommonService paymentCommonService;

	private static final Logger LOGGER = LogManager.getLogger(CompanyAdvPaymentService.class);

	@Override
	public Map<String, Object> requestPayment(HxCompanyAdvPaymentReqDto req) throws Exception {

		String loanNo = req.getLOANNO();

		// #start 验证参数
		verifyService.verifyEmpty(loanNo, "借款编号为空");

		LOGGER.info("借款编号为：" + loanNo + "的借款信息，请求还款");

		BigDecimal feeAmt = new BigDecimal(0);
		if (!StringUtils.isEmpty(req.getFEEAMT())) {
			verifyService.verifyMoney(req.getFEEAMT(), "手续费格式错误");
			feeAmt = new BigDecimal(req.getFEEAMT());
			LOGGER.info("请求手续费为：" + feeAmt);
		}

		// #end

		// 根据借款编号查询还款信息
		HxBidBorrowUnionInfo bidUnionInfo = verifyService.verifyBorrowInfo(loanNo);

		verifyService.verifyEmpty(bidUnionInfo.getBwAcno(), "还款账号");
		LOGGER.info("还款账号为：" + bidUnionInfo.getBwAcno());
		verifyService.verifyEmpty(bidUnionInfo.getBwAcname(), "还款账号户名");
		LOGGER.info("还款账号户名为：" + bidUnionInfo.getBwAcname());
		// 查询是否已经还款
		verifyService.verifyPaymentStatus(loanNo);

//		// 计算应还金额 本金*预期年化收益率*实际存续天数/365
//		// 查询产品信息
		Product product = verifyService.verifyProductStatus(bidUnionInfo.getpId());
		Date start = product.getValueDt(); // 产品计息日
		int days = com.mrbt.lingmoney.bank.utils.DateUtils.dateDiff(start, new Date());

		// 还款总额 通过每个投资人的本金+收益算出
		BigDecimal resBwAcc = paymentCommonService.queryRepayList(product, true);

		LOGGER.info("应还款金额为：" + resBwAcc);
		LOGGER.info("用户输入还款金额为：" + req.getAMOUNT());

		// 入库
		HxPayment hxPayment = new HxPayment();
		String id = UUID.randomUUID().toString().replace("-", "");
		hxPayment.setId(id);
		hxPayment.setBwId(bidUnionInfo.getBwId());
		hxPayment.setPaymentType(ResultParame.ResultNumber.THREE.getNumber());
		hxPayment.setPaymentDate(new Date());
		hxPayment.setStatus(ResultParame.ResultNumber.TWO.getNumber()); // 还款中
		hxPayment.setAmount(resBwAcc);
		hxPayment.setFeeAmt(feeAmt);
		hxPayment.setDays(days);
		hxPaymentMapper.insertSelective(hxPayment);
		LOGGER.info("还款记录入库成功，id为：" + id);
		
		// 请求
		req.setAMOUNT(resBwAcc.toString());
		req.setBWACNAME(bidUnionInfo.getBwAcname());
		req.setBWACNO(bidUnionInfo.getBwAcno());
		Map<String, Object> resMap = hxCompanyAdvPayment.requestPayment(req);
		Object flow = resMap.get("channelFlow");
		if (StringUtils.isEmpty(flow)) {
			// 更新状态
			hxPayment = new HxPayment();
			hxPayment.setId(id);
			hxPayment.setChannelFlow(flow.toString());
			hxPaymentMapper.updateByPrimaryKeySelective(hxPayment);
			throw new ResponseInfoException("返回流水号为空", ResultParame.ResultInfo.EMPTY_DATA.getCode());
		}
		// 更新状态
		hxPayment = new HxPayment();
		hxPayment.setId(id);
		hxPayment.setChannelFlow(flow.toString());
		hxPaymentMapper.updateByPrimaryKeySelective(hxPayment);
		LOGGER.info("还款请求已提交，流水号为：" + flow);
		
		paymentCommonService.changeProductStatus(bidUnionInfo.getpId(), AppCons.PRODUCT_STATUS_REPAYMENT_APPLY);
		
		return resMap;
	}
}
