package com.mrbt.lingmoney.service.common.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mrbt.lingmoney.commons.cache.RedisDao;
import com.mrbt.lingmoney.mapper.InfoBigBannerMapper;
import com.mrbt.lingmoney.mapper.InfoClientBannerMapper;
import com.mrbt.lingmoney.model.InfoBigBanner;
import com.mrbt.lingmoney.model.InfoBigBannerExample;
import com.mrbt.lingmoney.model.InfoClientBanner;
import com.mrbt.lingmoney.service.common.BannerInfoService;
import com.mrbt.lingmoney.utils.PageInfo;
import com.mrbt.lingmoney.utils.ResultParame.ResultInfo;

/**
 * BANNER INFO
 */
@Service
public class BannerInfoServiceImpl implements BannerInfoService {
	
    private static final Logger LOGGER = LogManager.getLogger(BannerInfoServiceImpl.class);
    
    @Autowired
	private InfoBigBannerMapper infoBigBannerMapper;
	
	@Autowired
	private InfoClientBannerMapper infoClientBannerMapper;
	
	@Autowired
	private RedisDao redisDao;
	
	private static final String PC_HOME_BANNER_INFO = "PC_HOME_BANNER_INFO";
	
	private static final String MOBILE_HOME_BANNER_INFO = "MOBILE_HOME_BANNER_INFO_";

	@Override
	public PageInfo queryhomeMainBanner(String sizeCode, String cityCode, PageInfo pageInfo) {
		
		if (StringUtils.isBlank(sizeCode)) {
            pageInfo.setCode(ResultInfo.PARAM_MISS.getCode());
			pageInfo.setMsg("尺寸编码为空");
			return pageInfo;
		}
		
		if (StringUtils.isBlank(cityCode) || !NumberUtils.isNumber(cityCode)) {
			cityCode = "131";
		}
		
		String key = MOBILE_HOME_BANNER_INFO + sizeCode;
		if (redisDao.get(key) != null) {
			List<InfoClientBanner> bannerList = (List<InfoClientBanner>) redisDao.get(key);
			pageInfo.setCode(ResultInfo.SUCCESS.getCode());
			pageInfo.setMsg("查询成功");
			pageInfo.setRows(bannerList);
			pageInfo.setTotal(bannerList.size());
		} else {
			// 根据sizeCode拼出数据库列名
			String sizeCodePath = "code_path" + sizeCode;
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("sizeCode", sizeCodePath);
			map.put("cityCode", cityCode);
			List<InfoClientBanner> bannerList = infoClientBannerMapper.selectBySizeCode(map);
			
	        if (bannerList != null && bannerList.size() > 0) {
	            pageInfo.setCode(ResultInfo.SUCCESS.getCode());
				pageInfo.setMsg("查询成功");
				pageInfo.setRows(bannerList);
				pageInfo.setTotal(bannerList.size());
				
				redisDao.set(key, bannerList);
				
	        } else {
	            pageInfo.setCode(ResultInfo.RETURN_DATA_EMPTY_DATA.getCode());
				pageInfo.setMsg("查询数据为空");
			}
		}
		return pageInfo;
	}

	@Override
	public void queryPcHomeBanner(String cityCode, PageInfo pageInfo) {
		LOGGER.info("查询PC端首页BANNER信息");
		
		if (redisDao.get(PC_HOME_BANNER_INFO) != null) {
			pageInfo.setCode(ResultInfo.SUCCESS.getCode());
			pageInfo.setMsg(ResultInfo.SUCCESS.getMsg());
			pageInfo.setRows((List<InfoBigBanner>) redisDao.get(PC_HOME_BANNER_INFO));
		} else {
			InfoBigBannerExample ibbExample = new InfoBigBannerExample();
			ibbExample.createCriteria().andStatusEqualTo(1);
			ibbExample.setOrderByClause("level desc");
			List<InfoBigBanner> listInfoBigBanner = infoBigBannerMapper.selectByExample(ibbExample);
			if (listInfoBigBanner != null && listInfoBigBanner.size() > 0) {
				
				List<InfoBigBanner> listInfoBigBannerVo = new ArrayList<InfoBigBanner>();
				
				//处理图片请求路径为https
				for(InfoBigBanner InfoBigBanner : listInfoBigBanner) {
					InfoBigBanner.setPath(InfoBigBanner.getPath().replace("http://", "https://"));
					listInfoBigBannerVo.add(InfoBigBanner);
				}
				
				pageInfo.setCode(ResultInfo.SUCCESS.getCode());
				pageInfo.setMsg(ResultInfo.SUCCESS.getMsg());
				pageInfo.setRows(listInfoBigBannerVo);
				redisDao.set(PC_HOME_BANNER_INFO, listInfoBigBannerVo);
			} else {
				pageInfo.setCode(ResultInfo.EMPTY_DATA.getCode());
				pageInfo.setMsg(ResultInfo.EMPTY_DATA.getMsg());
			}
		}
	}

}
