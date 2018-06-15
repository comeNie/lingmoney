package com.mrbt.lingmoney.admin.service.product.impl;

import java.util.List;

import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.mrbt.lingmoney.admin.service.product.ActivityPropertyService;
import com.mrbt.lingmoney.mapper.ActivityPropertyMapper;
import com.mrbt.lingmoney.model.ActivityProperty;
import com.mrbt.lingmoney.model.ActivityPropertyExample;
import com.mrbt.lingmoney.model.ActivityPropertyWithBLOBs;
import com.mrbt.lingmoney.utils.FtpUtils;
import com.mrbt.lingmoney.utils.GridPage;
import com.mrbt.lingmoney.utils.ResultParame.ResultNumber;

/**
 * 产品设置——》活动属性
 *
 */
@Service
public class ActivityPropertyServiceImpl implements ActivityPropertyService {
	
	@Autowired
	private ActivityPropertyMapper activityPropertyMapper;
	
	@Autowired
	private FtpUtils ftpUtils;
	
	private String actImagePath = "actImage";

	@Override
	public GridPage<ActivityProperty> listGrid(RowBounds rowBounds) {
		ActivityPropertyExample example = new ActivityPropertyExample();
		example.setLimitStart(rowBounds.getOffset());
		example.setLimitEnd(rowBounds.getLimit());
		
		GridPage<ActivityProperty> result = new GridPage<ActivityProperty>();

		result.setRows(activityPropertyMapper.selectByExample(example));
		result.setTotal(activityPropertyMapper.countByExample(example));

		return result;
	}

	@Override
	public ActivityProperty findByPk(int id) {
		return activityPropertyMapper.selectByPrimaryKey(id);
	}

	@Override
	public int saveAndUpdate(MultipartFile file1, MultipartFile file2, ActivityPropertyWithBLOBs vo) {
		
		if (file1 != null && !file1.isEmpty()) {
			String codePath1 = ftpUtils.uploadImages(file1, actImagePath, ftpUtils);
			vo.setActTitleImage(codePath1);
		}
		if (file2 != null && !file2.isEmpty()) {
			String codePath2 = ftpUtils.uploadImages(file2, actImagePath, ftpUtils);
			vo.setActImage(codePath2);
		}
		if (vo.getId() == null || vo.getId() <= ResultNumber.ZERO.getNumber()) {
			if(vo.getActId()!=null && vo.getActId()!=""){
				ActivityPropertyExample example = new ActivityPropertyExample();
				example.createCriteria().andActIdEqualTo(vo.getActId());
				List<ActivityProperty> list=activityPropertyMapper.selectByExample(example);
				if (list.size()>ResultNumber.ZERO.getNumber()){ 
					return ResultNumber.ZERO.getNumber();
				}
			}
			return activityPropertyMapper.insert(vo);
		} else {
			ActivityPropertyWithBLOBs record = activityPropertyMapper.selectByPrimaryKey(vo.getId());
			if (record != null) {
				return activityPropertyMapper.updateByPrimaryKeySelective(vo);
			} else {
				return ResultNumber.ZERO.getNumber();
			}
		}
	}

}
