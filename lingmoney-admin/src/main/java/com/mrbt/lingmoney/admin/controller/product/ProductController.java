package com.mrbt.lingmoney.admin.controller.product;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mrbt.lingmoney.admin.controller.base.BaseController;
import com.mrbt.lingmoney.admin.service.product.ProductService;
import com.mrbt.lingmoney.model.Product;
import com.mrbt.lingmoney.model.ProductWithBLOBs;
import com.mrbt.lingmoney.utils.AppCons;
import com.mrbt.lingmoney.utils.PageInfo;
import com.mrbt.lingmoney.utils.ResponseUtils;
import com.mrbt.lingmoney.utils.ResultParame;
import com.mrbt.lingmoney.utils.ResultParame.ResultInfo;
import com.mrbt.lingmoney.utils.ResultParame.ResultNumber;

/**
 * 产品设置——》产品表
 * 
 * @date 2017年6月7日 下午3:11:14
 * @description
 * @version 1.0
 * @since 2017-04-12
 */
@Controller
@RequestMapping("product/product")
public class ProductController extends BaseController {

	@Autowired
	private ProductService productService;

	/**
	 * 查找固定类产品区间
	 * @param	record	record
	 * @return		return
	 */
	@RequestMapping("listFixArea")
	@ResponseBody
	public Object listFixArea(Product record) {
		return productService.listType(AppCons.FIX_FLAG, record);
	}

	/**
	 * 分页查询
	 * 
	 * @param vo 实体类
	 * @param page  当前页
	 * @param rows 每页显示的条数 
	 * @param q q
	 * @return 分页实体类
	 */
	@RequestMapping("list")
	@ResponseBody
	public Object list(Product vo, Integer page, Integer rows, String q) {
		if (StringUtils.isNotBlank(q)) {
			vo.setName(q);
		}
		PageInfo pageInfo = new PageInfo(page, rows);
		try {
			pageInfo = productService.getList(vo, pageInfo);
		} catch (Exception e) {
			e.printStackTrace();
			pageInfo.setCode(ResultInfo.SERVER_ERROR.getCode());
			pageInfo.setMsg(ResultInfo.SERVER_ERROR.getMsg());
		}
		return pageInfo;
	}

	/**
	 * 保存
	 * @param vo	封装
	 * @param isCopy 1 添加 2 修改 3 复制
	 * @return	结果
	 */
	@RequestMapping("saveAndUpdate")
	@ResponseBody
	public Object saveAndUpdate(ProductWithBLOBs vo, Integer isCopy) {
		PageInfo pageInfo = new PageInfo();

		try {
			if (isCopy == ResultNumber.ONE.getNumber()) {
				productService.save(vo, pageInfo);
			}
			if (isCopy == ResultNumber.TWO.getNumber()) {
				productService.update(vo, pageInfo);
			}
			if (isCopy == ResultNumber.THREE.getNumber()) {
				productService.copy(vo, pageInfo);
			}
		} catch (Exception e) {
			e.printStackTrace();
			pageInfo.setCode(ResultInfo.SERVER_ERROR.getCode());
			pageInfo.setMsg(ResultInfo.SERVER_ERROR.getMsg());
		}
		return pageInfo;
	}

	/**
	 * 产品详情等信息
	 * 
	 * @param id	id
	 * @return	return
	 */
	@RequestMapping("getBlobContent")
	@ResponseBody
	public Object getBlobContent(String id) {
		if (StringUtils.isNotBlank(id) && NumberUtils.isNumber(id)) {
			List<String> list = productService.getBlobContent(NumberUtils.toInt(id));
			if (list != null && list.size() > 0) {
				return ResponseUtils.success(list);
			} else {
				return ResponseUtils.failure(ResponseUtils.ERROR_PARAM, "没有查询到数据");
			}

		} else {
			return ResponseUtils.failure(ResponseUtils.ERROR_PARAM, "参数错误");
		}
	}

	/**
	 * 删除
	 * 
	 * @param id	id
	 * @return	retrun
	 */
	@RequestMapping("delete")
	@ResponseBody
	public Object delete(String id) {
		try {
			if (StringUtils.isNotBlank(id) && NumberUtils.isNumber(id) && NumberUtils.toInt(id) > 0) {
                int res = productService.deleteProduct(NumberUtils.toInt(id));
				if (res > 0) {
					return ResponseUtils.success();
				}

                if (res == ResultParame.ResultNumber.MINUS3.getNumber()) {
                    return ResponseUtils.failure(ResponseUtils.ERROR_PARAM, "已发标产品不能删除");
                }

				if (res < 0) {
					return ResponseUtils.failure(ResponseUtils.ERROR_PARAM, "已经审批过的产品不能删除");
				}

				return ResponseUtils.failure(ResponseUtils.ERROR_PARAM, "数据不存在");

			} else {
				return ResponseUtils.failure(ResponseUtils.ERROR_PARAM, "参数错误");
			}

		} catch (Exception ex) {
			ex.printStackTrace();
			return ResponseUtils.failure(ResponseUtils.ERROR_SERVER, ex.getMessage());
		}

	}

