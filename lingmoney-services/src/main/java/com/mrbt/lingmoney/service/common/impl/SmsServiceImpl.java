package com.mrbt.lingmoney.service.common.impl;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mrbt.lingmoney.commons.cache.RedisDao;
import com.mrbt.lingmoney.commons.exception.PageInfoException;
import com.mrbt.lingmoney.mapper.UsersMapper;
import com.mrbt.lingmoney.model.CodeObject;
import com.mrbt.lingmoney.model.Users;
import com.mrbt.lingmoney.model.UsersExample;
import com.mrbt.lingmoney.model.trading.SmsMessage;
import com.mrbt.lingmoney.service.common.SmsService;
import com.mrbt.lingmoney.service.common.UtilService;
import com.mrbt.lingmoney.service.users.VerifyService;
import com.mrbt.lingmoney.utils.AppCons;
import com.mrbt.lingmoney.utils.CodeUtils;
import com.mrbt.lingmoney.utils.DateUtils;
import com.mrbt.lingmoney.utils.PageInfo;
import com.mrbt.lingmoney.utils.ResultParame.ResultInfo;
import com.mrbt.lingmoney.utils.Validation;

/**
 * 短信验证码实现类
 * 
 * @author Administrator
 *
 */
@Service
public class SmsServiceImpl implements SmsService {

    public static final String SMSMESSAGE_KEY_HEAD = "smsmessage:data:";

	private static final Logger LOGGER = LogManager.getLogger(SmsServiceImpl.class);
	
	static final int sendNumber = 6; // 验证次数为6

	@Autowired
	private RedisDao redisDao;

	@Autowired
	private UsersMapper usersMapper;
	@Autowired
	private VerifyService verifyService;
	@Autowired
	private UtilService utilService;

	/**
	 * 验证短信验证码
	 * 
	 * @param key
	 * @param code
	 * @return
	 */
	@Override
	public boolean validateCode(String key, String code) {
        boolean rs = true;

		try {
			List<Object> result = redisDao.getList(key);
			if (result == null || result.size() < 1) {
                rs = false;
                return rs;
			}

			CodeObject codeObject = (CodeObject) result.get(result.size() - 1);
			String valiCode = codeObject.getCode();

			LOGGER.info("redis中保存的验证码为:" + valiCode + "\t发送手机的验证码:" + code);

			Date date = codeObject.getCreateDdate();

			long min = DateUtils.dateDiffMins(date, new Date());

            final int overTime = 2; //超时时间为两分钟
            if (min > overTime) {
                rs = false;
            } else {
                rs = valiCode.equals(code);
			}

		} catch (Exception e) {
            rs = false;
		}

        return rs;
	}

	/**
	 * 记录验证码
	 * 
	 * @param key key
	 * @param codeObject code
	 */
	public void putCodeListTORedis(String key, CodeObject codeObject) {
		List<Object> result = redisDao.getList(key);
		if (result != null) {
			result.add(codeObject);
			redisDao.delete(key);
			redisDao.setList(key, result);
		} else {
			result = new ArrayList<Object>();
			result.add(codeObject);
			redisDao.setList(key, result);
		}
		redisDao.expire(key, DateUtils.secsFromTomorrow(), TimeUnit.SECONDS);
	}

	/**
	 * 发送信息存入redis
	 * 
	 * @param message message
	 * @return 成功true 失败false
	 */
	@Override
	public boolean saveSmsMessage(SmsMessage message) {
		boolean result = true;
		String key = SMSMESSAGE_KEY_HEAD;
		try {
			redisDao.setList(key, message);
		} catch (Exception e) {
			result = false;
		}
		return result;
	}

	/**
	 * 验证每天发短信次数
	 * 
	 * @param key key
	 * @return 通过 true 不通过false
	 */
	public boolean sendNumber(String key) {
		long size = redisDao.getListSize(key);
        return size >= sendNumber;
	}

	@Override
	public PageInfo saveSendRegister(String phone, PageInfo pageInfo, String picKey, String picCode) {
		PageInfo validInfo = commonVerifyTellephoneMethod(phone, picKey, picCode);
		if (validInfo != null) {
			return validInfo;
		}

		String key = getVerifyTimesOfSmsKey(phone);

		// 验证发短信次数
		if (sendNumber(key)) {
            pageInfo.setCode(ResultInfo.VERIFICATION_COUNT_ERROR.getCode());
			pageInfo.setMsg("请求次数超出限制，请联系客服！");
			return pageInfo;
		}

		// 验证手机号是否已经注册
		UsersExample example = new UsersExample();
		example.createCriteria().andTelephoneEqualTo(phone);
		if (usersMapper.countByExample(example) > 0) {
            pageInfo.setCode(ResultInfo.ACCOUNT_EXIST.getCode());
			pageInfo.setMsg("账号已经存在");
			return pageInfo;
		}

		try {
			String code = CodeUtils.getRandomCode();
			String content = AppCons.regist_content_y;
			content = MessageFormat.format(content, code, "2");

			// 存入消息队列
			saveSMSInfoToRedisList(phone, key, code, content);

            pageInfo.setCode(ResultInfo.SUCCESS.getCode());
			pageInfo.setMsg("发送成功");

		} catch (Exception e) {
			e.printStackTrace();
            pageInfo.setCode(ResultInfo.SERVER_ERROR.getCode());
			pageInfo.setMsg("服务器错误");
		}

		return pageInfo;
	}

