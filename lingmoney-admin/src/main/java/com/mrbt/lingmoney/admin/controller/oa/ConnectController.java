package com.mrbt.lingmoney.admin.controller.oa;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mrbt.lingmoney.admin.service.oa.UsersOaService;
import com.mrbt.lingmoney.utils.PageInfo;
import com.mrbt.lingmoney.utils.ResultParame.ResultInfo;
/**
 * 与OA部门进行数据对接相关接口
 * @author 
 *
 */
@Controller
@RequestMapping("/abutment")
public class ConnectController {

	Logger log = LogManager.getLogger(ConnectController.class);

	@Autowired
	private UsersOaService usersOaService;
	
	/**
	 * 内部员工注册零钱儿接口，默认登录为手机号，默认密码0000
	 * 要求，判断员工是否添加，如果没有添加，写入到员工表中
	 * 进行注册逻辑
	 * 添加员工和注册信息关联表
	 * @param employeeID	员工ID
	 * @param name	员工姓名
	 * @param department	部门
	 * @param compName	公司
	 * @param telephone	手机号
	 * @return	返回处理结果
	 */
	@RequestMapping(value = "/employeeRegist", method = RequestMethod.POST)
	public @ResponseBody Object employeeRegist(String employeeID, String name, String department, String compName, String telephone) {
		log.info("users-用户注册");
		PageInfo pageInfo = new PageInfo();
		try {
			if (StringUtils.isBlank(employeeID) || StringUtils.isBlank(name) || StringUtils.isBlank(department) || StringUtils.isBlank(compName) || StringUtils.isBlank(telephone)) {
				pageInfo.setCode(ResultInfo.PARAMETER_ERROR.getCode());
				pageInfo.setMsg(ResultInfo.PARAMETER_ERROR.getMsg());
			} else {
				pageInfo = usersOaService.regist(employeeID, name, department, compName, telephone);
			}
		} catch (Exception e) {
			e.printStackTrace();
			pageInfo.setCode(ResultInfo.SERVER_ERROR.getCode());
			pageInfo.setMsg(ResultInfo.SERVER_ERROR.getMsg());
		}
		return pageInfo;
	}
	
	/**
	 * 用户与员工关系绑定
	 * @author ZAB 2017年11月28日
	 * @param employeeID 员工号
	 * @param idCard 用户身份证号
	 * @param telephone   用户手机号
	 * @return	返回结果
	 */
	@RequestMapping(value = "/bandRelations", method = RequestMethod.POST)
	public @ResponseBody Object bandRelations(String employeeID, String idCard, String telephone) {
		log.info("users-员工用户绑定");
		PageInfo pageInfo = new PageInfo();
		try {
			if (StringUtils.isBlank(employeeID) || StringUtils.isBlank(idCard) || StringUtils.isBlank(telephone)) {
				pageInfo.setCode(ResultInfo.PARAMETER_ERROR.getCode());
				pageInfo.setMsg(ResultInfo.PARAMETER_ERROR.getMsg());
			} else {
				pageInfo = usersOaService.bandRelations(employeeID, telephone, idCard);
			}
		} catch (Exception e) {
			e.printStackTrace();
			pageInfo.setCode(ResultInfo.SERVER_ERROR.getCode());
			pageInfo.setMsg(ResultInfo.SERVER_ERROR.getMsg());
		}
		return pageInfo;
		
	}
	
	/**
	 * 员工信息修改
	 * @author ZAB 2017年11月28日
	 * @param employeeID 员工号
	 * @param department  部门
	 * @param compName 公司
	 * @param status  在职离职状态
	 * @return	返回处理结果
	 */
	@RequestMapping(value = "/modify", method = RequestMethod.POST)
	public  @ResponseBody Object modify(String employeeID, String department, String compName, int status) {
		log.info("users-员工信息修改");
		PageInfo pageInfo = new PageInfo();
		try {
			if (StringUtils.isBlank(employeeID) || StringUtils.isBlank(department) || StringUtils.isBlank(compName)) {
				pageInfo.setCode(ResultInfo.PARAMETER_ERROR.getCode());
				pageInfo.setMsg(ResultInfo.PARAMETER_ERROR.getMsg());
			} else {
				pageInfo = usersOaService.empolyeeModify(employeeID, department, compName, status);
			}
		} catch (Exception e) {
			e.printStackTrace();
			pageInfo.setCode(ResultInfo.SERVER_ERROR.getCode());
			pageInfo.setMsg(ResultInfo.SERVER_ERROR.getMsg());
		}
		return pageInfo;
		
	}
}
