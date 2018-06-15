package com.mrbt.lingmoney.admin.controller.bank;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mrbt.lingmoney.admin.controller.base.BaseController;
import com.mrbt.lingmoney.admin.service.bank.HxBiddingLossService;
import com.mrbt.lingmoney.utils.PageInfo;
import com.mrbt.lingmoney.utils.ResultParame.ResultInfo;

/**
 *@author syb
 *@date 2017年6月5日 下午4:25:08
 *@version 1.0
 *@description 流标
 **/
@Controller
@RequestMapping("/bank")
public class HxBiddingLossController extends BaseController {
	private static final Logger LOGGER = LogManager.getLogger(HxBiddingLossController.class);
	private static final String APPID = "PC";
	
	@Autowired
	private HxBiddingLossService hxBiddingLossService;
	
	/**
	 * 申请流标
	 * @param loanNo 原投标借款编号
	 * @param cancelReason 流标理由（选填）
	 * @return 返回处理结果
	 */
	@RequestMapping(value = "/applyBiddingLoss", method = RequestMethod.POST)
	public @ResponseBody Object applyBiddingLoss(String loanNo, String cancelReason) {
		String logGroup = "\napplyBiddingLoss_" + System.currentTimeMillis() + "_";
		
		LOGGER.info(logGroup + "申请流标 \t" + loanNo + "\t" + cancelReason);
		
		PageInfo pageInfo = new PageInfo();
		try {
			pageInfo = hxBiddingLossService.applyBiddingLoss(APPID, loanNo, cancelReason, logGroup);
		} catch (Exception e) {
			pageInfo.setCode(ResultInfo.SERVER_ERROR.getCode());
			pageInfo.setMsg(ResultInfo.SERVER_ERROR.getMsg());
			e.printStackTrace();
			LOGGER.error(logGroup + "请求流标失败，系统错误。 \n" + e.getMessage());
		}
		
		return pageInfo;
	}
	
	/**
	 * 查询流标结果 当收不到返回时（5~10分钟后），可通过该接口查询银行处理结果。
	 * @param loanNo 借款编号
	 * @return 返回处理结果
	 */
	@RequestMapping(value = "/queryBiddingLossResult", method = RequestMethod.POST)
	public @ResponseBody Object queryBiddingLossResult(String loanNo) {
		String logGroup = "\nqueryBiddingLossResult_" + System.currentTimeMillis() + "_";
		
		LOGGER.info(logGroup + "请求查询流标结果，参数信息：" + "loanNo:" + loanNo);
		
		PageInfo pageInfo = new PageInfo();
		try {
			pageInfo = hxBiddingLossService.queryBiddingLossResult(APPID, loanNo, logGroup);
		} catch (Exception e) {
			e.printStackTrace();
			pageInfo.setCode(ResultInfo.SERVER_ERROR.getCode());
			pageInfo.setMsg(ResultInfo.SERVER_ERROR.getMsg());
			LOGGER.error(logGroup + "查询流标结果失败 \n" + e.getMessage());
		}
		
		return pageInfo;
	}
	
	/**
	 * 处理银行主动流标请求
	 * @param request req
	 * @param response res
	 */
	@RequestMapping(value = "/handleHxInitiativeBiddingLoss", method = RequestMethod.POST)
	private void handleHxInitiativeBiddingLoss(HttpServletRequest request, HttpServletResponse response) {
		String logGroup = "\nhandleHxInitiativeBiddingLoss_" + System.currentTimeMillis() + "_";
		
		LOGGER.info(logGroup + "收到银行主动流标请求");
		
		try {
			String xml = getXmlDocument(request);
			
			LOGGER.info(logGroup + "请求信息：\n" + xml);
			
			String xmlResponse = hxBiddingLossService.handleHxInitiativeBiddingLoss(xml, logGroup);
			if (xmlResponse != null) {
				response.setContentLength(xmlResponse.getBytes().length);
				response.setContentType("application/xml; charset=UTF-8");
				response.getWriter().write(xmlResponse);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
