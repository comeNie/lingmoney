package com.mrbt.lingmoney.service.info;

import org.springframework.ui.ModelMap;

import com.mrbt.lingmoney.model.InfoMedia;
import com.mrbt.lingmoney.utils.GridPage;

/**
 *@author syb
 *@date 2017年5月8日 下午3:52:21
 *@version 1.0
 *@description 
 **/
public interface InfoMediaService {

	/**
	 * 媒体资讯 分页信息
	 * @param pageNo 分页页数
	 * @return 分页信息
	 */
	GridPage<InfoMedia> listGrid(Integer pageNo);

	/**
	 * 详情页信息处理
	 * @param modelMap model
	 * @param id id
	 */
	void packageDetailInfo(ModelMap modelMap, Integer id);

}
