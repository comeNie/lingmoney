package com.mrbt.lingmoney.admin.controller.info;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mrbt.lingmoney.admin.controller.base.BaseController;
import com.mrbt.lingmoney.admin.service.info.UsersMessageService;
import com.mrbt.lingmoney.model.UsersMessage;
import com.mrbt.lingmoney.utils.MyUtils;
import com.mrbt.lingmoney.utils.PageInfo;
import com.mrbt.lingmoney.utils.ResultParame.ResultInfo;

/**
 * 页面设置——》用户消息
 * 
 * @author lihq
 * @date 2017年5月19日 下午1:55:09
 * @description
 * @version 1.0
 * @since 2017-04-12
 */
@Controller
@RequestMapping("/user/usermessage")
public class UsersMessageController extends BaseController {
	private Logger log = MyUtils.getLogger(UsersMessageController.class);
	@Autowired
	private UsersMessageService usersMessageService;

	/**
	 * 删除
	 * 
	 * @param id	数据ID
	 * @return	返回结果
	 */
	@RequestMapping("delete")
	@ResponseBody
	public Object delete(Integer id) {
		log.info("/info/usermessage/delete");
		PageInfo pageInfo = new PageInfo();
		try {
			usersMessageService.delete(id);
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
	 * 更改阅读状态
	 * 
	 * @param id 主键
	 * @return 分页实体类
	 */
	@RequestMapping("reader")
	@ResponseBody
	public Object reader(Integer id) {
		log.info("/info/usermessage/reader");
		PageInfo pageInfo = new PageInfo();
		try {
			boolean flag = usersMessageService.reader(id);
			if (flag) {
				pageInfo.setCode(ResultInfo.SUCCESS.getCode());
				pageInfo.setMsg(ResultInfo.SUCCESS.getMsg());
			} else {
				pageInfo.setCode(ResultInfo.EMPTY_DATA.getCode());
				pageInfo.setMsg(ResultInfo.EMPTY_DATA.getMsg());
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
	 * @param vo	对象
	 * @param nameCom 名称
	 * @param page	页数
	 * @param rows	条数
	 * @return 返回结果
	 */
	@RequestMapping("list")
	@ResponseBody
	public Object list(UsersMessage vo, String nameCom, Integer rows, Integer page) {
		log.info("/info/usermessage/list");
		PageInfo pageInfo = new PageInfo(page, rows);
		try {
			usersMessageService.listGrid(vo, pageInfo);
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
	 * 返回text内容
	 * 
	 * @param id	数据ID
	 * @return	返回结果
	 */
	@RequestMapping("listContent")
	@ResponseBody
	public Object listContent(Integer id) {
		log.info("/info/usermessage/listContent");
		PageInfo pageInfo = new PageInfo();
		try {
			pageInfo.setMsg(usersMessageService.listContent(id));
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
