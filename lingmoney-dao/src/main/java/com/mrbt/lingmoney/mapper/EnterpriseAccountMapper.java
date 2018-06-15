package com.mrbt.lingmoney.mapper;

import com.mrbt.lingmoney.model.EnterpriseAccount;
import com.mrbt.lingmoney.model.EnterpriseAccountExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface EnterpriseAccountMapper {
    /**
     *  根据指定的条件获取数据库记录数,enterprise_account
     *
     * @param example
     */
    int countByExample(EnterpriseAccountExample example);

    /**
     *  根据指定的条件删除数据库符合条件的记录,enterprise_account
     *
     * @param example
     */
    int deleteByExample(EnterpriseAccountExample example);

    /**
     *  根据主键删除数据库的记录,enterprise_account
     *
     * @param id
     */
    int deleteByPrimaryKey(String id);

    /**
     *  新写入数据库记录,enterprise_account
     *
     * @param record
     */
    int insert(EnterpriseAccount record);

    /**
     *  动态字段,写入数据库记录,enterprise_account
     *
     * @param record
     */
    int insertSelective(EnterpriseAccount record);

    /**
     *  根据指定的条件查询符合条件的数据库记录,enterprise_account
     *
     * @param example
     */
    List<EnterpriseAccount> selectByExample(EnterpriseAccountExample example);

    /**
     *  根据指定主键获取一条数据库记录,enterprise_account
     *
     * @param id
     */
    EnterpriseAccount selectByPrimaryKey(String id);

    /**
     *  动态根据指定的条件来更新符合条件的数据库记录,enterprise_account
     *
     * @param record
     * @param example
     */
    int updateByExampleSelective(@Param("record") EnterpriseAccount record, @Param("example") EnterpriseAccountExample example);

    /**
     *  根据指定的条件来更新符合条件的数据库记录,enterprise_account
     *
     * @param record
     * @param example
     */
    int updateByExample(@Param("record") EnterpriseAccount record, @Param("example") EnterpriseAccountExample example);

    /**
     *  动态字段,根据主键来更新符合条件的数据库记录,enterprise_account
     *
     * @param record
     */
    int updateByPrimaryKeySelective(EnterpriseAccount record);

    /**
     *  根据主键来更新符合条件的数据库记录,enterprise_account
     *
     * @param record
     */
    int updateByPrimaryKey(EnterpriseAccount record);
}