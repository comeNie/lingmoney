package com.mrbt.lingmoney.admin.service.commonweal.impl;


import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.mrbt.lingmoney.admin.service.commonweal.CommonwealService;
import com.mrbt.lingmoney.mapper.PublicBenefitActivitiesMapper;
import com.mrbt.lingmoney.model.PublicBenefitActivities;
import com.mrbt.lingmoney.model.PublicBenefitActivitiesExample;
import com.mrbt.lingmoney.model.PublicBenefitActivitiesWithBLOBs;
import com.mrbt.lingmoney.utils.FtpUtils;
import com.mrbt.lingmoney.utils.GridPage;
import com.mrbt.lingmoney.utils.PageInfo;
import com.mrbt.lingmoney.utils.ResultParame.ResultNumber;

@Service
public class CommonwealServiceImpl implements CommonwealService {
	
	@Autowired
	private PublicBenefitActivitiesMapper publicBenefitActivitiesMapper;
	
	private static final String actImagePath = "commonweal";
	
	@Autowired
	private FtpUtils ftpUtils;

	@Override
	public GridPage<PublicBenefitActivitiesWithBLOBs> listGrid(PageInfo pageinfo) {
		PublicBenefitActivitiesExample example = new PublicBenefitActivitiesExample();
		example.setLimitStart(pageinfo.getFrom());
		example.setLimitEnd(pageinfo.getSize());
		GridPage<PublicBenefitActivitiesWithBLOBs> result = new GridPage<PublicBenefitActivitiesWithBLOBs>();
		result.setRows(publicBenefitActivitiesMapper.selectByExampleWithBLOBs(example));
		result.setTotal(publicBenefitActivitiesMapper.countByExample(example));
		return result;
	}

	@Override
	public int saveAndUpdate(MultipartFile file1, PublicBenefitActivitiesWithBLOBs vo) {
		if (file1 != null && !file1.isEmpty()) {
			String codePath1 = ftpUtils.uploadImages(file1, actImagePath, ftpUtils);
			vo.setPbaPicture(codePath1);
		}
		if (vo.getId() == null || vo.getId() <= ResultNumber.ZERO.getNumber()) {
			vo.setStatus(0);
			vo.setCreateTime(new Date());
			return publicBenefitActivitiesMapper.insertSelective(vo);
		} else {
			return publicBenefitActivitiesMapper.updateByPrimaryKeySelective(vo);
		}
	}

	@Override
	public int del(Integer id) {
		PublicBenefitActivities pba = publicBenefitActivitiesMapper.selectByPrimaryKey(id);
		if(pba != null) {
			if (pba.getStatus() != null && pba.getStatus() > 0) {
				return 2;
			}else {
				return publicBenefitActivitiesMapper.deleteByPrimaryKey(id);
			}
		}
		return 0;
	}

	@Override
	public int push(Integer id) {
		PublicBenefitActivitiesWithBLOBs pba = publicBenefitActivitiesMapper.selectByPrimaryKey(id);
		if(pba != null) {
			if (pba.getStatus() != null && pba.getStatus() == 0) {
				PublicBenefitActivitiesWithBLOBs pbaStatus = new PublicBenefitActivitiesWithBLOBs();
				pbaStatus.setId(id);
				pbaStatus.setStatus(1);
				return publicBenefitActivitiesMapper.updateByPrimaryKeySelective(pbaStatus);
			}else {
				return 2;
			}
		}
		return 0;
	}

	@Override
	public int fulfil(Integer id) {
		PublicBenefitActivitiesWithBLOBs pba = new PublicBenefitActivitiesWithBLOBs();
		pba.setId(id);
		pba.setStatus(2);
		return publicBenefitActivitiesMapper.updateByPrimaryKeySelective(pba);
	}

}
