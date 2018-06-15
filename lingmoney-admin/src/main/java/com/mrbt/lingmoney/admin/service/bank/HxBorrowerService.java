package com.mrbt.lingmoney.admin.service.bank;

import com.mrbt.lingmoney.model.HxAccount;
import com.mrbt.lingmoney.model.HxBorrower;
import com.mrbt.lingmoney.utils.PageInfo;

/**
 * 华兴银行借款人列表
 * 
 * @author lihq
 * @date 2017年6月8日 上午8:53:55
 * @description
 * @version 1.0
 * @since 2017-04-12
 */
public interface HxBorrowerService {

	/**
	 * 分页查询
	 * @param pageInfo	pageInfo
	 * @return	return
	 */
	PageInfo getList(PageInfo pageInfo);

	/**
	 * 保存
	 * @param vo	HxBorrower
	 * @return	return
	 */
	int save(HxBorrower vo);

	/**
	 * 更新
	 * 
	 * @param vo	vo
	 * @return	return
	 */
	int update(HxBorrower vo);

	/**
	 * 更改状态
	 * 
	 * @param id	id
	 *            主键
	 * @return 分页实体类
	 */
	PageInfo changeStatus(String id);

	/**
	 * 删除
	 * 
	 * @param id	id
	 *            主键
	 * @return 分页实体类
	 */
	PageInfo delete(String id);

	/**
	 * 查询E账户
	 * 
	 * @param vo	vo
	 * @param pageInfo	pageInfo
	 * @return	return
	 */
	PageInfo getList(HxAccount vo, PageInfo pageInfo);

	/**
	 * 根据id查询华兴银行E账户姓名
	 * @param accId	accId
	 * @return	return
	 */
	PageInfo getHxAccountName(String accId);

	/**
	 * 判断该借款人是否已借款，是则不可删除
	 * 
	 * @param bwId	bwId
	 * @return	return
	 */
	PageInfo checkIsBw(String bwId);

	/**
	 * 调用华兴账户管理
	 * @param clientType
	 * @param acNo
	 * @return
	 */
	PageInfo accountManager(String clientType, String acNo);
}
