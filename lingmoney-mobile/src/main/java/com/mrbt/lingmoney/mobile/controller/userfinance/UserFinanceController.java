package com.mrbt.lingmoney.mobile.controller.userfinance;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mrbt.lingmoney.commons.cache.RedisGet;
import com.mrbt.lingmoney.commons.exception.PageInfoException;
import com.mrbt.lingmoney.mobile.controller.BaseController;
import com.mrbt.lingmoney.mobile.vo.userfinance.AccountFlowVo;
import com.mrbt.lingmoney.mobile.vo.userfinance.UserMessageVo;
import com.mrbt.lingmoney.model.AccountFlow;
import com.mrbt.lingmoney.model.SellResult;
import com.mrbt.lingmoney.model.UsersMessage;
import com.mrbt.lingmoney.service.common.UtilService;
import com.mrbt.lingmoney.service.trading.TradingService;
import com.mrbt.lingmoney.service.userfinance.UserFinanceHomeService;
import com.mrbt.lingmoney.service.users.UserRedPacketService;
import com.mrbt.lingmoney.utils.AppCons;
import com.mrbt.lingmoney.utils.DateUtils;
import com.mrbt.lingmoney.utils.PageInfo;
import com.mrbt.lingmoney.utils.ResultParame.ResultInfo;
import com.mrbt.lingmoney.utils.ResultParame.ResultNumber;
import com.mrbt.lingmoney.utils.StringOpertion;

/**
 * 我的理财首页
 * 
 * @author 罗鑫
 * @Date 2017年4月12日
 */
@Controller
@RequestMapping(value = "/userfinance", method = RequestMethod.POST)
public class UserFinanceController extends BaseController {
	private static final Logger LOG = LogManager.getLogger(UserFinanceController.class);
	@Autowired
	private UserFinanceHomeService userFinanceHomeService;
	@Autowired
	private UserRedPacketService userRedPacketService;
	@Autowired
	private TradingService tradingService;
	@Autowired
	private RedisGet redisGet;
	@Autowired
	private UtilService utilService;
	
	private static Map<String, String> typeMap = new HashMap<String, String>();
	
	static {
		typeMap.put("0", "充值");
		typeMap.put("1", "取现");
		typeMap.put("2", "理财");
		typeMap.put("3", "赎回");
	}

	/**
	 * 我的码
	 * @param token
	 *            token
	 * @param request
	 *            request
	 * @return pageInfo
	 */ 
	@RequestMapping("/myCode")
	public @ResponseBody Object myCode(@RequestParam(required = true) String token, HttpServletRequest request) {
		LOG.info("/userfinance/myCode");
		PageInfo pageInfo = null;
		try {
			pageInfo = new PageInfo();
			pageInfo.setCode(ResultInfo.SUCCESS.getCode());
			pageInfo.setMsg(ResultInfo.SUCCESS.getMsg());
			String uId = redisGet.getRedisStringResult(AppCons.TOKEN_VERIFY + token).toString();
			pageInfo.setObj(userFinanceHomeService.findUserReferralCode(uId, request));
		} catch (Exception e) {
			e.printStackTrace();
			LOG.error(e);
			pageInfo = new PageInfo();
			pageInfo.setCode(ResultInfo.SERVER_ERROR.getCode());
			pageInfo.setMsg(ResultInfo.SERVER_ERROR.getMsg());
		}
		return pageInfo;
	}

