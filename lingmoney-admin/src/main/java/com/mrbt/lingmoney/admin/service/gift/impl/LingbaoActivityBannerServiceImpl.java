package com.mrbt.lingmoney.admin.service.gift.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mrbt.lingmoney.admin.service.gift.LingbaoActivityBannerService;
import com.mrbt.lingmoney.mapper.LingbaoActivityBannerMapper;
import com.mrbt.lingmoney.model.LingbaoActivityBanner;
import com.mrbt.lingmoney.model.LingbaoActivityBannerExample;
import com.mrbt.lingmoney.utils.PageInfo;
import com.mrbt.lingmoney.utils.ResultParame;

/**
 * 我的领地活动banner
 *
 */
@Service
public class LingbaoActivityBannerServiceImpl implements
		LingbaoActivityBannerService {
	@Autowired
	private LingbaoActivityBannerMapper lingbaoActivityBannerMapper;

	@Override
	public LingbaoActivityBanner findById(int id) {
		return lingbaoActivityBannerMapper.selectByPrimaryKey(id);
	}

	@Override
	@Transactional
	public void delete(int id) {
		lingbaoActivityBannerMapper.deleteByPrimaryKey(id);
	}

	@Override
	@Transactional()
	public void save(LingbaoActivityBanner vo) {
		lingbaoActivityBannerMapper.insert(vo);
	}

	@Override
	@Transactional()
	public void update(LingbaoActivityBanner vo) {
		lingbaoActivityBannerMapper.updateByPrimaryKeySelective(vo);
	}

	@Override
	public PageInfo getList(LingbaoActivityBannerExample example) {
		PageInfo pageInfo = new PageInfo();
		int resSize = (int) lingbaoActivityBannerMapper.countByExample(example);
		List<LingbaoActivityBanner> list = lingbaoActivityBannerMapper
				.selectByExample(example);
		pageInfo.setRows(list);
		pageInfo.setTotal(resSize);
		return pageInfo;
	}

	@Override
	public PageInfo changeStatus(Integer id) {
		PageInfo pageInfo = new PageInfo();
		try {
			if (id != null && id > 0) {
				LingbaoActivityBanner record = lingbaoActivityBannerMapper
						.selectByPrimaryKey(id);
				if (record != null) {
					if (record.getStatus() == null || record.getStatus() == 0) { // 不可用，设置为可用。
						record.setStatus(1);
					} else { // 可用，设置为不可用。
						record.setStatus(0);
					}
					lingbaoActivityBannerMapper
							.updateByPrimaryKeySelective(record);
					pageInfo.setCode(ResultParame.ResultInfo.SUCCESS.getCode());
					return pageInfo;
				}
			}
			pageInfo.setCode(ResultParame.ResultInfo.RETURN_DATA_EMPTY_DATA.getCode());
			pageInfo.setMsg("找不到记录");
		} catch (Exception e) {
			e.printStackTrace();
			pageInfo.setCode(ResultParame.ResultInfo.SERVER_ERROR.getCode());
			pageInfo.setMsg("服务器出错");
		}
		return pageInfo;
	}

}
