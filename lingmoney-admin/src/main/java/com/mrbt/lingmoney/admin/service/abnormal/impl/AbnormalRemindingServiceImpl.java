package com.mrbt.lingmoney.admin.service.abnormal.impl;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mrbt.lingmoney.admin.service.abnormal.AbnormalRemindingService;
import com.mrbt.lingmoney.mapper.AccountFlowMapper;
import com.mrbt.lingmoney.mapper.HxAccountMapper;
import com.mrbt.lingmoney.mapper.SynUserDataMapper;
import com.mrbt.lingmoney.mapper.TradingMapper;
import com.mrbt.lingmoney.mapper.UsersAccountMapper;
import com.mrbt.lingmoney.mapper.UsersPropertiesMapper;
import com.mrbt.lingmoney.model.AccountFlow;
import com.mrbt.lingmoney.model.AccountFlowExample;
import com.mrbt.lingmoney.model.HxAccount;
import com.mrbt.lingmoney.model.HxAccountExample;
import com.mrbt.lingmoney.model.SynUserData;
import com.mrbt.lingmoney.model.SynUserDataExample;
import com.mrbt.lingmoney.model.Trading;
import com.mrbt.lingmoney.model.TradingExample;
import com.mrbt.lingmoney.model.UsersAccount;
import com.mrbt.lingmoney.model.UsersProperties;
import com.mrbt.lingmoney.utils.MailUtil;
import com.mrbt.lingmoney.utils.PropertiesUtil;
import com.mrbt.lingmoney.utils.ResultParame.ResultNumber;

/**
 * @author ruochen.yu
 *
 */
@Service
public class AbnormalRemindingServiceImpl implements AbnormalRemindingService {
	
	public static final Logger LOGGER = LogManager.getLogger(AbnormalRemindingServiceImpl.class);

	// 邮件发送者信息
	private final static String smtpHost = PropertiesUtil.getPropertiesByKey("config/email.properties", "smtpHost");
	private final static String sender = PropertiesUtil.getPropertiesByKey("config/email.properties", "sender");
	private final static String userName = PropertiesUtil.getPropertiesByKey("config/email.properties", "user");
	private final static String pwd = PropertiesUtil.getPropertiesByKey("config/email.properties", "pwd");
	
	@Autowired
	private AccountFlowMapper accountFlowMapper;
	
	@Autowired
	private UsersAccountMapper usersAccountMapper;
	
	@Autowired
	private SynUserDataMapper synUserDataMapper;
	
	@Autowired
	private HxAccountMapper hxAccountMapper;
	
	@Autowired
	private UsersPropertiesMapper usersPropertiesMapper;
	
	@Autowired
	private TradingMapper tradingMapper;
	
	private static final String CSS_STR = "<style>table,table tr th, table tr td { border:1px solid #0094ff; border-collapse: collapse;}</style>";
	
	private static final String REMARK_INFO = "<h5>注意：请通过手机号查询后台，确定操作是否成功！</h5>";
	
