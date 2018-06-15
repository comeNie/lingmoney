package com.mrbt.lingmoney.web.controller.product;

import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mrbt.lingmoney.commons.exception.PageInfoException;
import com.mrbt.lingmoney.mapper.TradingMapper;
import com.mrbt.lingmoney.model.AreaDomain;
import com.mrbt.lingmoney.service.product.ProductService;
import com.mrbt.lingmoney.service.product.PurchaseService;
import com.mrbt.lingmoney.service.trading.JDPurchaseService;
import com.mrbt.lingmoney.service.users.UsersPropertiesService;
import com.mrbt.lingmoney.service.users.UsersService;
import com.mrbt.lingmoney.utils.PageInfo;
import com.mrbt.lingmoney.utils.ProductUtils;
import com.mrbt.lingmoney.utils.ResultParame;
import com.mrbt.lingmoney.web.controller.common.CommonMethodUtil;

/**
 * 产品相关
 * 
 * @author 000608
 *
 */

@Controller
@RequestMapping("/product")
public class ProductController {
	private static final Logger LOG = LogManager.getLogger(ProductController.class);
	@Autowired
	private ProductService productService;
	@Autowired
	private UsersPropertiesService usersPropertiesService;
	@Autowired
	private PurchaseService purchaseService;
	@Autowired
	private UsersService usersService;
	@Autowired
	private TradingMapper tradingMapper;
    @Autowired
    private JDPurchaseService jdPurchaseService;


	/**
	 * 产品列表页
	 * 
	 * @param pageNo
	 *            页数
	 * @param isRecommend
	 *            是否推荐
	 * @param cType
	 *            产品类型
	 * @param cRate
	 *            利率
	 * @param cCycle
	 *            期限
	 * @param minMenoy
	 *            起投金额
	 * @param pcStatus
	 *            项目状态
	 * @param key
	 *            产品名
	 * @param request
	 *            请求
	 * @param model
	 *            数据包装
	 * @return 返回信息
	 */
	@RequestMapping("/list/{isRecommend}_{cType}_{cRate}_{cCycle}_{minMenoy}_{pcStatus}")
	public String list(Integer pageNo, @PathVariable("isRecommend") Integer isRecommend,
			@PathVariable("cType") Integer cType, @PathVariable("cRate") Integer cRate,
			@PathVariable("cCycle") Integer cCycle, @PathVariable("minMenoy") Integer minMenoy,
			@PathVariable("pcStatus") Integer pcStatus, String key, HttpServletRequest request, ModelMap model) {
		LOG.info("查询产品信息==================");
		try {
			String cityCode = null;
			AreaDomain ad = (AreaDomain) request.getSession().getAttribute("AREADOMAIN");
			if (ad != null) {
				cityCode = ad.getBdCityCode();
			}

			productService.packageListModel(pageNo, isRecommend, cType, cRate, cCycle, minMenoy, pcStatus, key,
					cityCode, model);

			// 用户购买产品状态，默认为没买过
			model.addAttribute("userBuyState", 0);
			String uid = CommonMethodUtil.getUidBySession(request);
			if (!StringUtils.isEmpty(uid)) {
				model.addAttribute("userBuyState", usersService.userBuyState(uid).getObj());
			}

			// 新手特供活动
			PageInfo pi = usersService.activityNovicePcUrl(ResultParame.ResultNumber.TWO.getNumber());
			if (pi.getCode() == ResultParame.ResultInfo.SUCCESS.getCode()) {
				model.addAttribute("novice", pi.getObj());
			}

			CommonMethodUtil.modelUserBaseInfo(model, request, usersService);

		} catch (Exception e) {
			LOG.error("查询产品列表失败。" + e.toString());
			e.printStackTrace();
			return String.valueOf(ResultParame.ResultInfo.SERVER_ERROR.getCode());
		}

		return "product/productList";
	}

