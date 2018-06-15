package com.mrbt.lingmoney.mapper;

import com.mrbt.lingmoney.model.ReferralAll;
import com.mrbt.lingmoney.model.ReferralAllExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface ReferralAllMapper {
    /**
     *  根据指定的条件获取数据库记录数,referral_all
     *
     * @param example
     */
    long countByExample(ReferralAllExample example);

    /**
     *  根据指定的条件删除数据库符合条件的记录,referral_all
     *
     * @param example
     */
    int deleteByExample(ReferralAllExample example);

    /**
     *  新写入数据库记录,referral_all
     *
     * @param record
     */
    int insert(ReferralAll record);

    /**
     *  动态字段,写入数据库记录,referral_all
     *
     * @param record
     */
    int insertSelective(ReferralAll record);

    /**
     *  根据指定的条件查询符合条件的数据库记录,referral_all
     *
     * @param example
     */
    List<ReferralAll> selectByExample(ReferralAllExample example);

    /**
     *  动态根据指定的条件来更新符合条件的数据库记录,referral_all
     *
     * @param record
     * @param example
     */
    int updateByExampleSelective(@Param("record") ReferralAll record, @Param("example") ReferralAllExample example);

    /**
     *  根据指定的条件来更新符合条件的数据库记录,referral_all
     *
     * @param record
     * @param example
     */
    int updateByExample(@Param("record") ReferralAll record, @Param("example") ReferralAllExample example);
}