package com.mrbt.lingmoney.admin.service.info.impl;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.mrbt.lingmoney.admin.service.info.CartoonCategoryService;
import com.mrbt.lingmoney.mapper.CartoonCategoryMapper;
import com.mrbt.lingmoney.model.CartoonCategory;
import com.mrbt.lingmoney.model.CartoonCategoryExample;
import com.mrbt.lingmoney.utils.FtpUtils;
import com.mrbt.lingmoney.utils.PageInfo;
import com.mrbt.lingmoney.utils.ResultParame;

/**
 * 卡通分类
 */
@Service
public class CartoonCategoryServiceImpl implements CartoonCategoryService {
	@Autowired
	private CartoonCategoryMapper cartoonCategoryMapper;
	@Autowired
	private FtpUtils ftpUtils;

	/**
	 * 生成辅助类
	 * 
	 * @param vo
	 *            实体bean
	 * @return 辅助类
	 */
	private CartoonCategoryExample createCartoonCategoryExample(CartoonCategory vo) {
		CartoonCategoryExample example = new CartoonCategoryExample();
		CartoonCategoryExample.Criteria cri = example.createCriteria()
				.andStatusNotEqualTo(ResultParame.ResultNumber.MINUS_ONE.getNumber());
		if (StringUtils.isNotBlank(vo.getName())) {
			cri.andNameLike("%" + vo.getName() + "%");
		}
		if (vo.getStatus() != null) {
			cri.andStatusEqualTo(vo.getStatus());
		}
		return example;
	}

	@Transactional
	@Override
	public void delete(int id) {
		CartoonCategory cartoonCategory = new CartoonCategory();
		cartoonCategory.setId(id);
		cartoonCategory.setStatus(ResultParame.ResultNumber.MINUS_ONE.getNumber());
		cartoonCategoryMapper.updateByPrimaryKeySelective(cartoonCategory);
	}

	@Transactional
	@Override
	public void save(CartoonCategory vo, MultipartFile file, String bannerRootPath) {
		String imgUrl = ftpUtils.uploadImages(file, bannerRootPath, ftpUtils);
		if (imgUrl != null && !imgUrl.equals("300") && !imgUrl.equals("500")) {
			vo.setIndexPic(imgUrl);
			vo.setParentid(0);
			vo.setStatus(0);
		}
		cartoonCategoryMapper.insert(vo);
	}

	@Transactional
	@Override
	public void update(CartoonCategory vo, MultipartFile file, String bannerRootPath) {
		String imgUrl = ftpUtils.uploadImages(file, bannerRootPath, ftpUtils);
		if (imgUrl != null && !imgUrl.equals("300") && !imgUrl.equals("500")) {
			vo.setIndexPic(imgUrl);
		}
		cartoonCategoryMapper.updateByPrimaryKeySelective(vo);
	
	}

	@Override
	public PageInfo getList(CartoonCategory vo, PageInfo pageInfo) {
		CartoonCategoryExample example = createCartoonCategoryExample(vo);
		example.setLimitStart(pageInfo.getFrom());
		example.setLimitEnd(pageInfo.getSize());

		int resSize = (int) cartoonCategoryMapper.countByExample(example);
		List<CartoonCategory> list = cartoonCategoryMapper.selectByExample(example);
		pageInfo.setRows(list);
		pageInfo.setTotal(resSize);
		return pageInfo;
	}

	@Transactional
	@Override
	public boolean changeStatus(Integer id) {
		boolean flag = false;
		if (id != null && id > 0) {
			CartoonCategory record = cartoonCategoryMapper.selectByPrimaryKey(id);
			if (record != null) {
				if (record.getStatus() == null || record.getStatus() == 0) {
					record.setStatus(1);
				} else {
					record.setStatus(0);
				}
				cartoonCategoryMapper.updateByPrimaryKeySelective(record);
				flag = true;
			}
		}
		return flag;
	}
}
