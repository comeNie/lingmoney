package com.mrbt.lingmoney.mobile.controller.discover;

import java.io.PrintWriter;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mrbt.lingmoney.mobile.controller.BaseController;
import com.mrbt.lingmoney.service.discover.LingbaoLotteryService;
import com.mrbt.lingmoney.service.discover.MyPlaceService;
import com.mrbt.lingmoney.service.web.MyAccountService;
import com.mrbt.lingmoney.utils.AppCons;
import com.mrbt.lingmoney.utils.PageInfo;
import com.mrbt.lingmoney.utils.ResultParame.ResultInfo;
import com.mrbt.lingmoney.utils.ResultParame.ResultNumber;

import net.sf.json.JSONObject;

/**
 * @author syb
 * @date 2017年5月4日 下午3:14:30
 * @version 1.0
 * @description 《我的领地》
 **/
@Controller
@RequestMapping("/myPlace")
public class MyPlaceController extends BaseController {

	private static final Logger LOG = LogManager.getLogger(MyPlaceController.class);

	@Autowired
	private MyPlaceService myPlaceService;
	@Autowired
	private LingbaoLotteryService lingbaoLotteryService;
	@Autowired
	private MyAccountService myAccountService;
	

	/**
	 * 获取banner列表
	 * @param type type
	 * @return pageInfo
	 */
	@RequestMapping(value = "/getBannerList", method = RequestMethod.POST)
	public @ResponseBody Object getBannerList(Integer type) {
		LOG.info("获取我的领地banner列表");
		PageInfo pageInfo = new PageInfo();
		try {
			pageInfo = myPlaceService.getBannerList(type);
		} catch (Exception e) {
			pageInfo.setCode(ResultInfo.SERVER_ERROR.getCode());
			pageInfo.setMsg("获取我的领地首页信息失败");
			e.printStackTrace();
			LOG.error("获取我的领地banner列表失败，系统错误。" + e.getMessage());
		}
		return pageInfo;
	}

	/**
	 * 礼品兑换
	 * 
	 * @param token
	 *            用户token
	 * @param giftId
	 *            礼品id
	 * @param num
	 *            兑换数量
	 * @param addressID
	 *            地址
	 * @return pageInfo
	 */
	@RequestMapping(value = "/exchangeGift", method = RequestMethod.POST)
	public @ResponseBody Object exchangeGift(String token, Integer giftId, Integer num, Integer addressID) {
		LOG.info("--我的领地 礼品兑换-- \n" + token + "\t" + giftId + "\t" + num + "\t" + addressID);
		PageInfo pageInfo = new PageInfo();
		try {
			String tokenKey = AppCons.TOKEN_VERIFY + token;
			pageInfo = myPlaceService.exchangeGift(getUid(tokenKey), giftId, num, addressID);
		} catch (Exception e) {
			pageInfo.setCode(ResultInfo.SERVER_ERROR.getCode());
			pageInfo.setMsg(ResultInfo.SERVER_ERROR.getMsg());
			e.printStackTrace();
			LOG.error("进入我的领地礼品兑换页失败，系统错误。token:" + token + "; giftId:" + giftId + "\n" + e.getMessage());
		}
		return pageInfo;
	}

	/**
	 * 查询兑换记录
	 * 
	 * @param token
	 *            用户token
	 * @param status
	 *            兑换状态 0兑换成功 1已发货 2已收货
	 * @param pageNo
	 *            页数
	 * @param pageSize
	 *            每页条数
	 * @return pageInfo
	 */
	@RequestMapping(value = "/queryExchangeRecord", method = RequestMethod.POST)
	public @ResponseBody Object queryExchangeRecord(String token, Integer status, Integer pageNo, Integer pageSize) {
		LOG.info("--我的领地 查询礼品兑换记录-- \n" + token + "\t" + status + "\t" + pageNo + "\t" + pageSize);
		PageInfo pageInfo = new PageInfo();
		try {
			String tokenKey = AppCons.TOKEN_VERIFY + token;
			pageInfo = myPlaceService.queryExchangeRecord(getUid(tokenKey), status, pageNo, pageSize);
		} catch (Exception e) {
			pageInfo.setCode(ResultInfo.SERVER_ERROR.getCode());
			pageInfo.setMsg(ResultInfo.SERVER_ERROR.getMsg());
			e.printStackTrace();
			LOG.error("查询兑换记录失败--系统错误 " + e.getMessage());
		}
		return pageInfo;
	}

