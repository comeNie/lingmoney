package com.mrbt.lingmoney.admin.service.gift.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mrbt.lingmoney.admin.service.gift.LingbaoLotteryRatioService;
import com.mrbt.lingmoney.mapper.LingbaoLotteryRatioMapper;
import com.mrbt.lingmoney.model.LingbaoLotteryRatio;
import com.mrbt.lingmoney.utils.PageInfo;

/**
 * 我的领地抽奖比例
 *
 */
@Service
public class LingbaoLotteryRatioServiceImpl implements
		LingbaoLotteryRatioService {

	@Autowired
	private LingbaoLotteryRatioMapper lingbaoLotteryRatioMapper;

	@Override
	public LingbaoLotteryRatio findById(int id) {
		return lingbaoLotteryRatioMapper.selectByPrimaryKey(id);
	}

	@Override
	@Transactional
	public void delete(int id) {
		lingbaoLotteryRatioMapper.deleteByPrimaryKey(id);
	}

	@Override
	@Transactional()
	public void save(LingbaoLotteryRatio vo) {
		lingbaoLotteryRatioMapper.insert(vo);
	}

	@Override
	@Transactional()
	public void updateByPrimaryKeySelective(LingbaoLotteryRatio vo) {
		lingbaoLotteryRatioMapper.updateByPrimaryKeySelective(vo);
	}

	@Override
	@Transactional()
	public void updateByPrimaryKey(LingbaoLotteryRatio vo) {
		lingbaoLotteryRatioMapper.updateByPrimaryKey(vo);
	}

	@Override
	public PageInfo getList(PageInfo pageInfo) {
		int resSize = lingbaoLotteryRatioMapper.findCountByCondition(pageInfo);
		List<LingbaoLotteryRatio> list = lingbaoLotteryRatioMapper.findByCondition(pageInfo);
		pageInfo.setRows(list);
		pageInfo.setTotal(resSize);
		return pageInfo;
	}

	@Override
	public boolean changeStatus(Integer id) {
		boolean flag = false;
		if (id != null && id > 0) {
			LingbaoLotteryRatio record = lingbaoLotteryRatioMapper.selectByPrimaryKey(id);
			if (record != null) {
				if (record.getStatus() == null || record.getStatus() == 0) {
					record.setStatus(1);
				} else {
					record.setStatus(0);
				}
				lingbaoLotteryRatioMapper.updateByPrimaryKey(record);
				flag = true;
			}
		}
		return flag;
	}
}