	    /**
     * 资金流水
     * @param token
     *            token
     * @param pageNo
     *            pageNo
     * @param pageSize
     *            pageSize
     * @param type
     *            type
     * @param multyTypes
     *            multyTypes 多类型用英文逗号分隔
     * @return pageInfo
     */
	@RequestMapping("moneyFlow")
    public @ResponseBody Object moneyFlow(String token, Integer pageNo, Integer pageSize, Integer type,
            String multyTypes) {
		LOG.info("/userfinance/moneyFlow");
		PageInfo pageInfo = null;
		try {
			pageInfo = new PageInfo(pageNo, pageSize);
			pageInfo.setCode(ResultInfo.SUCCESS.getCode());
			pageInfo.setMsg(ResultInfo.SUCCESS.getMsg());
			String uId = redisGet.getRedisStringResult(AppCons.TOKEN_VERIFY + token).toString();
            userFinanceHomeService.findMoneyFlow(uId, pageInfo, type, multyTypes);
			List<?> rows = pageInfo.getRows();
			if (rows != null) {
				AccountFlow flow = null;
				AccountFlowVo vo = null;
				List<AccountFlowVo> voList = new ArrayList<>();
				for (int i = 0; i < rows.size(); i++) {
					vo = new AccountFlowVo();
					flow = (AccountFlow) rows.get(i);
					vo.setMoney(flow.getMoney());
					vo.setOperateTime(DateUtils.getFormatDateString(flow.getOperateTime()));
					vo.setStatus(flow.getStatus());
					vo.setType(flow.getType());
                    // 华兴流水号28位，京东30位
                    if (null != flow.getNumber() && !StringUtils.isEmpty(flow.getNumber())) {
                        vo.setpType(flow.getNumber().length() == 28 ? 2 : 0);
                    } else {
                        vo.setpType(0);
                    }
					switch (flow.getType()) {
					case 0:
						vo.setTypeName("充值");
						break;
					case 1:
						vo.setTypeName("提现");
						break;
					case 2:
						vo.setTypeName("理财");
						break;
					case 3:
						vo.setTypeName("赎回");
						break;
					case 4:
						vo.setTypeName("奖励");
						break;
					}
					// 银行卡
					if (null != flow.getCardPan()) {
						vo.setCardPan(StringOpertion.hideBankCardLast4(flow.getCardPan()));
					}
					// vo.setTypeName(typeMap.get(flow.getType() + ""));
					voList.add(vo);
				}
				pageInfo.setRows(voList);
			}
		} catch (Exception e) {
			e.printStackTrace();
			LOG.error(e);
			pageInfo = new PageInfo();
			pageInfo.setCode(ResultInfo.SERVER_ERROR.getCode());
			pageInfo.setMsg(ResultInfo.SERVER_ERROR.getMsg());
		}
		return pageInfo;
	}

	/**
	 * 理财详情
	 * @param tId
	 *           tId
	 * @param token
	 *           token
	 * @return pageInfo
	 */
	@RequestMapping("/financeDetail")
	public @ResponseBody Object financeDetail(@RequestParam(required = true) Integer tId, String token) {
		LOG.info("/userfinance/financeDetail");
		PageInfo pageInfo = null;
		try {
			pageInfo = new PageInfo();
			pageInfo.setCode(ResultInfo.SUCCESS.getCode());
			pageInfo.setMsg(ResultInfo.SUCCESS.getMsg());
			String uId = redisGet.getRedisStringResult(AppCons.TOKEN_VERIFY + token).toString();
			pageInfo.setObj(userFinanceHomeService.findUserTradingDetail(tId, uId));
		} catch (Exception e) {
			e.printStackTrace();
			LOG.error(e);
			pageInfo = new PageInfo();
			pageInfo.setCode(ResultInfo.SERVER_ERROR.getCode());
			pageInfo.setMsg(ResultInfo.SERVER_ERROR.getMsg());
		}
		return pageInfo;
	}

	/**
	 * 消息详情
	 * @param mId
	 *           mId
	 * @param model
	 *          model
	 * @return string
	 */
	@RequestMapping("messageDetail")
	public String messageDetail(@RequestParam(required = true) Integer mId, Model model) {
		LOG.info("/userfinance/messageDetail");
		UsersMessage message = userFinanceHomeService.findUserMessageById(mId);
		model.addAttribute("message", message);
		return "usermessage";
	}

