package com.mrbt.lingmoney.admin.service.info;

import org.springframework.web.multipart.MultipartFile;

import com.mrbt.lingmoney.model.InfoClientVersion;
import com.mrbt.lingmoney.utils.PageInfo;

/**
 * 手机版本号
 * 
 * @author luox
 * @Date 2017年5月11日
 */
public interface InfoClientVersionService {

	/**
	 * 分页查询
	 * 
	 * @param vo
	 *            InfoClientVersion
	 * @param pageInfo
	 *            PageInfo
	 */
	void listGrid(InfoClientVersion vo, PageInfo pageInfo);

	/**
	 * 删除
	 * 
	 * @param int1
	 *            数据id
	 */
	void delete(int int1);

	/**
	 * 新增
	 * 
	 * @param vo
	 *            InfoClientVersion
	 */
	void save(InfoClientVersion vo);

	/**
	 * 修改
	 * 
	 * @param vo
	 *            InfoClientVersion
	 */
	void update(InfoClientVersion vo);

	/**
	 * 上传新的版本图片（手机端）
	 * 
	 * @param file
	 *            file
	 * @param bannerRootPath
	 *            bannerRootPath
	 */
	void putImageUrl(MultipartFile file, String bannerRootPath);

	/**
	 * 禁用、开通版本更新图片
	 * 
	 * @param status
	 *            0禁用 1开通
	 * @return 数据返回
	 */
	Integer imageUrlAllow(Integer status);

	/**
	 * redis提取图片
	 * 
	 * @return 数据返回
	 */
	String showImageUrl();

}
