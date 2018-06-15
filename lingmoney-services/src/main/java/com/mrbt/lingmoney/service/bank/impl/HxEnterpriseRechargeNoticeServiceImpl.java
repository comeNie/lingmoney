package com.mrbt.lingmoney.service.bank.impl;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.dom4j.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mrbt.lingmoney.bank.HxBaseData;
import com.mrbt.lingmoney.bank.enterprise.HxEnterpriseRechargeNotice;
import com.mrbt.lingmoney.mapper.AccountFlowMapper;
import com.mrbt.lingmoney.mapper.EnterpriseRechargeMapper;
import com.mrbt.lingmoney.mapper.HxAccountMapper;
import com.mrbt.lingmoney.mapper.UsersAccountMapper;
import com.mrbt.lingmoney.model.AccountFlow;
import com.mrbt.lingmoney.model.AccountFlowExample;
import com.mrbt.lingmoney.model.EnterpriseRecharge;
import com.mrbt.lingmoney.model.HxAccount;
import com.mrbt.lingmoney.model.HxAccountExample;
import com.mrbt.lingmoney.model.UsersAccount;
import com.mrbt.lingmoney.service.bank.HxEnterpriseRechargeNoticeService;

/**
 *
 *@author syb
 *@date 2017年9月29日 下午3:43:18
 *@version 1.0
 **/
@Service
public class HxEnterpriseRechargeNoticeServiceImpl implements HxEnterpriseRechargeNoticeService {

    private static final Logger LOGGER = LogManager.getLogger(HxEnterpriseRechargeNoticeServiceImpl.class);

	@Autowired
	private HxEnterpriseRechargeNotice hxEnterpriseRechargeNotice;
	
	@Autowired
	private EnterpriseRechargeMapper enterpriseRechargeMapper;
	
	@Autowired
	private HxAccountMapper hxaccountMapper;
	
	@Autowired
	private AccountFlowMapper accountFlowMapper;
	
	@Autowired
	private UsersAccountMapper usersAccountMapper;
	
	private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	@Override
	public String callBackOfEnterpriseRecharge(Document document) {
		String logGroup = "\ncallBackOfEnterpriseRecharge_" + System.currentTimeMillis() + "_";

		String relString = ""; 
		if (document != null) {
			LOGGER.info("{}解密后银行异步通知：\n{}", logGroup, document.asXML());
			

			Map<String, Object> map = HxBaseData.xmlToMap(document);
			
			Map<String, Object> asyncMsg = new HashMap<String, Object>();
			asyncMsg.put("channelFlow", map.get("REQSEQNO"));
			
			String banAccountNo = (String) map.get("BANACCOUNTNO");
			
			//通过E账户获取是企业充值还是个人充值
			HxAccountExample hxAccountExample = new HxAccountExample();
			hxAccountExample.createCriteria().andStatusEqualTo(1).andAcNoEqualTo(banAccountNo);
			List<HxAccount> hxAccountList = hxaccountMapper.selectByExample(hxAccountExample);
			if (hxAccountList != null && hxAccountList.size() > 0) {
				relString = opertionPersonalUsers(map, logGroup, asyncMsg, hxAccountList.get(0));
			} else {
				relString = opertionEnterpriseUsers(map, logGroup, asyncMsg);
			}
		}

		return relString;
	}

	/**
	 * 处理个人账户转账充值
	 * @param map
	 * @param logGroup
	 * @param asyncMsg
	 * @return
	 */
	
	private String opertionPersonalUsers(Map<String, Object> map, String logGroup, Map<String, Object> asyncMsg, HxAccount hxAccount) {
		try {
			//查询users_account表
			UsersAccount ua = usersAccountMapper.selectByUid(hxAccount.getuId());
			
			//查询流水是否存在
			AccountFlowExample afe = new AccountFlowExample();
			afe.createCriteria().andNumberEqualTo(map.get("REQSEQNO").toString());
			
			List<AccountFlow> afList = accountFlowMapper.selectByExample(afe);
			
			BigDecimal amount = new BigDecimal(map.get("AMOUNT").toString());//获取充值金额
			
			//判断来账充值方式，如果查询到为T+1充值，注意，用户绑定华兴银行T+1充值也是事实到账
			if(afList == null || afList.size() == 0) {
				
				//插入流水
				AccountFlow accountFlow = new AccountFlow();
				accountFlow.setaId(ua.getId());
				accountFlow.setOperateTime(operateDateTime(map.get("TRANSDATE").toString(), map.get("TRANSACTIONDATE").toString()));
				accountFlow.setMoney(amount);
				accountFlow.setNumber(map.get("REQSEQNO").toString());//流水号
				accountFlow.setStatus(1);//状态成功
				accountFlow.setType(0);//类型
				accountFlow.setPlatform(9);//来账充值
				
				LOGGER.info("来账充值处理:流水号[" + map.get("REQSEQNO").toString() + "]\t金额[" + amount + "]\t交易时间[" + accountFlow.getOperateTime() + "]");
				
				accountFlowMapper.insertSelective(accountFlow);
				
				//修改用户余额
				UsersAccount usersAccount = new UsersAccount();
				usersAccount.setId(ua.getId());
				usersAccount.setBalance(ua.getBalance().add(amount));
				usersAccountMapper.updateByPrimaryKeySelective(usersAccount);
			} else {
				AccountFlow accountFlow = afList.get(0);
				
				//判断充值流水状态不能为1，通知接口金额==流水记录金额
				if (accountFlow.getStatus() != 1 && accountFlow.getMoney().equals(amount.setScale(2,BigDecimal.ROUND_HALF_DOWN))) {
					
					LOGGER.info("T+1充值处理:流水号[" + map.get("REQSEQNO").toString() + "]\t金额[" + amount + "]\t交易时间[" + operateDateTime(map.get("TRANSDATE").toString(), map.get("TRANSACTIONDATE").toString()) + "]");
					
					AccountFlowExample accountFlowExample = new AccountFlowExample();
					accountFlowExample.createCriteria().andIdEqualTo(accountFlow.getId()).andNumberEqualTo(map.get("REQSEQNO").toString()).andStatusEqualTo(0);
					
					AccountFlow upAccountFlow = new AccountFlow();
					upAccountFlow.setStatus(1);
					
					//更新流水表，条件，ID，number, status=0
					int rel = accountFlowMapper.updateByExampleSelective(upAccountFlow, accountFlowExample);
					
					//判断更新流水状态，如果>0更新余额
					if(rel > 0) {
						//修改用户余额
						UsersAccount usersAccount = new UsersAccount();
						usersAccount.setId(ua.getId());
						usersAccount.setBalance(ua.getBalance().add(amount));
						usersAccountMapper.updateByPrimaryKeySelective(usersAccount);
					}
				}
			}
		} catch (Exception e) {
			LOGGER.error("{}，收到银行充值来账通知，处理失败，系统错误。", logGroup);
			e.printStackTrace();
			return hxEnterpriseRechargeNotice.generatinAsynsReply(asyncMsg, logGroup, false);
		}
		//修改用户余额
		return hxEnterpriseRechargeNotice.generatinAsynsReply(asyncMsg, logGroup, true);
	}
	
