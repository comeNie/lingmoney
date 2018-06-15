package com.mrbt.lingmoney.admin.controller.info;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mrbt.lingmoney.admin.service.info.UsersBankService;
import com.mrbt.lingmoney.model.UsersBank;
import com.mrbt.lingmoney.utils.MyUtils;
import com.mrbt.lingmoney.utils.PageInfo;
import com.mrbt.lingmoney.utils.ResultParame.ResultInfo;

/**
 * 京东绑卡信息
 * 
 * @author lihq
 * @date 2017年5月15日 上午10:08:13
 * @description
 * @version 1.0
 * @since 2017-04-12
 */
@Controller
@RequestMapping("/user/bindBank")
public class BindBankController {
	
	private Logger log = MyUtils.getLogger(BindBankController.class);
	
	@Autowired
	private UsersBankService usersBankService;

	/**
	 * 根据条件查询用户绑卡信息
	 * 
	 * @param vo	vo
	 * @param page 当前页数
	 * @param rows 每页显示的条数
	 * @return	return
	 */
	@RequestMapping("list")
	public @ResponseBody Object list(UsersBank vo, Integer page, Integer rows) {
		log.info("/user/bindBank/list");
		PageInfo pageInfo = new PageInfo(page, rows);
		Map<String, Object> condition;
		try {
			condition = new HashMap<String, Object>();
			if (vo != null) {
				// 用户手机号
				if (StringUtils.isNotBlank(vo.getUserTel())) {
					condition.put("userTel", vo.getUserTel());
				}
				// 用户真实姓名
				if (StringUtils.isNotBlank(vo.getUserName())) {
					condition.put("userName", vo.getUserName());
				}
			}
			pageInfo.setCondition(condition);
			usersBankService.findDataGrid(pageInfo);
		} catch (Exception e) {
			e.printStackTrace();
			log.error("根据条件查询用户绑卡信息，失败原因是：" + e);
			pageInfo.setCode(ResultInfo.SERVER_ERROR.getCode());
			pageInfo.setMsg(ResultInfo.SERVER_ERROR.getMsg());
		}
		return pageInfo;
	}

}