	/**
	 * 消息列表
	 * @param token
	 *           token
	 * @param pageNo
	 *           pageNo
	 * @param pageSize
	 *           pageSize
	 * @return pageInfo
	 */
	@RequestMapping("userMessage")
	public @ResponseBody Object userMessage(String token, Integer pageNo, Integer pageSize) {
		LOG.info("/userfinance/userMessage");
		PageInfo pageInfo = null;
		try {
			pageInfo = new PageInfo(pageNo, pageSize);
			pageInfo.setCode(ResultInfo.SUCCESS.getCode());
			pageInfo.setMsg(ResultInfo.SUCCESS.getMsg());
			String uId = redisGet.getRedisStringResult(AppCons.TOKEN_VERIFY + token).toString();
			userFinanceHomeService.findUserMessage(uId, pageInfo);
			List<?> rows = pageInfo.getRows();
			if (rows != null) {
				UsersMessage me = null;
				UserMessageVo vo = null;
				List<UserMessageVo> voList = new ArrayList<>();
				for (int i = 0; i < rows.size(); i++) {
					vo = new UserMessageVo();
					me = (UsersMessage) rows.get(i);
					vo.setId(me.getId());
					vo.setSender(me.getSender());
					vo.setStatus(me.getStatus());
					vo.setTime(DateUtils.getFormatDateString(me.getTime()));
					vo.setTopic(me.getTopic());
					voList.add(vo);
				}
				pageInfo.setRows(voList);
			}
		} catch (Exception e) {
			e.printStackTrace();
			LOG.error(e);
			pageInfo = new PageInfo();
			pageInfo.setCode(ResultInfo.SERVER_ERROR.getCode());
			pageInfo.setMsg(ResultInfo.SERVER_ERROR.getMsg());
		}
		return pageInfo;
	}

	/**
	 * 我的理财
	 * @param token
	 *            token
	 * @param pageNo
	 *            pageNo
	 * @param pageSize
	 *            pageSize
	 * @param status
	 *            status
	 * @return pageInfo
	 */
	@RequestMapping("/myFinance")
    public @ResponseBody Object myFinance(String token, Integer pageNo, Integer pageSize, Integer status,
            String multyStatus) {
		LOG.info("/userfinance/myFinance");
		PageInfo pageInfo = null;
		try {
			String tokenKey = AppCons.TOKEN_VERIFY + token;
			pageInfo = new PageInfo((pageNo == null || pageNo < ResultNumber.ONE.getNumber()) ? ResultNumber.ONE.getNumber() : pageNo,
					(pageSize == null || pageSize < ResultNumber.ONE.getNumber()) ? ResultNumber.TEN.getNumber() : pageSize);
            userFinanceHomeService.findTradingData(getUid(tokenKey), pageInfo,
                    status == null ? ResultNumber.MINUS_ONE.getNumber() : status, multyStatus);
		} catch (Exception e) {
			e.printStackTrace();
			LOG.error(e);
			pageInfo = new PageInfo();
			pageInfo.setCode(ResultInfo.SERVER_ERROR.getCode());
			pageInfo.setMsg(ResultInfo.SERVER_ERROR.getMsg());
		}
		return pageInfo;
	}

	/**
	 * 是否有消息
	 * @param token
	 *            token
	 * @return pageInfo
	 */
	@RequestMapping("/hasMessage")
	public @ResponseBody Object hasMessage(@RequestParam(required = true) String token) {
		LOG.info("/userfinance/hasMessage");
		PageInfo pageInfo = null;
		try {
			pageInfo = new PageInfo();
			pageInfo.setCode(ResultInfo.SUCCESS.getCode());
			pageInfo.setMsg(ResultInfo.SUCCESS.getMsg());
			String uId = redisGet.getRedisStringResult(AppCons.TOKEN_VERIFY + token).toString();
			pageInfo.setObj(userFinanceHomeService.hasMessage(uId));
		} catch (Exception e) {
			e.printStackTrace();
			LOG.error(e);
			pageInfo = new PageInfo();
			pageInfo.setCode(ResultInfo.SERVER_ERROR.getCode());
			pageInfo.setMsg(ResultInfo.SERVER_ERROR.getMsg());
		}
		return pageInfo;
	}

