package com.mrbt.lingmoney.admin.controller.product;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.mrbt.lingmoney.admin.controller.base.BaseController;
import com.mrbt.lingmoney.admin.service.product.ActivityPropertyService;
import com.mrbt.lingmoney.model.ActivityProperty;
import com.mrbt.lingmoney.model.ActivityPropertyWithBLOBs;
import com.mrbt.lingmoney.utils.GridPage;
import com.mrbt.lingmoney.utils.ResponseUtils;
import com.mrbt.lingmoney.utils.RowBoundsUtils;

/**
 * 产品设置——》活动属性
 * 
 * @author Administrator
 *
 */

@Controller
@RequestMapping("product/activityProperty")
public class ActivityPropertyController extends BaseController {

	@Autowired
	private ActivityPropertyService activityPropertyService;

	/**
	 * 查询
	 * @param page	页数
	 * @param rows	条数
	 * @return	return
	 */
	@RequestMapping("list")
	@ResponseBody
	public Object list(String page, String rows) {
		GridPage<ActivityProperty> gridPage = activityPropertyService.listGrid(RowBoundsUtils.getRowBounds(page, rows));
		return gridPage;

	}

	/**
	 * 
	 * @param id	数据ID
	 * @return	return
	 */
	@RequestMapping("listContent")
	@ResponseBody
	public Object listContent(String id) {
		ActivityProperty activityProperty = null;
		if (StringUtils.isNotBlank(id) && NumberUtils.isNumber(id)) {
			activityProperty = activityPropertyService.findByPk(Integer.parseInt(id));
		}
		return activityProperty;
	}

	/**
	 * 保存
	 * @param request	request
	 * @param vo	vo
	 * @return		return
	 * @throws Exception	Exception
	 */
	@RequestMapping("saveAndUpdate")
	@ResponseBody
	public Object saveAndUpdate(MultipartHttpServletRequest request, ActivityPropertyWithBLOBs vo) throws Exception {
		try {
			
			MultipartFile file1 = request.getFile("path1");
			MultipartFile file2 = request.getFile("path2");

			int res = activityPropertyService.saveAndUpdate(file1, file2, vo);
			if (res > 0) {
				return ResponseUtils.success();
			} else {
				return ResponseUtils.failure(ResponseUtils.ERROR_PARAM, "参数错误,未找到记录");
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			return ResponseUtils.failure(ResponseUtils.ERROR_SERVER, ex.getMessage());
		}
	}
}
