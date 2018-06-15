package com.mrbt.lingmoney.admin.service.info;

import com.mrbt.lingmoney.model.EmployeeRelationsMapping;
import com.mrbt.lingmoney.utils.PageInfo;

/**
 * 员工推荐码映射
 * 
 * @author luox
 * @Date 2017年5月11日
 */
public interface EmployeeRelationsMappingService {

	/**
	 * 删除
	 * 
	 * @param int1
	 *            数据id
	 */
	void delete(int int1);

	/**
	 * 分页查询
	 * 
	 * @param vo
	 *            EmployeeRelationsMapping
	 * @param pageInfo
	 *            PageInfo
	 */
	void listGrid(EmployeeRelationsMapping vo, PageInfo pageInfo);

	/**
	 * 新增
	 * 
	 * @param vo
	 *            EmployeeRelationsMapping
	 */
	void save(EmployeeRelationsMapping vo);

	/**
	 * 修改
	 * 
	 * @param vo
	 *            EmployeeRelationsMapping
	 */
	void update(EmployeeRelationsMapping vo);

	/**
	 * 根据主键查询
	 * 
	 * @param id
	 *            id
	 * @return 数据返回
	 */
	EmployeeRelationsMapping findByPk(Integer id);

}

