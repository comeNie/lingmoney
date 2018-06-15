package com.mrbt.lingmoney.admin.service.info;

import java.util.List;

import com.mrbt.lingmoney.model.InfoNotice;
import com.mrbt.lingmoney.model.InfoNoticeExample;
import com.mrbt.lingmoney.utils.PageInfo;

/**
 * 站内公告
 * 
 * @author lihq
 * @date 2017年5月19日 上午10:46:07
 * @description
 * @version 1.0
 * @since 2017-04-12
 */
public interface InfoNoticeService {

	/**
	 * 生成辅助类根据实体bean
	 * 
	 * @param vo
	 *            实体bean
	 * @return InfoNoticeExample辅助类
	 */
	InfoNoticeExample createInfoNoticeExample(InfoNotice vo);

	/**
	 * 删除
	 * 
	 * @param id
	 *            id
	 */
	void delete(int id);

	/**
	 * 查询列表
	 * 
	 * @param vo
	 *            InfoNotice
	 * @param offset
	 *            offset
	 * @param limit
	 *            limit
	 * @return 数据返回
	 */
	List<InfoNotice> list(InfoNotice vo, int offset, int limit);

	/**
	 * 返回text的内容
	 * 
	 * @param id
	 *            id
	 * @return 数据返回
	 */
	String listContent(int id);

	/**
	 * 根据id取数据，不带blob字段
	 * 
	 * @param id
	 *            id
	 * @return 数据返回
	 */
	InfoNotice listByIdNotBlob(int id);

	/**
	 * 发布
	 * 
	 * @param id
	 *            id
	 * @return 数据返回
	 */
	boolean publish(int id);

	/**
	 * 保存
	 * 
	 * @param vo
	 *            InfoNotice
	 */
	void save(InfoNotice vo);

	/**
	 * 更新
	 * 
	 * @param vo
	 *            InfoNotice
	 */
	void update(InfoNotice vo);

	/**
	 * 刷新
	 * 
	 * @param infoNoticeList
	 *            infoNoticeList
	 */
	@SuppressWarnings("rawtypes")
	void reload(List infoNoticeList);

	/**
	 * 更改状态
	 * 
	 * @param id
	 *            id
	 * @return 数据返回
	 */
	boolean changeStatus(Integer id);

	/**
	 * 分页查询
	 * 
	 * @param vo
	 *            InfoNotice
	 * @param pageInfo
	 *            PageInfo
	 * @return 数据返回
	 */
	PageInfo list(InfoNotice vo, PageInfo pageInfo);

}
