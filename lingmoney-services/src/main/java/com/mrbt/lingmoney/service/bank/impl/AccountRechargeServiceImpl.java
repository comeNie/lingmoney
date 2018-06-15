package com.mrbt.lingmoney.service.bank.impl;

import java.math.BigDecimal;
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
import com.mrbt.lingmoney.bank.eaccount.HxAccountRecharge;
import com.mrbt.lingmoney.commons.cache.RedisDao;
import com.mrbt.lingmoney.mapper.AccountFlowMapper;
import com.mrbt.lingmoney.mapper.HxAccountMapper;
import com.mrbt.lingmoney.mapper.UsersAccountMapper;
import com.mrbt.lingmoney.mapper.UsersPropertiesMapper;
import com.mrbt.lingmoney.model.AccountFlow;
import com.mrbt.lingmoney.model.AccountFlowExample;
import com.mrbt.lingmoney.model.HxAccount;
import com.mrbt.lingmoney.model.HxAccountExample;
import com.mrbt.lingmoney.model.UsersAccount;
import com.mrbt.lingmoney.model.UsersProperties;
import com.mrbt.lingmoney.service.bank.AccountRechargeService;
import com.mrbt.lingmoney.utils.AppCons;
import com.mrbt.lingmoney.utils.DateUtils;
import com.mrbt.lingmoney.utils.PageInfo;
import com.mrbt.lingmoney.utils.PropertiesUtil;
import com.mrbt.lingmoney.utils.ResultParame.ResultInfo;
import com.mrbt.lingmoney.utils.Validation;

/**
 * 账户充值实现
 */
@Service
public class AccountRechargeServiceImpl implements AccountRechargeService {

	@Autowired
	private HxAccountRecharge hxAccountRecharge;

	@Autowired
	private HxAccountMapper hxAccountMapper;

	@Autowired
	private UsersPropertiesMapper usersPropertiesMapper;

	@Autowired
	private UsersAccountMapper usersAccountMapper;

	@Autowired
	private AccountFlowMapper accountFlowMapper;
	
	@Autowired
	private RedisDao redisDao;
	
	private static final String RECHARGE_LIMIT = "RECHARGE_LIMIT";

    private static final Logger LOGGER = LogManager.getLogger(AccountRechargeServiceImpl.class);

    private static final String LOGGROUP = "nHxAccountRecharge_";

    private static final String BANKURL = PropertiesUtil.getPropertiesByKey("BANK_POST_URL");

	private static final String ACCOUNT_RECHARGE_RETURN_URL = PropertiesUtil.getPropertiesByKey("ACCOUNT_RECHARGE_RETURN_URL");

