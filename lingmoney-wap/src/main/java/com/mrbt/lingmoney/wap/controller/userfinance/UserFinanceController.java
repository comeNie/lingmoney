package com.mrbt.lingmoney.wap.controller.userfinance;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mrbt.lingmoney.commons.exception.PageInfoException;
import com.mrbt.lingmoney.model.AccountFlow;
import com.mrbt.lingmoney.model.SellResult;
import com.mrbt.lingmoney.model.UsersMessage;
import com.mrbt.lingmoney.service.trading.TradingService;
import com.mrbt.lingmoney.service.userfinance.UserFinanceHomeService;
import com.mrbt.lingmoney.service.users.UserRedPacketService;
import com.mrbt.lingmoney.utils.DateUtils;
import com.mrbt.lingmoney.utils.PageInfo;
import com.mrbt.lingmoney.utils.ResultParame.ResultInfo;
import com.mrbt.lingmoney.utils.ResultParame.ResultNumber;
import com.mrbt.lingmoney.utils.StringOpertion;
import com.mrbt.lingmoney.wap.controller.BaseController;
import com.mrbt.lingmoney.wap.controller.common.CommonMethodUtil;
import com.mrbt.lingmoney.wap.vo.userfinance.AccountFlowVo;
import com.mrbt.lingmoney.wap.vo.userfinance.UserMessageVo;

