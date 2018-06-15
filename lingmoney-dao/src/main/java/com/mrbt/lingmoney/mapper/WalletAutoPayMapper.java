package com.mrbt.lingmoney.mapper;

import com.mrbt.lingmoney.model.WalletAutoPay;
import com.mrbt.lingmoney.model.WalletAutoPayExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface WalletAutoPayMapper {
    /**
     *  根据指定的条件获取数据库记录数,wallet_auto_pay
     *
     * @param example
     */
    long countByExample(WalletAutoPayExample example);

    /**
     *  根据指定的条件删除数据库符合条件的记录,wallet_auto_pay
     *
     * @param example
     */
    int deleteByExample(WalletAutoPayExample example);

    /**
     *  根据主键删除数据库的记录,wallet_auto_pay
     *
     * @param id
     */
    int deleteByPrimaryKey(Integer id);

    /**
     *  新写入数据库记录,wallet_auto_pay
     *
     * @param record
     */
    int insert(WalletAutoPay record);

    /**
     *  动态字段,写入数据库记录,wallet_auto_pay
     *
     * @param record
     */
    int insertSelective(WalletAutoPay record);

    /**
     *  根据指定的条件查询符合条件的数据库记录,wallet_auto_pay
     *
     * @param example
     */
    List<WalletAutoPay> selectByExample(WalletAutoPayExample example);

    /**
     *  根据指定主键获取一条数据库记录,wallet_auto_pay
     *
     * @param id
     */
    WalletAutoPay selectByPrimaryKey(Integer id);

    /**
     *  动态根据指定的条件来更新符合条件的数据库记录,wallet_auto_pay
     *
     * @param record
     * @param example
     */
    int updateByExampleSelective(@Param("record") WalletAutoPay record, @Param("example") WalletAutoPayExample example);

    /**
     *  根据指定的条件来更新符合条件的数据库记录,wallet_auto_pay
     *
     * @param record
     * @param example
     */
    int updateByExample(@Param("record") WalletAutoPay record, @Param("example") WalletAutoPayExample example);

    /**
     *  动态字段,根据主键来更新符合条件的数据库记录,wallet_auto_pay
     *
     * @param record
     */
    int updateByPrimaryKeySelective(WalletAutoPay record);

    /**
     *  根据主键来更新符合条件的数据库记录,wallet_auto_pay
     *
     * @param record
     */
    int updateByPrimaryKey(WalletAutoPay record);
}