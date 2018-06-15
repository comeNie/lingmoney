package com.mrbt.lingmoney.mapper;

import com.mrbt.lingmoney.model.WorldCupProblem;
import com.mrbt.lingmoney.model.WorldCupProblemExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface WorldCupProblemMapper {
    /**
     *  根据指定的条件获取数据库记录数,world_cup_problem
     *
     * @param example
     */
    long countByExample(WorldCupProblemExample example);

    /**
     *  根据指定的条件删除数据库符合条件的记录,world_cup_problem
     *
     * @param example
     */
    int deleteByExample(WorldCupProblemExample example);

    /**
     *  根据主键删除数据库的记录,world_cup_problem
     *
     * @param id
     */
    int deleteByPrimaryKey(Integer id);

    /**
     *  新写入数据库记录,world_cup_problem
     *
     * @param record
     */
    int insert(WorldCupProblem record);

    /**
     *  动态字段,写入数据库记录,world_cup_problem
     *
     * @param record
     */
    int insertSelective(WorldCupProblem record);

    /**
     *  根据指定的条件查询符合条件的数据库记录,world_cup_problem
     *
     * @param example
     */
    List<WorldCupProblem> selectByExample(WorldCupProblemExample example);

    /**
     *  根据指定主键获取一条数据库记录,world_cup_problem
     *
     * @param id
     */
    WorldCupProblem selectByPrimaryKey(Integer id);

    /**
     *  动态根据指定的条件来更新符合条件的数据库记录,world_cup_problem
     *
     * @param record
     * @param example
     */
    int updateByExampleSelective(@Param("record") WorldCupProblem record, @Param("example") WorldCupProblemExample example);

    /**
     *  根据指定的条件来更新符合条件的数据库记录,world_cup_problem
     *
     * @param record
     * @param example
     */
    int updateByExample(@Param("record") WorldCupProblem record, @Param("example") WorldCupProblemExample example);

    /**
     *  动态字段,根据主键来更新符合条件的数据库记录,world_cup_problem
     *
     * @param record
     */
    int updateByPrimaryKeySelective(WorldCupProblem record);

    /**
     *  根据主键来更新符合条件的数据库记录,world_cup_problem
     *
     * @param record
     */
    int updateByPrimaryKey(WorldCupProblem record);
}