package com.mrbt.lingmoney.service.users.impl;

import java.io.File;
import java.util.ArrayList;
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
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.mrbt.lingmoney.commons.cache.RedisSet;
import com.mrbt.lingmoney.commons.exception.PageInfoException;
import com.mrbt.lingmoney.mapper.LingbaoAddressMapper;
import com.mrbt.lingmoney.mapper.TempletMapper;
import com.mrbt.lingmoney.mapper.UsersAccountMapper;
import com.mrbt.lingmoney.mapper.UsersBankMapper;
import com.mrbt.lingmoney.mapper.UsersMapper;
import com.mrbt.lingmoney.mapper.UsersPropertiesMapper;
import com.mrbt.lingmoney.model.LingbaoAddress;
import com.mrbt.lingmoney.model.LingbaoAddressExample;
import com.mrbt.lingmoney.model.Templet;
import com.mrbt.lingmoney.model.TempletExample;
import com.mrbt.lingmoney.model.Users;
import com.mrbt.lingmoney.model.UsersAccount;
import com.mrbt.lingmoney.model.UsersBank;
import com.mrbt.lingmoney.model.UsersBankExample;
import com.mrbt.lingmoney.model.UsersExample;
import com.mrbt.lingmoney.model.UsersProperties;
import com.mrbt.lingmoney.model.UsersPropertiesExample;
import com.mrbt.lingmoney.service.common.SmsService;
import com.mrbt.lingmoney.service.common.UtilService;
import com.mrbt.lingmoney.service.users.UsersAccountSetService;
import com.mrbt.lingmoney.service.users.VerifyService;
import com.mrbt.lingmoney.utils.AppCons;
import com.mrbt.lingmoney.utils.Common;
import com.mrbt.lingmoney.utils.DateUtils;
import com.mrbt.lingmoney.utils.FileProcessUtil;
import com.mrbt.lingmoney.utils.FtpUtils;
import com.mrbt.lingmoney.utils.IOUtils;
import com.mrbt.lingmoney.utils.MD5Utils;
import com.mrbt.lingmoney.utils.MailUtil;
import com.mrbt.lingmoney.utils.PageInfo;
import com.mrbt.lingmoney.utils.PropertiesUtil;
import com.mrbt.lingmoney.utils.ResultParame.ResultInfo;

/**
 * 用户账户设置
 *
 */
@Service
public class UsersAccountSetServiceImpl implements UsersAccountSetService {
	Logger log = LogManager.getLogger(UsersAccountSetServiceImpl.class);

	@Autowired
	private UsersMapper usersMapper;
	@Autowired
	private UsersPropertiesMapper usersPropertiesMapper;
	@Autowired
	private TempletMapper templetMapper;
	@Autowired
	private LingbaoAddressMapper lingbaoAddressMapper;
	@Autowired
	private UsersBankMapper usersBankMapper;
	@Autowired
	private UsersAccountMapper usersAccountMapper;
	@Autowired
	private SmsService smsService;
	@Autowired
	private VerifyService verifyService;
	@Autowired
	private UtilService utilService;

	@Autowired
	private FtpUtils ftpUtils;
	@Autowired
	private RedisSet redisSet;

	@Override
	public PageInfo modifyAvatar(HttpServletRequest request, String image, String key) throws Exception {
		PageInfo pageInfo = new PageInfo();

		String uid = "";
		try {
			uid = utilService.queryUid(key);
			verifyService.verifyEmpty(image, "图片为空");
		} catch (PageInfoException e) {
			pageInfo.setCode(e.getCode());
			pageInfo.setMsg(e.getMessage());
			return pageInfo;
		}
		// #start 上传文件
		String fileName = UUID.randomUUID().toString() + ".jpg";
		String virtualPath = AppCons.USER_PICTURE_DIR;

		ftpUtils.upload(IOUtils.Base64ToInputStream(image), virtualPath, fileName);

		String url = ftpUtils.getUrl() + virtualPath + "/" + fileName;

		// #end

		// #start 修改Url
		UsersProperties usersProperties = new UsersProperties();
		usersProperties.setPicture(url);

		UsersPropertiesExample ex = new UsersPropertiesExample();
		ex.createCriteria().andUIdEqualTo(uid);
		usersPropertiesMapper.updateByExampleSelective(usersProperties, ex);
		// #end

		pageInfo.setObj(url);
        pageInfo.setCode(ResultInfo.SUCCESS.getCode());
		pageInfo.setMsg("头像修改成功");
		return pageInfo;

	}

