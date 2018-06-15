package com.mrbt.lingmoney.mapper;

import com.mrbt.lingmoney.model.NewUserReward;
import com.mrbt.lingmoney.model.NewUserRewardExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface NewUserRewardMapper {
    /**
     *  根据指定的条件获取数据库记录数,new_user_reward
     * @param example
     */
    long countByExample(NewUserRewardExample example);

    /**
     *  根据指定的条件删除数据库符合条件的记录,new_user_reward
     * @param example
     */
    int deleteByExample(NewUserRewardExample example);

    /**
     *  根据主键删除数据库的记录,new_user_reward
     * @param id
     */
    int deleteByPrimaryKey(Integer id);

    /**
     *  新写入数据库记录,new_user_reward
     * @param record
     */
    int insert(NewUserReward record);

    /**
     *  动态字段,写入数据库记录,new_user_reward
     * @param record
     */
    int insertSelective(NewUserReward record);

    /**
     *  根据指定的条件查询符合条件的数据库记录,new_user_reward
     * @param example
     */
    List<NewUserReward> selectByExample(NewUserRewardExample example);

    /**
     *  根据指定主键获取一条数据库记录,new_user_reward
     * @param id
     */
    NewUserReward selectByPrimaryKey(Integer id);

    /**
     *  动态根据指定的条件来更新符合条件的数据库记录,new_user_reward
     * @param record
     * @param example
     */
    int updateByExampleSelective(@Param("record") NewUserReward record, @Param("example") NewUserRewardExample example);

    /**
     *  根据指定的条件来更新符合条件的数据库记录,new_user_reward
     * @param record
     * @param example
     */
    int updateByExample(@Param("record") NewUserReward record, @Param("example") NewUserRewardExample example);

    /**
     *  动态字段,根据主键来更新符合条件的数据库记录,new_user_reward
     * @param record
     */
    int updateByPrimaryKeySelective(NewUserReward record);

    /**
     *  根据主键来更新符合条件的数据库记录,new_user_reward
     * @param record
     */
    int updateByPrimaryKey(NewUserReward record);
}