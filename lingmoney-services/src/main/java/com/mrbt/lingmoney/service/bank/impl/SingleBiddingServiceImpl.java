package com.mrbt.lingmoney.service.bank.impl;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.dom4j.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.mrbt.lingmoney.bank.HxBaseData;
import com.mrbt.lingmoney.bank.bid.HxSingleBidding;
import com.mrbt.lingmoney.commons.exception.PageInfoException;
import com.mrbt.lingmoney.mapper.ActivityRecordMapper;
import com.mrbt.lingmoney.mapper.HxAccountMapper;
import com.mrbt.lingmoney.mapper.HxBiddingMapper;
import com.mrbt.lingmoney.mapper.ProductMapper;
import com.mrbt.lingmoney.mapper.TradingFixInfoMapper;
import com.mrbt.lingmoney.mapper.TradingMapper;
import com.mrbt.lingmoney.mapper.UsersAccountMapper;
import com.mrbt.lingmoney.mapper.UsersPropertiesMapper;
import com.mrbt.lingmoney.model.ActivityRecord;
import com.mrbt.lingmoney.model.ActivityRecordExample;
import com.mrbt.lingmoney.model.HxAccount;
import com.mrbt.lingmoney.model.HxAccountExample;
import com.mrbt.lingmoney.model.HxBidding;
import com.mrbt.lingmoney.model.HxBiddingExample;
import com.mrbt.lingmoney.model.Product;
import com.mrbt.lingmoney.model.Trading;
import com.mrbt.lingmoney.model.TradingFixInfo;
import com.mrbt.lingmoney.model.UsersAccount;
import com.mrbt.lingmoney.model.UsersAccountExample;
import com.mrbt.lingmoney.model.UsersProperties;
import com.mrbt.lingmoney.model.UsersPropertiesExample;
import com.mrbt.lingmoney.service.bank.SingleBiddingService;
import com.mrbt.lingmoney.service.trading.TradingFixRuleBuyService;
import com.mrbt.lingmoney.service.trading.impl.TradingFixRuleBuyServiceImpl.CallbackType;
import com.mrbt.lingmoney.service.users.VerifyService;
import com.mrbt.lingmoney.utils.AppCons;
import com.mrbt.lingmoney.utils.PageInfo;
import com.mrbt.lingmoney.utils.ProductUtils;
import com.mrbt.lingmoney.utils.PropertiesUtil;
import com.mrbt.lingmoney.utils.ResultParame.ResultInfo;

/**
 * 单笔投标
 */
@Service
public class SingleBiddingServiceImpl implements SingleBiddingService {

	@Autowired
	private HxSingleBidding hxSingleBidding;
	@Autowired
	private HxBiddingMapper hxBiddingMapper;
	@Autowired
	private HxAccountMapper hxAccountMapper;
	@Autowired
	private VerifyService verifyService;
	@Autowired
	private UsersPropertiesMapper usersPropertiesMapper;
	@Autowired
	private TradingMapper tradingMapper;
	@Autowired
	private ProductMapper productMapper;
	@Autowired
	private TradingFixInfoMapper tradingFixInfoMapper;
	@Autowired
	private ActivityRecordMapper activityRecordMapper;
	@Autowired
	private UsersAccountMapper usersAccountMapper;
	@Autowired
	private TradingFixRuleBuyService tradingFixRuleBuyService;

	private static final Logger LOGGER = LogManager.getLogger(SingleBiddingServiceImpl.class);

	private static final String LOGHEARD = "\nHxSingleBidding_";

	private static final String POSTURL = PropertiesUtil.getPropertiesByKey("BANK_POST_URL");

	private static final String SINGLE_BIDDING_RETURN_URL = PropertiesUtil
			.getPropertiesByKey("SINGLE_BIDDING_RETURN_URL");

