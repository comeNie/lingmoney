package com.mrbt.lingmoney.web2.controller.common;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mrbt.lingmoney.service.web.IndexInfoService;
import com.mrbt.lingmoney.utils.PageInfo;
import com.mrbt.lingmoney.utils.ResultParame.ResultInfo;

/**
 * 
 * 进入首页
 *
 */
@Controller
@RequestMapping("/home")
public class HomeController {
	private static final Logger LOGGER = LogManager.getLogger(HomeController.class);
	@Autowired
	private IndexInfoService indexInfoService;
		
	/**
	 * 查询累计数据
	 * @param response
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/queryTotal", method = RequestMethod.POST)
	public @ResponseBody Object queryTotal(HttpServletResponse response, HttpServletRequest request) {
		
		LOGGER.info("查询累计数据");
		PageInfo pageInfo = new PageInfo();
		try {
			indexInfoService.queryTotalData(pageInfo);
		} catch (Exception e) {
			e.printStackTrace();
			pageInfo.setCode(ResultInfo.SERVER_ERROR.getCode());
			pageInfo.setMsg(ResultInfo.SERVER_ERROR.getMsg());
		}

		return pageInfo;
	}
	
}
