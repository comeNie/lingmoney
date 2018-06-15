package com.mrbt.lingmoney.admin.service.info;

/**
 * 领宝消耗记录
 * 
 * @author lihq
 * @date 2017年5月18日 下午2:44:39
 * @description
 * @version 1.0
 * @since 2017-04-12
 */
public interface ActivityRecordService {
	/**
	 * 根据tid查询记录个数
	 * 
	 * @param tId
	 *            tId
	 * @return 数据返回
	 */
	int selectCountByTid(int tId);
}
