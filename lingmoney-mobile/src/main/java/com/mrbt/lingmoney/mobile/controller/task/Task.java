package com.mrbt.lingmoney.mobile.controller.task;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/** 
 * @author  suyibo 
 * @date 创建时间：2018年3月20日
 */
@Controller
@RequestMapping("/task")
public class Task {

	/**
	 * @return string
	 */
	@RequestMapping("/waitHtml")
	public String waitHtml(String token, Model model) {
		model.addAttribute("token", token);
		return "/wait";
	}
}