	@Override
	public PageInfo modifyAvatar(MultipartFile file, String key) throws Exception {
		PageInfo pageInfo = new PageInfo();
		String uId = "";

		try {
			uId = utilService.queryUid(key);

		} catch (PageInfoException e) {
			pageInfo.setCode(e.getCode());
			pageInfo.setMsg(e.getMessage());
			return pageInfo;
		}

		if (StringUtils.isEmpty(file)) {
            pageInfo.setCode(ResultInfo.PARAMETER_ERROR.getCode());
			pageInfo.setMsg("提交图片文件为空");
			return pageInfo;
		}

		String fileName = UUID.randomUUID().toString().replace("-", "");
		fileName += ".jpg";

		String virtualPath = AppCons.USER_PICTURE_DIR + "/" + uId;

		ftpUtils.upload(file.getInputStream(), virtualPath, fileName);

		String url = ftpUtils.getUrl() + virtualPath + File.separator + fileName;

		ArrayList<String> avatarUrls = new ArrayList<String>();
		avatarUrls.add(url);

        pageInfo.setCode(ResultInfo.SUCCESS.getCode());
		pageInfo.setMsg("Success");
		pageInfo.setObj(avatarUrls);

		return pageInfo;

	}

	@Override
	public PageInfo modifyNickName(String nickName, String key) throws Exception {
		PageInfo pageInfo = new PageInfo();

		String uid = "";
		try {
			verifyService.verifyEmpty(nickName, "昵称为空");
			// 从redis中获取用户id
			uid = utilService.queryUid(key);
		} catch (PageInfoException e) {
			pageInfo.setCode(e.getCode());
			pageInfo.setMsg(e.getMessage());
			return pageInfo;
		}

		// #start 更新昵称
		UsersProperties usersProperties = new UsersProperties();
		usersProperties.setNickName(nickName);

		UsersPropertiesExample ex = new UsersPropertiesExample();
		ex.createCriteria().andUIdEqualTo(uid);

		usersPropertiesMapper.updateByExampleSelective(usersProperties, ex);
        pageInfo.setCode(ResultInfo.SUCCESS.getCode());
		pageInfo.setMsg("昵称修改成功");
		return pageInfo;
		// #end
	}

	@Override
	public PageInfo sendVailEmail(String email, String key) throws Exception {
		PageInfo pageInfo = new PageInfo();

		String uid = "";
		try {
			verifyService.verifyEmail(email);
			// 从redis中获取用户id
			uid = utilService.queryUid(key);
		} catch (PageInfoException e) {
			pageInfo.setCode(e.getCode());
			pageInfo.setMsg(e.getMessage());
			return pageInfo;
		}

		// 邮件发送者信息
		String smtpHost = PropertiesUtil.getPropertiesByKey("config/email.properties", "smtpHost");
		String sender = PropertiesUtil.getPropertiesByKey("config/email.properties", "sender");
		String userName = PropertiesUtil.getPropertiesByKey("config/email.properties", "user");
		String pwd = PropertiesUtil.getPropertiesByKey("config/email.properties", "pwd");

		// 发送邮件

		// 将用户id，email地址存入redis key值为随机串 时间为10分钟
		String vailCode = Common.generateRandomCharArray(6);

		System.out.println("发送至用户[" + uid + "],邮箱[" + email + "]的邮件验证码为:" + vailCode);

		String ekey = AppCons.SEND_EMAIL_CODE + uid + "_" + email;
		redisSet.setRedisStringResult(ekey, vailCode.toUpperCase(), 10, TimeUnit.MINUTES);

		// data中存入要发送给用户的验证码
		Map<String, Object> dataMap = new HashMap<String, Object>();
		dataMap.put("key", vailCode);

		// 配置邮件模板
//		String templetId = PropertiesUtil.getPropertiesByKey("config/email.properties", "templetId");
		TempletExample tempExmp = new TempletExample();
		tempExmp.createCriteria().andStatusEqualTo(1);
		tempExmp.setOrderByClause("id desc");
		List<Templet> tempList = templetMapper.selectByExampleWithBLOBs(tempExmp);
		Templet temp = tempList.get(0);

		String res = FileProcessUtil.createFileReturnContent(dataMap, temp.getFile());
		if (StringUtils.isEmpty(res)) {
            pageInfo.setCode(ResultInfo.SERVER_ERROR.getCode());
			pageInfo.setMsg("生成邮件内容失败");
			return pageInfo;
		}

		if (!MailUtil.sendEmail(smtpHost, email, null, "领钱儿邮箱激活邮件通知", res, sender, "领钱儿(lingmoney.cn)",
				userName, pwd, null, null)) {
            pageInfo.setCode(ResultInfo.SERVER_ERROR.getCode());
			pageInfo.setMsg("邮件发送失败");
			return pageInfo;
		}

        pageInfo.setCode(ResultInfo.SUCCESS.getCode());
		pageInfo.setMsg("邮件发送成功");

		return pageInfo;
	}

