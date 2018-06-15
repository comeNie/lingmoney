package com.mrbt.lingmoney.web.controller.bank;

import javax.servlet.http.HttpServletRequest;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mrbt.lingmoney.service.bank.HxTenderService;
import com.mrbt.lingmoney.utils.PageInfo;
import com.mrbt.lingmoney.utils.ResultParame;
import com.mrbt.lingmoney.web.controller.common.CommonMethodUtil;

/**
 * 投标
 * @author luox
 */
@Controller
@RequestMapping(value = "/bank", method = RequestMethod.POST)
public class HxTenderController {
	private static final Logger LOG = LogManager.getLogger(HxTenderController.class);
	@Autowired
	private HxTenderService hxTenderService;
	
	/**
	 * 6.9 自动单笔投标
	 * 
	 * @param request
	 *            请求
	 * @param loanno
	 *            流水号
	 * @param amount
	 *            金额
	 * @param remark
	 *            活动类型
	 * @return 信息
	 */
	@RequestMapping("/autoTenderOne")
	public @ResponseBody Object autoTenderOne(HttpServletRequest request, String loanno, String amount, String remark) {
		LOG.info("自动单笔投标");
		PageInfo pageInfo = null;
		try {
			String uId = CommonMethodUtil.getUidBySession(request);
			pageInfo = hxTenderService.autoTenderOne(uId, "PC", loanno, amount, remark);
		} catch (Exception e) {
			e.printStackTrace();
			LOG.error(e);
			pageInfo = new PageInfo();
			pageInfo.setCode(ResultParame.ResultInfo.SERVER_ERROR.getCode());
			pageInfo.setMsg(e.getMessage());
		}
		return pageInfo;
	}
	
	/**
	 * 6.8 自动投标授权撤销
	 * 
	 * @param request
	 *            请求
	 * @param otpseqno
	 *            流水号
	 * @param otpno
	 *            金额
	 * @param remark
	 *            活动类型
	 * @return 信息
	 */
	@RequestMapping("/autoTenderAuthorizeCancel")
	public @ResponseBody Object autoTenderAuthorizeCancel(HttpServletRequest request,
			String otpseqno, String otpno, String remark) {
		LOG.info("自动投标授权撤销");
		PageInfo pageInfo = null;
		try {
			String uId = CommonMethodUtil.getUidBySession(request);
			pageInfo = hxTenderService.autoTenderAuthorizeCancel(uId, "PC", otpseqno, otpno, remark);
		} catch (Exception e) {
			e.printStackTrace();
			LOG.error(e);
			pageInfo = new PageInfo();
			pageInfo.setCode(ResultParame.ResultInfo.SERVER_ERROR.getCode());
			pageInfo.setMsg(e.getMessage());
		}
		return pageInfo;
	}
	
	/**
	 * 6.7 自动投标授权结果查询
	 * 
	 * @param request
	 *            请求
	 * @return 信息
	 */
	@RequestMapping("/autoTenderAuthorizeSearch")
	public @ResponseBody Object autoTenderAuthorizeSearch(HttpServletRequest request) {
		LOG.info("自动投标授权结果查询");
		PageInfo pageInfo = null;
		try {
			String uId = CommonMethodUtil.getUidBySession(request);
			pageInfo = hxTenderService.tenderReturnSearch("PC", uId);
		} catch (Exception e) {
			e.printStackTrace();
			LOG.error(e);
			pageInfo = new PageInfo();
			pageInfo.setCode(ResultParame.ResultInfo.SERVER_ERROR.getCode());
			pageInfo.setMsg(ResultParame.ResultInfo.SERVER_ERROR.getMsg());
		}
		return pageInfo;
	}
	
	
	/**
	 * 投标优惠返回结果查询
	 * 
	 * @param request
	 *            请求
	 * @param oldreqseqno
	 *            订单号
	 * @param subseqno
	 *            流水号
	 * @return 信息
	 */
	@RequestMapping("/tenderReturnSearch")
	public @ResponseBody Object tenderReturnSearch(HttpServletRequest request,
			String oldreqseqno, String subseqno) {
		LOG.info("投标优惠返回结果查询");
		PageInfo pageInfo = null;
		try {
			String uId = CommonMethodUtil.getUidBySession(request);
			pageInfo = hxTenderService.tenderReturnSearch("PC", uId, oldreqseqno, subseqno);
		} catch (Exception e) {
			e.printStackTrace();
			LOG.error(e);
			pageInfo = new PageInfo();
			pageInfo.setCode(ResultParame.ResultInfo.SERVER_ERROR.getCode());
			pageInfo.setMsg(ResultParame.ResultInfo.SERVER_ERROR.getMsg());
		}
		return pageInfo;
	}
	
	/**
	 * 投标优惠返回
	 * 
	 * @param request
	 *            请求
	 * @param loanno
	 *            借款编号
	 * @param bwacname
	 *            还款账号户名
	 * @param bwacno
	 *            还款账号
	 * @param amount
	 *            优惠总金额
	 * @param totalnum
	 *            优惠总笔数
	 * @return 信息
	 */
	@RequestMapping("/tenderReturn")
	public @ResponseBody Object tenderReturn(HttpServletRequest request,
			String loanno, String bwacname, String bwacno, String amount, String totalnum) {
		LOG.info("投标优惠返回");
		PageInfo pageInfo = null;
		try {
			String uId = CommonMethodUtil.getUidBySession(request);
			pageInfo = hxTenderService.tenderReturn("PC", uId, loanno, bwacname, bwacno, amount, totalnum);
		} catch (Exception e) {
			e.printStackTrace();
			LOG.error(e);
			pageInfo = new PageInfo();
			pageInfo.setCode(ResultParame.ResultInfo.SERVER_ERROR.getCode());
			pageInfo.setMsg(e.getMessage());
		}
		return pageInfo;
	}
	
}
