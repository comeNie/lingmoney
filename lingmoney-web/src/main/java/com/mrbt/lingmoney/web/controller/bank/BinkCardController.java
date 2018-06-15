package com.mrbt.lingmoney.web.controller.bank;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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

import com.mrbt.lingmoney.bank.tiedcard.dto.HxTiedCardReqDto;
import com.mrbt.lingmoney.bank.utils.dto.PageResponseDto;
import com.mrbt.lingmoney.bank.utils.exception.ResponseInfoException;
import com.mrbt.lingmoney.commons.cache.RedisDao;
import com.mrbt.lingmoney.commons.exception.PageInfoException;
import com.mrbt.lingmoney.service.bank.BankCardService;
import com.mrbt.lingmoney.service.common.UtilService;
import com.mrbt.lingmoney.service.users.UsersService;
import com.mrbt.lingmoney.utils.AppCons;
import com.mrbt.lingmoney.utils.PageInfo;
import com.mrbt.lingmoney.utils.ResultParame;
import com.mrbt.lingmoney.utils.ResultParame.ResultInfo;
import com.mrbt.lingmoney.web.controller.common.CommonMethodUtil;
import com.mrbt.lingmoney.web.vo.view.ResultPageInfo;

import net.sf.json.JSONObject;

/**
 * 华兴E账户卡操作
 * 
 * @author YJQ
 *
 */
@Controller
@RequestMapping("/bank")
public class BinkCardController {
	private static final Logger LOGGER = LogManager.getLogger(BinkCardController.class);

	@Autowired
	private BankCardService bankCardService;
	@Autowired
	private RedisDao redisDao;
	@Autowired
	private UsersService usersService;
	@Autowired
	private UtilService utilService;

	/**
	 * 绑定银行卡
	 * 
	 * @author YJQ 2017年6月6日
	 * @param request 请求
	 * @return 信息
	 */
	@RequestMapping("/tiedCard")
	@ResponseBody
	public Object tiedCard(HttpServletRequest request) {
		LOGGER.info("绑定银行卡");

		PageInfo pageInfo = new PageInfo();
		String uId = "";
		try {
			HxTiedCardReqDto req = new HxTiedCardReqDto();
			req.setAPPID("PC");
		    uId = utilService.queryUid(AppCons.SESSION_USER + request.getSession().getId());
		    
			PageResponseDto res = bankCardService.TiedBankCard(req, uId);

			pageInfo.setCode(ResultParame.ResultInfo.SUCCESS.getCode());
			pageInfo.setObj(res);

		} catch (ResponseInfoException resE) {
			pageInfo.setCode(resE.getCode());
			pageInfo.setMsg(resE.getMessage());

		} catch (PageInfoException pageE) {
			pageInfo.setCode(pageE.getCode());
			pageInfo.setMsg(pageE.getMessage());

		} catch (Exception e) {
			LOGGER.info("绑定银行卡异常：" + e.toString()); // 抛出堆栈信息
			e.printStackTrace();
			pageInfo.setCode(ResultParame.ResultInfo.SERVER_ERROR.getCode());
			pageInfo.setMsg(ResultParame.ResultInfo.SERVER_ERROR.getMsg());

		}
		redisDao.delete(AppCons.REPEAT_CLICK + uId);
		return pageInfo;
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
	public String receiveTiedCardResult(HttpServletRequest request, @PathVariable String note, ModelMap model) {
		LOGGER.info("返回商户同步调用url");

		try {
			CommonMethodUtil.modelUserBaseInfo(model, request, usersService);
		} catch (Exception e) {
			e.printStackTrace();
		}

		ResultPageInfo res = new ResultPageInfo();
		res.setType(ResultParame.ResultNumber.TWO.getNumber());
		res.setAutoReturnName("银行卡页面");
		res.setAutoReturnUrl("/myAccount/bindBankCard");
		res.setButtonOneName("立即查看");
		res.setButtonOneUrl("/myAccount/bindBankCard");
		try {
			Map<String, Object> map = bankCardService.queryTiedCardResult(note);
			Integer status = (Integer) map.get("status");
			if (status.equals(ResultParame.ResultNumber.TWO.getNumber())) {
				res.setButtonTwoName("重新激活");
				res.setFailReason(map.get("failReason").toString());
			}
			res.setStatus(status);
			res.setResultMsg(map.get("msg").toString());
		} catch (PageInfoException e) {
			res.setStatus(ResultParame.ResultNumber.ZERO.getNumber());
			res.setResultMsg(e.getMessage());
		} catch (ResponseInfoException e) {
			res.setStatus(ResultParame.ResultNumber.ZERO.getNumber());
			res.setResultMsg(e.getMessage());
		} catch (Exception e) {
			res.setStatus(ResultParame.ResultNumber.ZERO.getNumber());
			res.setResultMsg("查询激活结果出错");
			LOGGER.info("绑定银行卡异常："); // 抛出堆栈信息
			e.printStackTrace();
		}
		model.addAttribute("resultInfo", res);

		return "hxCallBack";
	}

	/**
	 * 返回激活结果
	 * 
	 * @author YJQ 2017年7月31日
	 * @param request
	 *            请求
	 * @param model
	 *            数据包装
	 * @param note
	 *            note
	 * @return 信息
	 */
	@RequestMapping("/queryTiedCardResult/{note}")
	public String queryTiedCardResult(HttpServletRequest request, ModelMap model, @PathVariable String note) {
		try {
			CommonMethodUtil.modelUserBaseInfo(model, request, usersService);
		} catch (Exception e) {
			e.printStackTrace();
		}

		ResultPageInfo info = new ResultPageInfo();
		info.setType(ResultParame.ResultNumber.TWO.getNumber());
		info.setAutoReturnName("银行卡页面");
		info.setAutoReturnUrl("/myAccount/bindBankCard");
		info.setButtonOneName("立即查看");
		info.setButtonOneUrl("/myAccount/bindBankCard");

		try {
			Map<String, Object> map = bankCardService
					.queryTiedCardResult(AppCons.SESSION_USER + request.getSession().getId(), note);
			Integer status = (Integer) map.get("status");
			if (status.equals(ResultParame.ResultNumber.THREE.getNumber())) {
				info.setButtonTwoName("重新激活");
			}
			info.setStatus(status);
			info.setResultMsg(map.get("msg").toString());
		} catch (PageInfoException pageE) {
			info.setStatus(ResultParame.ResultInfo.SERVER_ERROR.getCode());
			info.setResultMsg(ResultParame.ResultInfo.SERVER_ERROR.getMsg());
		} catch (Exception e) {
			LOGGER.info("绑定银行卡异常：" + e.toString()); // 抛出堆栈信息
			e.printStackTrace();
		}

		model.addAttribute("resultInfo", info);

		return "results";
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
			req.setAPPID("PC");
			uId = CommonMethodUtil.getUidBySession(request);
			pageInfo = bankCardService.changePersonalBindCard(0, "PC", uId);
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
