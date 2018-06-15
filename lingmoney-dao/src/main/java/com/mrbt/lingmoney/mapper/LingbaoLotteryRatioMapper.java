package com.mrbt.lingmoney.mapper;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.mrbt.lingmoney.model.LingbaoLotteryRatio;
import com.mrbt.lingmoney.model.LingbaoLotteryRatioExample;
import com.mrbt.lingmoney.utils.PageInfo;

public interface LingbaoLotteryRatioMapper {
    /**
     *  根据指定的条件获取数据库记录数,lingbao_lottery_ratio
     *
     * @param example
     */
    long countByExample(LingbaoLotteryRatioExample example);

    /**
     *  根据指定的条件删除数据库符合条件的记录,lingbao_lottery_ratio
     *
     * @param example
     */
    int deleteByExample(LingbaoLotteryRatioExample example);

    /**
     *  根据主键删除数据库的记录,lingbao_lottery_ratio
     *
     * @param id
     */
    int deleteByPrimaryKey(Integer id);

    /**
     *  新写入数据库记录,lingbao_lottery_ratio
     *
     * @param record
     */
    int insert(LingbaoLotteryRatio record);

    /**
     *  动态字段,写入数据库记录,lingbao_lottery_ratio
     *
     * @param record
     */
    int insertSelective(LingbaoLotteryRatio record);

    /**
     *  根据指定的条件查询符合条件的数据库记录,lingbao_lottery_ratio
     *
     * @param example
     */
    List<LingbaoLotteryRatio> selectByExample(LingbaoLotteryRatioExample example);

    /**
     *  根据指定主键获取一条数据库记录,lingbao_lottery_ratio
     *
     * @param id
     */
    LingbaoLotteryRatio selectByPrimaryKey(Integer id);

    /**
     *  动态根据指定的条件来更新符合条件的数据库记录,lingbao_lottery_ratio
     *
     * @param record
     * @param example
     */
    int updateByExampleSelective(@Param("record") LingbaoLotteryRatio record, @Param("example") LingbaoLotteryRatioExample example);

    /**
     *  根据指定的条件来更新符合条件的数据库记录,lingbao_lottery_ratio
     *
     * @param record
     * @param example
     */
    int updateByExample(@Param("record") LingbaoLotteryRatio record, @Param("example") LingbaoLotteryRatioExample example);

    /**
     *  动态字段,根据主键来更新符合条件的数据库记录,lingbao_lottery_ratio
     *
     * @param record
     */
    int updateByPrimaryKeySelective(LingbaoLotteryRatio record);

    /**
     *  根据主键来更新符合条件的数据库记录,lingbao_lottery_ratio
     *
     * @param record
     */
    int updateByPrimaryKey(LingbaoLotteryRatio record);
    
	/**
	 * 根据活动类型查询该活动下奖项信息，用于页面展示
	 * 
	 * @author yiban
	 * @date 2017年10月19日 下午2:11:08
	 * @version 1.0
	 * @param typeId
	 * @return
	 *
	 */
    List<Map<String, Object>> queryByTypeId(int typeId);

	/**
	 * 根据活动类型查询该活动下奖项信息，用于抽奖计算
	 * 
	 * @author yiban
	 * @date 2017年10月19日 下午2:11:08
	 * @version 1.0
	 * @param typeId
	 * @return
	 *
	 */
	List<Map<String, Object>> queryByTypeIdForCount(int typeId);

	/**
	 * 从视图user_year_financialmoney查询用户年化理财金额（稳赢宝、车贷宝）
	 * @param uid
	 * @return
	 */
	BigDecimal queryYearFinancing(String uid);
	
	/**
	 * 根据条件查询抽奖比例
	 * 
	 * @param pageInfo
	 * @return
	 */
	List<LingbaoLotteryRatio> findByCondition(PageInfo pageInfo);

	/**
	 * 根据条件查询抽奖比例个数
	 * 
	 * @param pageInfo
	 * @return
	 */
	int findCountByCondition(PageInfo pageInfo);
}