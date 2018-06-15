package com.mrbt.lingmoney.admin.service.product;

import java.util.List;

import org.apache.ibatis.session.RowBounds;
import org.springframework.web.multipart.MultipartFile;

import com.mrbt.lingmoney.model.ProductImg;
import com.mrbt.lingmoney.utils.PageInfo;

/**
 * 产品图片设置
 * 
 * @author lihq
 * @date 2017年5月22日 下午5:28:53
 * @description
 * @version 1.0
 * @since 2017-04-12
 */
public interface ProductImgService {

	/**
	 * 分页查询
	 * 
	 * @param vo
	 *            ProductImg
	 * @param pageInfo
	 *            PageInfo
	 * @return 数据返回
	 */
	PageInfo getList(ProductImg vo, PageInfo pageInfo);

	/**
	 * 删除
	 * 
	 * @param int1
	 *            数据Id
	 */
	void delete(int int1);

	/**
	 * 根据类型查询产品图片列表
	 * 
	 * @param type
	 *            类型 0,WEB端我的理财产品图片 1,APP端产品背景图
	 * @return 数据返回
	 */
	List<ProductImg> listUrl(Integer type);

	/**
	 * 查询
	 * 
	 * @param vo
	 *            ProductImg
	 * @param rowBounds
	 *            RowBounds
	 * @return 数据返回
	 */
	Object listGrid(ProductImg vo, RowBounds rowBounds);

	/**
	 * 更改状态
	 * 
	 * @param id
	 *            id
	 * @return 数据返回
	 */
	boolean changeStatus(Integer id);

	/**
	 * 保存
	 * 
	 * @param vo
	 *            ProductImg
	 * @param bannerRootPath
	 *            bannerRootPath
	 * @param file
	 *            file
	 */
	void save(ProductImg vo, MultipartFile file, String bannerRootPath);

	/**
	 * 更新
	 * 
	 * @param vo
	 *            ProductImg
	 * @param bannerRootPath
	 *            bannerRootPath
	 * @param file
	 *            file
	 */
	void update(ProductImg vo, MultipartFile file, String bannerRootPath);
}
