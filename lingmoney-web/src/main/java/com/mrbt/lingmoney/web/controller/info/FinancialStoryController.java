package com.mrbt.lingmoney.web.controller.info;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;

import com.mrbt.lingmoney.model.CartoonCategory;
import com.mrbt.lingmoney.model.CartoonContent;
import com.mrbt.lingmoney.model.FinancialManagement;
import com.mrbt.lingmoney.service.discover.FinancialClassService;
import com.mrbt.lingmoney.service.users.UsersService;
import com.mrbt.lingmoney.utils.PageInfo;
import com.mrbt.lingmoney.utils.ResultParame;
import com.mrbt.lingmoney.web.controller.common.CommonMethodUtil;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * 理财小故事
 * 
 * @author syb
 * @Date 2017/05/23 14:11
 */
@Controller
@RequestMapping("/financialStory")
public class FinancialStoryController {
	private static final Logger LOG = LogManager.getLogger(FinancialStoryController.class);

	@Autowired
	private FinancialClassService financialClassService;
	@Autowired
	private UsersService usersService;

	/**
	 * 理财小故事首页
	 * 
	 * @param model
	 *            数据包装
	 * @param request
	 *            请求
	 * @return 返回信息
	 */
	@RequestMapping("/homeList")
	public String homeList(ModelMap model, HttpServletRequest request) {
		// 首页漫画贴分类
		List<CartoonCategory> listCartoonCategory = financialClassService.listCartoonCategory();
		if (listCartoonCategory != null && listCartoonCategory.size() > 0) {
			model.addAttribute("listCartoonCategory", listCartoonCategory);
			model.addAttribute("listCartoonCategorySize", listCartoonCategory.size());
		}
		
		// 首页理财经
		PageInfo pi = financialClassService.getIndexInfo(ResultParame.ResultNumber.ONE.getNumber(),
				ResultParame.ResultNumber.TEN.getNumber());
		if (pi.getCode() == ResultParame.ResultInfo.SUCCESS.getCode()) {
			@SuppressWarnings("unchecked")
			List<FinancialManagement> listFinancialManagement = pi.getRows();
			model.addAttribute("listFinancialManagement", listFinancialManagement);
			model.addAttribute("listFinancialManagementSize", listFinancialManagement.size());
		}
		
		try {
			CommonMethodUtil.modelUserBaseInfo(model, request, usersService);
		} catch (Exception e) {
			LOG.error("加载用户基本信息错误。" + e.getMessage());
			e.printStackTrace();
			return String.valueOf(ResultParame.ResultInfo.SERVER_ERROR.getCode());
		}
		
		return "info/financialStory";
	}

	/**
	 * 漫画贴分类页
	 * 
	 * @param model
	 *            数据包装
	 * @param request
	 *            请求
	 * @param id
	 *            分类id
	 * @return 返回信息
	 */
	@RequestMapping("/cartoonCategory")
	public String cartoonCategory(ModelMap model, HttpServletRequest request, Integer id) {
		if (StringUtils.isEmpty(id)) {
			return "info/financialStory";
		}
		
		// 漫画贴分类列表
		List<CartoonCategory> listCartoonCategory = financialClassService.listCartoonCategory();
		model.addAttribute("listCartoonCategory", listCartoonCategory);
		
		// 漫画贴列表(按分类id查询)
		List<CartoonContent> listCartoonContent = financialClassService.listCartoonContentByTypeId(id);
		model.addAttribute("listCartoonContent", listCartoonContent);
		
		// 漫画贴分类
		CartoonCategory cartoonCategory = financialClassService.findCartoonCategoryById(id);
		model.addAttribute("cartoonCategory", cartoonCategory);
		model.addAttribute("cid", id);
		
		try {
			CommonMethodUtil.modelUserBaseInfo(model, request, usersService);
		} catch (Exception e) {
			LOG.error("加载用户基本信息错误。" + e.getMessage());
			e.printStackTrace();
			return String.valueOf(ResultParame.ResultInfo.SERVER_ERROR.getCode());
		}
		
		return "info/cartoonCategory";
	}

