package com.mrbt.lingmoney.wap.controller.bank;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mrbt.lingmoney.service.bank.SingleBiddingResultService;
import com.mrbt.lingmoney.service.bank.SingleBiddingService;
import com.mrbt.lingmoney.utils.PageInfo;
import com.mrbt.lingmoney.utils.ResultParame;
import com.mrbt.lingmoney.utils.ResultParame.ResultInfo;
import com.mrbt.lingmoney.wap.controller.BaseController;
import com.mrbt.lingmoney.wap.controller.common.CommonMethodUtil;

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
	public String singleBiddingPage(HttpServletRequest request, Integer tId, Model model) {
		JSONObject paramsJson = new JSONObject();
		paramsJson.put("tId", tId);
        model.addAttribute("params", paramsJson.toString());
		model.addAttribute("reqUrl", "/bank/singleBidding");
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
	public @ResponseBody Object singleBidding(HttpServletResponse response, HttpServletRequest request, Integer tId, Model model) {
		LOGGER.info("用户发起单笔投标请求并跳转到华兴银行");
		response.setContentType("text/html;charset=UTF-8");
		PageInfo pageInfo = new PageInfo();
		String uId = null;
		// 发送投标报文
		try {
            uId = CommonMethodUtil.getUidBySession(request);
            if (StringUtils.isEmpty(uId)) {
                pageInfo.setResultInfo(ResultInfo.LOGIN_OVERTIME);
            } else {
                pageInfo = singleBiddingService.requestSingleBidding(1, APP_ID, tId, uId);
            }

		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.error("单笔投标失败，失败原因是：" + e.getMessage());
			pageInfo.setCode(ResultInfo.SERVER_ERROR.getCode());
			pageInfo.setMsg(ResultInfo.SERVER_ERROR.getMsg());
		}

        return pageInfo;
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
    @RequestMapping(value = "/singleBiddingCallBack/{note}")
    public void singleBiddingCallBack(HttpServletResponse response, @PathVariable String note, ModelMap model) {
        LOGGER.info("单笔投标返回商户操作");
        PageInfo pageInfo = new PageInfo();
        try {
//            pageInfo = singleBiddingResultService.requestSingleBiddingResult(APP_ID, note);
            
            int status = ResultParame.ResultNumber.ZERO.getNumber();
            if (pageInfo != null) {
                if (pageInfo.getCode() == ResultParame.ResultInfo.SUCCESS.getCode()) {
                    status = ResultParame.ResultNumber.ONE.getNumber();
                } else if (pageInfo.getCode() == ResultParame.ResultInfo.NO_SUCCESS.getCode()) {
                    status = ResultParame.ResultNumber.TWO.getNumber();
                }
            }
            
            response.sendRedirect("http://static.lingmoney.cn/wap/callback/singlebidding.html?r=" + status);
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
	public @ResponseBody Object querySingleBidding(HttpServletRequest request, String number) {
		PageInfo pageInfo = new PageInfo();
		String uId = null;
		try {
            uId = CommonMethodUtil.getUidBySession(request);
            if (StringUtils.isEmpty(uId)) {
                pageInfo.setResultInfo(ResultInfo.LOGIN_OVERTIME);
            } else {
                pageInfo = singleBiddingResultService.requestSingleBiddingResult(1, APP_ID, uId, number);
            }

		} catch (Exception e) {
			e.printStackTrace();
			pageInfo.setCode(ResultInfo.SERVER_ERROR.getCode());
			pageInfo.setMsg(ResultInfo.SERVER_ERROR.getMsg());
		}

		return pageInfo;
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
	public @ResponseBody Object singleBiddingVersionOne(HttpServletResponse response, HttpServletRequest request,
			Integer tId, Model model) {
		LOGGER.info("用户发起单笔投标请求并跳转到华兴银行");
		response.setContentType("text/html;charset=UTF-8");
		JSONObject json = new JSONObject();
		PageInfo pageInfo = new PageInfo();
		String uId = null;
		// 发送投标报文
		try {
			uId = CommonMethodUtil.getUidBySession(request);
			pageInfo = singleBiddingService.requestSingleBiddingVersionOne(1, APP_ID, tId, uId);
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
