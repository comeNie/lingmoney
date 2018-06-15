package com.mrbt.lingmoney.mapper;

import com.mrbt.lingmoney.model.InfoNews;
import com.mrbt.lingmoney.model.InfoNewsExample;

import java.util.List;

import org.apache.ibatis.annotations.Param;

public interface InfoNewsMapper {
    /**
     *  根据指定的条件获取数据库记录数,info_news
     *
     * @param example
     */
    long countByExample(InfoNewsExample example);

    /**
     *  根据指定的条件删除数据库符合条件的记录,info_news
     *
     * @param example
     */
    int deleteByExample(InfoNewsExample example);

    /**
     *  根据主键删除数据库的记录,info_news
     *
     * @param id
     */
    int deleteByPrimaryKey(Integer id);

    /**
     *  新写入数据库记录,info_news
     *
     * @param record
     */
    int insert(InfoNews record);

    /**
     *  动态字段,写入数据库记录,info_news
     *
     * @param record
     */
    int insertSelective(InfoNews record);

    /**
     * ,info_news
     *
     * @param example
     */
    List<InfoNews> selectByExampleWithBLOBs(InfoNewsExample example);

    /**
     *  根据指定的条件查询符合条件的数据库记录,info_news
     *
     * @param example
     */
    List<InfoNews> selectByExample(InfoNewsExample example);

    /**
     *  根据指定主键获取一条数据库记录,info_news
     *
     * @param id
     */
    InfoNews selectByPrimaryKey(Integer id);

    /**
     *  动态根据指定的条件来更新符合条件的数据库记录,info_news
     *
     * @param record
     * @param example
     */
    int updateByExampleSelective(@Param("record") InfoNews record, @Param("example") InfoNewsExample example);

    /**
     * ,info_news
     *
     * @param record
     * @param example
     */
    int updateByExampleWithBLOBs(@Param("record") InfoNews record, @Param("example") InfoNewsExample example);

    /**
     *  根据指定的条件来更新符合条件的数据库记录,info_news
     *
     * @param record
     * @param example
     */
    int updateByExample(@Param("record") InfoNews record, @Param("example") InfoNewsExample example);

    /**
     *  动态字段,根据主键来更新符合条件的数据库记录,info_news
     *
     * @param record
     */
    int updateByPrimaryKeySelective(InfoNews record);

    /**
     * ,info_news
     *
     * @param record
     */
    int updateByPrimaryKeyWithBLOBs(InfoNews record);

    /**
     *  根据主键来更新符合条件的数据库记录,info_news
     *
     * @param record
     */
    int updateByPrimaryKey(InfoNews record);

    /**
     * 查询所有ID
     * @return
     */
	List<Integer> findIdList();
}