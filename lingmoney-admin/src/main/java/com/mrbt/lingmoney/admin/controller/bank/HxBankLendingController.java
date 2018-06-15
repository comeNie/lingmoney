package com.mrbt.lingmoney.admin.controller.bank;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mrbt.lingmoney.admin.service.bank.HxBankLendingService;
import com.mrbt.lingmoney.utils.PageInfo;
import com.mrbt.lingmoney.utils.ResultParame.ResultInfo;

/**
 * @author syb
 * @date 2017年6月6日 上午11:54:24
 * @version 1.0
 * @description 放款
 **/
@Controller
@RequestMapping("/bank")
public class HxBankLendingController {
	private static final Logger LOGGER = LogManager.getLogger(HxBankLendingController.class);
	private static final String APPID = "PC";

	@Autowired
	private HxBankLendingService hxBankLenddingService;

	/**
	 * 申请银行放款
	 * 1.只有产品状态为募集成功或者放款申请中（失败后二次申请）的，才能申请放款。
	 * 2.如果还有支付中的记录时不可申请放款。
	 * 3.账户管理费 = 产品有效购买金额（支付成功金额） * 平台佣金
	 * 4.保存/更新放款申请记录
	 * 5.如果初次申请，需要更新产品状态为放款申请中，并且更新产品成立日和计息日为申请放款时间，计息日如果是节假日顺延
	 * @param biddingId
	 *            标的id
	 * @param guarantAmt
	 *            风险保证金（选填） 保留两位小数
	 * @param remark
	 *            备注（选填）
	 * @return  返回处理结果
	 */
	@RequestMapping(value = "/askForBankLendding", method = RequestMethod.POST)
    public @ResponseBody Object askForBankLendding(String biddingId, Double guarantAmt, String remark) {
		String logGroup = "\naskForBankLendding" + System.currentTimeMillis() + "_";
		
        LOGGER.info("{}申请银行放款， 参数：biddingId : {}, acmngAmt : {}, guarantAmt : {}, remark : {}", logGroup, biddingId,
                guarantAmt, remark);
		
		PageInfo pi = new PageInfo();
		try {
            pi = hxBankLenddingService.askForBankLending(APPID, biddingId, guarantAmt, remark, logGroup);
		} catch (Exception e) {
			LOGGER.error("{}申请银行放款失败，系统错误。\n{}", logGroup, e.toString());
			e.printStackTrace();
			pi.setCode(ResultInfo.SERVER_ERROR.getCode());
			pi.setMsg(ResultInfo.SERVER_ERROR.getMsg());
		}
		
		return pi;
	}

	/**
	 * 查询银行放款结果 交易提交5~10分钟后，可通过该接口查询银行处理结果
	 * 1.根据查询结果更新对应放款记录信息。
	 * 2.放款成功后：
	 *  1）修改借款人账户余额 = 账户余额 + 募集成功金额 - 账户管理费 - 风险保证金
	 *  2）插入一条账户流水
	 *  3）修改产品状态为运行中
	 *  4）修改标的到期时间
	 *  5）修改支付成功交易记录状态为理财中，添加计息日期和赎回日期；修改交易流水信息，计算利息。
	 *  6）修改支付成功用户账户金额 （扣除冻结金额，增加理财金额），修改账户流水状态
	 * @param loanNo
	 *            借款编号
	 * @return 返回处理结果
	 */
	@RequestMapping(value = "/queryBankLenddingResult", method = RequestMethod.POST)
	public @ResponseBody Object queryBankLenddingResult(String loanNo) {
		String logGroup = "\nqueryBankLenddingResult" + System.currentTimeMillis() + "_";
		
		LOGGER.info("{}查询银行放款结果，参数：loanNo : {}", logGroup, loanNo);
		
		PageInfo pi = new PageInfo();
		try {
			pi = hxBankLenddingService.queryBankLendingResult(APPID, loanNo, logGroup);
		} catch (Exception e) {
			LOGGER.error("{}查询银行放款结果失败，系统错误。\n{}", logGroup, e.toString());
			e.printStackTrace();
			pi.setCode(ResultInfo.SERVER_ERROR.getCode());
			pi.setMsg(ResultInfo.SERVER_ERROR.getMsg());
		}
		
		return pi;
	}
}
