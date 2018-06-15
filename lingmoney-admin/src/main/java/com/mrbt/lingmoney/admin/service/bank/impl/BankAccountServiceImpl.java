package com.mrbt.lingmoney.admin.service.bank.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.dom4j.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.mrbt.lingmoney.admin.controller.redPacket.RedPacketController;
import com.mrbt.lingmoney.admin.service.bank.BankAccountService;
import com.mrbt.lingmoney.bank.HxBaseData;
import com.mrbt.lingmoney.bank.eaccount.HxAccountOpen;
import com.mrbt.lingmoney.bank.eaccount.HxRealNameAuthentication;
import com.mrbt.lingmoney.bank.enterprise.HxEnterpriseBindCard;
import com.mrbt.lingmoney.commons.cache.RedisDao;
import com.mrbt.lingmoney.mapper.ActivityRecordMapper;
import com.mrbt.lingmoney.mapper.CustomQueryMapper;
import com.mrbt.lingmoney.mapper.HxAccountMapper;
import com.mrbt.lingmoney.mapper.HxCardMapper;
import com.mrbt.lingmoney.mapper.HxRedPacketMapper;
import com.mrbt.lingmoney.mapper.UsersAccountMapper;
import com.mrbt.lingmoney.mapper.UsersPropertiesMapper;
import com.mrbt.lingmoney.mapper.UsersRedPacketMapper;
import com.mrbt.lingmoney.model.ActivityRecord;
import com.mrbt.lingmoney.model.HxAccount;
import com.mrbt.lingmoney.model.HxAccountExample;
import com.mrbt.lingmoney.model.HxCard;
import com.mrbt.lingmoney.model.HxCardExample;
import com.mrbt.lingmoney.model.UsersAccount;
import com.mrbt.lingmoney.model.UsersProperties;
import com.mrbt.lingmoney.model.UsersPropertiesExample;
import com.mrbt.lingmoney.utils.PropertiesUtil;
import com.mrbt.lingmoney.utils.ResultParame;
import com.mrbt.lingmoney.utils.ResultParame.ResultNumber;

/**
 * 
 * BankAccountServiceImpl
 *
 */
@Service("BankAccountService")
public class BankAccountServiceImpl implements BankAccountService {

	private static final Logger LOGGER = LogManager.getLogger(BankAccountService.class);
	
	@Autowired
	private RedisDao redisDao;
	@Autowired
	private HxAccountMapper hxAccountMapper;
	@Autowired
	private UsersPropertiesMapper usersPropertiesMapper;
	@Autowired
	private UsersAccountMapper usersAccountMapper;
	@Autowired
	private ActivityRecordMapper activityRecordMapper;
	@Autowired
	private HxCardMapper hxCardMapper;
	@Autowired
	private HxRealNameAuthentication hxRealNameAuthentication;
	@Autowired
	private CustomQueryMapper customQueryMapper;
	@Autowired
	private RedPacketController redPacketController;

	private static final String BANKURL = PropertiesUtil.getPropertiesByKey("BANK_POST_URL");

	@Override
	public void queryAccountOpen() {
		HxAccountExample example = new HxAccountExample();
		example.createCriteria().andStatusEqualTo(0);
		List<HxAccount> resList = hxAccountMapper.selectByExample(example);

		if (resList != null && resList.size() > 0) {
			for (int i = 0; i < resList.size(); i++) {
				HxAccount hxAccount = resList.get(i);
				// 频繁查询过滤
				String seqNo = "queryAccountOpen_" + hxAccount.getSeqNo();
				if (redisDao.get(seqNo) == null) {
					redisDao.set(seqNo, hxAccount.getuId());
					redisDao.expire(seqNo, ResultParame.ResultNumber.TWO.getNumber(), TimeUnit.MINUTES);

					// 发查询请求
					Map<String, Object> resMap = new HxAccountOpen().queryAccountOpen(hxAccount.getSeqNo(),
							hxAccount.getAppId(), hxAccount.getuId(), BANKURL);

                    if (resMap != null) {
                        if (resMap.get("RETURN_STATUS") != null) {
							String returnStatus2 = resMap.get("RETURN_STATUS").toString();
                            // 处理成功
							if (returnStatus2.equals("S")) {
                                rightOption(resMap, hxAccount.getuId());
                            }
                            // 处理失败
							if (returnStatus2.equals("F")) {
                                errorOption(hxAccount.getuId(), hxAccount.getSeqNo());
                            }

                            long time = new Date().getTime() - hxAccount.getCreateTime().getTime();
                            // 处理中,如果操过25分钟做失败处理
							if (returnStatus2.equals("R")) {
								if (time > ResultParame.ResultNumber.TWENTY_FIVE.getNumber()
										* ResultParame.ResultNumber.SIXTY.getNumber()
										* ResultParame.ResultNumber.ONE_THOUSAND.getNumber()) {
                                    errorOption(hxAccount.getuId(), hxAccount.getSeqNo());
                                }
                            }

                            // 30 分钟仍是未成功的状态可判定交易为失败，无需再进行查询
							if (time > ResultParame.ResultNumber.THIRTY.getNumber()
									* ResultParame.ResultNumber.SIXTY.getNumber()
									* ResultParame.ResultNumber.ONE_THOUSAND.getNumber()) {
                                errorOption(hxAccount.getuId(), hxAccount.getSeqNo());
                            }

                        } else {
                            String errorCode = (String) resMap.get("errorCode");
                            // 无此交易流水
                            if ("OGWERR999997".equals(errorCode)) {
                                errorOption(hxAccount.getuId(), hxAccount.getSeqNo());
                            }
                        }

					}
				}
			}
		}
	}

