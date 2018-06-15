package com.mrbt.lingmoney.admin.controller.task;

import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.mrbt.lingmoney.admin.service.schedule.ScheduleService;
import com.mrbt.lingmoney.mapper.ProductCustomerMapper;
import com.mrbt.lingmoney.mapper.ProductMapper;
import com.mrbt.lingmoney.model.Product;
import com.mrbt.lingmoney.model.ProductExample;
import com.mrbt.lingmoney.utils.AppCons;
import com.mrbt.lingmoney.utils.ResultParame.ResultNumber;

/**
 * 产品自动结束定时任务
 * 
 * @author Administrator
 *
 */
@Component("autoUpdateProductEndStatus")
public class AutoUpdateProductEndStatus {
	
	private static final Logger LOGGER = LogManager.getLogger(AutoUpdateProductEndStatus.class);
	
	@Autowired
	private ProductCustomerMapper productCustomerMapper;
	
	@Autowired
	private ProductMapper productMapper;
	
	@Autowired
	private ScheduleService scheduleService;
	
	/**
	 * 执行方法
	 */
	public void autoUpdateProductEndStatus() {
		LOGGER.info("\n产品自动结束定时任务开始执行...");
		scheduleService.saveScheduleLog(null, "产品自动结束定时任务开始执行...", null);

		// 查询Product表，查询出状态为3（项目汇款中/项目到期）的记录 或者 15 收益明细提交成功数据
		ProductExample example = new ProductExample();
		List<Integer> paramList = new ArrayList<Integer>();
		paramList.add(AppCons.PRODUCT_STATUS_REPAYMENT);
		paramList.add(AppCons.PRODUCT_STATUS_INCOME_DETAIL_SUCCESS);
		example.createCriteria().andStatusIn(paramList);
		List<Product> result = productMapper.selectByExample(example);
		if (result != null && result.size() > 0) {
			for (Product product : result) {
				if (product.getpType() == ResultNumber.TWO.getNumber()) {
					// 华兴产品,状态为15才更新
					if (product.getStatus() == AppCons.PRODUCT_STATUS_INCOME_DETAIL_SUCCESS) {
						endProduct(product.getId());
					}
				} else {
					// 老产品，状态为3才更新
					if (product.getStatus() == AppCons.PRODUCT_STATUS_REPAYMENT) {
						endProduct(product.getId());
					}
				}
			}
		}

		LOGGER.info("产品自动结束定时任务执行完毕。");
		scheduleService.saveScheduleLog(null, "产品自动结束定时任务执行完毕", null);
	}

	/**
	 * @param pid	产品ID
	 */
	private void endProduct(Integer pid) {
		// 操作记录
		StringBuffer resultRecord = new StringBuffer(pid + "号产品修改为项目结束");
		int updateResult = productCustomerMapper.updateProductEndStatus(pid);
		if (updateResult > 0) {
			LOGGER.info(resultRecord.append("， 更新产品状态成功。").toString());
			scheduleService.saveScheduleLog(null, resultRecord.append("， 更新产品状态成功。").toString(), null);

		} else {
			LOGGER.info(resultRecord.append("， 更新产品状态失败。").toString());
			scheduleService.saveScheduleLog(null, resultRecord.append("， 更新产品状态失败。").toString(), "修改产品状态失败");
		}
	}

}
