package com.mrbt.lingmoney.service.bank.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.dom4j.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.mrbt.lingmoney.bank.HxBaseData;
import com.mrbt.lingmoney.bank.enterprise.HxEnterpriseBindCard;
import com.mrbt.lingmoney.bank.tiedcard.HxChangePersonalBindCard;
import com.mrbt.lingmoney.bank.tiedcard.HxTiedCard;
import com.mrbt.lingmoney.bank.tiedcard.dto.HxTiedCardReqDto;
import com.mrbt.lingmoney.bank.utils.dto.PageResponseDto;
import com.mrbt.lingmoney.bank.utils.dto.ResponseBodyDto;
import com.mrbt.lingmoney.bank.utils.exception.ResponseInfoException;
import com.mrbt.lingmoney.commons.cache.RedisGet;
import com.mrbt.lingmoney.commons.cache.RedisSet;
import com.mrbt.lingmoney.commons.exception.PageInfoException;
import com.mrbt.lingmoney.mapper.ActivityRecordMapper;
import com.mrbt.lingmoney.mapper.HxAccountMapper;
import com.mrbt.lingmoney.mapper.HxCardMapper;
import com.mrbt.lingmoney.mapper.HxRedPacketMapper;
import com.mrbt.lingmoney.mapper.SupportBankMapper;
import com.mrbt.lingmoney.mapper.UsersAccountMapper;
import com.mrbt.lingmoney.mapper.UsersPropertiesMapper;
import com.mrbt.lingmoney.mapper.UsersRedPacketMapper;
import com.mrbt.lingmoney.model.ActivityRecord;
import com.mrbt.lingmoney.model.HxAccount;
import com.mrbt.lingmoney.model.HxAccountExample;
import com.mrbt.lingmoney.model.HxCard;
import com.mrbt.lingmoney.model.HxCardExample;
import com.mrbt.lingmoney.model.SupportBank;
import com.mrbt.lingmoney.model.SupportBankExample;
import com.mrbt.lingmoney.model.UsersAccount;
import com.mrbt.lingmoney.model.UsersProperties;
import com.mrbt.lingmoney.model.UsersPropertiesExample;
import com.mrbt.lingmoney.service.bank.BankAccountService;
import com.mrbt.lingmoney.service.bank.BankCardService;
import com.mrbt.lingmoney.service.common.UtilService;
import com.mrbt.lingmoney.service.redPacket.RedPacketService;
import com.mrbt.lingmoney.service.users.VerifyService;
import com.mrbt.lingmoney.utils.AppCons;
import com.mrbt.lingmoney.utils.DateUtils;
import com.mrbt.lingmoney.utils.PageInfo;
import com.mrbt.lingmoney.utils.PropertiesUtil;
import com.mrbt.lingmoney.utils.ResultParame.ResultInfo;
import com.mrbt.lingmoney.utils.ResultParame.ResultNumber;

/**
 * 银行卡服务
 */
@Service
public class BankCardServiceImpl implements BankCardService {

	@Autowired
	private HxTiedCard hxTiedCard;
	@Autowired
	private HxCardMapper hxCardMapper;
	@Autowired
	private VerifyService verifyService;
	@Autowired
	private UtilService utilService;
	@Autowired
	private UsersPropertiesMapper usersPropertiesMapper;
	@Autowired
	private UsersAccountMapper usersAccountMapper;
	@Autowired
	private ActivityRecordMapper activityRecordMapper;
	@Autowired
	private RedisSet redisSet;
	@Autowired
	private RedisGet redisGet;
	@Autowired
	private BankAccountService bankAccountService;
	@Autowired
	private RedPacketService redPacketService;
    @Autowired
    private HxChangePersonalBindCard hxChangePersonalBindCard;
    @Autowired
    private HxAccountMapper hxAccountMapper;
    @Autowired
    private HxEnterpriseBindCard hxEnterpriseBindCard;
    @Autowired
    private SupportBankMapper supportBankMapper;

