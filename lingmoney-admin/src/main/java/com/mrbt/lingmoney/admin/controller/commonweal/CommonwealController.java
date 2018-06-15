package com.mrbt.lingmoney.admin.controller.commonweal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.mrbt.lingmoney.admin.controller.base.BaseController;
import com.mrbt.lingmoney.admin.service.commonweal.CommonwealService;
import com.mrbt.lingmoney.commons.cache.RedisDao;
import com.mrbt.lingmoney.model.ActivityPropertyWithBLOBs;
import com.mrbt.lingmoney.model.PublicBenefitActivities;
import com.mrbt.lingmoney.model.PublicBenefitActivitiesWithBLOBs;
import com.mrbt.lingmoney.utils.GridPage;
import com.mrbt.lingmoney.utils.PageInfo;
import com.mrbt.lingmoney.utils.ResponseUtils;

@Controller
@RequestMapping("/commonweal")
public class CommonwealController extends BaseController {
	
	@Autowired
	private CommonwealService commonwealService;
	
	@Autowired
	private RedisDao redisDao;
	
	private static final String SET_LOVE_NUMBER = "SET_LOVE_NUMBER";
	
	/**
	 * 查询
	 * @param page	页数
	 * @param rows	条数
	 * @return	return
	 */
	@RequestMapping("/list")
	@ResponseBody
	public Object list(Integer page, Integer rows) {
		GridPage<PublicBenefitActivitiesWithBLOBs> gridPage = commonwealService.listGrid(new PageInfo(page, rows));
		return gridPage;
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
	public Object saveAndUpdate(MultipartHttpServletRequest request, PublicBenefitActivitiesWithBLOBs vo) throws Exception {
		try {
			
			MultipartFile file1 = request.getFile("path1");

			int res = commonwealService.saveAndUpdate(file1, vo);
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
	
	/**
	 * 删除
	 * @param request	request
	 * @param vo	vo
	 * @return		return
	 * @throws Exception	Exception
	 */
	@RequestMapping("/del")
	@ResponseBody
	public Object del(Integer id) {
		try {
			if (id != null && id > 0) {
				int res = commonwealService.del(id);
				if (res > 0) {
					if(res == 2) {
						return ResponseUtils.failure(ResponseUtils.ERROR_PARAM, "不是初始化状态不能删除");
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
				int res = commonwealService.push(id);
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
	 * 完成
	 * @param request	request
	 * @param vo	vo
	 * @return		return
	 * @throws Exception	Exception
	 */
	@RequestMapping("/fulfil")
	@ResponseBody
	public Object fulfil(Integer id) {
		try {
			if (id != null && id > 0) {
				int res = commonwealService.fulfil(id);
				if (res > 0) {
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
	 * 设置用户获取爱心值，APP每天打开一次获取一次，不重复获取
	 * @param request	request
	 * @param vo	vo
	 * @return		return
	 * @throws Exception	Exception
	 */
	@RequestMapping("/setLoveNum")
	@ResponseBody
	public Object setLoveNum(Integer num) {
		PageInfo pageInfo = new PageInfo();
		if(num != null && num > 0) {
			redisDao.set(SET_LOVE_NUMBER, num);
			pageInfo.setCode(200);
			pageInfo.setMsg("设置完成");
		}else {
			pageInfo.setCode(1006);
			pageInfo.setMsg("参数不能为空");
		}
		return pageInfo;
	}
	
	/**
	 * 获取设置用户获取爱心值，APP每天打开一次获取一次，不重复获取
	 * @param request	request
	 * @param vo	vo
	 * @return		return
	 * @throws Exception	Exception
	 */
	@RequestMapping("/getLoveNum")
	@ResponseBody
	public Object getLoveNum() {
		PageInfo pageInfo = new PageInfo();
		Object num = redisDao.get(SET_LOVE_NUMBER);
		if (num != null) {
			pageInfo.setCode(200);
			pageInfo.setMsg("获取成功");
			pageInfo.setObj(num);
		} else {
			pageInfo.setCode(1006);
			pageInfo.setMsg("没有设置爱心值");
		}
		return pageInfo;
	}
}
