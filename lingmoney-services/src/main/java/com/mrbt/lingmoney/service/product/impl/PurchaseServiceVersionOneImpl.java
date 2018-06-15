package com.mrbt.lingmoney.service.product.impl;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.mrbt.lingmoney.commons.cache.RedisDao;
import com.mrbt.lingmoney.mapper.HxAccountMapper;
import com.mrbt.lingmoney.mapper.HxRedPacketMapper;
import com.mrbt.lingmoney.mapper.ProductCustomerMapper;
import com.mrbt.lingmoney.mapper.TradingMapper;
import com.mrbt.lingmoney.mapper.UsersAccountMapper;
import com.mrbt.lingmoney.mapper.UsersBankMapper;
import com.mrbt.lingmoney.mapper.UsersPropertiesMapper;
import com.mrbt.lingmoney.mapper.UsersRedPacketMapper;
import com.mrbt.lingmoney.model.HxAccount;
import com.mrbt.lingmoney.model.HxAccountExample;
import com.mrbt.lingmoney.model.HxRedPacket;
import com.mrbt.lingmoney.model.Product;
import com.mrbt.lingmoney.model.TradingExample;
import com.mrbt.lingmoney.model.UsersAccount;
import com.mrbt.lingmoney.model.UsersProperties;
import com.mrbt.lingmoney.model.UsersRedPacket;
import com.mrbt.lingmoney.model.trading.BankInfo;
import com.mrbt.lingmoney.service.product.PurchaseServiceVersionOne;
import com.mrbt.lingmoney.service.trading.TradingFixRuleBuyService;
import com.mrbt.lingmoney.service.trading.TradingTakeheartBuyService;
import com.mrbt.lingmoney.utils.AppCons;
import com.mrbt.lingmoney.utils.BigDecimalUtils;
import com.mrbt.lingmoney.utils.PageInfo;
import com.mrbt.lingmoney.utils.ProductUtils;
import com.mrbt.lingmoney.utils.PropertiesUtil;
import com.mrbt.lingmoney.utils.ResultParame.ResultInfo;
import com.mrbt.lingmoney.utils.ResultParame.ResultNumber;

/**
  *
  *@author yiban
  *@date 2018年3月13日 上午9:22:57
  *@version 1.0
 **/
@Service
public class PurchaseServiceVersionOneImpl implements PurchaseServiceVersionOne {
    private static final Logger LOG = LogManager.getLogger(PurchaseServiceVersionOneImpl.class);

    @Autowired
    private UsersAccountMapper usersAccountMapper;
    @Autowired
    private UsersPropertiesMapper usersPropertiesMapper;
    @Autowired
    private TradingMapper tradingMapper;
    @Autowired
    private TradingFixRuleBuyService tradingFixRuleBuyService;
    @Autowired
    private HxAccountMapper hxAccountMapper;
    @Autowired
    private UsersRedPacketMapper usersRedPacketMapper;
    @Autowired
    private HxRedPacketMapper hxRedPacketMapper;
    @Autowired
    private ProductCustomerMapper productCustomerMapper;
    @Autowired
    private UsersBankMapper usersBankMapper;
    @Autowired
    private RedisDao redisDao;
    @Autowired
    private TradingTakeheartBuyService tradingTakeheartBuyService;

