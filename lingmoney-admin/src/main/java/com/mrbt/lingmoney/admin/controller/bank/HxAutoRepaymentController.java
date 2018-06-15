package com.mrbt.lingmoney.admin.controller.bank;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mrbt.lingmoney.admin.controller.base.BaseController;
import com.mrbt.lingmoney.admin.service.bank.HxAutoRepaymentService;
import com.mrbt.lingmoney.utils.PageInfo;
import com.mrbt.lingmoney.utils.ResultParame.ResultInfo;

/**
 * @author syb
 * @date 2017年6月6日 下午5:10:29
 * @version 1.0
 * @description 自动还款
 **/
@Controller
@RequestMapping("/bank")
public class HxAutoRepaymentController extends BaseController {
	private static final Logger LOGGER = LogManager.getLogger(HxAutoRepaymentController.class);
	private static final String APPID = "PC";

	@Autowired
	private HxAutoRepaymentService hxAutoRepaymentService;

	/**
	 * 请求自动还款授权。
	 * 1.需要区分该标的是企业账户还是个人账户，并查出对应的账户信息发送请求。
	 * 2.如果是初次申请向hx_auto_repayment_apply_info表中插入一条记录，否则更新该条记录。
	 * @param request req
	 * @param response res
	 * @param model model
	 * @param loanNo 借款编号
	 * @param borrowerId 借款人ID
	 * @param remark 备注
	 * @return 返回授权结果
	 */
	@RequestMapping(value = "/askForAutoRepaymentAuthorization", method = RequestMethod.POST)
    public @ResponseBody Object askForAutoRepaymentAuthorization(HttpServletRequest request,
            HttpServletResponse response, ModelMap model, String loanNo, String borrowerId, String remark) {
		String logGroup = "\naskForAutoRepaymentAuthorization_" + System.currentTimeMillis() + "_";
		
		LOGGER.info("{}请求自动还款授权 \n 参数信息：loanNo : {}, borrowerId : {}, remark : {}", logGroup, loanNo, borrowerId,
				remark);
		PageInfo pageInfo = new PageInfo();
		try {
			pageInfo = hxAutoRepaymentService.askForAutoRepaymentAuthorization(0, APPID, loanNo, borrowerId,
					remark, logGroup);
			
		} catch (Exception e) {
			LOGGER.error("{}请求自动还款授权失败，系统错误。\n{}", logGroup, e.toString());
			e.printStackTrace();
			pageInfo.setCode(ResultInfo.SERVER_ERROR.getCode());
			pageInfo.setMsg(ResultInfo.SERVER_ERROR.getMsg());
		}

		return pageInfo;
	}

