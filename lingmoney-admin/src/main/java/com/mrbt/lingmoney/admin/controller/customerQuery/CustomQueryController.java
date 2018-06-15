package com.mrbt.lingmoney.admin.controller.customerQuery;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mrbt.lingmoney.admin.service.customerQuery.CustomQueryService;

/**
 * @author yiban sun
 * @date 2017年7月25日 下午2:10:27
 * @version 1.0
 * @description 自定义查询 --》产品购买情况
 * @since
 **/
@Controller
@RequestMapping("/customQuery")
public class CustomQueryController {
	
	@Autowired
	private CustomQueryService customQueryService;
	

	/**
	 * 
	 *@Description  查询产品购买情况。购买成功以及待支付的用户姓名，手机号，理财经理
	 *@param pid	产品ID
	 *@param status	状态
	 *@param page	页数
	 *@param rows	每页条数
	 *@return	返回查询结果
	 */
	@RequestMapping("/productSellTradingInfo")
	public @ResponseBody Object productSellTradingInfo(Integer pid, Integer status, Integer page, Integer rows) {
		return customQueryService.queryProductSellTradingInfo(pid, status, page, rows);
	}
	
	/**
	 * 
	 * @Description 产品购买情况统计数据。包括产品募集规模，已筹规模，成功交易金额，占用金额，
	 * @param pid	产品ID
	 * @return	返回查询结果
	 */
	@RequestMapping("/productSellSumInfo")
	public @ResponseBody Object productSellSumInfo(Integer pid) {
		return customQueryService.queryProductSellSumInfo(pid);
	}
	
}
