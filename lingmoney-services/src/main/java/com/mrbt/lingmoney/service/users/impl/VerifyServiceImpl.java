package com.mrbt.lingmoney.service.users.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.mrbt.lingmoney.bank.utils.exception.ResponseInfoException;
import com.mrbt.lingmoney.commons.cache.RedisGet;
import com.mrbt.lingmoney.commons.cache.RedisSet;
import com.mrbt.lingmoney.commons.exception.PageInfoException;
import com.mrbt.lingmoney.mapper.HxAccountMapper;
import com.mrbt.lingmoney.mapper.HxBidBorrowUnionMapper;
import com.mrbt.lingmoney.mapper.HxCardMapper;
import com.mrbt.lingmoney.mapper.HxPaymentBidBorrowUnionMapper;
import com.mrbt.lingmoney.mapper.LingbaoAddressMapper;
import com.mrbt.lingmoney.mapper.UsersMapper;
import com.mrbt.lingmoney.mapper.UsersPropertiesMapper;
import com.mrbt.lingmoney.model.HxAccount;
import com.mrbt.lingmoney.model.HxAccountExample;
import com.mrbt.lingmoney.model.HxBidBorrowUnionExample;
import com.mrbt.lingmoney.model.HxBidBorrowUnionInfo;
import com.mrbt.lingmoney.model.HxCard;
import com.mrbt.lingmoney.model.HxCardExample;
import com.mrbt.lingmoney.model.HxPaymentBidBorrowUnionExample;
import com.mrbt.lingmoney.model.HxPaymentBidBorrowUnionInfo;
import com.mrbt.lingmoney.model.LingbaoAddress;
import com.mrbt.lingmoney.model.LingbaoAddressExample;
import com.mrbt.lingmoney.model.UsersExample;
import com.mrbt.lingmoney.model.UsersPropertiesExample;
import com.mrbt.lingmoney.service.common.SmsService;
import com.mrbt.lingmoney.service.users.VerifyService;
import com.mrbt.lingmoney.utils.AppCons;
import com.mrbt.lingmoney.utils.CheckPwd;
import com.mrbt.lingmoney.utils.DateUtils;
import com.mrbt.lingmoney.utils.PageInfo;
import com.mrbt.lingmoney.utils.ResultParame.ResultInfo;
import com.mrbt.lingmoney.utils.ResultParame.ResultNumber;
import com.mrbt.lingmoney.utils.Validation;

@Service
public class VerifyServiceImpl implements VerifyService {
	@Autowired
	private SmsService smsService;
	@Autowired
	private UsersMapper usersMapper;
	@Autowired
	private UsersPropertiesMapper usersPropertiesMapper;
	@Autowired
	private LingbaoAddressMapper lingbaoAddressMapper;
	@Autowired
	private HxAccountMapper hxAccountMapper;
	@Autowired
	private HxCardMapper hxCardMapper;
	@Autowired
	private RedisGet redisGet;
	@Autowired
	private RedisSet redisSet;
	@Autowired
	private HxBidBorrowUnionMapper hxBidBorrowUnionMapper;
	@Autowired
	private HxPaymentBidBorrowUnionMapper paymentUnionMapper;

	@Override
	public void verifyAccount(String account) throws Exception {
		verifyEmpty(account, "账号为空");

		if (!Validation.MatchAccount(account) && !Validation.MatchMobile(account)) {
            throw new PageInfoException("账号格式错误", ResultInfo.SUBMIT_PARAMS_ERROR.getCode());
		}
		if (isExsisAccount(account)) {
            throw new PageInfoException("用户账号已存在", ResultInfo.ACCOUNT_EXIST.getCode());
		}
	}

	@Override
	public void verifyTelephone(String telephone, Integer type) throws Exception {
		verifyTelephoneBase(telephone);
		if (isExsisTelephone(telephone)) {
			if (type.equals(0)) {
				// 手机号已存在则异常 适用于添加、修改手机号
                throw new PageInfoException("手机号已存在", ResultInfo.ACCOUNT_EXIST.getCode());
			}
		} else {
			if (type.equals(1)) {
				// 手机号不存在则异常 适用于验证已有手机号
                throw new PageInfoException("手机号不存在", ResultInfo.ACCOUNT_EXIST.getCode());
			}
		}
	}