    @Override
    public PageInfo validBuyProduct(String uid, String pCode, String buyMoney, int platForm,
            Integer userRedPacketId) {
        PageInfo pageInfo = new PageInfo();

        if (StringUtils.isEmpty(pCode) || StringUtils.isEmpty(buyMoney)) {
            pageInfo.setResultInfo(ResultInfo.PARAM_MISS);
            return pageInfo;
        }

        BigDecimal buyMoneyBD = new BigDecimal(buyMoney);

        Product product = productCustomerMapper.selectByCode(pCode);
        if (product != null) {

            // 1.验证产品信息
            pageInfo = validProductInfo(product, pCode, uid);
            if (pageInfo.getCode() != ResultInfo.SUCCESS.getCode()) {
                return pageInfo;
            }

            // 2.验证用户开卡状态，账户余额
            pageInfo = validUserInfoVersionOne(uid, buyMoneyBD, product);
            if (pageInfo.getCode() != ResultInfo.SUCCESS.getCode()) {
                return pageInfo;
            }

            // 3.产品类型id 判断新手标
            if (AppCons.NEW_PRODUCT_TYPE_ID.equals(product.getPcId().toString())) {
                // 新手标不可使用加息券
                if (!StringUtils.isEmpty(userRedPacketId)) {
                    pageInfo.setCode(ResultInfo.PARAMS_ERROR.getCode());
                    pageInfo.setMsg("新手标不支持使用加息券");
                    return pageInfo;
                }

                // 购买过产品，不能再购买新手产品
                TradingExample tradingExample = new TradingExample();
                tradingExample
                        .createCriteria()
                        .andUIdEqualTo(uid)
                        .andStatusIn(
                                Arrays.asList(AppCons.BUY_OK, AppCons.SELL_STATUS, AppCons.SELL_OK, AppCons.BUY_FROKEN,
                                        AppCons.SELL_WAITING));
                long buyCount = tradingMapper.countByExample(tradingExample);
                if (buyCount > 0) {
                    pageInfo.setCode(ResultInfo.DATA_EXISTED.getCode());
                    pageInfo.setMsg("首次理财才可购买新手标");
                    return pageInfo;
                }

            } else {
                // 非新手产品，验证优惠券是否可用
                if (!StringUtils.isEmpty(userRedPacketId)) {
                    pageInfo = buyValidRedPacketInfo(uid, userRedPacketId, pCode, buyMoney);
                    if (pageInfo.getCode() != ResultInfo.SUCCESS.getCode()) {
                        return pageInfo;
                    }
                }

            }

            // 4.验证用户购买金额
            pageInfo = validUserBuymoneyVersionOne(uid, pCode, buyMoneyBD, product);
            if (pageInfo.getCode() != ResultInfo.SUCCESS.getCode()) {
                return pageInfo;
            }

            // 5.生成购买订单
            if (AppCons.TAKE_HEART_PCODE.equals(pCode)) {// 随心取购买
                pageInfo = tradingTakeheartBuyService.buy(pCode, uid, buyMoneyBD);

            } else {
                // 订单有效支付时间
                String orderRemainPayTime = PropertiesUtil.getPropertiesByKey("orderRemainPayTime");
                // 稳赢宝购买，占用标的份额，生成购买流水
                pageInfo = tradingFixRuleBuyService.buyVersionOne(pCode, uid, platForm, buyMoneyBD,
                        Integer.valueOf(orderRemainPayTime), userRedPacketId);
            }

        } else {
            pageInfo.setCode(ResultInfo.NO_DATA.getCode());
            pageInfo.setMsg("产品code无效：" + pCode);
        }

        return pageInfo;
    }

