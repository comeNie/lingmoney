package com.mrbt.lingmoney.mapper;

import com.mrbt.lingmoney.model.HxCard;
import com.mrbt.lingmoney.model.HxCardExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface HxCardMapper {
    /**
     *  根据指定的条件获取数据库记录数,hx_card
     *
     * @param example
     */
    int countByExample(HxCardExample example);

    /**
     *  根据指定的条件删除数据库符合条件的记录,hx_card
     *
     * @param example
     */
    int deleteByExample(HxCardExample example);

    /**
     *  根据主键删除数据库的记录,hx_card
     *
     * @param id
     */
    int deleteByPrimaryKey(String id);

    /**
     *  新写入数据库记录,hx_card
     *
     * @param record
     */
    int insert(HxCard record);

    /**
     *  动态字段,写入数据库记录,hx_card
     *
     * @param record
     */
    int insertSelective(HxCard record);

    /**
     *  根据指定的条件查询符合条件的数据库记录,hx_card
     *
     * @param example
     */
    List<HxCard> selectByExample(HxCardExample example);

    /**
     *  根据指定主键获取一条数据库记录,hx_card
     *
     * @param id
     */
    HxCard selectByPrimaryKey(String id);

    /**
     *  动态根据指定的条件来更新符合条件的数据库记录,hx_card
     *
     * @param record
     * @param example
     */
    int updateByExampleSelective(@Param("record") HxCard record, @Param("example") HxCardExample example);

    /**
     *  根据指定的条件来更新符合条件的数据库记录,hx_card
     *
     * @param record
     * @param example
     */
    int updateByExample(@Param("record") HxCard record, @Param("example") HxCardExample example);

    /**
     *  动态字段,根据主键来更新符合条件的数据库记录,hx_card
     *
     * @param record
     */
    int updateByPrimaryKeySelective(HxCard record);

    /**
     *  根据主键来更新符合条件的数据库记录,hx_card
     *
     * @param record
     */
    int updateByPrimaryKey(HxCard record);
}