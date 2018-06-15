package com.mrbt.lingmoney.mapper;

import com.mrbt.lingmoney.model.WalletAccountDay;
import com.mrbt.lingmoney.model.WalletAccountDayExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface WalletAccountDayMapper {
    /**
     *  根据指定的条件获取数据库记录数,wallet_account_day
     *
     * @param example
     */
    long countByExample(WalletAccountDayExample example);

    /**
     *  根据指定的条件删除数据库符合条件的记录,wallet_account_day
     *
     * @param example
     */
    int deleteByExample(WalletAccountDayExample example);

    /**
     *  新写入数据库记录,wallet_account_day
     *
     * @param record
     */
    int insert(WalletAccountDay record);

    /**
     *  动态字段,写入数据库记录,wallet_account_day
     *
     * @param record
     */
    int insertSelective(WalletAccountDay record);

    /**
     *  根据指定的条件查询符合条件的数据库记录,wallet_account_day
     *
     * @param example
     */
    List<WalletAccountDay> selectByExample(WalletAccountDayExample example);

    /**
     *  动态根据指定的条件来更新符合条件的数据库记录,wallet_account_day
     *
     * @param record
     * @param example
     */
    int updateByExampleSelective(@Param("record") WalletAccountDay record, @Param("example") WalletAccountDayExample example);

    /**
     *  根据指定的条件来更新符合条件的数据库记录,wallet_account_day
     *
     * @param record
     * @param example
     */
    int updateByExample(@Param("record") WalletAccountDay record, @Param("example") WalletAccountDayExample example);
}