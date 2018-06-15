package com.mrbt.lingmoney.service.users.impl;

import java.text.MessageFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.mrbt.lingmoney.commons.cache.RedisDao;
import com.mrbt.lingmoney.commons.cache.RedisGet;
import com.mrbt.lingmoney.commons.cache.RedisSet;
import com.mrbt.lingmoney.commons.exception.PageInfoException;
import com.mrbt.lingmoney.mapper.ActivityProductCustomerMapper;
import com.mrbt.lingmoney.mapper.ActivityRecordMapper;
import com.mrbt.lingmoney.mapper.PrefixBankMapper;
import com.mrbt.lingmoney.mapper.RiskAssessmentMapper;
import com.mrbt.lingmoney.mapper.SupportBankMapper;
import com.mrbt.lingmoney.mapper.TradingMapper;
import com.mrbt.lingmoney.mapper.UserUnionInfoMapper;
import com.mrbt.lingmoney.mapper.UsersAccountMapper;
import com.mrbt.lingmoney.mapper.UsersBankMapper;
import com.mrbt.lingmoney.mapper.UsersMapper;
import com.mrbt.lingmoney.mapper.UsersMessageMapper;
import com.mrbt.lingmoney.mapper.UsersPropertiesMapper;
import com.mrbt.lingmoney.mapper.UsersRedPacketUnionMapper;
import com.mrbt.lingmoney.model.ActivityProductCustomer;
import com.mrbt.lingmoney.model.ActivityRecord;
import com.mrbt.lingmoney.model.PrefixBank;
import com.mrbt.lingmoney.model.PrefixBankExample;
import com.mrbt.lingmoney.model.RiskAssessment;
import com.mrbt.lingmoney.model.RiskAssessmentExample;
import com.mrbt.lingmoney.model.SupportBank;
import com.mrbt.lingmoney.model.SupportBankExample;
import com.mrbt.lingmoney.model.TradingExample;
import com.mrbt.lingmoney.model.UserUnionInfo;
import com.mrbt.lingmoney.model.Users;
import com.mrbt.lingmoney.model.UsersAccount;
import com.mrbt.lingmoney.model.UsersAccountExample;
import com.mrbt.lingmoney.model.UsersBank;
import com.mrbt.lingmoney.model.UsersBankExample;
import com.mrbt.lingmoney.model.UsersExample;
import com.mrbt.lingmoney.model.UsersMessage;
import com.mrbt.lingmoney.model.UsersMessageExample;
import com.mrbt.lingmoney.model.UsersProperties;
import com.mrbt.lingmoney.model.UsersPropertiesExample;
import com.mrbt.lingmoney.model.UsersRedPacketUnion;
import com.mrbt.lingmoney.model.UsersRedPacketUnionExample;
import com.mrbt.lingmoney.model.trading.SmsMessage;
import com.mrbt.lingmoney.service.common.UtilService;
import com.mrbt.lingmoney.service.users.UsersService;
import com.mrbt.lingmoney.service.users.VerifyService;
import com.mrbt.lingmoney.utils.AppCons;
import com.mrbt.lingmoney.utils.Common;
import com.mrbt.lingmoney.utils.DateUtils;
import com.mrbt.lingmoney.utils.MD5Utils;
import com.mrbt.lingmoney.utils.PageInfo;
import com.mrbt.lingmoney.utils.PropertiesUtil;
import com.mrbt.lingmoney.utils.ResultParame.ResultInfo;
import com.mrbt.lingmoney.utils.ShortUUID;
import com.mrbt.lingmoney.utils.Validation;
import com.mrbt.myutils.type.FourElementsType;
import com.mrbt.pay.jd.BankBindByJd;
import com.mrbt.pay.jd.vo.BankFourElementsVo;

@Service
public class UsersServiceImpl implements UsersService {
	private static final Logger LOGGER = LogManager.getLogger(UsersServiceImpl.class);

	// #start autowired
	@Autowired
	private VerifyService verifyService;
	@Autowired
	private UsersMapper usersMapper;
	@Autowired
	private UsersPropertiesMapper usersPropertiesMapper;
	@Autowired
	private UsersAccountMapper usersAccountMapper;
	@Autowired
	private UsersBankMapper usersBankMapper;
	@Autowired
	private UsersMessageMapper usersMessageMapper;
	@Autowired
	private ActivityRecordMapper activityRecordMapper;
	@Autowired
	private UserUnionInfoMapper userUnionInfoMapper;
	@Autowired
	private UtilService utilService;
	@Autowired
	private RedisGet redisGet;
	@Autowired
	private RedisSet redisSet;
	@Autowired
	private RedisDao redisDao;
	@Autowired
	private BankBindByJd bankBindByJd;
	@Autowired
	private PrefixBankMapper prefixBankMapper;
	@Autowired
	private SupportBankMapper supportBankMapper;
	@Autowired
	private UsersRedPacketUnionMapper usersRedPacketUnionMapper;
	@Autowired
	private TradingMapper tradingMapper;
	@Autowired
	private ActivityProductCustomerMapper activityProductCustomerMapper;
    @Autowired
    private RiskAssessmentMapper riskAssessmentMapper;

	// #end

