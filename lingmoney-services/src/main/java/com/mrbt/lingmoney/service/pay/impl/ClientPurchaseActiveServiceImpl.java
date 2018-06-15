package com.mrbt.lingmoney.service.pay.impl;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;

import com.mrbt.lingmoney.mapper.ProductMapper;
import com.mrbt.lingmoney.mapper.UsersBankMapper;
import com.mrbt.lingmoney.mapper.UsersPropertiesMapper;
import com.mrbt.lingmoney.model.Product;
import com.mrbt.lingmoney.model.UsersBank;
import com.mrbt.lingmoney.model.UsersProperties;
import com.mrbt.lingmoney.model.trading.PaymentClient;
import com.mrbt.lingmoney.service.pay.ClientPurchaseActiveService;

import net.sf.json.JSONObject;

/** 
 * @author  suyibo 
 * @date 创建时间：2018年1月3日
 */
@Service
public class ClientPurchaseActiveServiceImpl implements ClientPurchaseActiveService {
	Logger log = LogManager.getLogger(ClientPurchaseActiveServiceImpl.class);
	
	@Autowired
	private UsersPropertiesMapper usersPropertiesMapper;
	@Autowired
	private ProductMapper productMapper;
	@Autowired
	private UsersBankMapper usersBankMapper;

