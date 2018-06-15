package com.mrbt.lingmoney.admin.service.info;

import org.springframework.web.multipart.MultipartFile;

import com.mrbt.lingmoney.model.CartoonCategory;
import com.mrbt.lingmoney.utils.PageInfo;

/**
 * 卡通分类
 * 
 * @author lihq
 * @date 2017年5月5日 上午9:07:41
 * @description
 * @version 1.0
 * @since 2017-04-12
 */
public interface CartoonCategoryService {
	/**
	 * 更改状态
	 * 
	 * @param id
	 *            主键
	 * @return 分页实体类
	 */
	boolean changeStatus(Integer id);

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
	 *            CartoonCategory
	 * @param file
	 *            file
	 * @param bannerRootPath
	 *            bannerRootPath
	 */
	void save(CartoonCategory vo, MultipartFile file, String bannerRootPath);

	/**
	 * 更新
	 * 
	 * @param vo
	 *            CartoonCategory
	 * @param file
	 *            file
	 * @param bannerRootPath
	 *            bannerRootPath
	 */
	void update(CartoonCategory vo, MultipartFile file, String bannerRootPath);

	/**
	 * 分页查询
	 * 
	 * @param vo
	 *            CartoonCategory
	 * @param pageInfo
	 *            pageInfo
	 * @return 数据返回
	 */
	PageInfo getList(CartoonCategory vo, PageInfo pageInfo);
}
