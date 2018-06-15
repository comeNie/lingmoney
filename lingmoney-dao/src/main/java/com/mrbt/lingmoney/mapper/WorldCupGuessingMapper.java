package com.mrbt.lingmoney.mapper;

import com.mrbt.lingmoney.model.WorldCupGuessing;
import com.mrbt.lingmoney.model.WorldCupGuessingExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface WorldCupGuessingMapper {
    /**
     *  根据指定的条件获取数据库记录数,world_cup_guessing
     *
     * @param example
     */
    long countByExample(WorldCupGuessingExample example);

    /**
     *  根据指定的条件删除数据库符合条件的记录,world_cup_guessing
     *
     * @param example
     */
    int deleteByExample(WorldCupGuessingExample example);

    /**
     *  根据主键删除数据库的记录,world_cup_guessing
     *
     * @param id
     */
    int deleteByPrimaryKey(Integer id);

    /**
     *  新写入数据库记录,world_cup_guessing
     *
     * @param record
     */
    int insert(WorldCupGuessing record);

    /**
     *  动态字段,写入数据库记录,world_cup_guessing
     *
     * @param record
     */
    int insertSelective(WorldCupGuessing record);

    /**
     *  根据指定的条件查询符合条件的数据库记录,world_cup_guessing
     *
     * @param example
     */
    List<WorldCupGuessing> selectByExample(WorldCupGuessingExample example);

    /**
     *  根据指定主键获取一条数据库记录,world_cup_guessing
     *
     * @param id
     */
    WorldCupGuessing selectByPrimaryKey(Integer id);

    /**
     *  动态根据指定的条件来更新符合条件的数据库记录,world_cup_guessing
     *
     * @param record
     * @param example
     */
    int updateByExampleSelective(@Param("record") WorldCupGuessing record, @Param("example") WorldCupGuessingExample example);

    /**
     *  根据指定的条件来更新符合条件的数据库记录,world_cup_guessing
     *
     * @param record
     * @param example
     */
    int updateByExample(@Param("record") WorldCupGuessing record, @Param("example") WorldCupGuessingExample example);

    /**
     *  动态字段,根据主键来更新符合条件的数据库记录,world_cup_guessing
     *
     * @param record
     */
    int updateByPrimaryKeySelective(WorldCupGuessing record);

    /**
     *  根据主键来更新符合条件的数据库记录,world_cup_guessing
     *
     * @param record
     */
    int updateByPrimaryKey(WorldCupGuessing record);
}