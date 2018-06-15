package com.mrbt.lingmoney.admin.service.info;

import org.springframework.web.multipart.MultipartFile;

import com.mrbt.lingmoney.model.FinancialManagement;
import com.mrbt.lingmoney.utils.PageInfo;

/**
 * 理财经
 * 
 * @author lihq
 * @date 2017年5月5日 上午9:08:50
 * @description
 * @version 1.0
 * @since 2017-04-12
 */
public interface FinancialManagementService {

	/**
	 * 删除
	 * 
	 * @param id
	 *            id
	 */
	void delete(int id);

	/**
	 * 新增
	 * 
	 * @param vo
	 *            FinancialManagement
	 * @param file
	 *            file
	 * @param bannerRootPath
	 *            bannerRootPath
	 */
	void save(FinancialManagement vo, MultipartFile file, String bannerRootPath);

	/**
	 * 更新
	 * 
	 * @param vo
	 *            FinancialManagement
	 * @param file
	 *            file
	 * @param bannerRootPath
	 *            bannerRootPath
	 */
	void update(FinancialManagement vo, MultipartFile file, String bannerRootPath);

	/**
	 * 返回text的内容
	 * 
	 * @param id
	 *            id
	 * @return 数据返回
	 */
	String listContent(int id);

	/**
	 * 更改状态
	 * 
	 * @param id
	 *            主键
	 * @return 分页实体类
	 */
	boolean changeStatus(Integer id);

	/**
	 * 分页查询
	 * 
	 * @param vo
	 *            FinancialManagement
	 * @param pageInfo
	 *            PageInfo
	 * @return 数据返回
	 */
	PageInfo getList(FinancialManagement vo, PageInfo pageInfo);
}
