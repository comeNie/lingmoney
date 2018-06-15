package com.mrbt.lingmoney.admin.controller.gift;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mrbt.lingmoney.admin.controller.base.BaseController;
import com.mrbt.lingmoney.admin.service.gift.LingbaoExchangeInfoService;
import com.mrbt.lingmoney.model.LingbaoAddress;
import com.mrbt.lingmoney.model.LingbaoExchangeInfo;
import com.mrbt.lingmoney.model.LingbaoExchangeInfoVo;
import com.mrbt.lingmoney.utils.MyUtils;
import com.mrbt.lingmoney.utils.PageInfo;
import com.mrbt.lingmoney.utils.ResultParame.ResultInfo;

/**
 * 我的领地礼品兑换信息
 * 
 * @author lhq
 *
 */
@Controller
@RequestMapping("/gift/lingbaoExchangeInfo")
public class LingbaoExchangeInfoController extends BaseController {

	private Logger log = MyUtils.getLogger(LingbaoExchangeInfoController.class);

	@Autowired
	private LingbaoExchangeInfoService lingbaoExchangeInfoService;

	/**
	 * 订单处理
	 * 
	 * @param vo 礼品兑换信息
	 * @param request request
	 * @return 分页实体类
	 */
	@RequestMapping("processingOrder")
	@ResponseBody
	public Object processingOrder(HttpServletRequest request,
			LingbaoExchangeInfo vo) {
		PageInfo pageInfo = new PageInfo();
		try {
			pageInfo = lingbaoExchangeInfoService.processingOrder(vo);
			pageInfo.setCode(ResultInfo.SUCCESS.getCode());
			pageInfo.setMsg(ResultInfo.SUCCESS.getMsg());
		} catch (Exception e) {
			e.printStackTrace();
			log.error("我的领地礼品兑换信息，订单处理，失败原因是：" + e);
			pageInfo.setCode(ResultInfo.SERVER_ERROR.getCode());
			pageInfo.setMsg(ResultInfo.SERVER_ERROR.getMsg());
		}
		return pageInfo;
	}

	/**
	 * 根据主键获取用户收货地址
	 * 
	 * @param id
	 *            收货地址id
	 * @return 分页实体类
	 */
	@RequestMapping(value = "getAddress")
	public @ResponseBody Object getAddress(Integer id) {
		PageInfo pageInfo = new PageInfo();
		try {
			LingbaoAddress lingbaoAddress = lingbaoExchangeInfoService
					.findAddressById(id);
			pageInfo.setCode(ResultInfo.SUCCESS.getCode());
			pageInfo.setMsg(ResultInfo.SUCCESS.getMsg());
			pageInfo.setObj(lingbaoAddress);
		} catch (Exception e) {
			e.printStackTrace();
			log.error("我的领地礼品兑换信息，获取用户收货地址，失败原因是：" + e);
			pageInfo.setCode(ResultInfo.SERVER_ERROR.getCode());
			pageInfo.setMsg(ResultInfo.SERVER_ERROR.getMsg());
		}
		return pageInfo;

	}

	/**
	 * 根据条件查询礼品兑换信息
	 * 
	 * @param vo
	 *            礼品兑换信息
	 * @param page
	 *            当前页
	 * @param rows
	 *            每页显示的记录数
	 * @return 分页实体类
	 */
	@RequestMapping("list")
	@ResponseBody
	public Object list(LingbaoExchangeInfoVo vo, Integer page, Integer rows) {
		PageInfo pageInfo = new PageInfo(page, rows);
		Map<String, Object> condition;
		try {
			condition = new HashMap<String, Object>();
			if (vo != null) {
				// 用户姓名
				if (StringUtils.isNotBlank(vo.getUname())) {
					condition.put("uname", vo.getUname());
				}
				// 用户手机号
				if (vo.getuPhoneNumber() != null
						&& !vo.getuPhoneNumber().equals("")) {
					condition.put("uPhoneNumber", vo.getuPhoneNumber());
				}
				// 礼品名称
				if (StringUtils.isNotBlank(vo.getGiftName())) {
					condition.put("giftName", vo.getGiftName());
				}
				// 兑换状态
				if (vo.getStatus() != null) {
					condition.put("status", vo.getStatus());
				}
				// 兑换类型
				if (vo.getType() != null) {
					condition.put("type", vo.getType());
				}
				// 兑换类型
				if (vo.getReceiveWay() != null) {
					condition.put("receiveWay", vo.getReceiveWay());
				}
				// 领宝活动
				if (vo.getActivityId() != null) {
					condition.put("activityId", vo.getActivityId());
				}
				// 流水号
				if (vo.getSerialNumber() != null
						&& !vo.getSerialNumber().equals("")) {
					condition.put("serialNumber", vo.getSerialNumber());
				}
				// 快递单号
				if (vo.getExpressNumber() != null
						&& !vo.getExpressNumber().equals("")) {
					condition.put("expressNumber", vo.getExpressNumber());
				}
				// 快递公司
				if (StringUtils.isNotBlank(vo.getExpressCompany())) {
					condition.put("expressCompany", vo.getExpressCompany());
				}
				// 礼品类型
				if (vo.getGifttype() != null) {
					condition.put("gifttype", vo.getGifttype());
				}
			}

			pageInfo.setCondition(condition);
			lingbaoExchangeInfoService.findDataGrid(pageInfo);
		} catch (Exception e) {
			e.printStackTrace();
			log.error("我的领地礼品兑换信息，根据条件查询礼品兑换信息，失败原因是：" + e);
			pageInfo.setCode(ResultInfo.SERVER_ERROR.getCode());
			pageInfo.setMsg(ResultInfo.SERVER_ERROR.getMsg());
		}
		return pageInfo;
	}

}
