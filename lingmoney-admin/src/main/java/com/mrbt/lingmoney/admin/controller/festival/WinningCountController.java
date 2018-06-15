package com.mrbt.lingmoney.admin.controller.festival;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.mrbt.lingmoney.admin.controller.base.BaseController;
import com.mrbt.lingmoney.admin.service.festival.WinningCountService;
import com.mrbt.lingmoney.model.WinningCount;
import com.mrbt.lingmoney.model.WinningCountExample;
import com.mrbt.lingmoney.utils.MyUtils;
import com.mrbt.lingmoney.utils.PageInfo;
import com.mrbt.lingmoney.utils.ResultParame.ResultInfo;

/**
 * 中奖统计/限制表
 * 
 * @author lhq
 *
 */
@Controller
@RequestMapping("festival/winningCount")
public class WinningCountController extends BaseController {
	private Logger log = MyUtils.getLogger(WinningCountController.class);

	@Autowired
	private WinningCountService winningCountService;

	/**
	 * 更改活动飘窗状态
	 * 
	 * @param id
	 *            主键
	 * @return 分页实体类
	 */
	@RequestMapping("publish")
	@ResponseBody
	public Object publish(Integer id) {
		PageInfo pageInfo = new PageInfo();
		try {
			pageInfo = winningCountService.changeStatus(id);
		} catch (Exception e) {
			e.printStackTrace();
			log.error("活动飘窗，更改活动飘窗状态，失败原因是：" + e);
			pageInfo.setCode(ResultInfo.SERVER_ERROR.getCode());
			pageInfo.setMsg(ResultInfo.SERVER_ERROR.getMsg());
		}
		return pageInfo;
	}

	/**
	 * 导入限制手机号
	 * 
	 * @param request req
	 * @return 分页实体类
	 */
	@RequestMapping("upload")
	public @ResponseBody Object upload(MultipartHttpServletRequest request) {
		PageInfo pageInfo = new PageInfo();
		try {
			pageInfo = winningCountService.upload(pageInfo, request);
		} catch (Exception e) {
			e.printStackTrace();
			log.error("活动飘窗，新增或修改活动飘窗，失败原因是：" + e);
			pageInfo.setCode(ResultInfo.SERVER_ERROR.getCode());
			pageInfo.setMsg(ResultInfo.SERVER_ERROR.getMsg());
		}
		return pageInfo;
	}

	/**
	 * 查询活动飘窗列表
	 * 
	 * @param vo 活动飘窗
	 * @param page 当前页
	 * @param rows 每页显示的条数
	 * @return 分页实体类
	 */
	@RequestMapping("list")
	public @ResponseBody Object list(WinningCount vo, Integer page, Integer rows) {
		PageInfo pageInfo = new PageInfo(page, rows);
		try {
			WinningCountExample example = new WinningCountExample();
			WinningCountExample.Criteria cri = example.createCriteria();
			if (vo.getId() != null) {
				cri.andIdEqualTo(vo.getId());
			}
			if (StringUtils.isNotBlank(vo.getTelephone())) {
				cri.andTelephoneLike("%" + vo.getTelephone() + "%");
			}
			if (vo.getStatus() != null) {
				cri.andStatusEqualTo(vo.getStatus());
			}
			if (page != null && rows != null) {
				example.setLimitStart(pageInfo.getFrom());
				example.setLimitEnd(pageInfo.getSize());
			}
			pageInfo = winningCountService.getList(example);
		} catch (Exception e) {
			e.printStackTrace();
			log.error("活动飘窗，查询活动飘窗列表，失败原因是：" + e);
			pageInfo.setCode(ResultInfo.SERVER_ERROR.getCode());
			pageInfo.setMsg(ResultInfo.SERVER_ERROR.getMsg());
		}
		return pageInfo;
	}

	/**
	 * 删除活动飘窗
	 * 
	 * @param id
	 *            主键
	 * @return 分页实体类
	 */
	@RequestMapping("delete")
	@ResponseBody
	public Object delete(Integer id) {
		PageInfo pageInfo = new PageInfo();
		try {
			if (id != null && id > 0) {
				winningCountService.delete(id);
				pageInfo.setCode(ResultInfo.SUCCESS.getCode());
				pageInfo.setMsg(ResultInfo.SUCCESS.getMsg());
			} else {
				pageInfo.setCode(ResultInfo.PARAMETER_ERROR.getCode());
				pageInfo.setMsg(ResultInfo.PARAMETER_ERROR.getMsg());
			}
		} catch (Exception e) {
			e.printStackTrace();
			log.error("活动飘窗，删除活动飘窗，失败原因是：" + e);
			pageInfo.setCode(ResultInfo.SERVER_ERROR.getCode());
			pageInfo.setMsg(ResultInfo.SERVER_ERROR.getMsg());
		}
		return pageInfo;
	}

	/**
	 * 根据主键查找活动飘窗
	 * 
	 * @param id
	 *            主键
	 * @return 分页实体类
	 */
	@RequestMapping("findById")
	@ResponseBody
	public Object findById(Integer id) {
		PageInfo pageInfo = new PageInfo();
		try {
			if (id != null && id > 0) {
				WinningCount record = winningCountService.findById(id);
				if (record != null) {
					pageInfo.setCode(ResultInfo.SUCCESS.getCode());
					pageInfo.setMsg(ResultInfo.SUCCESS.getMsg());
					return pageInfo;
				}
			}
			pageInfo.setCode(ResultInfo.PARAMETER_ERROR.getCode());
			pageInfo.setMsg(ResultInfo.PARAMETER_ERROR.getMsg());
		} catch (Exception e) {
			e.printStackTrace();
			log.error("活动飘窗，根据主键查找活动飘窗，失败原因是：" + e);
			pageInfo.setCode(ResultInfo.SERVER_ERROR.getCode());
			pageInfo.setMsg(ResultInfo.SERVER_ERROR.getMsg());
		}
		return pageInfo;
	}

}
