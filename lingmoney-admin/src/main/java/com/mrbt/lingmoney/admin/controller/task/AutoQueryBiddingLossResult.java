package com.mrbt.lingmoney.admin.controller.task;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.mrbt.lingmoney.admin.service.bank.HxBiddingLossService;
import com.mrbt.lingmoney.mapper.HxBiddingLossRecordMapper;
import com.mrbt.lingmoney.model.HxBiddingLossRecord;
import com.mrbt.lingmoney.model.HxBiddingLossRecordExample;

/**
 * @author syb
 * @date 2017年7月10日 上午9:32:50
 * @version 1.0
 * @description 自动查询流标结果
 **/
@Component
public class AutoQueryBiddingLossResult {
	private static final Logger LOGGER = LogManager.getLogger(AutoQueryBiddingLossResult.class);

	@Autowired
	private HxBiddingLossService hxBiddingLossService;
	@Autowired
	private HxBiddingLossRecordMapper hxBiddingLossRecordMapper;

	/**
	 * 执行方法
	 */
	public void queryBiddingLossResult() {
		LOGGER.info("查询流标结果定时任务开始执行...");
        HxBiddingLossRecordExample example = new HxBiddingLossRecordExample();
        example.createCriteria().andStateEqualTo(0).andTypeEqualTo(0);

        List<HxBiddingLossRecord> list = hxBiddingLossRecordMapper.selectByExample(example);
        if (list != null && list.size() > 0) {
            for (HxBiddingLossRecord biddingLoss : list) {
                try {
                    hxBiddingLossService.queryBiddingLossResult("PC", biddingLoss.getLoanNo(),
                            "自动查询流标结果_" + System.currentTimeMillis() + "_");
                } catch (Exception e) {
                    LOGGER.error("自动查询流标结果失败，系统错误。\n{}", e.toString());
                    e.printStackTrace();
                }
			}
        }

		LOGGER.info("查询流标结果定时任务执行完毕...");
	}
}
