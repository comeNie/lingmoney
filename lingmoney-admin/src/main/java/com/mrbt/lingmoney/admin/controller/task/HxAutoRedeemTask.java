package com.mrbt.lingmoney.admin.controller.task;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ApplicationObjectSupport;
import org.springframework.stereotype.Component;

import com.mrbt.lingmoney.admin.service.bank.HxAutoRepaymentService;
import com.mrbt.lingmoney.admin.service.bank.HxQueryTradingResultService;
import com.mrbt.lingmoney.admin.service.base.CommonMethodService;
import com.mrbt.lingmoney.admin.service.schedule.ScheduleService;
import com.mrbt.lingmoney.mapper.CustomQueryMapper;
import com.mrbt.lingmoney.mapper.EnterpriseAccountMapper;
import com.mrbt.lingmoney.mapper.HxAccountMapper;
import com.mrbt.lingmoney.mapper.HxBiddingMapper;
import com.mrbt.lingmoney.mapper.HxBorrowerInfoMapper;
import com.mrbt.lingmoney.mapper.HxBorrowerMapper;
import com.mrbt.lingmoney.mapper.HxPaymentMapper;
import com.mrbt.lingmoney.mapper.ProductMapper;
import com.mrbt.lingmoney.model.EnterpriseAccount;
import com.mrbt.lingmoney.model.HxAccount;
import com.mrbt.lingmoney.model.HxBidding;
import com.mrbt.lingmoney.model.HxBiddingExample;
import com.mrbt.lingmoney.model.HxBorrower;
import com.mrbt.lingmoney.model.HxBorrowerInfo;
import com.mrbt.lingmoney.model.HxPayment;
import com.mrbt.lingmoney.model.HxPaymentExample;
import com.mrbt.lingmoney.model.Product;
import com.mrbt.lingmoney.model.ProductExample;
import com.mrbt.lingmoney.model.ProductWithBLOBs;
import com.mrbt.lingmoney.utils.AppCons;
import com.mrbt.lingmoney.utils.PageInfo;
import com.mrbt.lingmoney.utils.ResultParame.ResultInfo;
import com.mrbt.lingmoney.utils.ResultParame.ResultNumber;

/**
 * 自动还款--华兴
 * 
 * @author Administrator
 *
 */
@Component
public class HxAutoRedeemTask extends ApplicationObjectSupport {

	private static final Logger LOGGER = LogManager.getLogger(AutoRedeemTask.class);

	@Autowired
	private HxBiddingMapper hxBiddingMapper;
	@Autowired
	private ProductMapper productMapper;
	@Autowired
	private CustomQueryMapper customQueryMapper;
	@Autowired
	private HxAutoRepaymentService hxAutoRepaymentService;
	@Autowired
	private ScheduleService scheduleService;
	@Autowired
	private HxQueryTradingResultService hxQueryTradingResultService;
	@Autowired
	private HxPaymentMapper hxPaymentMapper;
	@Autowired
	private HxAccountMapper hxAccountMapper;
	@Autowired
	private CommonMethodService commonMethodService;
	@Autowired
	private HxBorrowerInfoMapper hxBorrowerInfoMapper;
    @Autowired
    private HxBorrowerMapper hxBorrowerMapper;
    @Autowired
    private EnterpriseAccountMapper enterpriseAccountMapper;

