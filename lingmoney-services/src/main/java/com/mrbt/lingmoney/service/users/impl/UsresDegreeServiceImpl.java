package com.mrbt.lingmoney.service.users.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mrbt.lingmoney.mapper.UsresDegreeMapper;
import com.mrbt.lingmoney.model.UsresDegree;
import com.mrbt.lingmoney.service.users.UsresDegreeService;

/**
 *@author syb
 *@date 2017年5月9日 上午9:24:25
 *@version 1.0
 *@description 用户级别 
 **/
@Service
public class UsresDegreeServiceImpl implements UsresDegreeService {
	@Autowired
	private UsresDegreeMapper usresDegreeMapper;

	@Override
	public UsresDegree queryById(int id) {
		return usresDegreeMapper.selectByPrimaryKey(id);
	}
}