    private static final String LOGGROUP = "\nbankCardServiceImpl_";
    private static final String CHANGE_BINDCARD_RETURN_URL = PropertiesUtil
            .getPropertiesByKey("CHANGE_BINDCARD_RETURN_URL");
    private static final String BANKURL = PropertiesUtil.getPropertiesByKey("BANK_POST_URL");

	@Override
	public PageResponseDto TiedBankCard(HxTiedCardReqDto req, String uId) throws Exception {
		if (uId.equals(redisGet.getRedisStringResult(AppCons.REPEAT_CLICK + uId))) {
            throw new PageInfoException("请求未完毕，请勿重复请求", ResultInfo.REQUEST_AGAIN.getCode());
		}

		redisSet.setRedisStringResult(AppCons.REPEAT_CLICK + uId, uId);

		// 查询用户信息, util已判断是否有账户
		HxAccount acc = utilService.queryUserHxAccount(uId);

		// 查询开卡记录
//        boolean flag = true; // 初次请求
		HxCardExample ex = new HxCardExample();
		ex.setOrderByClause("tied_date desc");
		ex.createCriteria().andAccIdEqualTo(acc.getId());
		List<HxCard> cardInfo = hxCardMapper.selectByExample(ex);
		
		if (!cardInfo.isEmpty()) {
			// 已有开卡记录
//			flag = false;
			// 查询账户开卡状态
			Map<String, Object> cardStatusMap = queryStatus(uId);
			Integer cardStatus = Integer.parseInt(cardStatusMap.get("status").toString());

            if (cardStatus.equals(1)) { // 已成功
                throw new PageInfoException("此用户已激活成功", ResultInfo.ACCOUNT_FORMAT_EXIST.getCode());
			}

//            if (cardStatus.equals(3)) { // 请求中
//                throw new PageInfoException("此账号请求结果正在处理中，请稍候", ResultInfo.ING.getCode());
//			}
		}

		// set华兴银行账号
		req.setACNO(acc.getAcNo());

		// 取出url
		String url = PropertiesUtil.getPropertiesByKey("TIED_CARD_RETURN_URL");

		req.setRETURNURL(url + uId);
		req.setTTRANS("1");

		// 获取报文数据
		PageResponseDto res = hxTiedCard.requestTiedCard(req);
		HxCard hxCard = new HxCard();
		hxCard.setAccId(acc.getId());
		hxCard.setAppId(req.getAPPID());
		hxCard.setSeqNo(res.getchannelFlow());
		hxCard.setTiedDate(new Date());
		hxCard.setStatus(2);
//		if (flag) {
			// 初次请求
			// 此时插入新记录 状态为 处理中
			hxCard.setId(UUID.randomUUID().toString().replace("-", ""));
			hxCardMapper.insertSelective(hxCard);
//		} else {
//			HxCardExample cardEx = new HxCardExample();
//			cardEx.createCriteria().andAccIdEqualTo(acc.getId());
//			hxCardMapper.updateByExampleSelective(hxCard, cardEx);
//		}

		res.setbankUrl(PropertiesUtil.getPropertiesByKey("BANK_POST_URL"));
		return res;
	}

	@Override
	public String receiveTiedCardResult(Document document) throws Exception {

		System.currentTimeMillis();
		// dto
		ResponseBodyDto paraDto = new ResponseBodyDto();
		String uId = "";
		String channelFlow = "";
		try {
			// 接收结果
			Map<String, Object> resMap = HxBaseData.xmlToMap(document);

			// String channelFlow =
			// hxTiedCard.receiveTiedCardAsync(document.asXML());

			channelFlow = (String) resMap.get("OLDREQSEQNO");
			paraDto.setOLDREQSEQNO(channelFlow);
			// 查询流水号对应的acc
			uId = verifyService.verifyAcNoByChannelFlow(channelFlow).getuId();

		} catch (ResponseInfoException resE) {
			paraDto.setRETURNCODE(resE.getCode().toString());
			paraDto.setRETURNMSG(resE.getMessage());
			return hxTiedCard.responseTiedCardAsync(paraDto);
		} catch (PageInfoException pEx) {
			paraDto.setRETURNCODE(pEx.getCode().toString());
			paraDto.setRETURNMSG(pEx.getMessage());
			return hxTiedCard.responseTiedCardAsync(paraDto);
		}

		successOperate(uId, channelFlow, false);

		// 返回成功报文
		return hxTiedCard.responseTiedCardAsync(paraDto);
	}

