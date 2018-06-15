package com.mrbt.lingmoney.service.bank;

import com.mrbt.lingmoney.utils.PageInfo;

/**
 * 支付公共
 */
public interface PaymentCommonService {

	/**
	 * 返回标的所有投资人信息
	 * @author YJQ  2017年7月18日
	 * @param loanno 借款编号
	 * @param days 计息日，传null时从payment表中取  
	 * @param limitStart 分页起始
	 * @param limitEnd 分页结束
	 * @return pageinfo
	 * @throws Exception 
	 */
	PageInfo queryRepayList(String loanno, Integer days, Integer limitStart, Integer limitEnd) throws Exception;

	/**
	 * 修改产品状态
	 * @author YJQ  2017年7月18日
	 * @param pid 产品id
	 * @param status 状态
	 * @throws Exception 
	 */
	void changeProductStatus(Integer pid, Integer status) throws Exception;
	
}
