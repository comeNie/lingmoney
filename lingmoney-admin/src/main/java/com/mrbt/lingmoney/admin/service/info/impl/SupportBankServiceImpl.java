package com.mrbt.lingmoney.admin.service.info.impl;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mrbt.lingmoney.admin.service.info.SupportBankService;
import com.mrbt.lingmoney.commons.cache.RedisDao;
import com.mrbt.lingmoney.mapper.SupportBankMapper;
import com.mrbt.lingmoney.model.SupportBank;
import com.mrbt.lingmoney.model.SupportBankExample;
import com.mrbt.lingmoney.model.SupportBankExample.Criteria;
import com.mrbt.lingmoney.utils.AppCons;
import com.mrbt.lingmoney.utils.PageInfo;
import com.mrbt.lingmoney.utils.ResultParame;

/**
 * 京东支付，支持银行管理
 *
 */
@Service
public class SupportBankServiceImpl implements SupportBankService {
	@Autowired
	private RedisDao redisDao;
	@Autowired
	private SupportBankMapper supportBankMapper;

	@Override
	public void listGrid(SupportBank vo, PageInfo pageInfo) {
		SupportBankExample example = createSupportBankExample(vo);
		example.setLimitStart(pageInfo.getFrom());
		example.setLimitEnd(pageInfo.getPagesize());
		pageInfo.setRows(supportBankMapper.selectByExample(example));
		pageInfo.setTotal((int) supportBankMapper.countByExample(example));
	}

	@Override
	@Transactional
	public void delete(Integer id) {
		SupportBank s = new SupportBank();
		s.setId(id);
		s.setStatus(ResultParame.ResultNumber.MINUS_ONE.getNumber());
		supportBankMapper.updateByPrimaryKey(s);		
	}

	/**
	 * 保存实体bean
	 * 
	 * @param vo
	 * @throws Exception
	 */
	@Override
	@Transactional()
	public void save(SupportBank vo) {
		supportBankMapper.insertSelective(vo);
	}

	/**
	 * 更新实体bean
	 * 
	 * @param vo
	 */
	@Override
	@Transactional()
	public void update(SupportBank vo) {
		supportBankMapper.updateByPrimaryKeySelective(vo);
	}

	/**
	 * 更新实体bean
	 * 
	 * @param vo
	 */
	@Override
	@Transactional()
	public void updateOrder(SupportBank vo) {
		SupportBank suppoerBank = supportBankMapper.selectByPrimaryKey(vo.getId());
		if (suppoerBank != null) {
			if (!vo.getBankOrder().equals(suppoerBank.getBankOrder())) {
				SupportBankExample example = new SupportBankExample();
				example.createCriteria().andBankOrderEqualTo(vo.getBankOrder());
				List<SupportBank> lists = supportBankMapper.selectByExample(example);
				SupportBank old = null;
				if (lists != null && lists.size() > 0) {
					old = lists.get(0);
				}
				if (old != null) {
					old.setBankOrder(suppoerBank.getBankOrder());
					supportBankMapper.updateByPrimaryKeySelective(old);
				}
			}
		}
		supportBankMapper.updateByPrimaryKeySelective(vo);
	}

	/**
	 * 查询最大的Order
	 * @return
	 */
	@Override
	public Integer getOrderOrder() {
		SupportBankExample example = new SupportBankExample();
		example.setDistinct(true);
		example.createCriteria().andBankOrderIsNotNull();
		example.setOrderByClause("bank_order desc");
		List<SupportBank> lists = supportBankMapper.selectByExample(example);
		if (lists != null && lists.size() > 0) {
			return lists.get(0).getBankOrder();
		}
		return ResultParame.ResultNumber.MINUS_ONE.getNumber();
	}

