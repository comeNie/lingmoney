package com.mrbt.lingmoney.admin.controller.product;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.mrbt.lingmoney.admin.service.product.ProductImgService;
import com.mrbt.lingmoney.model.ProductImg;
import com.mrbt.lingmoney.utils.MyUtils;
import com.mrbt.lingmoney.utils.PageInfo;
import com.mrbt.lingmoney.utils.ResultParame.ResultInfo;

/**
 * 产品设置——》产品图片设置
 * 
 * @author lihq
 * @date 2017年5月22日 下午5:28:53
 * @description
 * @version 1.0
 * @since 2017-04-12
 */
@Controller
@RequestMapping("/product/image")
public class ProductImgController {
	private Logger log = MyUtils.getLogger(ProductImgController.class);
	private String pictureUrl = "productImgUrl";

	@Autowired
	private ProductImgService productImgService;

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
		log.info("/product/image/delete");
		PageInfo pageInfo = new PageInfo();
		try {
			if (id != null && id > 0) {
				productImgService.delete(id);
				pageInfo.setCode(ResultInfo.SUCCESS.getCode());
				pageInfo.setMsg(ResultInfo.SUCCESS.getMsg());

			} else {
				pageInfo.setCode(ResultInfo.PARAMETER_ERROR.getCode());
				pageInfo.setMsg(ResultInfo.PARAMETER_ERROR.getMsg());
			}
		} catch (Exception e) {
			log.error(e.getMessage());
			e.printStackTrace();
			pageInfo.setMsg(ResultInfo.SERVER_ERROR.getMsg());
		}
		return pageInfo;
	}

	/**
	 * 分页查询
	 * @param vo 实体类
	 * @param page 当前页
	 * @param rows 每页显示的条数
	 * @return 分页实体类
	 */
	@RequestMapping("list")
	@ResponseBody
	public Object list(ProductImg vo, Integer page, Integer rows) {
		PageInfo pageInfo = new PageInfo(page, rows);
		try {
			pageInfo = productImgService.getList(vo, pageInfo);
		} catch (Exception e) {
			e.printStackTrace();
			pageInfo.setCode(ResultInfo.SERVER_ERROR.getCode());
			pageInfo.setMsg(ResultInfo.SERVER_ERROR.getMsg());
		}
		return pageInfo;
	}

	/**
	 * 根据类型查询产品图片列表
	 * 
	 * @param type 类型 0,WEB端我的理财产品图片 1,APP端产品背景图
	 * @return	结果
	 */
	@RequestMapping("listUrl")
	@ResponseBody
	public Object listUrl(Integer type) {
		return productImgService.listUrl(type);
	}

	/**
	 * 添加和修改
	 * 
	 * @param vo	封装
	 * @param request	request
	 * @return	结果
	 */
	@RequestMapping("saveAndUpdate")
	@ResponseBody
	public Object saveAndUpdate(ProductImg vo, MultipartHttpServletRequest request) {
		log.info("/product/recommend/saveAndUpdate");
		PageInfo pageInfo = new PageInfo();
		try {
			MultipartFile file = request.getFile("imageFile");
			if (vo.getId() == null) {
				productImgService.save(vo, file, pictureUrl);
			} else {
				productImgService.update(vo, file, pictureUrl);
			}
			pageInfo.setCode(ResultInfo.SUCCESS.getCode());
			pageInfo.setMsg(ResultInfo.SUCCESS.getMsg());
		} catch (Exception e) {
			log.error(e.getMessage());
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
			boolean flag = productImgService.changeStatus(id);
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

}
