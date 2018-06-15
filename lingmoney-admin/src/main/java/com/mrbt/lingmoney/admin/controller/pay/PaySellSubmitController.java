package com.mrbt.lingmoney.admin.controller.pay;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mrbt.lingmoney.admin.service.pay.PaySellSubmitService;
import com.mrbt.lingmoney.mapper.UsersRedPacketMapper;
import com.mrbt.lingmoney.model.UsersFinancing;
import com.mrbt.lingmoney.model.UsersRedPacket;
import com.mrbt.lingmoney.model.UsersRedPacketExample;
import com.mrbt.lingmoney.utils.DateUtils;
import com.mrbt.lingmoney.utils.MyUtils;
import com.mrbt.lingmoney.utils.PageInfo;
import com.mrbt.lingmoney.utils.RowBoundsUtils;
import com.mrbt.lingmoney.utils.ResultParame.ResultInfo;
import com.mrbt.lingmoney.utils.ResultParame.ResultNumber;

/**
 * 赎回信息
 * 
 * @author lzp
 *
 */
@Controller
@RequestMapping("/sell_submit")
public class PaySellSubmitController {
	
	private Logger log = MyUtils.getLogger(PaySellSubmitController.class);
	
	@Autowired
	private PaySellSubmitService paySellSubmitService;
	
	@Autowired
	private UsersRedPacketMapper usersRedPacketMapper;
	
	/**
	 * 查看融资人信息
	 * @param vo	封装对象
	 * @param page	页数
	 * @param rows	条数
	 * @return	返回列表
	 */
	@RequestMapping("listUsers")
	@ResponseBody
	public Object listUsers(UsersFinancing vo, String page, String rows) {
		log.info("/sell_submit/listUsers");
		return paySellSubmitService.listUsers(vo, RowBoundsUtils.getRowBounds(page, rows));
	}

	/**
	 * 查询固定类交易的赎回信息 《产品赎回确认》菜单
	 * @param platUserNo	platUserNo
	 * @param pType	产品类型
	 * @return	返回结果
	 */
	@RequestMapping("listByFix")
	@ResponseBody
	public Object listByFix(String platUserNo, Integer pType) {
		log.info("/sell_submit/listByFix");
		HashMap<String, Object> resMap = new HashMap<String, Object>();
		if (StringUtils.isBlank(platUserNo)) {
			resMap.put("rows", new ArrayList<Object>());
			resMap.put("total", 0);
		} else {
			HashMap<String, Object> params = new HashMap<String, Object>();
			params.put("platUserNo", platUserNo);
			params.put("pType", pType);
			List<Map<String, Object>> result = paySellSubmitService.listByFix(params);
			// 如果是华兴产品，需要验证是否存在红包
			if (pType == ResultNumber.TWO.getNumber()) {
				log.info("===============华兴产品，计算红包==================");
				for (Map<String, Object> map : result) {
					String uid = (String) map.get("u_id");
					Integer tid = (Integer) map.get("t_id");
					// 查询用户是否有红包/加息券
					UsersRedPacketExample usersRedPacketExmp = new UsersRedPacketExample();
					usersRedPacketExmp.createCriteria().andUIdEqualTo(uid).andTIdEqualTo(tid);
					List<UsersRedPacket> usersRedPacketList = usersRedPacketMapper
							.selectByExample(usersRedPacketExmp);
					if (usersRedPacketList != null && usersRedPacketList.size() > 0) {
						// 加息金额
						UsersRedPacket usersRedPacket = usersRedPacketList.get(0);
						map.put("redPacketMoney", usersRedPacket.getActualAmount());
					}
				}
			}
			resMap.put("rows", result);
			resMap.put("total", result.size());
		}
		return resMap;
	}

	/**
	 * 查询随心取交易的赎回信息
	 * @param platUserNo	platUserNo
	 * @return	返回结果
	 */
	@RequestMapping("listByTakeHeart")
	@ResponseBody
	public Object listByTakeHeart(String platUserNo) {
		log.info("/sell_submit/listByTakeHeart");
		HashMap<String, Object> map = new HashMap<String, Object>();
		if (StringUtils.isBlank(platUserNo)) {
			map.put("rows", new ArrayList<Object>());
			map.put("total", 0);
		}
		if (StringUtils.isNotBlank(platUserNo)) {
			Calendar calendar = Calendar.getInstance();

			calendar.set(Calendar.HOUR_OF_DAY, ResultNumber.SIXTEEN.getNumber());
			calendar.set(Calendar.MINUTE, 0);
			calendar.set(Calendar.SECOND, 0);

			List<Map<String, Object>> result = paySellSubmitService.listTakeHeart(platUserNo, calendar.getTime());
			map.put("rows", result);
			map.put("total", result.size());
		}
		return map;
	}

	/**
	 * 随心取兑付
	 * @param idStr	数据IDS
	 * @return	返回处理结果
	 */
	@RequestMapping("takeHeartPay")
	@ResponseBody
	public Object takeHeartPay(String idStr) {
		log.info("/sell_submit/takeHeartPay");
		PageInfo pageInfo = new PageInfo();
		// 一天只能执行一次确认还款，在这可以进行判断
		try {
			Date cd = new Date();
			// 判断时间如果小于4点无法结账
//			if (DateUtils.isToDay(cd)) {
//				pageInfo.setCode(ResultInfo.SERVER_ERROR.getCode());
//				pageInfo.setMsg("导出错误，未到16点，不能确认还款。更新失败");
//				return pageInfo;
//			}

			String batch = DateUtils.getDtStr(new Date(), DateUtils.sf_m);
			StringBuffer buffer = new StringBuffer();

			if (idStr != null) {
				String[] arr = idStr.split(",");

				buffer.append("( ");
				for (int i = 0; i < arr.length; i++) {
					if (i == 0) {
						buffer.append("id =" + arr[i]);
					} else {
						buffer.append(" or id =" + arr[i]);
					}
				}
				buffer.append(" )");
				if (paySellSubmitService.updateTakeHeartList(buffer.toString(), batch)) {
					pageInfo.setCode(ResultInfo.SUCCESS.getCode());
					pageInfo.setMsg(ResultInfo.SUCCESS.getMsg());
				} else {
					pageInfo.setCode(ResultInfo.MODIFY_REJECT.getCode());
					pageInfo.setMsg(ResultInfo.MODIFY_REJECT.getMsg());
				}
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
}
