package com.mrbt.lingmoney.wap.controller.product;

import java.text.DecimalFormat;
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
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.mrbt.lingmoney.bank.utils.DateUtils;
import com.mrbt.lingmoney.commons.cache.RedisGet;
import com.mrbt.lingmoney.model.ProductDescVo;
import com.mrbt.lingmoney.model.TradingRecordCustomer;
import com.mrbt.lingmoney.service.product.ProductService;
import com.mrbt.lingmoney.utils.PageInfo;
import com.mrbt.lingmoney.utils.ResultParame;
import com.mrbt.lingmoney.utils.StringOpertion;
import com.mrbt.lingmoney.utils.ResultParame.ResultInfo;
import com.mrbt.lingmoney.utils.ResultParame.ResultNumber;
import com.mrbt.lingmoney.wap.controller.common.CommonMethodUtil;

/**
 * 产品
 * 
 * @author lihq
 * @date 2017年4月14日 上午9:22:32
 * @description
 * @version 1.0
 * @since 2017-04-12
 */
@Controller
@RequestMapping(value = "/product")
public class ProductController {
	private static final Logger LOG = LogManager.getLogger(ProductController.class);
	@Autowired
	private ProductService productService;
	@Autowired
	private RedisGet redisGet;
	/**
	 * 首页推荐产品
	 * 
	 * @description
	 * @param token
	 *            token
	 * @param needLogin
	 *            是否登录：Y是，N否
	 * @param cityCode
	 *            cityCode
	 * @param type
	 *            客户端类型 0，ios 1，安卓
	 * @return pageInfo
	 */
	@RequestMapping(value = "/homeProduct", method = RequestMethod.POST)
	public @ResponseBody Object homeProduct(HttpServletRequest request, String cityCode, String needLogin, Integer type) {
		LOG.info("首页推荐产品 product/homeProduct");
		PageInfo pageInfo = new PageInfo();
		try {
			pageInfo = new PageInfo();
			if (StringUtils.isBlank(needLogin) || (!"N".equals(needLogin) && !"Y".equals(needLogin))
					|| !Arrays.asList(0, 1).contains(type)) {
				pageInfo.setCode(ResultInfo.PARAMETER_ERROR.getCode());
				pageInfo.setMsg(ResultInfo.PARAMETER_ERROR.getMsg());
				return pageInfo;
			}
			String key = "";
			if ("Y".equals(needLogin)) {
			    key = CommonMethodUtil.getRedisUidKeyByRequest(request);
                if (StringUtils.isBlank(key) || redisGet.getRedisStringResult(key) == null) {
                    pageInfo.setCode(ResultInfo.LOGIN_FAILURE.getCode());
                    pageInfo.setMsg(ResultInfo.LOGIN_FAILURE.getMsg());
                    return pageInfo;
                }
			}
			pageInfo = productService.selectHomeProduct(key, cityCode, needLogin, type);

		} catch (Exception e) {
			LOG.error("首页推荐产品查询失败" + e.getMessage());
			e.printStackTrace();
			pageInfo.setCode(ResultInfo.SERVER_ERROR.getCode());
			pageInfo.setMsg(ResultInfo.SERVER_ERROR.getMsg());
		}
		return pageInfo;
	}

	/**
	 * 产品列表
	 * 
	 * @description
	 * @param proId 
	 *            proId
	 * @param status 
	 *            status
	 * @param cityCode
	 *            cityCode
	 * @param pageNo
	 *            pageNo
	 * @param pageSize
	 *            pageSize
	 * @return pageInfo
	 */
	@RequestMapping(value = "/productList", method = RequestMethod.POST)
	public @ResponseBody Object productList(Integer proId, Integer status, String cityCode, Integer pageNo,
			Integer pageSize) {
		LOG.info("产品列表 product/productList");
		PageInfo pageInfo = null;
		try {
			pageInfo = new PageInfo(pageNo, pageSize);
			productService.selectProductList(proId, pageInfo, status, cityCode);
		} catch (Exception e) {
			LOG.error("产品列表查询失败" + e.getMessage());
			e.printStackTrace();
			pageInfo = new PageInfo();
			pageInfo.setCode(ResultInfo.SERVER_ERROR.getCode());
			pageInfo.setMsg(ResultInfo.SERVER_ERROR.getMsg());
		}
		return pageInfo;
	}

