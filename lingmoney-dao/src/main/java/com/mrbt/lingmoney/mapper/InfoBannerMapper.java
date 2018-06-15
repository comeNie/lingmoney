package com.mrbt.lingmoney.mapper;

import com.mrbt.lingmoney.model.InfoBanner;
import com.mrbt.lingmoney.model.InfoBannerExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface InfoBannerMapper {
    /**
     *  根据指定的条件获取数据库记录数,info_banner
     *
     * @param example
     */
    long countByExample(InfoBannerExample example);

    /**
     *  根据指定的条件删除数据库符合条件的记录,info_banner
     *
     * @param example
     */
    int deleteByExample(InfoBannerExample example);

    /**
     *  根据主键删除数据库的记录,info_banner
     *
     * @param id
     */
    int deleteByPrimaryKey(Integer id);

    /**
     *  新写入数据库记录,info_banner
     *
     * @param record
     */
    int insert(InfoBanner record);

    /**
     *  动态字段,写入数据库记录,info_banner
     *
     * @param record
     */
    int insertSelective(InfoBanner record);

    /**
     *  根据指定的条件查询符合条件的数据库记录,info_banner
     *
     * @param example
     */
    List<InfoBanner> selectByExample(InfoBannerExample example);

    /**
     *  根据指定主键获取一条数据库记录,info_banner
     *
     * @param id
     */
    InfoBanner selectByPrimaryKey(Integer id);

    /**
     *  动态根据指定的条件来更新符合条件的数据库记录,info_banner
     *
     * @param record
     * @param example
     */
    int updateByExampleSelective(@Param("record") InfoBanner record, @Param("example") InfoBannerExample example);

    /**
     *  根据指定的条件来更新符合条件的数据库记录,info_banner
     *
     * @param record
     * @param example
     */
    int updateByExample(@Param("record") InfoBanner record, @Param("example") InfoBannerExample example);

    /**
     *  动态字段,根据主键来更新符合条件的数据库记录,info_banner
     *
     * @param record
     */
    int updateByPrimaryKeySelective(InfoBanner record);

    /**
     *  根据主键来更新符合条件的数据库记录,info_banner
     *
     * @param record
     */
    int updateByPrimaryKey(InfoBanner record);
}