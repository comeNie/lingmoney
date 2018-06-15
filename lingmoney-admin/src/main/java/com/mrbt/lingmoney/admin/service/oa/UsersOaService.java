package com.mrbt.lingmoney.admin.service.oa;

import com.mrbt.lingmoney.utils.PageInfo;

/**
 * 用户相关基础接口
 * 
 * @author YJQ
 *
 */
public interface UsersOaService {

	/**
	 * 用户注册
	 * 
	 * @author ZAB 2017年11月28日
	 * @param employeeID
	 *            员工号
	 * @param name
	 *            姓名
	 * @param department
	 *            部门
	 * @param compName
	 *            公司
	 * @param telephone
	 *            电话
	 * @return 数据返回
	 * @throws Exception
	 *             异常
	 */
	PageInfo regist(String employeeID, String name, String department, String compName, String telephone)
			throws Exception;
	
	/**
	 * 修改员工信息
	 * 
	 * @param employeeID
	 *            员工工号
	 * @param department
	 *            部门
	 * @param compName
	 *            公司
	 * @param status
	 *            是否离职，0正常，1离职
	 * @return 数据返回
	 * @throws Exception
	 *             异常
	 */
	PageInfo empolyeeModify(String employeeID, String department, String compName, int status) throws Exception;
	
	/**
	 * 客户员工关系绑定及修改
	 * 
	 * @author ZAB 2017年11月28日
	 * @param employeeID
	 *            员工号
	 * @param telephone
	 *            客户手机号
	 * @param idCard
	 *            客户身份证号
	 * @return 数据返回
	 * @throws Exception
	 *             异常
	 */
	PageInfo bandRelations(String employeeID, String telephone, String idCard) throws Exception;
}
