package com.mrbt.lingmoney.service.bank.impl;

import java.util.Arrays;
import java.util.Date;
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

import com.mrbt.lingmoney.bank.HxBaseData;
import com.mrbt.lingmoney.bank.eaccount.HxAccountOpen;
import com.mrbt.lingmoney.bank.eaccount.HxRealNameAuthentication;
import com.mrbt.lingmoney.commons.cache.RedisDao;
import com.mrbt.lingmoney.mapper.HxAccountMapper;
import com.mrbt.lingmoney.mapper.UsersPropertiesMapper;
import com.mrbt.lingmoney.model.HxAccount;
import com.mrbt.lingmoney.model.HxAccountExample;
import com.mrbt.lingmoney.model.UsersProperties;
import com.mrbt.lingmoney.model.UsersPropertiesExample;
import com.mrbt.lingmoney.service.bank.BankAccountService;
import com.mrbt.lingmoney.service.redPacket.RedPacketService;
import com.mrbt.lingmoney.utils.PageInfo;
import com.mrbt.lingmoney.utils.PropertiesUtil;
import com.mrbt.lingmoney.utils.ResultParame.ResultInfo;
import com.mrbt.lingmoney.utils.ResultParame.ResultNumber;
import com.mrbt.lingmoney.utils.Validation;

/**
 * 银行账户实现
 */
@Service
public class BankAccountServiceImpl implements BankAccountService {

    @Autowired
    private HxAccountOpen hxAccountOpen;
    @Autowired
    private UsersPropertiesMapper usersPropertiesMapper;
    @Autowired
    private HxAccountMapper hxAccountMapper;
    @Autowired
    private RedisDao redisDao;
    @Autowired
    private HxRealNameAuthentication hxRealNameAuthentication;
    @Autowired
    private RedPacketService redPacketService;

    private static final Logger LOGGER = LogManager.getLogger(BankAccountServiceImpl.class);

    private static final String LOGGROUP = "\nbankAccountServiceImpl_";

    private static final String BANKURL = PropertiesUtil.getPropertiesByKey("BANK_POST_URL");

    private static final String ACCOUNT_OPEN_RETURN_URL = PropertiesUtil.getPropertiesByKey("ACCOUNT_OPEN_RETURN_URL");

