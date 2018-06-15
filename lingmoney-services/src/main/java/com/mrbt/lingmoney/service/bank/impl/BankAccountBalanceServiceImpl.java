package com.mrbt.lingmoney.service.bank.impl;

import java.util.List;

import org.dom4j.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.mrbt.lingmoney.bank.eaccount.HxAccountBanlance;
import com.mrbt.lingmoney.mapper.HxAccountMapper;
import com.mrbt.lingmoney.mapper.UsersPropertiesMapper;
import com.mrbt.lingmoney.model.HxAccount;
import com.mrbt.lingmoney.model.HxAccountExample;
import com.mrbt.lingmoney.model.UsersProperties;
import com.mrbt.lingmoney.service.bank.BankAccountBalanceService;
import com.mrbt.lingmoney.utils.PageInfo;
import com.mrbt.lingmoney.utils.ResultParame.ResultInfo;
import com.mrbt.lingmoney.utils.ResultParame.ResultNumber;
import com.mrbt.lingmoney.utils.StringOpertion;

/**
 * @author luox
 * @Date 2017年6月5日
 */
@Service
public class BankAccountBalanceServiceImpl implements BankAccountBalanceService {

    @Autowired
    private HxAccountMapper hxAccountMapper;
    @Autowired
    private HxAccountBanlance hxAccountBanlance;
    @Autowired
    private UsersPropertiesMapper usersPropertiesMapper;

    @Override
    public PageInfo userHxAccout(String uId) throws Exception {
        PageInfo pageInfo = new PageInfo();

        // 是否开通华兴E账户
        UsersProperties usersProperties = usersPropertiesMapper.selectByuId(uId);

        if (usersProperties == null) {
            pageInfo.setMsg("尚未开通华兴E账户");
            pageInfo.setCode(ResultInfo.NOT_HX_ACCOUNT.getCode());
            return pageInfo;
		}

        // 已开通
        HxAccount hxAccount = null;
		if (usersProperties.getCertification() == 2 || usersProperties.getCertification() == 3) {
    		HxAccountExample example = new HxAccountExample();
    		example.createCriteria().andUIdEqualTo(uId).andStatusEqualTo(ResultNumber.ONE.getNumber());
    		List<HxAccount> exampleList = hxAccountMapper.selectByExample(example);
    		if (!exampleList.isEmpty()) {
				hxAccount = exampleList.get(0);
    		}
        }

        JSONObject json = new JSONObject();
        if (hxAccount != null) {
        	json.put("acName", StringOpertion.hideName(hxAccount.getAcName())); // 账户姓名
        	json.put("acNo", hxAccount.getAcNo()); // E账户
		}
		json.put("bank", usersProperties.getBank());
		json.put("certification", usersProperties.getCertification());

        pageInfo.setObj(json);
        pageInfo.setCode(ResultInfo.SUCCESS.getCode());

        return pageInfo;
    }

    @Override
    public PageInfo findUserBalance(String appId, String uId) throws Exception {
        PageInfo pageInfo = new PageInfo();

        HxAccount hxAccount = hxAccountMapper.selectByUid(uId);

        if (hxAccount == null) {
            pageInfo.setMsg("数据异常");
            pageInfo.setCode(ResultInfo.SERVER_ERROR.getCode());
            return pageInfo;
        }

        Document result = hxAccountBanlance.requestAccountBalance(appId, hxAccount.getAcName(), hxAccount.getAcNo());

        if (result == null) {
            pageInfo.setMsg("查询失败");
            pageInfo.setCode(ResultInfo.RETURN_EMPTY_DATA.getCode());
            return pageInfo;
        }

        System.out.println("查询账户余额返回信息：\n" + result.asXML());

        String errorCode = result.selectSingleNode("//errorCode").getText();

        if ("0".equals(errorCode)) {
            JSONObject json = new JSONObject();
            json.put("availableBalance", result.selectSingleNode("//AVAILABLEBAL").getText());
            pageInfo.setCode(ResultInfo.SUCCESS.getCode());
            pageInfo.setObj(json);
        } else {
            String errorMsg = result.selectSingleNode("//errorMsg").getText();
            pageInfo.setCode(ResultInfo.MODIFY_REJECT.getCode());
            pageInfo.setMsg(errorMsg);
        }

        return pageInfo;
    }

    @Override
    public PageInfo hxAccoutBalanceVerify(String appId, String uId, String amount) throws Exception {
        PageInfo pageInfo = new PageInfo();

        HxAccount hxAccount = hxAccountMapper.selectByUid(uId);

        if (hxAccount == null) {
            pageInfo.setMsg("数据异常");
            pageInfo.setCode(ResultInfo.SERVER_ERROR.getCode());
            return pageInfo;
        }

        Document result = hxAccountBanlance.checkAccountBalance(appId, amount, hxAccount.getAcNo());

        if (result == null) {
            pageInfo.setMsg("查询失败");
            pageInfo.setCode(ResultInfo.RETURN_EMPTY_DATA.getCode());
            return pageInfo;
        }

        String errorCode = result.selectSingleNode("//errorCode").getText();

        if ("0".equals(errorCode)) {
            JSONObject json = new JSONObject();
            json.put("resflag", result.selectSingleNode("//RESFLAG").getText());
            pageInfo.setCode(ResultInfo.SUCCESS.getCode());
            pageInfo.setObj(json);
        } else {
            String errorMsg = result.selectSingleNode("//errorMsg").getText();
            pageInfo.setCode(ResultInfo.MODIFY_REJECT.getCode());
            pageInfo.setMsg(errorMsg);
        }

        return pageInfo;

    }

}
