package com.mrbt.lingmoney.mapper;

import com.mrbt.lingmoney.model.HxBorrower;
import com.mrbt.lingmoney.model.HxBorrowerExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface HxBorrowerMapper {
    /**
     *  根据指定的条件获取数据库记录数,hx_borrower
     * @param example
     */
    long countByExample(HxBorrowerExample example);

    /**
     *  根据指定的条件删除数据库符合条件的记录,hx_borrower
     * @param example
     */
    int deleteByExample(HxBorrowerExample example);

    /**
     *  根据主键删除数据库的记录,hx_borrower
     * @param id
     */
    int deleteByPrimaryKey(String id);

    /**
     *  新写入数据库记录,hx_borrower
     * @param record
     */
    int insert(HxBorrower record);

    /**
     *  动态字段,写入数据库记录,hx_borrower
     * @param record
     */
    int insertSelective(HxBorrower record);

    /**
     *  根据指定的条件查询符合条件的数据库记录,hx_borrower
     * @param example
     */
    List<HxBorrower> selectByExample(HxBorrowerExample example);

    /**
     *  根据指定主键获取一条数据库记录,hx_borrower
     * @param id
     */
    HxBorrower selectByPrimaryKey(String id);

    /**
     *  动态根据指定的条件来更新符合条件的数据库记录,hx_borrower
     * @param record
     * @param example
     */
    int updateByExampleSelective(@Param("record") HxBorrower record, @Param("example") HxBorrowerExample example);

    /**
     *  根据指定的条件来更新符合条件的数据库记录,hx_borrower
     * @param record
     * @param example
     */
    int updateByExample(@Param("record") HxBorrower record, @Param("example") HxBorrowerExample example);

    /**
     *  动态字段,根据主键来更新符合条件的数据库记录,hx_borrower
     * @param record
     */
    int updateByPrimaryKeySelective(HxBorrower record);

    /**
     *  根据主键来更新符合条件的数据库记录,hx_borrower
     * @param record
     */
    int updateByPrimaryKey(HxBorrower record);
}