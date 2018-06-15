package com.mrbt.lingmoney.admin.controller.info;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mrbt.lingmoney.admin.controller.base.BaseController;
import com.mrbt.lingmoney.admin.service.info.UsersPropertiesService;
import com.mrbt.lingmoney.model.UsersProperties;
import com.mrbt.lingmoney.utils.MyUtils;
import com.mrbt.lingmoney.utils.PageInfo;
import com.mrbt.lingmoney.utils.ResponseUtils;
import com.mrbt.lingmoney.utils.ResultParame.ResultInfo;
import com.mrbt.lingmoney.utils.ResultParame.ResultNumber;

/**
 * 页面设置——》推荐码绑定修改
 * 
 * @author lihq
 * @date 2017年5月12日 下午4:40:00
 * @description
 * @version 1.0
 * @since 2017-04-12
 */
@Controller
@RequestMapping("/user/transfer")
public class TransferController extends BaseController {
	private Logger log = MyUtils.getLogger(TransferController.class);
	
	@Autowired
	private UsersPropertiesService usersPropertiesService;

	/**
	 * 根据手机号查询当前用户
	 * 
	 * @param telephone	手机号
	 * @return	返回查询结果
	 */
	@RequestMapping("searchUserByTelephone")
	@ResponseBody
	public Object searchUserByTelephone(String telephone) {
		log.info("/user/transfer/searchUserByTelephone");
		if (telephone == null) {
			telephone = "";
		}
		UsersProperties usersProperties = usersPropertiesService.selectByTelephone(telephone);
		List<UsersProperties> list = new ArrayList<UsersProperties>();
		if (usersProperties != null) {
			list.add(usersProperties);
		}
		return list;
	}

	/**
	 * 根据手机号、推荐码查询当前用户
	 * 
	 * @param telephone	用户手机号
	 * @param referralCode	推荐码
	 * @return	返回查询结果
	 */
	@RequestMapping("searchUserByMap")
	@ResponseBody
	public Object searchUserByMap(String telephone, String referralCode) {
		log.info("/user/transfer/searchUserByMap");
		if (telephone == null) {
			telephone = "";
		}
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("telephone", telephone);
		map.put("referralCode", referralCode);
		UsersProperties usersProperties = usersPropertiesService.selectByMap(map);
		List<UsersProperties> list = new ArrayList<UsersProperties>();
		if (usersProperties != null) {
			list.add(usersProperties);
		}
		return list;
	}

	/**
	 * 根据推荐码查询当前用户
	 * @param referralCode	推荐码
	 * @return	查询结果
	 */
	@RequestMapping("searchUserByReferralCode")
	@ResponseBody
	public Object searchUserByReferralCode(String referralCode) {
		log.info("/user/transfer/searchUserByReferralCode");
		if (StringUtils.isBlank(referralCode)) {
			return new ArrayList<UsersProperties>();
		}

		UsersProperties usersProperties = usersPropertiesService.selectByReferralCode(referralCode);
		List<UsersProperties> list = new ArrayList<UsersProperties>();
		if (usersProperties != null) {
			list.add(usersProperties);
		}
		return list;
	}

	/**
	 * 根据手机号查询下级用户
	 * 
	 * @param telephone	用户手机号
	 * @param name	姓名
	 * @param referralCode	推荐码
	 * @return	返回查询结果
	 */
	@RequestMapping("searchRecByTelephone")
	@ResponseBody
	public Object searchRecByTelephone(String telephone, String name, String referralCode) {
		log.info("/user/transfer/searchRecByTelephone");
		if (telephone == null) {
			telephone = "";
		}

		UsersProperties usersProperties = usersPropertiesService.selectByTelephone(telephone);
		List<UsersProperties> list = new ArrayList<UsersProperties>();
		if (usersProperties != null) {
			list = usersPropertiesService.getReferralUsers(usersProperties.getuId());
		}
		return list;
	}

	/**
	 * 根据手机号、推荐码查询下级用户
	 * @param telephone	用户手机号
	 * @param referralCode	推荐人推荐码
	 * @return	返回查询列表
	 */
	@RequestMapping("searchRecByMap")
	@ResponseBody
	public Object searchRecByMap(String telephone, String referralCode) {
		log.info("/user/transfer/searchRecByMap");
		if (telephone == null) {
			telephone = "";
		}

		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("telephone", telephone);
		map.put("referralCode", referralCode);
		UsersProperties usersProperties = usersPropertiesService.selectByMap(map);
		List<UsersProperties> list = new ArrayList<UsersProperties>();
		if (usersProperties != null) {
			list = usersPropertiesService.getReferralUsers(usersProperties.getuId());
		}
		return list;
	}

