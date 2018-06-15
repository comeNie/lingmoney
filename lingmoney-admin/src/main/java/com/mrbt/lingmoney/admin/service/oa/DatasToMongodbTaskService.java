package com.mrbt.lingmoney.admin.service.oa;

/**
 * 用户设置
 * 
 * @date 2017年5月18日 下午2:59:27
 * @description
 * @version 1.0
 * @since 2017-04-12
 */
public interface DatasToMongodbTaskService {

	/**
	 * 用户信息同步mongodb
	 * 
	 */
	void usersDatasToMongodbTask();
	
	
	/**
	 * 产品信息和交易信息同步mongodb
	 * 
	 */
	void tradingDatasToMongodbTask();

}
