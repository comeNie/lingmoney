package com.mrbt.lingmoney.service.discover;

import java.util.List;

import com.mrbt.lingmoney.model.CartoonCategory;
import com.mrbt.lingmoney.model.CartoonContent;
import com.mrbt.lingmoney.model.FinancialManagement;
import com.mrbt.lingmoney.utils.PageInfo;

/**
 *@author syb
 *@date 2017年4月12日 下午3:27:02
 *@version 1.0
 *@description 
 **/
public interface FinancialClassService {

	/**
	 * 
	 * @description 获取理财课堂数据（分页），按发布日期倒序查询
	 * @author syb
	 * @date 2017年8月23日 下午5:11:31
	 * @version 1.0
	 * @param pageNo
	 *            当前页
	 * @param pageSize
	 *            每页条数
	 * @return pageinfo
	 *
	 */
	PageInfo getIndexInfo(Integer pageNo, Integer pageSize);
	
	/**
	 * 根据ID查询详细信息
	 * @param id id
	 * @return financialManagement
	 */
	FinancialManagement getDetailById(Integer id);

	/**
	 * 查询漫画贴信息
	 * @return 漫画帖列表
	 */
	List<CartoonCategory> listCartoonCategory();

	/**
	 * 按漫画类别查询其下漫画
	 * @param id id
	 * @return 漫画内容
	 */
	List<CartoonContent> listCartoonContentByTypeId(Integer id);

	/**
	 * 根据ID查询漫画类别信息
	 * @param id id
	 * @return 漫画类别
	 */
	CartoonCategory findCartoonCategoryById(Integer id);

	/**
	 * 根据id查询漫画信息
	 * @param id id
	 * @return 漫画信息
	 */
	CartoonContent findCartoonContentById(Integer id);

	/**
	 * 查询漫画类型下ID
	 * @param cid 漫画类型id
	 * @return 该类型下列表
	 */
	List<Integer> listCartoonContentId(Integer cid);

	/**
	 * 查询所有id
	 * @return id集合
	 */
	List<Integer> listFinancialManagementId();

}
