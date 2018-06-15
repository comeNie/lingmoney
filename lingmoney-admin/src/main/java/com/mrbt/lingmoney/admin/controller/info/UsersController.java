package com.mrbt.lingmoney.admin.controller.info;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.mrbt.lingmoney.admin.controller.base.BaseController;
import com.mrbt.lingmoney.admin.service.info.UsersAccountService;
import com.mrbt.lingmoney.admin.service.info.UsersPropertiesService;
import com.mrbt.lingmoney.admin.service.info.UsersService;
import com.mrbt.lingmoney.model.BuyRecordsVo;
import com.mrbt.lingmoney.model.Users;
import com.mrbt.lingmoney.model.UsersAccount;
import com.mrbt.lingmoney.model.UsersProperties;
import com.mrbt.lingmoney.model.UsersVo;
import com.mrbt.lingmoney.utils.AppCons;
import com.mrbt.lingmoney.utils.MD5Utils;
import com.mrbt.lingmoney.utils.MyUtils;
import com.mrbt.lingmoney.utils.PageInfo;
import com.mrbt.lingmoney.utils.ResultParame.ResultInfo;
import com.mrbt.lingmoney.utils.ResultParame.ResultNumber;

/**
 * 页面设置——》账号设置
 * 
 * @author lihq
 * @date 2017年5月12日 上午9:34:45
 * @description
 * @version 1.0
 * @since 2017-04-12
 */
@Controller
@RequestMapping("/user/users")
public class UsersController extends BaseController {
	
	private Logger log = MyUtils.getLogger(UsersController.class);
	
	@Autowired
	private UsersService usersService;
	
	@Autowired
	private UsersPropertiesService usersPropertiesService;
	
	@Autowired
	private UsersAccountService usersAccountService;
	
	private static String uploadFilePath = "/home/www/temp_upload_file/";

