package com.mrbt.lingmoney.admin.service.trading.impl;

import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mrbt.lingmoney.admin.service.base.CommonMethodService;
import com.mrbt.lingmoney.admin.service.trading.TradingBuyCallbackService;
import com.mrbt.lingmoney.admin.vo.trading.CallbackValidCode;
import com.mrbt.lingmoney.mapper.CustomQueryMapper;
import com.mrbt.lingmoney.mapper.ProductCustomerMapper;
import com.mrbt.lingmoney.mapper.ProductMapper;
import com.mrbt.lingmoney.mapper.ProductParamMapper;
import com.mrbt.lingmoney.mapper.TradingMapper;
import com.mrbt.lingmoney.model.ProductWithBLOBs;
import com.mrbt.lingmoney.model.Trading;
import com.mrbt.lingmoney.model.TradingExample;
import com.mrbt.lingmoney.utils.AppCons;
import com.mrbt.lingmoney.utils.DateUtils;

/**
 * 
 * TradingBuyCallbackService实现类
 *
 */
@Service
public class TradingBuyCallbackServiceImpl implements TradingBuyCallbackService {

	@Autowired
	private ProductParamMapper productParamMapper;

	@Autowired
	private TradingMapper tradingMapper;

	@Autowired
	private CustomQueryMapper customQueryMapper;

	@Autowired
	private ProductCustomerMapper productCustomerMapper;

	@Autowired
	private ProductMapper productMapper;

	@Autowired
	private CommonMethodService commonMethodService;

	@Override
	public CallbackValidCode manualEstablish(int pid, Integer days, boolean auto) {
		// 计算日期，如果是自动后台的成立，则选择今日，如果是后台手动点击成立，则按照时间算
		Date valueDt = auto ? new Date() : DateUtils.getTradeDate(new Date(), commonMethodService.findHoliday());
		try {
			// 查找最后一次交易信息
			Trading lastTrading = findTradingByRuleOk(pid);
			if (lastTrading != null) {
				// 添加最小赎回的天数
				lastTrading.setValueDt(valueDt);
				// 设置最小卖出时间
				lastTrading.setMinSellDt(DateUtils.addDay(valueDt, days));
				// 更新交易表
                if (updateProductByRun(lastTrading, true)) {
                    return CallbackValidCode.callback_success;
                }

			} else {
				// 如果没找到最后一条消息，则自动改状态为失败
				// 更新交易表
                if (updateProductForFail(pid)) {
                    return CallbackValidCode.callback_success;
                }
			}
            return CallbackValidCode.callback_product_error;

		} catch (Exception ex) {
			ex.printStackTrace();
            return CallbackValidCode.callback_product_error;
		}
	}

    /**
     * 募集失败，产品作废
     * 
     * @param pid
     *            pid
     * @return 返回数据
     */
	private boolean updateProductForFail(int pid) {
		ProductWithBLOBs record = new ProductWithBLOBs();
        record.setStatus(AppCons.PRODUCT_STATUS_ABANDON);
		record.setId(pid);
		return productMapper.updateByPrimaryKeySelective(record) > 0 ? true : false;
	}

