package com.mrbt.lingmoney.service.bank.impl;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.mrbt.lingmoney.bank.eaccount.HxModifyTradingPassword;
import com.mrbt.lingmoney.mapper.HxAccountMapper;
import com.mrbt.lingmoney.mapper.UsersPropertiesMapper;
import com.mrbt.lingmoney.model.HxAccount;
import com.mrbt.lingmoney.model.UsersProperties;
import com.mrbt.lingmoney.service.bank.ModifyTradingPasswordService;
import com.mrbt.lingmoney.utils.PageInfo;
import com.mrbt.lingmoney.utils.PropertiesUtil;
import com.mrbt.lingmoney.utils.ResultParame.ResultInfo;
import com.mrbt.lingmoney.utils.ResultParame.ResultNumber;

/** 
 * @author  suyibo 
 * @date 创建时间：2018年4月19日
 */
@Service
public class ModifyTradingPasswordServiceImpl implements ModifyTradingPasswordService {
	@Autowired
	private UsersPropertiesMapper usersPropertiesMapper;
	@Autowired
	private HxAccountMapper hxAccountMapper;
	@Autowired
	private HxModifyTradingPassword hxModifyTradingPassword;

//	private static final String MODIFY_TRADINGPASSWORD_RETURN_URL = PropertiesUtil
//			.getPropertiesByKey("MODIFY_TRADINGPASSWORD_RETURN_URL");
	private static final String BANKURL = PropertiesUtil.getPropertiesByKey("BANK_POST_URL");

	@Override
	public PageInfo modifyTradingPassword(Integer clientType, String appId, String uId) {
		PageInfo pageInfo = new PageInfo();
		if (StringUtils.isEmpty(clientType) || StringUtils.isEmpty(appId) || StringUtils.isEmpty(uId)) {
			pageInfo.setResultInfo(ResultInfo.PARAM_MISS);
			return pageInfo;
		}

		// 验证客户状态
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
			Map<String, String> resultMap = hxModifyTradingPassword.requestHxModifyTradingPassword(clientType, appId,
					hxAccount.getAcNo(), "", uId);

			resultMap.put("bankUrl", BANKURL);
			pageInfo.setResultInfo(ResultInfo.SUCCESS);
			pageInfo.setObj(resultMap);
		}

		return pageInfo;
	}

}