	@Override
	public PageInfo verifyRegisterCode(String phone, String verifyCode, PageInfo pageInfo) {
		if (StringUtils.isBlank(verifyCode)) {
            pageInfo.setCode(ResultInfo.PARAM_MISS.getCode());
			pageInfo.setMsg("验证码不能为空");
			return pageInfo;
		}

		String key = getVerifyTimesOfSmsKey(phone);

		if (validateCode(key, verifyCode)) {
            pageInfo.setCode(ResultInfo.SUCCESS.getCode());
			pageInfo.setMsg("验证成功");
			
		} else {
            pageInfo.setCode(ResultInfo.VERIFICATION_CODE_ERROR.getCode());
			pageInfo.setMsg("验证码错误");
		}

		return pageInfo;
	}

	@Override
	public PageInfo saveSendModiPw(String phone, PageInfo pageInfo, String picKey, String picCode) {
		
		PageInfo validInfo = commonVerifyTellephoneMethod(phone, picKey, picCode);
		if (validInfo != null) {
			return validInfo;
		}

		String key = getVerifyTimesOfSmsKey(phone);

		// 验证发短信次数
		if (sendNumber(key)) {
            pageInfo.setCode(ResultInfo.VERIFICATION_COUNT_ERROR.getCode());
			pageInfo.setMsg("请求次数超出限制，请联系客服！");
			return pageInfo;
		}

		// 验证手机号是否已经注册
		UsersExample example = new UsersExample();
		example.createCriteria().andTelephoneEqualTo(phone);
		if (usersMapper.countByExample(example) <= 0) {
            pageInfo.setCode(ResultInfo.ACCOUNT_NUMBER_NON_EXIST.getCode());
			pageInfo.setMsg("用户账号不存在");
			return pageInfo;
		}

		try {
			String code = CodeUtils.getRandomCode();
			String content = AppCons.password_content_y;
			content = MessageFormat.format(content, code, "2");

			saveSMSInfoToRedisList(phone, key, code, content);

            pageInfo.setCode(ResultInfo.SUCCESS.getCode());
			pageInfo.setMsg("发送成功");
		} catch (Exception e) {
			e.printStackTrace();
            pageInfo.setCode(ResultInfo.SERVER_ERROR.getCode());
			pageInfo.setMsg("服务器错误");
		}

		return pageInfo;
	}

	@Override
	public PageInfo sendModiPhoneVerifyOld(String oldKey, String phone, PageInfo pageInfo, String picKey, String picCode) throws Exception {
		if (!PictureVerifyServiceImpl.varifyCode(picKey, picCode, redisDao, 1)) {
            pageInfo.setCode(ResultInfo.VERIFICATION_CODE_ERROR.getCode());
			pageInfo.setMsg("图形验证码错误或失效");
			pageInfo.setObj(false);
			return pageInfo;
		}
		
		String id = "";
		try {
			id = utilService.queryUid(oldKey);
			verifyService.verifyTelephoneAndUid(phone, id);
		} catch (PageInfoException pe) {
			pageInfo.setCode(pe.getCode());
			pageInfo.setMsg(pe.getMessage());
			return pageInfo;
		}

		// redis key
		String key = getVerifyTimesOfSmsKey(phone);

		// 验证发短信次数
		if (sendNumber(key)) {
            pageInfo.setCode(ResultInfo.VERIFICATION_COUNT_ERROR.getCode());
			pageInfo.setMsg("请求次数超出限制，请联系客服！");
			return pageInfo;
		}

		Users user = usersMapper.selectByPrimaryKey(id);

		String code = CodeUtils.getRandomCode();
		String content = AppCons.CHANGE_PHONE_CONTENT;
		content = MessageFormat.format(content, user.getLoginAccount(), code, "2");

		saveSMSInfoToRedisList(phone, key, code, content);

        pageInfo.setCode(ResultInfo.SUCCESS.getCode());
		pageInfo.setMsg("发送成功");

		return pageInfo;
	}

