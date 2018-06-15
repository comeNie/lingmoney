package com.mrbt.lingmoney.admin.controller.pay;

import java.util.HashMap;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mrbt.lingmoney.admin.service.pay.PaymentConfServices;
import com.mrbt.lingmoney.model.PaymentPartition;
import com.mrbt.lingmoney.utils.MyUtils;
import com.mrbt.lingmoney.utils.ResultParame.ResultInfo;

/**
 * 企业管理——》兑付确认
 * 
 * @author lihq
 * @date 2017年5月22日 下午2:00:25
 * @description
 * @version 1.0
 * @since 2017-04-12
 */
@Controller
@RequestMapping("/paymentConf")
public class PaymentConfController {
	
	private Logger log = MyUtils.getLogger(PaymentConfController.class);
	
	@Autowired
	private PaymentConfServices paymentVonfServices;

	/**
	 * 查询需要确认兑付的数据
	 * @param platUserNo	platUserNo
	 * @return	返回处理结果
	 */
	@RequestMapping("/paymentList")
	@ResponseBody
	public Object paymentList(String platUserNo) {
		log.info("/paymentConf/paymentList");
		HashMap<String, Object> map = new HashMap<String, Object>();
		List<PaymentPartition> result = paymentVonfServices.paymentList();
		log.info(result);
		map.put("rows", result);
		map.put("total", result.size());
		return map;
	}

	/**
	 * 确认兑付
	 * @param idStr	idStr
	 * @return	返回处理结果
	 */
	@RequestMapping("/paymentSubmission")
	@ResponseBody
	public Object paymentSubmission(String idStr) {
		log.info("/paymentConf/paymentSubmission");
		HashMap<String, Object> map = new HashMap<String, Object>();

		if (idStr != null && !idStr.equals("")) {
			Integer relInt = paymentVonfServices.paymentSubmission(idStr);
			if (relInt > 0) {
				map.put("msg", "确认兑付成功");
				map.put("code", ResultInfo.SUCCESS.getCode());
			} else {
				map.put("msg", "确认没有成功，请与技术人员联系！");
				map.put("code", ResultInfo.MODIFY_REJECT.getCode());
			}
		} else {
			map.put("msg", "没有选择确认兑付的数据");
			map.put("code", ResultInfo.EMPTY_DATA.getCode());
		}
		return map;
	}
}
