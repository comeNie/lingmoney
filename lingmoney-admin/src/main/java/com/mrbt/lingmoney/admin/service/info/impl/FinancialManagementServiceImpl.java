package com.mrbt.lingmoney.admin.service.info.impl;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.mrbt.lingmoney.admin.service.info.FinancialManagementService;
import com.mrbt.lingmoney.mapper.FinancialManagementMapper;
import com.mrbt.lingmoney.model.FinancialManagement;
import com.mrbt.lingmoney.model.FinancialManagementExample;
import com.mrbt.lingmoney.utils.FtpUtils;
import com.mrbt.lingmoney.utils.PageInfo;

/**
 * 理财经
 * 
 */
@Service
public class FinancialManagementServiceImpl implements FinancialManagementService {
	@Autowired
	private FinancialManagementMapper financialManagementMapper;
	@Autowired
	private FtpUtils ftpUtils;

	/**
	 * 生成辅助类根据实体bean
	 * 
	 * @param vo
	 *            实体bean
	 * @return AdminBannerExample辅助类
	 */
	private FinancialManagementExample createFinancialManagementExample(FinancialManagement vo) {
		FinancialManagementExample example = new FinancialManagementExample();
		if (StringUtils.isNotBlank(vo.getTitle())) {
			example.createCriteria().andTitleLike("%" + vo.getTitle() + "%");
		}
		example.setOrderByClause("pub_date desc");
		return example;
	}

	@Transactional
	@Override
	public void delete(int id) {
		financialManagementMapper.deleteByPrimaryKey(id);
	}

	@Transactional
	@Override
	public void save(FinancialManagement vo, MultipartFile file, String bannerRootPath) {
		String imgUrl = ftpUtils.uploadImages(file, bannerRootPath, ftpUtils);
		if (imgUrl != null && !imgUrl.equals("300") && !imgUrl.equals("500")) {
			vo.setIndexPic(imgUrl);
		}
		financialManagementMapper.insert(vo);
	}

	@Transactional
	@Override
	public void update(FinancialManagement vo, MultipartFile file, String bannerRootPath) {
		String imgUrl = ftpUtils.uploadImages(file, bannerRootPath, ftpUtils);
		if (imgUrl != null && !imgUrl.equals("300") && !imgUrl.equals("500")) {
			vo.setIndexPic(imgUrl);
		}
		financialManagementMapper.updateByPrimaryKeySelective(vo);
	}

	@Override
	public String listContent(int id) {
		FinancialManagement record = financialManagementMapper.selectByPrimaryKey(id);
		if (record != null) {
			return record.getContent();
		}
		return "";
	}

	@Transactional
	@Override
	public boolean changeStatus(Integer id) {
		boolean flag = false;
		if (id != null && id > 0) {
			FinancialManagement record = financialManagementMapper.selectByPrimaryKey(id);
			if (record != null) {
				if (record.getStatus() == null || record.getStatus() == 0) {
					record.setStatus(1);
					record.setPubDate(new Date());
				} else {
					record.setStatus(0);
				}
				financialManagementMapper.updateByPrimaryKeySelective(record);
				flag = true;
			}
		}
		return flag;
	}

	@Override
	public PageInfo getList(FinancialManagement vo, PageInfo pageInfo) {
		FinancialManagementExample example = createFinancialManagementExample(vo);
		example.setLimitStart(pageInfo.getFrom());
		example.setLimitEnd(pageInfo.getSize());

		int resSize = (int) financialManagementMapper.countByExample(example);
		List<FinancialManagement> list = financialManagementMapper.selectByExample(example);
		pageInfo.setRows(list);
		pageInfo.setTotal(resSize);
		return pageInfo;
	}
}
