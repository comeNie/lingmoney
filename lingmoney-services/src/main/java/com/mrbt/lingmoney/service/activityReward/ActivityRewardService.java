package com.mrbt.lingmoney.service.activityReward;

import com.alibaba.fastjson.JSONObject;
import com.mrbt.lingmoney.utils.PageInfo;

/** 
 * @author  suyibo 
 * @date 创建时间：2018年5月10日
 */
public interface ActivityRewardService {

	/**
	 * 查询用户红包个数
	 * 
	 * @params uId 用户id
	 * @author suyb
	 * @date 2018-05-11 09:27:53
	 * @return JSONObject
	 */
	JSONObject findUserRedPacketCount(String uId);

	/**
	 * 点击红包操作
	 * @param uId 用户id
	 * @author suyb
	 * @date 2018-05-11 09:51:23
	 * @return JSONObject
	 */
	JSONObject clickRedPacketDo(String uId);

	/**
	 * 查询用户是否有未查看红包
	 * 
	 * @param uId 用户id
	 * @return JSONObject
	 */
	JSONObject notCheckedRedPacket(String uId);

	/**
	 * 未查看-->已查看
	 * 
	 * @param uId 用户id
	 */
	void checkedRedPacket(String uId);

	/**
	 * 是否符合弹框要求
	 * 
	 * @param request 请求
	 * @return JSONObject
	 */
	JSONObject alertControl(String uId);

	PageInfo showCouponReminding(String uId);

}
