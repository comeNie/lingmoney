package com.mrbt.lingmoney.mobile.controller.bank;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mrbt.lingmoney.mobile.controller.BaseController;
import com.mrbt.lingmoney.service.bank.SingleBiddingResultService;
import com.mrbt.lingmoney.service.bank.SingleBiddingService;
import com.mrbt.lingmoney.utils.AES256Encryption;
import com.mrbt.lingmoney.utils.AppCons;
import com.mrbt.lingmoney.utils.PageInfo;
import com.mrbt.lingmoney.utils.ResultParame.ResultInfo;

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
	/**
	 * @param token
	 *            用户token
	 * @param tId
	 *            交易ID
	 * @param model  
	 *             model
	 * @return string
	 * , method = RequestMethod.POST
	 */
	@RequestMapping(value = "/singleBiddingPage")
	public String singleBiddingPage(String token, Integer tId, Model model) {
		JSONObject paramsJson = new JSONObject();
		paramsJson.put("token", token);
		paramsJson.put("tId", tId);
		model.addAttribute("params", AES256Encryption.aes256Encrypt(paramsJson.toString()));
        //		model.addAttribute("reqUrl", "/bank/singleBidding");
        model.addAttribute("reqUrl", "/bank/singleBiddingVersionOne");
		return "hxBank/hxBankAction";
	}

	/**
	 * 用户发起单笔投标请求
	 * @param response res
	 * @param token
	 *            用户token
	 * @param tId
	 *            交易ID
	 * @param model model
	 * @return json
	 */
	@RequestMapping(value = "/singleBidding", method = RequestMethod.POST)
	public @ResponseBody Object singleBidding(HttpServletResponse response, String token, Integer tId, Model model) {
		LOGGER.info("用户发起单笔投标请求并跳转到华兴银行");
		response.setContentType("text/html;charset=UTF-8");
		JSONObject json = new JSONObject();
		PageInfo pageInfo = new PageInfo();
		String uId = null;
		// 发送投标报文
		try {
			uId = getUid(AppCons.TOKEN_VERIFY + token);
			pageInfo = singleBiddingService.requestSingleBidding(1, "APP", tId, uId);
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

	/**
	 * 单笔投标返回商户url
	 * @param request req
	 * @param response res
	 * @param note note
	 * 
	 */
	@RequestMapping(value = "/singleBiddingCallBack/{note}", method = RequestMethod.POST)
	public void singleBiddingCallBack(HttpServletRequest request, HttpServletResponse response,
			@PathVariable String note) {
		LOGGER.info("单笔提现返回商户url");
		try {
			String xml = getXmlDocument(request); // 接收银行发送的报文
			LOGGER.info("单笔提现银行异步通知报文" + xml);
//			String res = singleBiddingService.singleBiddingCallBack(xml, note);
//			response.setContentLength(res.getBytes().length);
//			response.setContentType("application/xml; charset=UTF-8");
//			response.getWriter().write(res);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 查询投标结果
	 * @param token token
	 * @param number number
	 * @return pageInfo
	 */
	@RequestMapping(value = "/querySingleBidding")
	public @ResponseBody Object querySingleBidding(String token, String number) {
		PageInfo pageInfo = new PageInfo();
		String uId = null;
		try {
			uId = getUid(AppCons.TOKEN_VERIFY + token);
			pageInfo = singleBiddingResultService.requestSingleBiddingResult(1, "APP", uId, number);
		} catch (Exception e) {
			e.printStackTrace();
			pageInfo.setCode(ResultInfo.SERVER_ERROR.getCode());
			pageInfo.setMsg(ResultInfo.SERVER_ERROR.getMsg());
		}
		return pageInfo;
	}

    /**
     * @param token
     *            用户token
     * @param tId
     *            交易ID
     * @param model  
     *             model
     * @return string
     * , method = RequestMethod.POST
     */
    @RequestMapping(value = "/singleBiddingPageVersionOne")
    public String singleBiddingPageVersionOne(String token, Integer tId, Model model) {
        JSONObject paramsJson = new JSONObject();
        paramsJson.put("token", token);
        paramsJson.put("tId", tId);
        model.addAttribute("params", AES256Encryption.aes256Encrypt(paramsJson.toString()));
        model.addAttribute("reqUrl", "/bank/singleBiddingVersionOne");
        return "hxBank/hxBankAction";
    }

    /**
     * 用户发起单笔投标请求
     * @param response res
     * @param token
     *            用户token
     * @param tId
     *            交易ID
     * @param model model
     * @return json
     */
    @RequestMapping(value = "/singleBiddingVersionOne", method = RequestMethod.POST)
    public @ResponseBody Object singleBiddingVersionOne(HttpServletResponse response, String token, Integer tId,
            Model model) {
        LOGGER.info("用户发起单笔投标请求并跳转到华兴银行");
        response.setContentType("text/html;charset=UTF-8");
        JSONObject json = new JSONObject();
        PageInfo pageInfo = new PageInfo();
        String uId = null;
        // 发送投标报文
        try {
            uId = getUid(AppCons.TOKEN_VERIFY + token);
            pageInfo = singleBiddingService.requestSingleBiddingVersionOne(1, "APP", tId, uId);
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
