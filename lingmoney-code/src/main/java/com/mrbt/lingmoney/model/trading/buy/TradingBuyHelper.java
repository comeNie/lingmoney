package com.mrbt.lingmoney.model.trading.buy;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.springframework.stereotype.Component;

import com.mrbt.lingmoney.model.Product;
import com.mrbt.lingmoney.model.Trading;
import com.mrbt.lingmoney.model.TradingFixInfo;
import com.mrbt.lingmoney.model.trading.TradingBase;
import com.mrbt.lingmoney.model.trading.valid.BuyValidCode;
import com.mrbt.lingmoney.utils.AppCons;
import com.mrbt.lingmoney.utils.DateUtils;
import com.mrbt.lingmoney.utils.ProductUtils;

@Component
public class TradingBuyHelper {

	public Trading trading;

	protected TradingBase tradingBase;
	protected Product product;
	protected BuyValidCode validCode = null;
	protected HashMap<String, String> holiday = null;

	public TradingBuyHelper() {

	}

	public TradingBuyHelper(String pCode) {
		createTradingBase(pCode);
	}

	/**
	 * 购买的交易流水号
	 */
	protected String bizCode;
	/**
	 * 详细表的id
	 */
	protected Integer infoId;

	protected Integer tid;

	public Trading getTrading() {
		return trading;
	}

	public void setTrading(Trading trading) {
		this.trading = trading;
		if (product != null) {
			this.trading.setProduct(product);
		}
	}

	public TradingBase getTradingBase() {
		return tradingBase;
	}