	/**
	 * 获取 礼品详情
	 * @param id id
	 * @return pageInfo
	 */
	@RequestMapping(value = "/getDetail", method = RequestMethod.POST)
	public @ResponseBody Object getDetail(Integer id) {
		LOG.info("--我的领地 获取礼品详情-- \t" + id);
		PageInfo pageInfo = new PageInfo();
		try {
			pageInfo = myPlaceService.queryDetail(id);
		} catch (Exception e) {
			pageInfo.setCode(ResultInfo.SERVER_ERROR.getCode());
			pageInfo.setMsg(ResultInfo.SERVER_ERROR.getMsg());
			e.printStackTrace();
			LOG.error("查询礼品详情失败--系统错误. \n" + e.getMessage());
		}
		return pageInfo;
	}

	/**
	 * 确认收货
	 * 
	 * @param id
	 *            兑换id
	 * @param token
	 *            用户TOKEN
	 * @return pageInfo
	 */
	@RequestMapping(value = "/confirmAcceptGift", method = RequestMethod.POST)
	public @ResponseBody Object confirmAcceptGift(Integer id, String token) {
		LOG.info("--我的领地 确认收货-- \n" + id + "\t" + token);
		PageInfo pageInfo = new PageInfo();
		try {
			String tokenKey = AppCons.TOKEN_VERIFY + token;
			pageInfo = myPlaceService.confirmAcceptGift(id, getUid(tokenKey));
		} catch (Exception e) {
			pageInfo.setCode(ResultInfo.SERVER_ERROR.getCode());
			pageInfo.setMsg(ResultInfo.SERVER_ERROR.getMsg());
			e.printStackTrace();
			LOG.error("我的领地  确认收货失败，系统错误。id:" + id + "; token:" + token + "\n" + e.getMessage());
		}
		return pageInfo;
	}

	/**
	 * 查询购物车
	 * 
	 * @param token
	 *            用户token
	 * @param pageNo
	 *            页码
	 * @param pageSize
	 *            条数
	 * @return pageInfo           
	 */
	@RequestMapping(value = "/queryShopCart", method = RequestMethod.POST)
	public @ResponseBody Object queryShopCart(String token, Integer pageNo, Integer pageSize) {
		LOG.info("--我的领地 查看购物车-- \n" + token + "\t" + pageNo + "\t" + pageSize);
		PageInfo pageInfo = new PageInfo();
		try {
			String tokenKey = AppCons.TOKEN_VERIFY + token;
			pageInfo = myPlaceService.queryShopCart(getUid(tokenKey), pageNo, pageSize);
		} catch (Exception e) {
			pageInfo.setCode(ResultInfo.SERVER_ERROR.getCode());
			pageInfo.setMsg(ResultInfo.SERVER_ERROR.getMsg());
			e.printStackTrace();
			LOG.error("我的领地 查看购物车数据失败，系统错误。token:" + token + "\n" + e.getMessage());
		}
		return pageInfo;
	}

	/**
	 * 编辑购物车数量
	 * 
	 * @param id id
	 * @param num
	 *            数量
	 * @param token
	 *            用户token
	 * @return pageInfo
	 */
	@RequestMapping(value = "/editShopCart", method = RequestMethod.POST)
	public @ResponseBody Object editShopCart(Integer id, Integer num, String token) {
		LOG.info("--我的领地 修改购物车数量-- \n" + id + "\t" + num + "\t" + token);
		PageInfo pageInfo = new PageInfo();
		try {
			String tokenKey = AppCons.TOKEN_VERIFY + token;
			pageInfo = myPlaceService.editShopCart(id, num, getUid(tokenKey));
		} catch (Exception e) {
			pageInfo.setCode(ResultInfo.SERVER_ERROR.getCode());
			pageInfo.setMsg(ResultInfo.SERVER_ERROR.getMsg());
			e.printStackTrace();
			LOG.error("我的领地，修改购物车数量失败，系统错误。id:" + id + "; token:" + token + "\n" + e.getMessage());
		}
		return pageInfo;
	}

