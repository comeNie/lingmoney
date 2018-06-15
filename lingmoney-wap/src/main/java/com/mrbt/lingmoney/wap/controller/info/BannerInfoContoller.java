package com.mrbt.lingmoney.wap.controller.info;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mrbt.lingmoney.model.InfoClientBanner;
import com.mrbt.lingmoney.service.common.BannerInfoService;
import com.mrbt.lingmoney.utils.PageInfo;
import com.mrbt.lingmoney.utils.ResultParame.ResultInfo;
import com.mrbt.lingmoney.utils.ResultParame.ResultNumber;
import com.mrbt.lingmoney.wap.vo.info.InfoClientBannerVo;
/**
 * @author lihq
 * @date 2017年6月6日 下午3:01:16
 * @description
 * @version 1.0
 * @since 2017-04-12
 */
@Controller
@RequestMapping("/bannerInfo")
public class BannerInfoContoller {
	
	private static final Logger LOGGER = LogManager.getLogger(BannerInfoContoller.class);
	
	@Autowired
	private BannerInfoService bannerInfoService;
	
	/**
	 * 首页banner
	 * 
	 * @param response req
	 * @param request res
	 * @param cityCode cityCode
	 * @param sizeCode sizeCode
	 * @return pageInfo
	 */
	@RequestMapping(value = "/homeMainBanner", method = RequestMethod.POST)
	public @ResponseBody Object homeMainBanner(HttpServletResponse response, HttpServletRequest request,
			String sizeCode, String cityCode) {
		
		LOGGER.info("bannerInfo sizeCode:" + sizeCode + ",cityCode:" + cityCode);

		PageInfo pageInfo = new PageInfo();
		try {
			bannerInfoService.queryhomeMainBanner(sizeCode, cityCode, pageInfo);
			
			//包装数据
			List<?> listDO = pageInfo.getRows();
			List<InfoClientBannerVo> bannerInfo = new ArrayList<InfoClientBannerVo>();
			
			if (listDO != null && listDO.size() > ResultNumber.ONE.getNumber()) {
				for (int i = 0; i < listDO.size(); i++) {
					InfoClientBanner icbDO = (InfoClientBanner) listDO.get(i);
					InfoClientBannerVo icbVO = new InfoClientBannerVo();
					icbVO.setId(icbDO.getId());
					icbVO.setDefultPath(icbDO.getDefultPath());
					icbVO.setName(icbDO.getName());
					icbVO.setUrl(icbDO.getUrl());
					bannerInfo.add(icbVO);
				}
				pageInfo.setRows(bannerInfo);
			}
		} catch (Exception e) {
			e.printStackTrace();
			pageInfo.setCode(ResultInfo.SERVER_ERROR.getCode());
			pageInfo.setMsg(ResultInfo.SERVER_ERROR.getMsg());
		}

		return pageInfo;
	}
}
