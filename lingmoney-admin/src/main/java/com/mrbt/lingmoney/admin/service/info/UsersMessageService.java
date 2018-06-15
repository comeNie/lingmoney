package com.mrbt.lingmoney.admin.service.info;

import java.util.List;

import com.mrbt.lingmoney.model.UsersMessage;
import com.mrbt.lingmoney.utils.PageInfo;

/**
 * 用户消息
 * 
 * @author luox
 * @Date 2017年5月10日
 */
public interface UsersMessageService {

	/**
	 * 删除
	 * 
	 * @param id
	 *            id
	 */
	void delete(Integer id);

	/**
	 * 分页查询
	 * 
	 * @param vo
	 *            UsersMessage
	 * @param pageInfo
	 *            PageInfo
	 */
	void listGrid(UsersMessage vo, PageInfo pageInfo);

	/**
	 * 更改阅读状态
	 * 
	 * @param id
	 *            id
	 * @return 数据返回
	 */
	boolean reader(Integer id);

	/**
	 * 查询text内容
	 * 
	 * @param id
	 *            id
	 * @return 数据返回
	 */
	String listContent(Integer id);

	/**
	 * 批量插入
	 * 
	 * @param list
	 *            list
	 * @return 数据返回
	 */
	long insertCountList(List<UsersMessage> list);
}
