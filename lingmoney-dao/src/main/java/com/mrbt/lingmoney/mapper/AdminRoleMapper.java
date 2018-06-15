package com.mrbt.lingmoney.mapper;

import com.mrbt.lingmoney.model.AdminRole;
import com.mrbt.lingmoney.model.AdminRoleExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface AdminRoleMapper {
    /**
     *  根据指定的条件获取数据库记录数,admin_role
     *
     * @param example
     */
    long countByExample(AdminRoleExample example);

    /**
     *  根据指定的条件删除数据库符合条件的记录,admin_role
     *
     * @param example
     */
    int deleteByExample(AdminRoleExample example);

    /**
     *  根据主键删除数据库的记录,admin_role
     *
     * @param id
     */
    int deleteByPrimaryKey(Integer id);

    /**
     *  新写入数据库记录,admin_role
     *
     * @param record
     */
    int insert(AdminRole record);

    /**
     *  动态字段,写入数据库记录,admin_role
     *
     * @param record
     */
    int insertSelective(AdminRole record);

    /**
     *  根据指定的条件查询符合条件的数据库记录,admin_role
     *
     * @param example
     */
    List<AdminRole> selectByExample(AdminRoleExample example);

    /**
     *  根据指定主键获取一条数据库记录,admin_role
     *
     * @param id
     */
    AdminRole selectByPrimaryKey(Integer id);

    /**
     *  动态根据指定的条件来更新符合条件的数据库记录,admin_role
     *
     * @param record
     * @param example
     */
    int updateByExampleSelective(@Param("record") AdminRole record, @Param("example") AdminRoleExample example);

    /**
     *  根据指定的条件来更新符合条件的数据库记录,admin_role
     *
     * @param record
     * @param example
     */
    int updateByExample(@Param("record") AdminRole record, @Param("example") AdminRoleExample example);

    /**
     *  动态字段,根据主键来更新符合条件的数据库记录,admin_role
     *
     * @param record
     */
    int updateByPrimaryKeySelective(AdminRole record);

    /**
     *  根据主键来更新符合条件的数据库记录,admin_role
     *
     * @param record
     */
    int updateByPrimaryKey(AdminRole record);
}