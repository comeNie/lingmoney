package com.mrbt.lingmoney.mapper;

import com.mrbt.lingmoney.model.PublicBenefitActivities;
import com.mrbt.lingmoney.model.PublicBenefitActivitiesExample;
import com.mrbt.lingmoney.model.PublicBenefitActivitiesWithBLOBs;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface PublicBenefitActivitiesMapper {
    /**
     *  根据指定的条件获取数据库记录数,public_benefit_activities
     *
     * @param example
     */
    long countByExample(PublicBenefitActivitiesExample example);

    /**
     *  根据指定的条件删除数据库符合条件的记录,public_benefit_activities
     *
     * @param example
     */
    int deleteByExample(PublicBenefitActivitiesExample example);

    /**
     *  根据主键删除数据库的记录,public_benefit_activities
     *
     * @param id
     */
    int deleteByPrimaryKey(Integer id);

    /**
     *  新写入数据库记录,public_benefit_activities
     *
     * @param record
     */
    int insert(PublicBenefitActivitiesWithBLOBs record);

    /**
     *  动态字段,写入数据库记录,public_benefit_activities
     *
     * @param record
     */
    int insertSelective(PublicBenefitActivitiesWithBLOBs record);

    /**
     * ,public_benefit_activities
     *
     * @param example
     */
    List<PublicBenefitActivitiesWithBLOBs> selectByExampleWithBLOBs(PublicBenefitActivitiesExample example);

    /**
     *  根据指定的条件查询符合条件的数据库记录,public_benefit_activities
     *
     * @param example
     */
    List<PublicBenefitActivities> selectByExample(PublicBenefitActivitiesExample example);

    /**
     *  根据指定主键获取一条数据库记录,public_benefit_activities
     *
     * @param id
     */
    PublicBenefitActivitiesWithBLOBs selectByPrimaryKey(Integer id);

    /**
     *  动态根据指定的条件来更新符合条件的数据库记录,public_benefit_activities
     *
     * @param record
     * @param example
     */
    int updateByExampleSelective(@Param("record") PublicBenefitActivitiesWithBLOBs record, @Param("example") PublicBenefitActivitiesExample example);

    /**
     * ,public_benefit_activities
     *
     * @param record
     * @param example
     */
    int updateByExampleWithBLOBs(@Param("record") PublicBenefitActivitiesWithBLOBs record, @Param("example") PublicBenefitActivitiesExample example);

    /**
     *  根据指定的条件来更新符合条件的数据库记录,public_benefit_activities
     *
     * @param record
     * @param example
     */
    int updateByExample(@Param("record") PublicBenefitActivities record, @Param("example") PublicBenefitActivitiesExample example);

    /**
     *  动态字段,根据主键来更新符合条件的数据库记录,public_benefit_activities
     *
     * @param record
     */
    int updateByPrimaryKeySelective(PublicBenefitActivitiesWithBLOBs record);

    /**
     * ,public_benefit_activities
     *
     * @param record
     */
    int updateByPrimaryKeyWithBLOBs(PublicBenefitActivitiesWithBLOBs record);

    /**
     *  根据主键来更新符合条件的数据库记录,public_benefit_activities
     *
     * @param record
     */
    int updateByPrimaryKey(PublicBenefitActivities record);
}