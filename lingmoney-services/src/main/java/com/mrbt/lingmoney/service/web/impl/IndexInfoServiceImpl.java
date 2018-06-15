package com.mrbt.lingmoney.service.web.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;

import com.mrbt.lingmoney.commons.cache.RedisDao;
import com.mrbt.lingmoney.mapper.ActivityProductMapper;
import com.mrbt.lingmoney.mapper.CustomQueryMapper;
import com.mrbt.lingmoney.mapper.InfoBannerMapper;
import com.mrbt.lingmoney.mapper.InfoBigBannerMapper;
import com.mrbt.lingmoney.mapper.InfoMediaMapper;
import com.mrbt.lingmoney.mapper.InfoNoticeMapper;
import com.mrbt.lingmoney.mapper.NoticeMapper;
import com.mrbt.lingmoney.mapper.ProductCustomerMapper;
import com.mrbt.lingmoney.mapper.UsersAskMapper;
import com.mrbt.lingmoney.mapper.UsersMapper;
import com.mrbt.lingmoney.model.ActivityProduct;
import com.mrbt.lingmoney.model.ActivityProductExample;
import com.mrbt.lingmoney.model.InfoBanner;
import com.mrbt.lingmoney.model.InfoBannerExample;
import com.mrbt.lingmoney.model.InfoBigBanner;
import com.mrbt.lingmoney.model.InfoBigBannerExample;
import com.mrbt.lingmoney.model.InfoMedia;
import com.mrbt.lingmoney.model.InfoMediaExample;
import com.mrbt.lingmoney.model.InfoNotice;
import com.mrbt.lingmoney.model.InfoNoticeExample;
import com.mrbt.lingmoney.model.Notice;
import com.mrbt.lingmoney.model.Product;
import com.mrbt.lingmoney.model.UsersAsk;
import com.mrbt.lingmoney.model.UsersAskExample;
import com.mrbt.lingmoney.model.UsersExample;
import com.mrbt.lingmoney.service.web.IndexInfoService;
import com.mrbt.lingmoney.utils.PageInfo;
import com.mrbt.lingmoney.utils.PropertiesUtil;
import com.mrbt.lingmoney.utils.ResultParame.ResultInfo;

/**
 * @author syb
 * @date 2017年5月3日 下午3:58:19
 * @version 1.0
 * @description
 **/
@Service
public class IndexInfoServiceImpl implements IndexInfoService {
	@Autowired
	private InfoBigBannerMapper infoBigBannerMapper;
	@Autowired
	private InfoBannerMapper infoBannerMapper;
	@Autowired
	private UsersAskMapper usersAskMapper;
	@Autowired
	private InfoMediaMapper infoMediaMapper;
	@Autowired
	private InfoNoticeMapper infoNoticeMapper;
	@Autowired
	private ProductCustomerMapper productCustomerMapper;
	@Autowired
	private ActivityProductMapper activityProductMapper;
	@Autowired
	private NoticeMapper noticeMapper;
	
	@Autowired
	private RedisDao redisDao;
	
	@Autowired
	private CustomQueryMapper customQueryMapper;
	
	@Autowired
	private UsersMapper usersMapper;
	
	private static final String PC_HOME_TOTAL_INFO = "PC_HOME_TOTAL_INFO";
	
	
	@Override
	public void queryTotalData(PageInfo pageInfo) {
		
		Object object = redisDao.get(PC_HOME_TOTAL_INFO);
		
		if (object != null) {
			pageInfo.setCode(ResultInfo.SUCCESS.getCode());
			pageInfo.setMsg(ResultInfo.SUCCESS.getMsg());
			pageInfo.setObj(object);
		} else {
			//查询累计数据
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("totalSumMoney", customQueryMapper.queryTotalSumMoney());
			map.put("totalInterest", customQueryMapper.queryTotalInterest());
			
			UsersExample example = new UsersExample();
			map.put("totalUser", usersMapper.countByExample(example) + "");
			
			redisDao.set(PC_HOME_TOTAL_INFO, map);
			redisDao.expire(PC_HOME_TOTAL_INFO, 3600, TimeUnit.SECONDS);
			
			pageInfo.setCode(ResultInfo.SUCCESS.getCode());
			pageInfo.setMsg(ResultInfo.SUCCESS.getMsg());
			pageInfo.setObj(map);
		}
	}

