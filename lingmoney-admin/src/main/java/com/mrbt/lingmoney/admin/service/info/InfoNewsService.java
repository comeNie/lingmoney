package com.mrbt.lingmoney.admin.service.info;

import com.mrbt.lingmoney.model.InfoNews;
import com.mrbt.lingmoney.utils.PageInfo;

/**
 * 公司新闻
 * 
 * @author luox
 * @Date 2017年5月10日
 */
public interface InfoNewsService {

	/**
	 * 分页查询
	 * 
	 * @param vo
	 *            InfoNews
	 * @param pageInfo
	 *            PageInfo
	 */
	void listGrid(InfoNews vo, PageInfo pageInfo);

	/**
	 * 查询text内容
	 * 
	 * @param id
	 *            id
	 * @return 数据返回
	 */
	String listContent(Integer id);

	/**
	 * 删除
	 * 
	 * @param id
	 *            id
	 */
	void delete(Integer id);

	/**
	 * 发布
	 * 
	 * @param id
	 *            id
	 */
	void publish(Integer id);

	/**
	 * 新增
	 * 
	 * @param vo
	 *            InfoNews
	 */
	void save(InfoNews vo);

	/**
	 * 修改
	 * 
	 * @param vo
	 *            InfoNews
	 */
	void update(InfoNews vo);

	/**
	 * 更改状态
	 * 
	 * @param id
	 *            InfoNews
	 * @return 数据返回
	 */
	boolean changeStatus(Integer id);

}