    @Override
    public PageInfo requestAccountOpen(String uId, String acName, String idNo, String mobile, int clientType,
            String appId) {
        PageInfo pageInfo = new PageInfo();
        if (StringUtils.isEmpty(uId)) {
            pageInfo.setCode(ResultInfo.PARAM_MISS.getCode());
            pageInfo.setMsg("用户id参数缺失");
            return pageInfo;
        }
        // 判断是否以经开通账户
        UsersProperties usersProp = usersPropertiesMapper.selectByuId(uId);
        if (usersProp.getCertification() == ResultNumber.TWO.getNumber() || usersProp.getCertification() == ResultNumber.THREE.getNumber()) {
            pageInfo.setCode(ResultInfo.HX_ACCOUNT_NOT_ACTIVATION.getCode());
            pageInfo.setMsg("已经开通了华兴E账号");
            return pageInfo;
        }
        //根据用户ID查询状态
        HxAccountExample hxe = new HxAccountExample();
        hxe.createCriteria().andUIdEqualTo(uId);
        List<HxAccount> listHxe = hxAccountMapper.selectByExample(hxe);
        
		if (listHxe != null && listHxe.size() > 0) {
			HxAccount reshxAccount = listHxe.get(0);
			if (reshxAccount != null) {
				if (reshxAccount.getStatus() > 0) {
					pageInfo.setCode(ResultInfo.HX_ACCOUNT_NOT_ACTIVATION.getCode());
					pageInfo.setMsg("开户成功，请不要重复操作!");
					return pageInfo;
				}
			}
		}

        idNo = idNo.toUpperCase();
        // 验证身份证格式
        if (!Validation.IdCardNo(idNo)) {
            pageInfo.setCode(ResultInfo.ID_CRAD_ERROR.getCode());
            pageInfo.setMsg("身份证格式错误");
            return pageInfo;
        }

        // 验证身份证是否存在
        UsersPropertiesExample example = new UsersPropertiesExample();
        example.createCriteria().andUIdNotEqualTo(uId).andIdCardEqualTo(idNo);

        long resCount = usersPropertiesMapper.countByExample(example);
        if (resCount > 0) {
            pageInfo.setCode(ResultInfo.ID_CRAD_EXIST.getCode());
            pageInfo.setMsg("身份证已经被绑定");
            return pageInfo;
        }

        // 更新身份证信息
        // 如果用户已经绑定过京东，则不做修改
        UsersProperties record = new UsersProperties();
        record.setIdCard(idNo);
        record.setName(acName);
		UsersPropertiesExample example2 = new UsersPropertiesExample();
		example2.createCriteria().andUIdEqualTo(uId).andBankNotIn(Arrays.asList(1, 3))
				.andCertificationNotIn(Arrays.asList(1, 3));
		usersPropertiesMapper.updateByExampleSelective(record, example2);

        // 发起请求, 手机号要用户开通E账号的时候手动输入
        //		Users users = usersMapper.selectByPrimaryKey(uId);
        //		String mobile = users.getTelephone();

        String email = "";
        // 生成报文
        Map<String, String> contentMap = hxAccountOpen.requestAccountOpen(clientType, appId, acName, idNo, mobile,
                email, uId, ACCOUNT_OPEN_RETURN_URL);

        HxAccountExample hxExample = new HxAccountExample();
        hxExample.createCriteria().andUIdEqualTo(uId);

        //		Long count = hxAccountMapper.countByExample(hxExample);
        HxAccount hxAccount = new HxAccount();
        hxAccount.setSeqNo(contentMap.get("channelFlow"));
        hxAccount.setAcName(acName);
        hxAccount.setMobile(mobile);
        hxAccount.setEmail(email);
        hxAccount.setAppId(appId);
        hxAccount.setStatus(0);
        hxAccount.setCreateTime(new Date());
        hxAccount.setId(UUID.randomUUID().toString().replace("-", ""));
        hxAccount.setuId(uId);
        hxAccountMapper.insert(hxAccount);

        contentMap.put("bankUrl", BANKURL);
        System.out.println("生成的开户请求参数：" + contentMap);
        pageInfo.setCode(ResultInfo.SUCCESS.getCode());
        pageInfo.setMsg("生成报文成功");
        pageInfo.setObj(contentMap);
        return pageInfo;
    }

