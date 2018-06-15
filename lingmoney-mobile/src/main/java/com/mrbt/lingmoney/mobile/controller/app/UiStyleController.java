package com.mrbt.lingmoney.mobile.controller.app;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mrbt.lingmoney.commons.cache.RedisDao;
import com.mrbt.lingmoney.service.app.UiStyleService;
import com.mrbt.lingmoney.utils.PageInfo;
import com.mrbt.lingmoney.utils.ResultParame;

/**
 * ui 样式
 *
 * @author syb
 * @date 2017年9月13日 下午2:22:17
 * @version 1.0
 **/
@Controller
@RequestMapping("/uiStyle")
public class UiStyleController {
	private static final String UI_STYLE_KEY = "ui_style_lingqian";

	@Autowired
	private RedisDao redisDao;

	@Autowired
	private UiStyleService uiStyleService;

	/**
	 * 获取当前可用样式
	 * 
	 * @author syb
	 * @date 2017年9月13日 下午2:55:48
	 * @version 1.0
	 * @return Number
	 *
	 */
	@RequestMapping(value = "/getUsedUiStyle", method = RequestMethod.POST)
	public @ResponseBody Object getUsedUiStyle() {
		if (redisDao.hasKey(UI_STYLE_KEY)) {
			return redisDao.get(UI_STYLE_KEY);
		} else {
			return ResultParame.ResultNumber.MINUS_ONE.getNumber();
		}
	}

	/**
	 * 查询列表
	 * 
	 * @author syb
	 * @date 2017年9月13日 下午3:26:59
	 * @version 1.0
	 * @return pageInfo
	 *
	 */
	@RequestMapping(value = "/listUiStyle", method = RequestMethod.POST)
	public @ResponseBody Object listUiStyle() {
		PageInfo pi = new PageInfo();

		try {
			pi = uiStyleService.listUiStyle();
		} catch (Exception e) {
			pi.setCode(ResultParame.ResultInfo.SERVER_ERROR.getCode());
			pi.setMsg(ResultParame.ResultInfo.SERVER_ERROR.getMsg());
			e.printStackTrace();
		}

		return pi;
	}


}
