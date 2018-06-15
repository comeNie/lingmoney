package com.mrbt.lingmoney.web2.controller.info;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mrbt.lingmoney.model.FinancialManagement;
import com.mrbt.lingmoney.service.discover.FinancialClassService;
import com.mrbt.lingmoney.utils.PageInfo;
import com.mrbt.lingmoney.utils.ResultParame.ResultInfo;

/**
 * @author syb
 * @date 2017年5月4日 下午3:24:04
 * @version 1.0
 * @description 《理财课堂》
 **/
@Controller
@RequestMapping("/financialClass")
public class FinancialClassController {

	private static final Logger LOG = LogManager.getLogger(FinancialClassController.class);

	@Autowired
	private FinancialClassService financialClassService;

	/**
	 * 获取理财课堂信息列表
	 * 
	 * @param pageNo pageNo
	 * @param pageSize pageSize
	 * @return pageInfo
	 */
	@RequestMapping(value = "/getList", method = RequestMethod.POST)
	public @ResponseBody Object getList(Integer pageNo, Integer pageSize) {
		LOG.info("获取理财课堂信息列表 \t" + pageNo + "\t" + pageSize);
		PageInfo pageInfo = new PageInfo();
		try {
			pageInfo = financialClassService.getIndexInfo(pageNo, pageSize);
		} catch (Exception e) {
			pageInfo.setCode(ResultInfo.SERVER_ERROR.getCode());
			pageInfo.setMsg(ResultInfo.SERVER_ERROR.getMsg());
			e.printStackTrace();
			
			LOG.error("获取理财课堂信息列表失败，系统错误。" + "\n" + e.getMessage());
		}
		return pageInfo;
	}

	/**
	 * 根据ID获取详情
	 * 
	 * @param id id
	 * @return pageInfo
	 */
	@RequestMapping(value = "/getDetailById", method = RequestMethod.POST)
	public @ResponseBody Object getDetailById(Integer id) {
		LOG.info("获取理财课堂详情:" + id);
		PageInfo pageInfo = new PageInfo();
		try {
			FinancialManagement res = financialClassService.getDetailById(id);
			if (res != null) {
				pageInfo.setCode(ResultInfo.SUCCESS.getCode());
				pageInfo.setMsg(ResultInfo.SUCCESS.getMsg());
				pageInfo.setObj(res);
			} else {
				pageInfo.setCode(ResultInfo.EMPTY_DATA.getCode());
				pageInfo.setMsg(ResultInfo.EMPTY_DATA.getMsg());
			}
		} catch (Exception e) {
			pageInfo.setCode(ResultInfo.SERVER_ERROR.getCode());
			pageInfo.setMsg(ResultInfo.SERVER_ERROR.getMsg());
			e.printStackTrace();
			LOG.error("获取理财课堂详情失败，系统错误。 id:" + id + "\n" + e.getMessage());
		}
		return pageInfo;
	}
}
