package com.mrbt.lingmoney.mapper;

import com.mrbt.lingmoney.model.UserYearFinancialmoney;
import com.mrbt.lingmoney.model.UserYearFinancialmoneyExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface UserYearFinancialmoneyMapper {
    /**
     *  根据指定的条件获取数据库记录数,user_year_financialmoney
     *
     * @param example
     */
    long countByExample(UserYearFinancialmoneyExample example);

    /**
     *  根据指定的条件删除数据库符合条件的记录,user_year_financialmoney
     *
     * @param example
     */
    int deleteByExample(UserYearFinancialmoneyExample example);

    /**
     *  新写入数据库记录,user_year_financialmoney
     *
     * @param record
     */
    int insert(UserYearFinancialmoney record);

    /**
     *  动态字段,写入数据库记录,user_year_financialmoney
     *
     * @param record
     */
    int insertSelective(UserYearFinancialmoney record);

    /**
     *  根据指定的条件查询符合条件的数据库记录,user_year_financialmoney
     *
     * @param example
     */
    List<UserYearFinancialmoney> selectByExample(UserYearFinancialmoneyExample example);

    /**
     *  动态根据指定的条件来更新符合条件的数据库记录,user_year_financialmoney
     *
     * @param record
     * @param example
     */
    int updateByExampleSelective(@Param("record") UserYearFinancialmoney record, @Param("example") UserYearFinancialmoneyExample example);

    /**
     *  根据指定的条件来更新符合条件的数据库记录,user_year_financialmoney
     *
     * @param record
     * @param example
     */
    int updateByExample(@Param("record") UserYearFinancialmoney record, @Param("example") UserYearFinancialmoneyExample example);
}