package com.mrbt.lingmoney.admin.service.product;

import org.apache.ibatis.session.RowBounds;
import org.springframework.web.multipart.MultipartFile;

import com.mrbt.lingmoney.model.ActivityProperty;
import com.mrbt.lingmoney.model.ActivityPropertyWithBLOBs;
import com.mrbt.lingmoney.utils.GridPage;

/**
 * 产品设置——》活动属性
 *
 */
public interface ActivityPropertyService {

	/**
	 * 列表
	 * 
	 * @param rowBounds
	 *            分页信息
	 * @return 数据返回
	 */
	GridPage<ActivityProperty> listGrid(RowBounds rowBounds);

	/**
	 * 查询
	 * 
	 * @param parseInt
	 *            数据id
	 * @return 数据返回
	 */
	ActivityProperty findByPk(int parseInt);

	/**
	 * 保存或更新
	 * 
	 * @param file1
	 *            file1
	 * @param file2
	 *            file2
	 * @param vo
	 *            ActivityPropertyWithBLOBs
	 * @return 数据返回
	 */
	int saveAndUpdate(MultipartFile file1, MultipartFile file2, ActivityPropertyWithBLOBs vo);

}
