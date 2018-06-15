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

import com.mrbt.lingmoney.service.bank.TransferDebtService;
import com.mrbt.lingmoney.utils.PageInfo;
import com.mrbt.lingmoney.utils.ResultParame.ResultInfo;
import com.mrbt.lingmoney.web2.controller.BaseController;
import com.mrbt.lingmoney.web2.controller.common.CommonMethodUtil;

/**
 * 债权转让
 * @author luox
 * @Date 2017年6月8日
 */
@Controller
@RequestMapping(value = "/bank")
public class TransferDebtController extends BaseController {
	private static final Logger LOGGER = LogManager.getLogger(TransferDebtController.class);
	@Autowired
	private TransferDebtService transferDebtService;
	
	/**
	 * 6.11	债权转让结果查询
	 * @param token token
	 * @return pageInfo
	 */
	@RequestMapping(value = "/transferDebtAssignmentSearch", method = RequestMethod.POST)
    public @ResponseBody Object transferDebtAssignmentSearch(HttpServletRequest request) {
		LOGGER.info("债权转让结果查询");
		PageInfo pageInfo = new PageInfo();
		try {
            String uId = CommonMethodUtil.getUidBySession(request);
            if (StringUtils.isEmpty(uId)) {
                pageInfo.setResultInfo(ResultInfo.LOGIN_OVERTIME);
            } else {
        		pageInfo = transferDebtService.transferDebtAssignmentSearch(APP_ID, uId);
            }
		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.error(e);
			pageInfo.setCode(ResultInfo.SERVER_ERROR.getCode());
			pageInfo.setMsg(e.getMessage());
		}
		return pageInfo;
	}
	
	/**
	 * 债权转让申请
	 * @param response req
	 * @param model model
	 * @param token token
	 * @param amount amount
	 * @param remark remark
	 * @param pCode pCode
	 * @return json
	 */
	@RequestMapping(value = "/transferDebt", method = RequestMethod.POST)
	@ResponseBody
    public Object transferDebt(HttpServletRequest request, String amount, String remark, Integer tId) {
		LOGGER.info("债权转让申请");
		PageInfo pageInfo = new PageInfo();
		try {
            String uId = CommonMethodUtil.getUidBySession(request);
            if (StringUtils.isEmpty(uId)) {
                pageInfo.setResultInfo(ResultInfo.LOGIN_OVERTIME);
            } else {
//                pageInfo = transferDebtService.transferDebtAssignment(uId, 1, APP_ID, remark, tId);
            }
		} catch (Exception e) {
			e.printStackTrace();
            pageInfo.setResultInfo(ResultInfo.SERVER_ERROR);
		}

        return pageInfo;
	}
	
}
