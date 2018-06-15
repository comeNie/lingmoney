package com.mrbt.lingmoney.mapper;

import com.mrbt.lingmoney.model.HxAutoRepaymentApplyInfo;
import com.mrbt.lingmoney.model.HxAutoRepaymentApplyInfoExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface HxAutoRepaymentApplyInfoMapper {
    /**
     *  根据指定的条件获取数据库记录数,hx_auto_repayment_apply_info
     * @param example
     */
    long countByExample(HxAutoRepaymentApplyInfoExample example);

    /**
     *  根据指定的条件删除数据库符合条件的记录,hx_auto_repayment_apply_info
     * @param example
     */
    int deleteByExample(HxAutoRepaymentApplyInfoExample example);

    /**
     *  根据主键删除数据库的记录,hx_auto_repayment_apply_info
     * @param id
     */
    int deleteByPrimaryKey(String id);

    /**
     *  新写入数据库记录,hx_auto_repayment_apply_info
     * @param record
     */
    int insert(HxAutoRepaymentApplyInfo record);

    /**
     *  动态字段,写入数据库记录,hx_auto_repayment_apply_info
     * @param record
     */
    int insertSelective(HxAutoRepaymentApplyInfo record);

    /**
     *  根据指定的条件查询符合条件的数据库记录,hx_auto_repayment_apply_info
     * @param example
     */
    List<HxAutoRepaymentApplyInfo> selectByExample(HxAutoRepaymentApplyInfoExample example);

    /**
     *  根据指定主键获取一条数据库记录,hx_auto_repayment_apply_info
     * @param id
     */
    HxAutoRepaymentApplyInfo selectByPrimaryKey(String id);

    /**
     *  动态根据指定的条件来更新符合条件的数据库记录,hx_auto_repayment_apply_info
     * @param record
     * @param example
     */
    int updateByExampleSelective(@Param("record") HxAutoRepaymentApplyInfo record, @Param("example") HxAutoRepaymentApplyInfoExample example);

    /**
     *  根据指定的条件来更新符合条件的数据库记录,hx_auto_repayment_apply_info
     * @param record
     * @param example
     */
    int updateByExample(@Param("record") HxAutoRepaymentApplyInfo record, @Param("example") HxAutoRepaymentApplyInfoExample example);

    /**
     *  动态字段,根据主键来更新符合条件的数据库记录,hx_auto_repayment_apply_info
     * @param record
     */
    int updateByPrimaryKeySelective(HxAutoRepaymentApplyInfo record);

    /**
     *  根据主键来更新符合条件的数据库记录,hx_auto_repayment_apply_info
     * @param record
     */
    int updateByPrimaryKey(HxAutoRepaymentApplyInfo record);
}