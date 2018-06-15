package com.mrbt.lingmoney.admin.controller.userinfo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mrbt.lingmoney.admin.service.userinfo.UserInfoService;
import com.mrbt.lingmoney.utils.PageInfo;
import com.mrbt.lingmoney.utils.ResultParame.ResultInfo;

/** 
 * @author  suyibo 
 * @date 创建时间：2018年4月10日
 */
@Controller
@RequestMapping("/userInfo")
public class UserInfoController {

	@Autowired
	private UserInfoService userInfoService;

	/**
	 * 查询用户信息
	 * 
	 * @param uid 用户id
	 * @param telephone 电话
	 * @param name 姓名
	 * @return 用户信息
	 */
	@RequestMapping("findUserInfoByParams")
	@ResponseBody
	public Object findUserInfoByParams(String uid, String telephone, String name) {
		PageInfo pageInfo = new PageInfo();
		try {
			pageInfo = userInfoService.findUserInfoByParams(uid, telephone, name);
		} catch (Exception e) {
			e.printStackTrace();
			pageInfo.setCode(ResultInfo.SERVER_ERROR.getCode());
			pageInfo.setMsg(ResultInfo.SERVER_ERROR.getMsg());
		}
		return pageInfo;
	}
	
	/**
	 * 查询用户账户流水
	 * 
	 * @param aid 用户账户id
	 * @return 账户流水信息
	 */
	@RequestMapping("findAccountFlowByAId")
	@ResponseBody
	public Object findAccountFlowByAId(Integer aid) {
		PageInfo pageInfo = new PageInfo();
		try {
			pageInfo = userInfoService.findAccountFlowByAId(aid);
		} catch (Exception e) {
			e.printStackTrace();
			pageInfo.setCode(ResultInfo.SERVER_ERROR.getCode());
			pageInfo.setMsg(ResultInfo.SERVER_ERROR.getMsg());
		}
		return pageInfo;
	}
}
