package com.mrbt.lingmoney.web.controller.users;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mrbt.lingmoney.commons.exception.PageInfoException;
import com.mrbt.lingmoney.model.TakeheartTransactionFlow;
import com.mrbt.lingmoney.model.UsersAccount;
import com.mrbt.lingmoney.service.userfinance.UserFinanceHomeService;
import com.mrbt.lingmoney.service.users.UserRedPacketService;
import com.mrbt.lingmoney.service.users.UsersAccountSetService;
import com.mrbt.lingmoney.service.users.UsersService;
import com.mrbt.lingmoney.service.web.MyFinanceService;
import com.mrbt.lingmoney.utils.PageInfo;
import com.mrbt.lingmoney.utils.ResultParame;
import com.mrbt.lingmoney.utils.ResultParame.ResultInfo;
import com.mrbt.lingmoney.web.controller.common.CommonMethodUtil;

/**
 * @author syb
 * @date 2017年5月10日 下午1:57:53
 * @version 1.0
 * @description 我的理财
 **/
@Controller
@RequestMapping("/myFinancial")
public class MyFinancialContorller {
	private static final Logger LOG = LogManager.getLogger(MyFinancialContorller.class);

	@Autowired
	private MyFinanceService myFinanceService;
	@Autowired
	private UsersService usersService;
	@Autowired
	private UsersAccountSetService usersAccountSetService;
	@Autowired
	private UserFinanceHomeService userFinanceHomeService;
	@Autowired
	private UserRedPacketService userRedPacketService;

	/**
	 * 我的理财 首页
	 * 
	 * @param request
	 *            请求
	 * @param model
	 *            数据包装
	 * @param status
	 *            理财状态 默认 理财中
	 * @param pageNo
	 *            页数
	 * @return 返回信息
	 */
	@RequestMapping("/finanCialManage")
	public String listManager(HttpServletRequest request, ModelMap model, Integer status, Integer pageNo) {
		String uid = CommonMethodUtil.getUidBySession(request);

		if (!StringUtils.isEmpty(uid)) {
			try {
				myFinanceService.packageFinancialManageInfo(model, status, pageNo, uid);
				CommonMethodUtil.modelUserBaseInfo(model, request, usersService);

			} catch (Exception e) {
				LOG.error("获取我的理财数据失败" + e.getMessage());
				e.printStackTrace();
				return String.valueOf(ResultParame.ResultInfo.SERVER_ERROR.getCode());
			}

		} else {
			return "users/login";
		}

		return "myLingqian/financeManage";
	}

	/**
	 * 账户流水
	 * 
	 * @param request
	 *            请求
	 * @param type
	 *            资金类型
	 * @param timeType
	 *            时间范围
	 * @param beginTime
	 *            开始时间
	 * @param endTime
	 *            结束时间
	 * @param model
	 *            数据包装
	 * @param pageNo
	 *            分页页数
	 * @return 返回信息
	 */
	@RequestMapping("/accountFlow")
	public String accountFlow(HttpServletRequest request, Integer type, String timeType, String beginTime,
			String endTime, ModelMap model, Integer pageNo) {
		String uid = CommonMethodUtil.getUidBySession(request);

		try {
			if (!StringUtils.isEmpty(uid)) {
				myFinanceService.packageAccountFlowInfo(model, type, uid, timeType, beginTime, endTime, pageNo);
			} else {
				return "users/login";
			}
			CommonMethodUtil.modelUserBaseInfo(model, request, usersService);

		} catch (Exception e) {
			LOG.info("获取账户流水失败" + e.getMessage());
			e.printStackTrace();
			return String.valueOf(ResultParame.ResultInfo.SERVER_ERROR.getCode());
		}

		return "myLingqian/accountFlow";
	}

