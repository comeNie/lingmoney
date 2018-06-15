package com.mrbt.lingmoney.mapper;

import com.mrbt.lingmoney.model.PaymentCardLimit;
import com.mrbt.lingmoney.model.PaymentCardLimitExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface PaymentCardLimitMapper {
    /**
     *  根据指定的条件获取数据库记录数,payment_card_limit
     *
     * @param example
     */
    long countByExample(PaymentCardLimitExample example);

    /**
     *  根据指定的条件删除数据库符合条件的记录,payment_card_limit
     *
     * @param example
     */
    int deleteByExample(PaymentCardLimitExample example);

    /**
     *  根据主键删除数据库的记录,payment_card_limit
     *
     * @param id
     */
    int deleteByPrimaryKey(Integer id);

    /**
     *  新写入数据库记录,payment_card_limit
     *
     * @param record
     */
    int insert(PaymentCardLimit record);

    /**
     *  动态字段,写入数据库记录,payment_card_limit
     *
     * @param record
     */
    int insertSelective(PaymentCardLimit record);

    /**
     *  根据指定的条件查询符合条件的数据库记录,payment_card_limit
     *
     * @param example
     */
    List<PaymentCardLimit> selectByExample(PaymentCardLimitExample example);

    /**
     *  根据指定主键获取一条数据库记录,payment_card_limit
     *
     * @param id
     */
    PaymentCardLimit selectByPrimaryKey(Integer id);

    /**
     *  动态根据指定的条件来更新符合条件的数据库记录,payment_card_limit
     *
     * @param record
     * @param example
     */
    int updateByExampleSelective(@Param("record") PaymentCardLimit record, @Param("example") PaymentCardLimitExample example);

    /**
     *  根据指定的条件来更新符合条件的数据库记录,payment_card_limit
     *
     * @param record
     * @param example
     */
    int updateByExample(@Param("record") PaymentCardLimit record, @Param("example") PaymentCardLimitExample example);

    /**
     *  动态字段,根据主键来更新符合条件的数据库记录,payment_card_limit
     *
     * @param record
     */
    int updateByPrimaryKeySelective(PaymentCardLimit record);

    /**
     *  根据主键来更新符合条件的数据库记录,payment_card_limit
     *
     * @param record
     */
    int updateByPrimaryKey(PaymentCardLimit record);
}