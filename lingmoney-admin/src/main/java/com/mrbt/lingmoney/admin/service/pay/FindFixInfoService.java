package com.mrbt.lingmoney.admin.service.pay;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mrbt.lingmoney.mapper.TradingFixInfoMapper;
import com.mrbt.lingmoney.model.TradingFixInfo;

/**
 * 
 * FindFixInfoService
 *
 */
@Service
public class FindFixInfoService {
    @Autowired
	private TradingFixInfoMapper tradingFixInfoMapper;

	/**
	 * selectFixInfoTid
	 * 
	 * @param tid
	 *            tid
	 * @return 数据返回
	 */
    public Integer selectFixInfoTid(int tid) {
    	TradingFixInfo tfi = tradingFixInfoMapper.selectFixInfoByTid(tid);
    	return tfi.getId();
    }

}
