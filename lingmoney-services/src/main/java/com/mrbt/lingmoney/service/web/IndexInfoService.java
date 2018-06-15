package com.mrbt.lingmoney.service.web;

import org.springframework.ui.ModelMap;

import com.mrbt.lingmoney.utils.PageInfo;

/**
 *@author syb
 *@date 2017年5月3日 下午3:57:44
 *@version 1.0
 *@description 领钱儿首页展示信息封装service
 **/
public interface IndexInfoService {

    /**
     * 包装首页展示数据
     * @param modelMap 
     * @param cityCode 城市代码
     *
     */
    void packIndexInfo(ModelMap modelMap, String cityCode);

	/**
	 * Wap 首页
	 * 
	 * @param cityCode
	 *            cityCode
	 * @param pageInfo
	 *            pageaInfo
	 * @return return
	 */
	PageInfo packIndexInfoOfWap(PageInfo pageInfo, String cityCode);

	/**
	 * 查询累计数据，PC首页展示
	 * @param pageInfo
	 */
	void queryTotalData(PageInfo pageInfo);
}
