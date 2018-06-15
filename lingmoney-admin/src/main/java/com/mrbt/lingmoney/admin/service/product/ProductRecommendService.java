package com.mrbt.lingmoney.admin.service.product;

import org.springframework.web.multipart.MultipartFile;

import com.mrbt.lingmoney.model.ProductRecommend;
import com.mrbt.lingmoney.model.ProductRecommendCustomer;
import com.mrbt.lingmoney.utils.PageInfo;

/**
 * 产品推荐设置
 * 
 * @author lihq
 * @date 2017年5月22日 下午3:09:17
 * @description
 * @version 1.0
 * @since 2017-04-12
 */
public interface ProductRecommendService {

	/**
	 * 删除
	 * 
	 * @param id
	 *            id
	 * @return 数据返回
	 */
	int delete(int id);

	/**
	 * 分页查询
	 * 
	 * @param vo
	 *            ProductRecommendCustomer
	 * @param pageInfo
	 *            PageInfo
	 * @return 数据返回
	 */
	PageInfo getList(ProductRecommendCustomer vo, PageInfo pageInfo);

	/**
	 * 新增
	 * 
	 * @param vo
	 *            ProductRecommend
	 * @param file1
	 *            MultipartFile
	 * @param file2
	 *            MultipartFile
	 * @param bannerRootPath
	 *            bannerRootPath
	 * @return 数据返回
	 */
	int save(ProductRecommend vo, MultipartFile file1, MultipartFile file2, String bannerRootPath);

	/**
	 * 更新
	 * 
	 * @param vo
	 *            ProductRecommend
	 * @param file1
	 *            MultipartFile
	 * @param file2
	 *            MultipartFile
	 * @param bannerRootPath
	 *            bannerRootPath
	 * @return 数据返回
	 */
	int update(ProductRecommend vo, MultipartFile file1, MultipartFile file2, String bannerRootPath);

	/**
	 * 刷新
	 */
	void reload();

}