	/**
	 * 删除
	 * @param id	数据ID
	 * @return	返回结果
	 */
	@RequestMapping("delete")
	@ResponseBody
	public Object delete(String id) {
		log.info("/user/users/delete");
		PageInfo pageInfo = new PageInfo();
		try {
			usersService.delete(id);
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
	 * 查询
	 * @param vo	users对象
	 * @param page	页数
	 * @param rows	条数
	 * @return	返回查询列表
	 */
	@RequestMapping("list")
	@ResponseBody
	public Object list(Users vo, Integer page, Integer rows) {
		log.info("/user/users/list");
		PageInfo pageInfo = new PageInfo(page, rows);
		try {
			usersService.listGrid(vo, pageInfo);
			pageInfo.setCode(ResultInfo.SUCCESS.getCode());
		} catch (Exception e) {
			log.error(e.getMessage());
			e.printStackTrace();
			pageInfo.setCode(ResultInfo.SERVER_ERROR.getCode());
			pageInfo.setMsg("服务器出错");
		}
		return pageInfo;
	}

	/**
	 * 保存
	 * @param vo	users对象
	 * @return	返回处理结果
	 */
	@RequestMapping("update")
	@ResponseBody
	public Object update(Users vo) {
		log.info("/user/users/update");
		PageInfo pageInfo = new PageInfo();
		try {
			usersService.update(vo);
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
	 * 注销用户
	 * @param vo	users对象
	 * @return	返回处理结果
	 */
	@RequestMapping("deleteUser")
	@ResponseBody
	public Object deleteUser(Users vo) {
		log.info("/user/users/deleteUser");
		PageInfo pageInfo = new PageInfo();
		try {
			vo.setType(ResultNumber.NINE.getNumber());
			usersService.update(vo);
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
	 * 初始化用户密码
	 * @param id	用户ID
	 * @return	返回结果
	 */
	@RequestMapping("updateUsersPswByInit")
	@ResponseBody
	public Object updateUsersPswByInit(String id) {
		log.info("/user/users/updateUsersPswByInit");
		PageInfo pageInfo = new PageInfo();
		try {
			Users vo = new Users();
			vo.setId(id);
			vo.setLoginPsw(MD5Utils.MD5(AppCons.DEFAULT_PSW));
			usersService.update(vo);
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
	 * 查询用户属性信息
	 * 
	 * @param uId	用户UID
	 * @param response	res
	 */
	@RequestMapping("selectUsersProperties")
	@ResponseBody
	public void selectUsersProperties(String uId, HttpServletResponse response) {
		log.info("/user/users/selectUsersProperties");
		try {
			if (StringUtils.isNotBlank(uId)) {
				UsersProperties usersProperties = usersPropertiesService.findByUId(uId);
				JSONObject jsonObject = new JSONObject();
				jsonObject.put("data", usersProperties);
				jsonObject.write(response.getWriter());
			}
		} catch (IOException e) {
			log.error(e.getMessage());
			e.printStackTrace();
		}
	}

	/**
	 * 查询用户账户信息
	 * 
	 * @param uId	用户UID
	 * @param response	res
	 */
	@RequestMapping("selectUsersAccount")
	@ResponseBody
	public void selectUsersAccount(String uId, HttpServletResponse response) {
		log.info("/user/users/selectUsersAccount");
		try {
			if (StringUtils.isNotBlank(uId)) {
				UsersAccount usersAccount = usersAccountService.getUsersAccountByUid(uId);
				JSONObject jsonObject = new JSONObject();
				jsonObject.put("data", usersAccount);
				jsonObject.write(response.getWriter());
			}
		} catch (IOException e) {
			log.error(e.getMessage());
			e.printStackTrace();
		}
	}

	/**
	 * 批量注册用户
	 * 
	 * @param request req
	 * @return	返回结果
	 */
	@RequestMapping("upload")
	@ResponseBody
	public Object upload(MultipartHttpServletRequest request) {
		log.info("/user/users/upload");
		PageInfo pageInfo = new PageInfo();
		try {
			usersService.upload(request, uploadFilePath, pageInfo);
		} catch (Exception e) {
			log.error(e.getMessage());
			e.printStackTrace();
			pageInfo.setCode(ResultInfo.SERVER_ERROR.getCode());
			pageInfo.setMsg(ResultInfo.SERVER_ERROR.getMsg());
		}
		return pageInfo;
	}

	/**
	 * 查询推荐的用户
	 * 
	 * @param referralCode	推荐码
	 * @return	返回查询结果
	 */
	@RequestMapping("userLevel")
	@ResponseBody
	public Object userLevel(String referralCode) {
		log.info("/user/users/userLevel");
		PageInfo pageInfo = new PageInfo();
		try {
			usersService.selectRecommendUser(referralCode, pageInfo);
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
	 * 用户属性列表
	 * @param vo	用户信息数据
	 * @param regDt	注册时间
	 * @param platform	平台方式
	 * @param symbol	symbol
	 * @param rows	条数
	 * @param page	页数
	 * @return	返回列表
	 */
	@RequestMapping("listProperty")
	@ResponseBody
	public Object listProperty(UsersProperties vo, String regDt, Integer platform, Integer symbol, Integer rows,
			Integer page) {
		log.info("/user/users/listProperty");
		PageInfo pageInfo = new PageInfo(page, rows);
		try {
			SimpleDateFormat dateFormat2 = new SimpleDateFormat("yyyy-MM-dd");
			if (symbol == null) {
				symbol = 1;
			}
			if (regDt != null && !"".equals(regDt)) {
				Date d = dateFormat2.parse(regDt);
				vo.setRegDate(d);
			}
			if (platform != null) {
				vo.setPlatformType(platform);
			}
			usersPropertiesService.listGrid(vo, symbol, pageInfo);
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
	 * 根据条件查询封装用户列表
	 * @param vo	封装对象
	 * @param page	页数
	 * @param rows	条数
	 * @param financialMin	理财最小
	 * @param financialMax	理财最大
	 * @param holdMin	持有最小
	 * @param holdMax	持有最大
	 * @param sort	排序
	 * @param order	排序
	 * @param showMgr	理财经理
	 * @return	返回结果
	 */
	@RequestMapping("usersList")
	@ResponseBody
	public Object usersList(UsersVo vo, Integer page, Integer rows, Double financialMin, Double financialMax,
            Double holdMin, Double holdMax, String sort, String order, Boolean showMgr, String regDateStr,
            String regDateStrEnd) {
		PageInfo pageInfo = new PageInfo(page, rows);
		try {
			usersService.selectUsersList(vo, financialMin, financialMax, holdMin, holdMax, pageInfo, sort, order,
                    showMgr, regDateStr, regDateStrEnd);
		} catch (Exception e) {
			e.printStackTrace();
			pageInfo.setCode(ResultInfo.SERVER_ERROR.getCode());
			pageInfo.setMsg(ResultInfo.SERVER_ERROR.getMsg());
		}
		return pageInfo;
	}

	/**
	 * 查询用户购买产品列表
	 * @param vo	包装实体
	 * @param page	页数
	 * @param rows	条数
	 * @param sort	排序
	 * @param order	排序
	 * @return	返回列表
	 */
	@RequestMapping("tradingList")
	@ResponseBody
	public Object tradingList(BuyRecordsVo vo, Integer page, Integer rows, String sort, String order) {
		PageInfo pageInfo = new PageInfo();
		try {
			usersService.selectTradingList(vo, pageInfo, sort, order);
		} catch (Exception e) {
			e.printStackTrace();
			pageInfo.setCode(ResultInfo.SERVER_ERROR.getCode());
			pageInfo.setMsg(ResultInfo.SERVER_ERROR.getMsg());
		}
		return pageInfo;
	}

	/**
	 * 导出到excel
	 * @description
	 * @param request req
	 * @param response	res
	 * @param vo	用户对象
	 * @param financialMin	理财最小
	 * @param financialMax	理财最大
	 * @param holdMin	持有最小
	 * @param holdMax	持有最大
	 * @param showMgr	理财经理
	 * @throws IOException	异常
	 */
	@RequestMapping("exportExcel")
	@ResponseBody
	public void exportExcel(HttpServletRequest request, HttpServletResponse response, UsersVo vo, Double financialMin,
            Double financialMax, Double holdMin, Double holdMax, Boolean showMgr, String regDateStr,
            String regDateStrEnd) throws IOException {
		PageInfo pageInfo = new PageInfo(1, ResultNumber.EXPORT_MAX.getNumber());
		try {
			pageInfo = usersService.exportExcel(request, response, vo, financialMin, financialMax, holdMin, holdMax,
                    pageInfo, showMgr, regDateStr, regDateStrEnd);
			if (pageInfo.getCode() == ResultInfo.SUCCESS.getCode()) {
				response.sendRedirect(pageInfo.getObj().toString());
			} else {
				response.getWriter().write(pageInfo.getMsg());
			}
		} catch (Exception e) {
			e.printStackTrace();
			log.error("导出到excel，失败原因是：" + e);
			response.getWriter().write("服务器出错");
		}
		return;
	}

	/**
	 * 查询分公司
	 * 
	 * @param q	条件
	 * @return 分页实体类
	 */
	@RequestMapping("queryManagerCom")
	@ResponseBody
	public Object queryManagerCom(String q) {
		return usersService.queryManagerCom(q);
	}

	/**
	 * 查询部门
	 * 
	 * @param q 条件
	 * @return 分页实体类
	 */
	@RequestMapping("queryManagerDept")
	@ResponseBody
	public Object queryManagerDept(String q) {
		return usersService.queryManagerDept(q);
	}

	/**
	 * 查询注册渠道
	 * 
	 * @param q	条件
	 * @return 分页实体类
	 */
	@RequestMapping("queryRegistChannel")
	@ResponseBody
	public Object queryRegistChannel(String q) {
		return usersService.queryRegistChannel(q);
	}
}
