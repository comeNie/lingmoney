package com.mrbt.lingmoney.mapper;

import com.mrbt.lingmoney.model.RechargeLimit;
import com.mrbt.lingmoney.model.RechargeLimitExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface RechargeLimitMapper {
    /**
     *  根据指定的条件获取数据库记录数,recharge_limit
     *
     * @param example
     */
    long countByExample(RechargeLimitExample example);

    /**
     *  根据指定的条件删除数据库符合条件的记录,recharge_limit
     *
     * @param example
     */
    int deleteByExample(RechargeLimitExample example);

    /**
     *  根据主键删除数据库的记录,recharge_limit
     *
     * @param id
     */
    int deleteByPrimaryKey(Integer id);

    /**
     *  新写入数据库记录,recharge_limit
     *
     * @param record
     */
    int insert(RechargeLimit record);

    /**
     *  动态字段,写入数据库记录,recharge_limit
     *
     * @param record
     */
    int insertSelective(RechargeLimit record);

    /**
     *  根据指定的条件查询符合条件的数据库记录,recharge_limit
     *
     * @param example
     */
    List<RechargeLimit> selectByExample(RechargeLimitExample example);

    /**
     *  根据指定主键获取一条数据库记录,recharge_limit
     *
     * @param id
     */
    RechargeLimit selectByPrimaryKey(Integer id);

    /**
     *  动态根据指定的条件来更新符合条件的数据库记录,recharge_limit
     *
     * @param record
     * @param example
     */
    int updateByExampleSelective(@Param("record") RechargeLimit record, @Param("example") RechargeLimitExample example);

    /**
     *  根据指定的条件来更新符合条件的数据库记录,recharge_limit
     *
     * @param record
     * @param example
     */
    int updateByExample(@Param("record") RechargeLimit record, @Param("example") RechargeLimitExample example);

    /**
     *  动态字段,根据主键来更新符合条件的数据库记录,recharge_limit
     *
     * @param record
     */
    int updateByPrimaryKeySelective(RechargeLimit record);

    /**
     *  根据主键来更新符合条件的数据库记录,recharge_limit
     *
     * @param record
     */
    int updateByPrimaryKey(RechargeLimit record);
}