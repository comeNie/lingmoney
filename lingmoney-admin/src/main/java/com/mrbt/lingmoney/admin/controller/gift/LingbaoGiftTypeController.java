package com.mrbt.lingmoney.admin.controller.gift;

import java.awt.image.BufferedImage;
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

import com.mrbt.lingmoney.admin.service.gift.LingbaoGiftTypeService;
import com.mrbt.lingmoney.model.LingbaoGiftType;
import com.mrbt.lingmoney.model.LingbaoGiftTypeExample;
import com.mrbt.lingmoney.utils.FtpUtils;
import com.mrbt.lingmoney.utils.MyUtils;
import com.mrbt.lingmoney.utils.PageInfo;
import com.mrbt.lingmoney.utils.ResultParame.ResultInfo;

/**
 * 我的领地礼品类型
 * 
 * @author lhq
 *
 */
@Controller
@RequestMapping("/gift/lingbaoGiftType")
public class LingbaoGiftTypeController {

	private Logger log = MyUtils.getLogger(LingbaoGiftTypeController.class);

	@Autowired
	private LingbaoGiftTypeService lingbaoGiftTypeService;
	
	@Autowired
	private FtpUtils ftpUtils;

	private String indexPic = "lingbaoGiftType";
	/**
	 * 更改我的领地礼品类型状态
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
			pageInfo = lingbaoGiftTypeService.changeStatus(id);
		} catch (Exception e) {
			e.printStackTrace();
			log.error("我的领地礼品类型，更改我的领地礼品类型状态，失败原因是：" + e);
			pageInfo.setCode(ResultInfo.SERVER_ERROR.getCode());
			pageInfo.setMsg(ResultInfo.SERVER_ERROR.getMsg());
		}
		return pageInfo;
	}

	/**
	 * 新增或修改我的领地礼品类型
	 * 
	 * @param vo 我的领地礼品类型
	 * @param request request
	 * @return 分页实体类
	 */
	@RequestMapping("saveAndUpdate")
	@ResponseBody
	public Object saveAndUpdate(LingbaoGiftType vo, MultipartHttpServletRequest request) {
		PageInfo pageInfo = new PageInfo();
		try {
			MultipartFile file = request.getFile("pictureImg");
			if (file.getSize() > 0) {
				BufferedImage img = ImageIO.read(file.getInputStream());
				if (img != null) {
					String fileName = UUID.randomUUID().toString();
					String type = file.getOriginalFilename().substring(file.getOriginalFilename().indexOf("."));
					if (StringUtils.isNotBlank(type)) {
						fileName += type;
					} else {
						fileName += ".jpg";
					}
					ftpUtils.upload(file.getInputStream(), indexPic, fileName);

					String url = ftpUtils.getUrl() + indexPic + "/" + fileName;
					vo.setImageUrl(url);
				} else {
					pageInfo.setCode(ResultInfo.PARAMETER_ERROR.getCode());
					pageInfo.setMsg(ResultInfo.PARAMETER_ERROR.getMsg());
					return pageInfo;
				}
			}
			if (vo.getId() == null || vo.getId() <= 0) { // 添加
				vo.setStatus(0);
				lingbaoGiftTypeService.save(vo);
			} else { // 修改
				lingbaoGiftTypeService.update(vo);
			}
			pageInfo.setCode(ResultInfo.SUCCESS.getCode());
			pageInfo.setMsg(ResultInfo.SUCCESS.getMsg());
		} catch (Exception e) {
			e.printStackTrace();
			log.error("我的领地礼品类型，新增或修改我的领地礼品类型，失败原因是：" + e);
			pageInfo.setCode(ResultInfo.SERVER_ERROR.getCode());
			pageInfo.setMsg(ResultInfo.SERVER_ERROR.getMsg());
		}
		return pageInfo;
	}

	/**
	 * 查询我的领地礼品类型列表
	 * 
	 * @param vo
	 *            我的领地礼品类型
	 * @param page
	 *            当前页
	 * @param rows
	 *            每页显示的条数
	 * @return 分页实体类
	 */
	@RequestMapping("list")
	@ResponseBody
	public Object list(LingbaoGiftType vo, Integer page, Integer rows) {
		PageInfo pageInfo = new PageInfo(page, rows);
		try {
			LingbaoGiftTypeExample example = new LingbaoGiftTypeExample();
			LingbaoGiftTypeExample.Criteria cri = example.createCriteria();
			if (vo.getId() != null) {
				cri.andIdEqualTo(vo.getId());
			}
			if (StringUtils.isNotBlank(vo.getName())) {
				cri.andNameLike("%" + vo.getName() + "%");
			}
			if (vo.getStatus() != null) {
				cri.andStatusEqualTo(vo.getStatus());
			}
			if (page != null && rows != null) {
				example.setLimitStart(pageInfo.getFrom());
				example.setLimitEnd(pageInfo.getSize());
			}
			pageInfo = lingbaoGiftTypeService.getList(example);
		} catch (Exception e) {
			e.printStackTrace();
			log.error("我的领地礼品类型，查询我的领地礼品类型列表，失败原因是：" + e);
			pageInfo.setCode(ResultInfo.SERVER_ERROR.getCode());
			pageInfo.setMsg(ResultInfo.SERVER_ERROR.getMsg());
		}
		return pageInfo;
	}

	/**
	 * 删除我的领地礼品类型
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
				lingbaoGiftTypeService.delete(id);
				pageInfo.setCode(ResultInfo.SUCCESS.getCode());
				pageInfo.setMsg(ResultInfo.SUCCESS.getMsg());
			} else {
				pageInfo.setCode(ResultInfo.PARAMETER_ERROR.getCode());
				pageInfo.setMsg(ResultInfo.PARAMETER_ERROR.getMsg());
			}
		} catch (Exception e) {
			e.printStackTrace();
			log.error("我的领地礼品类型，删除我的领地礼品类型，失败原因是：" + e);
			pageInfo.setCode(ResultInfo.SERVER_ERROR.getCode());
			pageInfo.setMsg(ResultInfo.SERVER_ERROR.getMsg());
		}
		return pageInfo;
	}

	/**
	 * 根据主键查找我的领地礼品类型
	 * 
	 * @param id
	 *            主键
	 * @return 分页实体类
	 */
	@RequestMapping("findById")
	@ResponseBody
	public Object findById(Integer id) {
		PageInfo pageInfo = new PageInfo();
		try {
			if (id != null && id > 0) {
				LingbaoGiftType record = lingbaoGiftTypeService.findById(id);
				if (record != null) {
					pageInfo.setCode(ResultInfo.SUCCESS.getCode());
					pageInfo.setMsg(ResultInfo.SUCCESS.getMsg());
					pageInfo.setObj(record);
					return pageInfo;
				}
			}
			pageInfo.setCode(ResultInfo.PARAMETER_ERROR.getCode());
			pageInfo.setMsg(ResultInfo.PARAMETER_ERROR.getMsg());
		} catch (Exception e) {
			e.printStackTrace();
			log.error("我的领地礼品类型，根据主键查找我的领地礼品类型，失败原因是：" + e);
			pageInfo.setCode(ResultInfo.SERVER_ERROR.getCode());
			pageInfo.setMsg(ResultInfo.SERVER_ERROR.getMsg());
		}
		return pageInfo;
	}

}