    /**
     * 验证用户购买金额（新版）
     * 
     * @author yiban
     * @date 2018年3月13日 上午9:18:29
     * @version 1.0
     * @param uid 用户id
     * @param pCode 产品code
     * @param buyMoneyBD 购买金额
     * @param product 产品
     * @return 如果成功返回null
     *
     */
    private PageInfo validUserBuymoneyVersionOne(String uid, String pCode, BigDecimal buyMoneyBD, Product product) {
        PageInfo pageInfo = new PageInfo();

        int minMoney = product.getMinMoney(); // 最小购买金额
        BigDecimal increaseMoney = new BigDecimal(product.getIncreaMoney()); // 递增金额
        BigDecimal minBuyMoney = new BigDecimal(product.getMinMoney().toString());
        if (AppCons.TAKE_HEART_PCODE.equals(pCode)) {
            // 如果是随心取，购买金额只需大于递增金额
            minBuyMoney = increaseMoney;
        }

        // 1.大于最小购买金额kb
        if (buyMoneyBD.compareTo(minBuyMoney) == -1) {
            pageInfo.setCode(ResultInfo.AMOUNT_LESSTHAN_MIN_AMOUNT.getCode());
            pageInfo.setMsg("购买金额不能小于" + minMoney + "元");
            LOG.info("购买失败，购买金额小于最低金额。pCode" + pCode);
            redisDao.delete(AppCons.TAKE_HEART + uid);
            return pageInfo;
        }

        // 2.递增金额整数倍
        if (!BigDecimalUtils.divideByInt(buyMoneyBD, increaseMoney)) {
            pageInfo.setCode(ResultInfo.AMOUNT_ERROR.getCode());
            pageInfo.setMsg("输入的金额格式不符合要求");
            LOG.info("购买失败，购买金额不合法。 pCode:" + pCode);
            redisDao.delete(AppCons.TAKE_HEART + uid);
            return pageInfo;
        }

        // 3.大于产品剩余额度
        if (product.getPriorMoney() != null
                && product.getReachMoney().add(buyMoneyBD).compareTo(product.getPriorMoney()) == 1) {
            pageInfo.setResultInfo(ResultInfo.BUYMONEY_OVERFLOW);
            LOG.info("购买超过剩余可购金额。 pCode:" + pCode);
            redisDao.delete(AppCons.TAKE_HEART + uid);
            return pageInfo;
        }

        // 4.是否超过该产品购买上限
        BigDecimal buyLimit = product.getBuyLimit();
        if (buyLimit != null && buyLimit.doubleValue() > 0) {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("uId", uid);
            map.put("prCode", pCode);
            // 格式化金额，防止页面提示时展示数据格式错误
            DecimalFormat decimalFormat = new DecimalFormat("#,##0.00");
            // 用户已买金额
            BigDecimal userBuyMoney = tradingMapper.queryUserBoughtMoney(map);
            // 如果用户已买金额超过上限
            if (userBuyMoney.compareTo(buyLimit) != -1) {
                pageInfo.setCode(ResultInfo.AMOUNT_THAN.getCode());
                pageInfo.setMsg("购买失败，您已购买" + decimalFormat.format(userBuyMoney) + "元该产品，剩余可购金额为0元");
                LOG.info("用戶购买产品失败，购买额度达到上限.uid:" + uid);
                redisDao.delete(AppCons.TAKE_HEART + uid);
                return pageInfo;

            } else { // 如果用户已买金额未超过上限
                // 查询用户当前可买金额
                BigDecimal canBuyMoney = buyLimit.subtract(userBuyMoney);
                // 如果当前可买金额小于用户输入金额
                if (canBuyMoney.compareTo(buyMoneyBD) == -1) {
                    pageInfo.setCode(ResultInfo.AMOUNT_THAN.getCode());
                    pageInfo.setMsg("购买失败，您已购买" + decimalFormat.format(userBuyMoney) + "元该产品，剩余可购金额为"
                            + decimalFormat.format(canBuyMoney) + "元");
                    LOG.info("用戶购买产品失败，购买额度超过限制额度.uid:" + uid);
                    redisDao.delete(AppCons.TAKE_HEART + uid);
                    return pageInfo;
                }
            }
        }

        // 查询是否存在华兴银行账户
        if (product.getpType() == 2) {
            HxAccountExample example = new HxAccountExample();
            example.createCriteria().andUIdEqualTo(uid).andStatusEqualTo(1);

            List<HxAccount> hxAccountList = hxAccountMapper.selectByExample(example);
            // 未查询到账户信息
            if (hxAccountList == null || hxAccountList.size() <= 0) {
                pageInfo.setCode(ResultInfo.EMPTY_DATA.getCode());
                pageInfo.setMsg("信息有误,请联系管理员");
                LOG.error("用户认证通过，却未查询到华兴银行账户信息。 UID：" + uid);
                return pageInfo;

            } else { // 验证开户状态
                HxAccount hxAccount = hxAccountList.get(0);
                if (hxAccount.getStatus() != 1) {
                    pageInfo.setCode(ResultInfo.RETURN_MESSAGE_NOT_SUCCESS.getCode());
                    pageInfo.setMsg("账户未开通成功,请稍后再试");
                    return pageInfo;
                }
            }

        } else if (product.getpType() == 0) {
            List<BankInfo> bankList = usersBankMapper.selectByUIdAndStatus(uid, "1");
            if (bankList == null || bankList.size() < 1) {
                pageInfo.setCode(ResultInfo.RETURN_MESSAGE_NOT_SUCCESS.getCode());
                pageInfo.setMsg("没有有效的银行卡绑卡JD");
                redisDao.delete(AppCons.TAKE_HEART + uid);
                return pageInfo;
            }
        }

        pageInfo.setResultInfo(ResultInfo.SUCCESS);
        return pageInfo;
    }

