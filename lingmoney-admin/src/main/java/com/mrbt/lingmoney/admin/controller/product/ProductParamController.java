package com.mrbt.lingmoney.admin.controller.product;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mrbt.lingmoney.admin.service.product.ProductParamService;
import com.mrbt.lingmoney.model.ProductParam;
import com.mrbt.lingmoney.utils.ResponseUtils;
import com.mrbt.lingmoney.utils.RowBoundsUtils;

/**
 * 产品设置——》产品参数表 产品分类表的分类信息，现在没有用到 节假日信息：现在在用
 * 
 * @author Administrator
 *
 */
@Controller
@RequestMapping("/product/param")
public class ProductParamController {

	@Autowired
	private ProductParamService productParamService;

	/**
	 * 删除
	 * @param id	数据ID
	 * @return	处理结果
	 */
	@RequestMapping("delete")
	@ResponseBody
	public Object delete(String id) {
		if (StringUtils.isNotBlank(id) && NumberUtils.isNumber(id) && NumberUtils.toInt(id) > 0) {
			try {
				productParamService.delete(NumberUtils.toInt(id));
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
	 * @param name	名称
	 * @param page	页数
	 * @param rows	条数
	 * @return	结果
	 */
	@RequestMapping("list/{name}")
	@ResponseBody
	public Object list(@PathVariable String name, String page, String rows) {
		return productParamService.listGrid(name, RowBoundsUtils.getRowBounds(page, rows));
	}

	/**
	 * 保存
	 * @param vo	封装
	 * @param	type	类型
	 * @return	结果
	 */
	@RequestMapping("save")
	@ResponseBody
	public Object save(ProductParam vo, String type) {
		try {
			if (StringUtils.isBlank(type)) {
				return ResponseUtils.failure(ResponseUtils.ERROR_PARAM, "参数错误");
			}
			productParamService.save(vo, type);
			return ResponseUtils.success();
		} catch (Exception ex) {
			ex.printStackTrace();
			return ResponseUtils.failure(ResponseUtils.ERROR_SERVER, ex.getMessage());
		}
	}

	/**
	 * 更新
	 * @param vo	vo
	 * @return	结果
	 */
	@RequestMapping("update")
	@ResponseBody
	public Object update(ProductParam vo) {
		try {
			productParamService.update(vo);
			return ResponseUtils.success();
		} catch (Exception ex) {
			ex.printStackTrace();
			return ResponseUtils.failure(ResponseUtils.ERROR_SERVER, ex.getMessage());
		}
	}

}