	@Override
	public PageInfo setEmail(String email, String vailCode, String key) throws Exception {
		PageInfo pageInfo = new PageInfo();

		String uid = "";
		try {
			verifyService.verifyEmpty(vailCode, "验证码为空");
			verifyService.verifyEmail(email);
			uid = utilService.queryUid(key);
			verifyService.verifyRedisCode(AppCons.SEND_EMAIL_CODE + uid + "_" + email, vailCode.toUpperCase());

		} catch (PageInfoException e) {
			pageInfo.setCode(e.getCode());
			pageInfo.setMsg(e.getMessage());
			return pageInfo;
		}

		// #start 修改用户邮箱
		UsersProperties usersPro = new UsersProperties();
		usersPro.setEmail(email);
		UsersPropertiesExample ex = new UsersPropertiesExample();
		ex.createCriteria().andUIdEqualTo(uid);

		usersPropertiesMapper.updateByExampleSelective(usersPro, ex);
        pageInfo.setCode(ResultInfo.SUCCESS.getCode());
		pageInfo.setMsg("邮箱修改成功");

		return pageInfo;

	}

	@Override
	public PageInfo queryAddress(Integer page, Integer rows, String key) throws Exception {

		// 设置默认分页信息
		if (StringUtils.isEmpty(page)) {
			page = 1;
		}

		if (StringUtils.isEmpty(rows)) {
			rows = AppCons.DEFAULT_PAGE_SIZE;
		}

		PageInfo pageInfo = new PageInfo(page, rows);

		String uid = "";
		try {
			// 从redis中获取用户id
			uid = utilService.queryUid(key);

		} catch (PageInfoException e) {
			pageInfo.setCode(e.getCode());
			pageInfo.setMsg(e.getMessage());
			return pageInfo;
		}

		// 查询用户地址
		LingbaoAddressExample ex = new LingbaoAddressExample();
		ex.createCriteria().andUIdEqualTo(uid).andStatusEqualTo("1");
		ex.setOrderByClause("is_default DESC,create_time DESC");
		ex.setLimitStart(pageInfo.getFrom());
		ex.setLimitEnd(pageInfo.getSize());
		List<LingbaoAddress> addLi = lingbaoAddressMapper.selectByExample(ex);

		if (StringUtils.isEmpty(addLi) || addLi.isEmpty()) {
            pageInfo.setCode(ResultInfo.RETURN_DATA_EMPTY_DATA.getCode());
			pageInfo.setMsg("未查询到用户地址");
			return pageInfo;
		}

		long count = lingbaoAddressMapper.countByExample(ex);
        pageInfo.setCode(ResultInfo.SUCCESS.getCode());
		pageInfo.setMsg("查询用户地址列表成功");
		pageInfo.setRows(addLi);
		pageInfo.setTotal(new Long(count).intValue());

		return pageInfo;

	}

