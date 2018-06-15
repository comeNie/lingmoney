package com.mrbt.lingmoney.admin.controller.product;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mrbt.lingmoney.admin.service.product.ProductCategoryFixRateService;
import com.mrbt.lingmoney.model.ProductCategoryFixRate;
import com.mrbt.lingmoney.utils.ResponseUtils;
import com.mrbt.lingmoney.utils.RowBoundsUtils;

/**
 * 产品设置——》固定产品分类收益表
 * 
 * @author 000608
 *
 */
@Controller
@RequestMapping("/product/categoryFixRate")
public class ProductCategoryFixRateController {

	@Autowired
	private ProductCategoryFixRateService productCategoryFixRateService;

	/**
	 * 删除
	 * @param id	数据ID
	 * @return	return
	 */
	@RequestMapping("delete")
	@ResponseBody
	public Object delete(String id) {
		if (StringUtils.isNotBlank(id) && NumberUtils.isNumber(id) && NumberUtils.toInt(id) > 0) {
			try {
				productCategoryFixRateService.delete(NumberUtils.toInt(id));
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
	 * 查询
	 * @param vo	封装
	 * @param page	页数
	 * @param rows	条数
	 * @return	return
	 */
	@RequestMapping("list")
	@ResponseBody
	public Object list(ProductCategoryFixRate vo, String page, String rows) {
		return productCategoryFixRateService.listGrid(vo, RowBoundsUtils.getRowBounds(page, rows));
	}

	/**
	 * 保存
	 * @param vo	封装
	 * @return	return
	 */
	@RequestMapping("save")
	@ResponseBody
	public Object save(ProductCategoryFixRate vo) {
		try {
			productCategoryFixRateService.save(vo);
			return ResponseUtils.success();
		} catch (Exception ex) {
			ex.printStackTrace();
			return ResponseUtils.failure(ResponseUtils.ERROR_SERVER, ex.getMessage());
		}
	}

	/**
	 * 更新
	 * @param vo	封装
	 * @return	return
	 */
	@RequestMapping("update")
	@ResponseBody
	public Object update(ProductCategoryFixRate vo) {
		try {
			productCategoryFixRateService.update(vo);
			return ResponseUtils.success();
		} catch (Exception ex) {
			ex.printStackTrace();
			return ResponseUtils.failure(ResponseUtils.ERROR_SERVER, ex.getMessage());
		}
	}

}
