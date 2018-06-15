package com.mrbt.lingmoney.mapper;

import java.util.List;
import java.util.Map;

import com.mrbt.lingmoney.model.ActivityProductCustomer;
import com.mrbt.lingmoney.model.GiftExchangeInfoVo1;
import com.mrbt.lingmoney.model.ProductCustomer;
import com.mrbt.lingmoney.utils.PageInfo;

/**
 * 活动
 *
 * @author lihq
 * @date 2017年4月24日 上午11:36:42
 * @description
 * @version 1.0
 * @since 2017-04-12
 */
public interface ActivityProductCustomerMapper {
	/**
	 * 活动列表
	 * 
	 * @description
	 * @param pageInfo
	 * @return
	 */
	List<ActivityProductCustomer> selectActivityList(PageInfo pageInfo);

	/**
	 * 活动个数
	 * 
	 * @description
	 * @param pageInfo
	 * @return
	 */
	Integer selectActivityCount(PageInfo pageInfo);

	/**
	 * 查询活动参数人数
	 * 根据产品ID查询交易成功的数据记录（trading.status in 1,2,3,4）
	 * 
	 * @param pids
	 * @return
	 */
	Integer selectActivityTradingCount(String[] pids);

	/**
	 * 获取周期内活动
	 * 
	 * @description
	 * @param pids
	 * @return
	 */
	public List<ProductCustomer> selectActivityProductList(String[] pids);

	/**
	 * 活动产品说明
	 * 
	 * @description
	 * @param map
	 * @return
	 */
	String selectActivityDesc(Map<String, Object> map);

	/**
	 * 拉新活动第二季根据用户ID查询拉新活动记录信息
	 * 
	 * @param uid
	 *            用户ID
	 * @param pid
	 *            随心取ID
	 * @param startDate
	 *            活动开始时间
	 * @param endDate
	 *            活动结束时间
	 * @return
	 */
	public List<GiftExchangeInfoVo1> queryRecommendersByuId(Map<String, Object> map);

	/**
	 * 根据id或acturl查询活动
	 * 
	 * @description
	 * @param pageInfo
	 * @return
	 */
	ActivityProductCustomer selectActivityInfo(PageInfo pageInfo);

	/**
	 * 根据活动关键字查询手机端或pc端活动详情(h5)
	 * 
	 * @param map
	 * @return
	 */
	ActivityProductCustomer queryActivityInfoH5(Map<String, Object> map);

}