	@Override
	public PageInfo regist(String telephone, String password, String invitationCode, String verificationCode,
			String path, Integer channel) throws Exception {
		PageInfo pageInfo = new PageInfo();

		// 验证参数
		try {
			verifyService.verifyPwd(password);
			verifyService.verifyTelephone(telephone, 0);
			verifyService.verifyMsgCode(telephone, verificationCode);
			verifyService.verifyReferralTel(invitationCode);

		} catch (PageInfoException e) {
			pageInfo.setCode(e.getCode());
			pageInfo.setMsg(e.getMessage());
			return pageInfo;
		}

		// 添加用户（手机号码即为用户名）
		String userId = UUID.randomUUID().toString().replace("-", "");
		Users users = new Users();
		users.setId(userId);
		users.setLoginAccount(telephone);
		users.setLoginPsw(MD5Utils.MD5(password));
		users.setTelephone(telephone);
        users.setLastLogin(new Date()); // 最后第一登陆时间默认为当天
		users.setType(0);
		users.setChannel(channel);
		usersMapper.insertSelective(users);

		// 添加用户属性
		UsersAccount usersAccount = new UsersAccount();
		usersAccount.setuId(userId);
		usersAccount.setCountLingbao(0);
		usersAccountMapper.insertSelective(usersAccount);

		UsersProperties usersProperties = new UsersProperties();
        usersProperties.setBank(0); // 默认未绑定银行卡
        usersProperties.setCertification(0); // 默认未通过身份验证
        usersProperties.setLevel(1); // 默认会员等级为零级
		// usersProperties.setNickName("领钱儿用户" + "" + id);

		// 推荐码为8位UUID
		String referralCode = ShortUUID.generateShortUuid();
        usersProperties.setReferralCode(referralCode); // 推荐码
        usersProperties.setRegDate(new Date()); // 注册日期为当天
        usersProperties.setuId(users.getId()); // 对应用户
		usersProperties.setAutoPay(0);
		usersProperties.setFirstBuy(0);

		UsersProperties recommendUsersProperties = null;

		if (!StringUtils.isEmpty(invitationCode)) {
			
			//2018-05-24增加推荐码为手机号的处理逻辑
			boolean isTelephone = Validation.MatchMobile(invitationCode);
			if (isTelephone) {
				UsersProperties uProperties = usersPropertiesMapper.selectByTelephone(invitationCode);
				if (uProperties != null) {
					usersProperties.setReferralId(uProperties.getuId());
					// 添加推荐人层级记录
					recommendUsersProperties = usersPropertiesMapper.selectByuId(uProperties.getuId());
				}
			} else {
				UsersPropertiesExample usersPpsExample = new UsersPropertiesExample();
				usersPpsExample.createCriteria().andReferralCodeEqualTo(invitationCode);
				List<UsersProperties> usersPpsLi = usersPropertiesMapper.selectByExample(usersPpsExample);
				String uId = usersPpsLi.size() > 0 ? usersPpsLi.get(0).getuId() : null;
				if (uId != null) {
					usersProperties.setReferralId(uId);

					// 修改推荐人的信息,增加领宝的个数
					                /*
	                 * UsersAccount invitationAccount =
	                 * usersAccountDao.findByUId(uId); if (account != null) { int
	                 * count_linbao = invitationAccount.getCountLingbao() + ResultInfo.SERVER_ERROR.getCode();
	                 * invitationAccount.setCountLingbao(count_linbao);
	                 * usersAccountDao.update(invitationAccount); ActivityRecord
	                 * record = new ActivityRecord(); record.setName("推荐好友成功注册");
	                 * record.setContent("送领宝"); record.setStatus(1);
	                 * record.setType(0); record.setAmount(500);
	                 * record.setuId(invitationAccount.getuId());
	                 * record.setUseDate(new Date());
	                 * activityRecordDao.save(record);
	                 * 
	                 * }
	                 */

					// 添加推荐人层级记录
					recommendUsersProperties = usersPropertiesMapper.selectByuId(uId);
				}
			}
		}

		// 推荐人等级、推荐人层级
		if (recommendUsersProperties == null) {
			usersProperties.setUserLevel(0);
			usersProperties.setRecommendedLevel(userId + "");

		} else {
			usersProperties.setUserLevel(recommendUsersProperties.getUserLevel() + 1);
			usersProperties.setRecommendedLevel(recommendUsersProperties.getRecommendedLevel() + "," + userId);
		}

		usersPropertiesMapper.insertSelective(usersProperties);

		// #start 添加用户账户信息
		/*
		 * UsersBank usersBank = new UsersBank(); usersBank.setuId(userId);
		 * usersBankMapper.insertSelective(usersBank);
		 */
		// #end 添加用户账户信息

		// 将短信放入redis 统一发送
		String content = AppCons.regist_content_new;
		content = MessageFormat.format(content, "用户");
		SmsMessage message = new SmsMessage();
		message.setTelephone(telephone);
		message.setContent(content);
		redisSet.setListElement(DateUtils.getDateStr(new Date(), DateUtils.sf) + "_" + telephone, message);
		
		// #start 添加用户消息
		UsersMessage usersMessage = new UsersMessage();
		usersMessage.setContent(content);
		usersMessage.setSender("系统管理员");
        usersMessage.setStatus(0); // 阅读状态,默认0未读
		usersMessage.setTime(new Date());
		usersMessage.setTopic("注册成功发送短信");
		usersMessage.setuId(userId);
		usersMessageMapper.insertSelective(usersMessage);

        pageInfo.setCode(ResultInfo.SUCCESS.getCode());
		pageInfo.setMsg("注册成功");

		return pageInfo;
	}

	@Override
	public PageInfo loginForWeb(HttpServletRequest request, String account, String password) throws Exception {
		PageInfo pageInfo = new PageInfo();

		try {
			// 登录通用操作
			Users user = loginCommon(request, account, password);

			// #start 更新redis
			// sessionId-uid 存入redis 存储时间和session保持一致
			String sessionId = request.getSession().getId();
			
			Long expireTime = redisGet.getRestTime(sessionId, TimeUnit.MINUTES);//获取tomcat session的时间
			redisSet.setRedisStringResult(AppCons.SESSION_USER + sessionId, user.getId(), expireTime, TimeUnit.MINUTES);//用户ID写入redis并设置有效期
			
			//获取u_id存储的session
			String sessionKey = AppCons.SESSION_USER_ID + user.getId();
			//判断u_id是否有对应的session存在，如果存在，删除存储的session
			if (redisDao.get(sessionKey) != null) {
				
				String lastLoginSession = redisDao.get(sessionKey).toString();//获取上次登录的session
				
				//如果session与上次的session一致不删除
				if (!lastLoginSession.equals(sessionId)) {
					LOGGER.info("用户：" + user.getId() + "\t 上一次登录的session" +  lastLoginSession);
					redisDao.delete(AppCons.SESSION_USER + lastLoginSession);
					redisDao.delete(lastLoginSession);
				}
			}
			
			redisSet.setRedisStringResult(sessionKey, sessionId, expireTime, TimeUnit.MINUTES);//记录本次登录的session并设置有效期
			
			// #start 更新用户信息
			usersMapper.updateByPrimaryKeySelective(user);
			// #end

			// #start 返回用户信息
			pageInfo.setObj(queryUserInfoCommon(user.getId().toString(), null));
            pageInfo.setCode(ResultInfo.SUCCESS.getCode());
			pageInfo.setMsg("登录成功");

		} catch (PageInfoException pe) {
			pageInfo.setCode(pe.getCode());
			pageInfo.setMsg(pe.getMessage());
			return pageInfo;
		}
		return pageInfo;
	}

