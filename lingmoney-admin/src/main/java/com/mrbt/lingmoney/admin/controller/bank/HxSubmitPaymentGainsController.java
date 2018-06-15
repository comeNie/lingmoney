package com.mrbt.lingmoney.admin.controller.bank;

import javax.servlet.http.HttpServletRequest;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mrbt.lingmoney.admin.service.bank.SubmitPaymentGainsService;
import com.mrbt.lingmoney.bank.deal.dto.HxQuerySubmitPaymentReqDto;
import com.mrbt.lingmoney.utils.PageInfo;
import com.mrbt.lingmoney.utils.ResultParame.ResultInfo;

/**
 * 华兴E账户-提交还款收益明细
 * 
 * @author YJQ
 *
 */
@Controller
@RequestMapping("/bank/submitPaymentGains")
public class HxSubmitPaymentGainsController {
	private static final Logger LOGGER = LogManager.getLogger(HxSubmitPaymentGainsController.class);

	@Autowired
	private SubmitPaymentGainsService submitPaymentGainsService;

	/**
	 * 提交还款收益明细
	 * @param channelFlow	渠道流水
	 * @return	返回
	 */
	@RequestMapping("/")
	public @ResponseBody Object submitPaymentGains(String channelFlow) {
		LOGGER.info("提交还款收益明细-手动添加中断提交至redis队列");
		PageInfo pageInfo = new PageInfo();
		try {
			submitPaymentGainsService.addRedisQueue(channelFlow);
			pageInfo.setCode(ResultInfo.SUCCESS.getCode());
			pageInfo.setMsg(ResultInfo.SUCCESS.getMsg());
		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.info("提交还款收益明细异常：" + e); // 抛出堆栈信息
			pageInfo.setCode(ResultInfo.SERVER_ERROR.getCode());
			pageInfo.setMsg(ResultInfo.SERVER_ERROR.getMsg());
		}
		return pageInfo;
	}

	/**
	 * 查询提交还款收益明细结果
	 * @param request	req
	 * @param oldReqSeqNo	原交易流水号
	 * @param loanNo	借款标号
	 * @param subSeqNo	提交交易号
	 * @return	返回处理结果
	 */
	@RequestMapping("/query")
	public @ResponseBody Object query(HttpServletRequest request, String oldReqSeqNo, String loanNo, String subSeqNo) {
		LOGGER.info("查询提交还款收益明细结果");
		PageInfo pageInfo = new PageInfo();
		try {
			HxQuerySubmitPaymentReqDto dto = new HxQuerySubmitPaymentReqDto();
			dto.setOLDREQSEQNO(oldReqSeqNo);
			dto.setLOANNO(loanNo);
			dto.setSUBSEQNO(subSeqNo);
			pageInfo.setObj(submitPaymentGainsService.querySubmitResult(dto));
			pageInfo.setCode(ResultInfo.SUCCESS.getCode());
			pageInfo.setMsg(ResultInfo.SUCCESS.getMsg());
		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.info("查询提交还款收益明细结果：" + e.toString()); // 抛出堆栈信息
			pageInfo.setCode(ResultInfo.SERVER_ERROR.getCode());
            pageInfo.setMsg(e.getMessage());
		}
		return pageInfo;
	}

	/**
	 * 提交还款收益列表
	 * @param loanNo	借款编号
	 * @param channelFlow	流水号
	 * @param page	页数
	 * @param rows	条数
	 * @return	返回列表
	 */
	@RequestMapping("/list")
	public @ResponseBody Object list(String loanNo, String channelFlow, Integer page, Integer rows) {
		LOGGER.info("查询提交还款收益明细列表");
		PageInfo pageInfo = new PageInfo(page, rows);
		try {
			pageInfo = submitPaymentGainsService.queryList(loanNo, channelFlow, pageInfo);
			pageInfo.setCode(ResultInfo.SUCCESS.getCode());
			pageInfo.setMsg(ResultInfo.SUCCESS.getMsg());
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
			e.printStackTrace();
			pageInfo.setCode(ResultInfo.SERVER_ERROR.getCode());
			pageInfo.setMsg(ResultInfo.SERVER_ERROR.getMsg());
		}
		return pageInfo;
	}

	/**
	 * 查询提交还款收益明细投资人列表
	 * @author YJQ  2017年7月20日
	 * @param loanNo	借款编号
	 * @param page	页数
	 * @param rows	条数
	 * @return	返回列表
	 */
	@RequestMapping("/repayList")
	public @ResponseBody Object repayList(String loanNo, Integer page, Integer rows) {
		LOGGER.info("查询提交还款收益明细投资人列表");
		PageInfo pageInfo = new PageInfo(page, rows);
		try {
			pageInfo = submitPaymentGainsService.queryRepayList(loanNo, pageInfo);
			pageInfo.setCode(ResultInfo.SUCCESS.getCode());
			pageInfo.setMsg(ResultInfo.SUCCESS.getMsg());
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
			e.printStackTrace();
			pageInfo.setCode(ResultInfo.SERVER_ERROR.getCode());
			pageInfo.setMsg(ResultInfo.SERVER_ERROR.getMsg());
		}
		return pageInfo;
	}
}
