package com.mrbt.lingmoney.mobile.controller.enjoy;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/** 
 * @author  suyibo 
 * @date 创建时间：2018年3月30日
 */
@Controller
@RequestMapping("/enjoy")
public class EnjoyController {
	/**
	 * @return string
	 */
	@RequestMapping("/enjoyHtml")
	public String waitHtml(String token, Model model) {
		model.addAttribute("token", token);
		return "/enjoy";
	}
}
