package com.mrbt.lingmoney.admin.controller.bank;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mrbt.lingmoney.admin.service.bank.DailyReconciliateService;
import com.mrbt.lingmoney.bank.deal.dto.HxDailyReconciliateReqDto;
import com.mrbt.lingmoney.bank.utils.exception.ResponseInfoException;
import com.mrbt.lingmoney.commons.exception.PageInfoException;
import com.mrbt.lingmoney.utils.PageInfo;
import com.mrbt.lingmoney.utils.ResultParame.ResultInfo;

/**
 * 日终对账
 * 
 * @author YJQ
 *
 */
@Controller
@RequestMapping("/bank/dailyReconciliate")
public class HxDailyReconciliateController {
	private static final Logger LOGGER = LogManager.getLogger(HxDailyReconciliateController.class);

	@Autowired
	private DailyReconciliateService dailyReconciliateService;

	/**
	 * 日终对账
	 * 
	 * @author YJQ 2017年6月15日
	 * @param request
	 * @return 返回处理结果
	 */
	@RequestMapping("/")
	public @ResponseBody PageInfo dailyRec() {
		LOGGER.info("日终对账");
		PageInfo pageInfo = new PageInfo();
		try {
			pageInfo.setObj(dailyReconciliateService.request(new HxDailyReconciliateReqDto()));
			pageInfo.setCode(ResultInfo.SUCCESS.getCode());
			pageInfo.setMsg(ResultInfo.SUCCESS.getMsg());
		} catch (ResponseInfoException resE) {
			pageInfo.setCode(resE.getCode());
			pageInfo.setMsg(resE.getMessage());
		} catch (PageInfoException pageE) {
			pageInfo.setCode(pageE.getCode());
			pageInfo.setMsg(pageE.getMessage());
		} catch (Exception e) {
			LOGGER.info("日终对账异常：" + e); // 抛出堆栈信息
			e.printStackTrace();
			pageInfo.setCode(ResultInfo.SERVER_ERROR.getCode());
			pageInfo.setMsg(ResultInfo.SERVER_ERROR.getMsg());
		}
		return pageInfo;
	}

	/**
	 * 日终对账分页列表
	 * @param startDate  开始时间
	 * @param endDate	结束时间
	 * @param page	单钱页数
	 * @param rows	每页条数
	 * @return	返回数据列表
	 */
	@RequestMapping("/list")
	public @ResponseBody Object list(String startDate, String endDate, Integer page, Integer rows) {
		LOGGER.info("查询日终对账列表");
		PageInfo pageInfo = new PageInfo(page, rows);
		try {
			pageInfo = dailyReconciliateService.queryList(startDate, endDate, pageInfo);
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
