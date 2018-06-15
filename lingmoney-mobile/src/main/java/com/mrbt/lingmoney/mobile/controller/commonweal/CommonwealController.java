package com.mrbt.lingmoney.mobile.controller.commonweal;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.druid.util.StringUtils;
import com.alibaba.fastjson.JSONObject;
import com.mrbt.lingmoney.mobile.controller.BaseController;
import com.mrbt.lingmoney.service.commonweal.CommonwealService;
import com.mrbt.lingmoney.utils.AppCons;
import com.mrbt.lingmoney.utils.PageInfo;
import com.mrbt.lingmoney.utils.ResultParame.ResultInfo;

/**
 * 公益活动相关
 * @author Administrator
 *
 */
@Controller
@RequestMapping("/commonweal")
public class CommonwealController extends BaseController {
	
	private static final Logger LOGGER = LogManager.getLogger(CommonwealController.class);
	
	@Autowired
	private CommonwealService commonwealService;
	
	/**
	 * 打开公益列表页面
	 * @param token
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/openListPage")
	public String openListPage(String token, Model model) {
		LOGGER.info("打开公益列表h5页面 ：" + token);
		model.addAttribute("token", token);
		return "/commonweal/listPage";
	}
	
	/**
	 * 打开公益详情页面
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/openDetailsPage")
	public String openDetailsPage(String token, Integer commonwealId, Model model) {
		LOGGER.info("打开公益详情h5页面");
		if (!StringUtils.isEmpty(token)) {
			model.addAttribute("token", token);
		}
		model.addAttribute("commonwealId", commonwealId);
		commonwealService.queryDetailsHtml(model, 1, commonwealId);
		return "/commonweal/detailsPage";
	}
	
	/**
	 * 用户赞助公益
	 * @param token 用户token
	 * @param loveNumber 赞助数量
	 * @param commonwealId 公益项目ID
	 */
	@RequestMapping(value = "/sponsorProject")
	public @ResponseBody Object sponsorProject(String token, Integer loveNumber, Integer commonwealId) {
		LOGGER.info("用户赞助公益 ：" + token);
		
		JSONObject jsonObject = new JSONObject();
		
		String tokenKey = AppCons.TOKEN_VERIFY + token;
		String uId = getUid(tokenKey);
		try {
			jsonObject = commonwealService.sponsorProject(uId, loveNumber, commonwealId);
		} catch (Exception e) {
			jsonObject.put("code", ResultInfo.SERVER_ERROR.getCode());
			jsonObject.put("msg", ResultInfo.SERVER_ERROR.getMsg());
			e.printStackTrace();
			LOGGER.error("用户打开APP获取爱心值，系统错误。" + "\n" + e.getMessage());
		}
		return jsonObject;
	}
	
	/**
	 * 获取用户爱心数量
	 * @param token 用户token
	 * @param type 1:公益项目， 2：已赞助
	 */
	@RequestMapping(value = "/getUserLoveNum")
	public @ResponseBody Object getUserLoveNum(String token) {
		LOGGER.info("获取公益项目列表 ：" + token);
		
		JSONObject jsonObject = new JSONObject();
		
		String tokenKey = AppCons.TOKEN_VERIFY + token;
		String uId = getUid(tokenKey);
		try {
			if(StringUtils.isEmpty(uId)) {
				jsonObject.put("code", 1009);
				jsonObject.put("msg", "请先登录");
			}else {
				jsonObject = commonwealService.getUserLoveNum(uId);
			}
		} catch (Exception e) {
			jsonObject.put("code", ResultInfo.SERVER_ERROR.getCode());
			jsonObject.put("msg", ResultInfo.SERVER_ERROR.getMsg());
			e.printStackTrace();
			LOGGER.error("用户打开APP获取爱心值，系统错误。" + "\n" + e.getMessage());
		}
		return jsonObject;
	}
	
	
	/**
	 * 获取公益活动列表
	 * @param token 用户token
	 * @param type 1:公益项目， 2：已赞助
	 */
	@RequestMapping(value = "/list")
	public @ResponseBody Object list(String token, Integer type, Integer page, Integer rows) {
		LOGGER.info("获取公益项目列表 ：" + token + "\ttype:" + type);
		
		JSONObject jsonObject = new JSONObject();
		
		String uId = "";
		if(type != 1) {
			String tokenKey = AppCons.TOKEN_VERIFY + token;
			uId = getUid(tokenKey);
		} 
		try {
			jsonObject = commonwealService.queryList(uId, type, page, rows);
		} catch (Exception e) {
			jsonObject.put("code", ResultInfo.SERVER_ERROR.getCode());
			jsonObject.put("msg", ResultInfo.SERVER_ERROR.getMsg());
			e.printStackTrace();
			LOGGER.error("用户打开APP获取爱心值，系统错误。" + "\n" + e.getMessage());
		}
		return jsonObject;
	}
	
	
	
	/**
	 * 用户获取爱心值
	 * 每天打开一次获取一次
	 */
	@RequestMapping(value = "/achieveLove", method = RequestMethod.POST)
	public @ResponseBody Object achieveLove(String token) {
		LOGGER.info("用户打开APP获取爱心值 ：" + token);
		PageInfo pageInfo = new PageInfo();
		String tokenKey = AppCons.TOKEN_VERIFY + token;
		try {
			pageInfo = commonwealService.achieveLove(getUid(tokenKey));
		} catch (Exception e) {
			pageInfo.setCode(ResultInfo.SERVER_ERROR.getCode());
			pageInfo.setMsg(ResultInfo.SERVER_ERROR.getMsg());
			e.printStackTrace();
			LOGGER.error("用户打开APP获取爱心值，系统错误。" + "\n" + e.getMessage());
		}
		return pageInfo;
	}
}
