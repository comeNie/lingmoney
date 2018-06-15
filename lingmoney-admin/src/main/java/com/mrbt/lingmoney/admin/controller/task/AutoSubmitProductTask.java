package com.mrbt.lingmoney.admin.controller.task;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.mrbt.lingmoney.admin.service.product.impl.AutoSubmitProductServiceImpl;

/**
 * 自动提交产品
 * 
 * @author lihq
 * @date 2017年7月31日 下午8:43:45
 * @description 1，查询approval=1(已提交审核)status=1(募集中)根据batch(批次号)分组的List
 *              <String>，所有需要审核通过的批次。
 *              2，遍历批次号，根据批次号查询approval=2(审核通过)status=1(募集中)的产品个数，如果大于0则跳过不管；
 *              否则修改第一条，将产品表approval设为2(审核通过)，将对应产品提交表状态设为审核通过
 * @version 1.0
 * @since 2017-04-12
 */
@Component
public class AutoSubmitProductTask {

	private static final Logger LOGGER = LogManager.getLogger(AutoSubmitProductTask.class);

	@Autowired
	private AutoSubmitProductServiceImpl autoSubmitProductService;

	/**
	 * 自动提交产品
	 */
	public void autoSubmitProductTask() {
		LOGGER.info("自动提交产品定时任务开始执行");
		try {
			autoSubmitProductService.autoSubmitProduct();
		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.error("自动提交产品定时任务执行失败，失败原因" + e);
		} finally {
			LOGGER.info("自动提交产品定时任务结束执行");
		}
	}

}
