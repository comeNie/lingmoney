package com.mrbt.lingmoney.web2.controller.userfinance;

import java.util.ArrayList;
import java.util.List;

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
import com.mrbt.lingmoney.service.users.UsersAccountSetService;
import com.mrbt.lingmoney.utils.DateUtils;
import com.mrbt.lingmoney.utils.PageInfo;
import com.mrbt.lingmoney.utils.ResultParame.ResultInfo;
import com.mrbt.lingmoney.utils.ResultParame.ResultNumber;
import com.mrbt.lingmoney.utils.StringOpertion;
import com.mrbt.lingmoney.web2.controller.BaseController;
import com.mrbt.lingmoney.web2.controller.common.CommonMethodUtil;
import com.mrbt.lingmoney.web2.vo.userfinance.AccountFlowVo;
import com.mrbt.lingmoney.web2.vo.userfinance.UserMessageVo;

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
	
	/**
	 * 查询用户理财进程
	 * @param request request
	 * @return pageInfo
	 */ 
	@RequestMapping("/userFinProcess")
	public @ResponseBody Object userFinProcess(HttpServletRequest request) {
		LOG.info("/userfinance/myCode");
        PageInfo pageInfo = new PageInfo();
		try {
			String uId = CommonMethodUtil.getUidBySession(request);
            if (StringUtils.isEmpty(uId)) {
                pageInfo.setResultInfo(ResultInfo.LOGIN_OVERTIME);
            } else {
            	userFinanceHomeService.queryUserFinProcess(uId, pageInfo);
                pageInfo.setMsg("查询成功");
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
	 * @param request	request
	 * @param pageNo	页数
	 * @param pageSize	每页条数
	 * @param type	单类型	
	 * @param multyTypes	多类型
	 * @return
	 */
	@RequestMapping("moneyFlow")
	public @ResponseBody Object moneyFlow(HttpServletRequest request, Integer pageNo, Integer pageSize, Integer type, String multyTypes) {
		LOG.info("/userfinance/moneyFlow");
		PageInfo pageInfo = null;
		try {
			pageInfo = new PageInfo(pageNo, pageSize);
			String uId = CommonMethodUtil.getUidBySession(request);
            if (StringUtils.isEmpty(uId)) {
                pageInfo.setResultInfo(ResultInfo.LOGIN_OVERTIME);
            } else {
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
	 * @param mId  mId
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
	 * @param pageNo 页数
	 * @param pageSize 条数
	 * @param status 类型
	 * @param multyStatus 多类型
	 * @return pageInfo
	 */
	@RequestMapping("/myFinance")
	public @ResponseBody Object myFinance(Integer pageNo, Integer pageSize, Integer status, HttpServletRequest request, String multyStatus) {
		LOG.info("/userfinance/myFinance");
        PageInfo pageInfo = new PageInfo(pageNo, pageSize);
		try {
			String uId = CommonMethodUtil.getUidBySession(request);
            if (StringUtils.isEmpty(uId)) {
                pageInfo.setResultInfo(ResultInfo.LOGIN_OVERTIME);
            } else {
                userFinanceHomeService.findTradingData(uId, pageInfo, status == null ? ResultNumber.MINUS_ONE.getNumber() : status, multyStatus);
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
	 * @param request request
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
	 * @param uId 用户ID
	 * @param type 0未使用 1已使用 2 已过期 3全部
	 * @param hrpType 类型：1-加息券，2-返现红包
	 * @param pageSize 分页条数
	 * @param pageNo 分页页数
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
	
	/**
     * 获取回款日历数据
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
    public Object getRepaymentCalendar(HttpServletRequest request, String date) {
        PageInfo pageInfo = new PageInfo();

        try {
        	String uId = CommonMethodUtil.getUidBySession(request);
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
}