	@Override
	public PageInfo queryAddressDetail(Integer id, String key) throws Exception {
		PageInfo pageInfo = new PageInfo();

		try {
			String uid = utilService.queryUid(key);
			LingbaoAddress lingbaoAddress = verifyService.verifyAddressId(id, uid);
			pageInfo.setObj(lingbaoAddress);

		} catch (PageInfoException e) {
			pageInfo.setCode(e.getCode());
			pageInfo.setMsg(e.getMessage());
			return pageInfo;
		}

        pageInfo.setCode(ResultInfo.SUCCESS.getCode());
		pageInfo.setMsg("查询用户地址详情成功");

		return pageInfo;

	}

	@Override
	public PageInfo modifyAddress(Integer id, String name, String telephone, String province, String city, String town,
			String address, String key) throws Exception {
		PageInfo pageInfo = new PageInfo();

		LingbaoAddress lingbaoAddress;
		try {
			verifyService.verifyName(province, "所在省");
			verifyService.verifyName(city, "所在市");
			verifyService.verifyName(town, "所在区/县");
            if (!StringUtils.isEmpty(telephone) && !telephone.contains("*")) {
                verifyService.verifyTelephoneBase(telephone);
            }
			verifyService.verifyAddress(address);
			verifyService.verifyName(name, "收件人姓名");

			// 从redis中获取用户id
			String uid = utilService.queryUid(key);
			// 地址验证成功将实体返回
			lingbaoAddress = verifyService.verifyAddressId(id, uid);

		} catch (PageInfoException e) {
			pageInfo.setCode(e.getCode());
			pageInfo.setMsg(e.getMessage());
			return pageInfo;
		}

		lingbaoAddress.setId(null);
		lingbaoAddress.setName(name);
        if (!StringUtils.isEmpty(telephone) && !telephone.contains("*")) {
            lingbaoAddress.setTelephone(telephone);
        }
		lingbaoAddress.setProvince(province);
		lingbaoAddress.setCity(city);
		lingbaoAddress.setTown(town);
		lingbaoAddress.setAddress(address);

		// 1.添加一条新地址
		lingbaoAddressMapper.insertSelective(lingbaoAddress);

		// 2.将之前的地址状态改成已删除
		LingbaoAddress ling = new LingbaoAddress();
		ling.setId(id);
		ling.setStatus("0");
		ling.setIsDefault(0);
		lingbaoAddressMapper.updateByPrimaryKeySelective(ling);

        pageInfo.setCode(ResultInfo.SUCCESS.getCode());
		pageInfo.setMsg("修改成功");
		pageInfo.setObj(lingbaoAddress.getId());

		return pageInfo;

	}

	@Override
	public PageInfo addAddress(String name, String telephone, String province, String city, String town, String address,
			Integer isFirst, String key) throws Exception {
		PageInfo pageInfo = new PageInfo();

		String uid = "";
		try {
            if (city.equals("北京市") || city.equals("天津市") || city.equals("上海市") || city.equals("重庆市")) {
				province = city;
			}
			verifyService.verifyName(province, "所在省");
			verifyService.verifyName(city, "所在市");
			verifyService.verifyName(town, "所在区/县");
			verifyService.verifyTelephoneBase(telephone);
			verifyService.verifyAddress(address);
			verifyService.verifyName(name, "收件人姓名");
			verifyService.verifyStatus(isFirst);
			// 从redis中获取用户id
			uid = utilService.queryUid(key);

		} catch (PageInfoException e) {
			pageInfo.setCode(e.getCode());
			pageInfo.setMsg(e.getMessage());
			return pageInfo;
		}

		LingbaoAddress lingbaoAddress = new LingbaoAddress();
		lingbaoAddress.setName(name);
		lingbaoAddress.setTelephone(telephone);
		lingbaoAddress.setProvince(province);
		lingbaoAddress.setCity(city);
		lingbaoAddress.setTown(town);
		lingbaoAddress.setAddress(address);
		lingbaoAddress.setuId(uid);
		lingbaoAddress.setCreateTime(new Date());
		lingbaoAddress.setStatus("1");

		// 如果用户之前没有地址 则此条设置成默认地址
		lingbaoAddress.setIsDefault(isFirst);

		lingbaoAddressMapper.insertSelective(lingbaoAddress);

        pageInfo.setCode(ResultInfo.SUCCESS.getCode());
		pageInfo.setMsg("添加地址成功");
		pageInfo.setObj(lingbaoAddress.getId());

		return pageInfo;
	}

