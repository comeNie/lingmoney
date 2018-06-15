package com.mrbt.lingmoney.service.redPacket;

import com.mrbt.lingmoney.utils.PageInfo;

/**
 * 红包相关接口
 * 
 * @author suyibo
 *
 */
public interface RedPacketService {

	/**
	 * 执行红包奖励操作
	 * 
	 * @author suyibo 2017年10月25日
	 * @param userId 用户id
	 * @param actType 活动类型
	 * @param amount 金额
	 */
	void rewardRedPackage(String userId, Integer actType, Double amount);

	/**
	 * 展示注册完成后赠送优惠券内容提示
	 * 
	 * @author suyibo 2017年10月27日
	 * @return pageinfo 
	 */
	PageInfo getParamsInfo();

	/**
	 * 获取具体优惠券内容信息
	 * 
	 * @author suyibo 2017年10月27日
	 * @param token token
	 * @return pageinfo
	 */
	PageInfo getRegistRedPacketInfoList(String token);

	/**
	 * (未查看)优惠券-》已查看
	 * 
	 * @author suyibo 2017年10月30日
	 * @param token token
	 * @return pageinfo
	 */
	PageInfo getCheckRedPacketList(String token);

}
