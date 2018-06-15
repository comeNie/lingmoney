package com.mrbt.lingmoney.mapper;

import com.mrbt.lingmoney.model.SynUserData;
import com.mrbt.lingmoney.model.SynUserDataExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface SynUserDataMapper {
	/**
     *  根据指定的条件获取数据库记录数,syn_user_data
     *
     * @param example
     */
    long countByExample(SynUserDataExample example);

    /**
     *  根据指定的条件删除数据库符合条件的记录,syn_user_data
     *
     * @param example
     */
    int deleteByExample(SynUserDataExample example);

    /**
     *  新写入数据库记录,syn_user_data
     *
     * @param record
     */
    int insert(SynUserData record);

    /**
     *  动态字段,写入数据库记录,syn_user_data
     *
     * @param record
     */
    int insertSelective(SynUserData record);

    /**
     *  根据指定的条件查询符合条件的数据库记录,syn_user_data
     *
     * @param example
     */
    List<SynUserData> selectByExample(SynUserDataExample example);

    /**
     *  动态根据指定的条件来更新符合条件的数据库记录,syn_user_data
     *
     * @param record
     * @param example
     */
    int updateByExampleSelective(@Param("record") SynUserData record, @Param("example") SynUserDataExample example);

    /**
     *  根据指定的条件来更新符合条件的数据库记录,syn_user_data
     *
     * @param record
     * @param example
     */
    int updateByExample(@Param("record") SynUserData record, @Param("example") SynUserDataExample example);
}