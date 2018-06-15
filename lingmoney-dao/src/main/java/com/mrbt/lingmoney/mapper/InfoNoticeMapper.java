package com.mrbt.lingmoney.mapper;

import com.mrbt.lingmoney.model.InfoNotice;
import com.mrbt.lingmoney.model.InfoNoticeExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface InfoNoticeMapper {
    /**
     *  根据指定的条件获取数据库记录数,info_notice
     *
     * @param example
     */
    long countByExample(InfoNoticeExample example);

    /**
     *  根据指定的条件删除数据库符合条件的记录,info_notice
     *
     * @param example
     */
    int deleteByExample(InfoNoticeExample example);

    /**
     *  根据主键删除数据库的记录,info_notice
     *
     * @param id
     */
    int deleteByPrimaryKey(Integer id);

    /**
     *  新写入数据库记录,info_notice
     *
     * @param record
     */
    int insert(InfoNotice record);

    /**
     *  动态字段,写入数据库记录,info_notice
     *
     * @param record
     */
    int insertSelective(InfoNotice record);

    /**
     * ,info_notice
     *
     * @param example
     */
    List<InfoNotice> selectByExampleWithBLOBs(InfoNoticeExample example);

    /**
     *  根据指定的条件查询符合条件的数据库记录,info_notice
     *
     * @param example
     */
    List<InfoNotice> selectByExample(InfoNoticeExample example);

    /**
     *  根据指定主键获取一条数据库记录,info_notice
     *
     * @param id
     */
    InfoNotice selectByPrimaryKey(Integer id);

    /**
     *  动态根据指定的条件来更新符合条件的数据库记录,info_notice
     *
     * @param record
     * @param example
     */
    int updateByExampleSelective(@Param("record") InfoNotice record, @Param("example") InfoNoticeExample example);

    /**
     * ,info_notice
     *
     * @param record
     * @param example
     */
    int updateByExampleWithBLOBs(@Param("record") InfoNotice record, @Param("example") InfoNoticeExample example);

    /**
     *  根据指定的条件来更新符合条件的数据库记录,info_notice
     *
     * @param record
     * @param example
     */
    int updateByExample(@Param("record") InfoNotice record, @Param("example") InfoNoticeExample example);

    /**
     *  动态字段,根据主键来更新符合条件的数据库记录,info_notice
     *
     * @param record
     */
    int updateByPrimaryKeySelective(InfoNotice record);

    /**
     * ,info_notice
     *
     * @param record
     */
    int updateByPrimaryKeyWithBLOBs(InfoNotice record);

    /**
     *  根据主键来更新符合条件的数据库记录,info_notice
     *
     * @param record
     */
    int updateByPrimaryKey(InfoNotice record);
    
    // 查询所有id
 	List<Integer> selectIdList();

	InfoNotice selectByPrimaryKeyNotBlob(int id);
}