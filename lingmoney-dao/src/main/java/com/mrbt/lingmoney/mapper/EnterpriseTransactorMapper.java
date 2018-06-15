package com.mrbt.lingmoney.mapper;

import com.mrbt.lingmoney.model.EnterpriseTransactor;
import com.mrbt.lingmoney.model.EnterpriseTransactorExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface EnterpriseTransactorMapper {
    /**
     *  根据指定的条件获取数据库记录数,enterprise_transactor
     *
     * @param example
     */
    int countByExample(EnterpriseTransactorExample example);

    /**
     *  根据指定的条件删除数据库符合条件的记录,enterprise_transactor
     *
     * @param example
     */
    int deleteByExample(EnterpriseTransactorExample example);

    /**
     *  根据主键删除数据库的记录,enterprise_transactor
     *
     * @param id
     */
    int deleteByPrimaryKey(String id);

    /**
     *  新写入数据库记录,enterprise_transactor
     *
     * @param record
     */
    int insert(EnterpriseTransactor record);

    /**
     *  动态字段,写入数据库记录,enterprise_transactor
     *
     * @param record
     */
    int insertSelective(EnterpriseTransactor record);

    /**
     *  根据指定的条件查询符合条件的数据库记录,enterprise_transactor
     *
     * @param example
     */
    List<EnterpriseTransactor> selectByExample(EnterpriseTransactorExample example);

    /**
     *  根据指定主键获取一条数据库记录,enterprise_transactor
     *
     * @param id
     */
    EnterpriseTransactor selectByPrimaryKey(String id);

    /**
     *  动态根据指定的条件来更新符合条件的数据库记录,enterprise_transactor
     *
     * @param record
     * @param example
     */
    int updateByExampleSelective(@Param("record") EnterpriseTransactor record, @Param("example") EnterpriseTransactorExample example);

    /**
     *  根据指定的条件来更新符合条件的数据库记录,enterprise_transactor
     *
     * @param record
     * @param example
     */
    int updateByExample(@Param("record") EnterpriseTransactor record, @Param("example") EnterpriseTransactorExample example);

    /**
     *  动态字段,根据主键来更新符合条件的数据库记录,enterprise_transactor
     *
     * @param record
     */
    int updateByPrimaryKeySelective(EnterpriseTransactor record);

    /**
     *  根据主键来更新符合条件的数据库记录,enterprise_transactor
     *
     * @param record
     */
    int updateByPrimaryKey(EnterpriseTransactor record);
}