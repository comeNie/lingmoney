package com.mrbt.lingmoney.service.insura.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mrbt.lingmoney.mapper.ContractBorrowerInfoMapper;
import com.mrbt.lingmoney.model.ContractBorrowerInfo;
import com.mrbt.lingmoney.model.ContractBorrowerInfoExample;
import com.mrbt.lingmoney.service.insura.HuanongInsuraService;

import net.sf.json.JSONObject;

@Service
public class HuanongInsuraServiceImpl implements HuanongInsuraService {
	
	@Autowired
	private ContractBorrowerInfoMapper contractBorrowerInfoMapper;

	@Override
	public JSONObject queryProductBorrowerType(Integer pId) {
		JSONObject jsonObject = new JSONObject();
		
		ContractBorrowerInfoExample example = new ContractBorrowerInfoExample();
		example.createCriteria().andPIdEqualTo(pId);
		
		List<ContractBorrowerInfo> list =  contractBorrowerInfoMapper.selectByExample(example);
		if (list != null && list.size() > 0) {
			ContractBorrowerInfo cbi = list.get(0);
			jsonObject.put("code", 200);
			jsonObject.put("msg", "查询成功");
			jsonObject.put("obj", cbi.getBwIdtype());
		} else {
			jsonObject.put("code", 1003);
			jsonObject.put("msg", "没有查询到数据");
		}
		return jsonObject;
	}

}
