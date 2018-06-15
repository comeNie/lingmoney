package com.mrbt.lingmoney.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.mrbt.lingmoney.model.EnterpriseNameMoney;
import com.mrbt.lingmoney.model.EnterpriseNameMoneyExample;

public interface EnterpriseNameMoneyMapper {
    /**
	 * 根据指定的条件获取数据库记录数,enterprise_name_money
	 *
	 * @param example
	 */
    int countByExample(EnterpriseNameMoneyExample example);

    /**
	 * 根据指定的条件删除数据库符合条件的记录,enterprise_name_money
	 *
	 * @param example
	 */
    int deleteByExample(EnterpriseNameMoneyExample example);

    /**
	 * 根据主键删除数据库的记录,enterprise_name_money
	 *
	 * @param record
	 */
    int insert(EnterpriseNameMoney record);

    /**
	 * 新写入数据库记录,enterprise_name_money
	 *
	 * @param record
	 */
    int insertSelective(EnterpriseNameMoney record);

    /**
	 * 根据指定的条件查询符合条件的数据库记录,enterprise_name_money
	 *
	 * @param example
	 */
    List<EnterpriseNameMoney> selectByExample(EnterpriseNameMoneyExample example);

    /**
	 * 动态字段,根据主键来更新符合条件的数据库记录,enterprise_name_money
	 *
	 * @param record
	 * @param example
	 */
    int updateByExampleSelective(@Param("record") EnterpriseNameMoney record, @Param("example") EnterpriseNameMoneyExample example);

    /**
	 * 根据主键来更新符合条件的数据库记录,enterprise_name_money
	 *
	 * @param record
	 * @param example
	 */
    int updateByExample(@Param("record") EnterpriseNameMoney record, @Param("example") EnterpriseNameMoneyExample example);
}