package com.mrbt.lingmoney.service.common;

import com.mrbt.lingmoney.utils.PageInfo;

/**
 * 图片验证码
 */
public interface PictureVerifyService {
	
	/**
	 * 保存图片验证码
	 * @param picKey key
	 * @param code	值
	 */
	void save(String picKey, String code);

	/**
	 * 验证图片验证码
	 * @param picKey 图片key
	 * @param code code
	 * @param pageInfo pageinfo
	 */
	void varifyPicCode(String picKey, String code, PageInfo pageInfo);

}
