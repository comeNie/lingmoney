package com.mrbt.lingmoney.service.trading.impl;

import java.io.IOException;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.commons.lang.math.NumberUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;

import com.alibaba.fastjson.JSON;
import com.mrbt.lingmoney.commons.cache.RedisDao;
import com.mrbt.lingmoney.mapper.ProductCustomerMapper;
import com.mrbt.lingmoney.mapper.ProductMapper;
import com.mrbt.lingmoney.mapper.SupportBankMapper;
import com.mrbt.lingmoney.mapper.TakeheartFixRateMapper;
import com.mrbt.lingmoney.mapper.TakeheartTransactionFlowMapper;
import com.mrbt.lingmoney.mapper.TradingFixInfoMapper;
import com.mrbt.lingmoney.mapper.TradingMapper;
import com.mrbt.lingmoney.mapper.UsersBankMapper;
import com.mrbt.lingmoney.mapper.UsersPropertiesMapper;
import com.mrbt.lingmoney.model.Product;
import com.mrbt.lingmoney.model.TakeheartTransactionFlow;
import com.mrbt.lingmoney.model.Trading;
import com.mrbt.lingmoney.model.TradingFixInfo;
import com.mrbt.lingmoney.model.UsersBank;
import com.mrbt.lingmoney.model.UsersProperties;
import com.mrbt.lingmoney.model.trading.PaymentClient;
import com.mrbt.lingmoney.service.bank.SingleBiddingService;
import com.mrbt.lingmoney.service.trading.JDPurchaseService;
import com.mrbt.lingmoney.service.trading.TradingFixRuleBuyService;
import com.mrbt.lingmoney.service.trading.TradingTakeheartBuyService;
import com.mrbt.lingmoney.service.trading.impl.TradingFixRuleBuyServiceImpl.CallbackType;
import com.mrbt.lingmoney.utils.AppCons;
import com.mrbt.lingmoney.utils.GetHtmlStr;
import com.mrbt.lingmoney.utils.PageInfo;
import com.mrbt.lingmoney.utils.ResultParame;
import com.mrbt.lingmoney.utils.ResultParame.ResultInfo;
import com.mrbt.pay.ValidResultVo;
import com.mrbt.pay.face.PayMentIF;
import com.mrbt.pay.jd.QuickJdPay;
import com.mrbt.pay.jd.vo.QuickJdPayVo;

/**
  *
  *@author yiban
  *@date 2018年1月4日 下午5:03:16
  *@version 1.0
 **/
@Service
public class JDPurchaseServiceImpl implements JDPurchaseService {
	
	// 如果为真，正常交易，如果为假，测试模式
	private static boolean payMode = true;
//	private static boolean payMode = false;
	
    private static final Logger LOG = LogManager.getLogger(JDPurchaseServiceImpl.class);

    @Autowired
    private RedisDao redisDao;
    @Autowired
    private UsersBankMapper usersBankMapper;
    @Autowired
    private UsersPropertiesMapper usersPropertiesMapper;
    @Autowired
    private ProductCustomerMapper productCustomerMapper;
    @Autowired
    private TakeheartTransactionFlowMapper takeheartTransactionFlowMapper;
    @Autowired
    private TradingMapper tradingMapper;
    @Autowired
    private SupportBankMapper supportBankMapper;
    @Autowired
    private QuickJdPay quickJdPay;
    @Autowired
    private TradingFixRuleBuyService tradingFixRuleBuyService;
    @Autowired
    private TradingFixInfoMapper tradingFixInfoMapper;
    @Autowired
    private ProductMapper productMapper;
    @Autowired
    private TradingTakeheartBuyService tradingTakeheartBuyService;
    @Autowired
    private SingleBiddingService singleBiddingService;
    @Autowired
    private TakeheartFixRateMapper takeheartFixRateMapper;
    @Autowired
	public PayMentIF jdPay;

