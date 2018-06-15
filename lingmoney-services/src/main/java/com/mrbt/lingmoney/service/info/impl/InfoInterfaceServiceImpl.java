package com.mrbt.lingmoney.service.info.impl;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.mrbt.lingmoney.commons.cache.RedisDao;
import com.mrbt.lingmoney.mapper.UsersMapper;
import com.mrbt.lingmoney.model.CodeObject;
import com.mrbt.lingmoney.model.UsersExample;
import com.mrbt.lingmoney.model.trading.SmsMessage;
import com.mrbt.lingmoney.service.common.SmsService;
import com.mrbt.lingmoney.service.info.InfoInterfaceService;
import com.mrbt.lingmoney.utils.AppCons;
import com.mrbt.lingmoney.utils.CodeUtils;
import com.mrbt.lingmoney.utils.DateUtils;
import com.mrbt.lingmoney.utils.ResultParame.ResultInfo;

/**
  *
  *@author yiban
  *@date 2018年1月12日 下午10:12:16
  *@version 1.0
 **/
@Service
public class InfoInterfaceServiceImpl implements InfoInterfaceService {

    @Autowired
    private UsersMapper usersMapper;
    @Autowired
    private RedisDao redisDao;
    @Autowired
    private SmsService smsService;

    @Override
    public JSONObject getValidCode(String telephone) {
        JSONObject jsonObject = new JSONObject();

        if (StringUtils.isEmpty(telephone)) {
            jsonObject.put("code", ResultInfo.NO_SUCCESS.getCode());
            jsonObject.put("msg", "手机号为空");
            return jsonObject;
        }

        UsersExample usersExmp = new UsersExample();
        usersExmp.createCriteria().andTelephoneEqualTo(telephone);
        if (usersMapper.countByExample(usersExmp) > 0) {
            jsonObject.put("code", ResultInfo.NO_SUCCESS.getCode());
            jsonObject.put("msg", "该手机号已注册");
            return jsonObject;
        }

        String code = CodeUtils.getRandomCode();
        String content = AppCons.regist_content_y;
        content = MessageFormat.format(content, code, "2");

        String dateStr = DateUtils.getDateStr(new Date(), DateUtils.sf);
        String key = dateStr + "_" + telephone;

        // 发送成功
        if (!isFULLCodeList(key)) {
            // 发送短信修改为放入redis统一发送
            SmsMessage message = new SmsMessage();
            message.setTelephone(telephone);
            message.setContent(content.toString());
            smsService.saveSmsMessage(message);

            System.out.println("短信内容为：" + content.toString());
            CodeObject codeObject = new CodeObject();
            codeObject.setCode(code);
            codeObject.setCreateDdate(new Date());
            putCodeListTORedis(key, codeObject);

            jsonObject.put("code", ResultInfo.SUCCESS.getCode());
            jsonObject.put("msg", "发送成功");
        } else {
            jsonObject.put("code", ResultInfo.NO_SUCCESS.getCode());
            jsonObject.put("msg", "发送失败,今日验证次数已用完");
        }

        return jsonObject;
    }

    public boolean isFULLCodeList(String key) {
        long size = redisDao.getListSize(key);

        if (size >= 4) {
            return true;

        } else {
            return false;
        }
    }

    public void putCodeListTORedis(String key, CodeObject codeObject) {
        List result = redisDao.getList(key);
        if (result != null) {
            result.add(codeObject);
            redisDao.delete(key);
            redisDao.setList(key, result);
        } else {
            result = new ArrayList();
            result.add(codeObject);
            redisDao.setList(key, result);
        }
        redisDao.expire(key, 1, TimeUnit.DAYS);
    }

}