	/**
	 * 流水异常提醒功能
	 * @throws ParseException 
	 */
	@Override
	public void rechargeLossProcessing(String logGroup, StringBuffer emailContent, Date queryDate) throws Exception {
		
		LOGGER.info(logGroup + "查询用户充值流水失败数据.... ");
		
		//查询用户充值流水异常数据
		AccountFlowExample example = new AccountFlowExample();
		example.createCriteria().andTypeEqualTo(ResultNumber.ZERO.getNumber()).andOperateTimeGreaterThanOrEqualTo(queryDate).andStatusEqualTo(ResultNumber.TWO.getNumber());
		example.setOrderByClause(" a_id");
		List<AccountFlow> accountFlowsFail = accountFlowMapper.selectByExample(example);
		
		//生成充值流水头部
		emailTableHeard(emailContent, "充值");
		
		//aId去重记录
		Map<Integer, Object> distinctMap = new HashMap<Integer, Object>();
		
		//验证有无成功流水查询条件
		List<Integer> statusList = new ArrayList<Integer>();
		statusList.add(0);
		statusList.add(1);
		
		for (AccountFlow accountFlow : accountFlowsFail) {
			
			//aId去重验证
			if(distinctMap.containsKey(accountFlow.getaId())){
				continue;
			}
			
			AccountFlowExample example2 = new AccountFlowExample();
			example2.createCriteria().andAIdEqualTo(accountFlow.getaId()).andTypeEqualTo(ResultNumber.ZERO.getNumber()).andOperateTimeGreaterThanOrEqualTo(queryDate).andStatusIn(statusList);
			List<AccountFlow> accountFlowsSuccess = accountFlowMapper.selectByExample(example2);
			
			if (accountFlowsSuccess != null && accountFlowsSuccess.size() > 0) {
				
				//验证是否要发送邮件
				rechargeVerifySend(accountFlowsSuccess, accountFlow, emailContent);
				
			} else {
				
				LOGGER.info(accountFlow.getaId() + "用户没有充值成功的记录，发送邮件提醒");
				//查询用户信息,拼接发送邮件正文
				queryUsersInfo(accountFlow, emailContent);
				
			}
			
			//添加aId到去重map中
			distinctMap.put(accountFlow.getaId(), "");
		}
		
		//拼接表格尾部
		emailTableEnd(emailContent);
	}

	@Override
	public void putforwardLossProcessing(String logGroup, StringBuffer emailContent, Date queryDate) throws Exception {
		LOGGER.info(logGroup + "查询用户提现流水失败数据.... ");
		
		//查询用户提现流水异常数据
		AccountFlowExample example = new AccountFlowExample();
		example.createCriteria().andTypeEqualTo(ResultNumber.ONE.getNumber()).andOperateTimeGreaterThanOrEqualTo(queryDate).andStatusEqualTo(ResultNumber.TWO.getNumber());
		example.setOrderByClause(" a_id");
		List<AccountFlow> accountFlowsFail = accountFlowMapper.selectByExample(example);
		
		emailTableHeard(emailContent, "提现");
		
		//aId去重记录
		Map<Integer, Object> distinctMap = new HashMap<Integer, Object>();
		
		//验证有无成功流水查询条件
		List<Integer> statusList = new ArrayList<Integer>();
		statusList.add(0);
		statusList.add(1);
		
		for (AccountFlow accountFlow : accountFlowsFail) {
			
			//aId去重验证
			if(distinctMap.containsKey(accountFlow.getaId())){
				continue;
			}
			
			AccountFlowExample example2 = new AccountFlowExample();
			example2.createCriteria().andAIdEqualTo(accountFlow.getaId()).andTypeEqualTo(ResultNumber.ONE.getNumber()).andOperateTimeGreaterThanOrEqualTo(queryDate).andStatusIn(statusList);
			List<AccountFlow> accountFlowsSuccess = accountFlowMapper.selectByExample(example2);
			
			if (accountFlowsSuccess != null && accountFlowsSuccess.size() > 0) {
				
				//验证是否要发送邮件
				putforwardVerifySend(accountFlowsSuccess, accountFlow, emailContent);
				
			} else {
				LOGGER.info(accountFlow.getaId() + "用户没有提现成功的记录，发送邮件提醒");
				//查询用户信息,拼接发送邮件正文
				queryUsersInfo(accountFlow, emailContent);
			}
			//添加aId到map中
			distinctMap.put(accountFlow.getaId(), "");
		}
		
		//拼接表格尾部
		emailTableEnd(emailContent);
	}
	