	/**
	 * 自动还款授权 callback
	 * @param request req
	 * @param response res
	 * @param note	流水号，发起请求的时候进行设置
	 * @return	返回处理结果
	 */
	@RequestMapping(value = "/autoRepaymentAuthCallBack/{note}", method = RequestMethod.POST)
	public String autoRepaymentAuthCallBack(HttpServletRequest request, HttpServletResponse response,
			@PathVariable String note) {
		String logGroup = "\nautoRepaymentAuthCallBack_" + System.currentTimeMillis() + "_";
		
		LOGGER.info("{}收到自动还款授权异步通知", logGroup);
		
		try {
			String xml = getXmlDocument(request); // 接收银行发送的报文。

			LOGGER.info("{}自动还款授权异步通知报文：\n{}", logGroup, xml);

			if (!StringUtils.isEmpty(xml)) {
				String res = hxAutoRepaymentService.autoRepaymentAuthCallBack(xml, note, logGroup);
				response.getWriter().write(res);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	/**
	 * 查询自动还款授权结果
	 * @param request req
	 * @param loanNo 借款编号
	 * @return 返回查询结果
	 */
	@RequestMapping(value = "/queryAutoRepaymentAuthResult", method = RequestMethod.POST)
	public @ResponseBody Object queryAutoRepaymentAuthResult(HttpServletRequest request, String loanNo) {
		String logGroup = "\nqueryAutoRepaymentAuthResult_" + System.currentTimeMillis() + "_";
		
		LOGGER.info("{}请求查询自动还款授权结果,loanNo : {}", logGroup, loanNo);
		
		PageInfo pi = new PageInfo();
		try {
			pi = hxAutoRepaymentService.queryAutoRepaymentAuthResult(APPID, loanNo, logGroup);
		} catch (Exception e) {
			e.printStackTrace();
			pi.setCode(ResultInfo.SERVER_ERROR.getCode());
			pi.setMsg(ResultInfo.SERVER_ERROR.getMsg());
			
			LOGGER.error("{}请求查询自动还款授权结果失败，系统错误。借款编号：{}。\n{}", logGroup, loanNo, e.toString());
		}
		
		return pi;
	}

	/**
	 * 请求撤销 自动还款授权 调此接口前需先调用“获取短信验证码(OGW00041)”发送短信验证码后再发起此接口进行授权撤销
	 * @param otpSeqNo 动态密码唯一标识
	 * @param otpNo 动态密码
	 * @param loanNo 借款编号
	 * @param remark 备注
	 * @param request req
	 * @return 返回处理结果
	 */
	@RequestMapping(value = "/repealAutoRepaymentAuthorization", method = RequestMethod.POST)
	public @ResponseBody Object repealAutoRepaymentAuthorization(HttpServletRequest request, String otpSeqNo,
			String otpNo, String loanNo, String remark) {
		String logGroup = "\nrepealAutoRepaymentAuthorization_" + System.currentTimeMillis() + "_";
		
		LOGGER.info("{}请求撤销自动还款授权： otpSeqNo : {}, otpNo : {}, loanNo : {}, remark : {}", logGroup, otpSeqNo, otpNo,
				loanNo, remark);
		
		PageInfo pi = new PageInfo();
		try {
			pi = hxAutoRepaymentService.repealAutoRepaymentAuthorization(APPID, otpSeqNo, otpNo, loanNo, remark, logGroup);
		} catch (Exception e) {
			e.printStackTrace();
			pi.setCode(ResultInfo.SERVER_ERROR.getCode());
			pi.setMsg(ResultInfo.SERVER_ERROR.getMsg());
			LOGGER.error("{}请求撤销自动还款授权失败，系统错误。\n{}", logGroup, e.toString());
		}
		
		return pi;
	}

	/**
	 * 单笔自动还款请求
	 * 1.保存一条还款记录，类型为 自动还款
	 * 2.如果还款成功，并且是个人账户，需要冻结用户的还款金额，扣除对应的可用余额，并向流水表插入一条记录
	 * @param biddingId 标的ID
	 * @param feeAmt 手续费（选填）
	 * @param amount 还款金额
	 * @param remark 备注
	 * @return 返回处理结果
	 */
	@RequestMapping(value = "/requestAutoSingleRepayment", method = RequestMethod.POST)
	public @ResponseBody Object requestAutoSingleRepayment(String biddingId, double feeAmt, double amount, String remark) {
		String logGroup = "\nrequestAutoSingleRepayment_" + System.currentTimeMillis() + "_";

		LOGGER.info("{}请求单笔自动还款: biddingId : {}, feeAmt : {}, amount : {}, remark : {}", logGroup, biddingId, feeAmt,
				amount, remark);

		PageInfo pi = new PageInfo();
		try {
			pi = hxAutoRepaymentService.requestAutoSingleRepayment(APPID, biddingId, feeAmt, amount, remark, logGroup);
		} catch (Exception e) {
			pi.setCode(ResultInfo.SERVER_ERROR.getCode());
			pi.setMsg(ResultInfo.SERVER_ERROR.getMsg());
			e.printStackTrace();
		}

		return pi;
	}

}
