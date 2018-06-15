package com.mrbt.lingmoney.service.web.impl;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;

import com.mrbt.lingmoney.mapper.AccountFlowMapper;
import com.mrbt.lingmoney.mapper.TradingMapper;
import com.mrbt.lingmoney.mapper.UserUnionInfoMapper;
import com.mrbt.lingmoney.mapper.UsersAccountMapper;
import com.mrbt.lingmoney.mapper.UsersMessageMapper;
import com.mrbt.lingmoney.model.UsersAccount;
import com.mrbt.lingmoney.model.UsersMessageExample;
import com.mrbt.lingmoney.service.web.UsersIndexService;
import com.mrbt.lingmoney.utils.PageInfo;
import com.mrbt.lingmoney.utils.ResultParame.ResultInfo;
import com.mrbt.lingmoney.utils.StringOpertion;

/**
 * @author syb
 * @date 2017年5月9日 下午4:06:24
 * @version 1.0
 * @description
 **/
@Service
public class UsersIndexServiceImpl implements UsersIndexService {
	@Autowired
	private UserUnionInfoMapper userUnionInfoMapper;
	@Autowired
	private UsersMessageMapper usersMessageMapper;
	@Autowired
	private UsersAccountMapper usersAccountMapper;
	@Autowired
	private AccountFlowMapper accountFlowMapper;
	@Autowired
	private TradingMapper tradingMapper;
	
	static final DecimalFormat df = new DecimalFormat("#,##0.00");

	@Override
	public void packageUsersInfo(ModelMap modelMap, HttpServletRequest request, String uid) {
		// 未读消息的数量
		UsersMessageExample umExample = new UsersMessageExample();
		umExample.createCriteria().andUIdEqualTo(uid).andStatusEqualTo(0);
		int unreadMessageCount = (int) usersMessageMapper.countByExample(umExample);
		modelMap.addAttribute("messageCount", unreadMessageCount);
		request.getSession().setAttribute("unRead", unreadMessageCount);
		
		// 查询资金帐户的信息
		UsersAccount userAccount = usersAccountMapper.selectByUid(uid);
		// 页面所需展示数据
		UsersAccount viewAccount = new UsersAccount();
		viewAccount.setCountLingbao(userAccount.getCountLingbao());
		viewAccount.setBalance(userAccount.getBalance());
		viewAccount.setIncome(userAccount.getIncome());
		viewAccount.setFrozenMoney(userAccount.getFrozenMoney());
		modelMap.addAttribute("UserAccount", viewAccount);
		
		// 页面展示金额，在此格式化
		DecimalFormat df = new DecimalFormat("#,##0.00");
		String accountBalance = df.format(userAccount.getBalance().doubleValue());
		String frozenMoney = df.format(userAccount.getFrozenMoney().doubleValue());
		String availableMoney = df.format(userAccount.getBalance().add(userAccount.getFrozenMoney()).doubleValue());
		modelMap.addAttribute("accountBalance", accountBalance);
		modelMap.addAttribute("frozenMoney", frozenMoney);
		modelMap.addAttribute("availableMoney", availableMoney);
		modelMap.addAttribute("income", df.format(userAccount.getIncome()));
		
		List<Map<String, Object>> usersAccountList = new ArrayList<Map<String, Object>>();
		// 理财用户现财金额前三的信息
		List<Map<String, Object>> listUsersAccount = usersAccountMapper.queryUsersFinancialRanking();
        if (listUsersAccount != null && listUsersAccount.size() > 0) {
			for (int i = 0; i < listUsersAccount.size(); i++) {
				Map<String, Object> map = listUsersAccount.get(i);
				map.put("account", StringOpertion.hideTelephone(map.get("account").toString()));
				usersAccountList.add(map);
			}
		}
		//查询指定用户的理财排名
		List<Map<String, Object>> rankingList = usersAccountMapper.queryUsersRanking(uid);
        if (rankingList != null && rankingList.size() > 0) {
			Map<String, Object> rankingMap = rankingList.get(0);
			rankingMap.put("account", StringOpertion.hideTelephone(rankingMap.get("account").toString()));
			usersAccountList.add(rankingMap);
			modelMap.addAttribute("ranking", rankingMap.get("ranking"));
		}
		modelMap.addAttribute("listUsersAccount", usersAccountList);
		// 当前理财信息
		Map<String, BigDecimal> financialInfo = tradingMapper.queryUserPresentFinancialMoneyAndIncome(uid);
		modelMap.addAttribute("financialInfo", financialInfo);
	}

	@Override
	public void queryHxAccountInfo(ModelMap model, String uId) {
		//查询华兴用户账户余额信息
		UsersAccount usersAccount = usersAccountMapper.selectByUid(uId);
		
		// 页面展示金额，在此格式化
		String accountBalance = df.format(usersAccount.getBalance().doubleValue());
		String frozenMoney = df.format(usersAccount.getFrozenMoney().doubleValue());
		String availableMoney = df.format(usersAccount.getBalance().add(usersAccount.getFrozenMoney()).doubleValue());
		model.addAttribute("accountBalance", accountBalance);
		model.addAttribute("frozenMoney", frozenMoney);
		model.addAttribute("availableMoney", availableMoney);
	}

	@Override
	public PageInfo queryHxAccountInfoOfWap(PageInfo pageInfo, String uId) {
		List<Map<String, Object>> listMap = new ArrayList<Map<String, Object>>();
		Map<String, Object> map = new HashMap<String, Object>();
		// 查询华兴用户账户余额信息
		UsersAccount usersAccount = usersAccountMapper.selectByUid(uId);

		// 页面展示金额，在此格式化
		String accountBalance = df.format(usersAccount.getBalance().doubleValue());
		String frozenMoney = df.format(usersAccount.getFrozenMoney().doubleValue());
		String availableMoney = df.format(usersAccount.getBalance().add(usersAccount.getFrozenMoney()).doubleValue());
		map.put("accountBalance", accountBalance);
		map.put("frozenMoney", frozenMoney);
		map.put("availableMoney", availableMoney);
		listMap.add(map);

		pageInfo.setRows(listMap);
		pageInfo.setCode(ResultInfo.SUCCESS.getCode());
		return pageInfo;
	}

}
