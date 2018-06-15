package com.mrbt.lingmoney.mapper;

import com.mrbt.lingmoney.model.UsersLoveLog;
import com.mrbt.lingmoney.model.UsersLoveLogExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface UsersLoveLogMapper {
    /**
     *  根据指定的条件获取数据库记录数,users_love_log
     *
     * @param example
     */
    long countByExample(UsersLoveLogExample example);

    /**
     *  根据指定的条件删除数据库符合条件的记录,users_love_log
     *
     * @param example
     */
    int deleteByExample(UsersLoveLogExample example);

    /**
     *  根据主键删除数据库的记录,users_love_log
     *
     * @param id
     */
    int deleteByPrimaryKey(Integer id);

    /**
     *  新写入数据库记录,users_love_log
     *
     * @param record
     */
    int insert(UsersLoveLog record);

    /**
     *  动态字段,写入数据库记录,users_love_log
     *
     * @param record
     */
    int insertSelective(UsersLoveLog record);

    /**
     *  根据指定的条件查询符合条件的数据库记录,users_love_log
     *
     * @param example
     */
    List<UsersLoveLog> selectByExample(UsersLoveLogExample example);

    /**
     *  根据指定主键获取一条数据库记录,users_love_log
     *
     * @param id
     */
    UsersLoveLog selectByPrimaryKey(Integer id);

    /**
     *  动态根据指定的条件来更新符合条件的数据库记录,users_love_log
     *
     * @param record
     * @param example
     */
    int updateByExampleSelective(@Param("record") UsersLoveLog record, @Param("example") UsersLoveLogExample example);

    /**
     *  根据指定的条件来更新符合条件的数据库记录,users_love_log
     *
     * @param record
     * @param example
     */
    int updateByExample(@Param("record") UsersLoveLog record, @Param("example") UsersLoveLogExample example);

    /**
     *  动态字段,根据主键来更新符合条件的数据库记录,users_love_log
     *
     * @param record
     */
    int updateByPrimaryKeySelective(UsersLoveLog record);

    /**
     *  根据主键来更新符合条件的数据库记录,users_love_log
     *
     * @param record
     */
    int updateByPrimaryKey(UsersLoveLog record);
}