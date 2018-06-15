package com.mrbt.lingmoney.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.mrbt.lingmoney.model.UsersProperties;
import com.mrbt.lingmoney.model.UsersPropertiesExample;

public interface UsersPropertiesMapper {
	/**
	 * 根据指定的条件获取数据库记录数,users_properties
	 *
	 * @param example
	 */
	long countByExample(UsersPropertiesExample example);

	/**
	 * 根据指定的条件删除数据库符合条件的记录,users_properties
	 *
	 * @param example
	 */
	int deleteByExample(UsersPropertiesExample example);

	/**
	 * 根据主键删除数据库的记录,users_properties
	 *
	 * @param id
	 */
	int deleteByPrimaryKey(Integer id);

	/**
	 * 新写入数据库记录,users_properties
	 *
	 * @param record
	 */
	int insert(UsersProperties record);

	/**
	 * 动态字段,写入数据库记录,users_properties
	 *
	 * @param record
	 */
	int insertSelective(UsersProperties record);

	/**
	 * 根据指定的条件查询符合条件的数据库记录,users_properties
	 *
	 * @param example
	 */
	List<UsersProperties> selectByExample(UsersPropertiesExample example);

	/**
	 * 根据指定主键获取一条数据库记录,users_properties
	 *
	 * @param id
	 */
	UsersProperties selectByPrimaryKey(Integer id);

	/**
	 * 动态根据指定的条件来更新符合条件的数据库记录,users_properties
	 *
	 * @param record
	 * @param example
	 */
	int updateByExampleSelective(@Param("record") UsersProperties record,
			@Param("example") UsersPropertiesExample example);

	/**
	 * 根据指定的条件来更新符合条件的数据库记录,users_properties
	 *
	 * @param record
	 * @param example
	 */
	int updateByExample(@Param("record") UsersProperties record, @Param("example") UsersPropertiesExample example);

	/**
	 * 动态字段,根据主键来更新符合条件的数据库记录,users_properties
	 *
	 * @param record
	 */
	int updateByPrimaryKeySelective(UsersProperties record);

	/**
	 * 根据主键来更新符合条件的数据库记录,users_properties
	 *
	 * @param record
	 */
	int updateByPrimaryKey(UsersProperties record);

	/**
	 * 查询某用户属性信息
	 * 
	 * @param uId
	 * @param status
	 * @return
	 */
	UsersProperties selectByuId(String uId);

	/**
	 * 查询 用户信息，我的领地首页展示
	 * 
	 * @param id
	 * @return nickname 用户昵称 picture 用户头像 lingbao 用户领宝
	 */
	Map<String, Object> queryUserInfoMyPlace(String id);

	/**
	 * 根据uid查询推荐用户信息
	 * 
	 * @param uid
	 * @return
	 */
	List<Map<String, Object>> queryRecomUsersByUid(String uid);

	/**
	 * 查询谁推荐的我
	 * 
	 * @param uid
	 * @return
	 */
	Map<String, Object> queryMyrecommender(String uid);

	/**
	 * 根据用户ID查询拉新活动记录信息
	 * 
	 * @Description
	 * @param uid
	 *            用户ID
	 * @param startDate
	 *            活动开始时间
	 * @param endDate
	 *            活动结束时间
	 * @return buyMoney, uId, nickName, referralCode
	 */
	List<Map<String, Object>> queryGetNewInfoByUid(Map<String, Object> params);

	/**
	 * 查询用户根据手机
	 * 
	 * @param telephone
	 * @return
	 */
	UsersProperties selectByTelephone(String telephone);

	/**
	 * 查询用户根据map
	 * 
	 * @param map
	 * @return
	 */
	UsersProperties selectByMap(Map<String, Object> map);

	/**
	 * 查询推荐的用户
	 * 
	 * @param uId
	 * @return
	 */
	List<UsersProperties> selectRecommendUser(String uId);

	/**
	 * 查询推荐的用户个数
	 * 
	 * @param uId
	 * @return
	 */
	int selectRecommendCount(String uId);

	/**
	 * 定时修改用户等级
	 */
	void updateUsersPropertiesLevel();

	/**
	 * 查询是否购买过产品
	 * 
	 * @param uId
	 * @return
	 */
	int selectTradingCountByuId(String uId);
	
	/**
	 * 根据用户id判断是否为员工
	 * 
	 * @param uId
	 * @return
	 */
	int queryIsEmployeeUsers(String uId);
	
	/**
	 * 根据用户id查找员工id和自身id
	 * 
	 * @param uId
	 * @return
	 */
	public List<String> queryEmployeeUsersId(String uId);

	List<String> findAll();

	/**
	 * 查询用户信息
	 * 
	 * @param uid 参数
	 * @return 用户信息
	 */
	Map<String, Object> findUserInfoByParams(Map<String, Object> params);

	/**
	 * 根据渠道号查询用户信息
	 * @param map 参数
	 * @return 数据返回
	 */
	List<Map<String, Object>> findUsersByChannel(Map<String, Object> map);

	/**
	 * 根据渠道号和购买某个产品查询用户信息
	 * @param map 参数
	 * @return 数据返回
	 */
	List<Map<String, Object>> findUsersByChannelAndBuyProduct(Map<String, Object> map);

	/**
	 * 根据渠道号和兑付某个产品查询用户信息
	 * @param map 参数
	 * @return 数据返回
	 */
	List<Map<String, Object>> findUsersByChannelAndSellProduct(Map<String, Object> map);
	
	/**
	 * 根据渠道号查询用户信息总数
	 * @param map 参数
	 * @return 数据返回
	 */
	Integer findUsersByChannelCount(Map<String, Object> map);
	
	/**
	 * 根据渠道号和购买某个产品查询用户信息总数
	 * @param map 参数
	 * @return 数据返回
	 */
	Integer findUsersByChannelAndBuyProductCount(Map<String, Object> map);
	
	/**
	 * 根据渠道号和兑付某个产品查询用户信息总数
	 * @param map 参数
	 * @return 数据返回
	 */
	Integer findUsersByChannelAndSellProductCount(Map<String, Object> map);
}