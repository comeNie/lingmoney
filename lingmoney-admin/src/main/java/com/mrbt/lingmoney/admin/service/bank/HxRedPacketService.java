package com.mrbt.lingmoney.admin.service.bank;

import java.util.List;

import com.mrbt.lingmoney.model.HxRedPacket;
import com.mrbt.lingmoney.model.Product;
import com.mrbt.lingmoney.model.ProductCategory;
import com.mrbt.lingmoney.utils.PageInfo;

/**
 * 华兴红包
 * @author luox
 * @Date 2017年7月4日
 */
public interface HxRedPacketService {

	/**
	 * 获取列表
	 * @param acquisMode	获取方式
	 * @param hrpType	卡券类型
	 * @param status	状态
	 * @param pageInfo	pageInfo
	 */
	void getList(Integer acquisMode, Integer hrpType, Integer status, PageInfo pageInfo);

	/**
	 * 
	 * @param packet	packet
	 */
	void saveOrEdit(HxRedPacket packet);

	
	/**
	 * 
	 * @param id	id
	 * @param status	status
	 */
	void updateStatus(String id, Integer status);

	
	/**
	 * 
	 * @return	return
	 */
	List<ProductCategory> getProductTypeList();

	/**
	 * 
	 * @return	return
	 */
	List<Product> getProductList();

	
	/**
	 * 
	 * @param id	id
	 * @param pageInfo	pageInfo
	 */
	void findBuyerByProId(Integer id, PageInfo pageInfo);

	
	/**
	 * 
	 * @param pageInfo pageInfo
	 */
	void findRedPacketList(PageInfo pageInfo);

	
	/**
	 * 送卡券
	 * @param uIds	uIds
	 * @param rpId	rpId
	 */
	void giveRedPacket(String uIds, String rpId);

	
	/**
	 * 查询列表
	 * @param pageInfo pageInfo
	 */
	void findManualRedPacketList(PageInfo pageInfo);

}
