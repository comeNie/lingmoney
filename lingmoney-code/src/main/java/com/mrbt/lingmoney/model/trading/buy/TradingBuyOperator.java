package com.mrbt.lingmoney.model.trading.buy;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.UUID;

import org.springframework.stereotype.Component;

import com.mrbt.lingmoney.model.trading.service.TradingBuyService;
import com.mrbt.lingmoney.model.trading.valid.BuyValidCode;
import com.mrbt.lingmoney.utils.AppCons;
import com.mrbt.lingmoney.utils.BigDecimalUtils;
import com.mrbt.lingmoney.utils.ProductUtils;

/**
 * 购买的操作类
 * 
 * @author airgilbert
 *
 */
@Component
public class TradingBuyOperator {
	/**
	 * 购买的辅助类
	 */
	TradingBuyHelper helper;

	protected HashMap<String, TradingBuyService> services;

	// HashMap<String, TradingBuyService> services = new HashMap<String,
	// TradingBuyService>();

	public TradingBuyOperator() {
	}

	public TradingBuyOperator(TradingBuyHelper helper, HashMap<String, TradingBuyService> services) {
		this.services = services;
		this.helper = helper;
	}

	/**
	 * 填写购买金额，验证购买金额的正确性
	 * 
	 * @param buyMoney
	 *            购买的金额
	 * @param minMoney
	 *            最低金额
	 * @param increaMoney
	 *            递增金额
	 * @param c_pre_r
	 *            预收去的手续费，如果没有则设置为null
	 * @param isValid
	 *            是否需要验证，扫钱功能isValid是false
	 */
	public TradingBuyOperator buyMoney(BigDecimal buyMoney, BigDecimal c_pre_r, boolean isValid) {
		if (helper.getValidCode() == null) {
			BigDecimal zero = new BigDecimal(0);
			if (isValid) {
				BigDecimal minMoney = new BigDecimal(helper.getProduct().getMinMoney());
				BigDecimal increaMoney = new BigDecimal(helper.getProduct().getIncreaMoney());
				if (increaMoney.compareTo(zero) == 0) {
					increaMoney = new BigDecimal(0.01);
				}

				if (minMoney.compareTo(zero) == 0 || increaMoney.compareTo(zero) == 0) {
					helper.setValidCode(BuyValidCode.product_money_is_zero);
				}

				// 随心取
				if (helper.getProduct().getId().intValue() == ProductUtils.getIntContent("takeHeart_pID")) {

					if (!BigDecimalUtils.greatAndEqu(buyMoney, increaMoney)) {
						helper.setValidCode(BuyValidCode.less_min_money);
					}

				} else {

					if (!BigDecimalUtils.greatAndEqu(buyMoney, minMoney)) {
						helper.setValidCode(BuyValidCode.less_min_money);
					}
				}

				if (!BigDecimalUtils.divideByInt(buyMoney, increaMoney)) {
					helper.setValidCode(BuyValidCode.less_increa_money);
				}
			} else {
				if (buyMoney.compareTo(zero) == 0) {
					helper.setValidCode(BuyValidCode.less_min_money);
				}
			}
			helper.getTrading().setBuyMoney(buyMoney);
			helper.getTrading().setFinancialMoney(buyMoney);
			zero = null;
		}
		return this;
	}

	/**
	 * 自动扫钱标志
	 *
	 * @return
	 */
	public TradingBuyOperator autoWalletBatch() {
		if (helper.getTrading() == null) {
			throw new IllegalArgumentException("build错误，请先运行ProductAndUser");
		}
		if (helper.getValidCode() == null) {
			helper.getTrading().setWalletBatch(AppCons.WALLET_BATCH_FLAG);
		}
		return this;
	}

	/**
	 * 是否自动付款
	 *
	 * @param flag
	 * @return
	 */
	public TradingBuyOperator autoPay(Integer flag) {
		if (helper.getProduct() == null) {
			throw new IllegalArgumentException("build错误，请先运行ProductAndUser");
		}
		if (helper.getValidCode() == null) {
			helper.getTrading().setAutoPay(flag);
		}
		return this;
	}

	/**
	 * 创建bizCode码
	 *
	 * @param platFormNo
	 * @param buyType
	 *            1为快捷支付，2为网银支付
	 */
	private void buildBizCode() {
		String bizCode = UUID.randomUUID().toString().replace("-", "").substring(15) + System.currentTimeMillis();

		System.out.println("生成订单号为:" + bizCode + "\t" + bizCode.length());

		helper.getTrading().setBizCode(bizCode.toString());
	}

	/**
	 * 创建购买任务
	 *
	 * @param buyType
	 *
	 * @return
	 */
	public boolean builder() {
		System.out.println(helper.getTrading().getpCode() + "\t" + ProductUtils.getContent("product_code"));
		// 判断如果validCode==null
		if (helper.getValidCode() == null) {
			helper.getTrading().setStatus(AppCons.BUY_STATUS);
			buildBizCode();
			// 如果产品是钱包模式的零钱宝
			if (helper.getProduct().getpModel() == AppCons.PRODUCT_P_MODE_WALLET) {
				return services.get("walletService").buy(helper);
			} // 产品的推广模式（新手标）
			else if (helper.getTrading().getpCode().equals(ProductUtils.getContent("product_code"))) {
				if (helper.checkNewBigProduct()) {
					return services.get("newBigService").buy(helper);
				}
			}

			// 需要添加随心取

			else if (helper.getTrading().getpId() == ProductUtils.getIntContent("takeHeart_pID")) {
				if (helper.checkFixNoRule()) {
					return services.get("takeHeartService").buy(helper);
				}
			}
			// 稳赢宝系列
			else if (helper.getTrading().getPcType() == AppCons.FIX_FLAG && helper.getProduct().getRule() != null
					&& helper.getProduct().getRule() != AppCons.RULE_NONE) {
				if (helper.checkFixByRule()) {
					return services.get("fixByRuleService").buy(helper);
				}
			}
			// 活钱宝
			else if (helper.getTrading().getPcType() == AppCons.FIX_FLAG) {
				if (helper.checkFixNoRule()) {
					return services.get("fixNoRuleService").buy(helper);
				}
			} else {
				helper.setValidCode(BuyValidCode.buy_type_error);
			}
		}
		return false;
	}

	public BuyValidCode getValidCode() {
		return helper.getValidCode();
	}

	public Integer getTid() {
		return helper.getTrading().getId();
	}

	public Integer getInfoId() {
		return helper.getTrading().getInfoId();
	}

	public String getBizCode() {
		return helper.getTrading().getBizCode();
	}

	public TradingBuyHelper getHelper() {
		return helper;
	}

	public void setHelper(TradingBuyHelper helper) {
		this.helper = helper;
	}

}
