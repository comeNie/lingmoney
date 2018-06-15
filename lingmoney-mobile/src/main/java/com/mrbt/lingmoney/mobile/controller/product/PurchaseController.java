package com.mrbt.lingmoney.mobile.controller.product;

import java.io.IOException;

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

import com.mrbt.lingmoney.commons.exception.PageInfoException;
import com.mrbt.lingmoney.mobile.controller.BaseController;
import com.mrbt.lingmoney.service.product.ProductService;
import com.mrbt.lingmoney.service.product.PurchaseService;
import com.mrbt.lingmoney.service.product.PurchaseServiceVersionOne;
import com.mrbt.lingmoney.service.trading.JDPurchaseService;
import com.mrbt.lingmoney.service.trading.TradingService;
import com.mrbt.lingmoney.utils.AppCons;
import com.mrbt.lingmoney.utils.PageInfo;
import com.mrbt.lingmoney.utils.ResultParame;
import com.mrbt.lingmoney.utils.ResultParame.ResultInfo;

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
	 * @param token
	 *            token
	 * @param pCode
	 *            pCode
	 * @param tId
	 *            tId
	 *  @param userRedPacketId
	 *            userRedPacketId
	 *  @return pi
	 */             
    @RequestMapping(value = "/buyProduct", method = RequestMethod.POST)
	public @ResponseBody Object buyProduct(String token, String pCode, String buyMoney, String tId,
			Integer userRedPacketId) {
		LOG.info("--purchase buyProduct-- \n用户:" + token + ",购买:" + pCode + "产品,金额:" + buyMoney + "元,交易id:" + tId);
		// 返回结果 产品名称 剩余可购金额 用户账户余额 我的福利（红包，加息券）
		PageInfo pi = new PageInfo();
		try {
			String tokenKey = AppCons.TOKEN_VERIFY + token;
            //			pi = purchaseService.validBuyProduct(getUid(tokenKey), pCode, buyMoney, 1, userRedPacketId);
            pi = purchaseServiceVersionOne.validBuyProduct(getUid(tokenKey), pCode, buyMoney, 1, userRedPacketId);
		} catch (Exception e) {
			pi.setCode(ResultInfo.SERVER_ERROR.getCode());
			pi.setMsg(ResultInfo.SERVER_ERROR.getMsg());
			e.printStackTrace();
			LOG.error("购买失败，系统错误：token:" + token + ",pCode:" + pCode + ",buyMoney:" + buyMoney + ",tId:" + tId + "\n "
					+ e);
		}

		return pi;
	}
	
	/**
	 * 京东购买
	 * 
	 * @param tId
	 * @param request
	 * @param response
	 * @return return
	 */
	@RequestMapping("/jdBuyProduct")
	public @ResponseBody Object jdBuyProduct(String token, Integer tId)
			throws PageInfoException {
		PageInfo pageInfo = new PageInfo();
		try {
			String uId = getUid(AppCons.TOKEN_VERIFY + token);
			pageInfo = productService.jdBuyProduct(tId, uId);
		} catch (Exception e) {
			e.printStackTrace();
			pageInfo.setCode(ResultParame.ResultInfo.SERVER_ERROR.getCode());
			pageInfo.setMsg(ResultParame.ResultInfo.SERVER_ERROR.getMsg());
		}
		return pageInfo;

	}

	/**
	 * 取消支付
	 * @param token
	 *           token
	 * @param tId
	 *           tId
	 * @return pi
	 */
	@RequestMapping(value = "/cancelPay", method = RequestMethod.POST)
	public @ResponseBody Object cancelPay(String token, Integer tId) {
		LOG.info("purchase/cancelPay \n用户:" + token + ",取消支付,交易id:" + tId);
		PageInfo pi = new PageInfo();
		try {
			String tokenKey = AppCons.TOKEN_VERIFY + token;
            //			pi = tradingService.cancelPayment(getUid(tokenKey), tId);
            pi = tradingService.cancelPaymentVersionOne(getUid(tokenKey), tId);
		} catch (Exception e) {
			pi.setCode(ResultInfo.SERVER_ERROR.getCode());
			pi.setMsg(ResultInfo.SERVER_ERROR.getMsg());
			e.printStackTrace();
			LOG.error("取消支付出错，系统错误：token:" + token + ",tId:" + tId + "\n " + e);
		}

		return pi;
	}
	
	/**
	 * 未支付订单
	 * 
	 * @param token
	 *          token
	 * @return pi
	 */
	@RequestMapping(value = "/unPayOrder", method = RequestMethod.POST)
	public @ResponseBody Object unPayOrder(String token) {
		LOG.info("purchase/unPayOrder \n用户:" + token + ",查询未支付订单");
		PageInfo pi = new PageInfo();
		try {
			String tokenKey = AppCons.TOKEN_VERIFY + token;
			pi = tradingService.unPayOrder(getUid(tokenKey));
		} catch (Exception e) {
			pi.setCode(ResultInfo.SERVER_ERROR.getCode());
			pi.setMsg(ResultInfo.SERVER_ERROR.getMsg());
			e.printStackTrace();
			LOG.error("查询未支付订单出错，系统错误：token:" + token + "\n " + e);
		}
		
		return pi;
	}

    /**
     * ajax验证选择银行卡支付第一步
     * 
     * @author yiban
     * @date 2018年1月7日 下午2:16:51
     * @version 1.0
     * @param token token
     * @param tid 交易id
     * @param pCode 产品code
     * @param usersBankId 用户银行卡号
     * @return
     *
     */
    @RequestMapping(value = "/checkQuickPayMentFirst", method = RequestMethod.POST)
    @ResponseBody
    public Object checkQuickPayMentFirst(String token, Integer tid, String pCode, Integer usersBankId) {
        PageInfo pageInfo = new PageInfo();

        try {
            LOG.info("京东支付获取验证码，参数：token:{}_tid:{}_pCode:{}_usersBankId{}", token, tid, pCode, usersBankId);
            
            if (StringUtils.isEmpty(token) || StringUtils.isEmpty(tid) || StringUtils.isEmpty(pCode)
                    || StringUtils.isEmpty(usersBankId)) {
                pageInfo.setResultInfo(ResultInfo.PARAM_MISS);
            } else {
            	String uId = getUid(AppCons.TOKEN_VERIFY + token);
            	pageInfo = jdPurchaseService.getSecretCode(uId, tid, pCode, usersBankId);
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
     * @param tId
     * @param uId
     * @param dizNumber
     * @param buyMoney
     * @param productCode
     * @param usersBankId
     * @param verifyCode
     * @param response
     * @param request
     * @param modelMap
     * @throws IOException
     */
    @RequestMapping(value = "/checkQuickPayMentSecond", method = RequestMethod.POST)
    @ResponseBody
    public Object checkQuickPayMentSecond(String token, Integer tid, String pCode, Integer usersBankId,
            String verifyCode) {
        PageInfo pageInfo = new PageInfo();

        try {
            LOG.info("选择银行卡支付第二步,参数：token:{}, tid:{}, pCode:{}, usersBankId:{}, verifyCode:{}", token, tid, pCode,
                    usersBankId, verifyCode);
            if (StringUtils.isEmpty(token) || StringUtils.isEmpty(tid) || StringUtils.isEmpty(pCode)
                    || StringUtils.isEmpty(usersBankId)) {
                pageInfo.setResultInfo(ResultInfo.PARAM_MISS);
            } else {
            	String uId = getUid(AppCons.TOKEN_VERIFY + token);
            	pageInfo = jdPurchaseService.jdPay(uId, tid, pCode, usersBankId, verifyCode, 1);
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
     *  @param userRedPacketId
     *            userRedPacketId
     *  @return pi
     */
    @RequestMapping(value = "/buyProductVersionOne", method = RequestMethod.POST)
    public @ResponseBody Object buyProductVersionOne(String token, String pCode, String buyMoney, String tId,
            Integer userRedPacketId) {
        LOG.info("--purchase buyProductVersionOne-- \n用户:" + token + ",购买:" + pCode + "产品,金额:" + buyMoney + "元,交易id:"
                + tId);
        // 返回结果 产品名称 剩余可购金额 用户账户余额 我的福利（红包，加息券）
        PageInfo pi = new PageInfo();
        try {
            String uid = getUid(AppCons.TOKEN_VERIFY + token);

            if (StringUtils.isEmpty(uid)) {
                pi.setResultInfo(ResultInfo.LOGIN_OVERTIME);
            } else {
                pi = purchaseServiceVersionOne.validBuyProduct(uid, pCode, buyMoney, 1, userRedPacketId);
            }

        } catch (Exception e) {
            pi.setResultInfo(ResultInfo.SERVER_ERROR);
            e.printStackTrace();
            LOG.error("购买失败，系统错误：token:" + token + ",pCode:" + pCode + ",buyMoney:" + buyMoney + ",tId:" + tId + "\n "
                    + e);
        }

        return pi;
    }

    /**
     * 新版取消支付
     * @param token
     *           token
     * @param tId
     *           tId
     * @return pi
     */
    @RequestMapping(value = "/cancelPayVersionOne", method = RequestMethod.POST)
    public @ResponseBody Object cancelPayVersionOne(String token, Integer tId) {
        LOG.info("purchase/cancelPay \n用户:" + token + ",取消支付,交易id:" + tId);
        PageInfo pi = new PageInfo();
        try {
            String tokenKey = AppCons.TOKEN_VERIFY + token;
            pi = tradingService.cancelPaymentVersionOne(getUid(tokenKey), tId);
        } catch (Exception e) {
            pi.setCode(ResultInfo.SERVER_ERROR.getCode());
            pi.setMsg(ResultInfo.SERVER_ERROR.getMsg());
            e.printStackTrace();
            LOG.error("取消支付出错，系统错误：token:" + token + ",tId:" + tId + "\n " + e);
        }

        return pi;
    }

}
