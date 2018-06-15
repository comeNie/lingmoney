package com.mrbt.lingmoney.mapper;

import com.mrbt.lingmoney.model.UsersFinancing;
import com.mrbt.lingmoney.model.UsersFinancingExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface UsersFinancingMapper {
    /**
     *  根据指定的条件获取数据库记录数,users_financing
     *
     * @param example
     */
    long countByExample(UsersFinancingExample example);

    /**
     *  根据指定的条件删除数据库符合条件的记录,users_financing
     *
     * @param example
     */
    int deleteByExample(UsersFinancingExample example);

    /**
     *  根据主键删除数据库的记录,users_financing
     *
     * @param id
     */
    int deleteByPrimaryKey(Integer id);

    /**
     *  新写入数据库记录,users_financing
     *
     * @param record
     */
    int insert(UsersFinancing record);

    /**
     *  动态字段,写入数据库记录,users_financing
     *
     * @param record
     */
    int insertSelective(UsersFinancing record);

    /**
     *  根据指定的条件查询符合条件的数据库记录,users_financing
     *
     * @param example
     */
    List<UsersFinancing> selectByExample(UsersFinancingExample example);

    /**
     *  根据指定主键获取一条数据库记录,users_financing
     *
     * @param id
     */
    UsersFinancing selectByPrimaryKey(Integer id);

    /**
     *  动态根据指定的条件来更新符合条件的数据库记录,users_financing
     *
     * @param record
     * @param example
     */
    int updateByExampleSelective(@Param("record") UsersFinancing record, @Param("example") UsersFinancingExample example);

    /**
     *  根据指定的条件来更新符合条件的数据库记录,users_financing
     *
     * @param record
     * @param example
     */
    int updateByExample(@Param("record") UsersFinancing record, @Param("example") UsersFinancingExample example);

    /**
     *  动态字段,根据主键来更新符合条件的数据库记录,users_financing
     *
     * @param record
     */
    int updateByPrimaryKeySelective(UsersFinancing record);

    /**
     *  根据主键来更新符合条件的数据库记录,users_financing
     *
     * @param record
     */
    int updateByPrimaryKey(UsersFinancing record);
}