	/**
	 * 
	 * @description 自动还款
	 * @author syb
	 * @date 2017年8月15日 上午9:16:39
	 * @version 1.0
	 *
	 */
	public void hxAutoRedeem() {
		String logGroup = "\nautoRepaymentTask_" + System.currentTimeMillis();
		LOGGER.info("{}华兴自动还款定时任务开始", logGroup);

		// 查询自动还款确认产品
		ProductExample productExmp = new ProductExample();
		productExmp.createCriteria().andStatusEqualTo(AppCons.PRODUCT_STATUS_CONFIRM_AUTO_REPAYMENT);
		List<Product> proList = productMapper.selectByExample(productExmp);

		if (proList != null && proList.size() > 0) {
			for (Product product : proList) {
				try {
                    BigDecimal money = commonMethodService.countTotalRepaymentMoney(product.getId(), logGroup);

                    HxBiddingExample hxBiddingExmp = new HxBiddingExample();
                    hxBiddingExmp.createCriteria().andLoanNoEqualTo(product.getCode());
                    List<HxBidding> hxBiddingList = hxBiddingMapper.selectByExample(hxBiddingExmp);
                    if (hxBiddingList != null && hxBiddingList.size() > 0) {
                    	HxBidding hxBidding = hxBiddingList.get(0);
                        PageInfo pi = hxAutoRepaymentService.requestAutoSingleRepayment("PC", hxBidding.getId(), 0,
                                money.doubleValue(),
                                "自动还款", logGroup);

                    	if (pi.getCode() == ResultInfo.SUCCESS.getCode()) {
                    		// 成功
                    		LOGGER.info("{}还款成功", logGroup);
                    		scheduleService.saveScheduleLog(null, "自动还款成功，产品id：" + product.getId(), null);

                    		updateProductStatusAfterRepayment(product, AppCons.PRODUCT_STATUS_REPAYMENT_SUCCESS);

                    	} else if (pi.getCode() == ResultInfo.NOT_TRADING_RESULT.getCode()) {
                    		// 未知结果：
                    		LOGGER.info("{}还款等待中", logGroup);
                    		scheduleService.saveScheduleLog(null, "自动还款请求中，产品id：" + product.getId(), null);

                    		updateProductStatusAfterRepayment(product, AppCons.PRODUCT_STATUS_REPAYMENT_APPLY);

                    	} else {
                    		// 失败
                    		LOGGER.info("{}还款失败, 失败原因：{}", logGroup, pi.getMsg());

                    		scheduleService.saveScheduleLog(null, "自动还款失败，产品id：" + product.getId(), pi.getMsg());
                    	}

                    }
                } catch (Exception e) {
                    LOGGER.error("{}自动还款出错，产品id{}，错误信息：{}", logGroup, product.getId(), e.toString());
                    scheduleService.saveScheduleLog(null, "自动还款失败，产品id：" + product.getId(), e.toString());
                    e.printStackTrace();
                }
			}
		}

		LOGGER.info("{}华兴自动还款定时任务执行结束", logGroup);
	}

