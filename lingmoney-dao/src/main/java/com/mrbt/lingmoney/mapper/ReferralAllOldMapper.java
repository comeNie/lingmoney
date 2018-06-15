package com.mrbt.lingmoney.mapper;

import com.mrbt.lingmoney.model.ReferralAllOld;
import com.mrbt.lingmoney.model.ReferralAllOldExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface ReferralAllOldMapper {
    /**
     *  根据指定的条件获取数据库记录数,referral_all_old
     *
     * @param example
     */
    long countByExample(ReferralAllOldExample example);

    /**
     *  根据指定的条件删除数据库符合条件的记录,referral_all_old
     *
     * @param example
     */
    int deleteByExample(ReferralAllOldExample example);

    /**
     *  新写入数据库记录,referral_all_old
     *
     * @param record
     */
    int insert(ReferralAllOld record);

    /**
     *  动态字段,写入数据库记录,referral_all_old
     *
     * @param record
     */
    int insertSelective(ReferralAllOld record);

    /**
     *  根据指定的条件查询符合条件的数据库记录,referral_all_old
     *
     * @param example
     */
    List<ReferralAllOld> selectByExample(ReferralAllOldExample example);

    /**
     *  动态根据指定的条件来更新符合条件的数据库记录,referral_all_old
     *
     * @param record
     * @param example
     */
    int updateByExampleSelective(@Param("record") ReferralAllOld record, @Param("example") ReferralAllOldExample example);

    /**
     *  根据指定的条件来更新符合条件的数据库记录,referral_all_old
     *
     * @param record
     * @param example
     */
    int updateByExample(@Param("record") ReferralAllOld record, @Param("example") ReferralAllOldExample example);
}