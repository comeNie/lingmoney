package com.mrbt.lingmoney.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.mrbt.lingmoney.model.TakeheartFixRate;
import com.mrbt.lingmoney.model.TakeheartFixRateExample;

public interface TakeheartFixRateMapper {
	/**
	 * 根据指定的条件获取数据库记录数,takeheart_fix_rate
	 *
	 * @param example
	 */
	long countByExample(TakeheartFixRateExample example);

	/**
	 * 根据指定的条件删除数据库符合条件的记录,takeheart_fix_rate
	 *
	 * @param example
	 */
	int deleteByExample(TakeheartFixRateExample example);

	/**
	 * 根据主键删除数据库的记录,takeheart_fix_rate
	 *
	 * @param id
	 */
	int deleteByPrimaryKey(Integer id);

	/**
	 * 新写入数据库记录,takeheart_fix_rate
	 *
	 * @param record
	 */
	int insert(TakeheartFixRate record);

	/**
	 * 动态字段,写入数据库记录,takeheart_fix_rate
	 *
	 * @param record
	 */
	int insertSelective(TakeheartFixRate record);

	/**
	 * 根据指定的条件查询符合条件的数据库记录,takeheart_fix_rate
	 *
	 * @param example
	 */
	List<TakeheartFixRate> selectByExample(TakeheartFixRateExample example);

	/**
	 * 根据指定主键获取一条数据库记录,takeheart_fix_rate
	 *
	 * @param id
	 */
	TakeheartFixRate selectByPrimaryKey(Integer id);

	/**
	 * 动态根据指定的条件来更新符合条件的数据库记录,takeheart_fix_rate
	 *
	 * @param record
	 * @param example
	 */
	int updateByExampleSelective(@Param("record") TakeheartFixRate record,
			@Param("example") TakeheartFixRateExample example);

	/**
	 * 根据指定的条件来更新符合条件的数据库记录,takeheart_fix_rate
	 *
	 * @param record
	 * @param example
	 */
	int updateByExample(@Param("record") TakeheartFixRate record, @Param("example") TakeheartFixRateExample example);

	/**
	 * 动态字段,根据主键来更新符合条件的数据库记录,takeheart_fix_rate
	 *
	 * @param record
	 */
	int updateByPrimaryKeySelective(TakeheartFixRate record);

	/**
	 * 根据主键来更新符合条件的数据库记录,takeheart_fix_rate
	 *
	 * @param record
	 */
	int updateByPrimaryKey(TakeheartFixRate record);

	/**
	 * 根据购买金额和天数查询随心取利率
	 * 
	 * @param rDay
	 *            天数
	 * @param financialMoney
	 *            金额
	 * @return rId,cId,rate 利率,name 类型名,upperLimit 上限,lowerLimit 下限,state
	 *         0:初始，1：发布，2：使用, 3: 作废
	 */
	Map<String, Object> getTakeHeartFixRate(Map<String, Object> fixRateParams);

	/**
	 * 获取下一天数利率及相差天数
	 * 
	 * @param RDays
	 * @param cid
	 * @return
	 */
	TakeheartFixRate getUpDayTakeHeartFixRate(Map<String, Object> upDayfixRageParams);

    /**
     * 查询随心取对应费率
     * 
     * @author yiban
     * @date 2018年1月7日 下午6:16:25
     * @version 1.0
     * @return
     *
     */
    List<Map<String, Object>> getAllTakeHeartFixRate();
}