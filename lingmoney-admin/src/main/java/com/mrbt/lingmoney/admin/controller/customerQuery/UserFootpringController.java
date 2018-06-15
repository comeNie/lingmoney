package com.mrbt.lingmoney.admin.controller.customerQuery;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mrbt.lingmoney.admin.service.customerQuery.UserFootprintService;

/**
 * @author yiban sun
 * @date 2017年8月24日 上午9:05:58
 * @version 1.0
 * @description 埋点记录查看
 * @since
 **/
@Controller
@RequestMapping("/user")
public class UserFootpringController {

	@Autowired
	private UserFootprintService userFootprintService;
	
	/**
	 * 根据条件查询用户操作记录
	 * 
	 * @author syb
	 * @date 2017年9月27日 下午4:40:21
	 * @version 1.0
	 * @param account
	 *            用户账号，全模糊查询
	 * @param tel
	 *            用户手机号，全模糊查询
	 * @param page
	 *            当前页
	 * @param rows
	 *            每页条数
	 * @param date
	 *            日期
	 * @return	返回结果
	 *
	 */
	@RequestMapping("/viewFootprint")
	public @ResponseBody Object viewFootprint(String account, String tel, Integer page, Integer rows, String date) {
		
		return userFootprintService.viewFootprint(account, tel, page, rows, date);
		
	}
}
