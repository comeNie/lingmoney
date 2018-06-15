package com.mrbt.lingmoney.admin.service.base.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.mrbt.lingmoney.admin.service.base.VerifyService;
import com.mrbt.lingmoney.bank.utils.exception.ResponseInfoException;
import com.mrbt.lingmoney.commons.cache.RedisGet;
import com.mrbt.lingmoney.commons.exception.PageInfoException;
import com.mrbt.lingmoney.mapper.HxAccountMapper;
import com.mrbt.lingmoney.mapper.HxBidBorrowUnionMapper;
import com.mrbt.lingmoney.mapper.HxCardMapper;
import com.mrbt.lingmoney.mapper.HxPaymentBidBorrowUnionMapper;
import com.mrbt.lingmoney.mapper.HxPaymentMapper;
import com.mrbt.lingmoney.mapper.ProductMapper;
import com.mrbt.lingmoney.mapper.UsersMapper;
import com.mrbt.lingmoney.model.HxAccount;
import com.mrbt.lingmoney.model.HxAccountExample;
import com.mrbt.lingmoney.model.HxBidBorrowUnionExample;
import com.mrbt.lingmoney.model.HxBidBorrowUnionInfo;
import com.mrbt.lingmoney.model.HxCard;
import com.mrbt.lingmoney.model.HxCardExample;
import com.mrbt.lingmoney.model.HxPayment;
import com.mrbt.lingmoney.model.HxPaymentBidBorrowUnionExample;
import com.mrbt.lingmoney.model.HxPaymentBidBorrowUnionInfo;
import com.mrbt.lingmoney.model.HxPaymentExample;
import com.mrbt.lingmoney.model.Product;
import com.mrbt.lingmoney.model.UsersExample;
import com.mrbt.lingmoney.utils.AppCons;
import com.mrbt.lingmoney.utils.DateUtils;
import com.mrbt.lingmoney.utils.ResultParame;
import com.mrbt.lingmoney.utils.Validation;

/**
 * 验证服务
 *
 */
@Service
public class VerifyServiceImpl implements VerifyService {
	@Autowired
	private UsersMapper usersMapper;
	@Autowired
	private HxAccountMapper hxAccountMapper;
	@Autowired
	private HxPaymentMapper hxPaymentMapper;
	@Autowired
	private HxCardMapper hxCardMapper;
	@Autowired
	private RedisGet redisGet;
	@Autowired
	private HxBidBorrowUnionMapper hxBidBorrowUnionMapper;
	@Autowired
	private HxPaymentBidBorrowUnionMapper paymentUnionMapper;
	@Autowired
	private ProductMapper productMapper;

	@Override
	public void verifyAccount(String account) throws Exception {
		verifyEmpty(account, "账号为空");

		if (!Validation.MatchAccount(account) && !Validation.MatchMobile(account)) {
			throw new PageInfoException("账号格式错误", ResultParame.ResultInfo.SUBMIT_PARAMS_ERROR.getCode());
		}
		if (isExsisAccount(account)) {
			throw new PageInfoException("用户账号已存在", ResultParame.ResultInfo.ACCOUNT_EXIST.getCode());
		}
	}


	@Override
	public void verifyEmpty(Object obj, String msg) throws Exception {
		if (StringUtils.isEmpty(obj)) {
			throw new PageInfoException(msg, ResultParame.ResultInfo.SUBMIT_PARAMS_ERROR.getCode());
		}
	}


	@Override
	public void verifyRedisCode(String key, String targetVal) throws Exception {
		Object code = redisGet.getRedisStringResult(key);
		if (StringUtils.isEmpty(key) || !targetVal.equals(code)) {
			throw new PageInfoException(ResultParame.ResultInfo.VERIFICATION_CODE_ERROR.getMsg(),
					ResultParame.ResultInfo.VERIFICATION_CODE_ERROR.getCode());
		}
	}

	@Override
	public void verifyStatus(Integer val) throws Exception {
		verifyEmpty(val, "标识项为空");
		if (!val.equals(0) && !val.equals(1)) {
			throw new PageInfoException("标识项格式错误", ResultParame.ResultInfo.VERIFICATION_CODE_ERROR.getCode());
		}
	}

	/**
	 * start 私有验证方法
	 * 
	 * @param account
	 *            account
	 * @return 数据返回
	 * @throws Exception
	 *             Exception
	 */
	private boolean isExsisAccount(String account) throws Exception {
		UsersExample ex = new UsersExample();
		ex.createCriteria().andLoginAccountEqualTo(account);
		if (usersMapper.countByExample(ex) == 0) {
			return false;
		}
		return true;
	}

