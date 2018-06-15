package com.mrbt.lingmoney.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.mrbt.lingmoney.model.HxBanklenddingInfo;
import com.mrbt.lingmoney.model.HxBanklenddingInfoExample;

public interface HxBanklenddingInfoMapper {
    /**
     *  根据指定的条件获取数据库记录数,hx_banklendding_info
     * @param example
     */
    long countByExample(HxBanklenddingInfoExample example);

    /**
     *  根据指定的条件删除数据库符合条件的记录,hx_banklendding_info
     * @param example
     */
    int deleteByExample(HxBanklenddingInfoExample example);

    /**
     *  根据主键删除数据库的记录,hx_banklendding_info
     * @param id
     */
    int deleteByPrimaryKey(String id);

    /**
     *  新写入数据库记录,hx_banklendding_info
     * @param record
     */
    int insert(HxBanklenddingInfo record);

    /**
     *  动态字段,写入数据库记录,hx_banklendding_info
     * @param record
     */
    int insertSelective(HxBanklenddingInfo record);

    /**
     *  根据指定的条件查询符合条件的数据库记录,hx_banklendding_info
     * @param example
     */
    List<HxBanklenddingInfo> selectByExample(HxBanklenddingInfoExample example);

    /**
     *  根据指定主键获取一条数据库记录,hx_banklendding_info
     * @param id
     */
    HxBanklenddingInfo selectByPrimaryKey(String id);

    /**
     *  动态根据指定的条件来更新符合条件的数据库记录,hx_banklendding_info
     * @param record
     * @param example
     */
    int updateByExampleSelective(@Param("record") HxBanklenddingInfo record, @Param("example") HxBanklenddingInfoExample example);

    /**
     *  根据指定的条件来更新符合条件的数据库记录,hx_banklendding_info
     * @param record
     * @param example
     */
    int updateByExample(@Param("record") HxBanklenddingInfo record, @Param("example") HxBanklenddingInfoExample example);

    /**
     *  动态字段,根据主键来更新符合条件的数据库记录,hx_banklendding_info
     * @param record
     */
    int updateByPrimaryKeySelective(HxBanklenddingInfo record);

    /**
     *  根据主键来更新符合条件的数据库记录,hx_banklendding_info
     * @param record
     */
    int updateByPrimaryKey(HxBanklenddingInfo record);
}