/**
 * 我的理财首页
 * 
 * @author wzy
 * @Date 2017年12月22日
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
	
	private static Map<String, String> typeMap = new HashMap<String, String>();
	
	static {
		typeMap.put("0", "充值");
		typeMap.put("1", "取现");
		typeMap.put("2", "理财");
		typeMap.put("3", "赎回");
	}

	/**
	 * 我的码
	 * @param request
	 *            request
	 * @return pageInfo
	 */ 
	@RequestMapping("/myCode")
	public @ResponseBody Object myCode(HttpServletRequest request) {
		LOG.info("/userfinance/myCode");
        PageInfo pageInfo = new PageInfo();
		try {
			String uId = CommonMethodUtil.getUidBySession(request);
            if (StringUtils.isEmpty(uId)) {
                pageInfo.setResultInfo(ResultInfo.LOGIN_OVERTIME);
            } else {
                pageInfo.setObj(userFinanceHomeService.findUserReferralCode(uId, request));
                pageInfo.setResultInfo(ResultInfo.SUCCESS);
            }

		} catch (Exception e) {
			e.printStackTrace();
			LOG.error(e);
			pageInfo.setCode(ResultInfo.SERVER_ERROR.getCode());
			pageInfo.setMsg(ResultInfo.SERVER_ERROR.getMsg());
		}

		return pageInfo;
	}

	/**
	 * 资金流水
	 * @param request
	 *            request
	 * @param pageNo
	 *            pageNo
	 * @param pageSize
	 *            pageSize
	 * @param type
	 *            type
	 * @return pageInfo
	 */
	@RequestMapping("moneyFlow")
	public @ResponseBody Object moneyFlow(HttpServletRequest request, Integer pageNo, Integer pageSize, Integer type) {
		LOG.info("/userfinance/moneyFlow");
		PageInfo pageInfo = null;
		try {
			pageInfo = new PageInfo(pageNo, pageSize);
			String uId = CommonMethodUtil.getUidBySession(request);
            if (StringUtils.isEmpty(uId)) {
                pageInfo.setResultInfo(ResultInfo.LOGIN_OVERTIME);
            } else {
                userFinanceHomeService.findMoneyFlow(uId, pageInfo, type, null);
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
                        voList.add(vo);
                    }
                    pageInfo.setRows(voList);
                }
                pageInfo.setResultInfo(ResultInfo.SUCCESS);
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
	 * @return pageInfo
	 */
	@RequestMapping("/financeDetail")
	public @ResponseBody Object financeDetail(@RequestParam(required = true) Integer tId, HttpServletRequest request) {
		LOG.info("/userfinance/financeDetail");
        PageInfo pageInfo = new PageInfo();
		try {
			String uId = CommonMethodUtil.getUidBySession(request);
            if (StringUtils.isEmpty(uId)) {
                pageInfo.setResultInfo(ResultInfo.LOGIN_OVERTIME);
            } else {
                pageInfo.setObj(userFinanceHomeService.findUserTradingDetail(tId, uId));
                pageInfo.setResultInfo(ResultInfo.SUCCESS);
            }

		} catch (Exception e) {
			e.printStackTrace();
			LOG.error(e);
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
    @ResponseBody
    public Object messageDetail(@RequestParam(required = true) Integer mId) {
		LOG.info("/userfinance/messageDetail");
        PageInfo pi = new PageInfo();
		UsersMessage message = userFinanceHomeService.findUserMessageById(mId);
        if (message != null) {
            pi.setResultInfo(ResultInfo.SUCCESS);
            pi.setObj(message);
        } else {
            pi.setResultInfo(ResultInfo.NO_DATA);
        }
        return pi;
	}

	/**
	 * 消息列表
	 * @param pageNo
	 *           pageNo
	 * @param pageSize
	 *           pageSize
	 * @return pageInfo
	 */
	@RequestMapping("userMessage")
	public @ResponseBody Object userMessage(Integer pageNo, Integer pageSize, HttpServletRequest request) {
		LOG.info("/userfinance/userMessage");
        PageInfo pageInfo = new PageInfo(pageNo, pageSize);
		try {
			String uId = CommonMethodUtil.getUidBySession(request);
            if (StringUtils.isEmpty(uId)) {
                pageInfo.setResultInfo(ResultInfo.LOGIN_OVERTIME);
            } else {
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
                pageInfo.setResultInfo(ResultInfo.SUCCESS);
            }

		} catch (Exception e) {
			e.printStackTrace();
			LOG.error(e);
			pageInfo.setCode(ResultInfo.SERVER_ERROR.getCode());
			pageInfo.setMsg(ResultInfo.SERVER_ERROR.getMsg());
		}
		return pageInfo;
	}

	/**
	 * 我的理财
	 * @param pageNo
	 *            pageNo
	 * @param pageSize
	 *            pageSize
	 * @param status
	 *            status
	 * @return pageInfo
	 */
	@RequestMapping("/myFinance")
	public @ResponseBody Object myFinance(Integer pageNo, Integer pageSize, Integer status, HttpServletRequest request) {
		LOG.info("/userfinance/myFinance");
        PageInfo pageInfo = new PageInfo(pageNo, pageSize);
		try {
			String uId = CommonMethodUtil.getUidBySession(request);
            if (StringUtils.isEmpty(uId)) {
                pageInfo.setResultInfo(ResultInfo.LOGIN_OVERTIME);
            } else {
                userFinanceHomeService.findTradingData(uId, pageInfo,
                        status == null ? ResultNumber.MINUS_ONE.getNumber() : status, null);
            }

		} catch (Exception e) {
			e.printStackTrace();
			LOG.error(e);
			pageInfo.setCode(ResultInfo.SERVER_ERROR.getCode());
			pageInfo.setMsg(ResultInfo.SERVER_ERROR.getMsg());
		}

		return pageInfo;
	}

	/**
	 * 是否有消息
	 * @param request
	 *            request
	 * @return pageInfo
	 */
	@RequestMapping("/hasMessage")
	public @ResponseBody Object hasMessage(HttpServletRequest request) {
		LOG.info("/userfinance/hasMessage");
        PageInfo pageInfo = new PageInfo();
		try {
			String uId = CommonMethodUtil.getUidBySession(request);
            if (StringUtils.isEmpty(uId)) {
                pageInfo.setResultInfo(ResultInfo.LOGIN_OVERTIME);
            } else {
                pageInfo.setResultInfo(ResultInfo.SUCCESS);
                pageInfo.setObj(userFinanceHomeService.hasMessage(uId));
            }

		} catch (Exception e) {
			e.printStackTrace();
			LOG.error(e);
			pageInfo.setCode(ResultInfo.SERVER_ERROR.getCode());
			pageInfo.setMsg(ResultInfo.SERVER_ERROR.getMsg());
		}

		return pageInfo;
	}

	/**
	 * 查询预期收益
	 * @param request
	 *            request
	 * @return pageInfo
	 */
	@RequestMapping("/expectEarnings")
	public @ResponseBody Object expectEarnings(HttpServletRequest request) {
		LOG.info("/userfinance/moneyFlow");
        PageInfo pageInfo = new PageInfo();
		try {
			// 查询预期收益
			String uId = CommonMethodUtil.getUidBySession(request);
            if (StringUtils.isEmpty(uId)) {
                pageInfo.setResultInfo(ResultInfo.LOGIN_OVERTIME);
            } else {
                pageInfo.setObj(userFinanceHomeService.findExpectEarnings(uId));
                pageInfo.setResultInfo(ResultInfo.SUCCESS);
            }

		} catch (Exception e) {
			e.printStackTrace();
			LOG.error(e);
			pageInfo.setCode(ResultInfo.SERVER_ERROR.getCode());
			pageInfo.setMsg(ResultInfo.SERVER_ERROR.getMsg());
		}

		return pageInfo;
	}

	/**
	 * 当前理财
	 * @param request
	 *            request
	 * @return pageInfo
	 */
	@RequestMapping("/currentFinance")
	public @ResponseBody Object currentFinance(HttpServletRequest request) {
		LOG.info("/userfinance/currentFinance");
        PageInfo pageInfo = new PageInfo();
		try {
			String uId = CommonMethodUtil.getUidBySession(request);
            if (StringUtils.isEmpty(uId)) {
                pageInfo.setResultInfo(ResultInfo.LOGIN_OVERTIME);
            } else {
                // 查询理财明细
                pageInfo.setObj(userFinanceHomeService.findUserFinance(uId));
                pageInfo.setResultInfo(ResultInfo.SUCCESS);
            }

		} catch (Exception e) {
			e.printStackTrace();
			LOG.error(e);
			pageInfo.setCode(ResultInfo.SERVER_ERROR.getCode());
			pageInfo.setMsg(ResultInfo.SERVER_ERROR.getMsg());
		}
		return pageInfo;
	}

	/**
	 * 查询账户余额
	 * @param request
	 *           request
	 * @return pageInfo
	 */
	@RequestMapping("/getUserBalance")
	public @ResponseBody Object getUserBalance(HttpServletRequest request) {
		LOG.info("/userfinance/getUserBalance");
        PageInfo pageInfo = new PageInfo();
		try {
			String uId = CommonMethodUtil.getUidBySession(request);
            if (StringUtils.isEmpty(uId)) {
                pageInfo.setResultInfo(ResultInfo.LOGIN_OVERTIME);
            } else {
                pageInfo.setResultInfo(ResultInfo.SUCCESS);
                pageInfo.setObj(userFinanceHomeService.findUserBalance(uId));
            }
		} catch (Exception e) {
			e.printStackTrace();
			LOG.error(e);
			pageInfo.setCode(ResultInfo.SERVER_ERROR.getCode());
			pageInfo.setMsg(ResultInfo.SERVER_ERROR.getMsg());
		}
		return pageInfo;
	}

	/**
	 * 获取用户信息
	 * @param request
	 *           request
	 * @return pageInfo
	 */
	@RequestMapping("/getUserInfo")
	public @ResponseBody Object getUserInfo(HttpServletRequest request) {
		LOG.info("/userfinance/getUserInfo");
        PageInfo pageInfo = new PageInfo();
		try {
			String uId = CommonMethodUtil.getUidBySession(request);
            if (StringUtils.isEmpty(uId)) {
                pageInfo.setResultInfo(ResultInfo.LOGIN_OVERTIME);
            } else {
                pageInfo.setObj(userFinanceHomeService.finUserInfo(uId));
                pageInfo.setResultInfo(ResultInfo.SUCCESS);
            }
		} catch (Exception e) {
			e.printStackTrace();
			LOG.error(e);
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
	 * @return pi
	 */
	@RequestMapping("/queryFinancialAvailableRedPacket")
	public @ResponseBody Object redTest(HttpServletRequest request, String pCode, Double buyMoney) {
		PageInfo pi = new PageInfo();
		try {
			String uId = CommonMethodUtil.getUidBySession(request);
            if (StringUtils.isEmpty(uId)) {
                pi.setResultInfo(ResultInfo.LOGIN_OVERTIME);
            } else {
                pi.setObj(userRedPacketService.queryFinancialAvailableRedPacket(pCode, uId, buyMoney, 1));
                pi.setCode(ResultInfo.SUCCESS.getCode());
                pi.setMsg("success");
            }

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
	 * @param request
	 *            req
	 * @param type
	 *            type
	 * @param hrpType
	 *            hrpType
	 * @param pageSize
	 *            pageSize
	 * @param pageNo
	 *            pageNo
	 * @return pi
	 */
	@RequestMapping("/redPacket")
	public @ResponseBody Object redPacket(HttpServletRequest request, Integer type, Integer hrpType, Integer pageSize,
            Integer pageNo) {
		PageInfo pi = new PageInfo();
		try {
			String uId = CommonMethodUtil.getUidBySession(request);
            if (StringUtils.isEmpty(uId)) {
                pi.setResultInfo(ResultInfo.LOGIN_OVERTIME);
            } else {
                pi = userRedPacketService.queryUserRedPacketByType(uId, type, hrpType, pageSize, pageNo);
            }
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
	 * 已使用/已过期卡劵
	 * 
	 * @param request
	 *            req
	 * @param type
	 *            type
	 * @param pageSize
	 *            pageSize
	 * @param pageNo
	 *            pageNo
	 * @return pi
	 */
	@RequestMapping("/redPacketUsedInfo")
	public @ResponseBody Object redPacketUsedInfo(HttpServletRequest request, Integer type, Integer pageSize,
			Integer pageNo) {
		PageInfo pi = new PageInfo();
		try {
			String uId = CommonMethodUtil.getUidBySession(request);
            if (StringUtils.isEmpty(uId)) {
                pi.setResultInfo(ResultInfo.LOGIN_OVERTIME);
            } else {
                pi = userRedPacketService.queryUserRedPacketByTypeOfWap(uId, type, pageSize, pageNo);
            }
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
     * @param yearmonth 日期格式：年-月 ex: 2014-10
     * @return pageInfo
     */
	@RequestMapping("/findTakeheartTransactionFlow")
	public @ResponseBody Object findTakeheartTransactionFlow(HttpServletRequest request, Integer tId, String yearmonth) {
		PageInfo pageInfo = new PageInfo();
		try {
			String uId = CommonMethodUtil.getUidBySession(request);
            if (StringUtils.isEmpty(uId)) {
                pageInfo.setResultInfo(ResultInfo.LOGIN_OVERTIME);
            } else {
                pageInfo = userFinanceHomeService.findTakeheartTransactionFlow(uId, pageInfo, tId, yearmonth);
            }
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
     *            redeemType 1 部分赎回 2 全部赎回
     * @return pageInfo
     */
    @RequestMapping("/userfinance/sellProduct")
	@ResponseBody
	public Object sellProduct(Integer tId, Double moneyInput, Integer redeemType, HttpServletRequest request) {
		PageInfo pageInfo = new PageInfo();
		try {
			String logGroup = "\nsellProduct" + System.currentTimeMillis() + "_";
			String uId = CommonMethodUtil.getUidBySession(request);
            if (StringUtils.isEmpty(uId)) {
                pageInfo.setResultInfo(ResultInfo.LOGIN_OVERTIME);
            } else {
                LOG.info(logGroup + "用户" + uId + "卖出 " + tId + "号理财");
                SellResult sellResult = tradingService.sellProduct(uId, tId, moneyInput, redeemType, logGroup);
                if (sellResult.getFlag() == ResultNumber.ZERO.getNumber()) { // 卖出成功
                    pageInfo.setCode(ResultInfo.SUCCESS.getCode());
                    pageInfo.setMsg("卖出成功");
                } else {
                    pageInfo.setCode(Integer.valueOf(sellResult.getCode()));
                    pageInfo.setMsg(sellResult.getMsg());
                }
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
	 */
	@RequestMapping("/getUserFreeingAmount")
	public @ResponseBody Object getUserFreeingAmount(HttpServletRequest request) {
        PageInfo pageInfo = new PageInfo();
		try {
			String uId = CommonMethodUtil.getUidBySession(request);
            if (StringUtils.isEmpty(uId)) {
                pageInfo.setResultInfo(ResultInfo.LOGIN_OVERTIME);
            } else {
                pageInfo.setCode(ResultInfo.SUCCESS.getCode());
                pageInfo.setObj(userFinanceHomeService.getUserFreeingAmount(uId));
            }

		} catch (Exception e) {
			e.printStackTrace();
			LOG.error(e);
			pageInfo.setCode(ResultInfo.SERVER_ERROR.getCode());
			pageInfo.setMsg(ResultInfo.SERVER_ERROR.getMsg());
		}
		return pageInfo;
	}
}
