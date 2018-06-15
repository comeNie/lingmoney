package com.mrbt.lingmoney.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.mrbt.lingmoney.model.GiftExchangeInfo;
import com.mrbt.lingmoney.model.GiftExchangeInfoExample;
import com.mrbt.lingmoney.model.GiftExchangeInfoVo;
import com.mrbt.lingmoney.model.GiftExchangeInfoVo;
import com.mrbt.lingmoney.utils.PageInfo;

public interface GiftExchangeInfoMapper {
	/**
	 * 根据指定的条件获取数据库记录数,gift_exchange_info
	 * 
	 * @param example
	 */
	long countByExample(GiftExchangeInfoExample example);

	/**
	 * 根据指定的条件删除数据库符合条件的记录,gift_exchange_info
	 * 
	 * @param example
	 */
	int deleteByExample(GiftExchangeInfoExample example);

	/**
	 * 根据主键删除数据库的记录,gift_exchange_info
	 * 
	 * @param id
	 */
	int deleteByPrimaryKey(Integer id);

	/**
	 * 新写入数据库记录,gift_exchange_info
	 * 
	 * @param record
	 */
	int insert(GiftExchangeInfo record);

	/**
	 * 动态字段,写入数据库记录,gift_exchange_info
	 * 
	 * @param record
	 */
	int insertSelective(GiftExchangeInfo record);

	/**
	 * 根据指定的条件查询符合条件的数据库记录,gift_exchange_info
	 * 
	 * @param example
	 */
	List<GiftExchangeInfo> selectByExample(GiftExchangeInfoExample example);

	/**
	 * 根据指定主键获取一条数据库记录,gift_exchange_info
	 * 
	 * @param id
	 */
	GiftExchangeInfo selectByPrimaryKey(Integer id);

	/**
	 * 动态根据指定的条件来更新符合条件的数据库记录,gift_exchange_info
	 * 
	 * @param record
	 * @param example
	 */
	int updateByExampleSelective(@Param("record") GiftExchangeInfo record,
			@Param("example") GiftExchangeInfoExample example);

	/**
	 * 根据指定的条件来更新符合条件的数据库记录,gift_exchange_info
	 * 
	 * @param record
	 * @param example
	 */
	int updateByExample(@Param("record") GiftExchangeInfo record, @Param("example") GiftExchangeInfoExample example);

	/**
	 * 动态字段,根据主键来更新符合条件的数据库记录,gift_exchange_info
	 * 
	 * @param record
	 */
	int updateByPrimaryKeySelective(GiftExchangeInfo record);

	/**
	 * 根据主键来更新符合条件的数据库记录,gift_exchange_info
	 * 
	 * @param record
	 */
	int updateByPrimaryKey(GiftExchangeInfo record);

	/**
	 * 查询满足送话费条件的记录,排除gift_exchange_info中已经存在的记录
	 * 
	 * @param map
	 * @return
	 */
	List<GiftExchangeInfoVo> queryGiftCall(Map<String, Object> map);

	/**
	 * 批量插入gift_exchange_info
	 * 
	 * @param list
	 */
	void batchInsertGiftExchangeInfo(List<GiftExchangeInfoVo> validInfo);

	/**
	 * 根据条件分页查询兑换信息
	 * 
	 * @param pageInfo
	 * @return
	 */
	List<GiftExchangeInfoVo> findGiftExchangeInfoCondition(PageInfo pageInfo);

	/**
	 * 根据条件查询兑换信息个数
	 * 
	 * @param pageInfo
	 * @return
	 */
	int findGiftExchangeInfoCount(PageInfo pageInfo);
	
	/**
	 * 查询新客户购买3个月以上产品总金额
	 * 
	 * @param uId
	 * @return
	 */
	Double queryThreeMonthBuy(String uId);
	
	/**
	 * 批量插入gift_exchange_info_new
	 * 
	 * @param list
	 */
	void batchInsertGiftExchangeInfoNew(List<GiftExchangeInfoVo> validInfo);
}