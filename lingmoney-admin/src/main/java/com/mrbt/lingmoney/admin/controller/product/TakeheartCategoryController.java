package com.mrbt.lingmoney.admin.controller.product;


import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mrbt.lingmoney.admin.service.product.TakeheartCategoryService;
import com.mrbt.lingmoney.model.TakeheartCategory;
import com.mrbt.lingmoney.utils.MyUtils;
import com.mrbt.lingmoney.utils.ResponseUtils;
import com.mrbt.lingmoney.utils.ResultParame.ResultNumber;
import com.mrbt.lingmoney.utils.RowBoundsUtils;

/**
 * 产品设置——》随心取分类
 * 
 * @author lihq
 * @date 2017年5月17日 上午10:03:34
 * @description
 * @version 1.0
 * @since 2017-04-12
 */
@Controller
@RequestMapping("/takeheart/category")
public class TakeheartCategoryController {
	private Logger log = MyUtils.getLogger(TakeheartCategoryController.class);
	@Autowired
	private TakeheartCategoryService takeheartCategoryService;

	/**
	 * 
	 * @param id	ID
	 * @return	返回数据
	 */
	@RequestMapping("use")
	@ResponseBody
	public Object use(String id) {
		if (StringUtils.isNotBlank(id) && NumberUtils.isNumber(id) && NumberUtils.toInt(id) > 0) {
			try {
				TakeheartCategory record = takeheartCategoryService.findByPk(NumberUtils.toInt(id));
				if (record == null) {
					return ResponseUtils.failure(ResponseUtils.ERROR_PARAM, "未找到记录");
				}
				record.setStatus(ResultNumber.TWO.getNumber());
				takeheartCategoryService.update(record);
				return ResponseUtils.success();

			} catch (Exception e) {
				log.error(e);
				return ResponseUtils.failure(ResponseUtils.ERROR_SERVER, e.getMessage());
			}
		} else {
			return ResponseUtils.failure(ResponseUtils.ERROR_PARAM, "参数错误");
		}
	}

	/**
	 * 
	 * @param id	id
	 * @return 结果
	 */
	@RequestMapping("cancel")
	@ResponseBody
	public Object cancel(String id) {
		if (StringUtils.isNotBlank(id) && NumberUtils.isNumber(id) && NumberUtils.toInt(id) > 0) {
			try {
				TakeheartCategory record = takeheartCategoryService.findByPk(NumberUtils.toInt(id));
				if (record == null) {
					return ResponseUtils.failure(ResponseUtils.ERROR_PARAM, "未找到记录");
				}
				record.setStatus(ResultNumber.THREE.getNumber());
				takeheartCategoryService.update(record);
				return ResponseUtils.success();

			} catch (Exception e) {
				log.error(e);
				return ResponseUtils.failure(ResponseUtils.ERROR_SERVER, e.getMessage());
			}
		} else {
			return ResponseUtils.failure(ResponseUtils.ERROR_PARAM, "参数错误");
		}
	}

	/**
	 * 查询
	 * @param vo	封装类
	 * @param name 名称
	 * @param page	页数
	 * @param rows	条数
	 * @param status 状态
	 * @return	结果
	 */
	@RequestMapping("list")
	@ResponseBody
	public Object list(TakeheartCategory vo, String name, String rows, String page, Integer status) {
		if (name != null && !"".equals(name)) {
			vo.setName(name);
		}
		if (status != null) {
			vo.setStatus(status);
		}
		return takeheartCategoryService.listGrid(vo, RowBoundsUtils.getRowBounds(page, rows));
	}

	/**
	 * 保存
	 * 
	 * @param vo	封装实体类
	 * @return	结果
	 */
	@RequestMapping("save")
	@ResponseBody
	public Object save(TakeheartCategory vo) {

		try {
			vo.setStatus(0);
			takeheartCategoryService.save(vo);
			return ResponseUtils.success();
		} catch (Exception e) {
			log.error(e);
			return ResponseUtils.failure(ResponseUtils.ERROR_SERVER, e.getMessage());
		}
	}

	/**
	 * 更新
	 * 
	 * @param vo	封装
	 * @return	return
	 */
	@RequestMapping("update")
	@ResponseBody
	public Object update(TakeheartCategory vo) {
		try {
			if (vo.getId() == null || vo.getId() <= 0) {
				return ResponseUtils.failure(ResponseUtils.ERROR_PARAM, "参数错误");
			}
			TakeheartCategory pc = takeheartCategoryService.findByPk(vo.getId());
			if (pc == null) {
				return ResponseUtils.failure(ResponseUtils.ERROR_PARAM, "未找到记录");
			}

			takeheartCategoryService.update(vo);

			return ResponseUtils.success();
		} catch (Exception e) {
			log.error(e);
			return ResponseUtils.failure(ResponseUtils.ERROR_SERVER, e.getMessage());
		}
	}

	/**
	 * 发布
	 * @param vo	封装
	 * @return	return
	 */
	@RequestMapping("pub")
	@ResponseBody
    public Object pub(TakeheartCategory vo) {
		try {
			if (vo.getId() == null || vo.getId() <= 0) {
				return ResponseUtils.failure(ResponseUtils.ERROR_PARAM, "参数错误");
			}
			TakeheartCategory pc = takeheartCategoryService.findByPk(vo.getId());
			if (pc == null) {
				return ResponseUtils.failure(ResponseUtils.ERROR_PARAM, "未找到记录");
			}
			vo.setStatus(1);
			takeheartCategoryService.update(vo);

			return ResponseUtils.success();
		} catch (Exception e) {
			log.error(e);
			return ResponseUtils.failure(ResponseUtils.ERROR_SERVER, e.getMessage());
		}
	}
}
