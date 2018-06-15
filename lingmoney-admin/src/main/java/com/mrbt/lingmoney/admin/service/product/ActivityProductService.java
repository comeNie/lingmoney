package com.mrbt.lingmoney.admin.service.product;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.mrbt.lingmoney.model.ActivityProduct;
import com.mrbt.lingmoney.model.ActivityProductWithBLOBs;
import com.mrbt.lingmoney.utils.PageInfo;

/**
 * 产品设置——》产品活动表
 */
public interface ActivityProductService {
	
	/**
	 * 分页查询
	 * 
	 * @param vo
	 *            ActivityProduct
	 * @param pageInfo
	 *            PageInfo
	 * @return 数据返回
	 */
	PageInfo getList(ActivityProduct vo, PageInfo pageInfo);


	/**
	 * 通过ID查询活动产品描述
	 * 
	 * @param id
	 *            id
	 * @return 数据返回
	 */
	List<String> listContent(int id);
	
	/**
	 * 保存或更新
	 * 
	 * @param vo
	 *            ActivityProductWithBLOBs
	 * @param file1
	 *            file1
	 * @param file2
	 *            file2
	 * @param file3
	 *            file3
	 * @param bannerRootPath
	 *            bannerRootPath
	 * @return 数据返回
	 */
	int saveAndUpdate(ActivityProductWithBLOBs vo, MultipartFile file1, MultipartFile file2, MultipartFile file3, String bannerRootPath);

	/**
	 * 删除活动产品
	 * 
	 * @param parseInt
	 *            数据id
	 * @return 数据返回
	 */
	int delete(int parseInt);

	/**
	 * 更改状态
	 * 
	 * @param id
	 *            主键
	 * @return 分页实体类
	 */
	boolean changeStatus(Integer id);
	
	/**
	 * 查询活动
	 * 
	 * @param activityProduct  对象
	 *           
	 * @return 分页实体类
	 */
	List<ActivityProduct> selectByActivityProduct(ActivityProduct activityProduct);
}
