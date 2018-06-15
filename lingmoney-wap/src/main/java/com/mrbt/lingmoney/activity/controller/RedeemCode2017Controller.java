package com.mrbt.lingmoney.activity.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mrbt.lingmoney.activity.service.RedeemCode2017Service;
import com.mrbt.lingmoney.utils.PageInfo;
import com.mrbt.lingmoney.utils.ResultParame.ResultInfo;
import com.mrbt.lingmoney.wap.controller.common.CommonMethodUtil;

/**
 * 2018活动兑换功能
 * 
 * @author Administrator
 *
 */
@Controller
@RequestMapping("/redeem2017")
public class RedeemCode2017Controller {
	
	@Autowired
	private RedeemCode2017Service reddemCode2017Service;

	/**
	 * 用户兑换
	 * 
	 * @param request
	 *            request
	 * @param response
	 *            response
	 * @param redeemCode
	 *            兑换码
	 * @return
	 */
	@RequestMapping("/userRedeemOper")
	public @ResponseBody Object userRedeemOper(HttpServletRequest request, HttpServletResponse response, String redeemCode) {
		PageInfo pageInfo = new PageInfo();
		try {
            String uid = CommonMethodUtil.getUidBySession(request);
            if (StringUtils.isEmpty(uid)) {
                pageInfo.setResultInfo(ResultInfo.LOGIN_OVERTIME);
			} else {
				if (redeemCode != null && !redeemCode.equals("")) {
                    pageInfo = reddemCode2017Service.userReddemOper(uid, redeemCode);
				}else {
                    pageInfo.setCode(ResultInfo.EMPTY_DATA.getCode());
					pageInfo.setMsg("兑换码不能为空");
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
            pageInfo.setResultInfo(ResultInfo.SERVER_ERROR);
		}

		return pageInfo;
	}

}
