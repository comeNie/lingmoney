package com.mrbt.lingmoney.service.product.impl;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.URLDecoder;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpSession;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;

import com.mrbt.lingmoney.commons.cache.RedisDao;
import com.mrbt.lingmoney.commons.cache.RedisGet;
import com.mrbt.lingmoney.commons.formula.ExchangeRateByFormula;
import com.mrbt.lingmoney.mapper.AccountFlowMapper;
import com.mrbt.lingmoney.mapper.ActivityProductMapper;
import com.mrbt.lingmoney.mapper.AlertPromptMapper;
import com.mrbt.lingmoney.mapper.ProductCategoryMapper;
import com.mrbt.lingmoney.mapper.ProductCustomerMapper;
import com.mrbt.lingmoney.mapper.ProductMapper;
import com.mrbt.lingmoney.mapper.TradingFixInfoMapper;
import com.mrbt.lingmoney.mapper.TradingMapper;
import com.mrbt.lingmoney.mapper.UsersAccountMapper;
import com.mrbt.lingmoney.mapper.UsersPropertiesMapper;
import com.mrbt.lingmoney.model.AccountFlow;
import com.mrbt.lingmoney.model.ActivityProduct;
import com.mrbt.lingmoney.model.AlertPrompt;
import com.mrbt.lingmoney.model.AlertPromptExample;
import com.mrbt.lingmoney.model.Product;
import com.mrbt.lingmoney.model.ProductCategory;
import com.mrbt.lingmoney.model.ProductCategoryExample;
import com.mrbt.lingmoney.model.ProductCustomer;
import com.mrbt.lingmoney.model.ProductDescVo;
import com.mrbt.lingmoney.model.ProductExample;
import com.mrbt.lingmoney.model.ProductExample.Criteria;
import com.mrbt.lingmoney.model.ProductQuery;
import com.mrbt.lingmoney.model.ProductWithBLOBs;
import com.mrbt.lingmoney.model.Trading;
import com.mrbt.lingmoney.model.TradingExample;
import com.mrbt.lingmoney.model.TradingFixInfo;
import com.mrbt.lingmoney.model.UserFinance;
import com.mrbt.lingmoney.model.UsersAccount;
import com.mrbt.lingmoney.model.UsersProperties;
import com.mrbt.lingmoney.model.UsersPropertiesExample;
import com.mrbt.lingmoney.model.webView.ProductView;
import com.mrbt.lingmoney.service.product.ProductService;
import com.mrbt.lingmoney.service.trading.TradingFixRuleBuyService;
import com.mrbt.lingmoney.service.trading.TradingService;
import com.mrbt.lingmoney.service.trading.impl.TradingFixRuleBuyServiceImpl.CallbackType;
import com.mrbt.lingmoney.service.users.UsersService;
import com.mrbt.lingmoney.service.users.VerifyService;
import com.mrbt.lingmoney.utils.AppCons;
import com.mrbt.lingmoney.utils.Common;
import com.mrbt.lingmoney.utils.DateUtils;
import com.mrbt.lingmoney.utils.GetHtmlStr;
import com.mrbt.lingmoney.utils.GridPage;
import com.mrbt.lingmoney.utils.PageInfo;
import com.mrbt.lingmoney.utils.ProductUtils;
import com.mrbt.lingmoney.utils.PropertiesUtil;
import com.mrbt.lingmoney.utils.ResultParame;
import com.mrbt.lingmoney.utils.ResultParame.ResultInfo;
import com.mrbt.lingmoney.utils.ResultParame.ResultNumber;

import net.sf.json.JSONObject;

/**
 * 产品
 *
 * @author lihq
 * @date 2017年4月13日 下午3:10:08
 * @description
 * @version 1.0
 * @since 2017-04-12
 */
@Service
public class ProductServiceImpl implements ProductService {
	private static final Logger LOGGER = LogManager.getLogger(ProductServiceImpl.class);
    private static final String CRABCOUNT_REDIS_KEY = "crabcount";
	@Autowired
	private ProductMapper productMapper;
	@Autowired
	private ProductCustomerMapper productCustomerMapper;
	@Autowired
	private TradingMapper tradingMapper;
	@Autowired
	private RedisGet redisGet;
	@Autowired
	private ProductCategoryMapper productCategoryMapper;
	@Autowired
	private TradingService tradingService;
	@Autowired
	private ExchangeRateByFormula formula;
	@Autowired
	private UsersAccountMapper usersAccountMapper;
	@Autowired
	private UsersService usersService;
	@Autowired
	private ActivityProductMapper activityProductMapper;
	@Autowired
	private AlertPromptMapper alertPromptMapper;
	@Autowired
	private RedisDao redisDao;
	@Autowired
	private VerifyService verifyService;
	@Autowired
	private UsersPropertiesMapper usersPropertiesMapper;
	@Autowired
	private TradingFixInfoMapper tradingFixInfoMapper;
	@Autowired
	private TradingFixRuleBuyService tradingFixRuleBuyService;
	@Autowired
	private AccountFlowMapper accountFlowMapper;