	@Override
	public void verifyTelephoneAndUid(String telephone, String uid) throws Exception {
		verifyTelephoneBase(telephone);
		verifyEmpty(uid, "用户id为空");
		UsersExample ex = new UsersExample();
		ex.createCriteria().andIdEqualTo(uid).andTelephoneEqualTo(telephone);
		if (usersMapper.countByExample(ex) == 0) {
            throw new PageInfoException("手机号与当前用户不匹配", ResultInfo.ACCOUNT_EXIST.getCode());
		}
	}

	@Override
	public void verifyTelephoneBase(String telephone) throws Exception {
		verifyEmpty(telephone, "手机号为空");

		if (!Validation.MatchMobile(telephone)) {
            throw new PageInfoException("手机号格式错误", ResultInfo.SUBMIT_PARAMS_ERROR.getCode());
		}
	}

	@Override
	public void verifyReferralTel(String referralTel) throws Exception {
		if (!StringUtils.isEmpty(referralTel)) {
			if (referralTel.length() != 8 && referralTel.length() != 6) {
                throw new PageInfoException("推荐码格式错误", ResultInfo.SUBMIT_PARAMS_ERROR.getCode());
			}
			UsersPropertiesExample userEx = new UsersPropertiesExample();
			userEx.createCriteria().andReferralCodeEqualTo(referralTel);
			if (usersPropertiesMapper.countByExample(userEx) == 0) {
                throw new PageInfoException("推荐码不存在", ResultInfo.RECOMMEND_NON_EXIST.getCode());
			}
		}

	}

	@Override
	public void verifyMsgCode(String telephone, String verificationCode) throws Exception {

		verifyEmpty(verificationCode, "验证码为空");

		if (!Validation.ZipCode(verificationCode)) {
            throw new PageInfoException("验证码格式错误", ResultInfo.SUBMIT_PARAMS_ERROR.getCode());
		}

        String dateStr = DateUtils.getDateStr(new Date(), DateUtils.sf);
        String key = dateStr + "_" + telephone;
		// 检测验证码
		if (!smsService.validateCode(key, verificationCode)) {
            throw new PageInfoException("验证码错误或失效", ResultInfo.VERIFICATION_CODE_ERROR.getCode());
		}
	}

	@Override
	public PageInfo verifyMobileAll(String telephone, String referralTel, String verificationCode) throws Exception {
		PageInfo pageInfo = new PageInfo();
		try {
			verifyTelephone(telephone, 0);
			verifyReferralTel(referralTel);
			verifyMsgCode(telephone, verificationCode);
		} catch (PageInfoException e) {
			pageInfo.setCode(e.getCode());
			pageInfo.setMsg(e.getMessage());
			return pageInfo;
		}
        pageInfo.setCode(ResultInfo.SUCCESS.getCode());
		pageInfo.setMsg("验证通过");
		return pageInfo;
	}

	@Override
	public PageInfo verifyWebAll(String account, String telephone, String referralTel, String verificationCode)
			throws Exception {
		PageInfo pageInfo = new PageInfo();
		try {
			verifyAccount(account);
			verifyTelephone(telephone, 0);
			verifyReferralTel(referralTel);
			verifyMsgCode(telephone, verificationCode);
		} catch (PageInfoException e) {
			pageInfo.setCode(e.getCode());
			pageInfo.setMsg(e.getMessage());
			return pageInfo;
		}
        pageInfo.setCode(ResultInfo.SUCCESS.getCode());
		pageInfo.setMsg("验证成功");
		return pageInfo;
	}

	@Override
	public void verifyUserId(Object id) throws Exception {
		verifyEmpty(id, "用户id为空");
		if (!isExsisId(id.toString())) {
            throw new PageInfoException("用户不存在", ResultInfo.RETURN_DATA_EMPTY_DATA.getCode());
		}
	}

	@Override
	public void verifyLoginAccount(String account) throws Exception {
		verifyEmpty(account, "用户账号为空");
		// 验证符合手机号规则或账号规则
		if (!Validation.MatchAccount(account) && !Validation.MatchMobile(account)) {
            throw new PageInfoException("用户账号格式错误", ResultInfo.SUBMIT_PARAMS_ERROR.getCode());
		}
	}

