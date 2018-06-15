package com.mrbt.lingmoney.admin.service.info.impl;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mrbt.lingmoney.admin.service.info.EmployeeRelationsMappingService;
import com.mrbt.lingmoney.mapper.EmployeeRelationsMappingMapper;
import com.mrbt.lingmoney.model.EmployeeRelationsMapping;
import com.mrbt.lingmoney.model.EmployeeRelationsMappingExample;
import com.mrbt.lingmoney.model.EmployeeRelationsMappingExample.Criteria;
import com.mrbt.lingmoney.utils.PageInfo;
import com.mrbt.lingmoney.utils.ResultParame;

/**
 * 员工推荐码映射
 * 
 */
@Service
public class EmployeeRelationsMappingServiceImpl implements EmployeeRelationsMappingService {

	@Autowired
	private EmployeeRelationsMappingMapper employeeRelationsMappingMapper;

	/**
	 * 生成辅助类根据实体bean
	 * 
	 * @param vo
	 *            实体bean
	 * @return ProductEmployeeRelationsMappingExample辅助类
	 */
	private EmployeeRelationsMappingExample createEmployeeRelationsMappingExample(EmployeeRelationsMapping vo) {
		EmployeeRelationsMappingExample example = new EmployeeRelationsMappingExample();
		Criteria criteria = example.createCriteria();
		criteria.andStatusNotEqualTo(ResultParame.ResultNumber.MINUS_ONE.getNumber());
		if (StringUtils.isNotBlank(vo.getEmployeeid())) {
			criteria.andEmployeeidLike("%" + vo.getEmployeeid() + "%");
		}
		if (StringUtils.isNotBlank(vo.getEmployeeName())) {
			criteria.andEmployeeNameLike("%" + vo.getEmployeeName() + "%");
		}
		example.setOrderByClause("employeeID");
		return example;
	}

	/**
	 * 删除
	 * 
	 * @param id
	 */
	@Transactional
	@Override
	public void delete(int id) {
		EmployeeRelationsMapping mapping = new EmployeeRelationsMapping();
		mapping.setId(id);
		mapping.setStatus(ResultParame.ResultNumber.MINUS_ONE.getNumber());
		employeeRelationsMappingMapper.updateByPrimaryKeySelective(mapping);
	}

	/**
	 * 保存实体bean
	 * 
	 * @param vo
	 */
	@Transactional
	@Override
	public void save(EmployeeRelationsMapping vo) {
		employeeRelationsMappingMapper.insertSelective(vo);
	}

	/**
	 * 更新实体bean
	 * 
	 * @param vo
	 */
	@Transactional
	@Override
	public void update(EmployeeRelationsMapping vo) {
		employeeRelationsMappingMapper.updateByPrimaryKeySelective(vo);
	}

	@Override
	public void listGrid(EmployeeRelationsMapping vo, PageInfo pageInfo) {
		EmployeeRelationsMappingExample example = createEmployeeRelationsMappingExample(vo);
		example.setLimitStart(pageInfo.getFrom());
		example.setLimitEnd(pageInfo.getPagesize());
		pageInfo.setRows(employeeRelationsMappingMapper.selectByExample(example));
		pageInfo.setTotal((int) employeeRelationsMappingMapper.countByExample(example));
	}

	@Override
	public EmployeeRelationsMapping findByPk(Integer id) {
		return employeeRelationsMappingMapper.selectByPrimaryKey(id);
	}

}
