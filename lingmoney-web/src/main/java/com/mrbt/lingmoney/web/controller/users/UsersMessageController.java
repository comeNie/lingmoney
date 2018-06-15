package com.mrbt.lingmoney.web.controller.users;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;

import com.mrbt.lingmoney.service.users.UsersMessageService;
import com.mrbt.lingmoney.service.users.UsersService;
import com.mrbt.lingmoney.utils.ResultParame;
import com.mrbt.lingmoney.web.controller.common.CommonMethodUtil;

/**
 * 站内信
 * 
 * @author yiban 2017年10月19日 上午9:48:48
 *
 */

@Controller
@RequestMapping("/usersMessage")
public class UsersMessageController {
	private static final Logger LOG = LogManager.getLogger(UsersMessageController.class);
	@Autowired
	private UsersMessageService usersMessageService;
	@Autowired
	private UsersService usersService;

	/**
	 * 站内信列表
	 * 
	 * @param model
	 *            数据包装
	 * @param pageNo
	 *            当前页数
	 * @param name
	 *            名称
	 * @param request
	 *            请求
	 * @return 返回信息
	 */
	@RequestMapping("/list")
	public String list(ModelMap model, Integer pageNo, String name, HttpServletRequest request) {
		String uid = CommonMethodUtil.getUidBySession(request);
		if (!StringUtils.isEmpty(uid)) {
			try {
				usersMessageService.packageUserMessageList(model, uid, pageNo);
				CommonMethodUtil.modelUserBaseInfo(model, request, usersService);

			} catch (Exception e) {
				LOG.info("查询用户站内信失败" + e.getMessage());
				e.printStackTrace();
				return String.valueOf(ResultParame.ResultInfo.SERVER_ERROR.getCode());
			}

		} else {
			return "users/login";
		}

		return "myLingqian/message";
	}

	/**
	 * 查看站内信
	 * 
	 * @param request
	 *            请求
	 * @param model
	 *            数据包装
	 * @param id
	 *            站内信id
	 * @return 返回信息
	 */
	@RequestMapping("/details")
	public String deatils(HttpServletRequest request, ModelMap model, Integer id) {
		String uid = CommonMethodUtil.getUidBySession(request);

		if (!StringUtils.isEmpty(id)) {
			if (!StringUtils.isEmpty(uid)) {
				try {
					usersMessageService.checkMessageDetail(model, id, uid);
					CommonMethodUtil.modelUserBaseInfo(model, request, usersService);

				} catch (Exception e) {
					LOG.error("查看站内信失败，系统错误" + e.getMessage());
					e.printStackTrace();
					return String.valueOf(ResultParame.ResultInfo.SERVER_ERROR.getCode());
				}

			} else {
				return "users/login";
			}

		} else {
			LOG.info("查看站内信失败，无效参数。id：" + id);
			return "redirect:/usersMessage/list.html";
		}

		return "myLingqian/messageDetail";
	}

	/**
	 * 删除站内信
	 * 
	 * @param id
	 *            站内信id
	 * @param request
	 *            请求
	 * @param model
	 *            数据包装
	 * @return 返回信息
	 */
	@RequestMapping("/delete")
	public String delete(String id, HttpServletRequest request, ModelMap model) {
		String uid = CommonMethodUtil.getUidBySession(request);

		if (!StringUtils.isEmpty(id)) {
			if (!StringUtils.isEmpty(uid)) {
				try {
					usersMessageService.deleteAndUpdateSession(id, uid);

				} catch (Exception e) {
					LOG.error("删除站内信失败" + e.getMessage());
					e.printStackTrace();
					return String.valueOf(ResultParame.ResultInfo.SERVER_ERROR.getCode());
				}

			} else {
				return "users/login";
			}

		} else {
			LOG.info("删除站内信失败,无效参数 id:" + id);
		}

		return "redirect:/usersMessage/list.html";
	}

	/**
	 * 设置用户站内信已读
	 * 
	 * @param id
	 *            站内信id
	 * @param request
	 *            请求
	 * @return 返回信息
	 */
	@RequestMapping("/setRead")
	public String setRead(String id, HttpServletRequest request) {
		String uid = CommonMethodUtil.getUidBySession(request);

		if (!StringUtils.isEmpty(id)) {
			if (!StringUtils.isEmpty(uid)) {
				try {
					usersMessageService.updateMessageReadStatus(id, uid, 1);

				} catch (Exception e) {
					LOG.error("设置用户站内信已读失败" + e.getMessage());
					e.printStackTrace();
					return String.valueOf(ResultParame.ResultInfo.SERVER_ERROR.getCode());
				}

			} else {
				return "users/index";
			}

		} else {
			LOG.error("站内信设置已读失败,参数为空");
		}

		return "redirect:/usersMessage/list.html";
	}

	/**
	 * 设置用户站内信未读
	 * 
	 * @param id
	 *            站内信id
	 * @param request
	 *            请求
	 * @return 返回信息
	 */
	@RequestMapping("/setUnread")
	public String setUnread(String id, HttpServletRequest request) {
		String uid = CommonMethodUtil.getUidBySession(request);

		if (!StringUtils.isEmpty(id)) {
			if (!StringUtils.isEmpty(uid)) {
				try {
					usersMessageService.updateMessageReadStatus(id, uid, 0);

				} catch (Exception e) {
					LOG.error("设置用户站内信未读失败" + e.getMessage());
					e.printStackTrace();
					return String.valueOf(ResultParame.ResultInfo.SERVER_ERROR.getCode());
				}

			} else {
				return "users/login";
			}

		} else {
			LOG.info("站内信设置未读失败，参数为空");
		}

		return "redirect:/usersMessage/list.html";
	}
}
