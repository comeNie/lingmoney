package com.mrbt.lingmoney.mapper;

import com.mrbt.lingmoney.model.PayEnterprise;
import com.mrbt.lingmoney.model.PayEnterpriseExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface PayEnterpriseMapper {
    /**
     *  根据指定的条件获取数据库记录数,pay_enterprise
     *
     * @param example
     */
    long countByExample(PayEnterpriseExample example);

    /**
     *  根据指定的条件删除数据库符合条件的记录,pay_enterprise
     *
     * @param example
     */
    int deleteByExample(PayEnterpriseExample example);

    /**
     *  根据主键删除数据库的记录,pay_enterprise
     *
     * @param platformuserno
     */
    int deleteByPrimaryKey(Integer platformuserno);

    /**
     *  新写入数据库记录,pay_enterprise
     *
     * @param record
     */
    int insert(PayEnterprise record);

    /**
     *  动态字段,写入数据库记录,pay_enterprise
     *
     * @param record
     */
    int insertSelective(PayEnterprise record);

    /**
     *  根据指定的条件查询符合条件的数据库记录,pay_enterprise
     *
     * @param example
     */
    List<PayEnterprise> selectByExample(PayEnterpriseExample example);

    /**
     *  根据指定主键获取一条数据库记录,pay_enterprise
     *
     * @param platformuserno
     */
    PayEnterprise selectByPrimaryKey(Integer platformuserno);

    /**
     *  动态根据指定的条件来更新符合条件的数据库记录,pay_enterprise
     *
     * @param record
     * @param example
     */
    int updateByExampleSelective(@Param("record") PayEnterprise record, @Param("example") PayEnterpriseExample example);

    /**
     *  根据指定的条件来更新符合条件的数据库记录,pay_enterprise
     *
     * @param record
     * @param example
     */
    int updateByExample(@Param("record") PayEnterprise record, @Param("example") PayEnterpriseExample example);

    /**
     *  动态字段,根据主键来更新符合条件的数据库记录,pay_enterprise
     *
     * @param record
     */
    int updateByPrimaryKeySelective(PayEnterprise record);

    /**
     *  根据主键来更新符合条件的数据库记录,pay_enterprise
     *
     * @param record
     */
    int updateByPrimaryKey(PayEnterprise record);
}