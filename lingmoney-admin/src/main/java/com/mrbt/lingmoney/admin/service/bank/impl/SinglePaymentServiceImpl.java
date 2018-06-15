package com.mrbt.lingmoney.admin.service.bank.impl;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.dom4j.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.mrbt.lingmoney.admin.service.bank.BankAccountBalanceService;
import com.mrbt.lingmoney.admin.service.bank.PaymentCommonService;
import com.mrbt.lingmoney.admin.service.bank.SinglePaymentService;
import com.mrbt.lingmoney.admin.service.base.VerifyService;
import com.mrbt.lingmoney.bank.HxBaseData;
import com.mrbt.lingmoney.bank.deal.HxSinglePayment;
import com.mrbt.lingmoney.bank.deal.dto.HxQueryPaymentReqDto;
import com.mrbt.lingmoney.bank.deal.dto.HxQueryPaymentResDto;
import com.mrbt.lingmoney.bank.deal.dto.HxSinglePaymentReqDto;
import com.mrbt.lingmoney.bank.repayment.HxRepaymentFinishNotice;
import com.mrbt.lingmoney.bank.utils.dto.PageResponseDto;
import com.mrbt.lingmoney.bank.utils.dto.ResponseBodyDto;
import com.mrbt.lingmoney.bank.utils.exception.ResponseInfoException;
import com.mrbt.lingmoney.commons.cache.RedisSet;
import com.mrbt.lingmoney.commons.exception.PageInfoException;
import com.mrbt.lingmoney.mapper.AccountFlowMapper;
import com.mrbt.lingmoney.mapper.HxBidBorrowUnionMapper;
import com.mrbt.lingmoney.mapper.HxBiddingMapper;
import com.mrbt.lingmoney.mapper.HxPaymentBidBorrowUnionMapper;
import com.mrbt.lingmoney.mapper.HxPaymentMapper;
import com.mrbt.lingmoney.mapper.UsersAccountMapper;
import com.mrbt.lingmoney.model.AccountFlow;
import com.mrbt.lingmoney.model.HxBidBorrowUnionExample;
import com.mrbt.lingmoney.model.HxBidBorrowUnionInfo;
import com.mrbt.lingmoney.model.HxBidding;
import com.mrbt.lingmoney.model.HxBiddingExample;
import com.mrbt.lingmoney.model.HxPayment;
import com.mrbt.lingmoney.model.HxPaymentBidBorrowUnionExample;
import com.mrbt.lingmoney.model.HxPaymentBidBorrowUnionInfo;
import com.mrbt.lingmoney.model.HxPaymentExample;
import com.mrbt.lingmoney.model.Product;
import com.mrbt.lingmoney.model.UsersAccount;
import com.mrbt.lingmoney.model.UsersAccountExample;
import com.mrbt.lingmoney.utils.AppCons;
import com.mrbt.lingmoney.utils.Common;
import com.mrbt.lingmoney.utils.DateUtils;
import com.mrbt.lingmoney.utils.PageInfo;
import com.mrbt.lingmoney.utils.PropertiesUtil;
import com.mrbt.lingmoney.utils.ResultParame;
import com.mrbt.lingmoney.utils.ResultParame.ResultInfo;
import com.mrbt.lingmoney.utils.ResultParame.ResultNumber;

/**
 * 
 * @author Administrator
 *
 */
@Service
public class SinglePaymentServiceImpl implements SinglePaymentService {
	@Autowired
	private VerifyService verifyService;
	@Autowired
	private HxSinglePayment hxSinglePayment;
	@Autowired
	private HxPaymentMapper hxPaymentMapper;
	@Autowired
	private HxPaymentBidBorrowUnionMapper hxPaymentBidBorrowUnionMapper;
	@Autowired
	private RedisSet redisSet;
	@Autowired
	private HxBidBorrowUnionMapper hxBidBorrowUnionMapper;
	@Autowired
	private BankAccountBalanceService bankAccountBalanceService;
	@Autowired
	private UsersAccountMapper usersAccountMapper;
	@Autowired
	private PaymentCommonService paymentCommonService;
	@Autowired
	private HxRepaymentFinishNotice hxRepaymentFinishNotice;
    @Autowired
    private HxBiddingMapper hxBiddingMapper;
    @Autowired
    private AccountFlowMapper accountFlowMapper;

	private static final Logger LOGGER = LogManager.getLogger(SinglePaymentService.class);

