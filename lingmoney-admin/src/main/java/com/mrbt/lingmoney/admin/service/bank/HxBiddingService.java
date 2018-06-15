package com.mrbt.lingmoney.admin.service.bank;

import com.mrbt.lingmoney.commons.exception.PageInfoException;
import com.mrbt.lingmoney.model.HxBidding;
import com.mrbt.lingmoney.utils.PageInfo;

/**
 * 华兴银行标的信息表
 * 
 * @author lihq
 * @date 2017年6月7日 下午1:56:21
 * @description
 * @version 1.0
 * @since 2017-04-12
 */
public interface HxBiddingService {

	/**
	 * 分页查询标的
	 * @param pageInfo	pageInfo
	 * @return	pageInfo
	 */
	PageInfo getBiddingList(PageInfo pageInfo);

	/**
	 * 保存
	 * @param vo	HxBidding
	 */
	void save(HxBidding vo);

	/**
	 * 更新
	 * 
	 * @param vo	HxBidding
	 */
	void update(HxBidding vo);

	/**
	 * 分页查询投标人信息
	 * 
	 * @param pageInfo	pageInfo
	 * @return pageInfo
	 */
	PageInfo getBidderList(PageInfo pageInfo);

	/**
	 * 手动成立
	 * @author yiban
	 * @date 2017年10月31日 上午10:05:29
	 * @version 1.0
	 * @param pCode 产品code
	 * @return	PageInfo
	 * @throws PageInfoException	PageInfoException
	 *
	 */
	PageInfo manualEstablish(String pCode) throws PageInfoException;
}
