package com.mrbt.lingmoney.admin.service.product;

import java.util.List;

import org.apache.ibatis.session.RowBounds;

import com.mrbt.lingmoney.model.TakeheartCategory;
import com.mrbt.lingmoney.model.TakeheartCategoryExample;
import com.mrbt.lingmoney.utils.GridPage;

/**
 * 
 * 产品设置——》随心取分类
 *
 */
public interface TakeheartCategoryService {

	/**
	 * 生成辅助类根据实体bean
	 * 
	 * @param vo
	 *            实体bean
	 * @return TakeheartCategoryExample辅助类
	 */
	TakeheartCategoryExample createTakeheartCategoryExample(TakeheartCategory vo);

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
	List<TakeheartCategory> list(TakeheartCategory vo, int offset, int limit);

	/**
	 * 生成datagrid表格需要的结果
	 * 
	 * @param vo
	 *            TakeheartCategory实体bean
	 * @param page
	 *            翻页信息
	 * @return GridPage<TakeheartCategory>
	 */
	GridPage<TakeheartCategory> listGrid(TakeheartCategory vo, RowBounds page);

	/**
	 * 保存实体bean
	 * 
	 * @param vo
	 *            TakeheartCategory
	 */
	void save(TakeheartCategory vo);

	/**
	 * 更新实体bean
	 * 
	 * @param vo
	 *            TakeheartCategory
	 */
	void update(TakeheartCategory vo);

	/**
	 * 删除
	 * 
	 * @param id
	 *            id
	 */
	void delete(int id);

	/**
	 * 主键查找
	 * 
	 * @param id
	 *            id
	 * @return 数据返回
	 */
	TakeheartCategory findByPk(int id);
}
