package com.mrbt.lingmoney.service.users.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;

import com.mrbt.lingmoney.mapper.UsersAskMapper;
import com.mrbt.lingmoney.mapper.UsersPropertiesMapper;
import com.mrbt.lingmoney.mapper.UsresDegreeMapper;
import com.mrbt.lingmoney.model.UsersAsk;
import com.mrbt.lingmoney.model.UsersAskExample;
import com.mrbt.lingmoney.model.UsersProperties;
import com.mrbt.lingmoney.model.UsresDegree;
import com.mrbt.lingmoney.service.users.UsersAskService;
import com.mrbt.lingmoney.utils.AppCons;
import com.mrbt.lingmoney.utils.GridPage;
import com.mrbt.lingmoney.utils.PageInfo;

/**
 * @author syb
 * @date 2017年5月8日 下午5:51:02
 * @version 1.0
 * @description
 **/
@Service
public class UsersAskServiceImpl implements UsersAskService {
	@Autowired
	private UsersAskMapper usersAskMapper;
	@Autowired
	private UsersPropertiesMapper usersPropertiesMapper;
	@Autowired
	private UsresDegreeMapper usresDegreeMapper;

	@Override
	public GridPage<UsersAsk> packageListInfo(ModelMap model, String title, Integer pageNo, Integer status,
			Integer type, Integer hot) {
        final int specialVal = 4; // 条件特殊值
		UsersAskExample uaExample = new UsersAskExample();
		UsersAskExample.Criteria criteria = uaExample.createCriteria();
		if (StringUtils.isEmpty(type)) {
			type = 0;
		}
		criteria.andTypeEqualTo(type);
        if (!StringUtils.isEmpty(status) && status != specialVal) {
			criteria.andStatusEqualTo(status);
			model.addAttribute("status", status);
		}
        if (!StringUtils.isEmpty(hot) && hot != specialVal) {
			criteria.andHotEqualTo(hot);
			model.addAttribute("hot", hot);
		}
		if (title != null && !"请输入搜索关键词".equals(title)) {
			criteria.andTitleLike("%" + title + "%");
		} else {
			title = "请输入搜索关键词";
		}
		uaExample.setOrderByClause("time desc");
		if (StringUtils.isEmpty(pageNo)) {
			pageNo = 1;
		}
		PageInfo pi = new PageInfo(pageNo, AppCons.DEFAULT_PAGE_SIZE);
		uaExample.setLimitStart(pi.getFrom());
		uaExample.setLimitEnd(pi.getSize());
		GridPage<UsersAsk> gridPage = new GridPage<UsersAsk>();
		gridPage.setRows(usersAskMapper.selectByExample(uaExample));
		gridPage.setTotal(usersAskMapper.countByExample(uaExample));
		model.addAttribute("gridPage", gridPage);
		model.addAttribute("pageNo", pageNo);
		model.addAttribute("pageSize", AppCons.DEFAULT_PAGE_SIZE);
		model.addAttribute("totalSize", gridPage.getTotal());
		model.addAttribute("title", title);
		return gridPage;
	}

	@Override
	public void packageDetailInfo(ModelMap model, Integer id) {
		UsersAsk usersAsk = usersAskMapper.selectByPrimaryKey(id);

		if (null != usersAsk) {
			String keyword = usersAsk.getKeyword();

			if (keyword != null && !"".equals(keyword)) {
				String[] keywordStr = keyword.split("，");
				model.addAttribute("keyword_str", keywordStr);
			}

			UsersAskExample example = new UsersAskExample();
			example.createCriteria().andAskIdEqualTo(id);
			List<UsersAsk> listUsersAsk = usersAskMapper.selectByExample(example);
			if (null != listUsersAsk && listUsersAsk.size() > 0) {
				model.addAttribute("usersAskAnwser", listUsersAsk.get(0));
			}

			Map<String, Object> map = new HashMap<String, Object>();
			String uId = usersAsk.getuId();
			UsersProperties usersProperties = usersPropertiesMapper.selectByuId(uId);
			if (usersProperties != null) {
				map.put("nickName", usersProperties.getNickName());
				if (usersProperties.getLevel() != null) {
					int level = usersProperties.getLevel();
					UsresDegree usersDegree = usresDegreeMapper.selectByPrimaryKey(level);
					map.put("degreeName", usersDegree.getName());
				}
			} else {
				map.put("degreeName", "--");
				map.put("nickName", "--");
			}
			map.put("id", usersAsk.getId());
			map.put("content", usersAsk.getContent());
			map.put("keyWord", usersAsk.getKeyword());
			map.put("title", usersAsk.getTitle());
			map.put("time", usersAsk.getTime());
			model.addAttribute("usersAskVo", map);

		} else {
			model.addAttribute("usersAskVo", null);
		}
	}

	@Override
	public void save(String uid, String newTitle, String newContent) {
		UsersAsk usersAsk = new UsersAsk();
		usersAsk.setContent(newContent);
		usersAsk.setTime(new Date());
		usersAsk.setTitle(newTitle);
		usersAsk.setuId(uid);
		usersAsk.setStatus(0);
		usersAsk.setType(0);
		usersAsk.setHot(0);
		usersAsk.setState(1);
		usersAskMapper.insert(usersAsk);
	}

}