	/**
	 * 漫画贴分类页JSON数据
	 * 
	 * @param modelMap
	 *            数据包装
	 * @param id
	 *            分类id
	 * @param response
	 *            响应
	 */
	@RequestMapping("/cartoonCategoryJson")
	public void cartoonCategoryJson(ModelMap modelMap, Integer id, HttpServletResponse response) {
		if (!StringUtils.isEmpty(id)) {
			
			// 漫画贴分类列表
			List<CartoonContent> listCartoonContent = financialClassService.listCartoonContentByTypeId(id);
			JSONObject jsonObject = new JSONObject();
			
			try {
				if (listCartoonContent.size() > ResultParame.ResultNumber.FOUR.getNumber()) {
					jsonObject.put("data",
							JSONArray.fromObject(listCartoonContent.subList(ResultParame.ResultNumber.FOUR.getNumber(),
									listCartoonContent.size())));
					jsonObject.write(response.getWriter());
				}
			} catch (IOException e) {
				e.printStackTrace();
				LOG.error("获取漫画贴数据失败，系统错误。\n" + e.getMessage());
			}
		}
	}

	/**
	 * 漫画贴详情页
	 * 
	 * @param model
	 *            数据包装
	 * @param request
	 *            请求
	 * @param id
	 *            分类id
	 * @return 返回信息
	 */
	@RequestMapping("/cartoonContent")
	public String cartoonContent(ModelMap model, HttpServletRequest request, Integer id) {
		if (StringUtils.isEmpty(id)) {
			return "info/cartoonCategory";
		}
		
		// 漫画贴分类列表
		List<CartoonCategory> listCartoonCategory = financialClassService.listCartoonCategory();
		model.addAttribute("listCartoonCategory", listCartoonCategory);
		
		// 漫画贴(根据id)
		CartoonContent cartoonContent = financialClassService.findCartoonContentById(id);
		model.addAttribute("cartoonContent", cartoonContent);
		
		// list用于接收id
		List<Integer> idList = financialClassService.listCartoonContentId(cartoonContent.getcId());
		int listCount = idList.size();
		int idThis = id;
		int idPre, idNext, indexThis;
		if (listCount > 0) {
			for (int i = 0; i < listCount; i++) {
				if (idThis == idList.get(i)) {
					indexThis = i;
					if (indexThis > 0 && indexThis <= listCount - 1) {
						idPre = idList.get(indexThis - 1);
						CartoonContent cartoonContentPre = financialClassService.findCartoonContentById(idPre);
						model.addAttribute("cartoonContent_pre", cartoonContentPre);
					}
					if (indexThis >= 0 && indexThis < listCount - 1) {
						idNext = idList.get(indexThis + 1);
						CartoonContent cartoonContentNext = financialClassService.findCartoonContentById(idNext);
						model.addAttribute("cartoonContent_next", cartoonContentNext);
					}
				}
			}
		}
		
		// 漫画贴分类(按分类id查询)
		CartoonCategory cartoonCategory = financialClassService.findCartoonCategoryById(cartoonContent.getcId());
		model.addAttribute("cartoonCategory", cartoonCategory);
		model.addAttribute("cid", cartoonContent.getcId());
		try {
			CommonMethodUtil.modelUserBaseInfo(model, request, usersService);
		} catch (Exception e) {
			LOG.error("加载用户基本信息错误。" + e.getMessage());
			e.printStackTrace();
			return String.valueOf(ResultParame.ResultInfo.SERVER_ERROR.getCode());
		}
		
		return "info/cartoonContent";
	}

