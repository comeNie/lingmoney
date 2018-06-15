package com.mrbt.lingmoney.service.dream.impl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import org.apache.commons.lang.StringUtils;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.mrbt.lingmoney.mapper.DreamBaseInfoMapper;
import com.mrbt.lingmoney.mapper.DreamCategoryMapper;
import com.mrbt.lingmoney.mapper.SynTradingDataMapper;
import com.mrbt.lingmoney.mapper.TradingMapper;
import com.mrbt.lingmoney.mapper.UsersDreamInfoMapper;
import com.mrbt.lingmoney.model.DreamBaseInfo;
import com.mrbt.lingmoney.model.DreamBaseInfoExample;
import com.mrbt.lingmoney.model.DreamCategory;
import com.mrbt.lingmoney.model.DreamCategoryExample;
import com.mrbt.lingmoney.model.SynTradingData;
import com.mrbt.lingmoney.model.SynTradingDataExample;
import com.mrbt.lingmoney.model.UsersDreamInfo;
import com.mrbt.lingmoney.model.UsersDreamInfoExample;
import com.mrbt.lingmoney.service.dream.DreamServer;

@Service
public class DreamServerImpl implements DreamServer {
	
	@Autowired
	private UsersDreamInfoMapper usersDreamInfoMapper;
	
	@Autowired
	private DreamCategoryMapper dreamcategoryMapper;
	
	@Autowired
	private DreamBaseInfoMapper dreamBaseInfoMapper;
	
	@Autowired
	private SynTradingDataMapper synTradingDataMapper;
	
	@Override
	public JSONObject queryDreamInfo(String uId) {
		
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("code", 200);
		jsonObject.put("msg", "查询成功");
		
		//获取用户的梦想数据
		Map<Integer, Object> usersMap = new HashMap<Integer, Object>();
		if (!StringUtils.isBlank(uId)) {
			UsersDreamInfoExample example = new UsersDreamInfoExample();
			example.createCriteria().andUIdEqualTo(uId);
			List<UsersDreamInfo> usersDreamList = usersDreamInfoMapper.selectByExample(example);
			for (UsersDreamInfo udi : usersDreamList) {
				usersMap.put(udi.getDreamInfoId(), udi.getStatus());
			}
		}
		
		//查询类别列表
		DreamCategoryExample dcExample = new DreamCategoryExample();
		List<DreamCategory> dreamCategoriesList = dreamcategoryMapper.selectByExample(dcExample);

		JSONArray jsonArray = new JSONArray();
		
		//循环类别列表，查询基础列表
		for (DreamCategory dc : dreamCategoriesList) {
			
			JSONObject jsonObject2 = (JSONObject) JSONObject.toJSON(dc);
			
			//根据dc.id查询基础信息
			DreamBaseInfoExample dbieExample = new DreamBaseInfoExample();
			dbieExample.createCriteria().andCategoryIdEqualTo(dc.getId());
			List<DreamBaseInfo> dbieList = dreamBaseInfoMapper.selectByExample(dbieExample);
			//匹配用户是否有该梦想
			
			JSONArray jsonArray2 = new JSONArray();
			
			//循环基础列表，增加用户梦想信息
			for (DreamBaseInfo dbi : dbieList) {
				JSONObject jsonObject3 = (JSONObject) JSONObject.toJSON(dbi);
				if (usersMap.containsKey(dbi.getId())) {
					jsonObject3.put("haveDrive", usersMap.get(dbi.getId()));
				}else {
					jsonObject3.put("haveDrive", 2);
				}
				jsonArray2.add(jsonObject3);
			}
			
			jsonObject2.put("dreamBaseInfo", jsonArray2);
			jsonArray.add(jsonObject2);
		}
		jsonObject.put("data", jsonArray);
		return jsonObject;
	}

	@Override
	public JSONObject queryBaseDreamInfo(Integer baseId) {
		
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("code", 200);
		jsonObject.put("msg", "查询成功");
		
		//查询梦想信息
		DreamBaseInfo dbi = dreamBaseInfoMapper.selectByPrimaryKey(baseId);
		jsonObject.put("data2", dbi);
		return jsonObject;
	}

