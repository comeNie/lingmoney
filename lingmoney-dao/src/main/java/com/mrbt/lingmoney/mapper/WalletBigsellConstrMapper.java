package com.mrbt.lingmoney.mapper;

import com.mrbt.lingmoney.model.WalletBigsellConstr;
import com.mrbt.lingmoney.model.WalletBigsellConstrExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface WalletBigsellConstrMapper {
    /**
     *  根据指定的条件获取数据库记录数,wallet_bigsell_constr
     *
     * @param example
     */
    long countByExample(WalletBigsellConstrExample example);

    /**
     *  根据指定的条件删除数据库符合条件的记录,wallet_bigsell_constr
     *
     * @param example
     */
    int deleteByExample(WalletBigsellConstrExample example);

    /**
     *  根据主键删除数据库的记录,wallet_bigsell_constr
     *
     * @param id
     */
    int deleteByPrimaryKey(Integer id);

    /**
     *  新写入数据库记录,wallet_bigsell_constr
     *
     * @param record
     */
    int insert(WalletBigsellConstr record);

    /**
     *  动态字段,写入数据库记录,wallet_bigsell_constr
     *
     * @param record
     */
    int insertSelective(WalletBigsellConstr record);

    /**
     *  根据指定的条件查询符合条件的数据库记录,wallet_bigsell_constr
     *
     * @param example
     */
    List<WalletBigsellConstr> selectByExample(WalletBigsellConstrExample example);

    /**
     *  根据指定主键获取一条数据库记录,wallet_bigsell_constr
     *
     * @param id
     */
    WalletBigsellConstr selectByPrimaryKey(Integer id);

    /**
     *  动态根据指定的条件来更新符合条件的数据库记录,wallet_bigsell_constr
     *
     * @param record
     * @param example
     */
    int updateByExampleSelective(@Param("record") WalletBigsellConstr record, @Param("example") WalletBigsellConstrExample example);

    /**
     *  根据指定的条件来更新符合条件的数据库记录,wallet_bigsell_constr
     *
     * @param record
     * @param example
     */
    int updateByExample(@Param("record") WalletBigsellConstr record, @Param("example") WalletBigsellConstrExample example);

    /**
     *  动态字段,根据主键来更新符合条件的数据库记录,wallet_bigsell_constr
     *
     * @param record
     */
    int updateByPrimaryKeySelective(WalletBigsellConstr record);

    /**
     *  根据主键来更新符合条件的数据库记录,wallet_bigsell_constr
     *
     * @param record
     */
    int updateByPrimaryKey(WalletBigsellConstr record);
}