package com.mrbt.lingmoney.admin.controller.gift;

import java.util.Date;

import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mrbt.lingmoney.admin.controller.base.BaseController;
import com.mrbt.lingmoney.admin.service.gift.LingbaoSignService;
import com.mrbt.lingmoney.model.AdminUser;
import com.mrbt.lingmoney.model.LingbaoSign;
import com.mrbt.lingmoney.model.LingbaoSignExample;
import com.mrbt.lingmoney.utils.AppCons;
import com.mrbt.lingmoney.utils.MyUtils;
import com.mrbt.lingmoney.utils.PageInfo;
import com.mrbt.lingmoney.utils.ResultParame.ResultInfo;
import com.mrbt.lingmoney.utils.session.UserSession;

/**
 * 我的领地签到属性
 * 
 * @author lhq
 *
 */
@Controller
@RequestMapping("/gift/lingbaoSign")
public class LingbaoSignController extends BaseController {

	private Logger log = MyUtils.getLogger(LingbaoSignController.class);

	@Autowired
	private LingbaoSignService lingbaoSignService;

	/**
	 * 添加或修改我的领地签到属性
	 * 
	 * @param vo 我的领地签到属性
	 * @param session session
	 * @return 分页实体类
	 */
	@RequestMapping("saveAndUpdate")
	@ResponseBody
	public Object saveAndUpdate(LingbaoSign vo, HttpSession session) {
		PageInfo pageInfo = new PageInfo();
		try {
			UserSession userSession = (UserSession) session
					.getAttribute(AppCons.SESSION_USER);
			if (userSession == null) {
				pageInfo.setCode(ResultInfo.ADMIN_LOGO_OUTTIME.getCode());
				pageInfo.setMsg(ResultInfo.ADMIN_LOGO_OUTTIME.getMsg());
				return pageInfo;
			}
			AdminUser adminUser = userSession.getUser();
			if (adminUser == null) {
				pageInfo.setCode(ResultInfo.ADMIN_LOGO_OUTTIME.getCode());
				pageInfo.setMsg(ResultInfo.ADMIN_LOGO_OUTTIME.getMsg());
				return pageInfo;
			}
			vo.setOperateName(adminUser.getName());
			vo.setOperateTime(new Date());
			if (vo.getId() == null || vo.getId() <= 0) {
				lingbaoSignService.save(vo);
			} else {
				lingbaoSignService.update(vo);
			}
			pageInfo.setCode(ResultInfo.SUCCESS.getCode());
			pageInfo.setMsg(ResultInfo.SUCCESS.getMsg());
		} catch (Exception e) {
			e.printStackTrace();
			log.error("我的领地签到属性，添加或修改我的领地签到属性，失败原因是：" + e);
			pageInfo.setCode(ResultInfo.SERVER_ERROR.getCode());
			pageInfo.setMsg(ResultInfo.SERVER_ERROR.getMsg());
		}
		return pageInfo;
	}

	/**
	 * 查询我的领地签到属性列表
	 * 
	 * @param vo 我的领地签到属性
	 * @param page 当前页
	 * @param rows  每页显示的条数
	 * @return 分页实体类
	 */
	@RequestMapping("list")
	@ResponseBody
	public Object list(LingbaoSign vo, Integer page, Integer rows) {
		PageInfo pageInfo = new PageInfo(page, rows);
		try {
			LingbaoSignExample example = new LingbaoSignExample();
			if (page != null && rows != null) {
				example.setLimitStart(pageInfo.getFrom());
				example.setLimitEnd(pageInfo.getSize());
			}
			pageInfo = lingbaoSignService.getList(example);
		} catch (Exception e) {
			e.printStackTrace();
			log.error("我的领地签到属性，查询我的领地签到属性列表，失败原因是：" + e);
			pageInfo.setCode(ResultInfo.SERVER_ERROR.getCode());
			pageInfo.setMsg(ResultInfo.SERVER_ERROR.getMsg());
		}
		return pageInfo;
	}

	/**
	 * 删除我的领地签到属性
	 * 
	 * @param id
	 *            主键
	 * @return 分页实体类
	 */
	@RequestMapping("delete")
	@ResponseBody
	public Object delete(Integer id) {
		PageInfo pageInfo = new PageInfo();
		try {
			if (id != null && id > 0) {
				lingbaoSignService.delete(id);
				pageInfo.setCode(ResultInfo.SUCCESS.getCode());
				pageInfo.setMsg(ResultInfo.SUCCESS.getMsg());
			} else {
				pageInfo.setCode(ResultInfo.PARAMETER_ERROR.getCode());
				pageInfo.setMsg(ResultInfo.PARAMETER_ERROR.getMsg());
			}
		} catch (Exception e) {
			e.printStackTrace();
			log.error("我的领地签到属性，删除我的领地签到属性，失败原因是：" + e);
			pageInfo.setCode(ResultInfo.SERVER_ERROR.getCode());
			pageInfo.setMsg(ResultInfo.SERVER_ERROR.getMsg());
		}
		return pageInfo;
	}

	/**
	 * 设置购物车限制个数
	 * 
	 * @param cartCount
	 *            数量
	 * @return 分页实体类
	 */
	@RequestMapping("setCartCount")
	@ResponseBody
	public Object setCartCount(Integer cartCount) {
		PageInfo pageInfo = new PageInfo();
		try {
			if (cartCount != null && cartCount > 0) {
				lingbaoSignService.setCartCount(cartCount);
				pageInfo.setCode(ResultInfo.SUCCESS.getCode());
				pageInfo.setMsg(ResultInfo.SUCCESS.getMsg());
			} else {
				pageInfo.setCode(ResultInfo.PARAMETER_ERROR.getCode());
				pageInfo.setMsg(ResultInfo.PARAMETER_ERROR.getMsg());
			}
		} catch (Exception e) {
			e.printStackTrace();
			log.error("我的领地签到属性，设置购物车限制个数，失败原因是：" + e);
			pageInfo.setCode(ResultInfo.SERVER_ERROR.getCode());
			pageInfo.setMsg(ResultInfo.SERVER_ERROR.getMsg());
		}
		return pageInfo;
	}

	/**
	 * 后台展示购物车限制个数
	 * 
	 * @return 分页实体类
	 */
	@RequestMapping("showCartCount")
	@ResponseBody
	public Object showCartCount() {
		PageInfo pageInfo = new PageInfo();
		try {
			pageInfo = lingbaoSignService.showCartCount();
			pageInfo.setCode(ResultInfo.SUCCESS.getCode());
			pageInfo.setMsg(ResultInfo.SUCCESS.getMsg());
		} catch (Exception e) {
			e.printStackTrace();
			log.error("我的领地签到属性，后台展示购物车限制个数，失败原因是：" + e);
			pageInfo.setCode(ResultInfo.SERVER_ERROR.getCode());
			pageInfo.setMsg(ResultInfo.SERVER_ERROR.getMsg());
		}
		return pageInfo;
	}

}
