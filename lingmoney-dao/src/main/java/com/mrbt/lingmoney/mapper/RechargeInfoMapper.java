package com.mrbt.lingmoney.mapper;

import com.mrbt.lingmoney.model.RechargeInfo;
import com.mrbt.lingmoney.model.RechargeInfoExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface RechargeInfoMapper {
    /**
     *  根据指定的条件获取数据库记录数,recharge_info
     *
     * @param example
     */
    long countByExample(RechargeInfoExample example);

    /**
     *  根据指定的条件删除数据库符合条件的记录,recharge_info
     *
     * @param example
     */
    int deleteByExample(RechargeInfoExample example);

    /**
     *  根据主键删除数据库的记录,recharge_info
     *
     * @param id
     */
    int deleteByPrimaryKey(String id);

    /**
     *  新写入数据库记录,recharge_info
     *
     * @param record
     */
    int insert(RechargeInfo record);

    /**
     *  动态字段,写入数据库记录,recharge_info
     *
     * @param record
     */
    int insertSelective(RechargeInfo record);

    /**
     *  根据指定的条件查询符合条件的数据库记录,recharge_info
     *
     * @param example
     */
    List<RechargeInfo> selectByExample(RechargeInfoExample example);

    /**
     *  根据指定主键获取一条数据库记录,recharge_info
     *
     * @param id
     */
    RechargeInfo selectByPrimaryKey(String id);

    /**
     *  动态根据指定的条件来更新符合条件的数据库记录,recharge_info
     *
     * @param record
     * @param example
     */
    int updateByExampleSelective(@Param("record") RechargeInfo record, @Param("example") RechargeInfoExample example);

    /**
     *  根据指定的条件来更新符合条件的数据库记录,recharge_info
     *
     * @param record
     * @param example
     */
    int updateByExample(@Param("record") RechargeInfo record, @Param("example") RechargeInfoExample example);

    /**
     *  动态字段,根据主键来更新符合条件的数据库记录,recharge_info
     *
     * @param record
     */
    int updateByPrimaryKeySelective(RechargeInfo record);

    /**
     *  根据主键来更新符合条件的数据库记录,recharge_info
     *
     * @param record
     */
    int updateByPrimaryKey(RechargeInfo record);
}