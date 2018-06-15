package com.mrbt.lingmoney.mapper;

import com.mrbt.lingmoney.model.UnbandBankcard;
import com.mrbt.lingmoney.model.UnbandBankcardExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface UnbandBankcardMapper {
    /**
     *  根据指定的条件获取数据库记录数,unband_bankcard
     *
     * @param example
     */
    long countByExample(UnbandBankcardExample example);

    /**
     *  根据指定的条件删除数据库符合条件的记录,unband_bankcard
     *
     * @param example
     */
    int deleteByExample(UnbandBankcardExample example);

    /**
     *  根据主键删除数据库的记录,unband_bankcard
     *
     * @param id
     */
    int deleteByPrimaryKey(Integer id);

    /**
     *  新写入数据库记录,unband_bankcard
     *
     * @param record
     */
    int insert(UnbandBankcard record);

    /**
     *  动态字段,写入数据库记录,unband_bankcard
     *
     * @param record
     */
    int insertSelective(UnbandBankcard record);

    /**
     *  根据指定的条件查询符合条件的数据库记录,unband_bankcard
     *
     * @param example
     */
    List<UnbandBankcard> selectByExample(UnbandBankcardExample example);

    /**
     *  根据指定主键获取一条数据库记录,unband_bankcard
     *
     * @param id
     */
    UnbandBankcard selectByPrimaryKey(Integer id);

    /**
     *  动态根据指定的条件来更新符合条件的数据库记录,unband_bankcard
     *
     * @param record
     * @param example
     */
    int updateByExampleSelective(@Param("record") UnbandBankcard record, @Param("example") UnbandBankcardExample example);

    /**
     *  根据指定的条件来更新符合条件的数据库记录,unband_bankcard
     *
     * @param record
     * @param example
     */
    int updateByExample(@Param("record") UnbandBankcard record, @Param("example") UnbandBankcardExample example);

    /**
     *  动态字段,根据主键来更新符合条件的数据库记录,unband_bankcard
     *
     * @param record
     */
    int updateByPrimaryKeySelective(UnbandBankcard record);

    /**
     *  根据主键来更新符合条件的数据库记录,unband_bankcard
     *
     * @param record
     */
    int updateByPrimaryKey(UnbandBankcard record);
}