	@Override
	public void packIndexInfo(ModelMap modelMap, String cityCode) {
		/**
		 * 首页大banner轮播,根据地区查询
		 */
		InfoBigBannerExample ibbExample = new InfoBigBannerExample();
		List<String> params = new ArrayList<String>();
		params.add("000");
		params.add(cityCode);
		ibbExample.createCriteria().andCityCodeIn(params).andStatusEqualTo(1);
		ibbExample.setOrderByClause("level desc");
		List<InfoBigBanner> listInfoBigBanner = infoBigBannerMapper.selectByExample(ibbExample);
		if (null == listInfoBigBanner || listInfoBigBanner.size() < 1) {
			// 如果没有地区banner 默认查询北京banner
			params.clear();
			params.add("000");
			params.add(PropertiesUtil.getPropertiesByKey("defaultAreaCityCode"));
			ibbExample.createCriteria().andCityCodeIn(params).andStatusEqualTo(1);
			ibbExample.setOrderByClause("level desc");
			listInfoBigBanner = infoBigBannerMapper.selectByExample(ibbExample);
		}
		modelMap.addAttribute("listInfoBigBanner", listInfoBigBanner);
		/**
		 * 首页小banner轮播
		 */
		InfoBannerExample ibExample = new InfoBannerExample();
		ibExample.createCriteria().andStatusEqualTo(1);
		ibExample.setOrderByClause("level desc");
		List<InfoBanner> listInfoBanner = infoBannerMapper.selectByExample(ibExample);
		modelMap.addAttribute("listInfoBanner", listInfoBanner);

		/**
		 * 你问我答列表
		 */
		UsersAskExample uaExample = new UsersAskExample();
		uaExample.createCriteria().andTypeEqualTo(0);
		uaExample.setOrderByClause("time desc");
		List<UsersAsk> listUsersAsk = usersAskMapper.selectByExample(uaExample);
		modelMap.addAttribute("listUsersAsk", listUsersAsk);

		/**
		 * 媒体资讯列表
		 */
		InfoMediaExample imExample = new InfoMediaExample();
		imExample.createCriteria().andStatusEqualTo(1);
		imExample.setOrderByClause("p_dt desc");
		List<InfoMedia> listInfoMedia = infoMediaMapper.selectByExample(imExample);
		modelMap.addAttribute("listInfoMedia", listInfoMedia);

		/**
		 * 站内公告列表
		 */
		InfoNoticeExample inExample = new InfoNoticeExample();
		inExample.createCriteria().andStatusEqualTo(1);
		inExample.setOrderByClause("p_dt desc");
		List<InfoNotice> listInfoNotice = infoNoticeMapper.selectByExample(inExample);
		modelMap.addAttribute("listInfoNotice", listInfoNotice);

		/**
		 * 首页产品展示
		 */
		// 明星产品 分地区
		List<Product> listx = productCustomerMapper.selectProductByIndexX(cityCode);
		modelMap.addAttribute("listx", listx);
		// 产品系列 分地区
		List<Product> listy = productCustomerMapper.selectProductByIndexY(cityCode);
		modelMap.addAttribute("listy", listy);

		/**
		 * 首页活动轮播
		 */
		ActivityProductExample apExample = new ActivityProductExample();
		apExample.createCriteria().andStatusEqualTo(1);
		List<ActivityProduct> activityList = activityProductMapper.selectByExample(apExample);
		modelMap.addAttribute("activityList", activityList);

		List<Notice> ntlist = noticeMapper.selectByExampleWithBLOBs(null);
		modelMap.addAttribute("ntlist", ntlist);
	}

	@Override
	public PageInfo packIndexInfoOfWap(PageInfo pageInfo, String cityCode) {
		List<Map<String, Object>> listMap = new ArrayList<Map<String, Object>>();
		Map<String, Object> map = new HashMap<String, Object>();
		/**
		 * 站内公告列表
		 */
		InfoNoticeExample inExample = new InfoNoticeExample();
		inExample.createCriteria().andStatusEqualTo(1);
		inExample.setOrderByClause("p_dt desc");
		List<InfoNotice> listInfoNotice = infoNoticeMapper.selectByExample(inExample);
		map.put("listInfoNotice", listInfoNotice);

		/**
		 * 首页产品展示
		 */
		// 明星产品 分地区
		List<Product> listx = productCustomerMapper.selectProductByIndexX(cityCode);
		map.put("listx", listx);
		// 产品系列 分地区
		// List<Product> listy =
		// productCustomerMapper.selectProductByIndexY(cityCode);
		// map.put("listy", listy);

		listMap.add(map);

		pageInfo.setCode(ResultInfo.SUCCESS.getCode());
		pageInfo.setRows(listMap);
		return pageInfo;
	}

}
