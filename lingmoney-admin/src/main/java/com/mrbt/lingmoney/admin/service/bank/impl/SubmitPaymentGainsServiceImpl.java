package com.mrbt.lingmoney.admin.service.bank.impl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.mrbt.lingmoney.admin.service.bank.PaymentCommonService;
import com.mrbt.lingmoney.admin.service.bank.SubmitPaymentGainsService;
import com.mrbt.lingmoney.admin.service.base.VerifyService;
import com.mrbt.lingmoney.bank.deal.HxSubmitPaymentGains;
import com.mrbt.lingmoney.bank.deal.dto.HxQuerySubmitPaymentReqDto;
import com.mrbt.lingmoney.bank.deal.dto.HxQuerySubmitPaymentResDto;
import com.mrbt.lingmoney.bank.deal.dto.HxSubmitPaymentGainsReqDto;
import com.mrbt.lingmoney.bank.deal.dto.RepayListReqDto;
import com.mrbt.lingmoney.bank.deal.dto.RepayListResDto;
import com.mrbt.lingmoney.bank.utils.exception.ResponseInfoException;
import com.mrbt.lingmoney.commons.cache.RedisGet;
import com.mrbt.lingmoney.commons.cache.RedisSet;
import com.mrbt.lingmoney.commons.exception.PageInfoException;
import com.mrbt.lingmoney.mapper.AccountFlowMapper;
import com.mrbt.lingmoney.mapper.HxAccountMapper;
import com.mrbt.lingmoney.mapper.HxPaymentBidBorrowUnionMapper;
import com.mrbt.lingmoney.mapper.HxPaymentMapper;
import com.mrbt.lingmoney.mapper.TradingFixInfoMapper;
import com.mrbt.lingmoney.mapper.TradingMapper;
import com.mrbt.lingmoney.mapper.UsersAccountMapper;
import com.mrbt.lingmoney.model.AccountFlow;
import com.mrbt.lingmoney.model.AccountFlowExample;
import com.mrbt.lingmoney.model.HxAccount;
import com.mrbt.lingmoney.model.HxAccountExample;
import com.mrbt.lingmoney.model.HxPayment;
import com.mrbt.lingmoney.model.HxPaymentBidBorrowUnionExample;
import com.mrbt.lingmoney.model.HxPaymentBidBorrowUnionExample.Criteria;
import com.mrbt.lingmoney.model.HxPaymentBidBorrowUnionInfo;
import com.mrbt.lingmoney.model.HxPaymentExample;
import com.mrbt.lingmoney.model.Trading;
import com.mrbt.lingmoney.model.TradingExample;
import com.mrbt.lingmoney.model.TradingFixInfo;
import com.mrbt.lingmoney.model.TradingFixInfoExample;
import com.mrbt.lingmoney.model.UsersAccount;
import com.mrbt.lingmoney.model.UsersAccountExample;
import com.mrbt.lingmoney.utils.AppCons;
import com.mrbt.lingmoney.utils.Common;
import com.mrbt.lingmoney.utils.PageInfo;
import com.mrbt.lingmoney.utils.ResultParame.ResultInfo;
import com.mrbt.lingmoney.utils.ResultParame.ResultNumber;

/**
 * 
 * @author Administrator
 *
 */
@Service("SubmitPaymentGainsService")
public class SubmitPaymentGainsServiceImpl implements SubmitPaymentGainsService {
	
	private static final Logger LOGGER = LogManager.getLogger(SubmitPaymentGainsService.class);

	@Autowired
	private VerifyService verifyService;
	@Autowired
	private HxSubmitPaymentGains hxSubmitPaymentGains;
	@Autowired
	private HxPaymentBidBorrowUnionMapper paymentUnionMapper;
	@Autowired
	private HxPaymentMapper hxPaymentMapper;
	@Autowired
	private RedisGet redisGet;
	@Autowired
	private RedisSet redisSet;
	@Autowired
	private UsersAccountMapper usersAccountMapper;
	@Autowired
	private PaymentCommonService paymentCommonService;
	@Autowired
	private HxAccountMapper hxAccountMapper;
	@Autowired
	private TradingMapper tradingMapper;
	@Autowired
	private TradingFixInfoMapper tradingFixInfoMapper;
	@Autowired
	private AccountFlowMapper accountFlowMapper;

