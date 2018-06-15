package com.mrbt.lingmoney.web.controller.myplace;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.math.NumberUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mrbt.lingmoney.model.LingbaoGift;
import com.mrbt.lingmoney.model.UsersAccount;
import com.mrbt.lingmoney.service.discover.MyPlaceService;
import com.mrbt.lingmoney.service.discover.MyPlaceWebService;
import com.mrbt.lingmoney.service.users.UsersAccountSetService;
import com.mrbt.lingmoney.service.users.UsersService;
import com.mrbt.lingmoney.utils.AppCons;
import com.mrbt.lingmoney.utils.PageInfo;
import com.mrbt.lingmoney.utils.ResultParame;
import com.mrbt.lingmoney.web.controller.common.CommonMethodUtil;

/**
 * @author yiban sun
 * @date 2016年12月27日 上午9:56:25
 * @version 1.0
 * @description 领宝兑换
 * @since
 **/
@Controller
@RequestMapping("/lingbaoExchange")
public class LingbaoExchangeController {
	private static final Logger LOG = LogManager.getLogger(LingbaoExchangeController.class);
	@Autowired
	private MyPlaceService myPlaceService;
	@Autowired
	private MyPlaceWebService myPlaceWebService;
	@Autowired
	private UsersService usersService;
	@Autowired
	private UsersAccountSetService usersAccountSetService;

	/**
	 * 兑换页面
	 *
	 * @Description
	 * @param model
	 * @param id
	 * @return
	 */
	@RequestMapping("/exchange")
	public String exchange(HttpServletRequest request, ModelMap model, Integer id) {
		LOG.info("兑换商品: 礼品ID=" + id);
		try {
			if (!StringUtils.isEmpty(id)) {
				PageInfo pi = myPlaceService.queryDetail(id);
				// 查询不到礼品或者礼品应用类型为抽奖，跳转失败页面
				if (pi.getCode() == ResultParame.ResultInfo.SUCCESS.getCode()) {
					LingbaoGift gift = (LingbaoGift) pi.getObj();
					model.addAttribute("gift", gift);
				} else {
					LOG.info("查询不到对应礼品信息,id:" + id);
					return "redirect:/lbmarket/index";
				}

			} else {
				LOG.info("参数错误，ID为空");
				return "redirect:/lbmarket/index";
			}

			CommonMethodUtil.modelUserBaseInfo(model, request, usersService);

		} catch (Exception e) {
			LOG.error("进入兑换礼品详情页失败，系统错误!" + e.getMessage());
			return String.valueOf(ResultParame.ResultInfo.SERVER_ERROR.getCode());
		}

		return "lingbaoMarket/exchange";
	}

	/**
	 * 添加商品到购物车
	 *
	 * @Description
	 * @param request
	 * @param giftId
	 * @param typeId
	 * @param num
	 * @param integral
	 * @return
	 */
	@RequestMapping("/addToShoppingCart")
	@ResponseBody
	public Object addToShoppingCart(HttpServletRequest request, Integer giftId, Integer num) {
		System.out.println("添加购物车");
		PageInfo pi = new PageInfo();

		String uid = CommonMethodUtil.getUidBySession(request);
		if (!StringUtils.isEmpty(uid)) {
			try {
				pi = myPlaceService.addToShopCart(uid, giftId, num);

			} catch (Exception e) {
				CommonMethodUtil.handelCatchedException(pi, e, LOG, "添加购物车失败，系统错误。");
			}

		} else {
			pi.setCode(ResultParame.ResultInfo.LOGIN_TIMEOUT.getCode());
			pi.setMsg("登录超时");

		}

		return pi;
	}

	/**
	 * 查询购物车礼品信息
	 *
	 * @Description
	 * @param request
	 * @param page
	 * @param rows
	 * @return
	 */
	@RequestMapping("/queryCartGift")
	@ResponseBody
	public Object queryCartGift(HttpServletRequest request, Integer page, Integer rows) {
		System.out.println("查询购物车");
		PageInfo pi = new PageInfo();

		try {
			String uid = CommonMethodUtil.getUidBySession(request);
			pi = myPlaceService.queryShopCart(uid, page, rows);

		} catch (Exception e) {
			CommonMethodUtil.handelCatchedException(pi, e, LOG, "查询购物车数据失败，系统错误。");
		}

		return pi;
	}

