package com.mrbt.lingmoney.web.controller.info;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;

import com.mrbt.lingmoney.model.InfoNotice;
import com.mrbt.lingmoney.service.info.InfoNoticeService;
import com.mrbt.lingmoney.service.users.UsersService;
import com.mrbt.lingmoney.utils.AppCons;
import com.mrbt.lingmoney.utils.GridPage;
import com.mrbt.lingmoney.utils.ResultParame;
import com.mrbt.lingmoney.web.controller.common.CommonMethodUtil;

/**
 * 站内公告
 * 
 * @author syb
 *
 */
@Controller
@RequestMapping("/infoNotice")
public class InfoNoticeController {
	private static final Logger LOG = LogManager.getLogger(InfoNoticeController.class);
	@Autowired
	private InfoNoticeService infoNoticeService;
	@Autowired
	private UsersService usersService;

	/**
	 * 站内公告列表
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
		if (StringUtils.isEmpty(pageNo)) {
			pageNo = 1;
		}
		
		try {
			GridPage<InfoNotice> gridPage = infoNoticeService.listGrid(pageNo);
			
			model.addAttribute("gridPage", gridPage);
			model.addAttribute("pageNo", Integer.valueOf(pageNo));
			model.addAttribute("pageSize", AppCons.DEFAULT_PAGE_SIZE);
			model.addAttribute("totalSize", gridPage.getTotal());
			
			CommonMethodUtil.modelUserBaseInfo(model, request, usersService);
		} catch (Exception e) {
			LOG.error("查询站内公告失败" + e.getMessage());
			e.printStackTrace();
			return String.valueOf(ResultParame.ResultInfo.SERVER_ERROR.getCode());
		}
		
		return "info/notice";
	}

	/**
	 * 站内公告详情
	 * 
	 * @param model
	 *            数据包装
	 * @param id
	 *            站内公告id
	 * @param request
	 *            请求
	 * @return 返回信息
	 */
	@RequestMapping("/details")
	public String deatils(ModelMap model, Integer id, HttpServletRequest request) {
		if (!StringUtils.isEmpty(id)) {
			try {
				infoNoticeService.packageDetailInfo(model, id);
				
				CommonMethodUtil.modelUserBaseInfo(model, request, usersService);
			} catch (Exception e) {
				LOG.error("获取站内公告详情失败" + e.getMessage());
				e.printStackTrace();
				return String.valueOf(ResultParame.ResultInfo.SERVER_ERROR.getCode());
			}
			
			return "info/noticeDetail";
		} else {
			return ResultParame.ResultInfo.PARAMS_ERROR.getMsg();
		}
	}

}
