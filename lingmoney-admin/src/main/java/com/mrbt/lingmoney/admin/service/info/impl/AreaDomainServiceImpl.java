package com.mrbt.lingmoney.admin.service.info.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mrbt.lingmoney.admin.service.info.AreaDomainService;
import com.mrbt.lingmoney.mapper.AreaDomainMapper;
import com.mrbt.lingmoney.model.AreaDomain;
import com.mrbt.lingmoney.model.AreaDomainExample;
import com.mrbt.lingmoney.utils.PageInfo;

import net.sf.json.JSONArray;

/**
 * 地区域名
 * 
 */
@Service
public class AreaDomainServiceImpl implements AreaDomainService {
	@Autowired
	private AreaDomainMapper areaDomainMapper;

	@Override
	@Transactional
	public boolean insert(AreaDomain ad) {
		int i = areaDomainMapper.insert(ad);
		return i > 0 ? true : false;
	}

	@Override
	@Transactional
	public boolean update(AreaDomain ad) {
		int i = areaDomainMapper.updateByPrimaryKeySelective(ad);
		return i > 0 ? true : false;
	}

	@Override
	public JSONArray queryCodeName() {
		JSONArray json = new JSONArray();
		List<Map<String, String>> list = areaDomainMapper.queryCodeName();
		json.addAll(list);
		return json;
	}

	@Override
	public PageInfo listGrid(Map<String, Object> map) {
		PageInfo pageInfo = new PageInfo();
		int resSize = areaDomainMapper.queryAllCount(map);
		List<Map<String, Object>> list = areaDomainMapper.queryAll(map);
		pageInfo.setRows(list);
		pageInfo.setTotal(resSize);
		return pageInfo;
	}

	@Override
	public PageInfo queryCityInfo(PageInfo pageInfo) {
		AreaDomainExample example = new AreaDomainExample(); 
		List<AreaDomain> list = areaDomainMapper.selectByExample(example);
		
		Map<String, String> map = new HashMap<String, String>();
		
		for (int i = 0; i < list.size(); i++) {
			AreaDomain ad = list.get(i);
			map.put(ad.getBdCityCode(), ad.getCityName());
		}
		pageInfo.setObj(map);
		return pageInfo;
	}

	
}
