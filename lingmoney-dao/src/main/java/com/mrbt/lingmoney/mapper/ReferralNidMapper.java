package com.mrbt.lingmoney.mapper;

import com.mrbt.lingmoney.model.ReferralNid;
import com.mrbt.lingmoney.model.ReferralNidExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface ReferralNidMapper {
    /**
     *  根据指定的条件获取数据库记录数,referral_nid
     *
     * @param example
     */
    long countByExample(ReferralNidExample example);

    /**
     *  根据指定的条件删除数据库符合条件的记录,referral_nid
     *
     * @param example
     */
    int deleteByExample(ReferralNidExample example);

    /**
     *  新写入数据库记录,referral_nid
     *
     * @param record
     */
    int insert(ReferralNid record);

    /**
     *  动态字段,写入数据库记录,referral_nid
     *
     * @param record
     */
    int insertSelective(ReferralNid record);

    /**
     *  根据指定的条件查询符合条件的数据库记录,referral_nid
     *
     * @param example
     */
    List<ReferralNid> selectByExample(ReferralNidExample example);

    /**
     *  动态根据指定的条件来更新符合条件的数据库记录,referral_nid
     *
     * @param record
     * @param example
     */
    int updateByExampleSelective(@Param("record") ReferralNid record, @Param("example") ReferralNidExample example);

    /**
     *  根据指定的条件来更新符合条件的数据库记录,referral_nid
     *
     * @param record
     * @param example
     */
    int updateByExample(@Param("record") ReferralNid record, @Param("example") ReferralNidExample example);
}