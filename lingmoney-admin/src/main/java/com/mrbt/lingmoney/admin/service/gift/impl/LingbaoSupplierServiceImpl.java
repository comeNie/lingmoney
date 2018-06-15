package com.mrbt.lingmoney.admin.service.gift.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mrbt.lingmoney.admin.service.gift.LingbaoSupplierService;
import com.mrbt.lingmoney.mapper.LingbaoSupplierMapper;
import com.mrbt.lingmoney.model.LingbaoSupplier;
import com.mrbt.lingmoney.model.LingbaoSupplierExample;
import com.mrbt.lingmoney.utils.PageInfo;

/**
 * 我的领地供应商
 *
 */
@Service
public class LingbaoSupplierServiceImpl implements LingbaoSupplierService {

	@Autowired
	private LingbaoSupplierMapper lingbaoSupplierMapper;

	@Override
	public LingbaoSupplier findById(int id) {
		return lingbaoSupplierMapper.selectByPrimaryKey(id);
	}

	@Override
	@Transactional
	public void delete(int id) {
		lingbaoSupplierMapper.deleteByPrimaryKey(id);
	}

	@Override
	@Transactional()
	public void save(LingbaoSupplier vo) {
		lingbaoSupplierMapper.insert(vo);
	}

	@Override
	@Transactional()
	public void update(LingbaoSupplier vo) {
		lingbaoSupplierMapper.updateByPrimaryKeySelective(vo);
	}

	@Override
	public PageInfo getList(LingbaoSupplierExample example) {
		PageInfo pageInfo = new PageInfo();
		int resSize = (int) lingbaoSupplierMapper.countByExample(example);
		List<LingbaoSupplier> list = lingbaoSupplierMapper
				.selectByExample(example);
		pageInfo.setRows(list);
		pageInfo.setTotal(resSize);
		return pageInfo;
	}

	@Override
	public boolean changeStatus(Integer id) {
		boolean flag = false;
		if (id != null && id > 0) {
			LingbaoSupplier record = lingbaoSupplierMapper.selectByPrimaryKey(id);
			if (record != null) {
				if (record.getStatus() == null || record.getStatus() == 0) {
					record.setStatus(1);
				} else {
					record.setStatus(0);
				}
				lingbaoSupplierMapper.updateByPrimaryKeySelective(record);
				flag = true;
			}
		}
		return flag;
	}

}