	@Override
	public PageInfo setDefaultAddress(Integer id, String key) throws Exception {
		PageInfo pageInfo = new PageInfo();

		String uid = "";
		try {
			// 从redis中获取用户id
			uid = utilService.queryUid(key);
			verifyService.verifyAddressId(id, uid);

		} catch (PageInfoException e) {
			pageInfo.setCode(e.getCode());
			pageInfo.setMsg(e.getMessage());
			return pageInfo;
		}

		LingbaoAddress lingbaoAddress = new LingbaoAddress();
		lingbaoAddress.setIsDefault(1);
		lingbaoAddress.setId(id);
		lingbaoAddressMapper.updateByPrimaryKeySelective(lingbaoAddress);

		// 设置此用户其他地址为不默认
		lingbaoAddress = new LingbaoAddress();
		LingbaoAddressExample ex = new LingbaoAddressExample();
		lingbaoAddress.setIsDefault(0);
		ex.createCriteria().andIdNotEqualTo(id).andUIdEqualTo(uid);
		lingbaoAddressMapper.updateByExampleSelective(lingbaoAddress, ex);

        pageInfo.setCode(ResultInfo.SUCCESS.getCode());
		pageInfo.setMsg("设置默认地址成功");

		return pageInfo;

	}

	@Override
	public PageInfo deleteAddress(Integer id, String key) throws Exception {
		PageInfo pageInfo = new PageInfo();

		String uid = "";
		try {
			// 从redis中获取用户id
			uid = utilService.queryUid(key);
			verifyService.verifyAddressId(id, uid);
		} catch (PageInfoException e) {
			pageInfo.setCode(e.getCode());
			pageInfo.setMsg(e.getMessage());
			return pageInfo;
		}

		LingbaoAddress lingbaoAddress = new LingbaoAddress();
		lingbaoAddress.setStatus("0");
		lingbaoAddress.setId(id);
		lingbaoAddressMapper.updateByPrimaryKeySelective(lingbaoAddress);

        pageInfo.setCode(ResultInfo.SUCCESS.getCode());
		pageInfo.setMsg("删除地址成功");

		return pageInfo;
	}

	@Override
	public PageInfo modifyPassword(String oldPassword, String password, String key) throws Exception {
		PageInfo pageInfo = new PageInfo();

		String id = "";
		try {
			// 从redis中获取用户id
			id = utilService.queryUid(key);
			verifyService.verifyPwd(password);

		} catch (PageInfoException e) {
			pageInfo.setCode(e.getCode());
			pageInfo.setMsg(e.getMessage());
			return pageInfo;
		}

		// 验证旧密码
		Users user = usersMapper.selectByPrimaryKey(id);
		String pwd = user.getLoginPsw();
		if (!MD5Utils.MD5(oldPassword).equals(pwd)) {
            pageInfo.setCode(ResultInfo.PASSWARD_ERROR.getCode());
			pageInfo.setMsg("密码错误");
			return pageInfo;
		}

		// 修改密码
		Users users = new Users();
		users.setLoginPsw(MD5Utils.MD5(password));
		users.setId(id);
		usersMapper.updateByPrimaryKeySelective(users);

        pageInfo.setCode(ResultInfo.SUCCESS.getCode());
		pageInfo.setMsg("修改密码成功");

		return pageInfo;
	}

