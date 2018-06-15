package com.mrbt.lingmoney.admin.service.info.impl;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mrbt.lingmoney.admin.service.info.UsersAskService;
import com.mrbt.lingmoney.commons.cache.RedisDao;
import com.mrbt.lingmoney.mapper.UsersAskMapper;
import com.mrbt.lingmoney.model.UsersAsk;
import com.mrbt.lingmoney.model.UsersAskExample;
import com.mrbt.lingmoney.utils.PageInfo;
import com.mrbt.lingmoney.utils.ResultParame;

/**
 * 你问我答
 */
@Service
public class UsersAskServiceImpl implements UsersAskService {
	@Autowired
	private RedisDao redisDao;
	@Autowired
	private UsersAskMapper usersAskMapper;

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public void reload() {
		UsersAsk ask = new UsersAsk();
		ask.setType(0);
		UsersAskExample example = createUsersAskExample(ask);
		example.setLimitStart(0);
		example.setLimitEnd(ResultParame.ResultNumber.TEN.getNumber());
		List userAskList = usersAskMapper.selectByExample(example);
		redisDao.refreshKeyValueList("HOMR_USER_ASK", userAskList);
	}

	@Override
	public Integer anwser(Integer askId) {
		UsersAskExample example = new UsersAskExample();
		example.createCriteria().andAskIdEqualTo(askId);
		List<UsersAsk> ask = usersAskMapper.selectByExample(example);
		if (ask != null && ask.size() > 0) {
			return ask.get(0).getId();
		}
		return null;
	}

	@Override
	public String listAnwser(Integer askId) {
		UsersAskExample example = new UsersAskExample();
		example.createCriteria().andAskIdEqualTo(askId);
		List<UsersAsk> ask = usersAskMapper.selectByExample(example);
		if (ask != null && ask.size() > 0) {
			return ask.get(0).getContent();
		}
		return "";
	}

	@Override
	@Transactional
	public void publish(Integer id) {
		UsersAsk record = usersAskMapper.selectByPrimaryKey(id);
		if (record != null && record.getHot() == 0) {
			record.setHot(1);
		} else if (record != null && record.getHot() == 1) {
			record.setHot(0);
		}
		usersAskMapper.updateByPrimaryKeySelective(record);
	}

	@Override
	@Transactional
	public void delete(Integer id) {
		UsersAsk a = new UsersAsk();
		a.setId(id);
		a.setState(ResultParame.ResultNumber.MINUS_ONE.getNumber());
		usersAskMapper.updateByPrimaryKeySelective(a);
		UsersAskExample example = new UsersAskExample();
		example.createCriteria().andAskIdEqualTo(id);
		List<UsersAsk> ask = usersAskMapper.selectByExample(example);
		if (ask != null && ask.size() > 0) {
			for (UsersAsk usersAsk : ask) {
				usersAsk.setState(ResultParame.ResultNumber.MINUS_ONE.getNumber());
				usersAskMapper.updateByPrimaryKeySelective(usersAsk);
			}
		}
	}

	@Override
	public void listGrid(UsersAsk vo, PageInfo pageInfo) {
		UsersAskExample example = createUsersAskExample(vo);
		example.setLimitStart(pageInfo.getFrom());
		example.setLimitEnd(pageInfo.getPagesize());
		pageInfo.setRows(usersAskMapper.selectByExample(example));
		pageInfo.setTotal((int) usersAskMapper.countByExample(example));
	}

	/**
	 * 生成辅助类根据实体bean
	 * 
	 * @param vo
	 *            实体bean
	 * @return UsersAskExample辅助类
	 */
	private UsersAskExample createUsersAskExample(UsersAsk vo) {
		UsersAskExample example = new UsersAskExample();
		UsersAskExample.Criteria criteria = example.createCriteria()
				.andStateNotEqualTo(ResultParame.ResultNumber.MINUS_ONE.getNumber());
		if (vo.getType() != null) {
			criteria.andTypeEqualTo(vo.getType());
		}
		if (vo.getState() != null) {
			criteria.andStateEqualTo(vo.getState());
		}
		if (StringUtils.isNotBlank(vo.getTitle())) {
			criteria.andTitleLike("%" + vo.getTitle() + "%");
		}
		example.setOrderByClause("id desc");
		return example;
	}

	/**
	 * 保存实体bean
	 * 
	 * @param vo
	 * @throws Exception
	 */
	@Transactional()
	@Override
	public void save(UsersAsk vo) {
		usersAskMapper.insertSelective(vo);
	}

	/**
	 * 更新实体bean
	 * 
	 * @param vo
	 */
	@Override
	@Transactional
	public void update(UsersAsk vo) {
		usersAskMapper.updateByPrimaryKeySelective(vo);
	}

	@Transactional
	@Override
	public boolean changeStatus(Integer id) {
		boolean flag = false;
		if (id != null && id > 0) {
			UsersAsk record = usersAskMapper.selectByPrimaryKey(id);
			if (record != null) {
				if (record.getState() == null || record.getState() == 0) {
					record.setState(1);
				} else {
					record.setState(0);
				}
				usersAskMapper.updateByPrimaryKeySelective(record);
				flag = true;
			}
		}
		return flag;
	}
}
