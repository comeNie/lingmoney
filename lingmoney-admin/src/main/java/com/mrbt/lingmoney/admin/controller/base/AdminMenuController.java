package com.mrbt.lingmoney.admin.controller.base;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mrbt.lingmoney.admin.service.base.AdminMenuService;
import com.mrbt.lingmoney.model.AdminMenu;
import com.mrbt.lingmoney.utils.AppCons;
import com.mrbt.lingmoney.utils.MenuTree;
import com.mrbt.lingmoney.utils.MyUtils;
import com.mrbt.lingmoney.utils.PageInfo;
import com.mrbt.lingmoney.utils.ResultParame.ResultInfo;
import com.mrbt.lingmoney.utils.session.UserSession;

/**
 * 菜单权限
 * 
 * @author lihq
 * @date 2017年5月3日 下午3:16:07
 * @description
 * @version 1.0
 * @since 2017-04-12
 */
@Controller
@RequestMapping("base/menu")
public class AdminMenuController {
	
	private Logger log = MyUtils.getLogger(AdminMenuController.class);
	
	@Autowired
	private AdminMenuService adminMenuService;

	/**
	 * 修改状态
	 * 
	 * @param id
	 *            主键
	 * @return 分页实体类
	 */
	@RequestMapping("changeStatus")
	public @ResponseBody Object changeStatus(Integer id) {
		log.info("/base/menu/changeStatus");
		PageInfo pageInfo = new PageInfo();
		try {
			boolean falg = adminMenuService.changeStatus(id);
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
	 * 从session中取菜单
	 * @param session session
	 * @return 返回菜单
	 */
	@RequestMapping("listMenuBySession")
	@ResponseBody
	public Object listMenuBySession(HttpSession session) {
		log.info("/base/menu/listMenuBySession");
		return ((UserSession) session.getAttribute(AppCons.SESSION_USER)).getMenuMap();
	}

	/**
	 * 查找所有tree
	 * @return 返回菜单
	 */
	@RequestMapping("listTree")
	public @ResponseBody List<MenuTree> listTree() {
		log.info("/base/menu/listTree");
		List<MenuTree> reList = new ArrayList<MenuTree>();
		reList.add(adminMenuService.listAllTree());
		return reList;
	}

	/**
	 * 根据pid查找所有子类
	 * @param pid	上级菜单ID
	 * @param page	页数
	 * @param rows	条数
	 * @return	返回数据
	 */
	@RequestMapping("listByPid")
	public @ResponseBody Object listByPid(Integer pid, Integer page, Integer rows) {
		log.info("/base/menu/listByPid");
		PageInfo pageInfo = new PageInfo(page, rows);
		try {
			if (pid == null) {
				pid = 1;
			}
			if (pid != null && pid > 0) {
				pageInfo = adminMenuService.listByPid(pid, pageInfo);
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
	 * @param vo adminMenu对象
	 * @return	返回处理结果
	 */
	@RequestMapping("save")
	public @ResponseBody Object save(AdminMenu vo) {
		log.info("保持菜单");
		PageInfo pageInfo = new PageInfo();
		try {
			if (vo != null) {
				vo.setState(AppCons.DEFAULT_STATE);
				adminMenuService.save(vo);
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
	 * @param vo AdminMenu对象
	 * @return	处理结果
	 */
	@RequestMapping("update")
	@ResponseBody
	public Object update(AdminMenu vo) {
		log.info("/base/menu/update");
		PageInfo pageInfo = new PageInfo();
		try {
			if (vo != null) {
				adminMenuService.update(vo);
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
