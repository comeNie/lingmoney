package com.mrbt.lingmoney.admin.service.info;

import com.mrbt.lingmoney.utils.PageInfo;

/**
 * 页面设置——》小功能设置
 *
 */
public interface InfoSettingService {

	/**
	 * 设置赠送螃蟹个数
	 * 
	 * @param crabCount
	 *            crabCount
	 */
	void setCrabCount(Integer crabCount);

	/**
	 * 后台展示赠送螃蟹个数
	 * 
	 * @return 数据返回
	 */
	PageInfo showCrabCount();
}
