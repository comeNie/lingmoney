package com.mrbt.lingmoney.mapper;

import com.mrbt.lingmoney.model.DreamBaseInfo;
import com.mrbt.lingmoney.model.DreamBaseInfoExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface DreamBaseInfoMapper {
    /**
     *  根据指定的条件获取数据库记录数,dream_base_info
     *
     * @param example
     */
    long countByExample(DreamBaseInfoExample example);

    /**
     *  根据指定的条件删除数据库符合条件的记录,dream_base_info
     *
     * @param example
     */
    int deleteByExample(DreamBaseInfoExample example);

    /**
     *  根据主键删除数据库的记录,dream_base_info
     *
     * @param id
     */
    int deleteByPrimaryKey(Integer id);

    /**
     *  新写入数据库记录,dream_base_info
     *
     * @param record
     */
    int insert(DreamBaseInfo record);

    /**
     *  动态字段,写入数据库记录,dream_base_info
     *
     * @param record
     */
    int insertSelective(DreamBaseInfo record);

    /**
     *  根据指定的条件查询符合条件的数据库记录,dream_base_info
     *
     * @param example
     */
    List<DreamBaseInfo> selectByExample(DreamBaseInfoExample example);

    /**
     *  根据指定主键获取一条数据库记录,dream_base_info
     *
     * @param id
     */
    DreamBaseInfo selectByPrimaryKey(Integer id);

    /**
     *  动态根据指定的条件来更新符合条件的数据库记录,dream_base_info
     *
     * @param record
     * @param example
     */
    int updateByExampleSelective(@Param("record") DreamBaseInfo record, @Param("example") DreamBaseInfoExample example);

    /**
     *  根据指定的条件来更新符合条件的数据库记录,dream_base_info
     *
     * @param record
     * @param example
     */
    int updateByExample(@Param("record") DreamBaseInfo record, @Param("example") DreamBaseInfoExample example);

    /**
     *  动态字段,根据主键来更新符合条件的数据库记录,dream_base_info
     *
     * @param record
     */
    int updateByPrimaryKeySelective(DreamBaseInfo record);

    /**
     *  根据主键来更新符合条件的数据库记录,dream_base_info
     *
     * @param record
     */
    int updateByPrimaryKey(DreamBaseInfo record);
}