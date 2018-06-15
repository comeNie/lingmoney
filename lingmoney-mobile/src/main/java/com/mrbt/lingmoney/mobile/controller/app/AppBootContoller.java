package com.mrbt.lingmoney.mobile.controller.app;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mrbt.lingmoney.mobile.vo.info.AppBootImageVo;
import com.mrbt.lingmoney.model.AppBootImage;
import com.mrbt.lingmoney.service.common.AppBootService;
import com.mrbt.lingmoney.utils.PageInfo;
import com.mrbt.lingmoney.utils.ResultParame.ResultInfo;

/**
 * APP启动图片
 * @author Administrator
 *
 */
@Controller
@RequestMapping("/appbase")
public class AppBootContoller {
	
	private static final Logger LOGGER = LogManager.getLogger(AppBootContoller.class);
	 
	@Autowired
	private AppBootService appBootService;
	
	/**
	 * App启动图片数据
	 * @param response req
	 * @param request  res
	 * @param sizeCode	请求尺寸
	 * @param cityCode	城市ID
	 * @return pageInfo
	 */
	@RequestMapping(value = "/bootImage", method = RequestMethod.POST)
	public @ResponseBody Object bootImage(HttpServletResponse response, HttpServletRequest request, String sizeCode,
			String cityCode) {
		
		LOGGER.info("app boot image sizeCode:" + sizeCode + ",cityCode:" + cityCode);

		PageInfo pageInfo = new PageInfo();
		try {
			appBootService.queryAppBootImage(sizeCode, cityCode, pageInfo);
			
			if (pageInfo.getRows() != null && pageInfo.getRows().size() > 0) {
				
				List<AppBootImageVo> voList = new ArrayList<AppBootImageVo>();
				
				for (int i = 0; i < pageInfo.getRows().size(); i++) {
					AppBootImage abiDo = (AppBootImage) pageInfo.getRows().get(i);
					AppBootImageVo abiVo = new AppBootImageVo();
					abiVo.setId(abiDo.getId());
					abiVo.setImgUrl(abiDo.getImgUrl());
					abiVo.setSizeCode(abiDo.getSizeCode());
					
					voList.add(abiVo);
				}
				
				pageInfo.setTotal(voList.size());
				pageInfo.setRows(voList);
			}
		} catch (Exception e) {
			e.printStackTrace();
			pageInfo.setCode(ResultInfo.SERVER_ERROR.getCode());
			pageInfo.setMsg(ResultInfo.SERVER_ERROR.getMsg());
		}

		return pageInfo;
	}

	/**
	 * APP升级接口
	 * 
	 * @param response res
	 * @param request req
	 * @param version ver
	 * @param type tp
	 * @return pageInfo
	 */
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public @ResponseBody Object update(HttpServletResponse response, HttpServletRequest request, String version,
			Integer type) {
		
		LOGGER.info("app update" + version + "," + type);

		PageInfo pageInfo = new PageInfo();
		try {
			if (StringUtils.isBlank(version)) {
				pageInfo.setCode(ResultInfo.NO_DATA.getCode());
				pageInfo.setMsg("已是最新版本");
			} else {
				appBootService.queryUpdateVersion(version, type, pageInfo);
			}
		} catch (Exception e) {
			e.printStackTrace();
			pageInfo.setCode(ResultInfo.SERVER_ERROR.getCode());
			pageInfo.setMsg(ResultInfo.SERVER_ERROR.getMsg());
		}

		return pageInfo;
	}
	
}