	/**
	 * 我的资产 页面
	 * 
	 * @param request
	 *            请求
	 * @param model
	 *            数据包装
	 * @return 返回信息
	 * 
	 */
	@RequestMapping("/myMoney")
	public String myMoney(HttpServletRequest request, ModelMap model) {
		String uid = CommonMethodUtil.getUidBySession(request);
		if (!StringUtils.isEmpty(uid)) {
			try {
				PageInfo accountInfo = usersAccountSetService.queryUserAccountByUid(uid);
				if (accountInfo.getCode() == ResultParame.ResultInfo.SUCCESS.getCode()) {
					UsersAccount userAccount = (UsersAccount) accountInfo.getObj();
					model.addAttribute("totalFinance", userAccount.getTotalFinance());
					model.addAttribute("income", userAccount.getIncome());

				} else {
					LOG.error("获取我的资产信息失败，未查询到该账户信息." + uid);
					return String.valueOf(ResultParame.ResultInfo.SERVER_ERROR.getCode());
				}
				CommonMethodUtil.modelUserBaseInfo(model, request, usersService);

			} catch (Exception e) {
				LOG.error("获取我的资产信息失败，系统错误。" + e.getMessage());
				e.printStackTrace();
				return String.valueOf(ResultParame.ResultInfo.SERVER_ERROR.getCode());
			}

		} else {
			return "users/login";
		}

		return "myLingqian/myMoney";
	}

	/**
	 * 我的资产--折线图数据
	 * 
	 * @param dateTime
	 *            日期
	 * @param request
	 *            请求
	 * @param response
	 *            响应
	 */
	@RequestMapping("/getMyMoneyData")
	public void getData(String dateTime, HttpServletRequest request, HttpServletResponse response) {
		String uid = CommonMethodUtil.getUidBySession(request);
		try {
			JSONObject jsonObject = myFinanceService.getMyMoneyData(dateTime, uid);
			jsonObject.write(response.getWriter());
		} catch (IOException e) {
			LOG.error("获取我的理财数据失败，系统错误" + e.getMessage());
			e.printStackTrace();
		}
	}

	/**
	 * 查询随心取交易流水
	 * 
	 * @param pId
	 *            产品id
	 * @param tId
	 *            交易id
	 * @param limit
	 *            条数
	 * @param request
	 *            请求
	 * @param response
	 *            响应
	 */
	@RequestMapping("/takeheartTransactionFlow")
	public void takeheartTransactionFlow(Integer pId, Integer tId, Integer limit, HttpServletRequest request,
			HttpServletResponse response) {
		response.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=utf-8");

		JSONObject jsonObject = new JSONObject();

		try {
			String uid = CommonMethodUtil.getUidBySession(request);
			if (!StringUtils.isEmpty(uid)) {
				PageInfo pi = userFinanceHomeService.findTakeHeartTransactionFlow(uid, pId, tId, limit);
				if (pi.getCode() == ResultParame.ResultInfo.SUCCESS.getCode()) {
					@SuppressWarnings("unchecked")
					List<TakeheartTransactionFlow> list = pi.getRows();
					JSONArray jsonArray = JSONArray.fromObject(list);
					jsonObject.put("data", jsonArray);

				} else {
					jsonObject.put("data", null);
				}

			} else {
				jsonObject.put("data", null);
			}

			jsonObject.write(response.getWriter());

		} catch (IOException e) {
			e.printStackTrace();
			LOG.error("查询随心取流水失败，系统错误。 \n " + e.getMessage());
		}
	}

	/**
	 * 账户充值
	 * 
	 * @param request
	 *            请求
	 * @param model
	 *            数据包装
	 * @return 返回信息
	 */
	@RequestMapping("/accountRecharge")
	public String accountRecharge(HttpServletRequest request, ModelMap model) {
		String uid = CommonMethodUtil.getUidBySession(request);
		
		if (!StringUtils.isEmpty(uid)) {
			try {
				myFinanceService.packageRechargeWithdraw(model, uid);
				CommonMethodUtil.modelUserBaseInfo(model, request, usersService);
			
			} catch (Exception e) {
				LOG.error("获取我的理财数据失败" + e.getMessage());
				e.printStackTrace();
				return String.valueOf(ResultParame.ResultInfo.SERVER_ERROR.getCode());
			}

		} else {
			return "users/login";
		}

		return "myLingqian/accountRecharge";
	}