    @Override
    public PageInfo queryAccountOpen(String uId, String seqNo) {
        PageInfo pageInfo = new PageInfo();

        //		HxAccount hxAccount = hxAccountMapper.selectByUid(uId);

        HxAccountExample example = new HxAccountExample();
        example.createCriteria().andUIdEqualTo(uId).andSeqNoEqualTo(seqNo);

        List<HxAccount> listHxacc = hxAccountMapper.selectByExample(example);
        if (listHxacc != null && listHxacc.size() > 0) {
            HxAccount hxAccount = listHxacc.get(0);

            if (hxAccount.getStatus() == 1) {
                pageInfo.setCode(ResultInfo.SUCCESS.getCode());
                pageInfo.setMsg("账户开立成功");
                return pageInfo;
            }

            final int failStatus = 3; // 华兴账户表中失败的状态为3
            if (hxAccount.getStatus() == failStatus) {
                pageInfo.setCode(ResultInfo.NO_SUCCESS.getCode());
                pageInfo.setMsg("账户开立失败");
                return pageInfo;
            }

            // 频繁查询过滤
            String seqNoLog = "queryAccountOpen_" + hxAccount.getSeqNo();
            System.out.println(redisDao.get(seqNoLog) + "");
            if (redisDao.get(seqNoLog) != null) {
                pageInfo.setCode(ResultInfo.WAIT_REQUEST.getCode());
                pageInfo.setMsg("接口访问过于频繁，请稍后再访问");
                return pageInfo;
            } else {
                redisDao.set(seqNo, uId);
                redisDao.expire(seqNo, 180, TimeUnit.SECONDS);
            }

            Map<String, Object> resMap = new HxAccountOpen().queryAccountOpen(hxAccount.getSeqNo(),
                    hxAccount.getAppId(), uId, BANKURL);
            
            if (resMap != null) {
                if (resMap.get("RETURN_STATUS") != null) {
                    String resultStatus = resMap.get("RETURN_STATUS").toString();
                    if (resultStatus.equals("S")) {
                        // 处理成功
                        if (rightOption(resMap, uId) > 0) {
                            pageInfo.setCode(ResultInfo.SUCCESS.getCode());
                            pageInfo.setMsg("账户开立成功");
                        } else {
                            pageInfo.setCode(ResultInfo.UPDATE_FAIL.getCode());
                            pageInfo.setMsg("更新数据失败");
                        }
                    }
                    if (resultStatus.equals("F")) {
                        // 处理失败
                        errorOption(uId, seqNo);
                        pageInfo.setCode(ResultInfo.NO_SUCCESS.getCode());
                        pageInfo.setMsg("账户开立失败");
                    }
                    if (resultStatus.equals("R")) {
                        // 处理中
                        processOption(resMap, uId);
                        pageInfo.setCode(ResultInfo.ING.getCode());
                        pageInfo.setMsg("账户开立处理中");
                    }
                    if (resultStatus.equals("N")) {
                        // 未知,不作处理
                        pageInfo.setCode(ResultInfo.ING.getCode());
                        pageInfo.setMsg("处理中");
                    }

                } else {
                    String errorCode = (String) resMap.get("errorCode");
                    // 无此交易流水
                    if ("OGWERR999997".equals(errorCode)) {
                        errorOption(hxAccount.getuId(), seqNo);
                    }
                }

            } else {
                pageInfo.setCode(ResultInfo.ING.getCode());
                pageInfo.setMsg("处理中");
            }
        } else {
            pageInfo.setCode(ResultInfo.RETURN_EMPTY_DATA.getCode());
            pageInfo.setMsg("用户请求开通信息不存在");
        }

        return pageInfo;
    }

    /**
     * 账户开立成功操作
     * 
     * @param resMap 返回信息
     * @param uId 用户id
     * @return 数据库执行结果
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
            	//查询users_properties表中的certifecation的状态，如果为0，设置为2，如果为1，设置为3
            	UsersProperties uProperties = usersPropertiesMapper.selectByuId(uId);
            	
                UsersProperties record = new UsersProperties();
                
                if (uProperties.getCertification() == 0) {
                	record.setCertification(ResultNumber.TWO.getNumber());
    			} else if (uProperties.getCertification() == 1) {
    				record.setCertification(ResultNumber.THREE.getNumber());
				} else {
					record.setCertification(uProperties.getCertification());
				}

                UsersPropertiesExample example = new UsersPropertiesExample();
                example.createCriteria().andUIdEqualTo(uId);
                usersPropertiesMapper.updateByExampleSelective(record, example);
                // 奖励优惠券
                final int actType = 2; // 2-开通E账户后，
				redPacketService.rewardRedPackage(uId, actType, null);
                return 1;
            } else {
                return 0;
            }
        } catch (Exception e) {
        	e.printStackTrace();
            return 0;
        }
    }

    /**
     * 处理中操作
     * 
     * @param resMap 返回结果
     * @param uId 用户id
     */
    private void processOption(Map<String, Object> resMap, String uId) {
        HxAccountExample example = new HxAccountExample();
        example.createCriteria().andUIdEqualTo(uId).andSeqNoEqualTo(resMap.get("OLDREQSEQNO").toString());

        List<HxAccount> hxAccountList = hxAccountMapper.selectByExample(example);

        if (hxAccountList != null && hxAccountList.size() > 0) {
            HxAccount hxAccount = hxAccountList.get(0);

            Long m = (new Date().getTime() - hxAccount.getCreateTime().getTime()) / 1000 / 60;
            if (m >= 25) {
                HxAccount hxAccount2 = new HxAccount();
                hxAccount.setStatus(3);
                hxAccount.setOpenDate(new Date());

                hxAccountMapper.updateByExampleSelective(hxAccount2, example);
            }
        }
    }

