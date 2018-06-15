package com.mrbt.lingmoney.admin.service.bank;

import com.mrbt.lingmoney.model.HxBorrowerInfo;
import com.mrbt.lingmoney.utils.PageInfo;

/**
 * 华兴银行借款人信息
 * 
 * @author lihq
 * @date 2017年6月8日 下午3:11:42
 * @description
 * @version 1.0
 * @since 2017-04-12
 */
public interface HxBorrowerInfoService {
	/**
	 * 根据主键查询
	 * 
	 * @param id
	 *            主键
	 * @return	return
	 */
	HxBorrowerInfo findById(String id);

	/**
	 * 删除
	 * @param id
	 *            主键
	 */
	void delete(String id);

	/**
	 * 保存
	 * @param vo	vo
	 * @return	return
	 */
	int save(HxBorrowerInfo vo);

	/**
	 * 更新,字段选择修改
	 * 
	 * @param vo	vo
	 * @return	return
	 */
	int updateByPrimaryKeySelective(HxBorrowerInfo vo);

	/**
	 * 根据分页实体类查询
	 * 
	 * @param pageInfo
	 *            分页实体类
	 * @return 分页实体类
	 */
	PageInfo getList(PageInfo pageInfo);

}