	@Override
	public PageInfo accountRechargeRequest(int clientType, String appId, String uId, String amount) {
		PageInfo pageInfo = new PageInfo();

        String logHeard = LOGGROUP + System.currentTimeMillis() + "_";
        LOGGER.info("请求账户充值_" + LOGGROUP);
		// 判断金额
		if (!Validation.MatchMoney(amount) || new BigDecimal(amount).compareTo(new BigDecimal(0)) <= 0) {
			pageInfo.setMsg("充值金额格式有误");
            pageInfo.setCode(ResultInfo.SUBMIT_PARAMS_ERROR.getCode());
			return pageInfo;
		}

		//判断充值的最小限额
		Double rechargeLimit1 = (Double) redisDao.get(RECHARGE_LIMIT);
		if (rechargeLimit1 == null) {
			rechargeLimit1 = 0.01;
		}
		
		if (new BigDecimal(amount).doubleValue() < rechargeLimit1) {
			pageInfo.setMsg("充值金额最低为" + rechargeLimit1);
        	pageInfo.setCode(ResultInfo.SUBMIT_PARAMS_ERROR.getCode());
			return pageInfo;
		}

		UsersProperties users = usersPropertiesMapper.selectByuId(uId);
		if (users.getCertification() == 0 || users.getCertification() == 1) {
            pageInfo.setCode(ResultInfo.NOT_HX_ACCOUNT.getCode());
			pageInfo.setMsg("未开通账户,请先开通账户");
			return pageInfo;
		}

		if (users.getBank() == 0 || users.getBank() == 1) {
            pageInfo.setCode(ResultInfo.USER_NOT_CRAD.getCode());
			pageInfo.setMsg("账户未绑卡,请先绑卡");
			return pageInfo;
		}

		HxAccountExample example = new HxAccountExample();
		example.createCriteria().andUIdEqualTo(uId).andStatusEqualTo(1);
		List<HxAccount> hxAccountList = hxAccountMapper.selectByExample(example);

		if (hxAccountList != null && hxAccountList.size() > 0) {
			HxAccount hxAccount = hxAccountList.get(0);
			Map<String, String> contentMap = hxAccountRecharge.accountRecharge(clientType, appId, uId,
					ACCOUNT_RECHARGE_RETURN_URL, hxAccount.getAcNo(), hxAccount.getAcName(), amount, logHeard);

			UsersAccount ua = usersAccountMapper.selectByUid(uId);
			// 生成资金流水
			AccountFlow accountFlow = new AccountFlow();
			accountFlow.setaId(ua.getId());
			accountFlow.setOperateTime(new Date());
			accountFlow.setMoney(new BigDecimal(amount));
			accountFlow.setNumber(contentMap.get("channelFlow"));
			accountFlow.setStatus(0);
			accountFlow.setType(0);
			accountFlow.setPlatform(clientType);

			long resInsert = accountFlowMapper.insert(accountFlow);
			if (resInsert > 0) {
                contentMap.put("bankUrl", BANKURL);

                pageInfo.setCode(ResultInfo.SUCCESS.getCode());
				pageInfo.setMsg("成功");
				pageInfo.setObj(contentMap);

				return pageInfo;
			} else {
                pageInfo.setCode(ResultInfo.UPDATE_FAIL.getCode());
				pageInfo.setMsg("操作失败");
				return pageInfo;
			}
		} else {
            pageInfo.setCode(ResultInfo.USER_NOT_CRAD.getCode());
			pageInfo.setMsg("没有账户数据,请与客服联系");
			return pageInfo;
		}
	}

	@Override
	public String accountRechargeCallBack(String xml, String note) {

		UsersAccount ua = usersAccountMapper.selectByUid(note);
		if (ua == null) {
			return "哪里来的数据";
		}

		// 数据验签，返回xml文档
		Document document = hxAccountRecharge.accountRechargeAsyncMsg(xml);

		if (document != null) {
            LOGGER.info(LOGGROUP + System.currentTimeMillis() + "应答报文\t" + document.asXML());

			// 判断报文status
			Map<String, Object> resMap = HxBaseData.xmlToMap(document);

			// 0：受理成功 1：受理失败 2：受理中 3：受理超时，不确定
			if (resMap.get("status").equals("0")) {
				// 更新hx_account数据 ORDER_COMPLETED：订单完成
				// ORDER_PRE_AUTHING：订单预授权中（非实时到账，下一工作日到账）

				if (resMap.get("ORDERSTATUS").equals("ORDER_COMPLETED")) {
					// 更新交易流水
					AccountFlowExample example = new AccountFlowExample();
                    example.createCriteria().andNumberEqualTo(resMap.get("OLDREQSEQNO").toString()).andTypeEqualTo(0);

					AccountFlow accountFlow = new AccountFlow();
                    accountFlow.setStatus(1); // 成功

					long res1 = accountFlowMapper.updateByExampleSelective(accountFlow, example);
					// 更新用户余额
					List<AccountFlow> list = accountFlowMapper.selectByExample(example);
					if (list != null && list.size() > 0) {
						accountFlow = list.get(0);
						ua.setBalance(ua.getBalance().add(accountFlow.getMoney()));
						long res2 = usersAccountMapper.updateByPrimaryKeySelective(ua);
						if (res1 == 0 || res2 == 0) {
							return "流水数据不存在";
						}
					}
				}
				// 生成应答返回报文
                String retMsg = hxAccountRecharge.generatinAsynsReply(resMap, LOGGROUP);
				if (retMsg != null) {
					return retMsg;
				}
			}
		}
		return "";
	}

