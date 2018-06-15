package com.mrbt.lingmoney.admin.service.oa.impl;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import com.mrbt.lingmoney.admin.mongo.MongoSynUserData;
import com.mrbt.lingmoney.admin.service.oa.UsersOaService;
import com.mrbt.lingmoney.mapper.EmployeeRelationsMappingMapper;
import com.mrbt.lingmoney.mapper.UsersAccountMapper;
import com.mrbt.lingmoney.mapper.UsersMapper;
import com.mrbt.lingmoney.mapper.UsersPropertiesMapper;
import com.mrbt.lingmoney.model.EmployeeRelationsMapping;
import com.mrbt.lingmoney.model.EmployeeRelationsMappingExample;
import com.mrbt.lingmoney.model.Users;
import com.mrbt.lingmoney.model.UsersAccount;
import com.mrbt.lingmoney.model.UsersExample;
import com.mrbt.lingmoney.model.UsersProperties;
import com.mrbt.lingmoney.model.UsersPropertiesExample;
import com.mrbt.lingmoney.utils.MD5Utils;
import com.mrbt.lingmoney.utils.PageInfo;
import com.mrbt.lingmoney.utils.ResultParame;
import com.mrbt.lingmoney.utils.ShortUUID;

/**
 * 与OA部门对接接口处理逻辑
 * @author Administrator
 *
 */
@Service
public class UsersOaServiceImpl implements UsersOaService {

	@Autowired
	private EmployeeRelationsMappingMapper employeeRelationsMappingMapper;
	@Autowired
	private UsersAccountMapper usersAccountMapper;
	@Autowired
	private UsersMapper usersMapper;
	@Autowired
	private UsersPropertiesMapper usersPropertiesMapper;
	@Autowired
	private MongoTemplate mongoTemplate;
	
	private String usersPws = MD5Utils.MD5("000000");
	