	/**
	 * 账户开立成功操作
	 * 
	 * @param resMap
	 *            resMap
	 * @param uId
	 *            uId
	 * @return 数据返回
	 */
	private int rightOption(Map<String, Object> resMap, String uId) {
		try {
			HxAccount hxAccount = new HxAccount();
			HxAccountExample hxExample = new HxAccountExample();
			hxExample.createCriteria().andUIdEqualTo(uId).andSeqNoEqualTo(resMap.get("OLDREQSEQNO").toString());

			hxAccount.setStatus(1);
			hxAccount.setAcNo(resMap.get("ACNO").toString());
			hxAccount.setAcName(resMap.get("ACNAME").toString());
			hxAccount.setMobile(resMap.get("MOBILE").toString());
			hxAccount.setOpenDate(new Date());

			int res = hxAccountMapper.updateByExampleSelective(hxAccount, hxExample);

			if (res > 0) {
				// 更新users_properties表中的certification为1
				UsersProperties record = new UsersProperties();
				
				UsersProperties uProperties = usersPropertiesMapper.selectByuId(uId);
				
				if (uProperties.getCertification() == 0) {
                	record.setCertification(ResultNumber.TWO.getNumber());
    			} else if (uProperties.getCertification() == 1) {
    				record.setCertification(ResultNumber.THREE.getNumber());
				}

				UsersPropertiesExample example = new UsersPropertiesExample();
				example.createCriteria().andUIdEqualTo(uId);
				usersPropertiesMapper.updateByExampleSelective(record, example);
				// 奖励优惠券
				redPacketController.rewardRedPackage(uId, ResultParame.ResultNumber.TWO.getNumber(), null);
				return 1;
			} else {
				return 0;
			}
		} catch (Exception e) {
			return 0;
		}
	}

	/**
	 * 失败操作
	 * 
	 * @param uId
	 *            uId
	 * @param seqNo
	 *            seqNo
	 */
    private void errorOption(String uId, String seqNo) {
		HxAccountExample example = new HxAccountExample();
		example.createCriteria().andUIdEqualTo(uId).andSeqNoEqualTo(seqNo);

		HxAccount hxAccount = new HxAccount();
		hxAccount.setStatus(ResultParame.ResultNumber.MINUS_ONE.getNumber());
		hxAccount.setOpenDate(new Date());

		hxAccountMapper.updateByExampleSelective(hxAccount, example);
	}