    @Override
    public PageInfo getSecretCode(String uId, Integer tid, String pCode, Integer usersBankId) {
        PageInfo pageInfo = new PageInfo();

//        if (StringUtils.isEmpty(token) || StringUtils.isEmpty(tid) || StringUtils.isEmpty(pCode)
//                || StringUtils.isEmpty(usersBankId)) {
//            pageInfo.setResultInfo(ResultInfo.PARAM_MISS);
//            return pageInfo;
//        }

//        String uid;
//        try {
//            uid = utilService.queryUid(AppCons.TOKEN_VERIFY + token);
//            if (StringUtils.isEmpty(uid)) {
//                pageInfo.setResultInfo(ResultInfo.LOGIN_OVERTIME);
//                return pageInfo;
//            }
//        } catch (Exception e) {
//            pageInfo.setResultInfo(ResultInfo.SERVER_ERROR);
//            e.printStackTrace();
//            return pageInfo;
//        }
        
        Product product = productCustomerMapper.selectByCode(pCode);
        if (product == null || product.getpType() != 0) {
            pageInfo.setCode(ResultInfo.DO_FAIL.getCode());
            pageInfo.setMsg("不是京东产品");
            return pageInfo;
        }

        PaymentClient paymentClient = new PaymentClient();

        if (AppCons.TAKE_HEART_PCODE.equals(pCode)) {// 随心取
            TakeheartTransactionFlow flow = takeheartTransactionFlowMapper.selectByPrimaryKey(tid);
            if (flow == null) {
                pageInfo.setResultInfo(ResultInfo.NO_DATA);
                return pageInfo;
            }

            if (!uId.equals(flow.getuId())) {
                pageInfo.setCode(ResultInfo.DO_FAIL.getCode());
                pageInfo.setMsg("交易信息不符");
                return pageInfo;
            }

            if (flow.getState() != 2) {
                pageInfo.setCode(ResultInfo.DO_FAIL.getCode());
                pageInfo.setMsg("交易状态有误");
                return pageInfo;
            }

            paymentClient.setBuyMoney(flow.getMoney().toString());
            paymentClient.setDizNumber(flow.getNumber());
            paymentClient.setuId(flow.getuId());

        } else {// 稳赢宝
            Trading trading = tradingMapper.selectByPrimaryKey(tid);
            if (trading == null) {
                pageInfo.setResultInfo(ResultInfo.NO_DATA);
                return pageInfo;
            }

            if (!uId.equals(trading.getuId())) {
                pageInfo.setCode(ResultInfo.DO_FAIL.getCode());
                pageInfo.setMsg("交易信息不符");
                return pageInfo;
            }

//            if (trading.getStatus() != AppCons.BUY_STATUS) {
            if (trading.getStatus() != AppCons.BUY_TRADING) {
                pageInfo.setCode(ResultInfo.DO_FAIL.getCode());
                pageInfo.setMsg("交易状态有误");
                return pageInfo;
            }

            paymentClient.setBuyMoney(trading.getFinancialMoney().toString());
            paymentClient.setDizNumber(trading.getBizCode());
            paymentClient.setuId(trading.getuId());
        }

//        //TODO 测试 用完删除
//        paymentClient.setBuyMoney("0.01");

        // 验证签名之前根据订单号和交易状态查询该订单是否可以提交
        String dizNum = paymentClient.getDizNumber();
        if (StringUtils.isEmpty(dizNum)) {
            LOG.info("传入参数,订单号为空,原交易不允许此操作");
            pageInfo.setCode(8);
            pageInfo.setMsg("原交易不允许此操作");
            return pageInfo;
        }

        // 将订单编号放入redis，如果有，则return，没有，则往下
        if (!checkBankRedis(dizNum, 1)) {
            pageInfo.setCode(505);
            pageInfo.setMsg("一分钟内不可多次获取验证码，请耐心等待");
            return pageInfo;
        }

        if (StringUtils.isEmpty(paymentClient.getuId())) {
            deleteBankRedis(dizNum, 1);
            pageInfo.setCode(4);
            pageInfo.setMsg("用户不存在");
            return pageInfo;
        }

        UsersBank usersBank = usersBankMapper.selectByPrimaryKey(usersBankId);
        if (usersBank == null) {
            deleteBankRedis(dizNum, 1);
            pageInfo.setCode(5);
            pageInfo.setMsg("银行卡不存在");
            return pageInfo;
        }

        UsersProperties usersProperties = usersPropertiesMapper.selectByuId(paymentClient.getuId());
        if (usersProperties.getCertification() != 1 && usersProperties.getCertification() != 3) {
            deleteBankRedis(dizNum, 1);
            pageInfo.setCode(7);
            pageInfo.setMsg("用户未绑卡");
            return pageInfo;
        }

        if (product.getpType() != 0) {
            deleteBankRedis(dizNum, 1);
            pageInfo.setCode(14);
            pageInfo.setMsg("不是京东产品");
            return pageInfo;
        }

        /**
         * 参数: card_bank - 持卡人支付卡号发卡行 例如ABC（中国农业银行） card_no - 持卡人支付的卡号
         * card_name - 持卡人的姓名 card_idno - 持卡人的身份证号 card_phone - 持卡人的手机号
         * trade_id - 交易号，数字或字母，长度为30个字符串 trade_amount - 交易的金额
         * 
         * trade_code - 手机验证码 trade_notice - 通知地址（可空） trade_date -
         * 交易日期格式为(yyyyMMdd) trade_time - 交易时间格式为(HHmmss)
         */
        String card_bank = "", card_no = "", card_name = "", card_idno = "", card_phone = "", trade_id = "";
        BigDecimal trade_amount = new BigDecimal(0);

        card_bank = supportBankMapper.selectBankInfoByCode(usersBank.getBankcode()).getBankShort().replaceAll(" ", "");
        card_no = usersBank.getNumber().replaceAll(" ", "");
        card_name = usersProperties.getName().replaceAll(" ", "");
        card_idno = usersProperties.getIdCard().replaceAll(" ", "");
        card_phone = usersBank.getTel().replaceAll(" ", "");
        // 验证卡号
        if (!checkBankNo(card_no)) {
            deleteBankRedis(dizNum, 1);
            LOG.info("绑定银行卡失败,银行卡号不正确");
            pageInfo.setCode(11);
            pageInfo.setMsg("银行卡号不正确");
            return pageInfo;
        }
        // 验证身份证
        if (!checkIdCard(card_idno)) {
            deleteBankRedis(dizNum, 1);
            LOG.info("绑定银行卡失败,身份证号码不正确");
            pageInfo.setCode(12);
            pageInfo.setMsg("身份证号码不正确");
            return pageInfo;
        }
        // 验证手机号码
        if (!checkMobile(card_phone)) {
            deleteBankRedis(dizNum, 1);
            LOG.info("绑定银行卡失败,手机号码格式错误");
            pageInfo.setCode(13);
            pageInfo.setMsg("手机号码格式错误");
            return pageInfo;
        }
        // 验证真实姓名
        if (!checkName(card_name)) {
            deleteBankRedis(dizNum, 1);
            LOG.info("绑定银行卡失败,真实姓名不是中文");
            pageInfo.setCode(14);
            pageInfo.setMsg("姓名须填写中文");
            return pageInfo;
        }

        trade_id = paymentClient.getDizNumber();
        trade_amount = new BigDecimal(paymentClient.getBuyMoney()).multiply(new BigDecimal(100));
        // trade_amount = new BigDecimal(100);

        String n = "\n";
        StringBuffer sb = new StringBuffer();
        sb.append("\n银行简称：" + card_bank + n);
        sb.append("银行卡号：" + card_no + n);
        sb.append("姓名：" + card_name + n);
        sb.append("身份证号：" + card_idno + n);
        sb.append("手机号：" + card_phone + n);
        sb.append("订单号：" + trade_id + n);
        sb.append("交易金额：" + trade_amount + n);

        LOG.info("第一步，交易信息为：" + n + sb.toString());

        QuickJdPayVo quickJdPayVo = null;
        if (payMode) {
        	 quickJdPayVo = quickJdPay.signBank(card_bank, card_no, card_name, card_idno, card_phone, trade_id,
        			trade_amount);
        } else {
        	quickJdPayVo = new QuickJdPayVo();
        	quickJdPayVo.setResultCode("0000");
        }

        LOG.info("第一步，交易结果为：{}_{}", quickJdPayVo.getResultCode(), quickJdPayVo.getResultInfo());

        if (quickJdPayVo.isOk()) {// 成功
            deleteBankRedis(dizNum, 1);
            pageInfo.setCode(ResultInfo.SUCCESS.getCode());
            pageInfo.setMsg(quickJdPayVo.getResultInfo());
            pageInfo.setObj(usersBankId);

        } else {// 处理中或失败
            deleteBankRedis(dizNum, 1);
            pageInfo.setCode(1);
            pageInfo.setMsg(quickJdPayVo.getResultInfo());
            pageInfo.setObj(usersBankId);
        }

        return pageInfo;
    }

