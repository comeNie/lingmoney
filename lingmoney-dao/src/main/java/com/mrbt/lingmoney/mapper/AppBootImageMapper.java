package com.mrbt.lingmoney.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.mrbt.lingmoney.model.AppBootImage;
import com.mrbt.lingmoney.model.AppBootImageExample;

public interface AppBootImageMapper {
    /**
     *  根据指定的条件获取数据库记录数,app_boot_image
     *
     * @param example
     */
    int countByExample(AppBootImageExample example);

    /**
     *  根据指定的条件删除数据库符合条件的记录,app_boot_image
     *
     * @param example
     */
    int deleteByExample(AppBootImageExample example);

    /**
     *  根据主键删除数据库的记录,app_boot_image
     *
     * @param id
     */
    int deleteByPrimaryKey(Integer id);

    /**
     *  新写入数据库记录,app_boot_image
     *
     * @param record
     */
    int insert(AppBootImage record);

    /**
     *  动态字段,写入数据库记录,app_boot_image
     *
     * @param record
     */
    int insertSelective(AppBootImage record);

    /**
     *  根据指定的条件查询符合条件的数据库记录,app_boot_image
     *
     * @param example
     */
    List<AppBootImage> selectByExample(AppBootImageExample example);

    /**
     *  根据指定主键获取一条数据库记录,app_boot_image
     *
     * @param id
     */
    AppBootImage selectByPrimaryKey(Integer id);

    /**
     *  动态根据指定的条件来更新符合条件的数据库记录,app_boot_image
     *
     * @param record
     * @param example
     */
    int updateByExampleSelective(@Param("record") AppBootImage record, @Param("example") AppBootImageExample example);

    /**
     *  根据指定的条件来更新符合条件的数据库记录,app_boot_image
     *
     * @param record
     * @param example
     */
    int updateByExample(@Param("record") AppBootImage record, @Param("example") AppBootImageExample example);

    /**
     *  动态字段,根据主键来更新符合条件的数据库记录,app_boot_image
     *
     * @param record
     */
    int updateByPrimaryKeySelective(AppBootImage record);

    /**
     *  根据主键来更新符合条件的数据库记录,app_boot_image
     *
     * @param record
     */
    int updateByPrimaryKey(AppBootImage record);

	/**
	 * 根据批次号查询该批次下最新产品
	 *
	 * @Description
	 * @param data
	 * @return
	 */
	Integer queryNewestProductByBatch(String data);
}