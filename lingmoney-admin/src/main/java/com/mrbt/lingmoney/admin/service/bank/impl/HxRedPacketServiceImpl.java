package com.mrbt.lingmoney.admin.service.bank.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mrbt.lingmoney.admin.service.bank.HxRedPacketService;
import com.mrbt.lingmoney.mapper.HxRedPacketCustomMapper;
import com.mrbt.lingmoney.mapper.HxRedPacketMapper;
import com.mrbt.lingmoney.mapper.ProductCategoryMapper;
import com.mrbt.lingmoney.mapper.ProductMapper;
import com.mrbt.lingmoney.mapper.TradingMapper;
import com.mrbt.lingmoney.mapper.UsersRedPacketMapper;
import com.mrbt.lingmoney.model.HxRedPacket;
import com.mrbt.lingmoney.model.HxRedPacketExample;
import com.mrbt.lingmoney.model.Product;
import com.mrbt.lingmoney.model.ProductCategory;
import com.mrbt.lingmoney.model.ProductCategoryExample;
import com.mrbt.lingmoney.model.ProductExample;
import com.mrbt.lingmoney.model.UsersRedPacket;
import com.mrbt.lingmoney.utils.Common;
import com.mrbt.lingmoney.utils.DateUtils;
import com.mrbt.lingmoney.utils.PageInfo;

/**
 * @author luox
 * @Date 2017年7月4日
 */
@Service
public class HxRedPacketServiceImpl implements HxRedPacketService {
	
	@Autowired
	private HxRedPacketMapper hxRedPacketMapper;
	@Autowired
	private HxRedPacketCustomMapper hxRedPacketCustomMapper;
	@Autowired
	private ProductCategoryMapper productCategoryMapper;
	@Autowired
	private ProductMapper productMapper;
	@Autowired
	private TradingMapper tradingMapper;
	@Autowired
	private UsersRedPacketMapper usersRedPacketMapper;

	@Override
	public void getList(Integer acquisMode, Integer hrpType, Integer status, PageInfo pageInfo) {
		
		Map<String, Object> condition = new HashMap<>();
		
		if (hrpType != null) {
			condition.put("hrpType", hrpType);
		}
		if (acquisMode != null) {
			condition.put("acquisMode", acquisMode);
		}
		if (status != null) {
			condition.put("status", status);
		}
		pageInfo.setCondition(condition);
		pageInfo.setRows(hxRedPacketCustomMapper.findBycondition(pageInfo));
		pageInfo.setTotal(hxRedPacketCustomMapper.countBycondition(pageInfo));
	}

	@Override
	public void saveOrEdit(HxRedPacket packet) {
		if (packet.getId() != null && !packet.getId().equals("")) {
			//编辑
			hxRedPacketMapper.updateByPrimaryKeySelective(packet);
		} else {
			// 保存
			packet.setId(Common.uuid());
			packet.setStatus(0);
			hxRedPacketMapper.insertSelective(packet);
		}
		
	}

	@Override
	public void updateStatus(String id, Integer status) {
		HxRedPacket packet = new HxRedPacket();
		packet.setId(id);
		packet.setStatus(status);
		hxRedPacketMapper.updateByPrimaryKeySelective(packet);
	}

	@Override
	public List<ProductCategory> getProductTypeList() {
		ProductCategoryExample example = new ProductCategoryExample();
		example.createCriteria().andStatusEqualTo(1);
		return productCategoryMapper.selectByExample(example);
	}

	@Override
	public List<Product> getProductList() {
		ProductExample example = new ProductExample();
		example.createCriteria().andStatusIn(Arrays.asList(0, 1));
		return productMapper.selectByExample(example);
	}

	@Override
	public void findBuyerByProId(Integer id, PageInfo pageInfo) {
		pageInfo.setRows(tradingMapper.findBuyerByProId(id));
	}

	@Override
	public void findRedPacketList(PageInfo pageInfo) {
		HxRedPacketExample example = new HxRedPacketExample();
		example.createCriteria().andStatusEqualTo(1).andAStartTimeLessThanOrEqualTo(new Date())
				.andAEndTimeGreaterThanOrEqualTo(new Date());
		List<HxRedPacket> hxRedPacketExampleList = hxRedPacketMapper.selectByExample(example);
		List<HxRedPacket> hxRedPacketList = new ArrayList<HxRedPacket>();
		if (!hxRedPacketExampleList.isEmpty()) {
			for (HxRedPacket hxRedPacket : hxRedPacketExampleList) {
				if (hxRedPacket.getValidityTime() == null
						|| hxRedPacket.getValidityTime().getTime() >= new Date().getTime()) {
					hxRedPacketList.add(hxRedPacket);
				}
			}
		}
		pageInfo.setRows(hxRedPacketList);
		
	}

	@Override
	public void findManualRedPacketList(PageInfo pageInfo) {
		HxRedPacketExample example = new HxRedPacketExample();
		example.createCriteria().andStatusEqualTo(1).andAStartTimeLessThanOrEqualTo(new Date())
				.andAEndTimeGreaterThanOrEqualTo(new Date());
		List<HxRedPacket> hxRedPacketExampleList = hxRedPacketMapper.selectByExample(example);
		List<HxRedPacket> hxRedPacketList = new ArrayList<HxRedPacket>();
		if (!hxRedPacketExampleList.isEmpty()) {
			for (HxRedPacket hxRedPacket : hxRedPacketExampleList) {
				if (hxRedPacket.getAcquisMode() == 0 && (hxRedPacket.getValidityTime() == null
						|| hxRedPacket.getValidityTime().getTime() >= new Date().getTime())) {
					hxRedPacketList.add(hxRedPacket);
				}
			}
		}
		pageInfo.setRows(hxRedPacketList);

	}

	@Override
	public void giveRedPacket(String uIds, String rpId) {
		
		UsersRedPacket packet = new UsersRedPacket();
		// 获取红包对象
		HxRedPacket redPacket = hxRedPacketMapper.selectByPrimaryKey(rpId);
		if (StringUtils.isNotBlank(uIds) && null != redPacket) {
			String[] split = uIds.split(",");
			for (String uId : split) {
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
