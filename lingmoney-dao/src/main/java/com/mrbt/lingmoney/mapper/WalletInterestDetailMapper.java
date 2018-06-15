package com.mrbt.lingmoney.mapper;

import com.mrbt.lingmoney.model.WalletInterestDetail;
import com.mrbt.lingmoney.model.WalletInterestDetailExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface WalletInterestDetailMapper {
    /**
     *  根据指定的条件获取数据库记录数,wallet_interest_detail
     *
     * @param example
     */
    long countByExample(WalletInterestDetailExample example);

    /**
     *  根据指定的条件删除数据库符合条件的记录,wallet_interest_detail
     *
     * @param example
     */
    int deleteByExample(WalletInterestDetailExample example);

    /**
     *  根据主键删除数据库的记录,wallet_interest_detail
     *
     * @param id
     */
    int deleteByPrimaryKey(Integer id);

    /**
     *  新写入数据库记录,wallet_interest_detail
     *
     * @param record
     */
    int insert(WalletInterestDetail record);

    /**
     *  动态字段,写入数据库记录,wallet_interest_detail
     *
     * @param record
     */
    int insertSelective(WalletInterestDetail record);

    /**
     *  根据指定的条件查询符合条件的数据库记录,wallet_interest_detail
     *
     * @param example
     */
    List<WalletInterestDetail> selectByExample(WalletInterestDetailExample example);

    /**
     *  根据指定主键获取一条数据库记录,wallet_interest_detail
     *
     * @param id
     */
    WalletInterestDetail selectByPrimaryKey(Integer id);

    /**
     *  动态根据指定的条件来更新符合条件的数据库记录,wallet_interest_detail
     *
     * @param record
     * @param example
     */
    int updateByExampleSelective(@Param("record") WalletInterestDetail record, @Param("example") WalletInterestDetailExample example);

    /**
     *  根据指定的条件来更新符合条件的数据库记录,wallet_interest_detail
     *
     * @param record
     * @param example
     */
    int updateByExample(@Param("record") WalletInterestDetail record, @Param("example") WalletInterestDetailExample example);

    /**
     *  动态字段,根据主键来更新符合条件的数据库记录,wallet_interest_detail
     *
     * @param record
     */
    int updateByPrimaryKeySelective(WalletInterestDetail record);

    /**
     *  根据主键来更新符合条件的数据库记录,wallet_interest_detail
     *
     * @param record
     */
    int updateByPrimaryKey(WalletInterestDetail record);
}