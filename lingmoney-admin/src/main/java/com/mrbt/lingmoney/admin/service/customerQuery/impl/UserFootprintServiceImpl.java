package com.mrbt.lingmoney.admin.service.customerQuery.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mrbt.lingmoney.admin.service.customerQuery.UserFootprintService;
import com.mrbt.lingmoney.mapper.UserFootprintMapper;
import com.mrbt.lingmoney.utils.PageInfo;
import com.mrbt.lingmoney.utils.ResultParame;

/**
 *
 *@author syb
 *@date 2017年9月5日 下午3:12:26
 *@version 1.0
 **/
@Service
public class UserFootprintServiceImpl implements UserFootprintService {

	@Autowired
	private UserFootprintMapper userFootprintMapper;

	@Override
	public PageInfo viewFootprint(String account, String tel, Integer page, Integer rows, String date) {

		PageInfo pi = new PageInfo(page, rows);
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("account", account);
		params.put("tel", tel);
		params.put("start", pi.getFrom());
		params.put("number", pi.getSize());
		params.put("date", date);

		List<Map<String, Object>> list = userFootprintMapper.listByUserInfo(params);
		pi.setRows(list);

		if (list != null && list.size() > 0) {
			int total = userFootprintMapper.countListByUserInfo(params);

			pi.setTotal(total);
			pi.setCode(ResultParame.ResultInfo.SUCCESS.getCode());
			pi.setMsg(ResultParame.ResultInfo.SUCCESS.getMsg());

		} else {
			pi.setTotal(0);
			pi.setCode(ResultParame.ResultInfo.EMPTY_DATA.getCode());
			pi.setMsg(ResultParame.ResultInfo.EMPTY_DATA.getMsg());
		}

		return pi;
	}

}
