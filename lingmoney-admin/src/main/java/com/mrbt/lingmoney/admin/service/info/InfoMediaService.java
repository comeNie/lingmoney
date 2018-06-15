package com.mrbt.lingmoney.admin.service.info;

import java.util.List;

import org.apache.ibatis.session.RowBounds;

import com.mrbt.lingmoney.model.InfoMedia;
import com.mrbt.lingmoney.model.InfoMediaExample;
import com.mrbt.lingmoney.utils.GridPage;
import com.mrbt.lingmoney.utils.PageInfo;

/**
 * 页面设置——》媒体资讯
 * 
 */
public interface InfoMediaService {

	/**
	 * 生成辅助类根据实体bean
	 * 
	 * @param vo
	 *            实体bean
	 * @return InfoMediaExample辅助类
	 */
	InfoMediaExample createInfoMediaExample(InfoMedia vo);

	/**
	 * 删除媒体资讯
	 * 
	 * @param id
	 *            id
	 */
	void delete(int id);

	/**
	 * 查询列表
	 * 
	 * @param vo
	 *            InfoMedia
	 * @param offset
	 *            offset
	 * @param limit
	 *            limit
	 * @return 数据返回
	 */
	List<InfoMedia> list(InfoMedia vo, int offset, int limit);

	/**
	 * 生成datagrid表格需要的结果
	 * 
	 * @param vo
	 *            InfoMedia实体bean
	 * @param page
	 *            翻页信息
	 * @return GridPage<AdminBanner>
	 */
	GridPage<InfoMedia> listGrid(InfoMedia vo, RowBounds page);

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
	InfoMedia listByIdNotBlob(int id);

	/**
	 * 发布媒体新闻 <br/>
	 * 分数取到秒，因为后面的三位毫秒级数据库不存
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
	 *            InfoMedia
	 * @throws Exception
	 *             异常
	 */
	void save(InfoMedia vo) throws Exception;

	/**
	 * 更新
	 * 
	 * @param vo
	 *            InfoMedia
	 */
	void update(InfoMedia vo);

	/**
	 * 刷新
	 * 
	 * @param infoMediaList
	 *            infoMediaList
	 */
	@SuppressWarnings("rawtypes")
	void reload(List infoMediaList);

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
	 *            InfoMedia
	 * @param pageInfo
	 *            PageInfo
	 * @return 数据返回
	 */
	PageInfo list(InfoMedia vo, PageInfo pageInfo);

}