	@Override
	public PageInfo loginForMobile(HttpServletRequest request, String account, String password) throws Exception {
		PageInfo pageInfo = new PageInfo();
		try {
			// 登录通用操作
			Users user = loginCommon(request, account, password);

			// #start 更新redis token

			// 取出用户token
			String userToken = user.getToken();
			if (!StringUtils.isEmpty(userToken)) {
				// 如果用户token值已存在 删除redis中的值
				redisDao.delete(AppCons.TOKEN_VERIFY + userToken);
			}
			// 生成新token
			String token = (UUID.randomUUID().toString() + UUID.randomUUID().toString()).replace("-", "");
			LOGGER.info("token:" + token);
			// 用户id存入redis
			String dayConfig = PropertiesUtil.getPropertiesByKey("redisSaveTime");
			Integer day = StringUtils.isEmpty(dayConfig) ? 1 : Integer.parseInt(dayConfig);

			redisSet.setRedisStringResult(AppCons.TOKEN_VERIFY + token, user.getId(), day, TimeUnit.DAYS);
			// #end 更新用户登录时间和token

			// #start 更新用户信息
			user.setToken(token);
			usersMapper.updateByPrimaryKeySelective(user);
			// #end

			// #start 返回用户信息和token
			pageInfo.setObj(queryUserInfoCommon(user.getId(), token));
            pageInfo.setCode(ResultInfo.SUCCESS.getCode());
			pageInfo.setMsg("登录成功");

			// 奖励红包
			// redPacketService.rewardRedPackage(user.getId(), 1, null);
			// #end 返回用户信息

		} catch (PageInfoException pe) {
			pageInfo.setCode(pe.getCode());
			pageInfo.setMsg(pe.getMessage());
			return pageInfo;
		}
		return pageInfo;
	}

	@Override
	public PageInfo verifyUserPwd(String userId, String pwd) throws Exception {
		PageInfo pageInfo = new PageInfo();
		// #start 从redis中获取用户数据
		// #end
		
		// 查询用户密码
		Users user = usersMapper.selectByPrimaryKey(userId);
		String dbPwd = user.getLoginPsw();
		if (!dbPwd.equals(MD5Utils.MD5(pwd))) {
            pageInfo.setResultInfo(ResultInfo.PASSWARD_ERROR);
			return pageInfo;
		}
        pageInfo.setCode(ResultInfo.SUCCESS.getCode());
		pageInfo.setMsg("用户密码匹配");
		return pageInfo;
	}

	@Override
	public PageInfo userVerified(String token, String uId, String name, String idCard) throws Exception {
		PageInfo pageInfo = new PageInfo();
		// #start 验证参数
		try {
			verifyService.verifyName(name, "用户姓名");
			verifyService.verifyIdCard(idCard);
		} catch (PageInfoException e) {
			pageInfo.setCode(e.getCode());
			pageInfo.setMsg(e.getMessage());
			return pageInfo;
		}
		// #end

		// #start 用户认证操作

		// #end 用户认证操作

		// #start 修改用户属性
		UsersPropertiesExample usersPpsExample = new UsersPropertiesExample();
		usersPpsExample.createCriteria().andUIdEqualTo(uId);
		UsersProperties usersProperties = new UsersProperties();
		usersProperties.setName(name);
		usersProperties.setIdCard(idCard);
		usersProperties.setCertification(1);
		usersProperties.setuId(uId);
		usersPropertiesMapper.updateByExampleSelective(usersProperties, usersPpsExample);
		// #end

		// #start 添加用户消息
		UsersMessage usersMessage = new UsersMessage();
		usersMessage.setContent("恭喜您完成实名认证，领钱儿已将1000领宝放入您的账户中，请注意查收！<p>温馨提示：领宝可以在领宝商城中兑换相应礼品，目前领宝商城正在搭建中，敬请期待！</p>");
		usersMessage.setSender("系统管理员");
        usersMessage.setStatus(0); // 阅读状态,默认0未读
		usersMessage.setTime(new Date());
		usersMessage.setTopic("实名认证有礼");
		usersMessage.setuId(uId);
		usersMessageMapper.insertSelective(usersMessage);
		// #end

		// #start 修改用户账户表
		UsersAccountExample usersAccExample = new UsersAccountExample();
		usersAccExample.createCriteria().andUIdEqualTo(uId);
		UsersAccount usersAccount = new UsersAccount();
		usersAccount.setCountLingbao(1000);
		usersAccountMapper.updateByExampleSelective(usersAccount, usersAccExample);
		// #end

		// #start 增加用户活动
		ActivityRecord record = new ActivityRecord();
		record.setName("实名认证有礼");
		record.setContent("送领宝");
		record.setStatus(1);
		record.setType(0);
		record.setAmount(1000);
		record.setuId(uId);
		record.setUseDate(new Date());
		activityRecordMapper.insertSelective(record);
		// #end 增加用户活动

		// #start 增加推荐人领宝
		String referralId = usersProperties.getReferralId();

		if (!StringUtils.isEmpty(referralId)) {
			// 如果推荐人存在
			usersPpsExample.clear();
			usersPpsExample.createCriteria().andUIdEqualTo(referralId);
			UsersProperties recommendUsersProperties = usersPropertiesMapper.selectByExample(usersPpsExample).get(0);

			// 非导入用户或者用户推荐等级不为0，增加领宝
			if (recommendUsersProperties.getPlatformType() != 1 || recommendUsersProperties.getUserLevel() != 0) {

				// 修改推荐人的信息,增加领宝的个数
				usersAccExample.clear();
				usersAccExample.createCriteria().andUIdEqualTo(referralId);
				UsersAccount invitationAccount = usersAccountMapper.selectByExample(usersAccExample).get(0);

				if (invitationAccount != null) {
					// 领宝+500
					int countLinbao = invitationAccount.getCountLingbao() + 500;
					invitationAccount.setCountLingbao(countLinbao);
					usersAccountMapper.updateByPrimaryKeySelective(invitationAccount);

					// 添加推荐人活动
					ActivityRecord recommendRecord = new ActivityRecord();
					recommendRecord.setName("推荐好友成功实名认证");
					recommendRecord.setContent("送领宝");
					recommendRecord.setStatus(1);
					recommendRecord.setType(0);
					recommendRecord.setAmount(500);
					recommendRecord.setuId(invitationAccount.getuId());
					recommendRecord.setUseDate(new Date());
					activityRecordMapper.insertSelective(recommendRecord);
				}
			}

		}
		// #end

		// #start 修改redis中的用户信息
		UserUnionInfo newUserUnion = userUnionInfoMapper.selectByUid(uId);
		if(token!=null){
			redisSet.setRedisStringResult(AppCons.TOKEN_VERIFY + token, newUserUnion, 1, TimeUnit.DAYS);
		}
		// #end

        pageInfo.setCode(ResultInfo.SUCCESS.getCode());
		pageInfo.setMsg("实名认证成功");
		return pageInfo;
	}

