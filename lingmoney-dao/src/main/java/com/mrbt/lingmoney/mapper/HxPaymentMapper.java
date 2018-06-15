package com.mrbt.lingmoney.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.mrbt.lingmoney.model.HxPayment;
import com.mrbt.lingmoney.model.HxPaymentExample;

public interface HxPaymentMapper {
    /**
     *  根据指定的条件获取数据库记录数,hx_payment
     *
     * @param example
     */
    int countByExample(HxPaymentExample example);

    /**
     *  根据指定的条件删除数据库符合条件的记录,hx_payment
     *
     * @param example
     */
    int deleteByExample(HxPaymentExample example);

    /**
     *  根据主键删除数据库的记录,hx_payment
     *
     * @param id
     */
    int deleteByPrimaryKey(String id);

    /**
     *  新写入数据库记录,hx_payment
     *
     * @param record
     */
    int insert(HxPayment record);

    /**
     *  动态字段,写入数据库记录,hx_payment
     *
     * @param record
     */
    int insertSelective(HxPayment record);

    /**
     *  根据指定的条件查询符合条件的数据库记录,hx_payment
     *
     * @param example
     */
    List<HxPayment> selectByExample(HxPaymentExample example);

    /**
     *  根据指定主键获取一条数据库记录,hx_payment
     *
     * @param id
     */
    HxPayment selectByPrimaryKey(String id);

    /**
     *  动态根据指定的条件来更新符合条件的数据库记录,hx_payment
     *
     * @param record
     * @param example
     */
    int updateByExampleSelective(@Param("record") HxPayment record, @Param("example") HxPaymentExample example);

    /**
     *  根据指定的条件来更新符合条件的数据库记录,hx_payment
     *
     * @param record
     * @param example
     */
    int updateByExample(@Param("record") HxPayment record, @Param("example") HxPaymentExample example);

    /**
     *  动态字段,根据主键来更新符合条件的数据库记录,hx_payment
     *
     * @param record
     */
    int updateByPrimaryKeySelective(HxPayment record);

    /**
     *  根据主键来更新符合条件的数据库记录,hx_payment
     *
     * @param record
     */
    int updateByPrimaryKey(HxPayment record);

	/**
	 * 
	 * @description 根据借款编号查询有效的还款金额和还款状态
	 * @author syb
	 * @date 2017年9月4日 下午3:25:10
	 * @version 1.0
	 * @param loanNo
	 * @return amount 还款金额 state 还款状态 1-还款成功 2- 还款中 3-还款失败 4-超时
	 *
	 */
	List<Map<String, Object>> queryRepaymentAmountAndStatusByLoanNo(String loanNo);

    /**
     * 根据BW id查询借款人姓名，E账号
     * 
     * @author yiban
     * @date 2018年3月29日 上午10:59:03
     * @version 1.0
     * @param bwId
     * @return
     *
     */
    Map<String, String> getAcnoAcnameByBwid(String bwId);
}