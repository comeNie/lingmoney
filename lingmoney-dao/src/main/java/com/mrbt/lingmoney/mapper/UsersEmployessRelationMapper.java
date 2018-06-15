package com.mrbt.lingmoney.mapper;

import com.mrbt.lingmoney.model.UsersEmployessRelation;
import com.mrbt.lingmoney.model.UsersEmployessRelationExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface UsersEmployessRelationMapper {
    /**
     *  根据指定的条件获取数据库记录数,users_employess_relation
     *
     * @param example
     */
    long countByExample(UsersEmployessRelationExample example);

    /**
     *  根据指定的条件删除数据库符合条件的记录,users_employess_relation
     *
     * @param example
     */
    int deleteByExample(UsersEmployessRelationExample example);

    /**
     *  根据主键删除数据库的记录,users_employess_relation
     *
     * @param id
     */
    int deleteByPrimaryKey(Integer id);

    /**
     *  新写入数据库记录,users_employess_relation
     *
     * @param record
     */
    int insert(UsersEmployessRelation record);

    /**
     *  动态字段,写入数据库记录,users_employess_relation
     *
     * @param record
     */
    int insertSelective(UsersEmployessRelation record);

    /**
     *  根据指定的条件查询符合条件的数据库记录,users_employess_relation
     *
     * @param example
     */
    List<UsersEmployessRelation> selectByExample(UsersEmployessRelationExample example);

    /**
     *  根据指定主键获取一条数据库记录,users_employess_relation
     *
     * @param id
     */
    UsersEmployessRelation selectByPrimaryKey(Integer id);

    /**
     *  动态根据指定的条件来更新符合条件的数据库记录,users_employess_relation
     *
     * @param record
     * @param example
     */
    int updateByExampleSelective(@Param("record") UsersEmployessRelation record, @Param("example") UsersEmployessRelationExample example);

    /**
     *  根据指定的条件来更新符合条件的数据库记录,users_employess_relation
     *
     * @param record
     * @param example
     */
    int updateByExample(@Param("record") UsersEmployessRelation record, @Param("example") UsersEmployessRelationExample example);

    /**
     *  动态字段,根据主键来更新符合条件的数据库记录,users_employess_relation
     *
     * @param record
     */
    int updateByPrimaryKeySelective(UsersEmployessRelation record);

    /**
     *  根据主键来更新符合条件的数据库记录,users_employess_relation
     *
     * @param record
     */
    int updateByPrimaryKey(UsersEmployessRelation record);
}