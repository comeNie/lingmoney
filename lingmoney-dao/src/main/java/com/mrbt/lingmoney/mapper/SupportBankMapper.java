package com.mrbt.lingmoney.mapper;

import com.mrbt.lingmoney.model.SupportBank;
import com.mrbt.lingmoney.model.SupportBankExample;

import java.util.List;

import org.apache.ibatis.annotations.Param;

public interface SupportBankMapper {
    /**
     *  根据指定的条件获取数据库记录数,support_bank
     *
     * @param example
     */
    long countByExample(SupportBankExample example);

    /**
     *  根据指定的条件删除数据库符合条件的记录,support_bank
     *
     * @param example
     */
    int deleteByExample(SupportBankExample example);

    /**
     *  根据主键删除数据库的记录,support_bank
     *
     * @param id
     */
    int deleteByPrimaryKey(Integer id);

    /**
     *  新写入数据库记录,support_bank
     *
     * @param record
     */
    int insert(SupportBank record);

    /**
     *  动态字段,写入数据库记录,support_bank
     *
     * @param record
     */
    int insertSelective(SupportBank record);

    /**
     *  根据指定的条件查询符合条件的数据库记录,support_bank
     *
     * @param example
     */
    List<SupportBank> selectByExample(SupportBankExample example);

    /**
     *  根据指定主键获取一条数据库记录,support_bank
     *
     * @param id
     */
    SupportBank selectByPrimaryKey(Integer id);

    /**
     *  动态根据指定的条件来更新符合条件的数据库记录,support_bank
     *
     * @param record
     * @param example
     */
    int updateByExampleSelective(@Param("record") SupportBank record, @Param("example") SupportBankExample example);

    /**
     *  根据指定的条件来更新符合条件的数据库记录,support_bank
     *
     * @param record
     * @param example
     */
    int updateByExample(@Param("record") SupportBank record, @Param("example") SupportBankExample example);

    /**
     *  动态字段,根据主键来更新符合条件的数据库记录,support_bank
     *
     * @param record
     */
    int updateByPrimaryKeySelective(SupportBank record);

    /**
     *  根据主键来更新符合条件的数据库记录,support_bank
     *
     * @param record
     */
    int updateByPrimaryKey(SupportBank record);

	SupportBank selectBankInfoByNumber(String onlineBankNo);

	/**
	 * 根据银行代码查询银行信息
	 * @param bankcode
	 * @return
	 */
	SupportBank selectBankInfoByCode(String bankcode);

	/**
	 * 查询网银支付银行
	 * @return
	 */
	List<SupportBank> findOnlineBank();
}