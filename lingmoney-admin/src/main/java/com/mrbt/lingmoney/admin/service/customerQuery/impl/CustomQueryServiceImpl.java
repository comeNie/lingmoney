package com.mrbt.lingmoney.admin.service.customerQuery.impl;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.mrbt.lingmoney.admin.service.customerQuery.CustomQueryService;
import com.mrbt.lingmoney.mapper.CustomQueryMapper;
import com.mrbt.lingmoney.model.EmployeeRelationsMapping;
import com.mrbt.lingmoney.utils.AppCons;
import com.mrbt.lingmoney.utils.GridPage;
import com.mrbt.lingmoney.utils.ResultParame;

/**
 *@author yiban sun
 *@date 2017年7月25日 下午2:15:19
 *@version 1.0
 *@description 自定义查询service
 *@since
 **/
@Service
public class CustomQueryServiceImpl implements CustomQueryService {
	
	@Autowired
	private CustomQueryMapper customQueryMapper;
	
	@Override
	public GridPage<Map<String, Object>> queryProductSellTradingInfo(Integer pid, Integer status, Integer pageNo, Integer pageSize) {
		if (StringUtils.isEmpty(pageNo) || pageNo < 1) {
			pageNo = AppCons.DEFAULT_PAGE_START;
		}

		if (StringUtils.isEmpty(pageSize)) {
			pageSize = AppCons.DEFAULT_PAGE_SIZE;
		}

		if (StringUtils.isEmpty(status)) {
			status = ResultParame.ResultNumber.MINUS_ONE.getNumber();
		}

		int start = (pageNo - 1) * pageSize;

		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("start", start);
		paramMap.put("number", pageSize);
		paramMap.put("pid", pid);
		paramMap.put("status", status);

		List<Map<String, Object>> list = customQueryMapper.queryBuyerInfoByPidAndStatus(paramMap);
		if (list != null && list.size() > 0) {
			for (Map<String, Object> map : list) {
				// 获取推荐人id
				String recomLevel = (String) map.get("recomLevel");
				if (!StringUtils.isEmpty(recomLevel)) {
					// 拆分推荐人id
					String[] uids = recomLevel.split(",");
					// 转换成集合
					List<String> uidList = Arrays.asList(uids);
					// 反转集合，从第一层推荐人往上找
					Collections.reverse(uidList);
					// 循环查找次数，只往上找三级，第一次为用户自己的id
					int count = ResultParame.ResultNumber.THREE.getNumber();
					if (uidList.size() < ResultParame.ResultNumber.THREE.getNumber()) {
						count = uidList.size();
					}
					for (int i = 0; i < count; i++) {
						EmployeeRelationsMapping employee = customQueryMapper.queryEmployeeInfoByUid(uidList.get(i));
						if (employee != null) {
							map.put("employeeName", employee.getEmployeeName());
						}
					}

					if (!map.containsKey("employeeName")) {
						map.put("employeeName", "");
					}

				}
			}
		}
		
		int total = customQueryMapper.countBuyerInfoByPidAndStatus(paramMap);

		GridPage<Map<String, Object>> gridPage = new GridPage<Map<String, Object>>();
		gridPage.setRows(list);
		gridPage.setTotal(total);

		return gridPage;
	}
	
	@Override
	public Map<String, Object> queryProductSellSumInfo(Integer pid) {
		return customQueryMapper.sumProductSellInfoByPid(pid);
	}
	
}
