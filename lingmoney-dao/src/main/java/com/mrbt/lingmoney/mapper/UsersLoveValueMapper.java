package com.mrbt.lingmoney.mapper;

import com.mrbt.lingmoney.model.UsersLoveValue;
import com.mrbt.lingmoney.model.UsersLoveValueExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface UsersLoveValueMapper {
    /**
     *  根据指定的条件获取数据库记录数,users_love_value
     *
     * @param example
     */
    long countByExample(UsersLoveValueExample example);

    /**
     *  根据指定的条件删除数据库符合条件的记录,users_love_value
     *
     * @param example
     */
    int deleteByExample(UsersLoveValueExample example);

    /**
     *  根据主键删除数据库的记录,users_love_value
     *
     * @param uId
     */
    int deleteByPrimaryKey(String uId);

    /**
     *  新写入数据库记录,users_love_value
     *
     * @param record
     */
    int insert(UsersLoveValue record);

    /**
     *  动态字段,写入数据库记录,users_love_value
     *
     * @param record
     */
    int insertSelective(UsersLoveValue record);

    /**
     *  根据指定的条件查询符合条件的数据库记录,users_love_value
     *
     * @param example
     */
    List<UsersLoveValue> selectByExample(UsersLoveValueExample example);

    /**
     *  根据指定主键获取一条数据库记录,users_love_value
     *
     * @param uId
     */
    UsersLoveValue selectByPrimaryKey(String uId);

    /**
     *  动态根据指定的条件来更新符合条件的数据库记录,users_love_value
     *
     * @param record
     * @param example
     */
    int updateByExampleSelective(@Param("record") UsersLoveValue record, @Param("example") UsersLoveValueExample example);

    /**
     *  根据指定的条件来更新符合条件的数据库记录,users_love_value
     *
     * @param record
     * @param example
     */
    int updateByExample(@Param("record") UsersLoveValue record, @Param("example") UsersLoveValueExample example);

    /**
     *  动态字段,根据主键来更新符合条件的数据库记录,users_love_value
     *
     * @param record
     */
    int updateByPrimaryKeySelective(UsersLoveValue record);

    /**
     *  根据主键来更新符合条件的数据库记录,users_love_value
     *
     * @param record
     */
    int updateByPrimaryKey(UsersLoveValue record);
}