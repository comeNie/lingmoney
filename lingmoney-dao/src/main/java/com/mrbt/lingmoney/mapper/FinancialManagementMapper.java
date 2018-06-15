package com.mrbt.lingmoney.mapper;

import com.mrbt.lingmoney.model.FinancialManagement;
import com.mrbt.lingmoney.model.FinancialManagementExample;

import java.util.List;

import org.apache.ibatis.annotations.Param;

public interface FinancialManagementMapper {
    /**
     *  根据指定的条件获取数据库记录数,financial_management
     *
     * @param example
     */
    long countByExample(FinancialManagementExample example);

    /**
     *  根据指定的条件删除数据库符合条件的记录,financial_management
     *
     * @param example
     */
    int deleteByExample(FinancialManagementExample example);

    /**
     *  根据主键删除数据库的记录,financial_management
     *
     * @param id
     */
    int deleteByPrimaryKey(Integer id);

    /**
     *  新写入数据库记录,financial_management
     *
     * @param record
     */
    int insert(FinancialManagement record);

    /**
     *  动态字段,写入数据库记录,financial_management
     *
     * @param record
     */
    int insertSelective(FinancialManagement record);

    /**
     * ,financial_management
     *
     * @param example
     */
    List<FinancialManagement> selectByExampleWithBLOBs(FinancialManagementExample example);

    /**
     *  根据指定的条件查询符合条件的数据库记录,financial_management
     *
     * @param example
     */
    List<FinancialManagement> selectByExample(FinancialManagementExample example);

    /**
     *  根据指定主键获取一条数据库记录,financial_management
     *
     * @param id
     */
    FinancialManagement selectByPrimaryKey(Integer id);

    /**
     *  动态根据指定的条件来更新符合条件的数据库记录,financial_management
     *
     * @param record
     * @param example
     */
    int updateByExampleSelective(@Param("record") FinancialManagement record, @Param("example") FinancialManagementExample example);

    /**
     * ,financial_management
     *
     * @param record
     * @param example
     */
    int updateByExampleWithBLOBs(@Param("record") FinancialManagement record, @Param("example") FinancialManagementExample example);

    /**
     *  根据指定的条件来更新符合条件的数据库记录,financial_management
     *
     * @param record
     * @param example
     */
    int updateByExample(@Param("record") FinancialManagement record, @Param("example") FinancialManagementExample example);

    /**
     *  动态字段,根据主键来更新符合条件的数据库记录,financial_management
     *
     * @param record
     */
    int updateByPrimaryKeySelective(FinancialManagement record);

    /**
     * ,financial_management
     *
     * @param record
     */
    int updateByPrimaryKeyWithBLOBs(FinancialManagement record);

    /**
     *  根据主键来更新符合条件的数据库记录,financial_management
     *
     * @param record
     */
    int updateByPrimaryKey(FinancialManagement record);

    /**
     * 查询所有id
     * @return
     */
	List<Integer> listFinancialManagementId();
}