package com.mrbt.lingmoney.admin.controller.info;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mrbt.lingmoney.admin.controller.base.BaseController;
import com.mrbt.lingmoney.admin.service.info.AdminMessageService;
import com.mrbt.lingmoney.admin.service.info.UsersAccountService;
import com.mrbt.lingmoney.model.AdminMessageWithBLOBs;
import com.mrbt.lingmoney.model.Condition;
import com.mrbt.lingmoney.model.UsersAccount;
import com.mrbt.lingmoney.utils.MyUtils;
import com.mrbt.lingmoney.utils.PageInfo;
import com.mrbt.lingmoney.utils.ResultParame.ResultInfo;

/**
 * 页面设置--》发送消息
 * 
 * @author lihq
 * @date 2017年5月12日 下午1:48:04
 * @description
 * @version 1.0
 * @since 2017-04-12
 */
@Controller
@RequestMapping("/user/adminmessage")
public class AdminMessageController extends BaseController {
	private Logger log = MyUtils.getLogger(AdminMessageController.class);
	@Autowired
	private AdminMessageService adminMessageService;
	@Autowired
	private UsersAccountService usersAccountService;

	/**
	 * 删除
	 * @param id	数据ID
	 * @return	返回处理结果
	 */
	@RequestMapping("delete")
	@ResponseBody
	public Object delete(Integer id) {
		log.info("/user/adminmessage/delete");
		PageInfo pageInfo = new PageInfo();
		try {
			adminMessageService.delete(id);
			pageInfo.setCode(ResultInfo.SUCCESS.getCode());
			pageInfo.setMsg(ResultInfo.SUCCESS.getMsg());
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
	 * @param vo	封装对象
	 * @param page	页数	
	 * @param rows	条数
	 * @return	返回处理结果
	 */
	@RequestMapping("list")
	@ResponseBody
	public Object list(AdminMessageWithBLOBs vo, Integer rows, Integer page) {
		log.info("/user/adminmessage/list");
		PageInfo pageInfo = new PageInfo(page, rows);
		try {
			adminMessageService.listGrid(vo, pageInfo);
			pageInfo.setCode(ResultInfo.SUCCESS.getCode());
			pageInfo.setMsg(ResultInfo.SUCCESS.getMsg());
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
	 * 
	 * @param vo	封装对象
	 * @return	返回处理结果
	 */
	@RequestMapping("save")
	@ResponseBody
	public Object save(AdminMessageWithBLOBs vo) {
		log.info("/user/adminmessage/save");
		PageInfo pageInfo = new PageInfo();
		try {
			adminMessageService.save(vo);
			pageInfo.setCode(ResultInfo.SUCCESS.getCode());
			pageInfo.setMsg(ResultInfo.SUCCESS.getMsg());
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
	 * @param vo	封装对象
	 * @return	返回处理结果
	 */
	@RequestMapping("update")
	@ResponseBody
	public Object update(AdminMessageWithBLOBs vo) {
		log.info("/user/adminmessage/update");
		PageInfo pageInfo = new PageInfo();
		try {
			adminMessageService.update(vo);
			pageInfo.setCode(ResultInfo.SUCCESS.getCode());
			pageInfo.setMsg(ResultInfo.SUCCESS.getMsg());
		} catch (Exception e) {
			log.error(e.getMessage());
			e.printStackTrace();
			pageInfo.setCode(ResultInfo.SERVER_ERROR.getCode());
			pageInfo.setMsg(ResultInfo.SERVER_ERROR.getMsg());
		}
		return pageInfo;
	}

	/**
	 * 查询消息
	 * @param telephone	用户手机号
	 * @param balence	均价
	 * @param finaclMoney	金额
	 * @return	返回结果
	 */
	@RequestMapping("selectAdminMessage")
	@ResponseBody
	public Object selectAdminMessage(String telephone, Double balence, Double finaclMoney) {
		log.info("/user/adminmessage/selectAdminMessage");
		Condition condition = new Condition();
		if (StringUtils.isBlank(telephone)) {
			condition.setBalance(balence);
			condition.setFincalMoney(finaclMoney);
		} else {
			condition.setTelephone(telephone);
			condition.setBalance(balence);
			condition.setFincalMoney(finaclMoney);
		}
		List<UsersAccount> list = usersAccountService.findAdminMessage(condition);

		return list;
	}

	/**
	 * 给部分人发信息
	 * 
	 * @param vo	数据对象
	 * @return	返回结果
	 */
	@RequestMapping("insertAdminMessage")
	@ResponseBody
	public Object insertAdminMessage(AdminMessageWithBLOBs vo) {
		log.info("/user/adminmessage/insertAdminMessage");
		PageInfo pageInfo = new PageInfo();
		try {
			adminMessageService.sendMessage(vo, pageInfo, 0);
		} catch (Exception e) {
			log.error(e.getMessage());
			e.printStackTrace();
			pageInfo.setCode(ResultInfo.SERVER_ERROR.getCode());
			pageInfo.setMsg(ResultInfo.SERVER_ERROR.getMsg());
		}
		return pageInfo;

	}

	/**
	 * 给所有人发送信息
	 * @param vo	数据对象
	 * @return	返回结果
	 */
	@RequestMapping("insertMessageToAll")
	@ResponseBody
	public Object insertMessageToAll(AdminMessageWithBLOBs vo) {
		log.info("/user/adminmessage/insertMessageToAll");
		PageInfo pageInfo = new PageInfo();
		try {
			adminMessageService.sendMessage(vo, pageInfo, 1);
		} catch (Exception e) {
			log.error(e.getMessage());
			e.printStackTrace();
			pageInfo.setCode(ResultInfo.SERVER_ERROR.getCode());
			pageInfo.setMsg(ResultInfo.SERVER_ERROR.getMsg());
		}
		return pageInfo;

	}

	/**
	 * 查询详情
	 * @param id	数据ID
	 * @return	返回结果
	 */
	@RequestMapping("listContent")
	@ResponseBody
	public Object listContent(Integer id) {
		log.info("/user/adminmessage/listContent");
		PageInfo pageInfo = new PageInfo();
		try {
			pageInfo.setObj(adminMessageService.listContent(id));
			pageInfo.setCode(ResultInfo.SUCCESS.getCode());
			pageInfo.setMsg(ResultInfo.SUCCESS.getMsg());
		} catch (Exception e) {
			log.error(e.getMessage());
			e.printStackTrace();
			pageInfo.setCode(ResultInfo.SERVER_ERROR.getCode());
			pageInfo.setMsg(ResultInfo.SERVER_ERROR.getMsg());
		}
		return pageInfo;
	}

}