	@Override
	public void pollingTiedCardResult() {
		
		String logGroup = "\n计划任务查询E账户激活信息：pollingTiedCardResult_" + System.currentTimeMillis() + "_";
		
		LOGGER.info("进入查询用户绑卡结果进程...");
		// 查询请求中的用户
		List<Map<String, Object>> list = customQueryMapper.listAutoQueryBindCardUsers();
		if (list == null || list.size() == 0) {
			LOGGER.info("暂无已开通华兴E账户未绑卡的用户。");
			return;
		}

		LOGGER.info("检测到领钱数据库中已开通华兴E账户未绑卡的用户有[" + list.size() + "]个");
		
		Date date = new Date();
		
		Map<String, Object> queryAcNoMap = new HashMap<String, Object>();
		
		for (Map<String, Object> map : list) {
			String acNo = map.get("acNo").toString();
			try {
				//验证操作时间，如果超过30分钟，修改hx_card的数据为失败
				Date tiedDate = (Date) map.get("tiedDate");
				
				if ((date.getTime() - tiedDate.getTime()) > 30 * 60 * 1000) {
					//修改数据状态
					HxCard hc = new HxCard();
					hc.setId(map.get("hcId").toString());
					hc.setStatus(3);
					hxCardMapper.updateByPrimaryKeySelective(hc);
					continue;
				}
				
				if (queryAcNoMap.containsKey(acNo)) {
					HxCard hc = new HxCard();
					hc.setId(map.get("hcId").toString());
					hc.setStatus(3);
					hxCardMapper.updateByPrimaryKeySelective(hc);
					continue;
				}
				
				//查询接口，验证用户激活E账户状态
				Document xml = new HxEnterpriseBindCard().queryEnterpriseBindCardInfo(acNo, "1", "2", logGroup + acNo);
				Map<String, Object> analysisMap =  HxBaseData.xmlToMap(xml);
				   
				if (analysisMap.get("STATUSID") != null && analysisMap.get("STATUSID").equals("0")) {
					tiedCardSuccessOperation(map);
				}
				//增加map防止重复查询
				LOGGER.info("查询到id为[" + map.get("uId") + "],acNo为[" + acNo + "]的用户华兴E账户的状态为[" + analysisMap.get("STATUSID") + "]");
				queryAcNoMap.put(acNo, "");
			} catch (Exception e) {
				e.printStackTrace();
				continue;
			}
		}

	}

	/**
	 * 已绑卡后的处理
	 * 
	 * @author YJQ 2017年7月20日
	 * @param uId
	 *            uId
	 * @param accId
	 *            accId
	 * @throws Exception
	 *             Exception
	 */
	private void tiedCardSuccessOperation(Map<String, Object> map) throws Exception {
		UsersPropertiesExample userEx = new UsersPropertiesExample();
		userEx.createCriteria().andUIdEqualTo(map.get("uId").toString());
		
		UsersProperties record = new UsersProperties();
		
		//验证,users_properties表中的状态
		UsersProperties uProperties = usersPropertiesMapper.selectByuId(map.get("uId").toString());
		if (uProperties.getBank() == 1|| uProperties.getBank() == 0) {
			if (uProperties.getBank() == 0) {
				record.setBank(2);
			} else {
				record.setBank(3);
			}
			
			usersPropertiesMapper.updateByExampleSelective(record, userEx);
			
			// 绑卡成功分配100个领宝，2017年12月31号前绑卡成功用户赠送200领宝
			int presentLingbao = ResultParame.ResultNumber.ONE_HUNDRED.getNumber();
	        // 推荐人获得50领宝，2017年12月31号前获得100领宝
			int recommendLingbao = ResultParame.ResultNumber.FIFTY.getNumber();

	        Date now = new Date();
	        Date validDate = new SimpleDateFormat("yyyyMMdd HHmmss").parse("20171231 235959");
	        if (now.getTime() < validDate.getTime()) {
				presentLingbao = ResultParame.ResultNumber.TWO.getNumber();
				recommendLingbao = ResultParame.ResultNumber.ONE_HUNDRED.getNumber();
	        }
			UsersAccount usersAcc = new UsersAccount();
	        UsersAccount oldUserAccount = usersAccountMapper.selectByUid(map.get("uId").toString());
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
			lingBaoRecord.setuId(map.get("uId").toString());
			lingBaoRecord.setStatus(1);
			lingBaoRecord.setType(0);
			lingBaoRecord.setUseDate(new Date()); // 实际记录为添加时间
			activityRecordMapper.insertSelective(lingBaoRecord);

	        // 如果用户有推荐人，赠送推荐人领宝
	        UsersProperties usersProperties = usersPropertiesMapper.selectByuId(map.get("uId").toString());
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

			redPacketController.rewardRedPackage(map.get("uId").toString(), 3, null);
		}
		
		// 更新绑卡记录属性
		HxCard hc = new HxCard();
		hc.setStatus(1);
		hc.setId(map.get("hcId").toString());
        hxCardMapper.updateByPrimaryKeySelective(hc);
	}

	@Override
	public String queryRealNameAuthenticationResult(Integer type, String acNo, String logGroup) {

		if (StringUtils.isEmpty(acNo) || StringUtils.isEmpty(type)) {
			return null;
		}

		Document resDoc = hxRealNameAuthentication.requestQueryRealNameAuthentication(acNo, logGroup);

		if (resDoc != null) {
			Map<String, Object> resultMap = HxBaseData.xmlToMap(resDoc);
			switch (type) {
			case 1:
				return (String) resultMap.get("STATUSID");
			case 2:
				return (String) resultMap.get("NETCHECKRESULT");
			case 3:
				return (String) resultMap.get("CERTIFICATIONRESULT");
			default:
				return null;
			}
		}

		return null;
	}

}
