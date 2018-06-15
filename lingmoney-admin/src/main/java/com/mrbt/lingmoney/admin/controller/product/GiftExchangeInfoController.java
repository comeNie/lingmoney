package com.mrbt.lingmoney.admin.controller.product;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mrbt.lingmoney.admin.controller.base.BaseController;
import com.mrbt.lingmoney.admin.service.product.GiftExchangeInfoService;
import com.mrbt.lingmoney.model.GiftExchangeInfo;
import com.mrbt.lingmoney.model.GiftExchangeInfoNew;
import com.mrbt.lingmoney.model.GiftExchangeInfoVo;
import com.mrbt.lingmoney.utils.MyUtils;
import com.mrbt.lingmoney.utils.PageInfo;
import com.mrbt.lingmoney.utils.ResultParame.ResultInfo;

/**
 * 产品设置——》拉新活动礼品兑换信息
 * 
 * @author Administrator
 * @date 2017年4月10日 下午3:08:36
 * @description
 * @since
 */
@Controller
@RequestMapping("/product/giftExchangeInfo")
public class GiftExchangeInfoController extends BaseController {

	private Logger log = MyUtils.getLogger(GiftExchangeInfoController.class);

	@Autowired
	private GiftExchangeInfoService giftExchangeInfoService;

	/**
	 * 根据条件查询礼品兑换信息
	 * 
	 * @param vo
	 *            礼品兑换信息
	 * @param page
	 *            当前页
	 * @param rows
	 *            每页显示的记录数
	 * @param regUser
	 *            是否为注册用户 0是 1否
	 * @param category
	 *            类别 0拉新活动 1新吉粉活动
	 * @return 分页实体类
	 */
	@RequestMapping("list")
	@ResponseBody
	public Object list(GiftExchangeInfoVo vo, Integer page, Integer rows, Integer regUser, Integer category) {
		PageInfo pageInfo = new PageInfo(page, rows);
		Map<String, Object> condition;
		try {
			condition = new HashMap<String, Object>();
			if (vo != null) {
				// 是否为注册用户 0是 1否
				if (regUser != null && regUser == 0) {
					condition.put("regUser", regUser);
				}
				// 类别 0拉新活动 1新吉粉活动
				if (category != null) {
					condition.put("category", category);
				}
				// 获奖用户手机号
				if (StringUtils.isNotBlank(vo.getTelephone())) {
					condition.put("telephone", vo.getTelephone());
				}
				// 获奖用户姓名
				if (StringUtils.isNotBlank(vo.getName())) {
					condition.put("name", vo.getName());
				}
				// 获奖用户推荐码
				if (StringUtils.isNotBlank(vo.getReferralCode())) {
					condition.put("referralCode", vo.getReferralCode());
				}
				// 获奖用户id
                if (StringUtils.isNotBlank(vo.getReferralId())) {
					condition.put("referralId", vo.getReferralId());
				}
				// 推荐的用户id
                if (StringUtils.isNotBlank(vo.getuId())) {
					condition.put("uId", vo.getuId());
				}
				// 礼品状态
				if (vo.getStatus() != null) {
					condition.put("status", vo.getStatus());
				}
				// 礼品类型
				if (vo.getType() != null) {
					condition.put("type", vo.getType());
				}
				// 活动名称
				if (StringUtils.isNotBlank(vo.getActivityName())) {
					condition.put("activityName", vo.getActivityName());
				}
				// 快递单号
				if (StringUtils.isNotBlank(vo.getExpressNumber())) {
					condition.put("expressNumber", vo.getExpressNumber());
				}
				// 快递公司
				if (StringUtils.isNotBlank(vo.getExpressCompany())) {
					condition.put("expressCompany", vo.getExpressCompany());
				}
			}

			pageInfo.setCondition(condition);
			giftExchangeInfoService.findDataGrid(pageInfo);
		} catch (Exception e) {
			e.printStackTrace();
			log.error(" 拉新活动礼品兑换信息，根据条件查询礼品兑换信息，失败原因是：" + e);
			pageInfo.setCode(ResultInfo.SERVER_ERROR.getCode());
			pageInfo.setMsg(ResultInfo.SERVER_ERROR.getMsg());
		}
		return pageInfo;
	}