	/**
	 * 产品详情
	 * 
	 * @description
	 * @param response
	 *           response
	 * @param proId
	 *           proId
	 * @param modelMap
	 *           modelMap
	 * @return pageInfo
	 */
	@RequestMapping(value = "/productDesc", method = RequestMethod.POST)
	public @ResponseBody Object productDesc(Integer proId, HttpServletResponse response, ModelMap modelMap) {
		LOG.info("产品详情 product/productDesc");
		PageInfo pageInfo = null;
		try {
			pageInfo = new PageInfo();
			if (proId == null) {
				pageInfo.setCode(ResultInfo.PARAMETER_ERROR.getCode());
				pageInfo.setMsg(ResultInfo.PARAMETER_ERROR.getMsg());
				return pageInfo;
			}
			List<ProductDescVo> productDesc = productService.selectProductDesc(proId);
			if (productDesc == null || productDesc.size() < ResultNumber.ONE.getNumber()) {
				pageInfo.setCode(ResultInfo.NO_DATA.getCode());
				pageInfo.setMsg(ResultInfo.NO_DATA.getMsg());
				return pageInfo;
			}
			pageInfo.setCode(ResultInfo.SUCCESS.getCode());
			pageInfo.setMsg(ResultInfo.SUCCESS.getMsg());
			pageInfo.setObj(productDesc);
		} catch (Exception e) {
			LOG.error("产品详情查询失败" + e.getMessage());
			e.printStackTrace();
			pageInfo = new PageInfo();
			pageInfo.setCode(ResultInfo.SERVER_ERROR.getCode());
			pageInfo.setMsg(ResultInfo.SERVER_ERROR.getMsg());
		}
		return pageInfo;
	}

	/**
	 * 项目信息
	 * 
	 * @description
	 * @param response
	 *           response
	 * @param proId
	 *           proId
	 * @param modelMap
	 *           modelMap
	 * @return string
	 */
	@RequestMapping(value = "/productInfo", method = RequestMethod.POST)
    @ResponseBody
	public Object productInfo(Integer proId, HttpServletResponse response, ModelMap modelMap) {
		LOG.info("项目信息 product/productInfo");
		response.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=utf-8");
        PageInfo pi = new PageInfo();
		try {
			String productInfo = productService.selectProductInfo(proId, 1);
            if (StringUtils.isBlank(productInfo)) {
                pi.setResultInfo(ResultInfo.NO_DATA);

            } else {
                pi.setResultInfo(ResultInfo.SUCCESS);
                pi.setObj(productInfo);
            }
		} catch (Exception e) {
			LOG.error("产品项目信息查询失败" + e.getMessage());
			e.printStackTrace();
		}
        return pi;
	}

	/**
	 * 产品图片
	 * 
	 * @description 仅限车贷宝
	 * @param proId 
	 *           proId
	 * @return pageInfo
	 */
	@RequestMapping(value = "/productPic", method = RequestMethod.POST)
	public @ResponseBody Object productPic(Integer proId) {
		LOG.info("产品图片 product/productPic");
		PageInfo pageInfo = null;
		try {
			pageInfo = new PageInfo();
			if (proId == null) {
				pageInfo.setCode(ResultInfo.PARAMETER_ERROR.getCode());
				pageInfo.setMsg(ResultInfo.PARAMETER_ERROR.getMsg());
				return pageInfo;
			}
			List<String> list = productService.selectProductPic(proId);
			if (list == null || list.size() < ResultNumber.ONE.getNumber()) {
				pageInfo.setCode(ResultInfo.NO_DATA.getCode());
				pageInfo.setMsg(ResultInfo.NO_DATA.getMsg());
				return pageInfo;
			}
			pageInfo.setCode(ResultInfo.SUCCESS.getCode());
			pageInfo.setMsg(ResultInfo.SUCCESS.getMsg());
			pageInfo.setObj(list);
		} catch (Exception e) {
			LOG.error("产品图片查询失败" + e.getMessage());
			e.printStackTrace();
			pageInfo = new PageInfo();
			pageInfo.setCode(ResultInfo.SERVER_ERROR.getCode());
			pageInfo.setMsg(ResultInfo.SERVER_ERROR.getMsg());
		}
		return pageInfo;
	}

