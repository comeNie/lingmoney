package com.mrbt.lingmoney.web2.controller.bank;

import javax.servlet.http.HttpServletRequest;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mrbt.lingmoney.service.bank.HxTenderService;
import com.mrbt.lingmoney.utils.PageInfo;
import com.mrbt.lingmoney.utils.ResultParame.ResultInfo;
import com.mrbt.lingmoney.web2.controller.BaseController;
import com.mrbt.lingmoney.web2.controller.common.CommonMethodUtil;

/**
 * 投标
 * @author luox
 */
@Controller
@RequestMapping(value = "/bank", method = RequestMethod.POST)
public class HxTenderController extends BaseController {
	private static final Logger LOG = LogManager.getLogger(HxTenderController.class);
	@Autowired
	private HxTenderService hxTenderService;
	
	/**
	 * 6.9	自动单笔投标
	 * @param request req
	 * @param token token
	 * @param loanno loanno
	 * @param amount amount
	 * @param remark remark
	 * @return pageInfo
	 */
	@RequestMapping("/autoTenderOne")
    public @ResponseBody Object autoTenderOne(HttpServletRequest request, String loanno, String amount, String remark) {
		LOG.info("自动单笔投标");
        PageInfo pageInfo = new PageInfo();
		try {
            String uId = CommonMethodUtil.getUidBySession(request);
            if (StringUtils.isEmpty(uId)) {
                pageInfo.setResultInfo(ResultInfo.LOGIN_OVERTIME);
            } else {
				pageInfo = hxTenderService.autoTenderOne(uId, APP_ID, loanno, amount, remark);
            }

		} catch (Exception e) {
			e.printStackTrace();
			LOG.error(e);
			pageInfo.setCode(ResultInfo.SERVER_ERROR.getCode());
			pageInfo.setMsg(e.getMessage());
		}
		return pageInfo;
	}
	
	/**
	 * 6.8	自动投标授权撤销
	 * @param request req
	 * @param token token
	 * @param otpseqno otpseqno
	 * @param otpno otpno
	 * @param remark remark
	 * @return pageInfo
	 */
	@RequestMapping("/autoTenderAuthorizeCancel")
    public @ResponseBody Object autoTenderAuthorizeCancel(HttpServletRequest request, String otpseqno, String otpno,
            String remark) {
		LOG.info("自动投标授权撤销");
        PageInfo pageInfo = new PageInfo();
		try {
            String uId = CommonMethodUtil.getUidBySession(request);
            if (StringUtils.isEmpty(uId)) {
                pageInfo.setResultInfo(ResultInfo.LOGIN_OVERTIME);
            } else {
				pageInfo = hxTenderService.autoTenderAuthorizeCancel(uId, APP_ID, otpseqno, otpno, remark);
            }

		} catch (Exception e) {
			e.printStackTrace();
			LOG.error(e);
			pageInfo.setCode(ResultInfo.SERVER_ERROR.getCode());
			pageInfo.setMsg(e.getMessage());
		}

		return pageInfo;
	}
	
	/**
	 * 6.7	自动投标授权结果查询
	 * @param request req
	 * @param token token
	 * @return pageInfo
	 */
	@RequestMapping("/autoTenderAuthorizeSearch")
    public @ResponseBody Object autoTenderAuthorizeSearch(HttpServletRequest request) {
		LOG.info("自动投标授权结果查询");
        PageInfo pageInfo = new PageInfo();
		try {
            String uId = CommonMethodUtil.getUidBySession(request);
            if (StringUtils.isEmpty(uId)) {
                pageInfo.setResultInfo(ResultInfo.LOGIN_OVERTIME);
            } else {
				pageInfo = hxTenderService.tenderReturnSearch(APP_ID, uId);
            }

		} catch (Exception e) {
			e.printStackTrace();
			LOG.error(e);
			pageInfo.setCode(ResultInfo.SERVER_ERROR.getCode());
			pageInfo.setMsg(e.getMessage());
		}

		return pageInfo;
	}
	
	
	/**
	 * 投标优惠返回结果查询
	 * @param request req
	 * @param token token
	 * @param oldreqseqno oldreqseqno
	 * @param subseqno subseqno
	 * @return pageInfo
	 */
	@RequestMapping("/tenderReturnSearch")
    public @ResponseBody Object tenderReturnSearch(HttpServletRequest request, String oldreqseqno, String subseqno) {
		LOG.info("投标优惠返回结果查询");
		PageInfo pageInfo = new PageInfo();
		try {
            String uId = CommonMethodUtil.getUidBySession(request);
			pageInfo = hxTenderService.tenderReturnSearch(APP_ID, uId, oldreqseqno, subseqno);
		} catch (Exception e) {
			e.printStackTrace();
			LOG.error(e);
			pageInfo.setCode(ResultInfo.SERVER_ERROR.getCode());
			pageInfo.setMsg(e.getMessage());
		}
		return pageInfo;
	}
	
	/**
	 * 投标优惠返回
	 * @param request request
	 * @param token token
	 * @param loanno	借款编号
	 * @param bwacname	还款账号户名
	 * @param bwacno	还款账号
	 * @param amount	优惠总金额
	 * @param totalnum	优惠总笔数
	 * @return pageInfo
	 */
	@RequestMapping("/tenderReturn")
    public @ResponseBody Object tenderReturn(HttpServletRequest request, String loanno, String bwacname, String bwacno,
            String amount, String totalnum) {
		LOG.info("投标优惠返回");
		PageInfo pageInfo = new PageInfo();
		try {
            String uId = CommonMethodUtil.getUidBySession(request);
			pageInfo = hxTenderService.tenderReturn(APP_ID, uId, loanno, bwacname, bwacno, amount, totalnum);
		} catch (Exception e) {
			e.printStackTrace();
			LOG.error(e);
			pageInfo.setCode(ResultInfo.SERVER_ERROR.getCode());
			pageInfo.setMsg(e.getMessage());
		}
		return pageInfo;
	}
	
}
