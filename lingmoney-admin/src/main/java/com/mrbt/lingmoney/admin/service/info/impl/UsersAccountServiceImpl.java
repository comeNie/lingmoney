package com.mrbt.lingmoney.admin.service.info.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mrbt.lingmoney.admin.service.info.UsersAccountService;
import com.mrbt.lingmoney.mapper.UsersAccountMapper;
import com.mrbt.lingmoney.model.Condition;
import com.mrbt.lingmoney.model.UsersAccount;
import com.mrbt.lingmoney.model.UsersAccountExample;

/**
 * 用户账户
 * 
 */
@Service
public class UsersAccountServiceImpl implements UsersAccountService {

	@Autowired
	private UsersAccountMapper usersAccountMapper;

	@Transactional
	@Override
	public void delete(int id) {
		usersAccountMapper.deleteByPrimaryKey(id);
	}

	@Transactional
	@Override
	public void save(UsersAccount vo) {
		usersAccountMapper.insertSelective(vo);
	}

	@Transactional
	@Override
	public void update(UsersAccount vo) {
		usersAccountMapper.updateByPrimaryKeySelective(vo);
	}

	@Override
	public UsersAccount getUsersAccountByUid(String uId) {
		UsersAccountExample example = new UsersAccountExample();
		example.createCriteria().andUIdEqualTo(uId);
		List<UsersAccount> list = usersAccountMapper.selectByExample(example);
		return (list == null || list.size() < 1) ? null : list.get(0);
	}

	@Override
	public List<UsersAccount> findAdminMessage(Condition condition) {
		return usersAccountMapper.selectAdminMessageList(condition);
	}
}
