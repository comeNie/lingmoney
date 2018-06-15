package com.mrbt.lingmoney.admin.controller.base;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mrbt.lingmoney.admin.service.base.AdminRoleMenuService;
import com.mrbt.lingmoney.admin.service.base.AdminRoleService;
import com.mrbt.lingmoney.model.AdminRole;
import com.mrbt.lingmoney.utils.AppCons;
import com.mrbt.lingmoney.utils.MyUtils;
import com.mrbt.lingmoney.utils.PageInfo;
import com.mrbt.lingmoney.utils.ResultParame.ResultInfo;

/**
 * 权限
 * 
 * @author lihq
 * @date 2017年5月3日 下午5:14:47
 * @description
 * @version 1.0
 * @since 2017-04-12
 */
@Controller
@RequestMapping("/base/role")
public class AdminRoleController {
	
	private Logger log = MyUtils.getLogger(AdminRoleController.class);
	
	@Autowired
	private AdminRoleService adminRoleService;
	
	@Autowired
	private AdminRoleMenuService adminRoleMenuService;

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
		log.info("/base/role/changeStatus");
		PageInfo pageInfo = new PageInfo();
		try {
			boolean falg = adminRoleService.changeStatus(id);
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
	 * 查询
	 * 
	 * @param vo	AdminRole对象
	 * @param page	页数
	 * @param rows	条数
	 * @param limit	不知道做什么的
	 * @return	返回列表
	 */
	@RequestMapping("list")
	public @ResponseBody Object list(AdminRole vo, Integer page, Integer rows, Integer limit) {
		log.info("/base/role/list");
		PageInfo pageInfo = new PageInfo(page, rows);
		try {
			if (vo != null) {
				pageInfo = adminRoleService.list(vo, pageInfo);
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
	 * @param vo	adminRole对象
	 * @return	返回结果
	 */
	@RequestMapping("save")
	public @ResponseBody Object save(AdminRole vo) {
		log.info("/base/role/save");
		PageInfo pageInfo = new PageInfo();
		try {
			if (vo != null) {
				vo.setState(AppCons.DEFAULT_STATE);
				adminRoleService.save(vo);
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
	 * 更新
	 * @param vo	adminRole对象
	 * @return	返回结果
	 */
	@RequestMapping("update")
	public @ResponseBody Object update(AdminRole vo) {
		log.info("/base/role/update");
		PageInfo pageInfo = new PageInfo();
		try {
			if (vo != null) {
				adminRoleService.update(vo);
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
	 * 修改角色权限
	 * @param roleId	角色ID	
	 * @param menuIds	菜单ID
	 * @return	返回处理结果
	 */
	@RequestMapping("setRoleMenu")
	public @ResponseBody Object setRoleMenu(Integer roleId, String menuIds) {
		log.info("/base/role/setRoleMenu");
		PageInfo pageInfo = new PageInfo();
		try {
			if (roleId != null && roleId > 0) {
				adminRoleMenuService.update(roleId, menuIds);
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
