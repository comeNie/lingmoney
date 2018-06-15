package com.mrbt.lingmoney.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.mrbt.lingmoney.model.EmployeeRelationsMapping;
import com.mrbt.lingmoney.model.EmployeeRelationsMappingExample;

public interface EmployeeRelationsMappingMapper {
    /**
     *  根据指定的条件获取数据库记录数,employee_relations_mapping
     *
     * @param example
     */
    long countByExample(EmployeeRelationsMappingExample example);

    /**
     *  根据指定的条件删除数据库符合条件的记录,employee_relations_mapping
     *
     * @param example
     */
    int deleteByExample(EmployeeRelationsMappingExample example);

    /**
     *  根据主键删除数据库的记录,employee_relations_mapping
     *
     * @param id
     */
    int deleteByPrimaryKey(Integer id);

    /**
     *  新写入数据库记录,employee_relations_mapping
     *
     * @param record
     */
    int insert(EmployeeRelationsMapping record);

    /**
     *  动态字段,写入数据库记录,employee_relations_mapping
     *
     * @param record
     */
    int insertSelective(EmployeeRelationsMapping record);

    /**
     *  根据指定的条件查询符合条件的数据库记录,employee_relations_mapping
     *
     * @param example
     */
    List<EmployeeRelationsMapping> selectByExample(EmployeeRelationsMappingExample example);

    /**
     *  根据指定主键获取一条数据库记录,employee_relations_mapping
     *
     * @param id
     */
    EmployeeRelationsMapping selectByPrimaryKey(Integer id);

    /**
     *  动态根据指定的条件来更新符合条件的数据库记录,employee_relations_mapping
     *
     * @param record
     * @param example
     */
    int updateByExampleSelective(@Param("record") EmployeeRelationsMapping record, @Param("example") EmployeeRelationsMappingExample example);

    /**
     *  根据指定的条件来更新符合条件的数据库记录,employee_relations_mapping
     *
     * @param record
     * @param example
     */
    int updateByExample(@Param("record") EmployeeRelationsMapping record, @Param("example") EmployeeRelationsMappingExample example);

    /**
     *  动态字段,根据主键来更新符合条件的数据库记录,employee_relations_mapping
     *
     * @param record
     */
    int updateByPrimaryKeySelective(EmployeeRelationsMapping record);

    /**
     *  根据主键来更新符合条件的数据库记录,employee_relations_mapping
     *
     * @param record
     */
    int updateByPrimaryKey(EmployeeRelationsMapping record);

	/**
	 * 查询投资人的推荐人
	 * 
	 * @param acNo
	 * @param acName
	 * @return
	 */
	EmployeeRelationsMapping selectReferee(Map<String, Object> params);
}