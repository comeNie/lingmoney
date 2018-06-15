package com.mrbt.lingmoney.admin.controller.bank;

import javax.servlet.http.HttpServletRequest;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mrbt.lingmoney.admin.service.bank.TransferDebtService;
import com.mrbt.lingmoney.utils.PageInfo;
import com.mrbt.lingmoney.utils.ResultParame.ResultInfo;

/**
 * 债权转让
 * 
 * @author
 * @Date 2017年6月8日
 */
@Controller
@RequestMapping(value = "/bank")
public class TransferDebtController {
	private static final Logger LOGGER = LogManager.getLogger(TransferDebtController.class);
	@Autowired
	private TransferDebtService transferDebtService;
	
	/**
	 * 债权转让结果查询
	 * @param request	req
	 * @param oldreqseqno	订单编号
	 * @return	返回结果
	 */
	@RequestMapping(value = "/transferDebtAssignmentSearch", method = RequestMethod.POST)
	public @ResponseBody Object transferDebtAssignmentSearch(HttpServletRequest request, String oldreqseqno) {
		LOGGER.info("债权转让结果查询");
		PageInfo pageInfo = null;
		try {
			pageInfo = transferDebtService.transferDebtAssignmentSearch("PC", oldreqseqno);
		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.error(e);
			pageInfo = new PageInfo();
			pageInfo.setCode(ResultInfo.SERVER_ERROR.getCode());
			pageInfo.setMsg(ResultInfo.SERVER_ERROR.getMsg());
		}
		return pageInfo;
	}
	
	/**
	 * 债权转让申请
	 * @param request	req
	 * @param amount	金额
	 * @param remark	备注
	 * @param pCode	产品编码
	 * @param uId	用户UID
	 * @return	返回
	 */
	@RequestMapping(value = "/transferDebt")
	public @ResponseBody Object transferDebt(HttpServletRequest request, String amount, String remark, String pCode, String uId) {
		LOGGER.info("债权转让申请");
		PageInfo pageInfo = new PageInfo();
		try {
			pageInfo = transferDebtService.transferDebtAssignment(uId, 0, "PC", amount, remark, pCode);
		} catch (Exception e) {
			e.printStackTrace();
			pageInfo.setCode(ResultInfo.SERVER_ERROR.getCode());
			pageInfo.setMsg(ResultInfo.SERVER_ERROR.getMsg());
		}
		return pageInfo;
		
	}
	
}
