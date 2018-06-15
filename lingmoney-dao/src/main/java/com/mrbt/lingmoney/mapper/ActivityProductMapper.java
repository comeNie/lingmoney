package com.mrbt.lingmoney.mapper;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.mrbt.lingmoney.model.ActivityProduct;
import com.mrbt.lingmoney.model.ActivityProductExample;
import com.mrbt.lingmoney.model.ActivityProductWithBLOBs;

public interface ActivityProductMapper {
    /**
     *  根据指定的条件获取数据库记录数,activity_product
     * @param example
     */
    long countByExample(ActivityProductExample example);

    /**
     *  根据指定的条件删除数据库符合条件的记录,activity_product
     * @param example
     */
    int deleteByExample(ActivityProductExample example);

    /**
     *  根据主键删除数据库的记录,activity_product
     * @param id
     */
    int deleteByPrimaryKey(Integer id);

    /**
     *  新写入数据库记录,activity_product
     * @param record
     */
    int insert(ActivityProductWithBLOBs record);

    /**
     *  动态字段,写入数据库记录,activity_product
     * @param record
     */
    int insertSelective(ActivityProductWithBLOBs record);

    /**
     * ,activity_product
     * @param example
     */
    List<ActivityProductWithBLOBs> selectByExampleWithBLOBs(ActivityProductExample example);

    /**
     *  根据指定的条件查询符合条件的数据库记录,activity_product
     * @param example
     */
    List<ActivityProduct> selectByExample(ActivityProductExample example);

    /**
     *  根据指定主键获取一条数据库记录,activity_product
     * @param id
     */
    ActivityProductWithBLOBs selectByPrimaryKey(Integer id);

    /**
     *  动态根据指定的条件来更新符合条件的数据库记录,activity_product
     * @param record
     * @param example
     */
    int updateByExampleSelective(@Param("record") ActivityProductWithBLOBs record, @Param("example") ActivityProductExample example);

    /**
     * ,activity_product
     * @param record
     * @param example
     */
    int updateByExampleWithBLOBs(@Param("record") ActivityProductWithBLOBs record, @Param("example") ActivityProductExample example);

    /**
     *  根据指定的条件来更新符合条件的数据库记录,activity_product
     * @param record
     * @param example
     */
    int updateByExample(@Param("record") ActivityProduct record, @Param("example") ActivityProductExample example);

    /**
     *  动态字段,根据主键来更新符合条件的数据库记录,activity_product
     * @param record
     */
    int updateByPrimaryKeySelective(ActivityProductWithBLOBs record);

    /**
     * ,activity_product
     * @param record
     */
    int updateByPrimaryKeyWithBLOBs(ActivityProductWithBLOBs record);

    /**
     *  根据主键来更新符合条件的数据库记录,activity_product
     * @param record
     */
    int updateByPrimaryKey(ActivityProduct record);

	/**
	 * 查询活动产品购买明细
	 * 
	 * @param params
	 *            params
	 * @return return
	 */
	List<Map<String, Object>> findFestivalBuyDetailList(Map<String, Object> params);

	/**
	 * 查询活动期间理财金额
	 * 
	 * @param pactName
	 *            产品名称
	 * @param uid
	 *            uid
	 * @param pcode
	 *            批次
	 * @param activityStartDate
	 *            活动开始时间
	 * @param activityEndDate
	 *            活动结束时间
	 * @return 理财金额
	 */
	BigDecimal findFinanceMoneyByPcode(@Param("pactName") String pactName, @Param("uid") String uid,
			@Param("pcode") String pcode, @Param("activityStartDate") String activityStartDate,
			@Param("activityEndDate") String activityEndDate);
}