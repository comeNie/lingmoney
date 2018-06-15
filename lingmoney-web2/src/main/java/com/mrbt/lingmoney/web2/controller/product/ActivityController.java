package com.mrbt.lingmoney.web2.controller.product;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mrbt.lingmoney.commons.cache.RedisGet;
import com.mrbt.lingmoney.model.ActivityProductCustomer;
import com.mrbt.lingmoney.service.product.ActivityService;
import com.mrbt.lingmoney.utils.PageInfo;
import com.mrbt.lingmoney.utils.PropertiesUtil;
import com.mrbt.lingmoney.utils.ResultParame.ResultInfo;
import com.mrbt.lingmoney.utils.ResultParame.ResultNumber;
import com.mrbt.lingmoney.web2.controller.BaseController;
import com.mrbt.lingmoney.web2.controller.common.CommonMethodUtil;
import com.mrbt.lingmoney.web2.vo.product.PopularActivityVo;

/**
 * 活动
 * 
 * @author lihq
 * @date 2017年4月24日 上午9:44:48
 * @description
 * @version 1.0
 * @since 2017-04-12
 */
@Controller
@RequestMapping(value = "/activity")
public class ActivityController extends BaseController {

	private static final Logger LOG = LogManager.getLogger(ActivityController.class);

	@Autowired
	private ActivityService activityService;

	@Autowired
	private RedisGet redisGet;
	
	private static final String ACT_URL_HERAD = PropertiesUtil.getPropertiesByKey("ACT_URL_HERAD");

	/**
	 * 热门活动
	 * 
	 * @description
	 * @param pageNo pageNo
	 * @param pageSize pageSize
	 * @return pageInfo
	 */
	@RequestMapping(value = "/activityList", method = RequestMethod.POST)
	public @ResponseBody Object activityList(Integer pageNo, Integer pageSize) {
		LOG.info("活动列表 activity/activityList");
		PageInfo pageInfo = null;
		try {
			pageInfo = new PageInfo(pageNo, pageSize);
			// 查询条件
			Map<String, Object> condition = new HashMap<String, Object>();
			condition.put("status", ResultNumber.ONE.getNumber()); // 状态可用
			condition.put("actCenter", ResultNumber.ONE.getNumber()); // 活动中心显示
			condition.put("actType", ResultNumber.ONE.getNumber()); // 活动产品
			condition.put("priority", ResultNumber.ONE.getNumber()); // 普通活动
			pageInfo.setCondition(condition);
			activityService.selectActivityList(pageInfo);
			
			List<PopularActivityVo> resPav = new ArrayList<PopularActivityVo>();
			if(pageInfo.getRows() != null && pageInfo.getRows().size() > 0) {
				List<ActivityProductCustomer> resList = pageInfo.getRows();
				for (ActivityProductCustomer apc : resList) {
					PopularActivityVo pav = new PopularActivityVo();
					
					pav.setActRecommendations(apc.getActRecommendations());
					pav.setActTitle(apc.getActTitle());
					pav.setActTitleImage(apc.getActTitleImage());
					pav.setActUrl(ACT_URL_HERAD + apc.getActUrl());
					pav.setBuyState(apc.getBuyState());
					pav.setCurrentDate(apc.getCurrentDate());
					pav.setDistanceEndTime(apc.getDistanceEndTime());
					pav.setDistanceStartTime(apc.getDistanceStartTime());
					pav.setId(apc.getId());
					pav.setNewFlag(apc.getNewFlag());
					pav.setTimeInterval(apc.getTimeInterval());
					resPav.add(pav);
				}
				pageInfo.setRows(resPav);
			}

		} catch (Exception e) {
			LOG.error("活动列表查询失败" + e.getMessage());
			e.printStackTrace();
			pageInfo = new PageInfo();
			pageInfo.setCode(ResultInfo.SERVER_ERROR.getCode());
			pageInfo.setMsg(ResultInfo.SERVER_ERROR.getMsg());
		}
		return pageInfo;
	}

