package com.mrbt.lingmoney.admin.service.info;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.mrbt.lingmoney.model.BuyRecordsVo;
import com.mrbt.lingmoney.model.Users;
import com.mrbt.lingmoney.model.UsersVo;
import com.mrbt.lingmoney.utils.PageInfo;

/**
 * 用户设置
 * 
 * @author lihq
 * @date 2017年5月18日 下午2:59:27
 * @description
 * @version 1.0
 * @since 2017-04-12
 */
public interface UsersService {

	/**
	 * 查询
	 * 
	 * @param vo
	 *            Users
	 * @param pageInfo
	 *            PageInfo
	 */
	void listGrid(Users vo, PageInfo pageInfo);

	/**
	 * 删除
	 * 
	 * @param id
	 *            id
	 */
	void delete(String id);

	/**
	 * 保存实体bean
	 * 
	 * @param vo
	 *            Users
	 */
	void save(Users vo);

	/**
	 * 更新实体bean
	 * 
	 * @param vo
	 *            Users
	 */
	void update(Users vo);

	/**
	 * 
	 * 批量注册，导入特有格式的txt文档
	 * 
	 * @param request
	 *            MultipartHttpServletRequest
	 * @param uploadFilePath
	 *            uploadFilePath
	 * @param pageInfo
	 *            PageInfo
	 */
	void upload(MultipartHttpServletRequest request, String uploadFilePath, PageInfo pageInfo);

	/**
	 * 用户注册
	 * 
	 * @param account
	 *            account
	 * @param password
	 *            password
	 * @param referralCode
	 *            referralCode
	 * @param phone
	 *            phone
	 * @param isPhone
	 *            isPhone
	 * @param name
	 *            name
	 * @param cardNo
	 *            cardNo
	 * @param path
	 *            path
	 */
	void registerUsers(String account, String password, String referralCode, String phone, boolean isPhone,
			String name, String cardNo, String path);

	/**
	 * 查询所有用户id
	 * 
	 * @return 数据返回
	 */
	List<String> selectAllUserId();

	/**
	 * 查询推荐用户
	 * 
	 * @param referralCode
	 *            referralCode
	 * @param pageInfo
	 *            pageInfo
	 */
	void selectRecommendUser(String referralCode, PageInfo pageInfo);

	/**
	 * 主键查询
	 * 
	 * @param id
	 *            id
	 * @return 数据返回
	 */
	Users findByPk(String id);

	/**
	 * 查询封装用户列表
	 * 
	 * @param vo
	 *            vo
	 * @param financialMin
	 *            financialMin
	 * @param financialMax
	 *            financialMax
	 * @param holdMin
	 *            holdMin
	 * @param holdMax
	 *            holdMax
	 * @param pageInfo
	 *            pageInfo
	 * @param sort
	 *            sort
	 * @param order
	 *            order
	 * @param showMgr
	 *            showMgr
	 * @param regDateStr
	 *            showMgr
	 */
	void selectUsersList(UsersVo vo, Double financialMin, Double financialMax, Double holdMin, Double holdMax,
            PageInfo pageInfo, String sort, String order, Boolean showMgr, String regDateStr, String regDateStrEnd);

	/**
	 * 查询用户购买产品列表
	 * 
	 * @param vo
	 *            BuyRecordsVo
	 * @param pageInfo
	 *            PageInfo
	 * @param sort
	 *            sort
	 * @param order
	 *            order
	 */
	void selectTradingList(BuyRecordsVo vo, PageInfo pageInfo, String sort, String order);

	/**
	 * 导出到excel
	 * 
	 * @param request
	 *            request
	 * @param response
	 *            response
	 * @param vo
	 *            vo
	 * @param financialMin
	 *            financialMin
	 * @param financialMax
	 *            financialMax
	 * @param holdMin
	 *            holdMin
	 * @param holdMax
	 *            holdMax
	 * @param pageInfo
	 *            pageInfo
	 * @param showMgr
	 *            showMgr
	 * @return 数据返回
	 */
	PageInfo exportExcel(HttpServletRequest request, HttpServletResponse response, UsersVo vo, Double financialMin,
            Double financialMax, Double holdMin, Double holdMax, PageInfo pageInfo, Boolean showMgr, String regDateStr,
            String regDateStrEnd);

	/**
	 * 查询分公司
	 * 
	 * @param q
	 *            q
	 * @return 数据返回
	 */
	List<Map<String, Object>> queryManagerCom(String q);

	/**
	 * 查询部门
	 * 
	 * @param q
	 *            q
	 * @return 数据返回
	 */
	List<Map<String, Object>> queryManagerDept(String q);

	/**
	 * 查询注册渠道
	 * 
	 * @param q
	 *            q
	 * @return 数据返回
	 */
	List<Map<String, Object>> queryRegistChannel(String q);
}