    /**
     * 判断redis中身份证号是否存在
     * 
     * @param idCard
     *            身份证号
     * @param type
     *            类型 1发送验证码 2提交
     * @return
     */
    public boolean checkBankRedis(String idCard, Integer type) {
        boolean flag = false;
        if (type == 1) {// 验证码
            try {
                // 查询redis
                String result_str = (String) redisDao.get(idCard + "_1");
                // 如果存在，则false
                if (result_str != null && !result_str.equals("")) {
                    flag = false;
                } else {
                    // 不存在，则放入redis，过期时间1分钟
                    redisDao.set(idCard + "_1", idCard + "_1");
                    redisDao.expire(idCard + "_1", 1, TimeUnit.MINUTES);
                    flag = true;
                }
            } catch (Exception e) {

            }
        } else if (type == 2) {// 提交
            try {
                // 查询redis
                String result_str = (String) redisDao.get(idCard + "_2");
                // 如果存在，则false
                if (result_str != null && !result_str.equals("")) {
                    flag = false;
                } else {
                    // 不存在，则放入redis，过期时间2分钟
                    redisDao.set(idCard + "_2", idCard + "_2");
                    redisDao.expire(idCard + "_2", 2, TimeUnit.MINUTES);
                    flag = true;
                }
            } catch (Exception e) {

            }
        }
        return flag;
    }

    public void deleteBankRedis(String idCard, Integer type) {
        if (type == 1) {// 验证码
            try {
                // 清除redis
                redisDao.delete(idCard + "_1");
            } catch (Exception e) {

            }
        } else if (type == 2) {// 提交
            try {
                // 清除redis
                redisDao.delete(idCard + "_2");
            } catch (Exception e) {

            }
        }
    }

    //卡号
    public static boolean checkBankNo(String bankNo) {
        if (bankNo == null || bankNo.length() < 16 || bankNo.length() > 19) {
            return false;
        } else if (!NumberUtils.isNumber(bankNo)) {
            return false;
        } else {
            return true;
        }
    }

    //身份证
    public static boolean checkIdCard(String idCard) {
        String idCardRegex15 = "^[1-9]\\d{7}((0\\d)|(1[0-2]))(([0|1|2]\\d)|3[0-1])\\d{3}$";
        String idCardRegex18 = "^[1-9]\\d{5}[1-9]\\d{3}((0\\d)|(1[0-2]))(([0|1|2]\\d)|3[0-1])\\d{3}([0-9]|X|x)$";
        boolean result = (Pattern.compile(idCardRegex15).matcher(idCard).matches())
                || (Pattern.compile(idCardRegex18).matcher(idCard).matches());
        return result;
    }

