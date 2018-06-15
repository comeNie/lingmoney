package com.mrbt.lingmoney.admin.controller.product;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mrbt.lingmoney.admin.controller.base.BaseController;
import com.mrbt.lingmoney.admin.service.product.GiftProductService;
import com.mrbt.lingmoney.model.GiftProduct;
import com.mrbt.lingmoney.utils.MyUtils;
import com.mrbt.lingmoney.utils.ResponseUtils;
import com.mrbt.lingmoney.utils.RowBoundsUtils;

/**
 * 产品设置——》活动产品关联礼品
 * 
 * @author lihq
 * @date 2017年5月16日 下午5:55:37
 * @description
 * @version 1.0
 * @since 2017-04-12
 */
@Controller
@RequestMapping("/product/giftProduct")
public class GiftProductController extends BaseController {
	private Logger log = MyUtils.getLogger(GiftProductController.class);
	@Autowired
	private GiftProductService giftProductService;
	
	/**
	 * 删除
	 * @param id	id
	 * @return	return
	 */
	@RequestMapping("delete")
	@ResponseBody
	public Object delete(String id) {
		if (StringUtils.isNotBlank(id) && NumberUtils.isNumber(id) && NumberUtils.toInt(id) > 0) {
			try {
				giftProductService.delete(NumberUtils.toInt(id));
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
	 * @param vo	封装
	 * @param pName 产品名称
	 * @param page	页数
	 * @param rows	条数
	 * @return	return
	 */
	@RequestMapping("list")
	@ResponseBody
	public Object list(GiftProduct vo, String pName, String rows, String page) {
		if (pName != null && !"".equals(pName)) {
			vo.setpName(pName);
		}
		return giftProductService.listGrid(vo, RowBoundsUtils.getRowBounds(page, rows));
	}

	/**
	 * 保存
	 * @param vo	封装
	 * @return	return 
	 */
	@RequestMapping("save")
	@ResponseBody
	public Object save(GiftProduct vo) {
		List<GiftProduct> list = giftProductService.selectByPid(vo);
		if (list != null && list.size() > 0) {
			return ResponseUtils.failure(ResponseUtils.ERROR_SERVER, "该活动产品已关联产品，请在原记录上进行修改！");
		}
		try {
			vo.setCreateTime(new Date());
			giftProductService.save(vo);
			return ResponseUtils.success();
		} catch (Exception e) {
			log.error(e);
			return ResponseUtils.failure(ResponseUtils.ERROR_SERVER, e.getMessage());
		}
	}

	/**
	 * 更新
	 * @param vo	封装
	 * @return	return
	 */
	@RequestMapping("update")
	@ResponseBody
	public Object update(GiftProduct vo) {
		try {
			if (vo.getId() == null || vo.getId() <= 0) {
				return ResponseUtils.failure(ResponseUtils.ERROR_PARAM, "参数错误");
			}
			giftProductService.update(vo);
			return ResponseUtils.success();
		} catch (Exception e) {
			log.error(e);
			return ResponseUtils.failure(ResponseUtils.ERROR_SERVER, e.getMessage());
		}
	}

	
}
