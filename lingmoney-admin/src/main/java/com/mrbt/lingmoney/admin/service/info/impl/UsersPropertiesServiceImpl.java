package com.mrbt.lingmoney.admin.service.info.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mrbt.lingmoney.admin.service.info.UsersPropertiesService;
import com.mrbt.lingmoney.mapper.UsersPropertiesMapper;
import com.mrbt.lingmoney.model.UsersProperties;
import com.mrbt.lingmoney.model.UsersPropertiesExample;
import com.mrbt.lingmoney.utils.PageInfo;
import com.mrbt.lingmoney.utils.ResultParame;

/**
 * 
 * 用户属性
 *
 */
@Service
public class UsersPropertiesServiceImpl implements UsersPropertiesService {

	@Autowired
	private UsersPropertiesMapper usersPropertiesMapper;

	/**
	 * 保存实体bean
	 * 
	 * @param vo
	 */
	@Transactional
	@Override
	public void save(UsersProperties vo) {
		usersPropertiesMapper.insertSelective(vo);
	}

	/**
	 * 更新实体bean
	 * 
	 * @param vo
	 */
	@Transactional
	@Override
	public void update(UsersProperties vo) {
		usersPropertiesMapper.updateByPrimaryKeySelective(vo);
	}

	/**
	 * 查询用户根据手机
	 * 
	 * @param telephone
	 * @return
	 */
	@Override
	public UsersProperties selectByTelephone(String telephone) {

		return usersPropertiesMapper.selectByTelephone(telephone);
	}

	/**
	 * 查询用户根据map
	 * 
	 * @param map
	 * @return
	 */
	@Override
	public UsersProperties selectByMap(Map<String, Object> map) {

		return usersPropertiesMapper.selectByMap(map);
	}

	/**
	 * 查询某用户属性信息
	 * 
	 * @param uId
	 * @return
	 */
	@Override
	public UsersProperties findByUId(String uId) {
		return usersPropertiesMapper.selectByuId(uId);
	}

	/**
	 * 更新用户等级
	 *
	 * @Description 根据理财金额从用户等级表中查询所属区段
	 */
	@Transactional
	@Override
	public void updateUsersPropertiesLevel() {
		usersPropertiesMapper.updateUsersPropertiesLevel();
	}

	/**
	 * 创建用户属性
	 * 
	 * @param vo
	 *            UsersProperties
	 * @param symbol
	 *            symbol
	 * @return 数据返回
	 */
	private UsersPropertiesExample createUsersPropertiesNewExample(UsersProperties vo, Integer symbol) {
		UsersPropertiesExample example = new UsersPropertiesExample();

		UsersPropertiesExample.Criteria criteria = example.createCriteria();

		if (vo.getRegDate() != null) {
			if (symbol == ResultParame.ResultNumber.ZERO.getNumber()) { // 大于
				criteria.andRegDateGreaterThan(vo.getRegDate());
			}
			if (symbol == ResultParame.ResultNumber.ONE.getNumber()) { // 等于
				criteria.andRegDateEqualTo(vo.getRegDate());
			}
			if (symbol == ResultParame.ResultNumber.TWO.getNumber()) { // 小于
				criteria.andRegDateLessThan(vo.getRegDate());
			}
		}
		if (vo.getPlatformType() != null) {
			criteria.andPlatformTypeEqualTo(vo.getPlatformType());
		}
		example.setOrderByClause("reg_date desc");
		return example;
	}

	@Override
	public void listGrid(UsersProperties vo, Integer symbol, PageInfo pageInfo) {
		UsersPropertiesExample example = createUsersPropertiesNewExample(vo, symbol);
		example.setLimitStart(pageInfo.getFrom());
		example.setLimitEnd(pageInfo.getPagesize());
		pageInfo.setRows(usersPropertiesMapper.selectByExample(example));
		pageInfo.setTotal((int) usersPropertiesMapper.countByExample(example));
	}

	@Override
	public UsersProperties selectByReferralCode(String referralCode) {
		UsersPropertiesExample example = new UsersPropertiesExample();
		example.createCriteria().andReferralCodeEqualTo(referralCode);
		List<UsersProperties> list = usersPropertiesMapper.selectByExample(example);
		return (list == null || list.size() < 1) ? null : list.get(0);
	}

	@Override
	public List<UsersProperties> getReferralUsers(String uId) {
		UsersPropertiesExample example = new UsersPropertiesExample();
		example.createCriteria().andReferralIdEqualTo(uId);
		List<UsersProperties> list = usersPropertiesMapper.selectByExample(example);
		return list;
	}

	@Override
	public UsersProperties findByPk(int id) {
		return usersPropertiesMapper.selectByPrimaryKey(id);
	}

	@Override
	public int selectTradingCountByuId(String uId) {
		return usersPropertiesMapper.selectTradingCountByuId(uId);
	}

}