    /**
     * 验证用户开卡状态，账户余额（新版购买）
     * 
     * @author yiban
     * @date 2018年3月13日 上午9:15:37
     * @version 1.0
     * @param uid 用户id
     * @param buyMoneyBD 购买金额
     * @param product 产品
     * @return pageinfo
     *
     */
    private PageInfo validUserInfoVersionOne(String uid, BigDecimal buyMoneyBD, Product product) {
        PageInfo pageInfo = new PageInfo();
        // 验证开户绑卡状态
        UsersProperties usersProperties = usersPropertiesMapper.selectByuId(uid);
        if (usersProperties == null) {
            LOG.error("未查询到用户属性信息，UID：" + uid);
            redisDao.delete(AppCons.TAKE_HEART + uid);
            pageInfo.setCode(ResultInfo.EMPTY_DATA.getCode());
            pageInfo.setMsg("信息有误,请联系管理员");
            return pageInfo;
        }

        // 京东、华兴产品判断区分
        if (product.getpType() == ResultNumber.ZERO.getNumber()) { // 领钱儿产品，验证京东绑卡
            if (usersProperties.getCertification() != ResultNumber.ONE.getNumber()
                    && usersProperties.getCertification() != ResultNumber.THREE.getNumber()) {
                LOG.info("该用户未实名认证，未进行京东绑卡。UID：" + uid);
                redisDao.delete(AppCons.TAKE_HEART + uid);
                pageInfo.setCode(ResultInfo.NOT_HX_ACCOUNT.getCode());
                pageInfo.setMsg("该用户未进行京东绑卡");
                return pageInfo;

            } else if (usersProperties.getBank() != ResultNumber.ONE.getNumber()
                    && usersProperties.getBank() != ResultNumber.THREE.getNumber()) {
                LOG.info("该用户未进行京东绑卡，UID：" + uid);
                redisDao.delete(AppCons.TAKE_HEART + uid);
                pageInfo.setCode(ResultInfo.HX_ACCOUNT_NOT_ACTIVATION.getCode());
                pageInfo.setMsg("该用户未进行京东绑卡");
                return pageInfo;
            }

        } else if (product.getpType() == ResultNumber.TWO.getNumber()) { // 华兴产品，验证华兴绑卡
            if (usersProperties.getCertification() != ResultNumber.TWO.getNumber()
                    && usersProperties.getCertification() != ResultNumber.THREE.getNumber()) {
                LOG.info("该用户未实名认证，未开通华兴银行账户。UID：" + uid);

                pageInfo.setCode(ResultInfo.NOT_HX_ACCOUNT.getCode());
                pageInfo.setMsg("该用户未开通华兴银行账户");
                return pageInfo;

            } else if (usersProperties.getBank() != ResultNumber.TWO.getNumber()
                    && usersProperties.getBank() != ResultNumber.THREE.getNumber()) {
                LOG.info("该用户账户未激活，UID：" + uid);

                pageInfo.setCode(ResultInfo.HX_ACCOUNT_NOT_ACTIVATION.getCode());
                pageInfo.setMsg("该用户账户未激活");
                return pageInfo;
            }

            // 判断账户余额
            UsersAccount usersAccount = usersAccountMapper.selectByUid(uid);
            if (usersAccount == null) {
                pageInfo.setCode(ResultInfo.EMPTY_DATA.getCode());
                pageInfo.setMsg("账户不存在,请联系管理员");
                LOG.info("未查询到用户账户信息，UID：" + uid);
                return pageInfo;

            } else {
                // 如果余额不足 提示： 您的当前账户余额不足，请充值
                if (buyMoneyBD.compareTo(usersAccount.getBalance()) == 1) {
                    pageInfo.setCode(ResultInfo.USER_BALANCE_INSUFFICIENT.getCode());
                    pageInfo.setMsg("您的当前账户余额不足,请充值");
                    return pageInfo;
                }
            }

        }

        pageInfo.setResultInfo(ResultInfo.SUCCESS);
        return pageInfo;
    }