	/**
	 * 更新交易表
	 * 
	 * @param lastTrading
	 *            lastTrading
	 * @param manual
	 *            manual
	 * @return 返回数据
	 */
	public boolean updateProductByRun(final Trading lastTrading, final boolean manual) {
		// 更新交易表
		Map<String, Object> map2 = new HashMap<String, Object>();
        // 产品状态 改为项目运行中
        map2.put("param1", AppCons.PRODUCT_STATUS_RUNNING);
		// 设置产品计息日
		map2.put("param2", new Timestamp(lastTrading.getValueDt().getTime()));
		// 设置产品成立日x,如果manual为真，手动成立，计息日（节假日顺延）为成立时间，如果为假，取最后一次交易的时间为成立时间
		map2.put("param3",
				new Timestamp(manual ? lastTrading.getValueDt().getTime() : lastTrading.getBuyDt().getTime()));
		// 设置交易表状态为成功
		map2.put("param4", AppCons.BUY_OK);
		// 设置交易信息表状态为成功
		map2.put("param5", AppCons.BUY_OK);
		// 设置交易表中的计息日
		map2.put("param6", new Timestamp(lastTrading.getValueDt().getTime()));
		// 设置交易表中可赎回日期
		map2.put("param7", new Timestamp(lastTrading.getMinSellDt().getTime()));
		// 设置固定类交易信息表中的结息日
		map2.put("param8", new Timestamp(lastTrading.getMinSellDt().getTime()));
		// 设置固定类交易信息表中的等待时长
		map2.put("param9", new Timestamp(lastTrading.getValueDt().getTime()));
		// 设置利息的时长
		map2.put("param10", DateUtils.dateDiff(lastTrading.getValueDt(), lastTrading.getMinSellDt()));
		// 设置利息计算中的等待时长
		map2.put("param11", new Timestamp(lastTrading.getValueDt().getTime()));
		// 设置产品id
		map2.put("param12", lastTrading.getpId());
		// 查询状态为筹集中的产品
		map2.put("param13", AppCons.PRODUCT_STATUS_READY);
		// 查询交易状态为冻结中（购买成功，但是未成立）
		map2.put("param14", AppCons.BUY_OK);
		int ok = productCustomerMapper.updateProductByRun(map2);
		return ok > 0 ? true : false;
	}

	// /**
	// * 更新产品为运行状态，并更新详细交易表中的结息日
	// *
	// * @param trading
	// * @return
	// */
	// public boolean updateProductByRun(final Trading trading, final boolean
	// auto) {
	//
	// Map<String, Object> prepareMap = new HashMap<String, Object>();
	// // 产品状态 改为项目筹集完毕,等待放款
	// prepareMap.put("set_p_status", AppCons.PRODUCT_STATUS_FINISH);
	// // 设置产品计息日
	// prepareMap.put("set_p_value_dt", new
	// Timestamp(trading.getValueDt().getTime()));
	// // 设置产品成立日x,如果auto为真，当天计息，如果为假，取最后一次交易日的时间为成立时间
	// prepareMap.put("set_p_establish_dt",new Timestamp(auto ?
	// trading.getValueDt().getTime() : trading.getBuyDt().getTime()));
	// // 设置交易表中的计息日
	// prepareMap.put("set_t_value_dt", new
	// Timestamp(trading.getValueDt().getTime()));
	// // 设置交易表中可赎回日期
	// prepareMap.put("set_t_min_sell_dt", new
	// Timestamp(trading.getMinSellDt().getTime()));
	// // 设置固定类交易信息表中的结息日
	// prepareMap.put("set_tfi_expiry_dt", new
	// Timestamp(trading.getMinSellDt().getTime()));
	// // 设置固定类交易信息表中的等待时长
	// prepareMap.put("set_tfi_w_time", new
	// Timestamp(trading.getValueDt().getTime()));
	// // 设置利息的时长
	// prepareMap.put("set_time1", DateUtils.dateDiff(trading.getValueDt(),
	// trading.getMinSellDt()));
	// // 设置利息计算中的等待时长
	// prepareMap.put("set_time2",new
	// Timestamp(trading.getValueDt().getTime()));
	// prepareMap.put("p_id", trading.getpId());
	// prepareMap.put("p_status", AppCons.PRODUCT_STATUS_READY);
	// prepareMap.put("t_status", AppCons.BUY_OK);
	//
	//
	// int ok = 0;
	// return ok > 0 ? true : false;
	// }

	/**
	 * 根据pid查找最后一条信息
	 * 
	 * @param pid
	 *            pid
	 * @return 返回数据
	 */
	public Trading findTradingByRuleOk(Integer pid) {
		TradingExample example = new TradingExample();
		example.createCriteria().andPIdEqualTo(pid).andStatusEqualTo(AppCons.BUY_OK);
		example.setOrderByClause("buy_dt desc");
		example.setLimitStart(0);
		example.setLimitEnd(1);

		List<Trading> list = tradingMapper.selectByExample(example);
		if (list != null && list.size() > 0) {
			return list.get(0);
		}
		return null;
	}

}
