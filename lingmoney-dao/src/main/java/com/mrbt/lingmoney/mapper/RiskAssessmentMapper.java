package com.mrbt.lingmoney.mapper;

import com.mrbt.lingmoney.model.RiskAssessment;
import com.mrbt.lingmoney.model.RiskAssessmentExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface RiskAssessmentMapper {
    /**
     *  根据指定的条件获取数据库记录数,risk_assessment
     *
     * @param example
     */
    int countByExample(RiskAssessmentExample example);

    /**
     *  根据指定的条件删除数据库符合条件的记录,risk_assessment
     *
     * @param example
     */
    int deleteByExample(RiskAssessmentExample example);

    /**
     *  根据主键删除数据库的记录,risk_assessment
     *
     * @param id
     */
    int deleteByPrimaryKey(Integer id);

    /**
     *  新写入数据库记录,risk_assessment
     *
     * @param record
     */
    int insert(RiskAssessment record);

    /**
     *  动态字段,写入数据库记录,risk_assessment
     *
     * @param record
     */
    int insertSelective(RiskAssessment record);

    /**
     *  根据指定的条件查询符合条件的数据库记录,risk_assessment
     *
     * @param example
     */
    List<RiskAssessment> selectByExample(RiskAssessmentExample example);

    /**
     *  根据指定主键获取一条数据库记录,risk_assessment
     *
     * @param id
     */
    RiskAssessment selectByPrimaryKey(Integer id);

    /**
     *  动态根据指定的条件来更新符合条件的数据库记录,risk_assessment
     *
     * @param record
     * @param example
     */
    int updateByExampleSelective(@Param("record") RiskAssessment record, @Param("example") RiskAssessmentExample example);

    /**
     *  根据指定的条件来更新符合条件的数据库记录,risk_assessment
     *
     * @param record
     * @param example
     */
    int updateByExample(@Param("record") RiskAssessment record, @Param("example") RiskAssessmentExample example);

    /**
     *  动态字段,根据主键来更新符合条件的数据库记录,risk_assessment
     *
     * @param record
     */
    int updateByPrimaryKeySelective(RiskAssessment record);

    /**
     *  根据主键来更新符合条件的数据库记录,risk_assessment
     *
     * @param record
     */
    int updateByPrimaryKey(RiskAssessment record);
}