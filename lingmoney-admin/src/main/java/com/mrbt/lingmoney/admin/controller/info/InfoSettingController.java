package com.mrbt.lingmoney.admin.controller.info;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mrbt.lingmoney.admin.controller.base.BaseController;
import com.mrbt.lingmoney.admin.service.info.InfoSettingService;
import com.mrbt.lingmoney.utils.PageInfo;
import com.mrbt.lingmoney.utils.ResultParame.ResultInfo;
import com.mrbt.lingmoney.utils.ResultParame.ResultNumber;

/**
 * 页面设置——》小功能设置
 * 
 * @author lhq
 *
 */
@Controller
@RequestMapping("/info/setting")
public class InfoSettingController extends BaseController {

	@Autowired
	private InfoSettingService infoSettingService;
	
	/**
	 * 设置赠送螃蟹个数
	 * 
	 * @param crabCount
	 *            数量
	 * @return 分页实体类
	 */
	@RequestMapping("setCrabCount")
	@ResponseBody
	public Object setCrabCount(Integer crabCount) {
		PageInfo pageInfo = new PageInfo();
		try {
			if (crabCount == null) {
				pageInfo.setCode(ResultInfo.PARAMETER_ERROR.getCode());
				pageInfo.setMsg("请输入赠送螃蟹个数");
			} else if (crabCount < 0 || crabCount > ResultNumber.SET_CRAD_COUNT.getNumber()) {
				pageInfo.setCode(ResultInfo.PARAMETER_ERROR.getCode());
				pageInfo.setMsg("赠送螃蟹个数范围0~10000");
			} else {
				infoSettingService.setCrabCount(crabCount);
				pageInfo.setCode(ResultInfo.SUCCESS.getCode());
			}
		} catch (Exception e) {
			e.printStackTrace();
			pageInfo.setCode(ResultInfo.SERVER_ERROR.getCode());
			pageInfo.setMsg(ResultInfo.SERVER_ERROR.getMsg());
		}
		return pageInfo;
	}

	/**
	 * 后台展示赠送螃蟹个数
	 * 
	 * @return 分页实体类
	 */
	@RequestMapping("showCrabCount")
	@ResponseBody
	public Object showCrabCount() {
		PageInfo pageInfo = new PageInfo();
		try {
			pageInfo = infoSettingService.showCrabCount();
			pageInfo.setCode(ResultInfo.SUCCESS.getCode());
		} catch (Exception e) {
			e.printStackTrace();
			pageInfo.setCode(ResultInfo.SERVER_ERROR.getCode());
			pageInfo.setMsg(ResultInfo.SERVER_ERROR.getMsg());
		}
		return pageInfo;
	}

}
