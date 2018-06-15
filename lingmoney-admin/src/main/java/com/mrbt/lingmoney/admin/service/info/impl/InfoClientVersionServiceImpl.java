package com.mrbt.lingmoney.admin.service.info.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.mrbt.lingmoney.admin.service.info.InfoClientVersionService;
import com.mrbt.lingmoney.commons.cache.RedisDao;
import com.mrbt.lingmoney.mapper.InfoClientVersionMapper;
import com.mrbt.lingmoney.model.InfoClientVersion;
import com.mrbt.lingmoney.model.InfoClientVersionExample;
import com.mrbt.lingmoney.utils.FtpUtils;
import com.mrbt.lingmoney.utils.PageInfo;
import com.mrbt.lingmoney.utils.ResultParame;

/**
 * 手机版本号
 */
@Service
public class InfoClientVersionServiceImpl implements InfoClientVersionService {
	@Autowired
	private InfoClientVersionMapper ifnfoClientVersionMapper;
	@Autowired
	private RedisDao redisDao;
	@Autowired
	private FtpUtils ftpUtils;

	@Override
	public void listGrid(InfoClientVersion vo, PageInfo pageInfo) {
		InfoClientVersionExample example = createInfoClientVersionExample(vo);
		example.setLimitStart(pageInfo.getFrom());
		example.setLimitEnd(pageInfo.getPagesize());
		pageInfo.setRows(ifnfoClientVersionMapper.selectByExample(example));
		pageInfo.setTotal((int) ifnfoClientVersionMapper.countByExample(example));
	}

	/**
	 * 生成辅助类根据实体bean
	 * 
	 * @param vo
	 *            实体bean
	 * @return InfoClientVersionExample辅助类
	 */
	private InfoClientVersionExample createInfoClientVersionExample(InfoClientVersion vo) {
		InfoClientVersionExample example = new InfoClientVersionExample();
		example.createCriteria().andStatusNotEqualTo(ResultParame.ResultNumber.MINUS_ONE.getNumber());
		example.setOrderByClause("id desc");
		return example;
	}

	/**
	 * 删除banner<br/>
	 * 
	 * @param id
	 *            id
	 */
	@Override
	@Transactional
	public void delete(int id) {
		InfoClientVersion version = new InfoClientVersion();
		version.setId(id);
		version.setStatus(ResultParame.ResultNumber.MINUS_ONE.getNumber());
		ifnfoClientVersionMapper.updateByPrimaryKeySelective(version);
	}

	/**
	 * 保存实体bean
	 * 
	 * @param vo
	 *            InfoClientVersion
	 * @throws Exception
	 *             异常
	 */
	@Override
	@Transactional()
	public void save(InfoClientVersion vo) {
		ifnfoClientVersionMapper.insertSelective(vo);
	}

	/**
	 * 更新实体bean
	 * 
	 * @param vo
	 *            InfoClientVersion
	 */
	@Override
	@Transactional()
	public void update(InfoClientVersion vo) {
		ifnfoClientVersionMapper.updateByPrimaryKeySelective(vo);
	}

	/**
	 * 根据主键查询
	 * 
	 * @param id
	 *            id
	 * @return 数据返回
	 */
	public InfoClientVersion findByPk(int id) {
		return ifnfoClientVersionMapper.selectByPrimaryKey(id);
	}

	/**
	 * 禁用、开通版本更新图片
	 * 
	 * @param status
	 *            0禁用 1开通
	 * @return 数据返回
	 */
	@Override
	public Integer imageUrlAllow(Integer status) {
		Integer res = 0;
		if (status == 1) {
			redisDao.set("imageUrlAllow", true);
			res = 1;
		} else {
			redisDao.set("imageUrlAllow", false);
			res = 1;
		}
		return res;
	}

	/**
	 * redis取版本更新图片
	 * 
	 * @return 数据返回
	 */
	@Override
	public String showImageUrl() {
		String imageUrl = null;
		Object obj = redisDao.get("imageUrl");
		if (obj != null) {
			imageUrl = (String) obj;
		}
		return imageUrl;
	}

	/**
	 * redis存放版本更新图片
	 * 
	 * @param file
	 *            file
	 * @param bannerRootPath
	 *            bannerRootPath
	 */
	@Override
	public void putImageUrl(MultipartFile file, String bannerRootPath) {
		String imgUrl = ftpUtils.uploadImages(file, bannerRootPath, ftpUtils);
		if (imgUrl != null && !imgUrl.equals("300") && !imgUrl.equals("500")) {
			redisDao.set("imageUrl", imgUrl);
		}
	}
}
