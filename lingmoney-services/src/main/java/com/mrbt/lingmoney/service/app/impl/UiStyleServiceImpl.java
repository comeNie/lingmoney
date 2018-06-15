package com.mrbt.lingmoney.service.app.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mrbt.lingmoney.mapper.UiIconStyleMapper;
import com.mrbt.lingmoney.model.UiIconStyle;
import com.mrbt.lingmoney.model.UiIconStyleExample;
import com.mrbt.lingmoney.service.app.UiStyleService;
import com.mrbt.lingmoney.utils.FtpUtils;
import com.mrbt.lingmoney.utils.PageInfo;
import com.mrbt.lingmoney.utils.ResultParame.ResultInfo;

/**
 *
 *@author syb
 *@date 2017年9月13日 下午3:05:01
 *@version 1.0
 **/
@Service
public class UiStyleServiceImpl implements UiStyleService {

	@Autowired
	private UiIconStyleMapper uiIconStyleMapper;
	@Autowired
	private FtpUtils ftpUtils;

	@Override
	public PageInfo listUiStyle() {
		PageInfo pi = new PageInfo();

		UiIconStyleExample example = new UiIconStyleExample();
		example.createCriteria().andStatusEqualTo(1);

		example.setOrderByClause("id, group_name");

		List<UiIconStyle> list = uiIconStyleMapper.selectByExample(example);

		Map<String, List<UiIconStyle>> resultMap = new HashMap<String, List<UiIconStyle>>();
		if (list != null && list.size() > 0) {
            pi.setCode(ResultInfo.SUCCESS.getCode());
			pi.setMsg("success");

			for (UiIconStyle us : list) {
				if (resultMap.containsKey(us.getGroupName())) {
					List<UiIconStyle> li = resultMap.get(us.getGroupName());
					li.add(us);
					resultMap.put(us.getGroupName(), li);

				} else {
					List<UiIconStyle> li = new ArrayList<UiIconStyle>();
					li.add(us);
					resultMap.put(us.getGroupName(), li);
				}
			}
			pi.setObj(resultMap);

		} else {
            pi.setCode(ResultInfo.EMPTY_DATA.getCode());
			pi.setMsg("no data");
		}

		return pi;
	}

}
