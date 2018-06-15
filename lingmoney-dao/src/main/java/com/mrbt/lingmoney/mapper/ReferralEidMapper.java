package com.mrbt.lingmoney.mapper;

import com.mrbt.lingmoney.model.ReferralEid;
import com.mrbt.lingmoney.model.ReferralEidExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface ReferralEidMapper {
    /**
     *  根据指定的条件获取数据库记录数,referral_eid
     *
     * @param example
     */
    long countByExample(ReferralEidExample example);

    /**
     *  根据指定的条件删除数据库符合条件的记录,referral_eid
     *
     * @param example
     */
    int deleteByExample(ReferralEidExample example);

    /**
     *  新写入数据库记录,referral_eid
     *
     * @param record
     */
    int insert(ReferralEid record);

    /**
     *  动态字段,写入数据库记录,referral_eid
     *
     * @param record
     */
    int insertSelective(ReferralEid record);

    /**
     *  根据指定的条件查询符合条件的数据库记录,referral_eid
     *
     * @param example
     */
    List<ReferralEid> selectByExample(ReferralEidExample example);

    /**
     *  动态根据指定的条件来更新符合条件的数据库记录,referral_eid
     *
     * @param record
     * @param example
     */
    int updateByExampleSelective(@Param("record") ReferralEid record, @Param("example") ReferralEidExample example);

    /**
     *  根据指定的条件来更新符合条件的数据库记录,referral_eid
     *
     * @param record
     * @param example
     */
    int updateByExample(@Param("record") ReferralEid record, @Param("example") ReferralEidExample example);
}