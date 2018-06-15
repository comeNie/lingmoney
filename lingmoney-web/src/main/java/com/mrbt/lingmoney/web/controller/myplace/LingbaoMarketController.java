package com.mrbt.lingmoney.web.controller.myplace;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mrbt.lingmoney.model.LingbaoActivityBanner;
import com.mrbt.lingmoney.model.LingbaoGift;
import com.mrbt.lingmoney.model.LingbaoGiftType;
import com.mrbt.lingmoney.service.discover.LingbaoLotteryService;
import com.mrbt.lingmoney.service.discover.MyDiscoverService;
import com.mrbt.lingmoney.service.discover.MyPlaceService;
import com.mrbt.lingmoney.service.discover.MyPlaceWebService;
import com.mrbt.lingmoney.service.users.UsersService;
import com.mrbt.lingmoney.utils.PageInfo;
import com.mrbt.lingmoney.utils.ResultParame;
import com.mrbt.lingmoney.web.controller.common.CommonMethodUtil;

import net.sf.json.JSONObject;

/**
 * @author yiban sun
 * @date 2016年12月21日 上午10:34:01
 * @version 1.0
 * @description 领宝商城
 * @since
 **/
@Controller
@RequestMapping("/lbmarket")
public class LingbaoMarketController {
	private static final Logger LOG = LogManager.getLogger(LingbaoMarketController.class);
	@Autowired
	private MyPlaceWebService myPlaceWebService;
	@Autowired
	private MyDiscoverService myDiscoverService;
	@Autowired
	private LingbaoLotteryService lingbaoLotteryService;
	@Autowired
	private UsersService usersService;
	@Autowired
	private MyPlaceService myPlaceService;

	/**
	 * 首页
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("/index")
	public String index(ModelMap model, HttpServletRequest request) {
		LOG.info("进入领宝商城首页");
		try {
			String uid = CommonMethodUtil.getUidBySession(request);
			if (!StringUtils.isEmpty(uid)) {
				// 用户基本信息
				PageInfo userInfo = myDiscoverService.getUserInfo(uid);
				if (userInfo.getCode() == ResultParame.ResultInfo.SUCCESS.getCode()) {
					Map<String, Object> info = (Map<String, Object>) userInfo.getObj();
					model.addAttribute("userInfo", info);
				} else {
					model.addAttribute("userInfo", null);
				}

			} else {
				model.addAttribute("userInfo", null);
			}

			myPlaceWebService.packageIndexModelMap(model, uid);

			// 查询热门兑换
			PageInfo lingbaoGiftInfo = myPlaceService.getGiftListWithCondition(null, 1, 6, null);
			if (lingbaoGiftInfo.getCode() == ResultParame.ResultInfo.SUCCESS.getCode()) {
				List<LingbaoGift> recomList = lingbaoGiftInfo.getRows();
				model.addAttribute("recomList", recomList);
			}

			// 首页banner
			PageInfo bannerInfo = myPlaceService.getBannerList(null);
			if (bannerInfo.getCode() == ResultParame.ResultInfo.SUCCESS.getCode()) {
				List<LingbaoActivityBanner> bannerList = bannerInfo.getRows();
				model.addAttribute("bannerList", bannerList);
			}

			CommonMethodUtil.modelUserBaseInfo(model, request, usersService);

		} catch (Exception e) {
			LOG.info("领宝商城首页数据装载错误" + e.getMessage());
			e.printStackTrace();
			return String.valueOf(ResultParame.ResultInfo.SERVER_ERROR.getCode());
		}

		return "lingbaoMarket/index";
	}

	/**
	 * 签到
	 */
	@RequestMapping("/checkIn")
	public @ResponseBody Object checkIn(HttpServletRequest request) {
		PageInfo pageInfo = new PageInfo();
		try {
			String uid = CommonMethodUtil.getUidBySession(request);
			pageInfo = myDiscoverService.signIn(uid);

		} catch (Exception e) {
			CommonMethodUtil.handelCatchedException(pageInfo, e, LOG, "签到失败--系统错误");
		}
		return pageInfo;
	}

	/**
	 * 进入抽奖页面
	 *
	 * @Description
	 * @param model
	 * @param typeID
	 *            对应活动类型ID
	 * @return
	 */
	@RequestMapping("/gotoLuckDraw")
	public String gotoLuckDraw(ModelMap model, Integer typeID, HttpServletRequest request) {
		try {
			// 查询中奖记录
			List<Map<String, Object>> lotteryList = lingbaoLotteryService.queryLotteryInfo();
			model.addAttribute("lotteryList", lotteryList);
			model.addAttribute("typeID", typeID);

			// 如果用户登录了，查询中奖几率
			String uid = CommonMethodUtil.getUidBySession(request);
			if (!StringUtils.isEmpty(uid)) {
				int ratio = lingbaoLotteryService.queryLotteryRatio(uid);
				model.addAttribute("ratio", ratio);
			} else {
				model.addAttribute("ratio", 50);
			}

			// 保存活动类型ID，用于抽奖计算时获取活动列表
			request.getSession().setAttribute("LOTTERYTYPE", typeID);
			CommonMethodUtil.modelUserBaseInfo(model, request, usersService);

		} catch (Exception e) {
			LOG.error("进入抽奖页失败，系统错误" + e.getMessage());
			e.printStackTrace();
			return String.valueOf(ResultParame.ResultInfo.SERVER_ERROR.getCode());
		}

		return "lingbaoMarket/luckyDraw";
	}

