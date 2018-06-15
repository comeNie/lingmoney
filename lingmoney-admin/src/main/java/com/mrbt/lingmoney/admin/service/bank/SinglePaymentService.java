package com.mrbt.lingmoney.admin.service.bank;

import java.math.BigDecimal;

import javax.servlet.http.HttpServletRequest;

import com.mrbt.lingmoney.bank.deal.dto.HxQueryPaymentReqDto;
import com.mrbt.lingmoney.bank.deal.dto.HxQueryPaymentResDto;
import com.mrbt.lingmoney.bank.deal.dto.HxSinglePaymentReqDto;
import com.mrbt.lingmoney.bank.utils.dto.PageResponseDto;
import com.mrbt.lingmoney.utils.PageInfo;

/**
 * 华兴E账户-还款人单笔还款服务接口
 * @author YJQ
 *
 */
public interface SinglePaymentService {

	/**
	 * 请求还款人单笔还款
	 * @param req	req
	 * @return	return
	 * @throws Exception	Exception
	 */
	PageResponseDto requestPayment(HxSinglePaymentReqDto req) throws Exception;

	/**
	 * 接收还款人单笔还款应答结果
	 * @param request	request
	 * @return	return
	 * @throws Exception	Exception
	 */
	String receivePaymentResult(HttpServletRequest request) throws Exception;

	/**
	 * 查询还款人单笔还款应答结果
	 * @param dto	dto
	 * @return	return
	 * @throws Exception	Exception
	 */
	HxQueryPaymentResDto queryPaymentResult(HxQueryPaymentReqDto dto) throws Exception;

	/**
	 * 查询还款信息列表
	 * @param bwId	bwId
	 * @param pageInfo	pageInfo
	 * @return	return
	 * @throws Exception	Exception
	 */
	PageInfo queryList(String bwId, PageInfo pageInfo)  throws Exception;

	/**
	 * 计划任务-检测是否有到期还款的标的  主动还款
	 * @author YJQ  2017年7月3日
	 * @throws Exception	Exception
	 */
	void checkPayment() throws Exception;

	/**
	 * 根据借款编号查询还款金额
	 * 
	 * @version 1.0
	 * @param loanNo 借款编号
	 * @return	return
	 * @throws Exception	Exception
	 *
	 */
	BigDecimal queryAmount(String loanNo) throws Exception;

	/**
	 * 定时任务 查询还款结果
	 * @author YJQ  2017年7月18日
	 */
	void pollingPaymentResult();
	
	/**
	 * 标的结清通知
	 * 标的结清后将不再受理还款交易
	 * @description
	 * @author syb
	 * @date 2017年8月23日 上午9:26:57
	 * @version 1.0
	 * @param loanNo 借款编号
	 * @param logGroup 日志头
	 * @return	return
	 *
	 */
	PageInfo hxRepaymentFinishNotice(String loanNo, String logGroup);

    /**
     * 垫付还款后借款人还款
     * 
     * @author yiban
     * @date 2018年3月29日 上午9:17:41
     * @version 1.0
     * @param channelFlow 垫付还款流水号
     * @return
     *
     */
    PageInfo repaymentAfterAdvance(String channelFlow) throws Exception;

}
