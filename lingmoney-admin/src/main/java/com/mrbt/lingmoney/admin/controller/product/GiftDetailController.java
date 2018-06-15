package com.mrbt.lingmoney.admin.controller.product;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mrbt.lingmoney.admin.controller.base.BaseController;
import com.mrbt.lingmoney.admin.service.product.GiftDetailService;
import com.mrbt.lingmoney.model.GiftDetail;
import com.mrbt.lingmoney.utils.ResponseUtils;
import com.mrbt.lingmoney.utils.RowBoundsUtils;

/**
 * 产品设置———》礼品详情
 * 
 * @author 000608
 *
 */
@Controller
@RequestMapping("/product/giftDetail")
public class GiftDetailController extends BaseController {
	@Autowired
	private GiftDetailService giftDetailService;

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
				int res = giftDetailService.delete(NumberUtils.toInt(id));
				if (res > 0) {
					return ResponseUtils.success();
				} else {
					return ResponseUtils.failure(ResponseUtils.ERROR_SERVER, "删除失败");
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
	 * @param vo	封装
	 * @param gName 名称
	 * @param page	页数
	 * @param rows	条数
	 * @return	return
	 */
	@RequestMapping("list")
	@ResponseBody
	public Object list(GiftDetail vo, String gName, String rows, String page) {
		if (gName != null && !"".equals(gName)) {
			vo.setgName(gName);
		}
		return giftDetailService.listGrid(vo, RowBoundsUtils.getRowBounds(page, rows));
	}

	/**
	 * 保存
	 * @param vo	封装
	 * @return	return
	 */
	@RequestMapping("save")
	@ResponseBody
	public Object save(GiftDetail vo) {
		try {
			giftDetailService.save(vo);
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
	public Object update(GiftDetail vo) {
		try {
			if (vo.getId() == null || vo.getId() <= 0) {
				return ResponseUtils.failure(ResponseUtils.ERROR_PARAM, "参数错误");
			}
			int res = giftDetailService.update(vo);
			if (res > 0) {
				return ResponseUtils.success();
			} else {
				return ResponseUtils.failure(ResponseUtils.ERROR_PARAM, "更新失败");
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			return ResponseUtils.failure(ResponseUtils.ERROR_SERVER, ex.getMessage());
		}
	}

	/**
	 * 更新状态
	 * @param vo	封装
	 * @param str	str
	 * @return	return
	 */
	@RequestMapping("updateStatus")
	@ResponseBody
	public Object updateStatus(GiftDetail vo, String str) {
		try {
			String[] gid = str.split(",");
			if (gid.length >= 1) {
				giftDetailService.updateStatus(gid, vo);
				return ResponseUtils.success();
			} else {
				return ResponseUtils.failure(ResponseUtils.ERROR_PARAM, "参数错误");
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			return ResponseUtils.failure(ResponseUtils.ERROR_SERVER, ex.getMessage());
		}
	}

}
