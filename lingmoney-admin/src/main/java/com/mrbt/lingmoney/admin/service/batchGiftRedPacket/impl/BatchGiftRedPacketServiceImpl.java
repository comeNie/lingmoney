package com.mrbt.lingmoney.admin.service.batchGiftRedPacket.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mrbt.lingmoney.admin.mongo.BatchGiftRedPacket;
import com.mrbt.lingmoney.admin.service.batchGiftRedPacket.BatchGiftRedPacketService;
import com.mrbt.lingmoney.mapper.HxRedPacketMapper;
import com.mrbt.lingmoney.mapper.UsersPropertiesMapper;
import com.mrbt.lingmoney.mapper.UsersRedPacketMapper;
import com.mrbt.lingmoney.model.HxRedPacket;
import com.mrbt.lingmoney.model.UsersRedPacket;
import com.mrbt.lingmoney.model.UsersRedPacketExample;
import com.mrbt.lingmoney.utils.DateUtils;
import com.mrbt.lingmoney.utils.PageInfo;
import com.mrbt.lingmoney.utils.ResultParame;

/** 
 * @author  suyibo 
 * @date 创建时间：2018年5月4日
 */
@Service
public class BatchGiftRedPacketServiceImpl implements BatchGiftRedPacketService {
	@Autowired
	private UsersPropertiesMapper usersPropertiesMapper;
	@Autowired
	private HxRedPacketMapper hxRedPacketMapper;
	@Autowired
	private UsersRedPacketMapper usersRedPacketMapper;

	@Override
	public PageInfo findUsersByParams(Integer pageNumber, Integer pageSize, Integer channel, String startDate, String endDate, Integer type,
			String productName) {
		PageInfo pageInfo = new PageInfo();
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("channel", channel);
			map.put("startDate", startDate);
			map.put("endDate", endDate);
			map.put("type", type);
			map.put("productName", productName);
			map.put("pageNumber", (pageNumber - 1) * pageSize);
			map.put("pageSize", pageSize);

			List<BatchGiftRedPacket> list = new ArrayList<BatchGiftRedPacket>();
			Integer count = 0;
			// 渠道
			if (null == type) {
				List<Map<String, Object>> usersList = usersPropertiesMapper.findUsersByChannel(map);
				batchGiftRedPacketUsersList(list, usersList);
				count = usersPropertiesMapper.findUsersByChannelCount(map);
			}
			// 渠道+购买产品
			else if (type == 1) {
				List<Map<String, Object>> usersList = usersPropertiesMapper.findUsersByChannelAndBuyProduct(map);
				batchGiftRedPacketUsersList(list, usersList);
				count = usersPropertiesMapper.findUsersByChannelAndBuyProductCount(map);
			}
			// 渠道+兑付产品
			else if (type == 2) {
				List<Map<String, Object>> usersList = usersPropertiesMapper.findUsersByChannelAndSellProduct(map);
				batchGiftRedPacketUsersList(list, usersList);
				count = usersPropertiesMapper.findUsersByChannelAndSellProductCount(map);
			}
			pageInfo.setCode(ResultParame.ResultInfo.SUCCESS.getCode());
			pageInfo.setRows(list);
			pageInfo.setTotal(count);
		} catch (Exception e) {
			e.printStackTrace();
			pageInfo.setCode(ResultParame.ResultInfo.NO_DATA.getCode());
			pageInfo.setMsg("查找数据异常");
		}
		return pageInfo;
	}

	/**
	 * 批量赠送优惠券用户对象
	 * @param list list
	 * @param usersList usersList
	 */
	private void batchGiftRedPacketUsersList(List<BatchGiftRedPacket> list, List<Map<String, Object>> usersList) {
		if (!usersList.isEmpty()) {
			for (Map<String, Object> usersMap : usersList) {
				BatchGiftRedPacket bgrp = new BatchGiftRedPacket();
				bgrp.setUid((String) usersMap.get("u_id"));
				bgrp.setName((String) usersMap.get("name"));
				bgrp.setTelephone((String) usersMap.get("telephone"));
				bgrp.setChannelName((String) usersMap.get("channelName"));
				bgrp.setProductName((String) usersMap.get("productName"));
				bgrp.setBuyDate((String) usersMap.get("buyDate"));
				bgrp.setBuyMoney(usersMap.get("buy_money").toString());
				bgrp.setMinSellDt((String) usersMap.get("minSellDt"));
				list.add(bgrp);
			}
		}
	}

	@Override
	public void giveRedPacket(String uIds, String rpId) {
		UsersRedPacket packet = new UsersRedPacket();
		// 获取红包对象
		HxRedPacket redPacket = hxRedPacketMapper.selectByPrimaryKey(rpId);
		if (StringUtils.isNotBlank(uIds) && null != redPacket) {
			String[] split = uIds.split(",");
			for (String uId : split) {
				// 验证用户是否获得过该红包，获得过则不再赠送
				UsersRedPacketExample example = new UsersRedPacketExample();
				example.createCriteria().andRpIdEqualTo(rpId).andUIdEqualTo(uId);
				List<UsersRedPacket> list = usersRedPacketMapper.selectByExample(example);
				if (!list.isEmpty()) {
					continue;
				}
				packet.setuId(uId);
				packet.setRpId(rpId);
				packet.setStatus(0);
				packet.setCreateTime(new Date());
				packet.setIsCheck(0);
				if (redPacket.getDelayed() != null) {
					packet.setValidityTime(DateUtils.addDay2(new Date(), redPacket.getDelayed() + 1));
				} else {
					packet.setValidityTime(redPacket.getValidityTime());
				}
				usersRedPacketMapper.insertSelective(packet);
			}
		}
	}
}
