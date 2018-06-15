package com.mrbt.lingmoney.web.controller.bank;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.mrbt.lingmoney.service.bank.AutoTenderAuthorizeService;
import com.mrbt.lingmoney.utils.PageInfo;
import com.mrbt.lingmoney.utils.ResultParame;
import com.mrbt.lingmoney.web.controller.common.CommonMethodUtil;

/**
 * 自动投标授权
 * @author luox
 * @Date 2017年6月6日
 */
@Controller
@RequestMapping(value = "/bank")
public class AutoTenderAuthorizeController {
	private static final Logger LOGGER = LogManager.getLogger(AutoTenderAuthorizeController.class);
	@Autowired
	private AutoTenderAuthorizeService autoTenderAuthorizeService;
	
	/**
	 * 接收银行xml报文
	 * 
	 * @param request
	 *            请求
	 * @return 信息
	 * @throws Exception
	 *             抛出异常信息
	 */
	public String getXmlDocument(HttpServletRequest request) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(request.getInputStream(), "UTF-8"));
		String buffer = null;

		StringBuffer xml = new StringBuffer();

		while ((buffer = br.readLine()) != null) {
			xml.append(buffer);
		}
		return xml.toString();
	}
	
	
	/**
	 * 自动投标授权
	 * 
	 * @param request
	 *            请求
	 * @param response
	 *            相应
	 * @param model
	 *            数据
	 * @param remark
	 *            标记
	 * @return 信息
	 */
	@RequestMapping(value = "/autoTenderAuthorize", method = RequestMethod.POST)
	public String autoTenderAuthorize(HttpServletRequest request, HttpServletResponse response, 
			Model model, String remark) {
			
		LOGGER.info("自动投标授权");
		
		PageInfo pageInfo = new PageInfo();
		try {
			String uId = CommonMethodUtil.getUidBySession(request);
			pageInfo = autoTenderAuthorizeService.autoTenderAuthorize(uId, 0, "PC", remark);
			
			if (pageInfo.getCode() == ResultParame.ResultInfo.SUCCESS.getCode()) {
				Map<?, ?> map = (Map<?, ?>) pageInfo.getObj();
				String requestData = (String) map.get("requestData");
				String replaceAll = requestData.replaceAll("\"", "&quot;").replaceAll("<", "&lt;").replaceAll(">", "&gt;");
				model.addAttribute("requestData", replaceAll);
				model.addAttribute("transCode", map.get("transCode"));
				model.addAttribute("bankUrl", map.get("bankUrl"));
			} else {
				response.getWriter().write("服务器出错");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "hxBank/hxBankAction2";
	}
	
}

