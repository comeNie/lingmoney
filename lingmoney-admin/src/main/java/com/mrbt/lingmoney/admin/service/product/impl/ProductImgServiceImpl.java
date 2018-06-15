package com.mrbt.lingmoney.admin.service.product.impl;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.mrbt.lingmoney.admin.service.product.ProductImgService;
import com.mrbt.lingmoney.mapper.ProductImgMapper;
import com.mrbt.lingmoney.model.ProductImg;
import com.mrbt.lingmoney.model.ProductImgExample;
import com.mrbt.lingmoney.utils.FtpUtils;
import com.mrbt.lingmoney.utils.GridPage;
import com.mrbt.lingmoney.utils.PageInfo;
import com.mrbt.lingmoney.utils.ResultParame;

/**
 * 产品图片设置
 * 
 */
@Service
public class ProductImgServiceImpl implements ProductImgService {

	@Autowired
	private ProductImgMapper productImgMapper;
	@Autowired
	private FtpUtils ftpUtils;

	/**
	 * 创建图片
	 * 
	 * @param vo
	 *            ProductImg
	 * @return 数据返回
	 */
	private ProductImgExample createProductImgExample(ProductImg vo) {
		ProductImgExample example = new ProductImgExample();
		ProductImgExample.Criteria cri = example.createCriteria()
				.andStatusNotEqualTo(ResultParame.ResultNumber.MINUS_ONE.getNumber());
		if (vo.getStatus() != null) {
			cri.andStatusEqualTo(vo.getStatus());
		}
		if (vo.getType() != null) {
			cri.andTypeEqualTo(vo.getType());
		}
		if (StringUtils.isNotBlank(vo.getName())) {
			cri.andNameLike("%" + vo.getName() + "%");
		}
		return example;
	}

	@Transactional
	@Override
	public void delete(int id) {
		ProductImg productImg = new ProductImg();
		productImg.setId(id);
		productImg.setStatus(ResultParame.ResultNumber.MINUS_ONE.getNumber());
		productImgMapper.updateByPrimaryKeySelective(productImg);
	}

	@Override
	public GridPage<ProductImg> listGrid(ProductImg vo, RowBounds page) {

		ProductImgExample example = createProductImgExample(vo);
		example.setLimitStart(page.getOffset());
		example.setLimitEnd(page.getLimit());

		GridPage<ProductImg> result = new GridPage<ProductImg>();
		result.setRows(productImgMapper.selectByExample(example));
		result.setTotal(productImgMapper.countByExample(example));

		return result;
	}

	@Override
	public List<ProductImg> listUrl(Integer type) {
		ProductImgExample example = new ProductImgExample();
		example.createCriteria().andStatusEqualTo(1).andTypeEqualTo(type);
		return productImgMapper.selectByExample(example);
	}

	@Override
	public PageInfo getList(ProductImg vo, PageInfo pageInfo) {
		ProductImgExample example = createProductImgExample(vo);
		example.setLimitStart(pageInfo.getFrom());
		example.setLimitEnd(pageInfo.getSize());

		int resSize = (int) productImgMapper.countByExample(example);
		List<ProductImg> list = productImgMapper.selectByExample(example);
		pageInfo.setCode(ResultParame.ResultInfo.SUCCESS.getCode());
		pageInfo.setRows(list);
		pageInfo.setTotal(resSize);
		return pageInfo;
	}

	@Transactional
	@Override
	public boolean changeStatus(Integer id) {
		boolean flag = false;
		if (id != null && id > 0) {
			ProductImg record = productImgMapper.selectByPrimaryKey(id);
			if (record != null) {
				if (record.getStatus() == null || record.getStatus() == 0) {
					record.setStatus(1);
				} else {
					record.setStatus(0);
				}
				productImgMapper.updateByPrimaryKeySelective(record);
				flag = true;
			}
		}
		return flag;
	}


	@Transactional
	@Override
	public void save(ProductImg vo, MultipartFile file, String bannerRootPath) {
		String imgUrl = ftpUtils.uploadImages(file, bannerRootPath, ftpUtils);
		if (imgUrl != null && !imgUrl.equals("300") && !imgUrl.equals("500")) {
			vo.setUrl(imgUrl);
		}
		vo.setStatus(0);
		productImgMapper.insert(vo);
	}

	@Transactional
	@Override
	public void update(ProductImg vo, MultipartFile file, String bannerRootPath) {
		String imgUrl = ftpUtils.uploadImages(file, bannerRootPath, ftpUtils);
		if (imgUrl != null && !imgUrl.equals("300") && !imgUrl.equals("500")) {
			vo.setUrl(imgUrl);
		}
		productImgMapper.updateByPrimaryKeySelective(vo);
	}
}
