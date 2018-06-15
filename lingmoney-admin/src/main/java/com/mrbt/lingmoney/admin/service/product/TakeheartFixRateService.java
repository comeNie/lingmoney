package com.mrbt.lingmoney.admin.service.product;

import java.util.List;

import org.apache.ibatis.session.RowBounds;

import com.mrbt.lingmoney.model.TakeheartFixRate;
import com.mrbt.lingmoney.model.TakeheartFixRateExample;
import com.mrbt.lingmoney.utils.GridPage;

/**
 * 
 * 随心取固定收益率设置
 *
 */
public interface TakeheartFixRateService {

	/**
	 * 生成辅助类根据实体bean
	 * 
	 * @param vo
	 *            实体bean
	 * @return TakeheartFixRateExample辅助类
	 */
	TakeheartFixRateExample createTakeheartFixRateExample(TakeheartFixRate vo);

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
	List<TakeheartFixRate> list(TakeheartFixRate vo, int offset, int limit);

	/**
	 * 生成datagrid表格需要的结果
	 * 
	 * @param vo
	 *            TakeheartFixRate实体bean
	 * @param page
	 *            翻页信息
	 * @return GridPage<TakeheartFixRate>
	 */
	GridPage<TakeheartFixRate> listGrid(TakeheartFixRate vo, RowBounds page);

	/**
	 * 根据id取数据
	 * 
	 * @param id
	 *            id
	 * @return 数据返回
	 */
	TakeheartFixRate findByPk(int id);

	/**
	 * 保存实体bean
	 * 
	 * @param vo
	 *            TakeheartFixRate
	 * @throws Exception
	 */
	void save(TakeheartFixRate vo);

	/**
	 * 更新实体bean
	 * 
	 * @param vo
	 *            TakeheartFixRate
	 */

	void update(TakeheartFixRate vo);

	/**
	 * 删除
	 * 
	 * @param id
	 *            id
	 */
	void delete(int id);
}
