package com.mrbt.lingmoney.admin.service.info;

import org.springframework.web.multipart.MultipartFile;

import com.mrbt.lingmoney.model.InfoClientBanner;
import com.mrbt.lingmoney.utils.PageInfo;

/**
 * 手机端banner
 * 
 * @author luox
 * @Date 2017年5月11日
 */
public interface InfoClientBannerService {

	/**
	 * 保存
	 * 
	 * @param vo
	 *            vo
	 * @param file1
	 *            file1
	 * @param file2
	 *            file2
	 * @param file3
	 *            file3
	 * @param file4
	 *            file4
	 * @param file5
	 *            file5
	 * @param file6
	 *            file6
	 * @param bannerRootPath
	 *            bannerRootPath
	 */
	void save(InfoClientBanner vo, MultipartFile file1, MultipartFile file2, MultipartFile file3, MultipartFile file4,
			MultipartFile file5, MultipartFile file6, String bannerRootPath);

	/**
	 * 更新
	 * 
	 * @param vo
	 *            vo
	 * @param file1
	 *            file1
	 * @param file2
	 *            file2
	 * @param file3
	 *            file3
	 * @param file4
	 *            file4
	 * @param file5
	 *            file5
	 * @param file6
	 *            file6
	 * @param bannerRootPath
	 *            bannerRootPath
	 */
	void update(InfoClientBanner vo, MultipartFile file1, MultipartFile file2, MultipartFile file3, MultipartFile file4,
			MultipartFile file5, MultipartFile file6, String bannerRootPath);

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
	 *            InfoClientBanner
	 * @param pageInfo
	 *            pageInfo
	 */
	void listGrid(InfoClientBanner vo, PageInfo pageInfo);

	/**
	 * 删除
	 * 
	 * @param id
	 *            id
	 */
	void delete(Integer id);

	/**
	 * 刷新手机端banner redis
	 */
	void refresh();

}
