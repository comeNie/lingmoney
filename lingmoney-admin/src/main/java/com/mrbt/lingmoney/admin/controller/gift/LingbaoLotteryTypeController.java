package com.mrbt.lingmoney.admin.controller.gift;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.imageio.ImageIO;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.mrbt.lingmoney.admin.controller.base.BaseController;
import com.mrbt.lingmoney.admin.service.gift.LingbaoLotteryTypeService;
import com.mrbt.lingmoney.model.LingbaoLotteryType;
import com.mrbt.lingmoney.model.LingbaoLotteryTypeExample;
import com.mrbt.lingmoney.utils.FtpUtils;
import com.mrbt.lingmoney.utils.MyUtils;
import com.mrbt.lingmoney.utils.PageInfo;
import com.mrbt.lingmoney.utils.ResultParame.ResultInfo;

/**
 * 我的领地活动类型
 * 
 * @author lhq
 *
 */
@Controller
@RequestMapping("/gift/lingbaoLotteryType")
public class LingbaoLotteryTypeController extends BaseController {

	private Logger log = MyUtils.getLogger(LingbaoLotteryTypeController.class);

	@Autowired
	private LingbaoLotteryTypeService lingbaoLotteryTypeService;

	@Autowired
	private FtpUtils ftpUtils;

	private String indexPic = "lingbaoLotteryType";

	/**
	 * 更改我的领地活动类型状态
	 * 
	 * @param id
	 *            主键
	 * @return 分页实体类
	 */
	@RequestMapping("publish")
	@ResponseBody
	public Object publish(Integer id) {
		PageInfo pageInfo = new PageInfo();
		try {
			pageInfo = lingbaoLotteryTypeService.changeStatus(id);
		} catch (Exception e) {
			e.printStackTrace();
			log.error("我的领地活动类型，更改我的领地活动类型状态，失败原因是：" + e);
			pageInfo.setCode(ResultInfo.SERVER_ERROR.getCode());
			pageInfo.setMsg(ResultInfo.SERVER_ERROR.getMsg());
		}
		return pageInfo;
	}

	/**
	 * 添加或修改我的领地活动类型
	 * 
	 * @param vo 我的领地活动类型
	 * @param request request
	 * @return 分页实体类
	 */
	@RequestMapping("saveAndUpdate")
	@ResponseBody
	public Object saveAndUpdate(LingbaoLotteryType vo,
			MultipartHttpServletRequest request) {
		PageInfo pageInfo = new PageInfo();
		try {
			MultipartFile file = request.getFile("pictureImg");
			if (file.getSize() > 0) {
				BufferedImage img = ImageIO.read(file.getInputStream());
				if (img != null) {
					String fileName = UUID.randomUUID().toString();
					String type = file.getOriginalFilename().substring(
							file.getOriginalFilename().indexOf("."));
					if (StringUtils.isNotBlank(type)) {
						fileName += type;
					} else {
						fileName += ".jpg";
					}
					ftpUtils.upload(file.getInputStream(), indexPic, fileName);

					String url = ftpUtils.getUrl() + indexPic + "/" + fileName;
					vo.setPicture(url);
				} else {
					pageInfo.setCode(ResultInfo.PARAMETER_ERROR.getCode());
					pageInfo.setMsg(ResultInfo.PARAMETER_ERROR.getMsg());
					return pageInfo;
				}
			}
			if (vo.getId() == null || vo.getId() <= 0) { // 添加
				vo.setStatus(0);
				lingbaoLotteryTypeService.save(vo);
			} else { // 修改
				lingbaoLotteryTypeService.updateByPrimaryKeySelective(vo);
			}
			if (vo.getType() == 1) { // 限时抢
				LingbaoLotteryType record = lingbaoLotteryTypeService
						.findById(vo.getId());
				record.setId(vo.getId());
				record.setIntegral(null);
				lingbaoLotteryTypeService.updateByPrimaryKey(record);
			}
			pageInfo.setCode(ResultInfo.SUCCESS.getCode());
			pageInfo.setMsg(ResultInfo.SUCCESS.getMsg());
		} catch (Exception e) {
			e.printStackTrace();
			log.error("我的领地活动类型，添加或修改我的领地活动类型，失败原因是：" + e);
			pageInfo.setCode(ResultInfo.SERVER_ERROR.getCode());
			pageInfo.setMsg(ResultInfo.SERVER_ERROR.getMsg());
		}
		return pageInfo;
	}

