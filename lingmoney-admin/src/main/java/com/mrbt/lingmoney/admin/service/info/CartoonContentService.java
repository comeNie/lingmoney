package com.mrbt.lingmoney.admin.service.info;

import org.springframework.web.multipart.MultipartFile;

import com.mrbt.lingmoney.model.CartoonContent;
import com.mrbt.lingmoney.utils.PageInfo;

/**
 * 卡通
 * 
 * @author lihq
 * @date 2017年5月5日 上午11:10:36
 * @description
 * @version 1.0
 * @since 2017-04-12
 */
public interface CartoonContentService {

	/**
	 * 删除
	 * 
	 * @param id
	 *            id
	 */
	void delete(int id);

	/**
	 * 保存
	 * 
	 * @param vo
	 *            CartoonContent
	 * @param file1
	 *            file1
	 * @param file2
	 *            file2
	 * @param bannerRootPath
	 *            bannerRootPath
	 */
	void save(CartoonContent vo, MultipartFile file1, MultipartFile file2, String bannerRootPath);

	/**
	 * 更新
	 * 
	 * @param vo
	 *            CartoonContent
	 * @param file1
	 *            file1
	 * @param file2
	 *            file2
	 * @param bannerRootPath
	 *            bannerRootPath
	 */
	void update(CartoonContent vo, MultipartFile file1, MultipartFile file2, String bannerRootPath);

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
	 *            CartoonContent
	 * @param pageInfo
	 *            分页信息
	 * @return 数据返回
	 */
	PageInfo getList(CartoonContent vo, PageInfo pageInfo);
}