	/**
	 * 单个产品理财记录
	 * 
	 * @description
	 * @param proId
	 *           proId
	 * @param pageNo
	 *           pageNo
	 * @param pageSize
	 *           pageSize
	 * @return pageInfo
	 */
	@RequestMapping(value = "/tradingRecordList", method = RequestMethod.POST)
	public @ResponseBody Object tradingRecordList(Integer proId, Integer pageNo, Integer pageSize) {
		LOG.info("单个产品理财记录 product/tradingRecordList");
		PageInfo pageInfo = null;
		try {
			pageInfo = new PageInfo(pageNo, pageSize);
			if (proId == null) {
				pageInfo.setCode(ResultInfo.PARAMETER_ERROR.getCode());
				pageInfo.setMsg(ResultInfo.PARAMETER_ERROR.getMsg());
				return pageInfo;
			}
			productService.selectTradingRecordList(proId, pageInfo);
		} catch (Exception e) {
			LOG.error("单个产品理财记录查询失败" + e.getMessage());
			e.printStackTrace();
			pageInfo = new PageInfo();
			pageInfo.setCode(ResultInfo.SERVER_ERROR.getCode());
			pageInfo.setMsg(ResultInfo.SERVER_ERROR.getMsg());
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
    public @ResponseBody Object queryProductByBatch(String batch, Integer size, HttpServletResponse response) {
		
		JSONObject jsonObject = new JSONObject();
		response.setHeader("Access-Control-Allow-Origin", "*");
		try {
			if (StringUtils.isBlank(batch)) {
				jsonObject.put("code", ResultParame.ResultInfo.PARAMS_ERROR.getCode());
				jsonObject.put("msg", ResultParame.ResultInfo.PARAMS_ERROR.getMsg());
				return jsonObject;
			}
			if (size == 0) {
				size = 1;
			}
			List<Map<String, Object>> resMap = productService.queryProductByBatch(batch, size);

			if (resMap == null || resMap.size() < 1) {
				jsonObject.put("code", ResultParame.ResultInfo.NOT_FOUND_DATA.getCode());
				jsonObject.put("msg", ResultParame.ResultInfo.NOT_FOUND_DATA.getMsg());
				return jsonObject;
			}

			jsonObject.put("code", ResultParame.ResultInfo.SUCCESS.getCode());
			jsonObject.put("msg", ResultParame.ResultInfo.SUCCESS.getMsg());
			jsonObject.put("obj", resMap);

		} catch (Exception e) {
			e.printStackTrace();
			jsonObject.put("code", ResultParame.ResultInfo.SERVER_ERROR.getCode());
			jsonObject.put("msg", ResultParame.ResultInfo.SERVER_ERROR.getMsg());
		}

		return jsonObject;
	}
	/**
	 * 查询产品分类
	 * 
	 * @return 返回信息
	 */
	@RequestMapping(value = "/queryProductType", method = RequestMethod.POST)
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

	/**
	 * 跳转项目详情页面
	 * 
	 * @author yiban
	 * @date 2018年3月12日 上午9:13:48
	 * @version 1.0
	 * @param pId
	 *            产品id
	 * @return pageinfo （项目详情、借款人信息、常见问题）
	 *
	 */
	@RequestMapping(value = "/getProductDetailInfo")
	public String getProductDetailInfo(Integer pId, Model model) {
		model.addAttribute("pid", pId);
		return "productDetailInfo";
	}

	/**
	 * 获取项目详情数据
	 * 
	 * @author yiban
	 * @date 2018年3月19日 上午11:46:55
	 * @version 1.0
	 * @param pId
	 * @return
	 *
	 */
	@RequestMapping("/productDetailInfoData")
	@ResponseBody
	public Object productDetailInfoData(Integer pId) {
		PageInfo pageInfo = new PageInfo();
		JSONObject json = new JSONObject();
		try {
			pageInfo = productService.getProductDetailInfo(pId);
			json.put("obj", pageInfo.getObj());
		} catch (Exception e) {
			pageInfo.setResultInfo(ResultInfo.SERVER_ERROR);
			e.printStackTrace();
		}

		json.put("code", pageInfo.getCode());
		json.put("msg", pageInfo.getMsg());
		return json;
	}
	
	/**
     * 单个产品理财记录
     * 
     * @description
     * @param proId
     *           proId
     * @param pageNo
     *           pageNo
     * @param pageSize
     *           pageSize
     * @return pageInfo
     */
    @RequestMapping(value = "/tradingRecordListVersionOne")
    public @ResponseBody Object tradingRecordListVersionOne(Integer proId, Integer pageNo, Integer pageSize) {
        LOG.info("单个产品理财记录 product/tradingRecordList");
        JSONObject json = new JSONObject();

        PageInfo pageInfo = null;
        try {
            pageInfo = new PageInfo(pageNo, pageSize);
            if (proId == null) {
                pageInfo.setCode(ResultInfo.PARAMETER_ERROR.getCode());
                pageInfo.setMsg(ResultInfo.PARAMETER_ERROR.getMsg());
                return pageInfo;
            }
            productService.selectTradingRecordList(proId, pageInfo);

            // 隐私数据处理
            if (pageInfo.getCode() == ResultNumber.TWO_HUNDRED.getNumber()) {
                List<TradingRecordCustomer> list = pageInfo.getRows();
                DecimalFormat df = new DecimalFormat("#,##0.00");
                List<Map<String, String>> resultList = new ArrayList<Map<String, String>>();
                for (TradingRecordCustomer trading : list) {
                    Map<String, String> resultMap = new HashMap<String, String>();
                    resultMap.put("name", StringOpertion.hideName(trading.getUsersName()));
                    resultMap.put("date", DateUtils.sft.format(trading.getBuyDt()));
                    resultMap.put("money", df.format(trading.getFinancialMoney().doubleValue()));
                    resultList.add(resultMap);
                }
                json.put("rows", resultList);
            }

        } catch (Exception e) {
            LOG.error("单个产品理财记录查询失败" + e.getMessage());
            e.printStackTrace();
            pageInfo = new PageInfo();
            pageInfo.setCode(ResultInfo.SERVER_ERROR.getCode());
            pageInfo.setMsg(ResultInfo.SERVER_ERROR.getMsg());
        }

        json.put("code", pageInfo.getCode());
        json.put("msg", pageInfo.getMsg());
        return json;
    }
}
