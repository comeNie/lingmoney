package com.mrbt.lingmoney.service.bank.impl;

import java.util.List;
import java.util.Map;

import org.dom4j.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.mrbt.lingmoney.bank.HxBaseData;
import com.mrbt.lingmoney.bank.transferdebt.TransferDebt;
import com.mrbt.lingmoney.mapper.HxAccountMapper;
import com.mrbt.lingmoney.mapper.ProductMapper;
import com.mrbt.lingmoney.mapper.TradingMapper;
import com.mrbt.lingmoney.model.HxAccount;
import com.mrbt.lingmoney.model.Product;
import com.mrbt.lingmoney.model.ProductExample;
import com.mrbt.lingmoney.model.Trading;
import com.mrbt.lingmoney.model.TradingExample;
import com.mrbt.lingmoney.service.bank.TransferDebtService;
import com.mrbt.lingmoney.utils.Common;
import com.mrbt.lingmoney.utils.PageInfo;
import com.mrbt.lingmoney.utils.PropertiesUtil;
import com.mrbt.lingmoney.utils.ResultParame.ResultInfo;

/**
 * 债权转让申请
 * @author luox
 * @Date 2017年6月8日
 */
@Service
public class TransferDebtServiceImpl implements TransferDebtService {

	@Autowired
	private TransferDebt transferDebt;
	@Autowired
	private HxAccountMapper hxAccountMapper;
	@Autowired
	private TradingMapper tradingMapper;
	@Autowired
	private ProductMapper productMapper;
	
	private final String postUrl = PropertiesUtil.getPropertiesByKey("BANK_POST_URL");
	
	@Override
	public String transferDebtCallBack(Document document) {
		String resXml = null;
		// 数据验签，返回xml文档
        if (document != null) {
			//判断报文status
			Map<String, Object> resMap = HxBaseData.xmlToMap(document);
			
            if ("0".equals(resMap.get("status"))) {
				//生成应答返回报文
				
            } else if ("1".equals(resMap.get("status"))) {
				
            } else if ("2".equals(resMap.get("status"))) {
				
			}
			resXml = transferDebt.generatinAsynsReply(resMap);
		}
		return resXml;
	}

	@Override
    public PageInfo transferDebtAssignment(String uId, int clientType, String appId, String amount, String remark,
            String pCode) throws Exception {
			
		PageInfo pageInfo = new PageInfo();
		
		HxAccount hxAccount = hxAccountMapper.selectByUid(uId);
		
        if (hxAccount == null) {
			pageInfo.setMsg("数据异常");
            pageInfo.setCode(ResultInfo.SERVER_ERROR.getCode());
			return pageInfo;
		}
		
		TradingExample example = new TradingExample();
		example.createCriteria().andUIdEqualTo(uId).andPCodeEqualTo(pCode);
		List<Trading> list = tradingMapper.selectByExample(example);
		// 原交易流水号
		String oldreqseqno = null;
		// 原标的编号
		String oldreqnumber = null;
        if (Common.isNotBlank(list)) {
			Trading trading = list.get(0);
			oldreqseqno = trading.getBizCode();
			oldreqnumber = trading.getpCode();
		}
		
		// 原标的名称
		String oldreqname = null;
        if (oldreqnumber != null) {
			ProductExample productExample = new ProductExample();
			productExample.createCriteria().andCodeEqualTo(oldreqnumber);
			List<Product> pros = productMapper.selectByExample(productExample);
            if (Common.isNotBlank(pros)) {
				Product product = pros.get(0);
				oldreqname = product.getPcName();
			}
		}
		
		// 预计剩余收益
		String preincome = String.valueOf(Integer.parseInt(amount) / 2);
		
		String returnUrl = "https://222.249.236.5:8443/bank/transferDebtCallBack" + "/" + uId;
		Map<String, String> map = transferDebt.transferDebtAssignment(clientType, appId, "2", oldreqseqno, 
				oldreqnumber, oldreqname, hxAccount.getAcNo(), hxAccount.getAcName(), amount, preincome, remark, returnUrl);
		
		map.put("bankUrl", postUrl);
        pageInfo.setCode(ResultInfo.SUCCESS.getCode());
		pageInfo.setObj(map);
		return pageInfo;
	}

	@Override
	public PageInfo transferDebtAssignmentSearch(String appId, String uId) throws Exception {
		PageInfo pageInfo = new PageInfo();
		JSONObject json = new JSONObject();
		pageInfo.setObj(json);
		
		String oldreqseqno = "P2P20220170718061Q5XSAZ40906";
		
		Document result = transferDebt.transferDebtAssignmentSearch(appId, oldreqseqno);
		
        if (result == null) {
			pageInfo.setMsg("查询失败");
            pageInfo.setCode(ResultInfo.RETURN_EMPTY_DATA.getCode());
			return pageInfo;
		}
		
		json.put("oldreqseqno", result.selectSingleNode("//OLDREQSEQNO").getText());
		json.put("resjnlno", result.selectSingleNode("//RESJNLNO").getText());
		json.put("transdt", result.selectSingleNode("//TRANSDT").getText());
		json.put("transtm", result.selectSingleNode("//TRANSTM").getText());
		json.put("returnStatus", result.selectSingleNode("//RETURN_STATUS").getText());
		
        pageInfo.setCode(ResultInfo.SUCCESS.getCode());
		return pageInfo;
		
	}
}
