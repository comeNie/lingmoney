package com.mrbt.lingmoney.model.trading.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import com.mrbt.lingmoney.model.schedule.SellTrading;
import com.mrbt.lingmoney.model.trading.buy.TradingCallbackHelper;

@Component
public abstract class TradingCallbackService {
	@Autowired
	protected JdbcTemplate jdbcTemplate;

	public abstract Boolean callbackOk(TradingCallbackHelper helper);

	public abstract Boolean callbackFail(TradingCallbackHelper helper);

	public abstract Boolean callbackFroken(TradingCallbackHelper helper);
	
	public abstract Boolean checkInfoExistUpdate(TradingCallbackHelper helper,
			String callback_type, int status);

	/**
	 * 返回卖出的交易类
	 * 
	 * @param id
	 *            交易id
	 * @return
	 */
	public SellTrading findTrading(int id) {
		String sql = "select t.*,p.rule,p.fees,p.fees_rate,p.f_yield,p.unit_time,p.prior_money,p.reach_money,p.f_time,p.exception_rate,p.p_model,p.platform_user_no from trading t,product p where t.id=? and t.p_id = p.id";
		BeanPropertyRowMapper<SellTrading> rowMapper = BeanPropertyRowMapper.newInstance(SellTrading.class);
		List<SellTrading> list = jdbcTemplate.query(sql, rowMapper, id);
		if (list != null && list.size() > 0) {
			return list.get(0);
		}
		return null;
	}
}
