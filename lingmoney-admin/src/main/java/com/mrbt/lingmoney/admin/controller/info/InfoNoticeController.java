package com.mrbt.lingmoney.admin.controller.info;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mrbt.lingmoney.admin.controller.base.AdminUserController;
import com.mrbt.lingmoney.admin.controller.base.BaseController;
import com.mrbt.lingmoney.admin.service.info.InfoNoticeService;
import com.mrbt.lingmoney.model.InfoNotice;
import com.mrbt.lingmoney.utils.MyUtils;
import com.mrbt.lingmoney.utils.PageInfo;
import com.mrbt.lingmoney.utils.ResultParame.ResultInfo;
import com.mrbt.lingmoney.utils.ResultParame.ResultNumber;

/**
 * 页面设置——》站内公告
 * 
 * @author lihq
 * @date 2017年5月16日 下午2:34:45
 * @description
 * @version 1.0
 * @since 2017-04-12
 */
@Controller
@RequestMapping("/info/notice")
public class InfoNoticeController extends BaseController {
	private Logger log = MyUtils.getLogger(AdminUserController.class);
	@Autowired
	private InfoNoticeService infoNoticeService;

	/**
	 * 分页查询
	 * @param vo	封装对象
	 * @param page	页数
	 * @param rows	条数
	 * @return	查询结果
	 */
	@RequestMapping("list")
	@ResponseBody
	public Object list(InfoNotice vo, Integer page, Integer rows) {
		log.info("/info/notice/list");
		PageInfo pageInfo = new PageInfo(page, rows);
		try {
			if (vo != null) {
				pageInfo = infoNoticeService.list(vo, pageInfo);
				pageInfo.setCode(ResultInfo.SUCCESS.getCode());
				pageInfo.setMsg(ResultInfo.SUCCESS.getMsg());
			} else {
				pageInfo.setCode(ResultInfo.PARAMETER_ERROR.getCode());
				pageInfo.setMsg(ResultInfo.PARAMETER_ERROR.getMsg());
			}
		} catch (Exception e) {
			log.error(e.getMessage());
			e.printStackTrace();
			pageInfo.setCode(ResultInfo.SERVER_ERROR.getCode());
			pageInfo.setMsg("服务器出错");
		}
		return pageInfo;
	}

	/**
	 * 返回text内容
	 * @param id	数据ID
	 * @return	返回数据
	 */
	@RequestMapping("listContent")
	@ResponseBody
	public Object listContent(Integer id) {
		log.info("/info/notice/listContent");
		PageInfo pageInfo = new PageInfo();
		try {
			pageInfo.setMsg(infoNoticeService.listContent(id));
			pageInfo.setCode(ResultInfo.SUCCESS.getCode());
		} catch (Exception e) {
			log.error(e.getMessage());
			e.printStackTrace();
			pageInfo.setCode(ResultInfo.SERVER_ERROR.getCode());
			pageInfo.setMsg("服务器出错");
		}
		return pageInfo;

	}

	/**
	 * 修改状态
	 * @param id 主键
	 * @return 分页实体类
	 */
	@RequestMapping("publish")
	@ResponseBody
	public Object publish(Integer id) {
		log.info("/info/notice/publish");
		PageInfo pageInfo = new PageInfo();
		try {
			boolean falg = infoNoticeService.changeStatus(id);
			if (falg) {
				pageInfo.setCode(ResultInfo.SUCCESS.getCode());
			} else {
				pageInfo.setCode(ResultInfo.PARAMETER_ERROR.getCode());
				pageInfo.setMsg(ResultInfo.PARAMETER_ERROR.getMsg());
			}
		} catch (Exception e) {
			log.error(e.getMessage());
			e.printStackTrace();
			pageInfo.setCode(ResultInfo.SERVER_ERROR.getCode());
			pageInfo.setMsg("服务器出错");
		}
		return pageInfo;
	}

	/**
	 * 添加
	 * @param vo	封装对象
	 * @return	处理结果
	 */
	@RequestMapping("save")
	@ResponseBody
	public Object save(InfoNotice vo) {
		log.info("/info/notice/save");
		PageInfo pageInfo = new PageInfo();
		try {
			infoNoticeService.save(vo);
			pageInfo.setCode(ResultInfo.SUCCESS.getCode());
		} catch (Exception e) {
			log.error(e.getMessage());
			e.printStackTrace();
			pageInfo.setCode(ResultInfo.SERVER_ERROR.getCode());
			pageInfo.setMsg("服务器出错");
		}
		return pageInfo;
	}

	/**
	 * 更新
	 * @param vo	封装对象
	 * @return	处理结果
	 */
	@RequestMapping("update")
	@ResponseBody
	public Object update(InfoNotice vo) {
		log.info("/info/notice/update");
		PageInfo pageInfo = new PageInfo();
		try {
			if (vo.getId() != null) {
				infoNoticeService.update(vo);
				pageInfo.setCode(ResultInfo.SUCCESS.getCode());
			} else {
				pageInfo.setCode(ResultInfo.PARAMETER_ERROR.getCode());
				pageInfo.setMsg(ResultInfo.PARAMETER_ERROR.getMsg());
			}
		} catch (Exception e) {
			log.error(e.getMessage());
			e.printStackTrace();
			pageInfo.setCode(ResultInfo.SERVER_ERROR.getCode());
			pageInfo.setMsg(ResultInfo.SERVER_ERROR.getMsg());
		}
		return pageInfo;
	}

	/**
	 * 刷新redis
	 * @return	返回处理结果
	 */
	@RequestMapping("reload")
	@ResponseBody
	public Object reload() {
		log.info("/info/notice/reload");
		PageInfo pageInfo = new PageInfo();
		try {
			InfoNotice notice = new InfoNotice();
			notice.setStatus(1);
			infoNoticeService.reload(infoNoticeService.list(notice, 0, ResultNumber.TEN.getNumber()));
			pageInfo.setCode(ResultInfo.SUCCESS.getCode());
		} catch (Exception e) {
			log.error(e.getMessage());
			e.printStackTrace();
			pageInfo.setCode(ResultInfo.SERVER_ERROR.getCode());
			pageInfo.setMsg("服务器出错");
		}
		return pageInfo;
	}
	/**
	 * 删除
	 * @param id	数据ID
	 * @return	返回结果
	 */
	@RequestMapping("delete")
	@ResponseBody
	public Object delete(Integer id) {
		log.info("/info/media/delete");
		PageInfo pageInfo = new PageInfo();
		try {
			infoNoticeService.delete(id);
			pageInfo.setCode(ResultInfo.SUCCESS.getCode());
		} catch (Exception e) {
			log.error(e.getMessage());
			e.printStackTrace();
			pageInfo.setCode(ResultInfo.SERVER_ERROR.getCode());
			pageInfo.setMsg("服务器出错");
		}
		return pageInfo;
	}
}
