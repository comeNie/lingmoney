package com.mrbt.lingmoney.mapper;

import com.mrbt.lingmoney.model.EnterpriseRecharge;
import com.mrbt.lingmoney.model.EnterpriseRechargeExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface EnterpriseRechargeMapper {
    /**
     *  根据指定的条件获取数据库记录数,enterprise_recharge
     *
     * @param example
     */
    int countByExample(EnterpriseRechargeExample example);

    /**
     *  根据指定的条件删除数据库符合条件的记录,enterprise_recharge
     *
     * @param example
     */
    int deleteByExample(EnterpriseRechargeExample example);

    /**
     *  根据主键删除数据库的记录,enterprise_recharge
     *
     * @param id
     */
    int deleteByPrimaryKey(String id);

    /**
     *  新写入数据库记录,enterprise_recharge
     *
     * @param record
     */
    int insert(EnterpriseRecharge record);

    /**
     *  动态字段,写入数据库记录,enterprise_recharge
     *
     * @param record
     */
    int insertSelective(EnterpriseRecharge record);

    /**
     *  根据指定的条件查询符合条件的数据库记录,enterprise_recharge
     *
     * @param example
     */
    List<EnterpriseRecharge> selectByExample(EnterpriseRechargeExample example);

    /**
     *  根据指定主键获取一条数据库记录,enterprise_recharge
     *
     * @param id
     */
    EnterpriseRecharge selectByPrimaryKey(String id);

    /**
     *  动态根据指定的条件来更新符合条件的数据库记录,enterprise_recharge
     *
     * @param record
     * @param example
     */
    int updateByExampleSelective(@Param("record") EnterpriseRecharge record, @Param("example") EnterpriseRechargeExample example);

    /**
     *  根据指定的条件来更新符合条件的数据库记录,enterprise_recharge
     *
     * @param record
     * @param example
     */
    int updateByExample(@Param("record") EnterpriseRecharge record, @Param("example") EnterpriseRechargeExample example);

    /**
     *  动态字段,根据主键来更新符合条件的数据库记录,enterprise_recharge
     *
     * @param record
     */
    int updateByPrimaryKeySelective(EnterpriseRecharge record);

    /**
     *  根据主键来更新符合条件的数据库记录,enterprise_recharge
     *
     * @param record
     */
    int updateByPrimaryKey(EnterpriseRecharge record);
}