	/**
	 * (验证)抽奖
	 *
	 * @Description
	 * @param request
	 * @return
	 */
	@RequestMapping("/validateChoujiang")
	@ResponseBody
	public Object validateChoujiang(HttpServletRequest request, HttpServletResponse response) {
		response.setHeader("Pragma", "No-cache");
		response.setHeader("Cache-Control", "No-cache");
		response.setDateHeader("Expires", -1);
		response.setHeader("Cache-Control", "No-store");

		LOG.info("验证抽奖");
		int typeID = (Integer) request.getSession().getAttribute("LOTTERYTYPE");
		String uid = CommonMethodUtil.getUidBySession(request);
		JSONObject json = new JSONObject();
		try {
			json = lingbaoLotteryService.validateChoujiang(uid, typeID);

		} catch (Exception e) {
			json.put("code", ResultParame.ResultInfo.SERVER_ERROR.getCode());
			json.put("msg", "系统错误");
			e.printStackTrace();
			LOG.error("抽奖失败，系统错误。 \n" + e.getMessage());
		}

		return json;
	}

	/**
	 * 查询抽奖活动信息
	 *
	 * @Description
	 * @return
	 */
	@RequestMapping("/queryChoujiangItem")
	@ResponseBody
	public Object queryChoujiangItem(Integer typeID) {
		LOG.info("查询抽奖信息");
		JSONObject json = lingbaoLotteryService.queryChoujiangItem(typeID);
		return json;
	}

	/**
	 * 查看更多
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("/queryMore")
	public String queryMore(ModelMap model, HttpServletRequest request, Integer typeId) {
		String uid = CommonMethodUtil.getUidBySession(request);
		if (StringUtils.isEmpty(typeId)) {
			LOG.error("进入查看更多页面失败，参数错误。typeId为空");
			return String.valueOf(ResultParame.ResultInfo.SERVER_ERROR.getCode());
		}

		try {
			if (!StringUtils.isEmpty(uid)) {
				// 用户基本信息
				PageInfo userInfo = myDiscoverService.getUserInfo(uid);
				if (userInfo.getCode() == ResultParame.ResultInfo.SUCCESS.getCode()) {
					Map<String, Object> info = (Map<String, Object>) userInfo.getObj();
					model.addAttribute("userInfo", info);
				} else {
					model.addAttribute("userInfo", null);
				}

			} else {
				model.addAttribute("userInfo", null);
			}

			// 根据条件查询礼品 默认查询第一页，20条记录
			PageInfo lingbaoGiftInfo = myPlaceService.getGiftListWithCondition(null, typeId, 20, null);
			if (lingbaoGiftInfo.getCode() == ResultParame.ResultInfo.SUCCESS.getCode()) {
				List<LingbaoGift> giftList = lingbaoGiftInfo.getRows();
				int total = giftList.size();
				model.addAttribute("total", total);
				// 计算分页页数
				int pages = total / 20 + 1;
				model.addAttribute("pages", pages);
				model.addAttribute("giftList", giftList);
				// 默认当前第一页
				model.addAttribute("currentPage", 1);
			}

			// 查询所有目录
			PageInfo giftTypeInfo = myPlaceService.getGiftTypeList(null);
			if (giftTypeInfo.getCode() == ResultParame.ResultInfo.SUCCESS.getCode()) {
				List<LingbaoGiftType> typelist = giftTypeInfo.getRows();
				model.addAttribute("typeList", typelist);
			}

			// 首页banner
			PageInfo bannerInfo = myPlaceService.getBannerList(null);
			if (bannerInfo.getCode() == ResultParame.ResultInfo.SUCCESS.getCode()) {
				List<LingbaoActivityBanner> bannerList = bannerInfo.getRows();
				model.addAttribute("bannerList", bannerList);
			}

			// 礼品类型ID，用于页面标记选中类型
			model.addAttribute("checkedType", typeId);
			CommonMethodUtil.modelUserBaseInfo(model, request, usersService);

		} catch (Exception e) {
			LOG.error("进入查看更多页面失败，系统错误。\n" + e.getMessage());
			e.printStackTrace();
			return String.valueOf(ResultParame.ResultInfo.SERVER_ERROR.getCode());
		}

		return "lingbaoMarket/indexMore";
	}

	/**
	 * 根据条件查询礼品
	 */
	@RequestMapping("/queryGiftWithCondition")
	@ResponseBody
	public Object queryGiftWithCondition(HttpServletResponse response, Integer typeId, Integer page, Integer rows,
			String range) {
		LOG.info("根据条件查询礼品" + typeId);
		//根据前端页面布局，每页中的奖品数量默认16条
		if(rows == null) {
			rows = 16;
		}
		return myPlaceWebService.queryGiftWithCondition(typeId, range, page, rows);
	}
}
