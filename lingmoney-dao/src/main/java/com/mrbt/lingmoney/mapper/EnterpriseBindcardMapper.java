package com.mrbt.lingmoney.mapper;

import com.mrbt.lingmoney.model.EnterpriseBindcard;
import com.mrbt.lingmoney.model.EnterpriseBindcardExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface EnterpriseBindcardMapper {
    /**
     *  根据指定的条件获取数据库记录数,enterprise_bindcard
     *
     * @param example
     */
    int countByExample(EnterpriseBindcardExample example);

    /**
     *  根据指定的条件删除数据库符合条件的记录,enterprise_bindcard
     *
     * @param example
     */
    int deleteByExample(EnterpriseBindcardExample example);

    /**
     *  根据主键删除数据库的记录,enterprise_bindcard
     *
     * @param id
     */
    int deleteByPrimaryKey(String id);

    /**
     *  新写入数据库记录,enterprise_bindcard
     *
     * @param record
     */
    int insert(EnterpriseBindcard record);

    /**
     *  动态字段,写入数据库记录,enterprise_bindcard
     *
     * @param record
     */
    int insertSelective(EnterpriseBindcard record);

    /**
     *  根据指定的条件查询符合条件的数据库记录,enterprise_bindcard
     *
     * @param example
     */
    List<EnterpriseBindcard> selectByExample(EnterpriseBindcardExample example);

    /**
     *  根据指定主键获取一条数据库记录,enterprise_bindcard
     *
     * @param id
     */
    EnterpriseBindcard selectByPrimaryKey(String id);

    /**
     *  动态根据指定的条件来更新符合条件的数据库记录,enterprise_bindcard
     *
     * @param record
     * @param example
     */
    int updateByExampleSelective(@Param("record") EnterpriseBindcard record, @Param("example") EnterpriseBindcardExample example);

    /**
     *  根据指定的条件来更新符合条件的数据库记录,enterprise_bindcard
     *
     * @param record
     * @param example
     */
    int updateByExample(@Param("record") EnterpriseBindcard record, @Param("example") EnterpriseBindcardExample example);

    /**
     *  动态字段,根据主键来更新符合条件的数据库记录,enterprise_bindcard
     *
     * @param record
     */
    int updateByPrimaryKeySelective(EnterpriseBindcard record);

    /**
     *  根据主键来更新符合条件的数据库记录,enterprise_bindcard
     *
     * @param record
     */
    int updateByPrimaryKey(EnterpriseBindcard record);
}