	@Override
	public PageInfo queryUserStatus(String uId) throws Exception {
		PageInfo pageInfo = new PageInfo();
		// 查询用户实名信息
		UserUnionInfo userInfo = userUnionInfoMapper.selectByUid(uId);
		if (StringUtils.isEmpty(userInfo.getCertification()) || userInfo.getCertification().equals(0)) {
            pageInfo.setCode(ResultInfo.USER_NOT_REALNAME.getCode());
			pageInfo.setMsg("用户未实名");
			return pageInfo;
		}

        pageInfo.setCode(ResultInfo.SUCCESS.getCode());
		pageInfo.setMsg("用户已登录，并且已实名");

		return pageInfo;
	}

	@Override
	public PageInfo logout(String key) throws Exception {
		PageInfo pageInfo = new PageInfo();

		if (redisDao.delete(key)) {
            pageInfo.setCode(ResultInfo.SUCCESS.getCode());
			pageInfo.setMsg("退出登录成功");

		} else {
            pageInfo.setCode(ResultInfo.SERVER_ERROR.getCode());
			pageInfo.setMsg("退出登录失败");
		}

		return pageInfo;
	}

	@Override
	public PageInfo verifyLogin(String account) throws Exception {
		PageInfo pageInfo = new PageInfo();
		try {
			verifyService.verifyLoginErrorCount(AppCons.login_error_verification + account, 6);
			verifyService.verifyLoginErrorCount(AppCons.login_error_verification_ip + account, 15);
		} catch (PageInfoException pe) {
			pageInfo.setCode(pe.getCode());
			pageInfo.setMsg(pe.getMessage());
			return pageInfo;
		}
        pageInfo.setCode(ResultInfo.SUCCESS.getCode());
		pageInfo.setMsg("验证成功");
		return pageInfo;
	}

	@Override
	public PageInfo queryUsersInfo(String key) throws Exception {
		PageInfo pageInfo = new PageInfo();
		UserUnionInfo userInfo = new UserUnionInfo();
		String uid = "";
		try {
			uid = utilService.queryUid(key);
			userInfo = queryUserInfoCommon(uid.toString(), null);
		} catch (PageInfoException e) {
			pageInfo.setCode(e.getCode());
			pageInfo.setMsg(e.getMessage());
			return pageInfo;
		}
		pageInfo.setObj(userInfo);
        pageInfo.setCode(ResultInfo.SUCCESS.getCode());
		return pageInfo;
	}

	// #start 私有方法

	/**
	 * 获取用户信息
	 * 
	 * @author YJQ 2017年5月18日
	 * @param uid
	 * @param token
	 * @return
	 */
	private UserUnionInfo queryUserInfoCommon(String uid, String token) throws Exception {
		// 查询用户未读的站内信
		UsersMessageExample usersMessageEx = new UsersMessageExample();
		usersMessageEx.createCriteria().andUIdEqualTo(uid).andStatusEqualTo(0);

		UserUnionInfo userInfo = userUnionInfoMapper.selectByUid(uid);
		if (StringUtils.isEmpty(userInfo)) {
            throw new PageInfoException("用户信息不存在", ResultInfo.ACCOUNT_EXIST.getCode());
		}
		userInfo.setUnRead((int) usersMessageMapper.countByExample(usersMessageEx));

		if (!StringUtils.isEmpty(token))
			userInfo.setToken(token);

        RiskAssessmentExample riskExample = new RiskAssessmentExample();
        riskExample.createCriteria().andUIdEqualTo(uid);
        List<RiskAssessment> risklist = riskAssessmentMapper.selectByExample(riskExample);
        if (risklist != null && risklist.size() > 0) {
            RiskAssessment ra = risklist.get(0);
            int score = ra.getScore();
            if (score >= 20 && score <= 40) {
                //20-40 谨慎型
                userInfo.setRiskAssessment("谨慎型");
            } else if (score >= 41 && score <= 60) {
                //41-60 稳健型
                userInfo.setRiskAssessment("稳健型");
            } else if (score >= 61 && score <= 80) {
                //61-80 积极型
                userInfo.setRiskAssessment("积极型");
            } else if (score >= 81 && score <= 100) {
                //81-100 激进型
                userInfo.setRiskAssessment("激进型");
            }
        }

		return userInfo;
	}