	/**
	 * 活动内页
	 * 
	 * @description 两个参数传一个即可
	 * @param activityId
	 *            活动id
	 * @param activityUrl
	 *            活动关键字
	 * @return pageInfo
	 */
	@RequestMapping(value = "/activityInfo", method = RequestMethod.POST)
	public @ResponseBody Object activityInfo(Integer activityId, String activityUrl) {
		LOG.info("活动内页 activity/activityInfo");
		PageInfo pageInfo = null;
		try {
			pageInfo = new PageInfo();
			if (activityId == null && StringUtils.isBlank(activityUrl)) {
				pageInfo.setCode(ResultInfo.PARAMETER_ERROR.getCode());
				pageInfo.setMsg(ResultInfo.PARAMETER_ERROR.getMsg());
				return pageInfo;
			}
			// 查询条件
			Map<String, Object> condition = new HashMap<String, Object>();
			if (activityId != null) {
				condition.put("id", activityId); // 活动id
			}
			if (StringUtils.isNotBlank(activityUrl)) {
				condition.put("actUrl", activityUrl); // 活动关键字
			}
			condition.put("status", 1); // 状态可用
			// condition.put("timeLimit", 0);// 0无时间限制 1有时间限制
			pageInfo.setCondition(condition);
			ActivityProductCustomer activityProductCustomer = activityService.selectActivityInfo(pageInfo);
			if (activityProductCustomer == null) {
				pageInfo.setCode(ResultInfo.NO_DATA.getCode());
				pageInfo.setMsg(ResultInfo.NO_DATA.getMsg());
				return pageInfo;
			}
			pageInfo.setCode(ResultInfo.SUCCESS.getCode());
			pageInfo.setMsg(ResultInfo.SUCCESS.getMsg());
			pageInfo.setObj(activityProductCustomer);
		} catch (Exception e) {
			LOG.error("活动内页查询失败" + e.getMessage());
			e.printStackTrace();
			pageInfo = new PageInfo();
			pageInfo.setCode(ResultInfo.SERVER_ERROR.getCode());
			pageInfo.setMsg(ResultInfo.SERVER_ERROR.getMsg());
		}
		return pageInfo;
	}

	/**
	 * 活动产品说明
	 * 
	 * @description
	 * @param response
	 *            response
	 * @param activityId
	 *            活动id
	 * @param productIndex
	 *            产品下标 从1开始
	 * @param modelMap
	 *            modelMap
	 * @return string
	 */
	@RequestMapping(value = "/activityDesc", method = RequestMethod.POST)
    @ResponseBody
	public Object activityDesc(Integer activityId, Integer productIndex, HttpServletResponse response,
			ModelMap modelMap) {
		LOG.info("活动产品说明 activity/activityDesc");
		response.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=utf-8");
        PageInfo pi = new PageInfo();
		try {
			String activityDesc = activityService.selectActivityDesc(activityId, productIndex);
            if (StringUtils.isBlank(activityDesc)) {
                pi.setResultInfo(ResultInfo.NO_DATA);
            } else {
                pi.setResultInfo(ResultInfo.SUCCESS);
                pi.setObj(activityDesc);
            }
		} catch (Exception e) {
			LOG.error("活动产品说明查询失败" + e.getMessage());
			e.printStackTrace();
            pi.setResultInfo(ResultInfo.SERVER_ERROR);
		}
        return pi;
	}

	/**
	 * 拉新活动统计
	 * 
	 * @description
	 * @param judgeStatus
	 *            0判断活动状态 1不判断
	 * @param  request
	 * 		      request
	 * @param  response
	 *            response
	 * @return pageInfo
	 */
	@RequestMapping(value = "/activityRecommend", method = RequestMethod.POST)
	public @ResponseBody Object activityRecommend(HttpServletRequest request, Integer judgeStatus, HttpServletResponse response) {
		LOG.info("拉新活动统计 activity/activityRecommend");
		String uId = CommonMethodUtil.getUidBySession(request);
		PageInfo pageInfo = null;
		try {
			pageInfo = new PageInfo();
			if (judgeStatus == null) {
				pageInfo.setCode(ResultInfo.PARAMETER_ERROR.getCode());
				pageInfo.setMsg(ResultInfo.PARAMETER_ERROR.getMsg());
				return pageInfo;
			}
			Map<String, Object> activityRecommend = activityService
					.activityRecommend(uId, judgeStatus);
			if (activityRecommend == null || activityRecommend.size() < 1) {
				pageInfo.setCode(ResultInfo.NO_DATA.getCode());
				pageInfo.setMsg(ResultInfo.NO_DATA.getMsg());
				return pageInfo;
			}
			pageInfo.setCode(ResultInfo.SUCCESS.getCode());
			pageInfo.setMsg(ResultInfo.SUCCESS.getMsg());
			pageInfo.setObj(activityRecommend);
		} catch (Exception e) {
			LOG.error("拉新活动统计查询失败" + e.getMessage());
			e.printStackTrace();
			pageInfo = new PageInfo();
			pageInfo.setCode(ResultInfo.SERVER_ERROR.getCode());
			pageInfo.setMsg(ResultInfo.SERVER_ERROR.getMsg());
		}
		return pageInfo;

	}

