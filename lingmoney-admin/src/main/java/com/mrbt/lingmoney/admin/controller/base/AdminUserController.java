package com.mrbt.lingmoney.admin.controller.base;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mrbt.lingmoney.admin.service.base.AdminUserService;
import com.mrbt.lingmoney.model.AdminUser;
import com.mrbt.lingmoney.utils.AppCons;
import com.mrbt.lingmoney.utils.MD5Utils;
import com.mrbt.lingmoney.utils.MyUtils;
import com.mrbt.lingmoney.utils.PageInfo;
import com.mrbt.lingmoney.utils.ResultParame.ResultInfo;

/**
 * 用户
 * @author lihq
 * @date 2017年5月3日 下午3:48:17
 * @description
 * @version 1.0
 * @since 2017-04-12
 */
@Controller
@RequestMapping("/base/user")
public class AdminUserController {
	
	private Logger log = MyUtils.getLogger(AdminUserController.class);
	
	@Autowired
	private AdminUserService adminUserService;

	/**
	 * 修改状态
	 * 
	 * @param id
	 *            主键
	 * @return 分页实体类
	 */
	@RequestMapping("changeStatus")
	@ResponseBody
	public Object changeStatus(Integer id) {
		log.info("/base/user/changeStatus");
		PageInfo pageInfo = new PageInfo();
		try {
			boolean falg = adminUserService.changeStatus(id);
			if (falg) {
				pageInfo.setCode(ResultInfo.SUCCESS.getCode());
				pageInfo.setMsg(ResultInfo.SUCCESS.getMsg());
			} else {
				pageInfo.setCode(ResultInfo.NO_DATA.getCode());
				pageInfo.setMsg(ResultInfo.NO_DATA.getMsg());
			}
		} catch (Exception e) {
			log.error(e.getMessage());
			e.printStackTrace();
			pageInfo.setCode(ResultInfo.SERVER_ERROR.getCode());
			pageInfo.setMsg(ResultInfo.SERVER_ERROR.getMsg());
		}
		return pageInfo;
	}

	/**
	 * 分页查询
	 * @param vo	adminUser对象
	 * @param page	页数	
	 * @param rows	条数
	 * @return	返回查询列表
	 */
	@RequestMapping("list")
	public @ResponseBody Object list(AdminUser vo, Integer page, Integer rows) {
		log.info("/base/user/list");
		PageInfo pageInfo = new PageInfo(page, rows);
		try {
			if (vo != null) {
				pageInfo = adminUserService.list(vo, pageInfo);
				pageInfo.setCode(ResultInfo.SUCCESS.getCode());
				pageInfo.setMsg(ResultInfo.SUCCESS.getMsg());
			} else {
				pageInfo.setCode(ResultInfo.NO_DATA.getCode());
				pageInfo.setMsg(ResultInfo.NO_DATA.getMsg());
			}
		} catch (Exception e) {
			log.error(e.getMessage());
			e.printStackTrace();
			pageInfo.setCode(ResultInfo.SERVER_ERROR.getCode());
			pageInfo.setMsg(ResultInfo.SERVER_ERROR.getMsg());
		}
		return pageInfo;
	}

	/**
	 * 保存
	 * @param vo	adminUser对象
	 * @return	返回
	 */
	@RequestMapping("save")
	public @ResponseBody Object save(AdminUser vo) {
		log.info("/base/user/save");
		PageInfo pageInfo = new PageInfo();
		try {
			if (vo != null) {
				vo.setLoginPsw(MD5Utils.MD5(vo.getLoginPsw()));
				vo.setState(AppCons.DEFAULT_STATE);
				int exist = adminUserService.listCount(vo, 0);
				if (exist > 0) {
					pageInfo.setCode(ResultInfo.PARAMETER_ERROR.getCode());
					pageInfo.setMsg("登录名已经存在，请重新设置");
				} else {
					adminUserService.save(vo);
					pageInfo.setCode(ResultInfo.SUCCESS.getCode());
					pageInfo.setMsg(ResultInfo.SUCCESS.getMsg());
				}
			} else {
				pageInfo.setCode(ResultInfo.NO_DATA.getCode());
				pageInfo.setMsg(ResultInfo.NO_DATA.getMsg());
			}
		} catch (Exception e) {
			log.error(e.getMessage());
			e.printStackTrace();
			pageInfo.setCode(ResultInfo.SERVER_ERROR.getCode());
			pageInfo.setMsg(ResultInfo.SERVER_ERROR.getMsg());
		}
		return pageInfo;
	}

	/**
	 * 更新
	 * 
	 * @param vo adminUser对象
	 * @return 返回结果
	 */
	@RequestMapping("update")
	public @ResponseBody Object update(AdminUser vo) {
		log.info("/base/user/update");
		PageInfo pageInfo = new PageInfo();
		try {
			if (vo != null) {
				int exist = adminUserService.listCount(vo, 1);
				if (exist > 0) {
					pageInfo.setCode(ResultInfo.PARAMETER_ERROR.getCode());
					pageInfo.setMsg("登录名已经存在，请重新设置");
				} else {
					adminUserService.update(vo);
					pageInfo.setCode(ResultInfo.SUCCESS.getCode());
					pageInfo.setMsg(ResultInfo.SUCCESS.getMsg());
				}
			} else {
				pageInfo.setCode(ResultInfo.NO_DATA.getCode());
				pageInfo.setMsg(ResultInfo.NO_DATA.getMsg());
			}
		} catch (Exception e) {
			log.error(e.getMessage());
			e.printStackTrace();
			pageInfo.setCode(ResultInfo.SERVER_ERROR.getCode());
			pageInfo.setMsg(ResultInfo.SERVER_ERROR.getMsg());
		}
		return pageInfo;
	}

	/**
	 * 初始化用户密码
	 * @param id	用户ID 
	 * @return	返回结果
	 */
	@RequestMapping("updateUserPswByInit")
	@ResponseBody
	public Object updateUserPswByInit(Integer id) {
		log.info("/base/user/updateUserPswByInit");
		PageInfo pageInfo = new PageInfo();
		try {
			if (id != null && id > 0) {
				AdminUser vo = new AdminUser();
				vo.setId(id);
				vo.setLoginPsw(MD5Utils.MD5(AppCons.DEFAULT_PSW));
				adminUserService.update(vo);
				pageInfo.setCode(ResultInfo.SUCCESS.getCode());
				pageInfo.setMsg(ResultInfo.SUCCESS.getMsg());
			} else {
				pageInfo.setCode(ResultInfo.NO_DATA.getCode());
				pageInfo.setMsg(ResultInfo.NO_DATA.getMsg());
			}
		} catch (Exception e) {
			log.error(e.getMessage());
			e.printStackTrace();
			pageInfo.setCode(ResultInfo.SERVER_ERROR.getCode());
			pageInfo.setMsg(ResultInfo.SERVER_ERROR.getMsg());
		}
		return pageInfo;
	}
}