	/**
	 * 兑换验证
	 *
	 * @Description
	 * @param request
	 * @param giftId
	 * @return
	 */
	@RequestMapping("/validateExchange")
	@ResponseBody
	public Object validateExchange(HttpServletRequest request, Integer giftId, Integer number, String cids) {
		LOG.info("兑换验证 礼品ID：" + giftId + " 数量：" + number + " 购物车ID：" + cids);
		PageInfo pi = new PageInfo();

		String uid = CommonMethodUtil.getUidBySession(request);
		if (!StringUtils.isEmpty(uid)) {
			try {
				if (!StringUtils.isEmpty(cids)) {// 批量兑换
					pi = myPlaceService.batchExchangeGiftValidate(cids, uid);
				} else {// 单个兑换
					pi = myPlaceService.validateExchange(giftId, uid, number);
				}

			} catch (Exception e) {
				CommonMethodUtil.handelCatchedException(pi, e, LOG, "礼品兑换验证失败，系统错误。");
			}

		} else {
			pi.setCode(ResultParame.ResultInfo.LOGIN_TIMEOUT.getCode());
			pi.setMsg(ResultParame.ResultInfo.LOGIN_TIMEOUT.getMsg());

		}

		return pi;
	}

	/**
	 * 确认兑换
	 *
	 * @Description
	 * @param request
	 * @param giftId
	 * @return
	 */
	@RequestMapping("/exchangeGift")
	@ResponseBody
	public Object exchangeGift(HttpServletRequest request, Integer giftId, Integer addressId, Integer num, String cids) {
		LOG.info("进行兑换 礼品ID：" + giftId + " 地址ID：" + addressId + " 数量：" + num + " 购物车ID：" + cids);
		PageInfo pi = new PageInfo();

		try {
			String uid = CommonMethodUtil.getUidBySession(request);
			if (!StringUtils.isEmpty(cids)) {
				pi = myPlaceService.batchExchangeGift(uid, cids, addressId);
			} else {
				pi = myPlaceService.exchangeGift(uid, giftId, num, addressId);
			}

		} catch (Exception e) {
			CommonMethodUtil.handelCatchedException(pi, e, LOG, "兑换礼品失败，系统错误.");
		}

		return pi;
	}

	/**
	 * 修改礼品数量（购物车）
	 *
	 * @Description
	 * @return
	 */
	@RequestMapping("/changeNum")
	@ResponseBody
	public Object changeNum(HttpServletRequest request, Integer num, Integer id) {
		LOG.info("修改礼品数量  购物车ID：" + id + " 数量：" + num);
		PageInfo pi = new PageInfo();

		try {
			String uid = CommonMethodUtil.getUidBySession(request);
			pi = myPlaceService.editShopCart(id, num, uid);

		} catch (Exception e) {
			CommonMethodUtil.handelCatchedException(pi, e, LOG, "修改购物车失败，系统错误.");
		}

		return pi;
	}

	/**
	 * 删除 批量删除时id用英文逗号分割
	 *
	 * @Description
	 * @param id
	 * @return
	 */
	@RequestMapping("/deleteFromCart")
	@ResponseBody
	public Object deleteFromCart(HttpServletRequest request, String id) {
		LOG.info("从购物车删除： id：" + id);
		PageInfo pi = new PageInfo();

		try {
			String uid = CommonMethodUtil.getUidBySession(request);
			if(id != null && id.contains(",")) {
				pi = myPlaceService.batchRemoveFromShopCart(id, uid);
			} else {
				pi = myPlaceService.removeFromShopCart(NumberUtils.toInt(id), uid);
			}

		} catch (Exception e) {
			CommonMethodUtil.handelCatchedException(pi, e, LOG, "从购物车删除失败，系统错误");
		}

		return pi;
	}

