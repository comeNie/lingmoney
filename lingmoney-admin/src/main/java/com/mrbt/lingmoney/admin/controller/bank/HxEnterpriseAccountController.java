package com.mrbt.lingmoney.admin.controller.bank;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mrbt.lingmoney.admin.service.bank.HxEnterpriseAccountService;
import com.mrbt.lingmoney.utils.PageInfo;
import com.mrbt.lingmoney.utils.ResultParame.ResultInfo;

/**
 *
 * @author syb
 * @date 2017年9月28日 下午4:03:25
 * @version 1.0 企业账户
 **/
@Controller
@RequestMapping("/bank/enterpriseAccount")
public class HxEnterpriseAccountController {

	@Autowired
	private HxEnterpriseAccountService hxEnterpriseAccountService;

    /**
     * 企业开户 如果该企业证件号已存在，执行更新操作
     * 
     * @author yiban
     * @date 2017年12月6日 下午4:25:24
     * @version 1.0
     * @param companyName 企业名称
     * @param occCodeNo 企业注册证件号
     * @return	返回处理结果
     *
     */
	@RequestMapping(value = "/requestAccountOpen", method = RequestMethod.POST)
	public @ResponseBody Object requestAccountOpen(String companyName, String occCodeNo) {
		String logGroup = "\nenterpriseAccountOpenRequest_" + System.currentTimeMillis() + "_";

		try {
			return hxEnterpriseAccountService.accountOpen("PC", 0, companyName, occCodeNo, logGroup);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
    /**
     * 查询企业开户结果
     * 
     * @author yiban
     * @date 2017年12月6日 下午4:25:43
     * @version 1.0
     * @param id 企业账户id
     * @return	返回查询结果
     *
     */
	@RequestMapping(value = "/queryAccountOpenResult", method = RequestMethod.POST)
	public @ResponseBody Object queryAccountOpenResult(String id) {
		String logGroup = "\nqueryEnterpriseAccountOpenResult" + System.currentTimeMillis() + "_";

		PageInfo pageInfo = new PageInfo();
		try {
			pageInfo = hxEnterpriseAccountService.queryAccountOpenResult(id, logGroup);
		} catch (Exception e) {
			pageInfo.setCode(ResultInfo.SERVER_ERROR.getCode());
			pageInfo.setMsg(ResultInfo.SERVER_ERROR.getMsg());
			e.printStackTrace();
		}
		
		return pageInfo;
	}

    /**
     * 分页查询企业账户
     * 
     * @author yiban
     * @date 2017年12月6日 下午4:25:56
     * @version 1.0
     * @param companyName
     *            企业名
     * @param bankNo
     *            银行账号
     * @param status
     *            状态
     * @param page 当前页数
     * @param rows	每页条数
     * @return	返回数据列表
     *
     */
	@RequestMapping(value = "/listEnterpriseAccount", method = RequestMethod.POST)
	public @ResponseBody Object listEnterpriseAccount(String companyName, String bankNo, Integer status, Integer page,
			Integer rows) {
		PageInfo pageInfo = new PageInfo();
		try {
			pageInfo = hxEnterpriseAccountService.listEnterpriseAccount(companyName, bankNo, status, page, rows);
		} catch (Exception e) {
			pageInfo.setCode(ResultInfo.SERVER_ERROR.getCode());
			pageInfo.setMsg(ResultInfo.SERVER_ERROR.getMsg());
			e.printStackTrace();
		}

		return pageInfo;
	}
	
	/**
	 * 查询企业账户余额
	 * @param companyNameId	企业账户数据ID
	 * @return 返回查询结果
	 */
	@RequestMapping(value = "/queryCompanyBalance", method = RequestMethod.POST)
	public @ResponseBody Object queryCompanyBalance(String companyNameId) {
		PageInfo pageInfo = new PageInfo();
		try {
			pageInfo = hxEnterpriseAccountService.queryCompanyBalance(companyNameId);
		} catch (Exception e) {
			pageInfo.setCode(ResultInfo.SERVER_ERROR.getCode());
			pageInfo.setMsg(ResultInfo.SERVER_ERROR.getMsg());
			e.printStackTrace();
		}

		return pageInfo;
	}
	
	/**
	 * 企业账户提现
	 * @param companyNameId	企业账户数据ID
	 * @param amount	提现金额
	 * @return	返回
	 */
	@RequestMapping(value = "/withdrawals", method = RequestMethod.POST)
	public @ResponseBody Object withdrawals(String companyNameId, String amount) {
		return hxEnterpriseAccountService.withdrawals(companyNameId, amount);
	}

}
