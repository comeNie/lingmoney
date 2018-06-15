package com.mrbt.lingmoney.admin.controller.info;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.mrbt.lingmoney.admin.controller.base.BaseController;
import com.mrbt.lingmoney.admin.service.info.AppBootImageService;
import com.mrbt.lingmoney.model.AppBootImage;
import com.mrbt.lingmoney.utils.PageInfo;
import com.mrbt.lingmoney.utils.ResultParame.ResultInfo;

/**
 * @author syb
 * @date 2017年5月22日 下午3:37:20
 * @version 1.0
 * @description 移动端设置管理--》APP引导页管理
 **/
@Controller
@RequestMapping("/appBootImage")
public class AppBootImageController extends BaseController {
	
	@Autowired
	private AppBootImageService appBootImageService;
	
	/**
	 * 查询列表
	 * @param pageNo	页数
	 * @param pageSize	条数
	 * @return	return
	 */
	@RequestMapping("/queryAll")
	public @ResponseBody Object queryAll(Integer pageNo, Integer pageSize) {
		PageInfo pageInfo = new PageInfo();	
		try {
			pageInfo = appBootImageService.listBootImages(pageNo, pageSize);
		} catch (Exception e) {
			pageInfo.setCode(ResultInfo.SERVER_ERROR.getCode());
			pageInfo.setMsg(ResultInfo.SERVER_ERROR.getMsg());
			e.printStackTrace();
		}
		return pageInfo;
	}
	
	/**
	 * 
	 * @param request	request
	 * @param id	id
	 * @param title	标题
	 * @param cityCode	城市编码
	 * @param sizeCode	大小
	 * @param status	状态
	 * @param showEndTime	显示结束时间
	 * @param type	类型
	 * @param data	数据
	 * @param colorInfo	北京颜色
	 * @return	return
	 */
	@RequestMapping("/save")
	public @ResponseBody Object saveBootImage(MultipartHttpServletRequest request, Integer id, String title,
			String cityCode, Integer sizeCode, Integer status, Date showEndTime, Integer type, String data,
			String colorInfo, Date createTime) {
		PageInfo pageInfo = new PageInfo();
		try {
			MultipartFile file = request.getFile("imageFile");
			AppBootImage params = new AppBootImage();
			params.setId(id);
			params.setTitle(title);
			params.setCityCode(cityCode);
			params.setSizeCode(sizeCode);
			params.setStatus(status);
			params.setShowEndTime(showEndTime);
			params.setCreateTime(createTime);
			if (!StringUtils.isEmpty(type)) {
				params.setType(type);
			}
			if (!StringUtils.isEmpty(data)) {
				params.setData(data);
			}
			if (!StringUtils.isEmpty(colorInfo)) {
				params.setColorInfo(colorInfo);
			}
			pageInfo = appBootImageService.saveBootImage(params, file);
		} catch (Exception e) {
			pageInfo.setCode(ResultInfo.SERVER_ERROR.getCode());
			pageInfo.setMsg(ResultInfo.SERVER_ERROR.getMsg());
			e.printStackTrace();
		}
		return pageInfo;
	}
}
