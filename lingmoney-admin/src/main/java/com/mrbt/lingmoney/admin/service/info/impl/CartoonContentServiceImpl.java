package com.mrbt.lingmoney.admin.service.info.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.mrbt.lingmoney.admin.service.info.CartoonContentService;
import com.mrbt.lingmoney.mapper.CartoonContentMapper;
import com.mrbt.lingmoney.model.CartoonContent;
import com.mrbt.lingmoney.model.CartoonContentExample;
import com.mrbt.lingmoney.utils.FtpUtils;
import com.mrbt.lingmoney.utils.PageInfo;
import com.mrbt.lingmoney.utils.ResultParame;

/**
 * 卡通
 */
@Service
public class CartoonContentServiceImpl implements CartoonContentService {
	@Autowired
	private CartoonContentMapper cartoonContentMapper;
	@Autowired
	private FtpUtils ftpUtils;

	/**
	 * 生成辅助类
	 * 
	 * @param vo
	 *            实体bean
	 * @return 辅助类
	 */
	private CartoonContentExample createCartoonContentExample(CartoonContent vo) {
		CartoonContentExample example = new CartoonContentExample();
		CartoonContentExample.Criteria cri = example.createCriteria()
				.andStatusNotEqualTo(ResultParame.ResultNumber.MINUS_ONE.getNumber());
		if (vo.getcId() != null) {
			cri.andCIdEqualTo(vo.getcId());
		}
		if (vo.getStatus() != null) {
			cri.andStatusEqualTo(vo.getStatus());
		}
		example.setOrderByClause("pub_date desc");

		return example;
	}

	@Transactional
	@Override
	public void delete(int id) {
		CartoonContent cartoonContent = new CartoonContent();
		cartoonContent.setId(id);
		cartoonContent.setStatus(ResultParame.ResultNumber.MINUS_ONE.getNumber());
		cartoonContentMapper.updateByPrimaryKeySelective(cartoonContent);
	}

	@Transactional
	@Override
	public boolean changeStatus(Integer id) {
		boolean flag = false;
		if (id != null && id > 0) {
			CartoonContent record = cartoonContentMapper.selectByPrimaryKey(id);
			if (record != null) {
				if (record.getStatus() == null || record.getStatus() == 0) {
					record.setStatus(1);
					record.setPubDate(new Date());
				} else {
					record.setStatus(0);
				}
				cartoonContentMapper.updateByPrimaryKeySelective(record);
				flag = true;
			}
		}
		return flag;
	}

	@Override
	public PageInfo getList(CartoonContent vo, PageInfo pageInfo) {
		CartoonContentExample example = createCartoonContentExample(vo);
		example.setLimitStart(pageInfo.getFrom());
		example.setLimitEnd(pageInfo.getSize());

		int resSize = (int) cartoonContentMapper.countByExample(example);
		List<CartoonContent> list = cartoonContentMapper.selectByExample(example);
		pageInfo.setRows(list);
		pageInfo.setTotal(resSize);
		return pageInfo;
	}

	/**
	 * 数据返回
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
	public void save(CartoonContent vo, MultipartFile file1, MultipartFile file2, String bannerRootPath) {

		String codePath1 = uploadFile(bannerRootPath, file1);
		if (codePath1 != null) {
			vo.setContentPic(codePath1);
		}
		String codePath2 = uploadFile(bannerRootPath, file2);
		if (codePath2 != null) {
			vo.setThemePic(codePath2);
		}
		vo.setStatus(0);
		cartoonContentMapper.insertSelective(vo);
	}

	@Transactional
	@Override
	public void update(CartoonContent vo, MultipartFile file1, MultipartFile file2, String bannerRootPath) {

		String codePath1 = uploadFile(bannerRootPath, file1);
		if (codePath1 != null) {
			vo.setContentPic(codePath1);
		}
		String codePath2 = uploadFile(bannerRootPath, file2);
		if (codePath2 != null) {
			vo.setThemePic(codePath2);
		}
		cartoonContentMapper.updateByPrimaryKeySelective(vo);
	}
}
