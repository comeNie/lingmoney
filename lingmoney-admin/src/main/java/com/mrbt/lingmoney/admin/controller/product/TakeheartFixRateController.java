package com.mrbt.lingmoney.admin.controller.product;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mrbt.lingmoney.admin.service.product.TakeheartFixRateService;
import com.mrbt.lingmoney.model.TakeheartFixRate;
import com.mrbt.lingmoney.utils.MyUtils;
import com.mrbt.lingmoney.utils.ResponseUtils;
import com.mrbt.lingmoney.utils.RowBoundsUtils;

/**
 * 随心取固定收益率设置
 * 
 * @author 000608
 *
 */
@Controller
@RequestMapping("/takeheart/fixRate")
public class TakeheartFixRateController {
	private Logger log = MyUtils.getLogger(TakeheartFixRateController.class);
	@Autowired
	private TakeheartFixRateService takeheartFixRateService;

	/**
	 * 删除设置
	 * @param id	数据ID
	 * @return	返回操作结果
	 */
	@RequestMapping("delete")
	@ResponseBody
	public Object delete(String id) {
		if (StringUtils.isNotBlank(id) && NumberUtils.isNumber(id) && NumberUtils.toInt(id) > 0) {
			try {
				TakeheartFixRate record = takeheartFixRateService.findByPk(NumberUtils.toInt(id));
				if (record == null) {
					return ResponseUtils.failure(ResponseUtils.ERROR_PARAM, "未找到记录");
				}
				takeheartFixRateService.delete(NumberUtils.toInt(id));
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
	 * @param vo	封装实体类
	 * @param	cId	活动ID
	 * @param page	页数
	 * @param rows	条数
	 * @return	返回列表
	 */
	@RequestMapping("list")
	@ResponseBody
	public Object list(TakeheartFixRate vo, Integer cId, String rows, String page) {
		if (cId != null) {
			vo.setcId(cId);
		}
		return takeheartFixRateService.listGrid(vo, RowBoundsUtils.getRowBounds(page, rows));
	}

	/**
	 * 保存
	 * @param vo	封装类
	 * @return	返回处理结果
	 */
	@RequestMapping("saveAndUpdate")
	@ResponseBody
	public Object saveAndUpdate(TakeheartFixRate vo) {
		try {
			if (vo.getId() == null || vo.getId() <= 0) {
				takeheartFixRateService.save(vo);
				return ResponseUtils.success();
			} else {
				return update(vo);
			}
		} catch (Exception e) {
			log.error(e);
			return ResponseUtils.failure(ResponseUtils.ERROR_SERVER, e.getMessage());
		}
	}

	/**
	 * 更新
	 * @param vo	实体类
	 * @return	返回处理结果
	 */
	@RequestMapping("update")
	@ResponseBody
	public Object update(TakeheartFixRate vo) {
		try {
			if (vo.getId() == null || vo.getId() <= 0) {
				return ResponseUtils.failure(ResponseUtils.ERROR_PARAM, "参数错误");
			}
			TakeheartFixRate pc = takeheartFixRateService.findByPk(vo.getId());
			if (pc == null) {
				return ResponseUtils.failure(ResponseUtils.ERROR_PARAM, "未找到记录");
			}
			takeheartFixRateService.update(vo);
			return ResponseUtils.success();
		} catch (Exception e) {
			log.error(e);
			return ResponseUtils.failure(ResponseUtils.ERROR_SERVER, e.getMessage());
		}
	}
}
