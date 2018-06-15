package com.mrbt.lingmoney.mapper;

import com.mrbt.lingmoney.model.InfoMedia;
import com.mrbt.lingmoney.model.InfoMediaExample;

import java.util.List;

import org.apache.ibatis.annotations.Param;

public interface InfoMediaMapper {
    /**
     *  根据指定的条件获取数据库记录数,info_media
     *
     * @param example
     */
    long countByExample(InfoMediaExample example);

    /**
     *  根据指定的条件删除数据库符合条件的记录,info_media
     *
     * @param example
     */
    int deleteByExample(InfoMediaExample example);

    /**
     *  根据主键删除数据库的记录,info_media
     *
     * @param id
     */
    int deleteByPrimaryKey(Integer id);

    /**
     *  新写入数据库记录,info_media
     *
     * @param record
     */
    int insert(InfoMedia record);

    /**
     *  动态字段,写入数据库记录,info_media
     *
     * @param record
     */
    int insertSelective(InfoMedia record);

    /**
     * ,info_media
     *
     * @param example
     */
    List<InfoMedia> selectByExampleWithBLOBs(InfoMediaExample example);

    /**
     *  根据指定的条件查询符合条件的数据库记录,info_media
     *
     * @param example
     */
    List<InfoMedia> selectByExample(InfoMediaExample example);

    /**
     *  根据指定主键获取一条数据库记录,info_media
     *
     * @param id
     */
    InfoMedia selectByPrimaryKey(Integer id);

    /**
     *  动态根据指定的条件来更新符合条件的数据库记录,info_media
     *
     * @param record
     * @param example
     */
    int updateByExampleSelective(@Param("record") InfoMedia record, @Param("example") InfoMediaExample example);

    /**
     * ,info_media
     *
     * @param record
     * @param example
     */
    int updateByExampleWithBLOBs(@Param("record") InfoMedia record, @Param("example") InfoMediaExample example);

    /**
     *  根据指定的条件来更新符合条件的数据库记录,info_media
     *
     * @param record
     * @param example
     */
    int updateByExample(@Param("record") InfoMedia record, @Param("example") InfoMediaExample example);

    /**
     *  动态字段,根据主键来更新符合条件的数据库记录,info_media
     *
     * @param record
     */
    int updateByPrimaryKeySelective(InfoMedia record);

    /**
     * ,info_media
     *
     * @param record
     */
    int updateByPrimaryKeyWithBLOBs(InfoMedia record);

    /**
     *  根据主键来更新符合条件的数据库记录,info_media
     *
     * @param record
     */
    int updateByPrimaryKey(InfoMedia record);

    /**
     * 查询所有id
     * @return
     */
	List<Integer> findIdList();

	/**
	 * 根据id取数据，不带blob字段
	 * 
	 * @param id
	 * @return
	 */
	InfoMedia selectByPrimaryKeyNotBlob(int id);
}