	@Override
	public Map<String, Object> requestSubmit(Object channelFlow) throws Exception {

		// 验证流水号
		HxPaymentBidBorrowUnionInfo paymentInfo = verifyService.verifyPaymentBorrowInfo(channelFlow);

		// 借款编码
		String loanno = paymentInfo.getLoanNo();

		@SuppressWarnings("unchecked")
		List<RepayListReqDto> li = paymentCommonService.queryRepayList(loanno, null, null).getRows();
		// 请求体
		HxSubmitPaymentGainsReqDto req = new HxSubmitPaymentGainsReqDto();
		req.setBWACNAME(paymentInfo.getBwAcname());
		req.setBWACNO(paymentInfo.getBwAcno());
		req.setREPAYLIST(li);
		req.setOLDREQSEQNO(paymentInfo.getChannelFlow());
		// 1=正常还款 3=公司垫付后
		if (paymentInfo.getPaymentType() == ResultNumber.THREE.getNumber()) {
			req.setDFFLAG("2");
		} else {
			req.setDFFLAG("1");
		}
		req.setLOANNO(loanno);
		req.setTOTALNUM(StringUtils.isEmpty(li.size()) ? "0" : Integer.toString(li.size()));

		HxPayment payment = new HxPayment();

		// 提交
		Map<String, Object> res = hxSubmitPaymentGains.requestPayment(req);
		if (!StringUtils.isEmpty(res.get("channelFlow"))) {
			payment.setSubmitChannelFlow(res.get("channelFlow").toString());
		}
		// 修改状态为已提交
		HxPaymentExample payEx = new HxPaymentExample();
		payEx.createCriteria().andChannelFlowEqualTo(channelFlow.toString());
		payment.setSubmitDetailStatus(1);
		hxPaymentMapper.updateByExampleSelective(payment, payEx);

		// 修改产品状态 14 还款收益明细已提交
		paymentCommonService.changeProductStatus(paymentInfo.getpId(), AppCons.PRODUCT_STATUS_INCOME_DETAIL_APPLY);

		return res;
	}

	@Override
	public HxQuerySubmitPaymentResDto querySubmitResult(HxQuerySubmitPaymentReqDto req) throws Exception {
		String channelFlow = req.getOLDREQSEQNO();

		HxPaymentBidBorrowUnionExample ex = new HxPaymentBidBorrowUnionExample();
		ex.createCriteria().andSubmitChannelFlowEqualTo(channelFlow);
		List<HxPaymentBidBorrowUnionInfo> paymentLi = paymentUnionMapper.selectByExample(ex);

		if (paymentLi.isEmpty()) {
			throw new PageInfoException("此提交明细流水号无对应还款信息", ResultInfo.EMPTY_DATA.getCode());
		}

		HxPaymentBidBorrowUnionInfo payment = paymentLi.get(0);

		Integer submitStatus = payment.getSubmitDetailStatus();
		if (submitStatus.equals(ResultNumber.THREE.getNumber())) {
			HxQuerySubmitPaymentResDto resDto = new HxQuerySubmitPaymentResDto();
			resDto.setBWACNAME(payment.getBwAcname());
			resDto.setBWACNO(payment.getBwAcno());
			resDto.setLOANNO(payment.getLoanNo());
			resDto.setDFFLAG(payment.getPaymentType().toString());
			resDto.setRETURN_STATUS("S");
			// 投资人列表
			@SuppressWarnings("unchecked")
			List<RepayListReqDto> li = paymentCommonService.queryRepayList(payment.getLoanNo(), null, null).getRows();
			List<RepayListResDto> res = Common.copyPropertyToList(li, RepayListResDto.class);
			resDto.setRSLIST(res);
			return resDto;
		}

		return requestSubmitResult(req, payment.getChannelFlow());
	}