	// @Override
	// public PageInfo queryAccountRecharge(String uId, String number, String
	// appId) {
	// PageInfo pageInfo = new PageInfo();
	//
	// String logHeard = logGroup + System.currentTimeMillis() + "_";
	//
	// UsersAccount ua = usersAccountMapper.selectByUid(uId);
	//
	// if (ua != null) {
	// AccountFlowExample example = new AccountFlowExample();
	// example.createCriteria().andAIdEqualTo(ua.getId()).andNumberEqualTo(number);
	//
	// List<AccountFlow> accountFlowList =
	// accountFlowMapper.selectByExample(example);
	// if (accountFlowList != null && accountFlowList.size() > 0) {
	// hxAccountRecharge.queryAccountRecharge(number, appId, logHeard);
	// } else {
    // pageInfo.setCode(ResultInfo.UPDATE_FAIL.getCode());
	// pageInfo.setMsg("没有查询到用户数据");
	// }
	// } else {
    // pageInfo.setCode(ResultInfo.UPDATE_FAIL.getCode());
	// pageInfo.setMsg("没有查询到用户数据");
	// }
	// return pageInfo;
	// }

	@Override
	public PageInfo queryAccountRecharge(String uId, String oldReqseqno, String appId) {
		// 生成日志头
        String logHeard = LOGGROUP + System.currentTimeMillis() + "_";

        LOGGER.info("充值结果查询请求：" + logHeard + "/t流水号" + oldReqseqno);

		PageInfo pageInfo = new PageInfo();

		// 如果不传用户ID，则为后台查询；否则为客户端查询
		if (!StringUtils.isEmpty(uId)) {
			UsersAccount usersAccount = usersAccountMapper.selectByUid(uId);
			if (StringUtils.isEmpty(usersAccount)) {
				pageInfo.setMsg("该用户不存在");
                pageInfo.setCode(ResultInfo.ACCOUNT_NUMBER_NON_EXIST.getCode());
				return pageInfo;
			}
		}

		// 根据原充值交易流水和从数据库中取出原充值交易日期
		AccountFlowExample example = new AccountFlowExample();
        example.createCriteria().andNumberEqualTo(oldReqseqno).andTypeEqualTo(0);
		List<AccountFlow> accountFlowList = accountFlowMapper.selectByExample(example);
		if (StringUtils.isEmpty(accountFlowList) || accountFlowList.size() == 0) {
			pageInfo.setMsg("该交易不存在");
            pageInfo.setCode(ResultInfo.RETURN_DATA_EMPTY_DATA.getCode());
			return pageInfo;
		}
		AccountFlow accountFlow = accountFlowList.get(0);

        Integer returnStatus = accountFlow.getStatus(); // 充值交易状态
        
		if (returnStatus == 1) {
			pageInfo.setMsg("充值成功");
            pageInfo.setCode(ResultInfo.SUCCESS.getCode());
			pageInfo.setObj(accountFlow.getMoney());
			return pageInfo;
		}
		
        final int failStatus = 2; //表示失败的状态
        if (returnStatus == failStatus) {
			pageInfo.setMsg("该操作已失败");
            pageInfo.setCode(ResultInfo.TRADING_NOT_SUCCESS.getCode());
			return pageInfo;
		}
        
        pageInfo.setMsg("处理中");
        pageInfo.setCode(ResultInfo.TRAD_ING.getCode());

//		new SimpleDateFormat("YYYYMMDD").format(accountFlow.getOperateTime());
//
//		// 7、向银行提交请求
//        Map<String, Object> resMap = hxAccountRecharge.queryAccountRecharge(oldReqseqno, appId, BANKURL, logHeard);
//
//		if (resMap != null) {
//			String errorCode = resMap.get("errorCode").toString();
//            if (errorCode.equals("0")) { // 以下信息，只有errorCode =0时才返回，即正常响应时才返回
//				String status = resMap.get("status").toString();
//                if (status.equals("0")) { // 受理成功，不代表操作成功
//                    String resultStatus = resMap.get("RETURN_STATUS").toString();
//                    if (resultStatus.equals("S")) {
//						// 处理成功
//						if (rightOption(resMap, uId) > 0) {
//                            pageInfo.setCode(ResultInfo.SUCCESS.getCode());
//							pageInfo.setMsg("充值成功");
//							pageInfo.setObj(accountFlow.getMoney());
//						} else {
//                            pageInfo.setCode(ResultInfo.UPDATE_FAIL.getCode());
//							pageInfo.setMsg("更新数据失败");
//						}
//                    } else if (resultStatus.equals("F")) {
//						// 处理失败
//						String errormsg = resMap.get("ERRORMSG").toString();
//						errorOption(resMap, uId);
//                        pageInfo.setCode(ResultInfo.NO_SUCCESS.getCode());
//						pageInfo.setMsg(errormsg);
//					} else {
//						// 处理中
//                        processOption(resMap, uId, resultStatus);
//                        pageInfo.setCode(ResultInfo.ING.getCode());
//						pageInfo.setMsg("处理中");
//					}
//				} else if (status.equals("1")) {// 受理失败，交易可置为失败
//					errorOption(resMap, uId);
//                    pageInfo.setCode(ResultInfo.TRADING_NOT_SUCCESS.getCode());
//					pageInfo.setMsg("银行受理失败");
//				} else {
//                    pageInfo.setCode(ResultInfo.ING.getCode());
//					pageInfo.setMsg("处理中");
//				}
//			} else {// 错误，返回具体错误原因
//				// 如果返回code不为访问频繁
//				if (!resMap.get("errorCode").equals("OGW100200009")) {
//					// 错误，返回具体错误原因
//					String errorMsg = resMap.get("errorMsg").toString();
//					// 如果无此流水号，则把所传流水号添加到map
//					if (resMap.get("errorCode").equals("OGWERR999997")) {
//						resMap.put("OLDREQSEQNO", oldReqseqno);
//					}
//					errorOption(resMap, uId);
//                    pageInfo.setCode(ResultInfo.TRADING_NOT_SUCCESS.getCode());
//					pageInfo.setMsg(errorMsg);
//				} else {
//                    pageInfo.setCode(ResultInfo.ING.getCode());
//					pageInfo.setMsg("处理中");
//				}
//			}
//		} else {
//            pageInfo.setCode(ResultInfo.ING.getCode());
//			pageInfo.setMsg("处理中");
//		}

		return pageInfo;
	}

