package com.mrbt.lingmoney.service.discover.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;

import com.mrbt.lingmoney.mapper.LingbaoGiftMapper;
import com.mrbt.lingmoney.mapper.LingbaoGiftTypeMapper;
import com.mrbt.lingmoney.model.LingbaoGift;
import com.mrbt.lingmoney.model.LingbaoGiftExample;
import com.mrbt.lingmoney.model.LingbaoGiftType;
import com.mrbt.lingmoney.model.LingbaoGiftTypeExample;
import com.mrbt.lingmoney.service.discover.MyPlaceWebService;
import com.mrbt.lingmoney.utils.PageInfo;
import com.mrbt.lingmoney.utils.ResultParame.ResultInfo;

/**
 * @author syb
 * @date 2017年5月4日 下午2:18:08
 * @version 1.0
 * @description
 **/
@Service
public class MyPlaceWebServiceImpl implements MyPlaceWebService {
	@Autowired
	private LingbaoGiftTypeMapper lingbaoGiftTypeMapper;
	@Autowired
	private LingbaoGiftMapper lingbaoGiftMapper;

	@Override
	public void packageIndexModelMap(ModelMap model, String uid) {
		// 查询兑换目录及礼品数据:name目录 value数据
		List<Map<String, Object>> giftlsit = queryGiftInfo();
		System.out.println("兑换目录及礼品数据---" + giftlsit);
		model.addAttribute("giftlsit", giftlsit);
		
		// 查询兑换排行榜
		LingbaoGiftExample lgExample = new LingbaoGiftExample();
		lgExample.createCriteria().andStoreGreaterThan(0).andShelfStatusEqualTo(1).andApplyTypeNotEqualTo(1);
		lgExample.setOrderByClause("exchange_count desc");
		lgExample.setLimitStart(0);
		lgExample.setLimitEnd(5);
		List<LingbaoGift> topSellList = lingbaoGiftMapper.selectByExample(lgExample);
		
		model.addAttribute("topSellList", topSellList);
		System.out.println("兑换排行榜---" + topSellList);
	}

	/**
	 * 查询所有礼品类别，及其下产品
	 *
	 * @return 礼品列表
	 */
	public List<Map<String, Object>> queryGiftInfo() {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		// 查询所有目录
		LingbaoGiftTypeExample lgtExample = new LingbaoGiftTypeExample();
		lgtExample.createCriteria().andStatusEqualTo(1);
		lgtExample.setOrderByClause("level asc");
		List<LingbaoGiftType> typelist = lingbaoGiftTypeMapper.selectByExample(lgtExample);
		
		// 循环查询该目录下内容
		if (null != typelist && typelist.size() > 0) {
			for (LingbaoGiftType lgt : typelist) {
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("typeId", lgt.getId());
				List<LingbaoGift> giftlist = lingbaoGiftMapper.queryGiftListWithCondition(map);
				// 以 name:目录名 value:目录内容格式放入map
				map.clear();
				map.put("typeName", lgt.getName());
				map.put("typeId", lgt.getId());
				map.put("value", giftlist);
				// 保存到list
				list.add(map);
			}
		}
		
		return list;
	}

	@Override
	public PageInfo queryGiftWithCondition(Integer typeId, String range, Integer page, Integer rows) {
		PageInfo pi = new PageInfo(page, rows);
		System.out.println("条件：TYPEID=" + typeId + "  领宝范围=" + range);

		Map<String, Object> params = new HashMap<String, Object>();
		params.put("start", pi.getFrom());
		params.put("number", pi.getSize());
		params.put("typeId", typeId);
		if (!StringUtils.isEmpty(range)) {
			String[] ranges = range.split("-");
			int rangestart = Integer.parseInt(ranges[0]);
			params.put("rangestart", rangestart);
			int rangeend = Integer.parseInt(ranges[1]);
			params.put("rangeend", rangeend);
		}

		List<LingbaoGift> giftList = lingbaoGiftMapper.selectGiftByTypeRange(params);
		
		System.out.println("查询到礼品数据为：" + giftList);
		
		if (null != giftList && giftList.size() > 0) {
			// 查询到礼品的数量
			int total = lingbaoGiftMapper.countGiftByTypeRange(params);
			System.out.println("礼品数量为：" + total);
			
			// 计算分页页数
			int pages = total / pi.getSize();
			if (total % pi.getSize() != 0) {
				pages = pages + 1;
			}
			
			Map<String, Object> resultMap = new HashMap<String, Object>();
			resultMap.put("giftList", giftList);
			resultMap.put("total", total);
			resultMap.put("pages", pages);
			resultMap.put("currentPage", page);
			pi.setObj(resultMap);
			
            pi.setCode(ResultInfo.SUCCESS.getCode());
			pi.setMsg("success");

		} else {
            pi.setCode(ResultInfo.EMPTY_DATA.getCode());
			pi.setMsg("暂无数据");
		}
		
		return pi;
	}

}
