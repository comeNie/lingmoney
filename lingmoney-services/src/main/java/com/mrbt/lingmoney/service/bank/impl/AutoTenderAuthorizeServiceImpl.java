package com.mrbt.lingmoney.service.bank.impl;

import java.util.Map;

import org.dom4j.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mrbt.lingmoney.bank.HxBaseData;
import com.mrbt.lingmoney.bank.tender.HxTender;
import com.mrbt.lingmoney.mapper.HxAccountMapper;
import com.mrbt.lingmoney.model.HxAccount;
import com.mrbt.lingmoney.service.bank.AutoTenderAuthorizeService;
import com.mrbt.lingmoney.utils.PageInfo;
import com.mrbt.lingmoney.utils.PropertiesUtil;
import com.mrbt.lingmoney.utils.ResultParame.ResultInfo;

/**
 * 自动投标授权
 * @author luox
 * @Date 2017年6月6日
 */
@Service
public class AutoTenderAuthorizeServiceImpl implements AutoTenderAuthorizeService {

    @Autowired
    private HxTender hxTender;

    @Autowired
    private HxAccountMapper hxAccountMapper;

    private final String postUrl = PropertiesUtil.getPropertiesByKey("BANK_POST_URL");

    private static final String AUTO_TENDERAUTHORIZE_RETURN_URL = PropertiesUtil
            .getPropertiesByKey("AUTO_TENDERAUTHORIZE_RETURN_URL");

    @Override
    public String autoTenderAuthorizeCallBack(Document document) throws Exception {

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
            resXml = hxTender.generatinAsynsReply(resMap);
        }
        return resXml;
    }

    @Override
    public PageInfo autoTenderAuthorize(String uId, Integer clientType, String appId, String remark) throws Exception {
        PageInfo pageInfo = new PageInfo();
        HxAccount hxAccount = hxAccountMapper.selectByUid(uId);

        if (hxAccount == null) {
            pageInfo.setMsg("数据异常");
            pageInfo.setCode(ResultInfo.SERVER_ERROR.getCode());
            return pageInfo;
        }

        String returnUrl = AUTO_TENDERAUTHORIZE_RETURN_URL + uId;
        Map<String, String> map = hxTender.autoTenderAuthorize(clientType, appId, "9", hxAccount.getAcNo(),
                hxAccount.getAcName(), remark, returnUrl);
        map.put("bankUrl", postUrl);
        pageInfo.setCode(ResultInfo.SUCCESS.getCode());
        pageInfo.setObj(map);
        return pageInfo;
    }

}