	@Override
	public PageInfo queryAccountRecharge(String oldReqseqno) {
		// 生成日志头
        String logHeard = LOGGROUP + System.currentTimeMillis() + "_";

        LOGGER.info("充值结果查询请求：" + LOGGROUP);

		PageInfo pageInfo = new PageInfo();

		// 根据原充值交易流水和从数据库中取出原充值交易日期
		AccountFlowExample example = new AccountFlowExample();
		example.createCriteria().andNumberEqualTo(oldReqseqno);
		List<AccountFlow> accountFlowList = accountFlowMapper.selectByExample(example);
		if (StringUtils.isEmpty(accountFlowList) || accountFlowList.size() == 0) {
			pageInfo.setMsg("该交易不存在");
            pageInfo.setCode(ResultInfo.RETURN_DATA_EMPTY_DATA.getCode());
			return pageInfo;
		}
		AccountFlow accountFlow = accountFlowList.get(0);
		
		// 查出流水对应uid
		Integer accId = accountFlow.getaId();
		HxAccount acc = hxAccountMapper.selectByPrimaryKey(accId.toString());
		if (StringUtils.isEmpty(acc)) {
			pageInfo.setMsg("该充值流水对应用户不存在");
            pageInfo.setCode(ResultInfo.RETURN_DATA_EMPTY_DATA.getCode());
			return pageInfo;
		}
		String uId = acc.getuId();
		
		Integer returnStatus = accountFlow.getStatus();// 充值交易状态
		if (returnStatus == 1) {
			pageInfo.setMsg("充值成功");
            pageInfo.setCode(ResultInfo.SUCCESS.getCode());
			pageInfo.setObj(accountFlow.getMoney());
			return pageInfo;
		}
		if (returnStatus == 2) {
			pageInfo.setMsg("该操作已失败");
            pageInfo.setCode(ResultInfo.TRADING_NOT_SUCCESS.getCode());
			return pageInfo;
		}

		// 7、 向银行提交请求
        Map<String, Object> resMap = hxAccountRecharge.queryAccountRecharge(oldReqseqno, "PC", BANKURL, logHeard);

		if (resMap != null) {
			// resMap.put("OLDREQSEQNO", oldReqseqno);// 测试用，开发删除
			String errorCode = resMap.get("errorCode").toString();
            if (errorCode.equals("0")) { // 以下信息，只有errorCode =0时才返回，即正常响应时才返回
				String status = resMap.get("status").toString();
                if (status.equals("0")) { // 受理成功，不代表操作成功
					String resultStatus = resMap.get("RETURN_STATUS").toString();
					if (resultStatus.equals("S")) {
						// 处理成功
						if (rightOption(resMap, uId) > 0) {
                            pageInfo.setCode(ResultInfo.SUCCESS.getCode());
							pageInfo.setMsg("充值成功");
							pageInfo.setObj(accountFlow.getMoney());
						} else {
                            pageInfo.setCode(ResultInfo.UPDATE_FAIL.getCode());
							pageInfo.setMsg("更新数据失败");
						}
					} else if (resultStatus.equals("F")) {
						// 处理失败
                        String errormsg = resMap.get("ERRORMSG").toString();
						errorOption(resMap, uId);
                        pageInfo.setCode(ResultInfo.NO_SUCCESS.getCode());
                        pageInfo.setMsg(errormsg);
					} else {
						// 处理中
						processOption(resMap, uId, resultStatus);
                        pageInfo.setCode(ResultInfo.ING.getCode());
						pageInfo.setMsg("处理中");
					}
                } else if (status.equals("1")) { // 受理失败，交易可置为失败
					errorOption(resMap, uId);
                    pageInfo.setCode(ResultInfo.TRADING_NOT_SUCCESS.getCode());
					pageInfo.setMsg("银行受理失败");
				} else {
                    pageInfo.setCode(ResultInfo.ING.getCode());
					pageInfo.setMsg("处理中");
				}
            } else { // 错误，返回具体错误原因
				// 如果返回code不为访问频繁
				if (!resMap.get("errorCode").equals("OGW100200009")) {
					// 错误，返回具体错误原因
					String errorMsg = resMap.get("errorMsg").toString();
					// 如果无此流水号，则把所传流水号添加到map
					if (resMap.get("errorCode").equals("OGWERR999997")) {
						resMap.put("OLDREQSEQNO", oldReqseqno);
					}
					errorOption(resMap, uId);
                    pageInfo.setCode(ResultInfo.TRADING_NOT_SUCCESS.getCode());
					pageInfo.setMsg(errorMsg);
				} else {
                    pageInfo.setCode(ResultInfo.ING.getCode());
					pageInfo.setMsg("处理中");
				}
			}
		} else {
            pageInfo.setCode(ResultInfo.ING.getCode());
			pageInfo.setMsg("处理中");
		}

		return pageInfo;
	}

