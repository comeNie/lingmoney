package com.mrbt.lingmoney.service.discover;

import com.mrbt.lingmoney.utils.PageInfo;

/**
 *@author syb
 *@date 2017年4月12日 下午3:16:36
 *@version 1.0
 *@description 
 **/
public interface MyPlaceService {

	/**
	 * 查询banner列表
	 * 
	 * @param type 0抽奖，1限时抢。
	 * @return pageinfo
	 */
	PageInfo getBannerList(Integer type);

	//礼品兑换------------------------------------------
	/**
	 * 兑换礼品
	 * @param uid 用户ID
	 * @param giftId 礼品ID
	 * @param num 兑换数量
	 * @param addressID 地址id
	 * @return pageinfo
	 */
	PageInfo exchangeGift(String uid, Integer giftId, Integer num, Integer addressID);
	
	/**
	 * 查询兑换记录
	 * 
	 * @param uid 用户ID
	 * @param status 兑换状态 0兑换成功 1已发货 2已收货 如果status值为10，查询时将拆分为 (0,1)
	 * @param pageNo 页码
	 * @param pageSize 页数
	 * @return pageinfo
	 */
	PageInfo queryExchangeRecord(String uid, Integer status, Integer pageNo, Integer pageSize);
	
	/**
	 * 确认收货
	 * @param id 兑换记录ID
	 * @param uid 用户id
	 * @return pageinfo
	 */
	PageInfo confirmAcceptGift(Integer id, String uid);
	
	//购物车-------------------------------------------
	/**
	 * 查询购物车
	 * @param uid 用户ID
	 * @param pageNo 页码
	 * @param pageSize 页数
	 * @return pageinfo
	 */
	PageInfo queryShopCart(String uid, Integer pageNo, Integer pageSize);

	/**
	 * 编辑
	 * @param id 购物车ID
	 * @param num 数量
	 * @param uid 用户id
	 * @return pageinfo
	 */
	PageInfo editShopCart(Integer id, Integer num, String uid);

	/**
	 * 添加到购物车
	 * @param uid 用户ID
	 * @param giftId 礼品id
	 * @param num 数量
	 * @return pageinfo
	 */
	PageInfo addToShopCart(String uid, Integer giftId, Integer num);

	/**
	 * 从购物车删除
	 * @param id 购物车id
	 * @param uid 用户id
	 * @return pageinfo
	 */
	PageInfo removeFromShopCart(Integer id, String uid);
	
	/**
	 * 购物车兑换
	 * @param uid 用户ID
	 * @param giftCartIds 礼品ID 多个用“,”分割
	 * @param addressID 地址
	 * @return pageinfo
	 */
	PageInfo batchExchangeGift(String uid, String giftCartIds, Integer addressID);

	/**
	 * 根据礼品ID查询礼品详情
	 * @param id 礼品id
	 * @return pageinfo
	 */
	PageInfo queryDetail(Integer id);

	/**
	 * 查询礼品分类详情
	 * @param number 需要的条数
	 * @return pageinfo 
	 */
	PageInfo getGiftTypeList(Integer number);

	/**
	 * 根据条件查询礼品详情
	 * 
	 * @param typeId 类别id
	 * @param recommend 是否热门
	 * @param number 查询条数
	 * @param giftName 礼品名称
	 * @return pageinfo
	 */
	PageInfo getGiftListWithCondition(Integer typeId, Integer recommend, Integer number, String giftName);

	/**
	 * 兑换验证
	 * @param giftId 礼品id
	 * @param uid 用户id
	 * @param number 数量
	 * @return pageinfo
	 */
	PageInfo validateExchange(Integer giftId, String uid, Integer number);

	/**
	 * 批量兑换验证
	 * @param cids 礼品id 多个用英文逗号分隔
	 * @param uid 用户id
	 * @return pageinfo
	 */
	PageInfo batchExchangeGiftValidate(String cids, String uid);

	/**
	 * 批量从购物车删除
	 * @param ids 1,3,2多个ID用逗号分割
	 * @param uid 用户ID
	 * @return pageinfo
	 */
	PageInfo batchRemoveFromShopCart(String ids, String uid);
	
}
