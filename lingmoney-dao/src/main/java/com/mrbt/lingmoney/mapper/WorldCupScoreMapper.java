package com.mrbt.lingmoney.mapper;

import com.mrbt.lingmoney.model.WorldCupScore;
import com.mrbt.lingmoney.model.WorldCupScoreExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface WorldCupScoreMapper {
    /**
     *  根据指定的条件获取数据库记录数,world_cup_score
     *
     * @param example
     */
    long countByExample(WorldCupScoreExample example);

    /**
     *  根据指定的条件删除数据库符合条件的记录,world_cup_score
     *
     * @param example
     */
    int deleteByExample(WorldCupScoreExample example);

    /**
     *  根据主键删除数据库的记录,world_cup_score
     *
     * @param id
     */
    int deleteByPrimaryKey(Integer id);

    /**
     *  新写入数据库记录,world_cup_score
     *
     * @param record
     */
    int insert(WorldCupScore record);

    /**
     *  动态字段,写入数据库记录,world_cup_score
     *
     * @param record
     */
    int insertSelective(WorldCupScore record);

    /**
     *  根据指定的条件查询符合条件的数据库记录,world_cup_score
     *
     * @param example
     */
    List<WorldCupScore> selectByExample(WorldCupScoreExample example);

    /**
     *  根据指定主键获取一条数据库记录,world_cup_score
     *
     * @param id
     */
    WorldCupScore selectByPrimaryKey(Integer id);

    /**
     *  动态根据指定的条件来更新符合条件的数据库记录,world_cup_score
     *
     * @param record
     * @param example
     */
    int updateByExampleSelective(@Param("record") WorldCupScore record, @Param("example") WorldCupScoreExample example);

    /**
     *  根据指定的条件来更新符合条件的数据库记录,world_cup_score
     *
     * @param record
     * @param example
     */
    int updateByExample(@Param("record") WorldCupScore record, @Param("example") WorldCupScoreExample example);

    /**
     *  动态字段,根据主键来更新符合条件的数据库记录,world_cup_score
     *
     * @param record
     */
    int updateByPrimaryKeySelective(WorldCupScore record);

    /**
     *  根据主键来更新符合条件的数据库记录,world_cup_score
     *
     * @param record
     */
    int updateByPrimaryKey(WorldCupScore record);
}