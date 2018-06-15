package com.mrbt.lingmoney.admin.service.info.impl;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.mrbt.lingmoney.admin.service.info.InfoBigBannerService;
import com.mrbt.lingmoney.commons.cache.RedisDao;
import com.mrbt.lingmoney.mapper.InfoBigBannerMapper;
import com.mrbt.lingmoney.model.InfoBigBanner;
import com.mrbt.lingmoney.model.InfoBigBannerExample;
import com.mrbt.lingmoney.utils.FtpUtils;
import com.mrbt.lingmoney.utils.PageInfo;
import com.mrbt.lingmoney.utils.ResultParame;

/**
 * 首页大banner
 * 
 */
@Service
public class InfoBigBannerServiceImpl implements InfoBigBannerService {
	@Autowired
	private InfoBigBannerMapper infoBigBannerMapper;

	@Autowired
	private FtpUtils ftpUtils;
	
	@Autowired
	private RedisDao redisDao;
	
	private static final String PC_HOME_BANNER_INFO = "PC_HOME_BANNER_INFO";

	/**
	 * 生成辅助类根据实体bean
	 * 
	 * @param vo
	 *            实体bean
	 * @return AdminBannerExample辅助类
	 */
	private InfoBigBannerExample createInfoBigBannerExample(InfoBigBanner vo) {
		InfoBigBannerExample example = new InfoBigBannerExample();
		InfoBigBannerExample.Criteria cri = example.createCriteria()
				.andStatusNotEqualTo(ResultParame.ResultNumber.MINUS_ONE.getNumber());
		
		if (StringUtils.isNotBlank(vo.getName())) {
			cri.andNameLike("%" + vo.getName() + "%");
		}
		if (vo.getStatus() != null) {
			cri.andStatusEqualTo(vo.getStatus());
		}
		if (StringUtils.isNotBlank(vo.getCityCode())) {
			cri.andCityCodeEqualTo(vo.getCityCode());
		}
		example.setOrderByClause("status desc");
		return example;
	}

	@Transactional
	@Override
	public void delete(int id) {
		InfoBigBanner infoBigBanner = new InfoBigBanner();
		infoBigBanner.setId(id);
		infoBigBanner.setStatus(ResultParame.ResultNumber.MINUS_ONE.getNumber());
		infoBigBannerMapper.updateByPrimaryKeySelective(infoBigBanner);
	}

	@Transactional
	@Override
	public void save(InfoBigBanner vo, String bannerRootPath, MultipartFile file) {
		String imgUrl = ftpUtils.uploadImages(file, bannerRootPath, ftpUtils);
		if (imgUrl != null && !imgUrl.equals("300") && !imgUrl.equals("500")) {
			vo.setPath(imgUrl);
		}
		vo.setStatus(0);
		infoBigBannerMapper.insert(vo);
	}

	@Transactional
	@Override
	public void update(InfoBigBanner vo, MultipartFile file, String bannerRootPath) {
		String imgUrl = ftpUtils.uploadImages(file, bannerRootPath, ftpUtils);
		if (imgUrl != null && !imgUrl.equals("300") && !imgUrl.equals("500")) {
			vo.setPath(imgUrl);
		}
		infoBigBannerMapper.updateByPrimaryKeySelective(vo);
	}

	@Override
	public PageInfo getList(InfoBigBanner vo, PageInfo pageInfo) {
		InfoBigBannerExample example = createInfoBigBannerExample(vo);
		example.setLimitStart(pageInfo.getFrom());
		example.setLimitEnd(pageInfo.getSize());

		int resSize = (int) infoBigBannerMapper.countByExample(example);
		List<InfoBigBanner> list = infoBigBannerMapper.selectByExample(example);
		pageInfo.setRows(list);
		pageInfo.setTotal(resSize);
		return pageInfo;
	}

	@Transactional
	@Override
	public boolean changeStatus(Integer id) {
		boolean flag = false;
		if (id != null && id > 0) {
			InfoBigBanner record = infoBigBannerMapper.selectByPrimaryKey(id);
			if (record != null) {
				if (record.getStatus() == null || record.getStatus() == 0) {
					record.setStatus(1);
				} else {
					record.setStatus(0);
				}
				infoBigBannerMapper.updateByPrimaryKeySelective(record);
				flag = true;
			}
		}
		return flag;
	}

	@Override
	public void refresh() {
		redisDao.delete(PC_HOME_BANNER_INFO);
	}
}
