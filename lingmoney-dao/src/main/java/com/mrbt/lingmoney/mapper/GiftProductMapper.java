package com.mrbt.lingmoney.mapper;

import com.mrbt.lingmoney.model.GiftProduct;
import com.mrbt.lingmoney.model.GiftProductExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface GiftProductMapper {
    /**
     *  根据指定的条件获取数据库记录数,gift_product
     *
     * @param example
     */
    long countByExample(GiftProductExample example);

    /**
     *  根据指定的条件删除数据库符合条件的记录,gift_product
     *
     * @param example
     */
    int deleteByExample(GiftProductExample example);

    /**
     *  根据主键删除数据库的记录,gift_product
     *
     * @param id
     */
    int deleteByPrimaryKey(Integer id);

    /**
     *  新写入数据库记录,gift_product
     *
     * @param record
     */
    int insert(GiftProduct record);

    /**
     *  动态字段,写入数据库记录,gift_product
     *
     * @param record
     */
    int insertSelective(GiftProduct record);

    /**
     * ,gift_product
     *
     * @param example
     */
    List<GiftProduct> selectByExampleWithBLOBs(GiftProductExample example);

    /**
     *  根据指定的条件查询符合条件的数据库记录,gift_product
     *
     * @param example
     */
    List<GiftProduct> selectByExample(GiftProductExample example);

    /**
     *  根据指定主键获取一条数据库记录,gift_product
     *
     * @param id
     */
    GiftProduct selectByPrimaryKey(Integer id);

    /**
     *  动态根据指定的条件来更新符合条件的数据库记录,gift_product
     *
     * @param record
     * @param example
     */
    int updateByExampleSelective(@Param("record") GiftProduct record, @Param("example") GiftProductExample example);

    /**
     * ,gift_product
     *
     * @param record
     * @param example
     */
    int updateByExampleWithBLOBs(@Param("record") GiftProduct record, @Param("example") GiftProductExample example);

    /**
     *  根据指定的条件来更新符合条件的数据库记录,gift_product
     *
     * @param record
     * @param example
     */
    int updateByExample(@Param("record") GiftProduct record, @Param("example") GiftProductExample example);

    /**
     *  动态字段,根据主键来更新符合条件的数据库记录,gift_product
     *
     * @param record
     */
    int updateByPrimaryKeySelective(GiftProduct record);

    /**
     * ,gift_product
     *
     * @param record
     */
    int updateByPrimaryKeyWithBLOBs(GiftProduct record);

    /**
     *  根据主键来更新符合条件的数据库记录,gift_product
     *
     * @param record
     */
    int updateByPrimaryKey(GiftProduct record);
}