	@Override
	public void openAccountLossProcessing(String logGroup, StringBuffer emailContent, Date queryDate) throws Exception {
		LOGGER.info(logGroup + "查询用户开户流水失败数据.... ");
		
		//查询开户异常的数据
		HxAccountExample example = new HxAccountExample();
		example.createCriteria().andStatusEqualTo(ResultNumber.MINUS_ONE.getNumber()).andCreateTimeGreaterThan(queryDate);
		
		List<HxAccount> hxAccounts = hxAccountMapper.selectByExample(example);
		
		emailTableHeard(emailContent, "开户");
		
		List<String> statusList = new ArrayList<String>();
		statusList.add("0");
		statusList.add("1");
		
		Map<String, Object> distinctMap = new HashMap<String, Object>();//aId去重记录
		
		for (HxAccount hxAccount : hxAccounts) {
			
			if (distinctMap.containsKey(hxAccount.getuId())) {
				continue;
			}
			
			//查询用户是否有开通成功或正在开通的数据
			HxAccountExample example2 = new HxAccountExample();
			example2.createCriteria().andUIdEqualTo(hxAccount.getuId()).andCreateTimeGreaterThan(queryDate).andStatusidIn(statusList);			
			
			List<HxAccount> hxAccounts2 = hxAccountMapper.selectByExample(example2);
			
			if(hxAccounts2 != null && hxAccounts2.size() > 0) {
				
				//验证是否要发送邮件
				openAccountVerifySend(hxAccounts2, emailContent);
				
			} else {
				LOGGER.info(hxAccount.getuId() + "用户没有提现成功的记录，发送邮件提醒");
				//查询用户信息,拼接发送邮件正文
				emailContent.append("<tr>");
				emailContent.append("<td>" + hxAccount.getAcName() + "</td><td>" + hxAccount.getMobile() + "</td><td>" + hxAccount.getId() + "</td><td>" + hxAccount.getuId() + "</td>");
				emailContent.append("</tr>");
			}
			
			distinctMap.put(hxAccount.getuId(), "");
		}
		
		//拼接表格尾部
		emailTableEnd(emailContent);
	}


	@Override
	public void bindCardLossProcessing(String logGroup, StringBuffer emailContent, Date queryDate) throws Exception {
		LOGGER.info(logGroup + "查询用户提现流水失败数据.... ");
		
		emailTableHeard(emailContent, "绑卡");
		
		//查询开户成功，但是没有绑卡的数据
		HxAccountExample example = new HxAccountExample();
		example.createCriteria().andStatusEqualTo(ResultNumber.ONE.getNumber()).andBindCardIsNull().andCreateTimeGreaterThan(queryDate);
		
		List<HxAccount> hxAccounts = hxAccountMapper.selectByExample(example);
		
		for (HxAccount hxAccount : hxAccounts) {
			//验证是否绑卡
			UsersProperties usersProperties = usersPropertiesMapper.selectByuId(hxAccount.getuId());
			if (usersProperties != null) {
				if (usersProperties.getBank() != 2 && usersProperties.getBank() != 3) {
					emailContent.append("<tr>");
					emailContent.append("<td>" + hxAccount.getAcName() + "</td><td>" + hxAccount.getMobile() + "</td><td>" + hxAccount.getId() + "</td><td>" + hxAccount.getuId() + "</td>");
					emailContent.append("</tr>");
				}
			}
		}
		
		//拼接表格尾部
		emailTableEnd(emailContent);
	}

