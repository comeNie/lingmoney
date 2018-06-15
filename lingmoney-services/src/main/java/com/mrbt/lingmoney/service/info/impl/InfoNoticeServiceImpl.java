package com.mrbt.lingmoney.service.info.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;

import com.mrbt.lingmoney.mapper.InfoClientBannerMapper;
import com.mrbt.lingmoney.mapper.InfoNoticeMapper;
import com.mrbt.lingmoney.model.InfoClientBanner;
import com.mrbt.lingmoney.model.InfoClientBannerExample;
import com.mrbt.lingmoney.model.InfoNotice;
import com.mrbt.lingmoney.model.InfoNoticeExample;
import com.mrbt.lingmoney.service.info.InfoNoticeService;
import com.mrbt.lingmoney.utils.AppCons;
import com.mrbt.lingmoney.utils.GridPage;
import com.mrbt.lingmoney.utils.PageInfo;
import com.mrbt.lingmoney.utils.ResultParame.ResultInfo;

/**
 * 公告
 *
 */
@Service
public class InfoNoticeServiceImpl implements InfoNoticeService {
	@Autowired
	private InfoNoticeMapper infoNoticeMapper;
	@Autowired
	private InfoClientBannerMapper infoClientBannerMapper;

	@Override
	public PageInfo queryNoticeList(Integer page, Integer rows) {
		if (StringUtils.isEmpty(page)) {
			page = 1;
		}
		if (StringUtils.isEmpty(rows)) {
			rows = AppCons.DEFAULT_PAGE_SIZE;
		}
		PageInfo pageInfo = new PageInfo(page, rows);
		InfoNoticeExample ex = new InfoNoticeExample();
		ex.createCriteria().andStatusEqualTo(1);
		long count = infoNoticeMapper.countByExample(ex);

		ex.setLimitStart(pageInfo.getFrom());
		ex.setLimitEnd(pageInfo.getSize());
        ex.setOrderByClause("order_index desc,p_dt desc");
		List<InfoNotice> noticeLi = infoNoticeMapper.selectByExample(ex);
		if (StringUtils.isEmpty(noticeLi) || count == 0) {
            pageInfo.setCode(ResultInfo.RETURN_EMPTY_DATA.getCode());
			pageInfo.setMsg("查询公告列表为空");
			return pageInfo;
		}
        pageInfo.setCode(ResultInfo.SUCCESS.getCode());
		pageInfo.setMsg("查询公告列表成功");
		pageInfo.setRows(noticeLi);
		pageInfo.setTotal(new Long(count).intValue());
		return pageInfo;
	}

	@Override
	public PageInfo queryNoticeDetail(Integer id) {
		PageInfo pageInfo = new PageInfo();
		InfoNotice notice = infoNoticeMapper.selectByPrimaryKey(id);
		if (StringUtils.isEmpty(notice)) {
            pageInfo.setCode(ResultInfo.RETURN_EMPTY_DATA.getCode());
			pageInfo.setMsg("查询公告详情为空");
			return pageInfo;
		}
        pageInfo.setCode(ResultInfo.SUCCESS.getCode());
		pageInfo.setObj(notice);
		pageInfo.setMsg("查询公告详情成功");
		return pageInfo;
	}

	@Override
	public GridPage<InfoNotice> listGrid(Integer pageNo) {
		InfoNoticeExample example = new InfoNoticeExample();
		example.createCriteria().andStatusEqualTo(1);
		example.setOrderByClause("p_dt desc");
		PageInfo pageInfo = new PageInfo(pageNo, AppCons.DEFAULT_PAGE_SIZE);
		example.setLimitStart(pageInfo.getFrom());
		example.setLimitEnd(pageInfo.getSize());
		GridPage<InfoNotice> gridPage = new GridPage<InfoNotice>();
		gridPage.setRows(infoNoticeMapper.selectByExample(example));
		gridPage.setTotal(infoNoticeMapper.countByExample(example));
		return gridPage;
	}

