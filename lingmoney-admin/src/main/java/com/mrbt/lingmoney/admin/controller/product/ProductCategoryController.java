package com.mrbt.lingmoney.admin.controller.product;

import java.util.Random;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mrbt.lingmoney.admin.service.product.ProductCategoryService;
import com.mrbt.lingmoney.model.ProductCategory;
import com.mrbt.lingmoney.utils.AppCons;
import com.mrbt.lingmoney.utils.ResponseUtils;
import com.mrbt.lingmoney.utils.RowBoundsUtils;
import com.mrbt.lingmoney.utils.StringFromatUtils;
import com.mrbt.lingmoney.utils.ResultParame.ResultNumber;

/**
 * 产品设置——》产品分类表
 * 
 * @author 000608
 *
 */
@Controller
@RequestMapping("/product/category")
public class ProductCategoryController {
	@Autowired
	private ProductCategoryService productCategoryService;

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
				productCategoryService.delete(NumberUtils.toInt(id));
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
	 * 
	 * @param vo	封装
	 * @param page	页数
	 * @param rows	条数
	 * @return	return
	 */
	@RequestMapping("list")
	@ResponseBody
	public Object list(ProductCategory vo, String page, String rows) {
		return productCategoryService.listGrid(vo, RowBoundsUtils.getRowBounds(page, rows));
	}

	/**
	 * 保存
	 * @param vo	封装
	 * @return	return
	 */
	@RequestMapping("save")
	@ResponseBody
	public Object save(ProductCategory vo) {
		/**
		 * 根据code码配置web项目中的处理类 生成规则：随机2位数字+（id前补0）4位id+2位g_id（前补0）+type
		 */
		try {
			if (vo.getType() == AppCons.FLOAT_FLAG) {
				vo.setFixType(0);
			}
			productCategoryService.save(vo);
			Random random = new Random();
			StringBuffer sb = new StringBuffer();
			sb.append(String.format("%s", random.nextInt(ResultNumber.NINE.getNumber()))).append(String.format("%s", random.nextInt(ResultNumber.NINE.getNumber())))
					.append(StringFromatUtils.leftFill(ResultNumber.FOUR.getNumber(), vo.getId()))
					.append(StringFromatUtils.leftFill(ResultNumber.TWO.getNumber(), vo.getgId())).append(String.format("%s", vo.getType()))
					.append(String.format("%s", vo.getFixType()));
			ProductCategory pc = new ProductCategory();
			pc.setCode(sb.toString());
			pc.setId(vo.getId());
			productCategoryService.update(pc);
			return ResponseUtils.success();
		} catch (Exception ex) {
			ex.printStackTrace();
			return ResponseUtils.failure(ResponseUtils.ERROR_SERVER, ex.getMessage());
		}
	}

	/**
	 * 更新
	 * @param vo 封装
	 * @return	return
	 */
	@RequestMapping("update")
	@ResponseBody
	public Object update(ProductCategory vo) {
		try {
			ProductCategory record = productCategoryService.findByPk(vo.getId());
			if (record.getStatus() == AppCons.PUBLIST_STATUS) {
				return ResponseUtils.failure(ResponseUtils.ERROR_PARAM, "生效状态不能更改");
			}
			if (vo.getType() == AppCons.FLOAT_FLAG) {
				vo.setFixType(0);
			}

			Random random = new Random();
			StringBuffer sb = new StringBuffer();
			sb.append(String.format("%s", random.nextInt(ResultNumber.NINE.getNumber()))).append(String.format("%s", random.nextInt(ResultNumber.NINE.getNumber())))
					.append(StringFromatUtils.leftFill(ResultNumber.FOUR.getNumber(), vo.getId()))
					.append(StringFromatUtils.leftFill(ResultNumber.TWO.getNumber(), vo.getgId())).append(String.format("%s", vo.getType()))
					.append(String.format("%s", vo.getFixType()));
			vo.setCode(sb.toString());
			productCategoryService.update(vo);
			return ResponseUtils.success();
		} catch (Exception ex) {
			ex.printStackTrace();
			return ResponseUtils.failure(ResponseUtils.ERROR_SERVER, ex.getMessage());
		}
	}

}