    //手机号码
    public static boolean checkMobile(String mobile) {
        String mobileRegex = "^[1][34578][0-9]{9}$";
        return Pattern.compile(mobileRegex).matcher(mobile).matches();
    }

    //真实姓名
    public static boolean checkName(String name) {
        String nameRegex = "^[\u4e00-\u9fa5]+$";
        return Pattern.compile(nameRegex).matcher(name).matches();
    }

    @Override
    public PageInfo jdPay(String uId, Integer tid, String pCode, Integer usersBankId, String verifyCode, int clent) {
        PageInfo pageInfo = new PageInfo();

//        if (StringUtils.isEmpty(token) || StringUtils.isEmpty(tid) || StringUtils.isEmpty(pCode)
//                || StringUtils.isEmpty(usersBankId) || StringUtils.isEmpty(verifyCode)) {
//            pageInfo.setResultInfo(ResultInfo.PARAM_MISS);
//            return pageInfo;
//        }
//
//        String uid;
//        try {
//            uid = utilService.queryUid(AppCons.TOKEN_VERIFY + token);
//            if (StringUtils.isEmpty(uid)) {
//                pageInfo.setResultInfo(ResultInfo.LOGIN_OVERTIME);
//                return pageInfo;
//            }
//        } catch (Exception e) {
//            pageInfo.setResultInfo(ResultInfo.SERVER_ERROR);
//            e.printStackTrace();
//            return pageInfo;
//        }

        Product product = productCustomerMapper.selectByCode(pCode);
        if (product == null || product.getpType() != 0) {
            pageInfo.setCode(ResultInfo.DO_FAIL.getCode());
            pageInfo.setMsg("不是京东产品");
            return pageInfo;
        }

        PaymentClient paymentClient = new PaymentClient();

        if (AppCons.TAKE_HEART_PCODE.equals(pCode)) {// 随心取
            TakeheartTransactionFlow flow = takeheartTransactionFlowMapper.selectByPrimaryKey(tid);
            if (flow == null) {
                pageInfo.setResultInfo(ResultInfo.NO_DATA);
                return pageInfo;
            }

            if (!uId.equals(flow.getuId())) {
                pageInfo.setCode(ResultInfo.DO_FAIL.getCode());
                pageInfo.setMsg("交易信息不符");
                return pageInfo;
            }

            switch (flow.getState()) {
            case 0:
                LOG.info("支付购买随心取产品,该笔交易状态为" + flow.getState() + "(失败),请勿重复交易");
                pageInfo.setCode(2);
                pageInfo.setMsg("购买失败，请检查网络");
                pageInfo.setObj(2);
                return pageInfo;
            case 1:
                LOG.info("支付购买随心取产品,该笔交易状态为" + flow.getState() + "(成功),请勿重复交易");
                pageInfo.setCode(0);
                pageInfo.setMsg("购买成功");
                pageInfo.setObj(0);
                return pageInfo;
            case 2:
                LOG.info("支付购买随心取产品,该笔交易状态为" + flow.getState() + "(待处理),通过");
                break;
            case 3:
                LOG.info("支付购买随心取产品,该笔交易状态为" + flow.getState() + "(冻结),请勿重复交易");
                pageInfo.setCode(1);
                pageInfo.setMsg("订单处理中");
                pageInfo.setObj(1);
                return pageInfo;
            }

            paymentClient.setBuyMoney(flow.getMoney().toString());
            paymentClient.setDizNumber(flow.getNumber());
            paymentClient.setuId(flow.getuId());
            paymentClient.settId(flow.gettId().toString());
            paymentClient.setProductCode(pCode);
            paymentClient.setInfoId(tid.toString());

        } else {// 稳赢宝
            Trading trading = tradingMapper.selectByPrimaryKey(tid);
            if (trading == null) {
                pageInfo.setResultInfo(ResultInfo.NO_DATA);
                return pageInfo;
            }

            if (!uId.equals(trading.getuId())) {
                pageInfo.setCode(ResultInfo.DO_FAIL.getCode());
                pageInfo.setMsg("交易信息不符");
                return pageInfo;
            }

//            if (trading.getStatus() != AppCons.BUY_STATUS) {
            if (trading.getStatus() != AppCons.BUY_TRADING) {
                pageInfo.setCode(ResultInfo.DO_FAIL.getCode());
                pageInfo.setMsg("交易状态有误");
                return pageInfo;
            }

            paymentClient.setBuyMoney(trading.getFinancialMoney().toString());
            paymentClient.setDizNumber(trading.getBizCode());
            paymentClient.setuId(trading.getuId());
            paymentClient.settId(tid.toString());
            paymentClient.setProductCode(pCode);
            paymentClient.setInfoId(tid.toString());

        }

//        //TODO 测试 用完删除
//        paymentClient.setBuyMoney("0.01");

        // 验证签名之前根据订单号和交易状态查询该订单是否可以提交
        String dizNum = paymentClient.getDizNumber();
        if (StringUtils.isEmpty(dizNum)) {
            LOG.info("传入参数,订单号为空,原交易不允许此操作");
            pageInfo.setCode(8);
            pageInfo.setMsg("交易订单号为空");
            return pageInfo;
        }
        // 将订单编号放入redis，如果有，则return，没有，则往下
        if (!checkBankRedis(dizNum, 2)) {
            pageInfo.setCode(505);
            pageInfo.setMsg("两分钟内不可多次提交，请耐心等待");
            return pageInfo;
        }

        UsersProperties usersProperties = usersPropertiesMapper.selectByuId(paymentClient.getuId());
        if (StringUtils.isEmpty(paymentClient.getuId()) || usersProperties == null) {
            deleteBankRedis(dizNum, 2);
            LOG.info("用户不存在");
            pageInfo.setCode(4);
            pageInfo.setMsg("用户不存在");
            return pageInfo;
        }

        UsersBank usersBank = usersBankMapper.findByUserBankId(usersBankId);
        if (usersBank == null) {
            deleteBankRedis(dizNum, 2);
            LOG.info("银行卡不存在");
            pageInfo.setCode(5);
            pageInfo.setMsg("银行卡不存在");
            return pageInfo;
        }

        if (usersProperties.getCertification() != 1 && usersProperties.getCertification() != 3 ) {
            deleteBankRedis(dizNum, 2);
            LOG.info("用户未绑卡");
            pageInfo.setCode(7);
            pageInfo.setMsg("用户未绑卡");
            return pageInfo;
        }

        /**
         * 参数: card_bank - 持卡人支付卡号发卡行 例如ABC（中国农业银行） card_no - 持卡人支付的卡号
         * card_name - 持卡人的姓名 card_idno - 持卡人的身份证号 card_phone - 持卡人的手机号
         * trade_id - 交易号，数字或字母，长度为30个字符串 trade_amount - 交易的金额
         * 
         * trade_code - 手机验证码 trade_notice - 通知地址（可空） trade_date -
         * 交易日期格式为(yyyyMMdd) trade_time - 交易时间格式为(HHmmss)
         */
        String card_bank = "", card_no = "", card_name = "", card_idno = "", card_phone = "", trade_id = "", trade_code = "", trade_notice = "", trade_date = "", trade_time = "";
        BigDecimal trade_amount = new BigDecimal(0);

        card_bank = supportBankMapper.selectBankInfoByCode(usersBank.getBankcode()).getBankShort().replaceAll(" ", "");
        card_no = usersBank.getNumber().replaceAll(" ", "");
        card_name = usersProperties.getName().replaceAll(" ", "");
        card_idno = usersProperties.getIdCard().replaceAll(" ", "");
        card_phone = usersBank.getTel().replaceAll(" ", "");
        // 验证卡号
        if (!checkBankNo(card_no)) {
            deleteBankRedis(dizNum, 2);
            LOG.info("绑定银行卡失败,银行卡号不正确");
            pageInfo.setCode(11);
            pageInfo.setMsg("银行卡号不正确");
            return pageInfo;
        }
        // 验证身份证
        if (!checkIdCard(card_idno)) {
            deleteBankRedis(dizNum, 2);
            LOG.info("绑定银行卡失败,身份证号码不正确");
            pageInfo.setCode(12);
            pageInfo.setMsg("身份证号码不正确");
            return pageInfo;
        }
        // 验证手机号码
        if (!checkMobile(card_phone)) {
            deleteBankRedis(dizNum, 2);
            LOG.info("绑定银行卡失败,手机号码格式错误");
            pageInfo.setCode(13);
            pageInfo.setMsg("手机号码格式错误");
            return pageInfo;
        }
        // 验证真实姓名
        if (!checkName(card_name)) {
            deleteBankRedis(dizNum, 2);
            LOG.info("绑定银行卡失败,真实姓名不是中文");
            pageInfo.setCode(14);
            pageInfo.setMsg("姓名须填写中文");
            return pageInfo;
        }
        trade_id = paymentClient.getDizNumber();
        trade_amount = new BigDecimal(paymentClient.getBuyMoney()).multiply(new BigDecimal(100));
        // trade_amount = new BigDecimal(100);
        trade_code = verifyCode;
        if(clent == 0) {
        	trade_notice = AppCons.QUICK_NOTICE + trade_id + "_" + paymentClient.gettId() + "_"  + paymentClient.getInfoId() + "_" + paymentClient.getuId();
        } else {
        	trade_notice = AppCons.QUICK_NOTICE_APP + trade_id + "_" + paymentClient.gettId() + "_"  + paymentClient.getInfoId() + "_" + paymentClient.getuId();
        }
        trade_date = getDateStrByYMD(new Date());
        trade_time = getDateStrByHMS(new Date());

        String n = "\n";
        StringBuffer sb = new StringBuffer();
        sb.append("\n银行简称：" + card_bank + n);
        sb.append("银行卡号：" + card_no + n);
        sb.append("姓名：" + card_name + n);
        sb.append("身份证号：" + card_idno + n);
        sb.append("手机号：" + card_phone + n);
        sb.append("订单号：" + trade_id + n);
        sb.append("交易金额：" + trade_amount + n);
        sb.append("验证码：" + trade_code + n);
        sb.append("通知地址：" + trade_notice + n);
        sb.append("交易日期" + trade_date + n);
        sb.append("交易时间" + trade_time + n);

        LOG.info("第二步，交易信息为：" + n + sb.toString());
        QuickJdPayVo quickJdPayVo = null;

		if (payMode) {
			quickJdPayVo = quickJdPay.quickSell(card_bank, card_no, card_name, card_idno, card_phone,
					trade_id, trade_amount, trade_code, trade_notice, trade_date, trade_time);
		} else {
			quickJdPayVo = new QuickJdPayVo();
			quickJdPayVo.setResultCode("0000");
		}

        if (quickJdPayVo.isOk()) {// 成功
            deleteBankRedis(dizNum, 2);
            if (AppCons.TAKE_HEART_PCODE.equals(pCode)) {// 随心取
                tradingTakeheartBuyService.handleTradingResult(tid, CallbackType.ok);

            } else {// 稳赢宝
                tradingFixRuleBuyService.handleBuyProduct(tid, paymentClient.getDizNumber(), uId, CallbackType.ok);
                LOG.info("购买支付第二步--成功--quickJdPayVo.isOk()");
                singleBiddingService.buyWeiyingbaoGetLingbao(uId, tid);
            }

            pageInfo.setCode(200);
            pageInfo.setMsg(quickJdPayVo.getResultInfo());
            pageInfo.setObj(0);

        } else if (quickJdPayVo.getResultCode().equals("0001") || quickJdPayVo.getResultCode().equals("6")) {// 处理中
            deleteBankRedis(dizNum, 2);
            LOG.info("购买支付第二步--处理中");
            if (AppCons.TAKE_HEART_PCODE.equals(pCode)) {// 随心取
                tradingTakeheartBuyService.handleTradingResult(tid, CallbackType.trading);

            } else {// 稳赢宝
                tradingFixRuleBuyService.handleBuyProduct(Integer.parseInt(paymentClient.gettId()),
                        paymentClient.getDizNumber(), paymentClient.getuId(), CallbackType.trading);
            }

            pageInfo.setCode(251);
            pageInfo.setMsg(quickJdPayVo.getResultInfo());
            pageInfo.setObj(1);

        } else {// 失败
            deleteBankRedis(dizNum, 2);
            LOG.info("购买支付第二步--非成功、非处理中");
            if (AppCons.TAKE_HEART_PCODE.equals(pCode)) {// 随心取
                tradingTakeheartBuyService.handleTradingResult(tid, CallbackType.failure);

            } else {// 稳赢宝
                tradingFixRuleBuyService.handleBuyProduct(Integer.parseInt(paymentClient.gettId()),
                        paymentClient.getDizNumber(), paymentClient.getuId(), CallbackType.failure);
            }

            pageInfo.setCode(250);
            pageInfo.setMsg(quickJdPayVo.getResultInfo());
            pageInfo.setObj(2);
        }

        return pageInfo;
    }