	@Override
	public PageInfo regist(String employeeID, String name, String department, String compName, String telephone)
			throws Exception {
		PageInfo pageInfo = new PageInfo();
		
		//判断员工是否存在
		EmployeeRelationsMappingExample ermeExample = new EmployeeRelationsMappingExample();
		ermeExample.createCriteria().andEmployeeidEqualTo(employeeID);
		
		long ermeCount = employeeRelationsMappingMapper.countByExample(ermeExample);
		if (ermeCount > ResultParame.ResultNumber.ZERO.getNumber()) {
			pageInfo.setCode(ResultParame.ResultInfo.RETURN_EMPTY_DATA.getCode());
			pageInfo.setMsg("员工已经存在");
			pageInfo.setObj(employeeRelationsMappingMapper.selectByExample(ermeExample));
			return pageInfo;
		}
		
		//生成注册信息，关系到users, users_properties, users_account
		//判断是否已经注册
		UsersExample usersExample = new UsersExample();
		usersExample.createCriteria().andTelephoneEqualTo(telephone);
		long usersCount = usersMapper.countByExample(usersExample);
		if (usersCount > ResultParame.ResultNumber.ZERO.getNumber()) {
			pageInfo.setCode(ResultParame.ResultInfo.EMPLOYEE_REGISTER_LINGMOMEY.getCode());
			pageInfo.setMsg(ResultParame.ResultInfo.EMPLOYEE_REGISTER_LINGMOMEY.getMsg());
			UsersProperties uProperties = usersPropertiesMapper.selectByTelephone(telephone);
			pageInfo.setObj(uProperties);
			return pageInfo;
		}
		
		//插入users数据
		String userId = UUID.randomUUID().toString().replace("-", "");
		Users users = new Users();
		users.setId(userId);
		users.setChannel(0);
		users.setLoginAccount(telephone);
		users.setLoginPsw(usersPws);
		users.setTelephone(telephone);
		users.setType(0);
		usersMapper.insertSelective(users);
		
		//生成员工推荐码
		String employeeRcode = ShortUUID.generateShortUuid();
		//插入员工信息
		EmployeeRelationsMapping erm = new EmployeeRelationsMapping();
		erm.setCompName(compName);
		erm.setDepartment(department);
		erm.setEmployeeid(employeeID);
		erm.setEmployeeName(name);
		erm.setStatus(0);
		erm.setType(0);
		erm.setNewid(employeeRcode);
		
		//插入用户信息表
		UsersProperties usersProperties = new UsersProperties();
		usersProperties.setName(name);
		usersProperties.setBank(0); // 默认未绑定银行卡
		usersProperties.setCertification(0); // 默认未通过身份验证
		usersProperties.setLevel(1); // 默认会员等级为零级
		usersProperties.setReferralCode(employeeRcode); // 推荐码
		usersProperties.setRegDate(new Date()); // 注册日期为当天
		usersProperties.setuId(users.getId()); // 对应用户
		usersProperties.setAutoPay(0);
		usersProperties.setFirstBuy(0);
		usersProperties.setReferralId(users.getId());
		usersProperties.setUserLevel(0);
		usersProperties.setRecommendedLevel(users.getId() + "");
		
		//插入用户account表
		UsersAccount usersAccount = new UsersAccount();
		usersAccount.setuId(users.getId());
		usersAccount.setCountLingbao(0);
		
		employeeRelationsMappingMapper.insert(erm);
		usersPropertiesMapper.insert(usersProperties);
		usersAccountMapper.insertSelective(usersAccount);
		
		pageInfo.setCode(ResultParame.ResultInfo.SUCCESS.getCode());
		pageInfo.setMsg("添加完成，员工在零钱儿的用户名为:" + telephone + "\t密码为:" + "000000, 请尽快修改密码！");
		pageInfo.setObj(employeeRcode);
		
		return pageInfo;
	}
	
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
	 */
	@Override
	public PageInfo bandRelations(String employeeID, String telephone, String idCard) throws Exception {
		PageInfo pageInfo = new PageInfo();
		//判断员工是否存在
		EmployeeRelationsMappingExample ermeExample = new EmployeeRelationsMappingExample();
		ermeExample.createCriteria().andEmployeeidEqualTo(employeeID);
		
		List<EmployeeRelationsMapping> ermList = employeeRelationsMappingMapper.selectByExample(ermeExample);
		if (ermList != null && ermList.size() > 0) {
			
			//员工的推荐码
			String employeeRCode = ermList.get(0).getNewid();
			//获取员工的u_id
			UsersPropertiesExample upExample = new UsersPropertiesExample();
			upExample.createCriteria().andReferralCodeEqualTo(employeeRCode);
			
			List<UsersProperties> emupList = usersPropertiesMapper.selectByExample(upExample);
			if (emupList != null && emupList.size() > 0) {
				
				UsersProperties emup = emupList.get(0);
				
				String[] tels = telephone.split(",");
				
				int u = 0;
				
				for (int i = 0; i < tels.length; i++) {
					//用手机号查询用户是否存在
					UsersProperties up2 = usersPropertiesMapper.selectByTelephone(tels[0]);
					if (up2 != null) {
						u++;
						if (up2.getIdCard() != null && !up2.getIdCard().equals("")) {
							if (!up2.getIdCard().equals(idCard)) {
								pageInfo.setCode(ResultParame.ResultInfo.IDCARD_ERROR.getCode());
								pageInfo.setMsg(telephone + "与用户的身份证号不符");
								return pageInfo;
							}
						}
						
						//判断如果用户有推荐人，判断是否已经绑定了该理财经理，如果是，直接跳过
						if (up2.getReferralId() != null) {
							if (up2.getReferralId().equals(emup.getuId())) {
								continue;
							}
						}
						
						UsersProperties modifyUserpro = new UsersProperties();
						modifyUserpro.setId(up2.getId());
						modifyUserpro.setReferralId(emup.getuId());
						modifyUserpro.setUserLevel(1);
						modifyUserpro.setRecommendedLevel(emup.getuId() + "," + up2.getuId());
						if (up2.getIsTransfer() == 0 && usersPropertiesMapper.selectTradingCountByuId(up2.getuId()) > 0) {
							modifyUserpro.setIsTransfer(1);
						}
						
						//修改数据
						usersPropertiesMapper.updateByPrimaryKeySelective(modifyUserpro);
						//修改mongodb中的数据
						mongoTemplate.upsert(new Query(Criteria.where("uId").is(up2.getuId() + "")), new Update()
								.set("referralId", emup.getuId() + "").set("recommenderIsCode", emup.getReferralCode()),
								MongoSynUserData.class);
						
						//查询用户推荐的用户
						UsersPropertiesExample reExample = new UsersPropertiesExample();
						reExample.createCriteria().andReferralIdEqualTo(up2.getuId());
						
						List<UsersProperties> reList = usersPropertiesMapper.selectByExample(reExample);
						for (UsersProperties upx : reList) {
							UsersProperties modifyUserpro2 = new UsersProperties();
							modifyUserpro2.setId(upx.getId());
							modifyUserpro2.setUserLevel(ResultParame.ResultNumber.TWO.getNumber());
							if (upx.getIsTransfer() == 0 && usersPropertiesMapper.selectTradingCountByuId(upx.getuId()) > 0) {
								modifyUserpro2.setIsTransfer(1);
							}
							modifyUserpro2.setRecommendedLevel(emup.getuId() + "," + up2.getuId() + "," + upx.getuId());
							//修改数据
							usersPropertiesMapper.updateByPrimaryKeySelective(modifyUserpro2);
						}
					}
				}
				
				if (u > 0) {
					pageInfo.setCode(ResultParame.ResultInfo.SUCCESS.getCode());
					pageInfo.setMsg("修改完成");
				} else {
					pageInfo.setCode(ResultParame.ResultInfo.IDCARD_ERROR.getCode());
					pageInfo.setMsg("用户没有注册零钱儿");
				}
			} else {
				pageInfo.setCode(ResultParame.ResultInfo.EMPLOYEE_NOT_REGISTER_LINGMOMEY.getCode());
				pageInfo.setMsg(ResultParame.ResultInfo.EMPLOYEE_NOT_REGISTER_LINGMOMEY.getMsg());
				return pageInfo;
			}
		} else {
			pageInfo.setCode(ResultParame.ResultInfo.EMPLOYEE_NON_EXISTENT.getCode());
			pageInfo.setMsg(ResultParame.ResultInfo.EMPLOYEE_NON_EXISTENT.getMsg());
			return pageInfo;
		}
		return pageInfo;
	}
	
