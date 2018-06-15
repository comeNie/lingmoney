package com.mrbt.lingmoney.admin.controller.bank;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mrbt.lingmoney.admin.service.bank.HxEnterpriseAgentService;
import com.mrbt.lingmoney.utils.PageInfo;
import com.mrbt.lingmoney.utils.ResultParame.ResultInfo;

/**
 * 企业经办人
 *
 * @author syb
 * @date 2017年9月29日 上午11:28:25
 * @version 1.0
 **/
@Controller
@RequestMapping("/bank/hxEnterpriseAgent")
public class HxEnterpriseAgentController {
	@Autowired
	private HxEnterpriseAgentService hxEnterpriseAgentService;

	/**
	 * 经办人信息变更
	 * @param bankNo  E账户账号
	 * @return 返回
	 */
	@RequestMapping(value = "/updateInfo", method = RequestMethod.POST)
	public @ResponseBody Object updateInfo(String bankNo) {
		String logGroup = "\nhxEnterpriseAgentUpdateInfo_" + System.currentTimeMillis() + "_";

		try {
			return hxEnterpriseAgentService.requestUpdateEnterpriseAgentInfo("PC", 0, bankNo, logGroup);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * 查询变更结果
	 * @author syb
	 * @date 2017年10月10日 下午6:47:08
	 * @version 1.0
	 * @param id	查询ID
	 * @return	返回处理结果
	 *
	 */
	@RequestMapping(value = "/queryResult", method = RequestMethod.POST)
	public @ResponseBody Object queryResult(String id) {
		String logGroup = "\nhxEnterpriseAgentQueryResult_" + System.currentTimeMillis() + "_";
		PageInfo pageInfo = new PageInfo();

		try {
			pageInfo = hxEnterpriseAgentService.queryUpdateEnterpriseAgentInfoResult(id, logGroup);
		} catch (Exception e) {
			pageInfo.setCode(ResultInfo.SERVER_ERROR.getCode());
			pageInfo.setMsg(ResultInfo.SERVER_ERROR.getMsg());
			e.printStackTrace();
		}

		return pageInfo;
	}

	/**
	 * 查询企业经办人变更记录
	 * 
	 * @author syb
	 * @date 2017年10月10日 下午7:26:19
	 * @version 1.0
	 * @param page	当前页数
	 * @param rows	每页条数
	 * @param eacId 企业账户id
	 * @return	返回处理结果
	 *
	 */
	@RequestMapping(value = "/listTransactor", method = RequestMethod.POST)
	public @ResponseBody Object listTransactor(Integer page, Integer rows, String eacId) {
		PageInfo pageInfo = new PageInfo();

		try {
			pageInfo = hxEnterpriseAgentService.listTransactor(page, rows, eacId);
		} catch (Exception e) {
			pageInfo.setCode(ResultInfo.SERVER_ERROR.getCode());
			pageInfo.setMsg(ResultInfo.SERVER_ERROR.getMsg());
			e.printStackTrace();
		}

		return pageInfo;
	}

}
