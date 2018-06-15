package com.mrbt.lingmoney.wap.controller.product;

import javax.servlet.http.HttpServletRequest;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mrbt.lingmoney.service.product.ProductService;
import com.mrbt.lingmoney.service.product.PurchaseService;
import com.mrbt.lingmoney.service.product.PurchaseServiceVersionOne;
import com.mrbt.lingmoney.service.trading.JDPurchaseService;
import com.mrbt.lingmoney.service.trading.TradingService;
import com.mrbt.lingmoney.utils.PageInfo;
import com.mrbt.lingmoney.utils.ResultParame;
import com.mrbt.lingmoney.utils.ResultParame.ResultInfo;
import com.mrbt.lingmoney.wap.controller.BaseController;
import com.mrbt.lingmoney.wap.controller.common.CommonMethodUtil;

/**
 * @author syb
 * @date 2017年4月12日 上午11:27:37
 * @version 1.0
 * @description 购买/支付 ctrl
 **/
@Controller
@RequestMapping("/purchase")
public class PurchaseController extends BaseController {
	private static final Logger LOG = LogManager.getLogger(PurchaseController.class);

	@Autowired
	private PurchaseService purchaseService;
	@Autowired
	private TradingService tradingService;
    @Autowired
    private JDPurchaseService jdPurchaseService;
    @Autowired
    private ProductService productService;
	@Autowired
	private PurchaseServiceVersionOne purchaseServiceVersionOne;

	/**
	 * 产品购买
	 * 
	 * @param buyMoney
	 *            buyMoney
	 * @param request
	 *            request
	 * @param pCode
	 *            pCode
	 * @param tId
	 *            tId
	 *  @param userRedPacketId
	 *            userRedPacketId
	 *  @return pi
	 */             
	@RequestMapping(value = "/buyProduct", method = RequestMethod.POST)
	public @ResponseBody Object buyProduct(HttpServletRequest request, String pCode, String buyMoney, String tId,
			Integer userRedPacketId) {
		// 返回结果 产品名称 剩余可购金额 用户账户余额 我的福利（红包，加息券）
		PageInfo pi = new PageInfo();
        String uId = "";
		try {
            uId = CommonMethodUtil.getUidBySession(request);
            LOG.info("--purchase buyProduct-- \n用户:" + uId + ",购买:" + pCode + "产品,金额:" + buyMoney + "元,交易id:" + tId);
            if (StringUtils.isEmpty(uId)) {
                pi.setResultInfo(ResultInfo.LOGIN_OVERTIME);
            } else {
                pi = purchaseService.validBuyProduct(uId, pCode, buyMoney, 1, userRedPacketId);
            }

		} catch (Exception e) {
			pi.setCode(ResultInfo.SERVER_ERROR.getCode());
			pi.setMsg(ResultInfo.SERVER_ERROR.getMsg());
			e.printStackTrace();
			LOG.error("购买失败，系统错误：token:" + uId + ",pCode:" + pCode + ",buyMoney:" + buyMoney + ",tId:" + tId + "\n "
					+ e);
		}

		return pi;
	}

    /**
     * 京东购买
     * 
     * @author yiban
     * @date 2018年2月6日 下午3:16:02
     * @version 1.0
     * @param tId 交易id
     * @return
     *
     */
    @RequestMapping("/jdBuyProduct")
    public @ResponseBody Object jdBuyProduct(HttpServletRequest reqeust, Integer tId) {
        PageInfo pageInfo = new PageInfo();
        try {
            String uId = CommonMethodUtil.getUidBySession(reqeust);
            if (StringUtils.isEmpty(uId)) {
                pageInfo.setResultInfo(ResultInfo.LOGIN_OVERTIME);
            } else {
                pageInfo = productService.jdBuyProduct(tId, uId);
            }
        } catch (Exception e) {
            e.printStackTrace();
            pageInfo.setCode(ResultParame.ResultInfo.SERVER_ERROR.getCode());
            pageInfo.setMsg(ResultParame.ResultInfo.SERVER_ERROR.getMsg());
        }
        return pageInfo;

    }

