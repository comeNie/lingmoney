package com.mrbt.lingmoney.mapper;

import com.mrbt.lingmoney.model.UserReconciliation;
import com.mrbt.lingmoney.model.UserReconciliationExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface UserReconciliationMapper {
    /**
     *  根据指定的条件获取数据库记录数,user_reconciliation
     *
     * @param example
     */
    long countByExample(UserReconciliationExample example);

    /**
     *  根据指定的条件删除数据库符合条件的记录,user_reconciliation
     *
     * @param example
     */
    int deleteByExample(UserReconciliationExample example);

    /**
     *  根据主键删除数据库的记录,user_reconciliation
     *
     * @param id
     */
    int deleteByPrimaryKey(Integer id);

    /**
     *  新写入数据库记录,user_reconciliation
     *
     * @param record
     */
    int insert(UserReconciliation record);

    /**
     *  动态字段,写入数据库记录,user_reconciliation
     *
     * @param record
     */
    int insertSelective(UserReconciliation record);

    /**
     *  根据指定的条件查询符合条件的数据库记录,user_reconciliation
     *
     * @param example
     */
    List<UserReconciliation> selectByExample(UserReconciliationExample example);

    /**
     *  根据指定主键获取一条数据库记录,user_reconciliation
     *
     * @param id
     */
    UserReconciliation selectByPrimaryKey(Integer id);

    /**
     *  动态根据指定的条件来更新符合条件的数据库记录,user_reconciliation
     *
     * @param record
     * @param example
     */
    int updateByExampleSelective(@Param("record") UserReconciliation record, @Param("example") UserReconciliationExample example);

    /**
     *  根据指定的条件来更新符合条件的数据库记录,user_reconciliation
     *
     * @param record
     * @param example
     */
    int updateByExample(@Param("record") UserReconciliation record, @Param("example") UserReconciliationExample example);

    /**
     *  动态字段,根据主键来更新符合条件的数据库记录,user_reconciliation
     *
     * @param record
     */
    int updateByPrimaryKeySelective(UserReconciliation record);

    /**
     *  根据主键来更新符合条件的数据库记录,user_reconciliation
     *
     * @param record
     */
    int updateByPrimaryKey(UserReconciliation record);
}