	@Override
	public JSONObject selectDream(String uId, Integer baseId) {
		JSONObject jsonObject = new JSONObject();
		
		//判断用户是否有该梦想
		if (!StringUtils.isBlank(uId)) {
			//验证用户是否已有该梦想
			UsersDreamInfoExample example = new UsersDreamInfoExample();
			example.createCriteria().andUIdEqualTo(uId).andDreamInfoIdEqualTo(baseId);
			List<UsersDreamInfo> udiList = usersDreamInfoMapper.selectByExample(example);
			if(udiList != null && udiList.size() > 0) {
				jsonObject.put("code", 1007);
				jsonObject.put("msg", "同一个梦想不能重复领取");
			} else {
				//添加梦想
				UsersDreamInfo usersDreamInfo = new UsersDreamInfo();
				usersDreamInfo.setAccumulatedIncome(0.00);
				usersDreamInfo.setDreamInfoId(baseId);
				usersDreamInfo.setDreamStartTime(new Date());
				usersDreamInfo.setStatus(0);
				usersDreamInfo.setuId(uId);
				usersDreamInfoMapper.insertSelective(usersDreamInfo);
				jsonObject.put("code", 200);
				jsonObject.put("msg", "梦想添加成功");
			}
		} else {
			jsonObject.put("code", 1006);
			jsonObject.put("msg", "用户ID不能为空");
		}
		return jsonObject;
	}

	@Override
	public UsersDreamInfo queryUsersHaveDream(String uId) {
		UsersDreamInfoExample example = new UsersDreamInfoExample();
		example.createCriteria().andUIdEqualTo(uId).andStatusEqualTo(0);
		List<UsersDreamInfo> udiList = usersDreamInfoMapper.selectByExample(example);
		if(udiList != null && udiList.size() > 0) {
			return udiList.get(0);
		}
		return null;
	}

	@Override
	public JSONObject queryUsersDreamInfo(String uId, Integer baseId) {
		
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("code", 200);
		jsonObject.put("msg", "查询成功");
		//判断用户是否有该梦想
		if (!StringUtils.isBlank(uId)) {
			UsersDreamInfoExample example = new UsersDreamInfoExample();
			example.createCriteria().andUIdEqualTo(uId).andStatusEqualTo(0).andDreamInfoIdEqualTo(baseId);
			List<UsersDreamInfo> udiList = usersDreamInfoMapper.selectByExample(example);
			if(udiList != null && udiList.size() > 0) {
				UsersDreamInfo udi = udiList.get(0);
				//统计用户累计收益
				SynTradingDataExample example2 = new SynTradingDataExample();
				example2.createCriteria().andUIdEqualTo(uId).andBuyDtGreaterThanOrEqualTo(udi.getDreamStartTime());
				List<SynTradingData> list = synTradingDataMapper.selectByExample(example2);
				
				BigDecimal sumInterest = new BigDecimal("0");
				for (SynTradingData std : list) {
					sumInterest = sumInterest.add(std.getInterest());
				}
				
				udi.setAccumulatedIncome(Double.parseDouble(sumInterest.toString()));
				
				UsersDreamInfo upUdi = new UsersDreamInfo();
				DreamBaseInfo dbi = dreamBaseInfoMapper.selectByPrimaryKey(baseId);
				
				if (udi.getAccumulatedIncome() >= dbi.getRequiredAmount()) {
					upUdi.setStatus(1);
				}
				
				//更新金额
				upUdi.setId(udi.getId());
				upUdi.setAccumulatedIncome(udi.getAccumulatedIncome());
				usersDreamInfoMapper.updateByPrimaryKeySelective(upUdi);
				
				jsonObject.put("data", udi);
			}
		} else {
			jsonObject.put("code", 1006);
			jsonObject.put("msg", "用户ID不能为空");
		}
		return jsonObject;
	}

}
