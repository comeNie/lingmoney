package com.mrbt.lingmoney.admin.service.product.impl;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mrbt.lingmoney.admin.service.product.TakeheartCategoryService;
import com.mrbt.lingmoney.mapper.TakeheartCategoryMapper;
import com.mrbt.lingmoney.model.TakeheartCategory;
import com.mrbt.lingmoney.model.TakeheartCategoryExample;
import com.mrbt.lingmoney.utils.GridPage;

/**
 * 
 * 产品设置——》随心取分类
 *
 */
@Service
public class TakeheartCategoryServiceImpl implements TakeheartCategoryService {
	@Autowired
	private TakeheartCategoryMapper takeheartCategoryMapper;

	/**
	 * 生成辅助类根据实体bean
	 * 
	 * @param vo
	 *            实体bean
	 * @return TakeheartCategoryExample辅助类
	 */
	@Override
	public TakeheartCategoryExample createTakeheartCategoryExample(TakeheartCategory vo) {
		TakeheartCategoryExample example = new TakeheartCategoryExample();
		TakeheartCategoryExample.Criteria cri = example.createCriteria();
		if (StringUtils.isNotBlank(vo.getName())) {
			cri.andNameLike("%" + vo.getName() + "%");
		}
		if (vo.getStatus() != null) {
			cri.andStatusEqualTo(vo.getStatus());
		}
		return example;
	}

	/**
	 * 查找部门，根据TakeheartCategory
	 * 
	 * @param vo
	 *            TakeheartCategory的实例
	 * @param offset
	 *            起始位置
	 * @param limit
	 *            记录条数
	 * @return TakeheartCategory的集合
	 */
	@Override
	public List<TakeheartCategory> list(TakeheartCategory vo, int offset, int limit) {
		TakeheartCategoryExample example = createTakeheartCategoryExample(vo);
		example.setLimitStart(offset);
		example.setLimitEnd(limit);
		return takeheartCategoryMapper.selectByExample(example);
	}

	/**
	 * 生成datagrid表格需要的结果
	 * 
	 * @param vo
	 *            TakeheartCategory实体bean
	 * @param page
	 *            翻页信息
	 * @return GridPage<TakeheartCategory>
	 */
	@Override
	public GridPage<TakeheartCategory> listGrid(TakeheartCategory vo, RowBounds page) {
		TakeheartCategoryExample example = createTakeheartCategoryExample(vo);
		example.setLimitStart(page.getOffset());
		example.setLimitEnd(page.getLimit());

		GridPage<TakeheartCategory> result = new GridPage<TakeheartCategory>();
		result.setRows(takeheartCategoryMapper.selectByExample(example));
		result.setTotal(takeheartCategoryMapper.countByExample(example));
		return result;
	}

	/**
	 * 保存实体bean
	 * 
	 * @param vo
	 */
	@Transactional()
	@Override
	public void save(TakeheartCategory vo) {
		takeheartCategoryMapper.insertSelective(vo);
	}

	/**
	 * 更新实体bean
	 * 
	 * @param vo
	 */
	@Transactional()
	@Override
	public void update(TakeheartCategory vo) {
		takeheartCategoryMapper.updateByPrimaryKeySelective(vo);
	}

	/**
	 * 删除
	 * 
	 * @param id
	 */
	@Transactional()
	@Override
	public void delete(int id) {
		takeheartCategoryMapper.deleteByPrimaryKey(id);
	}

	@Override
	public TakeheartCategory findByPk(int id) {
		return takeheartCategoryMapper.selectByPrimaryKey(id);
	}
}
