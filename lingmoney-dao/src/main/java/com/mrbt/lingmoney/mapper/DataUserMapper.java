package com.mrbt.lingmoney.mapper;

import com.mrbt.lingmoney.model.DataUser;
import com.mrbt.lingmoney.model.DataUserExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface DataUserMapper {
    /**
     *  根据指定的条件获取数据库记录数,data_user
     *
     * @param example
     */
    long countByExample(DataUserExample example);

    /**
     *  根据指定的条件删除数据库符合条件的记录,data_user
     *
     * @param example
     */
    int deleteByExample(DataUserExample example);

    /**
     *  根据主键删除数据库的记录,data_user
     *
     * @param uId
     */
    int deleteByPrimaryKey(String uId);

    /**
     *  新写入数据库记录,data_user
     *
     * @param record
     */
    int insert(DataUser record);

    /**
     *  动态字段,写入数据库记录,data_user
     *
     * @param record
     */
    int insertSelective(DataUser record);

    /**
     *  根据指定的条件查询符合条件的数据库记录,data_user
     *
     * @param example
     */
    List<DataUser> selectByExample(DataUserExample example);

    /**
     *  根据指定主键获取一条数据库记录,data_user
     *
     * @param uId
     */
    DataUser selectByPrimaryKey(String uId);

    /**
     *  动态根据指定的条件来更新符合条件的数据库记录,data_user
     *
     * @param record
     * @param example
     */
    int updateByExampleSelective(@Param("record") DataUser record, @Param("example") DataUserExample example);

    /**
     *  根据指定的条件来更新符合条件的数据库记录,data_user
     *
     * @param record
     * @param example
     */
    int updateByExample(@Param("record") DataUser record, @Param("example") DataUserExample example);

    /**
     *  动态字段,根据主键来更新符合条件的数据库记录,data_user
     *
     * @param record
     */
    int updateByPrimaryKeySelective(DataUser record);

    /**
     *  根据主键来更新符合条件的数据库记录,data_user
     *
     * @param record
     */
    int updateByPrimaryKey(DataUser record);
}