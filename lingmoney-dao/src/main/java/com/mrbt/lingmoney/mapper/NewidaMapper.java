package com.mrbt.lingmoney.mapper;

import com.mrbt.lingmoney.model.Newida;
import com.mrbt.lingmoney.model.NewidaExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface NewidaMapper {
    /**
     *  根据指定的条件获取数据库记录数,newida
     *
     * @param example
     */
    long countByExample(NewidaExample example);

    /**
     *  根据指定的条件删除数据库符合条件的记录,newida
     *
     * @param example
     */
    int deleteByExample(NewidaExample example);

    /**
     *  新写入数据库记录,newida
     *
     * @param record
     */
    int insert(Newida record);

    /**
     *  动态字段,写入数据库记录,newida
     *
     * @param record
     */
    int insertSelective(Newida record);

    /**
     *  根据指定的条件查询符合条件的数据库记录,newida
     *
     * @param example
     */
    List<Newida> selectByExample(NewidaExample example);

    /**
     *  动态根据指定的条件来更新符合条件的数据库记录,newida
     *
     * @param record
     * @param example
     */
    int updateByExampleSelective(@Param("record") Newida record, @Param("example") NewidaExample example);

    /**
     *  根据指定的条件来更新符合条件的数据库记录,newida
     *
     * @param record
     * @param example
     */
    int updateByExample(@Param("record") Newida record, @Param("example") NewidaExample example);
}