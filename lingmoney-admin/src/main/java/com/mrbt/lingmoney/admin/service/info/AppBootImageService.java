package com.mrbt.lingmoney.admin.service.info;

import org.springframework.web.multipart.MultipartFile;

import com.mrbt.lingmoney.model.AppBootImage;
import com.mrbt.lingmoney.utils.PageInfo;

/**
 *@author syb
 *@date 2017年5月22日 下午3:39:35
 *@version 1.0
 *@description 手机端引导图片service
 **/
public interface AppBootImageService {

	/**
	 * 查询所有加载图片
	 * 
	 * @param pageNo
	 *            页数
	 * @param pageSize
	 *            行数
	 * @return 数据返回
	 */
	PageInfo listBootImages(Integer pageNo, Integer pageSize);

	/**
	 * 保存/更新 引导图片信息
	 * 
	 * @param params
	 *            AppBootImage
	 * @param file
	 *            file
	 * @return 数据返回
	 */
	PageInfo saveBootImage(AppBootImage params, MultipartFile file);

}
