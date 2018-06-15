package com.mrbt.lingmoney.admin.service.product.impl;

import java.util.List;

import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mrbt.lingmoney.admin.service.product.ProductParamService;
import com.mrbt.lingmoney.mapper.ProductParamMapper;
import com.mrbt.lingmoney.model.ProductParam;
import com.mrbt.lingmoney.model.ProductParamExample;
import com.mrbt.lingmoney.utils.GridPage;
import com.mrbt.lingmoney.utils.ResultParame;

/**
 * 产品设置——》产品参数表 产品分类表的分类信息，现在没有用到 节假日信息：现在在用
 * 
 * @author Administrator
 *
 */
@Service
public class ProductParamServiceImpl implements ProductParamService {

	@Autowired
	private ProductParamMapper productParamMapper;

	/**
	 * 生成辅助类根据实体bean
	 * 
	 * @param vo
	 *            实体bean
	 * @return ProductParamExample辅助类
	 */
	private ProductParamExample createProductParamExample(ProductParam vo) {
		ProductParamExample example = new ProductParamExample();

		ProductParamExample.Criteria cri = example.createCriteria();

		if (vo.getId() != null) {
			cri.andPidEqualTo(vo.getId());
		}
		cri.andStatusNotEqualTo(ResultParame.ResultNumber.MINUS_ONE.getNumber());
		return example;
	}

	/**
	 * 删除
	 * 
	 * @param id
	 *            id
	 */
	@Override
	@Transactional
	public void delete(int id) {
		ProductParam record = new ProductParam();
		record.setId(id);
		record.setStatus(ResultParame.ResultNumber.MINUS_ONE.getNumber());
		productParamMapper.updateByPrimaryKeySelective(record);
	}

	/**
	 * 生成datagrid表格需要的结果
	 * 
	 * @param name
	 *            name
	 * @param page
	 *            翻页信息
	 * @return GridPage<ProductParam>
	 */
	@Override
	public GridPage<ProductParam> listGrid(String name, RowBounds page) {
		ProductParamExample example = new ProductParamExample();
		example.createCriteria().andNameEqualTo(name)
				.andStatusNotEqualTo(ResultParame.ResultNumber.MINUS_ONE.getNumber());
		example.setOrderByClause("id");

		List<ProductParam> reList = productParamMapper.selectByExample(example);
		if (reList != null && reList.size() > 0) {
			ProductParam vo = reList.get(0);
			if (vo != null) {
				example = createProductParamExample(vo);
				example.setLimitStart(page.getOffset());
				example.setLimitEnd(page.getLimit());
				GridPage<ProductParam> result = new GridPage<ProductParam>();
				result.setRows(productParamMapper.selectByExample(example));
				result.setTotal(productParamMapper.countByExample(example));
				return result;
			}
		}
		return null;
	}

	/**
	 * 保存实体bean
	 * 
	 * @param vo
	 *            ProductParam
	 * @param type
	 *            type
	 * @throws Exception
	 *             异常
	 */
	@Override
	@Transactional()
	public void save(ProductParam vo, String type) throws Exception {

		ProductParamExample example = new ProductParamExample();
		example.setOrderByClause("id");

		ProductParam pvo = null;

		// 不清楚这个位置为什么这么写
		if (type.equalsIgnoreCase("Holiday")) {
			example.createCriteria().andNameEqualTo("Holiday");
			List<ProductParam> reList = productParamMapper.selectByExample(example);
			if (reList != null && reList.size() > 0) {
				pvo = reList.get(0);
			}
		} else if (type.equalsIgnoreCase("product_group")) {
			example.createCriteria().andNameEqualTo("product_group");
			List<ProductParam> reList = productParamMapper.selectByExample(example);
			if (reList != null && reList.size() > 0) {
				pvo = reList.get(0);
			}
		}

		if (pvo != null) {
			vo.setPid(pvo.getId());
			productParamMapper.insertSelective(vo);
		}
	}

	/**
	 * 更新实体bean
	 * 
	 * @param vo
	 *            ProductParam
	 */
	@Override
	@Transactional()
	public void update(ProductParam vo) {
		productParamMapper.updateByPrimaryKeySelective(vo);
	}
}