	/**
	 * 发货
	 * 
	 * @param vo
	 *            礼品兑换信息
	 * @return 分页实体类
	 */
	@RequestMapping("processingDelivery")
	@ResponseBody
	public Object processingDelivery(GiftExchangeInfo vo) {
		PageInfo pageInfo = new PageInfo();
		try {
			pageInfo = giftExchangeInfoService.processingDelivery(vo);
			pageInfo.setCode(ResultInfo.SUCCESS.getCode());
			pageInfo.setMsg(ResultInfo.SUCCESS.getMsg());
		} catch (Exception e) {
			e.printStackTrace();
			log.error("拉新活动礼品兑换信息，发货，失败原因是：" + e);
			pageInfo.setCode(ResultInfo.SERVER_ERROR.getCode());
			pageInfo.setMsg(ResultInfo.SERVER_ERROR.getMsg());
		}
		return pageInfo;
	}

	/**
	 * 确认收货
	 * @param ids	ids
	 * @return	return
	 */
	@RequestMapping("comfirmReceipt")
	@ResponseBody
	public Object comfirmReceipt(String ids) {
		PageInfo pageInfo = new PageInfo();
		try {
			pageInfo = giftExchangeInfoService.comfirmReceipt(ids);
			pageInfo.setCode(ResultInfo.SUCCESS.getCode());
			pageInfo.setMsg(ResultInfo.SUCCESS.getMsg());
		} catch (Exception e) {
			e.printStackTrace();
			log.error("拉新活动礼品兑换信息，确认收货，失败原因是：" + e);
			pageInfo.setCode(ResultInfo.SERVER_ERROR.getCode());
			pageInfo.setMsg(ResultInfo.SERVER_ERROR.getMsg());
		}
		return pageInfo;
	}

	/**
	 * 导出到excel
	 * 
	 * @description
	 * @param request	request
	 * @param response	response
	 * @param json 后台查询后的rows组成的json串
	 * @throws IOException	IOException
	 */
	@RequestMapping("exportExcel")
	@ResponseBody
	public void exportExcel(HttpServletRequest request, HttpServletResponse response, String json) throws IOException {
		PageInfo pageInfo = new PageInfo();
		try {
			pageInfo = giftExchangeInfoService.exportExcel(request, response, json);
			if (pageInfo.getCode() == ResultInfo.SUCCESS.getCode()) {
				response.sendRedirect(pageInfo.getObj().toString());
			} else {
				response.getWriter().write(pageInfo.getMsg());
			}
		} catch (Exception e) {
			e.printStackTrace();
			log.error("拉新活动礼品兑换信息，导出到excel，失败原因是：" + e);
			response.getWriter().write("服务器出错");
		}
		return;
	}

    /**
     * 拉新活动第二季发货
     * 
     * @param vo
     *            礼品兑换信息
     * @return 分页实体类
     */
    @RequestMapping("processingDeliveryNew")
    @ResponseBody
    public Object processingDeliveryNew(GiftExchangeInfoNew vo) {
        PageInfo pageInfo = new PageInfo();
        try {
            pageInfo = giftExchangeInfoService.processingDeliveryNew(vo);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("拉新活动礼品兑换信息，发货，失败原因是：" + e);
            pageInfo.setResultInfo(ResultInfo.SERVER_ERROR);
        }
        return pageInfo;
    }

    /**
     * 拉新活动第二季确认收货
     * 
     * @param ids
     * @return
     */
    @RequestMapping("comfirmReceiptNew")
    @ResponseBody
    public Object comfirmReceiptNew(String ids) {
        PageInfo pageInfo = new PageInfo();
        try {
            pageInfo = giftExchangeInfoService.comfirmReceiptNew(ids);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("拉新活动礼品兑换信息，确认收货，失败原因是：" + e);
            pageInfo.setResultInfo(ResultInfo.SERVER_ERROR);
        }
        return pageInfo;
    }

}
