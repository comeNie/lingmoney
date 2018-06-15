package com.mrbt.lingmoney.admin.controller.info;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.mrbt.lingmoney.admin.service.info.InfoClientBannerService;
import com.mrbt.lingmoney.model.InfoClientBanner;
import com.mrbt.lingmoney.utils.MyUtils;
import com.mrbt.lingmoney.utils.PageInfo;
import com.mrbt.lingmoney.utils.ResultParame.ResultInfo;

/**
 * 页面设置——》手机端banner
 * 
 * @author lihq
 * @date 2017年5月18日 下午3:12:16
 * @description
 * @version 1.0
 * @since 2017-04-12
 */
@Controller
@RequestMapping("/info/clientBanner")
public class InfoClientBannerController {

	private Logger log = MyUtils.getLogger(InfoClientBannerController.class);
	@Autowired
	private InfoClientBannerService infoClientBannerService;
	/**
	 * banner保存的根目录
	 */
	private String bannerRootPath = "clientBanner";

	/**
	 * 修改状态
	 * @param id	数据ID
	 * @return	return
	 */
	@RequestMapping("publish")
	@ResponseBody
	public Object publish(Integer id) {
		log.info("/info/clientBanner/publish");
		PageInfo pageInfo = new PageInfo();
		try {
			boolean flag = infoClientBannerService.changeStatus(id);
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
	 * 保存
	 * @param request	request
	 * @param vo	vo
	 * @return	返回结果
	 * @throws Exception	异常处理
	 */
	@RequestMapping("save")
	@ResponseBody
	public Object save(MultipartHttpServletRequest request, InfoClientBanner vo) throws Exception {
		log.info("/info/clientBanner/save");
		PageInfo pageInfo = new PageInfo();
		try {
			MultipartFile file1 = request.getFile("path1");
			MultipartFile file2 = request.getFile("path2");
			MultipartFile file3 = request.getFile("path3");
			MultipartFile file4 = request.getFile("path4");
			MultipartFile file5 = request.getFile("path5");
			MultipartFile file6 = request.getFile("path6");
			infoClientBannerService.save(vo, file1, file2, file3, file4, file5, file6, bannerRootPath);
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
	 * 查询
	 * 
	 * @param vo	vo
	 * @param page	page
	 * @param rows	rows
	 * @return	return
	 */
	@RequestMapping("list")
	@ResponseBody
	public Object list(InfoClientBanner vo, Integer page, Integer rows) {
		log.info("/info/news/list");
		PageInfo pageInfo = new PageInfo(page, rows);
		try {
			infoClientBannerService.listGrid(vo, pageInfo);
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
	 * @param id	id
	 * @return	id
	 */
	@RequestMapping("delete")
	@ResponseBody
	public Object delete(Integer id) {
		log.info("/info/clientBanner/delete");
		PageInfo pageInfo = new PageInfo();
		try {
			infoClientBannerService.delete(id);
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
	 * 更新banner
	 * @param request	request
	 * @param vo	vo
	 * @return	vo
	 */
	@RequestMapping("update")
	@ResponseBody
	public Object update(MultipartHttpServletRequest request, InfoClientBanner vo) {
		log.info("/info/clientBanner/update");
		PageInfo pageInfo = new PageInfo();
		try {
			MultipartFile file1 = request.getFile("path1");
			MultipartFile file2 = request.getFile("path2");
			MultipartFile file3 = request.getFile("path3");
			MultipartFile file4 = request.getFile("path4");
			MultipartFile file5 = request.getFile("path5");
			MultipartFile file6 = request.getFile("path6");
			infoClientBannerService.update(vo, file1, file2, file3, file4, file5, file6, bannerRootPath);
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
	 * 刷新上线
	 * @param id	id
	 * @return	id
	 */
	@RequestMapping("refresh")
	@ResponseBody
	public Object refresh() {
		log.info("/info/clientBanner/refresh");
		PageInfo pageInfo = new PageInfo();
		try {
			infoClientBannerService.refresh();
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

}
