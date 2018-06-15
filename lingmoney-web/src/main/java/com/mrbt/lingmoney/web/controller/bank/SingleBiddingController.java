package com.mrbt.lingmoney.web.controller.bank;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mrbt.lingmoney.commons.exception.PageInfoException;
import com.mrbt.lingmoney.service.bank.SingleBiddingResultService;
import com.mrbt.lingmoney.service.bank.SingleBiddingService;
import com.mrbt.lingmoney.service.users.UsersService;
import com.mrbt.lingmoney.utils.PageInfo;
import com.mrbt.lingmoney.utils.ResultParame;
import com.mrbt.lingmoney.utils.ResultParame.ResultInfo;
import com.mrbt.lingmoney.web.controller.BaseController;
import com.mrbt.lingmoney.web.controller.common.CommonMethodUtil;
import com.mrbt.lingmoney.web.vo.view.ResultPageInfo;

import net.sf.json.JSONObject;

/**
 * 单笔投标
 * 
 * @author lihq
 * @date 2017年6月6日 下午3:01:16
 * @description
 * @version 1.0
 * @since 2017-04-12
 */
@Controller
@RequestMapping("/bank")
public class SingleBiddingController extends BaseController {
	private static final Logger LOGGER = LogManager.getLogger(SingleBiddingController.class);
	@Autowired
	private SingleBiddingService singleBiddingService;
	@Autowired
	private SingleBiddingResultService singleBiddingResultService;
	@Autowired
	private UsersService usersService;

