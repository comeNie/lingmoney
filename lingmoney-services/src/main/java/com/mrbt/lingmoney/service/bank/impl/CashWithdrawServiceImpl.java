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
import com.mrbt.lingmoney.bank.withdraw.HxCashWithdraw;
import com.mrbt.lingmoney.commons.exception.PageInfoException;
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
import com.mrbt.lingmoney.service.bank.CashWithdrawService;
import com.mrbt.lingmoney.service.users.VerifyService;
import com.mrbt.lingmoney.utils.PageInfo;
import com.mrbt.lingmoney.utils.PropertiesUtil;
import com.mrbt.lingmoney.utils.ResultParame.ResultInfo;
import com.mrbt.lingmoney.utils.Validation;

/**
 * 提现
 */
@Service
public class CashWithdrawServiceImpl implements CashWithdrawService {

	@Autowired
	private HxCashWithdraw hxCashWithdraw;

	@Autowired
	private HxAccountMapper hxAccountMapper;

	@Autowired
	private UsersAccountMapper usersAccountMapper;

	@Autowired
	private UsersPropertiesMapper usersPropertiesMapper;

	@Autowired
	private VerifyService verifyService;

	@Autowired
	private AccountFlowMapper accountFlowmapper;

    private static final Logger LOGGER = LogManager.getLogger(CashWithdrawServiceImpl.class);

    private static final String LOGHEARD = "\nHxCashWithdraw_";

    private static final String BANKURL = PropertiesUtil.getPropertiesByKey("BANK_POST_URL");

	private static final String CASH_WITHDRAW_RETURN_URL = PropertiesUtil.getPropertiesByKey("CASH_WITHDRAW_RETURN_URL");