	@Override
	public PageInfo retrievePassword(String telephone, String password, String verificationCode) throws Exception {
		PageInfo pageInfo = new PageInfo();

		// 验证参数
		try {
			verifyService.verifyPwd(password);
			verifyService.verifyTelephone(telephone, 1);
			verifyService.verifyEmpty(verificationCode, "验证码为空");

		} catch (PageInfoException e) {
			pageInfo.setCode(e.getCode());
			pageInfo.setMsg(e.getMessage());
			return pageInfo;
		}

		// 检测验证码
		String pkey = DateUtils.getDateStr(new Date(), DateUtils.sf) + "_" + telephone;

		if (!smsService.validateCode(pkey, verificationCode)) {
            pageInfo.setCode(ResultInfo.VERIFICATION_CODE_ERROR.getCode());
			pageInfo.setMsg("验证码错误或失效");
			return pageInfo;
		}

		// 修改密码
		UsersExample userEx = new UsersExample();
		userEx.createCriteria().andTelephoneEqualTo(telephone);

		Users users = new Users();
		users.setLoginPsw(MD5Utils.MD5(password));

		if (usersMapper.updateByExampleSelective(users, userEx) == 0) {
            pageInfo.setCode(ResultInfo.ACCOUNT_NUMBER_NON_EXIST.getCode());
			pageInfo.setMsg("非当前用户绑定手机号");
			return pageInfo;
		}

        pageInfo.setCode(ResultInfo.SUCCESS.getCode());
		pageInfo.setMsg("修改密码成功");

		return pageInfo;
	}

	@Override
	public PageInfo modifyTelephone(String oldTelephone, String oldVerificationCode, String password, String telephone,
			String verificationCode, String key) throws Exception {
		PageInfo pageInfo = new PageInfo();

		// 验证参数
		String uid = "";
		try {
			verifyService.verifyEmpty(password, "密码为空");
			uid = utilService.queryUid(key);
			verifyService.verifyTelephoneAndUid(oldTelephone, uid);
			verifyService.verifyMsgCode(oldTelephone, oldVerificationCode);
			verifyService.verifyTelephone(telephone, 0);
			verifyService.verifyMsgCode(telephone, verificationCode);

		} catch (PageInfoException pe) {
			pageInfo.setCode(pe.getCode());
			pageInfo.setMsg(pe.getMessage());
			return pageInfo;
		}

		// 对比密码
		Users user = usersMapper.selectByPrimaryKey(uid);
		String pwd = user.getLoginPsw();
		if (!pwd.equals(MD5Utils.MD5(password))) {
            pageInfo.setCode(ResultInfo.PASSWARD_ERROR.getCode());
			pageInfo.setMsg("密码错误");
			return pageInfo;
		}

		String acc = user.getLoginAccount();
		String tele = user.getTelephone();

		user = new Users();
		if (tele.equals(acc)) {
			// 如果账号也为手机号 则账号也修改掉
			user.setLoginAccount(telephone);
		}
		user.setId(uid);
		user.setTelephone(telephone);

		usersMapper.updateByPrimaryKeySelective(user);

        pageInfo.setCode(ResultInfo.SUCCESS.getCode());
		pageInfo.setMsg("修改手机号码成功");

		return pageInfo;
	}

	@Override
	public PageInfo setDefaultBinkCard(Integer id, String uId) throws Exception {
		PageInfo pageInfo = new PageInfo();
	 
		try {
			verifyService.verifyEmpty(id, "银行卡id为空");
		} catch (PageInfoException pe) {
			pageInfo.setCode(pe.getCode());
			pageInfo.setMsg(pe.getMessage());
			return pageInfo;
		}

		// 查询银行卡id是否存在
		UsersBankExample ex = new UsersBankExample();
		ex.createCriteria().andIdEqualTo(id).andUIdEqualTo(uId);
		if (usersBankMapper.countByExample(ex) == 0) {
            pageInfo.setCode(ResultInfo.SUBMIT_PARAMS_ERROR.getCode());
			pageInfo.setMsg("当前用户下无此银行卡");
			return pageInfo;
		}
		UsersBank usersBank = new UsersBank();
		// 设置其他银行卡为0
		ex.clear();
		ex.createCriteria().andUIdEqualTo(uId).andIdNotEqualTo(id);
		usersBank.setIsdefault("0");
		usersBankMapper.updateByExampleSelective(usersBank, ex);

		// 设置当前银行卡为1
		usersBank.setId(id);
		usersBank.setIsdefault("1");
		usersBankMapper.updateByPrimaryKeySelective(usersBank);
        pageInfo.setCode(ResultInfo.SUCCESS.getCode());
		pageInfo.setMsg("设置成功");
		return pageInfo;
	}

