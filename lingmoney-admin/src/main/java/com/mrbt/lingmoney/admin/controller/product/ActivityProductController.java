package com.mrbt.lingmoney.admin.controller.product;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.mrbt.lingmoney.admin.controller.base.BaseController;
import com.mrbt.lingmoney.admin.service.product.ActivityProductService;
import com.mrbt.lingmoney.admin.service.product.ProductService;
import com.mrbt.lingmoney.model.ActivityProduct;
import com.mrbt.lingmoney.model.ActivityProductWithBLOBs;
import com.mrbt.lingmoney.model.Product;
import com.mrbt.lingmoney.utils.PageInfo;
import com.mrbt.lingmoney.utils.ResultParame.ResultInfo;
import com.mrbt.lingmoney.utils.ResultParame.ResultNumber;

/**
 * 产品设置——》产品活动表
 * 
 * @author lihq
 * @date 2017年7月10日 上午9:12:14
 * @description
 * @version 1.0
 * @since 2017-04-12
 */
@Controller
@RequestMapping("product/activityProduct")
public class ActivityProductController extends BaseController {

	@Autowired
	private ActivityProductService activityProductService;

	@Autowired
	private ProductService productService;

	/**
	 * 图片保存的根目录
	 */
	private String bannerRootPath = "activityProduct";

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
			boolean flag = activityProductService.changeStatus(id);
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
	public Object list(ActivityProduct vo, Integer page, Integer rows) {
		PageInfo pageInfo = new PageInfo(page, rows);
		try {
			pageInfo = activityProductService.getList(vo, pageInfo);
		} catch (Exception e) {
			e.printStackTrace();
			pageInfo.setCode(ResultInfo.SERVER_ERROR.getCode());
			pageInfo.setMsg(ResultInfo.SERVER_ERROR.getMsg());
		}
		return pageInfo;
	}

	/**
	 * 查询产品
	 * @return	返回结果
	 */
	@RequestMapping("listProduct")
	@ResponseBody
	public Object listProduct() {
		Product product = new Product();
		product.setStatus(ResultNumber.ONE.getNumber());
		product.setRule(ResultNumber.TWO.getNumber());
		return productService.selectByActivity(product);
	}
	/**
	 * 查询活动
	 * @return	返回结果
	 */
	@RequestMapping("listActivityProduct")
	@ResponseBody
	public Object listActivityProduct() {
		ActivityProduct activityProduct = new ActivityProduct();
		activityProduct.setStatus(ResultNumber.ONE.getNumber());
		return activityProductService.selectByActivityProduct(activityProduct);
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
				int res = activityProductService.delete(id);
				if (res > 0) {
					pageInfo.setCode(ResultInfo.SUCCESS.getCode());
					pageInfo.setMsg(ResultInfo.SUCCESS.getMsg());
				} else {
					pageInfo.setCode(ResultInfo.EMPTY_DATA.getCode());
					pageInfo.setMsg(ResultInfo.EMPTY_DATA.getMsg());
				}
			} else {
				pageInfo.setCode(ResultInfo.PARAMETER_ERROR.getCode());
				pageInfo.setMsg(ResultInfo.PARAMETER_ERROR.getMsg());
			}
		} catch (Exception e) {
			e.printStackTrace();
			pageInfo.setCode(ResultInfo.SERVER_ERROR.getCode());
			pageInfo.setMsg(ResultInfo.SERVER_ERROR.getMsg());
		}
		return pageInfo;
	}

	/**
	 * 保存
	 * @param vo	封装对象
	 * @param request request
	 * @return	返回结果
	 */
	@RequestMapping("saveAndUpdate")
	@ResponseBody
	public Object saveAndUpdate(MultipartHttpServletRequest request, ActivityProductWithBLOBs vo) {
		PageInfo pageInfo = new PageInfo();
		try {
			MultipartFile file1 = request.getFile("path1");
			MultipartFile file2 = request.getFile("path2");
			MultipartFile file3 = request.getFile("path3");
			int res = activityProductService.saveAndUpdate(vo, file1, file2, file3, bannerRootPath);
			if (res > 0) {
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
	 * 修改活动产品，通过ID查询活动产品描述
	 * @param id	数据ID
	 * @return	返回结果
	 */
	@RequestMapping("listContent")
	@ResponseBody
	public Object listContent(Integer id) {
		PageInfo pageInfo = new PageInfo();
		try {
			if (id != null && id > 0) {
				List<String> list = activityProductService.listContent(id);
				pageInfo.setCode(ResultInfo.SUCCESS.getCode());
				pageInfo.setMsg(ResultInfo.SUCCESS.getMsg());
				pageInfo.setObj(list);
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