	/**
	 * 我的账户，领宝兑换管理页面
	 *
	 * @Description
	 * @param type
	 *            1购物车 2兑换管理
	 * @return
	 */
	@RequestMapping("/exchangeRecord")
	public String exchangeRecord(ModelMap model, HttpServletRequest request, String type, Integer page, Integer rows) {
		LOG.info("进入我的账户--》领宝兑换页面");
		model.addAttribute("type", type);
		try {
			String uid = CommonMethodUtil.getUidBySession(request);
			if (!StringUtils.isEmpty(uid)) {
				PageInfo accountInfo = usersAccountSetService.queryUserAccountByUid(uid);

				if (accountInfo.getCode() == ResultParame.ResultInfo.SUCCESS.getCode()) {
					model.addAttribute("lingbao", ((UsersAccount) accountInfo.getObj()).getCountLingbao());
				}

				if (StringUtils.isEmpty(page)) {
					page = 1;
				}

				if (StringUtils.isEmpty(rows)) {
					rows = AppCons.DEFAULT_PAGE_SIZE;
				}

				// 购物车信息
				model.addAttribute("dataCart", myPlaceService.queryShopCart(uid, page, rows));
				// 待收货信息
				model.addAttribute("dataWait", myPlaceService.queryExchangeRecord(uid, 10, page, rows));
				// 交易完成信息
				model.addAttribute("dataFinish", myPlaceService.queryExchangeRecord(uid, 2, page, rows));

			} else {
				return "users/login";
			}

			CommonMethodUtil.modelUserBaseInfo(model, request, usersService);

		} catch (Exception e) {
			LOG.error("进入领宝兑换页失败，系统错误" + e.getMessage());
			e.printStackTrace();
			return String.valueOf(ResultParame.ResultInfo.SERVER_ERROR.getCode());
		}
		
		return "lingbaoMarket/membersUserActiveExchange";
	}

	/**
	 * 获取购物车数据
	 *
	 * @Description
	 * @param request
	 * @param uId
	 *            用户ID
	 * @param page
	 * @param rows
	 * @return
	 */
	@RequestMapping("/exchangeRecordList")
	@ResponseBody
	public Object exchangeRecordList(HttpServletRequest request, Integer page, Integer rows) {
		LOG.info("获取购物车数据");
		PageInfo pi = new PageInfo();

		try {
			String uid = CommonMethodUtil.getUidBySession(request);
			pi = myPlaceService.queryShopCart(uid, page, rows);

		} catch (Exception e) {
			CommonMethodUtil.handelCatchedException(pi, e, LOG, "获取购物车数据失败，系统错误");
		}

		return pi;
	}

	/**
	 * 获取《领宝兑换->进行中数据》
	 *
	 * @Description
	 * @param request
	 * @param page
	 * @param rows
	 * @return
	 */
	@RequestMapping("/queryInfoByUidWait")
	@ResponseBody
	public Object queryInfoByUidWait(HttpServletRequest request, Integer page, Integer rows) {
		LOG.info("获取领宝兑换->进行中数据");
		PageInfo pi = new PageInfo();

		try {
			String uid = CommonMethodUtil.getUidBySession(request);
			pi = myPlaceService.queryExchangeRecord(uid, 10, page, rows);

		} catch (Exception e) {
			CommonMethodUtil.handelCatchedException(pi, e, LOG, "获取《领宝兑换->进行中数据》失败 ，系统错误");
		}

		return pi;
	}

	/**
	 * 获取《领宝兑换->已兑换》数据
	 *
	 * @Description
	 * @param request
	 * @param page
	 * @param rows
	 * @return
	 */
	@RequestMapping("/queryInfoByUidFinish")
	@ResponseBody
	public Object queryInfoByUidFinish(HttpServletRequest request, Integer page, Integer rows) {
		LOG.info("获取领宝兑换->已兑换数据");
		PageInfo pi = new PageInfo();

		try {
			String uid = CommonMethodUtil.getUidBySession(request);
			pi = myPlaceService.queryExchangeRecord(uid, 2, page, rows);

		} catch (Exception e) {
			CommonMethodUtil.handelCatchedException(pi, e, LOG, "获取已兑换数据失败，系统错误");
		}

		return pi;
	}

	/**
	 * 确认收货
	 *
	 * @Description
	 * @param request
	 * @param id
	 * @return
	 */
	@RequestMapping("/confirmReceipt")
	@ResponseBody
	public Object confirmReceipt(HttpServletRequest request, Integer id) {
		LOG.info("确认收货 id:" + id);
		PageInfo pi = new PageInfo();

		try {
			String uid = CommonMethodUtil.getUidBySession(request);
			pi = myPlaceService.confirmAcceptGift(id, uid);

		} catch (Exception e) {
			CommonMethodUtil.handelCatchedException(pi, e, LOG, "确认收货失败，系统错误");
		}

		return pi;
	}
}