	@Override
	public boolean validUserPassword(String uid, String oldPsw) throws Exception {
		Users user = usersMapper.selectByPrimaryKey(uid);
		if (user.getLoginPsw().equalsIgnoreCase(MD5Utils.MD5(oldPsw))) {
			return true;
		}
		return false;
	}

	@Override
	public PageInfo queryUserAccountByUid(String uid) throws Exception {
		PageInfo pi = new PageInfo();
		UsersAccount account = usersAccountMapper.selectByUid(uid);
		if (account == null) {
            pi.setCode(ResultInfo.SUBMIT_PARAMS_ERROR.getCode());
			pi.setMsg("查无此账户");

		} else {
            pi.setCode(ResultInfo.SUCCESS.getCode());
			pi.setMsg("success");
			pi.setObj(account);
		}

		return pi;
	}

	@Override
	public PageInfo modifyUsersInfo(UsersProperties usersProperties, String key) throws Exception {
		PageInfo pageInfo = new PageInfo();
		String uid = "";
		try {
			// 从redis中获取用户id
			uid = utilService.queryUid(key);
			verifyService.verifyUserInfo(usersProperties.getSex(), "性别");
			verifyService.verifyUserInfo(usersProperties.getEducation(), "教育程度");
			verifyService.verifyJob(usersProperties.getJob());
			usersProperties.setWechat(verifyService.verifyWechat(usersProperties.getWechat()));
			usersProperties.setEmail(verifyService.verifyEmailNoRepeat(usersProperties.getEmail(), uid));
			verifyService.verifyNickName(usersProperties.getNickName());

		} catch (PageInfoException e) {
			pageInfo.setCode(e.getCode());
			pageInfo.setMsg(e.getMessage());
			return pageInfo;
		}

		UsersPropertiesExample ex = new UsersPropertiesExample();
		ex.createCriteria().andUIdEqualTo(uid);
		usersPropertiesMapper.updateByExampleSelective(usersProperties, ex);

        pageInfo.setCode(ResultInfo.SUCCESS.getCode());
		pageInfo.setMsg("修改成功");

		return pageInfo;
	}

	@Override
	public PageInfo queryRecomUsersByUid(String key) throws Exception {
		PageInfo pageInfo = new PageInfo();
		// #start 验证参数
		String uId = "";
		try {
			// 从redis中获取用户id
			uId = utilService.queryUid(key);
		} catch (PageInfoException e) {
			pageInfo.setCode(e.getCode());
			pageInfo.setMsg(e.getMessage());
			return pageInfo;
		}
		List<Map<String, Object>> recomUserList = usersPropertiesMapper.queryRecomUsersByUid(uId);
		if (recomUserList == null || recomUserList.size() < 1) {
            pageInfo.setCode(ResultInfo.RETURN_DATA_EMPTY_DATA.getCode());
			pageInfo.setMsg("暂无我推荐的人");
		} else {
            pageInfo.setCode(ResultInfo.SUCCESS.getCode());
			pageInfo.setRows(recomUserList);
			pageInfo.setTotal(recomUserList.size());
		}
		return pageInfo;
	}

	@Override
	public PageInfo queryMyrecommender(String key) throws Exception {
		PageInfo pageInfo = new PageInfo();
		// #start 验证参数
		String uId = "";
		try {
			// 从redis中获取用户id
			uId = utilService.queryUid(key);
		} catch (PageInfoException e) {
			pageInfo.setCode(e.getCode());
			pageInfo.setMsg(e.getMessage());
			return pageInfo;
		}
		Map<String, Object> recomder = usersPropertiesMapper.queryMyrecommender(uId);
		if (recomder == null) {
            pageInfo.setCode(ResultInfo.RETURN_DATA_EMPTY_DATA.getCode());
			pageInfo.setMsg("暂无推荐我的人");
		} else {
            pageInfo.setCode(ResultInfo.SUCCESS.getCode());
			pageInfo.setObj(recomder);
		}
		return pageInfo;
	}
}
