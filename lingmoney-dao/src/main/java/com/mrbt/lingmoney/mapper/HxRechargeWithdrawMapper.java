package com.mrbt.lingmoney.mapper;

import com.mrbt.lingmoney.model.HxRechargeWithdraw;
import com.mrbt.lingmoney.model.HxRechargeWithdrawExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface HxRechargeWithdrawMapper {
    /**
     *  根据指定的条件获取数据库记录数,hx_recharge_withdraw
     * @param example
     */
    long countByExample(HxRechargeWithdrawExample example);

    /**
     *  根据指定的条件删除数据库符合条件的记录,hx_recharge_withdraw
     * @param example
     */
    int deleteByExample(HxRechargeWithdrawExample example);

    /**
     *  根据主键删除数据库的记录,hx_recharge_withdraw
     * @param id
     */
    int deleteByPrimaryKey(String id);

    /**
     *  新写入数据库记录,hx_recharge_withdraw
     * @param record
     */
    int insert(HxRechargeWithdraw record);

    /**
     *  动态字段,写入数据库记录,hx_recharge_withdraw
     * @param record
     */
    int insertSelective(HxRechargeWithdraw record);

    /**
     *  根据指定的条件查询符合条件的数据库记录,hx_recharge_withdraw
     * @param example
     */
    List<HxRechargeWithdraw> selectByExample(HxRechargeWithdrawExample example);

    /**
     *  根据指定主键获取一条数据库记录,hx_recharge_withdraw
     * @param id
     */
    HxRechargeWithdraw selectByPrimaryKey(String id);

    /**
     *  动态根据指定的条件来更新符合条件的数据库记录,hx_recharge_withdraw
     * @param record
     * @param example
     */
    int updateByExampleSelective(@Param("record") HxRechargeWithdraw record, @Param("example") HxRechargeWithdrawExample example);

    /**
     *  根据指定的条件来更新符合条件的数据库记录,hx_recharge_withdraw
     * @param record
     * @param example
     */
    int updateByExample(@Param("record") HxRechargeWithdraw record, @Param("example") HxRechargeWithdrawExample example);

    /**
     *  动态字段,根据主键来更新符合条件的数据库记录,hx_recharge_withdraw
     * @param record
     */
    int updateByPrimaryKeySelective(HxRechargeWithdraw record);

    /**
     *  根据主键来更新符合条件的数据库记录,hx_recharge_withdraw
     * @param record
     */
    int updateByPrimaryKey(HxRechargeWithdraw record);
}