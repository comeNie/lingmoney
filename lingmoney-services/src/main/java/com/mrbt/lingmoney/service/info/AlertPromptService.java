package com.mrbt.lingmoney.service.info;

import com.mrbt.lingmoney.utils.PageInfo;

/**
 * 首页弹框提示
 * 
 * @author lihq
 * @date 2017年7月7日 上午8:43:28
 * @description
 * @version 1.0
 * @since 2017-04-12
 */
public interface AlertPromptService {

	/**
	 * 首页弹框提示
	 * 
	 * @param type 弹框类型。0开户 1激活 2普通 3注册(红包活动弹框)
	 * @param token token
	 * @param needLogin 是否需要登录 'Y'需要 'N'不需要
	 * @return pageinfo
	 *
	 */
	PageInfo alertPromptInfo(Integer type, String uId, String needLogin);

}