	@Override
	public Map<String, Object> queryTiedCardResult(String uId) throws Exception {
		return queryStatus(uId);
	}

	@Override
	public Map<String, Object> queryTiedCardResult(String key, String channelFlow) throws Exception {
		String uId = utilService.queryUid(key);
		return queryStatus(uId);
	}

    /**
     * 查询绑卡状态 两种情况 1、用户登录查询  1：成功 2：失败 3：处理中
     * 
     * @param uId 用户id
     * @return map结果对象
     * @throws Exception 
     *
     */
	private Map<String, Object> queryStatus(String uId) throws Exception {
		Map<String, Object> res = new HashMap<String, Object>();

		// 验证用户账户
		HxAccount hxAcc = verifyService.verifyAcNoByUid(uId);

		// 获取用户绑卡记录
		HxCard hxCard = verifyService.verifyBankCard(hxAcc.getId());

		// 用户属性激活结果
		boolean userProp = isOpenFromUsersProp(uId);

		// 成功
		// 情形1：用户属性标识已激活
		if (userProp) {
			res.put("status", 1);
			res.put("msg", "激活成功！");
			return res;
		}

		// 异步回调返回结果
		boolean callBackRes = isOpenFromCallback(hxCard);
		// 查询账户状态结果
		boolean bankRes = isOpenFromBank(hxAcc);

		// 情形2：异步回调成功或请求查询接口成功
		if (callBackRes || bankRes) {
			res.put("status", 1);
			res.put("msg", "激活成功！");
			// 更新用户信息
			successOperate(uId, hxAcc.getId(), true);
			return res;
		}

		// 失败
		// 超时结果
		boolean timeOutRes = isTimeOut(hxCard);

		// 情形1：超时&查询接口标识未激活
		if (timeOutRes && !bankRes) {
			res.put("status", 2);
			res.put("msg", "激活失败，请重试！");
			res.put("failReason", "请求超时");
			return res;
		}

		// 请求中 未超时 &无异步回调结果& 查询接口账户状态未成功
		res.put("status", 3);
		res.put("msg", "处理中，请稍后查询");
		return res;

	}

	/**
	 * 查询用户表绑卡状态
	 * 
	 * @author YJQ 2017年8月3日
	 * @param uId 用户id
	 * @return true 已绑卡  false未绑卡
	 */
	private boolean isOpenFromUsersProp(String uId) {
		// 查询用户属性
		UsersPropertiesExample ex = new UsersPropertiesExample();
		ex.createCriteria().andUIdEqualTo(uId);
		UsersProperties userPro = usersPropertiesMapper.selectByExample(ex).get(0);
		Integer tiedBankStatus = userPro.getBank();
		return tiedBankStatus.equals(2) || tiedBankStatus.equals(3);
	}

	/**
	 * 查询 华兴卡表 绑卡状态（异步回调结果）
	 * 
	 * @author YJQ 2017年8月3日
	 * @param hxCard 银行卡信息
	 * @return true 成功  false 未成功
	 */
	private boolean isOpenFromCallback(HxCard hxCard) {
		Integer tiedBankStatus = hxCard.getStatus();
		return tiedBankStatus.equals(1);
	}