	/**
	 * @param employeeID
	 *            员工号
	 * @param department
	 *            部门
	 * @param compName
	 *            公司
	 * @param status
	 *            在离职状态
	 * @return 数据返回
	 */
	@Override
	public PageInfo empolyeeModify(String employeeID, String department, String compName, int status) throws Exception {
		PageInfo pageInfo = new PageInfo();
		
		//判断员工是否存在
		EmployeeRelationsMappingExample ermeExample = new EmployeeRelationsMappingExample();
		ermeExample.createCriteria().andEmployeeidEqualTo(employeeID);
		
		long ermeCount = employeeRelationsMappingMapper.countByExample(ermeExample);
		if (ermeCount > 0) {
			EmployeeRelationsMapping erm = new EmployeeRelationsMapping();
			erm.setCompName(compName);
			erm.setDepartment(department);
			erm.setStatus(status);

			EmployeeRelationsMappingExample example = new EmployeeRelationsMappingExample();
			example.createCriteria().andEmployeeidEqualTo(employeeID);
			long x = employeeRelationsMappingMapper.updateByExampleSelective(erm, example);
			if (x > 0) {
				pageInfo.setCode(ResultParame.ResultInfo.SUCCESS.getCode());
				pageInfo.setMsg("修改完成");
			} else {
				pageInfo.setCode(ResultParame.ResultInfo.MODIFY_FAIL.getCode());
				pageInfo.setMsg(ResultParame.ResultInfo.MODIFY_FAIL.getMsg());
			}
		} else {
			pageInfo.setCode(ResultParame.ResultInfo.EMPLOYEE_NON_EXISTENT.getCode());
			pageInfo.setMsg(ResultParame.ResultInfo.EMPLOYEE_NON_EXISTENT.getMsg());
		}
		return pageInfo;
	}
}
