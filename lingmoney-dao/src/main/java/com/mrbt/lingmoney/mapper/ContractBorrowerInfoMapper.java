package com.mrbt.lingmoney.mapper;

import com.mrbt.lingmoney.model.ContractBorrowerInfo;
import com.mrbt.lingmoney.model.ContractBorrowerInfoExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface ContractBorrowerInfoMapper {
    /**
     *  根据指定的条件获取数据库记录数,contract_borrower_info
     *
     * @param example
     */
    long countByExample(ContractBorrowerInfoExample example);

    /**
     *  根据指定的条件删除数据库符合条件的记录,contract_borrower_info
     *
     * @param example
     */
    int deleteByExample(ContractBorrowerInfoExample example);

    /**
     *  新写入数据库记录,contract_borrower_info
     *
     * @param record
     */
    int insert(ContractBorrowerInfo record);

    /**
     *  动态字段,写入数据库记录,contract_borrower_info
     *
     * @param record
     */
    int insertSelective(ContractBorrowerInfo record);

    /**
     *  根据指定的条件查询符合条件的数据库记录,contract_borrower_info
     *
     * @param example
     */
    List<ContractBorrowerInfo> selectByExample(ContractBorrowerInfoExample example);

    /**
     *  动态根据指定的条件来更新符合条件的数据库记录,contract_borrower_info
     *
     * @param record
     * @param example
     */
    int updateByExampleSelective(@Param("record") ContractBorrowerInfo record, @Param("example") ContractBorrowerInfoExample example);

    /**
     *  根据指定的条件来更新符合条件的数据库记录,contract_borrower_info
     *
     * @param record
     * @param example
     */
    int updateByExample(@Param("record") ContractBorrowerInfo record, @Param("example") ContractBorrowerInfoExample example);
}