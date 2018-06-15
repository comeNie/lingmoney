package com.mrbt.lingmoney.mapper;

import com.mrbt.lingmoney.model.AdminMessage;
import com.mrbt.lingmoney.model.AdminMessageExample;
import com.mrbt.lingmoney.model.AdminMessageWithBLOBs;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface AdminMessageMapper {
    /**
     *  根据指定的条件获取数据库记录数,admin_message
     *
     * @param example
     */
    long countByExample(AdminMessageExample example);

    /**
     *  根据指定的条件删除数据库符合条件的记录,admin_message
     *
     * @param example
     */
    int deleteByExample(AdminMessageExample example);

    /**
     *  根据主键删除数据库的记录,admin_message
     *
     * @param id
     */
    int deleteByPrimaryKey(Integer id);

    /**
     *  新写入数据库记录,admin_message
     *
     * @param record
     */
    int insert(AdminMessageWithBLOBs record);

    /**
     *  动态字段,写入数据库记录,admin_message
     *
     * @param record
     */
    int insertSelective(AdminMessageWithBLOBs record);

    /**
     * ,admin_message
     *
     * @param example
     */
    List<AdminMessageWithBLOBs> selectByExampleWithBLOBs(AdminMessageExample example);

    /**
     *  根据指定的条件查询符合条件的数据库记录,admin_message
     *
     * @param example
     */
    List<AdminMessage> selectByExample(AdminMessageExample example);

    /**
     *  根据指定主键获取一条数据库记录,admin_message
     *
     * @param id
     */
    AdminMessageWithBLOBs selectByPrimaryKey(Integer id);

    /**
     *  动态根据指定的条件来更新符合条件的数据库记录,admin_message
     *
     * @param record
     * @param example
     */
    int updateByExampleSelective(@Param("record") AdminMessageWithBLOBs record, @Param("example") AdminMessageExample example);

    /**
     * ,admin_message
     *
     * @param record
     * @param example
     */
    int updateByExampleWithBLOBs(@Param("record") AdminMessageWithBLOBs record, @Param("example") AdminMessageExample example);

    /**
     *  根据指定的条件来更新符合条件的数据库记录,admin_message
     *
     * @param record
     * @param example
     */
    int updateByExample(@Param("record") AdminMessage record, @Param("example") AdminMessageExample example);

    /**
     *  动态字段,根据主键来更新符合条件的数据库记录,admin_message
     *
     * @param record
     */
    int updateByPrimaryKeySelective(AdminMessageWithBLOBs record);

    /**
     * ,admin_message
     *
     * @param record
     */
    int updateByPrimaryKeyWithBLOBs(AdminMessageWithBLOBs record);

    /**
     *  根据主键来更新符合条件的数据库记录,admin_message
     *
     * @param record
     */
    int updateByPrimaryKey(AdminMessage record);
}