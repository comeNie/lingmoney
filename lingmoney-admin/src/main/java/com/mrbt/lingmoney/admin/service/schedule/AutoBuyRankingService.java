package com.mrbt.lingmoney.admin.service.schedule;
/**
 *@author syb
 *@date 2017年5月18日 下午1:38:53
 *@version 1.0
 *@description 每月赠送领宝
 **/
public interface AutoBuyRankingService {

	/**
	 * 每月赠送领宝
	 * 每月第一位购买、最后一位购买、以及每月购买前三名 赠送50领宝
	 */
	void monthlySendLingbao();
	
}
