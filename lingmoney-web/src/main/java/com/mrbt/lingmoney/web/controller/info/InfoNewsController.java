package com.mrbt.lingmoney.web.controller.info;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;

import com.mrbt.lingmoney.model.InfoNews;
import com.mrbt.lingmoney.service.info.InfoNewsService;
import com.mrbt.lingmoney.service.users.UsersService;
import com.mrbt.lingmoney.utils.AppCons;
import com.mrbt.lingmoney.utils.GridPage;
import com.mrbt.lingmoney.utils.ResultParame;
import com.mrbt.lingmoney.web.controller.common.CommonMethodUtil;

/**
 * 公司动态
 * 
 * @author syb
 *
 */
@Controller
@RequestMapping("/infoNews")
public class InfoNewsController {
	private static final Logger LOG = LogManager.getLogger(InfoNewsController.class);
	@Autowired
	private InfoNewsService infoNewsService;
	@Autowired
	private UsersService usersService;

	/**
	 * 公司动态信息列表
	 * 
	 * @param model
	 *            数据包装
	 * @param pageNo
	 *            当前页数
	 * @param request
	 *            请求
	 * @return 返回信息
	 */
	@RequestMapping("/list")
	public String list(ModelMap model, Integer pageNo, HttpServletRequest request) {
		if (StringUtils.isEmpty(pageNo) || pageNo < 1) {
			pageNo = 1;
		}
		
		try {
			GridPage<InfoNews> gridPage = infoNewsService.listGrid(pageNo);
			
			model.addAttribute("gridPage", gridPage);
			model.addAttribute("pageNo", Integer.valueOf(pageNo));
			model.addAttribute("pageSize", AppCons.DEFAULT_PAGE_SIZE);
			model.addAttribute("totalSize", gridPage.getTotal());
			
			CommonMethodUtil.modelUserBaseInfo(model, request, usersService);
		} catch (Exception e) {
			LOG.info("查询公司动态信息失败，系统错误" + e.getMessage());
			e.printStackTrace();
			return String.valueOf(ResultParame.ResultInfo.SERVER_ERROR.getCode());
		}
		
		return "info/news";
	}

	/**
	 * 
	 * @param model
	 *            数据包装
	 * @param id
	 *            公司动态id
	 * @param request
	 *            请求
	 * @return 返回信息
	 */
	@RequestMapping("/details")
	public String deatils(ModelMap model, Integer id, HttpServletRequest request) {
		if (!StringUtils.isEmpty(id)) {
			try {
				infoNewsService.packageDetailsInfo(model, id);
				
				CommonMethodUtil.modelUserBaseInfo(model, request, usersService);
			} catch (Exception e) {
				LOG.error("查询公司动态错误" + e.getMessage());
				e.printStackTrace();
				return String.valueOf(ResultParame.ResultInfo.SERVER_ERROR.getCode());
			}
			
			return "info/newsDetail";
		} else {
			return ResultParame.ResultInfo.PARAMS_ERROR.getMsg();
		}
	}

}
