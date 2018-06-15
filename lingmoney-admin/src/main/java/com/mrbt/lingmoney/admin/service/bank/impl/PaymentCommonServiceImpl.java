package com.mrbt.lingmoney.admin.service.bank.impl;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.mrbt.lingmoney.admin.service.bank.PaymentCommonService;
import com.mrbt.lingmoney.admin.service.base.VerifyService;
import com.mrbt.lingmoney.bank.HxBaseData;
import com.mrbt.lingmoney.bank.deal.dto.RepayListReqDto;
import com.mrbt.lingmoney.commons.exception.PageInfoException;
import com.mrbt.lingmoney.mapper.AccountFlowMapper;
import com.mrbt.lingmoney.mapper.HxBidBorrowUnionMapper;
import com.mrbt.lingmoney.mapper.ProductMapper;
import com.mrbt.lingmoney.mapper.TradingFixInfoMapper;
import com.mrbt.lingmoney.mapper.TradingMapper;
import com.mrbt.lingmoney.mapper.UsersAccountMapper;
import com.mrbt.lingmoney.mapper.UsersRedPacketMapper;
import com.mrbt.lingmoney.mapper.UsersRedPacketUnionMapper;
import com.mrbt.lingmoney.model.AccountFlow;
import com.mrbt.lingmoney.model.HxBidBorrowUnionInfo;
import com.mrbt.lingmoney.model.HxTradingAccUnion;
import com.mrbt.lingmoney.model.HxTradingAccUnionExample;
import com.mrbt.lingmoney.model.Product;
import com.mrbt.lingmoney.model.ProductExample;
import com.mrbt.lingmoney.model.ProductWithBLOBs;
import com.mrbt.lingmoney.model.Trading;
import com.mrbt.lingmoney.model.TradingFixInfo;
import com.mrbt.lingmoney.model.TradingFixInfoExample;
import com.mrbt.lingmoney.model.UsersAccount;
import com.mrbt.lingmoney.model.UsersRedPacket;
import com.mrbt.lingmoney.model.UsersRedPacketExample;
import com.mrbt.lingmoney.model.UsersRedPacketUnion;
import com.mrbt.lingmoney.model.UsersRedPacketUnionExample;
import com.mrbt.lingmoney.utils.AppCons;
import com.mrbt.lingmoney.utils.Common;
import com.mrbt.lingmoney.utils.PageInfo;
import com.mrbt.lingmoney.utils.ProductUtils;
import com.mrbt.lingmoney.utils.ResultParame;

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
	private ProductMapper productMapper;
	@Autowired
	private UsersRedPacketUnionMapper usersRedPacketUnionMapper;
	@Autowired
	private UsersRedPacketMapper usersRedPacketMapper;
	@Autowired
	private TradingFixInfoMapper tradingFixInfoMapper;
	@Autowired
	private AccountFlowMapper accountFlowMapper;
	@Autowired
	private UsersAccountMapper usersAccountMapper;
	@Autowired
	private TradingMapper tradingMapper;

	@Override
	public BigDecimal queryRepayList(Product product, boolean flag) throws Exception {

		Integer pid = product.getId();
		BigDecimal yield = product.getfYield(); // 固定收益率
		Date start = product.getValueDt(); // 产品计息日
		int days = com.mrbt.lingmoney.bank.utils.DateUtils.dateDiff(start, new Date());

		HxTradingAccUnionExample tradeEx = new HxTradingAccUnionExample();
		List<Integer> paramList = new ArrayList<Integer>();
		paramList.add(AppCons.BUY_OK);
		paramList.add(AppCons.SELL_STATUS);
		tradeEx.createCriteria().andPIdEqualTo(pid).andAcNameIsNotNull().andAcNoIsNotNull().andTradingStatusIn(paramList);

		List<HxTradingAccUnion> tradeLi = tradingAccUnionMapper.selectTradeUnionByExample(tradeEx);
		if (StringUtils.isEmpty(tradeLi) || tradeLi.size() == 0) {
			throw new PageInfoException("无投资信息", ResultParame.ResultInfo.SUBMIT_PARAMS_ERROR.getCode());
		}

		// 计算应还金额 本金*预期年化收益率*实际存续天数/365

		// 所有投资人总金额
		BigDecimal totalPaymentAmount = BigDecimal.ZERO;

		for (HxTradingAccUnion tradeAcc : tradeLi) {

			// 本金
			BigDecimal principalAmt = tradeAcc.getFinancialMoney();
			// uid
			String uid = tradeAcc.getuId();
			// trade id
			Integer tradeId = tradeAcc.getId();

			// 加息券收益
			BigDecimal incomeAmtRedpacket = BigDecimal.ZERO;
			// 产品收益
			BigDecimal incomeAmtPro = BigDecimal.ZERO;

			// 查询此用户下是否有加息券
			UsersRedPacketUnion redPacketInfo = null;
			UsersRedPacketUnionExample redPacketEx = new UsersRedPacketUnionExample();
            redPacketEx.createCriteria().andUIdEqualTo(uid).andStatusEqualTo(1).andTIdEqualTo(tradeId);
			List<UsersRedPacketUnion> redPacketLi = usersRedPacketUnionMapper.selectByExample(redPacketEx);
			// 如果有加息券，一笔交易只能使用一张加息券
			if (redPacketLi.size() > 0) {
				redPacketInfo = redPacketLi.get(0);
			}

			// 产品状态为3（还款中），表示产品正常还款，不需要重新计算
			if (product.getStatus().intValue() == AppCons.PRODUCT_STATUS_REPAYMENT) {
				if (redPacketInfo != null) {
					incomeAmtRedpacket = new BigDecimal(redPacketInfo.getActualAmount().toString());
				}

				incomeAmtPro = tradeAcc.getInterest();

			} else {
				if (redPacketInfo != null) {
					incomeAmtRedpacket = ProductUtils.countInterest(principalAmt, days, new BigDecimal(redPacketInfo
							.getHrpNumber().toString()));
				}

				incomeAmtPro = ProductUtils.countInterest(principalAmt, days, yield);

				// 数据更新
                if (flag && product.getStatus() != AppCons.PRODUCT_STATUS_REPAYMENT_APPLY) {
					// 更新用户加息券收益
					UsersRedPacket usersRedPacket = new UsersRedPacket();
					usersRedPacket.setActualAmount(incomeAmtRedpacket.doubleValue());
					UsersRedPacketExample packetEx = new UsersRedPacketExample();
					packetEx.createCriteria().andTIdEqualTo(tradeId).andUIdEqualTo(uid);
					usersRedPacketMapper.updateByExampleSelective(usersRedPacket, packetEx);

					// 更新trading信息
					Trading tradingRecord = new Trading();
					tradingRecord.setId(tradeAcc.getId());
					tradingRecord.setSellDt(new Date());
					tradingRecord.setStatus(AppCons.SELL_STATUS);
					tradingRecord.setSellMoney(principalAmt.add(incomeAmtPro));
                    tradingRecord.setInterest(incomeAmtPro);
                    tradingRecord.setpCode(tradeAcc.getpCode());
                    Common.buildOutBizCode(tradingRecord, "手动还款_");
					tradingMapper.updateByPrimaryKeySelective(tradingRecord);

					// 更新投资人trading_fix_info信息
					// 修改字段 interest expiry_dt f_time
					TradingFixInfo tradingFixInfo = new TradingFixInfo();
					tradingFixInfo.setInterest(incomeAmtPro);
					tradingFixInfo.setExpiryDt(new Date());
					tradingFixInfo.setfTime(days);
					tradingFixInfo.setStatus(AppCons.SELL_STATUS);
					TradingFixInfoExample tradeFixEx = new TradingFixInfoExample();
					tradeFixEx.createCriteria().andTIdEqualTo(tradeAcc.getId());
					tradingFixInfoMapper.updateByExampleSelective(tradingFixInfo, tradeFixEx);

					// 插入一条用户流水
					UsersAccount userAcc = usersAccountMapper.selectByUid(uid);

					AccountFlow accFlow = new AccountFlow();
					accFlow.setaId(userAcc.getId());
					accFlow.setBalance(userAcc.getBalance().add(tradingRecord.getSellMoney()).add(incomeAmtRedpacket));
					accFlow.setMoney(tradingRecord.getSellMoney().add(incomeAmtRedpacket));
					accFlow.setNote("赎回");
                    accFlow.setNumber(tradingRecord.getOutBizCode());
					accFlow.setOperateTime(new Date());
					accFlow.setPlatform(0);
					accFlow.setType(ResultParame.ResultNumber.THREE.getNumber());
					accFlow.setStatus(0);
					accountFlowMapper.insertSelective(accFlow);

				}

			}

			/*
			 * BigDecimal incomeAmt =
			 * principalAmt.multiply(yield.add(addNumberTotal)).multiply(new
			 * BigDecimal(days)) .divide(new BigDecimal(365),
			 * BigDecimal.ROUND_HALF_UP).setScale(2, BigDecimal.ROUND_HALF_UP);
			 */

			// 总收益
			BigDecimal incomeAmt = incomeAmtPro.add(incomeAmtRedpacket)
					.setScale(ResultParame.ResultNumber.TWO.getNumber(), BigDecimal.ROUND_HALF_UP);

			// 还款总金额 = 本次还款本金+本次还款收益+本次还款费用
			BigDecimal total = principalAmt.add(incomeAmt);

			// 计算所有投资人总金额
			totalPaymentAmount = totalPaymentAmount.add(total);

		}

		return totalPaymentAmount;
	}

	@Override
	public PageInfo queryRepayList(String loanno, Integer limitStart, Integer limitEnd) throws Exception {
		SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
		// 查询借款信息-已验证空否
		HxBidBorrowUnionInfo bwInfo = verifyService.verifyBorrowInfo(loanno);

		PageInfo res = new PageInfo();

		// 取出产品id
		Integer pid = bwInfo.getpId();
		if (StringUtils.isEmpty(pid)) {
			throw new PageInfoException("无产品信息", ResultParame.ResultInfo.SUBMIT_PARAMS_ERROR.getCode());
		}

		List<Integer> params = new ArrayList<Integer>();
		params.add(AppCons.BUY_OK);
		params.add(AppCons.SELL_STATUS);
		params.add(AppCons.SELL_OK);
		HxTradingAccUnionExample tradeEx = new HxTradingAccUnionExample();
		tradeEx.createCriteria().andPIdEqualTo(pid).andAcNameIsNotNull().andAcNoIsNotNull().andStatusIn(params);

		res.setTotal(new Long(tradingAccUnionMapper.countTradeUnionByExample(tradeEx)).intValue());

		if (limitStart != null && limitEnd != null) {
			tradeEx.setLimitStart(limitStart);
			tradeEx.setLimitEnd(limitEnd);
		}

		List<HxTradingAccUnion> tradeLi = tradingAccUnionMapper.selectTradeUnionByExample(tradeEx);
		if (StringUtils.isEmpty(tradeLi) || tradeLi.size() == 0) {
			throw new PageInfoException("无投资信息", ResultParame.ResultInfo.SUBMIT_PARAMS_ERROR.getCode());
		}

		List<RepayListReqDto> li = new ArrayList<RepayListReqDto>();

		// 所有投资人总
		BigDecimal totalPaymentAmount = BigDecimal.ZERO;

		for (HxTradingAccUnion tradeAcc : tradeLi) {
			// 查询此用户下是否有加息券
			BigDecimal addNumberTotal = BigDecimal.ZERO; // 加息券所获得利息总和
			String uid = tradeAcc.getuId();
			UsersRedPacketUnionExample redPacketEx = new UsersRedPacketUnionExample();
            redPacketEx.createCriteria().andUIdEqualTo(uid).andStatusEqualTo(1).andTIdEqualTo(tradeAcc.getId());
			List<UsersRedPacketUnion> redPacketLi = usersRedPacketUnionMapper.selectByExample(redPacketEx);
			if (redPacketLi.size() > 0) {
				// 用户存在加息券
				for (UsersRedPacketUnion redPacketInfo : redPacketLi) {
                    addNumberTotal = addNumberTotal.add(new BigDecimal(redPacketInfo.getActualAmount().toString()));
				}
			}
			// 本金
			BigDecimal principalAmt = StringUtils.isEmpty(tradeAcc.getFinancialMoney()) ? BigDecimal.ZERO
					: tradeAcc.getFinancialMoney();

			// 收益
			BigDecimal incomeAmt = StringUtils.isEmpty(tradeAcc.getInterest()) ? BigDecimal.ZERO
					: tradeAcc.getInterest();

			// 还款总金额 = 本次还款本金+本次还款收益+本次还款费用
			BigDecimal total = principalAmt.add(incomeAmt).add(addNumberTotal);

			// 计算所有投资人总金额
			totalPaymentAmount = totalPaymentAmount.add(total);

			RepayListReqDto r = new RepayListReqDto();

			r.setACNAME(tradeAcc.getAcName());

			r.setACNO(tradeAcc.getAcNo());

			// 总金额
			r.setAMOUNT(total.toPlainString());

			// 收益
            r.setINCOMEAMT(incomeAmt.add(addNumberTotal).toPlainString());

			// 本金
			r.setPRINCIPALAMT(principalAmt.toPlainString());

			// 结息日
			r.setINCOMEDATE(format.format(tradeAcc.getExpiryDt()));

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