    /**
     * 取消支付
     * @param request
     *           request
     * @param tId
     *           tId
     * @return pi
     */
	@RequestMapping(value = "/cancelPay", method = RequestMethod.POST)
	public @ResponseBody Object cancelPay(HttpServletRequest request, Integer tId) {
		PageInfo pi = new PageInfo();
        String uId = "";
		try {
            uId = CommonMethodUtil.getUidBySession(request);
            LOG.info("purchase/cancelPay \n用户:" + uId + ",取消支付,交易id:" + tId);
            if (StringUtils.isEmpty(uId)) {
                pi.setResultInfo(ResultInfo.LOGIN_OVERTIME);
            } else {
                pi = tradingService.cancelPayment(uId, tId);
            }
		} catch (Exception e) {
			pi.setCode(ResultInfo.SERVER_ERROR.getCode());
			pi.setMsg(ResultInfo.SERVER_ERROR.getMsg());
			e.printStackTrace();
			LOG.error("取消支付出错，系统错误：token:" + uId + ",tId:" + tId + "\n " + e);
		}

		return pi;
	}
	
	/**
	 * 未支付订单
	 * 
	 * @param request
	 *          request
	 * @return pi
	 */
	@RequestMapping(value = "/unPayOrder", method = RequestMethod.POST)
	public @ResponseBody Object unPayOrder(HttpServletRequest request) {
		PageInfo pi = new PageInfo();
        String uId = "";
		try {
            uId = CommonMethodUtil.getUidBySession(request);
            LOG.info("purchase/unPayOrder \n用户:" + uId + ",查询未支付订单");
            if (StringUtils.isEmpty(uId)) {
                pi.setResultInfo(ResultInfo.LOGIN_OVERTIME);
            } else {
                pi = tradingService.unPayOrder(uId);
            }

		} catch (Exception e) {
			pi.setCode(ResultInfo.SERVER_ERROR.getCode());
			pi.setMsg(ResultInfo.SERVER_ERROR.getMsg());
			e.printStackTrace();
			LOG.error("查询未支付订单出错，系统错误：token:" + uId + "\n " + e);
		}
		
		return pi;
	}

    /**
     * ajax验证选择银行卡支付第一步
     * 
     * @author yiban
     * @date 2018年1月7日 下午2:16:51
     * @version 1.0
     * @param tid 交易id
     * @param pCode 产品code
     * @param usersBankId 银行卡id
     * @return
     *
     */
    @RequestMapping(value = "/checkQuickPayMentFirst", method = RequestMethod.POST)
    @ResponseBody
    public Object checkQuickPayMentFirst(HttpServletRequest request, Integer tid, String pCode, Integer usersBankId) {
        PageInfo pageInfo = new PageInfo();

        try {
            LOG.info("京东支付获取验证码，参数：token:{}_tid:{}_pCode:{}_usersBankId{}", tid, pCode, usersBankId);

            if (StringUtils.isEmpty(tid) || StringUtils.isEmpty(pCode)
                    || StringUtils.isEmpty(usersBankId)) {
                pageInfo.setResultInfo(ResultInfo.PARAM_MISS);
            } else {
                String uId = CommonMethodUtil.getUidBySession(request);
                if (StringUtils.isEmpty(uId)) {
                    pageInfo.setResultInfo(ResultInfo.LOGIN_OVERTIME);
                } else {
                    pageInfo = jdPurchaseService.getSecretCode(uId, tid, pCode, usersBankId);
                }
            }
        } catch (Exception e) {
            LOG.error("购买支付第一步报错" + e);
            e.printStackTrace();
        }

        return pageInfo;
    }

    /**
     * ajax校验选择银行卡支付第二步
     * 
     * @author yiban
     * @date 2018年2月6日 下午2:16:07
     * @version 1.0
     * @param tid 交易id
     * @param pCode 产品code
     * @param usersBankId 银行卡id
     * @param verifyCode 验证码
     * @return
     *
     */
    @RequestMapping(value = "/checkQuickPayMentSecond", method = RequestMethod.POST)
    @ResponseBody
    public Object checkQuickPayMentSecond(HttpServletRequest request, Integer tid, String pCode, Integer usersBankId,
            String verifyCode) {
        PageInfo pageInfo = new PageInfo();

        try {
            LOG.info("选择银行卡支付第二步,参数：token:{}, tid:{}, pCode:{}, usersBankId:{}, verifyCode:{}", tid, pCode,
                    usersBankId, verifyCode);
            if (StringUtils.isEmpty(tid) || StringUtils.isEmpty(pCode)
                    || StringUtils.isEmpty(usersBankId)) {
                pageInfo.setResultInfo(ResultInfo.PARAM_MISS);
            } else {
                String uId = CommonMethodUtil.getUidBySession(request);
                if (StringUtils.isEmpty(uId)) {
                    pageInfo.setResultInfo(ResultInfo.LOGIN_OVERTIME);
                } else {
                    pageInfo = jdPurchaseService.jdPay(uId, tid, pCode, usersBankId, verifyCode, 1);
                }

            }
        } catch (NumberFormatException e) {
            LOG.error("购买支付第二步报错" + e);
            e.printStackTrace();
        }

        return pageInfo;
    }

