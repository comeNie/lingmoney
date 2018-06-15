package com.mrbt.lingmoney.admin.service.info;

import org.springframework.web.multipart.MultipartFile;

import com.mrbt.lingmoney.model.InfoBanner;
import com.mrbt.lingmoney.utils.PageInfo;

/**
 * 首页小banner
 * 
 * @author lihq
 * @date 2017年5月5日 下午3:42:07
 * @description
 * @version 1.0
 * @since 2017-04-12
 */
public interface InfoBannerService {

	/**
	 * 删除
	 * 
	 * @param id
	 *            id
	 */
	void delete(int id);

	/**
	 * 分页查询
	 * 
	 * @param vo
	 *            InfoBanner
	 * @param pageInfo
	 *            pageInfo
	 * @return 数据返回
	 */
	PageInfo getList(InfoBanner vo, PageInfo pageInfo);

	/**
	 * 保存
	 * 
	 * @param vo
	 *            InfoBanner
	 * @param bannerRootPath
	 *            bannerRootPath
	 * @param file
	 *            file
	 */
	void save(InfoBanner vo, MultipartFile file, String bannerRootPath);

	/**
	 * 更新
	 * 
	 * @param vo
	 *            InfoBanner
	 * @param bannerRootPath
	 *            bannerRootPath
	 * @param file
	 *            file
	 */
	void update(InfoBanner vo, MultipartFile file, String bannerRootPath);

	/**
	 * 更改状态
	 * 
	 * @param id
	 *            id
	 * @return 数据返回
	 */
	boolean changeStatus(Integer id);

	/**
	 * 刷新redis
	 * 
	 * @param vo
	 *            InfoBanner
	 * @param pageInfo
	 *            PageInfo
	 * @return 数据返回
	 */
	PageInfo reload(InfoBanner vo, PageInfo pageInfo);
}
