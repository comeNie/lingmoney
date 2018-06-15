package com.mrbt.lingmoney.admin.controller.info;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.mrbt.lingmoney.admin.controller.base.BaseController;
import com.mrbt.lingmoney.admin.service.info.FinancialManagementService;
import com.mrbt.lingmoney.model.FinancialManagement;
import com.mrbt.lingmoney.utils.PageInfo;
import com.mrbt.lingmoney.utils.ResultParame.ResultInfo;

/**
 * 页面设置——》理财经
 * 
 * @author lihq
 * @date 2017年5月5日 上午9:14:30
 * @description
 * @version 1.0
 * @since 2017-04-12
 */
@Controller
@RequestMapping("/info/financial")
public class FinancialManagementController extends BaseController {
	@Autowired
	private FinancialManagementService financialManagementService;
	/**
	 * 图片保存的根目录
	 */
	private String indexPic = "financialmanagement";

	/**
	 * 添加
	 * 
	 * @param request	request
	 * @param vo	vo
	 * @return	返回结果
	 */
	@RequestMapping("save")
	@ResponseBody
	public Object save(MultipartHttpServletRequest request, FinancialManagement vo) {
		PageInfo pageInfo = new PageInfo();
		try {
			MultipartFile file = request.getFile("indexImg");
			financialManagementService.save(vo, file, indexPic);
			pageInfo.setCode(ResultInfo.SUCCESS.getCode());
			pageInfo.setMsg(ResultInfo.SUCCESS.getMsg());
		} catch (Exception e) {
			e.printStackTrace();
			pageInfo.setCode(ResultInfo.SERVER_ERROR.getCode());
			pageInfo.setMsg(ResultInfo.SERVER_ERROR.getMsg());
		}
		return pageInfo;
	}

	/**
	 * 分页查询
	 * 
	 * @param vo
	 *            实体类
	 * @param page
	 *            当前页
	 * @param rows
	 *            每页显示的条数
	 * @return 分页实体类
	 */
	@RequestMapping("list")
	@ResponseBody
	public Object list(FinancialManagement vo, Integer page, Integer rows) {
		PageInfo pageInfo = new PageInfo(page, rows);
		try {
			pageInfo = financialManagementService.getList(vo, pageInfo);
		} catch (Exception e) {
			e.printStackTrace();
			pageInfo.setCode(ResultInfo.SERVER_ERROR.getCode());
			pageInfo.setMsg(ResultInfo.SERVER_ERROR.getMsg());
		}
		return pageInfo;
	}

	/**
	 * 删除
	 * @param id 主键
	 * @return 分页实体类
	 */
	@RequestMapping("delete")
	@ResponseBody
	public Object delete(Integer id) {
		PageInfo pageInfo = new PageInfo();
		try {
			if (id != null && id > 0) {
				financialManagementService.delete(id);
				pageInfo.setCode(ResultInfo.SUCCESS.getCode());
				pageInfo.setMsg(ResultInfo.SUCCESS.getMsg());
			} else {
				pageInfo.setCode(ResultInfo.EMPTY_DATA.getCode());
				pageInfo.setMsg(ResultInfo.EMPTY_DATA.getMsg());
			}
		} catch (Exception e) {
			e.printStackTrace();
			pageInfo.setCode(ResultInfo.SERVER_ERROR.getCode());
			pageInfo.setMsg(ResultInfo.SERVER_ERROR.getMsg());
		}
		return pageInfo;
	}

	/**
	 * 更新
	 * @param request	request
	 * @param vo	vo
	 * @return	return
	 */
	@RequestMapping("update")
	@ResponseBody
	public Object update(MultipartHttpServletRequest request, FinancialManagement vo) {
		PageInfo pageInfo = new PageInfo();
		try {
			if (vo.getId() != null) {
				MultipartFile file = request.getFile("indexImg");
				financialManagementService.update(vo, file, indexPic);
				pageInfo.setCode(ResultInfo.SUCCESS.getCode());
				pageInfo.setMsg(ResultInfo.SUCCESS.getMsg());
			} else {
				pageInfo.setCode(ResultInfo.EMPTY_DATA.getCode());
				pageInfo.setMsg(ResultInfo.EMPTY_DATA.getMsg());
			}
		} catch (Exception e) {
			e.printStackTrace();
			pageInfo.setCode(ResultInfo.SERVER_ERROR.getCode());
			pageInfo.setMsg(ResultInfo.SERVER_ERROR.getMsg());
		}
		return pageInfo;
	}

	/**
	 * 返回text内容
	 * 
	 * @param id	id
	 * @return	return
	 */
	@RequestMapping("listContent")
	@ResponseBody
	public Object listContent(Integer id) {
		PageInfo pageInfo = new PageInfo();
		try {
			if (id != null && id > 0) {
				pageInfo.setObj(financialManagementService.listContent(id));
				pageInfo.setCode(ResultInfo.SUCCESS.getCode());
				pageInfo.setMsg(ResultInfo.SUCCESS.getMsg());
			} else {
				pageInfo.setCode(ResultInfo.EMPTY_DATA.getCode());
				pageInfo.setMsg(ResultInfo.EMPTY_DATA.getMsg());
			}
		} catch (Exception e) {
			e.printStackTrace();
			pageInfo.setCode(ResultInfo.SERVER_ERROR.getCode());
			pageInfo.setMsg(ResultInfo.SERVER_ERROR.getMsg());
		}
		return pageInfo;

	}

	/**
	 * 更改状态
	 * 
	 * @param id
	 *            主键
	 * @return 分页实体类
	 */
	@RequestMapping("publish")
	@ResponseBody
	public Object publish(Integer id) {
		PageInfo pageInfo = new PageInfo();
		try {
			boolean flag = financialManagementService.changeStatus(id);
			if (flag) {
				pageInfo.setCode(ResultInfo.SUCCESS.getCode());
				pageInfo.setMsg(ResultInfo.SUCCESS.getMsg());
			} else {
				pageInfo.setCode(ResultInfo.EMPTY_DATA.getCode());
				pageInfo.setMsg(ResultInfo.EMPTY_DATA.getMsg());
			}
		} catch (Exception e) {
			e.printStackTrace();
			pageInfo.setCode(ResultInfo.SERVER_ERROR.getCode());
			pageInfo.setMsg("服务器出错");
		}
		return pageInfo;
	}
}