	/**
	 * 处理报文中的时间，如果报错，直接返回当前时间
	 * @param date
	 * @param time
	 * @return
	 */
	private Date operateDateTime(String date, String time) {
		try {
			String dateTime = date.substring(0, 4) + "-" + date.substring(4, 6) + "-" + date.substring(6, 8) + " "
					+ time.substring(0, 2) + ":" + time.substring(2, 4) + ":" + time.substring(4, 6);
			return sdf.parse(dateTime);
		} catch (Exception e) {
			return new Date();
		}
	}

	/**
	 * 处理企业用户转账充值
	 * @param map
	 * @param logGroup
	 * @param asyncMsg
	 * @return
	 */
	private String opertionEnterpriseUsers(Map<String, Object> map, String logGroup, Map<String, Object> asyncMsg) {
		try {
			// 数据入库
			EnterpriseRecharge enterpriseRecharge = new EnterpriseRecharge();
			String uuid = UUID.randomUUID().toString().replaceAll("-", "");
			enterpriseRecharge.setId(uuid);
			enterpriseRecharge.setReqSeqNo((String) map.get("REQSEQNO"));
			enterpriseRecharge.setBankAccountNo((String) map.get("BANACCOUNTNO"));
			// DEPOSIT_ACCOUNT：收入 DRAW_ACCOUNT：支出
			enterpriseRecharge.setTransType(((String) map.get("TRANSTYPE")) == "DEPOSIT_ACCOUNT" ? "0" : "1");
			enterpriseRecharge.setOppAccountNo((String) map.get("OPPACCOUNTNO"));
			enterpriseRecharge.setOppAccountName((String) map.get("OPPACCOUNTNAME"));
			String transdate = (String) map.get("TRANSDATE");
			String transactiondate = (String) map.get("TRANSACTIONDATE");
			enterpriseRecharge.setTransDate(transdate + " " + transactiondate);
			enterpriseRecharge.setAmount((String) map.get("AMOUNT"));

			if (map.containsKey("OPPACCOPENBANKNO")) {
				enterpriseRecharge.setOppAccOpenBankNo((String) map.get("OPPACCOPENBANKNO"));
			}
			if (map.containsKey("OPPACCOPENBANKNAME")) {
				enterpriseRecharge.setOppAccOpenBankName((String) map.get("OPPACCOPENBANKNAME"));
			}
			if (map.containsKey("POSTSCRIPT")) {
				enterpriseRecharge.setPostScript((String) map.get("POSTSCRIPT"));
			}
			if (map.containsKey("COMMENTS")) {
				enterpriseRecharge.setComments((String) map.get("COMMENTS"));
			}
			if (map.containsKey("SUMMARYINFO")) {
				enterpriseRecharge.setSummaryInfo((String) map.get("SUMMARYINFO"));
			}
			if (map.containsKey("SUMMARYINFOMSG")) {
				enterpriseRecharge.setSummaryInfoMsg((String) map.get("SUMMARYINFOMSG"));
			}
			if (map.containsKey("EXTFILED1")) {
				enterpriseRecharge.setExtFiled1((String) map.get("EXTFILED1"));
			}
			if (map.containsKey("EXTFILED2")) {
				enterpriseRecharge.setExtField2((String) map.get("EXTFILED2"));
			}
			if (map.containsKey("EXTFILED3")) {
				enterpriseRecharge.setExtField3((String) map.get("EXTFILED3"));
			}

			enterpriseRechargeMapper.insertSelective(enterpriseRecharge);

		} catch (Exception e) {
			LOGGER.error("{}，收到银行充值来账通知，处理失败，系统错误。", logGroup);
			e.printStackTrace();
			return hxEnterpriseRechargeNotice.generatinAsynsReply(asyncMsg, logGroup, false);
		}

		return hxEnterpriseRechargeNotice.generatinAsynsReply(asyncMsg, logGroup, true);
	}
}
