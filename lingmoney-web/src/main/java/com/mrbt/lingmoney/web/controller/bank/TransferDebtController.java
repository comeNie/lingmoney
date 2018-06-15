package com.mrbt.lingmoney.web.controller.bank;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mrbt.lingmoney.service.bank.TransferDebtService;
import com.mrbt.lingmoney.utils.PageInfo;
import com.mrbt.lingmoney.utils.ResultParame;
import com.mrbt.lingmoney.web.controller.common.CommonMethodUtil;

/**
 * 债权转让
 * @author luox
 * @Date 2017年6月8日
 */
@Controller
@RequestMapping(value = "/bank")
public class TransferDebtController {
	private static final Logger LOGGER = LogManager.getLogger(TransferDebtController.class);
	@Autowired
	private TransferDebtService transferDebtService;
	
	/**
	 * 接收银行xml报文
	 * 
	 * @param request
	 *            请求
	 * @return 信息
	 * @throws Exception
	 *             异常信息
	 */
	public String getXmlDocument(HttpServletRequest request) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(request.getInputStream(), "UTF-8"));
		String buffer = null;

		StringBuffer xml = new StringBuffer();

		while ((buffer = br.readLine()) != null) {
			xml.append(buffer);
		}
		return xml.toString();
	}
	
	/**
	 * 6.11 债权转让结果查询
	 * 
	 * @param request
	 *            请求
	 * @return 信息
	 */
	@RequestMapping(value = "/transferDebtAssignmentSearch", method = RequestMethod.POST)
	public @ResponseBody Object transferDebtAssignmentSearch(
			HttpServletRequest request) {
		LOGGER.info("债权转让结果查询");
		PageInfo pageInfo = null;
		try {
			String uId = CommonMethodUtil.getUidBySession(request);
			pageInfo = transferDebtService.transferDebtAssignmentSearch("PC", uId);
		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.error(e);
			pageInfo = new PageInfo();
			pageInfo.setCode(ResultParame.ResultInfo.SERVER_ERROR.getCode());
			pageInfo.setMsg(e.getMessage());
		}
		return pageInfo;
	}
	
	/**
	 * 债权转让申请
	 * 
	 * @param response
	 *            相应
	 * @param model
	 *            数据包装
	 * @param request
	 *            请求
	 * @param amount
	 *            金额
	 * @param remark
	 *            活动类型
	 * @param pCode
	 *            产品编码
	 * @return 信息
	 */
	@RequestMapping(value = "/transferDebt")
	public String transferDebt(HttpServletResponse response, Model model, HttpServletRequest request,
			String amount, String remark, String pCode) {
			
		LOGGER.info("债权转让申请");
		PageInfo pageInfo = new PageInfo();
		String uId = CommonMethodUtil.getUidBySession(request);
		try {
			pageInfo = transferDebtService.transferDebtAssignment(uId, 0, "PC", amount, remark, pCode);
			if (pageInfo.getCode() == ResultParame.ResultInfo.SUCCESS.getCode()) {
				Map<?, ?> map = (Map<?, ?>) pageInfo.getObj();
				String requestData = (String) map.get("requestData");
				String replaceAll = requestData.replaceAll("\"", "&quot;").replaceAll("<", "&lt;").replaceAll(">", "&gt;");
				model.addAttribute("requestData", replaceAll);
				model.addAttribute("transCode", map.get("transCode"));
				model.addAttribute("bankUrl", map.get("bankUrl"));
			} else {
				response.getWriter().write("服务器出错");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "hxBank/hxBankAction2";
		
	}
	
}
