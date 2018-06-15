package com.mrbt.lingmoney.service.users;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.multipart.MultipartFile;

import com.mrbt.lingmoney.model.UsersProperties;
import com.mrbt.lingmoney.utils.PageInfo;

/**
 * 用户账户设置相关接口
 * 
 * @author YJQ
 *
 */
public interface UsersAccountSetService {

	/**
	 * 修改头像
	 * 
	 * @author YJQ 2017年4月17日
	 * @param request req
	 * @param image 头像地址
	 * @param token 用户token
	 * @return pageinfo
	 * @throws Exception 
	 */
	PageInfo modifyAvatar(HttpServletRequest request, String image, String token) throws Exception;

	/**
	 * 修改用户头像 MultipartFile方式
	 * 
	 * @author YJQ 2017年5月20日
	 * @param file 文件
	 * @param key redis中uid的key
	 * @return pageinfo 
	 * @throws Exception 
	 */
	PageInfo modifyAvatar(MultipartFile file, String key) throws Exception;

	/**
	 * 修改昵称
	 * 
	 * @author YJQ 2017年4月17日
	 * @param token reids中uid的key
	 * @param nickName 用户昵称
	 * @return pageinfo 
	 * @throws Exception 
	 */
	PageInfo modifyNickName(String nickName, String token) throws Exception;

	/**
	 * 发送验证邮件
	 * 
	 * @author YJQ 2017年4月17日
	 * @param email email地址
	 * @param token 用户token
	 * @return pageinfo
	 * @throws Exception 
	 */
	PageInfo sendVailEmail(String email, String token) throws Exception;

	/**
	 * 设置邮箱
	 * 
	 * @author YJQ 2017年4月17日
	 * @param token 用户token
	 * @param email 邮件地址
	 * @param vailCode 验证码
	 * @return pageinfo
	 * @throws Exception 
	 */
	PageInfo setEmail(String email, String vailCode, String token) throws Exception;

	/**
	 * 获取用户有效地址列表，按是否默认倒序，创建时间倒序
	 * 
	 * @author YJQ 2017年4月17日
	 * @param token 用户token
	 * @param page 页数
	 * @param rows 每页条数
	 * @return pageinfo
	 * @throws Exception 
	 */
	PageInfo queryAddress(Integer page, Integer rows, String token) throws Exception;

	/**
	 * 添加地址
	 * 
	 * @author YJQ 2017年4月18日
	 * @version 1.0
	 * @param name 姓名
	 * @param telephone 手机号
	 * @param province 省
	 * @param city 市
	 * @param town 镇
	 * @param address 详细地址
	 * @param isFirst 是否初次添加
	 * @param token 用户token
	 * @return pageinfo
	 * @throws Exception 
	 *
	 */
	PageInfo addAddress(String name, String telephone, String province, String city, String town, String address,
			Integer isFirst, String token) throws Exception;

	/**
	 * 修改地址
	 * 
	 * @author YJQ 2017年4月18日
	 * @version 1.0
	 * @param id 地址id
	 * @param name 姓名
	 * @param telephone 手机号
	 * @param province 省
	 * @param city 市
	 * @param town 镇
	 * @param address 详细地址
	 * @param key redis中uid的key
	 * @return pageinfo 
	 * @throws Exception 
	 *
	 */
	PageInfo modifyAddress(Integer id, String name, String telephone, String province, String city, String town,
			String address, String key) throws Exception;

	/**
	 * 设置默认地址
	 * 
	 * @author YJQ 2017年4月18日
	 * @param token 用户token
	 * @param id 地址id
	 * @return pageinfo
	 * @throws Exception 
	 */
	PageInfo setDefaultAddress(Integer id, String token) throws Exception;

	/**
	 * 删除地址
	 * 
	 * @author YJQ 2017年4月18日
	 * @param key redis中uid的key
	 * @param id 地址id
	 * @return pageinfo 
	 * @throws Exception 
	 */
	PageInfo deleteAddress(Integer id, String key) throws Exception;

	/**
	 * 忘记密码
	 * 
	 * @author YJQ 2017年4月18日
	 * @param telephone 手机号
	 * @param password 新密码
	 * @param verificationCode 验证码
	 * @return pageinfo 
	 * @throws Exception 
	 */
	PageInfo retrievePassword(String telephone, String password, String verificationCode) throws Exception;

	/**
	 * 修改密码
	 * 
	 * @author YJQ 2017年5月19日
	 * @param oldPassword 旧密码
	 * @param password 新密码
	 * @param token 用户token
	 * @return pageinfo
	 * @throws Exception 
	 */
	PageInfo modifyPassword(String oldPassword, String password, String token) throws Exception;

	/**
	 * 修改手机号码
	 * 
	 * @author YJQ 2017年4月18日
	 * @param oldTelephone 原手机号
	 * @param oldVerificationCode 原手机号验证码
	 * @param password 密码
	 * @param telephone 手机号
	 * @param verificationCode 验证码
	 * @param token 用户token
	 * @return pageinfo
	 * @throws Exception 
	 *
	 */
    PageInfo modifyTelephone(String oldTelephone, String oldVerificationCode, String password, String telephone,
            String verificationCode, String token) throws Exception;

	/**
	 * 验证用户密码
	 * 
	 * @param uid 用户id
	 * @param oldPsw 旧密码
	 * @return true 通过  false 不通过
	 * @throws Exception 
	 */
	boolean validUserPassword(String uid, String oldPsw) throws Exception;

	/**
	 * 根据用户id查询用户账户信息
	 * 
	 * @param uid 用户id
	 * @return pageinfo
	 * @throws Exception 
	 */
	PageInfo queryUserAccountByUid(String uid) throws Exception;

	/**
	 * 查询地址详情
	 * 
	 * @author YJQ 2017年5月15日
	 * @param id 地址id
	 * @param key redis中uid的key
	 * @return pageinfo
	 * @throws Exception 
	 */
	PageInfo queryAddressDetail(Integer id, String key) throws Exception;

	/**
	 * 设置默认银行卡
	 * 
	 * @author YJQ 2017年5月17日
	 * @param id 银行卡id
	 * @param key redis中uid的key
	 * @return pageinfo
	 * @throws Exception 
	 */
	PageInfo setDefaultBinkCard(Integer id, String uid) throws Exception;

	/**
	 * 修改用户信息
	 * 
	 * @author YJQ 2017年5月20日
	 * @param usersProperties 用户信息
	 * @param key redis中uid的key
	 * @return pageinfo
	 * @throws Exception 
	 */
	PageInfo modifyUsersInfo(UsersProperties usersProperties, String key) throws Exception;

	/**
	 * 查询我推荐的人
	 * 
	 * @param key redis中uid的key
	 * @return pageinfo
	 * @throws Exception 
	 */
	PageInfo queryRecomUsersByUid(String key) throws Exception;

	/**
	 * 查询谁推荐的我
	 * 
	 * @param key redis中uid的key
	 * @return pageinfo
	 * @throws Exception 
	 */
	PageInfo queryMyrecommender(String key) throws Exception;
}
