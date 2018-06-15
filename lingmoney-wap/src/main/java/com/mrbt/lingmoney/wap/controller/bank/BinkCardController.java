package com.mrbt.lingmoney.wap.controller.bank;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mrbt.lingmoney.bank.tiedcard.dto.HxTiedCardReqDto;
import com.mrbt.lingmoney.bank.utils.dto.PageResponseDto;
import com.mrbt.lingmoney.bank.utils.exception.ResponseInfoException;
import com.mrbt.lingmoney.commons.cache.RedisDao;
import com.mrbt.lingmoney.commons.exception.PageInfoException;
import com.mrbt.lingmoney.service.bank.BankCardService;
import com.mrbt.lingmoney.service.common.UtilService;
import com.mrbt.lingmoney.utils.AppCons;
import com.mrbt.lingmoney.utils.PageInfo;
import com.mrbt.lingmoney.utils.ResultParame;
import com.mrbt.lingmoney.utils.ResultParame.ResultInfo;
import com.mrbt.lingmoney.wap.controller.BaseController;
import com.mrbt.lingmoney.wap.controller.common.CommonMethodUtil;

import net.sf.json.JSONObject;

/**
 * 华兴E账户卡操作
 * 
 * @author YJQ
 *
 */
@Controller
@RequestMapping("/bank")
public class BinkCardController extends BaseController {
	private static final Logger LOGGER = LogManager.getLogger(BinkCardController.class);

	@Autowired
	private BankCardService bankCardService;

	@Autowired
	private RedisDao redisDao;
	
	@Autowired
	private UtilService utilService;
	/**
	 * @return string
	 */
	@RequestMapping(value = "/test", method = RequestMethod.GET)
	public String tiedCard() {
		return "hxBank/skip";
	}
	/**
	 * @param token token
	 * @param model model
	 * @return string
	 */
    @RequestMapping(value = "/tiedCardPage", method = RequestMethod.POST)
    public String tiedCardPage(Model model) {
        model.addAttribute("params", null);
		model.addAttribute("reqUrl", "/bank/tiedCard");
		return "hxBank/hxBankAction";
	}

	/**
	 * 绑定银行卡
	 * 
	 * @author YJQ 2017年6月6日
	 * @param request req
	 * @param token token
	 * @param cardNo
	 * @param model model
	 * @return json
	 */
	@RequestMapping("/tiedCard")
    public @ResponseBody Object tiedCard(HttpServletRequest request, Model model) {
		LOGGER.info("绑定银行卡");
		JSONObject json = new JSONObject();
		String uId="";
		try {
			HxTiedCardReqDto req = new HxTiedCardReqDto();
            req.setAPPID(APP_ID);
            uId = CommonMethodUtil.getUidBySession(request);
            if (!StringUtils.isEmpty(uId)) {
                PageResponseDto res = bankCardService.TiedBankCard(req, uId);
                json.put("code", ResultInfo.SUCCESS.getCode());
                json.put("obj", res);
            } else {
                json.put("code", ResultInfo.LOGIN_OVERTIME.getCode());
                json.put("msg", ResultInfo.LOGIN_OVERTIME.getMsg());
            }
		} catch (ResponseInfoException resE) {
			json.put("code", resE.getCode());
			json.put("msg", resE.getMessage());
		} catch (PageInfoException pageE) {
			json.put("code", pageE.getCode());
			json.put("msg", pageE.getMessage());
		} catch (Exception e) {
			LOGGER.info("绑定银行卡异常：" + e.toString()); // 抛出堆栈信息
			e.printStackTrace();
			json.put("code", ResultInfo.SERVER_ERROR.getCode());
			json.put("msg", ResultInfo.SERVER_ERROR.getMsg());
		}
		redisDao.delete(AppCons.REPEAT_CLICK +uId);
		return json;
	}

    /**
     * 处理绑卡返回商户结果
     * 
     * @author YJQ 2017年6月6日
     * @param request
     *            请求
     * @param note
     *            note
     * @param model
     *            数据模型
     * @return 信息
     */
    @RequestMapping("/tiedCardCallBack/{note}")
    public void receiveTiedCardResult(HttpServletResponse response, @PathVariable String note) {
        LOGGER.info("返回商户同步调用url");
        try {
            Map<String, Object> map = bankCardService.queryTiedCardResult(note);
            Integer status = (Integer) map.get("status");
            //处理中
            int returnStatus = ResultParame.ResultNumber.ZERO.getNumber();
            if (status == ResultParame.ResultNumber.ONE.getNumber()) {
                // 成功
                returnStatus = ResultParame.ResultNumber.ONE.getNumber();
            } else if (status == ResultParame.ResultNumber.TWO.getNumber()) {
                // 失败
                returnStatus = ResultParame.ResultNumber.TWO.getNumber();
            }

            response.sendRedirect("http://static.lingmoney.cn/wap/callback/bindCard.html?r=" + returnStatus);

        } catch (Exception e) {
            LOGGER.info("绑定银行卡异常："); // 抛出堆栈信息
            e.printStackTrace();
        }
    }
    
    /**
	 * 查询绑定卡信息
	 * 
	 * @author suyibo
	 * @date 2018年3月26日 下午15:18:54
	 * @return
	 *
	 */
	@RequestMapping(value = "/queryBindCardInfo", method = RequestMethod.POST)
	@ResponseBody
	public Object queryBindCardInfo(Integer currentPage, Integer pageNumber, HttpServletRequest request) {
		PageInfo pageInfo = new PageInfo(currentPage, pageNumber);

		String uid = CommonMethodUtil.getUidBySession(request);
		if (!StringUtils.isEmpty(uid)) {
			pageInfo = bankCardService.queryBindCardInfo(uid, pageInfo.getFrom() + "", pageInfo.getSize() + "",
					"changePersonalBindCardCallback_");

		} else {
			pageInfo.setResultInfo(ResultInfo.LOGIN_OVERTIME);
		}

		return pageInfo;
	}
	
	/**
	 * 修改个人绑定卡
	 * 
	 * @author suyibo
	 * @param model
	 *            model
	 * @param request
	 *            req
	 * @return
	 *
	 */
    @RequestMapping(value = "/changePersonalBindCardPage", method = RequestMethod.POST)
	public String changePersonalBindCardPage(HttpServletRequest request, HttpServletResponse response, Model model) {
		model.addAttribute("params", "");
        model.addAttribute("reqUrl", "/bank/changePersonalBindCard");
        return "hxBank/hxBankAction";
    }
    
    /**
     * 修改绑定银行卡
     * 
     * @param request req
     * @return json
     */
    @RequestMapping(value = "/changePersonalBindCard", method = RequestMethod.POST)
    public @ResponseBody Object changePersonalBindCard(HttpServletRequest request) {
        LOGGER.info("绑定银行卡");
        PageInfo pageInfo = new PageInfo();
        JSONObject json = new JSONObject();
        String uId = "";
        try {
            HxTiedCardReqDto req = new HxTiedCardReqDto();
			req.setAPPID(APP_ID);
			uId = CommonMethodUtil.getUidBySession(request);
			pageInfo = bankCardService.changePersonalBindCard(1, APP_ID, uId);
            json.put("obj", pageInfo.getObj());

        } catch (Exception e) {
            LOGGER.info("绑定银行卡异常：" + e.toString()); // 抛出堆栈信息
            e.printStackTrace();
            pageInfo.setResultInfo(ResultInfo.SERVER_ERROR);
        }
        json.put("code", pageInfo.getCode());
        json.put("msg", pageInfo.getMsg());

        return json;
    }

}
