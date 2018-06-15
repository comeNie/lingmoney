package com.mrbt.lingmoney.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.mrbt.lingmoney.model.LingbaoExchangeInfo;
import com.mrbt.lingmoney.model.LingbaoExchangeInfoExample;
import com.mrbt.lingmoney.model.LingbaoExchangeInfoVo;
import com.mrbt.lingmoney.utils.PageInfo;

public interface LingbaoExchangeInfoMapper {
    /**
     *  根据指定的条件获取数据库记录数,lingbao_exchange_info
     *
     * @param example
     */
    long countByExample(LingbaoExchangeInfoExample example);

    /**
     *  根据指定的条件删除数据库符合条件的记录,lingbao_exchange_info
     *
     * @param example
     */
    int deleteByExample(LingbaoExchangeInfoExample example);

    /**
     *  根据主键删除数据库的记录,lingbao_exchange_info
     *
     * @param id
     */
    int deleteByPrimaryKey(Integer id);

    /**
     *  新写入数据库记录,lingbao_exchange_info
     *
     * @param record
     */
    int insert(LingbaoExchangeInfo record);

    /**
     *  动态字段,写入数据库记录,lingbao_exchange_info
     *
     * @param record
     */
    int insertSelective(LingbaoExchangeInfo record);

    /**
     *  根据指定的条件查询符合条件的数据库记录,lingbao_exchange_info
     *
     * @param example
     */
    List<LingbaoExchangeInfo> selectByExample(LingbaoExchangeInfoExample example);

    /**
     *  根据指定主键获取一条数据库记录,lingbao_exchange_info
     *
     * @param id
     */
    LingbaoExchangeInfo selectByPrimaryKey(Integer id);

    /**
     *  动态根据指定的条件来更新符合条件的数据库记录,lingbao_exchange_info
     *
     * @param record
     * @param example
     */
    int updateByExampleSelective(@Param("record") LingbaoExchangeInfo record, @Param("example") LingbaoExchangeInfoExample example);

    /**
     *  根据指定的条件来更新符合条件的数据库记录,lingbao_exchange_info
     *
     * @param record
     * @param example
     */
    int updateByExample(@Param("record") LingbaoExchangeInfo record, @Param("example") LingbaoExchangeInfoExample example);

    /**
     *  动态字段,根据主键来更新符合条件的数据库记录,lingbao_exchange_info
     *
     * @param record
     */
    int updateByPrimaryKeySelective(LingbaoExchangeInfo record);

    /**
     *  根据主键来更新符合条件的数据库记录,lingbao_exchange_info
     *
     * @param record
     */
    int updateByPrimaryKey(LingbaoExchangeInfo record);

    /**
     * 查询中奖记录 最新50条
     * @return
     */
	List<Map<String, Object>> queryLotteryInfo();

	/**
	 * 根据用户ID查询中奖记录
	 * @param uid
	 * @return
	 */
	int queryLotteryByUid(String uid);

	/**
	 * 根据序列号查询
	 * @param serialNumber
	 * @return
	 */
	LingbaoExchangeInfo queryBySerialNumber(String serialNumber);

	/**
	 * 根据条件查询展示用数据
	 * @param map
	 * @return
	 */
	List<Map<String,Object>> queryExchangeInfoViewList(Map<String, Object> map);
	/**
	 * 根据条件查询展示用数据 总条数
	 * @param map
	 * @return
	 */
	int countExchangeInfoViewList(Map<String,Object> map);
	
	/**
	 * 根据条件分页查询兑换信息
	 * 
	 * @param pageInfo
	 * @return
	 */
	List<LingbaoExchangeInfoVo> findLingbaoExchangeInfoCondition(
			PageInfo pageInfo);

	/**
	 * 根据条件查询兑换信息个数
	 * 
	 * @param pageInfo
	 * @return
	 */
	int findLingbaoExchangeInfoCount(PageInfo pageInfo);
}