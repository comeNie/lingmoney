package com.mrbt.lingmoney.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.mrbt.lingmoney.model.AreaDomain;
import com.mrbt.lingmoney.model.AreaDomainExample;

public interface AreaDomainMapper {
    /**
     *  根据指定的条件获取数据库记录数,area_domain
     *
     * @param example
     */
    long countByExample(AreaDomainExample example);

    /**
     *  根据指定的条件删除数据库符合条件的记录,area_domain
     *
     * @param example
     */
    int deleteByExample(AreaDomainExample example);

    /**
     *  根据主键删除数据库的记录,area_domain
     *
     * @param id
     */
    int deleteByPrimaryKey(Integer id);

    /**
     *  新写入数据库记录,area_domain
     *
     * @param record
     */
    int insert(AreaDomain record);

    /**
     *  动态字段,写入数据库记录,area_domain
     *
     * @param record
     */
    int insertSelective(AreaDomain record);

    /**
     *  根据指定的条件查询符合条件的数据库记录,area_domain
     *
     * @param example
     */
    List<AreaDomain> selectByExample(AreaDomainExample example);

    /**
     *  根据指定主键获取一条数据库记录,area_domain
     *
     * @param id
     */
    AreaDomain selectByPrimaryKey(Integer id);

    /**
     *  动态根据指定的条件来更新符合条件的数据库记录,area_domain
     *
     * @param record
     * @param example
     */
    int updateByExampleSelective(@Param("record") AreaDomain record, @Param("example") AreaDomainExample example);

    /**
     *  根据指定的条件来更新符合条件的数据库记录,area_domain
     *
     * @param record
     * @param example
     */
    int updateByExample(@Param("record") AreaDomain record, @Param("example") AreaDomainExample example);

    /**
     *  动态字段,根据主键来更新符合条件的数据库记录,area_domain
     *
     * @param record
     */
    int updateByPrimaryKeySelective(AreaDomain record);

    /**
     *  根据主键来更新符合条件的数据库记录,area_domain
     *
     * @param record
     */
    int updateByPrimaryKey(AreaDomain record);
    
    /**
	 * 分页查询
	 *@Description  
	 *@param ad
	 *@return
	 */
	public List<Map<String,Object>> queryAll(Map<String,Object> map);
	/**
	 * 分页查询条数
	 *@Description  
	 *@param ad
	 *@return
	 */
	public int queryAllCount(Map<String,Object> map);
	/**
	 * 查询所有城市代码，城市名
	 *@Description  
	 *@return
	 */
	public List<Map<String, String>> queryCodeName();
}