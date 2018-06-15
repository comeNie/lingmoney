package com.mrbt.lingmoney.mobile.controller.bank;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONObject;

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
import com.mrbt.lingmoney.mobile.controller.BaseController;
import com.mrbt.lingmoney.service.bank.BankCardService;
import com.mrbt.lingmoney.service.common.UtilService;
import com.mrbt.lingmoney.utils.AES256Encryption;
import com.mrbt.lingmoney.utils.AppCons;
import com.mrbt.lingmoney.utils.PageInfo;
import com.mrbt.lingmoney.utils.ResultParame.ResultInfo;

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
	public String tiedCardPage(String token, Model model) {
		JSONObject paramsJson = new JSONObject();
		paramsJson.put("token", token);
		model.addAttribute("params", AES256Encryption.aes256Encrypt(paramsJson.toString()));
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
	public @ResponseBody Object tiedCard(HttpServletRequest request, String token, Model model) {
		LOGGER.info("绑定银行卡");
		JSONObject json = new JSONObject();
		String uId="";
		try {
			HxTiedCardReqDto req = new HxTiedCardReqDto();
			req.setAPPID("APP");
			uId = utilService.queryUid(AppCons.TOKEN_VERIFY + token);
			PageResponseDto res = bankCardService.TiedBankCard(req, uId);
			json.put("code", ResultInfo.SUCCESS.getCode());
			json.put("obj", res);
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
     * 修改个人绑定卡
     * 
     * @author yiban
     * @date 2018年3月13日 上午11:40:01
     * @version 1.0
     * @param token token
     * @param model model
     * @return
     *
     */
    @RequestMapping(value = "/changePersonalBindCardPage", method = RequestMethod.POST)
    public String changePersonalBindCardPage(String token, Model model) {
        JSONObject paramsJson = new JSONObject();
        paramsJson.put("token", token);
        model.addAttribute("params", AES256Encryption.aes256Encrypt(paramsJson.toString()));
        model.addAttribute("reqUrl", "/bank/changePersonalBindCard");
        return "hxBank/hxBankAction";
    }

    /**
     * 修改绑定银行卡
     * 
     * @author YJQ 2017年6月6日
     * @param request req
     * @param token token
     * @param cardNo
     * @param model model
     * @return json
     */
    @RequestMapping(value = "/changePersonalBindCard", method = RequestMethod.POST)
    public @ResponseBody Object changePersonalBindCard(HttpServletRequest request, String token, Model model) {
        LOGGER.info("绑定银行卡");
        PageInfo pageInfo = new PageInfo();
        JSONObject json = new JSONObject();
        String uId = "";
        try {
            HxTiedCardReqDto req = new HxTiedCardReqDto();
            req.setAPPID("APP");
            uId = utilService.queryUid(AppCons.TOKEN_VERIFY + token);
            pageInfo = bankCardService.changePersonalBindCard(1, "APP", uId);
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

    /**
     * 修改绑定卡 返回商户
     * 
     * @author yiban
     * @date 2018年3月13日 下午12:26:54
     * @version 1.0
     * @param note
     * @return
     *
     */
    @RequestMapping("/changePersonalBindCardCallback/{note}")
    @ResponseBody
    public Object changePersonalBindCardCallback(Integer currentPage, Integer pageNumber, @PathVariable String note) {
        PageInfo pageInfo = new PageInfo(currentPage, pageNumber);

        pageInfo = bankCardService.queryBindCardInfo(note, pageInfo.getFrom() + "", pageInfo.getSize() + "",
                "changePersonalBindCardCallback_");

        return pageInfo;
    }

    /**
     * 查询绑定卡信息
     * 
     * @author yiban
     * @date 2018年3月13日 下午12:26:54
     * @version 1.0
     * @param note
     * @return
     *
     */
    @RequestMapping(value = "/queryBindCardInfo", method = RequestMethod.POST)
    @ResponseBody
    public Object queryBindCardInfo(Integer currentPage, Integer pageNumber, String token) {
        PageInfo pageInfo = new PageInfo(currentPage, pageNumber);

        String uid = getUid(AppCons.TOKEN_VERIFY + token);
        if (!StringUtils.isEmpty(uid)) {
            pageInfo = bankCardService.queryBindCardInfo(uid, pageInfo.getFrom() + "", pageInfo.getSize() + "",
                    "changePersonalBindCardCallback_");

        } else {
            pageInfo.setResultInfo(ResultInfo.LOGIN_OVERTIME);
        }

        return pageInfo;
    }
}
