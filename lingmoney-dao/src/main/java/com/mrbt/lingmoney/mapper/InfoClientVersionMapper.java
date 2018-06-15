package com.mrbt.lingmoney.mapper;

import com.mrbt.lingmoney.model.InfoClientVersion;
import com.mrbt.lingmoney.model.InfoClientVersionExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface InfoClientVersionMapper {
    /**
     *  根据指定的条件获取数据库记录数,info_client_version
     *
     * @param example
     */
    long countByExample(InfoClientVersionExample example);

    /**
     *  根据指定的条件删除数据库符合条件的记录,info_client_version
     *
     * @param example
     */
    int deleteByExample(InfoClientVersionExample example);

    /**
     *  根据主键删除数据库的记录,info_client_version
     *
     * @param id
     */
    int deleteByPrimaryKey(Integer id);

    /**
     *  新写入数据库记录,info_client_version
     *
     * @param record
     */
    int insert(InfoClientVersion record);

    /**
     *  动态字段,写入数据库记录,info_client_version
     *
     * @param record
     */
    int insertSelective(InfoClientVersion record);

    /**
     *  根据指定的条件查询符合条件的数据库记录,info_client_version
     *
     * @param example
     */
    List<InfoClientVersion> selectByExample(InfoClientVersionExample example);

    /**
     *  根据指定主键获取一条数据库记录,info_client_version
     *
     * @param id
     */
    InfoClientVersion selectByPrimaryKey(Integer id);

    /**
     *  动态根据指定的条件来更新符合条件的数据库记录,info_client_version
     *
     * @param record
     * @param example
     */
    int updateByExampleSelective(@Param("record") InfoClientVersion record, @Param("example") InfoClientVersionExample example);

    /**
     *  根据指定的条件来更新符合条件的数据库记录,info_client_version
     *
     * @param record
     * @param example
     */
    int updateByExample(@Param("record") InfoClientVersion record, @Param("example") InfoClientVersionExample example);

    /**
     *  动态字段,根据主键来更新符合条件的数据库记录,info_client_version
     *
     * @param record
     */
    int updateByPrimaryKeySelective(InfoClientVersion record);

    /**
     *  根据主键来更新符合条件的数据库记录,info_client_version
     *
     * @param record
     */
    int updateByPrimaryKey(InfoClientVersion record);
}