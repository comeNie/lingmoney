package com.mrbt.lingmoney.web.controller.info;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;

import com.mrbt.lingmoney.model.InfoMedia;
import com.mrbt.lingmoney.service.info.InfoMediaService;
import com.mrbt.lingmoney.service.users.UsersService;
import com.mrbt.lingmoney.utils.AppCons;
import com.mrbt.lingmoney.utils.GridPage;
import com.mrbt.lingmoney.utils.ResultParame;
import com.mrbt.lingmoney.web.controller.common.CommonMethodUtil;

/**
 * 媒体资讯
 * 
 * @author syb
 *
 */
@Controller
@RequestMapping("/infoMedia")
public class InfoMediaController {
	private static final Logger LOG = LogManager.getLogger(InfoMediaController.class);
	@Autowired
	private InfoMediaService infoMediaService;
	@Autowired
	private UsersService usersService;

	/**
	 * 媒体资讯页列表
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
			GridPage<InfoMedia> gridPage = infoMediaService.listGrid(pageNo);
			
			model.addAttribute("gridPage", gridPage);
			model.addAttribute("pageNo", Integer.valueOf(pageNo));
			model.addAttribute("pageSize", AppCons.DEFAULT_PAGE_SIZE);
			model.addAttribute("totalSize", gridPage.getTotal());
			
			CommonMethodUtil.modelUserBaseInfo(model, request, usersService);
		} catch (Exception e) {
			LOG.error("进入媒体资讯页失败,系统错误" + e.getMessage());
			e.printStackTrace();
			return String.valueOf(ResultParame.ResultInfo.SERVER_ERROR.getCode());
		}
		
		return "info/media";
	}

	/**
	 * 查询媒体咨询详情
	 * 
	 * @param model
	 *            数据包装
	 * @param request
	 *            请求
	 * @param id
	 *            媒体咨询id
	 * @return 返回信息
	 */
	@RequestMapping("/details")
	public String deatils(ModelMap model, HttpServletRequest request, Integer id) {
		if (!StringUtils.isEmpty(id)) {
			try {
				infoMediaService.packageDetailInfo(model, id);
				
				CommonMethodUtil.modelUserBaseInfo(model, request, usersService);
			} catch (Exception e) {
				LOG.error("查询媒体咨询详情错误" + e.getMessage());
				e.printStackTrace();
				return String.valueOf(ResultParame.ResultInfo.SERVER_ERROR.getCode());
			}
			
			return "info/mediaDetail";
		} else {
			return ResultParame.ResultInfo.PARAMS_ERROR.getMsg();
		}
	}

}
