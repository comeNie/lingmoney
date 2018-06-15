package com.mrbt.lingmoney.mapper;

import com.mrbt.lingmoney.model.UsersDreamInfo;
import com.mrbt.lingmoney.model.UsersDreamInfoExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface UsersDreamInfoMapper {
    /**
     *  根据指定的条件获取数据库记录数,users_dream_info
     *
     * @param example
     */
    long countByExample(UsersDreamInfoExample example);

    /**
     *  根据指定的条件删除数据库符合条件的记录,users_dream_info
     *
     * @param example
     */
    int deleteByExample(UsersDreamInfoExample example);

    /**
     *  根据主键删除数据库的记录,users_dream_info
     *
     * @param id
     */
    int deleteByPrimaryKey(Integer id);

    /**
     *  新写入数据库记录,users_dream_info
     *
     * @param record
     */
    int insert(UsersDreamInfo record);

    /**
     *  动态字段,写入数据库记录,users_dream_info
     *
     * @param record
     */
    int insertSelective(UsersDreamInfo record);

    /**
     *  根据指定的条件查询符合条件的数据库记录,users_dream_info
     *
     * @param example
     */
    List<UsersDreamInfo> selectByExample(UsersDreamInfoExample example);

    /**
     *  根据指定主键获取一条数据库记录,users_dream_info
     *
     * @param id
     */
    UsersDreamInfo selectByPrimaryKey(Integer id);

    /**
     *  动态根据指定的条件来更新符合条件的数据库记录,users_dream_info
     *
     * @param record
     * @param example
     */
    int updateByExampleSelective(@Param("record") UsersDreamInfo record, @Param("example") UsersDreamInfoExample example);

    /**
     *  根据指定的条件来更新符合条件的数据库记录,users_dream_info
     *
     * @param record
     * @param example
     */
    int updateByExample(@Param("record") UsersDreamInfo record, @Param("example") UsersDreamInfoExample example);

    /**
     *  动态字段,根据主键来更新符合条件的数据库记录,users_dream_info
     *
     * @param record
     */
    int updateByPrimaryKeySelective(UsersDreamInfo record);

    /**
     *  根据主键来更新符合条件的数据库记录,users_dream_info
     *
     * @param record
     */
    int updateByPrimaryKey(UsersDreamInfo record);
}