	/**
	 * 理财经
	 * 
	 * @param model
	 *            数据包装
	 * @param request
	 *            请求
	 * @return 返回信息
	 */
	@RequestMapping("/financialManagement")
	public String financialManagement(ModelMap model, HttpServletRequest request) {
		// 漫画贴分类列表
		List<CartoonCategory> listCartoonCategory = financialClassService.listCartoonCategory();
		model.addAttribute("listCartoonCategory", listCartoonCategory);
		
		PageInfo pi = financialClassService.getIndexInfo(ResultParame.ResultNumber.ONE.getNumber(),
				ResultParame.ResultNumber.TEN.getNumber());
		if (pi.getCode() == ResultParame.ResultInfo.SUCCESS.getCode()) {
			@SuppressWarnings("unchecked")
			List<FinancialManagement> listFinancialManagement = pi.getRows();
			model.addAttribute("listFinancialManagement", listFinancialManagement);
		}
		
		try {
			CommonMethodUtil.modelUserBaseInfo(model, request, usersService);
		} catch (Exception e) {
			LOG.error("加载用户基本信息错误。" + e.getMessage());
			e.printStackTrace();
			return String.valueOf(ResultParame.ResultInfo.SERVER_ERROR.getCode());
		}
		
		return "info/financialManagement";
	}

	/**
	 * 理财经JSON数据
	 * 
	 * @param modelMap
	 *            数据包装
	 * @param response
	 *            响应
	 * @param pageNo
	 *            当前页数
	 * @throws IOException
	 *             io异常
	 */
	@RequestMapping("/financialManagementJson")
	public void financialManagementJson(ModelMap modelMap, HttpServletResponse response, Integer pageNo) {
		// 漫画贴分类列表
		PageInfo pi = financialClassService.getIndexInfo(pageNo, ResultParame.ResultNumber.FOUR.getNumber());
		if (pi.getCode() == ResultParame.ResultInfo.SUCCESS.getCode()) {
			@SuppressWarnings("unchecked")
			List<FinancialManagement> listFinancialManagement = pi.getRows();
			
			JSONObject jsonObject = new JSONObject();
			try {
				if (listFinancialManagement.size() > 0) {
					jsonObject.put("data", JSONArray.fromObject(listFinancialManagement));
					jsonObject.write(response.getWriter());
				}
			} catch (IOException e) {
				e.printStackTrace();
				LOG.error("查询理财经数据失败，系统错误。\n" + e.getMessage());
			}
		}
	}

	/**
	 * 理财经详情页
	 * 
	 * @param model
	 *            数据包装
	 * @param request
	 *            请求
	 * @param id
	 *            分类id
	 * @return 返回信息
	 */
	@RequestMapping("/financialManagementDetail")
	public String financialManagementDetail(ModelMap model, HttpServletRequest request, Integer id) {
		if (StringUtils.isEmpty(id)) {
			return "info/financialManagement";
		}
		
		// 漫画贴分类列表
		List<CartoonCategory> listCartoonCategory = financialClassService.listCartoonCategory();
		model.addAttribute("listCartoonCategory", listCartoonCategory);

		// 理财经(根据主键)
		FinancialManagement financialManagement = financialClassService.getDetailById(id);
		model.addAttribute("financialManagement", financialManagement);

		// list用于接收id
		List<Integer> idList = financialClassService.listFinancialManagementId();
		int listCount = idList.size();
		int idThis = id;
		int idPre, idNext, indexThis;
		if (listCount > 0) {
			for (int i = 0; i < listCount; i++) {
				if (idThis == idList.get(i)) {
					indexThis = i;
					if (indexThis > 0 && indexThis <= listCount - 1) {
						idPre = idList.get(indexThis - 1);
						FinancialManagement financialManagementPre = financialClassService.getDetailById(idPre);
						model.addAttribute("financialManagement_pre", financialManagementPre);
					}
					if (indexThis >= 0 && indexThis < listCount - 1) {
						idNext = idList.get(indexThis + 1);
						FinancialManagement financialManagementNext = financialClassService.getDetailById(idNext);
						model.addAttribute("financialManagement_next", financialManagementNext);
					}
				}

			}
		}
		
		try {
			CommonMethodUtil.modelUserBaseInfo(model, request, usersService);
		} catch (Exception e) {
			LOG.error("加载用户基本信息错误。" + e.getMessage());
			e.printStackTrace();
			return String.valueOf(ResultParame.ResultInfo.SERVER_ERROR.getCode());
		}
		
		return "info/financialManagementDetail";
	}
}
