package com.mrbt.lingmoney.mapper;

import com.mrbt.lingmoney.model.Gift;
import com.mrbt.lingmoney.model.GiftExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface GiftMapper {
    /**
     *  根据指定的条件获取数据库记录数,gift
     *
     * @param example
     */
    long countByExample(GiftExample example);

    /**
     *  根据指定的条件删除数据库符合条件的记录,gift
     *
     * @param example
     */
    int deleteByExample(GiftExample example);

    /**
     *  根据主键删除数据库的记录,gift
     *
     * @param id
     */
    int deleteByPrimaryKey(Integer id);

    /**
     *  新写入数据库记录,gift
     *
     * @param record
     */
    int insert(Gift record);

    /**
     *  动态字段,写入数据库记录,gift
     *
     * @param record
     */
    int insertSelective(Gift record);

    /**
     * ,gift
     *
     * @param example
     */
    List<Gift> selectByExampleWithBLOBs(GiftExample example);

    /**
     *  根据指定的条件查询符合条件的数据库记录,gift
     *
     * @param example
     */
    List<Gift> selectByExample(GiftExample example);

    /**
     *  根据指定主键获取一条数据库记录,gift
     *
     * @param id
     */
    Gift selectByPrimaryKey(Integer id);

    /**
     *  动态根据指定的条件来更新符合条件的数据库记录,gift
     *
     * @param record
     * @param example
     */
    int updateByExampleSelective(@Param("record") Gift record, @Param("example") GiftExample example);

    /**
     * ,gift
     *
     * @param record
     * @param example
     */
    int updateByExampleWithBLOBs(@Param("record") Gift record, @Param("example") GiftExample example);

    /**
     *  根据指定的条件来更新符合条件的数据库记录,gift
     *
     * @param record
     * @param example
     */
    int updateByExample(@Param("record") Gift record, @Param("example") GiftExample example);

    /**
     *  动态字段,根据主键来更新符合条件的数据库记录,gift
     *
     * @param record
     */
    int updateByPrimaryKeySelective(Gift record);

    /**
     * ,gift
     *
     * @param record
     */
    int updateByPrimaryKeyWithBLOBs(Gift record);

    /**
     *  根据主键来更新符合条件的数据库记录,gift
     *
     * @param record
     */
    int updateByPrimaryKey(Gift record);
}