	// #end


	@Override
	public void verifyAcc(String accId) throws Exception {
		verifyEmpty(accId, "银行账号为空");
		// 判断是否存在 以hxcard表为准
		HxCardExample ex = new HxCardExample();
		ex.createCriteria().andAccIdEqualTo(accId).andStatusNotEqualTo(ResultParame.ResultNumber.THREE.getNumber());
		List<HxCard> cardInfo = hxCardMapper.selectByExample(ex);
		if (StringUtils.isEmpty(cardInfo) || cardInfo.size() == 0) {
			return;
		}
		HxCard card = cardInfo.get(0);
		Integer status = card.getStatus();
		switch (status) {
		/*
		 * case 0: throw new ResponseInfoException("此银行账号正在请求绑卡中，请等待", 3022);
		 */
		case 1:
			throw new ResponseInfoException("此银行账号已绑定过，请更换", ResultParame.ResultInfo.ACCOUNT_FORMAT_EXIST.getCode());
			/*
			 * case 2: throw new ResponseInfoException("此银行账号正在处理中，请等待", 3022);
			 */
		default:
		}

	}

	@Override
	public HxAccount verifyAcNo(String acNo) throws Exception {
		verifyEmpty(acNo, "银行账号为空");
		HxAccountExample ex = new HxAccountExample();
		ex.createCriteria().andAcNoEqualTo(acNo);
		List<HxAccount> acc = hxAccountMapper.selectByExample(ex);
		if (StringUtils.isEmpty(acc) || acc.size() == 0) {
			throw new PageInfoException(ResultParame.ResultInfo.NOT_HX_ACCOUNT.getMsg(),
					ResultParame.ResultInfo.NOT_HX_ACCOUNT.getCode());
		}
		return acc.get(0);
	}

	@Override
	public void verifyMoney(String money, String msg) throws Exception {
		if (!Validation.MatchMoney(money)) {
			throw new PageInfoException(msg, ResultParame.ResultInfo.FORMAT_PARAMS_ERROR.getCode());
		}
	}

	@Override
	public HxBidBorrowUnionInfo verifyBorrowInfo(String lomoNo) throws Exception {
		verifyEmpty(lomoNo, "借款编号为空");
		HxBidBorrowUnionExample ex = new HxBidBorrowUnionExample();
		ex.createCriteria().andLoanNoEqualTo(lomoNo);
		List<HxBidBorrowUnionInfo> unionli = hxBidBorrowUnionMapper.selectByExample(ex);
		if (StringUtils.isEmpty(unionli) || unionli.size() == 0) {
			throw new ResponseInfoException("借款信息不存在", ResultParame.ResultInfo.BORROW_NUMBER_NOT_EXIST.getCode());
		}
		return unionli.get(0);

	}

	@Override
	public HxPaymentBidBorrowUnionInfo verifyPaymentStatus(String loanNo) throws Exception {
		HxPaymentBidBorrowUnionExample bwEx = new HxPaymentBidBorrowUnionExample();
		bwEx.createCriteria().andLoanNoEqualTo(loanNo).andStatusNotEqualTo(ResultParame.ResultNumber.THREE.getNumber())
				.andStatusNotEqualTo(ResultParame.ResultNumber.FOUR.getNumber());
		List<HxPaymentBidBorrowUnionInfo> paymentInfoLi = paymentUnionMapper.selectByExample(bwEx);
		if (StringUtils.isEmpty(paymentInfoLi) || paymentInfoLi.size() == 0) {
			// 无还款信息
			return null;
		}
		HxPaymentBidBorrowUnionInfo paymentInfo = paymentInfoLi.get(0);
		if (StringUtils.isEmpty(paymentInfo.getChannelFlow())) {
			// 上次并未请求成功
			hxPaymentMapper.deleteByPrimaryKey(paymentInfo.getId());
			return null;
		}

		switch (paymentInfo.getStatus()) {
		// 1-还款成功 2- 还款中 3-还款失败 4-请求超时 3.4时可继续请求
		case 1:
			throw new PageInfoException("借款编号：" + loanNo + "对应的还款操作已还款成功",
					ResultParame.ResultInfo.REQUEST_AGAIN.getCode());
		case 2:
			// 判断是否超时
			if (DateUtils.dateDiffMins(paymentInfo.getPaymentDate(), new Date()) >= ResultParame.ResultNumber.TWENTY
					.getNumber()) {
				HxPayment payment = new HxPayment();
				payment.setId(paymentInfo.getId());
				payment.setStatus(ResultParame.ResultNumber.FOUR.getNumber());
				hxPaymentMapper.updateByPrimaryKeySelective(payment);

				paymentInfo.setStatus(ResultParame.ResultNumber.FOUR.getNumber());
				return paymentInfo;
			}
			throw new PageInfoException("借款编号：" + loanNo + "对应的还款操作在处理中，请等待一段时间再次查询",
					ResultParame.ResultInfo.REQUEST_AGAIN.getCode());
		default:
		}

		return paymentInfo;
	}