	@Override
	public PageInfo requestCashWithdraw(int clientType, String appId, String amount, String uId) throws Exception {
		// 生成日志头
        String logGroup = LOGHEARD + System.currentTimeMillis() + "_";

        LOGGER.info("提现请求：" + logGroup);

		PageInfo pageInfo = new PageInfo();

		// 验证用户ID
		try {
			verifyService.verifyUserId(uId);
		} catch (PageInfoException e) {
			pageInfo.setCode(e.getCode());
			pageInfo.setMsg(e.getMessage());
			return pageInfo;
		}

		// 判断用户是否存在
		UsersProperties usersProperties = usersPropertiesMapper.selectByuId(uId);
		if (StringUtils.isEmpty(usersProperties)) {
			pageInfo.setMsg("用户不存在");
            pageInfo.setCode(ResultInfo.ACCOUNT_NUMBER_NON_EXIST.getCode());
			return pageInfo;
		}

		// 防止重复提交 TODO
		UsersAccount ua = usersAccountMapper.selectByUid(uId);
		// AccountFlowExample accountFlowExample = new AccountFlowExample();
		// accountFlowExample.createCriteria().andAIdEqualTo(ua.getId()).andTypeEqualTo(1).andStatusEqualTo(0)
		// .andOperateTimeGreaterThan(new
		// SimpleDateFormat("yyyy-MM-dd").parse("2017-06-01"));
		// long count = accountFlowmapper.countByExample(accountFlowExample);
		// if (count > 0) {
		// pageInfo.setMsg("请求未完毕，请勿重复请求");
        // pageInfo.setCode(ResultInfo.REQUEST_AGAIN.getCode());
		// return pageInfo;
		// }

		// 判断金额
		if (!Validation.MatchMoney(amount) || new BigDecimal(amount).compareTo(new BigDecimal(0)) <= 0) {
			pageInfo.setMsg("提现金额格式有误");
            pageInfo.setCode(ResultInfo.SUBMIT_PARAMS_ERROR.getCode());
			return pageInfo;
		}

		// 是否开通华兴E账户
		if (usersProperties.getCertification() == 0 || usersProperties.getCertification() == 1) {
            pageInfo.setMsg("未开通账户,请先开通账户");
            pageInfo.setCode(ResultInfo.NOT_HX_ACCOUNT.getCode());
			return pageInfo;
		}

		// 已开通未激活绑卡
		if (usersProperties.getBank() == 0  || usersProperties.getBank() == 1) {
            pageInfo.setMsg("账户未绑卡,请先绑卡");
            pageInfo.setCode(ResultInfo.HX_ACCOUNT_NOT_ACTIVATION.getCode());
			return pageInfo;
		}

		// 判断余额
		if (ua.getBalance().compareTo(new BigDecimal(amount)) < 0) {
			pageInfo.setMsg("账户余额不足");
            pageInfo.setCode(ResultInfo.BALANCE_INSUFFICIENT.getCode());
			return pageInfo;
		}

		// 从数据库中取出华兴E账户信息
		HxAccountExample example = new HxAccountExample();
		example.createCriteria().andUIdEqualTo(uId).andStatusEqualTo(1);
		List<HxAccount> hxList = hxAccountMapper.selectByExample(example);
		if (StringUtils.isEmpty(hxList) || hxList.size() == 0) {
			pageInfo.setMsg("尚未开通华兴E账户");
            pageInfo.setCode(ResultInfo.NOT_HX_ACCOUNT.getCode());
			return pageInfo;
		}
		HxAccount hxAccount = hxList.get(0);

		// 银行户名
		String acName = hxAccount.getAcName();

		// 银行账号
		String acNo = hxAccount.getAcNo();

		// 备注
		String remark = "用户" + uId + "，单笔提现，金额为" + amount;

		// 生成报文
		Map<String, String> contentMap = hxCashWithdraw.requestCashWithdraw(clientType, appId, acNo, acName, amount,
				remark, uId, CASH_WITHDRAW_RETURN_URL, logGroup);
		if (StringUtils.isEmpty(contentMap) || contentMap.size() == 0) {
			pageInfo.setMsg("提现请求失败");
            pageInfo.setCode(ResultInfo.NO_SUCCESS.getCode());
			return pageInfo;
		}

		// 提现信息入库
		AccountFlow accountFlow = new AccountFlow();
		accountFlow.setaId(ua.getId());
		accountFlow.setOperateTime(new Date());
		accountFlow.setMoney(new BigDecimal(amount));
		accountFlow.setNumber(contentMap.get("channelFlow"));
		accountFlow.setStatus(0);
		accountFlow.setType(1);
		accountFlow.setPlatform(clientType);
		long resInsert = accountFlowmapper.insert(accountFlow);

		// 用户余额减少，冻结金额增加
		ua.setBalance(ua.getBalance().subtract(new BigDecimal(amount)));
		ua.setFrozenMoney(ua.getFrozenMoney().add(new BigDecimal(amount)));
		long resUpdate = usersAccountMapper.updateByPrimaryKeySelective(ua);

		if (resInsert > 0 && resUpdate > 0) {
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
	}

	@Override
	public String cashWithdrawCallBack(Document document) throws Exception {
		// 生成日志头
        String logGroup = LOGHEARD + System.currentTimeMillis() + "_";

		// 数据验签，返回xml文档
		if (!StringUtils.isEmpty(document)) {
            LOGGER.info(logGroup + "应答报文\t" + document.asXML());
			// 判断报文status
			Map<String, Object> resMap = HxBaseData.xmlToMap(document);

			if (resMap != null) {

				String transCode = resMap.get("TRANSCODE").toString();
				String channelFlow = resMap.get("channelFlow").toString();
				String status = "0";
				String errorCode = "0"; 
				String errorMsg = "受理成功";
				String returncode = "000000";
				String returnmsg = "交易成功";
				String oldreqseqno = resMap.get("OLDREQSEQNO").toString();
				String orderstatus = resMap.get("ORDERSTATUS").toString();
				
				// 通过提现交易流水号查询交易流水
				AccountFlowExample example = new AccountFlowExample();
				example.createCriteria().andNumberEqualTo(oldreqseqno).andTypeEqualTo(1).andStatusEqualTo(0);
				List<AccountFlow> list = accountFlowmapper.selectByExample(example);
				if (list != null && list.size() > 0) {
					
					/**
					 * ORDER_COMPLETED：订单完成
					 * ORDER_PRE_AUTHING：订单预授权中（非实时到账，下一工作日到账）
					 * 
					 */
					if ("ORDER_COMPLETED".equals(orderstatus)) {
                        LOGGER.info(logGroup + "\n订单" + oldreqseqno + "提现订单状态为" + orderstatus + "订单完成");
                        
                        AccountFlow accountFlow = list.get(0);
                        //更新流水状态
                        UsersAccount ua = usersAccountMapper.selectByPrimaryKey(accountFlow.getaId());
                        if (ua != null) {
                        	AccountFlowExample upexample = new AccountFlowExample();
                            upexample.createCriteria().andNumberEqualTo(oldreqseqno).andTypeEqualTo(1).andStatusEqualTo(0);
                            
                            AccountFlow upAccountFlow = new AccountFlow();
                            upAccountFlow.setStatus(1);
                            
                            int rel = accountFlowmapper.updateByExampleSelective(upAccountFlow, upexample);
                            if (rel > 0) {
                            	BigDecimal frozen = ua.getFrozenMoney().subtract(accountFlow.getMoney());

                            	UsersAccount upUsersAccount = new UsersAccount();
                            	upUsersAccount.setId(ua.getId());
                            	upUsersAccount.setFrozenMoney(frozen.doubleValue() <= 0 ? new BigDecimal(0) : frozen);
            					int rel2 = usersAccountMapper.updateByPrimaryKeySelective(upUsersAccount);
            					LOGGER.info(logGroup + "\n订单" + oldreqseqno + "提现订单状态为" + orderstatus + "更新流水状态为" + rel + "更新用户冻结金额状态为" + rel2);
    						}
						} else {
							status = "1";
							errorCode = "5003";
							errorMsg = "没有对应的用户";
						}
					} else if ("ORDER_PRE_AUTHING".equals(orderstatus)) {
                        LOGGER.info(logGroup + "\n订单" + oldreqseqno + "提现订单状态为" + orderstatus + "订单预授权中（非实时到账，下一工作日到账）");
					}
				} else {
					status = "1";
					errorCode = "5003";
					errorMsg = "交易不存在,或已经处理完成";
				}

				// 生成应答返回报文
				String retMsg = hxCashWithdraw.createCashWithdrawAsyncMsg(transCode, channelFlow, status, errorCode,
						errorMsg, returncode, returnmsg, oldreqseqno, logGroup);
				if (!StringUtils.isEmpty(retMsg)) {
					return retMsg;
				}
			}
		}
		return "报文不存在";
	}

}
