package com.mrbt.lingmoney.admin.service.product.impl;

import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mrbt.lingmoney.admin.service.product.ProductCategoryFixRateService;
import com.mrbt.lingmoney.mapper.ProductCategoryFixRateMapper;
import com.mrbt.lingmoney.model.ProductCategoryFixRate;
import com.mrbt.lingmoney.model.ProductCategoryFixRateExample;
import com.mrbt.lingmoney.utils.GridPage;
import com.mrbt.lingmoney.utils.ResultParame;

/**
 * /** 产品设置——》固定产品分类收益表
 *
 */
@Service
public class ProductCategoryFixRateServiceImpl implements ProductCategoryFixRateService {
	
	@Autowired
	private ProductCategoryFixRateMapper productCategoryFixRateMapper;
	
	/**
	 * 生成辅助类根据实体bean
	 * 
	 * @param vo
	 *            实体bean
	 * @return ProductCategoryFixRateExample辅助类
	 */
	private ProductCategoryFixRateExample createProductCategoryFixRateExample(ProductCategoryFixRate vo) {
		ProductCategoryFixRateExample example = new ProductCategoryFixRateExample();
		ProductCategoryFixRateExample.Criteria cri = example.createCriteria();
		
		if (vo.getpId() != null) {
			cri.andPIdEqualTo(vo.getpId());
		}
		cri.andStatusNotEqualTo(ResultParame.ResultNumber.MINUS_ONE.getNumber());
		return example;
	}

	/**
	 * 生成datagrid表格需要的结果
	 * 
	 * @param vo
	 *            ProductCategoryFixRate实体bean
	 * @param page
	 *            翻页信息
	 * @return GridPage<ProductCategoryFixRate>
	 */
	@Override
	public GridPage<ProductCategoryFixRate> listGrid(ProductCategoryFixRate vo, RowBounds page) {
		
		ProductCategoryFixRateExample example = createProductCategoryFixRateExample(vo);
		example.setLimitStart(page.getOffset());
		example.setLimitEnd(page.getLimit());
		
		GridPage<ProductCategoryFixRate> result = new GridPage<ProductCategoryFixRate>();
		result.setRows(productCategoryFixRateMapper.selectByExample(example));
		result.setTotal(productCategoryFixRateMapper.countByExample(example));
		
		return result;
	}

	@Override
	public void delete(int id) {
		ProductCategoryFixRate record = new ProductCategoryFixRate();
		record.setId(id);
		record.setStatus(ResultParame.ResultNumber.MINUS_ONE.getNumber());
		productCategoryFixRateMapper.updateByPrimaryKeySelective(record);
	}

	@Override
	public void save(ProductCategoryFixRate vo) {
		vo.setStatus(1);
		productCategoryFixRateMapper.insert(vo);
	}

	@Override
	public void update(ProductCategoryFixRate vo) {
		vo.setStatus(1);
		productCategoryFixRateMapper.updateByPrimaryKeySelective(vo);
	}


}
