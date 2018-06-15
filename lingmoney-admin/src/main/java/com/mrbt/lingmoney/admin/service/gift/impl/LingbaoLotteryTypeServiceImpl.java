package com.mrbt.lingmoney.admin.service.gift.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mrbt.lingmoney.admin.service.gift.LingbaoLotteryTypeService;
import com.mrbt.lingmoney.mapper.LingbaoLotteryRatioMapper;
import com.mrbt.lingmoney.mapper.LingbaoLotteryTypeMapper;
import com.mrbt.lingmoney.model.LingbaoLotteryRatioExample;
import com.mrbt.lingmoney.model.LingbaoLotteryType;
import com.mrbt.lingmoney.model.LingbaoLotteryTypeExample;
import com.mrbt.lingmoney.utils.PageInfo;
import com.mrbt.lingmoney.utils.ResultParame;

/**
 * 我的领地活动类型
 *
 */
@Service
public class LingbaoLotteryTypeServiceImpl implements LingbaoLotteryTypeService {

	@Autowired
	private LingbaoLotteryTypeMapper lingbaoLotteryTypeMapper;
	@Autowired
	private LingbaoLotteryRatioMapper lingbaoLotteryRatioMapper;

	@Override
	public LingbaoLotteryType findById(int id) {
		return lingbaoLotteryTypeMapper.selectByPrimaryKey(id);
	}

	@Override
	@Transactional
	public void delete(int id) {
		lingbaoLotteryTypeMapper.deleteByPrimaryKey(id);
	}

	@Override
	@Transactional()
	public void save(LingbaoLotteryType vo) {
		lingbaoLotteryTypeMapper.insert(vo);
	}

	@Override
	@Transactional()
	public void updateByPrimaryKeySelective(LingbaoLotteryType vo) {
		lingbaoLotteryTypeMapper.updateByPrimaryKeySelective(vo);
	}

	@Override
	@Transactional()
	public void updateByPrimaryKey(LingbaoLotteryType vo) {
		lingbaoLotteryTypeMapper.updateByPrimaryKey(vo);
	}

	@Override
	public PageInfo getList(LingbaoLotteryTypeExample example) {
		PageInfo pageInfo = new PageInfo();
		int resSize = (int) lingbaoLotteryTypeMapper.countByExample(example);
		List<LingbaoLotteryType> list = lingbaoLotteryTypeMapper
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
				LingbaoLotteryType record = lingbaoLotteryTypeMapper
						.selectByPrimaryKey(id);
				if (record != null) {
					if (record.getStatus() == null || record.getStatus() == 0) { // 不可用，设置为可用
						record.setStatus(1);
						lingbaoLotteryTypeMapper
								.updateByPrimaryKeySelective(record);
						pageInfo.setCode(ResultParame.ResultInfo.SUCCESS.getCode());
					} else { // 可用，设置为不可用。前提是判断该类型下是否有抽奖概率。
						LingbaoLotteryRatioExample example = new LingbaoLotteryRatioExample();
						LingbaoLotteryRatioExample.Criteria cri = example
								.createCriteria();
						cri.andTypeIdEqualTo(record.getId()); // 活动id
						cri.andStatusEqualTo(1); // 可用
						int count = (int) lingbaoLotteryRatioMapper.countByExample(example);
						if (count > 0) {
							pageInfo.setCode(ResultParame.ResultInfo.UPDATE_FAIL.getCode());
							pageInfo.setMsg("参数有误");
						} else {
							record.setStatus(0);
							lingbaoLotteryTypeMapper
									.updateByPrimaryKeySelective(record);
							pageInfo.setCode(ResultParame.ResultInfo.SUCCESS.getCode());
						}
					}
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
