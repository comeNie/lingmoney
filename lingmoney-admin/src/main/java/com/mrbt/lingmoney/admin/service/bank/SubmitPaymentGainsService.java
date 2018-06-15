package com.mrbt.lingmoney.admin.service.bank;

import java.util.Map;

import com.mrbt.lingmoney.bank.deal.dto.HxQuerySubmitPaymentReqDto;
import com.mrbt.lingmoney.bank.deal.dto.HxQuerySubmitPaymentResDto;
import com.mrbt.lingmoney.utils.PageInfo;

/**
 * 华兴E账户 - 还款收益明细提交服务接口
 * 
 * @author YJQ
 *
 */
public interface SubmitPaymentGainsService {

	/**
	 * 请求还款收益明细提交
	 * @param channelFlow	交易流水号
	 * @return	return
	 * @throws Exception 	Exception
	 */
	Map<String, Object> requestSubmit(Object channelFlow) throws Exception;

	/**
	 * 查询还款收益明细提交应答结果
	 * @param dto	dto
	 * @return	return
	 * @throws Exception	Exception
	 */
	HxQuerySubmitPaymentResDto querySubmitResult(HxQuerySubmitPaymentReqDto dto) throws Exception;

	/**
	 * 查询分页列表
	 * @param loanNo	loanNo
	 * @param channelFlow channelFlow
	 * @param pageInfo	pageInfo
	 * @return	return
	 */
	PageInfo queryList(String loanNo, String channelFlow, PageInfo pageInfo);

	/**
	 * 收益明细提交计划任务
	 * @author YJQ  2017年6月19日
	 */
	void pollingPaymentRedis();
	
	/**
	 * 查询明细提交结果计划任务
	 * @author YJQ  2017年7月20日
	 */
	void pollingSubmitResult();


	/**
	 * 手动添加收益明细至redis队列
	 * @param channelFlow	channelFlow
	 * @throws Exception	Exception
	 */
	void addRedisQueue(String channelFlow) throws Exception;

	/**
	 * 查询投资人列表
	 * @param loanno	loanno
	 * @param pageInfo	pageInfo
	 * @return	return
	 * @throws Exception	Exception
	 */
	PageInfo queryRepayList(String loanno, PageInfo pageInfo) throws Exception;


}
