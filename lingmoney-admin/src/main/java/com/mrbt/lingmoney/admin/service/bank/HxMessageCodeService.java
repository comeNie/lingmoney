package com.mrbt.lingmoney.admin.service.bank;

import com.mrbt.lingmoney.utils.PageInfo;

/**
 * @author syb
 * @date 2017年7月13日 下午4:03:58
 * @version 1.0
 * @description 获取短信验证码
 **/
public interface HxMessageCodeService {
	/**
	 * 获取验证码
	 * 
	 * @param type
	 *            类型 1：自动投标撤销 2：自动还款授权撤销 0：默认
	 * @param accountId
	 *            账户id
	 * @return	return
	 */
	PageInfo getMessageCode(String type, String accountId);
}