	/**
	 * 失败操作
	 * 
	 * @param resMap 响应数据
	 * @param uId 用户id
	 */
	private void errorOption(Map<String, Object> resMap, String uId) {

		AccountFlowExample example = new AccountFlowExample();
		example.createCriteria().andNumberEqualTo(resMap.get("OLDREQSEQNO").toString());

		List<AccountFlow> accountFlowList = accountFlowMapper.selectByExample(example);
		if (accountFlowList != null && accountFlowList.size() > 0) {

			// 更新流水状态为失败
			AccountFlow accountFlow = accountFlowList.get(0);
			accountFlow.setStatus(AppCons.ACCOUNT_FLOW_FAIL);
			accountFlowMapper.updateByPrimaryKeySelective(accountFlow);

		}
	}

	/**
	 * 成功操作
	 * 
	 * @param resMap 响应结果
	 * @param uId 用户id
	 * @return int 数据库执行结果
	 */
	private int rightOption(Map<String, Object> resMap, String uId) {
		AccountFlowExample example = new AccountFlowExample();
		example.createCriteria().andNumberEqualTo(resMap.get("OLDREQSEQNO").toString());

		List<AccountFlow> accountFlowList = accountFlowMapper.selectByExample(example);
		if (accountFlowList != null && accountFlowList.size() > 0) {

			// 更新流水状态为成功
			AccountFlow accountFlow = accountFlowList.get(0);
			if (accountFlow.getStatus() == 0) {
				accountFlow.setStatus(1);
				int res = accountFlowMapper.updateByPrimaryKeySelective(accountFlow);
				if (res > 0) {
					// 更新users_account表中的balance，增加
					UsersAccount usersAccount = usersAccountMapper.selectByUid(uId);
					usersAccount.setBalance(usersAccount.getBalance().add(accountFlow.getMoney()));
					return usersAccountMapper.updateByPrimaryKeySelective(usersAccount);
				}
			} else {
				return accountFlow.getStatus();
			}
		}
		return 0;
	}

