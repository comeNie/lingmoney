package com.mrbt.lingmoney.mapper;

import com.mrbt.lingmoney.model.Ermid;
import com.mrbt.lingmoney.model.ErmidExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface ErmidMapper {
    /**
     *  根据指定的条件获取数据库记录数,ermid
     *
     * @param example
     */
    long countByExample(ErmidExample example);

    /**
     *  根据指定的条件删除数据库符合条件的记录,ermid
     *
     * @param example
     */
    int deleteByExample(ErmidExample example);

    /**
     *  新写入数据库记录,ermid
     *
     * @param record
     */
    int insert(Ermid record);

    /**
     *  动态字段,写入数据库记录,ermid
     *
     * @param record
     */
    int insertSelective(Ermid record);

    /**
     *  根据指定的条件查询符合条件的数据库记录,ermid
     *
     * @param example
     */
    List<Ermid> selectByExample(ErmidExample example);

    /**
     *  动态根据指定的条件来更新符合条件的数据库记录,ermid
     *
     * @param record
     * @param example
     */
    int updateByExampleSelective(@Param("record") Ermid record, @Param("example") ErmidExample example);

    /**
     *  根据指定的条件来更新符合条件的数据库记录,ermid
     *
     * @param record
     * @param example
     */
    int updateByExample(@Param("record") Ermid record, @Param("example") ErmidExample example);
}