	public void setTradingBase(TradingBase tradingBase) {
		this.tradingBase = tradingBase;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public BuyValidCode getValidCode() {
		return validCode;
	}

	public void setValidCode(BuyValidCode validCode) {
		this.validCode = validCode;
	}

	public HashMap<String, String> getHoliday() {
		return holiday;
	}

	public void setHoliday(HashMap<String, String> holiday) {
		this.holiday = holiday;
	}

	public String getBizCode() {
		return bizCode;
	}

	public void setBizCode(String bizCode) {
		this.bizCode = bizCode;
	}

	public Integer getInfoId() {
		return infoId;
	}

	public void setInfoId(Integer infoId) {
		this.infoId = infoId;
	}

	public Integer getTid() {
		return tid;
	}

	public void setTid(Integer tid) {
		this.tid = tid;
	}

	/**
	 * 根据pCode获取相应的信息，包括pc_id,pc_type,pc_fix_type,p_id,
	 * 
	 * @param pCode
	 * @return
	 */
	public void createTradingBase(String pCode) throws IllegalArgumentException {
		String pc_id = null, pc_type = null, pc_fix_type = null, p_id = null;
		pc_id = pCode.substring(2, 6);
		if (!NumberUtils.isNumber(pc_id) || NumberUtils.toInt(pc_id) <= 0) {
			pc_id = null;
			throw new IllegalArgumentException("产品码错误,产品分类错误");
		}
		pc_type = pCode.substring(8, 9);
		if (!NumberUtils.isNumber(pc_type) || NumberUtils.toInt(pc_type) < 0 || NumberUtils.toInt(pc_type) > 1) {
			pc_id = pc_type = null;
			throw new IllegalArgumentException("产品码错误,产品分类标识错误");
		}

		pc_fix_type = pCode.substring(9, 10);
		if (NumberUtils.toInt(pc_type) == AppCons.FIX_FLAG && (!NumberUtils.isNumber(pc_fix_type)
				|| NumberUtils.toInt(pc_fix_type) < 1 || NumberUtils.toInt(pc_fix_type) > 2)) {
			pc_id = pc_type = pc_fix_type = null;
			throw new IllegalArgumentException("产品码错误,产品分类子类标识错误");
		}

		p_id = pCode.substring(pCode.length() - 4);
		if (!NumberUtils.isNumber(p_id) || NumberUtils.toInt(p_id) <= 0) {
			pc_id = pc_type = pc_fix_type = p_id = null;
			throw new IllegalArgumentException("产品码错误,产品未找到");
		}
		tradingBase = new TradingBase(NumberUtils.toInt(pc_id), NumberUtils.toInt(pc_type),
				NumberUtils.toInt(pc_fix_type), NumberUtils.toInt(p_id));
	}

	/**
	 * 检测规则,如果产品有规则，并且产品不再筹集期
	 * 
	 * @return
	 */
	public boolean checkRule() {
		if (product.getRule() != null && product.getRule().intValue() != AppCons.RULE_NONE
				&& product.getStatus().intValue() != AppCons.PRODUCT_STATUS_READY) {
			validCode = BuyValidCode.rule_status_error;
			return false;
		}
		tradingBase.setRule(product.getRule());
		return true;
	}

	/**
	 * 检测新手标
	 * 
	 * @return
	 */
	public boolean checkNewBigProduct() {
		// 中粮项目为20000，领钱为1000
		// if (trading.getBuyMoney().compareTo(new BigDecimal(1000)) > 0) {
		if (trading.getBuyMoney().compareTo(ProductUtils.getBigDecimalContent("product_limit")) > 0) {
			this.validCode = BuyValidCode.product_money_big;
			return false;
		}
		fix(ProductUtils.getIntContent("product_days"), ProductUtils.getBigDecimalContent("product_rate"));
		return true;
	}

	/**
	 * 创建带规则的固定类交易
	 *
	 * @return
	 */
	public boolean checkFixByRule() {
		if (tradingBase.getPc_fix_type().intValue() == AppCons.FIX_FLAG_SUB_AREA) {
			fix(product.getlTime(), null);
		} else if (tradingBase.getPc_fix_type().intValue() == AppCons.FIX_FLAG_SUB_FIX) {
			fix(product.getfTime(), product.getfYield());
		}
		rule(product.getStDt(), product.getEdDt(), product.getPriorMoney().intValue(),
				product.getReachMoney().intValue());
		// 判断如果validCode==null
		if (this.validCode == null) {
			return true;
		}
		return false;
	}

	/**
	 * 创建不带规则的固定类交易
	 *
	 * @return
	 */
	public boolean checkFixNoRule() {
		if (tradingBase.getPc_fix_type().intValue() == AppCons.FIX_FLAG_SUB_AREA) {
			fix(product.getlTime(), null);
		} else if (tradingBase.getPc_fix_type().intValue() == AppCons.FIX_FLAG_SUB_FIX) {
			fix(product.getfTime(), product.getfYield());
		}
		// 判断如果validCode==null
		if (this.validCode == null) {
			return true;
		}
		return false;
	}

	/**
	 * 计算利息，所有的计息方式都是按天计息
	 *
	 * @return
	 */
	private BigDecimal createFixInterest(BigDecimal pp, int hold, BigDecimal rate) {
		return (pp.multiply(rate).multiply(new BigDecimal(hold))).divide(new BigDecimal(365), 2,
				BigDecimal.ROUND_HALF_UP);
	}

	/**
	 * 固定期限固定汇率
	 *
	 * @param hold
	 *            持有的天数,如果为0，则默认+1
	 * @param fixRate
	 *            固定的汇率
	 * @return
	 */
	private void fix(Integer hold, BigDecimal fixRate) {
		Date minSellDt = null;
		if (trading.getExist() == null) {
			if (product.getUnitTime() == AppCons.UNIT_TIME_DAY) {
				minSellDt = DateUtils.addDay(trading.getValueDt(), hold);
			} else if (product.getUnitTime() == AppCons.UNIT_TIME_MONTH) {
				minSellDt = DateUtils.addMonth(trading.getValueDt(), hold);
			} else if (product.getUnitTime() == AppCons.UNIT_TIME_WEEK) {
				minSellDt = DateUtils.addWeek(trading.getValueDt(), hold);
			} else {
				throw new IllegalArgumentException("产品单位时间错误，请更正");
			}
		}
		trading.setMinSellDt(minSellDt);
		TradingFixInfo tfi = new TradingFixInfo();
		tfi.setBuyDt(trading.getBuyDt());
		tfi.setFinancialMoney(trading.getFinancialMoney());
		tfi.setValueDt(trading.getValueDt());
		tfi.setStatus(AppCons.BUY_STATUS);
		tfi.setBizCode(StringUtils.isNotBlank(trading.getBizCode()) ? trading.getBizCode() : "");
		if (fixRate != null && fixRate.compareTo(new BigDecimal(0)) > 0) {
			tfi.setInterestRate(fixRate);
			// 不是无限期的，有固定时长的
			if (hold != null && hold > 0) {
				tfi.setfTime(hold);
				if (product.getRule() == null || product.getRule() == AppCons.RULE_NONE) {
					tfi.setInterest(
							createFixInterest(trading.getFinancialMoney(), tfi.getfTime(), tfi.getInterestRate()));
					tfi.setExpiryDt(minSellDt);
				}
			}
		}
		trading.setFixInfo(tfi);
	}

	/**
	 * 规则的验证
	 * 
	 * @param start
	 * @param end
	 * @param prior_money
	 * @param reach_money
	 */
	private boolean rule(Date start, Date end, Integer prior_money, Integer reach_money) {
		// 对于日期的验证
		if (tradingBase.getRule().intValue() == AppCons.RULE_DATE
				|| tradingBase.getRule().intValue() == AppCons.RULE_MONEY_DATE) {
			if (start == null || end == null) {
				throw new IllegalArgumentException("带日期限制的规则，开始时间和结束时间不能为空");
			}
			if (trading.getBuyDt().getTime() > end.getTime() && trading.getBuyDt().getTime() < start.getTime()) {
				setValidCode(BuyValidCode.rule_dt_error);
				return false;
			}
		}
		// 金额的限制
		if (tradingBase.getRule().intValue() == AppCons.RULE_MONEY
				|| tradingBase.getRule().intValue() == AppCons.RULE_MONEY_DATE) {
			if (prior_money == null || prior_money.intValue() <= 0 || reach_money == null
					|| reach_money > prior_money) {
				throw new IllegalArgumentException("带金额限制的规则，筹备金额不能<=0,以筹金额不能>筹备金额");
			}
			// 如果筹备金额大于限制金额，则返回错误
			if (new BigDecimal(reach_money).add(trading.getBuyMoney()).compareTo(new BigDecimal(prior_money)) > 0) {
				setValidCode(BuyValidCode.rule_money_error);
				return false;
			}
			trading.setwValueDt(trading.getValueDt());
			trading.setValueDt(null);
			trading.setMinSellDt(null);
		}
		return true;
	}

}
