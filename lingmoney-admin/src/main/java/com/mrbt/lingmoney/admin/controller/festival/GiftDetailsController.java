package com.mrbt.lingmoney.admin.controller.festival;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mrbt.lingmoney.admin.controller.base.BaseController;
import com.mrbt.lingmoney.admin.service.festival.GiftDetailsService;
import com.mrbt.lingmoney.model.admin.GiftDetailVo;
import com.mrbt.lingmoney.utils.MyUtils;
import com.mrbt.lingmoney.utils.PageInfo;
import com.mrbt.lingmoney.utils.ResultParame.ResultInfo;

/**
 * 中奖礼品兑换详情
 * 
 * @author lhq
 *
 */
@Controller
@RequestMapping("/festival/giftDetail")
public class GiftDetailsController extends BaseController {
	private Logger log = MyUtils.getLogger(GiftDetailsController.class);
	@Autowired
	private GiftDetailsService giftDetailService;

	/**
	 * 根据条件查询礼品兑换信息
	 * 
	 * @param vo  礼品兑换信息
	 * @param page 当前页
	 * @param rows 每页显示的记录数
	 * @return 分页实体类
	 */
	@RequestMapping("list")
	@ResponseBody
	public Object list(GiftDetailVo vo, Integer page, Integer rows) {
		PageInfo pageInfo = new PageInfo(page, rows);
		Map<String, Object> condition;
		try {
			condition = new HashMap<String, Object>();
			if (vo != null) {
				// 用户姓名
				if (StringUtils.isNotBlank(vo.getuName())) {
					condition.put("uName", vo.getuName());
				}
				// 用户手机号
				if (StringUtils.isNotBlank(vo.getuTel())) {
					condition.put("uTel", vo.getuTel());
				}
				// 礼品名称
				if (StringUtils.isNotBlank(vo.getgName())) {
					condition.put("gName", vo.getgName());
				}
				// 兑换状态
				if (vo.getState() != null) {
					condition.put("state", vo.getState());
				}
			}

			pageInfo.setCondition(condition);
			giftDetailService.findDataGrid(pageInfo);
		} catch (Exception e) {
			e.printStackTrace();
			log.error("根据条件查询礼品兑换信息，失败原因是：" + e);
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
	public @ResponseBody Object processingDelivery(GiftDetailVo vo) {
		PageInfo pageInfo = new PageInfo();
		try {
			pageInfo = giftDetailService.processingDelivery(vo);
			pageInfo.setCode(ResultInfo.SUCCESS.getCode());
			pageInfo.setMsg(ResultInfo.SUCCESS.getMsg());
		} catch (Exception e) {
			e.printStackTrace();
			log.error("发货，失败原因是：" + e);
			pageInfo.setCode(ResultInfo.SERVER_ERROR.getCode());
			pageInfo.setMsg(ResultInfo.SERVER_ERROR.getMsg());
		}
		return pageInfo;
	}

	/**
	 * 确认收货
	 * @param id	数据ID
	 * @return	返回结果
	 */
	@RequestMapping("processingReceipt")
	public @ResponseBody Object processingReceipt(Integer id) {
		PageInfo pageInfo = new PageInfo();
		try {
			pageInfo = giftDetailService.processingReceipt(id);
			pageInfo.setCode(ResultInfo.SUCCESS.getCode());
			pageInfo.setMsg(ResultInfo.SUCCESS.getMsg());
		} catch (Exception e) {
			e.printStackTrace();
			log.error("发货，失败原因是：" + e);
			pageInfo.setCode(ResultInfo.SERVER_ERROR.getCode());
			pageInfo.setMsg(ResultInfo.SERVER_ERROR.getMsg());
		}
		return pageInfo;
	}

}
