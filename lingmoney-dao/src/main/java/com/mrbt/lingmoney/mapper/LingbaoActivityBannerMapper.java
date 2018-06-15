package com.mrbt.lingmoney.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.mrbt.lingmoney.model.LingbaoActivityBanner;
import com.mrbt.lingmoney.model.LingbaoActivityBannerExample;

public interface LingbaoActivityBannerMapper {
    /**
     *  根据指定的条件获取数据库记录数,lingbao_activity_banner
     *
     * @param example
     */
    long countByExample(LingbaoActivityBannerExample example);

    /**
     *  根据指定的条件删除数据库符合条件的记录,lingbao_activity_banner
     *
     * @param example
     */
    int deleteByExample(LingbaoActivityBannerExample example);

    /**
     *  根据主键删除数据库的记录,lingbao_activity_banner
     *
     * @param id
     */
    int deleteByPrimaryKey(Integer id);

    /**
     *  新写入数据库记录,lingbao_activity_banner
     *
     * @param record
     */
    int insert(LingbaoActivityBanner record);

    /**
     *  动态字段,写入数据库记录,lingbao_activity_banner
     *
     * @param record
     */
    int insertSelective(LingbaoActivityBanner record);

    /**
     *  根据指定的条件查询符合条件的数据库记录,lingbao_activity_banner
     *
     * @param example
     */
    List<LingbaoActivityBanner> selectByExample(LingbaoActivityBannerExample example);

    /**
     *  根据指定主键获取一条数据库记录,lingbao_activity_banner
     *
     * @param id
     */
    LingbaoActivityBanner selectByPrimaryKey(Integer id);

    /**
     *  动态根据指定的条件来更新符合条件的数据库记录,lingbao_activity_banner
     *
     * @param record
     * @param example
     */
    int updateByExampleSelective(@Param("record") LingbaoActivityBanner record, @Param("example") LingbaoActivityBannerExample example);

    /**
     *  根据指定的条件来更新符合条件的数据库记录,lingbao_activity_banner
     *
     * @param record
     * @param example
     */
    int updateByExample(@Param("record") LingbaoActivityBanner record, @Param("example") LingbaoActivityBannerExample example);

    /**
     *  动态字段,根据主键来更新符合条件的数据库记录,lingbao_activity_banner
     *
     * @param record
     */
    int updateByPrimaryKeySelective(LingbaoActivityBanner record);

    /**
     *  根据主键来更新符合条件的数据库记录,lingbao_activity_banner
     *
     * @param record
     */
    int updateByPrimaryKey(LingbaoActivityBanner record);

	/**
	 * 
	 * @description 根据活动类型查询活动信息
	 * @author syb
	 * @date 2017年8月9日 下午2:32:04
	 * @version 1.0
	 * @param type
	 *            0 抽奖 1限时抢
	 * @return
	 *
	 */
	List<LingbaoActivityBanner> listBannerByLotteryType(Integer type);
}