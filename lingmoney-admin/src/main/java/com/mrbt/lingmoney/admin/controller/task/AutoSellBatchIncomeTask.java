package com.mrbt.lingmoney.admin.controller.task;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.mrbt.lingmoney.admin.service.userincome.SellBatchIncomeService;
import com.mrbt.lingmoney.model.SellBatchInfo;

/**
 * 自动检测前一天兑付详情，保存回签数据
 * 
 * @author suyibo
 * @date 创建时间：2017年12月19日
 */
@Component("autoSellBatchIncomeTask")
public class AutoSellBatchIncomeTask {
	private static final Logger LOGGER = LogManager.getLogger(AutoRebalanceTask.class);
	@Autowired
	private SellBatchIncomeService sellBatchIncomeService;

	/**
	 * 兑付回签定时任务
	 */
	public void autoSellBatchIncomeTask() {
		LOGGER.info("兑付回签开始");
		try {
			// 获取所有兑付详情
			List<SellBatchInfo> sellBatchInfoList = sellBatchIncomeService.findDayBeforeSellBatchInfo();
			if (!sellBatchInfoList.isEmpty()) {
				// 如果前一天兑付数据不为空，则将今天兑付回签数据存入缓存
				sellBatchIncomeService.saveSellBatchInfoToMongo(sellBatchInfoList);
				LOGGER.info("兑付回签结束");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
