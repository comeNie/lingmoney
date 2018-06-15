package com.mrbt.lingmoney.mapper;

import com.mrbt.lingmoney.model.LingbaoSign;
import com.mrbt.lingmoney.model.LingbaoSignExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface LingbaoSignMapper {
    /**
     *  根据指定的条件获取数据库记录数,lingbao_sign
     *
     * @param example
     */
    long countByExample(LingbaoSignExample example);

    /**
     *  根据指定的条件删除数据库符合条件的记录,lingbao_sign
     *
     * @param example
     */
    int deleteByExample(LingbaoSignExample example);

    /**
     *  根据主键删除数据库的记录,lingbao_sign
     *
     * @param id
     */
    int deleteByPrimaryKey(Integer id);

    /**
     *  新写入数据库记录,lingbao_sign
     *
     * @param record
     */
    int insert(LingbaoSign record);

    /**
     *  动态字段,写入数据库记录,lingbao_sign
     *
     * @param record
     */
    int insertSelective(LingbaoSign record);

    /**
     *  根据指定的条件查询符合条件的数据库记录,lingbao_sign
     *
     * @param example
     */
    List<LingbaoSign> selectByExample(LingbaoSignExample example);

    /**
     *  根据指定主键获取一条数据库记录,lingbao_sign
     *
     * @param id
     */
    LingbaoSign selectByPrimaryKey(Integer id);

    /**
     *  动态根据指定的条件来更新符合条件的数据库记录,lingbao_sign
     *
     * @param record
     * @param example
     */
    int updateByExampleSelective(@Param("record") LingbaoSign record, @Param("example") LingbaoSignExample example);

    /**
     *  根据指定的条件来更新符合条件的数据库记录,lingbao_sign
     *
     * @param record
     * @param example
     */
    int updateByExample(@Param("record") LingbaoSign record, @Param("example") LingbaoSignExample example);

    /**
     *  动态字段,根据主键来更新符合条件的数据库记录,lingbao_sign
     *
     * @param record
     */
    int updateByPrimaryKeySelective(LingbaoSign record);

    /**
     *  根据主键来更新符合条件的数据库记录,lingbao_sign
     *
     * @param record
     */
    int updateByPrimaryKey(LingbaoSign record);

    /**
     * 查询有效签到活动
     * @return
     */
	LingbaoSign queryAvailableRecord();
}