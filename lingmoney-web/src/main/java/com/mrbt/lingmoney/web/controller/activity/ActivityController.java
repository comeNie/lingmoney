package com.mrbt.lingmoney.web.controller.activity;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mrbt.lingmoney.model.ActivityProductCustomer;
import com.mrbt.lingmoney.model.ProductCustomer;
import com.mrbt.lingmoney.service.product.ActivityService;
import com.mrbt.lingmoney.service.users.UsersService;
import com.mrbt.lingmoney.utils.PageInfo;
import com.mrbt.lingmoney.utils.ResultParame;
import com.mrbt.lingmoney.web.controller.common.CommonMethodUtil;

import net.sf.json.JSONObject;

/**
 * 产品活动
 * 
 * @author 000608
 *
 */

@Controller
@RequestMapping(value = "/activity")
public class ActivityController {
	
	private static final Logger LOG = LogManager.getLogger(ActivityController.class);
	
	@Autowired
	private ActivityService activityService;
	
	@Autowired
	private UsersService usersService;
	
	/**
	 * 活动页面H5开发代码测试类
	 * 
	 * @param activityKey
	 *            活动关键字
	 * @param token
	 *            传入token
	 * @return 返回活动页面H5开发代码测试类
	 */
	@RequestMapping("/activitytest")
	private String activityTest(String activityKey, String token) {
		return "test/" + activityKey;
	}
	
	/**
	 * 活动页面
	 * 
	 * @param activityKey
	 *            活动关键字
	 * @param request
	 *            request域
	 * @param model
	 *            数据模型类
	 * @return 返回活动页面
	 */
	@RequestMapping("/activityShow")
	public String activityShow(String activityKey, HttpServletRequest request, ModelMap model) {

		if (StringUtils.isBlank(activityKey)) {
			LOG.info("通过banner进入活动页面失败，未输入活动关键字");
			return String.valueOf(ResultParame.ResultInfo.NOT_FOUND.getCode());
		}

		try {
			boolean result = activityService.activityShow(activityKey, 1, null, 0, model);

			if (result) {
				CommonMethodUtil.modelUserBaseInfo(model, request, usersService);
				return "activity/activityProduct";

			} else {
				return String.valueOf(ResultParame.ResultInfo.NOT_FOUND.getCode());
			}

		} catch (Exception e) {
			e.printStackTrace();
			LOG.error("通过banner进入活动页面，服务器出错:" + e.getMessage());
			return String.valueOf(ResultParame.ResultInfo.SERVER_ERROR.getCode());
		}

	}

	/**
	 * 返回活动时间
	 * 
	 * @param activityKey
	 *            活动关键字
	 * @param request
	 *            request域
	 * @return 返回活动时间
	 */
	@RequestMapping("/activityTime")
	@ResponseBody
	public Object activityTime(String activityKey, HttpServletRequest request) {
		JSONObject jsonObject = new JSONObject();
		// 查询条件
		Map<String, Object> condition = new HashMap<String, Object>();
		if (StringUtils.isNotBlank(activityKey)) {
			condition.put("actUrl", activityKey); // 活动关键字
		}
		condition.put("status", ResultParame.ResultNumber.ONE.getNumber()); // 状态可用
		condition.put("timeLimit", ResultParame.ResultNumber.ZERO.getNumber()); // 0无时间限制
																				// 1有时间限制

		PageInfo pageInfo = new PageInfo();
		pageInfo.setCondition(condition);
		ActivityProductCustomer activityProduct = activityService.selectActivityInfo(pageInfo);

		if (activityProduct == null) {
			jsonObject.put("code", String.valueOf(ResultParame.ResultInfo.NOT_FOUND.getCode()));
			jsonObject.put("msg", "未找到该活动");

		} else {
			List<ProductCustomer> list = activityProduct.getProductList();

			if (list != null && list.size() >= ResultParame.ResultNumber.ONE.getNumber()) {
				jsonObject.put("productCodeA", list.get(0).getCode());
			}

			if (list != null && list.size() >= ResultParame.ResultNumber.TWO.getNumber()) {
				jsonObject.put("productCodeB", list.get(1).getCode());
			}

			// 开始时间
			Date startDate = activityProduct.getStartDate();
			// 结束时间
			Date endDate = activityProduct.getEndDate();
			// 当前时间
			Date currentDate = new Date();
			// buyState代表活动状态，0表示活动中，1表示还未开始，2表示已结束
			int buyState = activityProduct.getBuyState();
			// 如果活动未开始，开始时间大于当前时间
			if (buyState == ResultParame.ResultNumber.ONE.getNumber()) {
				// 距离活动开始时间
				long distanceStartTime = startDate.getTime() - currentDate.getTime();
				jsonObject.put("distanceStartTime", distanceStartTime);
			} else {
				jsonObject.put("distanceStartTime", 0);
			}
			// 如果活动开始了，还未结束，结束时间大于当前时间，开始时间小于当前时间
			if (buyState == ResultParame.ResultNumber.ZERO.getNumber()) {
				// 距离活动结束时间
				long distanceEndTime = endDate.getTime() - currentDate.getTime();
				jsonObject.put("distanceEndTime", distanceEndTime);
			} else {
				jsonObject.put("distanceEndTime", ResultParame.ResultNumber.ZERO.getNumber());
			}

			jsonObject.put("startDate", startDate);
			jsonObject.put("endDate", endDate);
			jsonObject.put("currentDate", currentDate);
			jsonObject.put("buyState", buyState);
			jsonObject.put("code", ResultParame.ResultInfo.SUCCESS.getCode());
			jsonObject.put("msg", "查询成功");
		}

		return jsonObject;
	}
}