	/**
	 * 说明 HxSinglePaymentReqDto 中借款编号必填，手续费、原垫付流水号、还款方式为选填
	 */
	@Override
	public PageResponseDto requestPayment(HxSinglePaymentReqDto req) throws Exception {

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

        // 如果还款方式为 2=垫付后借款人还款，原垫付请求流水号不得为空
		String oldReq = "";
		if ("2".equals(req.getDFFLAG())) {
			verifyService.verifyEmpty(req.getOLDREQSEQNO(), "原垫付请求流水号为空");
			oldReq = req.getOLDREQSEQNO();
			LOGGER.info("请求公司垫付还款，原垫付流水号为：" + oldReq);
		}
		// #end

		// 根据借款编号查询还款信息
		HxBidBorrowUnionInfo bidUnionInfo = verifyService.verifyBorrowInfo(loanNo);

		// 验证产品是否可以还款，只有产品状态为：2（运行中），3（还款中），11（已确认自动还款）的才可还款
		Product product = verifyService.verifyProductStatus(bidUnionInfo.getpId());

		verifyService.verifyEmpty(bidUnionInfo.getBwAcno(), "还款账号");
		LOGGER.info("还款账号为：" + bidUnionInfo.getBwAcno());
		verifyService.verifyEmpty(bidUnionInfo.getBwAcname(), "还款账号户名");
		LOGGER.info("还款账号户名为：" + bidUnionInfo.getBwAcname());

		// 查询是否已经还款
		verifyService.verifyPaymentStatus(loanNo);

		// 还款总额 通过每个投资人的本金+收益算出
		BigDecimal resBwAcc = paymentCommonService.queryRepayList(product, true);

		LOGGER.info("应还款金额为：" + resBwAcc);
		LOGGER.info("用户输入还款金额为：" + req.getAMOUNT());
		// 入库
		HxPayment hxPayment = new HxPayment();
		String id = UUID.randomUUID().toString().replace("-", "");
		hxPayment.setId(id);
		hxPayment.setBwId(bidUnionInfo.getBwId());
		// hxPayment.setChannelFlow(pageResponseDto.getchannelFlow());
		hxPayment.setPaymentType(Integer.parseInt(req.getDFFLAG()));
		hxPayment.setPaymentDate(new Date());
		hxPayment.setStatus(ResultNumber.TWO.getNumber()); // 还款中
		hxPayment.setAmount(resBwAcc);
		hxPayment.setOldChannelFlow(oldReq);
		hxPaymentMapper.insertSelective(hxPayment);
		LOGGER.info("还款记录入库成功，id为：" + id);

		// 验证余额是否够支付
		String code = bankAccountBalanceService.checkAccountBalance(req.getAPPID(), resBwAcc.toString(),
				bidUnionInfo.getBwAcno());
		// String code = "000000"; test data
		if (!"000000".equals(code)) {
			LOGGER.info("可用余额不足，还款失败");

			hxPayment = new HxPayment();
			hxPayment.setId(id);
			hxPayment.setStatus(ResultNumber.THREE.getNumber());
			hxPaymentMapper.updateByPrimaryKeySelective(hxPayment);

			switch (code) {
				case "540026" :
				throw new ResponseInfoException("余额为零",
						ResultParame.ResultNumber.FIVE_FOUR_ZERO_ZERO_TWO_SIX.getNumber());
				case "540009" :
				throw new ResponseInfoException("余额不足",
						ResultParame.ResultNumber.FIVE_FOUR_ZERO_ZERO_TWO_SIX.getNumber());
				case "540008" :
				throw new ResponseInfoException("账户没有关联",
						ResultParame.ResultNumber.FIVE_FOUR_ZERO_ZERO_TWO_SIX.getNumber());
				case "OGW001" :
				throw new ResponseInfoException("账户与户名不匹配", ResultParame.ResultNumber.THREE_ZERO_THREE_ONE.getNumber());
				default :
					break;
			}
		}

		// 装载请求数据
		req.setRETURNURL(PropertiesUtil.getPropertiesByKey("SINGLE_PAYMENT_RETURN_URL"));
		// req.setAMOUNT(new BigDecimal(0.01).setScale(2,
		// BigDecimal.ROUND_HALF_UP).toPlainString()); test data
		req.setAMOUNT(resBwAcc.toPlainString());
		req.setBWACNAME(bidUnionInfo.getBwAcname());
		req.setBWACNO(bidUnionInfo.getBwAcno());
		PageResponseDto pageResponseDto = hxSinglePayment.requestPayment(req);
		pageResponseDto.setbankUrl(PropertiesUtil.getPropertiesByKey("BANK_POST_URL"));

		// 更新状态
		hxPayment = new HxPayment();
		hxPayment.setId(id);
		hxPayment.setChannelFlow(pageResponseDto.getchannelFlow());
		hxPaymentMapper.updateByPrimaryKeySelective(hxPayment);
		LOGGER.info("还款请求已提交，流水号为：" + pageResponseDto.getchannelFlow());

		// 修改产品状态 12 还款申请已提交
		paymentCommonService.changeProductStatus(bidUnionInfo.getpId(), AppCons.PRODUCT_STATUS_REPAYMENT_APPLY);

		return pageResponseDto;
	}