	@Override
	public HxPaymentBidBorrowUnionInfo verifyPaymentBorrowInfo(Object channelFlow) throws Exception {
		verifyEmpty(channelFlow, "流水编号为空");
		HxPaymentBidBorrowUnionExample bwEx = new HxPaymentBidBorrowUnionExample();
		bwEx.createCriteria().andChannelFlowEqualTo(channelFlow.toString());
		List<HxPaymentBidBorrowUnionInfo> paymentInfoLi = paymentUnionMapper.selectByExample(bwEx);
		if (StringUtils.isEmpty(paymentInfoLi) || paymentInfoLi.size() == 0) {
			throw new PageInfoException("此流水号无对应还款信息", ResultParame.ResultInfo.EMPTY_DATA.getCode());
		}
		HxPaymentBidBorrowUnionInfo paymentInfo = paymentInfoLi.get(0);
		switch (paymentInfo.getSubmitDetailStatus()) {
        // 0-未提交 1-已提交 2-提交中 3-提交成功
        case 0:
            HxPaymentExample hxPaymentExmp = new HxPaymentExample();
            hxPaymentExmp.createCriteria().andChannelFlowEqualTo(channelFlow.toString());
            HxPayment hxPaymentRecord = new HxPayment();
			hxPaymentRecord.setSubmitDetailStatus(ResultParame.ResultNumber.TWO.getNumber());
            hxPaymentMapper.updateByExampleSelective(hxPaymentRecord, hxPaymentExmp);
            break;
		case 3:
			throw new PageInfoException("流水号：" + channelFlow + "对应的还款操作已提交成功",
					ResultParame.ResultInfo.REQUEST_AGAIN.getCode());
		default:
		}
		return paymentInfo;
	}

	@Override
	public Product verifyProductStatus(Integer pId) throws Exception {
		// 0 产品初始化，1:项目筹集期，2项目运行中/已放款, 3 项目汇款中/项目到期， 4项目已结束， 5项目已作废， 
		// 6 筹集金额未达标，7筹集金额已达标/等待申请放款, 8 流标申请中 ， 9已流标 ， 10 放款申请中 ， 11 已确认自动还款 , 
		// 12 还款申请已提交  , 13 还款成功 ， 14 还款收益明细已提交， 15 还款收益明细提交成功
		
		Product product = productMapper.selectByPrimaryKey(pId);
		if (StringUtils.isEmpty(product)) {
			throw new ResponseInfoException("无法查询到产品信息，请检查数据", ResultParame.ResultInfo.DO_FAIL.getCode());
		}

		int status = product.getStatus().intValue();

		if (status == AppCons.PRODUCT_STATUS_REPAYMENT_APPLY) {
			throw new ResponseInfoException("标的仍在还款操作中，请在还款记录界面查看还款结果", ResultParame.ResultInfo.DO_FAIL.getCode());
		}

		if (status == AppCons.PRODUCT_STATUS_REPAYMENT_SUCCESS || status == AppCons.PRODUCT_STATUS_INCOME_DETAIL_APPLY
				|| status == AppCons.PRODUCT_STATUS_INCOME_DETAIL_SUCCESS) {
			throw new ResponseInfoException("标的已还款", ResultParame.ResultInfo.DO_FAIL.getCode());
		}
		
		if (status == AppCons.PRODUCT_STATUS_FINISH) {
			throw new ResponseInfoException("项目已结束", ResultParame.ResultInfo.DO_FAIL.getCode());
		}
		
		if (status == AppCons.PRODUCT_STATUS_ABANDON) {
			throw new ResponseInfoException("项目已作废", ResultParame.ResultInfo.DO_FAIL.getCode());
		}
		
		if (status != AppCons.PRODUCT_STATUS_RUNNING && status != AppCons.PRODUCT_STATUS_REPAYMENT
				&& status != AppCons.PRODUCT_STATUS_CONFIRM_AUTO_REPAYMENT) {
			throw new ResponseInfoException("标的未放款或标的未到期", ResultParame.ResultInfo.DO_FAIL.getCode());
		}

		return product;
	}
	
}
