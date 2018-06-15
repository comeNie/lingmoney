package com.mrbt.lingmoney.admin.controller.info;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.mrbt.lingmoney.admin.service.info.InfoBigBannerService;
import com.mrbt.lingmoney.model.InfoBigBanner;
import com.mrbt.lingmoney.utils.PageInfo;
import com.mrbt.lingmoney.utils.ResultParame.ResultInfo;

/**
 * 页面设置——》首页大banner
 * 
 * @author lihq
 * @date 2017年5月5日 下午3:26:15
 * @description
 * @version 1.0
 * @since 2017-04-12
 */
@Controller
@RequestMapping("/base/bigBanner")
public class InfoBigBannerController {
	@Autowired
	private InfoBigBannerService infoBigBannerService;
	/**
	 * 图片保存的根目录
	 */
	private String bannerRootPath = "banner";

	/**
	 * 更改状态
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
			boolean flag = infoBigBannerService.changeStatus(id);
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
			pageInfo.setMsg("服务器出错");
		}
		return pageInfo;
	}

	/**
	 * 保存
	 * 
	 * @Description
	 * @param request	request
	 * @param vo	vo
	 * @return	返回结果
	 */
	@RequestMapping("upload")
	@ResponseBody
	public Object upload(MultipartHttpServletRequest request, InfoBigBanner vo) {
		PageInfo pageInfo = new PageInfo();
		try {
			MultipartFile file = request.getFile("imageFile");
			infoBigBannerService.save(vo, bannerRootPath, file);
			pageInfo.setCode(ResultInfo.SUCCESS.getCode());
			pageInfo.setMsg(ResultInfo.SUCCESS.getMsg());
		} catch (Exception e) {
			e.printStackTrace();
			pageInfo.setCode(ResultInfo.SERVER_ERROR.getCode());
			pageInfo.setMsg("服务器出错");
		}
		return pageInfo;
	}

	/**
	 * 分页查询
	 * 
	 * @param vo
	 *            实体类
	 * @param page
	 *            当前页
	 * @param rows
	 *            每页显示的条数
	 * @return 分页实体类
	 */
	@RequestMapping("list")
	@ResponseBody
	public Object list(InfoBigBanner vo, Integer page, Integer rows) {
		PageInfo pageInfo = new PageInfo(page, rows);
		try {
			pageInfo = infoBigBannerService.getList(vo, pageInfo);
		} catch (Exception e) {
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
		PageInfo pageInfo = new PageInfo();
		try {
			if (id != null && id > 0) {
				infoBigBannerService.delete(id);
				pageInfo.setCode(ResultInfo.SUCCESS.getCode());
				pageInfo.setMsg(ResultInfo.SUCCESS.getMsg());
			} else {
				pageInfo.setCode(ResultInfo.EMPTY_DATA.getCode());
				pageInfo.setMsg(ResultInfo.EMPTY_DATA.getMsg());
			}
		} catch (Exception e) {
			e.printStackTrace();
			pageInfo.setCode(ResultInfo.SERVER_ERROR.getCode());
			pageInfo.setMsg("服务器出错");
		}
		return pageInfo;
	}

	/**
	 * 更新
	 * @param vo	vo
	 * @param request	request
	 * @return	return
	 */
	@RequestMapping("update")
	@ResponseBody
	public Object update(MultipartHttpServletRequest request, InfoBigBanner vo) {
		PageInfo pageInfo = new PageInfo();
		try {
			if (vo.getId() != null) {
				MultipartFile file = request.getFile("imageFile");
				infoBigBannerService.update(vo, file, bannerRootPath);
				pageInfo.setCode(ResultInfo.SUCCESS.getCode());
				pageInfo.setMsg(ResultInfo.SUCCESS.getMsg());
			} else {
				pageInfo.setCode(ResultInfo.EMPTY_DATA.getCode());
				pageInfo.setMsg(ResultInfo.EMPTY_DATA.getMsg());
			}
		} catch (Exception e) {
			e.printStackTrace();
			pageInfo.setCode(ResultInfo.SERVER_ERROR.getCode());
			pageInfo.setMsg("服务器出错");
		}
		return pageInfo;
	}
	
	
	/**
	 * 刷新
	 * @param vo	vo
	 * @param request	request
	 * @return	return
	 */
	@RequestMapping("refresh")
	@ResponseBody
	public Object refresh() {
		PageInfo pageInfo = new PageInfo();
		try {
			infoBigBannerService.refresh();
			pageInfo.setCode(ResultInfo.SUCCESS.getCode());
			pageInfo.setMsg(ResultInfo.SUCCESS.getMsg());
		} catch (Exception e) {
			e.printStackTrace();
			pageInfo.setCode(ResultInfo.SERVER_ERROR.getCode());
			pageInfo.setMsg("服务器出错");
		}
		return pageInfo;
	}

}
