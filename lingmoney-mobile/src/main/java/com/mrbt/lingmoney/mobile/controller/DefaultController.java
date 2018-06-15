package com.mrbt.lingmoney.mobile.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
/**
 * 
 * @author ruochen.yu
 *
 */
@Controller
public class DefaultController {
    /** 
     * @param name name
     * @param model model
     * @return name
     */
	@RequestMapping("/{name}")
	public String forward(@PathVariable String name, Model model) {

		return name;
	}
    /**
     * @param name name
     * @param model model
     * @param request request
     * @return url
     */
	@RequestMapping("/*/{name}")
	public String forward(@PathVariable String name, Model model, HttpServletRequest request) {
		String url = request.getRequestURI();
		if (url.startsWith("/")) {
			url = url.replaceFirst("/", "");
		}
		if (url.endsWith(".html")) {
			url = url.substring(0, url.lastIndexOf("."));
		}
		return url;
	}
}