	/**
	 * 产品购买页
	 * 
	 * @param changTab
	 *            产品详情/理财记录 tab选中标识
	 * @param pageNo
	 *            页数
	 * @param code
	 *            产品code
	 * @param request
	 *            请求
	 * @param model
	 *            数据包装
	 * @return 返回信息
	 */
	@RequestMapping("/showProduct")
	public String showProduct(String changTab, Integer pageNo, String code, HttpServletRequest request,
			ModelMap model) {
		String uid = CommonMethodUtil.getUidBySession(request);
		try {
			CommonMethodUtil.modelUserBaseInfo(model, request, usersService);
			return productService.packageProductBuyDetail(model, changTab, pageNo, code, uid);
		} catch (Exception e) {
			LOG.error("加载产品详情失败。 \n" + e.toString());
			e.printStackTrace();
			return "redirect:/product/list/0_0_0_0_0_1";
		}
	}

	/**
	 * 获取 扣除手续费后理财金额
	 * 
	 * @param money
	 *            理财金额
	 * @param code
	 *            产品code
	 * @param response
	 *            响应
	 */
	@RequestMapping("/financialMoney")
	public void financialMoney(Double money, String code, HttpServletResponse response) {
		PrintWriter out = null;
		try {
			out = response.getWriter();
			response.setCharacterEncoding("utf-8");
			response.setContentType("text/html;charset=utf-8");
			double financialMoney = productService.getFinancialMoney(code, money);

			DecimalFormat df = new DecimalFormat("0.00");
			out.print(df.format(financialMoney));

		} catch (Exception e) {
			LOG.error("计算理财金额失败。 \n" + e.toString());
			e.printStackTrace();
			out.print("code:500");

		} finally {
			if (out != null) {
				out.flush();
				out.close();
			}
		}
	}

	/**
	 * 获取收益
	 * 
	 * @param money
	 *            理财金额
	 * @param ftime
	 *            理财时间
	 * @param fYield
	 *            年利率
	 * @param response
	 *            响应
	 */
	@RequestMapping("/income")
	public void income(Double money, Integer ftime, Double fYield, HttpServletResponse response) {
		PrintWriter out = null;
		try {
			out = response.getWriter();
			response.setCharacterEncoding("utf-8");
			response.setContentType("text/html;charset=utf-8");
			BigDecimal income = ProductUtils.countInterest(new BigDecimal(money), ftime, new BigDecimal(fYield));

			DecimalFormat df = new DecimalFormat("0.00");
			df.setRoundingMode(RoundingMode.HALF_UP);
			out.print(df.format(income));

		} catch (Exception e) {
			e.printStackTrace();
			LOG.error("计算收入错误。 \n" + e.toString());

		} finally {
			if (out != null) {
				out.flush();
				out.close();
			}
		}

	}

	/**
	 * 产品详情--》点击《立即购买》按钮
	 * 
	 * @param prCode
	 *            产品code
	 * @param lastbuyMoney
	 *            购买金额
	 * @param subAutoPay
	 *            是否自动定投
	 * @param request
	 *            请求
	 * @param model
	 *            数据包装
	 * @param response
	 *            响应
	 * @param userRedPacketId
	 *            使用优惠券id
	 * @return 返回信息
	 */
	@RequestMapping("/buyProduct")
	public @ResponseBody Object buyProduct(String prCode, String lastbuyMoney, Integer subAutoPay,
			HttpServletRequest request, ModelMap model, HttpServletResponse response, Integer userRedPacketId) {
		PageInfo pi = new PageInfo();

		// 返回结果 产品名称 剩余可购金额 用户账户余额 我的福利（红包，加息券）
		String uid = CommonMethodUtil.getUidBySession(request);

		LOG.info("用户:" + uid + ",购买:" + prCode + "产品,金额:" + lastbuyMoney + "元");

		try {
			pi = purchaseService.validBuyProduct(uid, prCode, lastbuyMoney, 0, userRedPacketId);

		} catch (Exception e) {
			pi.setCode(ResultParame.ResultInfo.SERVER_ERROR.getCode());
			pi.setMsg("系统错误");
			e.printStackTrace();
			LOG.error("购买失败：uid:" + uid + ",pCode:" + prCode + ",buyMoney:" + lastbuyMoney + "\n " + e.toString());
		}

		return pi;
	}