	@Override
	public String receivePaymentResult(HttpServletRequest request) throws Exception {

		ResponseBodyDto response = new ResponseBodyDto();

		String channelFlow = "";
		try {
			// 接收结果
			String message = Common.getXmlDocument(request);
			channelFlow = hxSinglePayment.receiveAsync(message);

			// 将还款信息加入redis队里，等待提交收益明细
			redisSet.setListElement("PAYMENTINFOQUEUE", channelFlow);
			LOGGER.info("流水号为" + channelFlow + "的还款操作，其收益明细提交已push到redis队列，请等待提交计划执行");

		} catch (ResponseInfoException ex) {
			response.setRETURNCODE(ex.getCode().toString());
			response.setRETURNMSG(ex.getMessage());
			return hxSinglePayment.responseAsync(response);
		}

		// 更新状态
		HxPayment hxPayment = new HxPayment();
		hxPayment.setStatus(1);
		hxPayment.setPaymentDate(new Date());
		HxPaymentExample ex = new HxPaymentExample();
		ex.createCriteria().andChannelFlowEqualTo(channelFlow);
		hxPaymentMapper.updateByExampleSelective(hxPayment, ex);

		// 返回状态

		response.setOLDREQSEQNO(channelFlow);

		// 更新借款人数据
		successOperation(channelFlow);

		return hxSinglePayment.responseAsync(response);
	}

	@Override
	public HxQueryPaymentResDto queryPaymentResult(HxQueryPaymentReqDto dto) throws Exception {
		SimpleDateFormat formatDate = new SimpleDateFormat("yyyyMMdd");
		SimpleDateFormat formatTime = new SimpleDateFormat("hhmmss");
		
		HxQueryPaymentResDto res = new HxQueryPaymentResDto();

		// 验证参数
		String channelFlow = dto.getOLDREQSEQNO();
		verifyService.verifyEmpty(channelFlow, "原借款人单笔还款交易流水号为空");

		// 查询数据库
		HxPaymentBidBorrowUnionExample ex = new HxPaymentBidBorrowUnionExample();
		ex.createCriteria().andChannelFlowEqualTo(channelFlow);
		List<HxPaymentBidBorrowUnionInfo> paymentLi = hxPaymentBidBorrowUnionMapper.selectByExample(ex);

		if (paymentLi.isEmpty()) {
			throw new ResponseInfoException("还款信息不存在", ResultParame.ResultInfo.BORROW_NUMBER_NOT_EXIST.getCode());
		}
		HxPaymentBidBorrowUnionInfo payment = paymentLi.get(0);
		if (payment.getStatus().equals(1)) {
			res.setAMOUNT(payment.getAmount().toPlainString());
			res.setOLDREQSEQNO(payment.getChannelFlow());
			res.setRETURN_STATUS("S");
			res.setTRANSDT(formatDate.format(payment.getPaymentDate()));
			res.setTRANSTM(formatTime.format(payment.getPaymentDate()));
			res.setLOANNO(payment.getLoanNo());
			res.setBWACNAME(payment.getBwAcname());
			res.setBWACNO(payment.getBwAcno());
			return res;
		}

		Integer status = null;

		try {
			Map<String, Object> resMap = hxSinglePayment.requestQueryResult(dto);
			res = (HxQueryPaymentResDto) Common.mapToBean(resMap, HxQueryPaymentResDto.class);

			// 根据结果更新数据库
			String returnStatus = res.getRETURN_STATUS();

			// 1-还款成功 2- 还款中 3-还款失败 4-超时
			if (!StringUtils.isEmpty(returnStatus)) {
				switch (returnStatus) {
				case "S":
					status = 1;
					redisSet.setListElement("PAYMENTINFOQUEUE", channelFlow);
					LOGGER.info("流水号为" + channelFlow + "的还款操作，其收益明细提交已push到redis队列，请等待提交计划执行");
					successOperation(channelFlow);
					break;
				case "F":
					status = ResultParame.ResultNumber.THREE.getNumber();
					failOperation(channelFlow);
					break;
				case "R":
					status = ResultParame.ResultNumber.TWO.getNumber();
					break;
				default:
					status = ResultParame.ResultNumber.TWO.getNumber();
				}

			} else {
				status = ResultParame.ResultNumber.TWO.getNumber();
			}

		} catch (ResponseInfoException e) {
			// 如果catch到异常 1005为逻辑异常
			if (e.getCode().equals(ResultParame.ResultInfo.CROSS_REQUEST_ERROR.getCode())) {
				status = ResultParame.ResultNumber.THREE.getNumber();
				res.setERRORMSG(e.getMessage());
				res.setOLDREQSEQNO(channelFlow);
				failOperation(channelFlow);
			}
		}

		HxPayment paymentNew = new HxPayment();
		paymentNew.setStatus(status);
		paymentNew.setId(payment.getId());
		hxPaymentMapper.updateByPrimaryKeySelective(paymentNew);
		res.setMyStatus(status);
		return res;
	}