	/**
	 * 提现
	 * 
	 * @param request
	 *            请求
	 * @param model
	 *            数据包装
	 * @return 返回信息
	 */
	@RequestMapping("/accountWithdraw")
	public String withdraw(HttpServletRequest request, ModelMap model) {
		String uid = CommonMethodUtil.getUidBySession(request);
		
		if (!StringUtils.isEmpty(uid)) {
			try {
				myFinanceService.packageRechargeWithdraw(model, uid);
				CommonMethodUtil.modelUserBaseInfo(model, request, usersService);
			
			} catch (Exception e) {
				LOG.error("获取我的理财数据失败" + e.getMessage());
				e.printStackTrace();
				return String.valueOf(ResultParame.ResultInfo.SERVER_ERROR.getCode());
			}
			
		} else {
			return "users/login";
		}
		
		return "myLingqian/accountWithdraw";
	}

	/**
	 * 我的优惠券页面
	 * 
	 * @param request
	 *            请求
	 * @param model
	 *            数据包装
	 * @param type
	 *            类型
	 * @param hrpType
	 *            红包类型
	 * @param pageSize
	 *            每页显示数量
	 * @param pageNo
	 *            当前页数
	 * @return 返回信息
	 */
	@RequestMapping("/redPacket")
	public String redPacket(HttpServletRequest request, ModelMap model, Integer type, Integer hrpType, Integer pageSize,
			Integer pageNo) {
		String uid = CommonMethodUtil.getUidBySession(request);
		
		if (!StringUtils.isEmpty(uid)) {
			try {
				CommonMethodUtil.modelUserBaseInfo(model, request, usersService);
                PageInfo pi = userRedPacketService.queryUserRedPacketByType(uid, type, hrpType, pageSize, pageNo);
				model.addAttribute("pageInfo", pi);
				model.addAttribute("type", type);
				model.addAttribute("hrp_type", hrpType);

			} catch (Exception e) {
				e.printStackTrace();
				return String.valueOf(ResultParame.ResultInfo.SERVER_ERROR.getCode());
			}

		} else {
			return "users/login";
		}

		return "myLingqian/redPacket";
	}

	/**
	 * 查询产品可用加息券
	 * 
	 * @author 2017年7月12日
	 * @param request
	 *            请求
	 * @param pCode
	 *            产品编码
	 * @param buyMoney
	 *            购买金额
	 * @return 返回信息
	 */
	@RequestMapping("/queryFinancialAvailableRedPacket")
	public @ResponseBody Object redTest(HttpServletRequest request, String pCode, Double buyMoney) {
		PageInfo pi = new PageInfo();
		try {
            String uid = CommonMethodUtil.getUidBySession(request);
            pi.setObj(userRedPacketService.queryFinancialAvailableRedPacket(pCode, uid, buyMoney, 1));
			pi.setCode(ResultParame.ResultInfo.SUCCESS.getCode());
			pi.setMsg(ResultParame.ResultInfo.SUCCESS.getMsg());

		} catch (PageInfoException e) {
			pi.setCode(e.getCode());
			pi.setMsg(e.getMessage());

		} catch (Exception e) {
			e.printStackTrace();
			pi.setCode(ResultParame.ResultInfo.SERVER_ERROR.getCode());
			pi.setMsg(ResultParame.ResultInfo.SERVER_ERROR.getMsg());
		}
		
		return pi;
	}

	/**
	 * 结果集
	 * 
	 * @return 返回信息
	 */
	@RequestMapping("/results")
	public String results() {
		return "myLingqian/results";
	}

	/**
	 * 查询用户冻结金额
	 * 
	 */
	@RequestMapping("/getUserFreeingAmount")
	public @ResponseBody Object getUserFreeingAmount(HttpServletRequest request) {
		String uId = CommonMethodUtil.getUidBySession(request);
		PageInfo pageInfo = new PageInfo();
		try {
			if (!StringUtils.isEmpty(uId)) {
				pageInfo.setCode(ResultInfo.SUCCESS.getCode());
				pageInfo.setMsg(ResultInfo.SUCCESS.getMsg());
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