	/**
	 * 请求银行接口查询还款收益明细提交结果
	 * 
	 * @author syb
	 * @date 2017年9月21日 下午3:16:13
	 * @version 1.0
	 * @param req	req
	 * @param repaymentChannelFlow
	 *            还款流水号
	 * @return	return
	 * @throws Exception Exception
	 *	
	 */
	public HxQuerySubmitPaymentResDto requestSubmitResult(HxQuerySubmitPaymentReqDto req, String repaymentChannelFlow)
			throws Exception {
		verifyService.verifyEmpty(req.getLOANNO(), "借款编号为空");
		verifyService.verifyEmpty(req.getOLDREQSEQNO(), "原流水号为空");
		LOGGER.info("准备查询流水号为[" + req.getOLDREQSEQNO() + "]的提交收益明细操作结果。");
		// 查询数据库状态

        HxQuerySubmitPaymentResDto resDto = hxSubmitPaymentGains.requestQueryResult(req);

		if ("F".equals(resDto.getRETURN_STATUS())) {
			LOGGER.info("流水号为[" + req.getOLDREQSEQNO() + "]的提交收益明细操作已失败。失败原因：" + resDto.getERRORMSG());
			// 重新提交
			redisSet.setListElement("PAYMENTINFOQUEUE", repaymentChannelFlow);

			LOGGER.info("已从新添加至还款收益明细提交redis队列，等待下次提交。流水号：" + req.getOLDREQSEQNO());
			// failedOperation(resDto.getOLDREQSEQNO(), resDto.getRSLIST());

		} else if ("S".equals(resDto.getRETURN_STATUS())) {
			LOGGER.info("流水号为[" + req.getOLDREQSEQNO() + "]的提交收益明细操作已成功。");
			successOperation(req.getOLDREQSEQNO(), resDto.getRSLIST());

		} else {
			LOGGER.info("流水号为[" + req.getOLDREQSEQNO() + "]的提交收益明细操作仍在处理中，请稍后查询。");

		}
		return resDto;
	}

	@Override
	public PageInfo queryList(String loanNo, String channelFlow, PageInfo pageInfo) {
		HxPaymentBidBorrowUnionExample ex = new HxPaymentBidBorrowUnionExample();
		Criteria criteria = ex.createCriteria();

		if (!StringUtils.isEmpty(loanNo)) {
			criteria.andLoanNoEqualTo(loanNo);
		}
		if (!StringUtils.isEmpty(channelFlow)) {
			criteria.andChannelFlowEqualTo(channelFlow);
		}
		criteria.andStatusEqualTo(1); // 只能提交已经还款成功的
        criteria.andPaymentTypeNotEqualTo(2); // 垫付后借款人还款，不需要提交收益明细

		pageInfo.setTotal(new Long(paymentUnionMapper.countByExample(ex)).intValue());

		ex.setOrderByClause("payment_date desc");
		ex.setLimitStart(pageInfo.getFrom());
		ex.setLimitEnd(pageInfo.getSize());
		pageInfo.setRows(paymentUnionMapper.selectByExample(ex));
		return pageInfo;
	}

	@Override
	public void addRedisQueue(String channelFlow) throws Exception {
		// 手动添加一条收益提交
		// 验证流水号
		verifyService.verifyPaymentBorrowInfo(channelFlow);

		LOGGER.info("流水号为" + channelFlow + "的还款操作，其收益明细提交已push到redis队列，请等待下一次提交计划执行");
		redisSet.setListElement("PAYMENTINFOQUEUE", channelFlow); // rightpush
	}

	@Override
	public PageInfo queryRepayList(String loanno, PageInfo pageInfo) throws Exception {

		return paymentCommonService.queryRepayList(loanno, pageInfo.getFrom(), pageInfo.getSize());
	}

