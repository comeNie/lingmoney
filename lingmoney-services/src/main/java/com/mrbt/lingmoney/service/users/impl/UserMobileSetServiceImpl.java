package com.mrbt.lingmoney.service.users.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.mrbt.lingmoney.commons.exception.PageInfoException;
import com.mrbt.lingmoney.mapper.UsersMobilePropertiesMapper;
import com.mrbt.lingmoney.model.UsersMobileProperties;
import com.mrbt.lingmoney.service.common.UtilService;
import com.mrbt.lingmoney.service.users.UserMobileSetService;
import com.mrbt.lingmoney.service.users.VerifyService;
import com.mrbt.lingmoney.utils.PageInfo;
import com.mrbt.lingmoney.utils.ResultParame.ResultInfo;

/**
 * 用户移动端设置
 *
 */
@Service
public class UserMobileSetServiceImpl implements UserMobileSetService {
	@Autowired
	private UsersMobilePropertiesMapper usersMobilePropMapper;
	@Autowired
	private VerifyService verifyService;
	@Autowired
	private UtilService utilService;

	@Override
	public PageInfo queryUserMobileProp(String key) throws Exception {
		PageInfo pageInfo = new PageInfo();

		// #start 取出用户uid
		String uid = "";
		try {
			uid = utilService.queryUid(key);
		} catch (PageInfoException e) {
			pageInfo.setCode(e.getCode());
			pageInfo.setMsg(e.getMessage());
			return pageInfo;
		}
		// #end

		UsersMobileProperties usersMobileProp = usersMobilePropMapper.selectByPrimaryKey(uid);
		if (StringUtils.isEmpty(usersMobileProp)) {
			usersMobileProp = new UsersMobileProperties();
			usersMobileProp.setIsUseGesture(0);
			usersMobileProp.setIsUsePush(1);
			usersMobileProp.setGesturePwd("0");
		}
		usersMobileProp.setuId(null);
        pageInfo.setCode(ResultInfo.SUCCESS.getCode());
		pageInfo.setMsg("查询用户移动端属性成功");
		pageInfo.setObj(usersMobileProp);
		return pageInfo;
	}

	@Override
	public PageInfo modifyGesturePwd(String gesturePwd, String key) throws Exception {
		PageInfo pageInfo = new PageInfo();
		// #start 验证参数
		String uid = "";
		try {
			uid = utilService.queryUid(key);
			verifyService.verifyEmpty(gesturePwd, "手势密码为空");
		} catch (PageInfoException e) {
			pageInfo.setCode(e.getCode());
			pageInfo.setMsg(e.getMessage());
			return pageInfo;
		}
		// #end
		UsersMobileProperties usersMobileProp = new UsersMobileProperties();
		usersMobileProp.setGesturePwd(gesturePwd);
		usersMobileProp.setuId(uid);
		if (usersMobilePropMapper.updateByPrimaryKeySelective(usersMobileProp) == 0) {
			// 如果更改记录为0 则证明无此uid 此时插入数据
			usersMobileProp.setIsUseGesture(1);
			usersMobileProp.setIsUsePush(1);
			usersMobilePropMapper.insertSelective(usersMobileProp);
		}
        pageInfo.setCode(ResultInfo.SUCCESS.getCode());
		pageInfo.setMsg("修改手势密码成功");
		return pageInfo;
	}

	@Override
	public PageInfo modifyGestureStatus(Integer status, String key) throws Exception {
		PageInfo pageInfo = new PageInfo();
		// #start 验证参数
		String uid = "";
		try {
			uid = utilService.queryUid(key);
			verifyService.verifyStatus(status);
		} catch (PageInfoException e) {
			pageInfo.setCode(e.getCode());
			pageInfo.setMsg(e.getMessage());
			return pageInfo;
		}
		// #end

		UsersMobileProperties usersMobileProp = new UsersMobileProperties();
		usersMobileProp.setIsUseGesture(status);
		usersMobileProp.setuId(uid);

		if (usersMobilePropMapper.updateByPrimaryKeySelective(usersMobileProp) == 0) {
			// 如果更改记录为0 则证明无此uid 此时插入数据
			usersMobileProp.setIsUseGesture(0);
			usersMobileProp.setIsUsePush(1);
			usersMobilePropMapper.insertSelective(usersMobileProp);
		}
        pageInfo.setCode(ResultInfo.SUCCESS.getCode());
		pageInfo.setMsg((status.equals(0) ? "关闭" : "开启") + "手势密码成功");
		return pageInfo;
	}

	@Override
	public PageInfo modifyPushStatus(Integer status, String key) throws Exception {
		PageInfo pageInfo = new PageInfo();
		// #start 验证参数
		String uid = "";
		try {
			uid = utilService.queryUid(key);
			verifyService.verifyStatus(status);
		} catch (PageInfoException e) {
			pageInfo.setCode(e.getCode());
			pageInfo.setMsg(e.getMessage());
			return pageInfo;
		}
		// #end

		UsersMobileProperties usersMobileProp = new UsersMobileProperties();
		usersMobileProp.setIsUsePush(status);
		usersMobileProp.setuId(uid);

		if (usersMobilePropMapper.updateByPrimaryKeySelective(usersMobileProp) == 0) {
			// 如果更改记录为0 则证明无此uid 此时插入数据
			usersMobileProp.setIsUseGesture(0);
			usersMobileProp.setIsUsePush(1);
			usersMobilePropMapper.insertSelective(usersMobileProp);
		}
        pageInfo.setCode(ResultInfo.SUCCESS.getCode());
		pageInfo.setMsg((status.equals(0) ? "关闭" : "开启") + "推送成功");
		return pageInfo;
	}
}
