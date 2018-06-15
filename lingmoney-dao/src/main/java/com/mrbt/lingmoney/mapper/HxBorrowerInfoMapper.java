package com.mrbt.lingmoney.mapper;

import com.mrbt.lingmoney.model.HxBorrowerInfo;
import com.mrbt.lingmoney.model.HxBorrowerInfoExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface HxBorrowerInfoMapper {
    /**
     *  根据指定的条件获取数据库记录数,hx_borrower_info
     * @param example
     */
    long countByExample(HxBorrowerInfoExample example);

    /**
     *  根据指定的条件删除数据库符合条件的记录,hx_borrower_info
     * @param example
     */
    int deleteByExample(HxBorrowerInfoExample example);

    /**
     *  根据主键删除数据库的记录,hx_borrower_info
     * @param id
     */
    int deleteByPrimaryKey(String id);

    /**
     *  新写入数据库记录,hx_borrower_info
     * @param record
     */
    int insert(HxBorrowerInfo record);

    /**
     *  动态字段,写入数据库记录,hx_borrower_info
     * @param record
     */
    int insertSelective(HxBorrowerInfo record);

    /**
     *  根据指定的条件查询符合条件的数据库记录,hx_borrower_info
     * @param example
     */
    List<HxBorrowerInfo> selectByExample(HxBorrowerInfoExample example);

    /**
     *  根据指定主键获取一条数据库记录,hx_borrower_info
     * @param id
     */
    HxBorrowerInfo selectByPrimaryKey(String id);

    /**
     *  动态根据指定的条件来更新符合条件的数据库记录,hx_borrower_info
     * @param record
     * @param example
     */
    int updateByExampleSelective(@Param("record") HxBorrowerInfo record, @Param("example") HxBorrowerInfoExample example);

    /**
     *  根据指定的条件来更新符合条件的数据库记录,hx_borrower_info
     * @param record
     * @param example
     */
    int updateByExample(@Param("record") HxBorrowerInfo record, @Param("example") HxBorrowerInfoExample example);

    /**
     *  动态字段,根据主键来更新符合条件的数据库记录,hx_borrower_info
     * @param record
     */
    int updateByPrimaryKeySelective(HxBorrowerInfo record);

    /**
     *  根据主键来更新符合条件的数据库记录,hx_borrower_info
     * @param record
     */
    int updateByPrimaryKey(HxBorrowerInfo record);
}