	/**
	 * 
	 * @description 查询自动还款结果
	 * @author syb
	 * @date 2017年8月15日 上午9:16:52
	 * @version 1.0
	 *
	 */
	public void queryAutoReedomResult() {
		String logGroup = "\n查询自动还款结果定时任务_" + System.currentTimeMillis();

		LOGGER.info("{}自动还款结果查询定时任务开始执行", logGroup);
		
		HxPaymentExample example = new HxPaymentExample();
		example.createCriteria().andPaymentTypeEqualTo(ResultNumber.FOUR.getNumber()).andStatusEqualTo(ResultNumber.TWO.getNumber());
		List<HxPayment> list = hxPaymentMapper.selectByExample(example);

		if (list != null && list.size() > 0) {
			for (HxPayment hxPayment : list) {
				try {
                    Map<String, Object> resultMap = hxQueryTradingResultService.queryHxTradingResult(hxPayment.getChannelFlow(), null, logGroup);
                    
                    if (resultMap != null) {
                    	// 还款记录更新数据
                    	HxPayment hxPaymentRecord = new HxPayment();
                    	hxPaymentRecord.setId(hxPayment.getId());
                    	
                    	// 产品表更新数据
                    	Integer pid = customQueryMapper.queryPidByHxPaymentId(hxPayment.getId());
                    	ProductWithBLOBs productRecord = new ProductWithBLOBs();
                    	productRecord.setId(pid);
                    	
                    	String errorCode = (String) resultMap.get("errorCode");

                    	if ("0".equals(errorCode)) {

                    		String status = (String) resultMap.get("STATUS");
                    		String acNo = null;
                            String uid = null;
                    		
                    		if ("S".equals(status)) { // 成功
                    			LOGGER.info("{}流水号：{}的交易成功", logGroup, hxPayment.getChannelFlow());

                    			hxPaymentRecord.setStatus(1);
                    			productRecord.setStatus(AppCons.PRODUCT_STATUS_REPAYMENT_SUCCESS);

                    			// 区分企业账户和个人账户
                    			HxBorrowerInfo hxBorrowerInfo = hxBorrowerInfoMapper.selectByPrimaryKey(hxPayment.getBwId());
                                HxBorrower hxBorrower = hxBorrowerMapper.selectByPrimaryKey(hxBorrowerInfo.getBwId());
                                if ("1010".equals(hxBorrower.getBwIdtype())) {
                                    HxAccount hxAccount = hxAccountMapper.selectByPrimaryKey(hxBorrower.getAccId());
                                    acNo = hxAccount.getAcNo();
                                    uid = hxAccount.getuId();

                                } else if ("2020".equals(hxBorrower.getBwIdtype())) {
                                    EnterpriseAccount enterpriseAccount = enterpriseAccountMapper
                                            .selectByPrimaryKey(hxBorrower.getAccId());
                                    acNo = enterpriseAccount.getBankNo();
                                }
                    			
                                if (acNo != null) {
                    				Product product = productMapper.selectByPrimaryKey(pid);
                                    hxAutoRepaymentService.operateAfterRepaymentSuccess(hxPayment.getAmount()
                                            .doubleValue(), logGroup, hxPayment.getChannelFlow(), uid, acNo, product
                                            .getCode());

                    			} else {
                    				LOGGER.error("{}未查询到E账号为{}的用户信息。", logGroup, acNo);
                    			}

                    		} else if ("F".equals(status)) { // 失败
                    			String errorMsg = (String) resultMap.get("ERRORMSG");
                    			LOGGER.info("{}自动还款失败，原因：{}", logGroup, errorMsg);

                    			hxPaymentRecord.setStatus(ResultNumber.THREE.getNumber());

                    			// 如果查询还款失败，将状态改回自动还款确认
                    			productRecord.setStatus(AppCons.PRODUCT_STATUS_CONFIRM_AUTO_REPAYMENT);

                    		} else if ("X".equals(status)) { // 未知
                    			hxPaymentRecord.setStatus(ResultNumber.TWO.getNumber());

                    			// 未知，依旧当做还款中处理
                    			productRecord.setStatus(AppCons.PRODUCT_STATUS_REPAYMENT_APPLY);
                    		}

                    	} else {
                    		if ("EAS020400001".equals(errorCode)) {
                    			// 查询结果不存在，当做失败处理
                    			hxPaymentRecord.setStatus(ResultNumber.THREE.getNumber());

                    			// 如果查询还款失败，将状态改回自动还款确认
                    			productRecord.setStatus(AppCons.PRODUCT_STATUS_CONFIRM_AUTO_REPAYMENT);
                    		} else {
                    			LOGGER.info("查询自动还款结果失败,错误原因：{}", resultMap.get("errorMsg"));
                    		}

                    	}

                    	if (hxPaymentRecord.getStatus() != null) {
                    		// 更新还款状态
                    		int result = hxPaymentMapper.updateByPrimaryKeySelective(hxPaymentRecord);

                    		if (result > 0) {
                    			LOGGER.info("{}自动还款结果查询成功，更新还款状态为{}成功", logGroup, hxPaymentRecord.getStatus());
                    		} else {
                    			LOGGER.error("{}自动还款结果查询成功，更新还款状态为{}失败", logGroup, hxPaymentRecord.getStatus());
                    		}

                    		// 更新产品状态
                    		result = productMapper.updateByPrimaryKeySelective(productRecord);

                    		if (result > 0) {
                    			LOGGER.info("{}自动还款结果查询成功，更新产品状态为{}成功", logGroup, productRecord.getStatus());
                    		} else {
                    			LOGGER.error("{}自动还款结果查询成功，更新产品状态为{}失败", logGroup, productRecord.getStatus());
                    		}
                    	}

                    }
                } catch (Exception e) {
                    LOGGER.error("{}自动还款失败，还款id：{}，系统错误。{}", logGroup, hxPayment.getId(), e.toString());
                    e.printStackTrace();
                }
				
			}
		}
		
	}

	/**
	 * 
	 * @param product	封装产品
	 * @param status	状态
	 */
	private void updateProductStatusAfterRepayment(Product product, int status) {
		ProductWithBLOBs productRecord = new ProductWithBLOBs();
		productRecord.setId(product.getId());
		productRecord.setStatus(status);

		int result = productMapper.updateByPrimaryKeySelective(productRecord);

		if (result > 0) {
			LOGGER.info("自动还款完成，更新产品状态为{}成功。", status);
		} else {
			LOGGER.error("自动还款完成，更新产品状态为{}失败。", status);
		}
	}

}
