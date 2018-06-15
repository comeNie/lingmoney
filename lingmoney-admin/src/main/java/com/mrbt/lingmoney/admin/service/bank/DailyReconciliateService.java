package com.mrbt.lingmoney.admin.service.bank;

import com.mrbt.lingmoney.bank.deal.dto.HxDailyReconciliateReqDto;
import com.mrbt.lingmoney.model.HxDailyReconciliate;
import com.mrbt.lingmoney.utils.PageInfo;

/**
 * 日终对账
 * @author YJQ
 *
 */
public interface DailyReconciliateService {
	/**
	 * 请求日终对账
	 * @author YJQ  2017年6月12日
	 * @param paymentDto	paymentDto
	 * @return	PageInfo
	 * @throws Exception	Exception
	 */
	HxDailyReconciliate request(HxDailyReconciliateReqDto paymentDto) throws Exception;

	/**
	 * 查询列表
	 * @author YJQ  2017年6月14日
	 * @param startDate	startDate
	 * @param endDate	endDate
	 * @param pageInfo	PageInfo
	 * @return	PageInfo
	 * @throws Exception	Exception
	 */
	PageInfo queryList(String startDate, String endDate, PageInfo pageInfo)  throws Exception;

	/**
	 * 计划任务，自动请求对账，每日下午6时自动请求，插入数据库
	 * @author YJQ  2017年7月1日
	 * @throws Exception	Exception
	 */
	void antoRequestDaily() throws Exception;

    /**
     * 新版日终对账请求
     * 
     * @author yiban
     * @date 2018年3月27日 下午2:13:52
     * @version 1.0
     * @param oprType U:上传 D：下载
     * @param logGroup 日志头
     * @return
     *
     */
    PageInfo newDailyReconciliateReqeust(String oprType, String logGroup);
}
