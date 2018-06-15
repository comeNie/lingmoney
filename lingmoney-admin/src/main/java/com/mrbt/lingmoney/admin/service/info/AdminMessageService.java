package com.mrbt.lingmoney.admin.service.info;

import com.mrbt.lingmoney.model.AdminMessageWithBLOBs;
import com.mrbt.lingmoney.utils.PageInfo;

/**
 * 发送消息
 * 
 * @author lihq
 * @date 2017年5月12日 下午1:53:20
 * @description
 * @version 1.0
 * @since 2017-04-12
 */
public interface AdminMessageService {

	/**
	 * 删除
	 * 
	 * @param id
	 *            id
	 */
	void delete(int id);

	/**
	 * 查找列表
	 * 
	 * @param vo
	 *            AdminMessageWithBLOBs
	 * @param pageInfo
	 *            分页信息
	 */
	void listGrid(AdminMessageWithBLOBs vo, PageInfo pageInfo);

	/**
	 * 保存实体bean
	 * 
	 * @param vo
	 *            AdminMessageWithBLOBs
	 */
	void save(AdminMessageWithBLOBs vo);

	/**
	 * 更新实体bean
	 * 
	 * @param vo
	 *            AdminMessageWithBLOBs
	 */
	void update(AdminMessageWithBLOBs vo);

	/**
	 * 查找内容
	 * 
	 * @param id
	 *            id
	 * @return 数据返回
	 */
	String listContent(int id);

	/**
	 * 管理员发送信息
	 * 
	 * @param vo
	 *            AdminMessageWithBLOBs
	 * @param pageInfo
	 *            分页信息
	 * @param partOrALl
	 *            0给部分人发送 1给所有人发送
	 */
	void sendMessage(AdminMessageWithBLOBs vo, PageInfo pageInfo, Integer partOrALl);

}
