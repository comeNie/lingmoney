package com.mrbt.lingmoney.admin.controller.info;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mrbt.lingmoney.admin.service.info.UsersAskService;
import com.mrbt.lingmoney.model.UsersAsk;
import com.mrbt.lingmoney.utils.MyUtils;
import com.mrbt.lingmoney.utils.PageInfo;
import com.mrbt.lingmoney.utils.ResultParame.ResultInfo;

/**
 * 页面设置——》你问我答
 * 
 * @author lihq
 * @date 2017年5月19日 上午10:50:37
 * @description
 * @version 1.0
 * @since 2017-04-12
 */
@Controller
@RequestMapping("/info/ask")
public class UsersAskController {
	
	private Logger log = MyUtils.getLogger(UsersAskController.class);
	
	@Autowired
	private UsersAskService usersAskService;

	/**
	 * 分页查询
	 * 
	 * @param vo	封装对象
	 * @param page	页数
	 * @param rows	条数
	 * @return	返回结果
	 */
	@RequestMapping("list")
	@ResponseBody
	public Object list(UsersAsk vo, Integer page, Integer rows) {
		log.info("/info/ask/list");
		PageInfo pageInfo = new PageInfo(page, rows);
		try {
			vo.setType(0);
			usersAskService.listGrid(vo, pageInfo);
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
	 * 更改问题状态
	 * 
	 * @param id
	 *            主键
	 * @return 分页实体类
	 */
	@RequestMapping("changeStatus")
	@ResponseBody
	public Object changeStatus(Integer id) {
		PageInfo pageInfo = new PageInfo();
		try {
			boolean flag = usersAskService.changeStatus(id);
			if (flag) {
				pageInfo.setCode(ResultInfo.SUCCESS.getCode());
				pageInfo.setMsg(ResultInfo.SUCCESS.getMsg());
			} else {
				pageInfo.setCode(ResultInfo.EMPTY_DATA.getCode());
				pageInfo.setMsg(ResultInfo.EMPTY_DATA.getMsg());
			}
		} catch (Exception e) {
			e.printStackTrace();
			pageInfo.setCode(ResultInfo.SERVER_ERROR.getCode());
			pageInfo.setMsg(ResultInfo.SERVER_ERROR.getMsg());
		}
		return pageInfo;
	}

	/**
	 * 更改热门状态
	 * @param id	数据ID
	 * @return	处理结果
	 */
	@RequestMapping("publish")
	@ResponseBody
	public Object publish(Integer id) {
		log.info("/info/ask/publish");
		PageInfo pageInfo = new PageInfo();
		try {
			usersAskService.publish(id);
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
	 * 修改
	 * @param vo	封装对象
	 * @return	处理结果
	 */
	@RequestMapping("update")
	@ResponseBody
	public Object update(UsersAsk vo) {
		log.info("/info/ask/update");
		PageInfo pageInfo = new PageInfo();
		try {
			usersAskService.update(vo);
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
	 * 新增
	 * 
	 * @param vo	封装对象
	 * @return	处理结果
	 */
	@RequestMapping("save")
	@ResponseBody
	public Object save(UsersAsk vo) {
		log.info("/info/ask/save");
		PageInfo pageInfo = new PageInfo();
		try {
			usersAskService.save(vo);
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
	 * 返回回答的内容
	 * @param askId askId
	 * @return	处理结果
	 */
	@RequestMapping("listAnwser")
	@ResponseBody
	public Object listAnwser(Integer askId) {
		log.info("/info/ask/listAnwser");
		PageInfo pageInfo = new PageInfo();
		try {
			pageInfo.setMsg(usersAskService.listAnwser(askId));
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
	 * 返回回答的主键
	 * @param askId	数据ID
	 * @param response	res
	 * @throws IOException	异常处理
	 */
	@RequestMapping("anwser")
	@ResponseBody
	public void anwser(Integer askId, HttpServletResponse response) throws IOException {
		log.info("/info/ask/anwser");
		PrintWriter out = response.getWriter();
		Integer id = usersAskService.anwser(askId);
		out.print(id);
	}

	/**
	 * 刷新redis
	 * @return	返回结果
	 */
	@RequestMapping("reload")
	@ResponseBody
	public Object reload() {
		log.info("/info/ask/reload");
		PageInfo pageInfo = new PageInfo();
		try {
			usersAskService.reload();
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
	 * 删除
	 * 
	 * @param id	数据ID
	 * @return	返回结果
	 */
	@RequestMapping("delete")
	@ResponseBody
	public Object delete(Integer id) {
		log.info("/info/ask/delete");
		PageInfo pageInfo = new PageInfo();
		try {
			usersAskService.delete(id);
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
