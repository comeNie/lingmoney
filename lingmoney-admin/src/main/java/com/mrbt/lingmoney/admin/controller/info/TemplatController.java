package com.mrbt.lingmoney.admin.controller.info;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.mrbt.lingmoney.admin.service.bank.TempletService;

/**
 * 页面设置——》邮件模板
 * 
 * @author 000608
 *
 */

@Controller
@RequestMapping("/templet")
public class TemplatController {

	@Autowired
	private TempletService templetService;
	/**
	 * 模板上传  
	 * @param	request		request
	 * @param	response	response
	 * @param	file 	fileInput
	 * 放到admin
	 * @return
	 */
	@RequestMapping("/saveFile")
	public void saveFile(HttpServletRequest request, HttpServletResponse response,
			@RequestParam(value = "file", required = true) MultipartFile file) {
		try {
			if (file != null) {
				templetService.uploadFile(file);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
