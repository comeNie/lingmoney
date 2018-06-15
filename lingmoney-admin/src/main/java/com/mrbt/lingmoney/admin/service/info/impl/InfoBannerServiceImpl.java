package com.mrbt.lingmoney.admin.service.info.impl;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.mrbt.lingmoney.admin.service.info.InfoBannerService;
import com.mrbt.lingmoney.commons.cache.RedisDao;
import com.mrbt.lingmoney.mapper.InfoBannerMapper;
import com.mrbt.lingmoney.model.InfoBanner;
import com.mrbt.lingmoney.model.InfoBannerExample;
import com.mrbt.lingmoney.utils.FtpUtils;
import com.mrbt.lingmoney.utils.PageInfo;
import com.mrbt.lingmoney.utils.ResultParame;

/**
 * 首页小banner
 * 
 */
@Service
public class InfoBannerServiceImpl implements InfoBannerService {
	@Autowired
	private InfoBannerMapper infoBannerMapper;

	@Autowired
	private RedisDao redisDao;

	@Autowired
	private FtpUtils ftpUtils;

	/**
	 * 生成辅助类根据实体bean
	 * 
	 * @param vo
	 *            实体bean
	 * @return AdminBannerExample辅助类
	 */
	private InfoBannerExample createInfoBannerExample(InfoBanner vo) {
		InfoBannerExample example = new InfoBannerExample();
		InfoBannerExample.Criteria cri = example.createCriteria()
				.andStatusNotEqualTo(ResultParame.ResultNumber.MINUS_ONE.getNumber());

		if (StringUtils.isNotBlank(vo.getName())) {
			cri.andNameLike("%" + vo.getName() + "%");
		}
		if (vo.getStatus() != null) {
			cri.andStatusEqualTo(vo.getStatus());
		}
		example.setOrderByClause("level");
		return example;
	}

	@Transactional
	@Override
	public void delete(int id) {
		InfoBanner infoBanner = new InfoBanner();
		infoBanner.setId(id);
		infoBanner.setStatus(ResultParame.ResultNumber.MINUS_ONE.getNumber());
		infoBannerMapper.updateByPrimaryKeySelective(infoBanner);
	}

	@Transactional
	@Override
	public void save(InfoBanner vo, MultipartFile file, String bannerRootPath) {
		String imgUrl = ftpUtils.uploadImages(file, bannerRootPath, ftpUtils);
		if (imgUrl != null && !imgUrl.equals("300") && !imgUrl.equals("500")) {
			vo.setPath(imgUrl);
		}
		vo.setStatus(0);
		infoBannerMapper.insert(vo);
	}

	@Transactional
	@Override
	public void update(InfoBanner vo, MultipartFile file, String bannerRootPath) {
		String imgUrl = ftpUtils.uploadImages(file, bannerRootPath, ftpUtils);
		if (imgUrl != null && !imgUrl.equals("300") && !imgUrl.equals("500")) {
			vo.setPath(imgUrl);
		}
		infoBannerMapper.updateByPrimaryKeySelective(vo);
	}

	@Override
	public PageInfo getList(InfoBanner vo, PageInfo pageInfo) {
		InfoBannerExample example = createInfoBannerExample(vo);
		example.setLimitStart(pageInfo.getFrom());
		example.setLimitEnd(pageInfo.getSize());

		int resSize = (int) infoBannerMapper.countByExample(example);
		List<InfoBanner> list = infoBannerMapper.selectByExample(example);
		pageInfo.setCode(ResultParame.ResultInfo.SUCCESS.getCode());
		pageInfo.setRows(list);
		pageInfo.setTotal(resSize);
		return pageInfo;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public PageInfo reload(InfoBanner vo, PageInfo pageInfo) {
		InfoBannerExample example = createInfoBannerExample(vo);
		example.setLimitStart(pageInfo.getFrom());
		example.setLimitEnd(pageInfo.getSize());

		List list = infoBannerMapper.selectByExample(example);
		redisDao.refreshKeyValueList("HOMR_BANNER_INFRO", list);
		pageInfo.setCode(ResultParame.ResultInfo.SUCCESS.getCode());
		return pageInfo;
	}

	@Transactional
	@Override
	public boolean changeStatus(Integer id) {
		boolean flag = false;
		if (id != null && id > 0) {
			InfoBanner record = infoBannerMapper.selectByPrimaryKey(id);
			if (record != null) {
				if (record.getStatus() == null || record.getStatus() == 0) {
					record.setStatus(1);
				} else {
					record.setStatus(0);
				}
				infoBannerMapper.updateByPrimaryKeySelective(record);
				flag = true;
			}
		}
		return flag;
	}
}
