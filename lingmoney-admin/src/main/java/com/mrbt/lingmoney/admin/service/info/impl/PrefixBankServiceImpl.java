package com.mrbt.lingmoney.admin.service.info.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mrbt.lingmoney.admin.service.info.PrefixBankService;
import com.mrbt.lingmoney.mapper.PrefixBankMapper;
import com.mrbt.lingmoney.model.PrefixBank;
import com.mrbt.lingmoney.utils.ResultParame;

/**
 *@author yiban sun
 *@date 2016年8月25日 上午9:26:19
 *@version 1.0
 *@description 
 *@since
 **/
@Service
public class PrefixBankServiceImpl implements PrefixBankService {
	@Autowired
	private PrefixBankMapper prefixBankMapper;	
	
	/**
	 * 添加
	 * 
	 * @Description
	 * @param record
	 *            PrefixBank
	 * @return 数据返回
	 */
	@Override
	public int insert(PrefixBank record) {
		return prefixBankMapper.insertSelective(record);
	}
	
	/**
	 * 根据ID删除
	 * 
	 * @Description
	 * @param id
	 *            id
	 * @return 数据返回
	 */
	@Override
	public int deleteByPrimaryKey(Integer id) {
		PrefixBank bank = new PrefixBank();
		bank.setId(id);
		bank.setStatus(ResultParame.ResultNumber.MINUS_ONE.getNumber());
		return prefixBankMapper.updateByPrimaryKeySelective(bank);
	}


    /**
	 * 修改
	 * 
	 * @Description
	 * @param record
	 *            PrefixBank
	 * @return 数据返回
	 */
    @Override
	public int updateByPrimaryKey(PrefixBank record) {
    	return prefixBankMapper.updateByPrimaryKey(record);
    }
    
    /**
	 * 根据条件查询
	 * 
	 * @Description
	 * @param map
	 *            map
	 * @return 数据返回
	 */
    @Override
	public List<PrefixBank> selectByCondition(Map<String, Object> map) {
    	return prefixBankMapper.selectByCondition(map);
    }
    
    /**
	 * 条件查询到条数
	 * 
	 * @Description
	 * @param map
	 *            map
	 * @return 数据返回
	 */
    @Override
	public int selectByConditionCount(Map<String, Object> map) {
    	return prefixBankMapper.selectByConditionCount(map);
    }
}
