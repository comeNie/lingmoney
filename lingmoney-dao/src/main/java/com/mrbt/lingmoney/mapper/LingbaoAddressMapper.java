package com.mrbt.lingmoney.mapper;

import com.mrbt.lingmoney.model.LingbaoAddress;
import com.mrbt.lingmoney.model.LingbaoAddressExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface LingbaoAddressMapper {
    /**
     *  根据指定的条件获取数据库记录数,lingbao_address
     *
     * @param example
     */
    long countByExample(LingbaoAddressExample example);

    /**
     *  根据指定的条件删除数据库符合条件的记录,lingbao_address
     *
     * @param example
     */
    int deleteByExample(LingbaoAddressExample example);

    /**
     *  根据主键删除数据库的记录,lingbao_address
     *
     * @param id
     */
    int deleteByPrimaryKey(Integer id);

    /**
     *  新写入数据库记录,lingbao_address
     *
     * @param record
     */
    int insert(LingbaoAddress record);

    /**
     *  动态字段,写入数据库记录,lingbao_address
     *
     * @param record
     */
    int insertSelective(LingbaoAddress record);

    /**
     *  根据指定的条件查询符合条件的数据库记录,lingbao_address
     *
     * @param example
     */
    List<LingbaoAddress> selectByExample(LingbaoAddressExample example);

    /**
     *  根据指定主键获取一条数据库记录,lingbao_address
     *
     * @param id
     */
    LingbaoAddress selectByPrimaryKey(Integer id);

    /**
     *  动态根据指定的条件来更新符合条件的数据库记录,lingbao_address
     *
     * @param record
     * @param example
     */
    int updateByExampleSelective(@Param("record") LingbaoAddress record, @Param("example") LingbaoAddressExample example);

    /**
     *  根据指定的条件来更新符合条件的数据库记录,lingbao_address
     *
     * @param record
     * @param example
     */
    int updateByExample(@Param("record") LingbaoAddress record, @Param("example") LingbaoAddressExample example);

    /**
     *  动态字段,根据主键来更新符合条件的数据库记录,lingbao_address
     *
     * @param record
     */
    int updateByPrimaryKeySelective(LingbaoAddress record);

    /**
     *  根据主键来更新符合条件的数据库记录,lingbao_address
     *
     * @param record
     */
    int updateByPrimaryKey(LingbaoAddress record);
}