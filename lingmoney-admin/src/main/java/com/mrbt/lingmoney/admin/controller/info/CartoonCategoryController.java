package com.mrbt.lingmoney.admin.controller.info;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.mrbt.lingmoney.admin.service.info.CartoonCategoryService;
import com.mrbt.lingmoney.model.CartoonCategory;
import com.mrbt.lingmoney.utils.PageInfo;
import com.mrbt.lingmoney.utils.ResultParame.ResultInfo;

/**
 * 页面设置——》漫画分类
 * 
 * @author lihq
 * @date 2017年5月5日 上午10:50:23
 * @description
 * @version 1.0
 * @since 2017-04-12
 */
@Controller
@RequestMapping("/info/cartooncategory")
public class CartoonCategoryController {
	
	@Autowired
	private CartoonCategoryService cartoonCategoryService;
	
	/**
	 * 图片保存的根目录
	 */
	private String indexPic = "cartooncategory";

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
			boolean flag = cartoonCategoryService.changeStatus(id);
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
			pageInfo.setMsg(ResultInfo.SERVER_ERROR.getMsg());
		}
		return pageInfo;
	}

	/**
	 * 添加
	 * 
	 * @param request	request
	 * @param vo	vo
	 * @return	vo
	 */
	@RequestMapping("save")
	@ResponseBody
	public Object save(MultipartHttpServletRequest request, CartoonCategory vo) {
		PageInfo pageInfo = new PageInfo();
		try {
			MultipartFile file = request.getFile("aimg");
			cartoonCategoryService.save(vo, file, indexPic);
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
	public Object list(CartoonCategory vo, Integer page, Integer rows) {
		PageInfo pageInfo = new PageInfo(page, rows);
		try {
			pageInfo = cartoonCategoryService.getList(vo, pageInfo);
		} catch (Exception e) {
			e.printStackTrace();
			pageInfo.setCode(ResultInfo.SERVER_ERROR.getCode());
			pageInfo.setMsg(ResultInfo.SERVER_ERROR.getMsg());
		}
		return pageInfo;
	}

	/**
	 * 删除
	 * 
	 * @param id
	 *            主键
	 * @return 分页实体类
	 */
	@RequestMapping("delete")
	@ResponseBody
	public Object delete(Integer id) {
		PageInfo pageInfo = new PageInfo();
		try {
			if (id != null && id > 0) {
				cartoonCategoryService.delete(id);
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
	 * 
	 * @param vo	vo
	 * @param request	request
	 * @return	return
	 */
	@RequestMapping("update")
	@ResponseBody
	public Object update(CartoonCategory vo, MultipartHttpServletRequest request) {
		PageInfo pageInfo = new PageInfo();
		try {
			if (vo.getId() != null) {
				MultipartFile file = request.getFile("eimg");
				cartoonCategoryService.update(vo, file, indexPic);
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

}
