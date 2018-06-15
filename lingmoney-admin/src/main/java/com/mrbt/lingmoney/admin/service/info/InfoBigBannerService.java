package com.mrbt.lingmoney.admin.service.info;

import org.springframework.web.multipart.MultipartFile;

import com.mrbt.lingmoney.model.InfoBigBanner;
import com.mrbt.lingmoney.utils.PageInfo;

/**
 * 首页大banner
 * 
 * @author lihq
 * @date 2017年5月5日 下午3:17:22
 * @description
 * @version 1.0
 * @since 2017-04-12
 */
public interface InfoBigBannerService {

	/**
	 * 删除
	 * 
	 * @param id
	 *            id
	 */
	void delete(int id);

	/**
	 * 保存
	 * 
	 * @param vo
	 *            InfoBigBanner
	 * @param file
	 *            file
	 * @param bannerRootPath
	 *            bannerRootPath
	 */
	void save(InfoBigBanner vo, String bannerRootPath, MultipartFile file);

	/**
	 * 更新
	 * 
	 * @param vo
	 *            InfoBigBanner
	 * @param bannerRootPath
	 *            bannerRootPath
	 * @param file
	 *            file
	 */
	void update(InfoBigBanner vo, MultipartFile file, String bannerRootPath);

	/**
	 * 分页查询
	 * 
	 * @param vo
	 *            InfoBigBanner
	 * @param pageInfo
	 *            pageInfo
	 * @return 数据返回
	 */
	PageInfo getList(InfoBigBanner vo, PageInfo pageInfo);

	/**
	 * 更改状态
	 * 
	 * @param id
	 *            主键
	 * @return 分页实体类
	 */
	boolean changeStatus(Integer id);

	/**
	 * 刷新PC首页BANNER redis
	 */
	void refresh();
}
