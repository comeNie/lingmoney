package com.mrbt.lingmoney.mapper;

import com.mrbt.lingmoney.model.ContractProductInfo;
import com.mrbt.lingmoney.model.ContractProductInfoExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface ContractProductInfoMapper {
    /**
     *  根据指定的条件获取数据库记录数,contract_product_info
     *
     * @param example
     */
    long countByExample(ContractProductInfoExample example);

    /**
     *  根据指定的条件删除数据库符合条件的记录,contract_product_info
     *
     * @param example
     */
    int deleteByExample(ContractProductInfoExample example);

    /**
     *  新写入数据库记录,contract_product_info
     *
     * @param record
     */
    int insert(ContractProductInfo record);

    /**
     *  动态字段,写入数据库记录,contract_product_info
     *
     * @param record
     */
    int insertSelective(ContractProductInfo record);

    /**
     *  根据指定的条件查询符合条件的数据库记录,contract_product_info
     *
     * @param example
     */
    List<ContractProductInfo> selectByExample(ContractProductInfoExample example);

    /**
     *  动态根据指定的条件来更新符合条件的数据库记录,contract_product_info
     *
     * @param record
     * @param example
     */
    int updateByExampleSelective(@Param("record") ContractProductInfo record, @Param("example") ContractProductInfoExample example);

    /**
     *  根据指定的条件来更新符合条件的数据库记录,contract_product_info
     *
     * @param record
     * @param example
     */
    int updateByExample(@Param("record") ContractProductInfo record, @Param("example") ContractProductInfoExample example);
}