	// 逻辑： 计划任务定时查询redis队列（右进左出），依次pop，执行收益明细提交
	@Override
	public void pollingPaymentRedis() {
		LOGGER.info("进入检测还款结果进程...");
		// redisSet.setListElement("PAYMENTINFOQUEUE",
		// "P2P00120170614067OCDU1YVLR5F");// 测试
		long size = redisGet.getListLength("PAYMENTINFOQUEUE");
		LOGGER.info("redis队列中已还款未提交收益明细的结果有" + size + "个");
		if (size <= 0) {
			LOGGER.info("进程结束。");
			return;
		}
		for (int i = 0; i < size; i++) {
			// pop队列元素
			Object channelFlow = redisGet.getListLeftLastElement("PAYMENTINFOQUEUE");
			if (StringUtils.isEmpty(channelFlow)) {
				LOGGER.info("提交失败，数据流水号为空");
				continue;
			}
			try {
				LOGGER.info("开始准备提交第[" + (i + 1) + "]个收益明细，其对应的还款流水号为" + channelFlow);

				// 执行提交 !
				requestSubmit(channelFlow);
			} catch (ResponseInfoException ex) {
				if (ex.getCode() == ResultInfo.REQUEST_AGAIN.getCode()) {
					LOGGER.info(ex.getMessage());
					continue;
				}
				// 提交失败 此时重新把此channel放回reids
				LOGGER.info("提交失败，错误码" + ex.getCode() + ":" + ex.getMessage());
				failedOperation(channelFlow);
			} catch (PageInfoException ex) {
				if (ex.getCode() == ResultInfo.REQUEST_AGAIN.getCode()) {
					LOGGER.info(ex.getMessage());
					continue;
				}
				// 提交失败 此时重新把此channel放回reids
				LOGGER.info("提交失败，错误码" + ex.getCode() + ":" + ex.getMessage());
				failedOperation(channelFlow);
			} catch (Exception ex) {
				// 提交失败 此时重新把此channel放回reids
				LOGGER.info("提交失败:异常信息");
				ex.printStackTrace();
				failedOperation(channelFlow);
			}
			continue;
		}

	}

	@Override
	public void pollingSubmitResult() {
		// 查询状态为已提交的明细
		HxPaymentExample ex = new HxPaymentExample();
		ex.createCriteria().andSubmitDetailStatusEqualTo(1);
		List<HxPayment> paymentLi = hxPaymentMapper.selectByExample(ex);
		if (paymentLi == null || paymentLi.size() == 0) {
			LOGGER.info("无在提交中状态的收益明细提交操作，任务结束");
			return;

		} 
		for (HxPayment payment : paymentLi) {
			// 查出借款编号
			HxQuerySubmitPaymentReqDto req = new HxQuerySubmitPaymentReqDto();
			req.setOLDREQSEQNO(payment.getSubmitChannelFlow());

			try {
				HxPaymentBidBorrowUnionInfo res = verifyService.verifyPaymentBorrowInfo(payment.getChannelFlow());
				req.setLOANNO(res.getLoanNo());
				requestSubmitResult(req, payment.getChannelFlow());
			} catch (ResponseInfoException e) {
				LOGGER.info("错误码:" + e.getCode() + ",错误信息：" + e.getMessage());
				continue;
			} catch (PageInfoException e) {
				LOGGER.info("错误码:" + e.getCode() + ",错误信息：" + e.getMessage());
				continue;
			} catch (Exception e) {
                LOGGER.info("请求查询失败。" + e.getMessage());
				e.printStackTrace();
				continue;
			}

		}

	}

