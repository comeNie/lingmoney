package com.mrbt.lingmoney.admin.service.info.impl;

import java.util.Date;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.mrbt.lingmoney.admin.service.info.InfoClientBannerService;
import com.mrbt.lingmoney.commons.cache.RedisDao;
import com.mrbt.lingmoney.mapper.InfoClientBannerMapper;
import com.mrbt.lingmoney.model.InfoClientBanner;
import com.mrbt.lingmoney.model.InfoClientBannerExample;
import com.mrbt.lingmoney.utils.FtpUtils;
import com.mrbt.lingmoney.utils.PageInfo;
import com.mrbt.lingmoney.utils.ResultParame;

/**
 * 手机端banner
 */
@Service
public class InfoClientBannerServiceImpl implements InfoClientBannerService {

	@Autowired
	private InfoClientBannerMapper infoClientBannerMapper;
	@Autowired
	private FtpUtils ftpUtils;
	
	@Autowired
	private RedisDao redisDao;
	
	private static final String MOBILE_HOME_BANNER_INFO = "MOBILE_HOME_BANNER_INFO_";

	@Override
	public void delete(Integer id) {
		InfoClientBanner banner = new InfoClientBanner();
		banner.setId(id);
		banner.setStatus(ResultParame.ResultNumber.MINUS_ONE.getNumber());
		infoClientBannerMapper.updateByPrimaryKeySelective(banner);
	}

	@Override
	public void listGrid(InfoClientBanner vo, PageInfo pageInfo) {
		InfoClientBannerExample example = createInfoBannerExample(vo);
		example.setLimitStart(pageInfo.getFrom());
		example.setLimitEnd(pageInfo.getPagesize());
		pageInfo.setRows(infoClientBannerMapper.selectByExample(example));
		pageInfo.setTotal((int) infoClientBannerMapper.countByExample(example));
	}

	/**
	 * 生成辅助类根据实体bean
	 * 
	 * @param vo
	 *            实体bean
	 * @return AdminBannerExample辅助类
	 */
	private InfoClientBannerExample createInfoBannerExample(InfoClientBanner vo) {

		InfoClientBannerExample example = new InfoClientBannerExample();
		InfoClientBannerExample.Criteria cri = example.createCriteria()
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

	/**
	 * 文件上传
	 * 
	 * @param bannerRootPath
	 *            bannerRootPath
	 * @param file
	 *            file
	 * @return 数据返回
	 */
	public String uploadFile(String bannerRootPath, MultipartFile file) {
		String imgUrl = ftpUtils.uploadImages(file, bannerRootPath, ftpUtils);
		if (imgUrl != null && !imgUrl.equals("300") && !imgUrl.equals("500")) {
			return imgUrl;
		}
		return null;
	}

	@Transactional
	@Override
	public void save(InfoClientBanner vo, MultipartFile file1, MultipartFile file2, MultipartFile file3,
			MultipartFile file4, MultipartFile file5, MultipartFile file6, String bannerRootPath) {
		String codePath1 = uploadFile(bannerRootPath, file1);
		if (codePath1 != null) {
			vo.setCodePath1(codePath1);
		}
		String codePath2 = uploadFile(bannerRootPath, file2);
		if (codePath2 != null) {
			vo.setCodePath2(codePath2);
		}
		String codePath3 = uploadFile(bannerRootPath, file3);
		if (codePath3 != null) {
			vo.setCodePath3(codePath3);
		}
		String codePath4 = uploadFile(bannerRootPath, file4);
		if (codePath4 != null) {
			vo.setCodePath4(codePath4);
		}
		String codePath5 = uploadFile(bannerRootPath, file5);
		if (codePath5 != null) {
			vo.setCodePath5(codePath5);
		}
		String codePath6 = uploadFile(bannerRootPath, file6);
		if (codePath6 != null) {
			vo.setCodePath6(codePath6);
		}
		vo.setStatus(0);
		vo.setCreateTime(new Date());
		infoClientBannerMapper.insert(vo);
	}

	@Transactional
	@Override
	public void update(InfoClientBanner vo, MultipartFile file1, MultipartFile file2, MultipartFile file3,
			MultipartFile file4, MultipartFile file5, MultipartFile file6, String bannerRootPath) {
		String codePath1 = uploadFile(bannerRootPath, file1);
		if (codePath1 != null) {
			vo.setCodePath1(codePath1);
		}
		String codePath2 = uploadFile(bannerRootPath, file2);
		if (codePath2 != null) {
			vo.setCodePath2(codePath2);
		}
		String codePath3 = uploadFile(bannerRootPath, file3);
		if (codePath3 != null) {
			vo.setCodePath3(codePath3);
		}
		String codePath4 = uploadFile(bannerRootPath, file4);
		if (codePath4 != null) {
			vo.setCodePath4(codePath4);
		}
		String codePath5 = uploadFile(bannerRootPath, file5);
		if (codePath5 != null) {
			vo.setCodePath5(codePath5);
		}
		String codePath6 = uploadFile(bannerRootPath, file6);
		if (codePath6 != null) {
			vo.setCodePath6(codePath6);
		}
		infoClientBannerMapper.updateByPrimaryKeySelective(vo);
	}

	@Transactional
	@Override
	public boolean changeStatus(Integer id) {
		boolean flag = false;
		if (id != null && id > 0) {
			InfoClientBanner record = infoClientBannerMapper.selectByPrimaryKey(id);
			if (record != null) {
				if (record.getStatus() == null || record.getStatus() == 0) {
					record.setStatus(1);
				} else {
					record.setStatus(0);
				}
				infoClientBannerMapper.updateByPrimaryKeySelective(record);
				flag = true;
			}
		}
		return flag;
	}

	@Override
	public void refresh() {
		Set<?> keys = redisDao.findKeys(MOBILE_HOME_BANNER_INFO + "*");
		for (Object key : keys) {
			redisDao.delete(key.toString());
		}
	}

}
