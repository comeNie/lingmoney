package com.mrbt.lingmoney.service.newuserreward.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.mrbt.lingmoney.mapper.NewUserRewardMapper;
import com.mrbt.lingmoney.model.NewUserReward;
import com.mrbt.lingmoney.model.NewUserRewardExample;
import com.mrbt.lingmoney.service.newuserreward.NewUserRewardService;
import com.mrbt.lingmoney.utils.Common;

/**
 * @author luox
 * @Date 2017年6月26日
 */
@Service
public class NewUserRewardServiceImpl implements NewUserRewardService{
	
	@Autowired
	private NewUserRewardMapper userRewardMapper;

	@Override
	public JSONObject getNewuserReward(int clientType) {
		NewUserRewardExample example = new NewUserRewardExample();
		example.createCriteria().andStatusEqualTo(1);
		List<NewUserReward> list = userRewardMapper.selectByExample(example);
        if (Common.isNotBlank(list)) {
			JSONObject json = new JSONObject();
			NewUserReward reward = list.get(0);
			json.put("markedWords", reward.getMarkedWords());

            if (clientType == 2) {
				json.put("proPic", reward.getProPic());
				json.put("indexPic", reward.getIndexPic());
			}

			return json;
		}

		return null;
	}

	
	
}
