package com.mrbt.lingmoney.web.controller.home;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.mrbt.lingmoney.service.users.UsersService;
import com.mrbt.lingmoney.service.web.AreaDomainService;
import com.mrbt.lingmoney.service.web.IndexInfoService;
import com.mrbt.lingmoney.utils.ResultParame;
import com.mrbt.lingmoney.web.controller.common.CommonMethodUtil;

/**
 * 
 * 进入首页
 *
 */
@Controller
@RequestMapping("/")
public class HomeController {
	private static final Logger LOG = LogManager.getLogger(HomeController.class);
	@Autowired
	private AreaDomainService areaDomainService;
	@Autowired
	private IndexInfoService indexInfoService;
	@Autowired
	private UsersService usersService;
	
	/**
	 * 进入首页
	 * 
	 * @param model
	 *            数据包装
	 * @param request
	 *            请求
	 * @return 返回信息
	 */
	@RequestMapping("/index")
	public String index(ModelMap model, HttpServletRequest request) {
		try {
//			if(StringUtils.isNotBlank(domainCityCode)) {
//				AreaDomain ad = areaDomainService.queryByCityCode(domainCityCode);
//				if(ad!=null){
//					request.getSession().setAttribute("AREADOMAIN", ad);
//				}
//			}
//			AreaDomain ad = (AreaDomain) request.getSession().getAttribute("AREADOMAIN");
			indexInfoService.packIndexInfo(model, "131");
			CommonMethodUtil.modelUserBaseInfo(model, request, usersService);
			return "index";
		} catch (Exception e) {
			LOG.error("进入首页异常，系统错误" + e.getMessage());
			e.printStackTrace();
			return String.valueOf(ResultParame.ResultInfo.SERVER_ERROR.getCode());
		}
	}
	
}