	private Users loginCommon(HttpServletRequest request, String account, String password) throws Exception {
		String ip = Common.getIpAddr(request);

		// #start 验证
		verifyService.verifyLoginErrorCount(AppCons.login_error_verification_ip + ip, 15);
		verifyService.verifyLoginAccount(account);
		verifyService.verifyEmpty(password, "密码为空");

		// 用户是否存在
		UsersExample verifyUsersExample = new UsersExample();
		verifyUsersExample.or().andLoginAccountEqualTo(account).andTypeNotEqualTo(9);
		verifyUsersExample.or().andTelephoneEqualTo(account).andTypeNotEqualTo(9);
		List<Users> isExsisLi = usersMapper.selectByExample(verifyUsersExample);
		if (StringUtils.isEmpty(isExsisLi) || isExsisLi.size() == 0) {
			// 用户不存在 记录此ip错误输入次数
			String ipErrorKey = AppCons.login_error_verification_ip + ip;
			utilService.dealLoginError(ipErrorKey, 15, "IP");
			throw new PageInfoException("用户不存在", ResultInfo.ACCOUNT_NUMBER_NON_EXIST.getCode());
		}

		verifyService.verifyLoginErrorCount(AppCons.login_error_verification + account, 6);

		Users user = isExsisLi.get(0);

		// #end

		// #start 验证密码是否匹配
		String md5Pwd = MD5Utils.MD5(password);
		if (!md5Pwd.equals(user.getLoginPsw())) {
			// 密码有误 则在redis中记录输错密码次数+1 (ip 和账号同时都要加)
			utilService.dealLoginError(AppCons.login_error_verification + account, 6, "账号");
            throw new PageInfoException("账号密码错误！", ResultInfo.PASSWARD_ERROR.getCode());
		}
		// #end

		String uid = user.getId();

		// #start 更新用户登录时间
		Users newUsers = new Users();
		newUsers.setId(uid);
		newUsers.setToken(user.getToken());
		newUsers.setLastLogin(new Date());

		// #end 更新用户登录时间

		// 登录成功即删除记录的错误次数
		redisDao.delete(AppCons.login_error_verification + account);
		redisDao.delete(AppCons.login_error_verification_ip + ip);

		return newUsers;
	}

	// #end

	public static void main(String[] args) {
		System.out.println(PropertiesUtil.getPropertiesByKey("redisSaveTime"));
	}

	@Override
	public PageInfo updateUserDeviceToken(String uId, String deviceToken, Integer deviceType) throws Exception {
		PageInfo pageInfo = new PageInfo();
		try {
			verifyService.verifyEmpty(deviceToken, "用户设备标识");
		} catch (PageInfoException e) {
			pageInfo.setCode(e.getCode());
			pageInfo.setMsg(e.getMessage());
			return pageInfo;
		}
		// 更新
		UsersProperties users = new UsersProperties();
		users.setYoumengToken(deviceToken);
		users.setDeviceType(deviceType);
		UsersPropertiesExample ex = new UsersPropertiesExample();
		ex.createCriteria().andUIdEqualTo(uId);
		usersPropertiesMapper.updateByExampleSelective(users, ex);
        pageInfo.setCode(ResultInfo.SUCCESS.getCode());
		pageInfo.setMsg("保存成功");
		return pageInfo;
	}

	@Override
	public PageInfo getJDBindCardSecurityCode(String uid, String tel, String name, String idCard, String number,
			String bankShort) {
		PageInfo pi = new PageInfo();

		if (StringUtils.isEmpty(tel) || StringUtils.isEmpty(name) || StringUtils.isEmpty(idCard)
				|| StringUtils.isEmpty(number) || StringUtils.isEmpty(bankShort)) {
            pi.setCode(ResultInfo.EMPTY_DATA.getCode());
			pi.setMsg("参数缺失");
			return pi;
		}

		// 验证绑卡接口是否可用
//		String result = (String) redisDao.get(AppCons.JD_BINDCARD_AVALIABLE);
//		if (StringUtils.isEmpty(result) || "N".equals(result)) {
//            pi.setCode(ResultInfo.JINGDONG_CRAD_ERROR.getCode());
//			pi.setMsg("京东绑卡暂不可用,请联系管理员");
//			return pi;
//		}

		// 验证卡号
		UsersBankExample example = new UsersBankExample();
		example.createCriteria().andNumberEqualTo(number).andStatusEqualTo("1");
		List<UsersBank> list = usersBankMapper.selectByExample(example);

		if (list != null && list.size() > 0) {
            pi.setResultInfo(ResultInfo.BANK_CARD_ALREADY_BIND);
			return pi;
		}

		// redis验证通过
		if (checkBankRedis(idCard, 1)) {

			StringBuffer sb = new StringBuffer();
			sb.append("用户名：" + name + "\n");
			sb.append("身份证号：" + idCard + "\n");
			sb.append("银行卡号：" + number + "\n");
			sb.append("银行简称：" + bankShort + "\n");
			sb.append("手机号：" + tel + "\n");
			System.out.println("实名认证获取验证码请求参数信息：\n" + sb.toString());

			BankFourElementsVo bfev = bankBindByJd.getToken("");

			System.out.println("第一次请求京东 \n" + bfev);

			if (bfev.isOk()) {
				bfev = bankBindByJd.getSignature(name, idCard, number, bankShort, tel, bfev);
				if (bfev.isOk()) {
					System.out.println("发送短信验证码成功：" + new Date());
					deleteBankRedis(idCard, 1);

					// 保存信息到redis 请求绑卡是需要使用,两分钟超时
					redisDao.set("JDBINDCARD_" + uid, bfev);
					redisDao.expire("JDBINDCARD_" + uid, 2, TimeUnit.MINUTES);
                    pi.setCode(ResultInfo.SUCCESS.getCode());
					pi.setMsg("发送验证码成功");
				} else {
					System.out.println("发送短信验证码失败：" + new Date());
					System.out.println("京东返回结果码：" + bfev.getResultCode() + "\n 京东返回结信息：" + bfev.getResultInfo());

					pi.setCode(302);
					pi.setMsg(bfev.getResultInfo());
				}

			} else {
				pi.setCode(301);
				pi.setMsg(bfev.getResultInfo());
			}

		} else {
			pi.setCode(300);
			pi.setMsg("一分钟内不可多次获取验证码，请耐心等待");
		}

		return pi;
	}

