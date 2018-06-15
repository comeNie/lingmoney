package com.mrbt.lingmoney.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.mrbt.lingmoney.model.UsersBank;
import com.mrbt.lingmoney.model.UsersBankExample;
import com.mrbt.lingmoney.model.trading.BankInfo;
import com.mrbt.lingmoney.utils.PageInfo;

public interface UsersBankMapper {
	/**
	 * 根据指定的条件获取数据库记录数,users_bank
	 *
	 * @param example
	 */
	long countByExample(UsersBankExample example);

	/**
	 * 根据指定的条件删除数据库符合条件的记录,users_bank
	 *
	 * @param example
	 */
	int deleteByExample(UsersBankExample example);

	/**
	 * 根据主键删除数据库的记录,users_bank
	 *
	 * @param id
	 */
	int deleteByPrimaryKey(Integer id);

	/**
	 * 新写入数据库记录,users_bank
	 *
	 * @param record
	 */
	int insert(UsersBank record);

	/**
	 * 动态字段,写入数据库记录,users_bank
	 *
	 * @param record
	 */
	int insertSelective(UsersBank record);

	/**
	 * 根据指定的条件查询符合条件的数据库记录,users_bank
	 *
	 * @param example
	 */
	List<UsersBank> selectByExample(UsersBankExample example);

	/**
	 * 根据指定主键获取一条数据库记录,users_bank
	 *
	 * @param id
	 */
	UsersBank selectByPrimaryKey(Integer id);

	/**
	 * 动态根据指定的条件来更新符合条件的数据库记录,users_bank
	 *
	 * @param record
	 * @param example
	 */
	int updateByExampleSelective(@Param("record") UsersBank record, @Param("example") UsersBankExample example);

	/**
	 * 根据指定的条件来更新符合条件的数据库记录,users_bank
	 *
	 * @param record
	 * @param example
	 */
	int updateByExample(@Param("record") UsersBank record, @Param("example") UsersBankExample example);

	/**
	 * 动态字段,根据主键来更新符合条件的数据库记录,users_bank
	 *
	 * @param record
	 */
	int updateByPrimaryKeySelective(UsersBank record);

	/**
	 * 根据主键来更新符合条件的数据库记录,users_bank
	 *
	 * @param record
	 */
	int updateByPrimaryKey(UsersBank record);

	UsersBank findByUserBankId(int parseInt);

	/**
	 * 根据用户ID,状态查询
	 * 
	 * @sort 是否默认倒序,绑定时间倒序
	 * @param uid
	 * @param status
	 * @return BankInfo（银行信息《交易帮助类》）
	 */
	List<BankInfo> selectByUIdAndStatus(String uid, String status);

	/**
	 * 查询用户银行信息（带支付限额等）
	 * 
	 * @param uid
	 * @return
	 */
	List<Map<String, Object>> queryUserBankPurchaseInfo(String uid);

	/**
	 * 通过条件查询用户绑卡信息
	 * 
	 * @param pageInfo
	 * @return
	 */
	List<UsersBank> queryByCondition(PageInfo pageInfo);

	/**
	 * 通过条件查询总记录数
	 * 
	 * @param pageInfo
	 * @return
	 */
	int queryCountByCondition(PageInfo pageInfo);

	/**
	 * 查询我的银行卡展示数据
	 * @param uid
	 * @return
	 */
	List<Map<String, Object>> listJDBankCardView(String uid);

	/**
	 * 根据用户ID,状态查询
	 * 
	 * @sort 是否默认倒序,绑定时间倒序
	 * @param uid
	 * @param status
	 * @return BankInfo（银行信息《交易帮助类》）
	 */
	List<UsersBank> selectByUIdAndStatus2(String uId, String status);

	/**
	 * 查询
	 * 
	 * @param uId
	 *            uId
	 * @param status
	 *            status
	 * @param isdefault
	 *            isdefault
	 * @return return
	 */
	List<UsersBank> selectByUIdAndDefault(String uId, String status, String isdefault);

	/**
	 * 查询
	 * 
	 * @param uid
	 *            uid
	 * @return return
	 */
	List<UsersBank> selectByUId(String uId);
}