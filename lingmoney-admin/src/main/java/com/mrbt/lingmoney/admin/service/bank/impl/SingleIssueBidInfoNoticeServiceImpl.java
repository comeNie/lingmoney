package com.mrbt.lingmoney.admin.service.bank.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.mrbt.lingmoney.admin.service.bank.SingleIssueBidInfoNoticeService;
import com.mrbt.lingmoney.bank.bid.HxSingleIssueBidInfoNotice;
import com.mrbt.lingmoney.mapper.HxBiddingCustomerMapper;
import com.mrbt.lingmoney.mapper.HxBiddingMapper;
import com.mrbt.lingmoney.mapper.HxBorrowerInfoCustomerMapper;
import com.mrbt.lingmoney.mapper.HxBorrowerInfoMapper;
import com.mrbt.lingmoney.mapper.HxBorrowerMapper;
import com.mrbt.lingmoney.mapper.ProductMapper;
import com.mrbt.lingmoney.model.HxBidding;
import com.mrbt.lingmoney.model.HxBiddingCustomer;
import com.mrbt.lingmoney.model.HxBorrowerInfoCustomer;
import com.mrbt.lingmoney.model.ProductWithBLOBs;
import com.mrbt.lingmoney.utils.AppCons;
import com.mrbt.lingmoney.utils.PageInfo;
import com.mrbt.lingmoney.utils.PropertiesUtil;
import com.mrbt.lingmoney.utils.ResultParame;

/**
 * 6.11 单笔发标信息通知相关接口
 * 
 * @author lihq
 * @date 2017年6月6日 下午1:44:42
 * @description
 * @version 1.0
 * @since 2017-04-12
 */
@Service
public class SingleIssueBidInfoNoticeServiceImpl implements SingleIssueBidInfoNoticeService {

	@Autowired
	private HxSingleIssueBidInfoNotice hxSingleIssueBidInfoNotice;

	@Autowired
	private HxBiddingMapper hxBiddingMapper;

	@Autowired
	private HxBiddingCustomerMapper hxBiddingCustomerMapper;

	@Autowired
	private HxBorrowerInfoMapper hxBorrowerInfoMapper;

	@Autowired
	private HxBorrowerMapper hxBorrowerMapper;

	@Autowired
	private ProductMapper productMapper;

	@Autowired
	private HxBorrowerInfoCustomerMapper hxBorrowerInfoCustomerMapper;

	private static final Logger LOGGER = LogManager.getLogger(SingleIssueBidInfoNoticeServiceImpl.class);

	private static final String LOGHEARD = "\nHxSingleIssueBidInfoNotice_";

	private static final String BANKURL = PropertiesUtil.getPropertiesByKey("BANK_POST_URL");