	@Override
	public PageInfo selectHomeProduct(String token, String cityCode, String needLogin, Integer type) {
		PageInfo pageInfo = new PageInfo();

		// 用户理财状态 默认新手标0 推荐产品1
		Integer userState = 0;
		// 用户需要登录
		if ("Y".equals(needLogin)) {
			// 从redis中获取用户id
			String uId = redisGet.getRedisStringResult(token).toString();
            if (!StringUtils.isEmpty(uId)) { // uId不为空
				TradingExample tradingExample = new TradingExample();
                tradingExample
                        .createCriteria()
                        .andUIdEqualTo(uId)
                        .andStatusIn(
                                Arrays.asList(AppCons.BUY_OK, AppCons.SELL_STATUS, AppCons.SELL_OK, AppCons.BUY_FROKEN,
                                        AppCons.SELL_WAITING));
				long buyCount = tradingMapper.countByExample(tradingExample);
                if (buyCount > 0) { // 购买过产品
					userState = 1;
				}
			}
		}

		// 根据用户理财状态查询对应产品
		final int limitEnd = 3; // 推荐产品只查询三条记录
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("userState", userState);
		map.put("limitEnd", limitEnd);
		// 如果cityCode不为null，则为** or 000
		if (!StringUtils.isEmpty(cityCode)) {
			map.put("cityCode", cityCode);
		}
		List<ProductCustomer> list = productCustomerMapper.selectHomeProduct(map);
		if (list == null || list.size() == 0) {
			pageInfo.setCode(ResultInfo.RETURN_DATA_EMPTY_DATA.getCode());
			pageInfo.setMsg("暂无数据");
			return pageInfo;
		}
		//
		List<Map<String, Object>> listMap = new ArrayList<Map<String, Object>>();
		for (ProductCustomer productCustomer : list) {
			// 返回结果集，将bean转为map
			Map<String, Object> resMap = Common.beanToMap(productCustomer);
			// 查询活动跳转链接
			// 对应活动id
			Integer actId = productCustomer.getActId();
			if (actId != null) {
				// 对应活动
				ActivityProduct activityProduct = activityProductMapper.selectByPrimaryKey(actId);
				if (activityProduct != null) {
					LOGGER.info("查询活动成功：" + activityProduct.getActName());
					// 查询弹框提示
					String apId = activityProduct.getApId();
					// 如果新手活动关联弹框提示表，则取关联提示信息，否则取h5url
					if (!StringUtils.isEmpty(apId)) {
						LOGGER.info("新手活动关联弹框提示表，urlType=0");
                        resMap.put("urlType", 0); // 链接跳转类型 0 app

						AlertPromptExample example = new AlertPromptExample();
						example.createCriteria().andStatusEqualTo(1).andIdEqualTo(apId);
						List<AlertPrompt> listAlertPrompt = alertPromptMapper.selectByExample(example);
						if (listAlertPrompt != null && listAlertPrompt.size() > 0) {
							// 查询状态为可用的弹框提示bean
							AlertPrompt alertPrompt = listAlertPrompt.get(0);
							LOGGER.info("当前弹框提示数据：" + alertPrompt.toString());
							resMap.put("activityPic", activityProduct.getActivityPic());
                            final int iosType = 0, androidType = 1;
                            if (type == iosType) { // ios
								// iOS封装对象
								Map<String, Object> iOSObj = new HashMap<String, Object>();
								// iOS调用类名
								iOSObj.put("class", alertPrompt.getClassIos());
								// iOS属性，put(K,V) K->propertyKeyIos按英文逗号分隔数组遍历
								// V->propertyValueIos按英文逗号分隔数组遍历
								Map<String, Object> iOSProperty = new HashMap<String, Object>();
								String propertyKeyIos = alertPrompt.getPropertyKeyIos();
								String propertyValueIos = alertPrompt.getPropertyValueIos();
                                String[] pki = propertyKeyIos.split(",");
                                String[] pvi = propertyValueIos.split(",");
								for (int i = 0; i < pki.length; i++) {
									iOSProperty.put(pki[i], pvi[i]);
								}
								// iOS属性封装
								iOSObj.put("property", iOSProperty);
								// iOS封装对象
								resMap.put("iOSObj", iOSObj);
                            } else if (type == androidType) { // android
								// 安卓跳转类型。0，activity 1，fragment 2，webview
								resMap.put("androidJumpType", alertPrompt.getAndroidJumpType());
								// android封装对象
								Map<String, Object> androidObj = new HashMap<String, Object>();
								// android调用类名
								androidObj.put("classAndroid", alertPrompt.getClassAndroid());
								// android属性，put(K,V)
								// K->propertyKeyIos按英文逗号分隔数组遍历
								// V->propertyValueIos按英文逗号分隔数组遍历
								Map<String, Object> androidProperty = new HashMap<String, Object>();
								String propertyKeyAndroid = alertPrompt.getPropertyKeyAndroid();
								String propertyValueAndroid = alertPrompt.getPropertyValueAndroid();
                                String[] pka = propertyKeyAndroid.split(",");
                                String[] pva = propertyValueAndroid.split(",");
								for (int i = 0; i < pka.length; i++) {
									androidProperty.put(pka[i], pva[i]);
								}
								// android属性封装
								androidObj.put("property", androidProperty);
								// android封装对象
								resMap.put("androidObj", androidObj);
							}
						}

					} else {
						LOGGER.info("新手活动跳转链接为h5，urlType=1");
                        resMap.put("urlType", 1); // 链接跳转类型 1 h5
						resMap.put("linkUrlApp", activityProduct.getLinkUrlApp());
					}
				}
			}
			listMap.add(resMap);
		}
		Map<String, Object> resMap = new HashMap<String, Object>();
        resMap.put("list", listMap); // 首页产品信息
        resMap.put("userState", userState); // 用户理财状态
        pageInfo.setCode(ResultInfo.SUCCESS.getCode());
		pageInfo.setMsg("查询成功");
		pageInfo.setObj(resMap);
		return pageInfo;
	}