	/**
	 * 查询代码是否重复
	 */
	@Override
	public Integer isExistsBankCode(String bankcode) {
		SupportBankExample example = new SupportBankExample();
		example.setDistinct(true);
		example.createCriteria().andBankCodeEqualTo(bankcode);
		List<SupportBank> lists = supportBankMapper.selectByExample(example);
		if (lists != null && lists.size() > 0) {
			return lists.size();
		}
		return ResultParame.ResultNumber.MINUS_ONE.getNumber();
	}

	/**
	 * 查询代码是否重复
	 */
	@Override
	public Integer isExistsBankCodeEdit(String bankcode, int id) {
		SupportBank supportBank = supportBankMapper.selectByPrimaryKey(id);
		if (supportBank != null) {
			if (bankcode.equals(supportBank.getBankCode())) {
				return ResultParame.ResultNumber.MINUS_ONE.getNumber();
			} else {
				SupportBankExample example = new SupportBankExample();
				example.setDistinct(true);
				example.createCriteria().andBankCodeEqualTo(bankcode);
				List<SupportBank> lists = supportBankMapper.selectByExample(example);
				if (lists != null && lists.size() > 0) {
					return lists.size();
				}
			}
		} else {
			SupportBankExample example = new SupportBankExample();
			example.setDistinct(true);
			example.createCriteria().andBankCodeEqualTo(bankcode);
			List<SupportBank> lists = supportBankMapper.selectByExample(example);
			if (lists != null && lists.size() > 0) {
				return lists.size();
			}
		}
		return ResultParame.ResultNumber.MINUS_ONE.getNumber();
	}

	/**
	 * 添加银行维护redis
	 * @param bankId
	 * @return
	 */
	@Override
	public Integer appendBankMaintain(Integer bankId) {
		Integer res = 0;
		SupportBank bankInfo = supportBankMapper.selectByPrimaryKey(bankId);
		if (bankInfo != null) {
			redisDao.set(AppCons.MAINTAIN + bankInfo.getBankShort(), "1");
			if (redisDao.get(AppCons.MAINTAIN + bankInfo.getBankShort()) != null) {
				res = ResultParame.ResultNumber.ONE.getNumber();
			}
		}
		return res;
	}

	/**
	 * 生成辅助类根据实体bean
	 * 
	 * @param vo
	 *            vo
	 * @return 数据返回
	 */
	private SupportBankExample createSupportBankExample(SupportBank vo) {
		SupportBankExample example = new SupportBankExample();
		Criteria criteria = example.createCriteria()
				.andStatusNotEqualTo(ResultParame.ResultNumber.MINUS_ONE.getNumber());
		example.setOrderByClause("bank_order asc");
		if (StringUtils.isNotBlank(vo.getBankName())) {
			criteria.andBankNameLike(
					"%" + vo.getBankName() + "%");
		}
		return example;
	}

	/**
	 * 删除银行维护redis
	 * @param bankId
	 * @return
	 */
	@Override
	public Integer deleteBankMaintain(Integer bankId) {
		Integer res = 0;
		SupportBank bankInfo = supportBankMapper.selectByPrimaryKey(bankId);
		if (bankInfo != null) {
			if (redisDao.get(AppCons.MAINTAIN + bankInfo.getBankShort()) != null) {
				redisDao.delete(AppCons.MAINTAIN + bankInfo.getBankShort());
				res = 1;
			}
		}
		return res;
	}

	/**
	 * 添加删除是否可用购买随心取
	 * @param status
	 * @param msg 
	 * @return
	 */
	@Override
	public Integer takeHeartAllowBuy(Integer status, String msg) {
		Integer res = 0;
		if (status == 1) {
			redisDao.set("takeHeart_Allow_Buy", msg);
			if (redisDao.get("takeHeart_Allow_Buy") != null) {
				res = 1;
			}
		} else {
			redisDao.delete("takeHeart_Allow_Buy");
			if (redisDao.get("takeHeart_Allow_Buy") == null) {
				res = 1;
			}
		}
		return res;
	}
}
