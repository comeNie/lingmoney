package com.mrbt.lingmoney.web2.controller.product;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.mrbt.lingmoney.bank.utils.DateUtils;
import com.mrbt.lingmoney.model.Product;
import com.mrbt.lingmoney.model.ProductDescVo;
import com.mrbt.lingmoney.model.TradingRecordCustomer;
import com.mrbt.lingmoney.service.product.ProductService;
import com.mrbt.lingmoney.utils.PageInfo;
import com.mrbt.lingmoney.utils.ResultParame;
import com.mrbt.lingmoney.utils.StringOpertion;
import com.mrbt.lingmoney.utils.ResultParame.ResultInfo;
import com.mrbt.lingmoney.utils.ResultParame.ResultNumber;
import com.mrbt.lingmoney.web2.vo.product.ProductVo;

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
	
	/**
     * 查询新手标产品
     * 
     * @author yiban
     * @date 2018年3月8日 下午3:50:52
     * @version 1.0
     * @param pageSize 每页条数
     * @param pageNo 页码
     * @return pageinfo pageinfo
     *
     */
    @RequestMapping(value = "/listNewerProduct", method = RequestMethod.POST)
    @ResponseBody
    public Object listNewerProduct(Integer pageSize, Integer pageNo) {
        PageInfo pageInfo = new PageInfo();
        try {
            pageInfo = productService.listNewerProduct(pageNo, pageSize);

        } catch (Exception e) {
            pageInfo.setResultInfo(ResultInfo.SERVER_ERROR);
            e.printStackTrace();
        }

        return pageInfo;
    }
	
	
	/**
	 * PC获取产品列表
	 * @description
	 * @param cityCode cityCode
	 * @return pageInfo
	 */
    @RequestMapping(value = "/productList", method = RequestMethod.POST)
    @ResponseBody
    public Object productList(String cityCode, Integer pageNo, Integer pageSize, Integer pcId, Integer status, Integer startTime,Integer endTime) {
    	LOG.info("首页产品 product/productList");
    	PageInfo pageInfo = new PageInfo();
        try {
        	pageInfo = new PageInfo(pageNo, pageSize);
        	productService.queryPcHomeProductList(pageInfo, cityCode, pcId, status, startTime, endTime);

        	if (pageInfo.getCode() == 200) {
				List<Product> productList = pageInfo.getRows();
				List<ProductVo> productVos = new ArrayList<ProductVo>();
				
				ProductVo productVo = new ProductVo();
				for (Product product : productList) {
					BeanUtils.copyProperties(product, productVo);
					productVos.add(productVo);
				}
				
				pageInfo.setRows(productVos);
			}
        	
        } catch (Exception e) {
        	LOG.error("PC获取产品列表失败" + e.getMessage());
			e.printStackTrace();
			pageInfo.setCode(ResultInfo.SERVER_ERROR.getCode());
			pageInfo.setMsg(ResultInfo.SERVER_ERROR.getMsg());
        }
        return pageInfo;
    }
    
    /**
	 * 通过id获取产品基本信息
	 * @description
	 * @param cityCode cityCode
	 * @return pageInfo
	 */
    @RequestMapping(value = "/productBaseInfo", method = RequestMethod.POST)
    @ResponseBody
    public Object productList(Integer proId) {
    	LOG.info("首页产品 product/productList");
    	PageInfo pageInfo = new PageInfo();
        try {
        	if (proId != null) {
        		Product productInfo = productService.queryBaseInfo(proId);
        		if (productInfo != null) {
        			ProductVo productVo = new ProductVo();
        			BeanUtils.copyProperties(productInfo, productVo);
        			pageInfo.setCode(ResultInfo.SUCCESS.getCode());
        			pageInfo.setMsg(ResultInfo.SUCCESS.getMsg());
        			pageInfo.setObj(productVo);
				} else {
					pageInfo.setCode(ResultInfo.EMPTY_DATA.getCode());
					pageInfo.setMsg(ResultInfo.EMPTY_DATA.getMsg());
				}
			} else {
				pageInfo.setCode(ResultInfo.PARAMS_ERROR.getCode());
				pageInfo.setMsg(ResultInfo.PARAMS_ERROR.getMsg());
			}
        } catch (Exception e) {
        	LOG.error("PC获取产品列表失败" + e.getMessage());
			e.printStackTrace();
			pageInfo.setCode(ResultInfo.SERVER_ERROR.getCode());
			pageInfo.setMsg(ResultInfo.SERVER_ERROR.getMsg());
        }
        return pageInfo;
    }

	/**
	 * 产品详情
	 * @description
	 * @param proId 产品ID
	 * @return pageInfo
	 */
	@RequestMapping(value = "/productDesc", method = RequestMethod.POST)
	public @ResponseBody Object productDesc(Integer proId, HttpServletResponse response) {
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
	 * @param response response
	 * @param proId 产品ID
	 * @return json
	 */
	@RequestMapping(value = "/productInfo", method = RequestMethod.POST)
    @ResponseBody
	public Object productInfo(Integer proId, HttpServletResponse response) {
		LOG.info("项目信息 product/productInfo");
		response.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=utf-8");
        PageInfo pi = new PageInfo();
		try {
			String productInfo = productService.selectProductInfo(proId, 0);
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
	 * 抵押物信息
	 * @description 仅限车贷宝
	 * @param proId proId
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
	 * 获取项目详情数据
	 * @author yiban
	 * @date 2018年3月19日 上午11:46:55
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
        LOG.info("单个产品理财记录 product/tradingRecordListVersionOne");
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
