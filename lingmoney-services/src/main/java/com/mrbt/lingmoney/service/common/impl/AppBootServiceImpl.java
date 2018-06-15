package com.mrbt.lingmoney.service.common.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.mrbt.lingmoney.mapper.AppBootImageMapper;
import com.mrbt.lingmoney.mapper.InfoClientVersionMapper;
import com.mrbt.lingmoney.model.AppBootImage;
import com.mrbt.lingmoney.model.AppBootImageExample;
import com.mrbt.lingmoney.model.InfoClientVersion;
import com.mrbt.lingmoney.model.InfoClientVersionExample;
import com.mrbt.lingmoney.service.common.AppBootService;
import com.mrbt.lingmoney.utils.PageInfo;
import com.mrbt.lingmoney.utils.ResultParame.ResultInfo;
import com.mrbt.lingmoney.utils.Validation;

/**
 * APP BOOT
 */
@Service
public class AppBootServiceImpl implements AppBootService {
	
	@Autowired
	private AppBootImageMapper appBootImageMapper;

	@Autowired
	private InfoClientVersionMapper infoClientVersionMapper;
	
	@Override
	public void queryAppBootImage(String sizeCode, String cityCode, PageInfo pageInfo) {

		AppBootImageExample example = new AppBootImageExample();

		if (cityCode == null || cityCode.equals("")) {
			cityCode = "000";
		}

		Date date = new Date();
		example.createCriteria().andStatusEqualTo(1).andCityCodeEqualTo(cityCode)
				.andSizeCodeEqualTo(Integer.parseInt(sizeCode))
				.andShowEndTimeGreaterThan(date).andCreateTimeLessThan(date);

		List<AppBootImage> resList = appBootImageMapper.selectByExample(example);
		
        if (resList != null && resList.size() > 0) {
            pageInfo.setCode(ResultInfo.SUCCESS.getCode());
			pageInfo.setMsg("查询成功");
			pageInfo.setRows(resList);
        } else {
            pageInfo.setCode(ResultInfo.RETURN_DATA_EMPTY_DATA.getCode());
			pageInfo.setMsg("查询数据为空");
		}
	}

	@Override
	public void queryUpdateVersion(String version, Integer type, PageInfo pageInfo) {
		InfoClientVersionExample example = new InfoClientVersionExample();
		example.setLimitStart(0);
		example.setLimitEnd(1);
		example.createCriteria().andTypeEqualTo(type);
		example.setOrderByClause("create_time desc");
		
		
		List<InfoClientVersion> result = infoClientVersionMapper.selectByExample(example);
		
        if (result != null && result.size() > 0) {
			InfoClientVersion icv = result.get(0);
			
            pageInfo.setCode(ResultInfo.SUCCESS.getCode());
			pageInfo.setMsg("有新版本");
			pageInfo.setObj(icv);
        } else {
            pageInfo.setCode(ResultInfo.EMPTY_DATA.getCode());
			pageInfo.setMsg("没有新版本");
		}
	}

	@Override
	public PageInfo queryAppBootImage(String sizeCode, String cityCode) {

		PageInfo pageInfo = new PageInfo();

		AppBootImageExample example = new AppBootImageExample();

		if (cityCode == null || cityCode.equals("")) {
			cityCode = "000";
		}

		example.createCriteria().andStatusEqualTo(1).andCityCodeEqualTo(cityCode)
				.andSizeCodeEqualTo(Integer.parseInt(sizeCode)).andShowEndTimeGreaterThan(new Date());

		List<AppBootImage> resList = appBootImageMapper.selectByExample(example);

		if (resList != null && resList.size() > 0) {
            pageInfo.setCode(ResultInfo.SUCCESS.getCode());
			pageInfo.setMsg("查询成功");

			for (AppBootImage abi : resList) {
				if (abi.getType() == 1) {
					// 如果是产品，根据产品批次号查询最新一个产品
					Integer pid = appBootImageMapper.queryNewestProductByBatch(abi.getData());
					if (!StringUtils.isEmpty(pid)) {
						abi.setData(pid + "");
					}
				}
			}

			pageInfo.setRows(resList);
		} else {
            pageInfo.setCode(ResultInfo.RETURN_DATA_EMPTY_DATA.getCode());
			pageInfo.setMsg("查询数据为空");
		}

		return pageInfo;
	}
}