	@Override
	public PageInfo queryList(String bwId, PageInfo pageInfo) throws Exception {

		HxPaymentExample ex = new HxPaymentExample();
		ex.createCriteria().andBwIdEqualTo(bwId);
		int count = hxPaymentMapper.countByExample(ex);

		ex.setLimitStart(pageInfo.getFrom());
		ex.setLimitEnd(pageInfo.getSize());
		List<HxPayment> resLi = hxPaymentMapper.selectByExample(ex);
		for (HxPayment payment : resLi) {
			// 如果有在处理中的记录 自动查询下
			if (payment.getStatus().equals(ResultParame.ResultNumber.TWO.getNumber())) {
				Integer index = resLi.indexOf(payment);
				HxQueryPaymentReqDto dto = new HxQueryPaymentReqDto();
				dto.setOLDREQSEQNO(payment.getChannelFlow());
				HxQueryPaymentResDto res = queryPaymentResult(dto);
				payment.setStatus(res.getMyStatus());
				resLi.set(index, payment);
			}
		}
		pageInfo.setCode(ResultParame.ResultInfo.SUCCESS.getCode());
		pageInfo.setRows(resLi);
		pageInfo.setTotal(count);
		return pageInfo;
	}

	@Override
	public void checkPayment() throws Exception {
		// 查询数据库，如果有当日到期的借款数据，自动还款
		HxBidBorrowUnionExample ex = new HxBidBorrowUnionExample();
		String currentDate = DateUtils.getDateStr(new Date(), new SimpleDateFormat("yyyy-MM-dd")).replace("-", "");
		ex.createCriteria().andRepayDateEqualTo(currentDate);
		List<HxBidBorrowUnionInfo> res = hxBidBorrowUnionMapper.selectByExample(ex);

		if (StringUtils.isEmpty(res) || res.size() == 0) {
			LOGGER.info("无" + currentDate + "当日应还款，等待下次还款。");
			return;
		}
		LOGGER.info(currentDate + "当日应还款，等待下次还款。");
		for (HxBidBorrowUnionInfo info : res) {
			HxSinglePaymentReqDto req = new HxSinglePaymentReqDto();
			req.setLOANNO(info.getLoanNo());
			// 请求还款
			requestPayment(req);
		}

	}

	@Override
	public BigDecimal queryAmount(String loanNo) throws Exception {

		// 根据借款编号查询还款信息
		HxBidBorrowUnionInfo bidUnionInfo = verifyService.verifyBorrowInfo(loanNo);

		// 计算应还金额 本金*预期年化收益率*实际存续天数/365
		// 查询产品信息,并验证产品状态
		Product product = verifyService.verifyProductStatus(bidUnionInfo.getpId());

		BigDecimal resBwAcc = paymentCommonService.queryRepayList(product, false);

		LOGGER.info("应还款金额为：" + resBwAcc);
		return resBwAcc;
	}

