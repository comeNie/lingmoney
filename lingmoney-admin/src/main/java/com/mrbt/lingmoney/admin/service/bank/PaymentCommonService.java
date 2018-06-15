package com.mrbt.lingmoney.admin.service.bank;

import java.math.BigDecimal;

import com.mrbt.lingmoney.model.Product;
import com.mrbt.lingmoney.utils.PageInfo;

/**
 * 
 * @author Administrator
 *
 */
public interface PaymentCommonService {

	/**
	 * 返回标的所有投资人信息
	 * @author YJQ  2017年7月18日
	 * @param loanno 借款编号
	 * @param limitStart 分页起始
	 * @param limitEnd 分页结束
	 * @return	Exception
	 * @throws Exception	Exception
	 */
	PageInfo queryRepayList(String loanno, Integer limitStart, Integer limitEnd) throws Exception;

	/**
	 * 修改产品状态
	 * @author YJQ  2017年7月18日
	 * @param pid	pid
	 * @param status	status
	 * @throws Exception	Exception
	 */
	void changeProductStatus(Integer pid, Integer status) throws Exception;

	/**
	 * 用于还款时 返回还款总金额
	 * @author YJQ  2017年8月4日
	 * @param product	product
	 * @param flag  true-更改还款人交易信息  false-不更改
	 * @return	return
	 * @throws Exception	Exception
	 */
	BigDecimal queryRepayList(Product product, boolean flag) throws Exception;
	
}
