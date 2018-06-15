package com.mrbt.lingmoney.wap.controller.bank;

import javax.servlet.http.HttpServletRequest;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mrbt.lingmoney.service.bank.HxMessageCodeService;
import com.mrbt.lingmoney.utils.PageInfo;
import com.mrbt.lingmoney.utils.ResultParame;
import com.mrbt.lingmoney.utils.ResultParame.ResultInfo;
import com.mrbt.lingmoney.wap.controller.BaseController;
import com.mrbt.lingmoney.wap.controller.common.CommonMethodUtil;

/**
 * 华兴验证码
 * @author luox
 */
@Controller
@RequestMapping(value = "/bank", method = RequestMethod.POST)
public class HxMessageCodeController extends BaseController {
	private static final Logger LOG = LogManager.getLogger(HxMessageCodeController.class);
	@Autowired
	private HxMessageCodeService hxMessageCodeService;
	
	/**
	 * 华兴验证码
	 * @param request req
	 * @param token token
	 * @return pageInfo
	 */
	@RequestMapping("/getHxMessageCode")
    public @ResponseBody Object getHxMessageCode(HttpServletRequest request) {
		LOG.info("华兴验证码");
        PageInfo pageInfo = new PageInfo();
		try {
            String uId = CommonMethodUtil.getUidBySession(request);
            if (StringUtils.isEmpty(uId)) {
                pageInfo.setResultInfo(ResultInfo.LOGIN_OVERTIME);
            } else {
                pageInfo = hxMessageCodeService.getHxMessageCode(APP_ID, uId);
            }

		} catch (Exception e) {
			e.printStackTrace();
			LOG.error(e);
			pageInfo.setCode(ResultParame.ResultInfo.SERVER_ERROR.getCode());
			pageInfo.setMsg(e.getMessage());
		}

		return pageInfo;
	}
	
	
}