	@Override
	public PageInfo requestSingleBidding(int clientType, String appId, Integer tId, String uId) throws Exception {
		// 生成日志头
		String logGroup = LOGHEARD + System.currentTimeMillis() + "_";

		LOGGER.info("投标请求：" + logGroup + "clientType=" + clientType + ",appId=" + appId + ",tId=" + tId + ",uId=" + uId);

		PageInfo pageInfo = new PageInfo();

		// 1、验证用户ID
		try {
			verifyService.verifyUserId(uId);
		} catch (PageInfoException e) {
			pageInfo.setCode(e.getCode());
			pageInfo.setMsg(e.getMessage());
			return pageInfo;
		}

		// 2、判断用户是否存在
		UsersPropertiesExample propertiesExample = new UsersPropertiesExample();
		propertiesExample.createCriteria().andUIdEqualTo(uId);
		List<UsersProperties> list = usersPropertiesMapper.selectByExample(propertiesExample);
		if (StringUtils.isEmpty(list) || list.size() == 0) {
			pageInfo.setMsg("用户不存在");
            pageInfo.setCode(ResultInfo.ACCOUNT_NUMBER_NON_EXIST.getCode());
			return pageInfo;
		}
		UsersProperties usersProperties = list.get(0);

		// 3、是否开通华兴E账户
		if (usersProperties.getCertification() == 0) {
			pageInfo.setMsg("尚未开通华兴E账户");
            pageInfo.setCode(ResultInfo.NOT_HX_ACCOUNT.getCode());
			return pageInfo;
		}

		// 4、已开通未激活绑卡
		if (usersProperties.getBank() == 0) {
			pageInfo.setMsg("尚未激活华兴E账户");
            pageInfo.setCode(ResultInfo.HX_ACCOUNT_NOT_ACTIVATION.getCode());
			return pageInfo;
		}

		// 5、从数据库中取出华兴E账户信息
		HxAccountExample example = new HxAccountExample();
		example.createCriteria().andUIdEqualTo(uId).andStatusEqualTo(1);
		List<HxAccount> hxList = hxAccountMapper.selectByExample(example);
		if (StringUtils.isEmpty(hxList) || hxList.size() == 0) {
			pageInfo.setMsg("尚未开通华兴E账户");
            pageInfo.setCode(ResultInfo.NOT_HX_ACCOUNT.getCode());
			return pageInfo;
		}

		HxAccount hxAccount = hxList.get(0);
		// E账号户名
		String acName = hxAccount.getAcName();
		// 银行账号
		String acNo = hxAccount.getAcNo();

		// 6、根据tId查询出对应的购买金额和借款编号
		Trading trading = tradingMapper.selectByPrimaryKey(tId);
		if (StringUtils.isEmpty(trading)) {
			pageInfo.setMsg("交易不存在");
            pageInfo.setCode(ResultInfo.PROJECT_NOT_EXIST.getCode());
			return pageInfo;
		}
		TradingFixInfo tradingFixInfo = tradingFixInfoMapper.selectFixInfoByTid(tId);
		if (StringUtils.isEmpty(tradingFixInfo)) {
			pageInfo.setMsg("交易不存在");
            pageInfo.setCode(ResultInfo.PROJECT_NOT_EXIST.getCode());
			return pageInfo;
		}

		// 7、订单状态必须为0待支付
		Integer status = trading.getStatus();
		if (StringUtils.isEmpty(status) || status != 0) {
			pageInfo.setMsg("不允许重复提交");
            pageInfo.setCode(ResultInfo.ORDER_NO_AGAIN_SUBMIT.getCode());
			return pageInfo;
		}

		// 判断产品状态以及距离产品结束时间
		Product product = productMapper.selectByPrimaryKey(trading.getpId());
		if (product.getStatus() != AppCons.PRODUCT_STATUS_READY
				|| product.getEdDt().getTime() < System.currentTimeMillis()) {
			pageInfo.setMsg("超时未支付,该订单已失效");
            pageInfo.setCode(ResultInfo.ORDER_INVALID.getCode());
			return pageInfo;
		}

		// 订单超时未支付，则不允许支付
		// 订单剩余支付时间
		String orderRemainPayTime = PropertiesUtil.getPropertiesByKey("orderRemainPayTime");
		// 支付剩余时间（毫秒）购买时间+15分钟-当前时间
		long remainDt = trading.getBuyDt().getTime() + Integer.valueOf(orderRemainPayTime) * 60 * 1000
				- System.currentTimeMillis();

		if (remainDt <= 0) {
			pageInfo.setMsg("超时未支付,该订单已失效");
            pageInfo.setCode(ResultInfo.ORDER_INVALID.getCode());
			return pageInfo;
		}

		HxBiddingExample hxBiddingExample = new HxBiddingExample();
		hxBiddingExample.createCriteria().andPIdEqualTo(trading.getpId());
		List<HxBidding> listHxBidding = hxBiddingMapper.selectByExample(hxBiddingExample);
		if (StringUtils.isEmpty(listHxBidding) || listHxBidding.size() == 0) {
			pageInfo.setMsg("标的不存在");
            pageInfo.setCode(ResultInfo.BIAODI_NOT_EXIST.getCode());
			return pageInfo;
		}
		HxBidding hxBidding = listHxBidding.get(0);
		// 借款编号
		String loanNo = hxBidding.getLoanNo();
		if (StringUtils.isEmpty(loanNo)) {
			pageInfo.setMsg("借款编号不存在");
            pageInfo.setCode(ResultInfo.BORROW_NUMBER_NOT_EXIST.getCode());
			return pageInfo;
		}

		// 7、生成报文
        String amount = trading.getFinancialMoney().toString(); // 购买金额
        String remark = "用户" + uId + "，购买产品" + trading.getpCode() + "，金额为" + amount; // 备注
		Map<String, String> contentMap = hxSingleBidding.requestSingleBidding(clientType, appId, loanNo, acNo, acName,
				amount, remark, uId, SINGLE_BIDDING_RETURN_URL, logGroup);

		if (StringUtils.isEmpty(contentMap) || contentMap.size() == 0) {
			pageInfo.setMsg("投标请求失败");
            pageInfo.setCode(ResultInfo.NO_SUCCESS.getCode());
			return pageInfo;
		}
		// 订单号，将交易表和交易信息表的订单号修改为报文交易流水号，保证统一，将订单状态改为支付中12
		String bizCode = contentMap.get("channelFlow");
		if (StringUtils.isEmpty(bizCode)) {
			pageInfo.setMsg("交易流水号不存在");
            pageInfo.setCode(ResultInfo.TRADING_NUMBER_NOT_EXIST.getCode());
			return pageInfo;
		}
		
		// 处理中
        tradingFixRuleBuyService.handleBuyProduct(tId, bizCode, uId, CallbackType.trading);

		contentMap.put("bankUrl", POSTURL);
        pageInfo.setCode(ResultInfo.SUCCESS.getCode());
		pageInfo.setMsg("生成报文成功");
		pageInfo.setObj(contentMap);
		return pageInfo;
	}

