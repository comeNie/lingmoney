package com.mrbt.lingmoney.service.bank.impl;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.mrbt.lingmoney.bank.HxBaseData;
import com.mrbt.lingmoney.bank.deal.dto.RepayListReqDto;
import com.mrbt.lingmoney.commons.exception.PageInfoException;
import com.mrbt.lingmoney.mapper.HxBidBorrowUnionMapper;
import com.mrbt.lingmoney.mapper.HxPaymentBidBorrowUnionMapper;
import com.mrbt.lingmoney.mapper.ProductMapper;
import com.mrbt.lingmoney.mapper.UsersRedPacketUnionMapper;
import com.mrbt.lingmoney.model.HxBidBorrowUnionInfo;
import com.mrbt.lingmoney.model.HxPaymentBidBorrowUnionExample;
import com.mrbt.lingmoney.model.HxPaymentBidBorrowUnionInfo;
import com.mrbt.lingmoney.model.HxTradingAccUnion;
import com.mrbt.lingmoney.model.HxTradingAccUnionExample;
import com.mrbt.lingmoney.model.Product;
import com.mrbt.lingmoney.model.ProductExample;
import com.mrbt.lingmoney.model.ProductWithBLOBs;
import com.mrbt.lingmoney.model.UsersRedPacketUnion;
import com.mrbt.lingmoney.model.UsersRedPacketUnionExample;
import com.mrbt.lingmoney.service.bank.PaymentCommonService;
import com.mrbt.lingmoney.service.users.VerifyService;
import com.mrbt.lingmoney.utils.PageInfo;
import com.mrbt.lingmoney.utils.ResultParame.ResultInfo;

/**
 * 还款、明细提交共用服务
 * 
 * @author YJQ
 *
 */
@Service
public class PaymentCommonServiceImpl implements PaymentCommonService {

	@Autowired
	private VerifyService verifyService;
	@Autowired
	private HxBidBorrowUnionMapper tradingAccUnionMapper;
	@Autowired
	private HxPaymentBidBorrowUnionMapper paymentUnionMapper;
	@Autowired
	private ProductMapper productMapper;
	@Autowired
	private UsersRedPacketUnionMapper usersRedPacketUnionMapper;

