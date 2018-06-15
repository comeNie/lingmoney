package com.mrbt.lingmoney.web2.controller.bank;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mrbt.lingmoney.service.bank.AccountRechargeService;
import com.mrbt.lingmoney.utils.PageInfo;
import com.mrbt.lingmoney.utils.ResultParame;
import com.mrbt.lingmoney.utils.ResultParame.ResultInfo;
import com.mrbt.lingmoney.web2.controller.BaseController;
import com.mrbt.lingmoney.web2.controller.common.CommonMethodUtil;

/**
 * 用户充值
 * 
 * @author ruochen.yu
 *
 */

@Controller
@RequestMapping("bank")
public class AccountRechargeController extends BaseController {

	private static final Logger LOGGER = LogManager.getLogger(AccountRechargeController.class);

	@Autowired
	private AccountRechargeService accountRechargeService;

	/**
	 * 发起账户充值请求
	 * @param request req
	 * @param response res
	 * @param token tk
	 * @param model model
	 * @param amount amount
	 * @return json
	 */
	@RequestMapping(value = "/accountRecharge", method = RequestMethod.POST)
    public @ResponseBody Object accountRecharge(HttpServletRequest request, HttpServletResponse response, String amount) {
		LOGGER.info("用户发起充值请求并跳转到华兴银行");
		response.setContentType("text/html;charset=UTF-8");

		PageInfo pageInfo = new PageInfo();
		String uId = null;
		try {
            uId = CommonMethodUtil.getUidBySession(request);
            if (StringUtils.isEmpty(uId)) {
                pageInfo.setResultInfo(ResultInfo.LOGIN_OVERTIME);
            } else {
                pageInfo = accountRechargeService.accountRechargeRequest(CLIENT_TYPE, APP_ID, uId, amount);
            }
		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.error("单笔充值失败，失败原因是：" + e.getMessage());
            pageInfo.setResultInfo(ResultInfo.SERVER_ERROR);
		}
        return pageInfo;
	}

	/**
     * 账户充值银行异步通知报文
     * @param request req
     * @param response res
     * @param note note 用户uId
     * @return null
     */
    @RequestMapping("/accountRechargeCallBack/{note}")
    public void queryAccountRecharge(@PathVariable String note, HttpServletResponse response) {
        LOGGER.info("查询银行账户充值结果");
        PageInfo pageInfo = null;
        try {
            pageInfo = accountRechargeService.queryAccountRecharge(note);

            int status = ResultParame.ResultNumber.ZERO.getNumber();
            if (pageInfo != null) {
                if (pageInfo.getCode() == ResultParame.ResultInfo.SUCCESS.getCode()) {
                    status = ResultParame.ResultNumber.ONE.getNumber();
                } else if (pageInfo.getCode() == ResultParame.ResultInfo.NO_SUCCESS.getCode()) {
                    status = ResultParame.ResultNumber.TWO.getNumber();
                }
            }

            response.sendRedirect(HX_CALL_BACK_OPER_PAGE + status + "&oper=3");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * @param request req
     * @param response res
     * @param token token
     * @param number number
     * @return pageInfo
     */
	@RequestMapping("/queryAccountRecharge")
    public @ResponseBody Object queryAccountRecharge(HttpServletRequest request, HttpServletResponse response,
            String number) {
		LOGGER.info("查询充值结果");
        PageInfo pageInfo = new PageInfo();

        try {
            String uId = CommonMethodUtil.getUidBySession(request);

            if (StringUtils.isEmpty(uId)) {
                pageInfo.setResultInfo(ResultInfo.LOGIN_OVERTIME);
            } else {
                pageInfo = accountRechargeService.queryAccountRecharge(uId, number, "APP");
            }
        } catch (Exception e) {
            pageInfo.setResultInfo(ResultInfo.SERVER_ERROR);
            e.printStackTrace();
        }

		return pageInfo;
	}
}
