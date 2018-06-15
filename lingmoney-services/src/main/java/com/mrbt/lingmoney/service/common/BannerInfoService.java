package com.mrbt.lingmoney.service.common;

import com.mrbt.lingmoney.utils.PageInfo;

/**
 * bannerinfo
 */
public interface BannerInfoService {

    /**
     * 查询首页主banner
     * 
     * @param sizeCode 尺寸
     * @param cityCode 城市代码
     * @param pageInfo pageinfo
     * @return pageinfo
     *
     */
	PageInfo queryhomeMainBanner(String sizeCode, String cityCode, PageInfo pageInfo);

	/**
	 * 获取PC端首页BANNER
	 * @param cityCode	城市代码
	 * @param pageInfo	返回包装方法
	 */
	void queryPcHomeBanner(String cityCode, PageInfo pageInfo);

}
