package com.mrbt.lingmoney.mapper;

import com.mrbt.lingmoney.model.AlertPrompt;
import com.mrbt.lingmoney.model.AlertPromptExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface AlertPromptMapper {
    /**
     *  根据指定的条件获取数据库记录数,alert_prompt
     * @param example
     */
    long countByExample(AlertPromptExample example);

    /**
     *  根据指定的条件删除数据库符合条件的记录,alert_prompt
     * @param example
     */
    int deleteByExample(AlertPromptExample example);

    /**
     *  根据主键删除数据库的记录,alert_prompt
     * @param id
     */
    int deleteByPrimaryKey(String id);

    /**
     *  新写入数据库记录,alert_prompt
     * @param record
     */
    int insert(AlertPrompt record);

    /**
     *  动态字段,写入数据库记录,alert_prompt
     * @param record
     */
    int insertSelective(AlertPrompt record);

    /**
     *  根据指定的条件查询符合条件的数据库记录,alert_prompt
     * @param example
     */
    List<AlertPrompt> selectByExample(AlertPromptExample example);

    /**
     *  根据指定主键获取一条数据库记录,alert_prompt
     * @param id
     */
    AlertPrompt selectByPrimaryKey(String id);

    /**
     *  动态根据指定的条件来更新符合条件的数据库记录,alert_prompt
     * @param record
     * @param example
     */
    int updateByExampleSelective(@Param("record") AlertPrompt record, @Param("example") AlertPromptExample example);

    /**
     *  根据指定的条件来更新符合条件的数据库记录,alert_prompt
     * @param record
     * @param example
     */
    int updateByExample(@Param("record") AlertPrompt record, @Param("example") AlertPromptExample example);

    /**
     *  动态字段,根据主键来更新符合条件的数据库记录,alert_prompt
     * @param record
     */
    int updateByPrimaryKeySelective(AlertPrompt record);

    /**
     *  根据主键来更新符合条件的数据库记录,alert_prompt
     * @param record
     */
    int updateByPrimaryKey(AlertPrompt record);
}