package com.mrbt.lingmoney.mapper;

import com.mrbt.lingmoney.model.PrefixBank;
import com.mrbt.lingmoney.model.PrefixBankExample;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

public interface PrefixBankMapper {
    /**
     *  根据指定的条件获取数据库记录数,prefix_bank
     *
     * @param example
     */
    long countByExample(PrefixBankExample example);

    /**
     *  根据指定的条件删除数据库符合条件的记录,prefix_bank
     *
     * @param example
     */
    int deleteByExample(PrefixBankExample example);

    /**
     *  根据主键删除数据库的记录,prefix_bank
     *
     * @param id
     */
    int deleteByPrimaryKey(Integer id);

    /**
     *  新写入数据库记录,prefix_bank
     *
     * @param record
     */
    int insert(PrefixBank record);

    /**
     *  动态字段,写入数据库记录,prefix_bank
     *
     * @param record
     */
    int insertSelective(PrefixBank record);

    /**
     *  根据指定的条件查询符合条件的数据库记录,prefix_bank
     *
     * @param example
     */
    List<PrefixBank> selectByExample(PrefixBankExample example);

    /**
     *  根据指定主键获取一条数据库记录,prefix_bank
     *
     * @param id
     */
    PrefixBank selectByPrimaryKey(Integer id);

    /**
     *  动态根据指定的条件来更新符合条件的数据库记录,prefix_bank
     *
     * @param record
     * @param example
     */
    int updateByExampleSelective(@Param("record") PrefixBank record, @Param("example") PrefixBankExample example);

    /**
     *  根据指定的条件来更新符合条件的数据库记录,prefix_bank
     *
     * @param record
     * @param example
     */
    int updateByExample(@Param("record") PrefixBank record, @Param("example") PrefixBankExample example);

    /**
     *  动态字段,根据主键来更新符合条件的数据库记录,prefix_bank
     *
     * @param record
     */
    int updateByPrimaryKeySelective(PrefixBank record);

    /**
     *  根据主键来更新符合条件的数据库记录,prefix_bank
     *
     * @param record
     */
    int updateByPrimaryKey(PrefixBank record);

	List<PrefixBank> selectByCondition(Map<String, Object> map);

	int selectByConditionCount(Map<String, Object> map);
}