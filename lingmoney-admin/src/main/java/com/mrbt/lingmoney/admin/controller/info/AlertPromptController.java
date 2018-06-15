package com.mrbt.lingmoney.admin.controller.info;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.mrbt.lingmoney.admin.service.info.AlertPromptService;
import com.mrbt.lingmoney.model.AlertPrompt;
import com.mrbt.lingmoney.model.AlertPromptExample;
import com.mrbt.lingmoney.utils.MyUtils;
import com.mrbt.lingmoney.utils.PageInfo;
import com.mrbt.lingmoney.utils.ResultParame.ResultInfo;
import com.mrbt.lingmoney.utils.ResultParame.ResultNumber;

/**
 * 页面设置--》弹框提示
 * 
 * @author lihq
 * @date 2017年7月6日 下午5:15:12
 * @description
 * @version 1.0
 * @since 2017-04-12
 */
@Controller
@RequestMapping("/info/alertPrompt")
public class AlertPromptController {

	private Logger log = MyUtils.getLogger(AlertPromptController.class);

	@Autowired
	private AlertPromptService alertPromptService;

	/**
	 * 图片保存的根目录
	 */
	private String bannerRootPath = "alertPrompt";

	/**
	 * 更改状态
	 * @param vo	vo
	 * @return	return
	 */
	@RequestMapping("changeStatus")
	@ResponseBody
	public Object changeStatus(AlertPrompt vo) {
		PageInfo pageInfo = new PageInfo();
		try {
			boolean flag = alertPromptService.changeStatus(vo);
			if (flag) {
				pageInfo.setCode(ResultInfo.SUCCESS.getCode());
				pageInfo.setMsg(ResultInfo.SUCCESS.getMsg());
			} else {
				pageInfo.setCode(ResultInfo.EMPTY_DATA.getCode());
				pageInfo.setMsg(ResultInfo.EMPTY_DATA.getMsg());
			}
		} catch (Exception e) {
			e.printStackTrace();
			log.error("更改状态，失败原因是：" + e);
			pageInfo.setCode(ResultInfo.SERVER_ERROR.getCode());
			pageInfo.setMsg(ResultInfo.SERVER_ERROR.getMsg());
		}
		return pageInfo;
	}

	/**
	 * 添加或修改
	 * @param request	request
	 * @param vo	vo
	 * @return	vo
	 */
	@RequestMapping("saveAndUpdate")
	@ResponseBody
	public Object saveAndUpdate(MultipartHttpServletRequest request, AlertPrompt vo) {
		PageInfo pageInfo = new PageInfo();
		try {
			MultipartFile file1 = request.getFile("path1");
			MultipartFile file2 = request.getFile("path2");
			if (StringUtils.isBlank(vo.getId())) {
				alertPromptService.save(vo, file1, file2, bannerRootPath);
			} else {
				alertPromptService.update(vo, file1, file2, bannerRootPath);
			}
			pageInfo.setCode(ResultInfo.SUCCESS.getCode());
			pageInfo.setMsg(ResultInfo.SUCCESS.getMsg());
		} catch (Exception e) {
			e.printStackTrace();
			log.error("，添加或修改，失败原因是：" + e);
			pageInfo.setCode(ResultInfo.SERVER_ERROR.getCode());
			pageInfo.setMsg(ResultInfo.SERVER_ERROR.getMsg());
		}
		return pageInfo;
	}

	/**
	 * 查询列表
	 * 
	 * @param vo	vo
	 * @param page 当前页
	 * @param rows 每页显示的条数
	 * @return 分页实体类
	 */
	@RequestMapping("list")
	@ResponseBody
	public Object list(AlertPrompt vo, Integer page, Integer rows) {
		PageInfo pageInfo = new PageInfo(page, rows);
		try {
			AlertPromptExample example = new AlertPromptExample();
			AlertPromptExample.Criteria cri = example.createCriteria();
			if (StringUtils.isNotBlank(vo.getName())) {
				cri.andNameLike("%" + vo.getName() + "%");
			}
			if (vo.getStatus() != null) {
				cri.andStatusEqualTo(vo.getStatus());
			}
			if (vo.getId() != null) {
				cri.andIdEqualTo(vo.getId());
			}
			cri.andStatusNotEqualTo(ResultNumber.MINUS_ONE.getNumber());
			if (page != null && rows != null) {
				example.setLimitStart(pageInfo.getFrom());
				example.setLimitEnd(pageInfo.getSize());
			}
			example.setOrderByClause("priority");
			pageInfo = alertPromptService.getList(example);
		} catch (Exception e) {
			e.printStackTrace();
			log.error("查询列表，失败原因是：" + e);
			pageInfo.setCode(ResultInfo.SERVER_ERROR.getCode());
			pageInfo.setMsg(ResultInfo.SERVER_ERROR.getMsg());
		}
		return pageInfo;
	}

	/**
	 * 删除
	 * 
	 * @param id
	 *            主键
	 * @return 分页实体类
	 */
	@RequestMapping("delete")
	@ResponseBody
	public Object delete(String id) {
		PageInfo pageInfo = new PageInfo();
		try {
			if (StringUtils.isNotBlank(id)) {
				alertPromptService.delete(id);
				pageInfo.setCode(ResultInfo.SUCCESS.getCode());
				pageInfo.setMsg(ResultInfo.SUCCESS.getMsg());
			} else {
				pageInfo.setCode(ResultInfo.EMPTY_DATA.getCode());
				pageInfo.setMsg(ResultInfo.EMPTY_DATA.getMsg());
			}
		} catch (Exception e) {
			e.printStackTrace();
			log.error("，删除，失败原因是：" + e);
			pageInfo.setCode(ResultInfo.SERVER_ERROR.getCode());
			pageInfo.setMsg(ResultInfo.SERVER_ERROR.getMsg());
		}
		return pageInfo;
	}

}