	/**
	 * 添加购物车
	 * 
	 * @param token
	 *            用户token
	 * @param giftId
	 *            礼品id
	 * @param num
	 *            数量
	 * @return pageInfo
	 */
	@RequestMapping(value = "/addToShopCart", method = RequestMethod.POST)
	public @ResponseBody Object addToShopCart(String token, Integer giftId, Integer num) {
		LOG.info("--我的领地 添加商品到购物车-- \n" + token + "\t" + giftId + "\t" + num);
		PageInfo pageInfo = new PageInfo();
		try {
			String tokenKey = AppCons.TOKEN_VERIFY + token;
			pageInfo = myPlaceService.addToShopCart(getUid(tokenKey), giftId, num);
		} catch (Exception e) {
			pageInfo.setCode(ResultInfo.SERVER_ERROR.getCode());
			pageInfo.setMsg(ResultInfo.SERVER_ERROR.getMsg());
			e.printStackTrace();
			LOG.error("我的领地，添加商品到购物车失败，系统错误。 token:" + token + "; giftId:" + giftId + "\n" + e.getMessage());
		}
		return pageInfo;
	}

	/**
	 * 从购物车删除
	 * 
	 * @param id 批量删除时id用英文逗号分割
	 * @param token
	 *            用户token
	 * @return pageInfo
	 */
	@RequestMapping(value = "/removeFromShopCart", method = RequestMethod.POST)
	public @ResponseBody Object removeFromShopCart(String id, String token) {
		LOG.info("--我的领地 从购物车删除-- \n" + id + "\t" + token);
		PageInfo pageInfo = new PageInfo();
		try {
			String tokenKey = AppCons.TOKEN_VERIFY + token;
			if (id != null && id.contains(",")) {
				pageInfo = myPlaceService.batchRemoveFromShopCart(id, getUid(tokenKey));
			} else {
				pageInfo = myPlaceService.removeFromShopCart(NumberUtils.toInt(id), getUid(tokenKey));
			}
		} catch (Exception e) {
			pageInfo.setCode(ResultInfo.SERVER_ERROR.getCode());
			pageInfo.setMsg(ResultInfo.SERVER_ERROR.getMsg());
			e.printStackTrace();
			LOG.error("我的领地，从购物车删除失败，系统错误。id:" + id + ";token:" + token + "\n" + e.getMessage());
		}
		return pageInfo;
	}

	/**
	 * 批量兑换
	 * 
	 * @param giftCartIds
	 *            购物车ID 多个用英文逗号分隔
	 * @param token
	 *            token
	 * @param addressID
	 *            地址
	 * @return pageInfo
	 */
	@RequestMapping(value = "/batchExchangeGift", method = RequestMethod.POST)
	public @ResponseBody Object batchExchangeGift(String token, String giftCartIds, Integer addressID) {
		LOG.info("--我的领地 批量兑换-- \n" + token + "\t" + giftCartIds + "\t" + addressID);
		PageInfo pageInfo = new PageInfo();
		try {
			String tokenKey = AppCons.TOKEN_VERIFY + token;
			pageInfo = myPlaceService.batchExchangeGift(getUid(tokenKey), giftCartIds, addressID);
		} catch (Exception e) {
			pageInfo.setCode(ResultInfo.SERVER_ERROR.getCode());
			pageInfo.setMsg(ResultInfo.SERVER_ERROR.getMsg());
			e.printStackTrace();
		}
		return pageInfo;
	}

