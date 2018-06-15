package com.mrbt.lingmoney.admin.controller.batchGiftRedPacket;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mrbt.lingmoney.admin.service.batchGiftRedPacket.BatchGiftRedPacketService;
import com.mrbt.lingmoney.utils.PageInfo;
import com.mrbt.lingmoney.utils.ResultParame.ResultInfo;

/** 
 * @author  suyibo 
 * @date 创建时间：2018年5月4日
 */
@Controller
@RequestMapping("/batchGift")
public class BatchGiftRedPacketController {
	@Autowired
	private BatchGiftRedPacketService batchGiftRedPacketService;

	/**
	 * 查找批量赠送对象
	 * @param channel 渠道号
	 * @param startDate 查询开始时间
	 * @param endDate 查询结束时间
	 * @param type 1：购买 2：兑付
	 * @param productName 产品名称
	 * @return 数据返回
	 */
	@RequestMapping("findUsersByParams")
	@ResponseBody
	public Object findUsersByParams(HttpServletRequest request, HttpServletResponse response, Integer channel,
			String startDate, String endDate, Integer type, String productName) {
		PageInfo pageInfo = new PageInfo();
		Integer pageNumber = Integer.valueOf(request.getParameter("page"));
		Integer pageSize = Integer.valueOf(request.getParameter("rows"));
		try {
			pageInfo = batchGiftRedPacketService.findUsersByParams(pageNumber, pageSize, channel, startDate, endDate,
					type, productName);
		} catch (Exception e) {
			e.printStackTrace();
			pageInfo.setCode(ResultInfo.SERVER_ERROR.getCode());
			pageInfo.setMsg(ResultInfo.SERVER_ERROR.getMsg());
		}
		return pageInfo;
	}

	/**
	 * 给指定人员赠送加息券
	 *
	 * @Description
	 * @param rId 红包ID
	 * @param uids 用户ID
	 * @return 返回处理结果
	 */
	@RequestMapping("/giveRedPacketByUid")
	public @ResponseBody Object giveRedPacketByUid(String rId, String uids) {
		PageInfo pageInfo = new PageInfo();

		try {
			if (!StringUtils.isEmpty(rId) && !StringUtils.isEmpty(uids)) {
				batchGiftRedPacketService.giveRedPacket(uids, rId);
				pageInfo.setCode(ResultInfo.SUCCESS.getCode());
				pageInfo.setMsg(ResultInfo.SUCCESS.getMsg());

			} else {
				pageInfo.setCode(ResultInfo.PARAM_MISS.getCode());
				pageInfo.setMsg(ResultInfo.PARAM_MISS.getMsg());
			}

		} catch (Exception e) {
			pageInfo.setCode(ResultInfo.SERVER_ERROR.getCode());
			pageInfo.setMsg(ResultInfo.SERVER_ERROR.getMsg());
			e.printStackTrace();
		}

		return pageInfo;
	}
}