	@Override
	public String singleBiddingCallBack(Document document) throws Exception {

		// 生成日志头
		String logGroup = LOGHEARD + System.currentTimeMillis() + "_";

		// 数据验签，返回xml文档
		if (!StringUtils.isEmpty(document)) {
			LOGGER.info(logGroup + "应答报文\t" + document.asXML());
			// 判断报文status
			Map<String, Object> resMap = HxBaseData.xmlToMap(document);
			if (resMap != null) {

                String transCode = resMap.get("TRANSCODE").toString(), channelFlow = resMap.get("channelFlow")
                        .toString(), status = "0", errorCode = "0", errorMsg = "受理成功", returncode = "000000", returnmsg = "交易成功", oldreqseqno = resMap
                        .get("OLDREQSEQNO").toString();

				// 通过交易流水号查询订单id和订单信息表id和用户id
                Trading trading = tradingMapper.selectByBizCode(oldreqseqno);
                TradingFixInfo tradingFixInfo = tradingFixInfoMapper.selectByBizCode(oldreqseqno);

				if (!StringUtils.isEmpty(trading) && !StringUtils.isEmpty(tradingFixInfo)) {
						
					int tId = trading.getId();
					tradingFixInfo.getId();
					// 支付成功
                    boolean b = tradingFixRuleBuyService.handleBuyProduct(tId, oldreqseqno, trading.getuId(),
							CallbackType.Frozen);
					if (b) {
                        buyWeiyingbaoGetLingbao(trading.getuId(), tId);
					} else {
						status = "1";
						errorCode = "5006";
						errorMsg = "交易失败";
					}
				} else {
					status = "1";
					errorCode = "5003";
					errorMsg = "交易不存在";
				}
				// 生成应答返回报文
				String retMsg = hxSingleBidding.createSingleBiddingAsyncMsg(transCode, channelFlow, status, errorCode,
                        errorMsg, returncode, returnmsg, oldreqseqno, logGroup);
				if (!StringUtils.isEmpty(retMsg)) {
					return retMsg;
				}
			}
		}

		return "报文不存在";

	}

