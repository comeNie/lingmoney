package com.mrbt.lingmoney.admin.service.worldCup;

import com.mrbt.lingmoney.model.WorldCupMatchInfo;
import com.mrbt.lingmoney.utils.GridPage;
import com.mrbt.lingmoney.utils.PageInfo;

public interface WorldCupMatchService {

	/**
	 * 查询列表
	 * @param pageInfo
	 * @return
	 */
	GridPage<WorldCupMatchInfo> listGrid(PageInfo pageInfo);

	/**
	 * 发布竞猜
	 * @param id
	 * @return
	 */
	int push(Integer id);

	/**
	 * 添加比分
	 * @param vo
	 * @return
	 */
	int saveAndUpdate(WorldCupMatchInfo vo);

}
