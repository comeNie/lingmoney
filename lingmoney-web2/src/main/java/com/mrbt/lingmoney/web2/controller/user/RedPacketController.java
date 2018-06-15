package com.mrbt.lingmoney.web2.controller.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mrbt.lingmoney.service.redPacket.RedPacketService;
import com.mrbt.lingmoney.utils.PageInfo;
import com.mrbt.lingmoney.utils.ResultParame.ResultInfo;

/**
 * 红包相关
 * 
 * @author suyibo
 *
 */
@Controller
@RequestMapping(value = "/appRedPacket")
public class RedPacketController {
	@Autowired
	private RedPacketService redPacketService;
	/**
	 * 展示注册完成后赠送优惠券内容提示
	 * @author suyibo 2017年10月27日
	 * @return pageInfo
	 */
	@RequestMapping(value = "/isDisplayInfo", method = RequestMethod.POST)
	public @ResponseBody Object getParamsInfo() {
		PageInfo pageInfo = new PageInfo();
		try {
			pageInfo = redPacketService.getParamsInfo();
		} catch (Exception e) {
			pageInfo.setCode(ResultInfo.SERVER_ERROR.getCode());
			pageInfo.setMsg("弹框提示查询失败");
			e.printStackTrace();
		}

		return pageInfo;
	}

	/**
	 * 获取具体用户(未查看)优惠券内容信息
	 * @param  token token
	 * @author suyibo 2017年10月27日
	 * @return pageInfo
	 */
	@RequestMapping(value = "/redPacketInfo", method = RequestMethod.POST)
	public @ResponseBody Object getRegistRedPacketInfoList(String token) {
		PageInfo pageInfo = new PageInfo();
		try {
			pageInfo = redPacketService.getRegistRedPacketInfoList(token);

		} catch (Exception e) {
			e.printStackTrace();
			pageInfo.setCode(ResultInfo.SERVER_ERROR.getCode());
			pageInfo.setMsg(ResultInfo.SERVER_ERROR.getMsg());
		}

		return pageInfo;
	}

	/**
	 * (未查看)优惠券-》已查看
	 * @param  token token
	 * @author suyibo 2017年10月30日
	 * @return pageInfo
	 */
	@RequestMapping(value = "/checkRedPacket", method = RequestMethod.POST)
	public @ResponseBody Object checkRedPacket(String token) {
		PageInfo pageInfo = new PageInfo();
		try {
			pageInfo = redPacketService.getCheckRedPacketList(token);

		} catch (Exception e) {
			e.printStackTrace();
			pageInfo.setCode(ResultInfo.SERVER_ERROR.getCode());
			pageInfo.setMsg(ResultInfo.SERVER_ERROR.getMsg());
		}

		return pageInfo;
	}

}
