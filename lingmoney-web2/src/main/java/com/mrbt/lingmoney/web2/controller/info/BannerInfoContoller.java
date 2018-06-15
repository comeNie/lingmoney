package com.mrbt.lingmoney.web2.controller.info;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mrbt.lingmoney.service.common.BannerInfoService;
import com.mrbt.lingmoney.utils.PageInfo;
import com.mrbt.lingmoney.utils.ResultParame.ResultInfo;
/**
 * @author lihq
 * @date 2017年6月6日 下午3:01:16
 * @description
 * @version 1.0
 * @since 2017-04-12
 */
@Controller
@RequestMapping("/bannerInfo")
public class BannerInfoContoller {
	
	private static final Logger LOGGER = LogManager.getLogger(BannerInfoContoller.class);
	
	@Autowired
	private BannerInfoService bannerInfoService;
	
	/**
	 * 首页banner
	 * 
	 * @param response req
	 * @param request res
	 * @param cityCode cityCode
	 * @param sizeCode sizeCode
	 * @return pageInfo
	 */
	@RequestMapping(value = "/homeMainBanner", method = RequestMethod.POST)
	public @ResponseBody Object homeMainBanner(HttpServletResponse response, HttpServletRequest request, String cityCode) {
		
		LOGGER.info("bannerInfo cityCode:" + cityCode);

		PageInfo pageInfo = new PageInfo();
		try {
			bannerInfoService.queryPcHomeBanner(cityCode, pageInfo);
		} catch (Exception e) {
			e.printStackTrace();
			pageInfo.setCode(ResultInfo.SERVER_ERROR.getCode());
			pageInfo.setMsg(ResultInfo.SERVER_ERROR.getMsg());
		}

		return pageInfo;
	}
}