	@Override
	public void pollingPaymentResult() {
		// 查询处理中的还款操作的结果,自动还款除外
		HxPaymentExample ex = new HxPaymentExample();
		ex.createCriteria().andStatusEqualTo(ResultParame.ResultNumber.TWO.getNumber())
				.andPaymentTypeNotEqualTo(ResultParame.ResultNumber.FOUR.getNumber());
		List<HxPayment> paymentLi = hxPaymentMapper.selectByExample(ex);
		LOGGER.info("查询到数据库中有[" + paymentLi.size() + "]条仍在处理中的还款请求。");

		for (HxPayment payment : paymentLi) {
			String channelFlow = payment.getChannelFlow();
			String paymentId = payment.getId();
			try {

				// 如果超过25分钟
				Date requestTime = payment.getPaymentDate();
				if (DateUtils.dateDiffMins(requestTime, new Date()) > ResultParame.ResultNumber.TWENTY_FIVE
						.getNumber()) {
					HxPayment paymentD = new HxPayment();
					paymentD.setStatus(ResultParame.ResultNumber.FOUR.getNumber());
					paymentD.setId(paymentId);
					hxPaymentMapper.updateByPrimaryKeySelective(paymentD);
					LOGGER.info("修改数据库还款操作流水号为[" + channelFlow + "]的还款状态为：已超时[4]");
					failOperation(channelFlow);

					continue;
				}

				// 依次查询
				HxQueryPaymentReqDto req = new HxQueryPaymentReqDto();

				LOGGER.info("开始准备请求银行还款流水号为[" + channelFlow + "]的还款结果...");
				req.setOLDREQSEQNO(channelFlow);

				HxQueryPaymentResDto res;

				res = (HxQueryPaymentResDto) Common.mapToBean(hxSinglePayment.requestQueryResult(req),
						HxQueryPaymentResDto.class);
				// 根据结果更新数据库
				String returnStatus = res.getRETURN_STATUS();
				LOGGER.info("还款银行流水号为[" + channelFlow + "]的还款结果为" + returnStatus);
				Integer status = null;
				// 1-还款成功 2- 还款中 3-还款失败 4-超时
				if (!StringUtils.isEmpty(returnStatus)) {
					switch (returnStatus) {
					case "S":
						status = 1;
						redisSet.setListElement("PAYMENTINFOQUEUE", channelFlow);
						LOGGER.info("流水号为" + channelFlow + "的还款操作，其收益明细提交已push到redis队列，请等待提交计划执行");
						successOperation(channelFlow);
						break;
					case "F":
						status = ResultParame.ResultNumber.THREE.getNumber();
						LOGGER.info("流水号为" + channelFlow + "的还款操作已失败[F]，请重新还款");
						failOperation(channelFlow);
						break;
					case "R":
						LOGGER.info("修改数据库还款操作流水号为[" + channelFlow + "]的还款状态仍在处理中[R]");
						status = ResultParame.ResultNumber.TWO.getNumber();
						break;
					default:
						status = ResultParame.ResultNumber.TWO.getNumber();
					}
				}
				payment = new HxPayment();
				payment.setStatus(status);
				payment.setId(paymentId);
				hxPaymentMapper.updateByPrimaryKeySelective(payment);
			} catch (ResponseInfoException e) {
				LOGGER.info("查询还款操作流水号为[" + channelFlow + "]的结果失败。错误码[" + e.getCode() + "];错误信息：" + e.getMessage());
			} catch (Exception e) {
				LOGGER.info("查询还款操作流水号为[" + channelFlow + "]的结果失败。");
				e.printStackTrace();
			}

		}
	}

