package com.mrbt.lingmoney.admin.service.product.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.mrbt.lingmoney.admin.service.product.ProductRecommendService;
import com.mrbt.lingmoney.commons.cache.RedisDao;
import com.mrbt.lingmoney.mapper.ProductCustomerMapper;
import com.mrbt.lingmoney.mapper.ProductRecommendMapper;
import com.mrbt.lingmoney.model.ProductRecommend;
import com.mrbt.lingmoney.model.ProductRecommendCustomer;
import com.mrbt.lingmoney.model.ProductRecommendExample;
import com.mrbt.lingmoney.utils.FtpUtils;
import com.mrbt.lingmoney.utils.PageInfo;
import com.mrbt.lingmoney.utils.ResultParame;

/**
 * 产品推荐设置
 * 
 * @author lihq
 * @date 2017年5月22日 下午3:09:17
 * @description
 * @version 1.0
 * @since 2017-04-12
 */
@Service
public class ProductRecommendServiceImpl implements ProductRecommendService {

	@Autowired
	private ProductRecommendMapper productRecommendMapper;
	@Autowired
	private ProductCustomerMapper productCustomerMapper;
	@Autowired
	private FtpUtils ftpUtils;
	@Autowired
	private RedisDao redisDao;

	@Transactional
	@Override
	public int delete(int id) {
		ProductRecommend res = productRecommendMapper.selectByPrimaryKey(id);
		if (res != null) {
			return productRecommendMapper.deleteByPrimaryKey(id);
		}
		return 0;
	}

	@Override
	public PageInfo getList(ProductRecommendCustomer vo, PageInfo pageInfo) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("pId", vo.getpId());
		map.put("cityCode", vo.getCityCode());
		map.put("limitStart", pageInfo.getFrom());
		map.put("limitEnd", pageInfo.getSize());

		long resSize = productCustomerMapper.selectProductRecommendListCount(map);
		List<ProductRecommendCustomer> list = productCustomerMapper.selectProductRecommendList(map);
		pageInfo.setRows(list);
		pageInfo.setTotal((int) resSize);
		return pageInfo;
	}

	/**
	 * 上传文件
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
	public int save(ProductRecommend vo, MultipartFile file1, MultipartFile file2, String bannerRootPath) {
		// 每个产品只能推荐一次
		ProductRecommendExample example = new ProductRecommendExample();
		example.createCriteria().andPIdEqualTo(vo.getpId());
		long count = productRecommendMapper.countByExample(example);
		if (count > 0) {
			return ResultParame.ResultNumber.MINUS2.getNumber();
		}

		String codePath1 = uploadFile(bannerRootPath, file1);
		if (codePath1 != null) {
			vo.setTitlePic(codePath1);
		}
		String codePath2 = uploadFile(bannerRootPath, file2);
		if (codePath2 != null) {
			vo.setActivityPic(codePath2);
		}
		return productRecommendMapper.insertSelective(vo);
	}

	@Transactional
	@Override
	public int update(ProductRecommend vo, MultipartFile file1, MultipartFile file2, String bannerRootPath) {
		// 每个产品只能推荐一次
		ProductRecommendExample example = new ProductRecommendExample();
		example.createCriteria().andPIdEqualTo(vo.getpId())
				.andPIdNotEqualTo(productRecommendMapper.selectByPrimaryKey(vo.getId()).getpId());
		long count = productRecommendMapper.countByExample(example);
		if (count > 0) {
			return ResultParame.ResultNumber.MINUS2.getNumber();
		}
		String codePath1 = uploadFile(bannerRootPath, file1);
		if (codePath1 != null) {
			vo.setTitlePic(codePath1);
		}
		String codePath2 = uploadFile(bannerRootPath, file2);
		if (codePath2 != null) {
			vo.setActivityPic(codePath2);
		}
		return productRecommendMapper.updateByPrimaryKey(vo);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public void reload() {
		List listX = productCustomerMapper.selectProductByIndexXadmin();
		List listY = productCustomerMapper.selectProductByIndexYadmin();

		redisDao.refreshKeyValueList("HOMR_LISTX", listX);
		redisDao.refreshKeyValueList("HOMR_LISTY", listY);
	}

}
