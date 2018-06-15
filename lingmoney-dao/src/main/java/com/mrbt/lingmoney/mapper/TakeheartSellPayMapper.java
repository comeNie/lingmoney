package com.mrbt.lingmoney.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.mrbt.lingmoney.model.TakeheartSellPay;
import com.mrbt.lingmoney.model.TakeheartSellPayExample;

public interface TakeheartSellPayMapper {
    /**
     *  根据指定的条件获取数据库记录数,takeheart_sell_pay
     *
     * @param example
     */
    long countByExample(TakeheartSellPayExample example);

    /**
     *  根据指定的条件删除数据库符合条件的记录,takeheart_sell_pay
     *
     * @param example
     */
    int deleteByExample(TakeheartSellPayExample example);

    /**
     *  根据主键删除数据库的记录,takeheart_sell_pay
     *
     * @param id
     */
    int deleteByPrimaryKey(Integer id);

    /**
     *  新写入数据库记录,takeheart_sell_pay
     *
     * @param record
     */
    int insert(TakeheartSellPay record);

    /**
     *  动态字段,写入数据库记录,takeheart_sell_pay
     *
     * @param record
     */
    int insertSelective(TakeheartSellPay record);

    /**
     *  根据指定的条件查询符合条件的数据库记录,takeheart_sell_pay
     *
     * @param example
     */
    List<TakeheartSellPay> selectByExample(TakeheartSellPayExample example);

    /**
     *  根据指定主键获取一条数据库记录,takeheart_sell_pay
     *
     * @param id
     */
    TakeheartSellPay selectByPrimaryKey(Integer id);

    /**
     *  动态根据指定的条件来更新符合条件的数据库记录,takeheart_sell_pay
     *
     * @param record
     * @param example
     */
    int updateByExampleSelective(@Param("record") TakeheartSellPay record, @Param("example") TakeheartSellPayExample example);

    /**
     *  根据指定的条件来更新符合条件的数据库记录,takeheart_sell_pay
     *
     * @param record
     * @param example
     */
    int updateByExample(@Param("record") TakeheartSellPay record, @Param("example") TakeheartSellPayExample example);

    /**
     *  动态字段,根据主键来更新符合条件的数据库记录,takeheart_sell_pay
     *
     * @param record
     */
    int updateByPrimaryKeySelective(TakeheartSellPay record);

    /**
     *  根据主键来更新符合条件的数据库记录,takeheart_sell_pay
     *
     * @param record
     */
    int updateByPrimaryKey(TakeheartSellPay record);

    /**
     * 随心取卖出后保存记录
     * @param sellPayParams
     */
	int saveAfterSell(Map<String, Object> sellPayParams);
}