	@Override
	public PageInfo requestSingleIssueBidInfoNotice(String appId, String biddingId) throws Exception {
		// 生成日志头
		String logGroup = LOGHEARD + System.currentTimeMillis() + "_";

		LOGGER.info("{}单笔发标请求,标的id：{}", logGroup, biddingId);

		PageInfo pageInfo = new PageInfo();
		// 根据标的id查询标的
		PageInfo pi = new PageInfo();
		Map<String, Object> condition = new HashMap<String, Object>();
		condition.put("id", biddingId);
		pi.setCondition(condition);
		List<HxBiddingCustomer> lists = hxBiddingCustomerMapper.findByCondition(pi);
		if (StringUtils.isEmpty(lists) || lists.size() == 0) {
			pageInfo.setMsg("标的不存在");
			pageInfo.setCode(ResultParame.ResultInfo.RETURN_DATA_EMPTY_DATA.getCode());
			return pageInfo;
		}
		HxBiddingCustomer hxBiddingCustomer = lists.get(0);
		// 如果不为初始状态（-1）或者 5 则不允许发标该产品已发标
        if (!"-1".equals(hxBiddingCustomer.getInvestObjState()) && !"5".equals(hxBiddingCustomer.getInvestObjState())) {
			pageInfo.setMsg("请选择初始标的");
			pageInfo.setCode(ResultParame.ResultInfo.RETURN_DATA_EMPTY_DATA.getCode());
			return pageInfo;
		}
		// 根据标的id找到所有借款人借款信息
		PageInfo pi2 = new PageInfo();
		Map<String, Object> condition2 = new HashMap<String, Object>();
		condition2.put("biddingId", biddingId);
		pi2.setCondition(condition2);
		List<HxBorrowerInfoCustomer> list2 = hxBorrowerInfoCustomerMapper.findByCondition(pi2);
		if (StringUtils.isEmpty(list2) || list2.size() == 0) {
			pageInfo.setMsg("借款人不存在");
			pageInfo.setCode(ResultParame.ResultInfo.RETURN_DATA_EMPTY_DATA.getCode());
			return pageInfo;
		}
		HxBorrowerInfoCustomer hxBorrowerInfoCustomer = list2.get(0);
		// 请求参数
		String loanNo = hxBiddingCustomer.getLoanNo(), investId = hxBiddingCustomer.getInvestId(),
				investObjName = hxBiddingCustomer.getInvestObjName(),
				investObjInfo = hxBiddingCustomer.getInvestObjInfo(),
				minInvestAmt = hxBiddingCustomer.getMinInvestAmt().toString(),
				maxInvestAmt = hxBiddingCustomer.getMaxInvestAmt().toString(),
				investObjAmt = hxBiddingCustomer.getInvestObjAmt().toString(),
				investBeginDate = hxBiddingCustomer.getInvestBeginDate(),
				investEndDate = hxBiddingCustomer.getInvestEndDate(), repayDate = hxBiddingCustomer.getRepayDate(),
				yearRate = hxBiddingCustomer.getYearRate().toString(),
				investRange = hxBiddingCustomer.getInvestRange().toString(),
				rateStype = hxBiddingCustomer.getRateStype(), repayStype = hxBiddingCustomer.getRepayStype(),
				investObjState = "0", bwTotalNum = hxBiddingCustomer.getBwTotalNum().toString(),
				remark1 = hxBiddingCustomer.getRemark(), zrFlag = hxBiddingCustomer.getZrFlag(),
				refLoanNo = hxBiddingCustomer.getRefLoanNo(), oldReqseq = hxBiddingCustomer.getOldReqseq(),
				bwAcname = hxBorrowerInfoCustomer.getBwAcname(), bwIdtype = hxBorrowerInfoCustomer.getBwIdtype(),
				bwIdno = hxBorrowerInfoCustomer.getBwIdno(), bwAcno = hxBorrowerInfoCustomer.getBwAcno(),
				bwAcbankid = hxBorrowerInfoCustomer.getBwAcbankid(),
				bwAcbankname = hxBorrowerInfoCustomer.getBwAcbankname(),
				bwAmt = hxBorrowerInfoCustomer.getBwAmt().toString(),
				mortgageId = hxBorrowerInfoCustomer.getMortgageId(),
				mortgageInfo = hxBorrowerInfoCustomer.getMortgageInfo(),
				checkDate = hxBorrowerInfoCustomer.getCheckDate(), remark2 = hxBorrowerInfoCustomer.getRemark();

		Map<String, Object> resMap = hxSingleIssueBidInfoNotice.singleIssueBidInfoNotice(appId, loanNo, investId,
				investObjName, investObjInfo, minInvestAmt, maxInvestAmt, investObjAmt, investBeginDate, investEndDate,
				repayDate, yearRate, investRange, rateStype, repayStype, investObjState, bwTotalNum, remark1, zrFlag,
				refLoanNo, oldReqseq, bwAcname, bwIdtype, bwIdno, bwAcno, bwAcbankid, bwAcbankname, bwAmt, mortgageId,
				mortgageInfo, checkDate, remark2, logGroup, BANKURL);
		if (resMap != null) {
			String errorCode = resMap.get("errorCode").toString();
			HxBidding hxBidding = hxBiddingMapper.selectByPrimaryKey(biddingId);
			// 原发标流水号channelFlow
			String channelFlow = resMap.get("channelFlow").toString();
			if (errorCode.equals("0")) { // 以下信息，只有errorCode =0时才返回，即正常响应时才返回
				String status = resMap.get("status").toString();
				if (status.equals("0")) { // 受理成功，不代表操作成功
					// 发标成功，修改标的状态为正常
					hxBidding.setInvestObjState("0");
					hxBidding.setRemark(channelFlow);
					hxBiddingMapper.updateByPrimaryKeySelective(hxBidding);
					// 修改产品状态为募集中
					ProductWithBLOBs product = productMapper.selectByPrimaryKey(hxBidding.getpId());
					product.setStatus(AppCons.PRODUCT_STATUS_READY);
					productMapper.updateByPrimaryKeySelective(product);
					pageInfo.setCode(ResultParame.ResultInfo.SUCCESS.getCode());
					pageInfo.setMsg("操作成功");
				} else if (status.equals("1")) { // 受理失败，交易可置为失败
					hxBidding.setInvestObjState("5");
					hxBidding.setRemark(channelFlow + "银行受理失败");
					hxBiddingMapper.updateByPrimaryKeySelective(hxBidding);
					pageInfo.setCode(ResultParame.ResultInfo.TRADING_NOT_SUCCESS.getCode());
					pageInfo.setMsg("银行受理失败");
				} else {
					pageInfo.setCode(ResultParame.ResultInfo.ING.getCode());
					pageInfo.setMsg("处理中");
				}
			} else { // 错误，返回具体错误原因
				String errorMsg = resMap.get("errorMsg").toString();
				hxBidding.setInvestObjState("5");
				hxBidding.setRemark(channelFlow + errorMsg);
				hxBiddingMapper.updateByPrimaryKeySelective(hxBidding);
				pageInfo.setCode(ResultParame.ResultInfo.TRADING_NOT_SUCCESS.getCode());
				pageInfo.setMsg(errorMsg);
			}
		} else {
			pageInfo.setCode(ResultParame.ResultInfo.ING.getCode());
			pageInfo.setMsg("处理中");
		}

		return pageInfo;
	}

}