	/**
	 * 请求提交明细失败时操作
	 * 
	 * @author YJQ 2017年8月3日
	 * @param oldreqseqno	oldreqseqno
	 */
	private void failedOperation(Object oldreqseqno) {
		// 修改状态为未提交
		HxPaymentExample ex = new HxPaymentExample();
        ex.createCriteria().andChannelFlowEqualTo(oldreqseqno.toString());
		List<HxPayment> paymentLi = hxPaymentMapper.selectByExample(ex);
		if (paymentLi != null && !paymentLi.isEmpty()) {
			HxPayment payment = new HxPayment();
			payment.setId(paymentLi.get(0).getId());
			payment.setSubmitDetailStatus(0);
			payment.setSubmitChannelFlow("");
			hxPaymentMapper.updateByPrimaryKeySelective(payment);

			if (!paymentLi.get(0).getStatus().equals(1)) {
				LOGGER.info("还款流水号为[" + paymentLi.get(0).getChannelFlow() + "]的还款操作已失败，请重新还款，若已还款请忽略此条信息");
				return;
			}
			redisSet.setListElement("PAYMENTINFOQUEUE", paymentLi.get(0).getChannelFlow()); // rightpush
			LOGGER.info("还款流水号为[" + paymentLi.get(0).getChannelFlow() + "]的提交收益明细已重新push到redis队列，等待下次提交");
		}
	}

	/**
	 * 更新账户流水状态
	 * 
	 * @author YJQ 2017年8月3日
	 * @param status	status
	 * @param bizCode	bizCode
	 */
    private void changeAccountFlowStatus(Integer status, String bizCode) {
		AccountFlowExample accFlowEx = new AccountFlowExample();
        accFlowEx.createCriteria().andNumberEqualTo(bizCode).andTypeEqualTo(ResultNumber.THREE.getNumber());
		AccountFlow accFlow = new AccountFlow();
		accFlow.setOperateTime(new Date());
		accFlow.setStatus(status);
		accountFlowMapper.updateByExampleSelective(accFlow, accFlowEx);
	}

