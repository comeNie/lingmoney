package com.mrbt.lingmoney.admin.service.info;

import com.mrbt.lingmoney.model.UsersAsk;
import com.mrbt.lingmoney.utils.PageInfo;

/**
 * 你问我答
 * 
 * @author luox
 * @Date 2017年5月10日
 */
public interface UsersAskService {

	/**
	 * 分页查询
	 * 
	 * @param vo
	 *            UsersAsk
	 * @param pageInfo
	 *            PageInfo
	 */
	void listGrid(UsersAsk vo, PageInfo pageInfo);

	/**
	 * 删除
	 * 
	 * @param id
	 *            id
	 */
	void delete(Integer id);

	/**
	 * 更改热门状态
	 * 
	 * @param id
	 *            id
	 */
	void publish(Integer id);

	/**
	 * 修改
	 * 
	 * @param vo
	 *            UsersAsk
	 */
	void update(UsersAsk vo);

	/**
	 * 新增
	 * 
	 * @param vo
	 *            UsersAsk
	 */
	void save(UsersAsk vo);

	/**
	 * 返回回答的内容
	 * 
	 * @param askId
	 *            askId
	 * @return 数据返回
	 */
	String listAnwser(Integer askId);

	/**
	 * 根据回答查找问题主键
	 * 
	 * @param askId
	 *            askId
	 * @return 数据返回
	 */
	Integer anwser(Integer askId);

	/**
	 * 刷新redis
	 */
	void reload();

	/**
	 * 更改问题状态
	 * 
	 * @param id
	 *            id
	 * @return 数据返回
	 */
	boolean changeStatus(Integer id);
}
