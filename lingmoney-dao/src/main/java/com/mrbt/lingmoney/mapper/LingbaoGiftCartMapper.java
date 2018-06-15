package com.mrbt.lingmoney.mapper;

import com.mrbt.lingmoney.model.LingbaoGiftCart;
import com.mrbt.lingmoney.model.LingbaoGiftCartExample;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

public interface LingbaoGiftCartMapper {
    /**
     *  根据指定的条件获取数据库记录数,lingbao_gift_cart
     *
     * @param example
     */
    long countByExample(LingbaoGiftCartExample example);

    /**
     *  根据指定的条件删除数据库符合条件的记录,lingbao_gift_cart
     *
     * @param example
     */
    int deleteByExample(LingbaoGiftCartExample example);

    /**
     *  根据主键删除数据库的记录,lingbao_gift_cart
     *
     * @param id
     */
    int deleteByPrimaryKey(Integer id);

    /**
     *  新写入数据库记录,lingbao_gift_cart
     *
     * @param record
     */
    int insert(LingbaoGiftCart record);

    /**
     *  动态字段,写入数据库记录,lingbao_gift_cart
     *
     * @param record
     */
    int insertSelective(LingbaoGiftCart record);

    /**
     *  根据指定的条件查询符合条件的数据库记录,lingbao_gift_cart
     *
     * @param example
     */
    List<LingbaoGiftCart> selectByExample(LingbaoGiftCartExample example);

    /**
     *  根据指定主键获取一条数据库记录,lingbao_gift_cart
     *
     * @param id
     */
    LingbaoGiftCart selectByPrimaryKey(Integer id);

    /**
     *  动态根据指定的条件来更新符合条件的数据库记录,lingbao_gift_cart
     *
     * @param record
     * @param example
     */
    int updateByExampleSelective(@Param("record") LingbaoGiftCart record, @Param("example") LingbaoGiftCartExample example);

    /**
     *  根据指定的条件来更新符合条件的数据库记录,lingbao_gift_cart
     *
     * @param record
     * @param example
     */
    int updateByExample(@Param("record") LingbaoGiftCart record, @Param("example") LingbaoGiftCartExample example);

    /**
     *  动态字段,根据主键来更新符合条件的数据库记录,lingbao_gift_cart
     *
     * @param record
     */
    int updateByPrimaryKeySelective(LingbaoGiftCart record);

    /**
     *  根据主键来更新符合条件的数据库记录,lingbao_gift_cart
     *
     * @param record
     */
    int updateByPrimaryKey(LingbaoGiftCart record);

    /**
     * 根据用户ID查询抽奖记录
     * @param uid
     * @return
     */
	int queryLotteryByUid(String uid);

	/**
	 * 查询购物车数据（关联表）
	 * @param map
	 * @return
	 */
	List<Map<String, Object>> queryGiftCartInfoList(Map<String, Object> map);
	/**
	 * 查询到购物车数据总条数
	 * @param map
	 * @return
	 */
	int countGiftCartInfoList(Map<String, Object> map);

	/**
	 * 添加购物车时，修改购物车：数量累加、领宝累加
	 * @param num 增加数量
	 * @param id 购物车id
	 * @return
	 */
	int updateNumById(LingbaoGiftCart lgc);
}