	@Override
	public void tradingLossProcessing(String logGroup, StringBuffer emailContent, Date queryDate) throws Exception {
		LOGGER.info(logGroup + "查询用户交易流水失败数据.... ");
		
		TradingExample example = new TradingExample();
		example.createCriteria().andBuyDtGreaterThan(queryDate).andStatusEqualTo(ResultNumber.EIGHTEEN.getNumber());
		
		List<Trading> tradings = tradingMapper.selectByExample(example);
		
		boolean send = false;
		
		emailTableHeard(emailContent, "交易");
		
		//查询交易成功数据，查询条件
		List<Integer> statusList = new ArrayList<Integer>();
		statusList.add(4);
		statusList.add(1);
		statusList.add(12);
		
		//u_id去重
		Map<String, Object> distinctMap = new HashMap<String, Object>();
		
		for (Trading trading : tradings) {
			
			if (distinctMap.containsKey(trading.getuId())) {
				continue;
			}
			
			TradingExample example2 = new TradingExample();
			example2.createCriteria().andBuyDtGreaterThan(queryDate).andStatusIn(statusList).andUIdEqualTo(trading.getuId());
			
			List<Trading> tradings2 = tradingMapper.selectByExample(example2);
			
			if (tradings2 != null && tradings2.size() > 0) {

				for (Trading trading2 : tradings2) {
					
					long flowTime = new Date().getTime() - trading2.getBuyDt().getTime();
					
					if (trading2.getStatus() == 1 || trading2.getStatus() == 4) {
						LOGGER.info(trading2.getuId() + "用户有交易成功的记录，不进行邮件提醒");
						break;
					} else if (trading2.getStatus() == 12 &&  flowTime > 60 * 60 * 1000 && flowTime < 24 * 60 * 60 * 1000) {
						LOGGER.info(trading2.getuId() + "用户有交易成功的记录，不进行邮件提醒");
						break;
					} else {
						send = true;
					}
				}
			} else {
				send = true;
			}
			
			if (send) {
				queryUsersInfoFromUid(trading, emailContent);
			}
			
			distinctMap.put(trading.getuId(), "");
		}
		
		//拼接表格尾部
		emailTableEnd(emailContent);
	}
	
	
	/**
	 * 拼接表格头部
	 * @param emailContent
	 */
	private void emailTableHeard(StringBuffer emailContent, String title) {
		//邮件内容
		emailContent.append("<h3>" + title + "流水异常数据：</h3>");
		emailContent.append(REMARK_INFO);
		emailContent.append(CSS_STR);
		emailContent.append("<table style='width:100%;'>");
		emailContent.append("<tr><td>用户姓名</td><td>手机号</td><td>aId</td><td>uId</td></tr>");
	}
	
	/**
	 * 拼接表格尾部
	 * @param emailContent
	 */
	private void emailTableEnd(StringBuffer emailContent) {
		emailContent.append("</table>");
	}
	
	
	
	
	/**
	 * 验证充值流水是否要发送邮件
	 * @param accountFlowsSuccess
	 * @param accountFlow 
	 * @param emailContent 
	 */
	private void rechargeVerifySend(List<AccountFlow> accountFlowsSuccess, AccountFlow accountFlow, StringBuffer emailContent) {
		for (AccountFlow accountFlow2 : accountFlowsSuccess) {
			
			long flowTime = new Date().getTime() - accountFlow2.getOperateTime().getTime();
			
			if (accountFlow2.getStatus() == 1) {
				LOGGER.info(accountFlow2.getaId() + "用户有充值成功的记录，不进行邮件提醒");
				break;
			} else if (accountFlow2.getStatus() == 0 && flowTime > 60 * 60 * 1000 && flowTime < 24 * 60 * 60 * 1000) {
				LOGGER.info(accountFlow.getaId() + "用户有充值成功,但没有到账的记录，不进行邮件提醒");
				break;
			} else {
				LOGGER.info(accountFlow.getaId() + "用户没有充值成功的记录，发送邮件提醒");
				//查询用户信息,拼接发送邮件正文
				queryUsersInfo(accountFlow, emailContent);
			}
		}
	}
	
	/**
	 * 验证提现流水是否要发送邮件
	 * @param accountFlowsSuccess
	 * @param accountFlow
	 * @param emailContent
	 */
	private void putforwardVerifySend(List<AccountFlow> accountFlowsSuccess, AccountFlow accountFlow, StringBuffer emailContent) {
		for (AccountFlow accountFlow2 : accountFlowsSuccess) {
			
			long flowTime = new Date().getTime() - accountFlow2.getOperateTime().getTime();
			
			if (accountFlow2.getStatus() == 1) {
				LOGGER.info(accountFlow.getaId() + "用户有提现成功的记录，不进行邮件提醒");
				break;
			} else if (accountFlow2.getStatus() == 0 && flowTime > 60 * 60 * 1000 && flowTime < 24 * 60 * 60 * 1000) {
				LOGGER.info(accountFlow.getaId() + "用户有提现成功,但没有到账的记录，不进行邮件提醒");
				break;
			} else {
				LOGGER.info(accountFlow.getaId() + "用户没有提现成功的记录，发送邮件提醒");
				//查询用户信息,拼接发送邮件正文
				queryUsersInfo(accountFlow, emailContent);
			}
		}
	}
	
