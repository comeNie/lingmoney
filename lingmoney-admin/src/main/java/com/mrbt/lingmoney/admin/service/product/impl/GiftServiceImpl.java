package com.mrbt.lingmoney.admin.service.product.impl;

import java.util.List;

import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mrbt.lingmoney.admin.service.product.GiftService;
import com.mrbt.lingmoney.mapper.GiftMapper;
import com.mrbt.lingmoney.model.Gift;
import com.mrbt.lingmoney.model.GiftExample;
import com.mrbt.lingmoney.utils.GridPage;
import com.mrbt.lingmoney.utils.ResultParame;

/**
 * 产品设置——》礼品
 *
 */
@Service
public class GiftServiceImpl implements GiftService {

	@Autowired
	private GiftMapper giftMapper;

	@Override
	public int delete(int id) {
		Gift record = giftMapper.selectByPrimaryKey(id);
		if (record != null) {
			record.setId(id);
			record.setStatus(ResultParame.ResultNumber.MINUS_ONE.getNumber());
			return giftMapper.updateByPrimaryKeySelective(record);
		}
		return 0;
	}

	@Override
	public GridPage<Gift> listGrid(Gift vo, RowBounds rowBounds) {
		GiftExample example = new GiftExample();
		GiftExample.Criteria cri = example.createCriteria();
		if (vo.getgName() != null && !vo.getgName().equals("")) {
			cri.andGNameEqualTo(vo.getgName());
		}
		cri.andStatusNotEqualTo(ResultParame.ResultNumber.MINUS_ONE.getNumber());

		example.setLimitStart(rowBounds.getOffset());
		example.setLimitEnd(rowBounds.getLimit());

		GridPage<Gift> result = new GridPage<Gift>();
		result.setRows(giftMapper.selectByExample(example));
		result.setTotal(giftMapper.countByExample(example));
		return result;
	}

	@Override
	public void save(Gift vo) {
		vo.setStatus(1);
		giftMapper.insert(vo);
	}

	@Override
	public int update(Gift vo) {
		Gift gift = giftMapper.selectByPrimaryKey(vo.getId());
		if (gift != null) {
			vo.setStatus(1);
			return giftMapper.updateByPrimaryKey(vo);
		}
		return 0;
	}

	/**
	 * @param vo
	 *            Gift
	 */
	@Override
	public List<Gift> selectByGiftProduct(Gift vo) {

		GiftExample example = new GiftExample();
		GiftExample.Criteria cri = example.createCriteria();

		if (vo.getType() != null) {
			cri.andTypeEqualTo(vo.getType());
		}

		return giftMapper.selectByExample(example);
	}
}
