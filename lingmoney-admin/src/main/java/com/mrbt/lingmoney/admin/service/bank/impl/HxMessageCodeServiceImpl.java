package com.mrbt.lingmoney.admin.service.bank.impl;

import java.util.Map;

import org.dom4j.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.mrbt.lingmoney.admin.service.bank.HxMessageCodeService;
import com.mrbt.lingmoney.bank.HxBaseData;
import com.mrbt.lingmoney.bank.messagecode.HxMessageCode;
import com.mrbt.lingmoney.mapper.HxAccountMapper;
import com.mrbt.lingmoney.model.HxAccount;
import com.mrbt.lingmoney.utils.PageInfo;
import com.mrbt.lingmoney.utils.ResultParame.ResultInfo;

/**
 *@author syb
 *@date 2017年7月13日 下午4:05:17
 *@version 1.0
 *@description 
 **/
@Service
public class HxMessageCodeServiceImpl implements HxMessageCodeService {

	@Autowired
	private HxAccountMapper hxAccountMapper;
	@Autowired
	private HxMessageCode hxMessageCode;

	@Override
	public PageInfo getMessageCode(String type, String accountId) {
		PageInfo pi = new PageInfo();

		if (!StringUtils.isEmpty(accountId)) {

			if (StringUtils.isEmpty(type)) {
				type = "0";
			}

			HxAccount hxAccount = hxAccountMapper.selectByPrimaryKey(accountId);

			if (hxAccount != null) {
				try {
					Document doc = hxMessageCode.getHxMessageCode("PC", hxAccount.getAcName(), type, hxAccount.getAcNo(), hxAccount.getMobile());

					if (doc != null) {
						Map<String, Object> map = HxBaseData.xmlToMap(doc);
						// 短信验证码唯一标识
						String otpSeqNo = (String) map.get("OTPSEQNO");
						pi.setObj(otpSeqNo);
						pi.setCode(ResultInfo.SUCCESS.getCode());
						pi.setMsg(ResultInfo.SUCCESS.getMsg());
					} else {
						pi.setCode(ResultInfo.EMPTY_DATA.getCode());
						pi.setMsg(ResultInfo.EMPTY_DATA.getMsg());
					}

				} catch (Exception e) {
					pi.setCode(ResultInfo.SERVER_ERROR.getCode());
					pi.setMsg(ResultInfo.SERVER_ERROR.getMsg());
					e.printStackTrace();
				}
			} else {
				pi.setCode(ResultInfo.EMPTY_DATA.getCode());
				pi.setMsg(ResultInfo.EMPTY_DATA.getMsg());
			}

		} else {
			pi.setCode(ResultInfo.PARAM_MISS.getCode());
			pi.setMsg(ResultInfo.PARAM_MISS.getMsg());
		}
		return pi;
	}

}
