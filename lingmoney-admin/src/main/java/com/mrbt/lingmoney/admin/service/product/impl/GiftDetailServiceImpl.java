package com.mrbt.lingmoney.admin.service.product.impl;

import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mrbt.lingmoney.admin.service.product.GiftDetailService;
import com.mrbt.lingmoney.mapper.GiftDetailMapper;
import com.mrbt.lingmoney.model.GiftDetail;
import com.mrbt.lingmoney.model.GiftDetailExample;
import com.mrbt.lingmoney.utils.GridPage;
import com.mrbt.lingmoney.utils.ResultParame;

/**
 * 产品设置———》礼品详情
 *
 */
@Service
public class GiftDetailServiceImpl implements GiftDetailService {
	
	@Autowired
	private GiftDetailMapper giftDetailMapper;

	@Override
	public GridPage<GiftDetail> listGrid(GiftDetail vo, RowBounds rowBounds) {
		
		GiftDetailExample example = new GiftDetailExample();
		example.setLimitStart(rowBounds.getOffset());
		example.setLimitEnd(rowBounds.getLimit());
		
		GridPage<GiftDetail> result = new GridPage<GiftDetail>();
		result.setRows(giftDetailMapper.selectByExample(example));
		result.setTotal(giftDetailMapper.countByExample(example));
		
		return result;
	}

	@Override
	public int delete(int id) {
		GiftDetail pc = new GiftDetail();
		pc.setId(id);
		pc.setState(ResultParame.ResultNumber.TWO.getNumber());
		return giftDetailMapper.updateByPrimaryKeySelective(pc);
	}

	@Override
	public void save(GiftDetail vo) {
		giftDetailMapper.insert(vo);
	}

	@Override
	public int update(GiftDetail vo) {
		GiftDetail pc = giftDetailMapper.selectByPrimaryKey(vo.getgId());
		if (pc != null) {
			return giftDetailMapper.updateByPrimaryKeySelective(vo);
		}
		return 0;
	}

	@Override
	public void updateStatus(String[] gid, GiftDetail vo) {
		for (int i = 0; i < gid.length; i++) {
			String id = gid[i];
			GiftDetail pc = giftDetailMapper.selectByPrimaryKey(Integer.parseInt(id));
			if (pc != null) {
				pc.setId(Integer.parseInt(gid[i]));
				pc.setgDesc(vo.getgDesc());
				pc.setState(vo.getState());
				giftDetailMapper.updateByPrimaryKeySelective(pc);
			}
		}
	}

}
