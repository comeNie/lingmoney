package com.mrbt.lingmoney.admin.service.product.impl;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mrbt.lingmoney.admin.service.product.ProductCategoryService;
import com.mrbt.lingmoney.mapper.ProductCategoryMapper;
import com.mrbt.lingmoney.model.ProductCategory;
import com.mrbt.lingmoney.model.ProductCategoryExample;
import com.mrbt.lingmoney.utils.GridPage;
import com.mrbt.lingmoney.utils.ResultParame;

/**
 * 产品设置——》产品分类表
 *
 */
@Service
public class ProductCategoryServiceImpl implements ProductCategoryService {

	@Autowired
	private ProductCategoryMapper productCategoryMapper;

	/**
	 * 生成辅助类根据实体bean
	 * 
	 * @param vo
	 *            实体bean
	 * @return ProductCategoryExample辅助类
	 */
	private ProductCategoryExample createProductCategoryExample(ProductCategory vo) {
		ProductCategoryExample example = new ProductCategoryExample();
		ProductCategoryExample.Criteria cri = example.createCriteria();
		if (StringUtils.isNotBlank(vo.getName())) {
			cri.andNameLike("%" + vo.getName() + "%");
		}
		cri.andStatusNotEqualTo(ResultParame.ResultNumber.MINUS_ONE.getNumber());
		return example;
	}

	/**
	 * 删除
	 * 
	 * @param id
	 */
	@Override
	@Transactional
	public void delete(int id) {
		ProductCategory record = new ProductCategory();
		record.setId(id);
		record.setStatus(ResultParame.ResultNumber.MINUS_ONE.getNumber());
		productCategoryMapper.updateByPrimaryKeySelective(record);
	}

	/**
	 * 根据主键查询
	 * 
	 * @param id
	 */
	@Override
	@Transactional
	public ProductCategory findByPk(int id) {
		return productCategoryMapper.selectByPrimaryKey(id);
	}

	/**
	 * 查找，根据ProductCategory
	 * 
	 * @param vo
	 *            ProductCategory的实例
	 * @param offset
	 *            起始位置
	 * @param limit
	 *            记录条数
	 * @return ProductCategory的集合
	 */
	@Override
	public List<ProductCategory> list(ProductCategory vo, int offset, int limit) {
		ProductCategoryExample example = createProductCategoryExample(vo);
		example.setLimitStart(offset);
		example.setLimitEnd(limit);
		
		return productCategoryMapper.selectByExample(example);
	}

	/**
	 * 生成datagrid表格需要的结果
	 * 
	 * @param vo
	 *            ProductCategory实体bean
	 * @param page
	 *            翻页信息
	 * @return GridPage<ProductCategory>
	 */
	@Override
	public GridPage<ProductCategory> listGrid(ProductCategory vo, RowBounds page) {
		ProductCategoryExample example = createProductCategoryExample(vo);
		example.setLimitStart(page.getOffset());
		example.setLimitEnd(page.getLimit());
		
		GridPage<ProductCategory> result = new GridPage<ProductCategory>();
		
		result.setRows(productCategoryMapper.selectByExample(example));
		result.setTotal(productCategoryMapper.countByExample(example));
		return result;
	}

	/**
	 * 保存实体bean
	 * 
	 * @param vo
	 * @throws Exception
	 */
	@Override
	@Transactional()
	public void save(ProductCategory vo) throws Exception {
		productCategoryMapper.insert(vo);
	}

	/**
	 * 更新实体bean
	 * 
	 * @param vo
	 */
	@Override
	@Transactional()
	public void update(ProductCategory vo) {
		productCategoryMapper.updateByPrimaryKeySelective(vo);
	}

}