	/**
	 * 查询预期收益
	 * @param token
	 *            token
	 * @return pageInfo
	 */
	@RequestMapping("/expectEarnings")
	public @ResponseBody Object expectEarnings(@RequestParam(required = true) String token) {
		LOG.info("/userfinance/moneyFlow");
		PageInfo pageInfo = null;
		try {
			pageInfo = new PageInfo();
			pageInfo.setCode(ResultInfo.SUCCESS.getCode());
			pageInfo.setMsg(ResultInfo.SUCCESS.getMsg());
			String uId = redisGet.getRedisStringResult(AppCons.TOKEN_VERIFY + token).toString();
			// 查询预期收益
			pageInfo.setObj(userFinanceHomeService.findExpectEarnings(uId));
		} catch (Exception e) {
			e.printStackTrace();
			LOG.error(e);
			pageInfo = new PageInfo();
			pageInfo.setCode(ResultInfo.SERVER_ERROR.getCode());
			pageInfo.setMsg(ResultInfo.SERVER_ERROR.getMsg());
		}
		return pageInfo;
	}

	/**
	 * 当前理财
	 * @param token
	 *            token
	 * @return pageInfo
	 */
	@RequestMapping("/currentFinance")
	public @ResponseBody Object currentFinance(@RequestParam(required = true) String token) {
		LOG.info("/userfinance/currentFinance");
		PageInfo pageInfo = null;
		try {
			pageInfo = new PageInfo();
			pageInfo.setCode(ResultInfo.SUCCESS.getCode());
			pageInfo.setMsg(ResultInfo.SUCCESS.getMsg());
			String uId = redisGet.getRedisStringResult(AppCons.TOKEN_VERIFY + token).toString();
			// 查询理财明细
			pageInfo.setObj(userFinanceHomeService.findUserFinance(uId));
		} catch (Exception e) {
			e.printStackTrace();
			LOG.error(e);
			pageInfo = new PageInfo();
			pageInfo.setCode(ResultInfo.SERVER_ERROR.getCode());
			pageInfo.setMsg(ResultInfo.SERVER_ERROR.getMsg());
		}
		return pageInfo;
	}

	/**
	 * 查询账户余额
	 * @param token
	 *            token
	 * @return pageInfo
	 */
	@RequestMapping("/getUserBalance")
	public @ResponseBody Object getUserBalance(@RequestParam(required = true) String token) {
		LOG.info("/userfinance/getUserBalance");
		PageInfo pageInfo = null;
		try {
			pageInfo = new PageInfo();
			pageInfo.setCode(ResultInfo.SUCCESS.getCode());
			pageInfo.setMsg(ResultInfo.SUCCESS.getMsg());
			String uId = redisGet.getRedisStringResult(AppCons.TOKEN_VERIFY + token).toString();
			pageInfo.setObj(userFinanceHomeService.findUserBalance(uId));
		} catch (Exception e) {
			e.printStackTrace();
			LOG.error(e);
			pageInfo = new PageInfo();
			pageInfo.setCode(ResultInfo.SERVER_ERROR.getCode());
			pageInfo.setMsg(ResultInfo.SERVER_ERROR.getMsg());
		}
		return pageInfo;
	}

	/**
	 * 获取用户信息
	 * @param token
	 *           token
	 * @return pageInfo
	 */
	@RequestMapping("/getUserInfo")
	public @ResponseBody Object getUserInfo(@RequestParam(required = true) String token) {
		LOG.info("/userfinance/getUserInfo");
		PageInfo pageInfo = null;
		try {
			pageInfo = new PageInfo();
			pageInfo.setCode(ResultInfo.SUCCESS.getCode());
			pageInfo.setMsg(ResultInfo.SUCCESS.getMsg());
			String uId = redisGet.getRedisStringResult(AppCons.TOKEN_VERIFY + token).toString();
			pageInfo.setObj(userFinanceHomeService.finUserInfo(uId));
		} catch (Exception e) {
			e.printStackTrace();
			LOG.error(e);
			pageInfo = new PageInfo();
			pageInfo.setCode(ResultInfo.SERVER_ERROR.getCode());
			pageInfo.setMsg(ResultInfo.SERVER_ERROR.getMsg());
		}
		return pageInfo;
	}