	@Override
	public PageInfo selectProductList(Integer proId, PageInfo pageInfo, Integer status, String cityCode) {
		
		// 查询条件
		Map<String, Object> condition = new HashMap<String, Object>();
		if (status != null) {
			condition.put("status", status);
		}
		if (proId != null) {
			condition.put("proId", proId);
		}
		// 如果cityCode不为null，则为** or 000
		if (!StringUtils.isEmpty(cityCode)) {
			condition.put("cityCode", cityCode);
		}
        condition.put("newPCid", AppCons.NEW_PRODUCT_TYPE_ID);
		pageInfo.setCondition(condition);
		// 总条数
		Integer total = productCustomerMapper.selectProductCount(pageInfo);
		if (total > 0) {
            pageInfo.setCode(ResultInfo.SUCCESS.getCode());
			pageInfo.setMsg("查询成功");
			// 结果集
			pageInfo.setRows(productCustomerMapper.selectProductList(pageInfo));
			pageInfo.setTotal(total);
		} else {
            pageInfo.setCode(ResultInfo.RETURN_DATA_EMPTY_DATA.getCode());
			pageInfo.setMsg("暂无数据");
		}
		return pageInfo;
	}

	/**
	 * 获取产品详情，返回list
	 */
	@Override
	public List<ProductDescVo> selectProductDesc(Integer proId) {
		try {
			ProductWithBLOBs product = productMapper.selectByPrimaryKey(proId);
			if (!StringUtils.isEmpty(product)) {
				String desc = product.getDescription();
				if (desc != null && !desc.equals("") && !desc.substring(0, 2).equals("{\"")) {
					return null;
				}
				if (!StringUtils.isEmpty(desc)) {
					List<ProductDescVo> list = new ArrayList<ProductDescVo>();
					JSONObject json = JSONObject.fromObject(desc);
                    final int descNum = 9; // 描述信息为JSON对象，里面有8个键值对，1 - 8
                    for (int i = 1; i < descNum; i++) {
						String title = json.optString("title" + i);
						String content = json.optString("content" + i);
						if (!StringUtils.isEmpty(title) && !StringUtils.isEmpty(content)) {
							list.add(new ProductDescVo(title, content));
						}
					}
					return list;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 获取产品详情，返回string
	 */
	@Override
	public String selectProductDescStr(Integer proId) {
		ProductWithBLOBs product = productMapper.selectByPrimaryKey(proId);
		if (!StringUtils.isEmpty(product)) {
			return product.getDescription();
		}
		return null;
	}

	@Override
	public String selectProductInfo(Integer proId, Integer clientType) {
		ProductWithBLOBs product = productMapper.selectByPrimaryKey(proId);
		String projectInfo = null;
		if (!StringUtils.isEmpty(product)) {
			if (clientType == 0) {
                projectInfo = product.getProjectInfo(); // pc详情
			} else if (clientType == 1) {
                projectInfo = product.getProjectInfoMobile(); // app详情
			}
		}
		return projectInfo;
	}

	@Override
	public List<String> selectProductPic(Integer proId) {
		ProductWithBLOBs product = productMapper.selectByPrimaryKey(proId);
		if (!StringUtils.isEmpty(product)) {
			String intro = product.getIntroduction();
			if (!StringUtils.isEmpty(intro)) {
				List<String> list = GetHtmlStr.getImgSrc(intro);
				return list;
			}
		}
		return null;
	}

	@Override
	public void selectTradingRecordList(Integer proId, PageInfo pageInfo) {
		// 查询条件
		Map<String, Object> condition = new HashMap<String, Object>();
		condition.put("proId", proId);
		pageInfo.setCondition(condition);
		// 总条数
		Integer total = productCustomerMapper.selectTradingRecordCount(pageInfo);
		if (total > 0) {
            pageInfo.setCode(ResultInfo.SUCCESS.getCode());
			pageInfo.setMsg("查询成功");
			// 结果集
			pageInfo.setRows(productCustomerMapper.selectTradingRecordList(pageInfo));
			pageInfo.setTotal(total);
		} else {
            pageInfo.setCode(ResultInfo.RETURN_DATA_EMPTY_DATA.getCode());
			pageInfo.setMsg("暂无数据");
		}
	}

	@Override
	public void packageListModel(Integer pageNo, Integer isRecommend, Integer cType, Integer cRate, Integer cCycle,
			Integer minMenoy, Integer pcStatus, String key, String cityCode, ModelMap modelMap) {
		ProductQuery query = new ProductQuery();
		// 分页判断
		if (StringUtils.isEmpty(pageNo)) {
			pageNo = 1;
		}
		PageInfo pi = new PageInfo(pageNo, AppCons.DEFAULT_PAGE_SIZE);
		query.setStart(pi.getFrom());
		query.setNumber(pi.getSize());

        query.setExcludPcId(Integer.parseInt(AppCons.NEW_PRODUCT_TYPE_ID));
		// 只显示地区+全国产品默认编码(000)
		if (!StringUtils.isEmpty(cityCode)) {
			query.setCityCode(cityCode);
		}

		if (!StringUtils.isEmpty(key)) {
			try {
				key = URLDecoder.decode(key, "UTF-8");
				query.setKeyword("%" + key + "%");
			} catch (UnsupportedEncodingException e) {
				LOGGER.error("解码KEY错误。key" + key + "==" + e);
				e.printStackTrace();
			}
		} else {
			if (!StringUtils.isEmpty(isRecommend)) {
				query.setIsRecommend(isRecommend);
			}
			if (!StringUtils.isEmpty(cType)) {
				query.setPcId(cType);
			}
			if (!StringUtils.isEmpty(cRate)) {
				query.changeRate(cRate);
			}
			if (!StringUtils.isEmpty(cCycle)) {
				query.changeCycle(cCycle);
			}
			if (!StringUtils.isEmpty(minMenoy)) {
				query.changeMinMoney(minMenoy);
			}
			if (!StringUtils.isEmpty(cType) && pcStatus != 0) {
				query.setPcStatus(pcStatus);
			}
		}

		GridPage<Product> gridPage = listGrid(query);
		modelMap.addAttribute("gridPage", gridPage);
		modelMap.addAttribute("pageNo", pageNo);
		modelMap.addAttribute("pageSize", AppCons.DEFAULT_PAGE_SIZE);
		modelMap.addAttribute("totalSize", gridPage.getTotal());

		if (key != null && !"".equals(key)) {
			modelMap.addAttribute("key", key);
			modelMap.addAttribute("isRecommend", 0);
			modelMap.addAttribute("cType", 0);
			modelMap.addAttribute("cRate", 0);
			modelMap.addAttribute("cCycle", 0);
			modelMap.addAttribute("minMenoy", 0);
			modelMap.addAttribute("pcStatus", 0);

		} else {
			modelMap.addAttribute("isRecommend", isRecommend);
			modelMap.addAttribute("cType", cType);
			modelMap.addAttribute("cRate", cRate);
			modelMap.addAttribute("cCycle", cCycle);
			modelMap.addAttribute("minMenoy", minMenoy);
			modelMap.addAttribute("pcStatus", pcStatus);
		}

		ProductCategoryExample pcExample = new ProductCategoryExample();
		pcExample.createCriteria().andStatusEqualTo(1);
		List<ProductCategory> productCatgoryList = productCategoryMapper.selectByExample(pcExample);

		modelMap.addAttribute("productCatgoryList", productCatgoryList);

	}

	@Override
	public String packageProductBuyDetail(ModelMap modelMap, String changTab, Integer pageNo, String code, String uid) {
		// 标识页面选中TAB
		modelMap.addAttribute("changTab", changTab);

		// 产品代码不为空，查询产品
		if (!StringUtils.isEmpty(code)) {
			ProductView product = productCustomerMapper.selectProductViewByCode(code);

            if (product == null || (product.getStatus() != 1 && !AppCons.TAKE_HEART_PCODE.equals(code))) {
				LOGGER.info("产品代码错误或者产品不在筹集期");
				return "redirect:/product/list/0_0_0_0_0_1";
			}

			modelMap.addAttribute("product", product);
			// meta 信息展示
			modelMap.addAttribute("pname", product.getName());
			modelMap.addAttribute("userBuyState", 0);

			// 登录标识
			boolean isLogin = false;

			// 如果用户登录，查询用户余额
			if (!StringUtils.isEmpty(uid)) {
				isLogin = true;
				UsersAccount account = usersAccountMapper.selectByUid(uid);
				if (account != null) {
					DecimalFormat df = new DecimalFormat("#,##0.00");
					modelMap.addAttribute("balance", df.format(account.getBalance()));
				} else {
					LOGGER.error("用户ID：" + uid + "，未查询到账户信息。");
				}

				// 用户购买产品状态
				modelMap.addAttribute("userBuyState", usersService.userBuyState(uid).getObj());
			}

			modelMap.addAttribute("isLogin", isLogin);

			// 新手特供活动
            PageInfo pi = usersService.activityNovicePcUrl(3);
            if (pi.getCode() == ResultInfo.SUCCESS.getCode()) {
				modelMap.addAttribute("novice", pi.getObj());
			}

			// 产品详情
			List<ProductDescVo> descriptionList = selectProductDesc(product.getId());
			// 兼容老版本产品，处理方式，如果详情是json方式，返回list,如果是string方式返回string
			if (descriptionList != null) {
				modelMap.addAttribute("descriptionList", selectProductDesc(product.getId()));
			} else {
				modelMap.addAttribute("descriptionString", selectProductDescStr(product.getId()));
			}

			// 产品项目信息
			modelMap.addAttribute("projectInfo", selectProductInfo(product.getId(), 0));

			// 判断是否为随心取
			if (AppCons.TAKE_HEART_PCODE.equals(code)) {
                // 判断用户是否有随心取
                TradingExample tradingExmp = new TradingExample();
                tradingExmp.createCriteria().andUIdEqualTo(uid).andPCodeEqualTo(code)
                        .andStatusIn(Arrays.asList(AppCons.BUY_OK, AppCons.BUY_FROKEN));
                if (tradingMapper.countByExample(tradingExmp) > 0) {
                    modelMap.addAttribute("takeHeart", 1);
                } else {
                    LOGGER.info("随心取产品只能申购");
                    return "redirect:/product/list/0_0_0_0_0_1";
                }

			} else {
				modelMap.addAttribute("takeHeart", 0);
			}

			// 查询理财该产品的购买记录
			if (StringUtils.isEmpty(pageNo)) {
				pageNo = 1;
			}
			int pid = Integer.parseInt(code.substring(code.length() - 4, code.length()));
			GridPage<Map<String, Object>> gridPage = tradingService.listTRUserGrid(pid, pageNo);
			if (product.getEdDt() != null) {
				modelMap.addAttribute("endDate", product.getEdDt().getTime());
			}
			modelMap.addAttribute("gridPage", gridPage);
			modelMap.addAttribute("pageNo", pageNo);
			modelMap.addAttribute("pageSize", AppCons.DEFAULT_PAGE_SIZE);
			modelMap.addAttribute("totalSize", gridPage.getTotal());

			// 是否能定投，此标识暂时无用
			Calendar cal = Calendar.getInstance();
			cal.setTime(new Date());
			int dingTouCanBuy = 0;
            final int dingtouDate = 25; // 定投日期在每月25号前
            if (cal.get(Calendar.DAY_OF_MONTH) >= 1 && cal.get(Calendar.DAY_OF_MONTH) <= dingtouDate) {
				dingTouCanBuy = 0;
			} else {
				dingTouCanBuy = 1;
			}
			modelMap.addAttribute("dingTouCanBuy", dingTouCanBuy);

			// 目前只有固定收益产品
            String type = code.substring(8, 9); // 产品类型 0 固定收益， 1 浮动
			if (type.equals("0")) {
                // 随心取提出一个页面
                if (AppCons.TAKE_HEART_PCODE.equals(code)) {
                    return "product/productTakeheartDetail";
                } else {
                    return "product/productDetail";
                }

			} else {
				return "redirect:/product/list/0_0_0_0_0_1";
			}

		}

		return null;
	}

	@Override
	public String queryIntroduction(String code) {
		ProductWithBLOBs pwb = productCustomerMapper.selectByCodeWithBlob(code);
		if (pwb != null) {
			return pwb.getIntroduction();
		}
		return "";
	}

	@Override
	public double getFinancialMoney(String code, Double buyMoney) {
		double financialMoney = 0;

		Product product = productCustomerMapper.selectByCode(code);

		if (product != null) {
            if (product.getFees() == 0 || product.getFees() == 2) { // 无手续费或后端收取手续费
				financialMoney = buyMoney;

            } else { // 前端手续费
				double feesRate = product.getFeesRate().doubleValue();
				double fees = buyMoney * feesRate;
				financialMoney = buyMoney - fees;
			}
		}

		return financialMoney;

	}

	@Override
	public JSONObject queryNewproduct() {
		JSONObject json = new JSONObject();

		// 最新发布产品
		ProductExample example = new ProductExample();
		example.createCriteria().andStatusEqualTo(1);
		example.setOrderByClause("st_dt desc");
		example.setLimitStart(0);
		example.setLimitEnd(1);
		List<Product> list = productMapper.selectByExample(example);

		if (list != null && list.size() > 0) {
			Product latestProduct = list.get(0);
			json.put("latestProduct", latestProduct);
            json.put("code", ResultInfo.SUCCESS.getCode());
			json.put("msg", "success");

		} else {
			json.put("latestProduct", null);
            json.put("code", ResultInfo.EMPTY_DATA.getCode());
			json.put("msg", "no data");
		}
		return json;
	}

	@Override
	public GridPage<Product> listGrid(ProductQuery query) {
		GridPage<Product> result = new GridPage<Product>();
		result.setRows(list(query));
		result.setTotal(listCount(query));
		return result;
	}

	/**
	 * 查询总数
	 * 
	 * @param productQuery 条件辅助类
	 * @return 总数
	 */
	private int listCount(ProductQuery productQuery) {
		return productCustomerMapper.selectProductListWebCount(productQuery);
	}

	/**
	 * 查找，根据vo
	 * 
	 * @param productQuery 条件辅助类
	 * @return ProductParam集合
	 */
	private List<Product> list(ProductQuery productQuery) {
		return productCustomerMapper.selectProductListWeb(productQuery);
	}

	@Override
	public void handleUserFinance(UserFinance userFinance) {
		// 只有待支付的订单有剩余支付时间
		if (userFinance.getStatus().intValue() == 0) {
			// 订单剩余支付时间
			String orderRemainPayTime = PropertiesUtil.getPropertiesByKey("orderRemainPayTime");
			// 支付剩余时间（毫秒）购买时间+15分钟-当前时间
			long remainDt = userFinance.getBuyDt().getTime() + Integer.valueOf(orderRemainPayTime) * 60 * 1000
					- System.currentTimeMillis();
			remainDt = remainDt < 0 ? 0 : remainDt;
			// 距离产品结束时间
			long remainEdDt = userFinance.getEdDt().getTime() - System.currentTimeMillis();
			remainEdDt = remainEdDt < 0 ? 0 : remainEdDt;
			userFinance.setRemainDt(remainDt < remainEdDt ? remainDt : remainEdDt);
		}
		// 预期收益
		if (userFinance.getExpectEarn() == null) {
			double expectInterest = getIncome(userFinance.getFinancialMoney().doubleValue(), 0,
					userFinance.getProductTerm(), userFinance.getfYield().doubleValue());
			userFinance.setExpectEarn(new BigDecimal(expectInterest));
		}
		// 实际支付金额，减去红包加息券等
		BigDecimal ticket = new BigDecimal(0);
		userFinance.setPayMoney(userFinance.getFinancialMoney().subtract(ticket));
	}

    /**
     * 计算收入
     * 
     * @param financialMoney 理财金额
     * @param unitTime 单位时间
     * @param number 持有时间
     * @param fYield 年化收益
     * @return 计算所得收入
     *
     */
	private double getIncome(Double financialMoney, Integer unitTime, Integer number, Double fYield) {
		double income = 0;
		if (StringUtils.isEmpty(financialMoney) || StringUtils.isEmpty(unitTime) || StringUtils.isEmpty(number)
				|| StringUtils.isEmpty(fYield)) {
			return income;
		}
		formula.setPp(new BigDecimal(financialMoney));
		formula.setR(new BigDecimal(fYield));
		formula.setHold(number);
		if (unitTime == AppCons.UNIT_TIME_DAY) {
			income = formula.getFix_day_r_value().doubleValue();
		} else if (unitTime == AppCons.UNIT_TIME_MONTH) {
			income = formula.getFix_month_r_value().doubleValue();
		}
		return income;
	}

	@Override
	public Map<String, Object> getFirstPidAndPcodeByBatch(String batch) {
		return productCustomerMapper.getFirstPidAndPcodeByBatch(batch);
	}

	@Override
	public int showCrabCount() {
		int crabCount = 0;
		if (redisDao.hasKey(CRABCOUNT_REDIS_KEY) && (Integer) redisDao.get(CRABCOUNT_REDIS_KEY) > 0) {
			crabCount = (Integer) redisDao.get(CRABCOUNT_REDIS_KEY);
		}
		return crabCount;
	}

	@Override
	public List<Map<String, Object>> queryProductByBatch(String batch, int size) {
		Map<String, Object> resMap = new HashMap<String, Object>();
		resMap.put("batch", batch);
		resMap.put("size", size);
		return productCustomerMapper.queryProductByBatch(resMap);
	}

	@Override
	public void getTradingFixInfoByTid(Trading trading) {
		TradingFixInfo fixInfo = tradingFixInfoMapper.selectFixInfoByTid(trading.getId());
		if (null != fixInfo) {
			trading.setFixInfo(fixInfo);
		}
	}

	@Override
	public PageInfo jdBuyProduct(Integer tId, String uId) {
		PageInfo pageInfo = new PageInfo();

		// 1、验证用户ID
		try {
			verifyService.verifyUserId(uId);
		} catch (Exception e) {
			pageInfo.setCode(ResultInfo.RETURN_DATA_EMPTY_DATA.getCode());
			pageInfo.setMsg("用户不存在");
			return pageInfo;
		}

		// 2、判断用户是否存在
		UsersPropertiesExample propertiesExample = new UsersPropertiesExample();
		propertiesExample.createCriteria().andUIdEqualTo(uId);
		List<UsersProperties> list = usersPropertiesMapper.selectByExample(propertiesExample);
		if (StringUtils.isEmpty(list) || list.size() == 0) {
			pageInfo.setMsg("用户不存在");
			pageInfo.setCode(ResultInfo.ACCOUNT_NUMBER_NON_EXIST.getCode());
			return pageInfo;
		}

		// 3、根据tId查询出对应的购买金额和借款编号
		Trading trading = tradingMapper.selectByPrimaryKey(tId);
		if (StringUtils.isEmpty(trading)) {
			pageInfo.setMsg("交易不存在");
			pageInfo.setCode(ResultInfo.PROJECT_NOT_EXIST.getCode());
			return pageInfo;
		}
		TradingFixInfo tradingFixInfo = tradingFixInfoMapper.selectFixInfoByTid(tId);
		if (StringUtils.isEmpty(tradingFixInfo)) {
			pageInfo.setMsg("交易不存在");
			pageInfo.setCode(ResultInfo.PROJECT_NOT_EXIST.getCode());
			return pageInfo;
		}

		// 4、判断产品状态以及距离产品结束时间
		Product product = productMapper.selectByPrimaryKey(trading.getpId());
		if (product.getStatus() != AppCons.PRODUCT_STATUS_READY
				|| product.getEdDt().getTime() < System.currentTimeMillis()) {
			pageInfo.setMsg("超时未支付,该订单已失效");
			pageInfo.setCode(ResultInfo.ORDER_INVALID.getCode());
			return pageInfo;
		}

		// 5、判断是否是京东支付
		if (product.getpType() != 0) {
			pageInfo.setMsg("支付通道不匹配");
			pageInfo.setCode(ResultInfo.PAY_NOT_SUCCESS.getCode());
			return pageInfo;
		}

		// 订单超时未支付，则不允许支付
		// 订单剩余支付时间
		String orderRemainPayTime = PropertiesUtil.getPropertiesByKey("orderRemainPayTime");
		// 支付剩余时间（毫秒）购买时间+15分钟-当前时间
		long remainDt = trading.getBuyDt().getTime() + Integer.valueOf(orderRemainPayTime) * 60 * 1000
				- System.currentTimeMillis();

		if (remainDt <= 0) {
			pageInfo.setMsg("超时未支付,该订单已失效");
			pageInfo.setCode(ResultInfo.ORDER_INVALID.getCode());
			return pageInfo;
		}

		// 处理中
		String bizCode = UUID.randomUUID().toString().replace("-", "").substring(15) + System.currentTimeMillis();
        tradingFixRuleBuyService.handleBuyProduct(tId, bizCode, uId, CallbackType.trading);

		pageInfo.setCode(ResultInfo.SUCCESS.getCode());
		return pageInfo;
	}

	@Override
	public String showPayResult(HttpSession session, ModelMap modelMap) {
		int type = ResultParame.ResultNumber.TWO.getNumber();
		String key = AppCons.USER_BUY_REQUESTNO;

		if (type == ResultParame.ResultNumber.TWO.getNumber()) {
			key = AppCons.USER_BUY_REQUESTNO;
		}

		if (session.getAttribute(key) != null) {
			String requestNo = session.getAttribute(key) + "";

			// 查询流水表中的记录
			AccountFlow accountFlow = accountFlowMapper.findByNumber(requestNo);
			if (accountFlow != null) {

				modelMap.addAttribute("account_flow", accountFlow);
				if (accountFlow.getStatus() == ResultParame.ResultNumber.ONE.getNumber()) {
					modelMap.addAttribute("status", ResultParame.ResultNumber.ZERO.getNumber());
					return "transfeerCallBackResult";
				} else if (accountFlow.getStatus() == ResultParame.ResultNumber.TWO.getNumber()) {

					modelMap.addAttribute("status", ResultParame.ResultNumber.ONE.getNumber());
					return "transfeerCallBackResult";
				} else {
					modelMap.addAttribute("status", ResultParame.ResultNumber.TWO.getNumber());
					return "transfeerCallBackResult";
				}

			} else {
				modelMap.addAttribute("status", ResultParame.ResultNumber.TWO.getNumber());
				return "transfeerCallBackResult";
			}

		} else {
			modelMap.addAttribute("status", ResultParame.ResultNumber.TWO.getNumber());
			return "transfeerCallBackResult";
		}
	}

	@Override
	public void productCatgoryList(PageInfo pageInfo) {
		List<Integer> list = new ArrayList<Integer>();
		list.add(17);
		list.add(22);
		
		ProductCategoryExample pcExample = new ProductCategoryExample();
		pcExample.createCriteria().andStatusEqualTo(ResultNumber.ONE.getNumber()).andIdNotIn(list);
		List<ProductCategory> productCatgoryList =productCategoryMapper.selectByExample(pcExample);
		if (productCatgoryList != null && productCatgoryList.size() > ResultNumber.ZERO.getNumber()) {
			pageInfo.setCode(ResultParame.ResultInfo.SUCCESS.getCode());
			pageInfo.setMsg(ResultParame.ResultInfo.SUCCESS.getMsg());
			pageInfo.setRows(productCatgoryList);
		}else{
			pageInfo.setCode(ResultParame.ResultInfo.SERVER_ERROR.getCode());
			pageInfo.setMsg("查询产品分类失败");
		}
	}

    @Override
    public PageInfo listNewerProduct(Integer pageNo, Integer pageSize) {
        PageInfo pageInfo = new PageInfo(pageNo, pageSize);
        // 获取新手产品类型id
        ProductExample productExample = new ProductExample();
        productExample.createCriteria().andPcIdEqualTo(Integer.parseInt(AppCons.NEW_PRODUCT_TYPE_ID))
                .andStatusEqualTo(AppCons.PRODUCT_STATUS_READY);
        productExample.setOrderByClause("sort asc");
        productExample.setLimitStart(pageInfo.getFrom());
        productExample.setLimitEnd(pageInfo.getSize());

        List<Map<String, Object>> returnList = new ArrayList<Map<String, Object>>();
        List<Product> list = productMapper.selectByExample(productExample);
        if (null != list && list.size() > 0) {
            // 处理返回数据
            for (Product pro : list) {
                Map<String, Object> nPro = new HashMap<String, Object>();
                nPro.put("id", pro.getId());
                nPro.put("name", pro.getName());
                nPro.put("fYield", pro.getfYield());
                nPro.put("fTime", pro.getfTime());
                nPro.put("minMoney", pro.getMinMoney());
                nPro.put("reWay", pro.getReWay());
                nPro.put("reachMoney", pro.getReachMoney());
                nPro.put("priorMoney", pro.getPriorMoney());
				nPro.put("buyLimit", pro.getBuyLimit());
                nPro.put("progress", pro.getReachMoney().divide(pro.getPriorMoney(), 2, RoundingMode.HALF_UP));
                nPro.put("endDate", DateUtils.sf.format(pro.getEdDt()));
                nPro.put("predictEndDate", DateUtils.sf.format(DateUtils.addDay(pro.getEdDt(), pro.getfTime())));
                nPro.put("expireDt", DateUtils.addDay(pro.getEdDt(), pro.getfTime()));
				nPro.put("insuranceTrust", pro.getInsuranceTrust());
                returnList.add(nPro);
            }

            pageInfo.setResultInfo(ResultInfo.SUCCESS);
            pageInfo.setRows(returnList);
            pageInfo.setTotal(productMapper.countByExample(productExample));
        } else {
            pageInfo.setResultInfo(ResultInfo.NO_DATA);
        }

        return pageInfo;
    }

    @Override
    public PageInfo listRecommendProduct(Integer pageNo, Integer pageSize, Integer status, Integer startTime,
            Integer endTime) {
        if (StringUtils.isEmpty(pageSize)) {
            pageSize = ResultNumber.FOUR.getNumber();// 默认一页四条
        }

        PageInfo pageInfo = new PageInfo(pageNo, pageSize);
        Map<String, Object> paramMap = new HashMap<String, Object>();
        if (status != null) {
        	paramMap.put("status", status);
		} else {
			paramMap.put("status", 1);
		}
        paramMap.put("startTime", startTime);
        paramMap.put("endTime", endTime);
        paramMap.put("newPCid", AppCons.NEW_PRODUCT_TYPE_ID);
        paramMap.put("start", pageInfo.getFrom());
        paramMap.put("number", pageInfo.getSize());
        paramMap.put("approval", 2);
        List<Map<String, Object>> list = productCustomerMapper.listRecommendProduct(paramMap);
        
        if (list == null || list.size() < pageSize) {
        	paramMap.put("status", 2);
        	paramMap.put("number", pageSize - list.size());
        	List<Map<String, Object>> listOld = productCustomerMapper.listRecommendProduct(paramMap);
        	for (Map<String, Object> map : listOld) {
        		list.add(map);
			}
		}

        if (list != null && list.size() > 0) {
            pageInfo.setResultInfo(ResultInfo.SUCCESS);
            pageInfo.setRows(list);
            pageInfo.setTotal(productCustomerMapper.countListRecommendProduct(paramMap));
            pageInfo.setObj(list.get(0)); // 取第一条记录放在OBJ，便于首页查询展示。
        } else {
            pageInfo.setResultInfo(ResultInfo.NO_DATA);
        }

        return pageInfo;
    }

    @Override
    public PageInfo getProductDetailInfo(Integer pId) {
        PageInfo pageInfo = new PageInfo();

        if (!StringUtils.isEmpty(pId)) {
            ProductWithBLOBs pwb = productMapper.selectByPrimaryKey(pId);
            if (pwb != null) {
                Map<String, String> resultMap = new HashMap<String, String>();
                // 产品名称
                resultMap.put("name", pwb.getName());
                // 产品期限
                resultMap.put("timeLimit", ProductUtils.getFinancialDays(pwb) + "天");
                // 认购规模
                DecimalFormat df = new DecimalFormat("#,##0.00");
                resultMap.put("scale", df.format(pwb.getPriorMoney().doubleValue()) + "元");
                // 起投递增
                resultMap.put("startIncrease", "起投" + pwb.getMinMoney() + "元，递增" + pwb.getIncreaMoney() + "元");
                // 产品到期日
                if (null != pwb.getEstablishDt() && pwb.getStatus() != AppCons.PRODUCT_STATUS_READY) {
                    resultMap.put("endDt", DateUtils.sf.format(DateUtils.addDay(pwb.getEstablishDt(), ProductUtils.getFinancialDays(pwb))));
                } else {
                    resultMap.put("endDt", DateUtils.sf.format(pwb.getEdDt()));
                }
                // 预期年化收益率
                resultMap.put("fYield", pwb.getfYield().multiply(new BigDecimal("100")).setScale(2).toString() + "%");
                //APP项目详情
                resultMap.put("description", pwb.getProjectInfoMobile());
                // 常见问题
                if (!StringUtils.isEmpty(pwb.getNormalProblem())) {
                    resultMap.put("normalProblem", pwb.getNormalProblem());
                } else {
                    resultMap.put("normalProblem", null);
                }
                //借款人信息
                if (!StringUtils.isEmpty(pwb.getLonnerInfo())) {
                    resultMap.put("lonnerInfo", pwb.getLonnerInfo());
                } else {
                    resultMap.put("lonnerInfo", null);
                }
                // 抵押物图片
                if (!StringUtils.isEmpty(pwb.getIntroduction())) {
                    resultMap.put("introduction", pwb.getIntroduction());
                } else {
                    resultMap.put("introduction", null);
                }
				// 有无保险增信
				resultMap.put("insuranceTrust", pwb.getInsuranceTrust().toString());
                pageInfo.setObj(resultMap);
                pageInfo.setResultInfo(ResultInfo.SUCCESS);

            } else {
                pageInfo.setResultInfo(ResultInfo.NO_DATA);
            }

        } else {
            pageInfo.setResultInfo(ResultInfo.PARAM_MISS);
        }

        return pageInfo;
    }

	@Override
	public void queryPcHomeProductList(PageInfo pageInfo, String cityCode, Integer pcId, Integer status, Integer startTime, Integer endTime) {
		//查询在售产品
		ProductExample onSaleExample = new ProductExample();
		Criteria criteria = onSaleExample.createCriteria();
		
		onSaleExample.setLimitStart(pageInfo.getFrom());
		onSaleExample.setLimitEnd(pageInfo.getSize());
		
		if (status != null && !status.equals(ResultNumber.ONE.getNumber())) {
			onSaleExample.setOrderByClause(" id desc");//查询默认排序
			criteria.andStatusEqualTo(status);
		} else {
			criteria.andStatusEqualTo(ResultNumber.ONE.getNumber());
			onSaleExample.setOrderByClause(" status, sort, st_dt");//查询默认排序
		}
		//获取查询条件
		pcHomeProductExample(criteria, cityCode, pcId, startTime, endTime);
		
		int count = productMapper.countByExample(onSaleExample);
		
		if (count > 0) {
			List<Product> onSaleProduct = productMapper.selectByExample(onSaleExample);
			if (onSaleProduct.size() < pageInfo.getSize() && status == null) {
				//补充产品列表
				onSaleExample.clear();
				
				Criteria criteria2 = onSaleExample.createCriteria();
				
				pcHomeProductExample(criteria2, cityCode, pcId, startTime, endTime);
				
				List<Integer> list = new ArrayList<Integer>();
				list.add(ResultNumber.TWO.getNumber());
				list.add(ResultNumber.FOUR.getNumber());
				criteria2.andStatusIn(list);
				
				onSaleExample.setLimitEnd(pageInfo.getSize() - onSaleProduct.size());
				onSaleExample.setOrderByClause(" id desc");
				
				List<Product> supplyProduct = productMapper.selectByExample(onSaleExample);
				
				for (Product product : supplyProduct) {
					onSaleProduct.add(product);
				}
				
			}
			pageInfo.setRows(onSaleProduct);
			pageInfo.setTotal(count);
			pageInfo.setCode(ResultInfo.SUCCESS.getCode());
			pageInfo.setMsg(ResultInfo.SUCCESS.getMsg());
		} else {
			pageInfo.setCode(ResultInfo.EMPTY_DATA.getCode());
			pageInfo.setMsg(ResultInfo.EMPTY_DATA.getMsg());
		}
	}

	/**
	 * PC端获取产品列表查询条件
	 * @param criteria
	 * @param cityCode
	 * @param pcId
	 * @param status
	 * @param startTime
	 * @param endTime
	 */
	private void pcHomeProductExample(Criteria criteria, String cityCode, Integer pcId, Integer startTime, Integer endTime) {
		criteria.andApprovalEqualTo(ResultNumber.TWO.getNumber());
		
		if (pcId != null) {
			criteria.andPcIdEqualTo(pcId);
		} else {
			criteria.andPcIdNotEqualTo(22).andPcIdNotEqualTo(17);
		}
		
		if (startTime != null && endTime != null) {
			criteria.andFTimeBetween(startTime, endTime);
		}
		
		if (cityCode != null) {
			criteria.andCityCodeEqualTo(cityCode);
		} else {
			criteria.andCityCodeEqualTo("000");
		}
	}

	@Override
	public Product queryBaseInfo(Integer proId) {
		Product product = productMapper.selectByPrimaryKey(proId);
		return product;
	}

}