	/**
	 * 购买稳赢宝送领宝
	 *
	 * @Description
	 * @param uid
	 *            用户ID
	 * @param tid
	 *            交易id
	 * @param infoid
	 *            交易信息id
	 */
	@Override
    public void buyWeiyingbaoGetLingbao(String uid, int tid) {
		// 支付成功，如果是购买的额稳赢宝，赠送领宝：理财额÷365天×理财天数÷50＝所赠送领宝
		Trading trading = tradingMapper.selectByPrimaryKey(tid);
		String wenyingbaocode = ProductUtils.getContent("wenyingbao_code");
		String chedaibaocode = ProductUtils.getContent("chedaibao_code");
        String huinongdaicode = ProductUtils.getContent("huinongdai_code");
        String qiyebaocode = ProductUtils.getContent("qiyebao_code");
		int takeheartcode = ProductUtils.getIntContent("takeHeart_pID");
		// 如果购买的是随心取直接返回
		if (trading.getpId().intValue() == takeheartcode) {
            return;
		}

		// 获取产品代码前十位和属性文件中的产品代码对比
		String productCode = trading.getpCode().substring(0, 10);
        String content = "理财送领宝";
		if (null != wenyingbaocode && wenyingbaocode.equals(productCode)) {
			content = "购买稳赢宝送领宝";
		} else if (null != chedaibaocode && chedaibaocode.equals(productCode)) {
			content = "购买车贷宝送领宝";
        } else if (null != huinongdaicode && huinongdaicode.equals(productCode)) {
            content = "购买惠农贷送领宝";
        } else if (null != qiyebaocode && qiyebaocode.equals(productCode)) {
            content = "购买企业宝送领宝";
		}
		Product product = productMapper.selectByPrimaryKey(trading.getpId());
        // 计算领宝  = 理财金额 * 理财天数 / 365 / 50
		int lingbao = (trading.getFinancialMoney().multiply(new BigDecimal(product.getfTime())))
				.divide(new BigDecimal(365 * 50), RoundingMode.HALF_UP).intValue();

		// 无领宝 不记录
		if (lingbao <= 0) {
			return;
		}

		ActivityRecordExample example = new ActivityRecordExample();
		example.createCriteria().andUIdEqualTo(uid).andTIdEqualTo(trading.getId()).andNameEqualTo("理财")
				.andUseDateEqualTo(new Date()).andAmountEqualTo(lingbao);
		int i = activityRecordMapper.countByExample(example);

        if (i < 1) { // 避免重复赠送
			ActivityRecord ar = new ActivityRecord();
			ar.setAmount(lingbao);
			ar.setContent(content);
			ar.setName("理财");
			ar.setStatus(1);
			ar.settId(trading.getId());
			ar.setType(0);
			ar.setuId(uid);
			ar.setUseDate(new Date());
			// 向领宝记录表中插入记录
			activityRecordMapper.insertSelective(ar);
			UsersAccountExample accountExample = new UsersAccountExample();
			accountExample.createCriteria().andUIdEqualTo(uid);
			UsersAccount ua = usersAccountMapper.selectByExample(accountExample).get(0);
			ua.setCountLingbao(lingbao + ua.getCountLingbao());
			// 更新用户账户领宝数量
			usersAccountMapper.updateByPrimaryKeySelective(ua);
			// 发送短信

		}
	}

