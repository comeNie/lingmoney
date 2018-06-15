package com.mrbt.lingmoney.admin.controller.bank;

import javax.servlet.http.HttpServletRequest;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mrbt.lingmoney.admin.service.bank.SinglePaymentService;
import com.mrbt.lingmoney.bank.deal.dto.HxQueryPaymentReqDto;
import com.mrbt.lingmoney.bank.deal.dto.HxQueryPaymentResDto;
import com.mrbt.lingmoney.bank.deal.dto.HxSinglePaymentReqDto;
import com.mrbt.lingmoney.bank.utils.dto.PageResponseDto;
import com.mrbt.lingmoney.utils.PageInfo;
import com.mrbt.lingmoney.utils.ResultParame.ResultInfo;

/**
 * 华兴E账户单笔还款
 * 
 * @author YJQ
 *
 */
@Controller
@RequestMapping("/bank/singlePayment")
public class HxSinglePaymentController {
	private static final Logger LOGGER = LogManager.getLogger(HxSinglePaymentController.class);

	@Autowired
	private SinglePaymentService singlePaymentService;

	/**
	 * 
	 * @return 返回页面
	 */
	@RequestMapping(value = "/requestTestPage", method = RequestMethod.GET)
	public String requestPage() {
		return "bank/testPayment";
	}
	
	/**
	 * 
	 * @return 返回页面
	 */
	@RequestMapping(value = "/testPage", method = RequestMethod.GET)
	public String test() {
		return "bank/testPaymentRes";
	}
	
	/**
	 * 
	 * @param requestData 请求数据
	 * @param errorPageHtml	错误HTML
	 * @param model	model
	 * @return 返回页面
	 */
	@RequestMapping(value = "/errorPage", method = RequestMethod.POST)
	public String hxBankErrorPage(String requestData, String errorPageHtml, Model model) {
		model.addAttribute("errorPageHtml", errorPageHtml);
		return "bank/HxBankErrorPage";
	}

	/**
	 * 单笔还款
	 * 
	 * @author YJQ 2017年6月12日
	 * @param request req
	 * @param oldReqSeqNo	原始订单号
	 * @param dfFlag	还款方式
	 * @param loanNo	订单号
	 * @param bwAcName	借款人姓名
	 * @param bwAcNo	借款人E账号
	 * @param amount	金额
	 * @param feeAmt	管理费
	 * @return	返回处理结果
	 */
	@RequestMapping("/")
	public @ResponseBody PageInfo singlePayment(HttpServletRequest request, String oldReqSeqNo, String dfFlag, String loanNo,
			String bwAcName, String bwAcNo, String feeAmt, String amount) {
		LOGGER.info("借款人单笔还款");
		PageInfo pageInfo = new PageInfo();
		try {
			HxSinglePaymentReqDto req = new HxSinglePaymentReqDto();
			req.setDFFLAG(dfFlag);
			req.setFEEAMT(feeAmt);
			req.setLOANNO(loanNo);
			req.setOLDREQSEQNO(oldReqSeqNo);
			req.setAMOUNT(amount);
			PageResponseDto res = singlePaymentService.requestPayment(req);
			pageInfo.setObj(res);
			pageInfo.setCode(ResultInfo.SUCCESS.getCode());
			pageInfo.setMsg(ResultInfo.SUCCESS.getMsg());
		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.info("借款人单笔还款异常：" + e); // 抛出堆栈信息
			pageInfo.setCode(ResultInfo.SERVER_ERROR.getCode());
            pageInfo.setMsg(e.getMessage());
		}
		return pageInfo;
	}

