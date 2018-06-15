package com.mrbt.lingmoney.mapper;

import com.mrbt.lingmoney.model.Users;
import com.mrbt.lingmoney.model.UsersExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface UsersMapper {
    /**
     *  根据指定的条件获取数据库记录数,users
     *
     * @param example
     */
    int countByExample(UsersExample example);

    /**
     *  根据指定的条件删除数据库符合条件的记录,users
     *
     * @param example
     */
    int deleteByExample(UsersExample example);

    /**
     *  根据主键删除数据库的记录,users
     *
     * @param id
     */
    int deleteByPrimaryKey(String id);

    /**
     *  新写入数据库记录,users
     *
     * @param record
     */
    int insert(Users record);

    /**
     *  动态字段,写入数据库记录,users
     *
     * @param record
     */
    int insertSelective(Users record);

    /**
     *  根据指定的条件查询符合条件的数据库记录,users
     *
     * @param example
     */
    List<Users> selectByExample(UsersExample example);

    /**
     *  根据指定主键获取一条数据库记录,users
     *
     * @param id
     */
    Users selectByPrimaryKey(String id);

    /**
     *  动态根据指定的条件来更新符合条件的数据库记录,users
     *
     * @param record
     * @param example
     */
    int updateByExampleSelective(@Param("record") Users record, @Param("example") UsersExample example);

    /**
     *  根据指定的条件来更新符合条件的数据库记录,users
     *
     * @param record
     * @param example
     */
    int updateByExample(@Param("record") Users record, @Param("example") UsersExample example);

    /**
     *  动态字段,根据主键来更新符合条件的数据库记录,users
     *
     * @param record
     */
    int updateByPrimaryKeySelective(Users record);

    /**
     *  根据主键来更新符合条件的数据库记录,users
     *
     * @param record
     */
    int updateByPrimaryKey(Users record);
    
    /**
	 * 查询所有用户id
	 * 
	 * @return
	 */
	List<String> selectAllUserId();
}