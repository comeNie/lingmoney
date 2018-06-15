package com.mrbt.lingmoney.mapper;

import com.mrbt.lingmoney.model.RedeemFailFlow;
import com.mrbt.lingmoney.model.RedeemFailFlowExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface RedeemFailFlowMapper {
    /**
     *  根据指定的条件获取数据库记录数,redeem_fail_flow
     *
     * @param example
     */
    long countByExample(RedeemFailFlowExample example);

    /**
     *  根据指定的条件删除数据库符合条件的记录,redeem_fail_flow
     *
     * @param example
     */
    int deleteByExample(RedeemFailFlowExample example);

    /**
     *  根据主键删除数据库的记录,redeem_fail_flow
     *
     * @param id
     */
    int deleteByPrimaryKey(Integer id);

    /**
     *  新写入数据库记录,redeem_fail_flow
     *
     * @param record
     */
    int insert(RedeemFailFlow record);

    /**
     *  动态字段,写入数据库记录,redeem_fail_flow
     *
     * @param record
     */
    int insertSelective(RedeemFailFlow record);

    /**
     *  根据指定的条件查询符合条件的数据库记录,redeem_fail_flow
     *
     * @param example
     */
    List<RedeemFailFlow> selectByExample(RedeemFailFlowExample example);

    /**
     *  根据指定主键获取一条数据库记录,redeem_fail_flow
     *
     * @param id
     */
    RedeemFailFlow selectByPrimaryKey(Integer id);

    /**
     *  动态根据指定的条件来更新符合条件的数据库记录,redeem_fail_flow
     *
     * @param record
     * @param example
     */
    int updateByExampleSelective(@Param("record") RedeemFailFlow record, @Param("example") RedeemFailFlowExample example);

    /**
     *  根据指定的条件来更新符合条件的数据库记录,redeem_fail_flow
     *
     * @param record
     * @param example
     */
    int updateByExample(@Param("record") RedeemFailFlow record, @Param("example") RedeemFailFlowExample example);

    /**
     *  动态字段,根据主键来更新符合条件的数据库记录,redeem_fail_flow
     *
     * @param record
     */
    int updateByPrimaryKeySelective(RedeemFailFlow record);

    /**
     *  根据主键来更新符合条件的数据库记录,redeem_fail_flow
     *
     * @param record
     */
    int updateByPrimaryKey(RedeemFailFlow record);
}