	@Override
	public PageInfo sendModiPhone(String targetPhone, String key, PageInfo pageInfo, String picKey, String picCode) throws Exception {
		if (!PictureVerifyServiceImpl.varifyCode(picKey, picCode, redisDao, 1)) {
            pageInfo.setCode(ResultInfo.VERIFICATION_CODE_ERROR.getCode());
			pageInfo.setMsg("图形验证码错误或失效");
			pageInfo.setObj(false);
			return pageInfo;
		}
		String id = "";
		try {
			verifyService.verifyTelephone(targetPhone, 0);
			id = utilService.queryUid(key);
		} catch (PageInfoException pe) {
			pageInfo.setCode(pe.getCode());
			pageInfo.setMsg(pe.getMessage());
			return pageInfo;
		}

		Users users = usersMapper.selectByPrimaryKey(id);

		// 给目标手机发送验证码
		String code = CodeUtils.getRandomCode();
		String content = AppCons.CHANGE_PHONE_CONTENT;
		content = MessageFormat.format(content, users.getLoginAccount(), code, "2");

		String newKey = getVerifyTimesOfSmsKey(targetPhone);

		LOGGER.info("修改手机号" + users.getTelephone() + "到" + targetPhone + "的验证码为：" + code + " \n 保存的redisKey为：" + newKey);

		// 验证发送次数
		if (sendNumber(newKey)) {
            pageInfo.setCode(ResultInfo.VERIFICATION_COUNT_ERROR.getCode());
			pageInfo.setMsg("请求次数超出限制，请联系客服！");
			return pageInfo;
		}

		saveSMSInfoToRedisList(targetPhone, newKey, code, content);

        pageInfo.setCode(ResultInfo.SUCCESS.getCode());
		pageInfo.setMsg("发送成功");

		return pageInfo;
	}

	@Override
	public PageInfo querySmsVerify(String phone) {
		PageInfo pageInfo = new PageInfo();

		String key = getVerifyTimesOfSmsKey(phone);
		List<Object> verify = redisDao.getList(key);

		if (verify != null && verify.size() > 0) {
			CodeObject codeObject = (CodeObject) verify.get(verify.size() - 1);
            pageInfo.setCode(ResultInfo.SUCCESS.getCode());
			pageInfo.setMsg(codeObject.getCode());
		} else {
			pageInfo.setCode(3002);
			pageInfo.setMsg("验证码不存在或已失效");
		}

		return pageInfo;

	}

	@Override
	public boolean saveSellSmsMessage(SmsMessage message) {
		boolean result = true;
		try {
			redisDao.setList(SMSMESSAGE_KEY_HEAD, message);
		} catch (Exception e) {
			result = false;
		}
		return result;
	}

	/**
	 * 获取验证短信次数所需的key
     * 
     * @author syb
     * @date 2017年8月23日 下午4:22:26
     * @version 1.0
     * @param phone
     *            手机号
     * @return key
     *
     */
	private String getVerifyTimesOfSmsKey(String phone) {
        String dateStr = DateUtils.getDateStr(new Date(), DateUtils.sf);
        String key = dateStr + "_" + phone;
		return key;
	}

	/**
	 * 保存到消息队列
	 * 
	 * @author syb
	 * @date 2017年8月23日 下午4:53:04
	 * @version 1.0
	 * @param phone 手机号
	 * @param key key
	 * @param code 验证码
	 * @param content 短信内容
	 *
	 */
	private void saveSMSInfoToRedisList(String phone, String key, String code, String content) {
		SmsMessage message = new SmsMessage();
		message.setTelephone(phone);
		message.setContent(content.toString());
		saveSmsMessage(message);

		LOGGER.info(phone + "短信内容为：" + content.toString());

		CodeObject codeObject = new CodeObject();
		codeObject.setCode(code);
		codeObject.setCreateDdate(new Date());
		putCodeListTORedis(key, codeObject);
	}

	/**
	 * 
	 * @description 公共手机号验证方法
	 * @author syb
	 * @date 2017年8月23日 下午5:03:32
	 * @version 1.0
	 * @param phone
	 *            手机号
	 * @param picKey
	 *            图形验证码key
	 * @param picCode
	 *            图形验证码
	 * @return 验证通过返回null 否则返回失败信息
	 *
	 */
	private PageInfo commonVerifyTellephoneMethod(String phone, String picKey, String picCode) {
		PageInfo pageInfo = new PageInfo();
		// 验证图片验证码
		if (!PictureVerifyServiceImpl.varifyCode(picKey, picCode, redisDao, 0)) {
            pageInfo.setCode(ResultInfo.VERIFICATION_CODE_ERROR.getCode());
			pageInfo.setMsg("验证码错误或失效");
			pageInfo.setObj(false);

        } else if (StringUtils.isBlank(phone)) { // 验证手机号是否为空
            pageInfo.setCode(ResultInfo.PARAM_MISS.getCode());
			pageInfo.setMsg("手机号不能为空");

        } else if (!Validation.MatchTelphone(phone)) { // 验证手机号格式
            pageInfo.setCode(ResultInfo.FORMAT_PARAMS_ERROR.getCode());
			pageInfo.setMsg("手机号格式错误");

        } else {
            pageInfo = null;
		}

        return pageInfo;
	}

}
