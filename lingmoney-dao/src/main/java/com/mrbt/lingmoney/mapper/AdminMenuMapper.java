package com.mrbt.lingmoney.mapper;

import com.mrbt.lingmoney.model.AdminMenu;
import com.mrbt.lingmoney.model.AdminMenuExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

public interface AdminMenuMapper {
    /**
     *  根据指定的条件获取数据库记录数,admin_menu
     *
     * @param example
     */
    long countByExample(AdminMenuExample example);

    /**
     *  根据指定的条件删除数据库符合条件的记录,admin_menu
     *
     * @param example
     */
    int deleteByExample(AdminMenuExample example);

    /**
     *  根据主键删除数据库的记录,admin_menu
     *
     * @param id
     */
    int deleteByPrimaryKey(Integer id);

    /**
     *  新写入数据库记录,admin_menu
     *
     * @param record
     */
    int insert(AdminMenu record);

    /**
     *  动态字段,写入数据库记录,admin_menu
     *
     * @param record
     */
    int insertSelective(AdminMenu record);

    /**
     *  根据指定的条件查询符合条件的数据库记录,admin_menu
     *
     * @param example
     */
    List<AdminMenu> selectByExample(AdminMenuExample example);

    /**
     *  根据指定主键获取一条数据库记录,admin_menu
     *
     * @param id
     */
    AdminMenu selectByPrimaryKey(Integer id);

    /**
     *  动态根据指定的条件来更新符合条件的数据库记录,admin_menu
     *
     * @param record
     * @param example
     */
    int updateByExampleSelective(@Param("record") AdminMenu record, @Param("example") AdminMenuExample example);

    /**
     *  根据指定的条件来更新符合条件的数据库记录,admin_menu
     *
     * @param record
     * @param example
     */
    int updateByExample(@Param("record") AdminMenu record, @Param("example") AdminMenuExample example);

    /**
     *  动态字段,根据主键来更新符合条件的数据库记录,admin_menu
     *
     * @param record
     */
    int updateByPrimaryKeySelective(AdminMenu record);

    /**
     *  根据主键来更新符合条件的数据库记录,admin_menu
     *
     * @param record
     */
    int updateByPrimaryKey(AdminMenu record);
    
    /**
	 * 手动生成，根据roleId查找树
	 * 
	 * @param roleId
	 * @return
	 */
	@Select("select menu.* from admin_menu menu ,admin_role_menu role where menu.status=1 and menu.id=role.menu_id and role.role_id=#{roleId} order by id,seq")
	List<AdminMenu> selectByRole(int roleId);
}