	/**
	 * 进入抽奖页面
	 *
	 * @Description
	 * @param request
	 *           request
	 * @param modelMap
	 *            modelMap
	 * @param typeID
	 *            对应活动类型ID
	 * @param token
	 *            用户token
	 * @return string
	 */
	@RequestMapping(value = "/gotoLuckDraw")
	public String gotoLuckDraw(ModelMap modelMap, Integer typeID, String token, HttpServletRequest request) {
		LOG.info("--进入抽奖页面-- \n" + typeID + "\t" + token);
		
		if (!StringUtils.isEmpty(token)) { // 如果用户登录了，查询中奖几率
			String redisKey = AppCons.TOKEN_VERIFY + token;
			String uid = getUid(redisKey);
			int ratio = lingbaoLotteryService.queryLotteryRatio(uid);
			request.getSession().setAttribute("MOBILE_LOTTERYTYPE_UID", uid);
			modelMap.addAttribute("ratio", ratio);
		} else {
			modelMap.addAttribute("ratio", ResultNumber.FIFTY.getNumber());
		}

		// 查询中奖记录
		List<Map<String, Object>> lotteryList = lingbaoLotteryService.queryLotteryInfo();
		modelMap.addAttribute("lotteryList", lotteryList);
		// 保存活动类型ID，用于抽奖计算时获取活动列表
		request.getSession().setAttribute("MOBILE_LOTTERYTYPE_ID", typeID);
		return "luckyDraw";
	}

	/**
	 * 查询抽奖活动信息
	 * @param response req
	 * @param request  res
	 * @Description
	 * @return
	 */
	@RequestMapping(value = "/queryChoujiangItem", method = RequestMethod.POST)
	public void queryChoujiangItem(HttpServletResponse response, HttpServletRequest request) {
		response.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=utf-8");
		JSONObject jsonObject = new JSONObject();
		PrintWriter writer = null;
		try {
			writer = response.getWriter();
			int typeID = (Integer) request.getSession().getAttribute("MOBILE_LOTTERYTYPE_ID");
			jsonObject = lingbaoLotteryService.queryChoujiangItem(typeID);
		} catch (Exception e) {
			jsonObject.put("code", ResultInfo.SERVER_ERROR.getCode());
			jsonObject.put("msg", ResultInfo.SERVER_ERROR.getMsg());
			e.printStackTrace();
			LOG.error("查询抽奖信息失败，系统错误。\n" + e.getMessage());
		}
		jsonObject.write(writer);
	}

	/**
	 * (验证)抽奖
	 *
	 * @Description
	 * @param request req
	 * @param response res
	 * @return
	 */
	@RequestMapping(value = "/validateChoujiang", method = RequestMethod.POST)
	public void validateChoujiang(HttpServletRequest request, HttpServletResponse response) {
		response.setHeader("Pragma", "No-cache");
		response.setHeader("Cache-Control", "No-cache");
		response.setDateHeader("Expires", ResultNumber.MINUS_ONE.getNumber());
		response.setHeader("Cache-Control", "No-store");
		response.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=utf-8");
		int typeID = (Integer) request.getSession().getAttribute("MOBILE_LOTTERYTYPE_ID");
		String uid = (String) request.getSession().getAttribute("MOBILE_LOTTERYTYPE_UID");
		
		LOG.info("用户" + uid + "开始抽奖 \t" + typeID);
		
		JSONObject jsonObject = new JSONObject();
		PrintWriter writer = null;
		try {
			jsonObject = lingbaoLotteryService.validateChoujiang(uid, typeID);
			writer = response.getWriter();
		} catch (Exception e) {
			jsonObject.put("code", ResultInfo.SERVER_ERROR.getCode());
			jsonObject.put("msg", ResultInfo.SERVER_ERROR.getMsg());
			e.printStackTrace();
			LOG.error("抽奖失败。系统错误。\n" + e.getMessage());
		}
		jsonObject.write(writer);
	}

