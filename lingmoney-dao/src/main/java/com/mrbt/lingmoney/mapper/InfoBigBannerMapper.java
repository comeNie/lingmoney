package com.mrbt.lingmoney.mapper;

import com.mrbt.lingmoney.model.InfoBigBanner;
import com.mrbt.lingmoney.model.InfoBigBannerExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface InfoBigBannerMapper {
    /**
     *  根据指定的条件获取数据库记录数,info_big_banner
     *
     * @param example
     */
    long countByExample(InfoBigBannerExample example);

    /**
     *  根据指定的条件删除数据库符合条件的记录,info_big_banner
     *
     * @param example
     */
    int deleteByExample(InfoBigBannerExample example);

    /**
     *  根据主键删除数据库的记录,info_big_banner
     *
     * @param id
     */
    int deleteByPrimaryKey(Integer id);

    /**
     *  新写入数据库记录,info_big_banner
     *
     * @param record
     */
    int insert(InfoBigBanner record);

    /**
     *  动态字段,写入数据库记录,info_big_banner
     *
     * @param record
     */
    int insertSelective(InfoBigBanner record);

    /**
     *  根据指定的条件查询符合条件的数据库记录,info_big_banner
     *
     * @param example
     */
    List<InfoBigBanner> selectByExample(InfoBigBannerExample example);

    /**
     *  根据指定主键获取一条数据库记录,info_big_banner
     *
     * @param id
     */
    InfoBigBanner selectByPrimaryKey(Integer id);

    /**
     *  动态根据指定的条件来更新符合条件的数据库记录,info_big_banner
     *
     * @param record
     * @param example
     */
    int updateByExampleSelective(@Param("record") InfoBigBanner record, @Param("example") InfoBigBannerExample example);

    /**
     *  根据指定的条件来更新符合条件的数据库记录,info_big_banner
     *
     * @param record
     * @param example
     */
    int updateByExample(@Param("record") InfoBigBanner record, @Param("example") InfoBigBannerExample example);

    /**
     *  动态字段,根据主键来更新符合条件的数据库记录,info_big_banner
     *
     * @param record
     */
    int updateByPrimaryKeySelective(InfoBigBanner record);

    /**
     *  根据主键来更新符合条件的数据库记录,info_big_banner
     *
     * @param record
     */
    int updateByPrimaryKey(InfoBigBanner record);
}