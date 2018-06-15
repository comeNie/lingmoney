package com.mrbt.lingmoney.admin.service.base;

import java.math.BigDecimal;
import java.util.HashMap;

import org.dom4j.Document;

/**
 * @author syb
 * @date 2017年7月5日 下午5:49:26
 * @version 1.0
 * @description 公共方法service
 **/
public interface CommonMethodService {
	/**
	 * 返回节假日
	 * 
	 * @return 数据返回
	 */
	HashMap<String, String> findHoliday();

	/**
	 * 流标/放款 后银行返回投标人信息保存到mongo
	 * 
	 * @param type
	 *            类型 1 流标结果、2 放款结果
	 * @param resDoc
	 *            resDoc
	 */
	void saveBanklendingInfoToMongo(Document resDoc, Integer type);

	/**
	 * 计算产品还款总金额
	 * 
	 * @author syb
	 * @date 2017年9月20日 下午5:42:19
	 * @version 1.0
	 * @param pid
	 *            产品id
	 * @param logGroup
	 *            logGroup
	 * @return 数据返回
	 *
	 */
	BigDecimal countTotalRepaymentMoney(Integer pid, String logGroup);
}
