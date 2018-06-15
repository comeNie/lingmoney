package com.mrbt.lingmoney.admin.controller.bank;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mrbt.lingmoney.admin.service.bank.HxMessageCodeService;
import com.mrbt.lingmoney.utils.PageInfo;
import com.mrbt.lingmoney.utils.ResultParame.ResultInfo;

/**
 *@author syb
 *@date 2017年7月13日 下午3:49:37
 *@version 1.0
 *@description 
 **/
@Controller
@RequestMapping("/bank")
public class HxMessageCodeController {
	private static final Logger LOGGER = LogManager.getLogger(HxMessageCodeController.class);

	@Autowired
	private HxMessageCodeService hxMessageCodeService;

	/**
	 * 获取短信验证码
	 * @param accountId  账户id
	 * @param type 1：自动投标撤销 2：自动还款授权撤销 0：默认
	 * @return  返回处理结果
	 */
	@RequestMapping(value = "/getMessageCode", method = RequestMethod.POST)
	public @ResponseBody Object getMessageCode(String accountId, String type) {
		LOGGER.info("请求获取短信验证码{}:{}", accountId, type);
		PageInfo pageInfo = new PageInfo();
		try {
			pageInfo = hxMessageCodeService.getMessageCode(type, accountId);
		} catch (Exception e) {
			pageInfo.setCode(ResultInfo.SERVER_ERROR.getCode());
			pageInfo.setMsg(ResultInfo.SERVER_ERROR.getMsg());
			e.printStackTrace();
		}

		return pageInfo;
	}

}