	/**
	 * 还款操作成功后进行的数据库操作
	 * 
	 * @param channelFlow
	 *            channelFlow
	 * @throws Exception
	 *             Exception
	 */
	private void successOperation(String channelFlow) throws Exception {

		HxPaymentBidBorrowUnionInfo paymentRes = verifyService.verifyPaymentBorrowInfo(channelFlow);
        // 如果是个人用户，扣除借款人账户余额
        if (!StringUtils.isEmpty(paymentRes.getuId())) {
            String uid = paymentRes.getuId();
            UsersAccountExample ex = new UsersAccountExample();
            ex.createCriteria().andUIdEqualTo(uid);

            List<UsersAccount> resLi = usersAccountMapper.selectByExample(ex);
            if (resLi == null || resLi.size() == 0) {
				throw new PageInfoException("用户账户信息不存在", ResultParame.ResultInfo.EMPTY_DATA.getCode());
            }
            UsersAccount userAcc = resLi.get(0);
            BigDecimal loanAmount = paymentRes.getAmount();
            UsersAccount userAccNew = new UsersAccount();
            userAccNew.setBalance(userAcc.getBalance().subtract(loanAmount));
            // 还款成功后，还款金额与手续费会冻结在借款人账户中，直到 P2P商户提交还款收益明细成功后才会扣除
            userAccNew.setFrozenMoney(userAcc.getFrozenMoney().add(loanAmount));
            usersAccountMapper.updateByExampleSelective(userAccNew, ex);

            // 保存一条账户流水
            AccountFlow accountFlow = new AccountFlow();
            accountFlow.setaId(userAcc.getId());
            accountFlow.setBalance(userAccNew.getBalance());
            accountFlow.setFrozenMoney(userAccNew.getFrozenMoney());
            accountFlow.setMoney(loanAmount);
            accountFlow.setNote("手动还款");
            accountFlow.setCardPan(paymentRes.getBwAcno());
            accountFlow.setNumber(channelFlow);
            accountFlow.setOperateTime(new Date());
            accountFlow.setPlatform(0);
			accountFlow.setStatus(ResultParame.ResultNumber.FIVE.getNumber());
			accountFlow.setType(ResultParame.ResultNumber.FIVE.getNumber());
            accountFlowMapper.insertSelective(accountFlow);
		}

        // 如果是垫付后借款人还款，不需要再次修改产品状态
        if (paymentRes.getPaymentType() != 2) {
            // 修改产品状态 13 还款成功
            paymentCommonService.changeProductStatus(paymentRes.getpId(), AppCons.PRODUCT_STATUS_REPAYMENT_SUCCESS);
        }
	}

	/**
	 * 数据返回
	 * 
	 * @param channelFlow
	 *            channelFlow
	 * @throws Exception
	 *             Exception
	 */
	private void failOperation(String channelFlow) throws Exception {
		HxPaymentBidBorrowUnionInfo paymentRes = verifyService.verifyPaymentBorrowInfo(channelFlow);
		// 修改产品状态为 3 （还款中）
		paymentCommonService.changeProductStatus(paymentRes.getpId(), AppCons.PRODUCT_STATUS_REPAYMENT);

	}

	@Override
	public PageInfo hxRepaymentFinishNotice(String loanNo, String logGroup) {
		PageInfo pageInfo = new PageInfo();

		// 结清标的必须存在有效的还款记录
		List<Map<String, Object>> resultMap = hxPaymentMapper.queryRepaymentAmountAndStatusByLoanNo(loanNo);
		if (resultMap == null || resultMap.size() < 1) {
			pageInfo.setCode(ResultParame.ResultInfo.EMPTY_DATA.getCode());
			pageInfo.setMsg("操作失败，未查询到有效还款记录!");
			return pageInfo;
		}

		// 还款成功后提交 标的结清通知
		Document finishDoc = hxRepaymentFinishNotice.requestRepaymentFinish(loanNo,
				"\n手动还款成功_" + System.currentTimeMillis());

		if (finishDoc != null) {
			Map<String, Object> map = HxBaseData.xmlToMap(finishDoc);
			String errorCode = (String) map.get("errorCode");

            if ("0".equals(errorCode) || "EAS020420054".equals(errorCode)) {
				pageInfo.setCode(ResultParame.ResultInfo.SUCCESS.getCode());
				pageInfo.setMsg("操作成功");
                // 标的结清成功后修改标的状态为3 ，标的结清
				HxBiddingExample example = new HxBiddingExample();
				example.createCriteria().andLoanNoEqualTo(loanNo);
				
				HxBidding record = new HxBidding();
				record.setInvestObjState("3");
                hxBiddingMapper.updateByExampleSelective(record, example);
			} else {
				pageInfo.setMsg((String) map.get("errorMsg"));
			}

		} else {
			pageInfo.setMsg("未收到银行返回信息，请稍后再试");
		}

		return pageInfo;
	}

