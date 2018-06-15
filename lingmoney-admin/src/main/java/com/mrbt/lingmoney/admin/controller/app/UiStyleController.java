package com.mrbt.lingmoney.admin.controller.app;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.mrbt.lingmoney.admin.service.app.UiStyleService;
import com.mrbt.lingmoney.commons.cache.RedisDao;
import com.mrbt.lingmoney.model.UiIconStyle;
import com.mrbt.lingmoney.utils.PageInfo;
import com.mrbt.lingmoney.utils.ResultParame.ResultInfo;
import com.mrbt.lingmoney.utils.ResultParame.ResultNumber;

/**
 * 移动端设置--》UI样式管理
 *
 * @author syb
 * @date 2017年9月13日 下午2:22:17
 * @version 1.0
 **/
@Controller
@RequestMapping("/uiStyle")
public class UiStyleController {
	private static final String UI_STYLE_KEY = "ui_style_lingqian";

	@Autowired
	private RedisDao redisDao;

	@Autowired
	private UiStyleService uiStyleService;

	/**
	 * 设置可用样式《ui样式选择》
	 * 
	 * @author syb
	 * @date 2017年9月13日 下午2:55:34
	 * @version 1.0
	 * @param num 
	 * @return 返回操作结果
	 *
	 */
	@RequestMapping(value = "/setUsedUiStyle", method = RequestMethod.POST)
	public @ResponseBody Object setUsedUiStyle(Integer num) {
		PageInfo pi = new PageInfo();

		try {
			redisDao.set(UI_STYLE_KEY, num);
			pi.setCode(ResultInfo.SUCCESS.getCode());
			pi.setMsg(ResultInfo.SUCCESS.getMsg());
		} catch (Exception e) {
			pi.setCode(ResultInfo.SERVER_ERROR.getCode());
			pi.setMsg(ResultInfo.SERVER_ERROR.getMsg());
			e.printStackTrace();
		}

		return pi;
	}

	/**
	 * 获取当前可用样式
	 * 
	 * @author syb
	 * @date 2017年9月13日 下午2:55:48
	 * @version 1.0
	 * @return 返回当前可用样式
	 *
	 */
	@RequestMapping(value = "/getUsedUiStyle", method = RequestMethod.POST)
	public @ResponseBody Object getUsedUiStyle() {
		if (redisDao.hasKey(UI_STYLE_KEY)) {
			return redisDao.get(UI_STYLE_KEY);
		} else {
			return ResultNumber.MINUS_ONE;
		}
	}

	/**
	 * 查询列表
	 * 
	 * @author syb
	 * @date 2017年9月13日 下午3:26:59
	 * @version 1.0
	 * @param page 当前页数
	 * @param row	每页条数
	 * @param batchNo	批次号
	 * @param status	状态
	 * @param desc	描述
	 * @return 返回数据列表
	 *
	 */
	@RequestMapping(value = "/listUiStyle", method = RequestMethod.POST)
	public @ResponseBody Object listUiStyle(Integer page, Integer row, String batchNo, Integer status, String desc) {
		PageInfo pi = new PageInfo();

		try {
			pi = uiStyleService.listUiStyle(page, row, batchNo, status, desc);
		} catch (Exception e) {
			pi.setCode(ResultInfo.SERVER_ERROR.getCode());
			pi.setMsg(ResultInfo.SERVER_ERROR.getMsg());
			e.printStackTrace();
		}

		return pi;
	}

	/**
	 * 保存或更新
	 * 
	 * @author syb
	 * @date 2017年9月13日 下午3:27:07
	 * @version 1.0
	 * @param uiIconStyle 对象
	 * @param request req
	 * @return 返回更新状态
	 *
	 */
	@RequestMapping(value = "/saveOrUpdate", method = RequestMethod.POST)
	public @ResponseBody Object saveOrUpdate(UiIconStyle uiIconStyle, MultipartHttpServletRequest request) {
		PageInfo pi = new PageInfo();

		try {
			MultipartFile file = request.getFile("imageFile");
			pi = uiStyleService.saveOrUpdate(uiIconStyle, file);

		} catch (Exception e) {
			pi.setCode(ResultInfo.SERVER_ERROR.getCode());
			pi.setMsg(ResultInfo.SERVER_ERROR.getMsg());
			e.printStackTrace();
		}

		return pi;
	}

	/**
     * 批量启用/禁用
     * 
     * @author syb
     * @date 2017年10月13日 下午4:04:00
     * @version 1.0
     * @param ids
     *            多个id用英文逗号分隔
     * @param status  状态
     * @return  返回处理结果
     *
     */
	@RequestMapping(value = "/batchUpdateStatus", method = RequestMethod.POST)
	public @ResponseBody Object batchUpdateStatus(String ids, Integer status) {
		PageInfo pi = new PageInfo();

		try {
			pi = uiStyleService.batchUpdateStatus(ids, status);
		} catch (Exception e) {
			pi.setCode(ResultInfo.SERVER_ERROR.getCode());
			pi.setMsg(ResultInfo.SERVER_ERROR.getMsg());
			e.printStackTrace();
		}

		return pi;
	}

}
