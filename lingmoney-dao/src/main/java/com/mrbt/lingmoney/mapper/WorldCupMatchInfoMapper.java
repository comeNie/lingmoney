package com.mrbt.lingmoney.mapper;

import com.mrbt.lingmoney.model.WorldCupMatchInfo;
import com.mrbt.lingmoney.model.WorldCupMatchInfoExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface WorldCupMatchInfoMapper {
    /**
     *  根据指定的条件获取数据库记录数,world_cup_match_info
     *
     * @param example
     */
    long countByExample(WorldCupMatchInfoExample example);

    /**
     *  根据指定的条件删除数据库符合条件的记录,world_cup_match_info
     *
     * @param example
     */
    int deleteByExample(WorldCupMatchInfoExample example);

    /**
     *  根据主键删除数据库的记录,world_cup_match_info
     *
     * @param id
     */
    int deleteByPrimaryKey(Integer id);

    /**
     *  新写入数据库记录,world_cup_match_info
     *
     * @param record
     */
    int insert(WorldCupMatchInfo record);

    /**
     *  动态字段,写入数据库记录,world_cup_match_info
     *
     * @param record
     */
    int insertSelective(WorldCupMatchInfo record);

    /**
     *  根据指定的条件查询符合条件的数据库记录,world_cup_match_info
     *
     * @param example
     */
    List<WorldCupMatchInfo> selectByExample(WorldCupMatchInfoExample example);

    /**
     *  根据指定主键获取一条数据库记录,world_cup_match_info
     *
     * @param id
     */
    WorldCupMatchInfo selectByPrimaryKey(Integer id);

    /**
     *  动态根据指定的条件来更新符合条件的数据库记录,world_cup_match_info
     *
     * @param record
     * @param example
     */
    int updateByExampleSelective(@Param("record") WorldCupMatchInfo record, @Param("example") WorldCupMatchInfoExample example);

    /**
     *  根据指定的条件来更新符合条件的数据库记录,world_cup_match_info
     *
     * @param record
     * @param example
     */
    int updateByExample(@Param("record") WorldCupMatchInfo record, @Param("example") WorldCupMatchInfoExample example);

    /**
     *  动态字段,根据主键来更新符合条件的数据库记录,world_cup_match_info
     *
     * @param record
     */
    int updateByPrimaryKeySelective(WorldCupMatchInfo record);

    /**
     *  根据主键来更新符合条件的数据库记录,world_cup_match_info
     *
     * @param record
     */
    int updateByPrimaryKey(WorldCupMatchInfo record);
}