	@Override
	public void verifyName(String val, String item) throws Exception {
		verifyEmpty(val, item + "为空");
		if (!Validation.verifyName(val)) {
            throw new PageInfoException(item + "格式错误", ResultInfo.SUBMIT_PARAMS_ERROR.getCode());
		}
	}

	@Override
	public void verifyIdCard(String idCard) throws Exception {
		verifyEmpty(idCard, "用户身份证号为空");
		if (!Validation.IdCardNo(idCard)) {
            throw new PageInfoException("用户身份证号格式错误", ResultInfo.SUBMIT_PARAMS_ERROR.getCode());
		}
		if (isExsisIdCard(idCard)) {
            throw new PageInfoException("用户身份证号已存在", ResultInfo.SUBMIT_PARAMS_ERROR.getCode());
		}
	}

	@Override
	public LingbaoAddress verifyAddressId(Integer id, String uid) throws Exception {

		verifyEmpty(id, "地址主键为空");

		// 查询地址是否存在
		LingbaoAddressExample ex = new LingbaoAddressExample();
		ex.createCriteria().andIdEqualTo(id).andUIdEqualTo(uid);
		List<LingbaoAddress> res = lingbaoAddressMapper.selectByExample(ex);
		if (StringUtils.isEmpty(res) || res.size() == 0) {
            throw new PageInfoException("当前用户地址不存在", ResultInfo.SUBMIT_PARAMS_ERROR.getCode());
		}
		return res.get(0);
	}

	@Override
	public void verifyAddress(String address) throws Exception {
		verifyEmpty(address, "地址为空");
		if (!Validation.verifyAddress(address)) {
            throw new PageInfoException("地址格式错误", ResultInfo.SUBMIT_PARAMS_ERROR.getCode());
		}
	}

	@Override
	public void verifyEmpty(Object obj, String msg) throws Exception {
		if (StringUtils.isEmpty(obj)) {
            throw new PageInfoException(msg, ResultInfo.SUBMIT_PARAMS_ERROR.getCode());
		}
	}

	@Override
	public void verifyPwd(String pwd) throws Exception {
		if (StringUtils.isEmpty(pwd)) {
            throw new PageInfoException("密码为空", ResultInfo.SUBMIT_PARAMS_ERROR.getCode());
		}
		if (!Validation.verifyPwd(pwd)) {
            throw new PageInfoException("请输入6-16位正确密码,不得含有特殊字符", ResultInfo.SUBMIT_PARAMS_ERROR.getCode());
		}
		// 规避弱密码
		Integer level = CheckPwd.GetPwdSecurityLevel(pwd);
		if (level < 2) {
            throw new PageInfoException("请输入6-16位数字、字母和符号任二组合", ResultInfo.SUBMIT_PARAMS_ERROR.getCode());
		}
	}

	@Override
	public void verifyEmail(String email) throws Exception {
		verifyEmpty(email, "邮箱为空");
		if (!Validation.MatchMail(email)) {
            throw new PageInfoException("邮箱格式错误", ResultInfo.SUBMIT_PARAMS_ERROR.getCode());
		}
		UsersPropertiesExample ex = new UsersPropertiesExample();
		ex.createCriteria().andEmailEqualTo(email);
		if (usersPropertiesMapper.countByExample(ex) > 0) {
            throw new PageInfoException("邮箱已存在", ResultInfo.SUBMIT_PARAMS_ERROR.getCode());
		}
	}

	@Override
	public void verifyRedisCode(String key, String targetVal) throws Exception {
		Object code = redisGet.getRedisStringResult(key);
		if (StringUtils.isEmpty(key) || !targetVal.equals(code)) {
            throw new PageInfoException("验证码错误或失效", ResultInfo.VERIFICATION_CODE_ERROR.getCode());
		}
	}

	@Override
	public void verifyStatus(Integer val) throws Exception {
		verifyEmpty(val, "标识项为空");
		if (!val.equals(0) && !val.equals(1)) {
            throw new PageInfoException("标识项格式错误", ResultInfo.VERIFICATION_CODE_ERROR.getCode());
		}
	}

