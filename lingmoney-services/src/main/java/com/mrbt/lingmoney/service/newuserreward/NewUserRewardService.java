package com.mrbt.lingmoney.service.newuserreward;

import com.alibaba.fastjson.JSONObject;

/**
 * 新手福利
 * @author luox
 * @Date 2017年6月26日
 */
public interface NewUserRewardService {

	/**
	 * 查询有效的新手福利
	 * @author syb
	 * @date 2017年8月24日 下午5:25:24
	 * @version 1.0
	 * @param clientType
	 *            客户端类型，如果为2 多返回图片信息
	 * @return json
	 *
	 */
	JSONObject getNewuserReward(int clientType);


}
