package com.mrbt.lingmoney.admin.service.product.impl;

import java.util.List;

import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mrbt.lingmoney.admin.service.product.TakeheartFixRateService;
import com.mrbt.lingmoney.mapper.TakeheartFixRateMapper;
import com.mrbt.lingmoney.model.TakeheartFixRate;
import com.mrbt.lingmoney.model.TakeheartFixRateExample;
import com.mrbt.lingmoney.utils.GridPage;

/**
 * 
 * 随心取固定收益率设置
 *
 */
@Service
public class TakeheartFixRateServiceImpl implements TakeheartFixRateService {
	@Autowired
	private TakeheartFixRateMapper takeheartFixRateMapper;

	/**
	 * 生成辅助类根据实体bean
	 * 
	 * @param vo
	 *            实体bean
	 * @return TakeheartFixRateExample辅助类
	 */
	@Override
	public TakeheartFixRateExample createTakeheartFixRateExample(TakeheartFixRate vo) {
		TakeheartFixRateExample example = new TakeheartFixRateExample();
		if (vo.getcId() != null) {
			example.createCriteria().andCIdEqualTo(vo.getcId());
		}
		return example;
	}

	/**
	 * 查找部门，根据TakeheartFixRate
	 * 
	 * @param vo
	 *            TakeheartFixRate的实例
	 * @param offset
	 *            起始位置
	 * @param limit
	 *            记录条数
	 * @return TakeheartFixRate的集合
	 */
	@Override
	public List<TakeheartFixRate> list(TakeheartFixRate vo, int offset, int limit) {
		TakeheartFixRateExample example = createTakeheartFixRateExample(vo);
		example.setLimitStart(offset);
		example.setLimitEnd(limit);
		return takeheartFixRateMapper.selectByExample(example);
	}

	/**
	 * 生成datagrid表格需要的结果
	 * 
	 * @param vo
	 *            TakeheartFixRate实体bean
	 * @param page
	 *            翻页信息
	 * @return GridPage<TakeheartFixRate>
	 */
	@Override
	public GridPage<TakeheartFixRate> listGrid(TakeheartFixRate vo, RowBounds page) {
		TakeheartFixRateExample example = createTakeheartFixRateExample(vo);
		example.setLimitStart(page.getOffset());
		example.setLimitEnd(page.getLimit());

		GridPage<TakeheartFixRate> result = new GridPage<TakeheartFixRate>();
		result.setRows(takeheartFixRateMapper.selectByExample(example));
		result.setTotal(takeheartFixRateMapper.countByExample(example));
		return result;
	}

	/**
	 * 根据id取数据
	 * 
	 * @param id
	 * @return
	 */
	@Override
	public TakeheartFixRate findByPk(int id) {
		return takeheartFixRateMapper.selectByPrimaryKey(id);
	}

	/**
	 * 保存实体bean
	 * 
	 * @param vo
	 * @throws Exception
	 */
	@Transactional()
	@Override
	public void save(TakeheartFixRate vo) {
		takeheartFixRateMapper.insertSelective(vo);
	}

	/**
	 * 更新实体bean
	 * 
	 * @param vo
	 *            TakeheartFixRate
	 */
	@Override
	@Transactional()
	public void update(TakeheartFixRate vo) {
		takeheartFixRateMapper.updateByPrimaryKeySelective(vo);
	}

	/**
	 * 删除
	 * 
	 * @param id
	 */
	@Override
	@Transactional()
	public void delete(int id) {
		takeheartFixRateMapper.deleteByPrimaryKey(id);
	}
}