    /**
     * 失败操作
     * 
     * @param uId 用户id
     * @param seqNo 流水号
     */
    private void errorOption(String uId, String seqNo) {
        HxAccountExample example = new HxAccountExample();
        example.createCriteria().andUIdEqualTo(uId).andSeqNoEqualTo(seqNo);

        HxAccount hxAccount = new HxAccount();
        hxAccount.setStatus(-1);
        hxAccount.setOpenDate(new Date());

        hxAccountMapper.updateByExampleSelective(hxAccount, example);
    }

    @Override
    public PageInfo requestAccountOpenVery(String uId, String acName, String idNo, int i, String string) {
        PageInfo pageInfo = new PageInfo();
        if (StringUtils.isEmpty(uId)) {
            pageInfo.setCode(ResultInfo.PARAM_MISS.getCode());
            pageInfo.setMsg("用户id参数缺失");
            return pageInfo;
        }
        // 判断是否以经开通账户
        UsersProperties usersProp = usersPropertiesMapper.selectByuId(uId);
        if (usersProp.getCertification() > 0) {
            pageInfo.setCode(ResultInfo.HX_ACCOUNT_NOT_ACTIVATION.getCode());
            pageInfo.setMsg("已经开通了账号");
            return pageInfo;
        }

        // 验证身份证格式
        if (!Validation.IdCardNo(idNo)) {
            pageInfo.setCode(ResultInfo.ID_CRAD_ERROR.getCode());
            pageInfo.setMsg("身份证格式错误");
            return pageInfo;
        }

        // 验证身份证是否存在
        UsersPropertiesExample example = new UsersPropertiesExample();
        example.createCriteria().andUIdNotEqualTo(uId).andIdCardEqualTo(idNo);

        long resCount = usersPropertiesMapper.countByExample(example);
        if (resCount > 0) {
            pageInfo.setCode(ResultInfo.ID_CRAD_EXIST.getCode());
            pageInfo.setMsg("身份证已经被绑定");
            return pageInfo;
        }
        pageInfo.setCode(ResultInfo.SUCCESS.getCode());
        pageInfo.setMsg("验证成功");
        return pageInfo;
    }

    @Override
    public String opertionAccountOpen(Document document) {

        String logGroupInfo = LOGGROUP + "开通E账户银行通知处理_" + System.currentTimeMillis();

        String resXml = "";

        Map<String, Object> resMap = HxBaseData.xmlToMap(document);

        HxAccountExample example = new HxAccountExample();
        example.createCriteria().andSeqNoEqualTo(resMap.get("OLDREQSEQNO").toString());
        List<HxAccount> hxAccount = hxAccountMapper.selectByExample(example);
        if (hxAccount != null && hxAccount.size() > 0) {
            HxAccount hxa = hxAccount.get(0);
            rightOption(resMap, hxa.getuId());
            resXml = hxAccountOpen.generatinAsynsReply(resMap, logGroupInfo, true);
        } else {
            // 返回处理失败报文
            resXml = hxAccountOpen.generatinAsynsReply(resMap, logGroupInfo, false);
        }
        return resXml;
    }

    @Override
    public PageInfo accountOpenStatus(String uId) {
        PageInfo pageInfo = new PageInfo();
        HxAccount hxAccount = hxAccountMapper.selectByUid(uId);
        if (hxAccount != null) {
            if (hxAccount.getStatus() == 1) {
                // 开户成功
                pageInfo.setCode(ResultInfo.TRAD_COMPLATE.getCode());
                pageInfo.setMsg("开户成功");
				// 开卡成功，奖励优惠券
//				final int actType = 2; // 2-开通E账户后，
				// final double amount = 0.019; // 指定的加息率
//				redPacketService.rewardRedPackage(uId, actType, null);
            } else if (hxAccount.getStatus() == 2 || hxAccount.getStatus() == 0) { //2是处理中  0是请求中
                // 开户处理中
                pageInfo.setCode(ResultInfo.TRAD_ING.getCode());
                pageInfo.setMsg("正常处理上一次的请求，请等待华兴银行处理结果！");
            } else {
                // 处理失败
                pageInfo.setCode(ResultInfo.TRAD_NOT_SUCCESS.getCode());
                pageInfo.setMsg("开户失败");
            }
        }
        return pageInfo;
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