	/**
	 * 查询单笔还款结果
	 * 
	 * @author YJQ 2017年6月12日
	 * @param request	req
	 * @param oldReqSeqNo	原始订单编号
	 * @return	返回处理结果
	 */
	@RequestMapping("/queryResult")
	@ResponseBody
	public Object query(HttpServletRequest request, String oldReqSeqNo) {
		LOGGER.info("查询单笔还款结果");
		PageInfo pageInfo = new PageInfo();
		try {
			HxQueryPaymentReqDto dto = new HxQueryPaymentReqDto();
			dto.setOLDREQSEQNO(oldReqSeqNo);
			HxQueryPaymentResDto res = singlePaymentService.queryPaymentResult(dto);
			pageInfo.setCode(ResultInfo.SUCCESS.getCode());
			pageInfo.setMsg(ResultInfo.SUCCESS.getMsg());
			pageInfo.setObj(res);
		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.info("查询单笔还款结果异常：" + e); // 抛出堆栈信息
			pageInfo.setCode(ResultInfo.SERVER_ERROR.getCode());
            pageInfo.setMsg(e.getMessage());
		}
		return pageInfo;
	}

	/**
	 * 查询单笔还款分页列表
	 * 
	 * @author YJQ 2017年6月13日
	 * @param bwId 借款人ID
	 * @param page	当前页数
	 * @param rows	每页条数
	 * @return	返回查询列表
	 */
	@RequestMapping("/list")
	public @ResponseBody Object list(String bwId, Integer page, Integer rows) {
		LOGGER.info("查询单笔还款分页列表");
		PageInfo pageInfo = new PageInfo(page, rows);
		try {
			pageInfo = singlePaymentService.queryList(bwId, pageInfo);
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
			e.printStackTrace();
			pageInfo.setCode(ResultInfo.SERVER_ERROR.getCode());
            pageInfo.setMsg(e.getMessage());
		}
		return pageInfo;
	}
	
	/**
	 * 查询还款金额
	 * @author YJQ  2017年8月3日
	 * @param loanNo	账号
	 * @return	查询结果
	 */
	@RequestMapping("/queryAmount")
	public @ResponseBody Object queryAmount(String loanNo) {
		LOGGER.info("查询还款金额");
		PageInfo pageInfo = new PageInfo();
		try {
			pageInfo.setObj(singlePaymentService.queryAmount(loanNo));
			pageInfo.setCode(ResultInfo.SUCCESS.getCode());
			pageInfo.setMsg(ResultInfo.SUCCESS.getMsg());
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
			e.printStackTrace();
			pageInfo.setCode(ResultInfo.SERVER_ERROR.getCode());
            pageInfo.setMsg(e.getMessage());
		}
		return pageInfo;
	}

    /**
     * 标的结清通知1） 由 P2P 商户发起。 2） 借款人还清借款后，商户必须同步发起该接口通知银行标的已结清。
     * 3）该接口涉及后续借款人借款额度限制，若标的结清后不传送该通知接 口，则对借款人在续贷上会存影响。 4） 标的结清后将不再受理还款交易。
     * 5） 平台需将存量标的批量传送标的结清通知给到我行，并发数需控制在 10 笔/秒。
     * 
     * @author syb
     * @date 2017年9月4日 下午3:10:42
     * @version 1.0
     * @param loanNo	账户
     * @return	查询结果
     *
     */
	@RequestMapping("/hxRepaymentFinishNotice")
	public @ResponseBody Object hxRepaymentFinishNotice(String loanNo) {
		PageInfo pageInfo = new PageInfo();
		try {
			pageInfo = singlePaymentService.hxRepaymentFinishNotice(loanNo, "\n提交标的结清通知" + System.currentTimeMillis());
		} catch (Exception e) {
			LOGGER.error("提交标的结清通知失败，系统错误：{}", e.toString());
			e.printStackTrace();
			pageInfo.setMsg("系统错误");
		}
		return pageInfo;
	}

    @RequestMapping(value = "/repaymentAfterAdvance", method = RequestMethod.POST)
    @ResponseBody
    public Object repaymentAfterAdvance(String channelFlow) {
        PageInfo pageInfo = new PageInfo();
        try {
            pageInfo = singlePaymentService.repaymentAfterAdvance(channelFlow);
        } catch (Exception e) {
            pageInfo.setResultInfo(ResultInfo.SERVER_ERROR);
            e.printStackTrace();
        }

        return pageInfo;
    }
}
