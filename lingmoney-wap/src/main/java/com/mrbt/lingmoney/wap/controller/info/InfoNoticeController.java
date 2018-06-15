package com.mrbt.lingmoney.wap.controller.info;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mrbt.lingmoney.service.info.InfoNoticeService;
import com.mrbt.lingmoney.utils.Common;
import com.mrbt.lingmoney.utils.PageInfo;
import com.mrbt.lingmoney.utils.ResultParame.ResultInfo;
import com.mrbt.lingmoney.wap.vo.info.InfoNoticeVo;

/**
 * @author lihq
 * @date 2017年7月6日 下午4:28:08
 * @description
 * @version 1.0
 * @since 2017-04-12
 */
@Controller
@RequestMapping("/info")
public class InfoNoticeController {
	private static final Logger LOGGER = LogManager.getLogger(InfoNoticeController.class);

	@Autowired
	private InfoNoticeService infoNoticeService;

	/**
	 * 查询公告列表
	 * 
	 * @author YJQ 2017年4月18日
	 * @param pageNo pageNo
	 * @param pageSize pageSize
	 * @return pageInfo
	 */
	@RequestMapping(value = "/queryNoticeList")
	@ResponseBody
	public Object queryNoticeList(Integer pageNo, Integer pageSize) {
		LOGGER.info("info-查询公告列表");
		PageInfo pageInfo = new PageInfo();
		try {
			pageInfo = infoNoticeService.queryNoticeList(pageNo, pageSize);
			pageInfo.setRows(Common.copyPropertyToList(pageInfo.getRows(), InfoNoticeVo.class));
		} catch (Exception e) {
			pageInfo.setCode(ResultInfo.SERVER_ERROR.getCode());
			pageInfo.setMsg("公告列表查询失败");
			e.printStackTrace();
		}
		return pageInfo;
	}

	/**
	 * 查询公告详情
	 * 
	 * @author YJQ 2017年4月18日
	 * @param id id
	 * @return pageInfo
	 */
	@RequestMapping(value = "/queryNoticeDetail")
	@ResponseBody
    public Object queryNoticeDetail(Integer id) {
		LOGGER.info("info-查询公告[" + id + "]详情 ");
		PageInfo pageInfo = null;
		try {
			pageInfo = infoNoticeService.queryNoticeDetail(id);
		} catch (Exception e) {
			pageInfo = new PageInfo();
			pageInfo.setCode(ResultInfo.SERVER_ERROR.getCode());
			pageInfo.setMsg("公告详情查询失败");
			e.printStackTrace();
		}
		return pageInfo;
	}

}
