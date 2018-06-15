package com.mrbt.lingmoney.admin.controller.task;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.mrbt.lingmoney.admin.service.trading.TradingService;
import com.mrbt.lingmoney.mapper.TradingMapper;
import com.mrbt.lingmoney.model.Trading;

/**
 * @author syb
 * @date 2017年6月30日 下午3:17:47
 * @version 1.0
 * @description 自动更新支付超时交易数据状态
 **/
@Component
public class AutoCancelOvertimeTrading {

	@Autowired
	private TradingMapper tradingMapper;
	@Autowired
    private TradingService tradingService;
	
	/**
	 * 执行方法
	 */
	@Transactional
	public void autoCancelOvertimeTrading() {
		List<Trading> list = tradingMapper.selectPayFailList();

		if (list != null && list.size() > 0) {
			for (Trading trading : list) {
                tradingService.handelTimeoutTrading(trading);
            }
        }
    }

}
