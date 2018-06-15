package com.mrbt.lingmoney.admin.service.info.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mrbt.lingmoney.admin.service.info.UsersMessageService;
import com.mrbt.lingmoney.mapper.UsersMessageMapper;
import com.mrbt.lingmoney.model.UsersMessage;
import com.mrbt.lingmoney.model.UsersMessageExample;
import com.mrbt.lingmoney.model.UsersMessageExample.Criteria;
import com.mrbt.lingmoney.utils.PageInfo;
import com.mrbt.lingmoney.utils.ResultParame;

/**
 * 用户消息
 */
@Service
public class UsersMessageServiceImpl implements UsersMessageService {

	@Autowired
	private UsersMessageMapper usersMessageMapper;
	
	@Override
	public String listContent(Integer id) {
		UsersMessage message = usersMessageMapper.selectByPrimaryKey(id);
		if (message != null) {
			return message.getContent();
		}
		return "";
	}


	@Override
	public void listGrid(UsersMessage vo, PageInfo pageInfo) {
		UsersMessageExample example = createUsersMessageExample(vo);
		example.setLimitStart(pageInfo.getFrom());
		example.setLimitEnd(pageInfo.getPagesize());
		pageInfo.setRows(usersMessageMapper.selectByExample(example));
		pageInfo.setTotal((int) usersMessageMapper.countByExample(example));
	}

	@Override
	@Transactional
	public void delete(Integer id) {
		UsersMessage message = new UsersMessage();
		message.setId(id);
		message.setStatus(ResultParame.ResultNumber.MINUS_ONE.getNumber());
		usersMessageMapper.updateByPrimaryKeySelective(message);
	}
	
	/**
	 * 创建用户信息
	 * 
	 * @param vo
	 *            UsersMessage
	 * @return 数据返回
	 */
	private UsersMessageExample createUsersMessageExample(UsersMessage vo) {
		UsersMessageExample example = new UsersMessageExample();
		Criteria criteria = example.createCriteria()
				.andStatusNotEqualTo(ResultParame.ResultNumber.MINUS_ONE.getNumber());
		if (vo.getuId() != null) {
			criteria.andUIdEqualTo(vo.getuId());
		}
		example.setOrderByClause("time desc");
		return example;
	}

	@Override
	public long insertCountList(List<UsersMessage> list) {
		return usersMessageMapper.insertContentList(list);
	}

	@Override
	public boolean reader(Integer id) {
		boolean flag = false;
		if (id != null && id > 0) {
			UsersMessage record = usersMessageMapper.selectByPrimaryKey(id);
			if (record != null) {
				if (record.getStatus() == null || record.getStatus() == 0) {
					record.setStatus(1);
				} else {
					record.setStatus(0);
				}
				usersMessageMapper.updateByPrimaryKeySelective(record);
				flag = true;
			}
		}
		return flag;
	}
}
