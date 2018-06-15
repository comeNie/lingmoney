package com.mrbt.lingmoney.mapper;

import com.mrbt.lingmoney.model.LingqianTmp;
import com.mrbt.lingmoney.model.LingqianTmpExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface LingqianTmpMapper {
    /**
     *  根据指定的条件获取数据库记录数,lingqian_tmp
     *
     * @param example
     */
    long countByExample(LingqianTmpExample example);

    /**
     *  根据指定的条件删除数据库符合条件的记录,lingqian_tmp
     *
     * @param example
     */
    int deleteByExample(LingqianTmpExample example);

    /**
     *  新写入数据库记录,lingqian_tmp
     *
     * @param record
     */
    int insert(LingqianTmp record);

    /**
     *  动态字段,写入数据库记录,lingqian_tmp
     *
     * @param record
     */
    int insertSelective(LingqianTmp record);

    /**
     *  根据指定的条件查询符合条件的数据库记录,lingqian_tmp
     *
     * @param example
     */
    List<LingqianTmp> selectByExample(LingqianTmpExample example);

    /**
     *  动态根据指定的条件来更新符合条件的数据库记录,lingqian_tmp
     *
     * @param record
     * @param example
     */
    int updateByExampleSelective(@Param("record") LingqianTmp record, @Param("example") LingqianTmpExample example);

    /**
     *  根据指定的条件来更新符合条件的数据库记录,lingqian_tmp
     *
     * @param record
     * @param example
     */
    int updateByExample(@Param("record") LingqianTmp record, @Param("example") LingqianTmpExample example);
}