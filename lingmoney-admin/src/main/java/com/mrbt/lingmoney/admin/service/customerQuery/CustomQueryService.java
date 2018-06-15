package com.mrbt.lingmoney.admin.service.customerQuery;

import java.util.Map;

import com.mrbt.lingmoney.utils.GridPage;

/**
 * @author syb
 * @date 2017年7月26日 下午1:41:14
 * @version 1.0
 * @description 自定义查询，用于处理业务人员特殊查询要求
 **/
public interface CustomQueryService {

	/**
	 * 查询产品购买情况 购买成功以及待支付的用户姓名，手机号，理财经理
	 * 
	 * @author yiban
	 * @date 2017年11月16日 下午2:59:30
	 * @version 1.0
	 * @param pid
	 *            产品id
	 * @param status
	 *            产品状态
	 * @param pageNo
	 *            页数
	 * @param pageSize
	 *            行数
	 * @return 数据返回
	 *
	 */
	GridPage<Map<String, Object>> queryProductSellTradingInfo(Integer pid, Integer status, Integer pageNo,
			Integer pageSize);

    /**
     * 查询产品购买情况
     * 
     * @author yiban
     * @date 2017年11月16日 下午3:00:17
     * @version 1.0
     * @param pid
     *            产品id
     * @return 产品募集规模，已筹规模，成功交易金额，占用金额
     *
     */
	Map<String, Object> queryProductSellSumInfo(Integer pid);

}
