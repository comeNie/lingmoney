package com.mrbt.lingmoney.admin.controller.bank;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mrbt.lingmoney.admin.controller.base.BaseController;
import com.mrbt.lingmoney.admin.service.bank.HxRedPacketService;
import com.mrbt.lingmoney.model.HxRedPacket;
import com.mrbt.lingmoney.utils.PageInfo;
import com.mrbt.lingmoney.utils.ResultParame.ResultInfo;
import com.mrbt.lingmoney.utils.ResultParame.ResultNumber;

/**
 * 华兴红包
 * @author luox
 * @Date 2017年7月4日
 */
@Controller
@RequestMapping(value = "/hxRedPacket", method = RequestMethod.POST)
public class HxRedPacketController extends BaseController {

	@Autowired
	private HxRedPacketService hxRedPacketService;
	
	/**
	 * 查询
	 * @param uIds	用户Uid
	 * @param rpId	红包ID
	 * @return	返回查询结果
	 */
	@RequestMapping("give")
	public @ResponseBody Object give(String uIds, String rpId) {
		PageInfo pageInfo = new PageInfo();
		try {
			hxRedPacketService.giveRedPacket(uIds, rpId);
			pageInfo.setCode(ResultInfo.SUCCESS.getCode());
			pageInfo.setMsg(ResultInfo.SUCCESS.getMsg());
		} catch (Exception e) {
			e.printStackTrace();
			pageInfo.setCode(ResultInfo.SERVER_ERROR.getCode());
			pageInfo.setMsg(ResultInfo.SERVER_ERROR.getMsg());
		}
		return pageInfo;
	}
	
	/**
	 * 获取列表
	 * @return	返回查询列表
	 */
	@RequestMapping("redPacketList")
	public @ResponseBody Object redPacketList() {
		PageInfo pageInfo = new PageInfo();
		try {
			hxRedPacketService.findRedPacketList(pageInfo);
			pageInfo.setCode(ResultInfo.SUCCESS.getCode());
			pageInfo.setMsg(ResultInfo.SUCCESS.getMsg());
		} catch (Exception e) {
			e.printStackTrace();
			pageInfo.setCode(ResultInfo.SERVER_ERROR.getCode());
			pageInfo.setMsg(ResultInfo.SERVER_ERROR.getMsg());
		}
		return pageInfo;
	}
	
	/**
	 * @return 返回列表
	 */
	@RequestMapping("manualRedPacketList")
	public @ResponseBody Object manualRedPacketList() {
		PageInfo pageInfo = new PageInfo();
		try {
			hxRedPacketService.findManualRedPacketList(pageInfo);
			pageInfo.setCode(ResultInfo.SUCCESS.getCode());
			pageInfo.setMsg(ResultInfo.SUCCESS.getMsg());
		} catch (Exception e) {
			e.printStackTrace();
			pageInfo.setCode(ResultInfo.SERVER_ERROR.getCode());
			pageInfo.setMsg(ResultInfo.SERVER_ERROR.getMsg());
		}
		return pageInfo;
	}

    /**
     * 
     * @param id	数据ID
     * @return 返回处理结果
     */
	@RequestMapping("buyer")
	public @ResponseBody Object buyer(Integer id) {
		PageInfo pageInfo = new PageInfo();
		try {
			hxRedPacketService.findBuyerByProId(id, pageInfo);
			pageInfo.setCode(ResultInfo.SUCCESS.getCode());
			pageInfo.setMsg(ResultInfo.SUCCESS.getMsg());
		} catch (Exception e) {
			e.printStackTrace();
			pageInfo.setCode(ResultInfo.SERVER_ERROR.getCode());
			pageInfo.setMsg(ResultInfo.SERVER_ERROR.getMsg());
		}
		return pageInfo;
	}
	
	/**
	 * 
	 * @param id	数据ID
	 * @return	返回处理结果
	 */
	@RequestMapping("publish")
	public @ResponseBody Object publish(String id) {
		PageInfo pageInfo = new PageInfo();
		try {
			hxRedPacketService.updateStatus(id, ResultNumber.ONE.getNumber());
			pageInfo.setCode(ResultInfo.SUCCESS.getCode());
			pageInfo.setMsg(ResultInfo.SUCCESS.getMsg());
		} catch (Exception e) {
			e.printStackTrace();
			pageInfo.setCode(ResultInfo.SERVER_ERROR.getCode());
			pageInfo.setMsg(ResultInfo.SERVER_ERROR.getMsg());
		}
		return pageInfo;
	}
	