	/**
	 * 查询我的领地活动类型列表
	 * 
	 * @param vo
	 *            我的领地活动类型
	 * @param page
	 *            当前页
	 * @param rows
	 *            每页显示的条数
	 * @return 分页实体类
	 */
	@RequestMapping("list")
	@ResponseBody
	public Object list(LingbaoLotteryType vo, Integer page, Integer rows) {
		PageInfo pageInfo = new PageInfo(page, rows);
		try {
			LingbaoLotteryTypeExample example = new LingbaoLotteryTypeExample();
			LingbaoLotteryTypeExample.Criteria cri = example.createCriteria();

			if (StringUtils.isNotBlank(vo.getName())) {
				cri.andNameLike("%" + vo.getName() + "%");
			}
			if (vo.getStatus() != null) {
				cri.andStatusEqualTo(vo.getStatus());
			}
			if (vo.getType() != null) {
				cri.andTypeEqualTo(vo.getType());
			}
			if (page != null && rows != null) {
				example.setLimitStart(pageInfo.getFrom());
				example.setLimitEnd(pageInfo.getSize());
			}
			pageInfo = lingbaoLotteryTypeService.getList(example);
		} catch (Exception e) {
			e.printStackTrace();
			log.error("我的领地活动类型，查询我的领地活动类型列表，失败原因是：" + e);
			pageInfo.setCode(ResultInfo.SERVER_ERROR.getCode());
			pageInfo.setMsg(ResultInfo.SERVER_ERROR.getMsg());
		}
		return pageInfo;
	}

	/**
	 * 根据主键删除我的领地活动类型
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
				lingbaoLotteryTypeService.delete(id);
				pageInfo.setCode(ResultInfo.SUCCESS.getCode());
				pageInfo.setMsg(ResultInfo.SUCCESS.getMsg());
			} else {
				pageInfo.setCode(ResultInfo.PARAMETER_ERROR.getCode());
				pageInfo.setMsg(ResultInfo.PARAMETER_ERROR.getMsg());
			}
		} catch (Exception e) {
			e.printStackTrace();
			log.error("我的领地活动类型，根据主键删除我的领地活动类型，失败原因是：" + e);
			pageInfo.setCode(ResultInfo.SERVER_ERROR.getCode());
			pageInfo.setMsg(ResultInfo.SERVER_ERROR.getMsg());
		}
		return pageInfo;
	}

	/**
	 * 查询大字段(规则详情)
	 * 
	 * @param id
	 *            主键
	 * @return 分页实体类
	 */
	@RequestMapping("listEditor")
	@ResponseBody
	public Object listEditor(Integer id) {
		PageInfo pageInfo = new PageInfo();
		try {
			if (id != null && id > 0) {
				LingbaoLotteryType record = lingbaoLotteryTypeService
						.findById(id);
				if (record != null) {
					List<String> list = new ArrayList<String>();
					String rule = record.getRule() == null ? "" : record
							.getRule();
					list.add(rule);
					pageInfo.setCode(ResultInfo.SUCCESS.getCode());
					pageInfo.setMsg(ResultInfo.SUCCESS.getMsg());
					pageInfo.setObj(list);
					return pageInfo;
				}
			}
			pageInfo.setCode(ResultInfo.PARAMETER_ERROR.getCode());
			pageInfo.setMsg(ResultInfo.PARAMETER_ERROR.getMsg());
		} catch (Exception e) {
			e.printStackTrace();
			log.error("我的领地活动类型，查询大字段(规则详情)，失败原因是：" + e);
			pageInfo.setCode(ResultInfo.SERVER_ERROR.getCode());
			pageInfo.setMsg(ResultInfo.SERVER_ERROR.getMsg());
		}
		return pageInfo;
	}

}
