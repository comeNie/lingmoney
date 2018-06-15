package com.mrbt.lingmoney.admin.service.bank.impl;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mrbt.lingmoney.admin.service.bank.HxBorrowerInfoService;
import com.mrbt.lingmoney.mapper.HxBiddingMapper;
import com.mrbt.lingmoney.mapper.HxBorrowerInfoCustomerMapper;
import com.mrbt.lingmoney.mapper.HxBorrowerInfoMapper;
import com.mrbt.lingmoney.model.HxBidding;
import com.mrbt.lingmoney.model.HxBorrowerInfo;
import com.mrbt.lingmoney.model.HxBorrowerInfoCustomer;
import com.mrbt.lingmoney.model.HxBorrowerInfoExample;
import com.mrbt.lingmoney.utils.PageInfo;
import com.mrbt.lingmoney.utils.ResultParame.ResultNumber;

/**
 * 
 * @author Administrator
 *
 */
@Service
public class HxBorrowerInfoServiceImpl implements HxBorrowerInfoService {

	@Autowired
	private HxBorrowerInfoMapper hxBorrowerInfoMapper;
	@Autowired
    private HxBiddingMapper hxBiddingMapper;
    @Autowired
	private HxBorrowerInfoCustomerMapper hxBorrowerInfoCustomerMapper;

	@Override
	public HxBorrowerInfo findById(String id) {
		return hxBorrowerInfoMapper.selectByPrimaryKey(id);
	}

	@Override
	@Transactional
    public void delete(String id) {
        hxBorrowerInfoMapper.deleteByPrimaryKey(id);
    }

	@Override
	@Transactional
	public int save(HxBorrowerInfo vo) {
		// 同一标的同一借款人只能添加一次
		try {
			HxBorrowerInfoExample hxBorrowerInfoExample = new HxBorrowerInfoExample();
			hxBorrowerInfoExample.createCriteria().andBiddingIdEqualTo(vo.getBiddingId());
			long count = hxBorrowerInfoMapper.countByExample(hxBorrowerInfoExample);
			if (count > 0) {
				return ResultNumber.MINUS3.getNumber();
			}
			hxBorrowerInfoExample.createCriteria().andBiddingIdEqualTo(vo.getBiddingId()).andBwIdEqualTo(vo.getBwId());
			count = hxBorrowerInfoMapper.countByExample(hxBorrowerInfoExample);
			if (count > 0) {
				return ResultNumber.MINUS2.getNumber();
			}
			vo.setId(UUID.randomUUID().toString().replace("-", ""));

            // 修改借款人总数
            HxBidding hxBidding = new HxBidding();
            hxBidding.setId(vo.getBiddingId());
            hxBidding.setBwTotalNum(1); // 目前只有一人，此处写死
            hxBiddingMapper.updateByPrimaryKeySelective(hxBidding);

			return hxBorrowerInfoMapper.insert(vo);
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
	}

	@Override
	@Transactional
	public int updateByPrimaryKeySelective(HxBorrowerInfo vo) {
		// 同一标的同一借款人只能添加一次
		try {
			HxBorrowerInfoExample hxBorrowerInfoExample = new HxBorrowerInfoExample();
			hxBorrowerInfoExample.createCriteria().andBiddingIdEqualTo(vo.getBiddingId()).andBwIdEqualTo(vo.getBwId())
					.andBwIdNotEqualTo(hxBorrowerInfoMapper.selectByPrimaryKey(vo.getId()).getBwId());
			long count = hxBorrowerInfoMapper.countByExample(hxBorrowerInfoExample);
			if (count > 0) {
				return ResultNumber.MINUS2.getNumber();
			}
			return hxBorrowerInfoMapper.updateByPrimaryKeySelective(vo);
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
	}

	@Override
	public PageInfo getList(PageInfo pageInfo) {
		int resSize = hxBorrowerInfoCustomerMapper.findCountByCondition(pageInfo);
		List<HxBorrowerInfoCustomer> list = hxBorrowerInfoCustomerMapper.findByCondition(pageInfo);
		pageInfo.setRows(list);
		pageInfo.setTotal(resSize);
		return pageInfo;
	}

}