	@Override
	public boolean singleBiddingCallBack(Integer tId, CallbackType flag) throws Exception {
		Trading trading = tradingMapper.selectByPrimaryKey(tId);
		String oldreqseqno = trading.getBizCode();
		TradingFixInfo tradingFixInfo = tradingFixInfoMapper.selectByBizCode(oldreqseqno);
		tradingFixInfo.getId();
		String uId = trading.getuId();
        return tradingFixRuleBuyService.handleBuyProduct(tId, oldreqseqno, uId, flag);
	}

    @Override
    public PageInfo requestSingleBiddingVersionOne(int clientType, String appId, Integer tId, String uId)
            throws Exception {
        // 生成日志头
        String logGroup = LOGHEARD + System.currentTimeMillis() + "_";

        LOGGER.info("投标请求（新版）：" + logGroup + "clientType=" + clientType + ",appId=" + appId + ",tId=" + tId + ",uId="
                + uId);

        PageInfo pageInfo = new PageInfo();

        // 1、验证用户ID
        try {
            verifyService.verifyUserId(uId);
        } catch (PageInfoException e) {
            pageInfo.setCode(e.getCode());
            pageInfo.setMsg(e.getMessage());
            return pageInfo;
        }

        // 2、判断用户是否存在
        UsersPropertiesExample propertiesExample = new UsersPropertiesExample();
        propertiesExample.createCriteria().andUIdEqualTo(uId);
        List<UsersProperties> list = usersPropertiesMapper.selectByExample(propertiesExample);
        if (StringUtils.isEmpty(list) || list.size() == 0) {
            pageInfo.setMsg("用户不存在");
            pageInfo.setCode(ResultInfo.ACCOUNT_NUMBER_NON_EXIST.getCode());
            return pageInfo;
        }
        UsersProperties usersProperties = list.get(0);

        // 3、是否开通华兴E账户
        if (usersProperties.getCertification() == 0) {
            pageInfo.setMsg("尚未开通华兴E账户");
            pageInfo.setCode(ResultInfo.NOT_HX_ACCOUNT.getCode());
            return pageInfo;
        }

        // 4、已开通未激活绑卡
        if (usersProperties.getBank() == 0) {
            pageInfo.setMsg("尚未激活华兴E账户");
            pageInfo.setCode(ResultInfo.HX_ACCOUNT_NOT_ACTIVATION.getCode());
            return pageInfo;
        }

        // 5、从数据库中取出华兴E账户信息
        HxAccountExample example = new HxAccountExample();
        example.createCriteria().andUIdEqualTo(uId).andStatusEqualTo(1);
        List<HxAccount> hxList = hxAccountMapper.selectByExample(example);
        if (StringUtils.isEmpty(hxList) || hxList.size() == 0) {
            pageInfo.setMsg("尚未开通华兴E账户");
            pageInfo.setCode(ResultInfo.NOT_HX_ACCOUNT.getCode());
            return pageInfo;
        }

        HxAccount hxAccount = hxList.get(0);
        // E账号户名
        String acName = hxAccount.getAcName();
        // 银行账号
        String acNo = hxAccount.getAcNo();

        // 6、根据tId查询出对应的购买金额和借款编号
        Trading trading = tradingMapper.selectByPrimaryKey(tId);
        if (StringUtils.isEmpty(trading)) {
            pageInfo.setMsg("交易不存在");
            pageInfo.setCode(ResultInfo.PROJECT_NOT_EXIST.getCode());
            return pageInfo;
        }
        TradingFixInfo tradingFixInfo = tradingFixInfoMapper.selectFixInfoByTid(tId);
        if (StringUtils.isEmpty(tradingFixInfo)) {
            pageInfo.setMsg("交易不存在");
            pageInfo.setCode(ResultInfo.PROJECT_NOT_EXIST.getCode());
            return pageInfo;
        }

        // 7、订单状态必须为0待支付
        Integer status = trading.getStatus();
        if (StringUtils.isEmpty(status) || status != 0) {
            pageInfo.setMsg("不允许重复提交");
            pageInfo.setCode(ResultInfo.ORDER_NO_AGAIN_SUBMIT.getCode());
            return pageInfo;
        }

        // 8、验证用户余额
        UsersAccount usersAccount = usersAccountMapper.selectByUid(uId);
        if (usersAccount.getBalance().compareTo(trading.getFinancialMoney()) == -1) {
            pageInfo.setCode(ResultInfo.NO_SUCCESS.getCode());
            pageInfo.setMsg("余额不足");
            return pageInfo;
        }

        // 9、判断产品状态以及距离产品结束时间
        Product product = productMapper.selectByPrimaryKey(trading.getpId());
        if (product.getStatus() != AppCons.PRODUCT_STATUS_READY
                || product.getEdDt().getTime() < System.currentTimeMillis()) {
            pageInfo.setMsg("超时未支付,该订单已失效");
            pageInfo.setCode(ResultInfo.ORDER_INVALID.getCode());
            return pageInfo;
        }

        // 订单超时未支付，则不允许支付
        // 订单剩余支付时间
        String orderRemainPayTime = PropertiesUtil.getPropertiesByKey("orderRemainPayTime");
        // 支付剩余时间（毫秒）购买时间+15分钟-当前时间
        long remainDt = trading.getBuyDt().getTime() + Integer.valueOf(orderRemainPayTime) * 60 * 1000
                - System.currentTimeMillis();

        if (remainDt <= 0) {
            pageInfo.setMsg("超时未支付,该订单已失效");
            pageInfo.setCode(ResultInfo.ORDER_INVALID.getCode());
            return pageInfo;
        }

        HxBiddingExample hxBiddingExample = new HxBiddingExample();
        hxBiddingExample.createCriteria().andPIdEqualTo(trading.getpId());
        List<HxBidding> listHxBidding = hxBiddingMapper.selectByExample(hxBiddingExample);
        if (StringUtils.isEmpty(listHxBidding) || listHxBidding.size() == 0) {
            pageInfo.setMsg("标的不存在");
            pageInfo.setCode(ResultInfo.BIAODI_NOT_EXIST.getCode());
            return pageInfo;
        }
        HxBidding hxBidding = listHxBidding.get(0);
        // 借款编号
        String loanNo = hxBidding.getLoanNo();
        if (StringUtils.isEmpty(loanNo)) {
            pageInfo.setMsg("借款编号不存在");
            pageInfo.setCode(ResultInfo.BORROW_NUMBER_NOT_EXIST.getCode());
            return pageInfo;
        }

        // 10、生成报文
        String amount = trading.getFinancialMoney().toString(); // 购买金额
        String remark = "用户" + uId + "，购买产品" + trading.getpCode() + "，金额为" + amount; // 备注
        Map<String, String> contentMap = hxSingleBidding.requestSingleBidding(clientType, appId, loanNo, acNo, acName,
                amount, remark, uId, SINGLE_BIDDING_RETURN_URL, logGroup);

        if (StringUtils.isEmpty(contentMap) || contentMap.size() == 0) {
            pageInfo.setMsg("投标请求失败");
            pageInfo.setCode(ResultInfo.NO_SUCCESS.getCode());
            return pageInfo;
        }
        // 订单号，将交易表和交易信息表的订单号修改为报文交易流水号，保证统一，将订单状态改为支付中12
        String bizCode = contentMap.get("channelFlow");
        if (StringUtils.isEmpty(bizCode)) {
            pageInfo.setMsg("交易流水号不存在");
            pageInfo.setCode(ResultInfo.TRADING_NUMBER_NOT_EXIST.getCode());
            return pageInfo;
        }

        // 处理中
        tradingFixRuleBuyService.handleBuyProductVersionOne(tId, bizCode, uId, CallbackType.trading);

        contentMap.put("bankUrl", POSTURL);
        pageInfo.setCode(ResultInfo.SUCCESS.getCode());
        pageInfo.setMsg("生成报文成功");
        pageInfo.setObj(contentMap);
        return pageInfo;
    }

}
