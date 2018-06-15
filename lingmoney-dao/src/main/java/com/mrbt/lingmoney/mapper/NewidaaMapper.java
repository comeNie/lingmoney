package com.mrbt.lingmoney.mapper;

import com.mrbt.lingmoney.model.Newidaa;
import com.mrbt.lingmoney.model.NewidaaExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface NewidaaMapper {
    /**
     *  根据指定的条件获取数据库记录数,newidaa
     *
     * @param example
     */
    long countByExample(NewidaaExample example);

    /**
     *  根据指定的条件删除数据库符合条件的记录,newidaa
     *
     * @param example
     */
    int deleteByExample(NewidaaExample example);

    /**
     *  新写入数据库记录,newidaa
     *
     * @param record
     */
    int insert(Newidaa record);

    /**
     *  动态字段,写入数据库记录,newidaa
     *
     * @param record
     */
    int insertSelective(Newidaa record);

    /**
     *  根据指定的条件查询符合条件的数据库记录,newidaa
     *
     * @param example
     */
    List<Newidaa> selectByExample(NewidaaExample example);

    /**
     *  动态根据指定的条件来更新符合条件的数据库记录,newidaa
     *
     * @param record
     * @param example
     */
    int updateByExampleSelective(@Param("record") Newidaa record, @Param("example") NewidaaExample example);

    /**
     *  根据指定的条件来更新符合条件的数据库记录,newidaa
     *
     * @param record
     * @param example
     */
    int updateByExample(@Param("record") Newidaa record, @Param("example") NewidaaExample example);
}