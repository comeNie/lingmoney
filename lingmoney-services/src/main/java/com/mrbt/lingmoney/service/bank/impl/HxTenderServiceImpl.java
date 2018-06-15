package com.mrbt.lingmoney.service.bank.impl;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.dom4j.Document;
import org.dom4j.Element;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.mrbt.lingmoney.bank.tender.HxTender;
import com.mrbt.lingmoney.mapper.HxAccountMapper;
import com.mrbt.lingmoney.model.HxAccount;
import com.mrbt.lingmoney.service.bank.HxTenderService;
import com.mrbt.lingmoney.service.users.VerifyService;
import com.mrbt.lingmoney.utils.Common;
import com.mrbt.lingmoney.utils.PageInfo;
import com.mrbt.lingmoney.utils.ResultParame.ResultInfo;

/**
 * @author luox
 * @Date 2017年7月17日
 */
@Service
public class HxTenderServiceImpl implements HxTenderService {

	@Autowired
	private HxTender hxTender;
	@Autowired
	private HxAccountMapper hxAccountMapper;
	@Autowired
	private VerifyService verifyService;
	
	@Override
	public PageInfo tenderReturn(String appId, String uId, String loanno, String bwacname, String bwacno,
			String amount, String totalnum) throws Exception {
		PageInfo pageInfo = new PageInfo();
		JSONObject json = new JSONObject();
		pageInfo.setObj(json);
        List<Map<String, String>> feedbacklist = new ArrayList<>();
		
		Map<String, String> map3 = new HashMap<>();
		String uuid = Common.uuid();
		System.out.println(uuid);
        map3.put("SUBSEQNO", uuid); // 子流水号
        map3.put("OLDREQSEQNO", "P2P20220170719052W671W0YPWIO"); // 原投标流水号
        map3.put("ACNO", "8970660100000029558"); // 投资人账号
        map3.put("ACNAME", "阮正豪 "); // 投资人账号户名
        map3.put("AMOUNT", "0.1"); // 优惠金额
        map3.put("REMARK", "remark3"); // 备注
		
		
        feedbacklist.add(map3);
        Document result = hxTender.tenderReturn(appId, loanno, bwacname, bwacno, amount, totalnum, feedbacklist);
		
        if (result == null || !"0".equals(result.selectSingleNode("//errorCode").getText())) {
			pageInfo.setMsg("查询失败");
            pageInfo.setCode(ResultInfo.RETURN_EMPTY_DATA.getCode());
			return pageInfo;
		}
		
		json.put("resjnlno", result.selectSingleNode("//RESJNLNO").getText());
		json.put("transdt", result.selectSingleNode("//TRANSDT").getText());
		json.put("transtm", result.selectSingleNode("//TRANSTM").getText());
		
        pageInfo.setCode(ResultInfo.SUCCESS.getCode());
		return pageInfo;
	}

	@Override
	public PageInfo tenderReturnSearch(String appId, String uId, String oldreqseqno, String subseqno)
			throws Exception {
		PageInfo pageInfo = new PageInfo();
		JSONObject json = new JSONObject();
		pageInfo.setObj(json);
		Document result = hxTender.tenderReturnSearch(appId, oldreqseqno, subseqno);
		
        if (result == null) {
			pageInfo.setMsg("查询失败");
            pageInfo.setCode(ResultInfo.RETURN_EMPTY_DATA.getCode());
			return pageInfo;
		}
		
		json.put("resjnlno", result.selectSingleNode("//RESJNLNO").getText());
		json.put("transdt", result.selectSingleNode("//TRANSDT").getText());
		json.put("transtm", result.selectSingleNode("//TRANSTM").getText());
		json.put("loanno", result.selectSingleNode("//LOANNO").getText());
		json.put("bwacname", result.selectSingleNode("//BWACNAME").getText());
		json.put("bwacno", result.selectSingleNode("//BWACNO").getText());
		json.put("amount", result.selectSingleNode("//AMOUNT").getText());
		json.put("totalnum", result.selectSingleNode("//TOTALNUM").getText());
		json.put("returnStatus", result.selectSingleNode("//RETURN_STATUS").getText());
		json.put("errormsg", result.selectSingleNode("//ERRORMSG").getText());
		
		// TODO RSLIST
		List<?> nodes = result.selectNodes("//RSLIST");
		JSONArray array = new JSONArray();
		Map<String, String> map = null;
		for (Object object : nodes) {
			Element e = (Element) object;
			Iterator<?> iterator = e.nodeIterator();
			map = new HashMap<>();
			while (iterator.hasNext()) {
				Element next = (Element) iterator.next();
				map.put(next.getName(), next.getText());
			}
			array.add(map);
		}
		
		json.put("rslist", array);
		
        pageInfo.setCode(ResultInfo.SUCCESS.getCode());
		return pageInfo;
	}

