package com.mrbt.lingmoney.wap.controller.insura;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mrbt.lingmoney.service.insura.HuanongInsuraService;
import com.mrbt.lingmoney.utils.PageInfo;

import net.sf.json.JSONObject;

/**
 * 获取保险增信协议
 * @author Administrator
 *
 */
@Controller
@RequestMapping("/insurance")
public class HuanongInsuraController {
	
	private static final Logger LOGGER = LogManager.getLogger(HuanongInsuraController.class);
	
	@Autowired
	private HuanongInsuraService huanongInsuraService;
	
	/**
	 * 查询产品借款人类型
	 * @param response
	 * @param request
	 * @param pId
	 * @return
	 */
	@RequestMapping(value = "/queryProBorType")
	public @ResponseBody Object verPictureCode(HttpServletResponse response, HttpServletRequest request, Integer pId) {
		LOGGER.info("获取保险增信协议");
		JSONObject jsonObject = new JSONObject();
		if (pId != null && pId > 0) {
			jsonObject = huanongInsuraService.queryProductBorrowerType(pId);
		}else {
			jsonObject.put("code", 1001);
			jsonObject.put("msg", "参数错误");
		}
		return jsonObject;
	}

}
