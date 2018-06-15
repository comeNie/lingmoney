package com.mrbt.lingmoney.admin.service.festival.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mrbt.lingmoney.admin.service.festival.GiftDetailsService;
import com.mrbt.lingmoney.mapper.GiftDetailMapper;
import com.mrbt.lingmoney.model.GiftDetail;
import com.mrbt.lingmoney.model.admin.GiftDetailVo;
import com.mrbt.lingmoney.utils.PageInfo;
import com.mrbt.lingmoney.utils.ResultParame;

/**
 * 中奖礼品兑换详情
 *
 */
@Service
public class GiftDetailsServiceImpl implements GiftDetailsService {
	@Autowired
	private GiftDetailMapper giftDetailMapper;

	@Override
	public void findDataGrid(PageInfo pageInfo) {
		List<GiftDetailVo> list = giftDetailMapper.findByCondition(pageInfo);
		pageInfo.setRows(list);
		pageInfo.setTotal(giftDetailMapper.findCountByCondition(pageInfo));
	}

	@Override
	public PageInfo processingDelivery(GiftDetailVo vo) {
		PageInfo pageInfo = new PageInfo();
		try {
			if (vo != null && vo.getId() != null) {
				vo.setState(ResultParame.ResultNumber.TWO.getNumber());
				vo.setSendTime(vo.getSendTime() == null ? new Date() : vo.getSendTime());
				giftDetailMapper.updateByPrimaryKeySelective(vo);
				pageInfo.setCode(ResultParame.ResultInfo.SUCCESS.getCode());
				return pageInfo;
			}
			pageInfo.setCode(ResultParame.ResultInfo.NOT_FOUND.getCode());
			pageInfo.setMsg("参数错误，未找到记录");
		} catch (Exception e) {
			e.printStackTrace();
			pageInfo.setCode(ResultParame.ResultInfo.SERVER_ERROR.getCode());
			pageInfo.setMsg("服务器内部错误");
		}
		return pageInfo;
	}

	@Override
	public PageInfo processingReceipt(Integer id) {
		PageInfo pageInfo = new PageInfo();
		try {
			if (id != null) {
				GiftDetail record = giftDetailMapper.selectByPrimaryKey(id);
				if (record != null) {
					record.setState(ResultParame.ResultNumber.FOUR.getNumber());
					record.setReceiveTime(new Date());
					giftDetailMapper.updateByPrimaryKeySelective(record);
					pageInfo.setCode(ResultParame.ResultInfo.SUCCESS.getCode());
					return pageInfo;
				}
			}
			pageInfo.setCode(ResultParame.ResultInfo.NOT_FOUND.getCode());
			pageInfo.setMsg("参数错误，未找到记录");
		} catch (Exception e) {
			e.printStackTrace();
			pageInfo.setCode(ResultParame.ResultInfo.SERVER_ERROR.getCode());
			pageInfo.setMsg("服务器内部错误");
		}
		return pageInfo;
	}

}
