package com.mrbt.lingmoney.mapper;

import com.mrbt.lingmoney.model.InfoClientFeedback;
import com.mrbt.lingmoney.model.InfoClientFeedbackExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface InfoClientFeedbackMapper {
    /**
     *  根据指定的条件获取数据库记录数,info_client_feedback
     *
     * @param example
     */
    long countByExample(InfoClientFeedbackExample example);

    /**
     *  根据指定的条件删除数据库符合条件的记录,info_client_feedback
     *
     * @param example
     */
    int deleteByExample(InfoClientFeedbackExample example);

    /**
     *  根据主键删除数据库的记录,info_client_feedback
     *
     * @param id
     */
    int deleteByPrimaryKey(Integer id);

    /**
     *  新写入数据库记录,info_client_feedback
     *
     * @param record
     */
    int insert(InfoClientFeedback record);

    /**
     *  动态字段,写入数据库记录,info_client_feedback
     *
     * @param record
     */
    int insertSelective(InfoClientFeedback record);

    /**
     * ,info_client_feedback
     *
     * @param example
     */
    List<InfoClientFeedback> selectByExampleWithBLOBs(InfoClientFeedbackExample example);

    /**
     *  根据指定的条件查询符合条件的数据库记录,info_client_feedback
     *
     * @param example
     */
    List<InfoClientFeedback> selectByExample(InfoClientFeedbackExample example);

    /**
     *  根据指定主键获取一条数据库记录,info_client_feedback
     *
     * @param id
     */
    InfoClientFeedback selectByPrimaryKey(Integer id);

    /**
     *  动态根据指定的条件来更新符合条件的数据库记录,info_client_feedback
     *
     * @param record
     * @param example
     */
    int updateByExampleSelective(@Param("record") InfoClientFeedback record, @Param("example") InfoClientFeedbackExample example);

    /**
     * ,info_client_feedback
     *
     * @param record
     * @param example
     */
    int updateByExampleWithBLOBs(@Param("record") InfoClientFeedback record, @Param("example") InfoClientFeedbackExample example);

    /**
     *  根据指定的条件来更新符合条件的数据库记录,info_client_feedback
     *
     * @param record
     * @param example
     */
    int updateByExample(@Param("record") InfoClientFeedback record, @Param("example") InfoClientFeedbackExample example);

    /**
     *  动态字段,根据主键来更新符合条件的数据库记录,info_client_feedback
     *
     * @param record
     */
    int updateByPrimaryKeySelective(InfoClientFeedback record);

    /**
     * ,info_client_feedback
     *
     * @param record
     */
    int updateByPrimaryKeyWithBLOBs(InfoClientFeedback record);

    /**
     *  根据主键来更新符合条件的数据库记录,info_client_feedback
     *
     * @param record
     */
    int updateByPrimaryKey(InfoClientFeedback record);
}