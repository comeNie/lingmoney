package com.mrbt.lingmoney.admin.controller.worldCup;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mrbt.lingmoney.admin.service.worldCup.WorldCupMatchService;
import com.mrbt.lingmoney.model.WorldCupMatchInfo;
import com.mrbt.lingmoney.utils.GridPage;
import com.mrbt.lingmoney.utils.PageInfo;
import com.mrbt.lingmoney.utils.ResponseUtils;

/**
 * 世界杯活动后台管理信息
 * @author Administrator
 *
 */
@Controller
@RequestMapping("/worldCup")
public class WorldCupActiveController {
	
	@Autowired
	private WorldCupMatchService worldCupMatchService;
	/**
	 * 查询
	 * @param page	页数
	 * @param rows	条数
	 * @return	return
	 */
	@RequestMapping("/list")
	@ResponseBody
	public Object list(Integer page, Integer rows) {
		GridPage<WorldCupMatchInfo> gridPage = worldCupMatchService.listGrid(new PageInfo(page, rows));
		return gridPage;
	}
	
	/**
	 * 发布
	 * @param request	request
	 * @param vo	vo
	 * @return		return
	 * @throws Exception	Exception
	 */
	@RequestMapping("/push")
	@ResponseBody
	public Object push(Integer id) {
		try {
			if (id != null && id > 0) {
				int res = worldCupMatchService.push(id);
				if (res > 0) {
					if(res == 2) {
						return ResponseUtils.failure(ResponseUtils.ERROR_PARAM, "不是初始化状态不能发布");
					}
					return ResponseUtils.success();
				} else {
					return ResponseUtils.failure(ResponseUtils.ERROR_PARAM, "参数错误,未找到记录");
				}
			}else {
				return ResponseUtils.failure(ResponseUtils.ERROR_PARAM, "参数错误,ID为空");
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			return ResponseUtils.failure(ResponseUtils.ERROR_SERVER, ex.getMessage());
		}
	}
	
	/**
	 * 保存
	 * @param request	request
	 * @param vo	vo
	 * @return		return
	 * @throws Exception	Exception
	 */
	@RequestMapping("/saveAndUpdate")
	@ResponseBody
	public Object saveAndUpdate(WorldCupMatchInfo vo) throws Exception {
		try {
			int res = worldCupMatchService.saveAndUpdate(vo);
			if (res > 0) {
				return ResponseUtils.success();
			} else {
				return ResponseUtils.failure(ResponseUtils.ERROR_PARAM, "参数错误,未找到记录");
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			return ResponseUtils.failure(ResponseUtils.ERROR_SERVER, ex.getMessage());
		}
	}
	
}
