package com.mrbt.lingmoney.admin.controller.task;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.mrbt.lingmoney.admin.controller.redPacket.RedPacketController;
import com.mrbt.lingmoney.admin.service.schedule.ScheduleService;
import com.mrbt.lingmoney.admin.service.trading.TradingBuyCallbackService;
import com.mrbt.lingmoney.admin.vo.trading.CallbackValidCode;
import com.mrbt.lingmoney.commons.cache.RedisSet;
import com.mrbt.lingmoney.commons.exception.PageInfoException;
import com.mrbt.lingmoney.mapper.CustomQueryMapper;
import com.mrbt.lingmoney.mapper.ProductCustomerMapper;
import com.mrbt.lingmoney.mapper.ProductMapper;
import com.mrbt.lingmoney.mapper.TradingMapper;
import com.mrbt.lingmoney.mapper.UsersMapper;
import com.mrbt.lingmoney.mapper.UsersPropertiesMapper;
import com.mrbt.lingmoney.model.Product;
import com.mrbt.lingmoney.model.ProductWithBLOBs;
import com.mrbt.lingmoney.utils.AppCons;
import com.mrbt.lingmoney.utils.ProductUtils;
import com.mrbt.lingmoney.utils.PropertiesUtil;
import com.mrbt.lingmoney.utils.ResultParame.ResultNumber;

/**
 * 产品自动成立定时任务
 * 
 * @author Administrator
 *
 */
@Component("autoSetUpTask")
public class AutoSetUpTask {

	private static final Logger LOGGER = LogManager
			.getLogger(AutoSetUpTask.class);
	/**
	 * 标的达标百分比，为小数类型
	 */
	private static final String PERCENT_OF_BIDDING = PropertiesUtil
			.getPropertiesByKey("AVAILABLE_BIDDING_PERCENT");

	@Autowired
	private ScheduleService scheduleService;
	@Autowired
	private ProductMapper productMapper;
	@Autowired
	private ProductCustomerMapper productCustomerMapper;
	@Autowired
	private RedPacketController redPacketController;
	@Autowired
	private CustomQueryMapper customQueryMapper;
	@Autowired
	private TradingMapper tradingMapper;
	@Autowired
	private UsersPropertiesMapper usersPropertiesMapper;
	@Autowired
	private UsersMapper usersMapper;
	@Autowired
	private RedisSet redisSet;
    @Autowired
    private TradingBuyCallbackService tradingBuyCallbackService;

	/**
	 * 执行方法
	 * 
	 * @throws PageInfoException
	 *             PageInfoException
	 */
	public void autoSetUp() throws PageInfoException {
		LOGGER.info("\n产品自动成立定时任务开始执行...");
		scheduleService.saveScheduleLog(null, "产品自动成立定时任务开始执行...", null);

		// 查询Product 表，查询出今天应该成立的产品
		List<Product> result = productCustomerMapper.getNnSetUpProduct();
		if (result != null && result.size() > 0) {
			for (Product product : result) {
                if (product.getpType() == ResultNumber.TWO.getNumber()) {
                    // 判断产品募集额度是否达标 已筹集金额/募集金额 保留4位小数
                    BigDecimal reachMoney = customQueryMapper.queryValidTradingMoney(product.getId());
                    BigDecimal reachedPercent = reachMoney.divide(product.getPriorMoney(),
                            ResultNumber.FOUR.getNumber(), RoundingMode.HALF_DOWN);
                    BigDecimal requiredPercent = new BigDecimal(PERCENT_OF_BIDDING);

                    // 操作记录
                    StringBuffer resultRecord = new StringBuffer(product.getId() + "号产品募集比例：" + reachedPercent);

                    ProductWithBLOBs productRecord = new ProductWithBLOBs();
                    productRecord.setId(product.getId());
                    if (reachedPercent.compareTo(requiredPercent) != ResultNumber.MINUS_ONE.getNumber()) {
                        // 达标，产品成立，等待申请放款
                        resultRecord.append("，金额达标，可以成立。");
                        productRecord.setStatus(AppCons.PRODUCT_STATUS_COLLECT_SUCCESS);

                        // 产品成立后获取红包
                        redPacketController.rewardRedPackageForProduct(product.getId());

                    } else {
                        // 不达标，产品失败
                        resultRecord.append("，金额不达标，等待申请流标。");
                        productRecord.setStatus(AppCons.PRODUCT_STATUS_COLLECT_FAIL);
                    }

                    int updateResult = productMapper.updateByPrimaryKeySelective(productRecord);
                    if (updateResult > 0) {
                        LOGGER.info(resultRecord.append("， 更新产品状态成功。").toString());
                        scheduleService.saveScheduleLog(null, resultRecord.append("， 更新产品状态成功。").toString(), null);

                    } else {
                        LOGGER.info(resultRecord.append("， 更新产品状态失败。").toString());
                        scheduleService
                                .saveScheduleLog(null, resultRecord.append("， 更新产品状态失败。").toString(), "修改产品状态失败");
                    }
					// if (product.getId() != null) {
					// TradingExample tradingExample = new TradingExample();
					// tradingExample.createCriteria().andPIdEqualTo(product.getId());
					// List<Trading> list =
					// tradingMapper.selectByExample(tradingExample);
					// String productName = product.getName(); // 产品名称
					// Integer tTime = product.getfTime(); // 产品固定期限
					// if (list != null && list.size() > 0) {
					// for (int i = 0; i < list.size(); i++) {
					// BigDecimal money = list.get(i).getBuyMoney();
					// String uid = list.get(i).getuId();
					// if (uid != null) {
					// String userName = null;
					// String telephone = null;
					// UsersProperties usersProperties =
					// usersPropertiesMapper.selectByuId(uid);
					// if (usersProperties != null) {
					// userName = usersProperties.getName();
					// }
					// Users user = usersMapper.selectByPrimaryKey(uid);
					// if (user != null) {
					// telephone = user.getTelephone();
					// }
					// if (userName != null && telephone != null) {
					// String content = AppCons.buy_content;
					// content = MessageFormat.format(content, userName,
					// productName, tTime, money);
					// SmsMessage message = new SmsMessage();
					// message.setTelephone(telephone);
					// message.setContent(content);
					// redisSet.setListElement(DateUtils.getDateStr(new Date(),
					// DateUtils.sf) + "_"
					// + telephone, message);
					// }
					// }
					// }
					// }
					// }

                } else {
                    if (result != null && result.size() > 0) {
                        for (int i = 0; i < result.size(); i++) {
                            Integer days = ProductUtils.getFinancialDays(product);
                            CallbackValidCode code = tradingBuyCallbackService.manualEstablish(product.getId(), days,
                                    true);

                            if (code.getCode() == CallbackValidCode.callback_success.getCode()) {
                                LOGGER.info("产品" + product.getId() + "名称" + product.getName() + "自动成立成功");
                            } else {
                                LOGGER.info("产品" + product.getId() + "名称" + product.getName() + "自动成立失败，失败原因:"
                                        + code.getMsg());
                            }
                        }
                    }
                }

			}
		}
		LOGGER.info("产品自动成立定时任务执行完毕。");
		scheduleService.saveScheduleLog(null, "产品自动成立定时任务执行完毕", null);
	}

}
