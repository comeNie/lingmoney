package com.mrbt.lingmoney.service.web;

import com.mrbt.lingmoney.model.AreaDomain;

/**
 *@author syb
 *@date 2017年5月3日 下午3:39:45
 *@version 1.0
 *@description 
 **/
public interface AreaDomainService {
	/**
	 * 根据城市代码获取相关信息
	 * @param cityCode 城市代码
	 * @return 域名信息
	 */
    AreaDomain queryByCityCode(String cityCode);

}