	/**
	 * 转移
	 * @param idFrom	idFrom
	 * @param idTo	idTo
	 * @param idStr	idStr
	 * @param commissionRateStr	佣金比例
	 * @return	转移结果
	 */
	@RequestMapping("transfer")
	@ResponseBody
	public Object transfer(Integer idFrom, Integer idTo, String idStr, String commissionRateStr) {
		log.info("/info/transfer/transfer");
		PageInfo pageInfo = new PageInfo();
		try {
			if (idFrom == null || idTo == null || commissionRateStr == null) {
				pageInfo.setCode(ResultInfo.PARAMETER_ERROR.getCode());
				pageInfo.setMsg(ResultInfo.PARAMETER_ERROR.getMsg());
				return pageInfo;
			}
			int regUserId = idFrom; // 手机号注册用户
			int empUserId = idTo; // 内部员工
			UsersProperties regUser = usersPropertiesService.findByPk(regUserId);
			UsersProperties empUser = usersPropertiesService.findByPk(empUserId);
			if (regUser == null || empUser == null) {
				return ResponseUtils.failure(ResponseUtils.ERROR_PARAM, "参数有误,未找到记录");
			}
			String regUseruId = regUser.getuId(); // 注册用户id
			String empUseruId = empUser.getuId(); // 员工用户id
			regUser.setReferralId(empUseruId); // 推荐人id改为员工用户id
			regUser.setUserLevel(1); // 用户推荐等级改为1
			regUser.setRecommendedLevel(empUseruId + "," + regUseruId); // 用户层级改为(员工用户id,自身用户id)
			// 如果用户不为转移用户且购买过产品，则将其设置为转移用户
			if (regUser.getIsTransfer() == 0 && usersPropertiesService.selectTradingCountByuId(regUseruId) > 0) {
				regUser.setIsTransfer(1);
			}
			BigDecimal commissionRate = new BigDecimal(commissionRateStr);
			regUser.setCommissionRate(commissionRate);
			usersPropertiesService.update(regUser);
			if (StringUtils.isNotBlank(idStr)) { // 该注册用户有一级推荐用户
				String[] arr = idStr.split(",");
				for (int i = 0; i < arr.length; i++) {
					UsersProperties usersProperties = usersPropertiesService.findByUId(arr[i]);
					usersProperties.setUserLevel(ResultNumber.TWO.getNumber()); // 用户推荐等级改为2
					usersProperties.setRecommendedLevel(empUseruId + "," + regUseruId + "," + usersProperties.getuId()); // 用户层级改为(员工用户id,上级用户id,自身用户id)
					// 如果用户不为转移用户且购买过产品，则将其设置为转移用户
					if (usersProperties.getIsTransfer() == 0
							&& usersPropertiesService.selectTradingCountByuId(usersProperties.getuId()) > 0) {
						usersProperties.setIsTransfer(1);
					}
					usersPropertiesService.update(usersProperties);
				}
			}
			pageInfo.setCode(ResultInfo.SUCCESS.getCode());
		} catch (Exception e) {
			log.error(e.getMessage());
			e.printStackTrace();
			pageInfo.setCode(ResultInfo.SERVER_ERROR.getCode());
			pageInfo.setMsg(ResultInfo.SERVER_ERROR.getMsg());
		}
		return pageInfo;

	}

	/**
	 * 修改用户属性，转移用户或普通用户
	 * 
	 * @param id	数据ID
	 * @param isTransfer	转移类型
	 * @return	处理结果
	 */
	@RequestMapping("updateUser")
	@ResponseBody
	public Object updateUser(Integer id, Integer isTransfer) {
		log.info("/user/transfer/updateUser");
		PageInfo pageInfo = new PageInfo();
		try {
			if (id == null || isTransfer == null) {
				pageInfo.setCode(ResultInfo.PARAMETER_ERROR.getCode());
				pageInfo.setMsg(ResultInfo.PARAMETER_ERROR.getMsg());
				return pageInfo;
			}
			UsersProperties vo = usersPropertiesService.findByPk(id);
			if (vo == null) {
				pageInfo.setCode(ResultInfo.EMPTY_DATA.getCode());
				pageInfo.setMsg(ResultInfo.EMPTY_DATA.getMsg());
				return pageInfo;
			}
			vo.setIsTransfer(isTransfer);
			usersPropertiesService.update(vo);
			pageInfo.setCode(ResultInfo.SUCCESS.getCode());
		} catch (Exception e) {
			log.error(e.getMessage());
			e.printStackTrace();
			pageInfo.setCode(ResultInfo.SERVER_ERROR.getCode());
			pageInfo.setMsg(ResultInfo.SERVER_ERROR.getMsg());
		}
		return pageInfo;
	}

	/**
	 * 查询用户状态，是否购买过产品
	 * @param uId	用户ID
	 * @return	结果列表
	 */
	@RequestMapping("searchUserState")
	@ResponseBody
	public Object searchUserState(String uId) {
		log.info("/user/transfer/searchUserState");
		PageInfo pageInfo = new PageInfo();
		try {
			if (uId == null) {
				pageInfo.setCode(ResultInfo.PARAMETER_ERROR.getCode());
				pageInfo.setMsg(ResultInfo.PARAMETER_ERROR.getMsg());
				return pageInfo;
			}
			int count = usersPropertiesService.selectTradingCountByuId(uId);
			if (count > 0) {
				pageInfo.setCode(ResultInfo.SUCCESS.getCode());
			} else {
				pageInfo.setCode(ResultInfo.EMPTY_DATA.getCode());
				pageInfo.setMsg(ResultInfo.EMPTY_DATA.getMsg());
			}
		} catch (Exception e) {
			log.error(e.getMessage());
			e.printStackTrace();
			pageInfo.setCode(ResultInfo.SERVER_ERROR.getCode());
			pageInfo.setMsg(ResultInfo.SERVER_ERROR.getMsg());
		}
		return pageInfo;
	}
}
