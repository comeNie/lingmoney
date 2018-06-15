package com.mrbt.lingmoney.mapper;

import com.mrbt.lingmoney.model.Notice;
import com.mrbt.lingmoney.model.NoticeExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface NoticeMapper {
    /**
     *  根据指定的条件获取数据库记录数,notice
     *
     * @param example
     */
    long countByExample(NoticeExample example);

    /**
     *  根据指定的条件删除数据库符合条件的记录,notice
     *
     * @param example
     */
    int deleteByExample(NoticeExample example);

    /**
     *  根据主键删除数据库的记录,notice
     *
     * @param id
     */
    int deleteByPrimaryKey(Integer id);

    /**
     *  新写入数据库记录,notice
     *
     * @param record
     */
    int insert(Notice record);

    /**
     *  动态字段,写入数据库记录,notice
     *
     * @param record
     */
    int insertSelective(Notice record);

    /**
     * ,notice
     *
     * @param example
     */
    List<Notice> selectByExampleWithBLOBs(NoticeExample example);

    /**
     *  根据指定的条件查询符合条件的数据库记录,notice
     *
     * @param example
     */
    List<Notice> selectByExample(NoticeExample example);

    /**
     *  根据指定主键获取一条数据库记录,notice
     *
     * @param id
     */
    Notice selectByPrimaryKey(Integer id);

    /**
     *  动态根据指定的条件来更新符合条件的数据库记录,notice
     *
     * @param record
     * @param example
     */
    int updateByExampleSelective(@Param("record") Notice record, @Param("example") NoticeExample example);

    /**
     * ,notice
     *
     * @param record
     * @param example
     */
    int updateByExampleWithBLOBs(@Param("record") Notice record, @Param("example") NoticeExample example);

    /**
     *  根据指定的条件来更新符合条件的数据库记录,notice
     *
     * @param record
     * @param example
     */
    int updateByExample(@Param("record") Notice record, @Param("example") NoticeExample example);

    /**
     *  动态字段,根据主键来更新符合条件的数据库记录,notice
     *
     * @param record
     */
    int updateByPrimaryKeySelective(Notice record);

    /**
     * ,notice
     *
     * @param record
     */
    int updateByPrimaryKeyWithBLOBs(Notice record);

    /**
     *  根据主键来更新符合条件的数据库记录,notice
     *
     * @param record
     */
    int updateByPrimaryKey(Notice record);
}