	/**
	 * 通过banner跳转活动页面（h5）
	 * @param response
	 *            response
	 * @param activityKey
	 *            活动关键字
	 * @param needLogin
	 *            是否需要登录 Y是 N否
	 * @param request
	 *            request
	 * @param modelMap
	 *            modelMap
	 * @return string
	 */
	@RequestMapping("/activityShow")
    @ResponseBody
    public Object activityShow(String activityKey, String needLogin, HttpServletRequest request,
            HttpServletResponse response, ModelMap modelMap) {
		LOG.info("活动页面 activity/activityShow");
		response.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=utf-8");
		String uId = null;
		Integer isLogin = 1;
        PageInfo pi = new PageInfo();
		try {
			if (StringUtils.isNotBlank(needLogin) && "Y".equals(needLogin)) { // 登录
			    uId = CommonMethodUtil.getUidBySession(request);
				isLogin = 0;
			} else {
				isLogin = 1;
			}
			activityService.activityShow(activityKey, isLogin, uId, 1, modelMap);
            Map<String, Object> resultMap = new HashMap<String, Object>();
            if (modelMap.containsKey("projectInfo")) {
                resultMap.put("projectInfo", modelMap.get("projectInfo"));
            }
            if (modelMap.containsKey("referralCode")) {
                resultMap.put("referralCode", modelMap.get("referralCode"));
            }
            if (modelMap.containsKey("isLogin")) {
                resultMap.put("isLogin", modelMap.get("isLogin"));
            }
            pi.setResultInfo(ResultInfo.SUCCESS);
            pi.setObj(resultMap);
		} catch (Exception e) {
			LOG.error("通过banner跳转活动页面失败" + e.getMessage());
			e.printStackTrace();
            pi.setResultInfo(ResultInfo.SERVER_ERROR);
		}
        return pi;
	}

	/**
	 * 活动横幅提示框
	 * 
	 * @param needLogin
	 *            是否登录：Y是，N否
	 * @param priority
	 *            优先级 1，新手特供活动（首页）；2，新手特供活动（理财列表页）；3，新手特供活动（购买页）
	 * @param type
	 *            客户端类型 0，ios 1，安卓
	 * @return pageInfo
	 */
	@RequestMapping(value = "/activityNovice", method = RequestMethod.POST)
	public @ResponseBody Object activityNovice(HttpServletRequest request, String needLogin, Integer priority, Integer type) {
		String uId = CommonMethodUtil.getUidBySession(request);
		LOG.info("查询新手特供活动 activity/activityNovice" + "，uId：" + uId + "，needLogin：" + needLogin + "，priority："
				+ priority);
		PageInfo pageInfo = new PageInfo();
		try {
			if (StringUtils.isBlank(needLogin) || (!"N".equals(needLogin) && !"Y".equals(needLogin) || priority == null
					|| !Arrays.asList(ResultNumber.ONE.getNumber(), ResultNumber.TWO.getNumber(), ResultNumber.THREE.getNumber()).contains(priority) || type == null || (type != 0 && type != 1))) {
				pageInfo.setCode(ResultInfo.PARAMETER_ERROR.getCode());
				pageInfo.setMsg(ResultInfo.PARAMETER_ERROR.getMsg());
				return pageInfo;
			}
			if ("Y".equals(needLogin)) {
				if (StringUtils.isBlank(uId) || uId == null) {
					pageInfo.setCode(ResultInfo.LOGIN_FAILURE.getCode());
					pageInfo.setMsg(ResultInfo.LOGIN_FAILURE.getMsg());
					return pageInfo;
				}
			}
			pageInfo = activityService.selectActivityNovice(uId, needLogin, priority,
					type);
		} catch (Exception e) {
			LOG.error("查询新手特供活动失败" + e.getMessage());
			e.printStackTrace();
			pageInfo = new PageInfo();
			pageInfo.setCode(ResultInfo.SERVER_ERROR.getCode());
			pageInfo.setMsg(ResultInfo.SERVER_ERROR.getMsg());
		}
		return pageInfo;
	}
}