    /**
     * #start 私有验证方法
     * 
     * @param account 账户
     * @return boolean 存在 true  不存在 false
     * @throws Exception 
     *
     */
	private boolean isExsisAccount(String account) throws Exception {
		UsersExample ex = new UsersExample();
		ex.createCriteria().andLoginAccountEqualTo(account);
		if (usersMapper.countByExample(ex) == 0) {
			return false;
		}
		return true;
	}

    /**
     * 是否存在手机号
     * 
     * @param telephone 手机号
     * @return 存在true 不存在false
     * @throws Exception 
     *
     */
	private boolean isExsisTelephone(String telephone) throws Exception {
		UsersExample ex = new UsersExample();
		ex.createCriteria().andTelephoneEqualTo(telephone);
		if (usersMapper.countByExample(ex) == 0) {
			return false;
		}
		return true;
	}

	/**
	 * 验证id[由于验证id的方法调用频繁 做redis缓存]
	 * 
	 * @author YJQ 2017年5月18日
	 * @param id 
	 * @return true-存在
	 * @throws Exception 
	 */
	private boolean isExsisId(String id) throws Exception {
		// 从redis中取
		Object uidObj = redisGet.getRedisStringResult(AppCons.UID_VERIFY + id);
		if (StringUtils.isEmpty(uidObj)) {
			UsersExample ex = new UsersExample();
			ex.createCriteria().andIdEqualTo(id).andTypeNotEqualTo(9);
			if (usersMapper.countByExample(ex) == 0) {
				return false;
			}
		}
		redisSet.setRedisStringResult(AppCons.UID_VERIFY + id, uidObj, 30, TimeUnit.MINUTES);
		return true;
	}

	/**
	 * 
	 * @author YJQ 2017年5月19日
	 * @param idCard 身份证号
	 * @return true-存在
	 * @throws Exception 
	 */
	private boolean isExsisIdCard(String idCard) throws Exception {
		UsersPropertiesExample exsisIdCardEx = new UsersPropertiesExample();
		exsisIdCardEx.createCriteria().andIdCardEqualTo(idCard);
		if (usersPropertiesMapper.countByExample(exsisIdCardEx) == 0) {
			return false;
		}
		return true;
	}
	// #end

	@Override
	public String verifyEmailNoRepeat(String email, String uid) throws Exception {
		if (StringUtils.isEmpty(email)) {
			return email;
		}
		if (email.contains("****")) {
			return null;
		}
		// 验证格式
		if (!Validation.MatchMail(email)) {
            throw new PageInfoException("邮箱格式错误", ResultInfo.SUBMIT_PARAMS_ERROR.getCode());
		}

		// 验证是否有重复
		UsersPropertiesExample ex = new UsersPropertiesExample();
		ex.createCriteria().andEmailEqualTo(email).andUIdNotEqualTo(uid);
		if (usersPropertiesMapper.countByExample(ex) > 0) {
            throw new PageInfoException("邮箱重复", ResultInfo.RE_EMAIL.getCode());
		}
		return email;
	}

	@Override
	public String verifyWechat(String wechat) throws Exception {
		if (StringUtils.isEmpty(wechat)) {
			return wechat;
		}
		if (wechat.contains("****")) {
			return null;
		}
		if (!Validation.MatchWechat(wechat)) {
            throw new PageInfoException("微信号格式错误", ResultInfo.SUBMIT_PARAMS_ERROR.getCode());
		}
		return wechat;
	}

	@Override
	public void verifyNickName(String name) throws Exception {
		if (!StringUtils.isEmpty(name)) {
			if (!Validation.MatchNickName(name)) {
                throw new PageInfoException("昵称格式错误", ResultInfo.SUBMIT_PARAMS_ERROR.getCode());
			}
		}

	}

	@Override
	public void verifyUserInfo(String val, String item) throws Exception {
		if (StringUtils.isEmpty(val)) {
			return;
		}
		if (!Validation.verifyName(val)) {
            throw new PageInfoException(item + "格式错误", ResultInfo.SUBMIT_PARAMS_ERROR.getCode());
		}
	}

