package com.mrbt.lingmoney.service.trading;

import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.mrbt.lingmoney.mapper.ProductParamMapper;

/**
 *@author syb
 *@date 2017年5月18日 上午11:28:18
 *@version 1.0
 *@description 
 **/
@Component
public class BaseTradingService {
	@Autowired
	private ProductParamMapper productParamMapper;
	
    /**
     * 查询节假日
     * 
     * @author yiban
     * @date 2017年11月16日 下午3:15:19
     * @version 1.0
     * @return 节日map
     *
     */
	public HashMap<String, String> findHoliday() {
		HashMap<String, String> holidMap = new HashMap<String, String>();
		List<String> list = productParamMapper.findHolidayList();
        if (null != list && list.size() > 0) {
			for (String dt : list) {
				holidMap.put(dt, dt);
			}
		}
		return holidMap;
	}

	/**
	 * 创建bizCode码
	 * @return 交易码
	 */
	public String buildBizCode() {
		String bizCode = UUID.randomUUID().toString().replace("-", "").substring(15) + System.currentTimeMillis();
		System.out.println("生成订单号为:" + bizCode + "\t" + bizCode.length());
		return bizCode;
	}
}
