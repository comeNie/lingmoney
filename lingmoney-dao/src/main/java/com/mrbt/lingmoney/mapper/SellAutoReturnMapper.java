package com.mrbt.lingmoney.mapper;

import com.mrbt.lingmoney.model.SellAutoReturn;
import com.mrbt.lingmoney.model.SellAutoReturnExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface SellAutoReturnMapper {
    /**
     *  根据指定的条件获取数据库记录数,sell_auto_return
     *
     * @param example
     */
    long countByExample(SellAutoReturnExample example);

    /**
     *  根据指定的条件删除数据库符合条件的记录,sell_auto_return
     *
     * @param example
     */
    int deleteByExample(SellAutoReturnExample example);

    /**
     *  根据主键删除数据库的记录,sell_auto_return
     *
     * @param id
     */
    int deleteByPrimaryKey(Integer id);

    /**
     *  新写入数据库记录,sell_auto_return
     *
     * @param record
     */
    int insert(SellAutoReturn record);

    /**
     *  动态字段,写入数据库记录,sell_auto_return
     *
     * @param record
     */
    int insertSelective(SellAutoReturn record);

    /**
     *  根据指定的条件查询符合条件的数据库记录,sell_auto_return
     *
     * @param example
     */
    List<SellAutoReturn> selectByExample(SellAutoReturnExample example);

    /**
     *  根据指定主键获取一条数据库记录,sell_auto_return
     *
     * @param id
     */
    SellAutoReturn selectByPrimaryKey(Integer id);

    /**
     *  动态根据指定的条件来更新符合条件的数据库记录,sell_auto_return
     *
     * @param record
     * @param example
     */
    int updateByExampleSelective(@Param("record") SellAutoReturn record, @Param("example") SellAutoReturnExample example);

    /**
     *  根据指定的条件来更新符合条件的数据库记录,sell_auto_return
     *
     * @param record
     * @param example
     */
    int updateByExample(@Param("record") SellAutoReturn record, @Param("example") SellAutoReturnExample example);

    /**
     *  动态字段,根据主键来更新符合条件的数据库记录,sell_auto_return
     *
     * @param record
     */
    int updateByPrimaryKeySelective(SellAutoReturn record);

    /**
     *  根据主键来更新符合条件的数据库记录,sell_auto_return
     *
     * @param record
     */
    int updateByPrimaryKey(SellAutoReturn record);
}