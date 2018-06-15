package com.mrbt.lingmoney.admin.controller.bank;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mrbt.lingmoney.admin.service.bank.CompanyAdvPaymentService;
import com.mrbt.lingmoney.bank.deal.dto.HxCompanyAdvPaymentReqDto;
import com.mrbt.lingmoney.bank.utils.exception.ResponseInfoException;
import com.mrbt.lingmoney.commons.exception.PageInfoException;
import com.mrbt.lingmoney.utils.PageInfo;
import com.mrbt.lingmoney.utils.ResultParame.ResultInfo;

/**
 * 公司垫付还款
 * 
 * @author YJQ
 *
 */
@Controller
@RequestMapping("/bank")
public class HxCompanyAdvPaymentController {
	private static final Logger LOGGER = LogManager.getLogger(HxCompanyAdvPaymentController.class);

	@Autowired
	private CompanyAdvPaymentService companyAdvPaymentService;

	/**
	 * 公司垫付还款
	 * 
	 * @author YJQ 2017年6月12日
	 * @param request req
	 * @param loanNo	订单号
	 * @param bwAcName 账号名称
	 * @param bwAcNo 账号
	 * @param feeAmt	费率
	 * @param model model
	 * @return 返回处理结果
	 */
	@RequestMapping("/companyAdvPayment")
	@ResponseBody
	public Object companyAdvPayment(HttpServletRequest request, String loanNo, String bwAcName, String bwAcNo,
			String feeAmt, Model model) {
		LOGGER.info("公司垫付还款");
		PageInfo pageInfo = new PageInfo();
		try {
			HxCompanyAdvPaymentReqDto req = new HxCompanyAdvPaymentReqDto();
			req.setFEEAMT(feeAmt);
			req.setLOANNO(loanNo);
			Map<String, Object> res = companyAdvPaymentService.requestPayment(req);
			pageInfo.setObj(res);
			pageInfo.setCode(ResultInfo.SUCCESS.getCode());
			pageInfo.setMsg(ResultInfo.SUCCESS.getMsg());
		} catch (ResponseInfoException resE) {
			pageInfo.setCode(resE.getCode());
			pageInfo.setMsg(resE.getMessage());
		} catch (PageInfoException pageE) {
			pageInfo.setCode(pageE.getCode());
			pageInfo.setMsg(pageE.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.info("借款人单笔还款异常：" + e); // 抛出堆栈信息
			pageInfo.setCode(ResultInfo.SERVER_ERROR.getCode());
			pageInfo.setMsg(ResultInfo.SERVER_ERROR.getMsg());
		}
		return pageInfo;
	}

}
