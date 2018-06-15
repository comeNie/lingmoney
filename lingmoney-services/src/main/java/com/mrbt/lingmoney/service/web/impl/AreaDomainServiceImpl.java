package com.mrbt.lingmoney.service.web.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mrbt.lingmoney.mapper.AreaDomainMapper;
import com.mrbt.lingmoney.model.AreaDomain;
import com.mrbt.lingmoney.model.AreaDomainExample;
import com.mrbt.lingmoney.service.web.AreaDomainService;

/**
 * @author syb
 * @date 2017年5月3日 下午3:40:20
 * @version 1.0
 * @description
 **/
@Service
public class AreaDomainServiceImpl implements AreaDomainService {

	@Autowired
	private AreaDomainMapper areaDomainMapper;

	@Override
	public AreaDomain queryByCityCode(String cityCode) {
		AreaDomainExample example = new AreaDomainExample();
		example.createCriteria().andBdCityCodeEqualTo(cityCode);
		List<AreaDomain> list = areaDomainMapper.selectByExample(example);
		if (null != list && list.size() > 0) {
			return list.get(0);
		}
		return null;
	}

}
