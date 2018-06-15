package com.mrbt.lingmoney.mapper;

import com.mrbt.lingmoney.model.WalletSellPay;
import com.mrbt.lingmoney.model.WalletSellPayExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface WalletSellPayMapper {
    /**
     *  根据指定的条件获取数据库记录数,wallet_sell_pay
     *
     * @param example
     */
    long countByExample(WalletSellPayExample example);

    /**
     *  根据指定的条件删除数据库符合条件的记录,wallet_sell_pay
     *
     * @param example
     */
    int deleteByExample(WalletSellPayExample example);

    /**
     *  根据主键删除数据库的记录,wallet_sell_pay
     *
     * @param id
     */
    int deleteByPrimaryKey(Integer id);

    /**
     *  新写入数据库记录,wallet_sell_pay
     *
     * @param record
     */
    int insert(WalletSellPay record);

    /**
     *  动态字段,写入数据库记录,wallet_sell_pay
     *
     * @param record
     */
    int insertSelective(WalletSellPay record);

    /**
     *  根据指定的条件查询符合条件的数据库记录,wallet_sell_pay
     *
     * @param example
     */
    List<WalletSellPay> selectByExample(WalletSellPayExample example);

    /**
     *  根据指定主键获取一条数据库记录,wallet_sell_pay
     *
     * @param id
     */
    WalletSellPay selectByPrimaryKey(Integer id);

    /**
     *  动态根据指定的条件来更新符合条件的数据库记录,wallet_sell_pay
     *
     * @param record
     * @param example
     */
    int updateByExampleSelective(@Param("record") WalletSellPay record, @Param("example") WalletSellPayExample example);

    /**
     *  根据指定的条件来更新符合条件的数据库记录,wallet_sell_pay
     *
     * @param record
     * @param example
     */
    int updateByExample(@Param("record") WalletSellPay record, @Param("example") WalletSellPayExample example);

    /**
     *  动态字段,根据主键来更新符合条件的数据库记录,wallet_sell_pay
     *
     * @param record
     */
    int updateByPrimaryKeySelective(WalletSellPay record);

    /**
     *  根据主键来更新符合条件的数据库记录,wallet_sell_pay
     *
     * @param record
     */
    int updateByPrimaryKey(WalletSellPay record);
}