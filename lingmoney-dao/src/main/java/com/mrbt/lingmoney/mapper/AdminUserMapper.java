package com.mrbt.lingmoney.mapper;

import com.mrbt.lingmoney.model.AdminUser;
import com.mrbt.lingmoney.model.AdminUserExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface AdminUserMapper {
    /**
     *  根据指定的条件获取数据库记录数,admin_user
     *
     * @param example
     */
    long countByExample(AdminUserExample example);

    /**
     *  根据指定的条件删除数据库符合条件的记录,admin_user
     *
     * @param example
     */
    int deleteByExample(AdminUserExample example);

    /**
     *  根据主键删除数据库的记录,admin_user
     *
     * @param id
     */
    int deleteByPrimaryKey(Integer id);

    /**
     *  新写入数据库记录,admin_user
     *
     * @param record
     */
    int insert(AdminUser record);

    /**
     *  动态字段,写入数据库记录,admin_user
     *
     * @param record
     */
    int insertSelective(AdminUser record);

    /**
     *  根据指定的条件查询符合条件的数据库记录,admin_user
     *
     * @param example
     */
    List<AdminUser> selectByExample(AdminUserExample example);

    /**
     *  根据指定主键获取一条数据库记录,admin_user
     *
     * @param id
     */
    AdminUser selectByPrimaryKey(Integer id);

    /**
     *  动态根据指定的条件来更新符合条件的数据库记录,admin_user
     *
     * @param record
     * @param example
     */
    int updateByExampleSelective(@Param("record") AdminUser record, @Param("example") AdminUserExample example);

    /**
     *  根据指定的条件来更新符合条件的数据库记录,admin_user
     *
     * @param record
     * @param example
     */
    int updateByExample(@Param("record") AdminUser record, @Param("example") AdminUserExample example);

    /**
     *  动态字段,根据主键来更新符合条件的数据库记录,admin_user
     *
     * @param record
     */
    int updateByPrimaryKeySelective(AdminUser record);

    /**
     *  根据主键来更新符合条件的数据库记录,admin_user
     *
     * @param record
     */
    int updateByPrimaryKey(AdminUser record);
}