	/**
	 * 获取礼品类别列表
	 * @param number number
	 * @return pageInfo
	 */
	@RequestMapping(value = "/getGiftTypeList", method = RequestMethod.POST)
	public @ResponseBody Object getGiftTypeList(Integer number) {
		LOG.info("--查询礼品类型-- \t" + number);
		PageInfo pageInfo = new PageInfo();
		try {
			pageInfo = myPlaceService.getGiftTypeList(number);
		} catch (Exception e) {
			pageInfo.setCode(ResultInfo.SERVER_ERROR.getCode());
			pageInfo.setMsg(ResultInfo.SERVER_ERROR.getMsg());
			e.printStackTrace();
			LOG.error("查询礼品类别失败--系统错误。\n" + e.getMessage());
		}
		return pageInfo;
	}

	/**
	 * 根据条件查询礼品列表
	 * @param giftName
	 *            giftName
	 * @param typeId
	 *            类别id
	 * @param recommend
	 *            是否推荐
	 * @param number
	 *            查询条数
	 * @return 默认过滤条件 上架商品，库存>0，应用类型为兑换 shelf_status = 1 and store > 0 and
	 *         apply_type in (0,2)
	 */
	@RequestMapping(value = "/getGiftListWithCondition", method = RequestMethod.POST)
	public @ResponseBody Object getGiftListWithCondition(Integer typeId, Integer recommend, Integer number,
			String giftName) {
		LOG.info("--根据条件查询礼品-- \n" + typeId + "\t" + recommend + "\t" + number + "\t" + giftName);
		PageInfo pageInfo = new PageInfo();
		try {
			pageInfo = myPlaceService.getGiftListWithCondition(typeId, recommend, number, giftName);
		} catch (Exception e) {
			pageInfo.setCode(ResultInfo.SERVER_ERROR.getCode());
			pageInfo.setMsg(ResultInfo.SERVER_ERROR.getMsg());
			e.printStackTrace();
			LOG.error("根据类型ID查询礼品失败--系统错误 typeId" + typeId + ";recommend:" + recommend + ";number:" 
			    + number + ";giftName:" + giftName + "\n" + e.getMessage());
		}
		return pageInfo;
	}
	/**
	 * @param token
	 *            token
	 * @param pageNo
	 *            pageNo
	 * @param pageSize
	 *            pageSize
	 * @return pageInfo
	 */
	@RequestMapping(value = "/listLingbaoRecord", method = RequestMethod.POST)
	public @ResponseBody Object listLingbaoRecord(String token, Integer pageNo, Integer pageSize) {
		LOG.info("--我的领地  领宝记录-- \n" + token + "\t" + pageNo);
		PageInfo pageInfo = new PageInfo();
		try {
			String tokenKey = AppCons.TOKEN_VERIFY + token;
			pageInfo = myAccountService.listLingbaoRecord(pageNo, getUid(tokenKey), pageSize);
		} catch (Exception e) {
			pageInfo.setCode(ResultInfo.SERVER_ERROR.getCode());
			pageInfo.setMsg(ResultInfo.SERVER_ERROR.getMsg());
			e.printStackTrace();
		}
		return pageInfo;
	}
	/**
	 * @param token
	 *            token
	 * @param pageNo
	 *            pageNo
	 * @return pageInfo
	 */
	@RequestMapping(value = "/listGiftInfo", method = RequestMethod.POST)
	public @ResponseBody Object listGiftInfo(String token, Integer pageNo) {
		LOG.info("--我的领地  礼品记录-- \n" + token + "\t" + pageNo);
		PageInfo pageInfo = new PageInfo();
		try {
			String tokenKey = AppCons.TOKEN_VERIFY + token;
			pageInfo = myAccountService.listGiftInfo(getUid(tokenKey), pageNo);
		} catch (Exception e) {
			pageInfo.setCode(ResultInfo.SERVER_ERROR.getCode());
			pageInfo.setMsg(ResultInfo.SERVER_ERROR.getMsg());
			e.printStackTrace();
		}
		return pageInfo;
	}
}