    /**
     * 处理中操作
     * 
     * @param resMap 响应结果
     * @param uId 用户id
     * @param status 状态
     */
	private void processOption(Map<String, Object> resMap, String uId, String status) {

		AccountFlowExample example = new AccountFlowExample();
		example.createCriteria().andNumberEqualTo(resMap.get("OLDREQSEQNO").toString());
		List<AccountFlow> accountFlowList = accountFlowMapper.selectByExample(example);
		if (accountFlowList != null && accountFlowList.size() > 0) {
			AccountFlow accountFlow = accountFlowList.get(0);
			int returnStatus = 0;
			/**
			 * R 页面处理中（客户仍停留在页面操作，25分钟后仍收到此状态可置交易为失败） N 未知（已提交后台，商户需再次发查询接口。） P
			 * 预授权成功（目前未到账，下一工作日到账，当天无需再进行查询，下一工作日上午6点再进行查询，状态会变成S，如状态不变则也无需再查询，
			 * 可在下一工作日在对账文件中确认交易状态。） D 后台支付系统处理中（如果
			 * ERRORMSG值为“ORDER_CREATED”，并超过25分钟未变，则可置交易是失败。其他情况商户需再次发查询接口。
			 * 但2小时后状态仍未变的则可置为异常，无需再发起查询，后续在对账文件中确认交易状态或线下人工处理） S 成功 F 失败
			 * 
			 */
			switch (status) {
			case "P":
                returnStatus = 0; // 订单预授权中
				break;
			case "R":
				long minuteDiff = DateUtils.dateDiffMins(accountFlow.getOperateTime(), new Date());
				if (minuteDiff >= 25) {
                    returnStatus = 2; // 失败
				}
				break;
			case "N":
                returnStatus = 0; // 未知
				break;
			case "D":
                String errormsg = resMap.get("ERRORMSG").toString();
                if (errormsg.equals("ORDER_CREATED")) {
					long minuteDiff1 = DateUtils.dateDiffMins(accountFlow.getOperateTime(), new Date());
					if (minuteDiff1 >= 25) {
                        returnStatus = 2; // 失败
					}
				} else {
					long minuteDiff2 = DateUtils.dateDiffMins(accountFlow.getOperateTime(), new Date());
					if (minuteDiff2 >= 120) {
                        returnStatus = 5; // 异常
					}
				}

				break;

			default:
				break;
			}
			if (returnStatus != 0) {
				accountFlow.setStatus(returnStatus);
				accountFlowMapper.updateByPrimaryKeySelective(accountFlow);
			}
		}
	}

	@Override
	public String opentionAccountRecharge(Document document) {
		if (document != null) {
            LOGGER.info(LOGGROUP + System.currentTimeMillis() + "应答报文\t" + document.asXML());

			// 判断报文status
			Map<String, Object> resMap = HxBaseData.xmlToMap(document);

			// 更新hx_account数据 ORDER_COMPLETED：订单完成
			// ORDER_PRE_AUTHING：订单预授权中（非实时到账，下一工作日到账）

			if (resMap.get("ORDERSTATUS").equals("ORDER_COMPLETED")) {
				// 更新交易流水
				AccountFlowExample example = new AccountFlowExample();
				example.createCriteria().andNumberEqualTo(resMap.get("OLDREQSEQNO").toString());
				List<AccountFlow> list = accountFlowMapper.selectByExample(example);
				
				if (list != null && list.size() > 0) {
					AccountFlow aFlow = list.get(0);
					
					//验证交易流水状态，如果为0，更新否则不更新
					if (aFlow != null && aFlow.getStatus() == 0) {
						
						AccountFlow accountFlow = new AccountFlow();
						accountFlow.setId(aFlow.getId());
						accountFlow.setStatus(1); // 成功

						long res1 = accountFlowMapper.updateByPrimaryKeySelective(accountFlow);
						
						if(res1 > 0) {
							//根据a_id查询users_account表
							UsersAccount ua = usersAccountMapper.selectByPrimaryKey(aFlow.getaId());
							
							// 更新用户余额
							UsersAccount uAccount = new UsersAccount();
							uAccount.setId(ua.getId());
							uAccount.setBalance(ua.getBalance().add(aFlow.getMoney()));
							usersAccountMapper.updateByPrimaryKeySelective(ua);
						}
					}
				} else {
					LOGGER.info(resMap.get("OLDREQSEQNO").toString() + "不存在");
				}
			}
			// 生成应答返回报文
            String retMsg = hxAccountRecharge.generatinAsynsReply(resMap, LOGGROUP);
			if (retMsg != null) {
				return retMsg;
			}
		}
		return "";
	}
}