	/**
	 * 查询银行接口(查询账户余额接口)返回的账户状态
	 * 
	 * @author YJQ 2017年8月3日
	 * @param acName 账户名
	 * @param acNo 账号
	 * @return true成功  false未成功
	 * @throws Exception 
	 */
	private boolean isOpenFromBank(HxAccount hxAcc) throws Exception {
		// 查询接口
		// Document result = new HxAccountBanlance().requestAccountBalance("PC",
		// hxAcc.getAcName(), hxAcc.getAcNo());
		// Object accStatus = result.selectSingleNode("//EXT_FILED1").getText();
        String accStatus = bankAccountService.queryRealNameAuthenticationResult(1, hxAcc.getAcNo(), LOGGROUP);

		if (StringUtils.isEmpty(accStatus)) {
			return false;
		}
		return "1".equals(accStatus);
	}

	/**
	 * 是否超时
	 * 
	 * @author YJQ 2017年8月3日
	 * @param hxCard 银行卡信息
	 * @return true 超时  false未超时
	 */
	private boolean isTimeOut(HxCard hxCard) {
		// 小于20分钟 返回false 未超时
		return DateUtils.dateDiffMins(hxCard.getTiedDate(), new Date()) > 20;
	}

	/**
	 * 用户绑卡成功后的操作
	 * 
	 * @author YJQ 2017年8月3日
	 * @param uId 用户id
	 * @param condition 账号或者流水号 根据flag判断
	 * @param flag true-用于condition是accid false-condition是channelflow
	 */
	private void successOperate(String uId, String condition, boolean flag) {
		// 更新用户属性
		UsersProperties uProperties = usersPropertiesMapper.selectByuId(uId);
		
		UsersPropertiesExample ex = new UsersPropertiesExample();
		ex.createCriteria().andUIdEqualTo(uId);
		UsersProperties record = new UsersProperties();
		
		//修改users_properties表中绑卡状态，如果是0，修改成2，如果是1，修改成3
		if(uProperties.getBank() == 0) {
			record.setBank(2);
		} else if(uProperties.getBank() == 1) {
			record.setBank(3);
		} else {
			record.setBank(uProperties.getBank());
		}
		
		usersPropertiesMapper.updateByExampleSelective(record, ex);

		// 更新绑卡记录属性
		HxCardExample cardEx = new HxCardExample();
		if (flag) {
			cardEx.createCriteria().andAccIdEqualTo(condition);
		} else {
			cardEx.createCriteria().andSeqNoEqualTo(condition);
		}
		HxCard card = new HxCard();
		card.setStatus(1);
		hxCardMapper.updateByExampleSelective(card, cardEx);

		// 绑卡成功分配100个领宝，2017年12月31号前绑卡成功用户赠送200领宝
		int presentLingbao = 100;
		// 推荐人获得50领宝，2017年12月31号前获得100领宝
		int recommendLingbao = 50;
		Date now = new Date();
		Date validDate = null;
		try {
			validDate = new SimpleDateFormat("yyyyMMdd HHmmss").parse("20171231 235959");
		} catch (ParseException e) {
			e.printStackTrace();
		}
		if (validDate != null && now.getTime() < validDate.getTime()) {
			presentLingbao = 200;
			recommendLingbao = 100;
		}
		UsersAccount usersAcc = new UsersAccount();
		UsersAccount oldUserAccount = usersAccountMapper.selectByUid(uId);
		usersAcc.setId(oldUserAccount.getId());
		if (oldUserAccount.getCountLingbao() != null && oldUserAccount.getCountLingbao() > 0) {
			usersAcc.setCountLingbao(presentLingbao + oldUserAccount.getCountLingbao());
		} else {
			usersAcc.setCountLingbao(presentLingbao);
		}
		usersAccountMapper.updateByPrimaryKeySelective(usersAcc);

		// 插入一条分配领宝的记录
		ActivityRecord lingBaoRecord = new ActivityRecord();
		lingBaoRecord.setAmount(presentLingbao);
		lingBaoRecord.setContent("绑卡送领宝");
		lingBaoRecord.setName("绑卡");
		lingBaoRecord.setuId(uId);
		lingBaoRecord.setStatus(1);
		lingBaoRecord.setType(0);
		lingBaoRecord.setUseDate(new Date());
		activityRecordMapper.insertSelective(lingBaoRecord);

		// 如果用户有推荐人，赠送推荐人领宝
		UsersProperties usersProperties = usersPropertiesMapper.selectByuId(uId);
		if (usersProperties != null && usersProperties.getReferralId() != null) {
			UsersAccount recomAccount = usersAccountMapper.selectByUid(usersProperties.getReferralId());
			if (recomAccount != null) {
				recomAccount.setCountLingbao(recomAccount.getCountLingbao() + recommendLingbao);
				usersAccountMapper.updateByPrimaryKeySelective(recomAccount);

				// 插入一条分配领宝的记录
				ActivityRecord recomRecord = new ActivityRecord();
				recomRecord.setAmount(recommendLingbao);
				recomRecord.setContent("推荐好友成功注册送领宝");
				recomRecord.setName("推荐好友成功注册");
				recomRecord.setuId(recomAccount.getuId());
				recomRecord.setStatus(1);
				recomRecord.setType(0);
				recomRecord.setUseDate(new Date());
				activityRecordMapper.insertSelective(recomRecord);
			}
		}

		// 绑卡成功，奖励优惠券活动
		redPacketService.rewardRedPackage(uId, 3, null);
	}