    public static SimpleDateFormat sf_m = new SimpleDateFormat("yyyyMMdd");
    public static SimpleDateFormat sf_h = new SimpleDateFormat("HHmmss");

    public static String getDateStrByYMD(Date dt) {
        return sf_m.format(dt);
    }

    public static String getDateStrByHMS(Date dt) {
        return sf_h.format(dt);
    }

    @Override
    public JSONObject quickNotice(HttpServletRequest request, String note) {
        JSONObject json = new JSONObject();

        String type = request.getParameter("type");// 交易类型
        String biz_code = request.getParameter("id");// 交易号
        String amount = request.getParameter("amount");// 交易金额
        String currency = request.getParameter("currency");// 交易币种
        String date = request.getParameter("date");// 交易日期
        String time = request.getParameter("time");// 交易时间
        String status = request.getParameter("status");// 交易返回状态
        String code = request.getParameter("code");// 交易返回码
        String desc = request.getParameter("desc");// 交易返回码信息

        LOG.info("\n快捷支付回调被调用:" + biz_code + "\n金额" + amount + "\n交易币种" + currency + "\n交易日期" + date + "\n交易时间" + time + "\nnote:" + note
                + "\n交易返回状态:" + status + "\n交易返回码" + code + "\n交易返回码信息:" + desc + "\n交易类型" + type);

        json.put("code", 200);
        json.put("msg", "成功");
        try {
            if (note != null && !note.equals("")) {
                String[] ids = note.split("_");
                String biz_number = ids[0];
                int tId = Integer.parseInt(ids[1]);
                int flawId = Integer.parseInt(ids[2]);
                
                QuickJdPayVo rel = null;

                if (payMode) {
                	rel = quickJdPay.queryByNet(biz_number);
                } else {
                	rel = new QuickJdPayVo();
                	rel.setResultCode("0000");
                }
                
                LOG.info("\nnote" + note + "查询结果:" + JSON.toJSON(rel));
                
                if (rel != null) {
                    if (rel.isOk()) {
                        // 交易处理
                        Trading trading = tradingMapper.selectByPrimaryKey(tId);
                        if (trading != null) {
                    		if (AppCons.TAKE_HEART_PCODE.equals(trading.getpCode())) {// 随心取
                    			LOG.info("\nnote" + note + "处理随心取数据:" + tId);
                            	
                                tradingTakeheartBuyService.handleTradingResult(flawId, CallbackType.ok);

                            } else {// 稳赢宝
                            	if (trading.getStatus() != AppCons.BUY_OK) {
                            		LOG.info("\nnote" + note + "处理稳赢宝数据:" + tId);
                            		tradingFixRuleBuyService.handleBuyProduct(tId, trading.getBizCode(), trading.getuId(), CallbackType.ok);
                            		LOG.info("购买支付第二步--成功--quickJdPayVo.isOk()");
                            		singleBiddingService.buyWeiyingbaoGetLingbao(trading.getuId(), tId);
                            	}
                            }
                        	LOG.info("\nnote" + note + "订单交易已成功:" + tId);
                        } else {
                        	LOG.info("\nnote" + note + "订单不存在:" + tId);
                            json.put("code", 207);
                            json.put("msg", "交易处理失败");
                        }
                    } else {
                    	LOG.info("\nnote" + note + "查询结果返回错误:" + rel);
                        json.put("code", 207);
                        json.put("msg", "交易处理失败");
                    }
                } else {
                	LOG.info("\nnote" + note + "查询结果返回为空");
                    json.put("code", 207);
                    json.put("msg", "交易处理失败");
                }
            } else {
            	LOG.info("\nnote" + note + "参数错误");
                json.put("code", 207);
                json.put("msg", "参数错误");
            }
        } catch (Exception e) {
        	LOG.info("\nnote" + note + "程序错误");
            json.put("code", 500);
            json.put("msg", "程序错误");
        }

        return json;
    }

