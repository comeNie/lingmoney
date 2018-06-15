package com.mrbt.lingmoney.admin.controller.info;

import java.util.Date;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.mrbt.lingmoney.admin.service.info.InfoClientVersionService;
import com.mrbt.lingmoney.model.InfoClientVersion;
import com.mrbt.lingmoney.utils.MyUtils;
import com.mrbt.lingmoney.utils.PageInfo;
import com.mrbt.lingmoney.utils.ResultParame.ResultInfo;

/**
 * 页面设置——》手机版本号
 * 
 * @author lihq
 * @date 2017年5月22日 上午11:05:39
 * @description
 * @version 1.0
 * @since 2017-04-12
 */
@Controller
@RequestMapping("/info/clientVersion")
public class InfoClientVersionController {
	private Logger log = MyUtils.getLogger(InfoClientVersionController.class);
	@Autowired
	private InfoClientVersionService infoClientVersionService;
	private String imageRootPath = "imageUrl";

	/**
	 * 查询
	 * @param vo	封装对象
	 * @param page	页数
	 * @param rows	条数
	 * @return	返回结果	
	 */
	@RequestMapping("list")
	@ResponseBody
	public Object list(InfoClientVersion vo, Integer page, Integer rows) {
		log.info("/info/clientVersion/list");
		PageInfo pageInfo = new PageInfo(page, rows);
		try {
			infoClientVersionService.listGrid(vo, pageInfo);
			pageInfo.setCode(ResultInfo.SUCCESS.getCode());
			pageInfo.setMsg(ResultInfo.SUCCESS.getMsg());
		} catch (Exception e) {
			log.error(e.getMessage());
			e.printStackTrace();
			pageInfo.setCode(ResultInfo.SERVER_ERROR.getCode());
			pageInfo.setMsg("服务器出错");
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
	public Object delete(Integer id) {
		log.info("/info/clientVersion/delete");
		PageInfo pageInfo = new PageInfo();
		try {
			if (id != null && id > 0) {
				infoClientVersionService.delete(id);
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
			pageInfo.setMsg("服务器出错");
		}
		return pageInfo;
	}

	/**
	 * 新增
	 * 
	 * @param vo	封装对象
	 * @return 分页实体类
	 */
	@RequestMapping("save")
	@ResponseBody
	public Object save(InfoClientVersion vo) {
		log.info("/info/clientVersion/save");
		PageInfo pageInfo = new PageInfo();
		try {
			vo.setCreateTime(new Date());
			infoClientVersionService.save(vo);
			pageInfo.setCode(ResultInfo.SUCCESS.getCode());
			pageInfo.setMsg(ResultInfo.SUCCESS.getMsg());
		} catch (Exception e) {
			log.error(e.getMessage());
			e.printStackTrace();
			pageInfo.setCode(ResultInfo.SERVER_ERROR.getCode());
			pageInfo.setMsg("服务器出错");
		}
		return pageInfo;
	}

	/**
	 * 更新
	 * @param vo	封装对象
	 * @return 分页实体类
	 */
	@RequestMapping("update")
	@ResponseBody
	public Object update(InfoClientVersion vo) {
		log.info("/info/clientVersion/update");
		PageInfo pageInfo = new PageInfo();
		try {
			infoClientVersionService.update(vo);
			pageInfo.setCode(ResultInfo.SUCCESS.getCode());
			pageInfo.setMsg(ResultInfo.SUCCESS.getMsg());
		} catch (Exception e) {
			log.error(e.getMessage());
			e.printStackTrace();
			pageInfo.setCode(ResultInfo.SERVER_ERROR.getCode());
			pageInfo.setMsg("服务器出错");
		}
		return pageInfo;
	}

	/**
	 * 上传新的版本图片（手机端）
	 * @param request request	
	 * @return 返回结果
	 */
	@RequestMapping("imageUpload")
	@ResponseBody
	public Object imageUpload(MultipartHttpServletRequest request) {
		log.info("/info/clientVersion/imageUpload");
		PageInfo pageInfo = new PageInfo();
		try {
			MultipartFile file = request.getFile("imageUpload");
			infoClientVersionService.putImageUrl(file, imageRootPath);
			pageInfo.setCode(ResultInfo.SUCCESS.getCode());
			pageInfo.setMsg(ResultInfo.SUCCESS.getMsg());
		} catch (Exception e) {
			log.error(e.getMessage());
			e.printStackTrace();
			pageInfo.setCode(ResultInfo.SERVER_ERROR.getCode());
			pageInfo.setMsg("服务器出错");
		}
		return pageInfo;
	}

	/**
	 * 禁用、开通版本更新图片
	 * @param status 0禁用 1开通
	 * @return	return
	 */
	@RequestMapping("imageUrlAllow")
	public @ResponseBody Object imageUrlAllow(Integer status) {
		log.info("/info/clientVersion/imageUrlAllow");
		PageInfo pageInfo = new PageInfo();
		try {
			Integer res = infoClientVersionService.imageUrlAllow(status);
			if (res > 0) {
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
			pageInfo.setMsg("服务器出错");
		}
		return pageInfo;
	}

	/**
	 * redis提取图片
	 * @return	return
	 */
	@RequestMapping("showImageUrl")
	public @ResponseBody Object showImageUrl() {
		log.info("/info/clientVersion/showImageUrl");
		PageInfo pageInfo = new PageInfo();
		try {
			String imageUrl = infoClientVersionService.showImageUrl();
			if (StringUtils.isNotBlank(imageUrl)) {
				pageInfo.setCode(ResultInfo.SUCCESS.getCode());
				pageInfo.setMsg(imageUrl);
			} else {
				pageInfo.setCode(ResultInfo.EMPTY_DATA.getCode());
				pageInfo.setMsg(ResultInfo.EMPTY_DATA.getMsg());
			}
		} catch (Exception e) {
			log.error(e.getMessage());
			e.printStackTrace();
			pageInfo.setCode(ResultInfo.SERVER_ERROR.getCode());
			pageInfo.setMsg("服务器出错");
		}
		return pageInfo;
	}
}