	@Override
	@Transactional
	public PageInfo jdBindCardCertification(String uid, String idCard, String name, String number, String tel,
			String bankShort, String msgcode) {
		PageInfo pi = new PageInfo();

		if (StringUtils.isEmpty(idCard) || StringUtils.isEmpty(name) || StringUtils.isEmpty(number)
				|| StringUtils.isEmpty(tel) || StringUtils.isEmpty(bankShort) || StringUtils.isEmpty(msgcode)) {
            pi.setCode(ResultInfo.EMPTY_DATA.getCode());
			pi.setMsg("参数缺失");
			return pi;
		}

		// 验证绑卡接口是否可用
//		String result = (String) redisDao.get(AppCons.JD_BINDCARD_AVALIABLE);
//		if (StringUtils.isEmpty(result) || "N".equals(result)) {
//            pi.setCode(ResultInfo.JINGDONG_CRAD_ERROR.getCode());
//			pi.setMsg("京东绑卡暂不可用,请联系管理员");
//			return pi;
//		}

		if (checkBankRedis(idCard, 2)) {
			BankFourElementsVo bfev = (BankFourElementsVo) redisDao.get("JDBINDCARD_" + uid);//获取签约的短信验证码

			if (bfev != null) {
				// 查询银行卡信息 验证是否初次绑卡
				UsersBankExample usersBankExmp = new UsersBankExample();
				usersBankExmp.createCriteria().andUIdEqualTo(uid);
				List<UsersBank> bankList = usersBankMapper.selectByExample(usersBankExmp);

				// 查询用户属性信息验证表单信息是否有效
				UsersPropertiesExample upExample = new UsersPropertiesExample();
				upExample.createCriteria().andUIdEqualTo(uid);
				List<UsersProperties> propertyList = usersPropertiesMapper.selectByExample(upExample);

				if (null != propertyList && propertyList.size() > 0) {
					
					StringBuffer sb = new StringBuffer();
					sb.append("用户名：" + name + "\n");
					sb.append("身份证号：" + idCard + "\n");
					sb.append("银行卡号：" + number + "\n");
					sb.append("银行简称：" + bankShort + "\n");
					sb.append("手机号：" + tel + "\n");
					sb.append("验证码：" + msgcode);
					System.out.println("实名认证获请求参数信息：" + sb.toString());

					bfev.setElementsType(FourElementsType.verify);
					bfev.setVerfyCode(msgcode);
					bfev = bankBindByJd.confirmBankBind(name, idCard, number, bankShort, tel, bfev, msgcode);

					// 绑卡成功
					if (bfev.isOk()) {
                        pi.setCode(ResultInfo.SUCCESS.getCode());
						pi.setMsg("绑卡成功");
						deleteBankRedis(idCard, 2);

						System.out.println("实名绑卡成功：" + new Date());

						// 根据银行简称查询银行信息
						String bankName = "";
						String bankCode = "";
						SupportBankExample bankExample = new SupportBankExample();
						bankExample.createCriteria().andBankShortEqualTo(bankShort);
						List<SupportBank> sbList = supportBankMapper.selectByExample(bankExample);
						if (sbList != null && sbList.size() > 0) {
							bankName = sbList.get(0).getBankName();
							bankCode = sbList.get(0).getBankCode();
						}

						// 绑卡成功后查询该银行卡前六位是否在数据库中，如果没有则新增
						savePrefixBankInfo(number, bankShort, bankName);

						// 保存用户银行卡信息
						bindCardSaveCardInfo(uid, number, tel, bankName, bankCode, bankList);
						
						//修改用户信息表users_properties表中的certification, bank的状态
						jdBindCardModfiUserProp(uid, name, idCard);

						deleteBankRedis(idCard, 2);
					} else {
						pi.setCode(305);
						pi.setMsg("绑卡失败:" + bfev.getResultInfo());
						deleteBankRedis(idCard, 2);

						System.out.println("实名绑卡失败：" + new Date());
						System.out.println("京东返回结果码：" + bfev.getResultCode() + "\n 京东返回结信息：" + bfev.getResultInfo());
					}

					// 删除reids绑卡信息
					redisDao.delete("JDBINDCARD_" + uid);
				} else {
					deleteBankRedis(idCard, 2);
                    pi.setCode(ResultInfo.EMPTY_DATA.getCode());
					pi.setMsg("用户信息有误");
				}

			} else {
				deleteBankRedis(idCard, 2);
                pi.setCode(ResultInfo.EMPTY_DATA.getCode());
				pi.setMsg("验证码超时");
			}

		} else {
            pi.setResultInfo(ResultInfo.SUBMIT_FREQUENTLY_IN_TWO_MIN);
		}

		return pi;
	}

