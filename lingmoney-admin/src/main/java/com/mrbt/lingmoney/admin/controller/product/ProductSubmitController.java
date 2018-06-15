package com.mrbt.lingmoney.admin.controller.product;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mrbt.lingmoney.admin.service.product.ProductSubmitService;
import com.mrbt.lingmoney.model.ProductSubmit;
import com.mrbt.lingmoney.utils.ResponseUtils;
import com.mrbt.lingmoney.utils.ResultParame.ResultNumber;
import com.mrbt.lingmoney.utils.RowBoundsUtils;

/**
 * 产品设置——》产品确认流程表
 * 
 * @author 000608
 *
 */
@Controller
@RequestMapping("/product/submit")
public class ProductSubmitController {

	@Autowired
	private ProductSubmitService productSubmitService;

	/**
	 * 删除
	 * @param id	数据ID
	 * @return	结果
	 */
	@RequestMapping("delete")
	@ResponseBody
	public Object delete(String id) {
		if (StringUtils.isNotBlank(id) && NumberUtils.isNumber(id) && NumberUtils.toInt(id) > 0) {
			try {
				productSubmitService.delete(NumberUtils.toInt(id));
				return ResponseUtils.success();
			} catch (Exception ex) {
				ex.printStackTrace();
				return ResponseUtils.failure(ResponseUtils.ERROR_SERVER, ex.getMessage());
			}
		} else {
			return ResponseUtils.failure(ResponseUtils.ERROR_PARAM, "参数错误");
		}
	}

	/**
	 * 查询列表
	 * @param type	类型
	 * @param page	页数
	 * @param rows	条数
	 * @return	列表
	 */
	@RequestMapping("listType")
	@ResponseBody
	public Object listType(int type, String page, String rows) {
		return productSubmitService.listGridType(type, RowBoundsUtils.getRowBounds(page, rows));
	}

	/**
	 * 查询
	 * @param vo	封装
	 * @param page	页数
	 * @param rows	条数
	 * @return	列表
	 */
	@RequestMapping("list")
	@ResponseBody
	public Object list(ProductSubmit vo, String page, String rows) {
		return productSubmitService.listGrid(vo, RowBoundsUtils.getRowBounds(page, rows));
	}

	/**
	 * 更新
	 * @param session	session
	 * @param vo	封装
	 * @param request	request
	 * @return	结果
	 */
	@RequestMapping("update")
	@ResponseBody
	public Object update(HttpSession session, ProductSubmit vo, HttpServletRequest request) {
		try {
			int res = productSubmitService.update(vo, request, session);
			if (res > 0) {
				return ResponseUtils.success();
			} else if (res == ResultNumber.MINUS2.getNumber()) {
                return ResponseUtils.failure(ResponseUtils.ERROR_SERVER, "华兴产品，发标成功后才可确认");
			} else {
				return ResponseUtils.failure(ResponseUtils.ERROR_SERVER, "更新失败");
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			return ResponseUtils.failure(ResponseUtils.ERROR_SERVER, ex.getMessage());
		}
	}

}