    @Override
    public void gotoPurchase(Integer jdTradingId, HttpServletResponse response, Integer takeheartTid) {
        try {
            if (!StringUtils.isEmpty(jdTradingId)) {// 稳赢宝
                Trading trading = tradingMapper.selectByPrimaryKey(jdTradingId);
                if (trading != null) {
                    TradingFixInfo tfi = tradingFixInfoMapper.selectFixInfoByTid(jdTradingId);

                    if (tfi != null) {
                        response.getWriter().write(
                                GetHtmlStr.getHtmlStrForPay(trading.getpId(), trading.getPcId(),
                                        trading.getFinancialMoney(), tfi.getId(), trading.getBizCode(), jdTradingId));
                    }
                }

            } else if (!StringUtils.isEmpty(takeheartTid)) {// 随心取
                TakeheartTransactionFlow flow = takeheartTransactionFlowMapper.selectByPrimaryKey(takeheartTid);
                if (flow != null) {
                    Product product = productMapper.selectByPrimaryKey(flow.getpId());

                    response.getWriter().write(
                            GetHtmlStr.getHtmlStrForPay(flow.getpId(), product.getPcId(), flow.getMoney(),
                                    flow.getId(), flow.getNumber(), flow.gettId()));
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public PageInfo getAllTakeHeartFixRate() {
        PageInfo pageInfo = new PageInfo();

        List<Map<String, Object>> list = takeheartFixRateMapper.getAllTakeHeartFixRate();
        if (list != null && list.size() > 0) {
            pageInfo.setResultInfo(ResultInfo.SUCCESS);
            pageInfo.setRows(list);

        } else {
            pageInfo.setResultInfo(ResultInfo.NO_DATA);
        }

        return pageInfo;
    }

	@Override
	public String onlineNotity(HttpServletRequest request, String note) {
		String resStr = "ok";
		// 获取参数
		String v_oid = request.getParameter("v_oid"); // 订单号
		// String v_pmode = request.getParameter("v_pmode"); //
		// 支付方式中文说明，如"中行长城信用卡"
		String v_pstatus = request.getParameter("v_pstatus"); // 支付结果，20支付完成；30支付失败；
		// String v_pstring = request.getParameter("v_pstring"); //
		// 对支付结果的说明，成功时（v_pstatus=20）为"支付成功"，支付失败时（v_pstatus=30）为"支付失败"
		String v_amount = request.getParameter("v_amount"); // 订单实际支付金额
		// String v_moneytype = request.getParameter("v_moneytype"); // 币种
		String v_md5str = request.getParameter("v_md5str"); // MD5校验码

		LOG.info("\n========================网银通知URL=================================\nnote:" + note + "\tv_oid:"
				+ v_oid + "\tv_pstatus:" + v_pstatus + "\tv_amount:" + v_amount + "\tv_md5str:" + v_md5str);
		
		if (v_oid != null && v_pstatus != null && v_amount != null && v_md5str != null) {

			ValidResultVo validRes = null;

			if (payMode) {
				validRes = jdPay.validPayResult(v_oid, v_pstatus, new BigDecimal(v_amount), v_md5str);
			} else {
				validRes = new ValidResultVo();
				validRes.setResultCode("0000");
			}

		   // JSON.toJSON(validRes):{"result":true,"resultCode":"200","resultMsg":"支付成功"}  validRes.isResult():true
			LOG.info("\n" + note + "\n" + JSON.toJSON(validRes) + "\n" + validRes.isResult()); 

			if (validRes.isResult()) {
				String[] ids = note.split("_"); //tId + "_" + infoId + "_" + uId
				int tId = Integer.parseInt(ids[ResultParame.ResultNumber.ZERO.getNumber()]);
				int infoId = Integer.parseInt(ids[ResultParame.ResultNumber.ONE.getNumber()]);
				String uId = String.valueOf(ids[ResultParame.ResultNumber.TWO.getNumber()]);
				
				// 交易处理
                Trading trading = tradingMapper.selectByPrimaryKey(tId);
                if (trading != null) {
                    if (AppCons.TAKE_HEART_PCODE.equals(trading.getpCode())) {// 随心取
                    	
                        tradingTakeheartBuyService.handleTradingResult(infoId, CallbackType.ok);
                        
                    } else {// 稳赢宝
                    	if (trading.getStatus() != AppCons.BUY_OK) {
                    		tradingFixRuleBuyService.handleBuyProduct(tId, trading.getBizCode(), trading.getuId(), CallbackType.ok);
                    		LOG.info("购买支付第二步--成功--quickJdPayVo.isOk()");
                    		singleBiddingService.buyWeiyingbaoGetLingbao(trading.getuId(), tId);
						} 
                    }
                } else {
                	resStr = "error";
                }
			}else {
				resStr = "error";
			}
		}else {
			resStr = "error";
		}
		return resStr;
	}

	@Override
	public String onlineCallBank(HttpServletRequest request, String note, ModelMap modelMap) {
		// 获取参数
		String v_oid = request.getParameter("v_oid"); // 订单号
		// String v_pmode = request.getParameter("v_pmode"); //
		// 支付方式中文说明，如"中行长城信用卡"
		String v_pstatus = request.getParameter("v_pstatus"); // 支付结果，20支付完成；30支付失败；
		// String v_pstring = request.getParameter("v_pstring"); //
		// 对支付结果的说明，成功时（v_pstatus=20）为"支付成功"，支付失败时（v_pstatus=30）为"支付失败"
		String v_amount = request.getParameter("v_amount"); // 订单实际支付金额
		// String v_moneytype = request.getParameter("v_moneytype"); // 币种
		String v_md5str = request.getParameter("v_md5str"); // MD5校验码

		LOG.info("\n========================网银返回商户URL=================================\n订单号:"
		+ v_oid + "\n支付结果，20支付完成；30支付失败:" + v_pstatus+ "\n订单实际支付金额:" + v_amount+ "\nMD5校验码:" + v_md5str);
		
		try {

			ValidResultVo validRes = null;

			if (payMode) {
				validRes = jdPay.validPayResult(v_oid, v_pstatus, new BigDecimal(v_amount), v_md5str);
			} else {
				validRes = new ValidResultVo();
				validRes.setResultCode("0000");
			}

			// JSON.toJSON(validRes):{"result":true,"resultCode":"200","resultMsg":"支付成功"}  validRes.isResult():true
			LOG.info("\n" + note + "\n" + JSON.toJSON(validRes) + "\n" + validRes.isResult()); 

			if (validRes.isResult()) {
				String[] ids = note.split("_"); //tId + "_" + infoId + "_" + uId
				int tId = Integer.parseInt(ids[ResultParame.ResultNumber.ZERO.getNumber()]);
				int infoId = Integer.parseInt(ids[ResultParame.ResultNumber.ONE.getNumber()]);
				String uId = String.valueOf(ids[ResultParame.ResultNumber.TWO.getNumber()]);
				
				// 交易处理
                Trading trading = tradingMapper.selectByPrimaryKey(tId);
                if (trading != null) {
                    if (AppCons.TAKE_HEART_PCODE.equals(trading.getpCode())) {// 随心取
                    	
                        tradingTakeheartBuyService.handleTradingResult(infoId, CallbackType.ok);
                        
                    } else {// 稳赢宝
                    	if (trading.getStatus() != AppCons.BUY_OK) {
                    		tradingFixRuleBuyService.handleBuyProduct(tId, trading.getBizCode(), trading.getuId(), CallbackType.ok);
                    		LOG.info("购买支付第二步--成功--quickJdPayVo.isOk()");
                    		singleBiddingService.buyWeiyingbaoGetLingbao(trading.getuId(), tId);
                    		modelMap.addAttribute("status", ResultParame.ResultNumber.ZERO.getNumber());
						} else {
							modelMap.addAttribute("status", ResultParame.ResultNumber.ZERO.getNumber());
						}
                    }
                } else {
                	modelMap.addAttribute("status", ResultParame.ResultNumber.ONE.getNumber());
                }
             } else {
					modelMap.addAttribute("status", ResultParame.ResultNumber.ONE.getNumber());
			}
		} catch (Exception e) {
			e.printStackTrace();
			modelMap.addAttribute("status", ResultParame.ResultNumber.ONE.getNumber());
		}
		return "transfeerCallBackResult";
	}
}
