package com.mrbt.lingmoney.admin.service.info;

import java.util.List;
import java.util.Map;

import com.mrbt.lingmoney.model.PrefixBank;

/**
 * 银行卡前六位管理
 * 
 * @author luox
 * @Date 2017年5月11日
 */
public interface PrefixBankService {

	/**
	 * 插入
	 * 
	 * @param record
	 *            PrefixBank
	 * @return 数据返回
	 */
	int insert(PrefixBank record);

	/**
	 * 删除
	 * 
	 * @param id
	 *            id
	 * @return 数据返回
	 */
	int deleteByPrimaryKey(Integer id);

	/**
	 * 更新
	 * 
	 * @param record
	 *            PrefixBank
	 * @return 数据返回
	 */
	int updateByPrimaryKey(PrefixBank record);

	/**
	 * 查询
	 * 
	 * @param map
	 *            map
	 * @return 结果列表
	 */
	List<PrefixBank> selectByCondition(Map<String, Object> map);

	/**
	 * 查询数量
	 * 
	 * @param map
	 *            map
	 * @return 数量返回
	 */
	int selectByConditionCount(Map<String, Object> map);

}
