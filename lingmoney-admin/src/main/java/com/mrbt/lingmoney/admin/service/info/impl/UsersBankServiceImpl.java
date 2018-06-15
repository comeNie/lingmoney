package com.mrbt.lingmoney.admin.service.info.impl;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mrbt.lingmoney.admin.service.info.UsersBankService;
import com.mrbt.lingmoney.mapper.UsersBankMapper;
import com.mrbt.lingmoney.model.UsersBank;
import com.mrbt.lingmoney.utils.PageInfo;
import com.mrbt.lingmoney.utils.StringOpertion;

/**
 * 用户银行卡
 */
@Service
public class UsersBankServiceImpl implements UsersBankService {

	@Autowired
	private UsersBankMapper usersBankMapper;

	@Transactional
	@Override
	public void update(UsersBank vo) {
		usersBankMapper.updateByPrimaryKeySelective(vo);
	}


	/**
	 * 通过条件查询用户绑卡信息（敏感信息特殊处理）
	 * 
	 * @param pageInfo
	 */
	@Override
	public void findDataGrid(PageInfo pageInfo) {
		List<UsersBank> list = usersBankMapper.queryByCondition(pageInfo);
		if (list != null && list.size() > 0) {
			int size = list.size();
			UsersBank usersBank = null;
			for (int i = 0; i < size; i++) {
				usersBank = list.get(i);
				if (usersBank != null) {
					if (StringUtils.isNotBlank(usersBank.getUserTel())) {
						usersBank.setUserTel(StringOpertion
								.hideTelephone(usersBank.getUserTel()));
					}
					if (StringUtils.isNotBlank(usersBank.getTel())) {
						usersBank.setTel(StringOpertion.hideTelephone(usersBank
								.getTel()));
					}
					if (StringUtils.isNotBlank(usersBank.getUserName())) {
						usersBank.setUserName(StringOpertion.hideName(usersBank
								.getUserName()));
					}
					if (StringUtils.isNotBlank(usersBank.getNumber())) {
						usersBank.setNumber(StringOpertion
								.hideBankCard(usersBank.getNumber()));
					}
				}
			}
		}
		pageInfo.setRows(list);
		pageInfo.setTotal(usersBankMapper.queryCountByCondition(pageInfo));
	}

}
