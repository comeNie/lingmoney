package com.mrbt.lingmoney.service.common;

import com.mrbt.lingmoney.utils.PageInfo;

/**
 * APP启动 
 */
public interface AppBootService {

	/**
	 * 查询启动图片
	 * @param sizeCode 图片尺寸
	 * @param cityCode 城市代码
	 * @param pageInfo pageinfo
	 */
	void queryAppBootImage(String sizeCode, String cityCode, PageInfo pageInfo);
	
	/**
	 * 查询APP升级接口
	 * @param version 版本号
	 * @param type 类型,0安卓，1苹果
	 * @param pageInfo pageinfo
	 */
	void queryUpdateVersion(String version, Integer type, PageInfo pageInfo);

	/**
	 * 查询启动图片
	 * 
	 * @param sizeCode 图片尺寸
	 * @param cityCode 城市代码
	 * @return pageInfo
	 */
	PageInfo queryAppBootImage(String sizeCode, String cityCode);

}
