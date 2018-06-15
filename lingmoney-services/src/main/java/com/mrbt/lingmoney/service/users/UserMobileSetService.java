package com.mrbt.lingmoney.service.users;

import com.mrbt.lingmoney.utils.PageInfo;

/**
 * 用户移动端相关设置接口
 * @author YJQ
 *
 */
public interface UserMobileSetService {

	/**
     * 查询用户移动端属性
     * @author YJQ  2017年4月18日
     * @param key redis中保存uid的key
     * @return pageInfo
     * @throws Exception 
     */
	PageInfo queryUserMobileProp(String key) throws Exception;

	/**
	 * 修改手势密码
	 * @author YJQ  2017年4月18日
	 * @param key redis中保存uid的key
	 * @param gesturePwd  0-表删除  其他-正常手势密码
	 * @return pageinfo
	 * @throws Exception 
	 */
	PageInfo modifyGesturePwd(String gesturePwd, String key) throws Exception;

	/**
	 * 开启/关闭手势密码
	 * @author YJQ  2017年4月18日
	 * @param key redis中保存uid的key
	 * @param status 0-关闭 1-开启
	 * @return pageinfo
	 * @throws Exception 
	 */
	PageInfo modifyGestureStatus(Integer status, String key) throws Exception;

	/**
	 * 开启/关闭消息推送
	 * @author YJQ  2017年4月18日
	 * @param key redis中保存uid的key
	 * @param status 是否开启消息推送 0-否 1-是
	 * @return pageinfo
	 * @throws Exception 
	 */
	PageInfo modifyPushStatus(Integer status, String key) throws Exception;

}