	/**
	 * 京东绑卡成功后修改users_properties表状态
	 * 如果状态为1或3，不做修改
	 * 如果状态为0， 0 修改成1，1， 如果状态为2，2，修改成3，3
	 * @param uid	用户UID
	 */
	private void jdBindCardModfiUserProp(String uId, String name, String idCard) {
		//查询状态
    	UsersProperties uProperties = usersPropertiesMapper.selectByuId(uId);
    	
    	if (uProperties.getCertification() != 1 && uProperties.getCertification() != 3) {
    		UsersProperties moUp = new UsersProperties();
        	
    		if (uProperties.getCertification() == 0) {
    			moUp.setCertification(1);
    		} else if(uProperties.getCertification() == 2) {
    			moUp.setCertification(3);
    		}
    		
    		if (uProperties.getBank() == 0) {
    			moUp.setBank(1);
    		} else if(uProperties.getBank() == 2) {
    			moUp.setBank(3);
    		}
    		
			if (StringUtils.isEmpty(uProperties.getName())) {
				moUp.setName(name);
			}

			if (StringUtils.isEmpty(uProperties.getIdCard())) {
				moUp.setIdCard(idCard);
			}

    		moUp.setId(uProperties.getId());
    		usersPropertiesMapper.updateByPrimaryKeySelective(moUp);
		}
	}

	/**
     * 保存用户银行卡信息
     * 
     * @param uid 用户id
     * @param number 卡号
     * @param tel 手机号
     * @param bankName 银行名称
     * @param bankCode 银行代码
     *
     */
	private void bindCardSaveCardInfo(String uid, String number, String tel, String bankName, String bankCode, List<UsersBank> bankList) {
		UsersBank ub = new UsersBank();
		if (bankList != null && bankList.size() > 0) { //判断是否绑定过银行卡
			int x = 0;
			for (UsersBank usersBank : bankList) {  //验证是否绑定过该银行卡，因为有可能是用户更好了银行绑定手机号
				if (usersBank.getNumber() != null && usersBank.getNumber().equals(number)) {
					x = 1;
				}
			}
			if (x > 0) {   //用户曾经绑定过该银行卡，更新手机号和银行卡状态、绑定时间，条件为银行卡号，用户ID
				UsersBankExample upExample = new UsersBankExample();
				upExample.createCriteria().andNumberEqualTo(number).andUIdEqualTo(uid);
				
				ub.setTel(tel);
				ub.setStatus("1");
				ub.setBindtime(new Date());
				int i = usersBankMapper.updateByExampleSelective(ub, upExample);
				if (i < 1) {
					LOGGER.error("用户绑卡成功，但保存用户银行卡信息失败。\n" + ub.toString());
				}
			} else { //用户没有绑定过这张银行卡，插入银行数据
				ub.setuId(uid);
				ub.setNumber(number);
				ub.setTel(tel);
				ub.setBankcode(bankCode);
				ub.setName(bankName);
				ub.setStatus("1");
				ub.setIsdefault("0");
				ub.setBindtime(new Date());
				ub.setPlatformType(1);
				int i = usersBankMapper.insertSelective(ub);
				if (i < 1) {
					LOGGER.error("用户绑卡成功，但保存用户银行卡信息失败。\n" + ub.toString());
				}
			}
		}else {
			ub.setuId(uid);
			ub.setNumber(number);
			ub.setTel(tel);
			ub.setBankcode(bankCode);
			ub.setName(bankName);
			ub.setStatus("1");
			ub.setIsdefault("1");
			ub.setBindtime(new Date());
			ub.setPlatformType(1);
			int i = usersBankMapper.insertSelective(ub);

			if (i < 1) {
				LOGGER.error("用户绑卡成功，但保存用户银行卡信息失败。\n" + ub.toString());
			}
		}
	}

    /**
     * 保存银行卡前六位
     * 
     * @param number 卡号
     * @param bankShort 银行简称
     * @param bankName 银行名
     *
     */
	private void savePrefixBankInfo(String number, String bankShort, String bankName) {
        final int six = 6; // 截取卡号前六位
        PrefixBankExample prefixBankExmp = new PrefixBankExample();
        prefixBankExmp.createCriteria().andPrefixNumberEqualTo(number.substring(0, six));
		List<PrefixBank> prefixBankList = prefixBankMapper.selectByExample(prefixBankExmp);
		if (prefixBankList == null || prefixBankList.isEmpty()) {
			PrefixBank pb = new PrefixBank();
			pb.setBankShort(bankShort);
			pb.setName(bankName);
            pb.setPrefixNumber(number.substring(0, six));
			pb.setSize(number.length());
			pb.setStatus(1);
			prefixBankMapper.insertSelective(pb);
		}
	}