	/**
	 * 查询产品介绍
	 * 
	 * @param code
	 *            产品编码
	 * @param response
	 *            响应
	 */
	@RequestMapping("/getIntroduction")
	public void getIntroduction(String code, HttpServletResponse response) {
		response.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=utf-8");

		PrintWriter out = null;
		try {
			out = response.getWriter();
			String introduction = "";
			if (StringUtils.isNotBlank(code)) {
				introduction = productService.queryIntroduction(code);
			}
			out.print(introduction);

		} catch (IOException e) {
			LOG.error("获取产品介绍失败" + e.toString());
			e.printStackTrace();

		} finally {
			if (out != null) {
				out.flush();
				out.close();
			}
		}
	}

	/**
	 * 查询最新产品（杭州页面展示）
	 * 
	 * @Description
	 * @param request
	 *            请求
	 * @param response
	 *            响应
	 */
	@RequestMapping("/queryNewproduct")
	@ResponseBody
	public void queryNewproduct(HttpServletRequest request, HttpServletResponse response) {
		try {
			JSONObject json = productService.queryNewproduct();
			json.write(response.getWriter());

		} catch (IOException e) {
			LOG.error("查询最新产品失败，系统错误。 \n" + e.toString());
			e.printStackTrace();
		}
	}

	/**
	 * 通过批次号查询最新一条产品的id和code
	 * 
	 * @param batch
	 *            产品批次
	 * @param response
	 *            响应
	 * @return 返回信息
	 */
	@RequestMapping(value = "/getFirstPidAndPcodeByBatch")
	public @ResponseBody Object getFirstPidAndPcodeByBatch(String batch, HttpServletResponse response) {
		PageInfo pageInfo = new PageInfo();
		response.setHeader("Access-Control-Allow-Origin", "*");
		try {
			if (StringUtils.isBlank(batch)) {
				pageInfo.setCode(ResultParame.ResultInfo.PARAMS_ERROR.getCode());
				pageInfo.setMsg(ResultParame.ResultInfo.PARAMS_ERROR.getMsg());
				return pageInfo;
			}

			Map<String, Object> resMap = productService.getFirstPidAndPcodeByBatch(batch);

			if (resMap == null || resMap.size() < 1) {
				pageInfo.setCode(ResultParame.ResultInfo.NOT_FOUND_DATA.getCode());
				pageInfo.setMsg(ResultParame.ResultInfo.NOT_FOUND_DATA.getMsg());
				return pageInfo;
			}

			pageInfo.setCode(ResultParame.ResultInfo.SUCCESS.getCode());
			pageInfo.setMsg(ResultParame.ResultInfo.SUCCESS.getMsg());
			pageInfo.setObj(resMap);

		} catch (Exception e) {
			e.printStackTrace();
			pageInfo.setCode(ResultParame.ResultInfo.SERVER_ERROR.getCode());
			pageInfo.setMsg(ResultParame.ResultInfo.SERVER_ERROR.getMsg());
		}

		return pageInfo;
	}
	
	/**
	 * 通过批次号查询指定条数的产品基本信息
	 * 
	 * @param batch
	 *            产品批次
	 * @param size
	 *            当前页数
	 * @param response
	 *            响应
	 * @return 返回信息
	 */
	@RequestMapping(value = "/queryProductByBatch")
	public @ResponseBody Object queryProductByBatch(String batch, int size, HttpServletResponse response) {
		PageInfo pageInfo = new PageInfo();
		response.setHeader("Access-Control-Allow-Origin", "*");
		try {
			if (StringUtils.isBlank(batch)) {
				pageInfo.setCode(ResultParame.ResultInfo.PARAMS_ERROR.getCode());
				pageInfo.setMsg(ResultParame.ResultInfo.PARAMS_ERROR.getMsg());
				return pageInfo;
			}
			if (size == 0) {
				size = 1;
			}
			List<Map<String, Object>> resMap = productService.queryProductByBatch(batch, size);

			if (resMap == null || resMap.size() < 1) {
				pageInfo.setCode(ResultParame.ResultInfo.NOT_FOUND_DATA.getCode());
				pageInfo.setMsg(ResultParame.ResultInfo.NOT_FOUND_DATA.getMsg());
				return pageInfo;
			}

			pageInfo.setCode(ResultParame.ResultInfo.SUCCESS.getCode());
			pageInfo.setMsg(ResultParame.ResultInfo.SUCCESS.getMsg());
			pageInfo.setObj(resMap);

		} catch (Exception e) {
			e.printStackTrace();
			pageInfo.setCode(ResultParame.ResultInfo.SERVER_ERROR.getCode());
			pageInfo.setMsg(ResultParame.ResultInfo.SERVER_ERROR.getMsg());
		}

		return pageInfo;
	}

