package com.mrbt.lingmoney.admin.controller.product;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mrbt.lingmoney.admin.controller.base.BaseController;
import com.mrbt.lingmoney.admin.service.product.GiftService;
import com.mrbt.lingmoney.model.Gift;
import com.mrbt.lingmoney.utils.ResponseUtils;
import com.mrbt.lingmoney.utils.RowBoundsUtils;

/**
 * 产品设置——》礼品
 * 
 * @author 000608
 *
 */
@Controller
@RequestMapping("/product/gift")
public class GiftController extends BaseController {
	@Autowired
	private GiftService giftService;

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
				int res = giftService.delete(NumberUtils.toInt(id));
				if (res > 0) {
					return ResponseUtils.success();
				} else {
					return ResponseUtils.failure(ResponseUtils.ERROR_PARAM, "参数错误");
				}
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
	 * @param gName	名称
	 * @param page	页数
	 * @param rows	条数
	 * @return	return
	 */
	@RequestMapping("list")
	@ResponseBody
	public Object list(Gift vo, String gName, String rows, String page) {
		if (gName != null && !"".equals(gName)) {
			vo.setgName(gName);
		}
		return giftService.listGrid(vo, RowBoundsUtils.getRowBounds(page, rows));
	}

	/**
	 * 保存
	 * @param vo	封装
	 * @return	return
	 */
	@RequestMapping("save")
	@ResponseBody
	public Object save(Gift vo) {
		try {
			giftService.save(vo);
			return ResponseUtils.success();
		} catch (Exception ex) {
			ex.printStackTrace();
			return ResponseUtils.failure(ResponseUtils.ERROR_SERVER, ex.getMessage());
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
	public Object update(Gift vo) {
		try {
			if (vo.getId() == null || vo.getId() <= 0) {
				return ResponseUtils.failure(ResponseUtils.ERROR_PARAM, "参数错误");
			}
			
			int res = giftService.update(vo);
			if (res > 0) {
				return ResponseUtils.success();
			} else {
				return ResponseUtils.failure(ResponseUtils.ERROR_PARAM, "更新产品推荐错误");
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			return ResponseUtils.failure(ResponseUtils.ERROR_SERVER, ex.getMessage());
		}
	}
	
	
	/**
	 * 列表
	 * @return	return
	 */
	@RequestMapping("listGift")
	@ResponseBody
	public Object listGift() {
		Gift gift = new Gift();
		gift.setType(1);
		return giftService.selectByGiftProduct(gift);
	}
}