	@Override
	public void packageDetailInfo(ModelMap modelMap, Integer id) {
        InfoNotice infoNoticeNow = infoNoticeMapper.selectByPrimaryKey(id);
        modelMap.addAttribute("infoNotice_now", infoNoticeNow);
		// list用于接收id
		List<Integer> idList = infoNoticeMapper.selectIdList();
        if (idList != null && idList.size() > 0) {
			int listCount = idList.size();
            int idThis = id;
            int idPre, idNext, indexThis;
			if (listCount > 0) {
				for (int i = 0; i < listCount; i++) {
                    if (idThis == idList.get(i)) {
                        indexThis = i;
                        if (indexThis > 0 && indexThis <= listCount - 1) {
                            idPre = idList.get(indexThis - 1);
                            InfoNotice infoNoticePre = infoNoticeMapper.selectByPrimaryKey(idPre);
                            modelMap.addAttribute("infoNotice_pre", infoNoticePre);
						}
                        if (indexThis >= 0 && indexThis < listCount - 1) {
                            idNext = idList.get(indexThis + 1);
                            InfoNotice infoNoticeNext = infoNoticeMapper.selectByPrimaryKey(idNext);
                            modelMap.addAttribute("infoNotice_next", infoNoticeNext);
						}
					}
				}
			}
		}
	}

	@Override
	public PageInfo allAessage() {
		PageInfo pageInfo = new PageInfo();
		// 查询未查看的活动消息对象
		InfoClientBannerExample ex = new InfoClientBannerExample();
		ex.createCriteria().andIdIsNotNull();
		List<InfoClientBanner> infoClientBannerList = infoClientBannerMapper.selectByExample(ex);
		// 查询未查看的公告消息对象
		InfoNoticeExample example = new InfoNoticeExample();
		example.createCriteria().andIdIsNotNull();
		List<InfoNotice> infoNoticeList = infoNoticeMapper.selectByExample(example);

		List<Map<String, Object>> dataList = new ArrayList<Map<String, Object>>();
		if (!infoClientBannerList.isEmpty()) {
			for (InfoClientBanner infoClientBanner : infoClientBannerList) {
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("type", 1); // 活动消息
				map.put("createDate", infoClientBanner.getCreateTime());
				dataList.add(map);
			}
		}

		if (!infoNoticeList.isEmpty()) {
			for (InfoNotice infoNotice : infoNoticeList) {
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("type", 2); // 系统消息
				map.put("createDate", infoNotice.getCtDt());
				dataList.add(map);
			}
		}
		pageInfo.setCode(ResultInfo.SUCCESS.getCode());
		pageInfo.setRows(dataList);
		return pageInfo;
	}
	
	@Override
	public PageInfo queryInfoClientBannerList(Integer page, Integer rows) {
		if (StringUtils.isEmpty(page)) {
			page = 1;
		}
		if (StringUtils.isEmpty(rows)) {
			rows = AppCons.DEFAULT_PAGE_SIZE;
		}
		PageInfo pageInfo = new PageInfo(page, rows);
		InfoClientBannerExample ex = new InfoClientBannerExample();
		ex.createCriteria().andIdIsNotNull().andStatusEqualTo(1);
		long count = infoClientBannerMapper.countByExample(ex);

		ex.setLimitStart(pageInfo.getFrom());
		ex.setLimitEnd(pageInfo.getSize());
		ex.setOrderByClause("create_time desc");
		List<InfoClientBanner> clientBannerList = infoClientBannerMapper.selectByExample(ex);
		if (StringUtils.isEmpty(clientBannerList) || count == 0) {
            pageInfo.setCode(ResultInfo.RETURN_EMPTY_DATA.getCode());
			pageInfo.setMsg("活动消息列表为空");
			return pageInfo;
		}
        pageInfo.setCode(ResultInfo.SUCCESS.getCode());
		pageInfo.setMsg("查询活动消息列表成功");
		pageInfo.setRows(clientBannerList);
		pageInfo.setTotal(new Long(count).intValue());
		return pageInfo;
	}
}
