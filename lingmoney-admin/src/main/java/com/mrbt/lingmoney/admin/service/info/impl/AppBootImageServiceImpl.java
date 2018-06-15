package com.mrbt.lingmoney.admin.service.info.impl;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.mrbt.lingmoney.admin.service.info.AppBootImageService;
import com.mrbt.lingmoney.mapper.AppBootImageMapper;
import com.mrbt.lingmoney.model.AppBootImage;
import com.mrbt.lingmoney.model.AppBootImageExample;
import com.mrbt.lingmoney.utils.AppCons;
import com.mrbt.lingmoney.utils.FtpUtils;
import com.mrbt.lingmoney.utils.PageInfo;
import com.mrbt.lingmoney.utils.ResultParame;

/**
 *@author syb
 *@date 2017年5月22日 下午3:39:55
 *@version 1.0
 *@description 
 **/
@Service
public class AppBootImageServiceImpl implements AppBootImageService {
	@Autowired
	private AppBootImageMapper appBootImageMapper;
	@Autowired
	private FtpUtils ftpUtils;
	
	@Override
	public PageInfo listBootImages(Integer pageNo, Integer pageSize) {
		if (StringUtils.isEmpty(pageNo)) {
			pageNo = 1;
		}
		if (StringUtils.isEmpty(pageSize)) {
			pageSize = AppCons.DEFAULT_PAGE_SIZE;
		}
		PageInfo pi = new PageInfo(pageNo, pageSize);
		AppBootImageExample example = new AppBootImageExample();
		example.setOrderByClause("status desc, create_time desc");
		pi.setRows(appBootImageMapper.selectByExample(example));
		pi.setTotal(appBootImageMapper.countByExample(example));
		pi.setCode(ResultParame.ResultInfo.SUCCESS.getCode());
		pi.setMsg("查询成功");
		return pi;
	}

	@Override
	public PageInfo saveBootImage(AppBootImage params, MultipartFile file) {
		PageInfo pi = new PageInfo();
		if (file != null) {
			String imgUrl = ftpUtils.uploadImages(file, "appBootImage", ftpUtils);
			if (imgUrl != null && !imgUrl.equals("300") && !imgUrl.equals("500")) {
				params.setImgUrl(imgUrl);
			}
		}
		if (params.getId() != null) { // 更新
			appBootImageMapper.updateByPrimaryKeySelective(params);
			pi.setMsg("更新成功");
		} else { // 新增
			params.setCreateTime(new Date());
			appBootImageMapper.insertSelective(params);
			pi.setMsg("保存成功");
		}
		pi.setCode(ResultParame.ResultInfo.SUCCESS.getCode());
		return pi;
	}

}
