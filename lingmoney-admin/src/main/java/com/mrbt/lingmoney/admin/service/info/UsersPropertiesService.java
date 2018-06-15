package com.mrbt.lingmoney.admin.service.info;

import java.util.List;
import java.util.Map;

import com.mrbt.lingmoney.model.UsersProperties;
import com.mrbt.lingmoney.utils.PageInfo;

/**
 * 用户属性
 * 
 * @author lihq
 * @date 2017年5月12日 上午10:47:48
 * @description
 * @version 1.0
 * @since 2017-04-12
 */
public interface UsersPropertiesService {
	/**
	 * 保存实体bean
	 * 
	 * @param vo
	 *            UsersProperties
	 */
	void save(UsersProperties vo);

	/**
	 * 更新实体bean
	 * 
	 * @param vo
	 *            UsersProperties
	 */
	void update(UsersProperties vo);

	/**
	 * 查询用户根据手机
	 * 
	 * @param telephone
	 *            电话
	 * @return 数据返回
	 */
	UsersProperties selectByTelephone(String telephone);

	/**
	 * 查询用户根据map
	 * 
	 * @param map
	 *            map
	 * @return 数据返回
	 */
	UsersProperties selectByMap(Map<String, Object> map);

	/**
	 * 查询某用户属性信息
	 * 
	 * @param uId
	 *            UID
	 * @return 数据返回
	 */
	UsersProperties findByUId(String uId);

	/**
	 * 更新用户等级
	 *
	 * @Description 根据理财金额从用户等级表中查询所属区段
	 */
	void updateUsersPropertiesLevel();

	/**
	 * 查询列表
	 * 
	 * @param vo
	 *            UsersProperties
	 * @param symbol
	 *            symbol
	 * @param pageInfo
	 *            PageInfo
	 */
	void listGrid(UsersProperties vo, Integer symbol, PageInfo pageInfo);

	/**
	 * 查询用户根据推荐码
	 * 
	 * @param referralCode
	 *            referralCode
	 * @return 数据返回
	 */
	UsersProperties selectByReferralCode(String referralCode);

	/**
	 * 查询推荐用户
	 * 
	 * @param uId
	 *            uid
	 * @return 数据返回
	 */
	List<UsersProperties> getReferralUsers(String uId);

	/**
	 * 根据主键查询
	 * 
	 * @param id
	 *            数据id
	 * @return 数据返回
	 */
	UsersProperties findByPk(int id);

	/**
	 * 查询是否购买过产品
	 * 
	 * @param uId
	 *            uid
	 * @return 数据返回
	 */
	int selectTradingCountByuId(String uId);
}