	@Override
	public PageInfo queryRepayList(String loanno, Integer days, Integer limitStart, Integer limitEnd) throws Exception {
		SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
		// 查询借款信息-已验证空否
        HxBidBorrowUnionInfo bwInfo = verifyService.verifyBorrowInfo(loanno);

		PageInfo res = new PageInfo();

		// 取出产品id
		Integer pid = bwInfo.getpId();
		if (StringUtils.isEmpty(pid)) {
            throw new PageInfoException("无产品信息", ResultInfo.SUBMIT_PARAMS_ERROR.getCode());
		}
		Product product = productMapper.selectByPrimaryKey(pid);
        BigDecimal yield = product.getfYield(); // 固定收益率
		HxPaymentBidBorrowUnionInfo paymentInfo = new HxPaymentBidBorrowUnionInfo();
		if (StringUtils.isEmpty(days)) {
			// 查询还款信息
			HxPaymentBidBorrowUnionExample bwEx = new HxPaymentBidBorrowUnionExample();
			bwEx.createCriteria().andLoanNoEqualTo(loanno).andStatusNotEqualTo(3).andStatusNotEqualTo(4);
			List<HxPaymentBidBorrowUnionInfo> paymentInfoLi = paymentUnionMapper.selectByExample(bwEx);
			if (StringUtils.isEmpty(paymentInfoLi) || paymentInfoLi.size() == 0) {
                throw new PageInfoException("此借款编号对应无成功还款操作", ResultInfo.SUBMIT_PARAMS_ERROR.getCode());
			}
			paymentInfo = paymentInfoLi.get(0);
            days = paymentInfo.getDays(); // 计息天数
		}
		if (StringUtils.isEmpty(days)) {
            throw new PageInfoException("计息天数不存在", ResultInfo.SUBMIT_PARAMS_ERROR.getCode());
		}

		HxTradingAccUnionExample tradeEx = new HxTradingAccUnionExample();
		tradeEx.createCriteria().andPIdEqualTo(pid).andAcNameIsNotNull().andAcNoIsNotNull().andStatusEqualTo(1);

		res.setTotal(new Long(tradingAccUnionMapper.countTradeUnionByExample(tradeEx)).intValue());

		if (limitStart != null && limitEnd != null) {
			tradeEx.setLimitStart(limitStart);
			tradeEx.setLimitEnd(limitEnd);
		}

		List<HxTradingAccUnion> tradeLi = tradingAccUnionMapper.selectTradeUnionByExample(tradeEx);
		if (StringUtils.isEmpty(tradeLi) || tradeLi.size() == 0) {
            throw new PageInfoException("无投资信息", ResultInfo.SUBMIT_PARAMS_ERROR.getCode());
		}

		List<RepayListReqDto> li = new ArrayList<RepayListReqDto>();

		// 所有投资人总
		BigDecimal totalPaymentAmount = BigDecimal.ZERO;

		for (HxTradingAccUnion tradeAcc : tradeLi) {

			// 查询此用户下是否有加息券
			BigDecimal addNumberTotal = BigDecimal.ZERO;
			String uid = tradeAcc.getuId();
			UsersRedPacketUnionExample redPacketEx = new UsersRedPacketUnionExample();
			redPacketEx.createCriteria().andUIdEqualTo(uid).andStatusEqualTo(1).andTIdEqualTo(tradeAcc.getId())
					.andValidityTimeGreaterThan(new Date());
			List<UsersRedPacketUnion> redPacketLi = usersRedPacketUnionMapper.selectByExample(redPacketEx);
			if (redPacketLi.size() > 0) {
				// 用户存在加息券
				for (UsersRedPacketUnion redPacketInfo : redPacketLi) {
					addNumberTotal = addNumberTotal.add(new BigDecimal(redPacketInfo.getHrpNumber()));
				}
			}
			// 本金
			BigDecimal principalAmt = tradeAcc.getFinancialMoney();

			if (StringUtils.isEmpty(principalAmt)) {
				principalAmt = new BigDecimal(0);
			}

			// 计算收益
			BigDecimal incomeAmt = principalAmt.multiply(yield.add(addNumberTotal)).multiply(new BigDecimal(days))
					.divide(new BigDecimal(365), BigDecimal.ROUND_HALF_UP).setScale(2, BigDecimal.ROUND_HALF_UP);

			// 还款总金额 = 本次还款本金+本次还款收益+本次还款费用
			BigDecimal total = principalAmt.add(incomeAmt);

			// 计算所有投资人总金额
			totalPaymentAmount = totalPaymentAmount.add(total);

			RepayListReqDto r = new RepayListReqDto();

			r.setACNAME(tradeAcc.getAcName());

			r.setACNO(tradeAcc.getAcNo());

			// 总金额
			r.setAMOUNT(total.toPlainString());

			// 收益
			r.setINCOMEAMT(incomeAmt.toPlainString());

			// 本金
			r.setPRINCIPALAMT(principalAmt.toPlainString());

            if (paymentInfo != null && !StringUtils.isEmpty(paymentInfo.getPaymentDate())) {
				r.setINCOMEDATE(format.format(paymentInfo.getPaymentDate()));
			}
			r.setSUBSEQNO(HxBaseData.MERCHANTID + HxBaseData.getChannelFlow("OGW00074"));

			li.add(r);
		}

		res.setRows(li);
		res.setObj(totalPaymentAmount);
		return res;
	}

	@Override
	public void changeProductStatus(Integer pid, Integer status) throws Exception {
		ProductExample proEx = new ProductExample();
		proEx.createCriteria().andIdEqualTo(pid);
		ProductWithBLOBs pro = new ProductWithBLOBs();
		pro.setStatus(status);
		productMapper.updateByExampleSelective(pro, proEx);
	}

}
