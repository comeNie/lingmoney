package com.mrbt.lingmoney.mapper;

import com.mrbt.lingmoney.model.AppPushMsg;
import com.mrbt.lingmoney.model.AppPushMsgExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface AppPushMsgMapper {
    /**
     *  根据指定的条件获取数据库记录数,app_push_msg
     * @param example
     */
    long countByExample(AppPushMsgExample example);

    /**
     *  根据指定的条件删除数据库符合条件的记录,app_push_msg
     * @param example
     */
    int deleteByExample(AppPushMsgExample example);

    /**
     *  根据主键删除数据库的记录,app_push_msg
     * @param id
     */
    int deleteByPrimaryKey(Integer id);

    /**
     *  新写入数据库记录,app_push_msg
     * @param record
     */
    int insert(AppPushMsg record);

    /**
     *  动态字段,写入数据库记录,app_push_msg
     * @param record
     */
    int insertSelective(AppPushMsg record);

    /**
     *  根据指定的条件查询符合条件的数据库记录,app_push_msg
     * @param example
     */
    List<AppPushMsg> selectByExample(AppPushMsgExample example);

    /**
     *  根据指定主键获取一条数据库记录,app_push_msg
     * @param id
     */
    AppPushMsg selectByPrimaryKey(Integer id);

    /**
     *  动态根据指定的条件来更新符合条件的数据库记录,app_push_msg
     * @param record
     * @param example
     */
    int updateByExampleSelective(@Param("record") AppPushMsg record, @Param("example") AppPushMsgExample example);

    /**
     *  根据指定的条件来更新符合条件的数据库记录,app_push_msg
     * @param record
     * @param example
     */
    int updateByExample(@Param("record") AppPushMsg record, @Param("example") AppPushMsgExample example);

    /**
     *  动态字段,根据主键来更新符合条件的数据库记录,app_push_msg
     * @param record
     */
    int updateByPrimaryKeySelective(AppPushMsg record);

    /**
     *  根据主键来更新符合条件的数据库记录,app_push_msg
     * @param record
     */
    int updateByPrimaryKey(AppPushMsg record);
}