package com.mrbt.lingmoney.service.info.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;

import com.mrbt.lingmoney.mapper.InfoNewsMapper;
import com.mrbt.lingmoney.model.InfoNews;
import com.mrbt.lingmoney.model.InfoNewsExample;
import com.mrbt.lingmoney.service.info.InfoNewsService;
import com.mrbt.lingmoney.utils.AppCons;
import com.mrbt.lingmoney.utils.GridPage;
import com.mrbt.lingmoney.utils.PageInfo;

/**
 * @author syb
 * @date 2017年5月8日 下午4:14:04
 * @version 1.0
 * @description
 **/
@Service
public class InfoNewsServiceImpl implements InfoNewsService {
	@Autowired
	private InfoNewsMapper infoNewsMapper;

	@Override
	public GridPage<InfoNews> listGrid(Integer pageNo) {
		InfoNewsExample example = new InfoNewsExample();
		example.createCriteria().andStatusEqualTo(1);
		example.setOrderByClause("p_dt desc");
		PageInfo pi = new PageInfo(pageNo, AppCons.DEFAULT_PAGE_SIZE);
		example.setLimitStart(pi.getFrom());
		example.setLimitEnd(pi.getSize());
		GridPage<InfoNews> gridPage = new GridPage<InfoNews>();
		gridPage.setRows(infoNewsMapper.selectByExample(example));
		gridPage.setTotal(infoNewsMapper.countByExample(example));
		return gridPage;
	}

	@Override
	public void packageDetailsInfo(ModelMap modelMap, Integer id) {
		InfoNews infoNewsNow = infoNewsMapper.selectByPrimaryKey(id);
		modelMap.addAttribute("infoNews_now", infoNewsNow);
		// list用于接收id
		List<Integer> idList = infoNewsMapper.findIdList();
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
							InfoNews infoNewsPre = infoNewsMapper.selectByPrimaryKey(idPre);
							modelMap.addAttribute("infoNews_pre", infoNewsPre);
						}
						if (indexThis >= 0 && indexThis < listCount - 1) {
							idNext = idList.get(indexThis + 1);
							InfoNews infoNewsNext = infoNewsMapper.selectByPrimaryKey(idNext);
							modelMap.addAttribute("infoNews_next", infoNewsNext);
						}
					}
				}
			}
		}
	}
}