	@Override
	public void verifyJob(String val) throws Exception {
		if (StringUtils.isEmpty(val)) {
			return;
		}
		if (!Validation.verifyJob(val)) {
            throw new PageInfoException("从事职业格式错误", ResultInfo.SUBMIT_PARAMS_ERROR.getCode());
		}
	}

	@Override
	public void verifyLoginErrorCount(String key, Integer times) throws Exception {
		Object count = redisGet.getRedisStringResult(key);
		// 若redis中无数据，则表示没有输错过，验证通过
		if (StringUtils.isEmpty(count) || !(count instanceof Integer)) {
			return;
		}
		Integer countInt = (Integer) count;

		// 次数小于界限
		if (countInt < times) {
			return;
		}

		String msg = "";
		if (times.equals(6)) {
			// 算一下时间 现在的时间加上从redis里取出来的
			Long remainSeconds = redisGet.getRestTime(key, TimeUnit.SECONDS);
			Date remainTime = StringUtils.isEmpty(remainSeconds) ? new Date()
					: DateUtils.addSecond(new Date(), remainSeconds.intValue());
			SimpleDateFormat format = new SimpleDateFormat("MM-dd HH:mm");
			msg = "您的账号已锁定，请于" + format.format(remainTime) + "重试";
		} else {
			msg = "您的IP已受限，请稍后尝试";
		}
        throw new PageInfoException(msg, ResultInfo.RE_EMAIL.getCode());

	}

	@Override
	public Integer verifyAcc(String accId) throws Exception {
		verifyEmpty(accId, "银行账号为空");
		// 判断是否存在 以hxcard表为准
		HxCardExample ex = new HxCardExample();
		ex.createCriteria().andAccIdEqualTo(accId).andStatusNotEqualTo(3);
		List<HxCard> cardInfo = hxCardMapper.selectByExample(ex);
		if (StringUtils.isEmpty(cardInfo) || cardInfo.size() == 0) {
			return null;
		}
		HxCard card = cardInfo.get(0);
		Integer status = card.getStatus();
		switch (status) {
		case 1:
            throw new ResponseInfoException("此银行账号已绑定成功", ResultInfo.ACCOUNT_FORMAT_EXIST.getCode());
			/*
			 * case 2: throw new ResponseInfoException("此银行账号正在请求绑卡中", 3022);
			 */
        default:
            break;
		}
		return status;
	}

	@Override
	public HxCard verifyBankCard(String accId) throws Exception {
		verifyEmpty(accId, "银行账号为空");
		// 判断是否存在 以hxcard表为准
		HxCardExample ex = new HxCardExample();
		ex.setOrderByClause("tied_date desc");
		ex.createCriteria().andAccIdEqualTo(accId);
		List<HxCard> cardInfo = hxCardMapper.selectByExample(ex);
		if (StringUtils.isEmpty(cardInfo) || cardInfo.size() == 0) {
            throw new ResponseInfoException("此用户无绑卡信息", ResultInfo.EMPTY_DATA.getCode());
		}
		return cardInfo.get(0);
	}

	@Override
	public HxAccount verifyAcNo(String acNo) throws Exception {
		verifyEmpty(acNo, "银行账号为空");
		HxAccountExample ex = new HxAccountExample();
		ex.createCriteria().andAcNoEqualTo(acNo);
		List<HxAccount> acc = hxAccountMapper.selectByExample(ex);
		if (StringUtils.isEmpty(acc) || acc.size() == 0) {
            throw new PageInfoException("银行账户未开通", ResultInfo.NOT_HX_ACCOUNT.getCode());
		}
		return acc.get(0);
	}

	@Override
	public HxAccount verifyAcNoByUid(String uid) throws Exception {
		verifyEmpty(uid, "用户id为空");
		HxAccountExample ex = new HxAccountExample();
		ex.createCriteria().andUIdEqualTo(uid).andStatusEqualTo(ResultNumber.ONE.getNumber());
		List<HxAccount> acc = hxAccountMapper.selectByExample(ex);
		if (StringUtils.isEmpty(acc) || acc.size() == 0) {
            throw new PageInfoException("银行账户未开通", ResultInfo.NOT_HX_ACCOUNT.getCode());
		}
		return acc.get(0);
	}
	
