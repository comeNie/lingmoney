package com.mrbt.lingmoney.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.mrbt.lingmoney.model.UserFootprint;
import com.mrbt.lingmoney.model.UserFootprintExample;

public interface UserFootprintMapper {
    /**
     *  根据指定的条件获取数据库记录数,user_footprint
     *
     * @param example
     */
    int countByExample(UserFootprintExample example);

    /**
     *  根据指定的条件删除数据库符合条件的记录,user_footprint
     *
     * @param example
     */
    int deleteByExample(UserFootprintExample example);

    /**
     *  根据主键删除数据库的记录,user_footprint
     *
     * @param id
     */
    int deleteByPrimaryKey(Integer id);

    /**
     *  新写入数据库记录,user_footprint
     *
     * @param record
     */
    int insert(UserFootprint record);

    /**
     *  动态字段,写入数据库记录,user_footprint
     *
     * @param record
     */
    int insertSelective(UserFootprint record);

    /**
     *  根据指定的条件查询符合条件的数据库记录,user_footprint
     *
     * @param example
     */
    List<UserFootprint> selectByExample(UserFootprintExample example);

    /**
     *  根据指定主键获取一条数据库记录,user_footprint
     *
     * @param id
     */
    UserFootprint selectByPrimaryKey(Integer id);

    /**
     *  动态根据指定的条件来更新符合条件的数据库记录,user_footprint
     *
     * @param record
     * @param example
     */
    int updateByExampleSelective(@Param("record") UserFootprint record, @Param("example") UserFootprintExample example);

    /**
     *  根据指定的条件来更新符合条件的数据库记录,user_footprint
     *
     * @param record
     * @param example
     */
    int updateByExample(@Param("record") UserFootprint record, @Param("example") UserFootprintExample example);

    /**
     *  动态字段,根据主键来更新符合条件的数据库记录,user_footprint
     *
     * @param record
     */
    int updateByPrimaryKeySelective(UserFootprint record);

    /**
     *  根据主键来更新符合条件的数据库记录,user_footprint
     *
     * @param record
     */
    int updateByPrimaryKey(UserFootprint record);
    
    /**
     * 根据用户信息查询用户足迹
     *@Description  
     *@param map
     *@return  uid 用户id, times 操作时间, tel 手机号, account 登录名, step 所到节点名 
     */
	List<Map<String, Object>> listByUserInfo(Map<String, Object> map);

	int countListByUserInfo(Map<String, Object> map);
}