	/**
	 * 赠送螃蟹个数
	 * 
	 * @param response
	 *            响应
	 * @return 分页实体类
	 */
	@RequestMapping("showCrabCount")
	@ResponseBody
	public Object showCrabCount(HttpServletResponse response) {
		PageInfo pageInfo = new PageInfo();
		response.setHeader("Access-Control-Allow-Origin", "*");
		try {
			pageInfo.setCode(ResultParame.ResultInfo.SUCCESS.getCode());
			pageInfo.setMsg(ResultParame.ResultInfo.SUCCESS.getMsg());
			pageInfo.setObj(productService.showCrabCount());

		} catch (Exception e) {
			e.printStackTrace();
			pageInfo.setCode(ResultParame.ResultInfo.SERVER_ERROR.getCode());
			pageInfo.setMsg(ResultParame.ResultInfo.SERVER_ERROR.getMsg());
		}
		return pageInfo;
	}

	/**
	 * 京东支付页面
	 * 
	 * @param prCode
	 * @param lastbuyMoney
	 * @param tId
	 * @param subAutoPay
	 * @param buyTime
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/jdBuyProductHtml")
    public void jdBuyProductHtml(HttpServletResponse response, Integer jdTradingId, Integer takeheartTid) {
        jdPurchaseService.gotoPurchase(jdTradingId, response, takeheartTid);
	}

	/**
	 * 京东购买
	 * 
	 * @param tId
	 * @param request
	 * @param response
	 * @return return
	 */
	@RequestMapping("/jdBuyProduct")
	public @ResponseBody Object jdBuyProduct(HttpServletRequest request, HttpServletResponse response, Integer tId)
			throws PageInfoException {
		PageInfo pageInfo = new PageInfo();
		String uId = CommonMethodUtil.getUidBySession(request);
		try {
			pageInfo = productService.jdBuyProduct(tId, uId);
		} catch (Exception e) {
			e.printStackTrace();
			pageInfo.setCode(ResultParame.ResultInfo.SERVER_ERROR.getCode());
			pageInfo.setMsg(ResultParame.ResultInfo.SERVER_ERROR.getMsg());
		}
		return pageInfo;

	}

	/**
	 * 展示支付结果
	 * 
	 * @param request
	 *            request
	 * @param modelMap
	 *            modelMap
	 * @return return
	 * @throws Exception
	 *             Exception
	 */
	@RequestMapping("/showPayResult")
	public String showPayResult(HttpServletRequest request, ModelMap modelMap) throws Exception {

		HttpSession session = request.getSession();
		CommonMethodUtil.modelUserBaseInfo(modelMap, request, usersService);
		return productService.showPayResult(session, modelMap);
	}
	/**
	 * 查询产品分类
	 * 
	 * @return 返回信息
	 */
	@RequestMapping("/queryProductType")
	public @ResponseBody Object queryProductType(){
		PageInfo pageInfo = new PageInfo();
		LOG.info("查询产品分类 product/queryProductType");
		try {
			 productService.productCatgoryList(pageInfo);
		} catch (Exception e) {
			pageInfo.setCode(ResultParame.ResultInfo.SERVER_ERROR.getCode());
			pageInfo.setMsg(ResultParame.ResultInfo.SERVER_ERROR.getMsg());
			e.printStackTrace();
		}
		return pageInfo;
	}
}
