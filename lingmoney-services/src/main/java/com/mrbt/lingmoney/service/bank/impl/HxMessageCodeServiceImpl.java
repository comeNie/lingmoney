package com.mrbt.lingmoney.service.bank.impl;

import org.dom4j.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.mrbt.lingmoney.bank.messagecode.HxMessageCode;
import com.mrbt.lingmoney.mapper.HxAccountMapper;
import com.mrbt.lingmoney.model.HxAccount;
import com.mrbt.lingmoney.service.bank.HxMessageCodeService;
import com.mrbt.lingmoney.utils.PageInfo;
import com.mrbt.lingmoney.utils.ResultParame.ResultInfo;

/**
 * @author luox
 * @Date 2017年7月17日
 */
@Service
public class HxMessageCodeServiceImpl implements HxMessageCodeService {

	@Autowired
	private HxAccountMapper hxAccountMapper;
	@Autowired
	private HxMessageCode hxMessageCode;
	
	@Override
	public PageInfo getHxMessageCode(String appId, String uId) throws Exception {
		PageInfo pageInfo = new PageInfo();
		JSONObject json = new JSONObject();
		pageInfo.setObj(json);
		
		HxAccount hxAccount = hxAccountMapper.selectByUid(uId);
		
        if (hxAccount == null) {
			pageInfo.setMsg("数据异常");
            pageInfo.setCode(ResultInfo.SERVER_ERROR.getCode());
			return pageInfo;
		}
		
		Document result = hxMessageCode.getHxMessageCode(appId, hxAccount.getAcName(), "1", hxAccount.getAcNo(),
				hxAccount.getMobile());
		
        if (result == null) {
			pageInfo.setMsg("查询失败");
            pageInfo.setCode(ResultInfo.RETURN_EMPTY_DATA.getCode());
			return pageInfo;
		}
		
		json.put("otpseqno", result.selectSingleNode("//OTPSEQNO").getText());
		json.put("otpindex", result.selectSingleNode("//OTPINDEX").getText());
		
        pageInfo.setCode(ResultInfo.SUCCESS.getCode());
		return pageInfo;
		
	}

}
