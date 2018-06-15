package com.mrbt.lingmoney.admin.controller.newuserreward;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartRequest;

import com.mrbt.lingmoney.admin.service.newuserreward.NewUserRewardService;
import com.mrbt.lingmoney.utils.PageInfo;
import com.mrbt.lingmoney.utils.ResultParame.ResultInfo;

/**
 * 我的领地——》新手福利
 * 
 * @author luox
 * @Date 2017年6月26日
 */
@Controller
@RequestMapping(value = "/newUserReward", method = RequestMethod.POST)
public class NewUserRewardController {

	@Autowired
	private NewUserRewardService newUserRewardService;
	
	/**
	 * 上传文件
	 * @param file	input
	 * @return	返回处理结果
	 */
	@RequestMapping("upload")
	@ResponseBody
	public Object upload(@RequestParam("file") MultipartFile file) {
		PageInfo pageInfo = new PageInfo();
		try {
			pageInfo.setCode(ResultInfo.SUCCESS.getCode());
			pageInfo.setMsg(ResultInfo.SUCCESS.getMsg());
			pageInfo.setObj(newUserRewardService.upload(file));
			
		} catch (Exception e) {
			pageInfo.setCode(ResultInfo.SERVER_ERROR.getCode());
			pageInfo.setMsg(ResultInfo.SERVER_ERROR.getMsg());
			e.printStackTrace();
		}
		return pageInfo;
	}
	
	/**
	 * 发布
	 * @param id	id
	 * @return	返回处理结果
	 */
	@RequestMapping("publish")
	@ResponseBody
	public Object publish(Integer id) {
		PageInfo pageInfo = new PageInfo();
		try {
			pageInfo.setCode(ResultInfo.SUCCESS.getCode());
			pageInfo.setMsg(ResultInfo.SUCCESS.getMsg());
			newUserRewardService.updateStatus(id);
		} catch (Exception e) {
			pageInfo.setCode(ResultInfo.SERVER_ERROR.getCode());
			pageInfo.setMsg(ResultInfo.SERVER_ERROR.getMsg());
			e.printStackTrace();
		}
		return pageInfo;
	}
	
	/**
	 * 删除
	 * @param id	ID
	 * @return	返回处理结果
	 */
	@RequestMapping("delete")
	@ResponseBody
	public Object delete(Integer id) {
		PageInfo pageInfo = new PageInfo();
		try {
			pageInfo.setCode(ResultInfo.SUCCESS.getCode());
			pageInfo.setMsg(ResultInfo.SUCCESS.getMsg());
			newUserRewardService.delete(id);
		} catch (Exception e) {
			pageInfo.setCode(ResultInfo.SERVER_ERROR.getCode());
			pageInfo.setMsg(ResultInfo.SERVER_ERROR.getMsg());
			e.printStackTrace();
		}
		return pageInfo;
	}
	
	/**
	 * 保存和更新
	 * @param markedWords	备注
	 * @param id	id	
	 * @param request req
	 * @return	返回结果
	 */
	@RequestMapping("saveOrEdit")
	@ResponseBody
	public Object saveOrEdit(String markedWords, Integer id, HttpServletRequest request) {
		PageInfo pageInfo = new PageInfo();
		try {
			pageInfo.setCode(ResultInfo.SUCCESS.getCode());
			pageInfo.setMsg(ResultInfo.SUCCESS.getMsg());
			Map<String, List<MultipartFile>> map = ((MultipartRequest) request).getMultiFileMap();
			newUserRewardService.saveOrEdit(markedWords, id, map);
		} catch (Exception e) {
			pageInfo.setCode(ResultInfo.SERVER_ERROR.getCode());
			pageInfo.setMsg(ResultInfo.SERVER_ERROR.getMsg());
			e.printStackTrace();
		}
		return pageInfo;
	}
	
	/**
	 * 查询列表
	 * @param markedWords	备注
	 * @param status	状态
	 * @param page	页数
	 * @param rows	条数
	 * @return	返回列表
	 */
	@RequestMapping("list")
	@ResponseBody
	public Object list(String markedWords, Integer status, Integer page, Integer rows) {
		
		PageInfo pageInfo = new PageInfo(page, rows);
		try {
			newUserRewardService.getList(markedWords, status, pageInfo);
		} catch (Exception e) {
			pageInfo.setCode(ResultInfo.SERVER_ERROR.getCode());
			pageInfo.setMsg(ResultInfo.SERVER_ERROR.getMsg());
			e.printStackTrace();
		}
		return pageInfo;
	}
}
