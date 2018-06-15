package com.mrbt.lingmoney.mapper;

import com.mrbt.lingmoney.model.LoanCompany;
import com.mrbt.lingmoney.model.LoanCompanyExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface LoanCompanyMapper {
    /**
     *  根据指定的条件获取数据库记录数,loan_company
     *
     * @param example
     */
    long countByExample(LoanCompanyExample example);

    /**
     *  根据指定的条件删除数据库符合条件的记录,loan_company
     *
     * @param example
     */
    int deleteByExample(LoanCompanyExample example);

    /**
     *  根据主键删除数据库的记录,loan_company
     *
     * @param id
     */
    int deleteByPrimaryKey(Integer id);

    /**
     *  新写入数据库记录,loan_company
     *
     * @param record
     */
    int insert(LoanCompany record);

    /**
     *  动态字段,写入数据库记录,loan_company
     *
     * @param record
     */
    int insertSelective(LoanCompany record);

    /**
     *  根据指定的条件查询符合条件的数据库记录,loan_company
     *
     * @param example
     */
    List<LoanCompany> selectByExample(LoanCompanyExample example);

    /**
     *  根据指定主键获取一条数据库记录,loan_company
     *
     * @param id
     */
    LoanCompany selectByPrimaryKey(Integer id);

    /**
     *  动态根据指定的条件来更新符合条件的数据库记录,loan_company
     *
     * @param record
     * @param example
     */
    int updateByExampleSelective(@Param("record") LoanCompany record, @Param("example") LoanCompanyExample example);

    /**
     *  根据指定的条件来更新符合条件的数据库记录,loan_company
     *
     * @param record
     * @param example
     */
    int updateByExample(@Param("record") LoanCompany record, @Param("example") LoanCompanyExample example);

    /**
     *  动态字段,根据主键来更新符合条件的数据库记录,loan_company
     *
     * @param record
     */
    int updateByPrimaryKeySelective(LoanCompany record);

    /**
     *  根据主键来更新符合条件的数据库记录,loan_company
     *
     * @param record
     */
    int updateByPrimaryKey(LoanCompany record);
}