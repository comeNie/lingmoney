package com.mrbt.lingmoney.service.product.impl;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang.math.NumberUtils;
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
import com.mrbt.lingmoney.model.Trading;
import com.mrbt.lingmoney.model.TradingExample;
import com.mrbt.lingmoney.model.UsersAccount;
import com.mrbt.lingmoney.model.UsersProperties;
import com.mrbt.lingmoney.model.UsersRedPacket;
import com.mrbt.lingmoney.model.trading.BankInfo;
import com.mrbt.lingmoney.service.product.PurchaseService;
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
 * @author syb
 * @date 2017年4月12日 上午11:31:00
 * @version 1.0
 * @description
 **/
@Service
public class PurchaseServiceImpl implements PurchaseService {
    private static final Logger LOG = LogManager.getLogger(PurchaseServiceImpl.class);

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
    public PageInfo validBuyProduct(String uid, String pCode, String buyMoney, Integer platForm, Integer userRedPacketId) {
        PageInfo pi = new PageInfo();
        // 1、判断参数
        if (StringUtils.isEmpty(uid) || StringUtils.isEmpty(pCode) || StringUtils.isEmpty(buyMoney)
                || !NumberUtils.isNumber(buyMoney)) {
            pi.setCode(ResultInfo.PARAM_MISS.getCode());
            pi.setMsg("参数错误");
            LOG.info("参数信息：uid:" + uid + ", pCode:" + pCode + ", buyMoney:" + buyMoney + " platForm:" + platForm);
            return pi;
        }
        LOG.info("购买参数：用户" + uid + ":购买 " + pCode + "产品,金额：" + buyMoney + ",购买平台：" + platForm);

        BigDecimal buyMoneyDecimal = new BigDecimal(buyMoney);
        // 2、验证产品信息
        PageInfo validInfo = buyValidProductInfo(uid, pCode, buyMoneyDecimal);
        if (validInfo != null) {
            return validInfo;
        }

        // 3、验证用户信息
        validInfo = buyValidUserInfo(uid, buyMoneyDecimal, pCode);
        if (validInfo != null) {
            return validInfo;
        }

        // 4、如果有优惠券，验证优惠券信息
        if (!StringUtils.isEmpty(userRedPacketId) && userRedPacketId > 0) {
            validInfo = buyValidRedPacketInfo(uid, userRedPacketId, pCode, buyMoney);
            if (validInfo != null) {
                return validInfo;
            }
        }

        // 5、创建订单 , 目前默认走稳赢宝service,后期添加新产品在此处判断处理
        if (AppCons.TAKE_HEART_PCODE.equals(pCode)) {// 随心取购买
            pi = tradingTakeheartBuyService.buy(pCode, uid, buyMoneyDecimal);

        } else {
            // 订单有效支付时间
            String orderRemainPayTime = PropertiesUtil.getPropertiesByKey("orderRemainPayTime");
            pi = tradingFixRuleBuyService.buy(pCode, uid, platForm, buyMoneyDecimal,
                    Integer.valueOf(orderRemainPayTime), userRedPacketId);
        }

        return pi;

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
        if (!validUsedRedPacket(pCode, new Double(buyMoney), hxRedPacket)) {
            pi.setResultInfo(ResultInfo.REDPACKET_UNVALID);
            return pi;
        }

        return null;
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

    @Override
    public PageInfo validBuyerInfo(String uid, String buyMoney, String pCode) {
        PageInfo pi = new PageInfo();

        if (!StringUtils.isEmpty(buyMoney) && NumberUtils.isNumber(buyMoney)) {

            pi = buyValidUserInfo(uid, new BigDecimal(buyMoney), pCode);

            if (pi == null) {
                pi = new PageInfo();
                pi.setCode(ResultInfo.SUCCESS.getCode());
                pi.setMsg("验证通过");
            }

        } else {
            pi.setCode(ResultInfo.PARAM_MISS.getCode());
            pi.setMsg("请输入金额");
        }

        return pi;
    }

    /**
     * 购买稳赢宝——验证产品信息
     * 
     * @param uid 用户ID
     * @param pCode 产品CODE
     * @param buyMoney 购买金额
     * @return NULL表示验证通过，否则返回错误信息：PageInfo
     */
    private PageInfo buyValidProductInfo(String uid, String pCode, BigDecimal buyMoney) {
        PageInfo pi = new PageInfo();

        Product product = productCustomerMapper.selectByCode(pCode);
        if (product != null) {
            // 验证产品CODE是否符合规范
            PageInfo validInfo = validPcode(pCode);
            if (validInfo != null) {
                return validInfo;
            }

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

        } else {
            pi.setCode(ResultInfo.EMPTY_DATA.getCode());
            pi.setMsg("操作失败");
            LOG.info("购买验证失败，未查询到产品信息。 产品CODE=" + pCode);
            return pi;
        }

        LOG.info("产品验证通过 uid:" + uid + ",pCode:" + pCode + ",buyMoney:" + buyMoney);
        return null;
    }

    /**
     * 购买——验证用户信息
     * 
     * @param uid 用户id
     * @param buyMoney 购买金额
     * @param pCode 产品code
     * @return 为NULL表示验证通过，否则返回错误信息：PageInfo
     */
    private PageInfo buyValidUserInfo(String uid, BigDecimal buyMoney, String pCode) {
        PageInfo pi = new PageInfo();

        Product product = productCustomerMapper.selectByCode(pCode);
        if (product == null) {
            pi.setCode(ResultInfo.SERVER_ERROR.getCode());
            pi.setMsg("信息有误，请刷新后重试");
            LOG.info("未查询到产品CODE为" + pCode + "的产品信息");
            return pi;
        }

        // 判断该用户是否绑卡认证
        UsersProperties usersProperties = usersPropertiesMapper.selectByuId(uid);
        if (usersProperties == null) {
            LOG.error("未查询到用户属性信息，UID：" + uid);

            pi.setCode(ResultInfo.EMPTY_DATA.getCode());
            pi.setMsg("信息有误,请联系管理员");
            return pi;
        }

        if (product.getpType() == ResultNumber.ZERO.getNumber()) { // 领钱儿产品，验证京东绑卡
            if (usersProperties.getCertification() != ResultNumber.ONE.getNumber()
                    && usersProperties.getCertification() != ResultNumber.THREE.getNumber()) {
                LOG.info("该用户未实名认证，未进行京东绑卡。UID：" + uid);
                redisDao.delete(AppCons.TAKE_HEART + uid);
                pi.setCode(ResultInfo.NOT_HX_ACCOUNT.getCode());
                pi.setMsg("该用户未进行京东绑卡");
                return pi;

            } else if (usersProperties.getBank() != ResultNumber.ONE.getNumber()
                    && usersProperties.getBank() != ResultNumber.THREE.getNumber()) {
                LOG.info("该用户未进行京东绑卡，UID：" + uid);
                redisDao.delete(AppCons.TAKE_HEART + uid);
                pi.setCode(ResultInfo.HX_ACCOUNT_NOT_ACTIVATION.getCode());
                pi.setMsg("该用户未进行京东绑卡");
                return pi;
            }

        } else if (product.getpType() == ResultNumber.TWO.getNumber()) { // 华兴产品，验证华兴绑卡
            if (usersProperties.getCertification() != ResultNumber.TWO.getNumber()
                    && usersProperties.getCertification() != ResultNumber.THREE.getNumber()) {
                LOG.info("该用户未实名认证，未开通华兴银行账户。UID：" + uid);

                pi.setCode(ResultInfo.NOT_HX_ACCOUNT.getCode());
                pi.setMsg("该用户未开通华兴银行账户");
                return pi;

            } else if (usersProperties.getBank() != ResultNumber.TWO.getNumber()
                    && usersProperties.getBank() != ResultNumber.THREE.getNumber()) {
                LOG.info("该用户账户未激活，UID：" + uid);

                pi.setCode(ResultInfo.HX_ACCOUNT_NOT_ACTIVATION.getCode());
                pi.setMsg("该用户账户未激活");
                return pi;
            }

            // 判断账户余额
            UsersAccount usersAccount = usersAccountMapper.selectByUid(uid);
            if (usersAccount == null) {
                pi.setCode(ResultInfo.EMPTY_DATA.getCode());
                pi.setMsg("账户不存在,请联系管理员");
                LOG.info("未查询到用户账户信息，UID：" + uid);
                return pi;

            } else {
                // 如果余额不足 提示： 您的当前账户余额不足，请充值
                if (buyMoney.compareTo(usersAccount.getBalance()) == 1) {
                    pi.setCode(ResultInfo.USER_BALANCE_INSUFFICIENT.getCode());
                    pi.setMsg("您的当前账户余额不足,请充值");
                    return pi;
                }
            }

        }

        if (!AppCons.TAKE_HEART_PCODE.equals(pCode)) {
            // 非随心取产品，判断用户是否有未完成交易（交易状态为0的记录）
            TradingExample tradingExample = new TradingExample();
            tradingExample.createCriteria().andUIdEqualTo(uid).andStatusEqualTo(AppCons.BUY_STATUS);
            List<Trading> tradingList = tradingMapper.selectByExample(tradingExample);
            if (tradingList != null && tradingList.size() > 0) {
                pi.setCode(ResultInfo.HAVE_PROJECT_NOT_PAY.getCode());
                pi.setMsg("您目前还有未支付的产品，请您到【我的理财-待支付】进行处理");
                return pi;
            }
        }

        // 验证用户当前购买金额
        int minMoney = product.getMinMoney(); // 最小购买金额
        BigDecimal increaseMoney = new BigDecimal(product.getIncreaMoney()); // 递增金额
        BigDecimal minBuyMoney = new BigDecimal(product.getMinMoney().toString());
        if (AppCons.TAKE_HEART_PCODE.equals(pCode)) {
            // 如果是随心取，购买金额只需大于递增金额
            minBuyMoney = increaseMoney;
        }

        // 2.大于最小购买金额kb
        if (buyMoney.compareTo(minBuyMoney) == -1) {
            pi.setCode(ResultInfo.AMOUNT_LESSTHAN_MIN_AMOUNT.getCode());
            pi.setMsg("购买金额不能小于" + minMoney + "元");
            LOG.info("购买失败，购买金额小于最低金额。pCode" + pCode);
            redisDao.delete(AppCons.TAKE_HEART + uid);
            return pi;
        }

        // 3.递增金额整数倍
        if (!BigDecimalUtils.divideByInt(buyMoney, increaseMoney)) {
            pi.setCode(ResultInfo.AMOUNT_ERROR.getCode());
            pi.setMsg("输入的金额格式不符合要求");
            LOG.info("购买失败，购买金额不合法。 pCode:" + pCode);
            redisDao.delete(AppCons.TAKE_HEART + uid);
            return pi;
        }

        // 4.大于产品剩余额度
        if (product.getPriorMoney() != null
                && product.getReachMoney().add(buyMoney).compareTo(product.getPriorMoney()) == 1) {
            pi.setResultInfo(ResultInfo.BUYMONEY_OVERFLOW);
            LOG.info("购买超过剩余可购金额。 pCode:" + pCode);
            redisDao.delete(AppCons.TAKE_HEART + uid);
            return pi;
        }

        // 验证是否超过该产品购买上限
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
                pi.setCode(ResultInfo.AMOUNT_THAN.getCode());
                pi.setMsg("购买失败，您已购买" + decimalFormat.format(userBuyMoney) + "元该产品，剩余可购金额为0元");
                LOG.info("用戶购买产品失败，购买额度达到上限.uid:" + uid);
                redisDao.delete(AppCons.TAKE_HEART + uid);
                return pi;

            } else { // 如果用户已买金额未超过上限
                // 查询用户当前可买金额
                BigDecimal canBuyMoney = buyLimit.subtract(userBuyMoney);
                // 如果当前可买金额小于用户输入金额
                if (canBuyMoney.compareTo(buyMoney) == -1) {
                    pi.setCode(ResultInfo.AMOUNT_THAN.getCode());
                    pi.setMsg("购买失败，您已购买" + decimalFormat.format(userBuyMoney) + "元该产品，剩余可购金额为"
                            + decimalFormat.format(canBuyMoney) + "元");
                    LOG.info("用戶购买产品失败，购买额度超过限制额度.uid:" + uid);
                    redisDao.delete(AppCons.TAKE_HEART + uid);
                    return pi;
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
                pi.setCode(ResultInfo.EMPTY_DATA.getCode());
                pi.setMsg("信息有误,请联系管理员");
                LOG.error("用户认证通过，却未查询到华兴银行账户信息。 UID：" + uid);
                return pi;

            } else { // 验证开户状态
                HxAccount hxAccount = hxAccountList.get(0);
                if (hxAccount.getStatus() != 1) {
                    pi.setCode(ResultInfo.RETURN_MESSAGE_NOT_SUCCESS.getCode());
                    pi.setMsg("账户未开通成功,请稍后再试");
                    return pi;
                }
            }

        } else if (product.getpType() == 0) {
            List<BankInfo> bankList = usersBankMapper.selectByUIdAndStatus(uid, "1");
            if (bankList == null || bankList.size() < 1) {
                pi.setCode(ResultInfo.RETURN_MESSAGE_NOT_SUCCESS.getCode());
                pi.setMsg("没有有效的银行卡绑卡JD");
                redisDao.delete(AppCons.TAKE_HEART + uid);
                return pi;
            }
        }

        LOG.info("用户信息验证通过 uid" + uid);
        return null;
    }

