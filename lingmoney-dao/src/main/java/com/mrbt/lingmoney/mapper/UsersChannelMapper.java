package com.mrbt.lingmoney.mapper;

import com.mrbt.lingmoney.model.UsersChannel;
import com.mrbt.lingmoney.model.UsersChannelExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface UsersChannelMapper {
    /**
     *  根据指定的条件获取数据库记录数,users_channel
     * @param example
     */
    long countByExample(UsersChannelExample example);

    /**
     *  根据指定的条件删除数据库符合条件的记录,users_channel
     * @param example
     */
    int deleteByExample(UsersChannelExample example);

    /**
     *  根据主键删除数据库的记录,users_channel
     * @param id
     */
    int deleteByPrimaryKey(Integer id);

    /**
     *  新写入数据库记录,users_channel
     * @param record
     */
    int insert(UsersChannel record);

    /**
     *  动态字段,写入数据库记录,users_channel
     * @param record
     */
    int insertSelective(UsersChannel record);

    /**
     *  根据指定的条件查询符合条件的数据库记录,users_channel
     * @param example
     */
    List<UsersChannel> selectByExample(UsersChannelExample example);

    /**
     *  根据指定主键获取一条数据库记录,users_channel
     * @param id
     */
    UsersChannel selectByPrimaryKey(Integer id);

    /**
     *  动态根据指定的条件来更新符合条件的数据库记录,users_channel
     * @param record
     * @param example
     */
    int updateByExampleSelective(@Param("record") UsersChannel record, @Param("example") UsersChannelExample example);

    /**
     *  根据指定的条件来更新符合条件的数据库记录,users_channel
     * @param record
     * @param example
     */
    int updateByExample(@Param("record") UsersChannel record, @Param("example") UsersChannelExample example);

    /**
     *  动态字段,根据主键来更新符合条件的数据库记录,users_channel
     * @param record
     */
    int updateByPrimaryKeySelective(UsersChannel record);

    /**
     *  根据主键来更新符合条件的数据库记录,users_channel
     * @param record
     */
    int updateByPrimaryKey(UsersChannel record);
}