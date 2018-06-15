package com.mrbt.lingmoney.admin.controller.product;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.mrbt.lingmoney.admin.service.product.ProductRecommendService;
import com.mrbt.lingmoney.model.ProductRecommend;
import com.mrbt.lingmoney.model.ProductRecommendCustomer;
import com.mrbt.lingmoney.utils.MyUtils;
import com.mrbt.lingmoney.utils.PageInfo;
import com.mrbt.lingmoney.utils.ResultParame.ResultInfo;
import com.mrbt.lingmoney.utils.ResultParame.ResultNumber;

/**
 * 产品设置——》产品推荐设置
 * 
 * @author lihq
 * @date 2017年5月22日 下午3:09:17
 * @description
 * @version 1.0
 * @since 2017-04-12
 */
@Controller
@RequestMapping("/product/recommend")
public class ProductRecommendController {
	private Logger log = MyUtils.getLogger(ProductRecommendController.class);
	@Autowired
	private ProductRecommendService productRecommendService;
	/**
	 * 图片保存的根目录
	 */
	private String indexPic = "recommend";

	/**
	 * 删除
	 * 
	 * @param id
	 *            主键
	 * @return 分页实体类
	 */
	@RequestMapping("delete")
	@ResponseBody
	public Object delete(Integer id) {
		log.info("/product/recommend/delete");
		PageInfo pageInfo = new PageInfo();
		try {
			if (id != null && id > 0) {
				productRecommendService.delete(id);
				pageInfo.setCode(ResultInfo.SUCCESS.getCode());
				pageInfo.setMsg(ResultInfo.SUCCESS.getMsg());
			} else {
				pageInfo.setCode(ResultInfo.EMPTY_DATA.getCode());
				pageInfo.setMsg(ResultInfo.EMPTY_DATA.getMsg());
			}
		} catch (Exception e) {
			log.error(e.getMessage());
			e.printStackTrace();
			pageInfo.setCode(ResultInfo.SERVER_ERROR.getCode());
			pageInfo.setMsg(ResultInfo.SERVER_ERROR.getMsg());
		}
		return pageInfo;
	}

	/**
	 * 查询
	 * @param vo	封装
	 * @param page	页数
	 * @param rows	条数
	 * @return	return
	 */
	@RequestMapping("list")
	@ResponseBody
	public Object list(ProductRecommendCustomer vo, Integer page, Integer rows) {
		log.info("/product/recommend/list");
		PageInfo pageInfo = new PageInfo(page, rows);
		try {
			pageInfo = productRecommendService.getList(vo, pageInfo);
		} catch (Exception e) {
			e.printStackTrace();
			pageInfo.setCode(ResultInfo.SERVER_ERROR.getCode());
			pageInfo.setMsg(ResultInfo.SERVER_ERROR.getMsg());
		}
		return pageInfo;
	}

	/**
	 * 添加和修改
	 * @param vo	封装
	 * @param request	request
	 * @return	结果
	 */
	@RequestMapping("saveAndUpdate")
	@ResponseBody
	public Object saveAndUpdate(ProductRecommend vo, MultipartHttpServletRequest request) {
		log.info("/product/recommend/saveAndUpdate");
		PageInfo pageInfo = new PageInfo();
		try {
			MultipartFile file1 = request.getFile("titleFile");
			MultipartFile file2 = request.getFile("activityFile");
			if (vo.getId() == null) {
				int a = productRecommendService.save(vo, file1, file2, indexPic);
				if (a == ResultNumber.MINUS2.getNumber()) {
					pageInfo.setCode(ResultInfo.MODIFY_REJECT.getCode());
					pageInfo.setMsg("该产品已推荐，请勿重复推荐");
					return pageInfo;
				}
			} else {
				int b = productRecommendService.update(vo, file1, file2, indexPic);
				if (b == ResultNumber.MINUS2.getNumber()) {
					pageInfo.setCode(ResultInfo.MODIFY_REJECT.getCode());
					pageInfo.setMsg("该产品已推荐，请勿重复推荐");
					return pageInfo;
				}
			}
			pageInfo.setCode(ResultInfo.SUCCESS.getCode());
			pageInfo.setMsg(ResultInfo.SUCCESS.getMsg());

		} catch (Exception e) {
			log.error(e.getMessage());
			e.printStackTrace();
			pageInfo.setCode(ResultInfo.SERVER_ERROR.getCode());
			pageInfo.setMsg(ResultInfo.SERVER_ERROR.getMsg());
		}
		return pageInfo;
	}

	/**
	 * 刷新redis
	 * @return	处理结果
	 */
	@RequestMapping("reload")
	@ResponseBody
	public Object reload() {
		log.info("/product/recommend/reload");
		PageInfo pageInfo = new PageInfo();
		try {
			productRecommendService.reload();
			pageInfo.setCode(ResultInfo.SUCCESS.getCode());
			pageInfo.setMsg(ResultInfo.SUCCESS.getMsg());
		} catch (Exception e) {
			log.error(e.getMessage());
			e.printStackTrace();
			pageInfo.setCode(ResultInfo.SERVER_ERROR.getCode());
			pageInfo.setMsg(ResultInfo.SERVER_ERROR.getMsg());
		}
		return pageInfo;
	}
}
