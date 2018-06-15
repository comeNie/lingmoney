package com.mrbt.lingmoney.admin.controller.bank;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mrbt.lingmoney.admin.service.bank.HxUsersAccountRepaymentRecordService;
import com.mrbt.lingmoney.utils.PageInfo;
import com.mrbt.lingmoney.utils.ResultParame.ResultInfo;

/**
 *流标/撤标 回款
 *@author syb
 *@date 2017年9月8日 下午2:04:03
 *@version 1.0
 **/
@Controller
@RequestMapping("/bank")
public class HxUsersAccountRepaymentRecordController {
	@Autowired
	private HxUsersAccountRepaymentRecordService hxUsersAccountRepaymentRecordService;

	/**
	 * 分页查询回款记录
	 * 
	 * @author yiban
	 * @date 2017年12月7日 上午9:43:25
	 * @version 1.0
	 * @param name 姓名
	 * @param tel 手机号
	 * @param initTime 入库时间
	 * @param page	页数
	 * @param rows	条数
	 * @param status 状态
	 * @return	返回列表
	 *
	 */
	@RequestMapping(value = "/queryUsersAccountRepaymentWithSelfCondition", method = RequestMethod.POST)
	public @ResponseBody Object queryUsersAccountRepaymentWithSelfCondition(String name, String tel, String initTime,
			Integer page, Integer rows, Integer status) {
		PageInfo pageInfo = new PageInfo();

		try {
			pageInfo = hxUsersAccountRepaymentRecordService.queryWithSelfCondition(tel, name, initTime, page, rows, status);

		} catch (Exception e) {
			pageInfo.setCode(ResultInfo.SERVER_ERROR.getCode());
			pageInfo.setMsg(ResultInfo.SERVER_ERROR.getMsg());
			e.printStackTrace();
		}
		return pageInfo;
	}

    /**
     * 手动恢复
     * @author yiban
     * @date 2017年12月7日 上午9:44:05
     * @version 1.0
     * @param id	数据ID
     * @return	处理结果	
     */
	@RequestMapping(value = "/manualRecoverPayment", method = RequestMethod.POST)
	public @ResponseBody Object manualRecoverPayment(String id) {
		PageInfo pageInfo = new PageInfo();
		try {
			pageInfo = hxUsersAccountRepaymentRecordService.manualRecoverPayment(id);
		} catch (Exception e) {
			pageInfo.setCode(ResultInfo.SERVER_ERROR.getCode());
			pageInfo.setMsg(ResultInfo.SERVER_ERROR.getMsg());
			e.printStackTrace();
		}
		return pageInfo;
	}
}