    @Override
    public PageInfo repaymentAfterAdvance(String channelFlow) throws Exception {
        PageInfo pageInfo = new PageInfo();

        if (!StringUtils.isEmpty(channelFlow)) {
            HxPaymentExample example = new HxPaymentExample();
            example.createCriteria().andChannelFlowEqualTo(channelFlow);
            List<HxPayment> hxPaymentList = hxPaymentMapper.selectByExample(example);
            if (hxPaymentList != null && hxPaymentList.size() > 0) {
                HxPayment hxPayment = hxPaymentList.get(0);
                // 只有原付款成功后才能进行垫付后借款人还款
                if (hxPayment.getStatus() == 1) {
                    // 要入库的还款记录
                    HxPayment recordPayment = null;

                    // 查询是否有垫付后还款记录
                    example.clear();
                    example.createCriteria().andOldChannelFlowEqualTo(channelFlow);
                    List<HxPayment> listAdvance = hxPaymentMapper.selectByExample(example);
                    if (listAdvance != null && listAdvance.size() > 0) {
                        // 如果有，状态是否失败
                        recordPayment = listAdvance.get(0);
                        if (recordPayment.getStatus() == 1) {
                            pageInfo.setCode(ResultInfo.NO_SUCCESS.getCode());
                            pageInfo.setMsg("该还款已成功，请勿再次还款");
                        } else if (recordPayment.getStatus() == 2) {
                            pageInfo.setCode(ResultInfo.NO_SUCCESS.getCode());
                            pageInfo.setMsg("该还款正在还款中，请等待");
                        } else {
                            // 进行还款
                            recordPayment.setStatus(2);
                            recordPayment.setPaymentDate(new Date());
                            hxPaymentMapper.updateByPrimaryKeySelective(recordPayment);
                            pageInfo = requestBankRepaymentAfterAdvance(recordPayment, pageInfo);
                        }

                    } else {
                        // 进行还款
                        recordPayment = new HxPayment();
                        String id = UUID.randomUUID().toString().replace("-", "");
                        recordPayment.setId(id);
                        recordPayment.setBwId(hxPayment.getBwId());
                        recordPayment.setPaymentType(2);
                        recordPayment.setPaymentDate(new Date());
                        recordPayment.setStatus(ResultNumber.TWO.getNumber()); // 还款中
                        recordPayment.setAmount(hxPayment.getAmount());
                        recordPayment.setOldChannelFlow(channelFlow);
                        hxPaymentMapper.insertSelective(recordPayment);
                        pageInfo = requestBankRepaymentAfterAdvance(recordPayment, pageInfo);
                    }

                } else {
                    pageInfo.setResultInfo(ResultInfo.NO_SUCCESS);
                }

            } else {
                pageInfo.setResultInfo(ResultInfo.NO_DATA);
            }

        } else {
            pageInfo.setResultInfo(ResultInfo.PARAM_MISS);
        }

        return pageInfo;
    }

    private PageInfo requestBankRepaymentAfterAdvance(HxPayment recordPayment, PageInfo pageInfo) throws Exception {
        // 装载请求数据
        HxSinglePaymentReqDto req = new HxSinglePaymentReqDto();
        req.setDFFLAG("2");
        req.setRETURNURL(PropertiesUtil.getPropertiesByKey("SINGLE_PAYMENT_RETURN_URL"));
        req.setAMOUNT(recordPayment.getAmount().setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString());
        req.setOLDREQSEQNO(recordPayment.getOldChannelFlow());
        Map<String, String> resultMap = hxPaymentMapper.getAcnoAcnameByBwid(recordPayment.getBwId());
        req.setBWACNAME(resultMap.get("acName"));
        req.setBWACNO(resultMap.get("acNo"));
        req.setLOANNO(resultMap.get("loanNo"));
        PageResponseDto pageResponseDto = hxSinglePayment.requestPayment(req);
        pageResponseDto.setbankUrl(PropertiesUtil.getPropertiesByKey("BANK_POST_URL"));

        // 更新状态
        HxPayment hxPayment = new HxPayment();
        hxPayment.setId(recordPayment.getId());
        hxPayment.setChannelFlow(pageResponseDto.getchannelFlow());
        hxPaymentMapper.updateByPrimaryKeySelective(hxPayment);
        LOGGER.info("还款请求已提交，流水号为：" + pageResponseDto.getchannelFlow());

        pageInfo.setResultInfo(ResultInfo.SUCCESS);
        pageInfo.setObj(pageResponseDto);
        return pageInfo;
    }

}