    /**
     * 快捷支付-通知
     * 
     * @param response
     * @param request
     * @return
     */
    @RequestMapping("/quickNotice/{note}")
    public @ResponseBody Object quickNotice(HttpServletRequest request, @PathVariable String note) {
        return jdPurchaseService.quickNotice(request, note);
    }

    /**
     * 查询随心取利率详情
     * 
     */
    @RequestMapping(value = "/getAllTakeHeartFixRate", method = RequestMethod.POST)
    public @ResponseBody Object getAllTakeHeartFixRate() {
        PageInfo pageInfo = new PageInfo();
        try {
            pageInfo = jdPurchaseService.getAllTakeHeartFixRate();
        } catch (Exception e) {
            e.printStackTrace();
            pageInfo.setResultInfo(ResultInfo.SERVER_ERROR);
        }

        return pageInfo;
    }

	/**
	 * 产品购买
	 * 
	 * @param buyMoney
	 *            buyMoney
	 * @param token
	 *            token
	 * @param pCode
	 *            pCode
	 * @param tId
	 *            tId
	 * @param userRedPacketId
	 *            userRedPacketId
	 * @return pi
	 */
	@RequestMapping(value = "/buyProductVersionOne", method = RequestMethod.POST)
	public @ResponseBody Object buyProductVersionOne(HttpServletRequest request, String pCode, String buyMoney,
			String tId, Integer userRedPacketId) {
		LOG.info("--purchase buyProductVersionOne-- \n用户id:" + CommonMethodUtil.getUidBySession(request) + ",购买:"
				+ pCode + "产品,金额:" + buyMoney + "元,交易id:" + tId);
		// 返回结果 产品名称 剩余可购金额 用户账户余额 我的福利（红包，加息券）
		PageInfo pi = new PageInfo();
		try {
			String uid = CommonMethodUtil.getUidBySession(request);

			if (StringUtils.isEmpty(uid)) {
				pi.setResultInfo(ResultInfo.LOGIN_OVERTIME);
			} else {
				pi = purchaseServiceVersionOne.validBuyProduct(uid, pCode, buyMoney, 2, userRedPacketId);
			}

		} catch (Exception e) {
			pi.setResultInfo(ResultInfo.SERVER_ERROR);
			e.printStackTrace();
			LOG.error("购买失败，系统错误：用户id:" + CommonMethodUtil.getUidBySession(request) + ",pCode:" + pCode + ",buyMoney:"
					+ buyMoney + ",tId:" + tId + "\n " + e);
		}

		return pi;
	}

	/**
	 * 新版取消支付
	 * 
	 * @param token
	 *            token
	 * @param tId
	 *            tId
	 * @return pi
	 */
	@RequestMapping(value = "/cancelPayVersionOne", method = RequestMethod.POST)
	public @ResponseBody Object cancelPayVersionOne(HttpServletRequest request, Integer tId) {
		String uid = CommonMethodUtil.getUidBySession(request);
		LOG.info("purchase/cancelPay \n用户:" + uid + ",取消支付,交易id:" + tId);
		PageInfo pi = new PageInfo();
		try {
			pi = tradingService.cancelPaymentVersionOne(uid, tId);
		} catch (Exception e) {
			pi.setCode(ResultInfo.SERVER_ERROR.getCode());
			pi.setMsg(ResultInfo.SERVER_ERROR.getMsg());
			e.printStackTrace();
			LOG.error("取消支付出错，系统错误：token:" + uid + ",tId:" + tId + "\n " + e);
		}

		return pi;
	}

}

