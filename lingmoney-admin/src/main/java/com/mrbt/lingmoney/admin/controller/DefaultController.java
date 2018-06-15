package com.mrbt.lingmoney.admin.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 默认controller层
 * @author Administrator
 *
 */
@Controller
@RequestMapping("forward")
public class DefaultController {

	/**
	 * 之间返回页面
	 * @param name	name
	 * @param rn	rn
	 * @param map	map
	 * @return	return
	 */
	@RequestMapping("/{name}")
	public String forward(@PathVariable String name, String rn, ModelMap map) {
		map.put("rn", rn);
		return name.replace("_", "/");
	}

}