    /**
     * 验证PCODE 14位，包括pc_id,pc_type,pc_fix_type,p_id,
     * 生成规则：product_category中的code码+4位前补0的id
     * product_category中的code码生成规则：随机2位数字+（id前补0）4位id+2位g_id（前补0）+ type +
     * fix_type
     * 
     * 此验证为原项目code码验证逻辑
     * 
     * @param pCode
     * @return
     */
    private PageInfo validPcode(String pCode) {
        PageInfo pi = new PageInfo();

        // 随心取停止申购
        //      if (AppCons.TAKE_HEART_PCODE.equals(pCode)) {
        //            pi.setCode(ResultInfo.SUIXINQU_STOP_BUY.getCode());
        //          pi.setMsg("随心取停止申购");
        //          return pi;
        //      }

        String pcId = null, pcType = null, pcFixType = null, pId = null;
        pcId = pCode.substring(2, 6);
        // 产品类型id不能为空
        if (!NumberUtils.isNumber(pcId) || NumberUtils.toInt(pcId) <= 0) {
            pi.setCode(ResultInfo.FORMAT_PARAMS_ERROR.getCode());
            pi.setMsg("购买失败");
            LOG.info("code:1004 产品码错误,产品分类错误");
            return pi;
        }

        // product_category.type 类型，0:固定收益类，1:浮动类
        pcType = pCode.substring(8, 9);
        if (!NumberUtils.isNumber(pcType) || NumberUtils.toInt(pcType) < 0 || NumberUtils.toInt(pcType) > 1) {
            pi.setCode(ResultInfo.FORMAT_PARAMS_ERROR.getCode());
            pi.setMsg("购买失败");
            LOG.info("code:1004 产品码错误,产品分类标识错误");
            return pi;
        }

        // product_category.fix_type 只有固定类有，0:无,1:固定不变，2:区间
        pcFixType = pCode.substring(9, 10);
        if (NumberUtils.toInt(pcType) == AppCons.FIX_FLAG
                && (!NumberUtils.isNumber(pcFixType) || NumberUtils.toInt(pcFixType) < 1 || NumberUtils
                        .toInt(pcFixType) > 2)) {
            pi.setCode(ResultInfo.FORMAT_PARAMS_ERROR.getCode());
            pi.setMsg("购买失败");
            LOG.info("code:1004 产品码错误,产品分类子类标识错误");
            return pi;
        }

        // 产品id
        pId = pCode.substring(pCode.length() - 4);
        if (!NumberUtils.isNumber(pId) || NumberUtils.toInt(pId) <= 0) {
            pi.setCode(ResultInfo.FORMAT_PARAMS_ERROR.getCode());
            pi.setMsg("购买失败");
            LOG.info("code:1004 产品码错误,产品未找到");
            return pi;
        }

        // 只可购买稳赢宝产品
        //      if ("2".equals(pcFixType)) {
        //            pi.setResultInfo(ResultInfo.NOT_WENYINGBAO);
        //          return pi;
        //      }

        LOG.info("产品code验证通过 pCode:" + pCode);
        return null;
    }

    public boolean checkTakeHeart(String key) {
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
    public void setTakeHeart(String key) {
        redisDao.set(key, key);
        redisDao.expire(key, 10, TimeUnit.MINUTES);
    }
}