    @Override
    public PageInfo changePersonalBindCard(Integer clientType, String appId, String uId) {
        PageInfo pageInfo = new PageInfo();
        if (StringUtils.isEmpty(clientType) || StringUtils.isEmpty(appId) || StringUtils.isEmpty(uId)) {
            pageInfo.setResultInfo(ResultInfo.PARAM_MISS);
            return pageInfo;
        }

        // 验证开户绑卡状态
        UsersProperties usersProperties = usersPropertiesMapper.selectByuId(uId);
        if (usersProperties == null) {
            pageInfo.setCode(ResultInfo.EMPTY_DATA.getCode());
            pageInfo.setMsg("信息有误,请联系管理员");
            return pageInfo;
        }

        if (usersProperties.getCertification() != ResultNumber.TWO.getNumber()
                && usersProperties.getCertification() != ResultNumber.THREE.getNumber()) {
            pageInfo.setCode(ResultInfo.NOT_HX_ACCOUNT.getCode());
            pageInfo.setMsg("该用户未开通华兴银行账户");

        } else if (usersProperties.getBank() != ResultNumber.TWO.getNumber()
                && usersProperties.getBank() != ResultNumber.THREE.getNumber()) {
            pageInfo.setCode(ResultInfo.HX_ACCOUNT_NOT_ACTIVATION.getCode());
            pageInfo.setMsg("该用户账户未激活");

        } else {
            HxAccount hxAccount = hxAccountMapper.selectByUid(uId);
            Map<String, String> resultMap = hxChangePersonalBindCard.requestAccountOpen(clientType, appId,
                    hxAccount.getAcNo(), CHANGE_BINDCARD_RETURN_URL, uId);

            // 请求成功，修改 卡号变更状态
            HxAccount record = new HxAccount();
            record.setId(hxAccount.getId());
            record.setIsChanged("1");
            hxAccountMapper.updateByPrimaryKeySelective(record);

            resultMap.put("bankUrl", BANKURL);
            pageInfo.setResultInfo(ResultInfo.SUCCESS);
            pageInfo.setObj(resultMap);
        }

        return pageInfo;
    }

