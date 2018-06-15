package com.mrbt.lingmoney.service.info.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;

import com.mrbt.lingmoney.mapper.InfoMediaMapper;
import com.mrbt.lingmoney.model.InfoMedia;
import com.mrbt.lingmoney.model.InfoMediaExample;
import com.mrbt.lingmoney.service.info.InfoMediaService;
import com.mrbt.lingmoney.utils.AppCons;
import com.mrbt.lingmoney.utils.GridPage;
import com.mrbt.lingmoney.utils.PageInfo;

/**
 * @author syb
 * @date 2017年5月8日 下午3:52:43
 * @version 1.0
 * @description
 **/
@Service
public class InfoMediaServiceImpl implements InfoMediaService {
	@Autowired
	private InfoMediaMapper infoMediaMapper;

	@Override
	public GridPage<InfoMedia> listGrid(Integer pageNo) {
		InfoMediaExample example = new InfoMediaExample();
		example.createCriteria().andStatusEqualTo(1);
		example.setOrderByClause("p_dt desc");
		PageInfo pi = new PageInfo(pageNo, AppCons.DEFAULT_PAGE_SIZE);
		example.setLimitStart(pi.getFrom());
		example.setLimitEnd(pi.getSize());
		GridPage<InfoMedia> gridPage = new GridPage<InfoMedia>();
		gridPage.setRows(infoMediaMapper.selectByExample(example));
		gridPage.setTotal(infoMediaMapper.countByExample(example));
		return gridPage;
	}

	@Override
	public void packageDetailInfo(ModelMap modelMap, Integer id) {
		InfoMedia infoMediaNow = infoMediaMapper.selectByPrimaryKey(id);
		modelMap.addAttribute("infoMedia_now", infoMediaNow);
		// list用于接收id
		List<Integer> idList = infoMediaMapper.findIdList();
        if (null != idList && idList.size() > 0) {
			int listCount = idList.size();
            int idThis = id;
            int idPre, idNext, indexThis;
			if (listCount > 0) {
				for (int i = 0; i < listCount; i++) {
                    if (idThis == idList.get(i)) {
                        indexThis = i;
                        if (indexThis > 0 && indexThis <= listCount - 1) {
                            idPre = idList.get(indexThis - 1);
                            InfoMedia infoMediaPre = infoMediaMapper.selectByPrimaryKey(idPre);
                            modelMap.addAttribute("infoMedia_pre", infoMediaPre);
						}
                        if (indexThis >= 0 && indexThis < listCount - 1) {
                            idNext = idList.get(indexThis + 1);
                            InfoMedia infoMediaNext = infoMediaMapper.selectByPrimaryKey(idNext);
                            modelMap.addAttribute("infoMedia_next", infoMediaNext);
						}
					}
				}
			}
		}
	}

}