	/**
	 * 查询产品可用加息券
	 * 
	 * @author 2017年7月12日
	 * @param request req
	 * @param pCode   pCode
	 * @param buyMoney buyMoney
	 * @param token token
	 * @return pi
	 */
	@RequestMapping("/queryFinancialAvailableRedPacket")
	public @ResponseBody Object redTest(HttpServletRequest request, String pCode, Double buyMoney, String token) {
		PageInfo pi = new PageInfo();
		try {
			String uId = redisGet.getRedisStringResult(AppCons.TOKEN_VERIFY + token).toString();
			pi.setObj(userRedPacketService.queryFinancialAvailableRedPacket(pCode,  uId,
					buyMoney, 1));
			pi.setCode(ResultInfo.SUCCESS.getCode());
			pi.setMsg("success");
		} catch (PageInfoException e) {
			pi.setCode(e.getCode());
			pi.setMsg(e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			pi.setCode(ResultInfo.SERVER_ERROR.getCode());
			pi.setMsg(ResultInfo.SERVER_ERROR.getMsg());
		}
		return pi;
	}

	/**
	 * 我的优惠券/红包页面
	 * 
	 * @param request  req
	 * @param type type
	 * @param hrp_type hrp_type
	 * @param pageSize pageSize
	 * @param pageNo pageNo 
	 * @param token token
	 * @return pi
	 */
	@RequestMapping("/redPacket")
	public @ResponseBody Object redPacket(HttpServletRequest request, Integer type, Integer hrp_type, Integer pageSize, Integer pageNo,
			String token) {
		PageInfo pi = new PageInfo();
		try {
			String uid = utilService.queryUid(AppCons.TOKEN_VERIFY + token);
			pi = userRedPacketService.queryUserRedPacketByType(uid, type, hrp_type, pageSize, pageNo);
		} catch (PageInfoException e) { 
			pi.setCode(e.getCode());
			pi.setMsg(e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			pi.setCode(ResultInfo.SERVER_ERROR.getCode());
			pi.setMsg(ResultInfo.SERVER_ERROR.getMsg());
		}
		return pi;
	}

	/**
	 * 随心取交易流水
	 * 
	 * @param tId tId
	 * @param yearmonth yearmonth
	 * @param token token
	 * @return pageInfo
	 */
	@RequestMapping("/findTakeheartTransactionFlow")
	public @ResponseBody Object findTakeheartTransactionFlow(Integer tId, String token, String yearmonth) {
		PageInfo pageInfo = new PageInfo();
		try {
			String tokenKey = AppCons.TOKEN_VERIFY + token;
			pageInfo = userFinanceHomeService.findTakeheartTransactionFlow(getUid(tokenKey), pageInfo, tId, yearmonth);
		} catch (Exception e) {
			e.printStackTrace();
			pageInfo.setCode(ResultInfo.SERVER_ERROR.getCode());
			pageInfo.setMsg(ResultInfo.SERVER_ERROR.getMsg());
		}
		return pageInfo;
	}

	/**
	 * 随心取赎回确认
	 * 
	 * @param tId
	 *            交易ID
	 * @param moneyInput
	 *            赎回份额
	 * @param redeemType
	 *            redeemType
	 * @param token
	 *            token
	 * @return pageInfo
	 */
	@RequestMapping("/sellProduct")
	@ResponseBody
	public Object sellProduct(Integer tId, Double moneyInput, Integer redeemType, String token) {
		PageInfo pageInfo = new PageInfo();
		try {
			String logGroup = "\nsellProduct" + System.currentTimeMillis() + "_";
			String tokenKey = AppCons.TOKEN_VERIFY + token;
			String uId = getUid(tokenKey);
			LOG.info(logGroup + "用户" + uId + "卖出 " + tId + "号理财");
			SellResult sellResult = tradingService.sellProduct(uId, tId, moneyInput, redeemType, logGroup);
			if (sellResult.getFlag() == ResultNumber.ZERO.getNumber()) { // 卖出成功
				pageInfo.setCode(ResultInfo.SUCCESS.getCode());
				pageInfo.setMsg("卖出成功");
			} else {
				pageInfo.setCode(Integer.valueOf(sellResult.getCode()));
				pageInfo.setMsg(sellResult.getMsg());
			}
		} catch (Exception e) {
			e.printStackTrace();
			pageInfo.setCode(ResultInfo.SERVER_ERROR.getCode());
			pageInfo.setMsg(ResultInfo.SERVER_ERROR.getMsg());
		}
		return pageInfo;

	}

	/**
	 * 查询用户冻结金额
	 * 
	 * @param token
	 *            token
	 */
	@RequestMapping("/getUserFreeingAmount")
	public @ResponseBody Object getUserFreeingAmount(@RequestParam(required = true) String token) {
		String uId = redisGet.getRedisStringResult(AppCons.TOKEN_VERIFY + token).toString();
		PageInfo pageInfo = null;
		try {
			pageInfo = new PageInfo();
			pageInfo.setCode(ResultInfo.SUCCESS.getCode());
			pageInfo.setMsg(ResultInfo.SUCCESS.getMsg());
			pageInfo.setObj(userFinanceHomeService.getUserFreeingAmount(uId));
		} catch (Exception e) {
			e.printStackTrace();
			LOG.error(e);
			pageInfo = new PageInfo();
			pageInfo.setCode(ResultInfo.SERVER_ERROR.getCode());
			pageInfo.setMsg(ResultInfo.SERVER_ERROR.getMsg());
		}
		return pageInfo;
	}

    /**
     * 获取回款日历数据
     * 
     * @author yiban
     * @date 2018年3月15日 上午10:32:56
     * @version 1.0
     * @param token token
     * @param date 日期 格式 （yyyy-MM）
     * @return
     *
     */
    @RequestMapping(value = "/getRepaymentCalendar", method = RequestMethod.POST)
    @ResponseBody
    public Object getRepaymentCalendar(String token, String date) {
        PageInfo pageInfo = new PageInfo();

        try {
            String uId = getUid(AppCons.TOKEN_VERIFY + token);
            if (!StringUtils.isEmpty(uId)) {
                pageInfo = userFinanceHomeService.getRepaymentCalendar(uId, date);

            } else {
                pageInfo.setResultInfo(ResultInfo.LOGIN_OVERTIME);
            }
        } catch (Exception e) {
            pageInfo.setResultInfo(ResultInfo.SERVER_ERROR);
            e.printStackTrace();
        }

        return pageInfo;
    }

    /**
     * 获取陪伴天数
     * 
     * @author yiban
     * @date 2018年3月15日 上午10:32:56
     * @version 1.0
     * @param token token
     * @return
     *
     */
    @RequestMapping(value = "/getAccompanyDays")
    @ResponseBody
    public Object getAccompanyDays(String token) {
        PageInfo pageInfo = new PageInfo();

        // 当期算个死时间
        String startdate = "2014-12-02 00:00:00";
        Date date = DateUtils.parseDate(startdate);
        Map<String, String> resultMap = new HashMap<String, String>();
        resultMap.put("front", "今天是领钱儿陪伴您第");
        resultMap.put("days", DateUtils.dateDiff(date, new Date()) + "");
        resultMap.put("behind", "天");
        pageInfo.setResultInfo(ResultInfo.SUCCESS);
        pageInfo.setObj(resultMap);
        //        String uId = getUid(AppCons.TOKEN_VERIFY + token);
        //        if (!StringUtils.isEmpty(uId)) {
        //            pageInfo = userFinanceHomeService.getAccompanyDays(uId);
        //
        //        } else {
        //            pageInfo.setResultInfo(ResultInfo.LOGIN_OVERTIME);
        //        }

        return pageInfo;
    }

    /**
     * 查询冻结金额明细
     * @author yiban
     * @date 2018年3月18日 下午2:42:03
     * @version 1.0
     * @param token
     * @param pageNo
     * @param pageSize
     * @return
     **/
    @RequestMapping(value = "/listFrozenMoneyDetail", method = RequestMethod.POST)
    @ResponseBody
    public Object listFrozenMoneyDetail(String token, Integer pageNo, Integer pageSize) {
        PageInfo pageInfo = new PageInfo();

        try {
            String uid = getUid(AppCons.TOKEN_VERIFY + token);
            if (!StringUtils.isEmpty(uid)) {
                pageInfo = userFinanceHomeService.listFrozenMoneyDetail(uid, pageNo, pageSize);

            } else {
                pageInfo.setResultInfo(ResultInfo.LOGIN_OVERTIME);
            }
        } catch (Exception e) {
            pageInfo.setResultInfo(ResultInfo.SERVER_ERROR);
            e.printStackTrace();
        }

        return pageInfo;
    }

}
