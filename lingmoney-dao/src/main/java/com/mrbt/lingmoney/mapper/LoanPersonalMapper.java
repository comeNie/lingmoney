package com.mrbt.lingmoney.mapper;

import com.mrbt.lingmoney.model.LoanPersonal;
import com.mrbt.lingmoney.model.LoanPersonalExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface LoanPersonalMapper {
    /**
     *  根据指定的条件获取数据库记录数,loan_personal
     *
     * @param example
     */
    long countByExample(LoanPersonalExample example);

    /**
     *  根据指定的条件删除数据库符合条件的记录,loan_personal
     *
     * @param example
     */
    int deleteByExample(LoanPersonalExample example);

    /**
     *  根据主键删除数据库的记录,loan_personal
     *
     * @param id
     */
    int deleteByPrimaryKey(Integer id);

    /**
     *  新写入数据库记录,loan_personal
     *
     * @param record
     */
    int insert(LoanPersonal record);

    /**
     *  动态字段,写入数据库记录,loan_personal
     *
     * @param record
     */
    int insertSelective(LoanPersonal record);

    /**
     *  根据指定的条件查询符合条件的数据库记录,loan_personal
     *
     * @param example
     */
    List<LoanPersonal> selectByExample(LoanPersonalExample example);

    /**
     *  根据指定主键获取一条数据库记录,loan_personal
     *
     * @param id
     */
    LoanPersonal selectByPrimaryKey(Integer id);

    /**
     *  动态根据指定的条件来更新符合条件的数据库记录,loan_personal
     *
     * @param record
     * @param example
     */
    int updateByExampleSelective(@Param("record") LoanPersonal record, @Param("example") LoanPersonalExample example);

    /**
     *  根据指定的条件来更新符合条件的数据库记录,loan_personal
     *
     * @param record
     * @param example
     */
    int updateByExample(@Param("record") LoanPersonal record, @Param("example") LoanPersonalExample example);

    /**
     *  动态字段,根据主键来更新符合条件的数据库记录,loan_personal
     *
     * @param record
     */
    int updateByPrimaryKeySelective(LoanPersonal record);

    /**
     *  根据主键来更新符合条件的数据库记录,loan_personal
     *
     * @param record
     */
    int updateByPrimaryKey(LoanPersonal record);
}