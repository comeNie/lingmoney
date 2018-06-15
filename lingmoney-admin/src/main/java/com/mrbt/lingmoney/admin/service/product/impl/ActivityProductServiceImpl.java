package com.mrbt.lingmoney.admin.service.product.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.mrbt.lingmoney.admin.service.product.ActivityProductService;
import com.mrbt.lingmoney.mapper.ActivityProductMapper;
import com.mrbt.lingmoney.mapper.ActivityPropertyMapper;
import com.mrbt.lingmoney.model.ActivityProduct;
import com.mrbt.lingmoney.model.ActivityProductExample;
import com.mrbt.lingmoney.model.ActivityProductWithBLOBs;
import com.mrbt.lingmoney.model.ActivityPropertyExample;
import com.mrbt.lingmoney.model.ActivityPropertyWithBLOBs;
import com.mrbt.lingmoney.utils.FtpUtils;
import com.mrbt.lingmoney.utils.PageInfo;
import com.mrbt.lingmoney.utils.ResultParame;

/**
 * 产品设置——》产品活动表
 */
@Service
public class ActivityProductServiceImpl implements ActivityProductService {

	@Autowired
	private ActivityProductMapper activityProductMapper;

	@Autowired
	private ActivityPropertyMapper activityPropertyMapper;

	@Autowired
	private FtpUtils ftpUtils;

	/**
	 * 创建查询条件
	 * 
	 * @param vo
	 *            ActivityProduct
	 * @return 数据返回
	 */
	private ActivityProductExample createActivityProductExample(ActivityProduct vo) {
		ActivityProductExample example = new ActivityProductExample();
		ActivityProductExample.Criteria cri = example.createCriteria();
		if (StringUtils.isNotBlank(vo.getActName())) {
			cri.andActNameLike("%" + vo.getActName() + "%");
		}
		if (StringUtils.isNotBlank(vo.getActUrl())) {
			cri.andActUrlLike("%" + vo.getActUrl() + "%");
		}
		cri.andStatusNotEqualTo(ResultParame.ResultNumber.MINUS_ONE.getNumber());
		example.setOrderByClause("create_time desc");
		return example;
	}

	@Override
	public PageInfo getList(ActivityProduct vo, PageInfo pageInfo) {
		ActivityProductExample example = createActivityProductExample(vo);
		example.setLimitStart(pageInfo.getFrom());
		example.setLimitEnd(pageInfo.getSize());

		int resSize = (int) activityProductMapper.countByExample(example);
		List<ActivityProduct> list = activityProductMapper.selectByExample(example);
		pageInfo.setRows(list);
		pageInfo.setTotal(resSize);
		return pageInfo;
	}

	@Override
	public List<String> listContent(int id) {
		ActivityProductWithBLOBs vo = activityProductMapper.selectByPrimaryKey(id);
		List<String> list = new ArrayList<String>();
		if (vo != null) {
			String actDesc = vo.getActDesc();
			String actDescMobile = vo.getActDescMobile();
			list.add(actDesc);
			list.add(actDescMobile);
		}
		return list;
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

	/**
	 * 上传
	 * 
	 * @param vo
	 *            ActivityProductWithBLOBs
	 * @param file1
	 *            file1
	 * @param file2
	 *            file2
	 * @param file3
	 *            file3
	 * @param bannerRootPath
	 *            bannerRootPath
	 */
	public void upload(ActivityProductWithBLOBs vo, MultipartFile file1, MultipartFile file2, MultipartFile file3,
			String bannerRootPath) {
		String codePath1 = uploadFile(bannerRootPath, file1);
		String codePath2 = uploadFile(bannerRootPath, file3);
		String codePath3 = uploadFile(bannerRootPath, file3);
		if (codePath1 != null) {
			vo.setActivityPic(codePath1);
		}
		if (codePath2 != null) {
			vo.setActivityGift(codePath2);
		}
		if (codePath3 != null) {
			vo.setActivityWord(codePath3);
		}
	}

	@Override
	public int saveAndUpdate(ActivityProductWithBLOBs vo, MultipartFile file1, MultipartFile file2, MultipartFile file3,
			String bannerRootPath) {
		if (vo.getId() == null || vo.getId() <= 0) {
			upload(vo, file1, file2, file3, bannerRootPath);
			vo.setCreateTime(new Date());
			return activityProductMapper.insert(vo);
		} else {
			upload(vo, file1, file2, file3, bannerRootPath);
			ActivityProduct record = activityProductMapper.selectByPrimaryKey(vo.getId());
			if (record != null) {
				return activityProductMapper.updateByPrimaryKeySelective(vo);
			} else {
				return 0;
			}
		}
	}

	@Override
	public int delete(int id) {
		try {
			ActivityProductWithBLOBs ap = activityProductMapper.selectByPrimaryKey(id);
			if (ap != null) {
				ap.setStatus(ResultParame.ResultNumber.MINUS_ONE.getNumber());

				if (activityProductMapper.updateByPrimaryKeySelective(ap) > 0) {
					ActivityPropertyExample example = new ActivityPropertyExample();
					ActivityPropertyExample.Criteria criteria = example.createCriteria();
					criteria.andActIdEqualTo(id + "");
					criteria.andStatusEqualTo(1);

					List<ActivityPropertyWithBLOBs> res = activityPropertyMapper.selectByExampleWithBLOBs(example);
					if (res != null && res.size() > 0) {
						ActivityPropertyWithBLOBs record = res.get(0);
						record.setStatus(ResultParame.ResultNumber.MINUS_ONE.getNumber());
						return activityPropertyMapper.updateByPrimaryKeySelective(record);
					} else {
						return 1;
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0;
	}

	@Transactional
	@Override
	public boolean changeStatus(Integer id) {
		boolean flag = false;
		if (id != null && id > 0) {
			ActivityProductWithBLOBs record = activityProductMapper.selectByPrimaryKey(id);
			if (record != null) {
				if (record.getStatus() == null || record.getStatus() == 0) {
					record.setStatus(1);
				} else {
					record.setStatus(0);
				}
				activityProductMapper.updateByPrimaryKeySelective(record);
				flag = true;
			}
		}
		return flag;
	}

	@Override
	public List<ActivityProduct> selectByActivityProduct(ActivityProduct activityProduct) {
		ActivityProductExample example = new ActivityProductExample();
		ActivityProductExample.Criteria cri = example.createCriteria();
		if (activityProduct.getStatus() != null) {
			cri.andStatusEqualTo(activityProduct.getStatus());
		}
		return activityProductMapper.selectByExample(example);
	}

}