	    /**
     * 判断redis中身份证号是否存在
     * 
     * @param idCard
     *            身份证号
     * @param type
     *            类型 1发送验证码 2提交
     * @return true验证通过  false 不通过
     */
	private boolean checkBankRedis(String idCard, Integer type) {
		boolean flag = false;
        if (type == 1) { // 验证码
			try {
				// 查询redis
                String resultStr = (String) redisDao.get(idCard + "_1");
				// 如果存在，则false
                if (resultStr != null && !resultStr.equals("")) {
					flag = false;
				} else {
					// 不存在，则放入redis，过期时间1分钟
					redisDao.set(idCard + "_1", idCard + "_1");
					redisDao.expire(idCard + "_1", 1, TimeUnit.MINUTES);
					flag = true;
				}
			} catch (Exception e) {

			}
        } else if (type == 2) { // 提交
			try {
				// 查询redis
                String resultStr = (String) redisDao.get(idCard + "_2");
				// 如果存在，则false
                if (resultStr != null && !resultStr.equals("")) {
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

	/**
	 * 清除身份证号redis
	 * 
	 * @param idCard
	 *            身份证号
	 * @param type
	 *            类型 1发送验证码 2提交
	 * @return
	 */
	private void deleteBankRedis(String idCard, Integer type) {
        if (type == 1) { // 验证码
			try {
				// 清除redis
				redisDao.delete(idCard + "_1");
			} catch (Exception e) {

			}
        } else if (type == 2) { // 提交
			try {
				// 清除redis
				redisDao.delete(idCard + "_2");
			} catch (Exception e) {

			}
		}
	}

	@Override
	public PageInfo listSupportBank() {
		PageInfo pi = new PageInfo();

		SupportBankExample example = new SupportBankExample();
		example.createCriteria().andBankTypeNotEqualTo(1);
		List<SupportBank> list = supportBankMapper.selectByExample(example);

		if (list != null && list.size() > 0) {
            pi.setCode(ResultInfo.SUCCESS.getCode());
			pi.setMsg("成功");
			pi.setRows(list);
			pi.setTotal(list.size());
		} else {
            pi.setCode(ResultInfo.EMPTY_DATA.getCode());
			pi.setMsg("暂无数据");
		}

		return pi;
	}

	@Override
	public PageInfo listJDBankCard(String uid) {
		PageInfo pi = new PageInfo();

		List<Map<String, Object>> list = usersBankMapper.listJDBankCardView(uid);
		if (list!=null && list.size()>0) {
		   for (int i=0;i<list.size();i++) {
			   if (list.get(i).get("tel") != null) {
				   String oldStr= list.get(i).get("tel").toString().substring(3, 7);
				   String tel=list.get(i).get("tel").toString().replace(oldStr, "****");
				   list.get(i).put("tel", tel);
			   }
		   }
		}
		if (list != null && list.size() > 0) {
            pi.setCode(ResultInfo.SUCCESS.getCode());
			pi.setMsg("成功");
			pi.setRows(list);
			pi.setTotal(list.size());
		} else {
            pi.setCode(ResultInfo.EMPTY_DATA.getCode());
			pi.setMsg("无数据");
		}

		return pi;
	}

	@Override
	public PageInfo queryRedPackage(PageInfo pageInfo, String uId) throws Exception {
		// 从redis中获取用户数据
		UsersRedPacketUnionExample ex = new UsersRedPacketUnionExample();
		ex.createCriteria().andUIdEqualTo(uId);
		pageInfo.setTotal(usersRedPacketUnionMapper.countByExample(ex));

		// 先判断是不是对应产品
		ex.setLimitStart(pageInfo.getFrom());
		ex.setLimitEnd(pageInfo.getSize());
		List<UsersRedPacketUnion> li = usersRedPacketUnionMapper.selectByExample(ex);
		pageInfo.setRows(li);

		return pageInfo;
	}

	@Override
	public PageInfo queryRedPackage(PageInfo pageInfo, Double actualAmount) throws Exception {
		UsersRedPacketUnionExample ex = new UsersRedPacketUnionExample();
		ex.createCriteria().andActualAmountEqualTo(actualAmount);
		pageInfo.setTotal(usersRedPacketUnionMapper.countByExample(ex));

		ex.setLimitStart(pageInfo.getFrom());
		ex.setLimitEnd(pageInfo.getSize());
		pageInfo.setRows(usersRedPacketUnionMapper.selectByExample(ex));
		return pageInfo;
	}

	@Override
	public PageInfo userBuyState(String uId) {
		PageInfo pageInfo = new PageInfo();

		// 是否购买过产品：0否 1是
		int userState = 0;

		TradingExample tradingExample = new TradingExample();
        tradingExample
                .createCriteria()
                .andUIdEqualTo(uId)
                .andStatusIn(
                        Arrays.asList(AppCons.BUY_OK, AppCons.SELL_STATUS, AppCons.SELL_OK, AppCons.BUY_FROKEN,
                                AppCons.SELL_WAITING));
		long buyCount = tradingMapper.countByExample(tradingExample);
        if (buyCount > 0) { // 购买过产品
			userState = 1;
		}

		pageInfo.setObj(userState);
        pageInfo.setCode(ResultInfo.SUCCESS.getCode());

		return pageInfo;
	}

	@Override
    public PageInfo activityNovicePcUrl(int priority) {
		PageInfo pageInfo = new PageInfo();
		PageInfo info = new PageInfo();
		// 查询条件
		Map<String, Object> condition = new HashMap<String, Object>();
        condition.put("status", 1); // 状态可用
        condition.put("priority", priority);
		info.setCondition(condition);

		ActivityProductCustomer activityProductCustomer = activityProductCustomerMapper.selectActivityInfo(info);
		if (activityProductCustomer != null) {
			Map<String, Object> resMap = new HashMap<String, Object>();
			resMap.put("actTitle", activityProductCustomer.getActTitle());
			resMap.put("linkUrlPc", activityProductCustomer.getLinkUrlPc());
			pageInfo.setObj(resMap);
            pageInfo.setCode(ResultInfo.SUCCESS.getCode());
			return pageInfo;
		} else {
            pageInfo.setCode(ResultInfo.RETURN_EMPTY_DATA.getCode());
			pageInfo.setMsg("暂无数据");
			return pageInfo;
		}
	}

    @Override
    public void saveOrUpdateAssessmentResult(String uid, Integer score) {
        if (!StringUtils.isEmpty(uid) && !StringUtils.isEmpty(score)) {
            RiskAssessmentExample example = new RiskAssessmentExample();
            example.createCriteria().andUIdEqualTo(uid);
            List<RiskAssessment> list = riskAssessmentMapper.selectByExample(example);
            if (list != null && list.size() > 0) {
                RiskAssessment risk = list.get(0);
                risk.setScore(score);
                riskAssessmentMapper.updateByPrimaryKeySelective(risk);
            } else {
                RiskAssessment risk = new RiskAssessment();
                risk.setScore(score);
                risk.setuId(uid);
                riskAssessmentMapper.insertSelective(risk);
            }
        }
    }

    @Override
    public Integer hasTestRisAssessment(String uid) {
        RiskAssessmentExample example = new RiskAssessmentExample();
        example.createCriteria().andUIdEqualTo(uid);
        List<RiskAssessment> list = riskAssessmentMapper.selectByExample(example);
        if (list != null && list.size() > 0) {
            RiskAssessment risk = list.get(0);
            return risk.getScore();
        }

        return null;
    }
}
