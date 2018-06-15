package com.mrbt.lingmoney.admin.service.schedule;

import java.util.List;

import com.mrbt.lingmoney.model.ProductStatistics;

/**
 *@author syb
 *@date 2017年5月18日 下午2:38:10
 *@version 1.0
 *@description 产品统计定时任务service
 **/
public interface ProductStatisticsService {

	/**
	 * 查询并保存产品统计数据
	 * 
	 * @return 数据返回
	 */
	List<ProductStatistics> recommendLineStatistics();

	/**
	 * 导出产品统计
	 * 
	 * @param productStatisticsList
	 *            ProductStatistics对象集合
	 */
	void exportProductStatistics(List<ProductStatistics> productStatisticsList);

}
