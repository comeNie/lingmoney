package com.mrbt.lingmoney.web.controller.product;

import javax.servlet.http.HttpServletRequest;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mrbt.lingmoney.service.product.PurchaseServiceVersionOne;
import com.mrbt.lingmoney.service.trading.TradingService;
import com.mrbt.lingmoney.utils.PageInfo;
import com.mrbt.lingmoney.utils.ResultParame.ResultInfo;
import com.mrbt.lingmoney.web.controller.BaseController;
import com.mrbt.lingmoney.web.controller.common.CommonMethodUtil;

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
    private PurchaseServiceVersionOne purchaseServiceVersionOne;
	@Autowired
	private TradingService tradingService;

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
    public @ResponseBody Object buyProductVersionOne(HttpServletRequest request, String pCode, String buyMoney, String tId,
            Integer userRedPacketId) {
        LOG.info("--purchase buyProductVersionOne-- \n用户id:" + CommonMethodUtil.getUidBySession(request) + ",购买:" + pCode + "产品,金额:" + buyMoney + "元,交易id:"
                + tId);
        // 返回结果 产品名称 剩余可购金额 用户账户余额 我的福利（红包，加息券）
        PageInfo pi = new PageInfo();
        try {
        	String uid = CommonMethodUtil.getUidBySession(request);

            if (StringUtils.isEmpty(uid)) {
                pi.setResultInfo(ResultInfo.LOGIN_OVERTIME);
            } else {
                pi = purchaseServiceVersionOne.validBuyProduct(uid, pCode, buyMoney, 1, userRedPacketId);
            }

        } catch (Exception e) {
            pi.setResultInfo(ResultInfo.SERVER_ERROR);
            e.printStackTrace();
            LOG.error("购买失败，系统错误：用户id:" + CommonMethodUtil.getUidBySession(request) + ",pCode:" + pCode + ",buyMoney:" + buyMoney + ",tId:" + tId + "\n "
                    + e);
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