	/**
	 * 
	 * @param id	数据ID
	 * @return	返回处理结果
	 */
	@RequestMapping("delete")
	public @ResponseBody Object delete(String id) {
		PageInfo pageInfo = new PageInfo();
		try {
			hxRedPacketService.updateStatus(id, ResultNumber.TWO.getNumber());
			pageInfo.setCode(ResultInfo.SUCCESS.getCode());
			pageInfo.setMsg(ResultInfo.SUCCESS.getMsg());
		} catch (Exception e) {
			e.printStackTrace();
			pageInfo.setCode(ResultInfo.SERVER_ERROR.getCode());
			pageInfo.setMsg(ResultInfo.SERVER_ERROR.getMsg());
		}
		return pageInfo;
	}
	
	/**
	 * 
	 * @param packet	红包数据对象
	 * @return	返回处理结果
	 */
	@RequestMapping("saveOrEdit")
	public @ResponseBody Object saveOrEdit(HxRedPacket packet) {
		PageInfo pageInfo = new PageInfo();
		try {
			hxRedPacketService.saveOrEdit(packet);
			pageInfo.setCode(ResultInfo.SUCCESS.getCode());
			pageInfo.setMsg(ResultInfo.SUCCESS.getMsg());
		} catch (Exception e) {
			e.printStackTrace();
			pageInfo.setCode(ResultInfo.SERVER_ERROR.getCode());
			pageInfo.setMsg(ResultInfo.SERVER_ERROR.getMsg());
		}
		return pageInfo;
	}
	
	/**
	 * 
	 * @param acquisMode  赠送方式
	 * @param hrpType	红包类型
	 * @param status	状态
	 * @param page	当前页数
	 * @param rows	每页条数
	 * @return	返回处理结果
	 */	
	@RequestMapping("list")
	public @ResponseBody Object list(Integer acquisMode, Integer hrpType, Integer status, Integer page, Integer rows) {
		PageInfo pageInfo = new PageInfo(page, rows);
		try {
			hxRedPacketService.getList(acquisMode, hrpType, status, pageInfo);
			pageInfo.setCode(ResultInfo.SUCCESS.getCode());
			pageInfo.setMsg(ResultInfo.SUCCESS.getMsg());
		} catch (Exception e) {
			e.printStackTrace();
			pageInfo.setCode(ResultInfo.SERVER_ERROR.getCode());
			pageInfo.setMsg(ResultInfo.SERVER_ERROR.getMsg());
		}
		return pageInfo;
	}
	
	/**
	 * 
	 * @return 返回处理结果
	 */
	@RequestMapping("productList")
	public @ResponseBody Object productList() {
		return hxRedPacketService.getProductList();
	}
	
	/**
	 * 返回处理结果
	 * @return 返回处理结果
	 */
	@RequestMapping("productTypeList")
	public @ResponseBody Object productTypeList() {
		return hxRedPacketService.getProductTypeList();
	}

	/**
	 * 给指定人员赠送加息券
	 *
	 * @Description
	 * @param rId	红包ID
	 * @param uids	用户ID
	 * @return	返回处理结果
	 */
	@RequestMapping("/giveRedPacketByUid")
	public @ResponseBody Object giveRedPacketByUid(String rId, String uids) {
		PageInfo pageInfo = new PageInfo();

		try {
			if (!StringUtils.isEmpty(rId) && !StringUtils.isEmpty(uids)) {
				hxRedPacketService.giveRedPacket(uids, rId);
				pageInfo.setCode(ResultInfo.SUCCESS.getCode());
				pageInfo.setMsg(ResultInfo.SUCCESS.getMsg());

			} else {
				pageInfo.setCode(ResultInfo.PARAM_MISS.getCode());
				pageInfo.setMsg(ResultInfo.PARAM_MISS.getMsg());
			}

		} catch (Exception e) {
			pageInfo.setCode(ResultInfo.SERVER_ERROR.getCode());
			pageInfo.setMsg(ResultInfo.SERVER_ERROR.getMsg());
			e.printStackTrace();
		}

		return pageInfo;
	}
}
