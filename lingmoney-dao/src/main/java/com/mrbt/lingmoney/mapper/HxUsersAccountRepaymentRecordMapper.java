package com.mrbt.lingmoney.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.mrbt.lingmoney.model.HxUsersAccountRepaymentRecord;
import com.mrbt.lingmoney.model.HxUsersAccountRepaymentRecordExample;

public interface HxUsersAccountRepaymentRecordMapper {
    /**
     *  根据指定的条件获取数据库记录数,hx_users_account_repayment_record
     *
     * @param example
     */
    int countByExample(HxUsersAccountRepaymentRecordExample example);

    /**
     *  根据指定的条件删除数据库符合条件的记录,hx_users_account_repayment_record
     *
     * @param example
     */
    int deleteByExample(HxUsersAccountRepaymentRecordExample example);

    /**
     *  根据主键删除数据库的记录,hx_users_account_repayment_record
     *
     * @param id
     */
    int deleteByPrimaryKey(String id);

    /**
     *  新写入数据库记录,hx_users_account_repayment_record
     *
     * @param record
     */
    int insert(HxUsersAccountRepaymentRecord record);

    /**
     *  动态字段,写入数据库记录,hx_users_account_repayment_record
     *
     * @param record
     */
    int insertSelective(HxUsersAccountRepaymentRecord record);

    /**
     *  根据指定的条件查询符合条件的数据库记录,hx_users_account_repayment_record
     *
     * @param example
     */
    List<HxUsersAccountRepaymentRecord> selectByExample(HxUsersAccountRepaymentRecordExample example);

    /**
     *  根据指定主键获取一条数据库记录,hx_users_account_repayment_record
     *
     * @param id
     */
    HxUsersAccountRepaymentRecord selectByPrimaryKey(String id);

    /**
     *  动态根据指定的条件来更新符合条件的数据库记录,hx_users_account_repayment_record
     *
     * @param record
     * @param example
     */
    int updateByExampleSelective(@Param("record") HxUsersAccountRepaymentRecord record, @Param("example") HxUsersAccountRepaymentRecordExample example);

    /**
     *  根据指定的条件来更新符合条件的数据库记录,hx_users_account_repayment_record
     *
     * @param record
     * @param example
     */
    int updateByExample(@Param("record") HxUsersAccountRepaymentRecord record, @Param("example") HxUsersAccountRepaymentRecordExample example);

    /**
     *  动态字段,根据主键来更新符合条件的数据库记录,hx_users_account_repayment_record
     *
     * @param record
     */
    int updateByPrimaryKeySelective(HxUsersAccountRepaymentRecord record);

    /**
     *  根据主键来更新符合条件的数据库记录,hx_users_account_repayment_record
     *
     * @param record
     */
    int updateByPrimaryKey(HxUsersAccountRepaymentRecord record);

	/**
	 * 按uid分组，查询该回款总金额，交易状态为 已流标/已撤标
	 * 
	 * @return money 金额 uid 用户id
	 */
	List<Map<String, Object>> selectSumMoneyGroupByUser();

	/**
	 * 回款成功 更新状态
	 * 
	 * @return
	 */
	int updateAfterRepaymentSuccess(String uid);

	/**
	 * 根据条件查询
	 * 
	 * @author syb
	 * @date 2017年9月8日 下午2:45:44
	 * @version 1.0
	 * @param params
	 * @return
	 *
	 */
	List<Map<String, Object>> queryWithSelfCondition(Map<String, Object> params);

	int countWithSelfCondition(Map<String, Object> params);
}