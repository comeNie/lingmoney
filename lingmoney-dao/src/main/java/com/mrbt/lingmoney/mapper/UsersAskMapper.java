package com.mrbt.lingmoney.mapper;

import com.mrbt.lingmoney.model.UsersAsk;
import com.mrbt.lingmoney.model.UsersAskExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface UsersAskMapper {
    /**
     *  根据指定的条件获取数据库记录数,users_ask
     * @param example
     */
    long countByExample(UsersAskExample example);

    /**
     *  根据指定的条件删除数据库符合条件的记录,users_ask
     * @param example
     */
    int deleteByExample(UsersAskExample example);

    /**
     *  根据主键删除数据库的记录,users_ask
     * @param id
     */
    int deleteByPrimaryKey(Integer id);

    /**
     *  新写入数据库记录,users_ask
     * @param record
     */
    int insert(UsersAsk record);

    /**
     *  动态字段,写入数据库记录,users_ask
     * @param record
     */
    int insertSelective(UsersAsk record);

    /**
     * ,users_ask
     * @param example
     */
    List<UsersAsk> selectByExampleWithBLOBs(UsersAskExample example);

    /**
     *  根据指定的条件查询符合条件的数据库记录,users_ask
     * @param example
     */
    List<UsersAsk> selectByExample(UsersAskExample example);

    /**
     *  根据指定主键获取一条数据库记录,users_ask
     * @param id
     */
    UsersAsk selectByPrimaryKey(Integer id);

    /**
     *  动态根据指定的条件来更新符合条件的数据库记录,users_ask
     * @param record
     * @param example
     */
    int updateByExampleSelective(@Param("record") UsersAsk record, @Param("example") UsersAskExample example);

    /**
     * ,users_ask
     * @param record
     * @param example
     */
    int updateByExampleWithBLOBs(@Param("record") UsersAsk record, @Param("example") UsersAskExample example);

    /**
     *  根据指定的条件来更新符合条件的数据库记录,users_ask
     * @param record
     * @param example
     */
    int updateByExample(@Param("record") UsersAsk record, @Param("example") UsersAskExample example);

    /**
     *  动态字段,根据主键来更新符合条件的数据库记录,users_ask
     * @param record
     */
    int updateByPrimaryKeySelective(UsersAsk record);

    /**
     * ,users_ask
     * @param record
     */
    int updateByPrimaryKeyWithBLOBs(UsersAsk record);

    /**
     *  根据主键来更新符合条件的数据库记录,users_ask
     * @param record
     */
    int updateByPrimaryKey(UsersAsk record);
}