	@Override
	public HxAccount verifyAcNoByChannelFlow(String channelFlow) throws Exception {
		verifyEmpty(channelFlow, "流水号为空");
		HxCardExample ex = new HxCardExample();
		ex.setOrderByClause("tied_date desc");
		ex.createCriteria().andSeqNoEqualTo(channelFlow);
		List<HxCard> cardInfo = hxCardMapper.selectByExample(ex);
		if (StringUtils.isEmpty(cardInfo) || cardInfo.size() == 0) {
            throw new ResponseInfoException("此用户无绑卡信息", ResultInfo.EMPTY_DATA.getCode());
		}
		HxAccount acc = hxAccountMapper.selectByPrimaryKey(cardInfo.get(0).getAccId());
		if (StringUtils.isEmpty(acc)) {
            throw new PageInfoException("银行账户未开通", ResultInfo.NOT_HX_ACCOUNT.getCode());
		}
        if (StringUtils.isEmpty(acc.getuId())) {
            throw new PageInfoException("账户信息有误，获取uid失败", ResultInfo.NOT_HX_ACCOUNT.getCode());
		}
		return acc;
	}

	@Override
	public void verifyMoney(String money, String msg) throws Exception {
		if (!Validation.MatchMoney(money)) {
            throw new PageInfoException(msg, ResultInfo.FORMAT_PARAMS_ERROR.getCode());
		}
	}

	@Override
	public HxBidBorrowUnionInfo verifyBorrowInfo(String lomoNo, String bwAcNo, String bwAcName) throws Exception {
		HxBidBorrowUnionExample ex = new HxBidBorrowUnionExample();
		ex.createCriteria().andLoanNoEqualTo(lomoNo).andBwAcnoEqualTo(bwAcNo).andBwAcnameEqualTo(bwAcName);
		List<HxBidBorrowUnionInfo> unionli = hxBidBorrowUnionMapper.selectByExample(ex);
		if (StringUtils.isEmpty(unionli) || unionli.size() == 0) {
            throw new ResponseInfoException("借款信息不存在", ResultInfo.BORROW_NUMBER_NOT_EXIST.getCode());
		}
		return unionli.get(0);

	}
	
	@Override
	public HxBidBorrowUnionInfo verifyBorrowInfo(String lomoNo) throws Exception {
		verifyEmpty(lomoNo, "借款编号为空");
		HxBidBorrowUnionExample ex = new HxBidBorrowUnionExample();
		ex.createCriteria().andLoanNoEqualTo(lomoNo);
		List<HxBidBorrowUnionInfo> unionli = hxBidBorrowUnionMapper.selectByExample(ex);
		if (StringUtils.isEmpty(unionli) || unionli.size() == 0) {
            throw new ResponseInfoException("借款信息不存在", ResultInfo.BORROW_NUMBER_NOT_EXIST.getCode());
		}
		return unionli.get(0);

	}
	
	@Override
	public HxPaymentBidBorrowUnionInfo verifyPaymentBorrowInfo(Object channelFlow) throws Exception {
		verifyEmpty(channelFlow, "流水编号为空");
		HxPaymentBidBorrowUnionExample bwEx = new HxPaymentBidBorrowUnionExample();
		bwEx.createCriteria().andChannelFlowEqualTo(channelFlow.toString());
		List<HxPaymentBidBorrowUnionInfo> paymentInfoLi = paymentUnionMapper.selectByExample(bwEx);
		if (StringUtils.isEmpty(paymentInfoLi) || paymentInfoLi.size() == 0) {
            throw new PageInfoException("此流水号无对应还款信息", ResultInfo.EMPTY_DATA.getCode());
		}
		HxPaymentBidBorrowUnionInfo paymentInfo = paymentInfoLi.get(0);
		switch (paymentInfo.getSubmitDetailStatus()) {
		// 0-未提交 1-已提交 2-提交中
		case 1:
            throw new PageInfoException("流水号：" + channelFlow + "对应的还款操作已完成收益明细提交", ResultInfo.REQUEST_AGAIN.getCode());
		case 2:
            throw new PageInfoException("流水号：" + channelFlow + "对应的还款操作，其收益明细在提交中，请等待",
                    ResultInfo.REQUEST_AGAIN.getCode());
        default:
            break;
		}
		return paymentInfo;
	}

}