    /**
     * 验证红包信息
     * 
     * @param uid 用户id
     * @param redPacketId 用户红包id
     * @param pCode 产品code
     * @param buyMoney 购买金额
     * @return pageinfo
     *
     */
    private PageInfo buyValidRedPacketInfo(String uid, Integer redPacketId, String pCode, String buyMoney) {
        PageInfo pi = new PageInfo();
        UsersRedPacket usersRedPacket = usersRedPacketMapper.selectByPrimaryKey(redPacketId);
        if (usersRedPacket == null) {
            pi.setCode(ResultInfo.EMPTY_DATA.getCode());
            pi.setMsg("未查询到该优惠券信息");
            return pi;
        }

        if (!usersRedPacket.getuId().equals(uid)) {
            pi.setResultInfo(ResultInfo.REDPACKET_UNSUITABLE);
            return pi;
        }

        if (usersRedPacket.getStatus() != 0) {
            pi.setResultInfo(ResultInfo.REDPACKET_UNUSEABLE);
            return pi;
        }

        // 验证是否已过期
        if (usersRedPacket.getValidityTime().getTime() < new Date().getTime()) {
            pi.setResultInfo(ResultInfo.REDPACKET_OVERTIME);
            return pi;
        }

        HxRedPacket hxRedPacket = hxRedPacketMapper.selectByPrimaryKey(usersRedPacket.getRpId());
        
        //验证复投红包是否复合复投
        if (hxRedPacket.getHrpType() == 3) {
			//查询是否有交易记录
        	TradingExample example = new TradingExample();
        	example.createCriteria().andUIdEqualTo(uid).andStatusEqualTo(ResultNumber.ONE.getNumber());
        	
        	long count = tradingMapper.countByExample(example);
        	if (count == 0) {
        		pi.setResultInfo(ResultInfo.REDPACKET_UNVALID_F);
				return pi;
			}
		}
        
        if (!validUsedRedPacket(pCode, new Double(buyMoney), hxRedPacket)) {
            pi.setResultInfo(ResultInfo.REDPACKET_UNVALID);
            return pi;
        }

        pi.setResultInfo(ResultInfo.SUCCESS);
        return pi;
    }

    /**
     * 验证优惠券是否可用
     * 
     * @param pCode 产品code
     * @param double1 购买金额
     * @param hxRedPacket 红包
     * @return true验证通过  false验证失败
     */
    private boolean validUsedRedPacket(String pCode, Double double1, HxRedPacket hxRedPacket) {
        if (hxRedPacket == null) {
            return false;
        }

        // 验证规则
        // 1.按产品id检索 2.没有则按产品类别检索 3.加息券的期限>=产品表f_time 4.用户输入金额>=加息券表use_limit
        Product product = productCustomerMapper.selectByCode(pCode); // 获取产品信息
        if (product.getpType() != 2) {
            return false;
        }

        // 验证产品批次使用现在
        if (hxRedPacket.getuInvestProBatch() != null && !hxRedPacket.getuInvestProBatch().equals("")) {
            if (product.getBatch() != null && hxRedPacket.getuInvestProBatch().indexOf(product.getBatch()) < 0) {
                return false;
            }
        } else if (hxRedPacket.getuInvestProType() != null && !hxRedPacket.getuInvestProType().equals("")) {
            // 没有产品ID 判断产品类型和产品期限，值如果为空则不判断 。
            if (hxRedPacket.getuInvestProType().indexOf(product.getPcId().toString()) < 0) {
                // 不可用 类型不符
                return false;
            }
        }

        // 判断产品期限
        if (hxRedPacket.getuInvestProDay() != null && hxRedPacket.getuInvestProMixday() != null) {
            Integer x = ProductUtils.getFinancialDays(product);

            //如果优惠券中的使用期限下限小于999，判断产品期限是否大于使用期限下限，如果是优惠券不可用
            if (hxRedPacket.getuInvestProMixday() > 0) {
                if (x > hxRedPacket.getuInvestProMixday()) {
                    return false;
                }
            }

            //如果优惠券中的使用期限上线大于30，判断产品期限是否小于使用期限上限，如果是优惠券不可用
            if (hxRedPacket.getuInvestProDay() > 0) {
                if (x < hxRedPacket.getuInvestProDay()) {
                    return false;
                }
            }
        }

        if (double1 > 0) { // 购买金额大于起投金额
            if (hxRedPacket.getuInvestProAmount() != null && hxRedPacket.getuInvestProAmount() > double1) {
                return false;
            }
        } else {
            return false;
        }

        return true;
    }
    
