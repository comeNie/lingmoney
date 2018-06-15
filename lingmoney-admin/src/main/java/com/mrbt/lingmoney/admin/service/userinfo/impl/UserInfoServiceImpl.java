package com.mrbt.lingmoney.admin.service.userinfo.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.dom4j.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mrbt.lingmoney.admin.service.userinfo.UserInfoService;
import com.mrbt.lingmoney.bank.eaccount.HxAccountBanlance;
import com.mrbt.lingmoney.mapper.AccountFlowMapper;
import com.mrbt.lingmoney.mapper.UsersPropertiesMapper;
import com.mrbt.lingmoney.model.AccountFlow;
import com.mrbt.lingmoney.model.AccountFlowExample;
import com.mrbt.lingmoney.utils.PageInfo;
import com.mrbt.lingmoney.utils.ResultParame;
import com.mrbt.lingmoney.utils.StringOpertion;

/** 
 * @author  suyibo 
 * @date 创建时间：2018年4月10日
 */
@Service
public class UserInfoServiceImpl implements UserInfoService {
	@Autowired
	private UsersPropertiesMapper usersPropertiesMapper;

	@Autowired
	private HxAccountBanlance hxAccountBanlance;

	@Autowired
	private AccountFlowMapper accountFlowMapper;

	@Override
	public PageInfo findUserInfoByParams(String uid, String telephone, String name) {
		PageInfo pageInfo = new PageInfo();
		// 封装参数
		Map<String, Object> params = new HashMap<String, Object>();
		if (StringUtils.isNotEmpty(uid) || StringUtils.isNotEmpty(telephone) || StringUtils.isNotEmpty(name)) {
			params.put("uid", uid);
			params.put("telephone", telephone);
			params.put("name", name);
			Map<String, Object> userInfo = usersPropertiesMapper.findUserInfoByParams(params);
			if (null != userInfo) {
				try {
					Document result = hxAccountBanlance.requestAccountBalance(String.valueOf(userInfo.get("app_id")),
							String.valueOf(userInfo.get("ac_name")), String.valueOf(userInfo.get("ac_no")));
					if (null == result || !"0".equals(result.selectSingleNode("//errorCode").getText())) {
						pageInfo.setCode(ResultParame.ResultInfo.DO_FAIL.getCode());
						pageInfo.setMsg("查询华兴余额失败");
					} else {
						userInfo.put("ACCTBAL", result.selectSingleNode("//ACCTBAL").getText());
					}
					userInfo.put("cardNumber",
							StringOpertion.hideBankCardLast4(String.valueOf(userInfo.get("bind_card"))));
					pageInfo.setCode(ResultParame.ResultInfo.SUCCESS.getCode());
				} catch (Exception e) {
					e.printStackTrace();
				}
			} else {
				pageInfo.setCode(ResultParame.ResultInfo.NOT_FOUND_DATA.getCode());
				pageInfo.setMsg("查询无数据");
			}
			pageInfo.setObj(userInfo);
		}
		return pageInfo;
	}

	@Override
	public PageInfo findAccountFlowByAId(Integer aid) {
		PageInfo pageInfo = new PageInfo();
		AccountFlowExample example = new AccountFlowExample();
		example.createCriteria().andAIdEqualTo(aid);
		example.setOrderByClause("operate_time desc");
		List<AccountFlow> accountFlowList = accountFlowMapper.selectByExample(example);
		if (!accountFlowList.isEmpty()) {
			for (AccountFlow accountFlow : accountFlowList) {
				if (null != accountFlow.getCardPan()) {
					accountFlow.setCardPan(StringOpertion.hideBankCardLast4(String.valueOf(accountFlow.getCardPan())));
				}
			}
			pageInfo.setCode(ResultParame.ResultInfo.SUCCESS.getCode());
			pageInfo.setRows(accountFlowList);
		} else {
			pageInfo.setCode(ResultParame.ResultInfo.NOT_FOUND_DATA.getCode());
			pageInfo.setMsg("无流水数据");
		}
		return pageInfo;
	}

}
