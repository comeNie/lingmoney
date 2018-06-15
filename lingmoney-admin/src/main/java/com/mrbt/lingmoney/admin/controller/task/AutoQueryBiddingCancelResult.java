package com.mrbt.lingmoney.admin.controller.task;

import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.mrbt.lingmoney.admin.service.bank.HxQueryTradingResultService;
import com.mrbt.lingmoney.admin.service.bank.SingleCancelBiddingService;
import com.mrbt.lingmoney.admin.service.schedule.ScheduleService;
import com.mrbt.lingmoney.mapper.HxBiddingCancelRecordMapper;
import com.mrbt.lingmoney.mapper.TradingMapper;
import com.mrbt.lingmoney.model.HxBiddingCancelRecord;
import com.mrbt.lingmoney.model.HxBiddingCancelRecordExample;
import com.mrbt.lingmoney.model.Trading;
import com.mrbt.lingmoney.utils.ResultParame.ResultNumber;

/**
 * @author syb
 * @date 2017年8月14日 上午10:48:33
 * @version 1.0
 * @description 自动查询撤标结果
 **/
@Component
public class AutoQueryBiddingCancelResult {
	private static final Logger LOGGER = LogManager.getLogger(AutoQueryBiddingLossResult.class);

	@Autowired
	private ScheduleService scheduleService;
	@Autowired
	private HxBiddingCancelRecordMapper hxBiddingCancelRecordMapper;
	@Autowired
	private SingleCancelBiddingService singleCancelBiddingService;
	@Autowired
	private HxQueryTradingResultService hxQueryTradingResultService;
	@Autowired
	private TradingMapper tradingMapper;

	/**
	 * 执行方法
	 */
	public void queryBiddingCancelResult() {
		LOGGER.info("自动查询撤标结果定时任务开始");

        HxBiddingCancelRecordExample example = new HxBiddingCancelRecordExample();
        example.createCriteria().andStatusEqualTo(0);
        List<HxBiddingCancelRecord> list = hxBiddingCancelRecordMapper.selectByExample(example);

        if (list != null && list.size() > 0) {
            for (HxBiddingCancelRecord record : list) {
                try {
                    // 此接口已废弃，用交易查询接口代替
                    // singleCancelBiddingService.querySingleBiddingCancelResult("PC",
                    // record.gettId(), "\n定时任务查询_");
                    Map<String, Object> resultMap = hxQueryTradingResultService.queryHxTradingResult(
                            record.getChannelFlow(), null, "\n撤标结果查询定时任务_");

                    if (resultMap != null) {
                        String resStatus = (String) resultMap.get("STATUS");

                        HxBiddingCancelRecord hxBiddingCancel = new HxBiddingCancelRecord();
                        hxBiddingCancel.setId(record.getId());
                        if ("S".equals(resStatus)) {
                            // 成功
                            hxBiddingCancel.setStatus(1);

                            Trading trading = tradingMapper.selectByPrimaryKey(record.gettId());

                            if (trading != null) {
                                singleCancelBiddingService.handelCancelBidding("\n撤标结果查询定时任务_", trading.getpCode(),
                                        trading.getuId(), trading.getId());
                            } else {
                                LOGGER.error("未查询到交易号{}的交易信息。", record.gettId());
                            }

                            int i = hxBiddingCancelRecordMapper.updateByPrimaryKeySelective(hxBiddingCancel);
                            if (i > 0) {
                                LOGGER.info("更新编号{}撤标结果成功", record.getId());
                            } else {
                                LOGGER.info("更新编号{}撤标结果失败", record.getId());
                            }

                        } else if ("F".equals(resStatus)) {
                            // 失败
                            hxBiddingCancel.setStatus(ResultNumber.TWO.getNumber());

                            int i = hxBiddingCancelRecordMapper.updateByPrimaryKeySelective(hxBiddingCancel);
                            if (i > 0) {
                                LOGGER.info("更新编号{}撤标结果成功", record.getId());
                            } else {
                                LOGGER.info("更新编号{}撤标结果失败", record.getId());
                            }

                        } else if ("X".equals(resStatus)) {
                            // 处理中
                            LOGGER.info("流水号为{}的撤标结果未知", record.getChannelFlow());
                        }

                    } else {
                        LOGGER.info("未查询到流水号为{}的撤标结果", record.getChannelFlow());
                    }

                } catch (Exception e) {
                    LOGGER.error("自动查询撤标结果定时任务异常：" + e.toString());

                    scheduleService.saveScheduleLog(null, "自动查询撤标结果定时任务异常", e.toString());
                    e.printStackTrace();
                }

			}
		}

		LOGGER.info("自动查询撤标结果定时任务结束");
	}
}
