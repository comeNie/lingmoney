package com.mrbt.lingmoney.service.dream;

import com.alibaba.fastjson.JSONObject;
import com.mrbt.lingmoney.model.UsersDreamInfo;

public interface DreamServer {

	JSONObject queryDreamInfo(String uId);

	JSONObject queryBaseDreamInfo(Integer baseId);

	JSONObject selectDream(String uId, Integer baseId);

	UsersDreamInfo queryUsersHaveDream(String uId);

	JSONObject queryUsersDreamInfo(String uId, Integer baseId);

}
