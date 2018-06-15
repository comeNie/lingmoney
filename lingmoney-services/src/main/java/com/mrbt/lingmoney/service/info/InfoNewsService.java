package com.mrbt.lingmoney.service.info;

import org.springframework.ui.ModelMap;

import com.mrbt.lingmoney.model.InfoNews;
import com.mrbt.lingmoney.utils.GridPage;

/**
 *@author syb
 *@date 2017年5月8日 下午4:13:55
 *@version 1.0
 *@description 
 **/
public interface InfoNewsService {
	/**
	 * 查询分页数据
	 * @param pageNo 页码
	 * @return 分页数据
	 */
	GridPage<InfoNews> listGrid(Integer pageNo);

	/**
	 * 包装详情页数据
	 * @param modelMap model
	 * @param id id
	 */
	void packageDetailsInfo(ModelMap modelMap, Integer id);

}