	@Override
	public String active(String paymentClientText, ModelMap modelMap) {
		if (StringUtils.isBlank(paymentClientText)) {
			log.info("非法参数异常，检查传入值");
			modelMap.addAttribute("status", "1");
			return "clientCallback";
		}
		JSONObject jsonObject = new JSONObject();
		try {
			paymentClientText = URLDecoder.decode(paymentClientText, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			log.info("传入参数解码错误");
			modelMap.addAttribute("status", "1");
			return "clientCallback";
		}
		PaymentClient paymentClient = (PaymentClient) jsonObject.toBean(JSONObject.fromObject(paymentClientText),
				PaymentClient.class);
		if (paymentClient == null) {
			log.info("快捷支付帮助类不存在");
			modelMap.addAttribute("status", "1");
			return "clientCallback";
		}
		if (StringUtils.isBlank(paymentClient.getuId()) || !NumberUtils.isNumber(paymentClient.getuId())
				|| usersPropertiesMapper.selectByuId(paymentClient.getuId()) == null) {
			log.info("用户不存在");
			modelMap.addAttribute("status", "1");
			return "clientCallback";
		}

		Product productClient = productMapper.selectProductByCode(paymentClient.getProductCode());
		if (productClient == null) {
			log.info("产品不存在");
			modelMap.addAttribute("status", "1");
			return "clientCallback";
		}
		modelMap.addAttribute("productName", productClient.getName());

		UsersProperties usersProperties = usersPropertiesMapper.selectByuId(paymentClient.getuId());
		modelMap.addAttribute("usersProperties", usersProperties);
		modelMap.addAttribute("paymentClient", paymentClient);

		// try {
		// paymentClientText =
		// URLEncoder.encode(jsonObject.fromObject(paymentClient).toString(),"UTF-8");
		// } catch (UnsupportedEncodingException e) {
		// throw new RuntimeException("传出参数解码错误");
		// }

		modelMap.addAttribute("paymentClientText", paymentClientText);

		// 查询当前用户所有绑定的银行卡(可用status=1)
		List<UsersBank> listUsersBank = usersBankMapper.selectByUIdAndStatus2(paymentClient.getuId(), "1");

		// 没有可用银行卡，需要绑卡并支付
		if (usersProperties.getCertification() == 0 || usersProperties.getBank() == 0 || listUsersBank == null
				|| listUsersBank.size() <= 0) {
			log.info("没有可用银行卡，需要绑卡并支付");
			modelMap.addAttribute("needBindBankCard", 0);
			return "bindNewBankCard";
		} else {
			log.info("有可用银行卡，选择银行卡并支付");
			modelMap.addAttribute("needBindBankCard", 1);
			modelMap.addAttribute("listUsersBank", listUsersBank);

			// 查询当前用户默认银行卡
			List<UsersBank> listUsersBankDefault = usersBankMapper
					.selectByUIdAndDefault(paymentClient.getuId(), "1", "1");
			// 没有默认银行卡，默认选择第一个
			if (listUsersBankDefault == null || listUsersBankDefault.size() <= 0) {
				log.info("没有默认银行卡，默认选择第一个");
				modelMap.addAttribute("hasDefaultBankCard", 0);
				modelMap.addAttribute("defaultUsersBank", listUsersBank.get(0));
			} else {
				log.info("有默认银行卡");
				modelMap.addAttribute("hasDefaultBankCard", 1);
				modelMap.addAttribute("defaultUsersBank", listUsersBankDefault.get(0));
			}
			return "purchaseActive";
		}
	}

	@Override
	public String selectQuickPayMentFirst(String paymentClientText, String usersBankId, ModelMap modelMap) {

		JSONObject jsonObject = new JSONObject();
		if (StringUtils.isBlank(paymentClientText) || StringUtils.isBlank(usersBankId)
				|| !NumberUtils.isNumber(usersBankId)) {
			log.info("非法参数异常，检查传入值");
			modelMap.addAttribute("status", "1");
			return "clientCallback";
		}
		try {
			paymentClientText = URLDecoder.decode(paymentClientText, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			log.info("传入参数解码错误");
			modelMap.addAttribute("status", "1");
			return "clientCallback";
		}

		PaymentClient paymentClient = (PaymentClient) jsonObject.toBean(JSONObject.fromObject(paymentClientText),
				PaymentClient.class);
		if (paymentClient == null) {
			log.info("快捷支付帮助类不存在");
			modelMap.addAttribute("status", "1");
			return "clientCallback";
		}
		if (StringUtils.isBlank(paymentClient.getuId()) || !NumberUtils.isNumber(paymentClient.getuId())
				|| usersPropertiesMapper.selectByuId(paymentClient.getuId()) == null) {
			log.info("用户不存在");
			modelMap.addAttribute("status", "1");
			return "clientCallback";
		}

		if (usersBankMapper.selectByPrimaryKey(NumberUtils.toInt(usersBankId)) == null) {
			log.info("银行卡不存在");
			modelMap.addAttribute("status", "1");
			return "clientCallback";
		}
		UsersProperties usersProperties = usersPropertiesMapper.selectByuId(paymentClient.getuId());
		if (usersProperties.getCertification() != 1 || usersProperties.getBank() != 1) {
			log.info("用户未绑卡");
			modelMap.addAttribute("status", "1");
			return "clientCallback";
		}
		UsersBank usersBank = usersBankMapper.selectByPrimaryKey(NumberUtils.toInt(usersBankId));
		modelMap.addAttribute("usersBank", usersBank);
		modelMap.addAttribute("usersBankId", usersBankId);
		modelMap.addAttribute("paymentClient", paymentClient);
		// try {
		// paymentClientText =
		// URLEncoder.encode(jsonObject.fromObject(paymentClient).toString(),"UTF-8");
		// } catch (UnsupportedEncodingException e) {
		// throw new RuntimeException("传出参数解码错误");
		// }
		modelMap.addAttribute("paymentClientText", paymentClientText);

		return "messageVerification";
	}

	@Override
	public String selectQuickPayMentSecond(String paymentClientText, String usersBankId, String verifyCode,
			String status, ModelMap modelMap) {
		JSONObject jsonObject = new JSONObject();
		if (StringUtils.isBlank(paymentClientText) || StringUtils.isBlank(usersBankId)
				|| !NumberUtils.isNumber(usersBankId) || StringUtils.isBlank(status) || !NumberUtils.isNumber(status)) {
			log.info("非法参数异常，检查传入值");
			modelMap.addAttribute("status", "1");
			return "clientCallback";
			// throw new IllegalArgumentException("非法参数异常，检查传入值");
		}
		if (NumberUtils.toInt(status) == 1) {
			modelMap.addAttribute("status", "1");
			return "clientCallback";
		}
		try {
			paymentClientText = URLDecoder.decode(paymentClientText, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			log.info("参数解码失败");
			modelMap.addAttribute("status", "1");
			return "clientCallback";
			// throw new RuntimeException("传递参数解码错误" + e);
		}

		PaymentClient paymentClient = (PaymentClient) jsonObject.toBean(JSONObject.fromObject(paymentClientText),
				PaymentClient.class);
		if (paymentClient == null) {
			log.info("快捷支付帮助类不存在");
			modelMap.addAttribute("status", "1");
			return "clientCallback";
			// throw new IllegalArgumentException("快捷支付帮助类不存在");
		}
		if (StringUtils.isBlank(paymentClient.getuId()) || !NumberUtils.isNumber(paymentClient.getuId())
				|| usersPropertiesMapper.selectByuId(paymentClient.getuId()) == null) {
			log.info("用户不存在");
			modelMap.addAttribute("status", "1");
			return "clientCallback";
			// throw new IllegalArgumentException("用户不存在");
		}

		if (usersBankMapper.selectByPrimaryKey(NumberUtils.toInt(usersBankId)) == null) {
			log.info("银行卡不存在");
			modelMap.addAttribute("status", "1");
			return "clientCallback";
			// throw new IllegalArgumentException("银行卡不存在");
		}
		UsersProperties usersProperties = usersPropertiesMapper.selectByuId(paymentClient.getuId());
		if (usersProperties.getCertification() != 1 || usersProperties.getBank() != 1) {
			log.info("用户未绑卡");
			modelMap.addAttribute("status", "1");
			return "clientCallback";
			// throw new IllegalArgumentException("用户未绑卡");
		}
		modelMap.addAttribute("usersBankId", usersBankId);
		modelMap.addAttribute("paymentClient", paymentClient);
		// try {
		// paymentClientText =
		// URLEncoder.encode(jsonObject.fromObject(paymentClient).toString(),"UTF-8");
		// } catch (UnsupportedEncodingException e) {
		// throw new RuntimeException("传出参数解码错误");
		// }
		modelMap.addAttribute("paymentClientText", paymentClientText);
		modelMap.addAttribute("status", "0");
		return "clientCallback";
	}
}