    private PageInfo validProductInfo(Product product, String pCode, String uid) {
        PageInfo pi = new PageInfo();
        // 产品只有在募集期才可购买/随心取运行中才可购买
        if (product.getStatus() != AppCons.PRODUCT_STATUS_READY && !AppCons.TAKE_HEART_PCODE.equals(pCode)) {
            pi.setResultInfo(ResultInfo.PRODUCT_CANNOT_BUY);
            return pi;
        }

        // 稳赢宝判断开始日期是否可以购买
        if (product.getRule() == 2) {
            Date date = new Date();
            if (date.getTime() < product.getStDt().getTime()) {
                pi.setResultInfo(ResultInfo.PROJECT_NOT_SHOPPING);
                LOG.info("购买失败，当前产品暂未开放认购");
                return pi;

            } else if (date.getTime() > product.getEdDt().getTime()) {
                pi.setResultInfo(ResultInfo.PROJECT_STOP_SHOPPING);
                LOG.info("购买失败，当前产品暂已停止认购");
                return pi;
            }
        }

        // 随心取产品验证
        if (AppCons.TAKE_HEART_PCODE.equals(pCode)) {
            if (product.getStatus() != AppCons.PRODUCT_STATUS_RUNNING) {
                pi.setResultInfo(ResultInfo.PRODUCT_CANNOT_BUY);
                return pi;
            }

            // 随心取产品目前只能申购
            TradingExample tradingExmp = new TradingExample();
            tradingExmp.createCriteria().andPCodeEqualTo(pCode).andUIdEqualTo(uid)
                    .andStatusIn(Arrays.asList(AppCons.BUY_OK, AppCons.BUY_FROKEN));
            if (tradingMapper.countByExample(tradingExmp) < 1) {
                pi.setCode(ResultInfo.DO_FAIL.getCode());
                pi.setMsg("当前随心取只能申购");
                return pi;
            }

            if (redisDao.get("takeHeart_Allow_Buy") != null) {
                LOG.info("随心取禁止购买：takeHeart_Allow_Buy=" + redisDao.get("takeHeart_Allow_Buy"));
                pi.setCode(ResultInfo.DO_FAIL.getCode());
                pi.setMsg((String) redisDao.get("takeHeart_Allow_Buy"));
                return pi;
            }

            // 随心取10分钟才可购买一次
            if (!checkTakeHeart(AppCons.TAKE_HEART + uid)) {
                pi.setCode(ResultInfo.DO_FAIL.getCode());
                pi.setMsg("该产品，您有未完成的操作，请稍后再试。");
                return pi;

            } else {
                setTakeHeart(AppCons.TAKE_HEART + uid);
            }
        }
        
        pi.setResultInfo(ResultInfo.SUCCESS);
        return pi;
    }
    
    private boolean checkTakeHeart(String key) {
        if (redisDao.get(key) == null && redisDao.get(AppCons.TAKE_HEART + "interest") == null) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 保存随心取购买记录到redis
     *
     * @Description 十分钟失效，用于限制购买/赎回
     * @param key 随心取key
     */
    private void setTakeHeart(String key) {
        redisDao.set(key, key);
        redisDao.expire(key, 10, TimeUnit.MINUTES);
    }

}
