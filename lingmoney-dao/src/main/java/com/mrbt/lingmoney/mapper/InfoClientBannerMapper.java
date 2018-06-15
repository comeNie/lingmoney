package com.mrbt.lingmoney.mapper;

import com.mrbt.lingmoney.model.InfoClientBanner;
import com.mrbt.lingmoney.model.InfoClientBannerExample;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

public interface InfoClientBannerMapper {
    /**
     *  根据指定的条件获取数据库记录数,info_client_banner
     *
     * @param example
     */
    long countByExample(InfoClientBannerExample example);

    /**
     *  根据指定的条件删除数据库符合条件的记录,info_client_banner
     *
     * @param example
     */
    int deleteByExample(InfoClientBannerExample example);

    /**
     *  根据主键删除数据库的记录,info_client_banner
     *
     * @param id
     */
    int deleteByPrimaryKey(Integer id);

    /**
     *  新写入数据库记录,info_client_banner
     *
     * @param record
     */
    int insert(InfoClientBanner record);

    /**
     *  动态字段,写入数据库记录,info_client_banner
     *
     * @param record
     */
    int insertSelective(InfoClientBanner record);

    /**
     *  根据指定的条件查询符合条件的数据库记录,info_client_banner
     *
     * @param example
     */
    List<InfoClientBanner> selectByExample(InfoClientBannerExample example);

    /**
     *  根据指定主键获取一条数据库记录,info_client_banner
     *
     * @param id
     */
    InfoClientBanner selectByPrimaryKey(Integer id);

    /**
     *  动态根据指定的条件来更新符合条件的数据库记录,info_client_banner
     *
     * @param record
     * @param example
     */
    int updateByExampleSelective(@Param("record") InfoClientBanner record, @Param("example") InfoClientBannerExample example);

    /**
     *  根据指定的条件来更新符合条件的数据库记录,info_client_banner
     *
     * @param record
     * @param example
     */
    int updateByExample(@Param("record") InfoClientBanner record, @Param("example") InfoClientBannerExample example);

    /**
     *  动态字段,根据主键来更新符合条件的数据库记录,info_client_banner
     *
     * @param record
     */
    int updateByPrimaryKeySelective(InfoClientBanner record);

    /**
     *  根据主键来更新符合条件的数据库记录,info_client_banner
     *
     * @param record
     */
    int updateByPrimaryKey(InfoClientBanner record);

    /**
     * 查询客户端banner信息，自定义查询
     * @param map
     * @return
     */
	List<InfoClientBanner> selectBySizeCode(Map<String, Object> map);
}