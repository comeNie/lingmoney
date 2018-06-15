package com.mrbt.lingmoney.service.users;

import java.util.Map;

import com.mrbt.lingmoney.model.HxRedPacket;
import com.mrbt.lingmoney.model.Product;
import com.mrbt.lingmoney.utils.PageInfo;

/**
 * @author syb
 * @date 2017年7月6日 下午4:48:16
 * @version 1.0
 * @description 用户红包/加息券
 **/
public interface UserRedPacketService {
	/**
	 * 查询该产品可用优惠券
	 * 
	 * @param pCode
	 *            产品code
	 * @param key redis保存uid的key
	 * @param buyMoney 用户购买金额
	 * @param redPacketType 1 加息劵  2红包
	 * @return 用户优惠券map集合，包含可用，不可用
	 * @throws Exception 
	 */
    Map<String, Object> queryFinancialAvailableRedPacket(String pCode, String uId, Double buyMoney,
            Integer redPacketType) throws Exception;

	/**
	 * 查询用户所有优惠券 查询类型 未使用 已使用 已过期 全部 分页查询
	 * 
	 * @param key redis保存uid的key
	 * @param type 0未使用 1已使用 2 已过期 3全部
	 * @param hrpType 类型：1-加息券，2-返现红包
	 * @param pageSize 分页条数
	 * @param pageNo 分页页数
	 * @return pageinfo 
	 * @throws Exception 
	 */
    PageInfo queryUserRedPacketByType(String uId, Integer type, Integer hrpType, Integer pageSize, Integer pageNo)
            throws Exception;

	/**
	 * 删除
	 * 
	 * @param uid 用户id
	 * @param id 红包id
	 * @return pageinfo
	 */
    PageInfo removeUserRedPacket(String uid, Integer id);

	/**
	 * 理财-验证优惠券
	 * 
	 * @param product 产品信息
	 * @param buyMoney 购买金额
	 * @param hxRedPacket 红包信息
	 * @param userRedPacketId 用户红包id
	 * @return 通过true 不通过false
	 */
    boolean validRedPacket(Product product, Double buyMoney, HxRedPacket hxRedPacket, Integer userRedPacketId);

	/**
	 * 已使用/已过期卡劵
	 * 
	 * @param uId
	 *            uId
	 * @param type
	 *            type
	 * @param pageSize
	 *            pageSize
	 * @param pageNo
	 *            pageNo
	 * @return pi
	 * @throws Exception
	 */
	PageInfo queryUserRedPacketByTypeOfWap(String uId, Integer type, Integer pageSize, Integer pageNo) throws Exception;
}
