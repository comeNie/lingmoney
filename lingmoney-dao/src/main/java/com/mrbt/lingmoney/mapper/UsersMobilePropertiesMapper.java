package com.mrbt.lingmoney.mapper;

import com.mrbt.lingmoney.model.UsersMobileProperties;
import com.mrbt.lingmoney.model.UsersMobilePropertiesExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface UsersMobilePropertiesMapper {
    /**
     *  根据指定的条件获取数据库记录数,users_mobile_properties
     *
     * @param example
     */
    int countByExample(UsersMobilePropertiesExample example);

    /**
     *  根据指定的条件删除数据库符合条件的记录,users_mobile_properties
     *
     * @param example
     */
    int deleteByExample(UsersMobilePropertiesExample example);

    /**
     *  根据主键删除数据库的记录,users_mobile_properties
     *
     * @param uId
     */
    int deleteByPrimaryKey(String uId);

    /**
     *  新写入数据库记录,users_mobile_properties
     *
     * @param record
     */
    int insert(UsersMobileProperties record);

    /**
     *  动态字段,写入数据库记录,users_mobile_properties
     *
     * @param record
     */
    int insertSelective(UsersMobileProperties record);

    /**
     *  根据指定的条件查询符合条件的数据库记录,users_mobile_properties
     *
     * @param example
     */
    List<UsersMobileProperties> selectByExample(UsersMobilePropertiesExample example);

    /**
     *  根据指定主键获取一条数据库记录,users_mobile_properties
     *
     * @param uId
     */
    UsersMobileProperties selectByPrimaryKey(String uId);

    /**
     *  动态根据指定的条件来更新符合条件的数据库记录,users_mobile_properties
     *
     * @param record
     * @param example
     */
    int updateByExampleSelective(@Param("record") UsersMobileProperties record, @Param("example") UsersMobilePropertiesExample example);

    /**
     *  根据指定的条件来更新符合条件的数据库记录,users_mobile_properties
     *
     * @param record
     * @param example
     */
    int updateByExample(@Param("record") UsersMobileProperties record, @Param("example") UsersMobilePropertiesExample example);

    /**
     *  动态字段,根据主键来更新符合条件的数据库记录,users_mobile_properties
     *
     * @param record
     */
    int updateByPrimaryKeySelective(UsersMobileProperties record);

    /**
     *  根据主键来更新符合条件的数据库记录,users_mobile_properties
     *
     * @param record
     */
    int updateByPrimaryKey(UsersMobileProperties record);
}