	@Override
    public PageInfo tenderReturnSearch(String appId, String uId) throws Exception {
		PageInfo pageInfo = new PageInfo();
		JSONObject json = new JSONObject();
		pageInfo.setObj(json);
		
		String oldreqseqno = "P2P20220170718056KUNNY0SJDK7";
		
		Document result = hxTender.autoTenderAuthorizeSearch(appId, oldreqseqno);
		
        if (result == null) {
			pageInfo.setMsg("查询失败");
            pageInfo.setCode(ResultInfo.RETURN_EMPTY_DATA.getCode());
			return pageInfo;
		}
		json.put("oldreqseqno", result.selectSingleNode("//OLDREQSEQNO").getText());
		json.put("resjnlno", result.selectSingleNode("//RESJNLNO").getText());
		json.put("transdt", result.selectSingleNode("//TRANSDT").getText());
		json.put("returnStatus", result.selectSingleNode("//RETURN_STATUS").getText());
		json.put("errormsg", result.selectSingleNode("//ERRORMSG").getText());
		
		return pageInfo;
	}

	@Override
	public PageInfo autoTenderAuthorizeCancel(String uId, String appId, String otpseqno, String otpno, String remark) throws Exception {
		PageInfo pageInfo = new PageInfo();
		JSONObject json = new JSONObject();
		pageInfo.setObj(json);
		
		HxAccount hxAccount = hxAccountMapper.selectByUid(uId);
		
        if (hxAccount == null) {
			pageInfo.setMsg("数据异常");
            pageInfo.setCode(ResultInfo.SERVER_ERROR.getCode());
			return pageInfo;
		}
		
		Document result = hxTender.autoTenderAuthorizeCancel(appId, otpseqno, otpno, hxAccount.getAcNo(), hxAccount.getAcName(), remark);
		
        if (result == null) {
			pageInfo.setMsg("查询失败");
            pageInfo.setCode(ResultInfo.RETURN_EMPTY_DATA.getCode());
			return pageInfo;
		}
		json.put("resjnlno", result.selectSingleNode("//RESJNLNO").getText());
		json.put("transdt", result.selectSingleNode("//TRANSDT").getText());
		json.put("transtm", result.selectSingleNode("//TRANSTM").getText());
		
		return pageInfo;
	}

	@Override
	public PageInfo autoTenderOne(String uId, String appId, String loanno, String amount, String remark)
			throws Exception {
		PageInfo pageInfo = new PageInfo();
		JSONObject json = new JSONObject();
		pageInfo.setObj(json);
		
		HxAccount hxAccount = hxAccountMapper.selectByUid(uId);
		
        if (hxAccount == null) {
			pageInfo.setMsg("数据异常");
            pageInfo.setCode(ResultInfo.SERVER_ERROR.getCode());
			return pageInfo;
		}
		
		verifyService.verifyMoney(amount, "金额格式不正确");

		DecimalFormat df = new DecimalFormat("#0.00");
		amount = df.format(Double.parseDouble(amount));

		Document result = hxTender.autoTenderOne(appId, loanno, hxAccount.getAcNo(), hxAccount.getAcName(), amount, remark);
		
        if (result == null) {
			pageInfo.setMsg("查询失败");
            pageInfo.setCode(ResultInfo.RETURN_EMPTY_DATA.getCode());
			return pageInfo;
		}
		json.put("resjnlno", result.selectSingleNode("//RESJNLNO").getText());
		json.put("transdt", result.selectSingleNode("//TRANSDT").getText());
		json.put("transtm", result.selectSingleNode("//TRANSTM").getText());
		
		return pageInfo;
	}

}
