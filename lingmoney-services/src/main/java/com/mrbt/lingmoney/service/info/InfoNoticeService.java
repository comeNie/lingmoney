package com.mrbt.lingmoney.service.info;

import org.springframework.ui.ModelMap;

import com.mrbt.lingmoney.model.InfoNotice;
import com.mrbt.lingmoney.utils.GridPage;
import com.mrbt.lingmoney.utils.PageInfo;

/**
 * 公告
 *
 */
public interface InfoNoticeService {

	/**
	 * 查询公告列表
	 * @author YJQ  2017年4月18日
	 * @param page 页码
	 * @param rows 页数
	 * @return pageinfo
	 */
	PageInfo queryNoticeList(Integer page, Integer rows);

	/**
	 * 查询公告详情
	 * @author YJQ  2017年4月18日
	 * @param id 公告id
	 * @return pageinfo
	 */
	PageInfo queryNoticeDetail(Integer id);

	/**
	 * 站内公告，分页信息
	 * @param pageNo 页码
	 * @return 分页信息
	 */
	GridPage<InfoNotice> listGrid(Integer pageNo);

	/**
	 * 包装详情页数据
	 * @param modelMap model
	 * @param id id
	 */
	void packageDetailInfo(ModelMap modelMap, Integer id);

	/**
	 * 获取所有消息
	 * 
	 * @return pageInfo
	 */
	PageInfo allAessage();

	/**
	 * 查询活动公告列表
	 * 
	 * @param pageNo pageNo
	 * @param pageSize pageSize
	 * @return pageInfo
	 */
	PageInfo queryInfoClientBannerList(Integer pageNo, Integer pageSize);

}
