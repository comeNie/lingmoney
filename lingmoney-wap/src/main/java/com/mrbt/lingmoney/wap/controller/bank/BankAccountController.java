package com.mrbt.lingmoney.wap.controller.bank;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mrbt.lingmoney.service.bank.BankAccountService;
import com.mrbt.lingmoney.utils.PageInfo;
import com.mrbt.lingmoney.utils.ResultParame;
import com.mrbt.lingmoney.utils.ResultParame.ResultInfo;
import com.mrbt.lingmoney.wap.controller.BaseController;
import com.mrbt.lingmoney.wap.controller.common.CommonMethodUtil;

/**
 * 银行账户开通相关接口
 * 
 * @author Administrator
 *
 */
@Controller
@RequestMapping("/bank")
public class BankAccountController extends BaseController {

	private static final Logger  LOGGER = LogManager.getLogger(BankAccountController.class);

	@Autowired
	private BankAccountService bankAccountService;
	/**
	 * @param request req
	 * @param response res
	 * @param token token
	 * @param acName acName
	 * @param mobile mobile
	 * @param idNo idNo
	 *  @param model model
	 * @return string
	 */
	@RequestMapping(value = "/accountOpenPage", method = RequestMethod.POST)
	public String accountOpenPage(HttpServletRequest request, HttpServletResponse response, String acName, String mobile, String idNo, Model model) {
		JSONObject paramsJson = new JSONObject();
		paramsJson.put("acName", acName);
		paramsJson.put("idNo", idNo);
		paramsJson.put("mobile", mobile);
		model.addAttribute("params", paramsJson.toString());
		model.addAttribute("reqUrl", "/bank/accountOpen");
		return "hxBank/hxBankAction";
	}

	/**
	 * 发起账户开立请求
	 * 
	 * @param request req
	 * @param response res
	 * @param token token 
	 * @param acName acName
	 * @param mobile mobile
	 * @param idNo idNo
	 * @param model model
	 * @return json
	 */
	@RequestMapping(value = "/accountOpen", method = RequestMethod.POST)
	public @ResponseBody Object accountOpen(HttpServletRequest request, HttpServletResponse response, String acName, String mobile, String idNo, Model model) {
		LOGGER.info("银行账户开立");
		response.setContentType("text/html;charset=UTF-8");
        PageInfo pageInfo = new PageInfo();
		// 发送投标报文
		try {
            String uId = CommonMethodUtil.getUidBySession(request);
            if (StringUtils.isEmpty(uId)) {
                pageInfo.setResultInfo(ResultInfo.LOGIN_OVERTIME);
            } else {
                pageInfo = bankAccountService.requestAccountOpen(uId, acName, idNo, mobile, 1, APP_ID);
            }

		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.error("银行账户开立失败，失败原因是：" + e.getMessage());
            pageInfo.setResultInfo(ResultInfo.SERVER_ERROR);
		}

        return pageInfo;
	}

	/**
	 * @param request req
	 * @param response res
	 * @param token token 
	 * @param seqNo seqNo
	 * @return pageInfo
	 */
	@RequestMapping("/queryAccountOpen")
    public @ResponseBody Object queryAccountOpen(HttpServletRequest request, HttpServletResponse response, String seqNo) {
		LOGGER.info("查询银行账户开立结果");
        PageInfo pageInfo = new PageInfo();

        try {
            String uId = CommonMethodUtil.getUidBySession(request);
            if (StringUtils.isEmpty(uId)) {
                pageInfo.setResultInfo(ResultInfo.LOGIN_OVERTIME);
            } else {
                pageInfo = bankAccountService.queryAccountOpen(uId, seqNo);
            }

        } catch (Exception e) {
            e.printStackTrace();
            pageInfo.setResultInfo(ResultInfo.SERVER_ERROR);
        }

		return pageInfo;
	}

	/**
	 * 查询用户开通E账户状态
	 * @param response req
	 * @param request res
	 * @param token token
	 * @return pageInfo
	 */
	@RequestMapping("/accountOpenStatus")
    public @ResponseBody Object accountOpenStatus(HttpServletRequest request, HttpServletResponse response) {
		PageInfo pageInfo = new PageInfo();
        try {
            String uId = CommonMethodUtil.getUidBySession(request);
            if (StringUtils.isEmpty(uId)) {
                pageInfo.setResultInfo(ResultInfo.LOGIN_OVERTIME);
            } else {
                pageInfo = bankAccountService.accountOpenStatus(uId);
            }

        } catch (Exception e) {
            e.printStackTrace();
            pageInfo.setResultInfo(ResultInfo.SERVER_ERROR);
        }

		return pageInfo;
	}

    /**
     * 账户开通银行返回商户按钮
     * 
     * @param request
     *            请求
     * @param response
     *            相应
     * @param uId
     *            用户uId
     * @param seqNo
     *            账户号
     * @param model
     *            数据
     * @return 信息
     */
    @RequestMapping(value = "/accountOpenCallBack/{uId}/{seqNo}")
    public void accountOpenCallBack(HttpServletRequest request, HttpServletResponse response,
            @PathVariable String uId, @PathVariable String seqNo, ModelMap model) {

        try {
            LOGGER.info("账户开通银行返回商户");
            PageInfo pageInfo = bankAccountService.queryAccountOpen(uId, seqNo);

            // 处理中
            int status = ResultParame.ResultNumber.ZERO.getNumber();
            if (pageInfo.getCode() == ResultParame.ResultInfo.SUCCESS.getCode()) {
                // 成功
                status = ResultParame.ResultNumber.ONE.getNumber();
            } else if (pageInfo.getCode() == ResultParame.ResultInfo.NO_SUCCESS.getCode()) {
                // 失败
                status = ResultParame.ResultNumber.TWO.getNumber();
            }

            response.sendRedirect("http://static.lingmoney.cn/wap/callback/accountOpen.html?r=" + status);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
