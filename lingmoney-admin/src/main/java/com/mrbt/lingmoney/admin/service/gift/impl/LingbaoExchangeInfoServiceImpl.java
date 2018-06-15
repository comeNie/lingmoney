package com.mrbt.lingmoney.admin.service.gift.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mrbt.lingmoney.admin.service.gift.LingbaoExchangeInfoService;
import com.mrbt.lingmoney.mapper.ActivityRecordMapper;
import com.mrbt.lingmoney.mapper.LingbaoAddressMapper;
import com.mrbt.lingmoney.mapper.LingbaoExchangeInfoMapper;
import com.mrbt.lingmoney.mapper.LingbaoGiftMapper;
import com.mrbt.lingmoney.mapper.UsersAccountMapper;
import com.mrbt.lingmoney.model.ActivityRecord;
import com.mrbt.lingmoney.model.LingbaoAddress;
import com.mrbt.lingmoney.model.LingbaoExchangeInfo;
import com.mrbt.lingmoney.model.LingbaoExchangeInfoVo;
import com.mrbt.lingmoney.model.LingbaoGift;
import com.mrbt.lingmoney.model.UsersAccount;
import com.mrbt.lingmoney.model.UsersAccountExample;
import com.mrbt.lingmoney.utils.PageInfo;
import com.mrbt.lingmoney.utils.ResultParame;

/**
 * 我的领地礼品兑换信息
 *
 */
@Service
public class LingbaoExchangeInfoServiceImpl implements
		LingbaoExchangeInfoService {

	@Autowired
	private LingbaoExchangeInfoMapper lingbaoExchangeInfoMapper;

	@Autowired
	private LingbaoAddressMapper lingbaoAddressMapper;
	
	@Autowired
	private LingbaoGiftMapper lingbaoGiftMapper;
	
	@Autowired
	private UsersAccountMapper usersAccountMapper;
	@Autowired
	private ActivityRecordMapper activityRecordMapper;

	@Override
	public LingbaoExchangeInfo findById(int id) {
		return lingbaoExchangeInfoMapper.selectByPrimaryKey(id);
	}

	@Override
	@Transactional
	public void delete(int id) {
		lingbaoExchangeInfoMapper.deleteByPrimaryKey(id);
	}

	@Override
	@Transactional()
	public void save(LingbaoExchangeInfo vo) {
		lingbaoExchangeInfoMapper.insert(vo);
	}

	@Override
	@Transactional()
	public void update(LingbaoExchangeInfo vo) {
		lingbaoExchangeInfoMapper.updateByPrimaryKeySelective(vo);
	}

	@Override
	public void findDataGrid(PageInfo pageInfo) {
		List<LingbaoExchangeInfoVo> list = lingbaoExchangeInfoMapper
				.findLingbaoExchangeInfoCondition(pageInfo);
		pageInfo.setRows(list);
		pageInfo.setTotal(lingbaoExchangeInfoMapper
				.findLingbaoExchangeInfoCount(pageInfo));
	}

	@Override
	public LingbaoAddress findAddressById(Integer id) {
		LingbaoAddress lingbaoAddress = lingbaoAddressMapper
				.selectByPrimaryKey(id);
		String address = lingbaoAddress.getProvince()
				+ lingbaoAddress.getCity() + lingbaoAddress.getTown()
				+ lingbaoAddress.getAddress();
		lingbaoAddress.setAddress(address);
		return lingbaoAddress;
	}

	@Override
	public PageInfo processingOrder(LingbaoExchangeInfo vo) {
		PageInfo pageInfo = new PageInfo();
		try {
			if (vo != null && vo.getId() != null) {
				LingbaoExchangeInfo record = lingbaoExchangeInfoMapper.selectByPrimaryKey(vo.getId());
				if (record != null) {
					record.setStatus(vo.getStatus());
					if (vo.getStatus() == 0) { // 兑换成功 快递公司、快递单号、发货时间、收货时间置空
						record.setExpressCompany(null);
						record.setExpressNumber(null);
						record.setSendTime(null);
						record.setReceiveTime(null);
					} else if (vo.getStatus() == 1) { // 已发货
														// 快递公司、快递单号、发货时间显示，收货时间置空
						record.setExpressCompany(vo.getExpressCompany());
						record.setExpressNumber(vo.getExpressNumber());
						record.setSendTime(vo.getSendTime());
						record.setReceiveTime(null);
						Integer giftId = vo.getGiftId();
						LingbaoGift lingbaoGift = lingbaoGiftMapper.selectByPrimaryKey(giftId);
						if (lingbaoGift.getType() == ResultParame.ResultNumber.TWO.getNumber()) { // 如果抽中的为领宝礼品
							String uId = vo.getuId();
							//activity_record新增记录
							ActivityRecord activityRecord = new ActivityRecord();
							activityRecord.setAmount(lingbaoGift.getIntegral());
							activityRecord.setContent("抽中" + lingbaoGift.getName());
							activityRecord.setName("抽奖");
							activityRecord.setStatus(0);
							activityRecord.setType(0);
							activityRecord.setuId(uId);
							activityRecord.setUseDate(new Date());
							activityRecordMapper.insertSelective(activityRecord);
							//用户账户增加领宝
							UsersAccountExample example = new UsersAccountExample();
							example.createCriteria().andUIdEqualTo(uId);
							UsersAccount usersAccount = usersAccountMapper.selectByExample(example).get(0);
							usersAccount.setCountLingbao(usersAccount.getCountLingbao() + lingbaoGift.getIntegral());
							usersAccountMapper.updateByPrimaryKeySelective(usersAccount);
						}
					} else if (vo.getStatus() == ResultParame.ResultNumber.TWO.getNumber()) { // 已收货
																								// 收货时间显示，快递公司、快递单号、发货时间隐藏
						record.setReceiveTime(vo.getReceiveTime());
					}
					lingbaoExchangeInfoMapper.updateByPrimaryKey(record);
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
