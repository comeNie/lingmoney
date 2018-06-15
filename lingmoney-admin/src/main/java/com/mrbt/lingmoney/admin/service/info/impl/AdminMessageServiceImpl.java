package com.mrbt.lingmoney.admin.service.info.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mrbt.lingmoney.admin.service.info.AdminMessageService;
import com.mrbt.lingmoney.mapper.AdminMessageMapper;
import com.mrbt.lingmoney.mapper.UsersMapper;
import com.mrbt.lingmoney.mapper.UsersMessageMapper;
import com.mrbt.lingmoney.model.AdminMessage;
import com.mrbt.lingmoney.model.AdminMessageExample;
import com.mrbt.lingmoney.model.AdminMessageWithBLOBs;
import com.mrbt.lingmoney.model.UsersMessage;
import com.mrbt.lingmoney.utils.PageInfo;
import com.mrbt.lingmoney.utils.ResultParame;

/**
 * 发送消息
 * 
 */
@Service
public class AdminMessageServiceImpl implements AdminMessageService {

	@Autowired
	private AdminMessageMapper adminMessageMapper;
	@Autowired
	private UsersMapper usersMapper;
	@Autowired
	private UsersMessageMapper usersMessageMapper;

	/**
	 * 查询
	 * 
	 * @param vo
	 *            AdminMessage
	 * @return 数据返回
	 */
	private AdminMessageExample createAdminMessageExample(AdminMessage vo) {
		AdminMessageExample example = new AdminMessageExample();
		if (vo.getId() != null) {
			example.createCriteria().andIdEqualTo(vo.getId());
		}
		example.setOrderByClause("sendtime desc");
		return example;
	}

	/**
	 * 删除
	 * 
	 * @param id
	 *            id
	 */
	@Transactional
	@Override
	public void delete(int id) {
		adminMessageMapper.deleteByPrimaryKey(id);
	}

	@Override
	public void listGrid(AdminMessageWithBLOBs vo, PageInfo pageInfo) {
		AdminMessageExample example = createAdminMessageExample(vo);
		example.setLimitStart(pageInfo.getFrom());
		example.setLimitEnd(pageInfo.getPagesize());
		pageInfo.setRows(adminMessageMapper.selectByExampleWithBLOBs(example));
		pageInfo.setTotal((int) adminMessageMapper.countByExample(example));
	}

	/**
	 * 保存实体bean
	 * 
	 * @param vo
	 */
	@Transactional
	@Override
	public void save(AdminMessageWithBLOBs vo) {
		adminMessageMapper.insertSelective(vo);
	}

	/**
	 * 更新实体bean
	 * 
	 * @param vo
	 */
	@Transactional
	@Override
	public void update(AdminMessageWithBLOBs vo) {
		adminMessageMapper.updateByPrimaryKeyWithBLOBs(vo);
	}

	/**
	 * 查找内容
	 * 
	 * @param id
	 * @return
	 */
	@Override
	public String listContent(int id) {
		AdminMessageWithBLOBs vo = adminMessageMapper.selectByPrimaryKey(id);
		if (vo != null) {
			return vo.getContent();
		}
		return "";
	}

	@Transactional
	@Override
	public void sendMessage(AdminMessageWithBLOBs vo, PageInfo pageInfo, Integer partOrALl) {
		if (partOrALl == 1) { // 给全部
			List<String> listStr = usersMapper.selectAllUserId();
			StringBuilder str = new StringBuilder();
			for (int i = 0; i < listStr.size(); i++) {
				if (i == listStr.size() - 1) {
					str.append(listStr.get(i));
				} else {
					str.append(listStr.get(i));
					str.append(",");
				}
			}
			vo.setCount(listStr.size());
			vo.setuId(str.toString());
		}
		vo.setSendtime(new Date());
		adminMessageMapper.insertSelective(vo);

		String idStr = vo.getuId(); // 所选择的用户id组成的字符串，英文逗号隔开
		String content = vo.getContent();
		String theme = vo.getTheme();
		String sendname = vo.getSendname();
		Date sendtime = vo.getSendtime();
		int admimId = vo.getId();
		String[] arr = idStr.split(",");
		List<UsersMessage> list = new ArrayList<UsersMessage>();
		int count = 0;
		for (int i = 0; i < arr.length; i++) {
			UsersMessage usersMessage = new UsersMessage();
			usersMessage.setContent(content);
			usersMessage.setSender(sendname);
			usersMessage.setStatus(0);
			usersMessage.setTime(sendtime);
			usersMessage.setTopic(theme);
			usersMessage.setAdminMessageId(admimId);
			usersMessage.setuId(arr[i]);
			list.add(usersMessage);
			count++;
			if (count % ResultParame.ResultNumber.ONE_HUNDRED.getNumber() == 0) {
				usersMessageMapper.insertContentList(list);
				list = new ArrayList<UsersMessage>();
			}
		}
		if (list.size() > 0) {
			usersMessageMapper.insertContentList(list);
		}
		pageInfo.setCode(ResultParame.ResultInfo.SUCCESS.getCode());
	}

}