	/**
	 * 查询提交明细已成功
	 * 
	 * @author YJQ 2017年8月4日
	 * @param channelFlow	channelFlow
	 * @param repayLi	channelFlow
	 * @throws Exception	Exception
	 */
	private void successOperation(String channelFlow, List<RepayListResDto> repayLi) throws Exception {
		HxPaymentBidBorrowUnionExample unionEx = new HxPaymentBidBorrowUnionExample();
		unionEx.createCriteria().andSubmitChannelFlowEqualTo(channelFlow);

		List<HxPaymentBidBorrowUnionInfo> paymentInfoLi = paymentUnionMapper.selectByExample(unionEx);
		if (StringUtils.isEmpty(paymentInfoLi) || paymentInfoLi.size() == 0) {
			throw new PageInfoException("此流水号无对应还款信息", ResultInfo.EMPTY_DATA.getCode());
		}
		HxPaymentBidBorrowUnionInfo paymentRes = paymentInfoLi.get(0);
		Integer pid = paymentRes.getpId();

        // 个人借款需要修改 借款人账户冻结金额，企业账户无账户信息，无需修改
		UsersAccount borrowerAccount = usersAccountMapper.selectByUid(paymentRes.getuId());
        if (borrowerAccount != null) {
            UsersAccount borrowerAccountRecord = new UsersAccount();
            borrowerAccountRecord.setId(borrowerAccount.getId());
            borrowerAccountRecord.setFrozenMoney(borrowerAccount.getFrozenMoney().subtract(paymentRes.getAmount()));
            usersAccountMapper.updateByPrimaryKeySelective(borrowerAccountRecord);
            LOGGER.info("更新借款人冻结金额成功。原：" + borrowerAccount.getFrozenMoney() + ";现："
                    + borrowerAccountRecord.getFrozenMoney());

            // 修改还款流水状态为成功
            AccountFlowExample accountFlowExmp = new AccountFlowExample();
            accountFlowExmp.createCriteria().andTypeEqualTo(ResultNumber.FIVE.getNumber()).andNumberEqualTo(paymentRes.getChannelFlow());
            AccountFlow accountFlowRecord = new AccountFlow();
            accountFlowRecord.setStatus(1);
            accountFlowRecord.setFrozenMoney(borrowerAccountRecord.getFrozenMoney());
            accountFlowMapper.updateByExampleSelective(accountFlowRecord, accountFlowExmp);
		}

		// 修改提交明细状态
		HxPayment submitDto = new HxPayment();
		submitDto.setId(paymentRes.getId());
		submitDto.setSubmitDetailStatus(ResultNumber.THREE.getNumber());
		hxPaymentMapper.updateByPrimaryKeySelective(submitDto);

		// 修改产品状态 15 还款收益明细提交成功
		paymentCommonService.changeProductStatus(pid, AppCons.PRODUCT_STATUS_INCOME_DETAIL_SUCCESS);

		for (RepayListResDto repay : repayLi) {
			if (!"S".equals(repay.getSTATUS())) {
				LOGGER.info("账号为：" + repay.getACNO() + ";账户名为：" + repay.getACNAME() + "的用户收益提交失败！");
				continue;
			}
			String acNo = repay.getACNO();
			BigDecimal money = new BigDecimal(repay.getAMOUNT());
			// #start 查询uid
			HxAccountExample accEx = new HxAccountExample();
			accEx.createCriteria().andAcNoEqualTo(acNo);
			List<HxAccount> hxAccLi = hxAccountMapper.selectByExample(accEx);
			if (hxAccLi.size() == 0) {
				LOGGER.info("账号为[" + repay.getACNO() + "];账户名为[" + repay.getACNAME() + "]的用户在本平台查不到信息！");
				continue;
			}
			String uid = hxAccLi.get(0).getuId();
			// #end

			// #start 更新用户账户信息
			// 投资人已获得收益增加，账户余额增加
			UsersAccountExample ex = new UsersAccountExample();
			ex.createCriteria().andUIdEqualTo(uid);

			List<UsersAccount> resLi = usersAccountMapper.selectByExample(ex);
			if (resLi == null || resLi.size() == 0) {
				throw new PageInfoException("用户账户信息不存在", ResultInfo.EMPTY_DATA.getCode());
			}
			UsersAccount userAcc = resLi.get(0);

			UsersAccount userAccNew = new UsersAccount();
			userAccNew.setBalance(userAcc.getBalance().add(money));
			userAccNew.setIncome(userAcc.getIncome().add(new BigDecimal(repay.getINCOMEAMT())));
			usersAccountMapper.updateByExampleSelective(userAccNew, ex);

			// #end

			// #start 修改用户交易状态
			// 收益明细提交成功后 需要把交易状态和交易流水状态 改成卖出成功 3（SELL_OK） ，
			// 插一条类型赎回[3]的账户流水

			// 修改数据的查询条件为状态为2（回款中）的交易记录，由于存在一个用户多次购买同一产品，所以此处需要循环修改
			TradingExample traEx = new TradingExample();
			traEx.createCriteria().andUIdEqualTo(uid).andPIdEqualTo(pid).andStatusEqualTo(AppCons.SELL_STATUS);
			List<Trading> tradeLi = tradingMapper.selectByExample(traEx);
			for (Trading trade : tradeLi) {
				Integer tid = trade.getId();
				// 逐条修改
				Trading newTrade = new Trading();
				newTrade.setId(tid);
				newTrade.setStatus(AppCons.SELL_OK);
				tradingMapper.updateByPrimaryKeySelective(newTrade);

				TradingFixInfo newFix = new TradingFixInfo();
				newFix.setStatus(AppCons.SELL_OK);
				TradingFixInfoExample fixEx = new TradingFixInfoExample();
				fixEx.createCriteria().andTIdEqualTo(tid);
				tradingFixInfoMapper.updateByExampleSelective(newFix, fixEx);

				// 更新用户流水状态
                changeAccountFlowStatus(1, trade.getOutBizCode());
			}
		}

	}
}