	/**
	 * 验证开户异常是否发送邮件
	 * @param hxAccounts2
	 * @param emailContent
	 */
	private void openAccountVerifySend(List<HxAccount> hxAccounts2, StringBuffer emailContent) {
		for (HxAccount hxAccount2 : hxAccounts2) {
			long flowTime = new Date().getTime() - hxAccount2.getCreateTime().getTime();
			
			if (hxAccount2.getStatus() == 1) {
				LOGGER.info(hxAccount2.getuId() + "用户有开户成功的记录，不进行邮件提醒");
				break;
			} else if (hxAccount2.getStatus() == 0 && flowTime > 60 * 60 * 1000 && flowTime < 24 * 60 * 60 * 1000) {
				LOGGER.info(hxAccount2.getuId() + "用户有开户成功,但没有到账的记录，不进行邮件提醒");
				break;
			} else {
				LOGGER.info(hxAccount2.getuId() + "用户没有开户成功的记录，发送邮件提醒");
				//查询用户信息,拼接发送邮件正文
				emailContent.append("<tr>");
				emailContent.append("<td>" + hxAccount2.getAcName() + "</td><td>" + hxAccount2.getMobile() + "</td><td>" + hxAccount2.getId() + "</td><td>" + hxAccount2.getuId() + "</td>");
				emailContent.append("</tr>");
			}
		}
	}
	
	
	/**
	 * 通过 aId查询出用户的姓名，手机号
	 * @param accountFlow
	 * @param emailContent
	 */
	private void queryUsersInfo(AccountFlow accountFlow, StringBuffer emailContent) {
		UsersAccount uAccount = usersAccountMapper.selectByPrimaryKey(accountFlow.getaId());
		
		if (uAccount != null) {
			SynUserDataExample example = new SynUserDataExample();
			example.createCriteria().andUIdEqualTo(uAccount.getuId());
			List<SynUserData> sudList = synUserDataMapper.selectByExample(example);
			
			if (sudList != null && sudList.size() > 0) {
				SynUserData sud = sudList.get(0);
				
				emailContent.append("<tr>");
				emailContent.append("<td>" + sud.getName() + "</td><td>" + sud.getTelephone() + "</td><td>" + accountFlow.getaId() + "</td><td>" + uAccount.getuId() + "</td>");
				emailContent.append("</tr>");
				
			} else {
				LOGGER.info(accountFlow.getaId() + "没有查询到用户信息，请联系技术查证");
			}
		}
		
	}
	
	/**
	 * 通过uId查询用户数据
	 * @param trading
	 * @param emailContent
	 */
	private void queryUsersInfoFromUid(Trading trading, StringBuffer emailContent) {
		SynUserDataExample example5 = new SynUserDataExample();
		example5.createCriteria().andUIdEqualTo(trading.getuId());
		List<SynUserData> sudList = synUserDataMapper.selectByExample(example5);
		
		if (sudList != null && sudList.size() > 0) {
			SynUserData sud = sudList.get(0);
			
			emailContent.append("<tr>");
			emailContent.append("<td>" + sud.getName() + "</td><td>" + sud.getTelephone() + "</td><td>" + trading.getId() + "</td><td>" + trading.getuId() + "</td>");
			emailContent.append("</tr>");
			
		} else {
			LOGGER.info(trading.getuId() + "没有查询到用户信息，请联系技术查证");
		}
		
	}
	
	/**
	 * 发送邮件
	 */
	@Override
	public void sendMail(String context) {
		
		// 邮件接收者
		String receiver = userName;
		// 邮件抄送者
		String copyReceiver = PropertiesUtil.getPropertiesByKey("ERROR_FLOW_SEND_MAIL");
		// 邮件内容
		String title = "领钱儿流水异常提醒";

		try {
			MailUtil.sendEmail(smtpHost, receiver, copyReceiver, title, context, sender, "领钱儿(lingmoney.cn)", userName, pwd, null, null);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
