package com.mrbt.lingmoney.mapper;

import com.mrbt.lingmoney.model.UnfreezeFlow;
import com.mrbt.lingmoney.model.UnfreezeFlowExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface UnfreezeFlowMapper {
    /**
     *  根据指定的条件获取数据库记录数,unfreeze_flow
     *
     * @param example
     */
    long countByExample(UnfreezeFlowExample example);

    /**
     *  根据指定的条件删除数据库符合条件的记录,unfreeze_flow
     *
     * @param example
     */
    int deleteByExample(UnfreezeFlowExample example);

    /**
     *  根据主键删除数据库的记录,unfreeze_flow
     *
     * @param id
     */
    int deleteByPrimaryKey(Integer id);

    /**
     *  新写入数据库记录,unfreeze_flow
     *
     * @param record
     */
    int insert(UnfreezeFlow record);

    /**
     *  动态字段,写入数据库记录,unfreeze_flow
     *
     * @param record
     */
    int insertSelective(UnfreezeFlow record);

    /**
     *  根据指定的条件查询符合条件的数据库记录,unfreeze_flow
     *
     * @param example
     */
    List<UnfreezeFlow> selectByExample(UnfreezeFlowExample example);

    /**
     *  根据指定主键获取一条数据库记录,unfreeze_flow
     *
     * @param id
     */
    UnfreezeFlow selectByPrimaryKey(Integer id);

    /**
     *  动态根据指定的条件来更新符合条件的数据库记录,unfreeze_flow
     *
     * @param record
     * @param example
     */
    int updateByExampleSelective(@Param("record") UnfreezeFlow record, @Param("example") UnfreezeFlowExample example);

    /**
     *  根据指定的条件来更新符合条件的数据库记录,unfreeze_flow
     *
     * @param record
     * @param example
     */
    int updateByExample(@Param("record") UnfreezeFlow record, @Param("example") UnfreezeFlowExample example);

    /**
     *  动态字段,根据主键来更新符合条件的数据库记录,unfreeze_flow
     *
     * @param record
     */
    int updateByPrimaryKeySelective(UnfreezeFlow record);

    /**
     *  根据主键来更新符合条件的数据库记录,unfreeze_flow
     *
     * @param record
     */
    int updateByPrimaryKey(UnfreezeFlow record);
}