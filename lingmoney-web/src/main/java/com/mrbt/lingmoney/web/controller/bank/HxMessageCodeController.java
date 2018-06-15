package com.mrbt.lingmoney.web.controller.bank;

import javax.servlet.http.HttpServletRequest;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mrbt.lingmoney.service.bank.HxMessageCodeService;
import com.mrbt.lingmoney.utils.PageInfo;
import com.mrbt.lingmoney.utils.ResultParame;
import com.mrbt.lingmoney.web.controller.common.CommonMethodUtil;

/**
 * 华兴验证码
 * @author luox
 */
@Controller
@RequestMapping(value = "/bank", method = RequestMethod.POST)
public class HxMessageCodeController {
	private static final Logger LOG = LogManager.getLogger(HxMessageCodeController.class);
	@Autowired
	private HxMessageCodeService hxMessageCodeService;
	
	/**
	 * 华兴验证码
	 * 
	 * @param request
	 *            请求
	 * @return 信息
	 */
	@RequestMapping("/getHxMessageCode")
	public @ResponseBody Object getHxMessageCode(
			HttpServletRequest request) {
		LOG.info("华兴验证码");
		PageInfo pageInfo = null;
		try {
			String uId = CommonMethodUtil.getUidBySession(request);
			pageInfo = hxMessageCodeService.getHxMessageCode("PC", uId);
		} catch (Exception e) {
			e.printStackTrace();
			LOG.error(e);
			pageInfo = new PageInfo();
			pageInfo.setCode(ResultParame.ResultInfo.SERVER_ERROR.getCode());
			pageInfo.setMsg(e.getMessage());
		}
		return pageInfo;
	}
	
}
