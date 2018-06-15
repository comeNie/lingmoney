package com.mrbt.lingmoney.admin.service.gift.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mrbt.lingmoney.admin.service.gift.LingbaoSignService;
import com.mrbt.lingmoney.commons.cache.RedisDao;
import com.mrbt.lingmoney.mapper.LingbaoSignMapper;
import com.mrbt.lingmoney.model.LingbaoSign;
import com.mrbt.lingmoney.model.LingbaoSignExample;
import com.mrbt.lingmoney.utils.PageInfo;
import com.mrbt.lingmoney.utils.ResultParame;

/**
 * 我的领地签到属性
 *
 */
@Service
public class LingbaoSignServiceImpl implements LingbaoSignService {
	private static String cartcountRedisKey = "cartcount";
	@Autowired
	private RedisDao redisDao;
	@Autowired
	private LingbaoSignMapper lingbaoSignMapper;

	@Override
	public LingbaoSign findById(int id) {
		return lingbaoSignMapper.selectByPrimaryKey(id);
	}

	@Override
	@Transactional
	public void delete(int id) {
		lingbaoSignMapper.deleteByPrimaryKey(id);
	}

	@Override
	@Transactional()
	public void save(LingbaoSign vo) {
		lingbaoSignMapper.insert(vo);
	}

	@Override
	@Transactional()
	public void update(LingbaoSign vo) {
		lingbaoSignMapper.updateByPrimaryKeySelective(vo);
	}

	@Override
	public PageInfo getList(LingbaoSignExample example) {
		PageInfo pageInfo = new PageInfo();
		int resSize = (int) lingbaoSignMapper.countByExample(example);
		List<LingbaoSign> list = lingbaoSignMapper.selectByExample(example);
		pageInfo.setRows(list);
		pageInfo.setTotal(resSize);
		return pageInfo;
	}

	@Override
	public void setCartCount(Integer cartCount) {
		redisDao.set(cartcountRedisKey, cartCount);
	}

	@Override
	public PageInfo showCartCount() {
		PageInfo pageInfo = new PageInfo();
		// 获取购物车数量限制，默认20个
		int cartCount = ResultParame.ResultNumber.TWENTY.getNumber();
		if (redisDao.hasKey(cartcountRedisKey) && (Integer) redisDao.get(cartcountRedisKey) > 0) {
			cartCount = (Integer) redisDao.get(cartcountRedisKey);
		}
		pageInfo.setObj(cartCount);
		return pageInfo;
	}
}
