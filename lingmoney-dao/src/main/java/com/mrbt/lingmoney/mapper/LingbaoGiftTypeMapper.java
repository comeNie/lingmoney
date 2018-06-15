package com.mrbt.lingmoney.mapper;

import com.mrbt.lingmoney.model.LingbaoGiftType;
import com.mrbt.lingmoney.model.LingbaoGiftTypeExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface LingbaoGiftTypeMapper {
    /**
     *  根据指定的条件获取数据库记录数,lingbao_gift_type
     *
     * @param example
     */
    long countByExample(LingbaoGiftTypeExample example);

    /**
     *  根据指定的条件删除数据库符合条件的记录,lingbao_gift_type
     *
     * @param example
     */
    int deleteByExample(LingbaoGiftTypeExample example);

    /**
     *  根据主键删除数据库的记录,lingbao_gift_type
     *
     * @param id
     */
    int deleteByPrimaryKey(Integer id);

    /**
     *  新写入数据库记录,lingbao_gift_type
     *
     * @param record
     */
    int insert(LingbaoGiftType record);

    /**
     *  动态字段,写入数据库记录,lingbao_gift_type
     *
     * @param record
     */
    int insertSelective(LingbaoGiftType record);

    /**
     *  根据指定的条件查询符合条件的数据库记录,lingbao_gift_type
     *
     * @param example
     */
    List<LingbaoGiftType> selectByExample(LingbaoGiftTypeExample example);

    /**
     *  根据指定主键获取一条数据库记录,lingbao_gift_type
     *
     * @param id
     */
    LingbaoGiftType selectByPrimaryKey(Integer id);

    /**
     *  动态根据指定的条件来更新符合条件的数据库记录,lingbao_gift_type
     *
     * @param record
     * @param example
     */
    int updateByExampleSelective(@Param("record") LingbaoGiftType record, @Param("example") LingbaoGiftTypeExample example);

    /**
     *  根据指定的条件来更新符合条件的数据库记录,lingbao_gift_type
     *
     * @param record
     * @param example
     */
    int updateByExample(@Param("record") LingbaoGiftType record, @Param("example") LingbaoGiftTypeExample example);

    /**
     *  动态字段,根据主键来更新符合条件的数据库记录,lingbao_gift_type
     *
     * @param record
     */
    int updateByPrimaryKeySelective(LingbaoGiftType record);

    /**
     *  根据主键来更新符合条件的数据库记录,lingbao_gift_type
     *
     * @param record
     */
    int updateByPrimaryKey(LingbaoGiftType record);
}