	/**
	 * 产品详情页，点击确认购买，验证通过后请求此处 用户发起单笔投标请求
	 * 
	 * @param request
	 *            请求
	 * 
	 * @param tId 
	 *            交易id
	 * @return 信息
	 */
	@RequestMapping("/singleBidding")
	public @ResponseBody Object singleBidding(HttpServletRequest request, Integer tId) {
		LOGGER.info("用户发起单笔投标请求");
		PageInfo pageInfo = new PageInfo();
		String uId = CommonMethodUtil.getUidBySession(request);
		try {
			pageInfo = singleBiddingService.requestSingleBidding(0, "PC", tId, uId);
		} catch (PageInfoException e) {
			pageInfo.setCode(e.getCode());
			pageInfo.setMsg(e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			pageInfo.setCode(ResultParame.ResultInfo.SERVER_ERROR.getCode());
			pageInfo.setMsg(ResultParame.ResultInfo.SERVER_ERROR.getMsg());
		}
		return pageInfo;
	}

	/**
	 * 单笔投标返回商户url
	 * 
	 * @param request
	 * @param response
	 * @param note
	 */
	/*
	 * @RequestMapping(value = "/singleBiddingCallBack/{note}", method =
	 * RequestMethod.GET) public @ResponseBody void
	 * singleBiddingCallBack(HttpServletRequest request, HttpServletResponse
	 * response,
	 * 
	 * @PathVariable String note) { LOGGER.info("单笔提现返回商户url"); try { String xml
	 * = getXmlDocument(request);// 接收银行发送的报文 LOGGER.info("单笔提现银行异步通知报文" + xml);
	 * String res = singleBiddingService.singleBiddingCallBack(xml, note);
	 * response.setContentLength(res.getBytes().length);
	 * response.setContentType("application/xml; charset=UTF-8");
	 * response.getWriter().write(res); } catch (Exception e) {
	 * e.printStackTrace(); } }
	 */
	/**
	 * 用户发起投标结果查询请求
	 * 
	 * @param request
	 *            请求
	 * @param model
	 *            数据包装
	 * @param number
	 *            数量
	 * @return 信息
	 */
	@RequestMapping("/singleBiddingResult")
	public String singleBiddingResult(HttpServletRequest request, ModelMap model, String number) {
		LOGGER.info("用户发起投标结果查询请求");
		PageInfo pageInfo = new PageInfo();
		String uId = CommonMethodUtil.getUidBySession(request);
		try {
			pageInfo = singleBiddingResultService.requestSingleBiddingResult(0, "PC", uId, number);
		} catch (Exception e) {
			e.printStackTrace();
		}

		try {
			CommonMethodUtil.modelUserBaseInfo(model, request, usersService);
		} catch (Exception e) {
			e.printStackTrace();
		}

		ResultPageInfo info = new ResultPageInfo();
		info.setType(ResultParame.ResultNumber.FIVE.getNumber());
		info.setAutoReturnName("会员首页");
		info.setAutoReturnUrl("/myLingqian/show");
		info.setButtonOneName("查看记录");
		// 支付完成需要跳转到冻结中
		info.setButtonOneUrl("/myFinancial/finanCialManage?status=4");
		info.setButtonTwoName("继续理财");
		info.setButtonTwoUrl("/product/list/0_0_0_0_0_1");

		int status = ResultParame.ResultNumber.ZERO.getNumber();
		String resultMsg = "理财结果处理中......";
		if (pageInfo != null) {
			if (pageInfo.getCode() == ResultParame.ResultInfo.SUCCESS.getCode()) {
				status = ResultParame.ResultNumber.ONE.getNumber();
				resultMsg = "恭喜您，理财成功！";
				info.setBackup(pageInfo.getObj());
			} else if (pageInfo.getCode() == ResultParame.ResultInfo.ING.getCode()) {
				status = ResultParame.ResultNumber.TWO.getNumber();
				resultMsg = "很遗憾，理财失败！";
				info.setFailReason(pageInfo.getMsg());
			}
		}
		info.setStatus(status);
		info.setResultMsg(resultMsg);

		model.addAttribute("resultInfo", info);

		return "results";
	}

	/**
	 * 单笔投标返回商户url
	 * 
	 * @param request
	 *            请求
	 * @param response
	 *            相应
	 * @param note
	 *            原请求流水号
	 * @param model
	 *            数据包装
	 * @return 信息
	 */
	@RequestMapping(value = "/singleBiddingCallBack/{note}", method = RequestMethod.GET)
	public String singleBiddingCallBack(HttpServletRequest request, HttpServletResponse response,
			@PathVariable String note, ModelMap model) {
		LOGGER.info("单笔投标返回商户操作");
		PageInfo pageInfo = new PageInfo();
		try {
			pageInfo = singleBiddingResultService.requestSingleBiddingResult("PC", note);
		} catch (Exception e) {
			e.printStackTrace();
		}

		try {
			CommonMethodUtil.modelUserBaseInfo(model, request, usersService);
		} catch (Exception e) {
			e.printStackTrace();
		}

		ResultPageInfo info = new ResultPageInfo();
		info.setType(ResultParame.ResultNumber.FIVE.getNumber());
		info.setAutoReturnName("会员首页");
		info.setAutoReturnUrl("/myLingqian/show");
		info.setButtonOneName("查看记录");
		// 支付完成需要跳转到冻结中
		info.setButtonOneUrl("/myFinancial/finanCialManage?status=4");
		info.setButtonTwoName("继续理财");
		info.setButtonTwoUrl("/product/list/0_0_0_0_0_1");

		int status = ResultParame.ResultNumber.ZERO.getNumber();
		String resultMsg = "理财结果处理中......";
		if (pageInfo != null) {
			if (pageInfo.getCode() == ResultParame.ResultInfo.SUCCESS.getCode()) {
				status = ResultParame.ResultNumber.ONE.getNumber();
				resultMsg = "恭喜您，理财成功！";
				info.setBackup(pageInfo.getObj());
			} else if (pageInfo.getCode() == ResultParame.ResultInfo.ING.getCode()) {
				status = ResultParame.ResultNumber.TWO.getNumber();
				resultMsg = "很遗憾，理财失败！";
				info.setFailReason(pageInfo.getMsg());
			}
		}
		info.setStatus(status);
		info.setResultMsg(resultMsg);

		model.addAttribute("resultInfo", info);

		return "hxCallBack";
	}

	/**
	 * 用户发起单笔投标请求
	 * 
	 * @param response
	 *            res
	 * @param token
	 *            用户token
	 * @param tId
	 *            交易ID
	 * @param model
	 *            model
	 * @return json
	 */
	@RequestMapping(value = "/singleBiddingVersionOne", method = RequestMethod.POST)
	public @ResponseBody Object singleBiddingVersionOne(HttpServletResponse response, HttpServletRequest request, Integer tId,
			Model model) {
		LOGGER.info("用户发起单笔投标请求并跳转到华兴银行");
		response.setContentType("text/html;charset=UTF-8");
		JSONObject json = new JSONObject();
		PageInfo pageInfo = new PageInfo();
		String uId = null;
		// 发送投标报文
		try {
			uId = CommonMethodUtil.getUidBySession(request);
			pageInfo = singleBiddingService.requestSingleBiddingVersionOne(0, "PC", tId, uId);
		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.error("单笔投标失败，失败原因是：" + e.getMessage());
			pageInfo.setCode(ResultInfo.SERVER_ERROR.getCode());
			pageInfo.setMsg(ResultInfo.SERVER_ERROR.getMsg());
		}
		if (pageInfo.getCode() == ResultInfo.SUCCESS.getCode()) {
			json.put("code", ResultInfo.SUCCESS.getCode());
			json.put("obj", pageInfo.getObj());
		} else {
			json.put("code", pageInfo.getCode());
			json.put("msg", pageInfo.getMsg());
		}
		return json;
	}
}
