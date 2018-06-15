package com.mrbt.lingmoney.mapper;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.mrbt.lingmoney.model.AccountFlow;
import com.mrbt.lingmoney.model.AccountFlowExample;
import com.mrbt.lingmoney.model.AccountFlowKey;

public interface AccountFlowMapper {
	/**
	 * 根据指定的条件获取数据库记录数,account_flow
	 *
	 * @param example
	 */
	long countByExample(AccountFlowExample example);

	/**
	 * 根据指定的条件删除数据库符合条件的记录,account_flow
	 *
	 * @param example
	 */
	int deleteByExample(AccountFlowExample example);

	/**
	 * 根据主键删除数据库的记录,account_flow
	 *
	 * @param key
	 */
	int deleteByPrimaryKey(AccountFlowKey key);

	/**
	 * 新写入数据库记录,account_flow
	 *
	 * @param record
	 */
	int insert(AccountFlow record);

	/**
	 * 动态字段,写入数据库记录,account_flow
	 *
	 * @param record
	 */
	int insertSelective(AccountFlow record);

	/**
	 * 根据指定的条件查询符合条件的数据库记录,account_flow
	 *
	 * @param example
	 */
	List<AccountFlow> selectByExample(AccountFlowExample example);

	/**
	 * 根据指定主键获取一条数据库记录,account_flow
	 *
	 * @param key
	 */
	AccountFlow selectByPrimaryKey(AccountFlowKey key);

	/**
	 * 动态根据指定的条件来更新符合条件的数据库记录,account_flow
	 *
	 * @param record
	 * @param example
	 */
	int updateByExampleSelective(@Param("record") AccountFlow record, @Param("example") AccountFlowExample example);

	/**
	 * 根据指定的条件来更新符合条件的数据库记录,account_flow
	 *
	 * @param record
	 *            record
	 * @param example
	 *            example
	 */
	int updateByExample(@Param("record") AccountFlow record, @Param("example") AccountFlowExample example);

	/**
	 * 动态字段,根据主键来更新符合条件的数据库记录,account_flow
	 *
	 * @param record
	 */
	int updateByPrimaryKeySelective(AccountFlow record);

	/**
	 * 根据主键来更新符合条件的数据库记录,account_flow
	 *
	 * @param record
	 */
	int updateByPrimaryKey(AccountFlow record);

    /**
	 * 随心取卖出后付息记录插入账户流水
	 * 
	 * @param uid
	 *            用户id
	 * @param tid
	 *            交易id
	 * @param interest
	 *            利息
	 * @return 数据返回
	 */
	int saveAccountFlowInterest(Map<String, Object> accountFlowParams);

	/**
	 * 查询用户是否有理财记录
	 * 
	 * @param map
	 * @return 数据返回
	 */
	int selectAccountFlowOfRechargeByUserId(Map<String, Object> map);

	/**
	 * 查询用户操作的冻结金额
	 * 
	 * @param map
	 * @return 数据返回
	 */
	BigDecimal selectByUidAndTypeAndStatus(Map<String, Object> map);

	/**
	 * 查询用户今天有理财记录
	 * 
	 * @param params
	 *            params
	 * @return 数据返回
	 */
	List<Map<String, Object>> findUserFinanceOfToday(Map<String, Object> params);

	/**
	 * 查询用户提现操作的冻结金额
	 * 
	 * @param map
	 * @return 数据返回
	 */
	BigDecimal getWithdrawalsFrozenAmount(Map<String, Object> map);

	/**
	 * 查询流水
	 * 
	 * @param number
	 *            number
	 * @return 数据返回
	 */
	AccountFlow findByNumber(String number);

	/**
	 * 更新账户流水
	 * 
	 * @param accountFlow
	 *            accountFlow
	 * @param id
	 *            id
	 * @return return
	 */
	int updateByAccountFlowId(@Param("record") AccountFlow record, @Param("id") Integer id);

    /**
     * 查询冻结金额明细
     * 
     * @author yiban
     * @date 2018年3月18日 下午2:47:06
     * @version 1.0
     * @param paramMap
     * @return
     *
     */
    List<Map<String, Object>> listFrozenMoneyDetail(Map<String, Object> paramMap);

    /**
     * 查询冻结金额明细总条数
     * 
     * @author yiban
     * @date 2018年3月18日 下午2:47:06
     * @version 1.0
     * @param paramMap
     * @return
     *
     */
    int countListFrozenMoneyDetail(Map<String, Object> paramMap);
}