package com.mrbt.lingmoney.wap.controller.bank;

import javax.servlet.http.HttpServletRequest;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mrbt.lingmoney.service.bank.BankAccountBalanceService;
import com.mrbt.lingmoney.utils.PageInfo;
import com.mrbt.lingmoney.utils.ResultParame.ResultInfo;
import com.mrbt.lingmoney.wap.controller.BaseController;
import com.mrbt.lingmoney.wap.controller.common.CommonMethodUtil;

/**
 * 账户余额
 * @author luox
 * @Date 2017年6月5日
 */
@Controller
@RequestMapping(value = "/bank", method = RequestMethod.POST)
public class BankAccountBalanceController extends BaseController {
	private static final Logger LOG = LogManager.getLogger(BankAccountBalanceController.class);
	@Autowired
	private BankAccountBalanceService bankAccountBalanceService;
	
	/**
	 * 账户余额校验
	 * @param token token
	 * @param amount amount
	 * @return pageInfo
	 */
	@RequestMapping("/hxAccoutBalanceVerify")
    public @ResponseBody Object hxAccoutBalanceVerify(HttpServletRequest request, String amount) {
		LOG.info("账户余额校验");
		PageInfo pageInfo = new PageInfo();
		try {
            String uId = CommonMethodUtil.getUidBySession(request);
            if (StringUtils.isEmpty(uId)) {
                pageInfo.setResultInfo(ResultInfo.LOGIN_OVERTIME);
            } else {
                pageInfo = bankAccountBalanceService.hxAccoutBalanceVerify(APP_ID, uId, amount);
            }
		} catch (Exception e) {
			e.printStackTrace();
			LOG.error(e);
			pageInfo.setCode(ResultInfo.SERVER_ERROR.getCode());
			pageInfo.setMsg(ResultInfo.SERVER_ERROR.getMsg());
		}
		return pageInfo;
	}
	
	/**
	 * 华兴E账户
	 * @param token token
	 * @return pageInfo
	 */
	@RequestMapping("/userHxAccout")
    public @ResponseBody Object userHxAccout(HttpServletRequest request) {
		LOG.info("账户余额查询");
		PageInfo pageInfo = new PageInfo();
		try {
            String uId = CommonMethodUtil.getUidBySession(request);
            if (StringUtils.isEmpty(uId)) {
                pageInfo.setResultInfo(ResultInfo.LOGIN_OVERTIME);
            } else {
                pageInfo = bankAccountBalanceService.userHxAccout(uId);
            }

		} catch (Exception e) {
			e.printStackTrace();
			LOG.error(e);
			pageInfo.setCode(ResultInfo.SERVER_ERROR.getCode());
			pageInfo.setMsg(ResultInfo.SERVER_ERROR.getMsg());
		}
		return pageInfo;
	}
}