    @Override
    public PageInfo queryBindCardInfo(String uid, String currentPage, String pageNumber, String logGroup) {
        PageInfo pageInfo = new PageInfo();
        
        if (!StringUtils.isEmpty(uid)) {
            HxAccountExample accountexample = new HxAccountExample();
            accountexample.createCriteria().andUIdEqualTo(uid).andStatusEqualTo(1);

            List<HxAccount> hxAccountList = hxAccountMapper.selectByExample(accountexample);
            if (hxAccountList != null && hxAccountList.size() > 0) {
                HxAccount hxAccount = hxAccountList.get(0);
                Document document = hxEnterpriseBindCard.queryEnterpriseBindCardInfo(hxAccount.getAcNo(), currentPage,
                        pageNumber, logGroup);
                Map<String, Object> resultMap = HxBaseData.xmlToMap(document);
                
                String errorCode = (String) resultMap.get("errorCode");
                if ("0".equals(errorCode)) {
                    String totalCount = (String) resultMap.get("TOTALCOUNT");

                    pageInfo.setTotal(Integer.parseInt(totalCount));

                    if (Integer.parseInt(totalCount) > 0) {
                        String cardno = (String) resultMap.get("CARDNO");
                        String statusid = (String) resultMap.get("STATUSID");
                        String bankname = (String) resultMap.get("BANKNAME");
                        // 如果查询到的绑定卡号和用户卡号不等，则进行更新
                        if (!cardno.equals(hxAccount.getBindCard())) {
                            HxAccount record = new HxAccount();
                            record.setId(hxAccount.getId());
                            record.setBindCard(cardno);
                            record.setBankName(bankname);
                            record.setStatusid(statusid);
                            hxAccountMapper.updateByPrimaryKeySelective(record);
                        }
                        Map<String, String> resultInfo = new HashMap<String, String>();
                        if (!StringUtils.isEmpty(cardno)) {
                            cardno = "尾号" + cardno.substring(cardno.length() - 4, cardno.length());
                        }
                        resultInfo.put("cardno", cardno);
                        resultInfo.put("statusid", "0".equals(statusid) ? "已绑定" : "预绑定");
                        resultInfo.put("bankname", bankname);

                        SupportBankExample example = new SupportBankExample();
                        example.createCriteria().andBankNameLike("%" + bankname + "%");
                        List<SupportBank> bankList = supportBankMapper.selectByExample(example);
                        if (bankList != null && bankList.size() > 0) {
                            resultInfo.put("logo", bankList.get(0).getBankLogo());
                            resultInfo.put("background", bankList.get(0).getBackground());
                            resultInfo.put("shortName", bankList.get(0).getBankShort());
                        } else {
                            resultInfo.put("logo", null);
                            resultInfo.put("background", null);
                            resultInfo.put("shortName", null);
                        }
                        pageInfo.setResultInfo(ResultInfo.SUCCESS);
                        pageInfo.setObj(resultInfo);
                    }

                } else if ("OGW100200009".equals(errorCode)) {
                    // 接口访问频繁，直接获取hxaccount中数据
                    Map<String, String> resultInfo = new HashMap<String, String>();
                    String card = hxAccount.getBindCard();
                    if (!StringUtils.isEmpty(card)) {
                        card = "尾号" + card.substring(card.length() - 4, card.length());
                    }
                    resultInfo.put("cardno", card);
                    resultInfo.put("statusid", "0".equals(hxAccount.getStatusid()) ? "已绑定" : "预绑定");
                    resultInfo.put("bankname", hxAccount.getBankName());
                    SupportBankExample example = new SupportBankExample();
                    example.createCriteria().andBankNameLike("%" + hxAccount.getBankName() + "%");
                    List<SupportBank> bankList = supportBankMapper.selectByExample(example);
                    if (bankList != null && bankList.size() > 0) {
                        resultInfo.put("logo", bankList.get(0).getBankLogo());
                        resultInfo.put("background", bankList.get(0).getBackground());
                        resultInfo.put("shortName", bankList.get(0).getBankShort());
                    } else {
                        resultInfo.put("logo", null);
                        resultInfo.put("background", null);
                        resultInfo.put("shortName", null);
                    }
                    pageInfo.setResultInfo(ResultInfo.SUCCESS);
                    pageInfo.setObj(resultInfo);

                } else {
                    pageInfo.setCode(ResultInfo.NO_SUCCESS.getCode());
                    pageInfo.setMsg("查询异常，请稍后再试。CODE:" + errorCode);
                }

            } else {
                pageInfo.setResultInfo(ResultInfo.NO_DATA);
            }
            
        } else {
            pageInfo.setResultInfo(ResultInfo.PARAM_MISS);
        }
        
        return pageInfo;
    }
}
