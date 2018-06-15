package com.mrbt.lingmoney.mapper;

import com.mrbt.lingmoney.model.DataSynWenyingFlow;
import com.mrbt.lingmoney.model.DataSynWenyingFlowExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface DataSynWenyingFlowMapper {
    /**
     *  根据指定的条件获取数据库记录数,data_syn_wenying_flow
     *
     * @param example
     */
    long countByExample(DataSynWenyingFlowExample example);

    /**
     *  根据指定的条件删除数据库符合条件的记录,data_syn_wenying_flow
     *
     * @param example
     */
    int deleteByExample(DataSynWenyingFlowExample example);

    /**
     *  新写入数据库记录,data_syn_wenying_flow
     *
     * @param record
     */
    int insert(DataSynWenyingFlow record);

    /**
     *  动态字段,写入数据库记录,data_syn_wenying_flow
     *
     * @param record
     */
    int insertSelective(DataSynWenyingFlow record);

    /**
     *  根据指定的条件查询符合条件的数据库记录,data_syn_wenying_flow
     *
     * @param example
     */
    List<DataSynWenyingFlow> selectByExample(DataSynWenyingFlowExample example);

    /**
     *  动态根据指定的条件来更新符合条件的数据库记录,data_syn_wenying_flow
     *
     * @param record
     * @param example
     */
    int updateByExampleSelective(@Param("record") DataSynWenyingFlow record, @Param("example") DataSynWenyingFlowExample example);

    /**
     *  根据指定的条件来更新符合条件的数据库记录,data_syn_wenying_flow
     *
     * @param record
     * @param example
     */
    int updateByExample(@Param("record") DataSynWenyingFlow record, @Param("example") DataSynWenyingFlowExample example);
}