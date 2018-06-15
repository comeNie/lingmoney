package com.mrbt.lingmoney.mapper;

import com.mrbt.lingmoney.model.LingbaoSupplier;
import com.mrbt.lingmoney.model.LingbaoSupplierExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface LingbaoSupplierMapper {
    /**
     *  根据指定的条件获取数据库记录数,lingbao_supplier
     *
     * @param example
     */
    long countByExample(LingbaoSupplierExample example);

    /**
     *  根据指定的条件删除数据库符合条件的记录,lingbao_supplier
     *
     * @param example
     */
    int deleteByExample(LingbaoSupplierExample example);

    /**
     *  根据主键删除数据库的记录,lingbao_supplier
     *
     * @param id
     */
    int deleteByPrimaryKey(Integer id);

    /**
     *  新写入数据库记录,lingbao_supplier
     *
     * @param record
     */
    int insert(LingbaoSupplier record);

    /**
     *  动态字段,写入数据库记录,lingbao_supplier
     *
     * @param record
     */
    int insertSelective(LingbaoSupplier record);

    /**
     *  根据指定的条件查询符合条件的数据库记录,lingbao_supplier
     *
     * @param example
     */
    List<LingbaoSupplier> selectByExample(LingbaoSupplierExample example);

    /**
     *  根据指定主键获取一条数据库记录,lingbao_supplier
     *
     * @param id
     */
    LingbaoSupplier selectByPrimaryKey(Integer id);

    /**
     *  动态根据指定的条件来更新符合条件的数据库记录,lingbao_supplier
     *
     * @param record
     * @param example
     */
    int updateByExampleSelective(@Param("record") LingbaoSupplier record, @Param("example") LingbaoSupplierExample example);

    /**
     *  根据指定的条件来更新符合条件的数据库记录,lingbao_supplier
     *
     * @param record
     * @param example
     */
    int updateByExample(@Param("record") LingbaoSupplier record, @Param("example") LingbaoSupplierExample example);

    /**
     *  动态字段,根据主键来更新符合条件的数据库记录,lingbao_supplier
     *
     * @param record
     */
    int updateByPrimaryKeySelective(LingbaoSupplier record);

    /**
     *  根据主键来更新符合条件的数据库记录,lingbao_supplier
     *
     * @param record
     */
    int updateByPrimaryKey(LingbaoSupplier record);
}