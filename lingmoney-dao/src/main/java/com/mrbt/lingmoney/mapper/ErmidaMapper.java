package com.mrbt.lingmoney.mapper;

import com.mrbt.lingmoney.model.Ermida;
import com.mrbt.lingmoney.model.ErmidaExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface ErmidaMapper {
    /**
     *  根据指定的条件获取数据库记录数,ermida
     *
     * @param example
     */
    long countByExample(ErmidaExample example);

    /**
     *  根据指定的条件删除数据库符合条件的记录,ermida
     *
     * @param example
     */
    int deleteByExample(ErmidaExample example);

    /**
     *  新写入数据库记录,ermida
     *
     * @param record
     */
    int insert(Ermida record);

    /**
     *  动态字段,写入数据库记录,ermida
     *
     * @param record
     */
    int insertSelective(Ermida record);

    /**
     *  根据指定的条件查询符合条件的数据库记录,ermida
     *
     * @param example
     */
    List<Ermida> selectByExample(ErmidaExample example);

    /**
     *  动态根据指定的条件来更新符合条件的数据库记录,ermida
     *
     * @param record
     * @param example
     */
    int updateByExampleSelective(@Param("record") Ermida record, @Param("example") ErmidaExample example);

    /**
     *  根据指定的条件来更新符合条件的数据库记录,ermida
     *
     * @param record
     * @param example
     */
    int updateByExample(@Param("record") Ermida record, @Param("example") ErmidaExample example);
}