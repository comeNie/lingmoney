package com.mrbt.lingmoney.admin.service.takeHeart;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.mrbt.lingmoney.model.TakeheartCategory;
import com.mrbt.lingmoney.model.Trading;

public interface TakeHeartInterestService {
	/**
	 * 获取当日需要结息的记录
	 * 
	 */
	public List<TakeheartCategory> findTakeheartCategory();
	/**
	 * 每日跑批开始记录到redis
	 * 
	 * @return
	 */
	public boolean saveRedisTakeHeart();
	/**
	 * 获取需要结息的记录
	 */
	public List<Trading> findTakeheartTrading(int pid);
	/**
	 * 更改利息类别状态
	 * @return
	 */
	public boolean updateTakeheartCategoryStatus(int cid,String name);
	/**
	 * 每日跑批结束删除redis
	 * 
	 * @return
	 */
	public boolean deleteRedisTakeHeart();
	
	/**
	 * 获取>224天的随心取记录
	 */
    public List<Trading> findTakeheartValueDtTrading();
	/**
	 * 获取用户的随心取redis记录，查看是否有正在处理的交易
	 * 
	 * @return
	 */
	public boolean getRedisTakeHeart(String uid);
	
	/**
	 * 获取随心取利率
	 * 
	 * @return
	 */
	public Map<String, Object> findTakeheartFixRate(Map<String, Object> fixRateParams) ;
	 
	/**
	 * 计息操作
	 */
	public void takeheartInterest(Trading trading,BigDecimal interest,Date reteDate);
}
