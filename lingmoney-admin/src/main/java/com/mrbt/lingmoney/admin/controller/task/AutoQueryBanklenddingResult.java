package com.mrbt.lingmoney.admin.controller.task;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.mrbt.lingmoney.admin.service.bank.HxBankLendingService;
import com.mrbt.lingmoney.admin.service.schedule.ScheduleService;
import com.mrbt.lingmoney.mapper.HxBanklenddingInfoMapper;
import com.mrbt.lingmoney.model.HxBanklenddingInfo;
import com.mrbt.lingmoney.model.HxBanklenddingInfoExample;
import com.mrbt.lingmoney.utils.PageInfo;
import com.mrbt.lingmoney.utils.ResultParame.ResultInfo;

/**
 * @author syb
 * @date 2017年7月1日 下午2:04:51
 * @version 1.0
 * @description 自动查询放款结果
 **/
@Component
public class AutoQueryBanklenddingResult {
	private static final Logger LOGGER = LogManager.getLogger(AutoQueryBanklenddingResult.class);

	@Autowired
	private HxBanklenddingInfoMapper hxBanklenddingInfoMapper;
	@Autowired
	private HxBankLendingService hxBankLendingService;
	@Autowired
	private ScheduleService scheduleService;

	/**
	 * 执行方法
	 */
	public void handelBanklenddingResult() {
		LOGGER.info("自动查询放款结果定时任务开始执行。。。。");
		scheduleService.saveScheduleLog(null, "自动查询放款结果定时任务开始执行", null);

		HxBanklenddingInfoExample example = new HxBanklenddingInfoExample();
		example.createCriteria().andStateEqualTo(0);
		List<HxBanklenddingInfo> list = hxBanklenddingInfoMapper.selectByExample(example);

		if (list != null && list.size() > 0) {
			for (HxBanklenddingInfo lenddingInfo : list) {
				PageInfo pi = hxBankLendingService.queryBankLendingResult("PC", lenddingInfo.getLoanNo(),
						"\n自动查询放款结果_" + System.currentTimeMillis());

				if (pi.getCode() == ResultInfo.SUCCESS.getCode()) {
					LOGGER.info("处理成功");

					scheduleService.saveScheduleLog(null, "自动查询放款结果查询成功，放款记录编号：" + lenddingInfo.getId(), null);

				} else {
					LOGGER.info("自动查询放款结果查询失败，放款记录编号：{}，失败原因：{}", lenddingInfo.getId(), pi.getMsg());

				}
			}
		}

		LOGGER.info("自动查询放款结果定时任务执行完毕。。。。");
		scheduleService.saveScheduleLog(null, "自动查询放款结果定时任务开始执行", null);
	}

}
