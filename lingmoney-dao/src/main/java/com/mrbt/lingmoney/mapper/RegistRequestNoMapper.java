package com.mrbt.lingmoney.mapper;

import com.mrbt.lingmoney.model.RegistRequestNo;
import com.mrbt.lingmoney.model.RegistRequestNoExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface RegistRequestNoMapper {
    /**
     *  根据指定的条件获取数据库记录数,regist_request_no
     *
     * @param example
     */
    long countByExample(RegistRequestNoExample example);

    /**
     *  根据指定的条件删除数据库符合条件的记录,regist_request_no
     *
     * @param example
     */
    int deleteByExample(RegistRequestNoExample example);

    /**
     *  根据主键删除数据库的记录,regist_request_no
     *
     * @param id
     */
    int deleteByPrimaryKey(Integer id);

    /**
     *  新写入数据库记录,regist_request_no
     *
     * @param record
     */
    int insert(RegistRequestNo record);

    /**
     *  动态字段,写入数据库记录,regist_request_no
     *
     * @param record
     */
    int insertSelective(RegistRequestNo record);

    /**
     *  根据指定的条件查询符合条件的数据库记录,regist_request_no
     *
     * @param example
     */
    List<RegistRequestNo> selectByExample(RegistRequestNoExample example);

    /**
     *  根据指定主键获取一条数据库记录,regist_request_no
     *
     * @param id
     */
    RegistRequestNo selectByPrimaryKey(Integer id);

    /**
     *  动态根据指定的条件来更新符合条件的数据库记录,regist_request_no
     *
     * @param record
     * @param example
     */
    int updateByExampleSelective(@Param("record") RegistRequestNo record, @Param("example") RegistRequestNoExample example);

    /**
     *  根据指定的条件来更新符合条件的数据库记录,regist_request_no
     *
     * @param record
     * @param example
     */
    int updateByExample(@Param("record") RegistRequestNo record, @Param("example") RegistRequestNoExample example);

    /**
     *  动态字段,根据主键来更新符合条件的数据库记录,regist_request_no
     *
     * @param record
     */
    int updateByPrimaryKeySelective(RegistRequestNo record);

    /**
     *  根据主键来更新符合条件的数据库记录,regist_request_no
     *
     * @param record
     */
    int updateByPrimaryKey(RegistRequestNo record);
}