	/**
	 * 更新审批状态
	 * @param session	session
	 * @param id	id
	 * @param request	request
	 * @return	return
	 */
	@RequestMapping("updateApproval")
	@ResponseBody
	public Object updateApproval(HttpSession session, String id, HttpServletRequest request) {
		String errorInfo = "";
		try {
			if (StringUtils.isNotBlank(id) && NumberUtils.isNumber(id) && NumberUtils.toInt(id) > 0) {
				int res = productService.updateApproval(NumberUtils.toInt(id), session, request);

				if (res == 1) {
					return ResponseUtils.success();
				}
				if (res == 0) {
					errorInfo = "数据不存在";
				}

				if (res < 0) {
					errorInfo = "非初始化的产品";
				}

			} else {
				errorInfo = "参数错误";
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			return ResponseUtils.failure(ResponseUtils.ERROR_SERVER, ex.getMessage());
		}
		return ResponseUtils.failure(ResponseUtils.ERROR_PARAM, errorInfo);
	}

	/**
	 * 项目成立
	 * @param id	id
	 * @return	return
	 */
	@RequestMapping("raise")
	@ResponseBody
	public Object raise(String id) {
		try {
			if (id != null && Integer.parseInt(id) > 0) {
				int res = productService.raiseOpertion(NumberUtils.toInt(id));
				if (res > 0) {
					return ResponseUtils.success();
				} else {
					return ResponseUtils.failure(ResponseUtils.ERROR_PARAM, "操作失败");
				}
			} else {
				return ResponseUtils.failure(ResponseUtils.ERROR_PARAM, "参数错误");
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			return ResponseUtils.failure(ResponseUtils.ERROR_SERVER, ex.getMessage());
		}
	}

	/**
	 * 查询产品推荐表可用产品
	 * @param page	页数
	 * @param rows	条数
	 * @return	return
	 */
	@RequestMapping("productList")
	public @ResponseBody Object productList(Integer page, Integer rows) {
		PageInfo pageInfo = new PageInfo(page, rows);
		try {
			productService.selectProductList(pageInfo);
		} catch (Exception e) {
			e.printStackTrace();
			pageInfo.setCode(ResultInfo.SERVER_ERROR.getCode());
			pageInfo.setMsg(ResultInfo.SERVER_ERROR.getMsg());
		}
		return pageInfo;
	}

	/**
	 * 修改大字段
	 * 
	 * @param pId	产品ID
	 * @param vo	封装
	 * @param type	类型
	 * @return	return
	 */
	@RequestMapping("saveDetail")
	@ResponseBody
	public Object saveDetail(Integer pId, ProductWithBLOBs vo, Integer type) {
		PageInfo pageInfo = new PageInfo();
		try {
			if (pId != null && pId > 0 && type != null && vo != null) {
				productService.saveDetail(pId, vo, type, pageInfo);
				pageInfo.setCode(ResultInfo.SUCCESS.getCode());
				pageInfo.setMsg(ResultInfo.SUCCESS.getMsg());
			} else {
				pageInfo.setCode(ResultInfo.PARAMETER_ERROR.getCode());
				pageInfo.setMsg(ResultInfo.PARAMETER_ERROR.getMsg());
			}
		} catch (Exception e) {
			e.printStackTrace();
			pageInfo.setCode(ResultInfo.SERVER_ERROR.getCode());
			pageInfo.setMsg(ResultInfo.SERVER_ERROR.getMsg());
		}
		return pageInfo;
	}

	/**
	 * 修改产品详情
	 * @param id	id
	 * @param title1	标题1
	 * @param content1	内容1
	 * @param title2	标题2
	 * @param content2	内容2
	 * @param title3	标题3
	 * @param content3	内容3
	 * @param title4	标题4
	 * @param content4	内容4
	 * @param title5	标题5
	 * @param content5	内容5
	 * @param title6	标题6
	 * @param content6	内容6
	 * @param title7	标题7
	 * @param content7	内容7
	 * @param title8	标题8
	 * @param content8	内容8
	 * @param description	描述
	 * @param insertType	类型
	 *            录入类型 0分块 1全部
	 * @return	返回处理结果
	 */
	@RequestMapping("saveDesc")
	@ResponseBody
	public Object saveDesc(Integer id, String title1, String content1, String title2, String content2, String title3,
			String content3, String title4, String content4, String title5, String content5, String title6,
			String content6, String title7, String content7, String title8, String content8, String description,
			Integer insertType) {
		PageInfo pageInfo = new PageInfo();
		try {
			if (id != null && id > 0) {
				productService.saveDesc(id, title1, content1, title2, content2, title3, content3, title4, content4,
						title5, content5, title6, content6, title7, content7, title8, content8, description,
						insertType);
				pageInfo.setCode(ResultInfo.SUCCESS.getCode());
				pageInfo.setMsg(ResultInfo.SUCCESS.getMsg());
			} else {
				pageInfo.setCode(ResultInfo.PARAMETER_ERROR.getCode());
				pageInfo.setMsg(ResultInfo.PARAMETER_ERROR.getMsg());
			}
		} catch (Exception e) {
			e.printStackTrace();
			pageInfo.setCode(ResultInfo.SERVER_ERROR.getCode());
			pageInfo.setMsg(ResultInfo.SERVER_ERROR.getMsg());
		}
		return pageInfo;
	}

	/**
	 * 查询兑付中的自动还款授权产品
	 * @param pageNo	页数
	 * @param pageSize	条数
	 * @return	结果
	 */
	@RequestMapping("listAutoRepaymentProduct")
	public @ResponseBody Object listAutoRepaymentProduct(Integer pageNo, Integer pageSize) {
		PageInfo pageInfo = new PageInfo();

		try {
			pageInfo = productService.listAutoRepaymentProduct(pageNo, pageSize);
		} catch (Exception e) {
			pageInfo.setCode(ResultInfo.SERVER_ERROR.getCode());
			pageInfo.setMsg(ResultInfo.SERVER_ERROR.getMsg());
			e.printStackTrace();
		}

		return pageInfo;
	}

	/**
	 * 产品自动还款确认
	 * 
	 * @param productIds  产品id列表
	 * @param type 状态更新
	 * @return	return
	 */
	@RequestMapping("confirmAutoRepayment")
	public @ResponseBody Object confirmAutoRepayment(String productIds, Integer type) {
		PageInfo pageInfo = new PageInfo();

		try {
			pageInfo = productService.productAutoRepaymentConfirm(productIds, type);
		} catch (Exception e) {
			pageInfo.setCode(ResultInfo.SERVER_ERROR.getCode());
			pageInfo.setMsg(ResultInfo.SERVER_ERROR.getMsg());

			e.printStackTrace();
		}

		return pageInfo;
	}

   /**
    * 设置产品排序
    * @param id	产品ID	
    * @param sort	序号
    * @return	return
    */
    @RequestMapping("sortProduct")
    public @ResponseBody Object sortProduct(Integer id, Byte sort) {
        PageInfo pageInfo = new PageInfo();

        try {
        	pageInfo = productService.sortProduct(id, sort);
        } catch (Exception e) {
        	e.printStackTrace();
        	pageInfo.setCode(ResultInfo.SERVER_ERROR.getCode());
			pageInfo.setMsg(ResultInfo.SERVER_ERROR.getMsg());
        }
        return pageInfo;
    }

	/**
	 * 产品募集列表
	 */
	@RequestMapping("productEnterprise")
	@ResponseBody
	public Object productEnterpriseList() {
		PageInfo pageInfo = new PageInfo();
		try {
			pageInfo = productService.festivalBuyDetailList();
		} catch (Exception e) {
			e.printStackTrace();
			pageInfo.setCode(ResultInfo.SERVER_ERROR.getCode());
			pageInfo.setMsg(ResultInfo.SERVER_ERROR.getMsg());
		}
		return pageInfo;
	}

    @RequestMapping(value = "/updateProductDetailInfo", method = RequestMethod.POST)
    @ResponseBody
    public Object updateProductDetailInfo(Integer id, String productDetailLonnerInfo, String productDetailNormalQues) {
        PageInfo pageInfo = new PageInfo();
        pageInfo = productService.updateProductDetailInfo(id, productDetailLonnerInfo, productDetailNormalQues);

        return pageInfo;
    }

    /**
     * 查询项目详情
     * 
     * @author yiban
     * @date 2018年3月12日 上午9:13:48
     * @version 1.0
     * @param pId 产品id
     * @return pageinfo （项目详情、借款人信息、常见问题）
     *
     */
    @RequestMapping(value = "/getProductDetailInfo", method = RequestMethod.POST)
    @ResponseBody
    public Object getProductDetailInfo(Integer pId) {
        PageInfo pageInfo = new PageInfo();

        try {
            pageInfo = productService.getProductDetailInfo(pId);
        } catch (Exception e) {
            pageInfo.setResultInfo(ResultInfo.SERVER_ERROR);
            e.printStackTrace();
        }

        return pageInfo;
    }
}
